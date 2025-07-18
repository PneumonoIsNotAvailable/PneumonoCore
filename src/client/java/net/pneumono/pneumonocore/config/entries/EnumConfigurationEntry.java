package net.pneumono.pneumonocore.config.entries;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.pneumono.pneumonocore.config.AbstractConfiguration;
import net.pneumono.pneumonocore.config.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config.ConfigsListWidget;
import net.pneumono.pneumonocore.config.EnumConfiguration;

import java.util.List;
import java.util.function.Supplier;

public class EnumConfigurationEntry<T extends Enum<T>> extends AbstractConfigurationEntry<EnumConfiguration<T>> {
    private final ButtonWidget cycleWidget;
    private T value;

    @SuppressWarnings("unchecked")
    public EnumConfigurationEntry(AbstractConfiguration<?> abstractConfiguration, ConfigOptionsScreen parent, ConfigsListWidget widget) {
        super((EnumConfiguration<T>) abstractConfiguration, parent, widget);
        Supplier<T> configValueSupplier = configuration::getValue;
        this.value = configValueSupplier.get();
        this.cycleWidget = ButtonWidget.builder(configName, (button) -> {
            this.parent.selectedConfiguration = configuration;
            ConfigOptionsScreen.save(configuration.getModID(), configuration.getName(), cycle(configValueSupplier.get()));
            this.widget.update();
        }).dimensions(0, 0, 110, 20).build();
        this.update();
    }

    private T cycle(T value) {
        T[] enumConstants = value.getDeclaringClass().getEnumConstants();
        for (int i = 0; i < enumConstants.length; ++i) {
            T enumConstant = enumConstants[i];
            if (enumConstant == value) {
                return enumConstants[i + 1 >= enumConstants.length ? 0 : i + 1];
            }
        }
        return value;
    }

    public void update() {
        this.value = configuration.getValue();
        this.cycleWidget.setMessage(Text.translatable(this.configuration.getTranslationKey(value.name().toLowerCase())));
    }

    @Override
    public void reset() {
        ConfigOptionsScreen.save(configuration.getModID(), configuration.getName(), configuration.getDefaultValue());
        update();
    }

    @Override
    public List<? extends Selectable> selectableChildren() {
        return ImmutableList.of(this.cycleWidget);
    }

    @Override
    public List<? extends Element> children() {
        return ImmutableList.of(this.cycleWidget);
    }

    public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        renderNameAndInformation(context, x, y, entryHeight, mouseX, mouseY, tickDelta);
        this.cycleWidget.setX(x + OFFSET + 35);
        this.cycleWidget.setY(y);

        this.cycleWidget.render(context, mouseX, mouseY, tickDelta);
    }
}
