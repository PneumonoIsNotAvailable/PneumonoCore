package net.pneumono.pneumonocore.config_api.screen.entries;

import net.minecraft.client.gui.GuiGraphics;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config_api.screen.components.ConfigsList;
import net.pneumono.pneumonocore.config_api.configurations.BoundedFloatConfiguration;
import net.pneumono.pneumonocore.config_api.screen.components.FloatConfigSliderButton;

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
