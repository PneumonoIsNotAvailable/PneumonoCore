package net.pneumono.pneumonocore;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.minecraft.util.Identifier;
import net.pneumono.pneumonocore.config.*;
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
		PayloadTypeRegistry.playS2C().register(ConfigPayload.ID, ConfigPayload.CODEC);
		ServerConfigCommandRegistry.registerServerConfigCommand();
		ServerPlayConnectionEvents.JOIN.register(new PlayerJoinEvent());
		ResourceConditions.register(RESOURCE_CONDITION_CONFIGURATIONS);
		PneumonoCoreTestConfigs.registerTestConfigs();
	}

	public static Identifier identifier(String path) {
		return Identifier.of(MOD_ID, path);
	}
}