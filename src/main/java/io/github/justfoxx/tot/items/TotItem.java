package io.github.justfoxx.tot.items;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.optics.Adapter;
import io.github.justfoxx.tot.Global;
import io.github.justfoxx.tot.PreMain;
import io.github.justfoxx.tot.config.ModConfigs;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.BlockPileFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PileConfiguredFeatures;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

public class TotItem extends Item {
    private Random random = Random.create();
    public TotItem() {
        super(new FabricItemSettings().maxCount(12).group(ItemGroup.MISC));
    }

    public static void summonHelper(String id, ServerWorld world, Vec3d pos, @Nullable NbtCompound nbt) {
        if(nbt == null) nbt = new NbtCompound();
        nbt.putString("id", id);
        Entity entity = EntityType.loadEntityWithPassengers(nbt, world, entityx -> {
            entityx.refreshPositionAndAngles(pos.x, pos.y, pos.z, entityx.getYaw(), entityx.getPitch());
            return entityx;
        });
        if (entity instanceof MobEntity livingEntity) {
            livingEntity.equipStack(EquipmentSlot.HEAD, new ItemStack(Items.PLAYER_HEAD));
            livingEntity.initialize(world, world.getLocalDifficulty(new BlockPos(pos)), SpawnReason.COMMAND, null, null);
        };
        world.spawnNewEntityAndPassengers(entity);
    }

    private void randomMethod(ServerWorld world, ServerPlayerEntity player){
        if(PreMain.CONFIG.PRIZES.size() < 1) return;
        int randomInt = random.nextBetween(1,ModConfigs.PRIZES.size()-1);
        String prize = PreMain.CONFIG.PRIZES.get(randomInt);
        executeCommand(prize, player.getServer(), player);
    }

    private void executeCommand(String cmd, MinecraftServer server, ServerPlayerEntity player){
        String command = String.format("execute positioned %d %d %d as @p run %s",player.getBlockPos().getX(), player.getBlockPos().getY(), player.getBlockPos().getZ(), cmd);
        Global.logger.info(command);
        server.getCommandManager().executeWithPrefix(server.getCommandSource(), command);

    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.playSound(SoundEvents.ENTITY_WITHER_SKELETON_DEATH, 1.0F, 1.0F);
        if(world instanceof ServerWorld serverWorld) {
            randomMethod(serverWorld, (ServerPlayerEntity) user);
            serverWorld.spawnParticles(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, user.getX(), user.getY() + 1.0D, user.getZ(), 10, 0.5D, 0.5D, 0.5D, 0.1D);
        }
        return TypedActionResult.consume(user.getStackInHand(hand));
    }
}
