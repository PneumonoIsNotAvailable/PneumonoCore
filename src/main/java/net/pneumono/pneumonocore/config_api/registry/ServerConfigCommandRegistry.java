package net.pneumono.pneumonocore.config_api.registry;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.ConfigApi;
import net.pneumono.pneumonocore.config_api.ConfigFile;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

//? if >=1.21.11 {
import net.minecraft.commands.Commands;
//?}

@SuppressWarnings("unused")
public class ServerConfigCommandRegistry {
    public static void registerServerConfigCommand(String modId, String commandName) {
        ConfigSuggestionProvider<CommandSourceStack> configSuggestionProvider =
                new ConfigSuggestionProvider<>(modId);

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
                dispatcher.register(literal(commandName)
                        .requires(/*? if >=1.21.11 {*/Commands.hasPermission(Commands.LEVEL_GAMEMASTERS)/*?} else {*//*source -> source.hasPermission()*//*?}*/)
                        .then(literal("get")
                                .then(argument("config", StringArgumentType.string())
                                        .suggests(configSuggestionProvider)
                                        .executes(context -> {
                                            context.getSource().sendSystemMessage(Component.literal(getConfigValueString(
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
                return config.info().getModId() + ":" + config.info().getName() + " is set to " + config.getValue().toString();
            }
        }
        return modId + ":" + name + " does not exist!";
    }
}
