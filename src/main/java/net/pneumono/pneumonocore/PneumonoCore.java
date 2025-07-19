package net.pneumono.pneumonocore;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.minecraft.util.Identifier;
import net.pneumono.pneumonocore.config_api.ConfigApi;
import net.pneumono.pneumonocore.config_api.ConfigSyncS2CPayload;
import net.pneumono.pneumonocore.config_api.enums.LoadType;
import net.pneumono.pneumonocore.config_api.registry.ServerConfigCommandRegistry;
import net.pneumono.pneumonocore.datagen.ConfigResourceCondition;
import net.pneumono.pneumonocore.test.PneumonoCoreTestConfigs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PneumonoCore implements ModInitializer {
	public static final String MOD_ID = "pneumonocore";
	public static final Logger LOGGER = LoggerFactory.getLogger("PneumonoCore");

	public static final Identifier CONFIG_SYNC_ID = identifier("config_sync");

	public static final ResourceConditionType<ConfigResourceCondition> RESOURCE_CONDITION_CONFIGURATIONS = ResourceConditionType.create(
			identifier("configurations"),
			ConfigResourceCondition.CODEC
	);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing PneumonoCore");

		// Config
		PayloadTypeRegistry.playS2C().register(ConfigSyncS2CPayload.ID, ConfigSyncS2CPayload.CODEC);
		ServerConfigCommandRegistry.registerServerConfigCommand();
		ServerPlayConnectionEvents.JOIN.register((handler, packetSender, server) -> ConfigApi.sendConfigSyncPacket(handler.getPlayer()));
		ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, resourceManager, success) -> {
			ConfigApi.readAllFromFiles(LoadType.RELOAD);
			ConfigApi.sendConfigSyncPacket(PlayerLookup.all(server));
		});
		ResourceConditions.register(RESOURCE_CONDITION_CONFIGURATIONS);
		PneumonoCoreTestConfigs.registerTestConfigs();
	}

	public static Identifier identifier(String path) {
		return Identifier.of(MOD_ID, path);
	}
}