package net.pneumono.pneumonocore.config_api.screen.entries;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.CommonColors;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;

import java.util.Objects;

public class CategoryTitleEntry extends NonInteractableEntry {
    protected final String translationKey;

    public CategoryTitleEntry(ConfigOptionsScreen parent, String translationKey) {
        super(parent);
        this.translationKey = translationKey;
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
                font, Component.translatable(translationKey),
                x + (getRowEndXOffset() / 2),
                (y + entryHeight / 2) - 2,
                CommonColors.WHITE
        );
    }
}
