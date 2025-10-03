package net.pneumono.pneumonocore.config_api.screen.entries;

import net.minecraft.client.gui.DrawContext;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config_api.screen.widgets.ConfigsListWidget;
import net.pneumono.pneumonocore.config_api.configurations.BoundedFloatConfiguration;
import net.pneumono.pneumonocore.config_api.screen.widgets.FloatConfigSliderWidget;

public class BoundedFloatConfigurationEntry extends AbstractConfigurationEntry<Float, BoundedFloatConfiguration> {
    private final FloatConfigSliderWidget sliderWidget;

    public BoundedFloatConfigurationEntry(ConfigOptionsScreen parent, ConfigsListWidget widget, BoundedFloatConfiguration configuration) {
        super(parent, widget, configuration);

        this.sliderWidget = addChild(new FloatConfigSliderWidget(
                (slider, configValue) -> setValue(configValue),
                this.value, 0, 0, getTotalWidgetWidth(), 20
        ));
    }

    @Override
    public void updateWidgets() {
        this.sliderWidget.setValue(this.value);
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

        this.sliderWidget.setX(x + getWidgetStartX());
        this.sliderWidget.setY(y);
        this.sliderWidget.render(context, mouseX, mouseY, tickDelta);
    }
}
