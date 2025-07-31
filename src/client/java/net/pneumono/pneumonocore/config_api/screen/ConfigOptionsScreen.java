package net.pneumono.pneumonocore.config_api.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.pneumono.pneumonocore.config_api.screen.widgets.ConfigsListWidget;
import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.screen.entries.AbstractConfigListWidgetEntry;
import net.pneumono.pneumonocore.config_api.screen.entries.AbstractConfigurationEntry;

import java.util.Objects;

public abstract class ConfigOptionsScreen extends Screen {
    public final Screen parent;
    public final String modId;

    public ConfigsListWidget configsListWidget;

    public ConfigOptionsScreen(Screen parent, String modId) {
        super(Text.translatable("configs." + modId + ".screen_title"));
        this.parent = parent;
        this.modId = modId;
    }

    public abstract <T> T getConfigValue(AbstractConfiguration<T> configuration);

    public abstract <T, C extends AbstractConfiguration<T>> void setSavedValue(AbstractConfigurationEntry<T, C> entry);

    public abstract void writeSavedValues();

    @Override
    protected void init() {
        this.configsListWidget = this.addDrawableChild(new ConfigsListWidget(this));
        this.configsListWidget.init();

        this.addDrawableChild(ButtonWidget.builder(Text.translatable("configs_screen.pneumonocore.reset_all"), button -> {
            for (AbstractConfigListWidgetEntry entry : configsListWidget.children()) {
                if (entry instanceof AbstractConfigurationEntry<?, ?> configurationEntry) {
                    configurationEntry.reset();
                }
            }

            this.configsListWidget.updateEntryList();
            this.configsListWidget.updateEntryValues();
        }).dimensions((this.width / 2) - 155, this.height - 29, 150, 20).build());

        this.addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, button -> {
            for (AbstractConfigListWidgetEntry entry : configsListWidget.getEntries()) {
                if (entry instanceof AbstractConfigurationEntry<?, ?> configurationEntry && configurationEntry.shouldSaveValue()) {
                    setSavedValue(configurationEntry);
                }
            }

            this.writeSavedValues();
            Objects.requireNonNull(this.client).setScreen(this.parent);
        }).dimensions((this.width / 2) + 5, this.height - 29, 150, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        this.configsListWidget.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 10, 16777215);
        super.render(context, mouseX, mouseY, delta);
    }

    public MinecraftClient getClient() {
        return this.client;
    }
}
