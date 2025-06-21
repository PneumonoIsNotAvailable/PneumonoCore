package net.pneumono.pneumonocore.test;

import net.pneumono.pneumonocore.PneumonoCore;
import net.pneumono.pneumonocore.config.*;

/**
 * This only exists for testing, don't touch anything here as it won't be registered!
 */
@SuppressWarnings("unused")
public class PneumonoCoreTestConfigs {
    public static final String MOD_ID = PneumonoCore.MOD_ID;

    public static final BooleanConfiguration BOOLEAN = new BooleanConfiguration(MOD_ID, "test_boolean", ConfigEnv.SERVER, true);
    public static final EnumConfiguration<TestEnum> ENUM = new EnumConfiguration<>(MOD_ID, "test_enum", ConfigEnv.SERVER, TestEnum.VALUE_1);
    public static final FloatConfiguration FLOAT = new FloatConfiguration(MOD_ID, "test_float", ConfigEnv.SERVER, 0.5F);
    public static final IntegerConfiguration INTEGER = new IntegerConfiguration(MOD_ID, "test_integer", ConfigEnv.SERVER, 0, 10, 5);
    public static final StringConfiguration STRING = new StringConfiguration(MOD_ID, "test_string", ConfigEnv.SERVER, "Testing!");
    public static final TimeConfiguration TIME = new TimeConfiguration(MOD_ID, "test_time", ConfigEnv.SERVER, TimeUnit.MINUTES.getDivision() * 22L);
    public static final BooleanConfiguration CLIENT = new BooleanConfiguration(MOD_ID, "test_client", ConfigEnv.CLIENT, true);

    public static void registerTestConfigs() {
        Configs.register(MOD_ID,
                BOOLEAN,
                ENUM,
                FLOAT,
                INTEGER,
                STRING,
                TIME,
                CLIENT
        );
    }

    public enum TestEnum {
        VALUE_1,
        VALUE_2,
        VALUE_3
    }
}
