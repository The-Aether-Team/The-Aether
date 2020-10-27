package com.aether.entity.projectile;

import com.aether.entity.AetherEntityTypes;
import com.aether.entity.monster.ZephyrEntity;
import com.aether.item.AetherItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class ZephyrSnowballEntity extends ThrowableEntity implements IRendersAsItem {
    private int ticksInAir;
    public ZephyrSnowballEntity(EntityType<? extends ZephyrSnowballEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public ZephyrSnowballEntity(World worldIn, ZephyrEntity throwerIn) {
        super(AetherEntityTypes.ZEPHYR_SNOWBALL, throwerIn, worldIn);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (result.getType() == RayTraceResult.Type.ENTITY)
        {
            Entity entity = ((EntityRayTraceResult)result).getEntity();

            if (entity instanceof PlayerEntity)
            {
                PlayerEntity player = ((PlayerEntity)entity);


                if (player.inventory.armorInventory.get(0).getItem() == AetherItems.SENTRY_BOOTS)
                {
                    return;
                }

                if (!player.isActiveItemStackBlocking())
                {
                    entity.setMotion(entity.getMotion().x, entity.getMotion().y + 0.5D, entity.getMotion().z);
                }
                else
                {
                    ItemStack activeItemStack = player.getActiveItemStack();
                    activeItemStack.damageItem(1, player, d -> {
                        d.sendBreakAnimation(activeItemStack.getEquipmentSlot());
                    });

                    if (activeItemStack.getCount() <= 0)
                    {
                        this.playSound(SoundEvents.ITEM_SHIELD_BREAK, 1.0F, 0.8F + this.world.rand.nextFloat() * 0.4F);
                    }
                    else
                    {
                        this.playSound(SoundEvents.ITEM_SHIELD_BLOCK, 1.0F, 0.8F + this.world.rand.nextFloat() * 0.4F);
                    }
                }

                entity.setMotion(entity.getMotion().x + (this.getMotion().x * 1.5F), entity.getMotion().y, entity.getMotion().z + (this.getMotion().z * 1.5F));
            }
        }
        this.remove();
    }

    @Override
    protected void registerData() {
        this.setNoGravity(true);
    }

    @Override
    public void tick()
    {
        super.tick();
        if (!this.onGround)
        {
            ++this.ticksInAir;
        }

        if (this.ticksInAir > 400)
        {
            this.remove();
        }
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Items.SNOWBALL);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
