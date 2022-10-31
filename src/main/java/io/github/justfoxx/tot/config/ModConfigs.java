package io.github.justfoxx.tot.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.datafixers.util.Pair;
import io.github.justfoxx.tot.Global;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ModConfigs {
    public static Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static int CHANCE = 25;
    public static List<String> PRIZES = List.of(
            "tellraw @s {\"text\":\"Funni xp\"}",
            "give @s diamond",
            "give @s coarse_dirt",
            "give @s cookie 5",
            "tp @s ~ ~10 ~",
            "give @s wooden_sword",
            "effect give @s luck 20 1",
            "summon zombie",
            "summon creeper",
            "effect give @s darkness 20 1"
    );
    //public static List<String> BLACK_LIST = new ArrayList<>();

    public static void writeConfig(ModConfigs config) throws IOException {
        var path = FabricLoader.getInstance().getConfigDir().resolve("totconfig.json");
        Files.writeString(path, GSON.toJson(config));
    }

    public static ModConfigs readConfig() throws IOException {
        var path = FabricLoader.getInstance().getConfigDir().resolve("totconfig.json");
        return GSON.fromJson(Files.readString(path), ModConfigs.class);
    }
}

