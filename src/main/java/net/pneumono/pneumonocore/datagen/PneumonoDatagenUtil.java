package net.pneumono.pneumonocore.datagen;

import net.pneumono.pneumonocore.config.AbstractConfiguration;

@SuppressWarnings("unused")
public class PneumonoDatagenUtil {
    public static String configScreenTranslationKey(String modID) {
        return "configs." + modID + ".screen_title";
    }

    public static String enumValueConfigTranslationKey(AbstractConfiguration<?> configuration, Enum<?> value) {
        return configuration.getTranslationKey() + "." + value.name().toLowerCase();
    }
}
