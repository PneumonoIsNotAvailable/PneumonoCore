package net.pneumono.pneumonocore.config.entries;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.pneumono.pneumonocore.config.ConfigCategory;
import net.pneumono.pneumonocore.config.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config.ConfigsListWidget;

import java.util.List;
import java.util.Objects;

public class CategoryTitleEntry extends AbstractConfigListWidgetEntry {
    protected final ConfigOptionsScreen parent;
    protected final ConfigCategory category;

    public CategoryTitleEntry(ConfigCategory category, ConfigOptionsScreen parent, ConfigsListWidget widget) {
        this.parent = parent;
        this.category = category;
    }

    @Override
    public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        TextRenderer textRenderer = Objects.requireNonNull(this.parent.getClient()).textRenderer;
        context.drawCenteredTextWithShadow(textRenderer, Text.translatable(category.getTranslationKey()), x + 108, (y + entryHeight / 2) - 2, 16777215);
    }

    @Override
    public List<? extends Selectable> selectableChildren() {
        return List.of();
    }

    @Override
    public List<? extends Element> children() {
        return List.of();
    }

    @Override
    public void update() {

    }

    @Override
    public void reset() {

    }
}
