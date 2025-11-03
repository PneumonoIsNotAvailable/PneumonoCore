package net.pneumono.pneumonocore.config_api.screen.entries;

import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config_api.screen.components.ConfigsList;
import net.pneumono.pneumonocore.config_api.configurations.StringConfiguration;
import org.jetbrains.annotations.Nullable;

public class StringConfigurationEntry extends SimpleEditBoxConfigurationEntry<String, StringConfiguration> {
    public StringConfigurationEntry(ConfigOptionsScreen parent, ConfigsList widget, StringConfiguration configuration) {
        super(parent, widget, configuration);
    }

    @Override
    public String stringFromValue(String value) {
        return value;
    }

    @Override
    public @Nullable String valueFromString(String string) {
        return string;
    }
}
