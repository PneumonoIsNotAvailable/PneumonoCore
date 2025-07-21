package net.pneumono.pneumonocore.config_api.screen.entries;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.TextIconButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.pneumono.pneumonocore.PneumonoCore;
import net.pneumono.pneumonocore.config_api.ConfigApi;
import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config_api.screen.widgets.ConfigsListWidget;

import java.util.Objects;

public abstract class AbstractConfigurationEntry<T, C extends AbstractConfiguration<T>> extends AbstractConfigListWidgetEntry {
    protected final ConfigsListWidget widget;
    protected final C configuration;
    protected final TextIconButtonWidget infoWidget;

    protected T value;

    public AbstractConfigurationEntry(ConfigOptionsScreen parent, ConfigsListWidget widget, C configuration) {
        super(parent);
        this.widget = widget;
        this.configuration = configuration;
        this.infoWidget = TextIconButtonWidget.builder(Text.translatable("configs_screen.pneumonocore.information"), button -> {}, true)
                .texture(PneumonoCore.identifier( "icon/information"), 15, 15)
                .width(20)
                .build();
        this.infoWidget.setTooltip(Tooltip.of(
                Text.translatable(ConfigApi.toTranslationKey(configuration, "tooltip"))
                        .append(Text.literal("\n\n"))
                        .append(switch (configuration.info().getLoadType()) {
                            case INSTANT -> Text.translatable("configs_screen.pneumonocore.load_instant");
                            case RELOAD -> Text.translatable("configs_screen.pneumonocore.load_reload");
                            case RESTART -> Text.translatable("configs_screen.pneumonocore.load_restart");
                        })
                        .append(Text.literal("\n\n"))
                        .append(configuration.info().isClientSided() ?
                                Text.translatable("configs_screen.pneumonocore.client") :
                                Text.translatable("configs_screen.pneumonocore.server")
                        )
        ));

        this.value = this.parent.getConfigValue(this.configuration);
    }

    public C getConfiguration() {
        return configuration;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
        this.widget.updateEntryList();
        this.updateWidgets();
    }

    @Override
    public void reset() {
        setValue(this.configuration.info().getDefaultValue());
    }

    @Override
    public boolean shouldDisplay() {
        AbstractConfiguration<?> configParent = this.configuration.info().getParent();
        if (configParent == null) return true;
        AbstractConfigurationEntry<?, ?> configParentEntry = this.widget.getEntry(configParent.info().getId());
        if (configParentEntry == null) return true;
        return this.configuration.info().isEnabled(configParentEntry.value);
    }

    /**
     * Renders the config name and the information icon that displays the config tooltip.
     *
     * <p>Should be called by all subclasses in {@code render()}.
     */
    public void renderNameAndInformation(DrawContext context, int x, int y, int entryHeight, int mouseX, int mouseY, float delta) {
        Text configName = Text.translatable(ConfigApi.toTranslationKey(this.configuration));
        TextRenderer textRenderer = Objects.requireNonNull(this.parent.getClient()).textRenderer;
        context.drawText(textRenderer, configName, x + OFFSET + 27 - textRenderer.getWidth(configName), (y + entryHeight / 2) - 2, Colors.WHITE, true);

        this.infoWidget.setX(x + OFFSET + 150);
        this.infoWidget.setY(y);
        this.infoWidget.render(context, mouseX, mouseY, delta);
    }

    /**
     * @return Whether this value should be saved when the Save button is pressed.
     *
     * <p>Should return false if the value is invalid.
     * (e.g. a text field widget for integers where a non-integer value has been entered)
     */
    public boolean shouldSaveValue() {
        return true;
    }
}
