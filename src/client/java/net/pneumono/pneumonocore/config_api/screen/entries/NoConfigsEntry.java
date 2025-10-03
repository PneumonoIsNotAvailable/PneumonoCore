package net.pneumono.pneumonocore.config_api.screen.entries;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;

import java.util.Objects;

public class NoConfigsEntry extends NonInteractableEntry {
    public NoConfigsEntry(ConfigOptionsScreen parent) {
        super(parent);
    }

    @Override
    //? if >=1.21.9 {
    public void render(DrawContext context, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        int x = getX();
        int y = getY();
        int entryHeight = getContentHeight();
    //?} else {
    /*public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
    *///?}

        TextRenderer textRenderer = Objects.requireNonNull(this.parent.getClient()).textRenderer;
        context.drawCenteredTextWithShadow(
                textRenderer, Text.translatable("configs_screen.pneumonocore.no_configs"),
                x + (getRowEndXOffset() / 2),
                (y + entryHeight / 2) - 2,
                // Colors.LIGHT_RED
                -2142128
        );
    }
}
