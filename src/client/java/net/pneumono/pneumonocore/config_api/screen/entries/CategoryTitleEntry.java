package net.pneumono.pneumonocore.config_api.screen.entries;

import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.util.CommonColors;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;

import java.util.Objects;

//? if >=26.1 {
import net.minecraft.client.gui.GuiGraphicsExtractor;
//?} else {
/*import net.minecraft.client.gui.GuiGraphics;
*///?}

public class CategoryTitleEntry extends NonInteractableEntry {
    protected final String translationKey;
    private final boolean displaysForServer;
    private final boolean displaysForClient;

    public CategoryTitleEntry(ConfigOptionsScreen parent, String translationKey, boolean displaysForServer, boolean displaysForClient) {
        super(parent);
        this.translationKey = translationKey;
        this.displaysForServer = displaysForServer;
        this.displaysForClient = displaysForClient;
    }

    @Override
    public boolean shouldDisplay() {
        return this.parent.isViewingServer() ? this.displaysForServer : this.displaysForClient;
    }

    @Override
    public void displayContent(/*? if >=26.1 {*/GuiGraphicsExtractor/*?} else {*//*GuiGraphics*//*?}*/ graphics, int x, int y, int mouseX, int mouseY, int entryHeight, boolean hovered, float tickDelta) {
        Font font = Objects.requireNonNull(this.parent.getMinecraft()).font;
        graphics./*? if >=26.1 {*/centeredText/*?} else {*//*drawCenteredString*//*?}*/(
                font, Component.translatable(translationKey),
                x + (getRowEndXOffset() / 2),
                (y + entryHeight / 2) - 2,
                CommonColors.WHITE
        );
    }
}
