package com.gildedgames.aether.common.event.listeners;

import com.gildedgames.aether.common.item.accessories.abilities.IZaniteAccessory;
import com.gildedgames.aether.common.item.accessories.gloves.GlovesItem;
import com.gildedgames.aether.common.registry.AetherItems;
import com.gildedgames.aether.common.registry.AetherLoot;
import com.gildedgames.aether.common.registry.AetherTags;
import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber
public class AbilityListener
{
    private static final Map<Integer, NonNullList<Item>> doubleDropEntities = new HashMap<>();

    @SubscribeEvent
    public static void trackEntityDeath(LivingDeathEvent event) {
        if (event.getSource() instanceof EntityDamageSource) {
            LivingEntity entity = event.getEntityLiving();
            EntityDamageSource source = (EntityDamageSource) event.getSource();
            if (source.getDirectEntity() instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) source.getDirectEntity();
                ItemStack stack = player.getItemInHand(Hand.MAIN_HAND);
                Item item = stack.getItem();
                if (item == AetherItems.SKYROOT_SWORD.get() && !entity.getType().is(AetherTags.Entities.NO_SKYROOT_DOUBLE_DROPS)) {
                    NonNullList<Item> inventory = NonNullList.create();
                    for (ItemStack handStack : entity.getHandSlots()) {
                        inventory.add(handStack.getItem());
                    }
                    for (ItemStack armorStack : entity.getArmorSlots()) {
                        inventory.add(armorStack.getItem());
                    }
                    inventory.removeIf((storedItem -> storedItem == Items.AIR));
                    doubleDropEntities.put(entity.getId(), inventory);
                }
            }
        }
    }
    
    @SubscribeEvent
    public static void doSkyrootDoubleDrops(LivingDropsEvent event) {
        if (event.getSource() instanceof EntityDamageSource) {
            LivingEntity entity = event.getEntityLiving();
            EntityDamageSource source = (EntityDamageSource) event.getSource();
            if (source.getDirectEntity() instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) source.getDirectEntity();
                ItemStack stack = player.getItemInHand(Hand.MAIN_HAND);
                Item item = stack.getItem();
                if (item == AetherItems.SKYROOT_SWORD.get() && !entity.getType().is(AetherTags.Entities.NO_SKYROOT_DOUBLE_DROPS)) {
                    ArrayList<ItemEntity> newDrops = new ArrayList<>(event.getDrops().size());
                    for (ItemEntity drop : event.getDrops()) {
                        ItemStack droppedStack = drop.getItem();
                        if (!droppedStack.getItem().is(AetherTags.Items.NO_SKYROOT_DOUBLE_DROPS)) {
                            ItemEntity dropEntity = new ItemEntity(entity.level, drop.getX(), drop.getY(), drop.getZ(), droppedStack.copy());
                            dropEntity.setDefaultPickUpDelay();
                            newDrops.add(dropEntity);
                        }
                    }
                    if (doubleDropEntities.containsKey(entity.getId())) {
                        NonNullList<Item> inventory = doubleDropEntities.get(entity.getId());
                        newDrops.removeIf(newDrop -> inventory.contains(newDrop.getItem().getItem()));
                        doubleDropEntities.remove(entity.getId());
                    }
                    event.getDrops().addAll(newDrops);
                }
            }
        }
    }

    @SubscribeEvent
    public static void doGoldenOakStripping(PlayerInteractEvent.RightClickBlock event) {
        World world = event.getWorld();
        ItemStack stack = event.getItemStack();
        if (stack.getItem() instanceof AxeItem) {
            BlockState blockState = world.getBlockState(event.getPos());
            if (blockState.getBlock().is(AetherTags.Blocks.GOLDEN_OAK_LOGS)) {
                if (world.getServer() != null) {
                    Vector3d vector = event.getHitVec().getLocation();
                    LootContext.Builder lootContext = new LootContext.Builder((ServerWorld) world)
                            .withParameter(LootParameters.BLOCK_STATE, blockState)
                            .withParameter(LootParameters.ORIGIN, vector)
                            .withParameter(LootParameters.TOOL, stack);
                    LootTable loottable = world.getServer().getLootTables().get(AetherLoot.STRIP_GOLDEN_OAK);
                    List<ItemStack> list = loottable.getRandomItems(lootContext.create(AetherLoot.STRIPPING));

                    for(ItemStack itemstack : list) {
                        ItemEntity itementity = new ItemEntity(world, vector.x(), vector.y(), vector.z(), itemstack);
                        world.addFreshEntity(itementity);
                    }
                }
            }
        }
    }



    @SubscribeEvent
    public static void onPlayerAttack(AttackEntityEvent event) {
        PlayerEntity player = event.getPlayer();
        Entity target = event.getTarget();
        if (!player.level.isClientSide() && target instanceof LivingEntity) {
            LivingEntity livingTarget = (LivingEntity) target;
            if (livingTarget.isAttackable() && !livingTarget.skipAttackInteraction(player)) {
                CuriosApi.getCuriosHelper().findEquippedCurio((stack) -> stack.getItem() instanceof GlovesItem, player).ifPresent((triple) -> triple.getRight().hurtAndBreak(1, player, (entity) -> entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND)));
            }
        }
    }

    @SubscribeEvent
    public static void onMiningSpeed(PlayerEvent.BreakSpeed event) {
        CuriosApi.getCuriosHelper().findEquippedCurio(AetherItems.ZANITE_RING.get(), event.getPlayer()).ifPresent((triple) -> IZaniteAccessory.handleMiningSpeed(event, triple));
        CuriosApi.getCuriosHelper().findEquippedCurio(AetherItems.ZANITE_PENDANT.get(), event.getPlayer()).ifPresent((triple) -> IZaniteAccessory.handleMiningSpeed(event, triple));
    }

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity livingEntity = event.getEntityLiving();
        if (CuriosApi.getCuriosHelper().findEquippedCurio(AetherItems.AGILITY_CAPE.get(), livingEntity).isPresent()) {
            livingEntity.maxUpStep = !livingEntity.isCrouching() ? 1.0F : 0.6F;
        } else {
            livingEntity.maxUpStep = 0.6F;
        }
    }

    //TODO: Make sure this doesn't ever affect projectiles shot by the player/entity wearing the shield.
    @SubscribeEvent
    public static void onProjectileImpact(ProjectileImpactEvent event) {
        if (event.getRayTraceResult().getType() == RayTraceResult.Type.ENTITY) {
            Entity impactedEntity = ((EntityRayTraceResult) event.getRayTraceResult()).getEntity();
            if (impactedEntity instanceof LivingEntity && event.getEntity() instanceof ProjectileEntity) {
                LivingEntity impactedLiving = (LivingEntity) impactedEntity;
                ProjectileEntity projectile = (ProjectileEntity) event.getEntity();
                if (projectile.getType().is(AetherTags.Entities.DEFLECTABLE_PROJECTILES)) {
                    CuriosApi.getCuriosHelper().findEquippedCurio(AetherItems.REPULSION_SHIELD.get(), impactedLiving).ifPresent((triple) -> {
                        event.setCanceled(true);
                        if (impactedEntity instanceof PlayerEntity) {
                            IAetherPlayer.get((PlayerEntity) impactedLiving).ifPresent(aetherPlayer -> {
                                aetherPlayer.setProjectileImpactedMaximum(150);
                                aetherPlayer.setProjectileImpactedTimer(150);
                            });
                        }
                        projectile.setDeltaMovement(projectile.getDeltaMovement().scale(-0.25D));
                        triple.getRight().hurtAndBreak(1, impactedLiving, (entity) -> CuriosApi.getCuriosHelper().onBrokenCurio(triple.getLeft(), triple.getMiddle(), entity));
                    });
                }
            }
        }
    }
}
