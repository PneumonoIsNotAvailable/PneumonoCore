package net.pneumono.pneumonocore.config_api.entries;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config_api.ConfigsListWidget;

import java.util.List;
import java.util.Objects;

public class ErroneousConfigurationEntry<T, C extends AbstractConfiguration<T>> extends AbstractConfigurationEntry<T, C> {
    public ErroneousConfigurationEntry(ConfigOptionsScreen parent, ConfigsListWidget widget, C configuration) {
        super(parent, widget, configuration);
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
        Text text = Text.translatable("configs_screen.pneumonocore.entry_type_error");
        int width = textRenderer.getWidth(text);
        int textX = x + OFFSET + 90 - (width / 2);
        int textY = (y + entryHeight / 2) - 2;
        context.drawText(textRenderer, text, textX, textY, Colors.RED, true);
    }
}
