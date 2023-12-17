package net.pneumono.pneumonocore.config;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.util.*;

public class ModConfigurations {
    protected final String modID;
    protected List<AbstractConfiguration<?>> configurations = new ArrayList<>();

    protected ModConfigurations(String modID, AbstractConfiguration<?> firstConfig) {
        this.modID = modID;
        this.configurations.add(firstConfig);
    }

    /**
     * Reloads this config file.
     */
    public void reload() {
        File configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), modID + ".json");

        // Creates config file if it does not exist already
        boolean savedConfigs = false;
        if (!configFile.exists()) {
            writeConfigs(configurations, true);
            savedConfigs = true;
        }

        // Reads config file
        JsonObject jsonObject = null;
        try {
            Reader reader = Files.newBufferedReader(configFile.toPath());
            jsonObject = new GsonBuilder().setPrettyPrinting().create().fromJson(reader, JsonObject.class);
            reader.close();
        } catch (IOException ignored) {}

        // Creates loadedConfigs using jsonObject (using defaultConfig values where necessary)
        List<AbstractConfiguration<?>> loadedConfigs = new ArrayList<>();
        for (AbstractConfiguration<?> config : configurations) {
            if (jsonObject != null && jsonObject.get(config.getName()) != null) {

                JsonElement element = jsonObject.get(config.getName());
                loadedConfigs.add(config.fromElement(element));

            } else {
                loadedConfigs.add(config);
            }
        }

        // Update outdated/incomplete config files
        if (!savedConfigs) {
            writeConfigs(loadedConfigs, false);
        }

        // Add Configurations if they don't already exist/update existing Configurations
        for (AbstractConfiguration<?> config : loadedConfigs) {
            for (AbstractConfiguration<?> checkedConfig : configurations) {
                if (Objects.equals(checkedConfig.getName(), config.getName())) {
                    configurations.remove(checkedConfig);
                    configurations.add(config);
                    break;
                }
            }
        }
    }

    protected void writeConfigs(List<AbstractConfiguration<?>> configs, boolean useDefaultValues) {
        File configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), modID + ".json");

        try {
            Writer writer = Files.newBufferedWriter(configFile.toPath());
            (new GsonBuilder().setPrettyPrinting().create()).toJson(getConfigsAsJSON(configs, useDefaultValues), writer);
            writer.close();
        } catch (IOException e) {
            Configs.LOGGER.error("Could not write " + modID + " configuration file.", e);
        }
    }

    private static JsonObject getConfigsAsJSON(List<AbstractConfiguration<?>> configs, boolean useDefaultValues) {
        JsonObject jsonObject = new JsonObject();
        for (AbstractConfiguration<?> config : configs) {
            jsonObject.addProperty(config.getName(), config.valueToJson(useDefaultValues));
        }
        return jsonObject;
    }
}
