package io.github.justfoxx.tot.items;

import io.github.justfoxx.tot.PreMain;
import io.github.justfoxx.tot.Util;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class TotItem extends Item {
    public TotItem() {
        super(new FabricItemSettings().maxCount(12).group(ItemGroup.MISC));
    }

    private void randomMethod(ServerPlayerEntity player){
        if(PreMain.CONFIG.prizes.size() < 1) return;
        int randomInt = Util.random.nextBetween(1,PreMain.CONFIG.prizes.size());
        String prize = PreMain.CONFIG.prizes.get(randomInt-1);
        Util.executeCommand(prize, player.getServer(), player);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.playSound(SoundEvents.ENTITY_WITHER_SKELETON_DEATH, 1.0F, 1.0F);
        if(world instanceof ServerWorld serverWorld) {
            randomMethod((ServerPlayerEntity) user);
            serverWorld.spawnParticles(ParticleTypes.WITCH, user.getX(), user.getY() + 1.0D, user.getZ(), 10, 0.1D, 0.1D, 0.1D, 0.1D);
        }
        if(!user.isCreative()) {
            user.getStackInHand(hand).decrement(1);
        }
        return TypedActionResult.consume(user.getStackInHand(hand));
    }
}
