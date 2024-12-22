package net.pneumono.pneumonocore.config.entries;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.gui.widget.TextIconButtonWidget;
import net.minecraft.text.Text;
import net.pneumono.pneumonocore.PneumonoCore;
import net.pneumono.pneumonocore.config.AbstractConfiguration;
import net.pneumono.pneumonocore.config.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config.ConfigsListWidget;

import java.util.Objects;

public abstract class AbstractConfigurationEntry extends AbstractConfigListWidgetEntry {
    protected final AbstractConfiguration<?> configuration;
    protected final Text configName;
    protected final ConfigOptionsScreen parent;
    protected final ConfigsListWidget widget;
    protected final TextIconButtonWidget infoWidget;

    public AbstractConfigurationEntry(AbstractConfiguration<?> configuration, ConfigOptionsScreen parent, ConfigsListWidget widget) {
        this.configuration = configuration;
        this.configName = Text.translatable(configuration.getTranslationKey());
        this.parent = parent;
        this.widget = widget;
        this.infoWidget = TextIconButtonWidget.builder(Text.translatable("pneumonocore.configs_screen.information"), button -> {}, true)
                .texture(PneumonoCore.identifier( "icon/information"), 15, 15)
                .width(20)
                .build();
        this.infoWidget.setTooltip(Tooltip.of(Text.translatable(configuration.getTooltipTranslationKey())));
    }

    public void renderNameAndInformation(DrawContext context, int x, int y, int entryHeight, int mouseX, int mouseY, float delta) {
        TextRenderer textRenderer = Objects.requireNonNull(this.parent.getClient()).textRenderer;
        context.drawText(textRenderer, this.configName, x + 104 - textRenderer.getWidth(this.configName), (y + entryHeight / 2) - 2, 16777215, true);
        this.infoWidget.setX(x + 227);
        this.infoWidget.setY(y);
        this.infoWidget.render(context, mouseX, mouseY, delta);
    }
}
