package net.pneumono.pneumonocore.config_api.screen.entries;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.pneumono.pneumonocore.config_api.ConfigApi;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config_api.screen.components.ConfigsList;
import net.pneumono.pneumonocore.config_api.configurations.EnumConfiguration;
import net.pneumono.pneumonocore.util.PneumonoCoreUtil;

//? if >=26.1 {
import net.minecraft.client.gui.GuiGraphicsExtractor;
//?} else {
/*import net.minecraft.client.gui.GuiGraphics;
*///?}

public class EnumConfigurationEntry<T extends Enum<T>> extends AbstractConfigurationEntry<T, EnumConfiguration<T>> {
    private final Button cycleButton;

    public EnumConfigurationEntry(ConfigOptionsScreen parent, ConfigsList widget, EnumConfiguration<T> configuration) {
        super(parent, widget, configuration);

        this.cycleButton = addChild(Button.builder(
                Component.literal(""), (button) -> this.setValue(PneumonoCoreUtil.cycleEnum(this.value))
        ).bounds(0, 0, getTotalWidgetWidth(), 20).build());
    }

    @Override
    public void updateButtons() {
        this.cycleButton.setMessage(Component.translatable(ConfigApi.toTranslationKey(this.configuration, this.value.name().toLowerCase())));
    }

    @Override
    public void displayContent(/*? if >=26.1 {*/GuiGraphicsExtractor/*?} else {*//*GuiGraphics*//*?}*/ graphics, int x, int y, int mouseX, int mouseY, int entryHeight, boolean hovered, float tickDelta) {
        super.displayContent(graphics, x, y, mouseX, mouseY, entryHeight, hovered, tickDelta);

        this.cycleButton.setX(x + getWidgetStartX());
        this.cycleButton.setY(y);
        this.cycleButton./*? if >=26.1 {*/extractRenderState/*?} else {*//*render*//*?}*/(graphics, mouseX, mouseY, tickDelta);
    }
}
