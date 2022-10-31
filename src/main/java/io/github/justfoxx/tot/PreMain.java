package io.github.justfoxx.tot;

import io.github.justfoxx.tot.config.ModConfigs;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

import java.io.IOException;

public class PreMain implements PreLaunchEntrypoint {
    public static ModConfigs CONFIG;
    @Override
    public void onPreLaunch() {
        try {
            CONFIG = ModConfigs.readConfig();
        } catch (IOException ignored) {

        }
        Global.logger.info("Made by JustFoxx");
        Global.logger.info("Thanks for installing our mod!");
        Global.logger.info("Check out other mods at https://www.curseforge.com/members/justafoxxo/projects");
    }
}
