package net.pneumono.pneumonocore.config.entries;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.pneumono.pneumonocore.config.AbstractConfiguration;
import net.pneumono.pneumonocore.config.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config.ConfigsListWidget;
import net.pneumono.pneumonocore.config.StringConfiguration;

import java.util.List;
import java.util.Objects;

public class StringConfigurationEntry extends AbstractConfigurationEntry<StringConfiguration> {
    private final TextFieldWidget textWidget;
    private String value;

    public StringConfigurationEntry(AbstractConfiguration<?> abstractConfiguration, ConfigOptionsScreen parent, ConfigsListWidget widget) {
        super((StringConfiguration) abstractConfiguration, parent, widget);
        this.value = configuration.getValue();
        this.textWidget = new TextFieldWidget(Objects.requireNonNull(parent.getClient()).textRenderer, 0, 0, 110, 20, null, Text.translatable(configuration.getTranslationKey()));
        this.textWidget.setText(value);
        this.textWidget.setChangedListener((text) -> {
            this.parent.selectedConfiguration = configuration;
            ConfigOptionsScreen.save(configuration.getModID(), configuration.getName(), text);
        });
    }

    @Override
    public void update() {
        String newValue = configuration.getValue();
        this.value = newValue;
        this.textWidget.setText(newValue);
    }

    @Override
    public void reset() {
        ConfigOptionsScreen.save(configuration.getModID(), configuration.getName(), configuration.getDefaultValue());
        update();
    }

    @Override
    public List<? extends Selectable> selectableChildren() {
        return ImmutableList.of(textWidget);
    }

    @Override
    public List<? extends Element> children() {
        return ImmutableList.of(textWidget);

    }

    @Override
    public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        renderNameAndInformation(context, x, y, entryHeight, mouseX, mouseY, tickDelta);
        this.textWidget.setX(x + OFFSET + 35);
        this.textWidget.setY(y);

        this.textWidget.render(context, mouseX, mouseY, tickDelta);
    }
}
