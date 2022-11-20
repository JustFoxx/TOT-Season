package io.github.justfoxx.tot;

import io.github.ivymc.ivycore.Global;
import io.github.ivymc.ivycore.config.ConfigBuilder;
import io.github.justfoxx.tot.config.CommonConfig;
import io.github.justfoxx.tot.config.TotItemConfig;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

import java.io.IOException;
import java.nio.file.Path;

public class PreMain implements PreLaunchEntrypoint {
    public static Global g = new Global("tot");
    public static ConfigBuilder configBuilder = new ConfigBuilder(g.MOD_ID);
    @Override
    public void onPreLaunch() {
        try {
            configBuilder.loadConfig();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Configs.commonConfig = configBuilder.createConfigKey(Path.of("commonconfig.json"), CommonConfig.class);
        Configs.totItemConfig = configBuilder.createConfigKey(Path.of("totitemconfig.json"), TotItemConfig.class);
        try {
            Configs.commonConfig.readConfig();
            Configs.totItemConfig.readConfig();
        } catch (IOException e) {
            try {
                Configs.commonConfig.writeConfig();
                Configs.totItemConfig.writeConfig();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
