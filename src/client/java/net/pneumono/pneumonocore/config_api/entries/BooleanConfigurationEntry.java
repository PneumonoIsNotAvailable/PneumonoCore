package net.pneumono.pneumonocore.config_api.entries;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config_api.ConfigsListWidget;
import net.pneumono.pneumonocore.config_api.configurations.BooleanConfiguration;

import java.util.List;

public class BooleanConfigurationEntry extends AbstractConfigurationEntry<Boolean, BooleanConfiguration> {
    private final ButtonWidget toggleWidget;

    public BooleanConfigurationEntry(ConfigOptionsScreen parent, ConfigsListWidget widget, BooleanConfiguration configuration) {
        super(parent, widget, configuration);

        this.toggleWidget = ButtonWidget.builder(
                getConfigName(), (button) -> this.setValue(!this.value)
        ).dimensions(0, 0, 110, 20).build();

        this.update();
    }

    @Override
    public void update() {
        this.toggleWidget.setMessage(Text.translatable(
                this.value ? "configs_screen.pneumonocore.boolean_enabled" :
                        "configs_screen.pneumonocore.boolean_disabled"
        ));
    }

    @Override
    public void reset() {
        super.reset();

    }

    @Override
    public List<? extends Selectable> selectableChildren() {
        return ImmutableList.of(this.toggleWidget);
    }

    @Override
    public List<? extends Element> children() {
        return ImmutableList.of(this.toggleWidget);
    }

    public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        renderNameAndInformation(context, x, y, entryHeight, mouseX, mouseY, tickDelta);

        this.toggleWidget.setX(x + OFFSET + 35);
        this.toggleWidget.setY(y);
        this.toggleWidget.render(context, mouseX, mouseY, tickDelta);
    }
}
