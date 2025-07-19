package net.pneumono.pneumonocore.config_api.screen.entries;

import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;

import java.util.List;

public abstract class AbstractConfigListWidgetEntry extends ElementListWidget.Entry<AbstractConfigListWidgetEntry> {
    // Exists so that if I need to adjust the positions of everything in the future, I can just change this.
    protected static final int OFFSET = 100;

    protected final ConfigOptionsScreen parent;

    public AbstractConfigListWidgetEntry(ConfigOptionsScreen parent) {
        this.parent = parent;
    }

    @Override
    public List<? extends Selectable> selectableChildren() {
        return getChildren();
    }

    @Override
    public List<? extends Element> children() {
        return getChildren();
    }

    public abstract List<? extends ClickableWidget> getChildren();

    public abstract void update();

    public abstract void reset();

    public abstract boolean shouldDisplay();
}
