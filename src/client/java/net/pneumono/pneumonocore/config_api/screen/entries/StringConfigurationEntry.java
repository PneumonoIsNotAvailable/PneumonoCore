package net.pneumono.pneumonocore.config_api.screen.entries;

import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config_api.screen.widgets.ConfigsListWidget;
import net.pneumono.pneumonocore.config_api.configurations.StringConfiguration;

public class StringConfigurationEntry extends TextFieldConfigurationEntry<String, StringConfiguration, String> {
    public StringConfigurationEntry(ConfigOptionsScreen parent, ConfigsListWidget widget, StringConfiguration configuration) {
        super(parent, widget, configuration);
    }

    @Override
    public String textGetValue() {
        return this.getValue();
    }

    @Override
    public void textSetValue(String value) {
        this.setValue(value);
    }

    @Override
    public String stringFromValue(String value) {
        return value;
    }

    @Override
    public String valueFromString(String string) {
        return string;
    }
}
