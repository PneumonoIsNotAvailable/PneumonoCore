package net.pneumono.pneumonocore.config_api.screen.components;

import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;

public class FloatConfigSliderButton extends AbstractSliderButton {
    private final SliderChangeAction action;

    public FloatConfigSliderButton(SliderChangeAction action, float value, int x, int y, int width, int height) {
        super(x, y, width, height, Component.literal(Float.toString(value)), value);
        this.action = action;
    }

    @Override
    protected void updateMessage() {
        setMessage(Component.literal(Float.toString(round(this.value))));
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
        void onChange(FloatConfigSliderButton widget, float value);
    }
}
