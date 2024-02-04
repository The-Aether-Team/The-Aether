package com.aetherteam.aether.block.dispenser;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.capability.accessory.MobAccessory;
import com.aetherteam.aether.entity.projectile.weapon.HammerProjectile;
import com.aetherteam.aether.entity.projectile.weapon.ThrownLightningKnife;
import com.aetherteam.aether.event.hooks.EntityHooks;
import com.aetherteam.aether.inventory.menu.AccessoriesMenu;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.item.accessories.AccessoryItem;
import com.aetherteam.aether.item.accessories.cape.CapeItem;
import com.aetherteam.aether.item.accessories.gloves.GlovesItem;
import com.aetherteam.aether.item.accessories.miscellaneous.ShieldOfRepulsionItem;
import com.aetherteam.aether.item.accessories.pendant.PendantItem;
import com.aetherteam.aether.item.miscellaneous.bucket.SkyrootBucketItem;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.decoration.ArmorStand;
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
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        BlockPos pos = blockSource.pos().relative(blockSource.state().getValue(DispenserBlock.FACING));
        List<LivingEntity> list = blockSource.level().getEntitiesOfClass(LivingEntity.class, new AABB(pos), EntitySelector.NO_SPECTATORS.and(new EntitySelector.MobCanWearArmorEntitySelector(stack)));
        if (list.isEmpty()) {
            return false;
        } else {
            LivingEntity livingEntity = list.get(0);
            ItemStack itemStack = stack.split(1);
            if (!(livingEntity instanceof ArmorStand armorStand)) {
                CuriosApi.getCurio(itemStack).ifPresent(curio -> CuriosApi.getCuriosInventory(livingEntity).ifPresent(handler -> {
                    Map<String, ICurioStacksHandler> curios = handler.getCurios();
                    for (Map.Entry<String, ICurioStacksHandler> entry : curios.entrySet()) { // Curios entries.
                        if (List.of(AccessoriesMenu.AETHER_IDENTIFIERS).contains(entry.getKey())) { // Check if Curios entries match the ones in the Aether accessories menu.
                            IDynamicStackHandler stackHandler = entry.getValue().getStacks();
                            for (int i = 0; i < stackHandler.getSlots(); i++) {
                                String id = entry.getKey();
                                SlotContext slotContext = new SlotContext(id, livingEntity, i, false, true); // Get slot that a Curio entry has.
                                if (curio.canEquip(slotContext) && curio.canEquipFromUse(slotContext)) {
                                    ItemStack slotStack = stackHandler.getStackInSlot(i);
                                    if (slotStack.isEmpty()) { // Check if Curio slot is empty.
                                        stackHandler.setStackInSlot(i, itemStack.copy()); // Put copy of stack from dispenser into slot.
                                        int count = itemStack.getCount();
                                        itemStack.shrink(count); // Shrink stack in dispenser.
                                        if (livingEntity instanceof Mob mob && EntityHooks.canMobSpawnWithAccessories(mob)) {
                                            MobAccessory.get(mob).ifPresent((accessoryMob) -> {
                                                accessoryMob.setGuaranteedDrop(id);
                                                accessoryMob.getMob().setPersistenceRequired();
                                            });
                                        }
                                    }
                                }
                            }
                        }
                    }
                }));
            } else {
                CuriosApi.getCurio(itemStack).ifPresent(curio -> CuriosApi.getCuriosInventory(livingEntity).ifPresent(handler -> {
                    if (itemStack.is(AetherTags.Items.ACCESSORIES)) {
                        String identifier = "";
                        if (itemStack.getItem() instanceof GlovesItem) {
                            identifier = AetherConfig.COMMON.use_curios_menu.get() ? "hands" : "aether_gloves";
                        } else if (itemStack.getItem() instanceof PendantItem) {
                            identifier = AetherConfig.COMMON.use_curios_menu.get() ? "necklace" : "aether_pendant";
                        } else if (itemStack.getItem() instanceof CapeItem) {
                            identifier = AetherConfig.COMMON.use_curios_menu.get() ? "back" : "aether_cape";
                        } else if (itemStack.getItem() instanceof ShieldOfRepulsionItem) {
                            identifier = AetherConfig.COMMON.use_curios_menu.get() ? "body" : "aether_shield";
                        }
                        Optional<ICurioStacksHandler> stacksHandler = handler.getStacksHandler(identifier);
                        if (stacksHandler.isPresent()) {
                            IDynamicStackHandler stackHandler = stacksHandler.get().getCosmeticStacks();
                            if (0 < stackHandler.getSlots()) {
                                if (stackHandler.getStackInSlot(0).isEmpty()) {
                                    if (itemStack.getItem() instanceof AccessoryItem accessoryItem) {
                                        SlotContext slotContext = new SlotContext(identifier, armorStand, 0, true, true);
                                        if (accessoryItem.canEquip(slotContext, itemStack)) {
                                            stackHandler.setStackInSlot(0, itemStack.copy());
                                            if (accessoryItem instanceof GlovesItem glovesItem) {
                                                armorStand.level().playSound(null, armorStand.blockPosition(), glovesItem.getEquipSound(slotContext, itemStack).soundEvent(), armorStand.getSoundSource(), 1, 1);
                                            } else if (accessoryItem instanceof PendantItem pendantItem) {
                                                armorStand.level().playSound(null, armorStand.blockPosition(), pendantItem.getEquipSound(slotContext, itemStack).soundEvent(), armorStand.getSoundSource(), 1, 1);
                                            } else {
                                                armorStand.level().playSound(null, armorStand.blockPosition(), SoundEvents.ARMOR_EQUIP_GENERIC, armorStand.getSoundSource(), 1, 1);
                                            }
                                            if (identifier.equals("hands") || identifier.equals("aether_gloves")) {
                                                armorStand.setShowArms(true);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }));
            }
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
            Projectile projectile = this.getProjectile(blockSource.level(), DispenserBlock.getDispensePosition(blockSource), stack);
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
        private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

        @Override
        public ItemStack execute(BlockSource source, ItemStack stack) {
            LevelAccessor levelAccessor = source.level();
            BlockPos blockPos = source.pos().relative(source.state().getValue(DispenserBlock.FACING));
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
                        if (source.<DispenserBlockEntity>blockEntity().addItem(new ItemStack(item)) < 0) {
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
        Level level = source.level();
        Direction direction = source.state().getValue(DispenserBlock.FACING);
        projectile.shoot(direction.getStepX(), direction.getStepY(), direction.getStepZ(), velocity, inaccuracy);
        level.addFreshEntity(projectile);
    }
}
