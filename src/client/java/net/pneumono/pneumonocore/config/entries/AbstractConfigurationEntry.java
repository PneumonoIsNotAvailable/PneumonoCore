package net.pneumono.pneumonocore.config.entries;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.pneumono.pneumonocore.PneumonoCore;
import net.pneumono.pneumonocore.config.AbstractConfiguration;
import net.pneumono.pneumonocore.config.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config.ConfigsListWidget;

import java.util.Objects;

public abstract class AbstractConfigurationEntry extends ElementListWidget.Entry<AbstractConfigurationEntry> {
    protected final AbstractConfiguration<?> configuration;
    protected final Text configName;
    protected final ConfigOptionsScreen parent;
    protected final ConfigsListWidget widget;
    protected final InformationIconWidget infoWidget;

    public AbstractConfigurationEntry(AbstractConfiguration<?> configuration, ConfigOptionsScreen parent, ConfigsListWidget widget) {
        this.configuration = configuration;
        this.configName = Text.translatable(configuration.getTranslationKey());
        this.parent = parent;
        this.widget = widget;
        this.infoWidget = new InformationIconWidget(Text.translatable("pneumonocore.configs_screen.information"));
        this.infoWidget.setTooltip(Tooltip.of(Text.translatable(configuration.getTranslationKey() + ".tooltip")));
    }

    public abstract void update();

    public void renderNameAndInformation(DrawContext context, int x, int y, int entryHeight, int mouseX, int mouseY, float delta) {
        TextRenderer textRenderer = Objects.requireNonNull(this.parent.getClient()).textRenderer;
        context.drawText(textRenderer, this.configName, x - 60, (y + entryHeight / 2) - 9 / 2, 16777215, false);
        this.infoWidget.setX(x + 205);
        this.infoWidget.setY(y);
        this.infoWidget.render(context, mouseX, mouseY, delta);
    }

    public static class InformationIconWidget extends ButtonWidget {
        InformationIconWidget(Text message) {
            super(0, 0, 20, 20, message, button -> {}, DEFAULT_NARRATION_SUPPLIER);
        }

        @Override
        public void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
            super.renderButton(context, mouseX, mouseY, delta);
            int i = this.getX() + 3;
            int j = this.getY() + 3;
            this.drawTexture(context, new Identifier(PneumonoCore.MOD_ID, "textures/gui/sprites/icon/information.png"), i, j, 0, 0, 0, 15, 15, 15, 15);
        }

        @Override
        public void drawMessage(DrawContext context, TextRenderer textRenderer, int color) {
        }
    }
}
