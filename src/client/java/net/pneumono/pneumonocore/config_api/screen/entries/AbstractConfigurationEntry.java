package net.pneumono.pneumonocore.config_api.screen.entries;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Formatting;
import net.pneumono.pneumonocore.PneumonoCore;
import net.pneumono.pneumonocore.config_api.ConfigApi;
import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config_api.screen.widgets.ConfigsListWidget;

//? if >=1.20.2 {
import net.minecraft.client.gui.widget.TextIconButtonWidget;
//?} else {
/*import net.minecraft.client.gui.widget.TexturedButtonWidget;
*///?}

import java.util.Objects;

public abstract class AbstractConfigurationEntry<T, C extends AbstractConfiguration<T>> extends AbstractConfigListWidgetEntry {
    protected final ConfigsListWidget widget;
    protected final C configuration;
    protected final /*? if >=1.20.2 {*/TextIconButtonWidget/*?} else {*/ /*TexturedButtonWidget*//*?}*/ resetWidget;

    protected T value;

    public AbstractConfigurationEntry(ConfigOptionsScreen parent, ConfigsListWidget widget, C configuration) {
        super(parent);
        this.widget = widget;
        this.configuration = configuration;
        this.resetWidget = addChild(
                //? if >=1.20.2 {
                TextIconButtonWidget.builder(
                        Text.translatable("configs_screen.pneumonocore.reset"),
                        button -> this.reset(),
                        true
                )
                .texture(PneumonoCore.identifier("icon/reset"), 15, 15)
                .width(20).build()
                //?} else {
                /*new TexturedButtonWidget(
                        0, 0,
                        20, 20,
                        0, 0,
                        20,
                        PneumonoCore.identifier("textures/gui/sprites/icon/reset_button.png"),
                        20,
                        40,
                        button -> this.reset()
                )
                *///?}
        );

        this.resetWidget.setTooltip(Tooltip.of(
                Text.translatable("configs_screen.pneumonocore.reset")
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
        this.updateWidgets();
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
    public void render(DrawContext context, int mouseX, int mouseY, boolean hovered, float tickProgress) {
        int x = getX();
        int y = getY();
        int entryHeight = getContentHeight();
    //?} else {
    /*public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickProgress) {
    *///?}
        Text configName = Text.translatable(ConfigApi.toTranslationKey(this.configuration));
        TextRenderer textRenderer = Objects.requireNonNull(this.parent.getClient()).textRenderer;
        context.drawTextWithShadow(textRenderer, configName, x, (y + entryHeight / 2) - 2, Colors.WHITE);

        if (mouseX >= x && mouseX <= x + 135 && mouseY >= y && mouseY <= y + entryHeight) {
            context.drawOrderedTooltip(
                    textRenderer,

                    textRenderer.wrapLines(
                            Text.translatable(ConfigApi.toTranslationKey(configuration, "tooltip"))
                                    .append("\n\n")
                                    .append(Text.translatable(configuration.info().isClientSided() ?
                                            "configs_screen.pneumonocore.client" :
                                            "configs_screen.pneumonocore.server"
                                    ).formatted(Formatting.GRAY))
                                    .append("\n")
                                    .append(Text.translatable(switch (configuration.info().getLoadType()) {
                                        case INSTANT -> "configs_screen.pneumonocore.load_instant";
                                        case RELOAD -> "configs_screen.pneumonocore.load_reload";
                                        case RESTART -> "configs_screen.pneumonocore.load_restart";
                                    }).formatted(Formatting.GRAY)),
                            250
                    ),

                    mouseX, mouseY
            );
        }

        this.resetWidget.setX(x + getRowEndXOffset() - this.resetWidget.getWidth());
        this.resetWidget.setY(y);
        this.resetWidget.render(context, mouseX, mouseY, tickProgress);
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
