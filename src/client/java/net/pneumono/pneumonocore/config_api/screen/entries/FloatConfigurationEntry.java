package net.pneumono.pneumonocore.config_api.screen.entries;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config_api.screen.widgets.ConfigsListWidget;
import net.pneumono.pneumonocore.config_api.configurations.FloatConfiguration;
import net.pneumono.pneumonocore.config_api.screen.widgets.FloatConfigSliderWidget;

import java.util.List;

public class FloatConfigurationEntry extends AbstractConfigurationEntry<Float, FloatConfiguration> {
    private final FloatConfigSliderWidget sliderWidget;

    public FloatConfigurationEntry(ConfigOptionsScreen parent, ConfigsListWidget widget, FloatConfiguration configuration) {
        super(parent, widget, configuration);

        this.sliderWidget = new FloatConfigSliderWidget(
                (slider, configValue) -> setValue(configValue),
                this.value, 0, 0, 110, 20
        );
    }

    @Override
    public void updateWidgets() {
        this.sliderWidget.setValue(this.value);
    }

    @Override
    public List<? extends ClickableWidget> getChildren() {
        return ImmutableList.of(this.sliderWidget);
    }

    @Override
    public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        super.render(context, index, y, x, entryWidth, entryHeight, mouseX, mouseY, hovered, tickDelta);

        this.sliderWidget.setX(x + OFFSET + 35);
        this.sliderWidget.setY(y);
        this.sliderWidget.render(context, mouseX, mouseY, tickDelta);
    }
}
