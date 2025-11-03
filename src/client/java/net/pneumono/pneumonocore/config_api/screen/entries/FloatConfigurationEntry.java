package net.pneumono.pneumonocore.config_api.screen.entries;

import net.pneumono.pneumonocore.config_api.configurations.FloatConfiguration;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config_api.screen.components.ConfigsList;
import org.jetbrains.annotations.Nullable;

public class FloatConfigurationEntry extends SimpleEditBoxConfigurationEntry<Float, FloatConfiguration> {
    public FloatConfigurationEntry(ConfigOptionsScreen parent, ConfigsList widget, FloatConfiguration configuration) {
        super(parent, widget, configuration);
    }

    @Override
    public String stringFromValue(Float value) {
        return Float.toString(value);
    }

    @Override
    public @Nullable Float valueFromString(String string) {
        try {
            return Float.parseFloat(string);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
