package net.pneumono.pneumonocore.config_api.screen.entries;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.pneumono.pneumonocore.config_api.ClientConfigApi;
import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config_api.screen.widgets.ConfigsListWidget;

import java.util.List;
import java.util.Objects;

/**
 * The entry used in a config screen when no entry type has been registered for a configuration.
 *
 * <p>Entry types can be registered via {@link ClientConfigApi#registerConfigEntryType}
 */
public class ErroneousConfigurationEntry<T, C extends AbstractConfiguration<T>> extends AbstractConfigurationEntry<T, C> {
    public ErroneousConfigurationEntry(ConfigOptionsScreen parent, ConfigsListWidget widget, C configuration) {
        super(parent, widget, configuration);
    }

    @Override
    public void updateWidgets() {

    }

    @Override
    public List<? extends ClickableWidget> getChildren() {
        return ImmutableList.of();
    }

    @Override
    public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        super.render(context, index, y, x, entryWidth, entryHeight, mouseX, mouseY, hovered, tickDelta);

        TextRenderer textRenderer = Objects.requireNonNull(this.parent.getClient()).textRenderer;
        Text text = Text.translatable("configs_screen.pneumonocore.entry_type_error");
        int width = textRenderer.getWidth(text);
        int textX = x + OFFSET + 90 - (width / 2);
        int textY = (y + entryHeight / 2) - 2;
        context.drawText(textRenderer, text, textX, textY, /*? if >=1.20.4 {*//*Colors.LIGHT_RED*//*?} else {*/Colors.RED/*?}*/, true);
    }
}
