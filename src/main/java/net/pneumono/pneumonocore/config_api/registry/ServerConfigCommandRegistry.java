package net.pneumono.pneumonocore.config_api.registry;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.ConfigApi;
import net.pneumono.pneumonocore.config_api.ConfigFile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class ServerConfigCommandRegistry {
    public static void registerServerConfigCommand() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
            dispatcher.register(literal("serverconfig")
                .requires(source -> source.hasPermission(4))
                .then(literal("get")
                    .then(argument("modid", StringArgumentType.string())
                        .suggests(new ModIdSuggestionProvider())
                        .then(argument("config", StringArgumentType.string())
                            .suggests(new ConfigSuggestionProvider())
                            .executes(context -> {
                                context.getSource().sendSystemMessage(Component.literal(getConfigValueString(
                                        StringArgumentType.getString(context, "modid"),
                                        StringArgumentType.getString(context, "config")
                                )).withStyle(ChatFormatting.AQUA));

                                return 1;
                            })
                        )
                        .executes(context -> {
                            context.getSource().sendSystemMessage(Component.literal("Configs:"));
                            List<String> configs = getAllConfigValueStrings(StringArgumentType.getString(context, "modid"));
                            if (configs.isEmpty()) {
                                context.getSource().sendSystemMessage(Component.literal("   None!"));
                            } else {
                                for (String config : configs) {
                                    context.getSource().sendSystemMessage(Component.literal("   " + config));
                                }
                            }

                            return 1;
                        })
                    )
                )
            )
        );
    }

    public static List<String> getAllConfigValueStrings(String modId) {
        List<String> returnConfigs = new ArrayList<>();
        ConfigFile modConfigs = ConfigApi.getConfigFile(modId);
        if (modConfigs != null) {
            for (AbstractConfiguration<?> config : modConfigs.getConfigurations()) {
                returnConfigs.add(config.info().getModId() + ":" + config.info().getName() + " is set to " + config.getValue().toString());
            }
        }
        return returnConfigs;
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

    public static class ModIdSuggestionProvider implements SuggestionProvider<CommandSourceStack> {
        @Override
        public CompletableFuture<Suggestions> getSuggestions(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
            for (ConfigFile modConfigs : ConfigApi.getConfigFiles()) {
                if (modConfigs.getModId().toLowerCase().startsWith(builder.getRemainingLowerCase())) {
                    builder.suggest(modConfigs.getModId());
                }
            }

            return builder.buildFuture();
        }
    }

    public static class ConfigSuggestionProvider implements SuggestionProvider<CommandSourceStack> {
        @Override
        public CompletableFuture<Suggestions> getSuggestions(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
            ConfigFile modConfigs = ConfigApi.getConfigFile(StringArgumentType.getString(context, "modid"));
            if (modConfigs != null) {
                for (AbstractConfiguration<?> config : modConfigs.getConfigurations()) {
                    if (config.info().getName().toLowerCase().startsWith(builder.getRemainingLowerCase())) {
                        builder.suggest(config.info().getName());
                    }
                }
            }

            return builder.buildFuture();
        }
    }
}
