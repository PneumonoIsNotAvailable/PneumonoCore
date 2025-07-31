package net.pneumono.pneumonocore.config_api.screen.entries;

import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config_api.screen.widgets.ConfigsListWidget;

public abstract class SimpleTextFieldConfigurationEntry<T, C extends AbstractConfiguration<T>> extends TextFieldConfigurationEntry<T, C, T> {
    public SimpleTextFieldConfigurationEntry(ConfigOptionsScreen parent, ConfigsListWidget widget, C configuration) {
        super(parent, widget, configuration);
    }

    @Override
    public T textGetValue() {
        return this.getValue();
    }

    @Override
    public void textSetValue(T value) {
        this.setValue(value);
    }
}
