package net.pneumono.pneumonocore.config_api.screen.entries;

import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config_api.screen.components.IntegerConfigSliderButton;
import net.pneumono.pneumonocore.config_api.screen.components.ConfigsList;
import net.pneumono.pneumonocore.config_api.configurations.BoundedIntegerConfiguration;

//? if >=26.1 {
import net.minecraft.client.gui.GuiGraphicsExtractor;
//?} else {
/*import net.minecraft.client.gui.GuiGraphics;
*///?}

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
    public void displayContent(/*? if >=26.1 {*/GuiGraphicsExtractor/*?} else {*//*GuiGraphics*//*?}*/ graphics, int x, int y, int mouseX, int mouseY, int entryHeight, boolean hovered, float tickDelta) {
        super.displayContent(graphics, x, y, mouseX, mouseY, entryHeight, hovered, tickDelta);

        this.sliderButton.setX(x + getWidgetStartX());
        this.sliderButton.setY(y);
        this.sliderButton./*? if >=26.1 {*/extractRenderState/*?} else {*//*render*//*?}*/(graphics, mouseX, mouseY, tickDelta);
    }
}
