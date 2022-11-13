package io.github.justfoxx.tot;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.GameRules;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class Util {
    public static void executeCommand(String cmd, MinecraftServer server, ServerPlayerEntity player) throws CommandSyntaxException {
        var rule = server.getGameRules().get(GameRules.SEND_COMMAND_FEEDBACK);
        String command = String.format("execute positioned %d %d %d as @p run %s",player.getBlockPos().getX(), player.getBlockPos().getY(), player.getBlockPos().getZ(), cmd);
        rule.set(false, server);
        server.getCommandManager().getDispatcher().execute(command, server.getCommandSource());
        rule.set(true, server);
    }
    public static final Random random = new Random();

    public static boolean checkEntity(Entity entity, @Nullable EntityType<?> type) {
        if (type == null) return false;
        return entity.getType() == type;
    }

    public static EntityType<?> getEntityType(String name) {
        return Registry.ENTITY_TYPE.get(Identifier.tryParse(name));
    }
}
