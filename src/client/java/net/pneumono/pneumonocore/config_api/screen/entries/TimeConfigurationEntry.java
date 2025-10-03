package net.pneumono.pneumonocore.config_api.screen.entries;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config_api.screen.widgets.ConfigsListWidget;
import net.pneumono.pneumonocore.config_api.configurations.TimeConfiguration;
import net.pneumono.pneumonocore.config_api.enums.TimeUnit;
import net.pneumono.pneumonocore.util.PneumonoCoreUtil;

public class TimeConfigurationEntry extends TextFieldConfigurationEntry<Long, TimeConfiguration, Long> {
    private final ButtonWidget cycleWidget;
    private TimeUnit units;
    private long amount;

    public TimeConfigurationEntry(ConfigOptionsScreen parent, ConfigsListWidget widget, TimeConfiguration configuration) {
        super(parent, widget, configuration, -25);

        this.amount = getAmount(this.value);
        this.units = TimeUnit.fromValue(this.value);

        this.cycleWidget = addChild(ButtonWidget.builder(Text.literal(""), (button) -> {
            this.units = PneumonoCoreUtil.cycleEnum(this.units);
            setValue(this.amount * this.units.getDivision());
        }).dimensions(0, 0, 20, 20).build());
    }

    @Override
    public Long textGetValue() {
        return this.amount;
    }

    @Override
    public void textSetValue(Long value) {
        this.amount = value;
        this.setValue(value * this.units.getDivision());
    }

    @Override
    public String stringFromValue(Long value) {
        return Long.toString(value);
    }

    @Override
    public Long valueFromString(String string) {
        try {
            return Long.parseLong(string);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private long getAmount(long value) {
        return value / TimeUnit.fromValue(value).getDivision();
    }

    @Override
    public void updateWidgets() {
        super.updateWidgets();

        try {
            this.amount = Long.parseLong(this.textWidget.getText());
        } catch (NumberFormatException ignored) {}

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
    //? if >=1.21.9 {
    public void render(DrawContext context, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        super.render(context, mouseX, mouseY, hovered, tickDelta);
        int x = getX();
        int y = getY();
    //?} else {
    /*public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        super.render(context, index, y, x, entryWidth, entryHeight, mouseX, mouseY, hovered, tickDelta);
    *///?}

        this.cycleWidget.setX(x + getWidgetEndX() - this.cycleWidget.getWidth());
        this.cycleWidget.setY(y);
        this.cycleWidget.render(context, mouseX, mouseY, tickDelta);
    }
}
