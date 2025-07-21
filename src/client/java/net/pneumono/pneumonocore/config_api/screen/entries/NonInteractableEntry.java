package net.pneumono.pneumonocore.config_api.screen.entries;

import net.minecraft.client.gui.widget.ClickableWidget;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;

import java.util.List;

/**
 * A screen entry that can't be interacted with, such as {@linkplain CategoryTitleEntry category titles} or the {@linkplain NoConfigsEntry "This mod has no configs!"} message.
 */
public abstract class NonInteractableEntry extends AbstractConfigListWidgetEntry {
    public NonInteractableEntry(ConfigOptionsScreen parent) {
        super(parent);
    }

    @Override
    public List<? extends ClickableWidget> getChildren() {
        return List.of();
    }

    @Override
    public void updateWidgets() {

    }

    @Override
    public void reset() {

    }

    @Override
    public boolean shouldDisplay() {
        return true;
    }
}
