package com.gildedgames.aether.common.event.listeners.abilities;

import com.gildedgames.aether.common.registry.AetherItems;
import com.gildedgames.aether.core.util.EquipmentUtil;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.Map;

@Mod.EventBusSubscriber
public class ArmorAbilityListener
{
    @SubscribeEvent
    public static void onEntityUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (EquipmentUtil.hasFullNeptuneSet(entity)) {
            if (entity.isInWaterOrBubble()) {
                float defaultBoost = 1.55F;
                float depthStriderModifier = Math.min(EnchantmentHelper.getDepthStrider(entity), 3.0F);
                if (depthStriderModifier > 0.0F) {
                    defaultBoost += depthStriderModifier * 0.15F;
                }
                Vec3 movement = entity.getDeltaMovement().multiply(defaultBoost, 1.0F, defaultBoost);
                entity.move(MoverType.SELF, movement);
            }
        } else if (EquipmentUtil.hasFullPhoenixSet(entity)) {
            entity.clearFire();
            if (entity.isInLava()) {
                entity.fallDistance *= 0.0F;
                float defaultBoost = 10.5F;
                float depthStriderModifier = Math.min(EnchantmentHelper.getDepthStrider(entity), 3.0F);
                if (depthStriderModifier > 0.0F) {
                    defaultBoost += depthStriderModifier * 1.5F;
                }
                Vec3 movement = entity.getDeltaMovement().multiply(defaultBoost, 0.25F, defaultBoost);
                entity.move(MoverType.SELF, movement);
            }
            if (entity.level instanceof ServerLevel) {
                ServerLevel world = (ServerLevel) entity.level;
                world.sendParticles(ParticleTypes.FLAME,
                        entity.getX() + (world.getRandom().nextGaussian() / 5.0D),
                        entity.getY() + (world.getRandom().nextGaussian() / 3.0D),
                        entity.getZ() + (world.getRandom().nextGaussian() / 5.0D),
                        1, 0.0D, 0.0D, 0.0D, 0.0F);
            }
        }

        if (entity.isInWaterRainOrBubble()) {
            for (EquipmentSlot equipmentslottype : EquipmentSlot.values()) {
                if (equipmentslottype.getType() == EquipmentSlot.Type.ARMOR) {
                    ItemStack equippedStack = entity.getItemBySlot(equipmentslottype);
                    if (equippedStack.getItem() == AetherItems.PHOENIX_HELMET.get()) {
                        breakPhoenixArmor(entity, equippedStack, new ItemStack(AetherItems.OBSIDIAN_HELMET.get()), equipmentslottype);
                    } else if (equippedStack.getItem() == AetherItems.PHOENIX_CHESTPLATE.get()) {
                        breakPhoenixArmor(entity, equippedStack, new ItemStack(AetherItems.OBSIDIAN_CHESTPLATE.get()), equipmentslottype);
                    } else if (equippedStack.getItem() == AetherItems.PHOENIX_LEGGINGS.get()) {
                        breakPhoenixArmor(entity, equippedStack, new ItemStack(AetherItems.OBSIDIAN_LEGGINGS.get()), equipmentslottype);
                    } else if (equippedStack.getItem() == AetherItems.PHOENIX_BOOTS.get()) {
                        breakPhoenixArmor(entity, equippedStack, new ItemStack(AetherItems.OBSIDIAN_BOOTS.get()), equipmentslottype);
                    }
                }
            }
            CuriosApi.getCuriosHelper().findFirstCurio(entity, AetherItems.PHOENIX_GLOVES.get()).ifPresent((slotResult) -> breakPhoenixGloves(entity, slotResult, new ItemStack(AetherItems.OBSIDIAN_GLOVES.get())));
        }
    }

    @SubscribeEvent
    public static void onEntityJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (EquipmentUtil.hasFullGravititeSet(entity)) {
            if (entity instanceof Player) {
                Player player = (Player) entity;
                if (!player.isShiftKeyDown()) {
                    player.push(0.0, 1.0, 0.0);
                    if (player instanceof ServerPlayer) {
                        ((ServerPlayer) player).connection.send(new ClientboundSetEntityMotionPacket(player));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntityFall(LivingFallEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.getItemBySlot(EquipmentSlot.FEET).getItem() == AetherItems.SENTRY_BOOTS.get() || EquipmentUtil.hasFullGravititeSet(entity) || EquipmentUtil.hasFullValkyrieSet(entity)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onEntityAttack(LivingAttackEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (EquipmentUtil.hasFullPhoenixSet(entity)) {
            if (event.getSource().isFire()) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onEntityHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (EquipmentUtil.hasFullObsidianSet(entity)) {
            float originalDamage = event.getAmount();
            event.setAmount(originalDamage / 2);
        }
    }

    private static void breakPhoenixArmor(LivingEntity entity, ItemStack equippedStack, ItemStack outcomeStack, EquipmentSlot equipmentslottype) {
        if (entity.level.getGameTime() % 10 == 0) {
            equippedStack.hurtAndBreak(1, entity, (p) -> p.broadcastBreakEvent(equipmentslottype));
            if (entity.getItemBySlot(equipmentslottype).isEmpty()) {
                EnchantmentHelper.setEnchantments(EnchantmentHelper.getEnchantments(equippedStack), outcomeStack);
                if (equippedStack.hasTag()) {
                    outcomeStack.setTag(equippedStack.getTag());
                }
                entity.setItemSlot(equipmentslottype, outcomeStack);
            }
        }
    }

    private static void breakPhoenixGloves(LivingEntity entity, SlotResult slotResult, ItemStack outcomeStack) {
        if (entity.level.getGameTime() % 10 == 0) {
            slotResult.stack().hurtAndBreak(1, entity, (p) -> p.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            if (CuriosApi.getCuriosHelper().findFirstCurio(entity, AetherItems.PHOENIX_GLOVES.get()).isEmpty()) {
                EnchantmentHelper.setEnchantments(EnchantmentHelper.getEnchantments(slotResult.stack()), outcomeStack);
                if (slotResult.stack().hasTag()) {
                    outcomeStack.setTag(slotResult.stack().getTag());
                }
                CuriosApi.getCuriosHelper().getCuriosHandler(entity).ifPresent(iCuriosItemHandler -> {
                    Map<String, ICurioStacksHandler> curios = iCuriosItemHandler.getCurios();
                    ICurioStacksHandler inv = curios.get(slotResult.slotContext().identifier());
                    if (inv != null) {
                        IDynamicStackHandler stackHandler = inv.getStacks();
                        stackHandler.setStackInSlot(slotResult.slotContext().index(), outcomeStack);
                    }
                });
            }
        }
    }
}
