package net.pneumono.pneumonocore.config_api;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.PacketByteBuf;
import net.pneumono.pneumonocore.PneumonoCore;
import net.pneumono.pneumonocore.config_api.configurations.*;
import net.pneumono.pneumonocore.config_api.registry.ConfigApiRegistry;
import net.pneumono.pneumonocore.config_api.screen.entries.*;

//? if >=1.20.4 {
import net.minecraft.nbt.NbtSizeTracker;
//?} else {
/*import net.minecraft.nbt.NbtTagSizeTracker;
*///?}

public final class ClientConfigApiRegistry {
    public static void register() {
        //? if >=1.20.6 {
        /*ClientPlayNetworking.registerGlobalReceiver(ConfigSyncS2CPayload.ID, ClientConfigApiRegistry::receiveSyncPacket);
        *///?} else {
        ClientPlayNetworking.registerGlobalReceiver(ConfigApiRegistry.CONFIG_SYNC_ID, ClientConfigApiRegistry::receiveSyncPacket);
        //?}

        ClientConfigCommandRegistry.registerClientConfigCommand();

        // Scuffed as hell but couldn't find a better way of doing that so whatever
        registerConfigEntryType("boolean", (parent, widget, configuration) ->
                configuration instanceof BooleanConfiguration booleanConfiguration ?
                        new BooleanConfigurationEntry(parent, widget, booleanConfiguration) : null
        );
        registerConfigEntryType("enum", (parent, widget, configuration) ->
                configuration instanceof EnumConfiguration<?> enumConfiguration ?
                        new EnumConfigurationEntry<>(parent, widget, enumConfiguration) : null
        );
        registerConfigEntryType("integer", (parent, widget, configuration) ->
                configuration instanceof IntegerConfiguration integerConfiguration ?
                        new IntegerConfigurationEntry(parent, widget, integerConfiguration) : null
        );
        registerConfigEntryType("float", (parent, widget, configuration) ->
                configuration instanceof FloatConfiguration floatConfiguration ?
                        new FloatConfigurationEntry(parent, widget, floatConfiguration) : null
        );
        registerConfigEntryType("string", (parent, widget, configuration) ->
                configuration instanceof StringConfiguration stringConfiguration ?
                        new StringConfigurationEntry(parent, widget, stringConfiguration) : null
        );
        registerConfigEntryType("time", (parent, widget, configuration) ->
                configuration instanceof TimeConfiguration timeConfiguration ?
                        new TimeConfigurationEntry(parent, widget, timeConfiguration) : null
        );
    }

    private static void registerConfigEntryType(String name, ClientConfigApi.EntryFactory selector) {
        ClientConfigApi.registerConfigEntryType(PneumonoCore.identifier(name), selector);
    }

    //? if >=1.20.6 {
    /*public static void receiveSyncPacket(ConfigSyncS2CPayload payload, ClientPlayNetworking.Context context) {
        ConfigApi.LOGGER.info("Received config sync packet");

        for (ConfigFile configFile : ConfigApi.getConfigFiles()) {
            NbtCompound compound = payload.storedValues().getCompound(configFile.getModId())/^? if >=1.21.8 {^//^.orElse(null)^//^?}^/;
            if (compound == null || compound.isEmpty()) continue;

            for (AbstractConfiguration<?> configuration : configFile.getConfigurations()) {
                NbtElement element = compound.get(configuration.info().getName());
                if (element == null) continue;

                if (!setReceivedEffectiveValue(configuration, element)) {
                    ConfigApi.LOGGER.warn("Config sync packet contains invalid value '{}' for config '{}'. The default config value will be used instead.", element, configuration.info().getId());
                }
            }
        }
    }
    *///?} else {
    public static void receiveSyncPacket(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        ConfigApi.LOGGER.info("Received config sync packet");

        for (ConfigFile configFile : ConfigApi.getConfigFiles()) {
            NbtElement payloadElement = buf.readNbt(/*? if >=1.20.4 {*/NbtSizeTracker.of/*?} else {*//*new NbtTagSizeTracker*//*?}*/(PacketByteBuf.MAX_READ_NBT_SIZE));
            if (!(payloadElement instanceof NbtCompound payload) || payload.isEmpty()) continue;
            NbtCompound compound = payload.getCompound(configFile.getModId());
            if (compound == null || compound.isEmpty()) continue;

            for (AbstractConfiguration<?> configuration : configFile.getConfigurations()) {
                NbtElement element = compound.get(configuration.info().getName());
                if (element == null) continue;

                if (!setReceivedEffectiveValue(configuration, element)) {
                    ConfigApi.LOGGER.warn("Config sync packet contains invalid value '{}' for config '{}'. The default config value will be used instead.", element, configuration.info().getId());
                }
            }
        }
    }
    //?}

    private static  <T> boolean setReceivedEffectiveValue(AbstractConfiguration<T> config, NbtElement nbtElement) {
        DataResult<Pair<T, NbtElement>> result = config.getValueCodec().decode(NbtOps.INSTANCE, nbtElement);
        if (result.result().isEmpty()) {
            return false;
        }

        ConfigManager.setEffectiveValue(config, result.result().orElseThrow().getFirst());
        return true;
    }
}
