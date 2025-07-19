package net.pneumono.pneumonocore.config_api.entries;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.TextIconButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.pneumono.pneumonocore.PneumonoCore;
import net.pneumono.pneumonocore.config_api.ConfigApi;
import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config_api.ConfigsListWidget;
import net.pneumono.pneumonocore.config_api.configurations.ConfigManager;

import java.util.Objects;

public abstract class AbstractConfigurationEntry<T, C extends AbstractConfiguration<T>> extends AbstractConfigListWidgetEntry {
    protected final ConfigOptionsScreen parent;
    protected final ConfigsListWidget widget;
    protected final C configuration;
    protected final TextIconButtonWidget infoWidget;

    protected T value;

    public AbstractConfigurationEntry(ConfigOptionsScreen parent, ConfigsListWidget widget, C configuration) {
        this.parent = parent;
        this.widget = widget;
        this.configuration = configuration;
        this.infoWidget = TextIconButtonWidget.builder(Text.translatable("configs_screen.pneumonocore.information"), button -> {}, true)
                .texture(PneumonoCore.identifier( "icon/information"), 15, 15)
                .width(20)
                .build();
        this.infoWidget.setTooltip(Tooltip.of(
                Text.translatable(ConfigApi.toTranslationKey(configuration, "tooltip"))
                        .append(Text.literal("\n\n"))
                        .append(switch (configuration.getInfo().getLoadType()) {
                            case INSTANT -> Text.translatable("configs_screen.pneumonocore.load_instant");
                            case RELOAD -> Text.translatable("configs_screen.pneumonocore.load_reload");
                            case RESTART -> Text.translatable("configs_screen.pneumonocore.load_restart");
                        })
                        .append(Text.literal("\n\n"))
                        .append(configuration.getInfo().isClientSided() ?
                                Text.translatable("configs_screen.pneumonocore.client") :
                                Text.translatable("configs_screen.pneumonocore.server")
                        )
        ));

        this.value = ConfigManager.getLoadedValue(this.configuration);
    }

    public C getConfiguration() {
        return configuration;
    }

    @Override
    public void reset() {
        setValue(this.configuration.getInfo().getDefaultValue());
    }

    @Override
    public boolean shouldDisplay() {
        AbstractConfiguration<?> configParent = this.configuration.getInfo().getParent();
        if (configParent == null) return true;
        AbstractConfigurationEntry<?, ?> configEntry = this.widget.getEntry(configParent.getId());
        if (configEntry == null) return true;
        return this.configuration.getInfo().isEnabled(configEntry.value);
    }

    public void setValue(T value) {
        this.value = value;
        this.widget.update();
    }

    public void save() {
        this.widget.save(this.configuration.getInfo().getName(), this.value);
    }

    public void renderNameAndInformation(DrawContext context, int x, int y, int entryHeight, int mouseX, int mouseY, float delta) {
        Text configName = getConfigName();
        TextRenderer textRenderer = Objects.requireNonNull(this.parent.getClient()).textRenderer;
        context.drawText(textRenderer, configName, x + OFFSET + 27 - textRenderer.getWidth(configName), (y + entryHeight / 2) - 2, Colors.WHITE, true);

        this.infoWidget.setX(x + OFFSET + 150);
        this.infoWidget.setY(y);
        this.infoWidget.render(context, mouseX, mouseY, delta);
    }

    public Text getConfigName() {
        return Text.translatable(ConfigApi.toTranslationKey(this.configuration));
    }
}
