package net.pneumono.pneumonocore.config_api;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.nbt.NbtCompound;
import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.configurations.ConfigManager;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ConfigFile {
    private final String modID;
    public List<AbstractConfiguration<?>> configurations = new ArrayList<>();

    protected ConfigFile(String modID) {
        this.modID = modID;
    }

    public String getModID() {
        return modID;
    }

    public AbstractConfiguration<?> getConfiguration(String name) {
        for (AbstractConfiguration<?> configuration : this.configurations) {
            if (configuration.getName().equals(name)) return configuration;
        }
        return null;
    }

    /**
     * Updates configurations with values from the config file.
     */
    public void readFromFile() {
        File configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), modID + ".json");

        // Create config file if it does not exist already
        if (!configFile.exists()) {
            ConfigApi.LOGGER.info("Config file for mod '{}' does not exist. Creating new one with default values...", modID);
            writeToFile();
            return;
        }

        // Read config file
        JsonObject jsonObject;
        try {
            Reader reader = Files.newBufferedReader(configFile.toPath());
            jsonObject = new GsonBuilder().setPrettyPrinting().create().fromJson(reader, JsonObject.class);
            reader.close();
        } catch (IOException e) {
            ConfigApi.LOGGER.error("Could not read config file for mod '{}'. Default config values will be used instead.", this.modID, e);
            return;
        } catch (JsonSyntaxException e) {
            ConfigApi.LOGGER.error("Config file for mod '{}' did not use valid syntax. Default config values will be used instead.", this.modID, e);
            return;
        }

        if (jsonObject == null) {
            ConfigApi.LOGGER.error("An error occurred reading config file for mod '{}'. Default config values will be used instead.", this.modID);
            return;
        }

        // Parse config file
        boolean shouldWrite = false;
        for (AbstractConfiguration<?> configuration : this.configurations) {
            String name = configuration.getName();

            if (!jsonObject.has(name)) {
                ConfigApi.LOGGER.warn("Config file for mod '{}' does not contain a value for config '{}'.", this.modID, configuration.getID());
                shouldWrite = true;
                continue;
            }

            if (!setConfigValue(configuration, jsonObject.get(name))) {
                ConfigApi.LOGGER.warn("Config file for mod '{}' contains invalid value '{}' for config '{}'. The default config value will be used instead.", this.modID, jsonObject, configuration.getID());
                shouldWrite = true;
            }
        }

        // Update outdated/incomplete config files
        if (shouldWrite) {
            writeToFile();
        }
    }

    private static  <T> boolean setConfigValue(AbstractConfiguration<T> config, JsonElement jsonElement) {
        DataResult<Pair<T, JsonElement>> result = config.getValueCodec().decode(JsonOps.INSTANCE, jsonElement);
        if (result.isError()) {
            return false;
        }
        ConfigManager.setValue(config, result.getOrThrow().getFirst());
        return true;
    }

    /**
     * Updates the config file with values from configurations.
     */
    public void writeToFile() {
        File configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), modID + ".json");

        // Create JsonObject
        JsonObject jsonObject = new JsonObject();
        for (AbstractConfiguration<?> config : this.configurations) {
            JsonElement jsonElement = encodeJson(config);
            jsonObject.add(config.getName(), jsonElement);
        }

        // Write JsonObject to config file
        try {
            Writer writer = Files.newBufferedWriter(configFile.toPath());
            (new GsonBuilder().setPrettyPrinting().create()).toJson(jsonObject, writer);
            writer.close();
        } catch (IOException e) {
            ConfigApi.LOGGER.error("Could not write configuration file for mod {}.", modID, e);
        }
    }

    private static <T> JsonElement encodeJson(AbstractConfiguration<T> config) {
        DataResult<JsonElement> result = config.getValueCodec().encodeStart(JsonOps.INSTANCE, ConfigManager.getLoadedValue(config));
        if (result.isError()) {
            ConfigApi.LOGGER.error("Could not encode value for config '{}'. The default value will be encoded instead.", config.getID());

            result = config.getValueCodec().encodeStart(JsonOps.INSTANCE, ConfigManager.getLoadedValue(config));
        }

        return result.getOrThrow(message -> new IllegalStateException("Could not encode default value for config '" + config.getID() + "'"));
    }

    public NbtCompound toNbt() {
        NbtCompound compound = new NbtCompound();
        for (AbstractConfiguration<?> config : this.configurations) {
            putConfigValue(compound, config);
        }
        return compound;
    }

    private static <T> void putConfigValue(NbtCompound compound, AbstractConfiguration<T> config) {
        compound.put(config.getName(), config.getValueCodec(), ConfigManager.getLoadedValue(config));
    }
}
