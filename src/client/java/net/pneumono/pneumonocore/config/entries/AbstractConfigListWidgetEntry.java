package net.pneumono.pneumonocore.config.entries;

import net.minecraft.client.gui.widget.ElementListWidget;

public abstract class AbstractConfigListWidgetEntry extends ElementListWidget.Entry<AbstractConfigListWidgetEntry> {
    public abstract void update();

    public abstract void reset();
}
