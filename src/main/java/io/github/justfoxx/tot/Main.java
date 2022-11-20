package io.github.justfoxx.tot;

import net.fabricmc.api.ModInitializer;

public class Main implements ModInitializer {
    @Override
    public void onInitialize() {
        if (!Configs.commonConfig.data.enabled) {
            PreMain.g.LOGGER.info("Tot is disabled in commonconfig.json");
            return;
        }
        Items.init();
        PreMain.g.LOGGER.info("Loaded mod!");
    }
}
