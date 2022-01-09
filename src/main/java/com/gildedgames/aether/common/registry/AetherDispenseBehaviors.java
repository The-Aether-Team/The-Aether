package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.common.entity.projectile.weapon.HammerProjectileEntity;
import com.gildedgames.aether.common.entity.projectile.weapon.LightningKnifeEntity;
import com.gildedgames.aether.common.item.materials.util.ISwetBallConversion;
import com.gildedgames.aether.common.item.miscellaneous.bucket.SkyrootWaterBucketItem;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;
import top.theillusivec4.curios.api.type.util.ICuriosHelper;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AetherDispenseBehaviors
{
    public static final DispenseItemBehavior DISPENSE_ACCESSORY_BEHAVIOR = new DefaultDispenseItemBehavior() {
        @Nonnull
        @Override
        protected ItemStack execute(@Nonnull BlockSource blockSource, @Nonnull ItemStack stack) {
            return dispenseAccessory(blockSource, stack) ? stack : super.execute(blockSource, stack);
        }
    };

    public static boolean dispenseAccessory(BlockSource blockSource, ItemStack stack) {
        BlockPos blockpos = blockSource.getPos().relative(blockSource.getBlockState().getValue(DispenserBlock.FACING));
        List<LivingEntity> list = blockSource.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(blockpos), EntitySelector.NO_SPECTATORS.and(new EntitySelector.MobCanWearArmorEntitySelector(stack)));
        if (list.isEmpty()) {
            return false;
        } else {
            LivingEntity livingentity = list.get(0);
            ItemStack itemStack = stack.split(1);
            ICuriosHelper curiosHelper = CuriosApi.getCuriosHelper();
            curiosHelper.getCurio(itemStack).ifPresent(curio -> curiosHelper.getCuriosHandler(livingentity).ifPresent(handler -> {
                Map<String, ICurioStacksHandler> curios = handler.getCurios();
                for (Map.Entry<String, ICurioStacksHandler> entry : curios.entrySet()) {
                    IDynamicStackHandler stackHandler = entry.getValue().getStacks();
                    for (int i = 0; i < stackHandler.getSlots(); i++) {
                        String id = entry.getKey();
                        SlotContext slotContext = new SlotContext(id, livingentity, i, true, true);
                        if (curiosHelper.isStackValid(slotContext, itemStack) && curio.canEquip(id, livingentity) && curio.canEquipFromUse(slotContext)) {
                            ItemStack present = stackHandler.getStackInSlot(i);
                            if (present.isEmpty()) {
                                stackHandler.setStackInSlot(i, itemStack.copy());
                                int count = itemStack.getCount();
                                itemStack.shrink(count);
                            }
                        }
                    }
                }
            }));
            return true;
        }
    }

    public static final DispenseItemBehavior DISPENSE_LIGHTNING_KNIFE_BEHAVIOR = new AbstractProjectileDispenseBehavior() {
        @Nonnull
        @Override
        protected Projectile getProjectile(@Nonnull Level world, @Nonnull Position position, @Nonnull ItemStack stack) {
            return Util.make(new LightningKnifeEntity(world), (projectile) -> {
                projectile.setPos(position.x(), position.y(), position.z());
                projectile.setItem(stack);
            });
        }

        @Override
        protected float getUncertainty() {
            return 1.5F;
        }
    };

    public static final DispenseItemBehavior DISPENSE_NOTCH_HAMMER_BEHAVIOR = new AbstractProjectileDispenseBehavior() {
        @Nonnull
        @Override
        public ItemStack execute(BlockSource blockSource, @Nonnull ItemStack stack) {
            Level world = blockSource.getLevel();
            Position position = DispenserBlock.getDispensePosition(blockSource);
            Direction direction = blockSource.getBlockState().getValue(DispenserBlock.FACING);
            Projectile projectileEntity = this.getProjectile(world, position, stack);
            projectileEntity.shoot(direction.getStepX(), (float) direction.getStepY(), direction.getStepZ(), this.getPower(), this.getUncertainty());
            world.addFreshEntity(projectileEntity);
            int damage = stack.getDamageValue();
            stack.setDamageValue(damage + 1);
            if (stack.getDamageValue() >= stack.getMaxDamage()) {
                stack.shrink(1);
            }
            return stack;
        }

        @Nonnull
        @Override
        protected Projectile getProjectile(@Nonnull Level world, Position position, @Nonnull ItemStack stack) {
            HammerProjectileEntity hammerProjectile = new HammerProjectileEntity(world);
            hammerProjectile.setPos(position.x(), position.y(), position.z());
            return hammerProjectile;
        }

        @Override
        protected float getUncertainty() {
            return 1.0F;
        }
    };

    public static final DispenseItemBehavior DISPENSE_WATER_BEHAVIOR = new DefaultDispenseItemBehavior() {
        private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

        @Nonnull
        @Override
        public ItemStack execute(BlockSource source, ItemStack stack) {
            SkyrootWaterBucketItem bucketItem = (SkyrootWaterBucketItem) stack.getItem();
            Level world = source.getLevel();
            BlockPos blockpos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
            if (bucketItem.tryPlaceContainedLiquid(null, world, blockpos, null)) {
                return new ItemStack(AetherItems.SKYROOT_BUCKET.get());
            } else {
                return this.defaultDispenseItemBehavior.dispense(source, stack);
            }
        }
    };

    public static final DispenseItemBehavior PICKUP_WATER_BEHAVIOR = new DefaultDispenseItemBehavior() {
        private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

        @Nonnull
        public ItemStack execute(BlockSource p_123566_, @Nonnull ItemStack p_123567_) {
            LevelAccessor levelaccessor = p_123566_.getLevel();
            BlockPos blockpos = p_123566_.getPos().relative(p_123566_.getBlockState().getValue(DispenserBlock.FACING));
            BlockState blockstate = levelaccessor.getBlockState(blockpos);
            Block block = blockstate.getBlock();
            if (block instanceof BucketPickup bucketPickup) {
                ItemStack itemstack = bucketPickup.pickupBlock(levelaccessor, blockpos, blockstate);
                if (itemstack.isEmpty() || itemstack.getItem() != Items.WATER_BUCKET) {
                    return super.execute(p_123566_, p_123567_);
                } else {
                    levelaccessor.gameEvent(null, GameEvent.FLUID_PICKUP, blockpos);
                    Item item = AetherItems.SKYROOT_WATER_BUCKET.get();
                    p_123567_.shrink(1);
                    if (p_123567_.isEmpty()) {
                        return new ItemStack(item);
                    } else {
                        if (p_123566_.<DispenserBlockEntity>getEntity().addItem(new ItemStack(item)) < 0) {
                            this.defaultDispenseItemBehavior.dispense(p_123566_, new ItemStack(item));
                        }

                        return p_123567_;
                    }
                }
            } else {
                return super.execute(p_123566_, p_123567_);
            }
        }
    };

    public static final DispenseItemBehavior DISPENSE_AMBROSIUM_BEHAVIOR = new OptionalDispenseItemBehavior() {
        @Nonnull
        @Override
        protected ItemStack execute(BlockSource source, @Nonnull ItemStack stack) {
            this.setSuccess(true);
            Level world = source.getLevel();
            BlockPos blockpos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
            BlockState blockstate = world.getBlockState(blockpos);
            if (blockstate.is(AetherTags.Blocks.ENCHANTABLE_GRASS_BLOCKS)) {
                world.setBlockAndUpdate(blockpos, AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get().defaultBlockState());
                stack.shrink(1);
            } else {
                this.setSuccess(false);
            }
            return stack;
        }
    };

    public static final DispenseItemBehavior DISPENSE_SWET_BALL_BEHAVIOR = new OptionalDispenseItemBehavior() {
        @Nonnull
        @Override
        protected ItemStack execute(BlockSource source, @Nonnull ItemStack stack) {
            this.setSuccess(true);
            Level world = source.getLevel();
            BlockPos blockpos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
            if (!ISwetBallConversion.convertBlockWithoutContext(world, blockpos, stack)) {
                this.setSuccess(false);
            }
            return stack;
        }
    };

    public static final DispenseItemBehavior DISPENSE_SPAWN_EGG_BEHAVIOR = new DefaultDispenseItemBehavior() {
        @Nonnull
        @Override
        public ItemStack execute(BlockSource source, ItemStack stack) {
            Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
            EntityType<?> entityType = ((SpawnEggItem) stack.getItem()).getType(stack.getTag());
            entityType.spawn(source.getLevel(), stack, null, source.getPos().relative(direction), MobSpawnType.DISPENSER, direction != Direction.UP, false);
            stack.shrink(1);
            return stack;
        }
    };

    public static final DispenseItemBehavior DISPENSE_FIRE_CHARGE_BEHAVIOR = new OptionalDispenseItemBehavior() {
        @Nonnull
        @Override
        public ItemStack execute(BlockSource source, @Nonnull ItemStack stack) {
            Level world = source.getLevel();
            if (world.dimension() == AetherDimensions.AETHER_WORLD) {
                this.setSuccess(false);
            } else {
                this.setSuccess(true);
                Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
                Position iposition = DispenserBlock.getDispensePosition(source);
                double d0 = iposition.x() + direction.getStepX() * 0.3F;
                double d1 = iposition.y() + direction.getStepY() * 0.3F;
                double d2 = iposition.z() + direction.getStepZ() * 0.3F;
                Random random = world.random;
                double d3 = random.nextGaussian() * 0.05 + direction.getStepX();
                double d4 = random.nextGaussian() * 0.05 + direction.getStepY();
                double d5 = random.nextGaussian() * 0.05 + direction.getStepZ();
                world.addFreshEntity(Util.make(new SmallFireball(world, d0, d1, d2, d3, d4, d5), (entity) -> entity.setItem(stack)));
                stack.shrink(1);
            }
            return stack;
        }

        @Override
        protected void playSound(BlockSource source) {
            source.getLevel().levelEvent(this.isSuccess() ? 1018 : 1001, source.getPos(), 0);
        }
    };

    public static final DispenseItemBehavior DISPENSE_FLINT_AND_STEEL = new OptionalDispenseItemBehavior() {
        @Override
        protected ItemStack execute(BlockSource source, ItemStack stack) {
            Level world = source.getLevel();
            if (world.dimension() == AetherDimensions.AETHER_WORLD) {
                this.setSuccess(false);
            } else {
                this.setSuccess(true);
                BlockPos blockpos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
                BlockState blockstate = world.getBlockState(blockpos);
                Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
                if (BaseFireBlock.canBePlacedAt(world, blockpos, direction)) {
                    world.setBlockAndUpdate(blockpos, Blocks.FIRE.defaultBlockState());
                } else if (CampfireBlock.canLight(blockstate)) {
                    world.setBlockAndUpdate(blockpos, blockstate.setValue(BlockStateProperties.LIT, true));
                } else if (blockstate.isFlammable(world, blockpos, source.getBlockState().getValue(DispenserBlock.FACING).getOpposite())) {
                    blockstate.onCaughtFire(world, blockpos, source.getBlockState().getValue(DispenserBlock.FACING).getOpposite(), null);
                    if (blockstate.getBlock() instanceof TntBlock) {
                        world.removeBlock(blockpos, false);
                    }
                } else {
                    this.setSuccess(false);
                }

                if (this.isSuccess() && stack.hurt(1, world.random, null)) {
                    stack.setCount(0);
                }
            }
            return stack;
        }
    };
}
