package net.pneumono.pneumonocore.config_api.screen.entries;

import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.pneumono.pneumonocore.config_api.ClientConfigApi;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;

import java.util.List;

/**
 * An entry in a config screen.
 *
 * <p>Entry types can be registered via {@link ClientConfigApi#registerConfigEntryType}
 */
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

    /**
     * @return A list of this entry's widgets.
     */
    public abstract List<? extends ClickableWidget> getChildren();

    /**
     * Should set all widgets to the value from {@link AbstractConfigurationEntry#value}, if it extends that class.
     */
    public abstract void updateWidgets();

    /**
     * Should set all fields that are connected to {@link AbstractConfigurationEntry#value} to their default value.
     */
    public abstract void reset();

    /**
     * @return Whether this entry should be visible in the entry list.
     */
    public abstract boolean shouldDisplay();
}
