package net.pneumono.pneumonocore.config_api.screen.entries;

import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;

/**
 * A screen entry that can't be interacted with, such as {@linkplain CategoryTitleEntry category titles} or the {@linkplain NoConfigsEntry "This mod has no configs!"} message.
 */
public abstract class NonInteractableEntry extends AbstractConfigListWidgetEntry {
    public NonInteractableEntry(ConfigOptionsScreen parent) {
        super(parent);
    }

    @Override
    public void updateWidgets() {

    }

    @Override
    public boolean shouldDisplay() {
        return true;
    }
}
