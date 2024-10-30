package com.aetherteam.aether.block.dispenser;

import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.aether.event.hooks.EntityHooks;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.item.accessories.gloves.GlovesItem;
import com.aetherteam.aether.item.combat.loot.HammerOfKingbdogzItem;
import com.aetherteam.aether.item.miscellaneous.bucket.SkyrootBucketItem;
import io.wispforest.accessories.api.AccessoriesAPI;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.EquipAction;
import io.wispforest.accessories.api.slot.SlotReference;
import io.wispforest.accessories.api.slot.SlotTypeReference;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class AetherDispenseBehaviors {
    /**
     * Behavior for allowing dispensers to equip accessories to players.
     */
    public static final DispenseItemBehavior DISPENSE_ACCESSORY_BEHAVIOR = new DefaultDispenseItemBehavior() {
        @Override
        protected ItemStack execute(BlockSource blockSource, ItemStack stack) {
            return dispenseAccessory(blockSource, stack) ? stack : super.execute(blockSource, stack);
        }
    };

    /**
     * Based on {@link net.minecraft.world.item.ArmorItem#dispenseArmor(BlockSource, ItemStack)} and {@link io.wispforest.accessories.impl.AccessoriesEventHandler#attemptEquipFromUse(Player, InteractionHand)}.<br><br>
     * Handles checking if an accessory shot from a dispenser can be equipped, and handles that equipping behavior if it can.
     *
     * @param blockSource The {@link BlockSource} for the dispenser.
     * @param stack       The {@link ItemStack} in the dispenser.
     * @return Whether the accessory can be dispensed, as a {@link Boolean}.
     */
    public static boolean dispenseAccessory(BlockSource blockSource, ItemStack stack) {
        BlockPos pos = blockSource.pos().relative(blockSource.state().getValue(DispenserBlock.FACING));
        List<LivingEntity> list = blockSource.level().getEntitiesOfClass(LivingEntity.class, new AABB(pos), EntitySelector.NO_SPECTATORS.and(new EntitySelector.MobCanWearArmorEntitySelector(stack)));
        if (list.isEmpty()) {
            return false;
        } else {
            LivingEntity livingEntity = list.getFirst();
            ItemStack itemStack = stack.split(1);
            AccessoriesCapability capability = AccessoriesCapability.get(livingEntity);
            if (capability != null) {
                Accessory accessory = AccessoriesAPI.getOrDefaultAccessory(itemStack);
                Pair<SlotReference, EquipAction> equipReference = capability.canEquipAccessory(itemStack, true);
                if (equipReference != null) {
                    SlotTypeReference slotTypeReference = new SlotTypeReference(equipReference.first().slotName());
                    if (accessory.canEquip(itemStack, equipReference.first())) {
                        accessory.onEquipFromUse(itemStack, equipReference.left());
                        equipReference.second().equipStack(itemStack.copy());
                        if (livingEntity instanceof ArmorStand armorStand) {
                            if (equipReference.first().slotName().equals(GlovesItem.getStaticIdentifier().slotName())) {
                                armorStand.setShowArms(true);
                            }
                        } else if (livingEntity instanceof Mob mob && EntityHooks.canMobSpawnWithAccessories(mob)) {
                            mob.getData(AetherDataAttachments.MOB_ACCESSORY).setGuaranteedDrop(slotTypeReference);
                            mob.setPersistenceRequired();
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Behavior for dispensing Kingbdogz Hammer projectiles.
     */
    public static final DispenseItemBehavior DISPENSE_KINGBDOGZ_HAMMER_BEHAVIOR = new DefaultDispenseItemBehavior() {
        @Override
        public ItemStack execute(BlockSource source, ItemStack stack) {
            Level level = source.level();
            Direction direction = source.state().getValue(DispenserBlock.FACING);
            HammerOfKingbdogzItem item = (HammerOfKingbdogzItem) AetherItems.HAMMER_OF_KINGBDOGZ.get();
            ProjectileItem.DispenseConfig config = item.createDispenseConfig();

            Position position = config.positionFunction().getDispensePosition(source, direction);
            Projectile projectile = item.asProjectile(level, position, stack, direction);
            item.shoot(projectile, direction.getStepX(), direction.getStepY(), direction.getStepZ(), config.power(), config.uncertainty());
            level.addFreshEntity(projectile);
            stack.setDamageValue(stack.getDamageValue() + 1); // Decrease durability by one.
            if (stack.getDamageValue() >= stack.getMaxDamage()) {
                stack.shrink(1); // Shrink stack if the durability is completely used up.
            }
            return stack;
        }

        @Override
        protected void playSound(BlockSource source) {
            source.level().levelEvent(1002, source.pos(), 0);
        }
    };

    /**
     * [CODE COPY] - {@link DispenseItemBehavior#bootStrap()}.<br><br>
     * Based on default dispenser behavior for filled buckets.
     */
    public static final DispenseItemBehavior SKYROOT_BUCKET_DISPENSE_BEHAVIOR = new DefaultDispenseItemBehavior() {
        private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

        @Override
        public ItemStack execute(BlockSource source, ItemStack stack) {
            DispensibleContainerItem dispensibleContainerItem = (DispensibleContainerItem) stack.getItem();
            BlockPos blockpos = source.pos().relative(source.state().getValue(DispenserBlock.FACING));
            Level level = source.level();
            if (dispensibleContainerItem.emptyContents(null, level, blockpos, null, stack)) {
                dispensibleContainerItem.checkExtraContent(null, level, stack, blockpos);
                return new ItemStack(AetherItems.SKYROOT_BUCKET.get());
            } else {
                return this.defaultDispenseItemBehavior.dispense(source, stack);
            }
        }
    };

    /**
     * [CODE COPY] - {@link DispenseItemBehavior#bootStrap()}.<br><br>
     * Based on default dispenser behavior for empty buckets.
     */
    public static final DispenseItemBehavior SKYROOT_BUCKET_PICKUP_BEHAVIOR = new DefaultDispenseItemBehavior() {
        @Override
        public ItemStack execute(BlockSource source, ItemStack stack) {
            LevelAccessor levelAccessor = source.level();
            BlockPos blockPos = source.pos().relative(source.state().getValue(DispenserBlock.FACING));
            BlockState blockState = levelAccessor.getBlockState(blockPos);
            Block block = blockState.getBlock();
            if (block instanceof BucketPickup bucketPickup) {
                ItemStack bucketStack = bucketPickup.pickupBlock(null, levelAccessor, blockPos, blockState);
                bucketStack = SkyrootBucketItem.swapBucketType(bucketStack);
                if (bucketStack.isEmpty()) {
                    return super.execute(source, stack);
                } else {
                    levelAccessor.gameEvent(null, GameEvent.FLUID_PICKUP, blockPos);
                    Item item = bucketStack.getItem();
                    return this.consumeWithRemainder(source, stack, new ItemStack(item));
                }
            } else {
                return super.execute(source, stack);
            }
        }
    };
}
