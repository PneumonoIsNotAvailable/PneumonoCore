package net.pneumono.pneumonocore.config.entries;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.pneumono.pneumonocore.config.*;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class TimeConfigurationEntry extends AbstractConfigurationEntry<TimeConfiguration> {
    private final TextFieldWidget textWidget;
    private long amount;
    private final ButtonWidget cycleWidget;
    private TimeUnit units;

    public TimeConfigurationEntry(AbstractConfiguration<?> abstractConfiguration, ConfigOptionsScreen parent, ConfigsListWidget widget) {
        super((TimeConfiguration) abstractConfiguration, parent, widget);
        Supplier<Long> configValueSupplier = configuration::getValue;

        this.amount = getAmount(configValueSupplier.get());
        this.textWidget = new TextFieldWidget(Objects.requireNonNull(parent.getClient()).textRenderer, 0, 0, 85, 20, null, Text.translatable(configuration.getTranslationKey()));
        this.textWidget.setText(String.valueOf(amount));
        this.textWidget.setChangedListener((text) -> {
            this.parent.selectedConfiguration = configuration;

            try {
                long newValue = Long.parseLong(text) * units.getDivision();
                ConfigOptionsScreen.save(configuration.getModID(), configuration.getName(), newValue);
            } catch (NumberFormatException ignored) {
                // If the value in the text widget isn't valid, it just doesn't save it
            }
        });

        this.units = TimeUnit.fromValue(configValueSupplier.get());
        Supplier<Long> amountSupplier = () -> this.amount;
        this.cycleWidget = ButtonWidget.builder(configName, (button) -> {
            this.parent.selectedConfiguration = configuration;

            this.units = cycle(units);
            long newValue = amountSupplier.get() * units.getDivision();
            ConfigOptionsScreen.save(configuration.getModID(), configuration.getName(), newValue);

            this.widget.update();
        }).dimensions(0, 0, 20, 20).build();
        this.update();
    }

    private long getAmount(long value) {
        return getAmount(value, TimeUnit.fromValue(value));
    }

    private long getAmount(long value, TimeUnit timeUnit) {
        return value / timeUnit.getDivision();
    }

    private TimeUnit cycle(TimeUnit value) {
        TimeUnit[] enumConstants = value.getDeclaringClass().getEnumConstants();
        for (int i = 0; i < enumConstants.length; ++i) {
            TimeUnit enumConstant = enumConstants[i];
            if (enumConstant == value) {
                return enumConstants[i + 1 >= enumConstants.length ? 0 : i + 1];
            }
        }
        return value;
    }

    @Override
    public void update() {
        try {
            this.amount = Long.parseLong(this.textWidget.getText());
        } catch (NumberFormatException ignored) {}

        String key = "configs_screen.pneumonocore." + units.name().toLowerCase();
        this.cycleWidget.setMessage(Text.translatable(key));
        this.cycleWidget.setTooltip(Tooltip.of(Text.translatable(key + ".full")));
    }

    @Override
    public void reset() {
        this.amount = getAmount(configuration.getDefaultValue());
        this.textWidget.setText(String.valueOf(amount));
        this.units = TimeUnit.fromValue(configuration.getDefaultValue());
        ConfigOptionsScreen.save(configuration.getModID(), configuration.getName(), configuration.getDefaultValue());
        this.update();
    }

    @Override
    public List<? extends Selectable> selectableChildren() {
        return ImmutableList.of(this.textWidget, this.cycleWidget);
    }

    @Override
    public List<? extends Element> children() {
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
}
