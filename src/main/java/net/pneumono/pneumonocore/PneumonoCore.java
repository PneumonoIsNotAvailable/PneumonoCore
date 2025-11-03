package net.pneumono.pneumonocore;

import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;
import net.pneumono.pneumonocore.config_api.registry.ConfigApiRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PneumonoCore implements ModInitializer {
	public static final String MOD_ID = "pneumonocore";
	public static final Logger LOGGER = LoggerFactory.getLogger("PneumonoCore");

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing PneumonoCore");

		ConfigApiRegistry.register();
	}

	public static ResourceLocation location(String path) {
		return ResourceLocation.tryBuild(MOD_ID, path);
	}
}
