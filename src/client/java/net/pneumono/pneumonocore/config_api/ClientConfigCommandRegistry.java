package net.pneumono.pneumonocore.config_api;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.configurations.ConfigManager;
import net.pneumono.pneumonocore.config_api.registry.ConfigSuggestionProvider;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

@SuppressWarnings("unused")
public final class ClientConfigCommandRegistry {
    public static void registerClientConfigCommand(String modId, String commandName) {
        ConfigSuggestionProvider<FabricClientCommandSource> configSuggestionProvider =
                new ConfigSuggestionProvider<>(modId);

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
                dispatcher.register(literal(commandName)
                        .then(literal("get")
                                .then(argument("config", StringArgumentType.string())
                                        .suggests(configSuggestionProvider)
                                        .executes(context -> {
                                            context.getSource().sendFeedback(Component.literal(getConfigValueString(
                                                    modId, StringArgumentType.getString(context, "config")
                                            )).withStyle(ChatFormatting.AQUA));

                                            return 1;
                                        })
                                )
                        )
                )
        );
    }

    public static String getConfigValueString(String modId, String name) {
        ConfigFile modConfigs = ConfigApi.getConfigFile(modId);
        if (modConfigs != null) {
            AbstractConfiguration<?> config = modConfigs.getConfiguration(name);
            if (config != null) {
                String valueString = config.info().isClientSided() ? config.getValue().toString() : ConfigManager.getSavedValue(config).toString();
                return config.info().getModId() + ":" + config.info().getName() + " is set to " + valueString;
            }
        }
        return modId + ":" + name + " does not exist!";
    }
}
