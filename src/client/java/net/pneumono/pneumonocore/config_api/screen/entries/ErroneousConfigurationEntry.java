package net.pneumono.pneumonocore.config_api.screen.entries;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.pneumono.pneumonocore.config_api.ClientConfigApi;
import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config_api.screen.widgets.ConfigsListWidget;

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
    //? if >=1.21.9 {
    public void render(DrawContext context, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        super.render(context, mouseX, mouseY, hovered, tickDelta);
        int x = getX();
        int y = getY();
        int entryHeight = getContentHeight();
    //?} else {
    /*public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        super.render(context, index, y, x, entryWidth, entryHeight, mouseX, mouseY, hovered, tickDelta);
    *///?}

        TextRenderer textRenderer = Objects.requireNonNull(this.parent.getClient()).textRenderer;
        Text text = Text.translatable("configs_screen.pneumonocore.entry_type_error");
        int textX = x + getWidgetStartX() + (getTotalWidgetWidth() / 2);
        int textY = (y + entryHeight / 2) - 2;
        context.drawCenteredTextWithShadow(
                textRenderer,
                text,
                textX, textY,
                // Colors.LIGHT_RED
                -2142128
        );
    }
}
