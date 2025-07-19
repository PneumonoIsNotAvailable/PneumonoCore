package net.pneumono.pneumonocore.config_api;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.configurations.ConfigManager;
import net.pneumono.pneumonocore.config_api.entries.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigsListWidget extends ElementListWidget<AbstractConfigListWidgetEntry> {
    protected final ConfigOptionsScreen parent;
    protected final ConfigFile configFile;
    private final Map<String, List<AbstractConfiguration<?>>> categorizedConfigs;
    private final List<AbstractConfigListWidgetEntry> entries;

    public ConfigsListWidget(ConfigOptionsScreen parent, MinecraftClient client) {
        super(client, parent.width, parent.layout.getContentHeight(), parent.layout.getHeaderHeight(), 20);
        this.parent = parent;
        this.configFile = ConfigApi.getConfigFile(this.parent.modID);

        this.categorizedConfigs = new HashMap<>();
        List<AbstractConfiguration<?>> configurations = this.configFile.getConfigurations();
        for (AbstractConfiguration<?> configuration : configurations) {
            this.categorizedConfigs.computeIfAbsent(configuration.getCategory(), string -> new ArrayList<>()).add(configuration);
        }
        this.categorizedConfigs.put("misc", this.categorizedConfigs.remove("misc"));

        this.entries = initEntryList();
        updateEntryList();
    }

    public List<AbstractConfigListWidgetEntry> initEntryList() {
        List<AbstractConfigListWidgetEntry> newEntries = new ArrayList<>();

        if (this.categorizedConfigs.isEmpty()) {
            newEntries.add(new NoConfigsEntry(this.parent));
            return newEntries;
        }

        for (Map.Entry<String, List<AbstractConfiguration<?>>> categorizedConfig : this.categorizedConfigs.entrySet()) {
            if (this.categorizedConfigs.size() > 1) {
                newEntries.add(new CategoryTitleEntry(
                        "configs.category." + this.configFile.getModID() + "." + categorizedConfig.getKey(), this.parent
                ));
            }

            for (AbstractConfiguration<?> configuration : categorizedConfig.getValue()) {

                AbstractConfigurationEntry<?, ?> entry = ClientConfigApi
                        .getConfigEntryType(configuration.getConfigTypeId())
                        .build(this.parent, this, configuration);
                if (entry == null) {
                    entry = ClientConfigApi.createErroneousEntry(parent, this, configuration);
                }
                newEntries.add(entry);
            }
        }

        return newEntries;
    }

    public void update() {
        this.children().forEach(AbstractConfigListWidgetEntry::update);
        updateEntryList();
    }

    public void updateEntryList() {
        this.replaceEntries(this.entries.stream().filter(AbstractConfigListWidgetEntry::shouldDisplay).toList());
    }

    public <T> void save(String name, T newValue) {
        AbstractConfiguration<?> config = this.configFile.getConfiguration(name);
        if (setValue(config, newValue)) {
            this.configFile.writeToFile();
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> boolean setValue(AbstractConfiguration<T> config, Object value) {
        try {
            ConfigManager.setLoadedValue(config, (T)value);
            return true;
        } catch (ClassCastException e) {
            ConfigApi.LOGGER.warn("Could not save value '{}' for config '{}'", value, config.getId());
            return false;
        }
    }

    @Override
    public int getRowWidth() {
        return 267;
    }
}
