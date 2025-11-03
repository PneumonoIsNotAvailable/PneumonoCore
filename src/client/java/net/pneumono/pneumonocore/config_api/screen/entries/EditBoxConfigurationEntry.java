package net.pneumono.pneumonocore.config_api.screen.entries;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.pneumono.pneumonocore.config_api.ConfigApi;
import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config_api.screen.components.ConfigsList;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public abstract class EditBoxConfigurationEntry<T, C extends AbstractConfiguration<T>, V> extends AbstractConfigurationEntry<T, C> {
    protected final EditBox editBox;

    public EditBoxConfigurationEntry(ConfigOptionsScreen parent, ConfigsList widget, C configuration) {
        this(parent, widget, configuration, 0);
    }

    public EditBoxConfigurationEntry(ConfigOptionsScreen parent, ConfigsList widget, C configuration, int fieldWidthOffset) {
        super(parent, widget, configuration);

        this.editBox = addChild(new EditBox(
                Objects.requireNonNull(parent.getMinecraft()).font,
                0, 0, getTotalWidgetWidth() + fieldWidthOffset, 20, null,
                Component.translatable(ConfigApi.toTranslationKey(this.configuration))
        ));
        this.editBox.setValue(stringFromValue(textGetValue()));
        //? if >=1.21.9 {
        this.editBox.addFormatter(
        //?} else {
        /*this.editBox.setFormatter(
        *///?}
                (string, firstCharIndex) -> FormattedCharSequence.forward(
                        string, isTextValid(this.editBox.getValue()) ? Style.EMPTY : Style.EMPTY.withColor(ChatFormatting.RED)
                )
        );
        this.editBox.setResponder(string -> {
            V value = this.valueFromString(string);
            if (value == null) {
                this.editBox.setTooltip(Tooltip.create(Component.translatable("configs_screen.pneumonocore.invalid")));
            } else {
                this.textSetValue(value);
                this.editBox.setTooltip(null);
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
    public void updateButtons() {
        V value = textGetValue();
        String valueText = stringFromValue(value);
        if (!Objects.equals(this.editBox.getValue(), valueText) && !Objects.equals(valueFromString(this.editBox.getValue()), value)) {
            this.editBox.setValue(valueText);
        }
    }

    @Override
    //? if >=1.21.9 {
    public void renderContent(GuiGraphics graphics, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        super.renderContent(graphics, mouseX, mouseY, hovered, tickDelta);
        int x = getX();
        int y = getY();
    //?} else {
    /*public void render(GuiGraphics graphics, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        super.render(graphics, index, y, x, entryWidth, entryHeight, mouseX, mouseY, hovered, tickDelta);
    *///?}

        this.editBox.setX(x + getWidgetStartX());
        this.editBox.setY(y);
        this.editBox.render(graphics, mouseX, mouseY, tickDelta);
    }

    @Override
    public boolean shouldSaveValue() {
        return isTextValid(this.editBox.getValue());
    }
}
