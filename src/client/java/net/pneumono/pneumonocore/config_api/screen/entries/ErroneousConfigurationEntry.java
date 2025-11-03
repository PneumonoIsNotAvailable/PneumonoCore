package net.pneumono.pneumonocore.config_api.screen.entries;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.pneumono.pneumonocore.config_api.ClientConfigApi;
import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config_api.screen.components.ConfigsList;

import java.util.Objects;

/**
 * The entry used in a config screen when no entry type has been registered for a configuration.
 *
 * <p>Entry types can be registered via {@link ClientConfigApi#registerConfigEntryType}
 */
public class ErroneousConfigurationEntry<T, C extends AbstractConfiguration<T>> extends AbstractConfigurationEntry<T, C> {
    public ErroneousConfigurationEntry(ConfigOptionsScreen parent, ConfigsList widget, C configuration) {
        super(parent, widget, configuration);
    }

    @Override
    public void updateButtons() {

    }

    @Override
    //? if >=1.21.9 {
    public void renderContent(GuiGraphics graphics, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        super.renderContent(graphics, mouseX, mouseY, hovered, tickDelta);
        int x = getX();
        int y = getY();
        int entryHeight = getContentHeight();
    //?} else {
    /*public void render(GuiGraphics graphics, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        super.render(graphics, index, y, x, entryWidth, entryHeight, mouseX, mouseY, hovered, tickDelta);
    *///?}

        Font font = Objects.requireNonNull(this.parent.getMinecraft()).font;
        Component text = Component.translatable("configs_screen.pneumonocore.entry_type_error");
        int textX = x + getWidgetStartX() + (getTotalWidgetWidth() / 2);
        int textY = (y + entryHeight / 2) - 2;
        graphics.drawCenteredString(
                font,
                text,
                textX, textY,
                // CommonColors.SOFT_RED
                -2142128
        );
    }
}
