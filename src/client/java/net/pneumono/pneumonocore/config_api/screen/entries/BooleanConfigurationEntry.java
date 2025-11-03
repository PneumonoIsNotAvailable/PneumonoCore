package net.pneumono.pneumonocore.config_api.screen.entries;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config_api.screen.components.ConfigsList;
import net.pneumono.pneumonocore.config_api.configurations.BooleanConfiguration;

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

    //? if >=1.21.9 {
    public void renderContent(GuiGraphics graphics, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        super.renderContent(graphics, mouseX, mouseY, hovered, tickDelta);
        int x = getX();
        int y = getY();
    //?} else {
    /*public void render(GuiGraphics graphics, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        super.render(graphics, index, y, x, entryWidth, entryHeight, mouseX, mouseY, hovered, tickDelta);
    *///?}

        this.toggleButton.setX(x + getWidgetStartX());
        this.toggleButton.setY(y);
        this.toggleButton.render(graphics, mouseX, mouseY, tickDelta);
    }
}
