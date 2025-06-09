package net.pneumono.pneumonocore.config;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.util.Identifier;
import net.pneumono.pneumonocore.PneumonoCoreClient;
import net.pneumono.pneumonocore.config.entries.*;

import java.util.ArrayList;
import java.util.List;

public class ConfigsListWidget extends ElementListWidget<AbstractConfigListWidgetEntry> {
    protected final List<AbstractConfiguration<?>> configurations;
    protected final ConfigCategory[] categories;
    protected final ConfigOptionsScreen parent;

    public ConfigsListWidget(ConfigOptionsScreen parent, MinecraftClient client) {
        super(client, parent.width, parent.height, 20, parent.height - 32, 20);
        this.parent = parent;
        ModConfigurations modConfigurations = Configs.CONFIGS.get(this.parent.modID);
        this.configurations = modConfigurations != null ? modConfigurations.configurations : List.of();
        this.categories = Configs.getCategories(this.parent.modID);

        if (configurations.isEmpty()) {
            addEntry(new NoConfigsEntry(this.parent));
            return;
        }

        List<Identifier> usedIds = new ArrayList<>();
        if (categories.length > 0) {
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
            this.addEntry(new CategoryTitleEntry(ConfigCategory.getEmpty(), parent));
        }
        for (AbstractConfiguration<?> configuration : configurations) {
            if (!usedIds.contains(configuration.getID())) {
                addConfigurationEntry(configuration);
            }
        }
    }

    private void addConfigurationEntry(AbstractConfiguration<?> configuration) {
        EntryBuilder builder = PneumonoCoreClient.CONFIG_SCREEN_ENTRY_TYPES.get(configuration.getClassID());
        if (builder == null) {
            builder = ErroneousConfigurationEntry::new;
        }
        this.addEntry(builder.build(configuration, parent, this));
    }

    public void update() {
        Configs.reload(this.parent.modID);
        this.updateChildren();
    }

    public void updateChildren() {
        this.children().forEach(AbstractConfigListWidgetEntry::update);
    }

    public interface EntryBuilder {
        AbstractConfigurationEntry build(AbstractConfiguration<?> configuration, ConfigOptionsScreen parent, ConfigsListWidget widget);
    }
}
