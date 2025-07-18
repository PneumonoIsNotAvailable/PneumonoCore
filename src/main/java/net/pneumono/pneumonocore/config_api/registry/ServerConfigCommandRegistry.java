package net.pneumono.pneumonocore.config_api.registry;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.pneumono.pneumonocore.config_api.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.ConfigApi;
import net.pneumono.pneumonocore.config_api.ModConfigurations;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class ServerConfigCommandRegistry {
    public static void registerServerConfigCommand() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
            dispatcher.register(literal("serverconfig")
                .requires(source -> source.hasPermissionLevel(4))
                .then(literal("get")
                    .then(argument("modid", StringArgumentType.string())
                        .suggests(new ModIDSuggestionProvider())
                        .then(argument("config", StringArgumentType.string())
                            .suggests(new ConfigSuggestionProvider())
                            .executes(context -> {
                                context.getSource().sendMessage(Text.literal(getConfigValueString(
                                        StringArgumentType.getString(context, "modid"),
                                        StringArgumentType.getString(context, "config")
                                )).formatted(Formatting.AQUA));

                                return 1;
                            })
                        )
                        .executes(context -> {
                            context.getSource().sendMessage(Text.literal("Configs:"));
                            List<String> configs = getAllConfigValueStrings(StringArgumentType.getString(context, "modid"));
                            if (configs.isEmpty()) {
                                context.getSource().sendMessage(Text.literal("   None!"));
                            } else {
                                for (String config : configs) {
                                    context.getSource().sendMessage(Text.literal("   " + config));
                                }
                            }

                            return 1;
                        })
                    )
                )
            )
        );
    }

    public static List<String> getAllConfigValueStrings(String modID) {
        List<String> returnConfigs = new ArrayList<>();
        ModConfigurations modConfigs = ConfigApi.getModConfigs(modID);
        if (modConfigs != null) {
            for (AbstractConfiguration<?> config : modConfigs.configurations) {
                returnConfigs.add(config.getModID() + ":" + config.getName() + " is set to " + config.getReloadableLoadedValue(false).toString());
            }
        }
        return returnConfigs;
    }

    public static String getConfigValueString(String modID, String name) {
        ModConfigurations modConfigs = ConfigApi.getModConfigs(modID);
        if (modConfigs != null) {
            for (AbstractConfiguration<?> config : modConfigs.configurations) {
                if (Objects.equals(config.getName(), name)) {
                    return config.getModID() + ":" + config.getName() + " is set to " + config.getReloadableLoadedValue(false).toString();
                }
            }
        }
        return modID + ":" + name + " does not exist!";
    }

    public static class ModIDSuggestionProvider implements SuggestionProvider<ServerCommandSource> {
        @Override
        public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) {
            for (ModConfigurations modConfigs : ConfigApi.getModConfigs()) {
                if (modConfigs.getModID().toLowerCase().startsWith(builder.getRemainingLowerCase())) {
                    builder.suggest(modConfigs.getModID());
                }
            }

            return builder.buildFuture();
        }
    }

    public static class ConfigSuggestionProvider implements SuggestionProvider<ServerCommandSource> {
        @Override
        public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) {
            ModConfigurations modConfigs = ConfigApi.getModConfigs(StringArgumentType.getString(context, "modid"));
            if (modConfigs != null) {
                for (AbstractConfiguration<?> config : modConfigs.configurations) {
                    if (config.getName().toLowerCase().startsWith(builder.getRemainingLowerCase())) {
                        builder.suggest(config.getName());
                    }
                }
            }

            return builder.buildFuture();
        }
    }
}
