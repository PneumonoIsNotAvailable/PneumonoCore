package net.pneumono.pneumonocore.config_api.screen.entries;

import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.pneumono.pneumonocore.config_api.ClientConfigApi;
import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config_api.screen.components.ConfigsList;

import java.util.Objects;

//? if >=26.1 {
import net.minecraft.client.gui.GuiGraphicsExtractor;
//?} else {
/*import net.minecraft.client.gui.GuiGraphics;
*///?}

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
    public void displayContent(/*? if >=26.1 {*/GuiGraphicsExtractor/*?} else {*//*GuiGraphics*//*?}*/ graphics, int x, int y, int mouseX, int mouseY, int entryHeight, boolean hovered, float tickDelta) {
        Font font = Objects.requireNonNull(this.parent.getMinecraft()).font;
        Component text = Component.translatable("configs_screen.pneumonocore.entry_type_error");
        int textX = x + getWidgetStartX() + (getTotalWidgetWidth() / 2);
        int textY = (y + entryHeight / 2) - 2;
        graphics./*? if >=26.1 {*/centeredText/*?} else {*//*drawCenteredString*//*?}*/(
                font,
                text,
                textX, textY,
                // CommonColors.SOFT_RED
                -2142128
        );
    }
}
