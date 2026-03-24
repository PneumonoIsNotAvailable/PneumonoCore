package net.pneumono.pneumonocore.config_api.screen.entries;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config_api.screen.components.ConfigsList;
import net.pneumono.pneumonocore.config_api.configurations.BooleanConfiguration;

//? if >=26.1 {
import net.minecraft.client.gui.GuiGraphicsExtractor;
//?} else {
/*import net.minecraft.client.gui.GuiGraphics;
*///?}

public class BooleanConfigurationEntry extends AbstractConfigurationEntry<Boolean, BooleanConfiguration> {
    private final Button toggleButton;

    public BooleanConfigurationEntry(ConfigOptionsScreen parent, ConfigsList widget, BooleanConfiguration configuration) {
        super(parent, widget, configuration);

        this.toggleButton = addChild(Button.builder(
                Component.literal(""), (button) -> this.setValue(!this.value)
        ).bounds(0, 0, getTotalWidgetWidth(), 20).build());
    }

    @Override
    public void updateButtons() {
        this.toggleButton.setMessage(Component.translatable(
                this.value ? "configs_screen.pneumonocore.boolean_enabled" :
                        "configs_screen.pneumonocore.boolean_disabled"
        ));
    }

    @Override
    public void displayContent(/*? if >=26.1 {*/GuiGraphicsExtractor/*?} else {*//*GuiGraphics*//*?}*/ graphics, int x, int y, int mouseX, int mouseY, int entryHeight, boolean hovered, float tickDelta) {
        super.displayContent(graphics, x, y, mouseX, mouseY, entryHeight, hovered, tickDelta);

        this.toggleButton.setX(x + getWidgetStartX());
        this.toggleButton.setY(y);
        this.toggleButton./*? if >=26.1 {*/extractRenderState/*?} else {*//*render*//*?}*/(graphics, mouseX, mouseY, tickDelta);
    }
}
