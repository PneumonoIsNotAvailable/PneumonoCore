package net.pneumono.pneumonocore.config_api.screen.entries;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.pneumono.pneumonocore.config_api.ClientConfigApi;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

//? if >=26.1 {
import net.minecraft.client.gui.GuiGraphicsExtractor;
//?} else {
/*import net.minecraft.client.gui.GuiGraphics;
 *///?}

/**
 * An entry in a config screen.
 *
 * <p>Entry types can be registered via {@link ClientConfigApi#registerConfigEntryType}
 */
public abstract class AbstractConfigListEntry extends ContainerObjectSelectionList.Entry<AbstractConfigListEntry> {
    protected final ConfigOptionsScreen parent;
    private final List<AbstractWidget> children = new ArrayList<>();

    public AbstractConfigListEntry(ConfigOptionsScreen parent) {
        this.parent = parent;
    }

    public int getRowEndXOffset() {
        return this.parent.configsList.getRowWidth() - 2;
    }

    public <T extends AbstractWidget> T addChild(T widget) {
        this.children.add(widget);
        return widget;
    }

    @Override
    public @NotNull List<? extends NarratableEntry> narratables() {
        return this.children;
    }

    @Override
    public @NotNull List<? extends GuiEventListener> children() {
        return this.children;
    }

    /**
     * Should set all buttons to the value from {@link AbstractConfigurationEntry#value}, if it extends that class.
     */
    public abstract void updateButtons();

    /**
     * @return Whether this entry should be visible in the entry list.
     */
    public abstract boolean shouldDisplay();

    @Override
    //? if >=26.1 {
    public void extractContent(GuiGraphicsExtractor graphics, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        int x = getX();
        int y = getY();
        int entryHeight = getContentHeight();
    //?} else if >=1.21.9 {
    /*public void renderContent(GuiGraphics graphics, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        int x = getX();
        int y = getY();
        int entryHeight = getContentHeight();
    *///?} else {
    /*public void render(GuiGraphics graphics, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
    *///?}
        displayContent(graphics, x, y, mouseX, mouseY, entryHeight, hovered, tickDelta);
    }

    public abstract void displayContent(/*? if >=26.1 {*/GuiGraphicsExtractor/*?} else {*//*GuiGraphics*//*?}*/ graphics, int x, int y, int mouseX, int mouseY, int entryHeight, boolean hovered, float tickDelta);
}
