package net.pneumono.pneumonocore.config.entries;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.pneumono.pneumonocore.config.AbstractConfiguration;
import net.pneumono.pneumonocore.config.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config.ConfigsListWidget;

import java.util.List;
import java.util.Objects;

public class ErroneousConfigurationEntry extends AbstractConfigurationEntry {
    public ErroneousConfigurationEntry(AbstractConfiguration<?> configuration, ConfigOptionsScreen parent, ConfigsListWidget widget) {
        super(configuration, parent, widget);
    }

    @Override
    public void update() {

    }

    @Override
    public List<? extends Selectable> selectableChildren() {
        return ImmutableList.of();
    }

    @Override
    public List<? extends Element> children() {
        return ImmutableList.of();
    }

    @Override
    public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        renderNameAndInformation(context, x, y, entryHeight, mouseX, mouseY, tickDelta);
        TextRenderer textRenderer = Objects.requireNonNull(this.parent.getClient()).textRenderer;

        Text text = Text.translatable("pneumonocore.configs_screen.entry_type_error").formatted(Formatting.RED);
        int width = textRenderer.getWidth(text);
        int textX = x + 145 - (width / 2);
        int textY = y + entryHeight / 2;
        context.drawText(textRenderer, Text.translatable("pneumonocore.configs_screen.entry_type_error").formatted(Formatting.RED), textX, textY - 9 / 2, 16777215, false);
    }
}
