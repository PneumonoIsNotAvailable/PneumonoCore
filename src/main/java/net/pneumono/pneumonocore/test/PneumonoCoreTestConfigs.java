package net.pneumono.pneumonocore.test;

import net.pneumono.pneumonocore.PneumonoCore;
import net.pneumono.pneumonocore.config_api.ConfigApi;
import net.pneumono.pneumonocore.config_api.configurations.*;
import net.pneumono.pneumonocore.config_api.enums.TimeUnit;

/**
 * This only exists for testing, don't touch anything here as it won't be registered!
 */
@SuppressWarnings("unused")
public class PneumonoCoreTestConfigs {
    public static final String MOD_ID = PneumonoCore.MOD_ID;

    public static final String CATEGORY_1 = "test_category_1";
    public static final String CATEGORY_2 = "test_category_2";

    public static final BooleanConfiguration BOOLEAN = register("test_boolean", new BooleanConfiguration(
            true, new ConfigSettings().category(CATEGORY_1)
    ));
    public static final EnumConfiguration<TestEnum> ENUM = register("test_enum", new EnumConfiguration<>(
            TestEnum.VALUE_1, new ConfigSettings().category(CATEGORY_1)
    ));
    public static final FloatConfiguration FLOAT = register("test_float", new FloatConfiguration(
            0.5F, new ConfigSettings().category(CATEGORY_1)
    ));
    public static final IntegerConfiguration INTEGER = register("test_integer", new IntegerConfiguration(
            5, 0, 10, new ConfigSettings().category(CATEGORY_2)
    ));
    public static final StringConfiguration STRING = register("test_string", new StringConfiguration(
            "Testing!", new ConfigSettings().category(CATEGORY_2)
    ));
    public static final TimeConfiguration TIME = register("test_time", new TimeConfiguration(
            TimeUnit.MINUTES.getDivision() * 22L, new ConfigSettings()
    ));
    public static final BooleanConfiguration CLIENT = register("test_client", new BooleanConfiguration(
            true, new ConfigSettings().clientSide()
    ));
    public static final PerPlayerConfiguration<Boolean> PER_PLAYER = register("test_per_player", new PerPlayerConfiguration<>(
            new BooleanConfiguration(true, new ConfigSettings())
    ));

    public static <T extends AbstractConfiguration<?>> T register(String name, T config) {
        return ConfigApi.register(PneumonoCore.identifier(name), config);
    }

    public static void registerTestConfigs() {
        ConfigApi.finishRegistry(MOD_ID);
    }

    public enum TestEnum {
        VALUE_1,
        VALUE_2,
        VALUE_3
    }
}
