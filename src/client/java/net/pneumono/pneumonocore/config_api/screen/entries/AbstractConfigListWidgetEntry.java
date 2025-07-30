package net.pneumono.pneumonocore.config_api.screen.entries;

import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.pneumono.pneumonocore.config_api.ClientConfigApi;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;

import java.util.ArrayList;
import java.util.List;

/**
 * An entry in a config screen.
 *
 * <p>Entry types can be registered via {@link ClientConfigApi#registerConfigEntryType}
 */
public abstract class AbstractConfigListWidgetEntry extends ElementListWidget.Entry<AbstractConfigListWidgetEntry> {
    protected final ConfigOptionsScreen parent;
    private final List<ClickableWidget> children = new ArrayList<>();

    public AbstractConfigListWidgetEntry(ConfigOptionsScreen parent) {
        this.parent = parent;
    }

    public int getRowEndXOffset() {
        return this.parent.configsListWidget.getRowWidth() - 2;
    }

    public <T extends ClickableWidget> T addChild(T widget) {
        this.children.add(widget);
        return widget;
    }

    @Override
    public List<? extends Selectable> selectableChildren() {
        return this.children;
    }

    @Override
    public List<? extends Element> children() {
        return this.children;
    }

    /**
     * Should set all widgets to the value from {@link AbstractConfigurationEntry#value}, if it extends that class.
     */
    public abstract void updateWidgets();

    /**
     * @return Whether this entry should be visible in the entry list.
     */
    public abstract boolean shouldDisplay();
}
