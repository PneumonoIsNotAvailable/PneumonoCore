package net.pneumono.pneumonocore.config_api.screen.components;

import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class IntegerConfigSliderButton extends AbstractSliderButton {
    private final SliderChangeAction action;
    private final int min;
    private final int max;

    public IntegerConfigSliderButton(SliderChangeAction action, int value, int min, int max, int x, int y, int width, int height) {
        super(x, y, width, height, Component.literal(Integer.toString(value)), toPercentage(value, min, max));
        this.action = action;
        this.min = min;
        this.max = max;
    }

    @Override
    protected void updateMessage() {
        setMessage(Component.literal(Integer.toString(fromPercentage(this.value, this.min, this.max))));
    }

    @Override
    protected void applyValue() {
        this.action.onChange(this, fromPercentage(this.value, this.min, this.max));
    }

    public void setValue(int value) {
        this.value = toPercentage(Mth.clamp(value, this.min, this.max), this.min, this.max);
        updateMessage();
    }

    private static double toPercentage(int value, int min, int max) {
        return (value - min) / (double)(max - min);
    }

    private static int fromPercentage(double value, int min, int max) {
        return (int)Math.round((value * (max - min)) + min);
    }

    public interface SliderChangeAction {
        void onChange(IntegerConfigSliderButton widget, int value);
    }
}
