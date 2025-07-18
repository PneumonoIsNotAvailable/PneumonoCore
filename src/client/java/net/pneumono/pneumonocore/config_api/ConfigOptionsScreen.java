package net.pneumono.pneumonocore.config_api;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.DirectionalLayoutWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.configurations.ConfigManager;
import net.pneumono.pneumonocore.config_api.entries.AbstractConfigListWidgetEntry;
import net.pneumono.pneumonocore.config_api.entries.AbstractConfigurationEntry;

public class ConfigOptionsScreen extends GameOptionsScreen {
    public final String modID;
    private ConfigsListWidget configsList;
    public AbstractConfiguration<?> selectedConfiguration;

    public ConfigOptionsScreen(Screen parent, String modID) {
        super(parent, null, Text.translatable("configs." + modID + ".screen_title"));
        this.modID = modID;
    }

    @Override
    protected void initBody() {
        this.configsList = this.layout.addBody(new ConfigsListWidget(this, this.client));
    }

    @Override
    protected void addOptions() {

    }

    @Override
    protected void initFooter() {
        ButtonWidget resetAllButton = ButtonWidget.builder(Text.translatable("configs_screen.pneumonocore.reset_all"), (button) -> {
            for (AbstractConfigListWidgetEntry entry : configsList.children()) {
                if (entry instanceof AbstractConfigurationEntry<?> configurationEntry) {
                    configurationEntry.reset();
                }
            }

            this.configsList.update();
        }).build();

        DirectionalLayoutWidget directionalLayoutWidget = this.layout.addFooter(DirectionalLayoutWidget.horizontal().spacing(8));
        directionalLayoutWidget.add(resetAllButton);
        directionalLayoutWidget.add(ButtonWidget.builder(ScreenTexts.DONE, button -> this.close()).build());
    }

    @Override
    protected void refreshWidgetPositions() {
        this.layout.refreshPositions();
        if (this.configsList != null) {
            this.configsList.position(this.width, this.layout);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
    }

    public static <T> void save(String modID, String name, T newValue) {
        ConfigFile configFile = ConfigApi.getConfigFile(modID);
        if (configFile != null) {
            AbstractConfiguration<?> config = configFile.getConfiguration(name);
            if (setValue(config, newValue)) {
                configFile.writeToFile();
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> boolean setValue(AbstractConfiguration<T> config, Object value) {
        try {
            ConfigManager.setLoadedValue(config, (T)value);
        } catch (ClassCastException e) {
            ConfigApi.LOGGER.warn("Could not save value '{}' for config '{}'", value, config.getId());
            return false;
        }
        return true;
    }

    public MinecraftClient getClient() {
        return this.client;
    }
}
