package io.github.justfoxx.tot;

import net.fabricmc.api.ModInitializer;

public class Main implements ModInitializer {
    @Override
    public void onInitialize() {
        Items.init();
        Global.logger.info("Loaded mod!");
    }
}
