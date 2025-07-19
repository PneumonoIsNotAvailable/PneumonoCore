package net.pneumono.pneumonocore.config_api.screen.entries;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.pneumono.pneumonocore.config_api.ConfigApi;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config_api.screen.widgets.ConfigsListWidget;
import net.pneumono.pneumonocore.config_api.configurations.EnumConfiguration;
import net.pneumono.pneumonocore.util.PneumonoCoreUtil;

import java.util.List;

public class EnumConfigurationEntry<T extends Enum<T>> extends AbstractConfigurationEntry<T, EnumConfiguration<T>> {
    private final ButtonWidget cycleWidget;

    public EnumConfigurationEntry(ConfigOptionsScreen parent, ConfigsListWidget widget, EnumConfiguration<T> configuration) {
        super(parent, widget, configuration);

        this.cycleWidget = ButtonWidget.builder(
                Text.literal(""), (button) -> this.setValue(PneumonoCoreUtil.cycleEnum(this.value))
        ).dimensions(0, 0, 110, 20).build();
    }

    @Override
    public void update() {
        this.cycleWidget.setMessage(Text.translatable(ConfigApi.toTranslationKey(this.configuration, this.value.name().toLowerCase())));
    }

    @Override
    public List<? extends ClickableWidget> getChildren() {
        return ImmutableList.of(this.cycleWidget);
    }

    public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        renderNameAndInformation(context, x, y, entryHeight, mouseX, mouseY, tickDelta);

        this.cycleWidget.setX(x + OFFSET + 35);
        this.cycleWidget.setY(y);
        this.cycleWidget.render(context, mouseX, mouseY, tickDelta);
    }
}
