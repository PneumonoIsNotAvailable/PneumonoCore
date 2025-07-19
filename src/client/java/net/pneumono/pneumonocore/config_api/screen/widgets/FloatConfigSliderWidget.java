package net.pneumono.pneumonocore.config_api.screen.widgets;

import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

public class FloatConfigSliderWidget extends SliderWidget {
    private final SliderChangeAction action;

    public FloatConfigSliderWidget(SliderChangeAction action, float value, int x, int y, int width, int height) {
        super(x, y, width, height, Text.literal(Float.toString(value)), value);
        this.action = action;
    }

    @Override
    protected void updateMessage() {
        setMessage(Text.literal(Float.toString(round(this.value))));
    }

    @Override
    protected void applyValue() {
        this.action.onChange(this, round(this.value));
    }

    public void setValue(float value) {
        this.value = value;
        updateMessage();
    }

    private static float round(double value) {
        return Math.round(value * 100) / 100F;
    }

    public interface SliderChangeAction {
        void onChange(FloatConfigSliderWidget widget, float value);
    }
}
