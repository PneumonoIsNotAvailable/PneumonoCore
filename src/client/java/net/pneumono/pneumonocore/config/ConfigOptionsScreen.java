package net.pneumono.pneumonocore.config;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.pneumono.pneumonocore.config.entries.AbstractConfigListWidgetEntry;
import net.pneumono.pneumonocore.config.entries.AbstractConfigurationEntry;

import java.util.Objects;

public class ConfigOptionsScreen extends GameOptionsScreen {
    public final String modID;
    private ConfigsListWidget configsList;
    public AbstractConfiguration<?> selectedConfiguration;

    public ConfigOptionsScreen(Screen parent, String modID) {
        super(parent, null, Text.translatable("configs." + modID + ".screen_title"));
        this.modID = modID;
    }

    @Override
    protected void init() {
        super.init();
        initBody();
        initFooter();
    }

    protected void initBody() {
        this.configsList = this.addSelectableChild(new ConfigsListWidget(this, this.client));
    }

    protected void initFooter() {
        ButtonWidget resetAllButton = ButtonWidget.builder(Text.translatable("configs_screen.pneumonocore.reset_all"), (button) -> {
            for(AbstractConfigListWidgetEntry entry : configsList.children()) {
                if (entry instanceof AbstractConfigurationEntry configurationEntry) {
                    configurationEntry.reset();
                }
            }

            this.configsList.update();
        }).build();

        this.addSelectableChild(resetAllButton);
        this.addSelectableChild(ButtonWidget.builder(ScreenTexts.DONE, button -> this.close()).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
    }

    public static <T> void save(String modID, String name, T newValue) {
        ModConfigurations modConfigs = Configs.CONFIGS.get(modID);
        if (modConfigs != null) {
            for (AbstractConfiguration<?> config : modConfigs.configurations) {
                if (Objects.equals(config.name, name)) {
                    config.setLoadedValue(newValue);
                }
            }
            modConfigs.writeConfigs(modConfigs.configurations, false);
        }
    }

    public MinecraftClient getClient() {
        return this.client;
    }
}
