package com.aetherteam.aether.event.listeners;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.event.hooks.EntityHooks;
import io.wispforest.accessories.api.*;
import io.wispforest.accessories.api.events.OnDeathCallback;
import io.wispforest.accessories.api.slot.SlotReference;
import it.unimi.dsi.fastutil.Pair;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityMountEvent;
import net.neoforged.neoforge.event.entity.EntityStruckByLightningEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class EntityListener {
    /**
     * @see Aether#eventSetup(IEventBus)
     */
    public static void listen(IEventBus bus) {
        bus.addListener(EntityListener::onEntityJoin);
        bus.addListener(EntityListener::onMountEntity);
        bus.addListener(EntityListener::onRiderTick);
        bus.addListener(EntityListener::onInteractWithEntity);
        bus.addListener(EntityListener::onProjectileHitEntity);
        bus.addListener(EntityListener::onShieldBlock);
        bus.addListener(EntityListener::onLightningStrike);
        bus.addListener(EntityListener::onPlayerDrops);
        bus.addListener(EntityListener::onDropExperience);
        bus.addListener(EntityListener::onEffectApply);
        bus.addListener(EntityListener::onEntitySplit);
        bus.addListener(EntityListener::onLoadPlayerFile);

        OnDeathCallback.EVENT.register((currentState, entity, capability, damageSource, droppedStacks) -> {
            List<ItemStack> droppedStacksCopy = new ArrayList<>(droppedStacks);
            boolean recentlyHit = entity.hurtMarked;
            int looting = EnchantmentHelper.getEnchantmentLevel(entity.level().registryAccess().holderOrThrow(Enchantments.LOOTING), entity);
            droppedStacks.clear();
            droppedStacks.addAll(EntityHooks.handleEntityAccessoryDrops(entity, droppedStacksCopy, recentlyHit, looting));
            return TriState.DEFAULT;
        });
    }

    /**
     * @see EntityHooks#addGoals(Entity)
     * @see EntityHooks#canMobSpawnWithAccessories(Entity)
     */
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        EntityHooks.addGoals(entity);
    }

    /**
     * @see EntityHooks#dismountPrevention(Entity, Entity, boolean)
     */
    public static void onMountEntity(EntityMountEvent event) {
        Entity riderEntity = event.getEntityMounting();
        Entity mountEntity = event.getEntityBeingMounted();
        boolean isDismounting = event.isDismounting();
        event.setCanceled(EntityHooks.dismountPrevention(riderEntity, mountEntity, isDismounting));
    }

    /**
     * @see EntityHooks#launchMount(Player)
     */
    public static void onRiderTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        EntityHooks.launchMount(player);
    }

    /**
     * @see EntityHooks#skyrootBucketMilking(Entity, Player, InteractionHand)
     * @see EntityHooks#pickupBucketable(Entity, Player, InteractionHand)
     * @see EntityHooks#interactWithArmorStand(Entity, Player, ItemStack, Vec3, InteractionHand)
     */
    public static void onInteractWithEntity(PlayerInteractEvent.EntityInteractSpecific event) {
        Entity targetEntity = event.getTarget();
        Player player = event.getEntity();
        ItemStack itemStack = event.getItemStack();
        Vec3 position = event.getLocalPos();
        InteractionHand interactionHand = event.getHand();
        EntityHooks.skyrootBucketMilking(targetEntity, player, interactionHand);
        Optional<InteractionResult> result = EntityHooks.pickupBucketable(targetEntity, player, interactionHand);
        if (result.isEmpty()) {
            result = EntityHooks.interactWithArmorStand(targetEntity, player, itemStack, position, interactionHand);
        }
        result.ifPresent(event::setCancellationResult);
        event.setCanceled(result.isPresent());
    }

    /**
     * @see EntityHooks#preventEntityHooked(Entity, HitResult)
     */
    public static void onProjectileHitEntity(ProjectileImpactEvent event) {
        Entity projectileEntity = event.getEntity();
        HitResult rayTraceResult = event.getRayTraceResult();
        event.setCanceled(EntityHooks.preventEntityHooked(projectileEntity, rayTraceResult));
    }

    /**
     * @see EntityHooks#preventSliderShieldBlock(DamageSource)
     */
    public static void onShieldBlock(LivingShieldBlockEvent event) {
        if (!event.isCanceled()) {
            event.setCanceled(EntityHooks.preventSliderShieldBlock(event.getDamageSource()));
        }
    }

    /**
     * @see EntityHooks#lightningHitKeys(Entity)
     */
    public static void onLightningStrike(EntityStruckByLightningEvent event) {
        Entity entity = event.getEntity();
        LightningBolt lightningBolt = event.getLightning();
        if (EntityHooks.lightningHitKeys(entity) || EntityHooks.thunderCrystalHitItems(entity, lightningBolt)) {
            event.setCanceled(true);
        }
    }

    /**
     * @see EntityHooks#trackDrops(LivingEntity, Collection)
     */
    public static void onPlayerDrops(LivingDropsEvent event) {
        LivingEntity entity = event.getEntity();
        Collection<ItemEntity> itemDrops = event.getDrops();
        EntityHooks.trackDrops(entity, itemDrops);
    }

    /**
     * @see EntityHooks#modifyExperience(LivingEntity, int)
     */
    public static void onDropExperience(LivingExperienceDropEvent event) {
        LivingEntity livingEntity = event.getEntity();
        int experience = event.getDroppedExperience();
        int newExperience = EntityHooks.modifyExperience(livingEntity, experience);
        event.setDroppedExperience(newExperience);
    }

    /**
     * @see EntityHooks#preventInebriation(LivingEntity, MobEffectInstance)
     */
    public static void onEffectApply(MobEffectEvent.Applicable event) {
        LivingEntity livingEntity = event.getEntity();
        MobEffectInstance effectInstance = event.getEffectInstance();
        if (EntityHooks.preventInebriation(livingEntity, effectInstance)) {
            event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
        }
    }

    /**
     * @see EntityHooks#preventSplit(Mob)
     */
    public static void onEntitySplit(MobSplitEvent event) {
        Mob mob = event.getParent();
        if (EntityHooks.preventSplit(mob)) {
            event.setCanceled(true);
        }
    }

    public static void onLoadPlayerFile(PlayerEvent.LoadFromFile event) {
        Player player = event.getEntity();
        if (player instanceof ServerPlayer serverPlayer) {
            CompoundTag playerTag = serverPlayer.server.getWorldData().getLoadedPlayerTag();
            if (playerTag != null) {
                CompoundTag capsTag = null;
                if (playerTag.contains("ForgeCaps")) {
                    capsTag = playerTag.getCompound("ForgeCaps");
                } else if (playerTag.contains("neoforge:attachments")) {
                    capsTag = playerTag.getCompound("neoforge:attachments");
                }
                if (capsTag != null && capsTag.contains("curios:inventory")) {
                    CompoundTag curiosInventoryTag = capsTag.getCompound("curios:inventory");
                    if (curiosInventoryTag.contains("Curios")) {
                        Tag curiosTag = curiosInventoryTag.get("Curios");
                        if (curiosTag instanceof ListTag curiosListTag) {
                            for (Tag tag : curiosListTag) {
                                if (tag instanceof CompoundTag compoundTag && compoundTag.contains("StacksHandler") && compoundTag.contains("Identifier")) {
                                    CompoundTag stacksHandlerTag = compoundTag.getCompound("StacksHandler");
                                    if (stacksHandlerTag.contains("Stacks")) {
                                        CompoundTag stacksTag = stacksHandlerTag.getCompound("Stacks");
                                        if (stacksTag.contains("Items")) {
                                            Tag itemsTag = stacksTag.get("Items");
                                            if (itemsTag instanceof ListTag listTag) {
                                                for (Tag itemTag : listTag) {
                                                    if (itemTag instanceof CompoundTag itemCompoundTag) {
                                                        if (itemCompoundTag.contains("id")) {
                                                            Item item = BuiltInRegistries.ITEM.get(ResourceLocation.parse(itemCompoundTag.getString("id")));
                                                            if (item != Items.AIR) {
                                                                ItemStack stack = new ItemStack(item);
                                                                AccessoriesCapability accessories = AccessoriesCapability.get(player);
                                                                if (accessories != null) {
                                                                    Accessory accessory = AccessoriesAPI.getOrDefaultAccessory(stack);
                                                                    Pair<SlotReference, EquipAction> equipReference = accessories.canEquipAccessory(stack, true);
                                                                    if (accessory.canEquip(stack, equipReference.first())) {
                                                                        equipReference.second().equipStack(stack.copy());
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
