package com.aetherteam.aether.event.hooks;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.aether.attachment.AetherPlayerAttachment;
import com.aetherteam.aether.attachment.LightningTrackerAttachment;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.data.generators.loot.AetherStrippingLoot;
import com.aetherteam.aether.entity.projectile.PoisonNeedle;
import com.aetherteam.aether.entity.projectile.dart.EnchantedDart;
import com.aetherteam.aether.entity.projectile.dart.GoldenDart;
import com.aetherteam.aether.entity.projectile.dart.PoisonDart;
import com.aetherteam.aether.item.EquipmentUtil;
import com.aetherteam.aether.item.accessories.abilities.ZaniteAccessory;
import com.aetherteam.aether.item.tools.abilities.HolystoneTool;
import com.aetherteam.aether.item.tools.abilities.ValkyrieTool;
import com.aetherteam.aether.item.tools.abilities.ZaniteTool;
import com.aetherteam.aether.loot.AetherLoot;
import com.aetherteam.aether.loot.AetherLootContexts;
import com.aetherteam.aether.network.packet.clientbound.ToolDebuffPacket;
import com.aetherteam.nitrogen.attachment.INBTSynchable;
import com.google.common.collect.ImmutableMap;
import io.wispforest.accessories.api.AccessoriesAPI;
import io.wispforest.accessories.api.slot.SlotEntryReference;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.event.entity.EntityStruckByLightningEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class AbilityHooks {
    public static class AccessoryHooks {
        /**
         * Damages an entity's Gloves when they hurt another entity.
         *
         * @param player The attacking {@link Player}.
         * @see com.aetherteam.aether.mixin.mixins.common.PlayerMixin#attack(Entity, CallbackInfo)
         */
        public static void damageGloves(Player player) {
            SlotEntryReference slotResult = EquipmentUtil.getGloves(player);
            if (slotResult != null) {
                if (player.level() instanceof ServerLevel serverLevel) {
                    slotResult.stack().hurtAndBreak(1, serverLevel, player, (item) -> AccessoriesAPI.breakStack(slotResult.reference()));
                }
            }
        }

        /**
         * Damages Zanite Rings when a block is broken.
         *
         * @see com.aetherteam.aether.event.listeners.abilities.AccessoryAbilityListener#onBlockBreak(BlockEvent.BreakEvent)
         */
        public static void damageZaniteRing(LivingEntity entity, LevelAccessor level, BlockState state, BlockPos pos) {
            List<SlotEntryReference> slotResults = EquipmentUtil.getZaniteRings(entity);
            for (SlotEntryReference slotResult : slotResults) {
                if (slotResult != null) {
                    if (state.getDestroySpeed(level, pos) > 0 && entity.getRandom().nextInt(6) == 0) {
                        if (entity.level() instanceof ServerLevel serverLevel) {
                            slotResult.stack().hurtAndBreak(1, serverLevel, entity, (item) -> AccessoriesAPI.breakStack(slotResult.reference()));
                        }
                    }
                }
            }
        }

        /**
         * Damages Zanite Pendant when a block is broken.
         *
         * @see com.aetherteam.aether.event.listeners.abilities.AccessoryAbilityListener#onBlockBreak(BlockEvent.BreakEvent)
         */
        public static void damageZanitePendant(LivingEntity entity, LevelAccessor level, BlockState state, BlockPos pos) {
            SlotEntryReference slotResult = EquipmentUtil.getZanitePendant(entity);
            if (slotResult != null) {
                if (state.getDestroySpeed(level, pos) > 0 && entity.getRandom().nextInt(6) == 0) {
                    if (entity.level() instanceof ServerLevel serverLevel) {
                        slotResult.stack().hurtAndBreak(1, serverLevel, entity, (item) -> AccessoriesAPI.breakStack(slotResult.reference()));
                    }
                }
            }
        }

        /**
         * Handles ability for {@link ZaniteAccessory} for Zanite Rings (accounts for if multiple are equipped).
         *
         * @see ZaniteAccessory#handleMiningSpeed(float, ItemStack)
         * @see com.aetherteam.aether.event.listeners.abilities.AccessoryAbilityListener#onMiningSpeed(PlayerEvent.BreakSpeed)
         */
        public static float handleZaniteRingAbility(LivingEntity entity, float speed) {
            float newSpeed = speed;
            List<SlotEntryReference> slotResults = EquipmentUtil.getZaniteRings(entity);
            for (SlotEntryReference slotResult : slotResults) {
                if (slotResult != null) {
                    newSpeed = ZaniteAccessory.handleMiningSpeed(newSpeed, slotResult.stack());
                }
            }
            return newSpeed;
        }

        /**
         * Handles ability for {@link ZaniteAccessory} for the Zanite Pendant.
         *
         * @see ZaniteAccessory#handleMiningSpeed(float, ItemStack)
         * @see com.aetherteam.aether.event.listeners.abilities.AccessoryAbilityListener#onMiningSpeed(PlayerEvent.BreakSpeed)
         */
        public static float handleZanitePendantAbility(LivingEntity entity, float speed) {
            SlotEntryReference slotResult = EquipmentUtil.getZanitePendant(entity);
            if (slotResult != null) {
                speed = ZaniteAccessory.handleMiningSpeed(speed, slotResult.stack());
            }
            return speed;
        }

        /**
         * Checks whether an entity can be targeted while wearing an Invisibility Cloak.
         *
         * @see com.aetherteam.aether.event.listeners.abilities.AccessoryAbilityListener#onTargetSet(LivingEvent.LivingVisibilityEvent)
         */
        public static boolean preventTargeting(LivingEntity target, @Nullable Entity lookingEntity) {
            if (target instanceof Player player) {
                var data = player.getData(AetherDataAttachments.AETHER_PLAYER);
                return lookingEntity != null
                        && !lookingEntity.getType().is(AetherTags.Entities.IGNORE_INVISIBILITY)
                        && data.isWearingInvisibilityCloak()
                        && data.isInvisibilityEnabled()
                        && !data.attackedWithInvisibility();
            } else {
                return lookingEntity != null
                        && !lookingEntity.getType().is(AetherTags.Entities.IGNORE_INVISIBILITY)
                        && EquipmentUtil.hasInvisibilityCloak(target);
            }
        }

        /**
         * Checks if an entity recently attacked while wearing an Invisibility Cloak.
         *
         * @see com.aetherteam.aether.event.listeners.abilities.AccessoryAbilityListener#onTargetSet(LivingEvent.LivingVisibilityEvent)
         */
        public static boolean recentlyAttackedWithInvisibility(LivingEntity target, Entity lookingEntity) {
            if (target instanceof Player player) {
                var data = player.getData(AetherDataAttachments.AETHER_PLAYER);
                return !lookingEntity.getType().is(AetherTags.Entities.IGNORE_INVISIBILITY)
                        && data.isWearingInvisibilityCloak()
                        && data.isInvisibilityEnabled()
                        && data.attackedWithInvisibility();
            } else {
                return false;
            }
        }

        /**
         * Sets that the player recently attacked.
         *
         * @see com.aetherteam.aether.event.listeners.abilities.AccessoryAbilityListener#onEntityHurt(net.neoforged.neoforge.event.entity.living.LivingAttackEvent)
         */
        public static void setAttack(DamageSource source) {
            if (source.getEntity() instanceof Player player) {
                player.getData(AetherDataAttachments.AETHER_PLAYER).setAttackedWithInvisibility(true);
            }
        }

        /**
         * Prevents magma block damage when wearing ice accessories.
         *
         * @see com.aetherteam.aether.event.listeners.abilities.AccessoryAbilityListener#onEntityHurt(net.neoforged.neoforge.event.entity.living.LivingAttackEvent)
         */
        public static boolean preventMagmaDamage(LivingEntity entity, DamageSource source) {
            return source == entity.level().damageSources().hotFloor() && EquipmentUtil.hasFreezingAccessory(entity);
        }
    }

    public static class ArmorHooks {
        /**
         * Cancels fall damage if the wearer either has Sentry Boots, a full Gravitite Armor set, or a full Valkyrie Armor set.
         *
         * @param entity The {@link LivingEntity} wearing the armor.
         * @return Whether the wearer's fall damage should be cancelled, as a {@link Boolean}.
         * @see com.aetherteam.aether.event.listeners.abilities.ArmorAbilityListener#onEntityFall(LivingFallEvent)
         */
        public static boolean fallCancellation(LivingEntity entity) {
            return EquipmentUtil.hasSentryBoots(entity) || EquipmentUtil.hasFullGravititeSet(entity) || EquipmentUtil.hasFullValkyrieSet(entity);
        }
    }

    public static class ToolHooks {
        /**
         * Blocks able to be stripped with {@link ItemAbilities#AXE_STRIP}, and the equivalent result block.
         */
        public static final Map<Block, Block> STRIPPABLES = (new ImmutableMap.Builder<Block, Block>())
                .put(AetherBlocks.SKYROOT_LOG.get(), AetherBlocks.STRIPPED_SKYROOT_LOG.get())
                .put(AetherBlocks.GOLDEN_OAK_LOG.get(), AetherBlocks.STRIPPED_SKYROOT_LOG.get())
                .put(AetherBlocks.SKYROOT_WOOD.get(), AetherBlocks.STRIPPED_SKYROOT_WOOD.get())
                .put(AetherBlocks.GOLDEN_OAK_WOOD.get(), AetherBlocks.STRIPPED_SKYROOT_WOOD.get())
                .build();

        /**
         * Blocks able to be flattened with {@link ItemAbilities#SHOVEL_FLATTEN}, and the equivalent result block.
         */
        public static final Map<Block, Block> FLATTENABLES = (new ImmutableMap.Builder<Block, Block>())
                .put(AetherBlocks.AETHER_GRASS_BLOCK.get(), AetherBlocks.AETHER_DIRT_PATH.get())
                .put(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get(), AetherBlocks.AETHER_DIRT_PATH.get())
                .put(AetherBlocks.AETHER_DIRT.get(), AetherBlocks.AETHER_DIRT_PATH.get())
                .build();

        /**
         * Blocks able to be tilled with {@link ItemAbilities#HOE_TILL}, and the equivalent result block.
         */
        public static final Map<Block, Block> TILLABLES = (new ImmutableMap.Builder<Block, Block>())
                .put(AetherBlocks.AETHER_DIRT.get(), AetherBlocks.AETHER_FARMLAND.get())
                .put(AetherBlocks.AETHER_GRASS_BLOCK.get(), AetherBlocks.AETHER_FARMLAND.get())
                .put(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get(), AetherBlocks.AETHER_FARMLAND.get())
                .put(AetherBlocks.AETHER_DIRT_PATH.get(), AetherBlocks.AETHER_FARMLAND.get())
                .build();

        public static boolean debuffTools;

        /**
         * Handles modifying blocks when a {@link ItemAbility} is performed on them.
         *
         * @param accessor The {@link LevelAccessor} of the level.
         * @param pos      The {@link Block} within the level.
         * @param old      The old {@link BlockState} of the block an action is being performed on.
         * @param action   The {@link ItemAbility} being performed on the block.
         * @return The new {@link BlockState} of the block.
         * @see com.aetherteam.aether.event.listeners.abilities.ToolAbilityListener#setupToolModifications(BlockEvent.BlockToolModificationEvent)
         */
        public static BlockState setupItemAbilities(LevelAccessor accessor, BlockPos pos, BlockState old, ItemAbility action) {
            Block oldBlock = old.getBlock();
            if (action == ItemAbilities.AXE_STRIP) {
                if (STRIPPABLES.containsKey(oldBlock)) {
                    return STRIPPABLES.get(oldBlock).withPropertiesOf(old);
                }
            } else if (action == ItemAbilities.SHOVEL_FLATTEN) {
                if (FLATTENABLES.containsKey(oldBlock)) {
                    return FLATTENABLES.get(oldBlock).withPropertiesOf(old);
                }
            } else if (action == ItemAbilities.HOE_TILL) {
                if (accessor.getBlockState(pos.above()).isAir()) {
                    if (TILLABLES.containsKey(oldBlock)) {
                        return TILLABLES.get(oldBlock).withPropertiesOf(old);
                    }
                }
            }
            return old;
        }

        /**
         * Handles ability for {@link com.aetherteam.aether.item.tools.abilities.HolystoneTool}.
         *
         * @see HolystoneTool#dropAmbrosium(Player, Level, BlockPos, ItemStack, BlockState)
         * @see com.aetherteam.aether.event.listeners.abilities.ToolAbilityListener#doHolystoneAbility(BlockEvent.BreakEvent)
         */
        public static void handleHolystoneToolAbility(Player player, Level level, BlockPos pos, ItemStack stack, BlockState blockState) {
            if (stack.getItem() instanceof HolystoneTool holystoneTool) {
                holystoneTool.dropAmbrosium(player, level, pos, stack, blockState);
            }
        }

        /**
         * Handles ability for {@link com.aetherteam.aether.item.tools.abilities.ZaniteTool}.
         *
         * @see ZaniteTool#increaseSpeed(ItemStack, float)
         * @see com.aetherteam.aether.event.listeners.abilities.ToolAbilityListener#modifyBreakSpeed(PlayerEvent.BreakSpeed)
         */
        public static float handleZaniteToolAbility(ItemStack stack, float speed) {
            if (stack.getItem() instanceof ZaniteTool zaniteTool) {
                return zaniteTool.increaseSpeed(stack, speed);
            }
            return speed;
        }

        /**
         * Debuffs the mining speed of blocks if they're Aether blocks (according to the description id, and the tags {@link AetherTags.Blocks#TREATED_AS_AETHER_BLOCK} and {@link AetherTags.Blocks#TREATED_AS_VANILLA_BLOCK}),
         * and if the item is non-Aether (according to the description id, and the tag {@link AetherTags.Items#TREATED_AS_AETHER_ITEM}), as long as it's not an empty item (no item at all) and as long as its detected as the correct tool type for the block.<br><br>
         * The debuffed value is the original value to the power of -0.2.
         *
         * @param state The {@link BlockState} of the block being mined.
         * @param stack The {@link ItemStack} being used for mining.
         * @param speed The mining speed of the stack, as a {@link Float}.
         * @return The debuffed mining speed, as a {@link Float}.
         * @see com.aetherteam.aether.event.listeners.abilities.ToolAbilityListener#modifyBreakSpeed(PlayerEvent.BreakSpeed)
         */
        public static float reduceToolEffectiveness(Player player, BlockState state, ItemStack stack, float speed) {
            if (AetherConfig.SERVER.tools_debuff.get()) {
                if (!player.level().isClientSide() && player.level() instanceof ServerLevel serverLevel && player instanceof ServerPlayer serverPlayer) {
                    debuffTools = true;
                    PacketDistributor.sendToPlayersNear(serverLevel, serverPlayer, player.getX(), player.getY(), player.getZ(), 10, new ToolDebuffPacket(true));
                }
            }
            if (debuffTools) {
                if ((state.getBlock().getDescriptionId().startsWith("block.aether.") || state.is(AetherTags.Blocks.TREATED_AS_AETHER_BLOCK)) && !state.is(AetherTags.Blocks.TREATED_AS_VANILLA_BLOCK)) {
                    if (!stack.isEmpty() && stack.isCorrectToolForDrops(state) && !stack.getItem().getDescriptionId().startsWith("item.aether.") && !stack.is(AetherTags.Items.TREATED_AS_AETHER_ITEM)) {
//                        speed = (float) Math.max(Math.pow(speed, speed > 1.0 ? -0.5 : 1.5), 1.0);
                        speed = 1.0F;
                    }
                }
            }
            return speed;
        }

        /**
         * Spawns Golden Amber at the user's click position when stripping Golden Oak Logs ({@link AetherTags.Blocks#GOLDEN_OAK_LOGS}), as long as the tool in usage can harvest Golden Amber ({@link AetherTags.Items#GOLDEN_AMBER_HARVESTERS}).<br><br>
         * The drops are handled using a special loot context type {@link AetherLootContexts#STRIPPING}, used for a loot table found in {@link AetherStrippingLoot}.
         *
         * @param accessor The {@link LevelAccessor} of the level.
         * @param state    The {@link BlockState} an action is being performed on.
         * @param stack    The {@link ItemStack} performing an action.
         * @param action   The {@link ItemAbility} being performed.
         * @param context  The {@link UseOnContext} of this interaction.
         * @see com.aetherteam.aether.event.listeners.abilities.ToolAbilityListener#doGoldenOakStripping(BlockEvent.BlockToolModificationEvent)
         */
        public static void stripGoldenOak(LevelAccessor accessor, BlockState state, ItemStack stack, ItemAbility action, UseOnContext context) {
            if (action == ItemAbilities.AXE_STRIP) {
                if (accessor instanceof Level level) {
                    if (state.is(AetherTags.Blocks.GOLDEN_OAK_LOGS) && stack.is(AetherTags.Items.GOLDEN_AMBER_HARVESTERS)) {
                        if (level.getServer() != null && level instanceof ServerLevel serverLevel) {
                            Vec3 vector = context.getClickLocation();
                            LootParams parameters = new LootParams.Builder(serverLevel).withParameter(LootContextParams.TOOL, stack).create(AetherLootContexts.STRIPPING);
                            LootTable lootTable = level.getServer().reloadableRegistries().getLootTable(AetherLoot.STRIP_GOLDEN_OAK);
                            List<ItemStack> list = lootTable.getRandomItems(parameters);
                            for (ItemStack itemStack : list) {
                                ItemEntity itemEntity = new ItemEntity(level, vector.x(), vector.y(), vector.z(), itemStack);
                                itemEntity.setDefaultPickUpDelay();
                                level.addFreshEntity(itemEntity);
                            }
                        }
                    }
                }
            }
        }

        /**
         * Checks if an entity is too far away for the player to be able to interact with if they're trying to interact using a hand that doesn't contain a {@link ValkyrieTool}, but are still holding a Valkyrie Tool in another hand.
         *
         * @param target The target {@link Entity} being interacted with.
         * @param player The {@link Player} attempting to interact.
         * @param hand   The {@link InteractionHand} used to interact.
         * @return Whether the player is too far to interact, as a {@link Boolean}.
         */
        public static boolean entityTooFar(Entity target, Player player, InteractionHand hand) {
            if (hand == InteractionHand.OFF_HAND && hasValkyrieItemInMainHandOnly(player)) {
                AttributeInstance attackRange = player.getAttribute(Attributes.ENTITY_INTERACTION_RANGE);
                if (attackRange != null) {
                    AttributeModifier valkyrieModifier = attackRange.getModifier(ValkyrieTool.ENTITY_INTERACTION_RANGE_MODIFIER_UUID);
                    if (valkyrieModifier != null) {
                        double range = player.getAttributeValue(Attributes.ENTITY_INTERACTION_RANGE) - valkyrieModifier.amount();
                        double trueReach = range == 0 ? 0 : range + (player.isCreative() ? 3 : 0); // [CODE COPY] - IForgePlayer#getAttackRange().
                        return !player.isCloseEnough(target, trueReach);
                    }
                }
            }
            return false;
        }

        /**
         * Checks if a block is too far away for the player to be able to interact with if they're trying to interact using a hand that doesn't contain a {@link ValkyrieTool}, but are still holding a Valkyrie Tool in another hand.
         *
         * @param player The {@link Player} attempting to interact.
         * @param hand   The {@link InteractionHand} used to interact.
         * @return Whether the player is too far to interact, as a {@link Boolean}.
         */
        public static boolean blockTooFar(Player player, InteractionHand hand) {
            if (hand == InteractionHand.OFF_HAND && hasValkyrieItemInMainHandOnly(player)) {
                AttributeInstance reachDistance = player.getAttribute(Attributes.BLOCK_INTERACTION_RANGE);
                if (reachDistance != null) {
                    AttributeModifier valkyrieModifier = reachDistance.getModifier(ValkyrieTool.BLOCK_INTERACTION_RANGE_MODIFIER_UUID);
                    if (valkyrieModifier != null) {
                        double reach = player.getAttributeValue(Attributes.BLOCK_INTERACTION_RANGE) - valkyrieModifier.amount();
                        double trueReach = reach == 0 ? 0 : reach + (player.isCreative() ? 0.5 : 0); // [CODE COPY] - IForgePlayer#getReachDistance().
                        return player.pick(trueReach, 0.0F, false).getType() != HitResult.Type.BLOCK;
                    }
                }
            }
            return false;
        }

        /**
         * Checks if the player is holding a {@link ValkyrieTool} in the main hand.
         *
         * @param player The {@link Player} holding the Valkyrie Tool.
         * @return A {@link Boolean} of whether the player is holding a Valkyrie Tool in the main hand.
         */
        public static boolean hasValkyrieItemInMainHandOnly(Player player) {
            ItemStack mainHandStack = player.getMainHandItem();
            ItemStack offHandStack = player.getOffhandItem();
            return mainHandStack.getItem() instanceof ValkyrieTool && !(offHandStack.getItem() instanceof ValkyrieTool);
        }
    }

    public static class WeaponHooks {
        /**
         * Renders Darts that hit the player as stuck on their model, similar to Arrows.<br><br>
         * This is done through increasing values stored in {@link AetherPlayerAttachment} that track the amount of different darts stuck in the player.
         *
         * @param entity The hurt {@link LivingEntity}.
         * @param source The {@link DamageSource} that hurt the entity.
         * @see com.aetherteam.aether.event.listeners.abilities.WeaponAbilityListener#onDartHurt(LivingHurtEvent)
         */
        public static void stickDart(LivingEntity entity, DamageSource source) {
            if (entity instanceof Player player && !player.level().isClientSide()) {
                Entity sourceEntity = source.getDirectEntity();
                var data = player.getData(AetherDataAttachments.AETHER_PLAYER);
                if (sourceEntity instanceof GoldenDart) {
                    data.setSynched(player.getId(), INBTSynchable.Direction.CLIENT, "setGoldenDartCount", data.getGoldenDartCount() + 1);
                } else if (sourceEntity instanceof PoisonDart || sourceEntity instanceof PoisonNeedle) {
                    data.setSynched(player.getId(), INBTSynchable.Direction.CLIENT, "setPoisonDartCount", data.getPoisonDartCount() + 1);
                } else if (sourceEntity instanceof EnchantedDart) {
                    data.setSynched(player.getId(), INBTSynchable.Direction.CLIENT, "setEnchantedDartCount", data.getEnchantedDartCount() + 1);
                }
            }
        }

        /**
         * Sets the hit entity on fire for the amount of seconds the Phoenix Arrow has stored, as determined by {@link com.aetherteam.aether.item.combat.loot.PhoenixBowItem#customArrow(AbstractArrow, ItemStack)}.
         *
         * @param result     The {@link HitResult} of the projectile.
         * @param projectile The {@link Projectile} that hit something.
         * @see com.aetherteam.aether.event.listeners.abilities.WeaponAbilityListener#onArrowHit(ProjectileImpactEvent)
         */
        public static void phoenixArrowHit(HitResult result, Projectile projectile) {
            if (result instanceof EntityHitResult entityHitResult && projectile instanceof AbstractArrow abstractArrow) {
                Entity impactedEntity = entityHitResult.getEntity();
                if (impactedEntity.getType() == EntityType.ENDERMAN) {
                    return;
                }
                if (abstractArrow.hasData(AetherDataAttachments.PHOENIX_ARROW)) {
                    var data = abstractArrow.getData(AetherDataAttachments.PHOENIX_ARROW);
                    if (data.isPhoenixArrow() && data.getFireTime() > 0) {
                        impactedEntity.setRemainingFireTicks(data.getFireTime());
                    }
                }
            }
        }

        /**
         * Prevents an entity from being hurt by a lightning strike if {@link LightningTrackerAttachment#getOwner(Level)} finds an owner associated with the lightning, if it was summoned through usage of a weapon.
         *
         * @param entity    The {@link Entity} struck by the lightning bolt.
         * @param lightning The {@link LightningBolt} that struck the entity.
         * @return Whether the entity being hurt by the lightning strike should be prevented, as a {@link Boolean}.
         * @see com.aetherteam.aether.event.listeners.abilities.WeaponAbilityListener#onLightningStrike(EntityStruckByLightningEvent)
         */
        public static boolean lightningTracking(Entity entity, LightningBolt lightning) {
            if (entity instanceof LivingEntity livingEntity) {
                if (lightning.hasData(AetherDataAttachments.LIGHTNING_TRACKER)) {
                    var tracker = lightning.getData(AetherDataAttachments.LIGHTNING_TRACKER);
                    Entity owner = tracker.getOwner(lightning.level());
                    if (owner != null) {
                        return livingEntity == owner || livingEntity == owner.getVehicle() || owner.getPassengers().contains(livingEntity);
                    }
                }
            }
            return false;
        }

        /**
         * Reduces the effectiveness of non-Aether weapons against Aether mobs.
         *
         * @param target The target {@link LivingEntity} being attacked.
         * @param source The attacking {@link Entity}.
         * @param damage The original damage as a {@link Float}.
         * @return The modified damage as a {@link Float}.
         * @see com.aetherteam.aether.event.listeners.abilities.WeaponAbilityListener#onEntityDamage(LivingDamageEvent)
         */
        public static float reduceWeaponEffectiveness(LivingEntity target, Entity source, float damage) {
//            if (AetherConfig.SERVER.tools_debuff.get() && !target.level().isClientSide()) { // Checks if tool debuffs are enabled and if the level is on the server side.
//                double pow = Math.max(Math.pow(damage, damage > 1.0 ? 0.6 : 1.6), 1.0);
//                if (source instanceof LivingEntity livingEntity) {
//                    ItemStack stack = livingEntity.getMainHandItem();
//                    if ((target.getType().getDescriptionId().startsWith("entity.aether") || target.getType().is(AetherTags.Entities.TREATED_AS_AETHER_ENTITY)) && !target.getType().is(AetherTags.Entities.TREATED_AS_VANILLA_ENTITY)) { // Checks if the target is an Aether entity.
//                        if (!stack.isEmpty() && !stack.getAttributeModifiers(EquipmentSlot.MAINHAND).isEmpty() && stack.getAttributeModifiers(EquipmentSlot.MAINHAND).containsKey(Attributes.ATTACK_DAMAGE) && !stack.getAttributeModifiers(EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE).isEmpty()) { // Checks if the attacking item is a weapon.
//                            double value = stack.getAttributeModifiers(EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE).stream().mapToDouble(AttributeModifier::getAmount).sum(); // Used for checking if the attack damage from the item is greater than the attacker's default (fist).
//                            if (value > livingEntity.getAttributeBaseValue(Attributes.ATTACK_DAMAGE) && !stack.getItem().getDescriptionId().startsWith("item.aether.") && !stack.is(AetherTags.Items.TREATED_AS_AETHER_ITEM)) { // Checks if the attacking item is non-Aether.
//                                damage = (float) pow;
//                            }
//                        }
//                    }
//                } else if (source instanceof Projectile) { // Used for reducing projectile weapon effectiveness.
//                    if ((target.getType().getDescriptionId().startsWith("entity.aether") || target.getType().is(AetherTags.Entities.TREATED_AS_AETHER_ENTITY)) && !target.getType().is(AetherTags.Entities.TREATED_AS_VANILLA_ENTITY)) { // Checks if the target is an Aether entity.
//                        if ((!source.getType().getDescriptionId().startsWith("entity.aether") && !source.getType().is(AetherTags.Entities.TREATED_AS_AETHER_ENTITY)) // Checks if the projectile is non-Aether.
//                                && (!(source instanceof AbstractArrow abstractArrow) || !abstractArrow.hasData(AetherDataAttachments.PHOENIX_ARROW) || !abstractArrow.getData(AetherDataAttachments.PHOENIX_ARROW).isPhoenixArrow())) { // Special check against Phoenix Arrows.
//                            damage = (float) pow;
//                        }
//                    }
//                }
//            }
            return damage;
        }

        /**
         * Reduces the effectiveness of non-Aether armor against Aether mobs.
         *
         * @param target The target {@link LivingEntity} wearing the armor.
         * @param source The attacking {@link Entity}.
         * @param damage The original damage as a {@link Float}.
         * @return The modified damage as a {@link Float}.
         * @see com.aetherteam.aether.event.listeners.abilities.WeaponAbilityListener#onEntityDamage(LivingDamageEvent)
         */
        public static float reduceArmorEffectiveness(LivingEntity target, @Nullable Entity source, float damage) {
//            if (source != null) {
//                if ((source.getType().getDescriptionId().startsWith("entity.aether") || source.getType().is(AetherTags.Entities.TREATED_AS_AETHER_ENTITY) && !source.getType().is(AetherTags.Entities.TREATED_AS_VANILLA_ENTITY))) { // Checks if the attacker is an Aether entity.
//                    for (ItemStack stack : target.getArmorSlots()) {
//                        if (stack.getItem() instanceof ArmorItem armorItem && !stack.getItem().getDescriptionId().startsWith("item.aether.") && !stack.is(AetherTags.Items.TREATED_AS_AETHER_ITEM)) { // Checks if the armor is non-Aether.
//                            if (!stack.getAttributeModifiers(armorItem.getEquipmentSlot()).isEmpty() && stack.getAttributeModifiers(armorItem.getEquipmentSlot()).containsKey(Attributes.ARMOR) && !stack.getAttributeModifiers(armorItem.getEquipmentSlot()).get(Attributes.ARMOR).isEmpty()) { // Checks if the armor has an armor modifier attribute.
//                                double value = stack.getAttributeModifiers(armorItem.getEquipmentSlot()).get(Attributes.ARMOR).stream().mapToDouble((attributeModifier) -> attributeModifier.getAmount() / 15).sum();
//                                damage += value;
//                            }
//                        }
//                    }
//                }
//            }
            return damage;
        }
    }
}
