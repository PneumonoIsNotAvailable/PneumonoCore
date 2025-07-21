package net.pneumono.pneumonocore.config_api;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.fabricmc.loader.api.FabricLoader;
import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.configurations.ConfigManager;
import net.pneumono.pneumonocore.config_api.enums.LoadType;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ConfigFile {
    private final String modId;
    private final List<AbstractConfiguration<?>> configurations = new ArrayList<>();

    protected ConfigFile(String modId) {
        this.modId = modId;
    }

    public String getModId() {
        return modId;
    }

    public void addConfiguration(AbstractConfiguration<?> configuration) {
        this.configurations.add(configuration);
    }

    public List<AbstractConfiguration<?>> getConfigurations() {
        return configurations;
    }

    public AbstractConfiguration<?> getConfiguration(String name) {
        for (AbstractConfiguration<?> configuration : this.configurations) {
            if (configuration.info().getName().equals(name)) return configuration;
        }
        return null;
    }

    /**
     * Updates the saved values of the configurations using values from the config file.
     *
     * <p>Also updates the effective values, if the load type is high enough.
     */
    public void readSavedFromFile(LoadType loadType) {
        File configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), modId + ".json");

        // Create config file if it does not exist already
        if (!configFile.exists()) {
            ConfigApi.LOGGER.info("Config file for mod '{}' does not exist. Creating new one with default values...", modId);
            writeSavedToFile();
            return;
        }

        // Read config file
        JsonObject jsonObject;
        try {
            Reader reader = Files.newBufferedReader(configFile.toPath());
            jsonObject = new GsonBuilder().setPrettyPrinting().create().fromJson(reader, JsonObject.class);
            reader.close();
        } catch (IOException e) {
            ConfigApi.LOGGER.error("Could not read config file for mod '{}'. Default config values will be used instead.", this.modId, e);
            return;
        } catch (JsonSyntaxException e) {
            ConfigApi.LOGGER.error("Config file for mod '{}' did not use valid syntax. Default config values will be used instead.", this.modId, e);
            return;
        }

        if (jsonObject == null) {
            ConfigApi.LOGGER.error("An error occurred reading config file for mod '{}'. Default config values will be used instead.", this.modId);
            return;
        }

        // Parse config file
        boolean shouldWrite = false;
        for (AbstractConfiguration<?> configuration : this.configurations) {
            String name = configuration.info().getName();

            if (!jsonObject.has(name)) {
                ConfigApi.LOGGER.warn("Config file for mod '{}' does not contain a value for config '{}'.", this.modId, configuration.info().getId());
                shouldWrite = true;
                continue;
            }

            JsonElement element = jsonObject.get(name);
            if (!setConfigValue(configuration, element, loadType)) {
                ConfigApi.LOGGER.warn("Config file for mod '{}' contains invalid value '{}' for config '{}'. The default config value will be used instead.", this.modId, element, configuration.info().getId());
                shouldWrite = true;
            }
        }

        // Update outdated/incomplete config files
        if (shouldWrite) {
            writeSavedToFile();
        }
    }

    private static <T> boolean setConfigValue(AbstractConfiguration<T> config, JsonElement jsonElement, LoadType loadType) {
        DataResult<Pair<T, JsonElement>> result = config.getValueCodec().decode(JsonOps.INSTANCE, jsonElement);
        if (result.isError()) {
            return false;
        }
        ConfigManager.setValue(config, result.getOrThrow().getFirst(), loadType, null);
        return true;
    }

    /**
     * Writes the saved values of the configurations to the config file.
     */
    public void writeSavedToFile() {
        JsonObject jsonObject = new JsonObject();
        for (AbstractConfiguration<?> config : this.configurations) {
            JsonElement jsonElement = encodeJson(config);
            jsonObject.add(config.info().getName(), jsonElement);
        }

        writeObjectToFile(jsonObject);
    }

    private static <T> JsonElement encodeJson(AbstractConfiguration<T> config) {
        DataResult<JsonElement> result = config.getValueCodec().encodeStart(JsonOps.INSTANCE, ConfigManager.getSavedValue(config));
        if (result.isError()) {
            ConfigApi.LOGGER.error("Could not encode value for config '{}'. The default value will be encoded instead.", config.info().getId());

            result = config.getValueCodec().encodeStart(JsonOps.INSTANCE, config.info().getDefaultValue());
        }

        return result.getOrThrow(message -> new IllegalStateException("Could not encode default value for config '" + config.info().getId() + "'"));
    }

    public void writeObjectToFile(JsonObject jsonObject) {
        File configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), modId + ".json");

        try {
            Writer writer = Files.newBufferedWriter(configFile.toPath());
            (new GsonBuilder().setPrettyPrinting().create()).toJson(jsonObject, writer);
            writer.close();
        } catch (IOException e) {
            ConfigApi.LOGGER.error("Could not write configuration file for mod {}.", modId, e);
        }
    }
}
