package net.pneumono.pneumonocore.config_api.screen.entries;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.pneumono.pneumonocore.config_api.ConfigApi;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config_api.screen.widgets.ConfigsListWidget;
import net.pneumono.pneumonocore.config_api.configurations.TimeConfiguration;
import net.pneumono.pneumonocore.config_api.enums.TimeUnit;
import net.pneumono.pneumonocore.util.PneumonoCoreUtil;

import java.util.List;
import java.util.Objects;

public class TimeConfigurationEntry extends AbstractConfigurationEntry<Long, TimeConfiguration> {
    private final TextFieldWidget textWidget;
    private long amount;
    private final ButtonWidget cycleWidget;
    private TimeUnit units;

    public TimeConfigurationEntry(ConfigOptionsScreen parent, ConfigsListWidget widget, TimeConfiguration configuration) {
        super(parent, widget, configuration);

        this.amount = getAmount(this.value);
        this.units = TimeUnit.fromValue(this.value);

        this.textWidget = new TextFieldWidget(Objects.requireNonNull(parent.getClient()).textRenderer, 0, 0, 85, 20, null, Text.translatable(ConfigApi.toTranslationKey(this.configuration)));
        this.textWidget.setText(String.valueOf(this.amount));
        this.textWidget.setChangedListener((text) -> {
            try {
                setValue(Long.parseLong(text) * this.units.getDivision());
            } catch (NumberFormatException ignored) {
                // If the value in the text widget isn't valid, it just doesn't save it
            }
        });

        this.cycleWidget = ButtonWidget.builder(Text.literal(""), (button) -> {
            this.units = PneumonoCoreUtil.cycleEnum(this.units);
            setValue(this.amount * this.units.getDivision());
        }).dimensions(0, 0, 20, 20).build();

        this.update();
    }

    private long getAmount(long value) {
        return getAmount(value, TimeUnit.fromValue(value));
    }

    private long getAmount(long value, TimeUnit timeUnit) {
        return value / timeUnit.getDivision();
    }

    @Override
    public void update() {
        try {
            this.amount = Long.parseLong(this.textWidget.getText());
        } catch (NumberFormatException ignored) {}

        this.textWidget.setText(String.valueOf(this.amount));

        String key = "configs_screen.pneumonocore." + this.units.name().toLowerCase();
        this.cycleWidget.setMessage(Text.translatable(key));
        this.cycleWidget.setTooltip(Tooltip.of(Text.translatable(key + ".full")));
    }

    @Override
    public void reset() {
        this.amount = getAmount(configuration.info().getDefaultValue());
        this.units = TimeUnit.fromValue(configuration.info().getDefaultValue());
        super.reset();
    }

    @Override
    public List<? extends ClickableWidget> getChildren() {
        return ImmutableList.of(this.textWidget, this.cycleWidget);
    }

    @Override
    public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        renderNameAndInformation(context, x, y, entryHeight, mouseX, mouseY, tickDelta);

        this.textWidget.setX(x + OFFSET + 35);
        this.textWidget.setY(y);
        this.textWidget.render(context, mouseX, mouseY, tickDelta);

        this.cycleWidget.setX(x + OFFSET + 125);
        this.cycleWidget.setY(y);
        this.cycleWidget.render(context, mouseX, mouseY, tickDelta);
    }

    @Override
    public boolean shouldSaveValue() {
        try {
            Long.parseLong(this.textWidget.getText());
            return true;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }
}
