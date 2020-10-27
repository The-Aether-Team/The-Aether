package com.aether.entity.ai.zephyr;

import com.aether.entity.monster.ZephyrEntity;
import com.aether.entity.projectile.ZephyrSnowballEntity;
import com.aether.util.AetherSoundEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ZephyrAttackGoal extends Goal {
    private final ZephyrEntity zephyr;
    public int attackCounter;
    private World worldObj;
    public int prevAttackCounter;
    private final float base;


    public ZephyrAttackGoal(ZephyrEntity zephyr) {
        this.zephyr = zephyr;
        this.worldObj = zephyr.world;
        this.attackCounter = 0;
        this.base = (this.zephyr.getRNG().nextFloat() - this.zephyr.getRNG().nextFloat()) * 0.2F + 1.0F;
    }

    @Override
    public boolean shouldExecute() {
        return zephyr.getAttackTarget() != null;
    }

    @Override
    public void startExecuting() {
        this.attackCounter = 0;
    }

    @Override
    public void tick() {
        this.prevAttackCounter = this.attackCounter;
        LivingEntity target = zephyr.getAttackTarget();
        if (target == null) {
            if (this.attackCounter > 0) {
                this.attackCounter--;
            }

            this.zephyr.setAttackTarget(this.worldObj.getClosestPlayer(this.zephyr, 100D));
        } else {
            if (target instanceof PlayerEntity && (((PlayerEntity) target).isCreative() || ((PlayerEntity) target).isSpectator())) {
                this.zephyr.setAttackTarget(null);
                return;
            }

            if (target.getDistanceSq(this.zephyr) < 4096.0D && this.zephyr.canEntityBeSeen(target)) {
                double x = target.getPosX() - this.zephyr.getPosX();
                double y = (target.getBoundingBox().minY + (target.getHeight() / 2.0F)) - (this.zephyr.getPosY() + (this.zephyr.getHeight() / 2.0F));
                double z = target.getPosZ() - this.zephyr.getPosZ();

                this.zephyr.rotationYaw = (-(float) Math.atan2(x, z) * 180F) / 3.141593F;

                ++this.attackCounter;

                if (this.attackCounter == 10) {
                    this.zephyr.playSound(AetherSoundEvents.ENTITY_ZEPHYR_AMBIENT, 3F, this.base);
                } else if (this.attackCounter == 20) {
                    this.zephyr.playSound(AetherSoundEvents.ENTITY_ZEPHYR_SHOOT, 3F, this.base);

                    ZephyrSnowballEntity projectile = new ZephyrSnowballEntity(this.worldObj, this.zephyr);
                    Vec3d lookVector = this.zephyr.getLook(1.0F);
                    projectile.setPosition(this.zephyr.getPosX() + lookVector.x * 4D, this.zephyr.getPosY() + (double) (this.zephyr.getHeight() / 2.0F) + 0.5D, this.zephyr.getPosZ() + lookVector.z * 4D);


                    if (!this.worldObj.isRemote) {
                        projectile.shoot(x, y, z, 1.2F, 1.0F);
                        this.worldObj.addEntity(projectile);
                    }

                    this.attackCounter = -40;
                }
            } else if (this.attackCounter > 0) {
                this.attackCounter--;
            }
        }
    }
}