package net.pneumono.pneumonocore.config_api.screen.entries;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.pneumono.pneumonocore.config_api.ConfigApi;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config_api.screen.widgets.ConfigsListWidget;
import net.pneumono.pneumonocore.config_api.configurations.TimeConfiguration;
import net.pneumono.pneumonocore.config_api.enums.TimeUnit;
import net.pneumono.pneumonocore.util.PneumonoCoreUtil;

import java.util.Objects;

public class TimeConfigurationEntry extends AbstractConfigurationEntry<Long, TimeConfiguration> {
    private final TextFieldWidget textWidget;
    private long amount;
    private final ButtonWidget cycleWidget;
    private TimeUnit units;

    public TimeConfigurationEntry(ConfigOptionsScreen parent, ConfigsListWidget widget, TimeConfiguration configuration) {
        super(parent, widget, configuration);

        this.amount = getAmount(this.value);
        this.units = TimeUnit.fromValue(this.value);

        this.cycleWidget = addChild(ButtonWidget.builder(Text.literal(""), (button) -> {
            this.units = PneumonoCoreUtil.cycleEnum(this.units);
            setValue(this.amount * this.units.getDivision());
        }).dimensions(0, 0, 20, 20).build());

        this.textWidget = addChild(new TextFieldWidget(
                Objects.requireNonNull(parent.getClient()).textRenderer,
                0, 0,
                getTotalWidgetWidth() - this.cycleWidget.getWidth() - 5, 20,
                null, Text.translatable(ConfigApi.toTranslationKey(this.configuration))
        ));
        this.textWidget.setRenderTextProvider(this::getOrderedText);
        this.textWidget.setText(String.valueOf(this.amount));
        this.textWidget.setChangedListener((text) -> {
            if (isTextValid(text)) {
                setValue(Long.parseLong(text) * this.units.getDivision());
            }
        });
    }

    public OrderedText getOrderedText(String string, int firstCharacterIndex) {
        return OrderedText.styledForwardsVisitedString(
                string, isTextValid(string) ? Style.EMPTY : Style.EMPTY.withFormatting(Formatting.RED)
        );
    }

    public boolean isTextValid(String string) {
        try {
            Long.parseLong(string);
            return true;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }

    private long getAmount(long value) {
        return getAmount(value, TimeUnit.fromValue(value));
    }

    private long getAmount(long value, TimeUnit timeUnit) {
        return value / timeUnit.getDivision();
    }

    @Override
    public void updateWidgets() {
        try {
            this.amount = Long.parseLong(this.textWidget.getText());
        } catch (NumberFormatException ignored) {}

        String amountText = String.valueOf(this.amount);
        if (!Objects.equals(this.textWidget.getText(), amountText)) {
            this.textWidget.setText(amountText);
        }

        String key = "configs_screen.pneumonocore." + this.units.name().toLowerCase();
        this.cycleWidget.setMessage(Text.translatable(key));
        this.cycleWidget.setTooltip(Tooltip.of(Text.translatable(key + ".full")));
    }

    @Override
    public void reset() {
        this.amount = getAmount(configuration.info().getDefaultValue());
        this.units = TimeUnit.fromValue(configuration.info().getDefaultValue());
        super.reset();
    }

    @Override
    public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        super.render(context, index, y, x, entryWidth, entryHeight, mouseX, mouseY, hovered, tickDelta);

        this.textWidget.setX(x + getWidgetStartX());
        this.textWidget.setY(y);
        this.textWidget.render(context, mouseX, mouseY, tickDelta);

        this.cycleWidget.setX(x + getWidgetEndX() - this.cycleWidget.getWidth());
        this.cycleWidget.setY(y);
        this.cycleWidget.render(context, mouseX, mouseY, tickDelta);
    }

    @Override
    public boolean shouldSaveValue() {
        return isTextValid(this.textWidget.getText());
    }
}
