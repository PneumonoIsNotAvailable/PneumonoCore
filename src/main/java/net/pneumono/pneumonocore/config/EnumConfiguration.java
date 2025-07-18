package net.pneumono.pneumonocore.config;

import net.pneumono.pneumonocore.util.CodecUtil;

/**
 * @deprecated Use {@link net.pneumono.pneumonocore.config_api.configurations.EnumConfiguration} instead.
 */
@Deprecated
public class EnumConfiguration<T extends Enum<T>> extends AbstractConfiguration<T, net.pneumono.pneumonocore.config_api.configurations.EnumConfiguration<T>> {
    public EnumConfiguration(String modId, String name, ConfigEnv environment, T defaultValue) {
        super(modId, name,
                new net.pneumono.pneumonocore.config_api.configurations.EnumConfiguration<>(
                defaultValue,
                CodecUtil.createEnumCodec(defaultValue.getDeclaringClass().getEnumConstants()),
                environment.toSettings()
        ));
    }
}
