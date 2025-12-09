package net.pneumono.pneumonocore.config_api.registry;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.pneumono.pneumonocore.config_api.ConfigApi;
import net.pneumono.pneumonocore.config_api.ConfigFile;
import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;

import java.util.concurrent.CompletableFuture;

public class ConfigSuggestionProvider<T> implements SuggestionProvider<T> {
    private final String modId;

    public ConfigSuggestionProvider(String modId) {
        this.modId = modId;
    }

    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<T> context, SuggestionsBuilder builder) {
        ConfigFile modConfigs = ConfigApi.getConfigFile(modId);
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
