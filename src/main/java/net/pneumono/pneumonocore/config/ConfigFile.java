package net.pneumono.pneumonocore.config;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.MapCodec;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import net.pneumono.pneumonocore.config.configuration.AbstractConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class ConfigFile {
    public static final Logger LOGGER = LoggerFactory.getLogger("PneumonoCoreConfig");

    private final String modId;
    private final File file;
    private final Map<String, AbstractConfiguration<?>> configs = new HashMap<>();
    private JsonObject json = null;

    public ConfigFile(String modId) {
        this.modId = modId;
        this.file = new File(FabricLoader.getInstance().getConfigDir().toFile(), modId + ".json");
    }

    public <T> void register(Identifier id, AbstractConfiguration<T> configuration) {
        this.configs.put(id.getPath(), configuration);
        T value = getSavedValue(id, configuration.getValueCodec().fieldOf(id.getPath()), configuration::canAccept, configuration.getDefaultValue());
        configuration.setSavedValue(value);
        configuration.setLoadedValue(value);
    }

    public <T> T getSavedValue(Identifier id, MapCodec<T> mapCodec, Predicate<T> predicate, T defaultValue) {
        DataResult<Pair<T, JsonElement>> result = mapCodec.codec().decode(JsonOps.INSTANCE, getSavedValues());
        if (result.isError()) {
            LOGGER.warn("Failed to decode config value for config {}", id.toString());
            return defaultValue;
        }

        T value = result.result().map(Pair::getFirst).filter(predicate).orElse(defaultValue);
        if (!predicate.test(value)) {
            value = defaultValue;
            LOGGER.warn("Config value {} does not pass validation checks for config {}", value.toString(), id.toString());
        }
        return value;
    }

    public JsonObject getSavedValues() {
        if (this.json != null) return this.json;

        return readConfigFile();
    }

    public JsonObject readConfigFile() {
        JsonObject jsonObject = null;

        try (Reader reader = Files.newBufferedReader(this.file.toPath())) {
            jsonObject = new GsonBuilder().setPrettyPrinting().create().fromJson(reader, JsonObject.class);
        } catch (IOException e) {
            LOGGER.warn("Failed to read config file for mod {}!", this.modId);
        }

        this.json = jsonObject;
        return jsonObject;
    }

    public void saveValues() {
        JsonObject jsonObject = new JsonObject();
        for (Map.Entry<String, AbstractConfiguration<?>> entry : configs.entrySet()) {

            AbstractConfiguration<?> configuration = entry.getValue();
            DataResult<JsonElement> result = encodeValue(configuration);
            if (result.isError()) {
                LOGGER.warn("Failed to encode config value {} for config {}", configuration.get().toString(), configuration.getId().getNamespace());
                continue;
            }

            jsonObject.add(entry.getKey(), result.getOrThrow());
        }
        writeConfigFile(jsonObject);
    }

    private <T> DataResult<JsonElement> encodeValue(AbstractConfiguration<T> configuration) {
        return configuration.getValueCodec().encodeStart(JsonOps.INSTANCE, configuration.get());
    }

    private void writeConfigFile(JsonObject jsonObject) {
        this.json = jsonObject;

        try (Writer writer = Files.newBufferedWriter(this.file.toPath())) {
            new GsonBuilder().setPrettyPrinting().create().toJson(this.json, writer);
        } catch (IOException e) {
            LOGGER.warn("Failed to write config file for mod {}!", this.modId);
        }
    }
}
