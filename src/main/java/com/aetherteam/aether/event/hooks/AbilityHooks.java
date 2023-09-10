package com.aetherteam.aether.event.hooks;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.capability.arrow.PhoenixArrow;
import com.aetherteam.aether.capability.lightning.LightningTracker;
import com.aetherteam.aether.capability.lightning.LightningTrackerCapability;
import com.aetherteam.aether.capability.player.AetherPlayer;
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
import com.aetherteam.aether.network.AetherPacketHandler;
import com.aetherteam.aether.network.packet.clientbound.ToolDebuffPacket;
import com.aetherteam.nitrogen.capability.INBTSynchable;
import com.aetherteam.nitrogen.network.PacketRelay;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AbilityHooks {
    public static class AccessoryHooks {
        /**
         * Damages an entity's Gloves when they hurt another entity.
         * @param player The attacking {@link Player}.
         * @see com.aetherteam.aether.mixin.mixins.common.PlayerMixin#attack(Entity, CallbackInfo)
         */
        public static void damageGloves(Player player) {
            SlotResult slotResult = EquipmentUtil.getGloves(player);
            if (slotResult != null) {
                slotResult.stack().hurtAndBreak(1, player, wearer -> CuriosApi.getCuriosHelper().onBrokenCurio(slotResult.slotContext()));
            }
        }

        /**
         * Damages Zanite Rings when a block is broken.
         * @see com.aetherteam.aether.event.listeners.abilities.AccessoryAbilityListener#onBlockBreak(BlockEvent.BreakEvent)
         */
        public static void damageZaniteRing(LivingEntity entity, LevelAccessor level, BlockState state, BlockPos pos) {
            List<SlotResult> slotResults = EquipmentUtil.getZaniteRings(entity);
            for (SlotResult slotResult : slotResults) {
                if (slotResult != null) {
                    if (state.getDestroySpeed(level, pos) > 0 && entity.getRandom().nextInt(6) == 0) {
                        slotResult.stack().hurtAndBreak(1, entity, wearer -> CuriosApi.getCuriosHelper().onBrokenCurio(slotResult.slotContext()));
                    }
                }
            }
        }

        /**
         * Damages Zanite Pendant when a block is broken.
         * @see com.aetherteam.aether.event.listeners.abilities.AccessoryAbilityListener#onBlockBreak(BlockEvent.BreakEvent)
         */
        public static void damageZanitePendant(LivingEntity entity, LevelAccessor level, BlockState state, BlockPos pos) {
            SlotResult slotResult = EquipmentUtil.getZanitePendant(entity);
            if (slotResult != null) {
                if (state.getDestroySpeed(level, pos) > 0 && entity.getRandom().nextInt(6) == 0) {
                    slotResult.stack().hurtAndBreak(1, entity, wearer -> CuriosApi.getCuriosHelper().onBrokenCurio(slotResult.slotContext()));
                }
            }
        }

        /**
         * Handles ability for {@link ZaniteAccessory} for Zanite Rings (accounts for if multiple are equipped).
         * @see ZaniteAccessory#handleMiningSpeed(float, ItemStack)
         * @see com.aetherteam.aether.event.listeners.abilities.AccessoryAbilityListener#onMiningSpeed(PlayerEvent.BreakSpeed)
         */
        public static float handleZaniteRingAbility(LivingEntity entity, float speed) {
            float newSpeed = speed;
            List<SlotResult> slotResults = EquipmentUtil.getZaniteRings(entity);
            for (SlotResult slotResult : slotResults) {
                if (slotResult != null) {
                    newSpeed = ZaniteAccessory.handleMiningSpeed(newSpeed, slotResult.stack());
                }
            }
            return newSpeed;
        }

        /**
         * Handles ability for {@link ZaniteAccessory} for the Zanite Pendant.
         * @see ZaniteAccessory#handleMiningSpeed(float, ItemStack)
         * @see com.aetherteam.aether.event.listeners.abilities.AccessoryAbilityListener#onMiningSpeed(PlayerEvent.BreakSpeed)
         */
        public static float handleZanitePendantAbility(LivingEntity entity, float speed) {
            SlotResult slotResult = EquipmentUtil.getZanitePendant(entity);
            if (slotResult != null) {
                speed = ZaniteAccessory.handleMiningSpeed(speed, slotResult.stack());
            }
            return speed;
        }

        /**
         * Checks whether an entity can be targeted while wearing an Invisibility Cloak.
         * @see com.aetherteam.aether.event.listeners.abilities.AccessoryAbilityListener#onTargetSet(LivingEvent.LivingVisibilityEvent)
         */
        public static boolean preventTargeting(LivingEntity target, @Nullable Entity lookingEntity) {
            if (target instanceof Player player && AetherPlayer.get(player).isPresent() && AetherPlayer.get(player).resolve().isPresent()) {
                return lookingEntity != null
                        && !lookingEntity.getType().is(AetherTags.Entities.IGNORE_INVISIBILITY)
                        && AetherPlayer.get(player).resolve().get().isWearingInvisibilityCloak()
                        && AetherPlayer.get(player).resolve().get().isInvisibilityEnabled()
                        && !AetherPlayer.get(player).resolve().get().attackedWithInvisibility();
            } else {
                return lookingEntity != null
                        && !lookingEntity.getType().is(AetherTags.Entities.IGNORE_INVISIBILITY)
                        && EquipmentUtil.hasInvisibilityCloak(target);
            }
        }

        /**
         * Checks if an entity recently attacked while wearing an Invisibility Cloak.
         * @see com.aetherteam.aether.event.listeners.abilities.AccessoryAbilityListener#onTargetSet(LivingEvent.LivingVisibilityEvent)
         */
        public static boolean recentlyAttackedWithInvisibility(LivingEntity target, Entity lookingEntity) {
            if (target instanceof Player player && AetherPlayer.get(player).isPresent() && AetherPlayer.get(player).resolve().isPresent()) {
                return !lookingEntity.getType().is(AetherTags.Entities.IGNORE_INVISIBILITY)
                        && AetherPlayer.get(player).resolve().get().isWearingInvisibilityCloak()
                        && AetherPlayer.get(player).resolve().get().isInvisibilityEnabled()
                        && AetherPlayer.get(player).resolve().get().attackedWithInvisibility();
            } else {
                return false;
            }
        }

        /**
         * Sets that the player recently attacked.
         * @see com.aetherteam.aether.event.listeners.abilities.AccessoryAbilityListener#onEntityHurt(net.minecraftforge.event.entity.living.LivingAttackEvent)
         */
        public static void setAttack(DamageSource source) {
            if (source.getEntity() instanceof Player player) {
                AetherPlayer.get(player).ifPresent(aetherPlayer -> aetherPlayer.setAttackedWithInvisibility(true));
            }
        }

        /**
         * Prevents magma block damage when wearing ice accessories.
         * @see com.aetherteam.aether.event.listeners.abilities.AccessoryAbilityListener#onEntityHurt(net.minecraftforge.event.entity.living.LivingAttackEvent)
         */
        public static boolean preventMagmaDamage(LivingEntity entity, DamageSource source) {
            return source == DamageSource.HOT_FLOOR && EquipmentUtil.hasFreezingAccessory(entity);
        }
    }

    public static class ArmorHooks {
        /**
         * Cancels fall damage if the wearer either has Sentry Boots, a full Gravitite Armor set, or a full Valkyrie Armor set.
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
         * Blocks able to be stripped with {@link ToolActions#AXE_STRIP}, and the equivalent result block.
         */
        public static final Map<Block, Block> STRIPPABLES = (new ImmutableMap.Builder<Block, Block>())
                .put(AetherBlocks.SKYROOT_LOG.get(), AetherBlocks.STRIPPED_SKYROOT_LOG.get())
                .put(AetherBlocks.GOLDEN_OAK_LOG.get(), AetherBlocks.STRIPPED_SKYROOT_LOG.get())
                .put(AetherBlocks.SKYROOT_WOOD.get(), AetherBlocks.STRIPPED_SKYROOT_WOOD.get())
                .put(AetherBlocks.GOLDEN_OAK_WOOD.get(), AetherBlocks.STRIPPED_SKYROOT_WOOD.get())
                .build();

        /**
         * Blocks able to be flattened with {@link ToolActions#SHOVEL_FLATTEN}, and the equivalent result block.
         */
        public static final Map<Block, Block> FLATTENABLES = (new ImmutableMap.Builder<Block, Block>())
                .put(AetherBlocks.AETHER_GRASS_BLOCK.get(), AetherBlocks.AETHER_DIRT_PATH.get())
                .put(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get(), AetherBlocks.AETHER_DIRT_PATH.get())
                .put(AetherBlocks.AETHER_DIRT.get(), AetherBlocks.AETHER_DIRT_PATH.get())
                .build();

        /**
         * Blocks able to be tilled with {@link ToolActions#HOE_TILL}, and the equivalent result block.
         */
        public static final Map<Block, Block> TILLABLES = (new ImmutableMap.Builder<Block, Block>())
                .put(AetherBlocks.AETHER_DIRT.get(), AetherBlocks.AETHER_FARMLAND.get())
                .put(AetherBlocks.AETHER_GRASS_BLOCK.get(), AetherBlocks.AETHER_FARMLAND.get())
                .put(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get(), AetherBlocks.AETHER_FARMLAND.get())
                .put(AetherBlocks.AETHER_DIRT_PATH.get(), AetherBlocks.AETHER_FARMLAND.get())
                .build();

        public static boolean debuffTools;

        /**
         * Handles modifying blocks when a {@link ToolAction} is performed on them.
         * @param accessor The {@link LevelAccessor} of the level.
         * @param pos The {@link Block} within the level.
         * @param old The old {@link BlockState} of the block an action is being performed on.
         * @param action The {@link ToolAction} being performed on the block.
         * @return The new {@link BlockState} of the block.
         * @see com.aetherteam.aether.event.listeners.abilities.ToolAbilityListener#setupToolModifications(BlockEvent.BlockToolModificationEvent)
         */
        public static BlockState setupToolActions(LevelAccessor accessor, BlockPos pos, BlockState old, ToolAction action) {
            Block oldBlock = old.getBlock();
            if (action == ToolActions.AXE_STRIP) {
                if (STRIPPABLES.containsKey(oldBlock)) {
                    return STRIPPABLES.get(oldBlock).withPropertiesOf(old);
                }
            } else if (action == ToolActions.SHOVEL_FLATTEN) {
                if (FLATTENABLES.containsKey(oldBlock)) {
                    return FLATTENABLES.get(oldBlock).withPropertiesOf(old);
                }
            } else if (action == ToolActions.HOE_TILL) {
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
         * @param state The {@link BlockState} of the block being mined.
         * @param stack The {@link ItemStack} being used for mining.
         * @param speed The mining speed of the stack, as a {@link Float}.
         * @return The debuffed mining speed, as a {@link Float}.
         * @see com.aetherteam.aether.event.listeners.abilities.ToolAbilityListener#modifyBreakSpeed(PlayerEvent.BreakSpeed)
         */
        public static float reduceToolEffectiveness(Player player, BlockState state, ItemStack stack, float speed) {
            if (AetherConfig.SERVER.tools_debuff.get()) {
                if (!player.getLevel().isClientSide()) {
                    debuffTools = true;
                    PacketRelay.sendToNear(AetherPacketHandler.INSTANCE, new ToolDebuffPacket(true), player.getX(), player.getY(), player.getZ(), 10, player.getLevel().dimension());
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
         * The drops are handled using a special loot context type {@link AetherLootContexts#STRIPPING}, used for a loot table found in AetherStrippingLoot.
         * @param accessor The {@link LevelAccessor} of the level.
         * @param state The {@link BlockState} an action is being performed on.
         * @param stack The {@link ItemStack} performing an action.
         * @param action The {@link ToolAction} being performed.
         * @param context The {@link UseOnContext} of this interaction.
         * @see com.aetherteam.aether.event.listeners.abilities.ToolAbilityListener#doGoldenOakStripping(BlockEvent.BlockToolModificationEvent)
         */
        public static void stripGoldenOak(LevelAccessor accessor, BlockState state, ItemStack stack, ToolAction action, UseOnContext context) {
            if (action == ToolActions.AXE_STRIP) {
                if (accessor instanceof Level level) {
                    if (state.is(AetherTags.Blocks.GOLDEN_OAK_LOGS) && stack.is(AetherTags.Items.GOLDEN_AMBER_HARVESTERS)) {
                        if (level.getServer() != null && level instanceof ServerLevel serverLevel) {
                            Vec3 vector = context.getClickLocation();
                            LootContext.Builder lootContext = new LootContext.Builder(serverLevel).withParameter(LootContextParams.TOOL, stack);
                            LootTable lootTable = level.getServer().getLootTables().get(AetherLoot.STRIP_GOLDEN_OAK);
                            List<ItemStack> list = lootTable.getRandomItems(lootContext.create(AetherLootContexts.STRIPPING));
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
         * Checks if the player is holding a {@link ValkyrieTool} in the main hand.
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
         * This is done through increasing values stored in {@link com.aetherteam.aether.capability.player.AetherPlayerCapability} that track the amount of different darts stuck in the player.
         * @param entity The hurt {@link LivingEntity}.
         * @param source The {@link DamageSource} that hurt the entity.
         * @see com.aetherteam.aether.event.listeners.abilities.WeaponAbilityListener#onDartHurt(LivingHurtEvent)
         */
        public static void stickDart(LivingEntity entity, DamageSource source) {
            if (entity instanceof Player player && !player.getLevel().isClientSide()) {
                Entity sourceEntity = source.getDirectEntity();
                if (sourceEntity instanceof GoldenDart) {
                    AetherPlayer.get(player).ifPresent(aetherPlayer -> aetherPlayer.setSynched(INBTSynchable.Direction.CLIENT, "setGoldenDartCount", aetherPlayer.getGoldenDartCount() + 1));
                } else if (sourceEntity instanceof PoisonDart || sourceEntity instanceof PoisonNeedle) {
                    AetherPlayer.get(player).ifPresent(aetherPlayer -> aetherPlayer.setSynched(INBTSynchable.Direction.CLIENT, "setPoisonDartCount", aetherPlayer.getPoisonDartCount() + 1));
                } else if (sourceEntity instanceof EnchantedDart) {
                    AetherPlayer.get(player).ifPresent(aetherPlayer -> aetherPlayer.setSynched(INBTSynchable.Direction.CLIENT, "setEnchantedDartCount", aetherPlayer.getEnchantedDartCount() + 1));
                }
            }
        }

        /**
         * Sets the hit entity on fire for the amount of seconds the Phoenix Arrow has stored, as determined by {@link com.aetherteam.aether.item.combat.loot.PhoenixBowItem#customArrow(AbstractArrow)}.
         * @param result The {@link HitResult} of the projectile.
         * @param projectile The {@link Projectile} that hit something.
         * @see com.aetherteam.aether.event.listeners.abilities.WeaponAbilityListener#onArrowHit(ProjectileImpactEvent)
         */
        public static void phoenixArrowHit(HitResult result, Projectile projectile) {
            if (result instanceof EntityHitResult entityHitResult && projectile instanceof AbstractArrow abstractArrow) {
                Entity impactedEntity = entityHitResult.getEntity();
                if (impactedEntity.getType() == EntityType.ENDERMAN) {
                    return;
                }
                PhoenixArrow.get(abstractArrow).ifPresent(phoenixArrow -> {
                    if (phoenixArrow.isPhoenixArrow() && phoenixArrow.getFireTime() > 0) {
                        impactedEntity.setSecondsOnFire(phoenixArrow.getFireTime());
                    }
                });
            }
        }

        /**
         * Prevents an entity from being hurt by a lightning strike if {@link LightningTrackerCapability#getOwner()} finds an owner associated with the lightning, if it was summoned through usage of a weapon.
         * @param entity The {@link Entity} struck by the lightning bolt.
         * @param lightning The {@link LightningBolt} that struck the entity.
         * @return Whether the entity being hurt by the lightning strike should be prevented, as a {@link Boolean}.
         * @see com.aetherteam.aether.event.listeners.abilities.WeaponAbilityListener#onLightningStrike(EntityStruckByLightningEvent)
         */
        public static boolean lightningTracking(Entity entity, LightningBolt lightning) {
            if (entity instanceof LivingEntity livingEntity) {
                Optional<LightningTracker> lightningTrackerOptional = LightningTracker.get(lightning).resolve();
                if (lightningTrackerOptional.isPresent()) {
                    LightningTracker lightningTracker = lightningTrackerOptional.get();
                    if (lightningTracker.getOwner() != null) {
                        return livingEntity == lightningTracker.getOwner() || livingEntity == lightningTracker.getOwner().getVehicle();
                    }
                }
            }
            return false;
        }

        /**
         * Reduces the effectiveness of non-Aether weapons against Aether mobs.
         * @param target The target {@link LivingEntity} being attacked.
         * @param source The attacking {@link Entity}.
         * @param damage The original damage as a {@link Float}.
         * @return The modified damage as a {@link Float}.
         * @see com.aetherteam.aether.event.listeners.abilities.WeaponAbilityListener#onEntityDamage(LivingDamageEvent)
         */
        public static float reduceWeaponEffectiveness(LivingEntity target, Entity source, float damage) {
            if (AetherConfig.SERVER.tools_debuff.get() && !target.getLevel().isClientSide()) { // Checks if tool debuffs are enabled and if the level is on the server side.
                double pow = Math.max(Math.pow(damage, damage > 1.0 ? 0.6 : 1.6), 1.0);
                if (source instanceof LivingEntity livingEntity) {
                    ItemStack stack = livingEntity.getMainHandItem();
                    if ((target.getType().getDescriptionId().startsWith("entity.aether") || target.getType().is(AetherTags.Entities.TREATED_AS_AETHER_ENTITY)) && !target.getType().is(AetherTags.Entities.TREATED_AS_VANILLA_ENTITY)) { // Checks if the target is an Aether entity.
                        if (!stack.isEmpty() && !stack.getAttributeModifiers(EquipmentSlot.MAINHAND).isEmpty() && !stack.getAttributeModifiers(EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE).isEmpty()) { // Checks if the attacking item is a weapon.
                            double value = stack.getAttributeModifiers(EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE).stream().mapToDouble(AttributeModifier::getAmount).sum(); // Used for checking if the attack damage from the item is greater than the attacker's default (fist).
                            if (value > livingEntity.getAttributeBaseValue(Attributes.ATTACK_DAMAGE) && !stack.getItem().getDescriptionId().startsWith("item.aether.") && !stack.is(AetherTags.Items.TREATED_AS_AETHER_ITEM)) { // Checks if the attacking item is non-Aether.
                                damage = (float) pow;
                            }
                        }
                    }
                } else if (source instanceof Projectile) { // Used for reducing projectile weapon effectiveness.
                    if ((target.getType().getDescriptionId().startsWith("entity.aether") || target.getType().is(AetherTags.Entities.TREATED_AS_AETHER_ENTITY)) && !target.getType().is(AetherTags.Entities.TREATED_AS_VANILLA_ENTITY)) { // Checks if the target is an Aether entity.
                        if ((!source.getType().getDescriptionId().startsWith("entity.aether") && !source.getType().is(AetherTags.Entities.TREATED_AS_AETHER_ENTITY)) // Checks if the projectile is non-Aether.
                                && (!(source instanceof AbstractArrow abstractArrow) || !PhoenixArrow.get(abstractArrow).isPresent() || PhoenixArrow.get(abstractArrow).resolve().isEmpty() || !PhoenixArrow.get(abstractArrow).resolve().get().isPhoenixArrow())) { // Special check against Phoenix Arrows.
                            damage = (float) pow;
                        }
                    }
                }
            }
            return damage;
        }

        /**
         * Reduces the effectiveness of non-Aether armor against Aether mobs.
         * @param target The target {@link LivingEntity} wearing the armor.
         * @param source The attacking {@link Entity}.
         * @param damage The original damage as a {@link Float}.
         * @return The modified damage as a {@link Float}.
         * @see com.aetherteam.aether.event.listeners.abilities.WeaponAbilityListener#onEntityDamage(LivingDamageEvent)
         */
        public static float reduceArmorEffectiveness(LivingEntity target, @Nullable Entity source, float damage) {
            if (source != null) {
                if ((source.getType().getDescriptionId().startsWith("entity.aether") || source.getType().is(AetherTags.Entities.TREATED_AS_AETHER_ENTITY) && !source.getType().is(AetherTags.Entities.TREATED_AS_VANILLA_ENTITY))) { // Checks if the attacker is an Aether entity.
                    for (ItemStack stack : target.getArmorSlots()) {
                        if (stack.getItem() instanceof ArmorItem armorItem && !stack.getItem().getDescriptionId().startsWith("item.aether.") && !stack.is(AetherTags.Items.TREATED_AS_AETHER_ITEM)) { // Checks if the armor is non-Aether.
                            if (!stack.getAttributeModifiers(armorItem.getEquipmentSlot(stack)).isEmpty() && !stack.getAttributeModifiers(armorItem.getEquipmentSlot(stack)).get(Attributes.ARMOR).isEmpty()) { // Checks if the armor has an armor modifier attribute.
                                double value = stack.getAttributeModifiers(armorItem.getEquipmentSlot(stack)).get(Attributes.ARMOR).stream().mapToDouble((attributeModifier) -> attributeModifier.getAmount() / 15).sum();
                                damage += value;
                            }
                        }
                    }
                }
            }
            return damage;
        }
    }
}
