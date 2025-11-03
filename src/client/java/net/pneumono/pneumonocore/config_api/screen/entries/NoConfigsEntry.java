package net.pneumono.pneumonocore.config_api.screen.entries;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;

import java.util.Objects;

public class NoConfigsEntry extends NonInteractableEntry {
    public NoConfigsEntry(ConfigOptionsScreen parent) {
        super(parent);
    }

    @Override
    //? if >=1.21.9 {
    public void renderContent(GuiGraphics graphics, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        int x = getX();
        int y = getY();
        int entryHeight = getContentHeight();
    //?} else {
    /*public void render(GuiGraphics graphics, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
    *///?}

        Font font = Objects.requireNonNull(this.parent.getMinecraft()).font;
        graphics.drawCenteredString(
                font, Component.translatable("configs_screen.pneumonocore.no_configs"),
                x + (getRowEndXOffset() / 2),
                (y + entryHeight / 2) - 2,
                // Colors.LIGHT_RED
                -2142128
        );
    }
}
