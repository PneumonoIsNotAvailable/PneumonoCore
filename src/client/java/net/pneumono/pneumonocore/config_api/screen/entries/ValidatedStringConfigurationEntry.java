package net.pneumono.pneumonocore.config_api.screen.entries;

import net.pneumono.pneumonocore.config_api.configurations.ValidatedStringConfiguration;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config_api.screen.widgets.ConfigsListWidget;
import org.jetbrains.annotations.Nullable;

public class ValidatedStringConfigurationEntry extends SimpleTextFieldConfigurationEntry<String, ValidatedStringConfiguration> {
    public ValidatedStringConfigurationEntry(ConfigOptionsScreen parent, ConfigsListWidget widget, ValidatedStringConfiguration configuration) {
        super(parent, widget, configuration);
    }

    @Override
    public String stringFromValue(String value) {
        return value;
    }

    @Override
    public @Nullable String valueFromString(String string) {
        return this.configuration.test(string) ? string : null;
    }
}
