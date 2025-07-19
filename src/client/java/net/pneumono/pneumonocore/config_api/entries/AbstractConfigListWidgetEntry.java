package net.pneumono.pneumonocore.config_api.entries;

import net.minecraft.client.gui.widget.ElementListWidget;

public abstract class AbstractConfigListWidgetEntry extends ElementListWidget.Entry<AbstractConfigListWidgetEntry> {
    // Exists so that if I need to adjust the positions of everything in the future, I can just change this.
    protected static final int OFFSET = 100;

    public abstract void update();

    public abstract void reset();

    public abstract boolean shouldDisplay();
}
