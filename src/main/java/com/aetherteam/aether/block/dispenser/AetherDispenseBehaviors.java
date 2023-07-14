package com.aetherteam.aether.block.dispenser;

import com.aetherteam.aether.entity.projectile.weapon.HammerProjectile;
import com.aetherteam.aether.entity.projectile.weapon.ThrownLightningKnife;
import com.aetherteam.aether.inventory.menu.AccessoriesMenu;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.item.miscellaneous.bucket.SkyrootBucketItem;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;
import top.theillusivec4.curios.api.type.util.ICuriosHelper;

import java.util.List;
import java.util.Map;

public class AetherDispenseBehaviors {
    /**
     * Behavior for allowing dispensers to equip Curios accessories to players.
     */
    public static final DispenseItemBehavior DISPENSE_ACCESSORY_BEHAVIOR = new DefaultDispenseItemBehavior() {
        @Override
        protected ItemStack execute(BlockSource blockSource, ItemStack stack) {
            return dispenseAccessory(blockSource, stack) ? stack : super.execute(blockSource, stack);
        }
    };

    /**
     * Based on {@link net.minecraft.world.item.ArmorItem#dispenseArmor(BlockSource, ItemStack)} and {@link top.theillusivec4.curios.common.event.CuriosEventHandler#curioRightClick(PlayerInteractEvent.RightClickItem)}.<br><br>
     * Handles checking if an accessory shot from a dispenser can be equipped, and handles that equipping behavior if it can.
     * @param blockSource The {@link BlockSource} for the dispenser.
     * @param stack The {@link ItemStack} in the dispenser.
     * @return Whether the accessory can be dispensed, as a {@link Boolean}.
     */
    public static boolean dispenseAccessory(BlockSource blockSource, ItemStack stack) {
        BlockPos pos = blockSource.getPos().relative(blockSource.getBlockState().getValue(DispenserBlock.FACING));
        List<LivingEntity> list = blockSource.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(pos), EntitySelector.NO_SPECTATORS.and(new EntitySelector.MobCanWearArmorEntitySelector(stack)));
        if (list.isEmpty()) {
            return false;
        } else {
            LivingEntity livingEntity = list.get(0);
            ItemStack itemStack = stack.split(1);
            ICuriosHelper curiosHelper = CuriosApi.getCuriosHelper();
            curiosHelper.getCurio(itemStack).ifPresent(curio -> curiosHelper.getCuriosHandler(livingEntity).ifPresent(handler -> {
                Map<String, ICurioStacksHandler> curios = handler.getCurios();
                for (Map.Entry<String, ICurioStacksHandler> entry : curios.entrySet()) { // Curios entries.
                    if (List.of(AccessoriesMenu.AETHER_IDENTIFIERS).contains(entry.getKey())) { // Check if Curios entries match the ones in the Aether accessories menu.
                        IDynamicStackHandler stackHandler = entry.getValue().getStacks();
                        for (int i = 0; i < stackHandler.getSlots(); i++) {
                            String id = entry.getKey();
                            SlotContext slotContext = new SlotContext(id, livingEntity, i, false, true); // Get slot that a Curio entry has.
                            if (curiosHelper.isStackValid(slotContext, itemStack) && curio.canEquip(slotContext) && curio.canEquipFromUse(slotContext)) {
                                ItemStack slotStack = stackHandler.getStackInSlot(i);
                                if (slotStack.isEmpty()) { // Check if Curio slot is empty.
                                    stackHandler.setStackInSlot(i, itemStack.copy()); // Put copy of stack from dispenser into slot.
                                    int count = itemStack.getCount();
                                    itemStack.shrink(count); // Shrink stack in dispenser.
                                }
                            }
                        }
                    }
                }
            }));
            return true;
        }
    }

    /**
     * Behavior for dispensing Lightning Knives.
     */
    public static final DispenseItemBehavior DISPENSE_LIGHTNING_KNIFE_BEHAVIOR = new AbstractProjectileDispenseBehavior() {
        @Override
        protected Projectile getProjectile(Level level, Position position, ItemStack stack) {
            return Util.make(new ThrownLightningKnife(level), (lightningKnife) -> {
                lightningKnife.setPos(position.x(), position.y(), position.z());
                lightningKnife.setItem(stack);
            });
        }

        @Override
        protected float getUncertainty() {
            return 1.5F;
        }
    };

    /**
     * Behavior for dispensing Kingbdogz Hammer projectiles.
     */
    public static final DispenseItemBehavior DISPENSE_KINGBDOGZ_HAMMER_BEHAVIOR = new AbstractProjectileDispenseBehavior() {
        @Override
        public ItemStack execute(BlockSource blockSource, ItemStack stack) {
            Projectile projectile = this.getProjectile(blockSource.getLevel(), DispenserBlock.getDispensePosition(blockSource), stack);
            AetherDispenseBehaviors.spawnProjectile(blockSource, projectile, this.getPower(), this.getUncertainty());
            stack.setDamageValue(stack.getDamageValue() + 1); // Decrease durability by one.
            if (stack.getDamageValue() >= stack.getMaxDamage()) {
                stack.shrink(1); // Shrink stack if the durability is completely used up.
            }
            return stack;
        }

        @Override
        protected Projectile getProjectile(Level level, Position position, ItemStack stack) {
            return Util.make(new HammerProjectile(level), (projectile) -> projectile.setPos(position.x(), position.y(), position.z()));
        }

        @Override
        protected float getUncertainty() {
            return 1.0F;
        }
    };

    /**
     * Based on default dispenser behavior for filled buckets in {@link DispenseItemBehavior#bootStrap()}.
     */
    public static final DispenseItemBehavior SKYROOT_BUCKET_DISPENSE_BEHAVIOR = new DefaultDispenseItemBehavior() {
        private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

        @Override
        public ItemStack execute(BlockSource source, ItemStack stack) {
            DispensibleContainerItem dispensibleContainerItem = (DispensibleContainerItem) stack.getItem();
            BlockPos blockpos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
            Level level = source.getLevel();
            if (dispensibleContainerItem.emptyContents(null, level, blockpos, null)) {
                dispensibleContainerItem.checkExtraContent(null, level, stack, blockpos);
                return new ItemStack(AetherItems.SKYROOT_BUCKET.get());
            } else {
                return this.defaultDispenseItemBehavior.dispense(source, stack);
            }
        }
    };

    /**
     * Based on default dispenser behavior for empty buckets in {@link DispenseItemBehavior#bootStrap()}.
     */
    public static final DispenseItemBehavior SKYROOT_BUCKET_PICKUP_BEHAVIOR = new DefaultDispenseItemBehavior() {
        private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

        @Override
        public ItemStack execute(BlockSource source, ItemStack stack) {
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

    /**
     * Behavior to shoot a projectile.
     * @param source The {@link BlockSource} for the dispenser.
     * @param projectile The {@link Projectile} to dispense.
     * @param velocity The velocity for the projectile, as a {@link Float}.
     * @param inaccuracy The inaccuracy for the projectile, as a {@link Float}.
     */
    protected static void spawnProjectile(BlockSource source, Projectile projectile, float velocity, float inaccuracy) {
        Level level = source.getLevel();
        Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
        projectile.shoot(direction.getStepX(), direction.getStepY(), direction.getStepZ(), velocity, inaccuracy);
        level.addFreshEntity(projectile);
    }
}
