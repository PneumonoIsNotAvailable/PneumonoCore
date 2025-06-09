package net.pneumono.pneumonocore;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.util.Identifier;
import net.pneumono.pneumonocore.config.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PneumonoCore implements ModInitializer {
	public static final String MOD_ID = "pneumonocore";
	public static final Logger LOGGER = LoggerFactory.getLogger("PneumonoCore");

	public static final Identifier CONFIG_SYNC_ID = identifier("config_sync");

	// Removing resource conditions sucks but this version of PneumonoCore doesn't really need them anyway

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing PneumonoCore");

		// Config
		ServerConfigCommandRegistry.registerServerConfigCommand();
		ServerPlayConnectionEvents.JOIN.register(new PlayerJoinEvent());
	}

	public static Identifier identifier(String path) {
		return Identifier.of(MOD_ID, path);
	}
}