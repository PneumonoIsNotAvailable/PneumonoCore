package net.pneumono.pneumonocore.config_api.widgets;

import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

public class IntegerConfigSliderWidget extends SliderWidget {
    private final SliderChangeAction action;
    private final int min;
    private final int max;

    public IntegerConfigSliderWidget(SliderChangeAction action, int value, int min, int max, int x, int y, int width, int height) {
        super(x, y, width, height, Text.literal(Integer.toString(value)), toPercentage(value, min, max));
        this.action = action;
        this.min = min;
        this.max = max;
    }

    @Override
    protected void updateMessage() {
        setMessage(Text.literal(Integer.toString(fromPercentage(this.value, this.min, this.max))));
    }

    @Override
    protected void applyValue() {
        this.action.onChange(this, fromPercentage(this.value, this.min, this.max));
    }

    public void setValue(int value) {
        this.value = toPercentage(MathHelper.clamp(value, this.min, this.max), this.min, this.max);
        updateMessage();
    }

    private static double toPercentage(int value, int min, int max) {
        return (value - min) / (double)(max - min);
    }

    private static int fromPercentage(double value, int min, int max) {
        return (int)Math.round((value * (max - min)) + min);
    }

    public interface SliderChangeAction {
        void onChange(IntegerConfigSliderWidget widget, int value);
    }
}
