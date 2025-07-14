package net.pneumono.pneumonocore.test;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringIdentifiable;
import net.pneumono.pneumonocore.PneumonoCore;
import net.pneumono.pneumonocore.config.Configs;
import net.pneumono.pneumonocore.config.configuration.*;

/**
 * This only exists for testing, don't touch anything here as it won't be registered!
 */
@SuppressWarnings("unused")
public class PneumonoCoreTestConfigs {
    public static final String MOD_ID = PneumonoCore.MOD_ID;

    public static final BooleanConfiguration DEFAULT_TRUE = register("default_true", new BooleanConfiguration(
            true, new AbstractConfiguration.Settings()
    ));
    public static final BooleanConfiguration DEFAULT_FALSE = register("default_false", new BooleanConfiguration(
            false, new AbstractConfiguration.Settings()
    ));
    public static final IntegerConfiguration ANY_INT = register("any_int", new IntegerConfiguration(
            7, new AbstractConfiguration.Settings()
    ));
    public static final BoundedIntegerConfiguration ONE_TO_TEN = register("one_to_ten", new BoundedIntegerConfiguration(
            5, 0, 10, new AbstractConfiguration.Settings()
    ));
    public static final EnumConfiguration<TestEnum> ENUM = register("enum", new EnumConfiguration<>(
            TestEnum.VALUE_2, TestEnum.CODEC, new AbstractConfiguration.Settings()
    ));
    public static final FloatConfiguration ANY_FLOAT = register("any_float", new FloatConfiguration(
            3.14159F, new AbstractConfiguration.Settings()
    ));
    public static final BoundedFloatConfiguration ZERO_TO_ONE = register("zero_to_one", new BoundedFloatConfiguration(
            0.5F, 0, 1, new AbstractConfiguration.Settings()
    ));
    public static final StringConfiguration STRING = register("string", new StringConfiguration(
            "This is a string!", new AbstractConfiguration.Settings()
    ));
    public static final ValidatedStringConfiguration LOWERCASE_STRING = register("lowercase_string", new ValidatedStringConfiguration(
            "lowercase string", string -> string.toLowerCase().equals(string), new AbstractConfiguration.Settings()
    ));

    private static <T, C extends AbstractConfiguration<T>> C register(String name, C config) {
        return Configs.register(PneumonoCore.identifier(name), config);
    }

    public static void registerTestConfigs() {
        Configs.initializeConfigFile(MOD_ID);
    }

    public enum TestEnum implements StringIdentifiable {
        VALUE_1("value_1"),
        VALUE_2("value_2"),
        VALUE_3("value_3");

        public static final Codec<TestEnum> CODEC = StringIdentifiable.createCodec(TestEnum::values);

        private final String name;

        TestEnum(String name) {
            this.name = name;
        }

        @Override
        public String asString() {
            return this.name;
        }
    }
}
