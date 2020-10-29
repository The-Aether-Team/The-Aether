package com.aether.entity.projectile;

import com.aether.entity.AetherEntityTypes;
import com.aether.item.AetherItems;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class ZephyrSnowballEntity extends AbstractFireballEntity {
    private int ticksInAir;
    public ZephyrSnowballEntity(EntityType<? extends ZephyrSnowballEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @OnlyIn(Dist.CLIENT)
    public ZephyrSnowballEntity(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
        super(AetherEntityTypes.ZEPHYR_SNOWBALL, x, y, z, accelX, accelY, accelZ, worldIn);
    }
    
    public ZephyrSnowballEntity(World worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ) {
        super(AetherEntityTypes.ZEPHYR_SNOWBALL, shooter, accelX, accelY, accelZ, worldIn);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
    	super.onImpact(result);
    	if (!this.world.isRemote) {
    		if (result.getType() == RayTraceResult.Type.ENTITY) {
    			Entity entity = ((EntityRayTraceResult)result).getEntity();
    			if (entity instanceof LivingEntity) {
    				LivingEntity livingEntity = (LivingEntity)entity;
    				boolean isPlayer = livingEntity instanceof PlayerEntity;

					if (isPlayer && ((PlayerEntity)entity).inventory.armorInventory.get(0).getItem() == AetherItems.SENTRY_BOOTS) {
						return;
					}

					if (!livingEntity.isActiveItemStackBlocking()) {
						entity.setMotion(entity.getMotion().x, entity.getMotion().y + 0.5, entity.getMotion().z);
					}
					else {
						ItemStack activeItemStack = livingEntity.getActiveItemStack();
						activeItemStack.damageItem(1, livingEntity, p -> p.sendBreakAnimation(activeItemStack.getEquipmentSlot()));

						if (activeItemStack.getCount() <= 0) {
							world.playSound((PlayerEntity)null, entity.getPosition(), SoundEvents.ITEM_SHIELD_BREAK, SoundCategory.PLAYERS, 1.0F, 0.8F + this.world.rand.nextFloat() * 0.4F);
						}
						else {
							world.playSound((PlayerEntity)null, entity.getPosition(), SoundEvents.ITEM_SHIELD_BLOCK, SoundCategory.PLAYERS, 1.0F, 0.8F + this.world.rand.nextFloat() * 0.4F);
						}
					}

					entity.setMotion(entity.getMotion().x + (this.getMotion().x * 1.5F), entity.getMotion().y, entity.getMotion().z + (this.getMotion().z * 1.5F));
    			}
    		}
            this.remove();
    	}
    }

    @Override
    protected void registerData() {
    	super.registerData();
        this.setNoGravity(true);
    }

	@Override
	public void tick() {
		super.tick();
		if (!this.onGround) {
			++this.ticksInAir;
		}

		if (this.ticksInAir > 400) {
			this.remove();
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public ItemStack getItem() {
        ItemStack itemstack = this.getStack();
        return itemstack.isEmpty()? new ItemStack(Items.SNOWBALL) : itemstack;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
