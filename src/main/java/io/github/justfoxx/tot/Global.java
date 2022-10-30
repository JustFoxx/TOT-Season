package io.github.justfoxx.tot;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Global {
    public static final String MOD_ID = "tot";
    public static final Logger logger = LoggerFactory.getLogger(MOD_ID);
    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }
}
