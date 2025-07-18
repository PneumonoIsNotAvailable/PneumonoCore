package net.pneumono.pneumonocore.config_api;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.entries.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigsListWidget extends ElementListWidget<AbstractConfigListWidgetEntry> {
    protected final List<AbstractConfiguration<?>> configurations;
    protected final ConfigOptionsScreen parent;

    public ConfigsListWidget(ConfigOptionsScreen parent, MinecraftClient client) {
        super(client, parent.width, parent.layout.getContentHeight(), parent.layout.getHeaderHeight(), 20);
        this.parent = parent;
        ConfigFile configFile = ConfigApi.getConfigFile(this.parent.modID);
        this.configurations = configFile != null ? configFile.getConfigurations() : List.of();

        if (this.configurations.isEmpty()) {
            addEntry(new NoConfigsEntry(this.parent));
            return;
        }

        Map<String, List<AbstractConfiguration<?>>> categories = new HashMap<>();
        for (AbstractConfiguration<?> configuration : this.configurations) {
            categories.computeIfAbsent(configuration.getCategory(), string -> new ArrayList<>()).add(configuration);
        }

        // This puts the misc category at the bottom of the widget
        List<AbstractConfiguration<?>> misc = categories.remove("misc");
        categories.put("misc", misc);

        for (Map.Entry<String, List<AbstractConfiguration<?>>> entry : categories.entrySet()) {
            this.addEntry(new CategoryTitleEntry(
                    "configs.category." + this.parent.modID + "." + entry.getKey(), this.parent
            ));
            for (AbstractConfiguration<?> configuration : entry.getValue()) {
                addConfigurationEntry(configuration);
            }
        }
    }

    @Override
    public int getRowWidth() {
        return 267;
    }

    private void addConfigurationEntry(AbstractConfiguration<?> configuration) {
        EntryBuilder builder = ClientConfigApi.getConfigEntryType(configuration.getConfigTypeId());
        if (builder == null) {
            builder = ErroneousConfigurationEntry::new;
        }
        this.addEntry(builder.build(configuration, parent, this));
    }

    public void update() {
        ConfigApi.readFromFile(this.parent.modID);
        this.updateChildren();
    }

    public void updateChildren() {
        this.children().forEach(AbstractConfigListWidgetEntry::update);
    }

    public interface EntryBuilder {
        AbstractConfigurationEntry<?> build(AbstractConfiguration<?> configuration, ConfigOptionsScreen parent, ConfigsListWidget widget);
    }
}
