package net.pneumono.pneumonocore.config_api.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.DirectionalLayoutWidget;
import net.minecraft.client.gui.widget.ThreePartsLayoutWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.pneumono.pneumonocore.config_api.screen.widgets.ConfigsListWidget;
import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.screen.entries.AbstractConfigListWidgetEntry;
import net.pneumono.pneumonocore.config_api.screen.entries.AbstractConfigurationEntry;

public abstract class ConfigOptionsScreen extends Screen {
    public final Screen parent;
    public final String modID;
    public final ThreePartsLayoutWidget layout = new ThreePartsLayoutWidget(this);

    public ConfigsListWidget configsListWidget;

    public ConfigOptionsScreen(Screen parent, String modID) {
        super(Text.translatable("configs." + modID + ".screen_title"));
        this.parent = parent;
        this.modID = modID;
    }

    public abstract <T> T getConfigValue(AbstractConfiguration<T> configuration);

    public abstract <T, C extends AbstractConfiguration<T>> void setSavedValue(AbstractConfigurationEntry<T, C> entry);

    public abstract void writeSavedValues();

    @Override
    protected void init() {
        this.initHeader();
        this.initBody();
        this.initFooter();
        this.layout.forEachChild(this::addDrawableChild);
        this.refreshWidgetPositions();
    }

    @Override
    protected void refreshWidgetPositions() {
        this.layout.refreshPositions();
        if (this.configsListWidget != null) {
            this.configsListWidget.position(this.width, this.layout);
        }
    }

    protected void initHeader() {
        this.layout.addHeader(this.title, this.textRenderer);
    }

    protected void initBody() {
        this.configsListWidget = this.layout.addBody(new ConfigsListWidget(this));
    }

    protected void initFooter() {
        DirectionalLayoutWidget directionalLayoutWidget = this.layout.addFooter(DirectionalLayoutWidget.horizontal().spacing(8));

        directionalLayoutWidget.add(ButtonWidget.builder(Text.translatable("configs_screen.pneumonocore.reset_all"), button -> {
            for (AbstractConfigListWidgetEntry entry : configsListWidget.children()) {
                if (entry instanceof AbstractConfigurationEntry<?, ?> configurationEntry) {
                    configurationEntry.reset();
                }
            }

            this.configsListWidget.update();
            this.configsListWidget.getEntries().forEach(AbstractConfigListWidgetEntry::update);
        }).build());

        directionalLayoutWidget.add(ButtonWidget.builder(ScreenTexts.DONE, button -> {
            for (AbstractConfigListWidgetEntry entry : configsListWidget.children()) {
                if (entry instanceof AbstractConfigurationEntry<?, ?> configurationEntry && configurationEntry.shouldSaveValue()) {
                    setSavedValue(configurationEntry);
                }
            }

            this.writeSavedValues();
            this.close();
        }).build());
    }

    public MinecraftClient getClient() {
        return this.client;
    }
}
