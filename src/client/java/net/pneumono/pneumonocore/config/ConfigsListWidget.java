package net.pneumono.pneumonocore.config;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.pneumono.pneumonocore.PneumonoCoreClient;
import net.pneumono.pneumonocore.config.entries.AbstractConfigurationEntry;
import net.pneumono.pneumonocore.config.entries.ErroneousConfigurationEntry;

import java.util.List;

public class ConfigsListWidget extends ElementListWidget<AbstractConfigurationEntry> {
    protected final List<AbstractConfiguration<?>> configurations;
    protected final ConfigOptionsScreen parent;

    public ConfigsListWidget(ConfigOptionsScreen parent, MinecraftClient client) {
        super(client, parent.width, parent.layout.getContentHeight(), parent.layout.getHeaderHeight(), 20);
        this.parent = parent;
        this.configurations = Configs.CONFIGS.get(this.parent.modID).configurations;

        for(AbstractConfiguration<?> configuration : configurations) {
            EntryBuilder builder = PneumonoCoreClient.CONFIG_SCREEN_ENTRY_TYPES.get(configuration.getClassID());
            if (builder == null) {
                builder = ErroneousConfigurationEntry::new;
            }
            this.addEntry(builder.build(configuration, parent, this));
        }
    }

    public void update() {
        Configs.reload(this.parent.modID);
        this.updateChildren();
    }

    public void updateChildren() {
        this.children().forEach(AbstractConfigurationEntry::update);
    }

    public interface EntryBuilder {
        AbstractConfigurationEntry build(AbstractConfiguration<?> configuration, ConfigOptionsScreen parent, ConfigsListWidget widget);
    }
}
