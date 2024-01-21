package net.pneumono.pneumonocore.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.*;

public class PackagedConfigs {
    private final Set<AbstractConfiguration<?>> configs;

    public PackagedConfigs() {
        Configs.LOGGER.info("Creating new config sync packet");

        List<AbstractConfiguration<?>> packagedConfigs = new ArrayList<>();
        for (ModConfigurations modConfigs : Configs.CONFIGS.values()) {
            for (AbstractConfiguration<?> config : modConfigs.configurations) {
                if (!config.isClientSide()) {
                    packagedConfigs.add(config);
                }
            }
        }

        this.configs = Set.copyOf(packagedConfigs);
    }

    public PackagedConfigs(String json) {
        Configs.LOGGER.info("Received config sync packet");
        this.configs = new HashSet<>();

        JsonObject jsonObject = (JsonObject) JsonParser.parseString(json);
        if (jsonObject != null) {
            for (ModConfigurations modConfigs : Configs.CONFIGS.values()) {
                for (AbstractConfiguration<?> config : modConfigs.configurations) {
                    if (jsonObject.get(config.getName()) != null) {

                        JsonElement element = jsonObject.get(config.getName());
                        configs.add(config.fromElement(element));

                    }
                }
            }
        }
    }

    public String toJsonString() {
        JsonObject jsonObject = new JsonObject();
        for (AbstractConfiguration<?> config : configs) {
            jsonObject.addProperty(config.getName(), config.valueToJson(false));
        }
        return jsonObject.toString();
    }

    public void updateServerConfigs() {
        // Makes sure all client configs are ready to go
        for (ModConfigurations modConfigs : Configs.CONFIGS.values()) {
            for (AbstractConfiguration<?> config : modConfigs.configurations) {
                config.getReloadableLoadedValue(false);
            }
        }

        // Update Server Configs
        packagedConfigLoop:
        for (AbstractConfiguration<?> packedConfig : configs) {
            ModConfigurations modConfigs = Configs.CONFIGS.get(packedConfig.modID);
            if (modConfigs != null) {
                for (AbstractConfiguration<?> config : modConfigs.configurations) {

                    if (Objects.equals(config.name, packedConfig.name) && !config.isClientSide()) {
                        config.setImportedValue(packedConfig);
                        continue packagedConfigLoop;
                    }
                }
            }

            Configs.LOGGER.warn("Received config " + packedConfig.modID + ":" + packedConfig.name + " which does not exist!");
        }
    }
}
