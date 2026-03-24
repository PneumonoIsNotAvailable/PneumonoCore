package net.pneumono.pneumonocore.config_api.screen.entries;

import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;

import java.util.Objects;

//? if >=26.1 {
import net.minecraft.client.gui.GuiGraphicsExtractor;
//?} else {
/*import net.minecraft.client.gui.GuiGraphics;
*///?}

public class NoConfigsEntry extends NonInteractableEntry {
    public NoConfigsEntry(ConfigOptionsScreen parent) {
        super(parent);
    }

    @Override
    public void displayContent(/*? if >=26.1 {*/GuiGraphicsExtractor/*?} else {*//*GuiGraphics*//*?}*/ graphics, int x, int y, int mouseX, int mouseY, int entryHeight, boolean hovered, float tickDelta) {

        Font font = Objects.requireNonNull(this.parent.getMinecraft()).font;
        graphics./*? if >=26.1 {*/centeredText/*?} else {*//*drawCenteredString*//*?}*/(
                font, Component.translatable("configs_screen.pneumonocore.no_configs"),
                x + (getRowEndXOffset() / 2),
                (y + entryHeight / 2) - 2,
                // Colors.LIGHT_RED
                -2142128
        );
    }
}
