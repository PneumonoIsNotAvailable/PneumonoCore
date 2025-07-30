package net.pneumono.pneumonocore.config_api.screen.entries;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.pneumono.pneumonocore.config_api.ConfigApi;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config_api.screen.widgets.ConfigsListWidget;
import net.pneumono.pneumonocore.config_api.configurations.StringConfiguration;

import java.util.Objects;

public class StringConfigurationEntry extends AbstractConfigurationEntry<String, StringConfiguration> {
    private final TextFieldWidget textWidget;

    public StringConfigurationEntry(ConfigOptionsScreen parent, ConfigsListWidget widget, StringConfiguration configuration) {
        super(parent, widget, configuration);
        this.textWidget = addChild(new TextFieldWidget(
                Objects.requireNonNull(parent.getClient()).textRenderer,
                0, 0, getTotalWidgetWidth(), 20, null,
                Text.translatable(ConfigApi.toTranslationKey(this.configuration))
        ));
        this.textWidget.setChangedListener(this::setValue);
    }

    @Override
    public void updateWidgets() {
        String valueText = String.valueOf(this.value);
        if (!Objects.equals(this.textWidget.getText(), valueText)) {
            this.textWidget.setText(valueText);
        }
    }

    @Override
    public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        super.render(context, index, y, x, entryWidth, entryHeight, mouseX, mouseY, hovered, tickDelta);

        this.textWidget.setX(x + getWidgetStartX());
        this.textWidget.setY(y);
        this.textWidget.render(context, mouseX, mouseY, tickDelta);
    }
}
