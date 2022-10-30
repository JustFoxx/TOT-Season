package io.github.justfoxx.tot;

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

public class PreMain implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        Global.logger.info("Made by JustFoxx");
        Global.logger.info("Thanks for installing our mod!");
        Global.logger.info("Check out other mods at https://www.curseforge.com/members/justafoxxo/projects");
    }
}
