package net.pneumono.pneumonocore.config_api.entries;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.pneumono.pneumonocore.config_api.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config_api.ConfigsListWidget;
import net.pneumono.pneumonocore.config_api.configurations.FloatConfiguration;
import net.pneumono.pneumonocore.config_api.widgets.FloatConfigSliderWidget;

import java.util.List;

public class FloatConfigurationEntry extends AbstractConfigurationEntry<Float, FloatConfiguration> {
    private final FloatConfigSliderWidget sliderWidget;

    public FloatConfigurationEntry(ConfigOptionsScreen parent, ConfigsListWidget widget, FloatConfiguration configuration) {
        super(parent, widget, configuration);

        this.sliderWidget = new FloatConfigSliderWidget(
                (slider, configValue) -> setValue(configValue),
                this.value, 0, 0, 110, 20
        );

        this.update();
    }

    @Override
    public void update() {
        this.sliderWidget.setValue(this.value);
    }

    @Override
    public List<? extends Selectable> selectableChildren() {
        return ImmutableList.of(this.sliderWidget);
    }

    @Override
    public List<? extends Element> children() {
        return ImmutableList.of(this.sliderWidget);
    }

    @Override
    public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        renderNameAndInformation(context, x, y, entryHeight, mouseX, mouseY, tickDelta);

        this.sliderWidget.setX(x + OFFSET + 35);
        this.sliderWidget.setY(y);
        this.sliderWidget.render(context, mouseX, mouseY, tickDelta);
    }
}
