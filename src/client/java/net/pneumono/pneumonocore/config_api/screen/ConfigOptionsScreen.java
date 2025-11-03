package net.pneumono.pneumonocore.config_api.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.CommonColors;
import net.pneumono.pneumonocore.config_api.screen.components.ConfigsList;
import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.screen.entries.AbstractConfigListEntry;
import net.pneumono.pneumonocore.config_api.screen.entries.AbstractConfigurationEntry;

import java.util.Objects;

public abstract class ConfigOptionsScreen extends Screen {
    public final Screen lastScreen;
    public final String modId;

    public ConfigsList configsList;

    public ConfigOptionsScreen(Screen lastScreen, String modId) {
        super(Component.translatable("configs." + modId + ".screen_title"));
        this.lastScreen = lastScreen;
        this.modId = modId;
    }

    public abstract <T> T getConfigValue(AbstractConfiguration<T> configuration);

    public abstract <T, C extends AbstractConfiguration<T>> void setSavedValue(AbstractConfigurationEntry<T, C> entry);

    public abstract void writeSavedValues();

    @Override
    protected void init() {
        this.initBody();
        this.initFooter();
        this.configsList.setScrollAmount(0);
    }

    protected void initBody() {
        this.configsList = this.addRenderableWidget(new ConfigsList(this));
        this.configsList.init();
    }

    protected void initFooter() {
        this.addRenderableWidget(Button.builder(Component.translatable("configs_screen.pneumonocore.reset_all"), button -> {
            for (AbstractConfigListEntry entry : configsList.children()) {
                if (entry instanceof AbstractConfigurationEntry<?, ?> configurationEntry) {
                    configurationEntry.reset();
                }
            }

            this.configsList.updateEntryList();
            this.configsList.updateEntryValues();
        }).bounds((this.width / 2) - 154, this.height - 28, 150, 20).build());

        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> {
            for (AbstractConfigListEntry entry : configsList.getEntries()) {
                if (entry instanceof AbstractConfigurationEntry<?, ?> configurationEntry && configurationEntry.shouldSaveValue()) {
                    setSavedValue(configurationEntry);
                }
            }

            this.writeSavedValues();
            Objects.requireNonNull(this.minecraft).setScreen(this.lastScreen);
        }).bounds((this.width / 2) + 4, this.height - 28, 150, 20).build());
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        //? if <1.20.2 {
        /*this.renderBackground(graphics);
        *///?}
        super.render(graphics, mouseX, mouseY, delta);
        graphics.drawCenteredString(this.font, this.title, this.width / 2, 10, CommonColors.WHITE);
    }

    public int getContentHeight() {
        return this.height - this.getHeaderHeight() - this.getFooterHeight();
    }

    public int getHeaderHeight() {
        return 36;
    }

    public int getFooterHeight() {
        return 36;
    }

    public Minecraft getMinecraft() {
        return this.minecraft;
    }
}
