package net.pneumono.pneumonocore.config;

import com.google.gson.JsonElement;
import net.minecraft.nbt.NbtElement;

@SuppressWarnings("unused")
public class InputValidatedStringConfiguration extends AbstractConfiguration<String> {
    private final InputValidator check;

    /**
     * Creates a new input validated string configuration. Register using {@link Configs#register(AbstractConfiguration)}.<p>
     * Configuration names use the translation key {@code "configs.<modID>.<name>"} in config menus.<p>
     * Input-validated string configurations have a check to make sure the value inputted is acceptable, and if it is not it will use the default value. If configured using the config screen, an error is shown to the user.
     *
     * @param modID The mod ID of the mod registering the configuration.
     * @param name The name of the configuration.
     * @param environment Whether the configuration is server-side (e.g. gameplay features) or client-side (e.g. visual settings).
     * @param check The check an inputted string must pass.
     * @param defaultValue The default value of the configuration.
     */
    @SuppressWarnings("unused")
    public InputValidatedStringConfiguration(String modID, String name, ConfigEnv environment, InputValidator check, String defaultValue) {
        super(modID, name, environment, defaultValue);
        this.check = check;
    }

    private InputValidatedStringConfiguration(String modID, String name, ConfigEnv environment, InputValidator check, String defaultValue, String loadedValue) {
        super(modID, name, environment, defaultValue, loadedValue);
        this.check = check;
    }

    @Override
    public InputValidatedStringConfiguration fromElement(NbtElement element) {
        return new InputValidatedStringConfiguration(modID, name, environment, check, getDefaultValue(), element.asString());
    }

    @Override
    protected InputValidatedStringConfiguration fromElement(JsonElement element) {
        return new InputValidatedStringConfiguration(modID, name, environment, check, getDefaultValue(), element.getAsString());
    }


    @Override
    public String getValue() {
        String value = super.getValue();
        return check.isValid(value) ? value : getDefaultValue();
    }

    @SuppressWarnings("unused")
    public InputValidator getCheck() {
        return check;
    }

    @Override
    public String getClassID() {
        return "InputValidatedStringConfiguration";
    }

    public interface InputValidator {
        boolean isValid(String input);
    }
}
