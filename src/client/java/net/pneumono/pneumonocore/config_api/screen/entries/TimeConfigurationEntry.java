package net.pneumono.pneumonocore.config_api.screen.entries;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config_api.screen.components.ConfigsList;
import net.pneumono.pneumonocore.config_api.configurations.TimeConfiguration;
import net.pneumono.pneumonocore.config_api.enums.TimeUnit;
import net.pneumono.pneumonocore.util.PneumonoCoreUtil;

//? if >=26.1 {
import net.minecraft.client.gui.GuiGraphicsExtractor;
//?} else {
/*import net.minecraft.client.gui.GuiGraphics;
 *///?}

public class TimeConfigurationEntry extends EditBoxConfigurationEntry<Long, TimeConfiguration, Long> {
    private final Button cycleButton;
    private TimeUnit units;
    private long amount;

    public TimeConfigurationEntry(ConfigOptionsScreen parent, ConfigsList widget, TimeConfiguration configuration) {
        super(parent, widget, configuration, -25);

        this.amount = getAmount(this.value);
        this.units = TimeUnit.fromValue(this.value);

        this.cycleButton = addChild(Button.builder(Component.literal(""), (button) -> {
            this.units = PneumonoCoreUtil.cycleEnum(this.units);
            setValue(this.amount * this.units.getDivision());
        }).bounds(0, 0, 20, 20).build());
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
    public void updateButtons() {
        super.updateButtons();

        try {
            this.amount = Long.parseLong(this.editBox.getValue());
        } catch (NumberFormatException ignored) {}

        String key = "configs_screen.pneumonocore." + this.units.name().toLowerCase();
        this.cycleButton.setMessage(Component.translatable(key));
        this.cycleButton.setTooltip(Tooltip.create(Component.translatable(key + ".full")));
    }

    @Override
    public void reset() {
        this.amount = getAmount(configuration.info().getDefaultValue());
        this.units = TimeUnit.fromValue(configuration.info().getDefaultValue());
        super.reset();
    }

    @Override
    public void displayContent(/*? if >=26.1 {*/GuiGraphicsExtractor/*?} else {*//*GuiGraphics*//*?}*/ graphics, int x, int y, int mouseX, int mouseY, int entryHeight, boolean hovered, float tickDelta) {
        super.displayContent(graphics, x, y, mouseX, mouseY, entryHeight, hovered, tickDelta);

        this.cycleButton.setX(x + getWidgetStartX());
        this.cycleButton.setY(y);
        this.cycleButton./*? if >=26.1 {*/extractRenderState/*?} else {*//*render*//*?}*/(graphics, mouseX, mouseY, tickDelta);
    }
}
