package net.pneumono.pneumonocore.config;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;

import java.util.*;

public class PackagedConfigs {
    private final Set<AbstractConfiguration<?>> configs;

    public PackagedConfigs() {
        Configs.LOGGER.info("Creating new config sync packet");

        List<AbstractConfiguration<?>> packagedConfigs = new ArrayList<>();
        for (ModConfigurations modConfigs : Configs.CONFIGS.values()) {
            for (AbstractConfiguration<?> config : modConfigs.configurations) {
                if (!config.isClientSide()) {
                    packagedConfigs.add(config);
                }
            }
        }

        this.configs = Set.copyOf(packagedConfigs);
    }

    public PackagedConfigs(NbtCompound compound) {
        Configs.LOGGER.info("Received config sync packet");
        this.configs = new HashSet<>();

        if (compound != null) {
            for (ModConfigurations modConfigs : Configs.CONFIGS.values()) {
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
        for (ModConfigurations modConfigs : Configs.CONFIGS.values()) {
            for (AbstractConfiguration<?> config : modConfigs.configurations) {
                config.getReloadableLoadedValue(false);
            }
        }

        // Update Server Configs
        packagedConfigLoop:
        for (AbstractConfiguration<?> packedConfig : configs) {
            ModConfigurations modConfigs = Configs.CONFIGS.get(packedConfig.modID);
            if (modConfigs != null) {
                for (AbstractConfiguration<?> config : modConfigs.configurations) {

                    if (Objects.equals(config.name, packedConfig.name) && !config.isClientSide()) {
                        config.setImportedValue(packedConfig);
                        continue packagedConfigLoop;
                    }
                }
            }

            Configs.LOGGER.warn("Received config {}:{} which does not exist!", packedConfig.modID, packedConfig.name);
        }
    }
}
