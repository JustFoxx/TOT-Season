package io.github.justfoxx.tot.items;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
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
        int randomInt = random.nextBetween(1,10);
        switch (randomInt) {
            case 1 -> player.sendMessageToClient(Text.literal("Funni xp"), false);
            case 2 -> player.giveItemStack(new ItemStack(Items.DIAMOND));
            case 3 -> player.giveItemStack(new ItemStack(Items.COARSE_DIRT));
            case 4 -> player.giveItemStack(new ItemStack(Items.COOKIE, 5));
            case 5 -> FabricDimensions.teleport(player, world, new TeleportTarget(new Vec3d(player.getX(), player.getY()+10, player.getZ()), player.getVelocity(), player.getYaw(), player.getPitch()));
            case 6 -> player.giveItemStack(new ItemStack(Items.WOODEN_SWORD));
            case 7 -> player.addStatusEffect(new StatusEffectInstance(StatusEffects.LUCK, 100, 1));
            case 8 -> summonHelper("minecraft:zombie", world, player.getPos(), null);
            case 9 -> summonHelper("minecraft:creeper", world, player.getPos(), null);
            case 10 -> player.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 100, 1));
        }
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
