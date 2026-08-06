package net.pneumono.pneumonocore.config_api.screen;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.pneumono.pneumonocore.config_api.ConfigApi;
import net.pneumono.pneumonocore.config_api.ConfigSyncPayload;
import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.configurations.ConfigManager;
import net.pneumono.pneumonocore.config_api.screen.entries.AbstractConfigListEntry;
import net.pneumono.pneumonocore.config_api.screen.entries.AbstractConfigurationEntry;
import net.pneumono.pneumonocore.config_api.enums.LoadType;
import net.pneumono.pneumonocore.util.MultiVersionUtil;

//? if <1.20.5 {
/*import net.pneumono.pneumonocore.config_api.registry.ConfigApiRegistry;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.FriendlyByteBuf;
*///?}

public class ClientConfigOptionsScreen extends ConfigOptionsScreen {
    public ClientConfigOptionsScreen(Screen parent, String modId) {
        super(parent, modId);
    }

    @Override
    public <T> T getConfigValue(AbstractConfiguration<T> configuration) {
        return ConfigManager.getSavedValue(configuration);
    }

    @Override
    public <T, C extends AbstractConfiguration<T>> void setSavedValue(AbstractConfigurationEntry<T, C> entry) {
        AbstractConfiguration<T> config = entry.getConfiguration();
        T value = entry.getValue();

        ConfigManager.setSavedValue(config, value);
        if (config.info().isClientSided() && LoadType.INSTANT.canLoad(config.info().getLoadType())) {
            ConfigManager.setEffectiveValue(config, value);
        }
    }

    @Override
    public void writeSavedValues() {
        JsonObject jsonObject = new JsonObject();
        CompoundTag compoundTag = new CompoundTag();

        for (AbstractConfigListEntry entry : this.configsList.getEntries()) {
            if (entry instanceof AbstractConfigurationEntry<?,?> configEntry) {
                if (configEntry.getConfiguration().info().isClientSided()) {
                    JsonElement jsonElement = encodeJson(configEntry);
                    if (jsonElement != null) {
                        jsonObject.add(configEntry.getConfiguration().info().getName(), jsonElement);
                    }
                } else {
                    Tag tag = encodeNbt(configEntry);
                    if (tag != null) {
                        compoundTag.put(configEntry.getConfiguration().info().getName(), tag);
                    }
                    JsonElement jsonElement = encodeCurrentJson(configEntry);
                    if (jsonElement != null) {
                        jsonObject.add(configEntry.getConfiguration().info().getName(), jsonElement);
                    }
                }
            }
        }

        this.configsList.configFile.writeObjectToFile(jsonObject);

        if (!compoundTag.isEmpty()) {
            CompoundTag finalTag = new CompoundTag();
            finalTag.put(this.configsList.configFile.getModId(), compoundTag);

            //? if >=1.20.5 {
            ClientPlayNetworking.send(new ConfigSyncPayload(finalTag));
            //?} else {
            /*FriendlyByteBuf buf = PacketByteBufs.create();
            buf.writeNbt(finalTag);
            ClientPlayNetworking.send(ConfigApiRegistry.CONFIG_SYNC_ID, buf);
            *///?}
        }
    }

    private static <T, C extends AbstractConfiguration<T>> JsonElement encodeJson(AbstractConfigurationEntry<T, C> entry) {
        DataResult<JsonElement> result = entry.getConfiguration().getValueCodec().encodeStart(JsonOps.INSTANCE, entry.getValue());
        if (MultiVersionUtil.resultIsError(result)) {
            ConfigApi.LOGGER.error("Could not encode json value for config '{}'", entry.getConfiguration().info().getId());
            return null;
        }

        return MultiVersionUtil.resultGetOrThrow(result);
    }

    private static <T, C extends AbstractConfiguration<T>> JsonElement encodeCurrentJson(AbstractConfigurationEntry<T, C> entry) {
        AbstractConfiguration<T> configuration = entry.getConfiguration();
        DataResult<JsonElement> result = configuration.getValueCodec().encodeStart(JsonOps.INSTANCE, ConfigManager.getSavedValue(configuration));
        if (MultiVersionUtil.resultIsError(result)) {
            ConfigApi.LOGGER.error("Could not encode current json value for config '{}'", configuration.info().getId());
            return null;
        }

        return MultiVersionUtil.resultGetOrThrow(result);
    }

    private static <T, C extends AbstractConfiguration<T>> Tag encodeNbt(AbstractConfigurationEntry<T, C> entry) {
        DataResult<Tag> result = entry.getConfiguration().getValueCodec().encodeStart(NbtOps.INSTANCE, entry.getValue());
        if (MultiVersionUtil.resultIsError(result)) {
            ConfigApi.LOGGER.error("Could not encode nbt value for config '{}'.", entry.getConfiguration().info().getId());
            return null;
        }

        return MultiVersionUtil.resultGetOrThrow(result);
    }
}
