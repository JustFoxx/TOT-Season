package io.github.justfoxx.tot;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.GameRules;
import org.jetbrains.annotations.Nullable;

public class Util {
    public static void executeCommand(String cmd, MinecraftServer server, ServerPlayerEntity player){
        var rule = server.getGameRules().get(GameRules.SEND_COMMAND_FEEDBACK);
        String command = String.format("execute positioned %d %d %d as @p run %s",player.getBlockPos().getX(), player.getBlockPos().getY(), player.getBlockPos().getZ(), cmd);
        rule.set(false, server);
        server.getCommandManager().executeWithPrefix(server.getCommandSource(), command);
        rule.set(true, server);
    }
    public static final Random random = Random.create();

    public static boolean checkEntity(Entity entity, @Nullable EntityType<?> type) {
        if (type == null) return false;
        return entity.getType() == type;
    }

    public static EntityType<?> getEntityType(String name) {
        return Registry.ENTITY_TYPE.get(Identifier.tryParse(name));
    }
}
