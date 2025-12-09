package net.pneumono.pneumonocore.test;

import net.pneumono.pneumonocore.PneumonoCore;
import net.pneumono.pneumonocore.config_api.ConfigApi;
import net.pneumono.pneumonocore.config_api.configurations.*;
import net.pneumono.pneumonocore.config_api.enums.LoadType;
import net.pneumono.pneumonocore.config_api.enums.TimeUnit;
import net.pneumono.pneumonocore.config_api.registry.ServerConfigCommandRegistry;

/**
 * This only exists for testing, don't touch anything here as it won't be registered!
 */
@SuppressWarnings("unused")
public final class PneumonoCoreTestConfigs {
    public static final String MOD_ID = PneumonoCore.MOD_ID;

    public static final String CATEGORY_1 = "test_category_1";
    public static final String CATEGORY_2 = "test_category_2";

    public static final BooleanConfiguration BOOLEAN = register("test_boolean", new BooleanConfiguration(
            true, new ConfigSettings().category(CATEGORY_1)
    ));
    public static final BoundedFloatConfiguration CHILD_1 = register("test_child_1", new BoundedFloatConfiguration(
            0.33F, new ConfigSettings().parent(BOOLEAN, value -> value)
    ));
    public static final BoundedFloatConfiguration CHILD_2 = register("test_child_2", new BoundedFloatConfiguration(
            0.66F, new ConfigSettings().parent(BOOLEAN, value -> value)
    ));
    public static final BoundedFloatConfiguration CHILD_3 = register("test_child_3", new BoundedFloatConfiguration(
            1F, new ConfigSettings().parent(BOOLEAN, value -> value)
    ));
    public static final EnumConfiguration<TestEnum> ENUM = register("test_enum", new EnumConfiguration<>(
            TestEnum.VALUE_1, new ConfigSettings().category(CATEGORY_1)
    ));
    public static final BoundedFloatConfiguration FLOAT = register("test_float", new BoundedFloatConfiguration(
            0.5F, new ConfigSettings().category(CATEGORY_1)
    ));
    public static final BoundedIntegerConfiguration INTEGER = register("test_integer", new BoundedIntegerConfiguration(
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
    public static final BooleanConfiguration INSTANT = register("test_instant", new BooleanConfiguration(
            true, new ConfigSettings().loadType(LoadType.INSTANT)
    ));
    public static final BooleanConfiguration RESTART = register("test_restart", new BooleanConfiguration(
            true, new ConfigSettings().loadType(LoadType.RESTART)
    ));
    public static final BooleanConfiguration CLIENT_INSTANT = register("test_client_instant", new BooleanConfiguration(
            true, new ConfigSettings().clientSide().loadType(LoadType.INSTANT)
    ));
    public static final BooleanConfiguration CLIENT_RESTART = register("test_client_restart", new BooleanConfiguration(
            true, new ConfigSettings().clientSide().loadType(LoadType.RESTART)
    ));
    public static final PerPlayerConfiguration<Boolean> PER_PLAYER = register("test_per_player", new PerPlayerConfiguration<>(
            new BooleanConfiguration(true, new ConfigSettings())
    ));
    public static final BoundedIntegerConfiguration ALIAS = register("test_alias", new BoundedIntegerConfiguration(
            5, 0, 10, new ConfigSettings().aliases("test_alias_old")
    ));
    public static final BoundedIntegerConfiguration BLOAT_1 = register("bloat_1", new BoundedIntegerConfiguration(
            5, 0, 10, new ConfigSettings()
    ));
    public static final BoundedIntegerConfiguration BLOAT_2 = register("bloat_2", new BoundedIntegerConfiguration(
            5, 0, 10, new ConfigSettings()
    ));
    public static final BoundedIntegerConfiguration BLOAT_3 = register("bloat_3", new BoundedIntegerConfiguration(
            5, 0, 10, new ConfigSettings()
    ));
    public static final IntegerConfiguration UNBOUNDED_INT = register("test_unbounded_int", new IntegerConfiguration(
            5, new ConfigSettings()
    ));
    public static final FloatConfiguration UNBOUNDED_FLOAT = register("test_unbounded_float", new FloatConfiguration(
            5F, new ConfigSettings()
    ));
    public static final ValidatedStringConfiguration VALIDATED = register("test_validated", new ValidatedStringConfiguration(
            "validated!", string -> string.equals(string.toLowerCase()), new ConfigSettings()
    ));

    public static <T extends AbstractConfiguration<?>> T register(String name, T config) {
        return ConfigApi.register(PneumonoCore.location(name), config);
    }

    public static void registerTestConfigs() {
        ConfigApi.finishRegistry(MOD_ID);
        ServerConfigCommandRegistry.registerServerConfigCommand(MOD_ID, "servertestconfigs");
    }

    public enum TestEnum {
        VALUE_1,
        VALUE_2,
        VALUE_3
    }
}
