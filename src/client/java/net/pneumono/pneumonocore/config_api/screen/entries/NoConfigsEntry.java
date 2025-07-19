package net.pneumono.pneumonocore.config_api.screen.entries;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;

import java.util.Objects;

public class NoConfigsEntry extends NonInteractableEntry {
    public NoConfigsEntry(ConfigOptionsScreen parent) {
        super(parent);
    }

    @Override
    public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        TextRenderer textRenderer = Objects.requireNonNull(this.parent.getClient()).textRenderer;
        context.drawCenteredTextWithShadow(textRenderer, Text.translatable("configs_screen.pneumonocore.no_configs"), x + OFFSET + 31, (y + entryHeight / 2) - 2, Colors.LIGHT_RED);
    }
}
