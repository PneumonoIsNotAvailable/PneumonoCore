package net.pneumono.pneumonocore.config_api.screen.entries;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.pneumono.pneumonocore.config_api.ConfigApi;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config_api.screen.widgets.ConfigsListWidget;
import net.pneumono.pneumonocore.config_api.configurations.StringConfiguration;

import java.util.List;
import java.util.Objects;

public class StringConfigurationEntry extends AbstractConfigurationEntry<String, StringConfiguration> {
    private final TextFieldWidget textWidget;

    public StringConfigurationEntry(ConfigOptionsScreen parent, ConfigsListWidget widget, StringConfiguration configuration) {
        super(parent, widget, configuration);
        this.textWidget = new TextFieldWidget(
                Objects.requireNonNull(parent.getClient()).textRenderer,
                0, 0, 110, 20, null,
                Text.translatable(ConfigApi.toTranslationKey(this.configuration))
        );
        this.textWidget.setChangedListener(this::setValue);
    }

    @Override
    public void update() {
        this.textWidget.setText(this.value);
    }

    @Override
    public List<? extends ClickableWidget> getChildren() {
        return ImmutableList.of(this.textWidget);
    }

    @Override
    public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        renderNameAndInformation(context, x, y, entryHeight, mouseX, mouseY, tickDelta);

        this.textWidget.setX(x + OFFSET + 35);
        this.textWidget.setY(y);
        this.textWidget.render(context, mouseX, mouseY, tickDelta);
    }
}
