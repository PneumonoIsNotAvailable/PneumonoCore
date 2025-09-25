package net.pneumono.pneumonocore.config_api.screen;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.minecraft.client.gui.screen.Screen;
import net.pneumono.pneumonocore.config_api.ConfigApi;
import net.pneumono.pneumonocore.config_api.ConfigFile;
import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.configurations.ConfigManager;
import net.pneumono.pneumonocore.config_api.screen.entries.AbstractConfigListWidgetEntry;
import net.pneumono.pneumonocore.config_api.screen.entries.AbstractConfigurationEntry;
import net.pneumono.pneumonocore.config_api.enums.LoadType;
import net.pneumono.pneumonocore.util.MultiVersionUtil;

public class ClientConfigOptionsScreen extends ConfigOptionsScreen {
    public ClientConfigOptionsScreen(Screen parent, String modId) {
        super(parent, modId);
    }

    @Override
    public <T> T getConfigValue(AbstractConfiguration<T> configuration) {
        return ConfigManager.getSavedValue(configuration);
    }

    @Override
    public <T, C extends AbstractConfiguration<T>> void setSavedValue(AbstractConfigurationEntry<T, C> entry) {
        AbstractConfiguration<T> config = entry.getConfiguration();
        T value = entry.getValue();

        ConfigManager.setSavedValue(config, value);
        if (config.info().isClientSided() && LoadType.INSTANT.canLoad(config.info().getLoadType())) {
            ConfigManager.setEffectiveValue(config, value);
        }
    }

    @Override
    public void writeSavedValues() {
        ConfigFile configFile = this.configsListWidget.configFile;
        if (configFile == null) return;

        JsonObject jsonObject = new JsonObject();
        for (AbstractConfigListWidgetEntry entry : this.configsListWidget.getEntries()) {
            if (entry instanceof AbstractConfigurationEntry<?,?> configEntry) {
                JsonElement jsonElement = encodeJson(configEntry);
                if (jsonElement != null) {
                    jsonObject.add(configEntry.getConfiguration().info().getName(), jsonElement);
                }
            }
        }

        configFile.writeObjectToFile(jsonObject);
    }

    private static <T, C extends AbstractConfiguration<T>> JsonElement encodeJson(AbstractConfigurationEntry<T, C> entry) {
        DataResult<JsonElement> result = entry.getConfiguration().getValueCodec().encodeStart(JsonOps.INSTANCE, entry.getValue());
        if (MultiVersionUtil.resultIsError(result)) {
            ConfigApi.LOGGER.error("Could not encode value for config '{}'.", entry.getConfiguration().info().getId());
            return null;
        }

        //? if >=1.20.6 {
        return result.getOrThrow(message -> new IllegalStateException("Could not encode value for config '" + entry.getConfiguration().info().getId() + "'"));
        //?} else {
        /*return result.getOrThrow(false, message -> {throw new IllegalStateException("Could not encode value for config '" + entry.getConfiguration().info().getId() + "'");});
        *///?}
    }
}
