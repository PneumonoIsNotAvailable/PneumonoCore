package net.pneumono.pneumonocore.config_api.registry;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.pneumono.pneumonocore.config_api.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.ConfigApi;
import net.pneumono.pneumonocore.config_api.ModConfigurations;

import java.util.*;

public class PackagedConfigs {
    private final Set<AbstractConfiguration<?>> configs;

    public PackagedConfigs() {
        ConfigApi.LOGGER.info("Creating new config sync packet");

        List<AbstractConfiguration<?>> packagedConfigs = new ArrayList<>();
        for (ModConfigurations modConfigs : ConfigApi.getModConfigs()) {
            for (AbstractConfiguration<?> config : modConfigs.configurations) {
                if (!config.isClientSided()) {
                    packagedConfigs.add(config);
                }
            }
        }

        this.configs = Set.copyOf(packagedConfigs);
    }

    public PackagedConfigs(NbtCompound compound) {
        ConfigApi.LOGGER.info("Received config sync packet");
        this.configs = new HashSet<>();

        if (compound != null) {
            for (ModConfigurations modConfigs : ConfigApi.getModConfigs()) {
                for (AbstractConfiguration<?> config : modConfigs.configurations) {
                    if (compound.get(config.getName()) != null) {

                        NbtElement element = compound.get(config.getName());
                        configs.add(config.fromElement(element));

                    }
                }
            }
        }
    }

    public NbtCompound toNbt() {
        NbtCompound compound = new NbtCompound();
        for (AbstractConfiguration<?> config : configs) {
            compound.putString(config.getName(), config.valueToJson(false));
        }
        return compound;
    }

    public void updateServerConfigs() {
        // Makes sure all client configs are ready to go
        for (ModConfigurations modConfigs : ConfigApi.getModConfigs()) {
            for (AbstractConfiguration<?> config : modConfigs.configurations) {
                config.getReloadableLoadedValue(false);
            }
        }

        // Update Server Configs
        packagedConfigLoop:
        for (AbstractConfiguration<?> packedConfig : configs) {
            ModConfigurations modConfigs = ConfigApi.getModConfigs(packedConfig.getModID());
            if (modConfigs != null) {
                for (AbstractConfiguration<?> config : modConfigs.configurations) {

                    if (Objects.equals(config.getName(), packedConfig.getName()) && !config.isClientSided()) {
                        config.setImportedValue(packedConfig);
                        continue packagedConfigLoop;
                    }
                }
            }

            ConfigApi.LOGGER.warn("Received config {}:{} which does not exist!", packedConfig.getModID(), packedConfig.getName());
        }
    }
}
