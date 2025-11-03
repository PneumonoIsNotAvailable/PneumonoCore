package net.pneumono.pneumonocore.config_api.screen.entries;

import net.minecraft.client.gui.GuiGraphics;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config_api.screen.components.IntegerConfigSliderButton;
import net.pneumono.pneumonocore.config_api.screen.components.ConfigsList;
import net.pneumono.pneumonocore.config_api.configurations.BoundedIntegerConfiguration;

public class BoundedIntegerConfigurationEntry extends AbstractConfigurationEntry<Integer, BoundedIntegerConfiguration> {
    private final IntegerConfigSliderButton sliderButton;

    public BoundedIntegerConfigurationEntry(ConfigOptionsScreen parent, ConfigsList widget, BoundedIntegerConfiguration configuration) {
        super(parent, widget, configuration);
        int minValue = configuration.getMinValue();
        int maxValue = configuration.getMaxValue();

        this.sliderButton = addChild(new IntegerConfigSliderButton(
                (slider, configValue) -> setValue(configValue),
                this.value, minValue, maxValue, 0, 0, getTotalWidgetWidth(), 20
        ));
    }

    @Override
    public void updateButtons() {
        this.sliderButton.setValue(this.value);
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

        this.sliderButton.setX(x + getWidgetStartX());
        this.sliderButton.setY(y);
        this.sliderButton.render(graphics, mouseX, mouseY, tickDelta);
    }
}
