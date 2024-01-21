package net.pneumono.pneumonocore.config.entries;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.pneumono.pneumonocore.config.AbstractConfiguration;
import net.pneumono.pneumonocore.config.BooleanConfiguration;
import net.pneumono.pneumonocore.config.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config.ConfigsListWidget;

import java.util.List;
import java.util.function.Supplier;

public class BooleanConfigurationEntry extends AbstractConfigurationEntry {
    private final ButtonWidget toggleWidget;
    private boolean value;

    public BooleanConfigurationEntry(AbstractConfiguration<?> configuration, ConfigOptionsScreen parent, ConfigsListWidget widget) {
        super(configuration, parent, widget);
        Supplier<Boolean> configValueSupplier = () -> configuration instanceof BooleanConfiguration booleanConfiguration && booleanConfiguration.getValue();
        this.value = configValueSupplier.get();
        this.toggleWidget = ButtonWidget.builder(configName, (button) -> {
            this.parent.selectedConfiguration = configuration;
            ConfigOptionsScreen.save(configuration.getModID(), configuration.getName(), !configValueSupplier.get());
            this.widget.update();
        }).dimensions(0, 0, 110, 20).build();
        this.update();
    }

    public void update() {
        this.value = configuration instanceof BooleanConfiguration booleanConfiguration && booleanConfiguration.getValue();
        this.toggleWidget.setMessage(Text.translatable(value ? "pneumonocore.configs_screen.boolean_enabled" : "pneumonocore.configs_screen.boolean_disabled"));
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
        this.toggleWidget.setX(x + 90);
        this.toggleWidget.setY(y);

        this.toggleWidget.render(context, mouseX, mouseY, tickDelta);
    }
}
