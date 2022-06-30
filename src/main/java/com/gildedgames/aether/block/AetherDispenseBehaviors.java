package com.gildedgames.aether.block;

import com.gildedgames.aether.AetherTags;
import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.entity.projectile.weapon.HammerProjectile;
import com.gildedgames.aether.entity.projectile.weapon.ThrownLightningKnife;
import com.gildedgames.aether.item.AetherItems;
import com.gildedgames.aether.item.materials.util.ISwetBallConversion;
import com.gildedgames.aether.item.miscellaneous.bucket.SkyrootBucketItem;
import com.gildedgames.aether.util.LevelUtil;
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
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
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

public class AetherDispenseBehaviors {
    public static DispenseItemBehavior DEFAULT_FIRE_CHARGE_BEHAVIOR;
    public static DispenseItemBehavior DEFAULT_FLINT_AND_STEEL_BEHAVIOR;

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
            return Util.make(new ThrownLightningKnife(world), (projectile) -> {
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
            HammerProjectile hammerProjectile = new HammerProjectile(world);
            hammerProjectile.setPos(position.x(), position.y(), position.z());
            return hammerProjectile;
        }

        @Override
        protected float getUncertainty() {
            return 1.0F;
        }
    };

    public static final DispenseItemBehavior SKYROOT_BUCKET_DISPENSE_BEHAVIOR = new DefaultDispenseItemBehavior() {
        private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

        @Nonnull
        @Override
        public ItemStack execute(BlockSource source, ItemStack stack) {
            DispensibleContainerItem dispensiblecontaineritem = (DispensibleContainerItem) stack.getItem();
            BlockPos blockpos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
            Level level = source.getLevel();
            if (dispensiblecontaineritem.emptyContents(null, level, blockpos, null)) {
                dispensiblecontaineritem.checkExtraContent(null, level, stack, blockpos);
                return new ItemStack(AetherItems.SKYROOT_BUCKET.get());
            } else {
                return this.defaultDispenseItemBehavior.dispense(source, stack);
            }
        }
    };

    public static final DispenseItemBehavior SKYROOT_BUCKET_PICKUP_BEHAVIOR = new DefaultDispenseItemBehavior() {
        private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

        @Nonnull
        @Override
        public ItemStack execute(@Nonnull BlockSource source, @Nonnull ItemStack stack) {
            LevelAccessor levelAccessor = source.getLevel();
            BlockPos blockPos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
            BlockState blockState = levelAccessor.getBlockState(blockPos);
            Block block = blockState.getBlock();
            if (block instanceof BucketPickup bucketPickup) {
                ItemStack bucketStack = bucketPickup.pickupBlock(levelAccessor, blockPos, blockState);
                bucketStack = SkyrootBucketItem.swapBucketType(bucketStack);
                if (bucketStack.isEmpty()) {
                    return super.execute(source, stack);
                } else {
                    levelAccessor.gameEvent(null, GameEvent.FLUID_PICKUP, blockPos);
                    Item item = bucketStack.getItem();
                    stack.shrink(1);
                    if (stack.isEmpty()) {
                        return new ItemStack(item);
                    } else {
                        if (source.<DispenserBlockEntity>getEntity().addItem(new ItemStack(item)) < 0) {
                            this.defaultDispenseItemBehavior.dispense(source, new ItemStack(item));
                        }
                        return stack;
                    }
                }
            } else {
                return super.execute(source, stack);
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

    public static final DispenseItemBehavior DISPENSE_FIRE_CHARGE_BEHAVIOR = new OptionalDispenseItemBehavior() {
        @Nonnull
        @Override
        public ItemStack execute(BlockSource source, @Nonnull ItemStack stack) {
            if (LevelUtil.inTag(source.getLevel(), AetherTags.Dimensions.ULTRACOLD)) {
                this.setSuccess(false);
                return stack;
            } else {
                return DEFAULT_FIRE_CHARGE_BEHAVIOR.dispense(source, stack);
            }
        }

        @Override
        protected void playSound(BlockSource source) {
            source.getLevel().levelEvent(this.isSuccess() ? 1018 : 1001, source.getPos(), 0);
        }
    };

    public static final DispenseItemBehavior DISPENSE_FLINT_AND_STEEL = new OptionalDispenseItemBehavior() {
        @Nonnull
        @Override
        protected ItemStack execute(BlockSource source, @Nonnull ItemStack stack) {
            if (LevelUtil.inTag(source.getLevel(), AetherTags.Dimensions.ULTRACOLD)) {
                this.setSuccess(false);
                return stack;
            } else {
                return DEFAULT_FLINT_AND_STEEL_BEHAVIOR.dispense(source, stack);
            }
        }
    };
}
