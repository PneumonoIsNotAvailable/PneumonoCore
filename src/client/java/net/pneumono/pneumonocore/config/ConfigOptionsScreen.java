package net.pneumono.pneumonocore.config;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.pneumono.pneumonocore.config.entries.AbstractConfigurationEntry;

import java.util.Objects;

public class ConfigOptionsScreen extends Screen {
    private final Screen parent;
    public final String modID;
    private ConfigsListWidget configsList;
    public AbstractConfiguration<?> selectedConfiguration;

    public ConfigOptionsScreen(Screen parent, String modID) {
        super(Text.translatable("configs." + modID + ".screen_title"));
        this.parent = parent;
        this.modID = modID;
    }

    @Override
    protected void init() {
        this.configsList = new ConfigsListWidget(this, this.client);
        this.addSelectableChild(this.configsList);

        this.addDrawableChild(ButtonWidget.builder(Text.translatable("configs_screen.pneumonocore.reset_all"), (button) -> {
            for(AbstractConfigurationEntry entry : configsList.children()) {
                entry.reset();
            }

            this.configsList.update();
        }).dimensions(this.width / 2 - 154, this.height - 28, 150, 20).build());

        this.addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, button -> Objects.requireNonNull(this.client).setScreen(this.parent)).dimensions(this.width / 2 + 4, this.height - 28, 150, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.configsList.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 8, 0xffffff);
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
