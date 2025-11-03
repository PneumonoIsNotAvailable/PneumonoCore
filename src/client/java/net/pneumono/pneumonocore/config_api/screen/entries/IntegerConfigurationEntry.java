package net.pneumono.pneumonocore.config_api.screen.entries;

import net.pneumono.pneumonocore.config_api.configurations.IntegerConfiguration;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config_api.screen.components.ConfigsList;
import org.jetbrains.annotations.Nullable;

public class IntegerConfigurationEntry extends SimpleEditBoxConfigurationEntry<Integer, IntegerConfiguration> {
    public IntegerConfigurationEntry(ConfigOptionsScreen parent, ConfigsList widget, IntegerConfiguration configuration) {
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
