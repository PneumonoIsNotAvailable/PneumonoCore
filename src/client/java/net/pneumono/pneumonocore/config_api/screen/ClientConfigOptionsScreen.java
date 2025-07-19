package net.pneumono.pneumonocore.config_api.screen;

import net.minecraft.client.gui.screen.Screen;
import net.pneumono.pneumonocore.config_api.ConfigApi;
import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.configurations.ConfigManager;

public class ClientConfigOptionsScreen extends ConfigOptionsScreen {
    public ClientConfigOptionsScreen(Screen parent, String modID) {
        super(parent, modID);
    }

    @Override
    public <T> void save(String name, T newValue) {
        AbstractConfiguration<?> config = this.configsListWidget.configFile.getConfiguration(name);
        if (setValue(config, newValue)) {
            this.configsListWidget.configFile.writeToFile();
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> boolean setValue(AbstractConfiguration<T> config, Object value) {
        try {
            ConfigManager.setLoadedValue(config, (T)value);
            return true;
        } catch (ClassCastException e) {
            ConfigApi.LOGGER.warn("Could not save value '{}' for config '{}'", value, config.getId());
            return false;
        }
    }

    @Override
    public <T> T getConfigValue(AbstractConfiguration<T> configuration) {
        return ConfigManager.getLoadedValue(configuration);
    }
}
