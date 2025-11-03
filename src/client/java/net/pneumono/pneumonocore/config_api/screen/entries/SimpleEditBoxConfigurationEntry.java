package net.pneumono.pneumonocore.config_api.screen.entries;

import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config_api.screen.components.ConfigsList;

public abstract class SimpleEditBoxConfigurationEntry<T, C extends AbstractConfiguration<T>> extends EditBoxConfigurationEntry<T, C, T> {
    public SimpleEditBoxConfigurationEntry(ConfigOptionsScreen parent, ConfigsList widget, C configuration) {
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
