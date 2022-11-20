package io.github.justfoxx.tot.mixin;

import io.github.justfoxx.tot.Configs;
import io.github.justfoxx.tot.Items;
import io.github.justfoxx.tot.Util;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

@Mixin(LivingEntity.class)
public abstract class MobEntityMixin {
    public final Random random = new Random();
    public final int low = 1;
    public final int high = 100;
    public final AtomicBoolean is = new AtomicBoolean(false);
    @Inject(at = @At("RETURN"), method = "onDeath")
    public void onDeath(DamageSource damageSource, CallbackInfo ci) {
        if(!((Object)this instanceof MobEntity entity)) return;
        if(!(entity.getWorld() instanceof ServerWorld world)) return;
        if(!(damageSource.getAttacker() instanceof PlayerEntity player)) return;
        Configs.totItemConfig.data.entityblocklist.forEach(
                id -> is.set(Util.checkEntity((Entity) (Object) this, Util.getEntityType(id)))
        );
        if(Util.reverse(is.get(), Configs.totItemConfig.data.whitelist)) return;
        int rndInt = random.nextInt(high-low) + low;
        if(rndInt <= Configs.totItemConfig.data.chance) {
            player.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 2.0F, 2.0F);
            ItemEntity item = entity.dropStack(new ItemStack(Items.TOT_ITEM));
            world.spawnParticles(ParticleTypes.HAPPY_VILLAGER, item.getX(), item.getY(), item.getZ(), 10, 0.1, 0.1, 0.1, 0.1);
        }
    }
}
