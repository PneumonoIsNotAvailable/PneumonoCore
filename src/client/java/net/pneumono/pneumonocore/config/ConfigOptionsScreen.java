package net.pneumono.pneumonocore.config;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.OptionListWidget;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConfigOptionsScreen extends GameOptionsScreen {
    private final String modID;
    private final Screen previous;
    private OptionListWidget list;
    private final List<StoredConfigValue<?>> storedValues = new ArrayList<>();

    public ConfigOptionsScreen(Screen previous, String modID) {
        super(previous, MinecraftClient.getInstance().options, Text.translatable(modID + ".configs_screen.reset"));
        this.previous = previous;
        this.modID = modID;
    }


    protected void init() {
        this.list = new OptionListWidget(this.client, this.width, this.height, 32, this.height - 32, 25);
        this.list.addAll(asOptions());
        this.addSelectableChild(this.list);

        this.addDrawableChild(ButtonWidget.builder(Text.translatable("pneumonocore.configs_screen.reset"), (button) -> {
            for (ModConfigurations modConfigs : Configs.CONFIGS) {
                if (Objects.equals(modConfigs.modID, modID)) {
                    for (AbstractConfiguration<?> config : modConfigs.configurations) {
                        storedValues.add(new StoredConfigValue<>(config.modID, config.name, config.getDefaultValue()));
                    }
                }
            }
        }).dimensions(this.width / 2 - 155, this.height - 29, 150, 20).build());

        this.addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, (button) -> {
            save();
            this.client.setScreen(this.previous);
        }).dimensions(this.width / 2 + 5, this.height - 29, 150, 20).build());
    }

    @Override
    public void render(DrawContext DrawContext, int mouseX, int mouseY, float delta) {
        super.render(DrawContext, mouseX, mouseY, delta);
        this.list.render(DrawContext, mouseX, mouseY, delta);
        DrawContext.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 5, 0xffffff);
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackgroundTexture(context);
    }

    public void removed() {
        save();
    }

    private void save() {
        for (ModConfigurations modConfigs : Configs.CONFIGS) {
            if (Objects.equals(modConfigs.modID, modID)) {
                storedValues:
                for (StoredConfigValue<?> storedValue : storedValues) {
                    for (AbstractConfiguration<?> config : modConfigs.configurations) {
                        if (Objects.equals(config.name, storedValue.name)) {
                            config.setLoadedValue(storedValue.newValue);
                            continue storedValues;
                        }
                    }
                }
                modConfigs.writeConfigs(modConfigs.configurations, false);
            }
        }
    }

    private SimpleOption<?>[] asOptions() {
        ArrayList<SimpleOption<?>> options = new ArrayList<>();
        for (ModConfigurations modConfigs : Configs.CONFIGS) {
            if (Objects.equals(modConfigs.modID, modID)) {
                for (AbstractConfiguration<?> config : modConfigs.configurations) {
                    options.add(asOption(config));
                }
                break;
            }
        }
        return options.toArray(SimpleOption[]::new);
    }

    private SimpleOption<?> asOption(AbstractConfiguration<?> config) {
        if (config instanceof BooleanConfiguration booleanConfig) {

            return new SimpleOption<>(getConfigKey(config.modID, config.name), SimpleOption.emptyTooltip(),
                    (text, value) -> Text.of(value.toString()),
                    SimpleOption.BOOLEAN,
                    booleanConfig.getLoadedValue(),
                    newValue -> storedValues.add(new StoredConfigValue<>(config.modID, config.name, newValue)));

        } else if (config instanceof IntegerConfiguration intConfig) {

            return new SimpleOption<>(getConfigKey(config.modID, config.name), SimpleOption.emptyTooltip(),
                    (text, value) -> Text.translatable(getConfigKey(config.modID, config.name)).append(Text.of(": " + value.toString())),
                    new SimpleOption.ValidatingIntSliderCallbacks(intConfig.getMinValue(), intConfig.getMaxValue()),
                    intConfig.getLoadedValue(),
                    newValue -> storedValues.add(new StoredConfigValue<>(config.modID, config.name, newValue)));

        } else {
            return null;
        }
    }

    private String getConfigKey(String modID, String name) {
        return modID + ".configs." + name;
    }

    private record StoredConfigValue<T>(String modID, String name, T newValue) {}
}
