package io.github.justfoxx.tot.mixin;

import io.github.justfoxx.tot.Items;
import io.github.justfoxx.tot.items.TotItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class MobEntityMixin {
    private NbtCompound generateNbt() {
        NbtCompound nbt = new NbtCompound();
        NbtCompound nbt2 = new NbtCompound();
        nbt2.putString("id", "tot:tot_item");
        nbt2.putInt("Count", 1);
        nbt.put("Item",nbt2);
        return nbt;
    }
    public NbtCompound nbt = generateNbt();
    public final Random random = Random.create();
    @Inject(at = @At("RETURN"), method = "onDeath")
    public void onDeath(DamageSource damageSource, CallbackInfo ci) {
        if(!((Object)this instanceof MobEntity)) return;
        if(!(damageSource.getAttacker() instanceof PlayerEntity player)) return;
        int rndInt = random.nextBetween(1, 3);
        if(rndInt == 1) {
            player.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
            player.giveItemStack(new ItemStack(Items.TOT_ITEM));
        }
    }
}
