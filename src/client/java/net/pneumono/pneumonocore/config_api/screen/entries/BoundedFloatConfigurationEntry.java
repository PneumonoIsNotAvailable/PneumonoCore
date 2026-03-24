package net.pneumono.pneumonocore.config_api.screen.entries;

import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config_api.screen.components.ConfigsList;
import net.pneumono.pneumonocore.config_api.configurations.BoundedFloatConfiguration;
import net.pneumono.pneumonocore.config_api.screen.components.FloatConfigSliderButton;

//? if >=26.1 {
import net.minecraft.client.gui.GuiGraphicsExtractor;
//?} else {
/*import net.minecraft.client.gui.GuiGraphics;
*///?}

public class BoundedFloatConfigurationEntry extends AbstractConfigurationEntry<Float, BoundedFloatConfiguration> {
    private final FloatConfigSliderButton sliderButton;

    public BoundedFloatConfigurationEntry(ConfigOptionsScreen parent, ConfigsList widget, BoundedFloatConfiguration configuration) {
        super(parent, widget, configuration);

        this.sliderButton = addChild(new FloatConfigSliderButton(
                (slider, configValue) -> setValue(configValue),
                this.value, 0, 0, getTotalWidgetWidth(), 20
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
