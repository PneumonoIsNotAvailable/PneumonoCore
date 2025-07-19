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
import net.pneumono.pneumonocore.config_api.entries.AbstractConfigListWidgetEntry;
import net.pneumono.pneumonocore.config_api.entries.AbstractConfigurationEntry;

public class ClientConfigOptionsScreen extends ConfigOptionsScreen {
    public ClientConfigOptionsScreen(Screen parent, String modID) {
        super(parent, modID);
    }

    @Override
    public <T> void saveValue(String name, T newValue) {
        AbstractConfiguration<?> config = this.configsListWidget.configFile.getConfiguration(name);
        setValue(config, newValue);
    }

    @SuppressWarnings("unchecked")
    private static <T> void setValue(AbstractConfiguration<T> config, Object value) {
        try {
            if (config.getInfo().isClientSided()) {
                ConfigManager.setLoadedValue(config, (T) value);
            }
        } catch (ClassCastException e) {
            ConfigApi.LOGGER.warn("Could not save value '{}' for config '{}'", value, config.getId());
        }
    }

    @Override
    public void save() {
        ConfigFile configFile = this.configsListWidget.configFile;

        JsonObject jsonObject = new JsonObject();
        for (AbstractConfigListWidgetEntry entry : this.configsListWidget.getEntries()) {
            if (entry instanceof AbstractConfigurationEntry<?,?> configEntry) {
                JsonElement jsonElement = encodeJson(configEntry);
                if (jsonElement != null) {
                    jsonObject.add(configEntry.getConfiguration().getInfo().getName(), jsonElement);
                }
            }
        }

        configFile.writeToFile(jsonObject);
    }

    private static <T, C extends AbstractConfiguration<T>> JsonElement encodeJson(AbstractConfigurationEntry<T, C> entry) {
        DataResult<JsonElement> result = entry.getConfiguration().getValueCodec().encodeStart(JsonOps.INSTANCE, entry.getValue());
        if (result.isError()) {
            ConfigApi.LOGGER.error("Could not encode value for config '{}'.", entry.getConfiguration().getId());
            return null;
        }

        return result.getOrThrow(message -> new IllegalStateException("Could not encode value for config '" + entry.getConfiguration().getId() + "'"));
    }

    @Override
    public <T> T getConfigValue(AbstractConfiguration<T> configuration) {
        return ConfigManager.getLoadedValue(configuration);
    }
}
