package net.pneumono.pneumonocore.config_api;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.pneumono.pneumonocore.PneumonoCore;
import net.pneumono.pneumonocore.config_api.configurations.*;
import net.pneumono.pneumonocore.config_api.screen.entries.*;
import net.pneumono.pneumonocore.util.MultiVersionUtil;

//? if <1.20.5 {
/*import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.pneumono.pneumonocore.config_api.registry.ConfigApiRegistry;
*///?}

public final class ClientConfigApiRegistry {
    public static void register() {
        //? if >=1.20.5 {
        ClientPlayNetworking.registerGlobalReceiver(ConfigSyncS2CPayload.TYPE, ClientConfigApiRegistry::receiveSyncPacket);
        //?} else {
        /*ClientPlayNetworking.registerGlobalReceiver(ConfigApiRegistry.CONFIG_SYNC_ID, ClientConfigApiRegistry::receiveSyncPacket);
        *///?}

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
        registerConfigEntryType("bounded_integer", (parent, widget, configuration) ->
                configuration instanceof BoundedIntegerConfiguration boundedIntegerConfiguration ?
                        new BoundedIntegerConfigurationEntry(parent, widget, boundedIntegerConfiguration) : null
        );
        registerConfigEntryType("float", (parent, widget, configuration) ->
                configuration instanceof FloatConfiguration floatConfiguration ?
                        new FloatConfigurationEntry(parent, widget, floatConfiguration) : null
        );
        registerConfigEntryType("bounded_float", (parent, widget, configuration) ->
                configuration instanceof BoundedFloatConfiguration boundedFloatConfiguration ?
                        new BoundedFloatConfigurationEntry(parent, widget, boundedFloatConfiguration) : null
        );
        registerConfigEntryType("string", (parent, widget, configuration) ->
                configuration instanceof StringConfiguration stringConfiguration ?
                        new StringConfigurationEntry(parent, widget, stringConfiguration) : null
        );
        registerConfigEntryType("validated_string", (parent, widget, configuration) ->
                configuration instanceof ValidatedStringConfiguration validatedStringConfiguration ?
                        new ValidatedStringConfigurationEntry(parent, widget, validatedStringConfiguration) : null
        );
        registerConfigEntryType("time", (parent, widget, configuration) ->
                configuration instanceof TimeConfiguration timeConfiguration ?
                        new TimeConfigurationEntry(parent, widget, timeConfiguration) : null
        );
    }

    private static void registerConfigEntryType(String name, ClientConfigApi.EntryFactory selector) {
        ClientConfigApi.registerConfigEntryType(PneumonoCore.location(name), selector);
    }

    public static void receiveSyncPacket(
    //? if >=1.20.5 {
            ConfigSyncS2CPayload payload,
            ClientPlayNetworking.Context context
    ) {
        receiveSyncPacket(payload.storedValues());
    }
    //?} else {
    /*      Minecraft client,
            ClientPacketListener handler,
            FriendlyByteBuf buf,
            PacketSender responseSender
    ) {
        receiveSyncPacket(buf.readNbt());
    }
    *///?}

    public static void receiveSyncPacket(CompoundTag nbt) {
        ConfigApi.LOGGER.info("Received config sync packet");

        for (ConfigFile configFile : ConfigApi.getConfigFiles()) {
            CompoundTag compound = MultiVersionUtil.getCompound(nbt, configFile.getModId());
            if (compound == null || compound.isEmpty()) continue;

            for (AbstractConfiguration<?> configuration : configFile.getConfigurations()) {
                Tag element = compound.get(configuration.info().getName());
                if (element == null) continue;

                if (!setReceivedEffectiveValue(configuration, element)) {
                    ConfigApi.LOGGER.warn("Config sync packet contains invalid value '{}' for config '{}'. The default config value will be used instead.", element, configuration.info().getId());
                }
            }
        }
    }

    private static  <T> boolean setReceivedEffectiveValue(AbstractConfiguration<T> config, Tag tag) {
        DataResult<Pair<T, Tag>> result = config.getValueCodec().decode(NbtOps.INSTANCE, tag);
        if (MultiVersionUtil.resultIsError(result)) {
            return false;
        }

        ConfigManager.setEffectiveValue(config, MultiVersionUtil.resultGetOrThrow(result).getFirst());
        return true;
    }
}