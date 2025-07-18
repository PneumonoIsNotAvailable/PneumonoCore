package net.pneumono.pneumonocore.config_api.entries;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config_api.ConfigsListWidget;
import net.pneumono.pneumonocore.config_api.configurations.IntegerConfiguration;

import java.util.List;

public class IntegerConfigurationEntry extends AbstractConfigurationEntry<IntegerConfiguration> {
    private final ConfigSliderWidget sliderWidget;
    private int value;

    public IntegerConfigurationEntry(AbstractConfiguration<?> abstractConfiguration, ConfigOptionsScreen parent, ConfigsListWidget widget) {
        super((IntegerConfiguration) abstractConfiguration, parent, widget);
        this.value = configuration.getValue();
        int minValue = configuration.getMinValue();
        int maxValue = configuration.getMaxValue();

        this.sliderWidget = new ConfigSliderWidget((slider, configValue) -> {
            this.parent.selectedConfiguration = configuration;
            ConfigOptionsScreen.save(configuration.getModID(), configuration.getName(), configValue);
            this.widget.update();
        }, value, minValue, maxValue, 0, 0, 110, 20);
        this.sliderWidget.setValue(value);
        this.update();
    }

    @Override
    public void update() {
        int newValue = configuration.getValue();
        this.value = newValue;
        this.sliderWidget.setValue(newValue);
    }

    @Override
    public void reset() {
        ConfigOptionsScreen.save(configuration.getModID(), configuration.getName(), configuration.getDefaultValue());
        update();
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
        this.sliderWidget.setX(x + OFFSET + 35);
        this.sliderWidget.setY(y);

        sliderWidget.render(context, mouseX, mouseY, tickDelta);
    }

    public static class ConfigSliderWidget extends SliderWidget {
        private final SliderChangeAction action;
        private final int min;
        private final int max;

        public ConfigSliderWidget(SliderChangeAction action, int value, int min, int max, int x, int y, int width, int height) {
            super(x, y, width, height, Text.literal(Integer.toString((int)Math.round(toPercentage(value, min, max)))), toPercentage(value, min, max));
            this.action = action;
            this.min = min;
            this.max = max;
        }

        @Override
        protected void updateMessage() {
            setMessage(Text.literal(Integer.toString(fromPercentage(value, min, max))));
        }

        private static int fromPercentage(double value, int min, int max) {
            return (int)Math.round((value * (max - min)) + min);
        }

        private static double toPercentage(int value, int min, int max) {
            return (value - min) / (double)(max - min);
        }

        @Override
        protected void applyValue() {
            action.onChange(this, fromPercentage(value, min, max));
        }

        public void setValue(int value) {
            this.value = toPercentage(MathHelper.clamp(value, min, max), min, max);
            updateMessage();
        }
    }

    public interface SliderChangeAction {
        void onChange(ConfigSliderWidget widget, int value);
    }
}
