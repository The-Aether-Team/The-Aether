package com.gildedgames.aether.potion;

import com.gildedgames.aether.registry.AetherItems;
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
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        this.distractEntity(entityLivingBaseIn);

        if (this.duration % 50 == 0) {
            entityLivingBaseIn.attackEntityFrom(new DamageSource("inebriation").setDamageBypassesArmor(), 1.0F);
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
        double gaussian = entityLivingBaseIn.world.rand.nextGaussian();
        double newMotD = 0.1D * gaussian;
        double newRotD = (Math.PI / 4D) * gaussian;

        this.motD = 0.2D * newMotD + (0.8D) * this.motD;

        entityLivingBaseIn.setMotion(entityLivingBaseIn.getMotion().add(this.motD, 0, this.motD));
        this.rotD = 0.125D * newRotD + (1.0D - 0.125D) * this.rotD;

        entityLivingBaseIn.rotationYaw = (float)((double)entityLivingBaseIn.rotationYaw + rotD);
        entityLivingBaseIn.rotationPitch = (float)((double)entityLivingBaseIn.rotationPitch + rotD);
        
        /*if (entityLivingBaseIn.world instanceof ServerWorld) {
            ((ServerWorld)entityLivingBaseIn.world).spawnParticle(ParticleTypes.ITEM, entityLivingBaseIn.getPosX(), entityLivingBaseIn.getBoundingBox().minY + entityLivingBaseIn.getHeight() * 0.8D, entityLivingBaseIn.getPosZ(), 2, 0.0D, 1, 0.0D, 0.0625D, 0.0D, 1);
        }*/
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        this.duration = duration;
        return true;
    }

    @Override
    public boolean isInstant() {
        return false;
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        ret.add(new ItemStack(AetherItems.SKYROOT_REMEDY_BUCKET.get()));
        return ret;
    }
}
