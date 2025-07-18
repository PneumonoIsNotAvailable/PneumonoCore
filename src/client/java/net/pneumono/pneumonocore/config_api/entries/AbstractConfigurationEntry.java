package net.pneumono.pneumonocore.config_api.entries;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.TextIconButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Formatting;
import net.pneumono.pneumonocore.PneumonoCore;
import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config_api.ConfigsListWidget;

import java.util.Objects;

public abstract class AbstractConfigurationEntry<T extends AbstractConfiguration<?>> extends AbstractConfigListWidgetEntry {
    protected final T configuration;
    protected final Text configName;
    protected final ConfigOptionsScreen parent;
    protected final ConfigsListWidget widget;
    protected final TextIconButtonWidget infoWidget;

    public AbstractConfigurationEntry(T configuration, ConfigOptionsScreen parent, ConfigsListWidget widget) {
        this.configuration = configuration;
        this.configName = Text.translatable(configuration.getTranslationKey());
        this.parent = parent;
        this.widget = widget;
        this.infoWidget = TextIconButtonWidget.builder(Text.translatable("configs_screen.pneumonocore.information"), button -> {}, true)
                .texture(PneumonoCore.identifier( "icon/information"), 15, 15)
                .width(20)
                .build();
        this.infoWidget.setTooltip(Tooltip.of(
                Text.translatable(configuration.getTooltipTranslationKey())
                        .append(Text.literal("\n\n"))
                        .append(configuration.isClientSided() ?
                                Text.translatable("configs_screen.pneumonocore.client").formatted(Formatting.AQUA) :
                                Text.translatable("configs_screen.pneumonocore.server").formatted(Formatting.GOLD)
                        )
        ));
    }

    public void renderNameAndInformation(DrawContext context, int x, int y, int entryHeight, int mouseX, int mouseY, float delta) {
        TextRenderer textRenderer = Objects.requireNonNull(this.parent.getClient()).textRenderer;
        context.drawText(textRenderer, this.configName, x + OFFSET + 27 - textRenderer.getWidth(this.configName), (y + entryHeight / 2) - 2, Colors.WHITE, true);
        this.infoWidget.setX(x + OFFSET + 150);
        this.infoWidget.setY(y);
        this.infoWidget.render(context, mouseX, mouseY, delta);
    }
}
