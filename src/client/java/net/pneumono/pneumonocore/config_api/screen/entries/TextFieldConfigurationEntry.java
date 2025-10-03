package net.pneumono.pneumonocore.config_api.screen.entries;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.pneumono.pneumonocore.config_api.ConfigApi;
import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config_api.screen.widgets.ConfigsListWidget;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public abstract class TextFieldConfigurationEntry<T, C extends AbstractConfiguration<T>, V> extends AbstractConfigurationEntry<T, C> {
    protected final TextFieldWidget textWidget;

    public TextFieldConfigurationEntry(ConfigOptionsScreen parent, ConfigsListWidget widget, C configuration) {
        this(parent, widget, configuration, 0);
    }

    public TextFieldConfigurationEntry(ConfigOptionsScreen parent, ConfigsListWidget widget, C configuration, int fieldWidthOffset) {
        super(parent, widget, configuration);

        this.textWidget = addChild(new TextFieldWidget(
                Objects.requireNonNull(parent.getClient()).textRenderer,
                0, 0, getTotalWidgetWidth() + fieldWidthOffset, 20, null,
                Text.translatable(ConfigApi.toTranslationKey(this.configuration))
        ));
        this.textWidget.setText(stringFromValue(textGetValue()));
        //? if >=1.21.9 {
        this.textWidget.addFormatter(
        //?} else {
        /*this.textWidget.setRenderTextProvider(
        *///?}
                (string, firstCharIndex) -> OrderedText.styledForwardsVisitedString(
                        string, isTextValid(this.textWidget.getText()) ? Style.EMPTY : Style.EMPTY.withFormatting(Formatting.RED)
                )
        );
        this.textWidget.setChangedListener(string -> {
            V value = this.valueFromString(string);
            if (value == null) {
                this.textWidget.setTooltip(Tooltip.of(Text.translatable("configs_screen.pneumonocore.invalid")));
            } else {
                this.textSetValue(value);
                this.textWidget.setTooltip(null);
            }
        });
    }

    public boolean isTextValid(String string) {
        return valueFromString(string) != null;
    }

    public abstract V textGetValue();

    public abstract void textSetValue(V value);

    public abstract String stringFromValue(V value);

    /**
     * Return {@code null} if the string is invalid
     */
    public abstract @Nullable V valueFromString(String string);

    @Override
    public void updateWidgets() {
        V value = textGetValue();
        String valueText = stringFromValue(value);
        if (!Objects.equals(this.textWidget.getText(), valueText) && !Objects.equals(valueFromString(this.textWidget.getText()), value)) {
            this.textWidget.setText(valueText);
        }
    }

    @Override
    //? if >=1.21.9 {
    public void render(DrawContext context, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        super.render(context, mouseX, mouseY, hovered, tickDelta);
        int x = getX();
        int y = getY();
    //?} else {
    /*public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        super.render(context, index, y, x, entryWidth, entryHeight, mouseX, mouseY, hovered, tickDelta);
    *///?}

        this.textWidget.setX(x + getWidgetStartX());
        this.textWidget.setY(y);
        this.textWidget.render(context, mouseX, mouseY, tickDelta);
    }

    @Override
    public boolean shouldSaveValue() {
        return isTextValid(this.textWidget.getText());
    }
}
