package net.pneumono.pneumonocore.config_api;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.configurations.ConfigManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public final class ClientConfigCommandRegistry {
    public static void registerClientConfigCommand() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
            dispatcher.register(literal("clientconfig")
                .then(literal("get")
                    .then(argument("modid", StringArgumentType.string())
                        .suggests(new ModIdSuggestionProvider())
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

    public static List<String> getAllConfigValueStrings(String modId) {
        List<String> returnConfigs = new ArrayList<>();
        ConfigFile modConfigs = ConfigApi.getConfigFile(modId);
        if (modConfigs != null) {
            for (AbstractConfiguration<?> config : modConfigs.getConfigurations()) {
                String valueString = config.info().isClientSided() ? config.getValue().toString() : ConfigManager.getSavedValue(config).toString();
                returnConfigs.add(config.info().getModId() + ":" + config.info().getName() + " is set to " + valueString);
            }
        }
        return returnConfigs;
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

    public static class ModIdSuggestionProvider implements SuggestionProvider<FabricClientCommandSource> {
        @Override
        public CompletableFuture<Suggestions> getSuggestions(CommandContext<FabricClientCommandSource> context, SuggestionsBuilder builder) {
            for (ConfigFile modConfigs : ConfigApi.getConfigFiles()) {
                if (modConfigs.getModId().toLowerCase().startsWith(builder.getRemainingLowerCase())) {
                    builder.suggest(modConfigs.getModId());
                }
            }

            return builder.buildFuture();
        }
    }

    public static class ConfigSuggestionProvider implements SuggestionProvider<FabricClientCommandSource> {
        @Override
        public CompletableFuture<Suggestions> getSuggestions(CommandContext<FabricClientCommandSource> context, SuggestionsBuilder builder) {
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
