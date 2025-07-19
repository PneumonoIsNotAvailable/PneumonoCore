package net.pneumono.pneumonocore.config_api.entries;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.pneumono.pneumonocore.config_api.ConfigApi;
import net.pneumono.pneumonocore.config_api.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config_api.ConfigsListWidget;
import net.pneumono.pneumonocore.config_api.configurations.EnumConfiguration;

import java.util.List;

public class EnumConfigurationEntry<T extends Enum<T>> extends AbstractConfigurationEntry<T, EnumConfiguration<T>> {
    private final ButtonWidget cycleWidget;

    public EnumConfigurationEntry(ConfigOptionsScreen parent, ConfigsListWidget widget, EnumConfiguration<T> configuration) {
        super(parent, widget, configuration);

        this.cycleWidget = ButtonWidget.builder(
                getConfigName(), (button) -> this.setValue(cycle(this.value))
        ).dimensions(0, 0, 110, 20).build();

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

    @Override
    public void update() {
        this.cycleWidget.setMessage(Text.translatable(ConfigApi.toTranslationKey(this.configuration, this.value.name().toLowerCase())));
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
