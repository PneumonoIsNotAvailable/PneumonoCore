package net.pneumono.pneumonocore.config_api.screen.widgets;

//? if <=1.20.1 {
/*import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class TextIconButtonWidget extends TexturedButtonWidget {
    public TextIconButtonWidget(
            int x,
            int y,
            int width,
            int height,
            int u,
            int v,
            int hoveredVOffset,
            Identifier texture,
            int textureWidth,
            int textureHeight,
            ButtonWidget.PressAction pressAction,
            Text message
    ) {
        super(x, y, width, height, u, v, hoveredVOffset, texture, textureWidth, textureHeight, pressAction, message);
    }

    @SuppressWarnings("unused")
    public static TextIconButtonWidget.Builder builder(Text text, ButtonWidget.PressAction onPress, boolean hideLabel) {
        return new TextIconButtonWidget.Builder(text, onPress);
    }

    public static class Builder {
        private final Text message;
        private final ButtonWidget.PressAction onPress;
        private int width = 150;
        private Identifier texture;
        private int textureWidth;
        private int textureHeight;

        public Builder(Text message, ButtonWidget.PressAction onPress) {
            this.message = message;
            this.onPress = onPress;
        }

        public Builder texture(Identifier texture, int width, int height) {
            this.texture = texture;
            this.textureWidth = width;
            this.textureHeight = height;
            return this;
        }

        public Builder width(int width) {
            this.width = width;
            return this;
        }

        public TextIconButtonWidget build() {
            int height = 20;
            return new TextIconButtonWidget(
                    0, 0,
                    this.width, height,
                    0, 0,
                    height,
                    this.texture, this.textureWidth, this.textureHeight,
                    this.onPress, this.message
            );
        }
    }
}
*///?}