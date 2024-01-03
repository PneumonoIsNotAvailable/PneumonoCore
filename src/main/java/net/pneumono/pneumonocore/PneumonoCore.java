package net.pneumono.pneumonocore;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.util.Identifier;
import net.pneumono.pneumonocore.config.Configs;
import net.pneumono.pneumonocore.config.PlayerJoinEvent;
import net.pneumono.pneumonocore.config.ServerConfigCommandRegistry;
import net.pneumono.pneumonocore.datagen.PneumonoDatagenHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PneumonoCore implements ModInitializer {
	public static final String MOD_ID = "pneumonocore";
	public static final Logger LOGGER = LoggerFactory.getLogger("PneumonoCore");

	public static final Identifier CONFIG_SYNC_ID = new Identifier(MOD_ID, "config_sync");

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing PneumonoCore");
		Configs.reload(MOD_ID);

		// Config
		ServerConfigCommandRegistry.registerServerConfigCommand();
		ServerPlayConnectionEvents.JOIN.register(new PlayerJoinEvent());
		PneumonoDatagenHelper.registerResourceConditions();
	}
}