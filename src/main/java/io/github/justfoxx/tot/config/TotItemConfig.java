package io.github.justfoxx.tot.config;

import com.google.gson.JsonObject;
import io.github.ivymc.ivycore.config.ConfigData;

import java.util.List;

public class TotItemConfig extends ConfigData {
    public boolean enabled = true;
    public boolean whitelist = false;
    public List<String> entityblocklist = List.of("minecraft:arrow");
    public int chance = 25;
    public List<String> prizes = List.of(
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

    @Override
    public void onRead(JsonObject jsonObject) throws Exception {

    }
}
