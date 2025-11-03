package net.pneumono.pneumonocore.config_api.screen.entries;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.util.CommonColors;
import net.pneumono.pneumonocore.PneumonoCore;
import net.pneumono.pneumonocore.config_api.ConfigApi;
import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config_api.screen.components.ConfigsList;

//? if >=1.20.2 {
import net.minecraft.client.gui.components.SpriteIconButton;
//?} else {
/*import net.minecraft.client.gui.components.ImageButton;
*///?}

import java.util.Objects;

public abstract class AbstractConfigurationEntry<T, C extends AbstractConfiguration<T>> extends AbstractConfigListEntry {
    protected final ConfigsList widget;
    protected final C configuration;
    protected final /*? if >=1.20.2 {*/SpriteIconButton/*?} else {*/ /*ImageButton*//*?}*/ resetWidget;

    protected T value;

    public AbstractConfigurationEntry(ConfigOptionsScreen parent, ConfigsList widget, C configuration) {
        super(parent);
        this.widget = widget;
        this.configuration = configuration;
        this.resetWidget = addChild(
                //? if >=1.20.2 {
                SpriteIconButton.builder(
                        Component.translatable("configs_screen.pneumonocore.reset"),
                        button -> this.reset(),
                        true
                )
                .sprite(PneumonoCore.location("icon/reset"), 15, 15)
                .width(20).build()
                //?} else {
                /*new ImageButton(
                        0, 0,
                        20, 20,
                        0, 0,
                        20,
                        PneumonoCore.location("textures/gui/sprites/icon/reset_button.png"),
                        20,
                        40,
                        button -> this.reset()
                )
                *///?}
        );

        this.resetWidget.setTooltip(Tooltip.create(
                Component.translatable("configs_screen.pneumonocore.reset")
        ));

        this.value = this.parent.getConfigValue(this.configuration);
    }

    public C getConfiguration() {
        return configuration;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
        this.widget.updateEntryList();
        this.updateButtons();
    }

    public void reset() {
        setValue(this.configuration.info().getDefaultValue());
    }

    @Override
    public boolean shouldDisplay() {
        AbstractConfiguration<?> configParent = this.configuration.info().getParent();
        if (configParent == null) return true;
        AbstractConfigurationEntry<?, ?> configParentEntry = this.widget.getEntry(configParent.info().getId());
        if (configParentEntry == null) return true;
        return this.configuration.info().isEnabled(configParentEntry.value);
    }

    public int getWidgetStartX() {
        return this.getRowEndXOffset() - 136;
    }

    public int getWidgetEndX() {
        return this.getRowEndXOffset() - this.resetWidget.getWidth() - 6;
    }

    public int getTotalWidgetWidth() {
        return this.getWidgetEndX() - this.getWidgetStartX();
    }

    @Override
    //? if >=1.21.9 {
    public void renderContent(GuiGraphics graphics, int mouseX, int mouseY, boolean hovered, float tickProgress) {
        int x = getX();
        int y = getY();
        int entryHeight = getContentHeight();
    //?} else {
    /*public void render(GuiGraphics graphics, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickProgress) {
    *///?}
        Component configName = Component.translatable(ConfigApi.toTranslationKey(this.configuration));
        Font font = Objects.requireNonNull(this.parent.getMinecraft()).font;
        graphics.drawString(font, configName, x, (y + entryHeight / 2) - 2, CommonColors.WHITE);

        if (mouseX >= x && mouseX <= x + 135 && mouseY >= y && mouseY <= y + entryHeight) {
            //? if >=1.21.6 {
            graphics.setTooltipForNextFrame(
            //?} else {
            /*graphics.renderTooltip(
            *///?}
                    font,

                    font.split(
                            Component.translatable(ConfigApi.toTranslationKey(configuration, "tooltip"))
                                    .append("\n\n")
                                    .append(Component.translatable(configuration.info().isClientSided() ?
                                            "configs_screen.pneumonocore.client" :
                                            "configs_screen.pneumonocore.server"
                                    ).withStyle(ChatFormatting.GRAY))
                                    .append("\n")
                                    .append(Component.translatable(switch (configuration.info().getLoadType()) {
                                        case INSTANT -> "configs_screen.pneumonocore.load_instant";
                                        case RELOAD -> "configs_screen.pneumonocore.load_reload";
                                        case RESTART -> "configs_screen.pneumonocore.load_restart";
                                    }).withStyle(ChatFormatting.GRAY)),
                            250
                    ),

                    mouseX, mouseY
            );
        }

        this.resetWidget.setX(x + getRowEndXOffset() - this.resetWidget.getWidth());
        this.resetWidget.setY(y);
        this.resetWidget.render(graphics, mouseX, mouseY, tickProgress);
    }

    /**
     * @return Whether this value should be saved when the Save button is pressed.
     *
     * <p>Should return false if the value is invalid.
     * (e.g. a text field widget for integers where a non-integer value has been entered)
     */
    public boolean shouldSaveValue() {
        return true;
    }
}
