package net.pneumono.pneumonocore.config;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class ClientConfigCommandRegistry {
    public static void registerClientConfigCommand() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
            dispatcher.register(literal("clientconfig")
                .then(literal("get")
                    .then(argument("modid", StringArgumentType.string())
                        .suggests(new ModIDSuggestionProvider())
                        .then(argument("config", StringArgumentType.string())
                            .suggests(new ConfigSuggestionProvider())
                            .executes(context -> {
                                context.getSource().sendFeedback(Text.literal(getConfigValueString(
                                        StringArgumentType.getString(context, "modid"),
                                        StringArgumentType.getString(context, "config")
                                )).formatted(Formatting.AQUA));

                                return 1;
                            })
                        )
                        .executes(context -> {
                            context.getSource().sendFeedback(Text.literal("Configs:"));
                            List<String> configs = getAllConfigValueStrings(StringArgumentType.getString(context, "modid"));
                            if (configs.isEmpty()) {
                                context.getSource().sendFeedback(Text.literal("   None!"));
                            } else {
                                for (String config : configs) {
                                    context.getSource().sendFeedback(Text.literal("   " + config));
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
        for (ModConfigurations modConfigs : Configs.CONFIGS) {
            if (Objects.equals(modConfigs.modID, modID)) {
                for (AbstractConfiguration<?> config : modConfigs.configurations) {
                    returnConfigs.add(config.getModID() + ":" + config.getName() + " is set to " + config.getReloadableLoadedValue(false).toString());
                }
            }
        }
        return returnConfigs;
    }

    public static String getConfigValueString(String modID, String name) {
        for (ModConfigurations modConfigs : Configs.CONFIGS) {
            if (Objects.equals(modConfigs.modID, modID)) {
                for (AbstractConfiguration<?> config : modConfigs.configurations) {
                    if (Objects.equals(config.getName(), name)) {
                        return config.getModID() + ":" + config.getName() + " is set to " + config.getReloadableLoadedValue(false).toString();
                    }
                }
            }
        }
        return modID + ":" + name + " does not exist!";
    }

    public static class ModIDSuggestionProvider implements SuggestionProvider<FabricClientCommandSource> {
        @Override
        public CompletableFuture<Suggestions> getSuggestions(CommandContext<FabricClientCommandSource> context, SuggestionsBuilder builder) {
            for (ModConfigurations modConfigs : Configs.CONFIGS) {
                if (modConfigs.modID.toLowerCase().startsWith(builder.getRemainingLowerCase())) {
                    builder.suggest(modConfigs.modID);
                }
            }

            return builder.buildFuture();
        }
    }

    public static class ConfigSuggestionProvider implements SuggestionProvider<FabricClientCommandSource> {
        @Override
        public CompletableFuture<Suggestions> getSuggestions(CommandContext<FabricClientCommandSource> context, SuggestionsBuilder builder) {
            for (ModConfigurations modConfigs : Configs.CONFIGS) {
                if (Objects.equals(modConfigs.modID, StringArgumentType.getString(context, "modid"))) {
                    for (AbstractConfiguration<?> config : modConfigs.configurations) {
                        if (config.getName().toLowerCase().startsWith(builder.getRemainingLowerCase())) {
                            builder.suggest(config.getName());
                        }
                    }
                }
            }

            return builder.buildFuture();
        }
    }
}
