package net.pneumono.pneumonocore.config_api;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.util.Identifier;
import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.entries.*;

import java.util.ArrayList;
import java.util.List;

public class ConfigsListWidget extends ElementListWidget<AbstractConfigListWidgetEntry> {
    protected final List<AbstractConfiguration<?>> configurations;
    protected final ConfigCategory[] categories;
    protected final ConfigOptionsScreen parent;

    public ConfigsListWidget(ConfigOptionsScreen parent, MinecraftClient client) {
        super(client, parent.width, parent.layout.getContentHeight(), parent.layout.getHeaderHeight(), 20);
        this.parent = parent;
        ConfigFile configFile = ConfigApi.getConfigFile(this.parent.modID);
        this.configurations = configFile != null ? configFile.getConfigurations() : List.of();
        this.categories = ConfigApi.getCategories(this.parent.modID);

        if (configurations.isEmpty()) {
            addEntry(new NoConfigsEntry(this.parent));
            return;
        }

        List<Identifier> usedIds = new ArrayList<>();
        for (ConfigCategory category : categories) {
            AbstractConfigListWidgetEntry entry = new CategoryTitleEntry(category, parent);
            this.addEntry(entry);

            for (AbstractConfiguration<?> configuration : configurations) {
                for (Identifier id : category.configurations()) {
                    Identifier configId = configuration.getID();
                    if (id.equals(configId) && !usedIds.contains(configId)) {
                        addConfigurationEntry(configuration);
                        usedIds.add(id);
                    }
                }
            }
        }
        boolean addUncategorizedTitle = categories.length > 0;
        for (AbstractConfiguration<?> configuration : configurations) {
            if (!usedIds.contains(configuration.getID())) {

                if (addUncategorizedTitle) {
                    this.addEntry(new CategoryTitleEntry(ConfigCategory.getEmpty(), parent));
                    addUncategorizedTitle = false;
                }
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
