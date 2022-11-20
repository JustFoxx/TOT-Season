package io.github.justfoxx.tot.config;

import com.google.gson.JsonObject;
import io.github.ivymc.ivycore.config.ConfigData;

import java.util.List;

public  class CommonConfig extends ConfigData {
    public boolean enabled = true;

    @Override
    public void onRead(JsonObject jsonObject) throws Exception {

    }
}
