package net.pneumono.pneumonocore.config.entries;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;
import net.pneumono.pneumonocore.config.*;
import net.pneumono.pneumonocore.util.PneumonoMathHelper;

import java.util.List;

public class IntegerConfigurationEntry extends AbstractConfigurationEntry {
    private final ConfigSliderWidget sliderWidget;
    private int value;

    public IntegerConfigurationEntry(AbstractConfiguration<?> configuration, ConfigOptionsScreen parent, ConfigsListWidget widget) {
        super(configuration, parent, widget);
        int maxValue;
        int minValue;
        if (configuration instanceof IntegerConfiguration integerConfiguration) {
            this.value = integerConfiguration.getValue();
            minValue = integerConfiguration.getMinValue();
            maxValue = integerConfiguration.getMaxValue();
        } else {
            this.value = 0;
            minValue = 0;
            maxValue = 10;
        }
        this.sliderWidget = new ConfigSliderWidget((slider, configValue) -> {
            this.parent.selectedConfiguration = configuration;
            ConfigOptionsScreen.save(configuration.getModID(), configuration.getName(), configValue);
            this.widget.update();
        }, value / (maxValue - minValue), minValue, maxValue, 0, 0, 110, 20);
        this.sliderWidget.setValue(value);
        this.update();
    }

    @Override
    public void update() {
        int newValue = configuration instanceof IntegerConfiguration integerConfiguration ? integerConfiguration.getValue() : 0;
        this.value = newValue;
        this.sliderWidget.setValue(newValue);
    }

    @Override
    public List<? extends Selectable> selectableChildren() {
        return ImmutableList.of(sliderWidget);
    }

    @Override
    public List<? extends Element> children() {
        return ImmutableList.of(sliderWidget);
    }

    @Override
    public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        renderNameAndInformation(context, x, y, entryHeight, mouseX, mouseY, tickDelta);
        this.sliderWidget.setX(x + 90);
        this.sliderWidget.setY(y);

        sliderWidget.render(context, mouseX, mouseY, tickDelta);
    }

    public static class ConfigSliderWidget extends SliderWidget {
        private final SliderChangeAction action;
        private final int min;
        private final int max;

        public ConfigSliderWidget(SliderChangeAction action, int value, int min, int max, int x, int y, int width, int height) {
            super(x, y, width, height, Text.literal(Integer.toString(PneumonoMathHelper.round(toPercentage(value, max, min)))), toPercentage(value, max, min));
            this.action = action;
            this.min = min;
            this.max = max;
        }

        @Override
        protected void updateMessage() {
            setMessage(Text.literal(Integer.toString(fromPercentage(value, max, min))));
        }

        @Override
        protected void applyValue() {
            action.onChange(this, fromPercentage(value, max, min));
        }

        private static int fromPercentage(double value, int max, int min) {
            return PneumonoMathHelper.round((value * (max - min)) + min);
        }

        private static double toPercentage(int value, int max, int min) {
            return value / (double)(max - min);
        }

        public void setValue(int value) {
            this.value = toPercentage(value, max, min);
            updateMessage();
        }
    }

    public interface SliderChangeAction {
        void onChange(ConfigSliderWidget widget, int value);
    }
}
