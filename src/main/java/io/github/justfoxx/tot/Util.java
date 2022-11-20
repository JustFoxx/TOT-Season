package io.github.justfoxx.tot;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.GameRules;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class Util {
    public static void executeCommand(String cmd, MinecraftServer server, ServerPlayerEntity player) throws CommandSyntaxException {
        var rule = server.getGameRules().get(GameRules.SEND_COMMAND_FEEDBACK);
        String command = String.format("execute as @p run %s",cmd);
        rule.set(false, server);
        var commandSource = new ServerCommandSource(CommandOutput.DUMMY,player.getPos(),player.getRotationClient(), player.getWorld(),4,"", Text.of("Server"), server,player);
        server.getCommandManager().execute(commandSource, command);
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

    public static boolean reverse(boolean original, boolean reverse) {
        return reverse != original;
    }
}
