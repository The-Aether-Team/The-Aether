package com.gildedgames.aether.common.effect;

import com.gildedgames.aether.common.registry.AetherItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;

import java.util.ArrayList;
import java.util.List;

public class InebriationEffect extends Effect {
    private int duration;

    public double rotD, motD;

    public InebriationEffect(EffectType typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
        this.distractEntity(entityLivingBaseIn);

        if (this.duration % 50 == 0) {
            entityLivingBaseIn.hurt(new DamageSource("inebriation").bypassArmor(), 1.0F);
        }

        /*if (entityLivingBaseIn instanceof PlayerEntity)
        {
            if (this.duration >= 500)
            {
                PlayerEntity player = (PlayerEntity) entityLivingBaseIn;
                IAetherPlayer iPlayerAether = AetherAPI.getInstance().get(player);

                if (iPlayerAether != null)
                {
                    AetherPlayer playerAether = (AetherPlayer) iPlayerAether;

                    if (!player.world.isRemote)
                    {
                        playerAether.setPoisoned();
                        AetherNetworkingManager.sendToAll(new PacketSendPoison(player));
                    }
                }
            }
        }*/
    }

    public void distractEntity(LivingEntity entityLivingBaseIn) {
        double gaussian = entityLivingBaseIn.level.random.nextGaussian();
        double newMotD = 0.1D * gaussian;
        double newRotD = (Math.PI / 4D) * gaussian;

        this.motD = 0.2D * newMotD + (0.8D) * this.motD;

        entityLivingBaseIn.setDeltaMovement(entityLivingBaseIn.getDeltaMovement().add(this.motD, 0, this.motD));
        this.rotD = 0.125D * newRotD + (1.0D - 0.125D) * this.rotD;

        entityLivingBaseIn.yRot = (float)((double)entityLivingBaseIn.yRot + rotD);
        entityLivingBaseIn.xRot = (float)((double)entityLivingBaseIn.xRot + rotD);
        
        /*if (entityLivingBaseIn.world instanceof ServerWorld) {
            ((ServerWorld)entityLivingBaseIn.world).spawnParticle(ParticleTypes.ITEM, entityLivingBaseIn.getPosX(), entityLivingBaseIn.getBoundingBox().minY + entityLivingBaseIn.getHeight() * 0.8D, entityLivingBaseIn.getPosZ(), 2, 0.0D, 1, 0.0D, 0.0625D, 0.0D, 1);
        }*/
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        this.duration = duration;
        return true;
    }

    @Override
    public boolean isInstantenous() {
        return false;
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        ret.add(new ItemStack(AetherItems.SKYROOT_REMEDY_BUCKET.get()));
        return ret;
    }
}
