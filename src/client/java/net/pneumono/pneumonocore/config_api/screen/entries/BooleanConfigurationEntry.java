package net.pneumono.pneumonocore.config_api.screen.entries;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config_api.screen.widgets.ConfigsListWidget;
import net.pneumono.pneumonocore.config_api.configurations.BooleanConfiguration;

public class BooleanConfigurationEntry extends AbstractConfigurationEntry<Boolean, BooleanConfiguration> {
    private final ButtonWidget toggleWidget;

    public BooleanConfigurationEntry(ConfigOptionsScreen parent, ConfigsListWidget widget, BooleanConfiguration configuration) {
        super(parent, widget, configuration);

        this.toggleWidget = addChild(ButtonWidget.builder(
                Text.literal(""), (button) -> this.setValue(!this.value)
        ).dimensions(0, 0, getTotalWidgetWidth(), 20).build());
    }

    @Override
    public void updateWidgets() {
        this.toggleWidget.setMessage(Text.translatable(
                this.value ? "configs_screen.pneumonocore.boolean_enabled" :
                        "configs_screen.pneumonocore.boolean_disabled"
        ));
    }

    //? if >=1.21.9 {
    public void render(DrawContext context, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        super.render(context, mouseX, mouseY, hovered, tickDelta);
        int x = getX();
        int y = getY();
    //?} else {
    /*public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        super.render(context, index, y, x, entryWidth, entryHeight, mouseX, mouseY, hovered, tickDelta);
    *///?}

        this.toggleWidget.setX(x + getWidgetStartX());
        this.toggleWidget.setY(y);
        this.toggleWidget.render(context, mouseX, mouseY, tickDelta);
    }
}
