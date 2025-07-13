package net.pneumono.pneumonocore;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.minecraft.util.Identifier;
import net.pneumono.pneumonocore.datagen.ConfigResourceCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PneumonoCore implements ModInitializer {
	public static final String MOD_ID = "pneumonocore";
	public static final Logger LOGGER = LoggerFactory.getLogger("PneumonoCore");

	public static final ResourceConditionType<ConfigResourceCondition> RESOURCE_CONDITION_CONFIGURATIONS = ResourceConditionType.create(
			identifier("configurations"),
			ConfigResourceCondition.CODEC
	);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing PneumonoCore");

		// Config
		ResourceConditions.register(RESOURCE_CONDITION_CONFIGURATIONS);
	}

	public static Identifier identifier(String path) {
		return Identifier.of(MOD_ID, path);
	}
}