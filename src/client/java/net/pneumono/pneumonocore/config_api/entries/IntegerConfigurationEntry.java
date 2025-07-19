package net.pneumono.pneumonocore.config_api.entries;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config_api.widgets.IntegerConfigSliderWidget;
import net.pneumono.pneumonocore.config_api.ConfigsListWidget;
import net.pneumono.pneumonocore.config_api.configurations.IntegerConfiguration;

import java.util.List;

public class IntegerConfigurationEntry extends AbstractConfigurationEntry<Integer, IntegerConfiguration> {
    private final IntegerConfigSliderWidget sliderWidget;

    public IntegerConfigurationEntry(ConfigOptionsScreen parent, ConfigsListWidget widget, IntegerConfiguration configuration) {
        super(parent, widget, configuration);
        int minValue = configuration.getMinValue();
        int maxValue = configuration.getMaxValue();

        this.sliderWidget = new IntegerConfigSliderWidget(
                (slider, configValue) -> setValue(configValue),
                this.value, minValue, maxValue, 0, 0, 110, 20
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
