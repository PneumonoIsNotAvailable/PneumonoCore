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

public class FloatConfigurationEntry extends AbstractConfigurationEntry {
    private final ConfigSliderWidget sliderWidget;
    private float value;

    public FloatConfigurationEntry(AbstractConfiguration<?> configuration, ConfigOptionsScreen parent, ConfigsListWidget widget) {
        super(configuration, parent, widget);
        this.value = configuration instanceof FloatConfiguration floatConfiguration ? floatConfiguration.getValue() : 0;
        this.sliderWidget = new ConfigSliderWidget((slider, configValue) -> {
            this.parent.selectedConfiguration = configuration;
            ConfigOptionsScreen.save(configuration.getModID(), configuration.getName(), configValue);
            this.widget.update();
        }, value, 0, 0, 110, 20);
        this.sliderWidget.setValue(value);
        this.update();
    }

    @Override
    public void update() {
        float newValue = configuration instanceof FloatConfiguration floatConfiguration ? floatConfiguration.getValue() : 0;
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
        this.sliderWidget.setX(x + 112);
        this.sliderWidget.setY(y);

        sliderWidget.render(context, mouseX, mouseY, tickDelta);
    }

    public static class ConfigSliderWidget extends SliderWidget {
        private final SliderChangeAction action;

        public ConfigSliderWidget(SliderChangeAction action, float value, int x, int y, int width, int height) {
            super(x, y, width, height, Text.literal(Float.toString(round(value))), value);
            this.action = action;
        }

        @Override
        protected void updateMessage() {
            setMessage(Text.literal(Float.toString(round(value))));
        }

        private static float round(double value) {
            return PneumonoMathHelper.round(value * 100) / 100F;
        }

        @Override
        protected void applyValue() {
            action.onChange(this, round(value));
        }

        public void setValue(float value) {
            this.value = value;
            updateMessage();
        }
    }

    public interface SliderChangeAction {
        void onChange(ConfigSliderWidget widget, float value);
    }
}
