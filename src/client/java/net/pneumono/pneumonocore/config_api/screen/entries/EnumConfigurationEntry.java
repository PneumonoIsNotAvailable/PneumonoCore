package net.pneumono.pneumonocore.config_api.screen.entries;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.pneumono.pneumonocore.config_api.ConfigApi;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config_api.screen.components.ConfigsList;
import net.pneumono.pneumonocore.config_api.configurations.EnumConfiguration;
import net.pneumono.pneumonocore.util.PneumonoCoreUtil;

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

    //? if >=1.21.9 {
    public void renderContent(GuiGraphics graphics, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        super.renderContent(graphics, mouseX, mouseY, hovered, tickDelta);
        int x = getX();
        int y = getY();
    //?} else {
    /*public void render(GuiGraphics graphics, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        super.render(graphics, index, y, x, entryWidth, entryHeight, mouseX, mouseY, hovered, tickDelta);
    *///?}

        this.cycleButton.setX(x + getWidgetStartX());
        this.cycleButton.setY(y);
        this.cycleButton.render(graphics, mouseX, mouseY, tickDelta);
    }
}
