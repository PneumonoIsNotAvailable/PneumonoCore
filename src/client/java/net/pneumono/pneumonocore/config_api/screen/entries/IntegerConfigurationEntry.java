package net.pneumono.pneumonocore.config_api.screen.entries;

import net.pneumono.pneumonocore.config_api.configurations.IntegerConfiguration;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config_api.screen.widgets.ConfigsListWidget;
import org.jetbrains.annotations.Nullable;

public class IntegerConfigurationEntry extends SimpleTextFieldConfigurationEntry<Integer, IntegerConfiguration> {
    public IntegerConfigurationEntry(ConfigOptionsScreen parent, ConfigsListWidget widget, IntegerConfiguration configuration) {
        super(parent, widget, configuration);
    }

    @Override
    public String stringFromValue(Integer value) {
        return Integer.toString(value);
    }

    @Override
    public @Nullable Integer valueFromString(String string) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
