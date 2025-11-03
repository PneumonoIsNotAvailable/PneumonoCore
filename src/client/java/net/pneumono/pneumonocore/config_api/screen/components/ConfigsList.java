package net.pneumono.pneumonocore.config_api.screen.components;

import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.resources.ResourceLocation;
import net.pneumono.pneumonocore.config_api.ClientConfigApi;
import net.pneumono.pneumonocore.config_api.ConfigApi;
import net.pneumono.pneumonocore.config_api.ConfigFile;
import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config_api.screen.entries.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigsList extends ContainerObjectSelectionList<AbstractConfigListEntry> {
    private static final String MISC = "misc";

    protected final ConfigOptionsScreen parentScreen;
    public final ConfigFile configFile;
    private final Map<String, List<AbstractConfiguration<?>>> categorizedConfigs;
    private List<AbstractConfigListEntry> entries;

    public ConfigsList(ConfigOptionsScreen parentScreen) {
        //? if >=1.20.3 {
        super(parentScreen.getMinecraft(), parentScreen.width, parentScreen.getContentHeight(), parentScreen.getHeaderHeight(), 20);
        //?} else {
        /*super(parentScreen.getMinecraft(), parentScreen.width, parentScreen.getContentHeight(), parentScreen.getHeaderHeight(), parentScreen.getContentHeight() + parentScreen.getHeaderHeight(), 20);
        *///?}

        this.parentScreen = parentScreen;
        this.configFile = ConfigApi.getConfigFile(this.parentScreen.modId);

        this.categorizedConfigs = new HashMap<>();
        List<AbstractConfiguration<?>> configurations = this.configFile == null ? new ArrayList<>() : this.configFile.getConfigurations();
        for (AbstractConfiguration<?> configuration : configurations) {
            this.categorizedConfigs.computeIfAbsent(configuration.info().getCategory(), string -> new ArrayList<>()).add(configuration);
        }
        if (this.categorizedConfigs.containsKey(MISC)) {
            this.categorizedConfigs.put(MISC, this.categorizedConfigs.remove(MISC));
        }
    }

    public void init() {
        this.entries = initEntryList();
        this.updateEntryList();
        this.updateEntryValues();
        this.setScrollAmount(0);
    }

    public List<AbstractConfigListEntry> initEntryList() {
        List<AbstractConfigListEntry> newEntries = new ArrayList<>();

        if (this.categorizedConfigs.isEmpty()) {
            newEntries.add(new NoConfigsEntry(this.parentScreen));
            return newEntries;
        }

        for (Map.Entry<String, List<AbstractConfiguration<?>>> categorizedConfig : this.categorizedConfigs.entrySet()) {
            if (this.categorizedConfigs.size() > 1) {

                String translationKey;
                if (categorizedConfig.getKey().equals(MISC)) {
                    translationKey = "configs.category.pneumonocore." + MISC;
                } else {
                    translationKey = "configs.category." + this.configFile.getModId() + "." + categorizedConfig.getKey();
                }
                newEntries.add(new CategoryTitleEntry(
                        this.parentScreen,
                        translationKey
                ));
            }

            for (AbstractConfiguration<?> configuration : categorizedConfig.getValue()) {

                AbstractConfigurationEntry<?, ?> entry = ClientConfigApi
                        .getConfigEntryType(configuration.info().getConfigTypeId())
                        .build(this.parentScreen, this, configuration);
                if (entry == null) {
                    entry = new ErroneousConfigurationEntry<>(this.parentScreen, this, configuration);
                }
                newEntries.add(entry);
            }
        }

        return newEntries;
    }

    public void updateEntryList() {
        this.replaceEntries(this.entries.stream().filter(AbstractConfigListEntry::shouldDisplay).toList());
    }

    public void updateEntryValues() {
        this.entries.forEach(AbstractConfigListEntry::updateButtons);
    }

    public List<AbstractConfigListEntry> getEntries() {
        return entries;
    }

    public AbstractConfigurationEntry<?, ?> getEntry(ResourceLocation id) {
        for (AbstractConfigListEntry entry : this.entries) {
            if (!(entry instanceof AbstractConfigurationEntry<?,?> configEntry)) continue;
            if (configEntry.getConfiguration().info().getId().equals(id)) {
                return configEntry;
            }
        }
        return null;
    }

    //? if <1.20.5 {
    /*//? if >=1.20.5 {
    @Override
    protected int getScrollbarPositionX() {
        return super.getScrollbarPositionX() + 52;
    }
    //?} else {
    /^@Override
    protected int getScrollbarPosition() {
        return super.getScrollbarPosition() + 52;
    }
    ^///?}
    *///?}

    @Override
    public int getRowWidth() {
        return super.getRowWidth() + 120;
    }
}
