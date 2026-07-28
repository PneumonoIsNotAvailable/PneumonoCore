package net.pneumono.pneumonocore.config_api.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.permissions.Permissions;
import net.minecraft.util.CommonColors;
import net.pneumono.pneumonocore.PneumonoCore;
import net.pneumono.pneumonocore.config_api.screen.components.ConfigsList;
import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.screen.entries.AbstractConfigListEntry;
import net.pneumono.pneumonocore.config_api.screen.entries.AbstractConfigurationEntry;

import java.util.Objects;

//? if >=26.1 {
import net.minecraft.client.gui.GuiGraphicsExtractor;
//?} else {
/*import net.minecraft.client.gui.GuiGraphics;
*///?}

//? if <1.21
//import net.minecraft.Util;

//? if >=1.20.2 {
import net.minecraft.client.gui.components.SpriteIconButton;
//?} else {
/*import net.minecraft.client.gui.components.ImageButton;
 *///?}

public abstract class ConfigOptionsScreen extends Screen {
    public static final String KOFI_LINK = "https://ko-fi.com/pneumono";

    public final Screen lastScreen;
    public final String modId;
    private final boolean canShowServerConfig;
    private boolean viewingServer = false;

    public ConfigsList configsList;
    public /*? if >=1.20.2 {*/SpriteIconButton/*?} else {*/ /*ImageButton*//*?}*/ kofiButton;

    public ConfigOptionsScreen(Screen lastScreen, String modId) {
        super(Component.translatable("configs." + modId + ".screen_title"));
        this.lastScreen = lastScreen;
        this.modId = modId;
        this.canShowServerConfig = canShowServerConfig(this.minecraft);
    }

    private static boolean canShowServerConfig(Minecraft minecraft) {
        return minecraft.getConnection() == null || minecraft.hasSingleplayerServer() || (minecraft.player != null && minecraft.player.permissions().hasPermission(Permissions.COMMANDS_GAMEMASTER));
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

        if (this.configsList.hasServerConfigs() && this.configsList.hasClientConfigs()) {
            Button serverButton = this.addRenderableWidget(Button.builder(Component.translatable("configs_screen.pneumonocore.server_button"), button -> {
                this.viewingServer = true;
                this.configsList.updateEntryList();
            }).bounds((this.width / 2) + 4, 25, 150, 20).build());

            if (!this.canShowServerConfig()) {
                serverButton.setTooltip(Tooltip.create(Component.translatable("configs_screen.pneumonocore.server_button.tooltip")));
                serverButton.active = false;
            }

            Button clientButton = this.addRenderableWidget(Button.builder(Component.translatable("configs_screen.pneumonocore.client_button"), button -> {
                this.viewingServer = false;
                this.configsList.updateEntryList();
            }).bounds((this.width / 2) - 154, 25, 150, 20).build());

            if (!this.canShowClientConfig()) {
                clientButton.active = false;
            }
        }

        if (!this.configsList.hasClientConfigs()) {
            this.viewingServer = true;
            this.configsList.updateEntryList();
        }
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
            Objects.requireNonNull(this.minecraft)/*? if >=26.2 {*/.gui/*?}*/.setScreen(this.lastScreen);
        }).bounds((this.width / 2) + 4, this.height - 28, 150, 20).build());

        //? if >=1.21 {
        Runnable openConfirmLinkScreen = () -> ConfirmLinkScreen.confirmLinkNow(
                this, KOFI_LINK, false
        );
        //?} else {
        /*Runnable openConfirmLinkScreen = () -> {
            Minecraft minecraft = Minecraft.getInstance();
            minecraft.setScreen(new ConfirmLinkScreen((bl) -> {
                if (bl) {
                    Util.getPlatform().openUri(KOFI_LINK);
                }

                minecraft.setScreen(this);
            }, KOFI_LINK, false));
        };
        *///?}

        this.kofiButton = this.addRenderableWidget(
                //? if >=1.20.2 {
                SpriteIconButton.builder(
                        Component.translatable("configs_screen.pneumonocore.kofi"),
                        button -> openConfirmLinkScreen.run(),
                        true
                ).sprite(PneumonoCore.location("icon/kofi"), 15, 15).width(20).build()
                //?} else {
                /*new ImageButton(
                        0, 0,
                        20, 20,
                        0, 0,
                        20,
                        PneumonoCore.location("textures/gui/sprites/icon/kofi_button.png"),
                        20,
                        40,
                        button -> openConfirmLinkScreen.run()
                )
                *///?}
        );
        this.kofiButton.setPosition((this.width / 2) + 162, this.height - 28);
        this.kofiButton.setTooltip(Tooltip.create(Component.translatable("configs_screen.pneumonocore.kofi")));
    }

    @Override
    public void /*? if >=26.1 {*/extractRenderState/*?} else {*//*render*//*?}*/(/*? if >=26.1 {*/GuiGraphicsExtractor/*?} else {*//*GuiGraphics*//*?}*/ graphics, int mouseX, int mouseY, float delta) {
        //? if <1.20.2 {
        /*this.renderBackground(graphics);
        *///?}
        super./*? if >=26.1 {*/extractRenderState/*?} else {*//*render*//*?}*/(graphics, mouseX, mouseY, delta);
        graphics./*? if >=26.1 {*/centeredText/*?} else {*//*drawCenteredString*//*?}*/(this.font, this.title, this.width / 2, 10, CommonColors.WHITE);
    }

    public boolean canShowServerConfig() {
        return this.canShowServerConfig;
    }

    public boolean canShowClientConfig() {
        return true;
    }

    public boolean isViewingServer() {
        return viewingServer;
    }

    public int getContentHeight() {
        return this.height - this.getHeaderHeight() - this.getFooterHeight();
    }

    public int getHeaderHeight() {
        return 50;
    }

    public int getFooterHeight() {
        return 36;
    }

    public Minecraft getMinecraft() {
        return this.minecraft;
    }
}
