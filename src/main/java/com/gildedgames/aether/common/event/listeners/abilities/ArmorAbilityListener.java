package com.gildedgames.aether.common.event.listeners.abilities;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.item.accessories.abilities.IZaniteAccessory;
import com.gildedgames.aether.common.registry.AetherItems;
import com.gildedgames.aether.core.util.EquipmentUtil;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SEntityVelocityPacket;
import net.minecraft.util.Hand;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.Map;

@Mod.EventBusSubscriber
public class ArmorAbilityListener
{
    @SubscribeEvent
    public static void onEntityUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
//        else if (EquipmentUtil.hasFullNeptuneSet(entity)) {
//            if (entity.isInWater()) {
//                Vector3d movement = entity.getDeltaMovement();
//                float defaultBoost = 1.25F;
//                float depthStriderModifier = Math.min(EnchantmentHelper.getDepthStrider(entity), 3.0F);
//                if (depthStriderModifier > 0.0F) {
//                    defaultBoost += depthStriderModifier * 0.25F;
//                }
//                entity.moveRelative(defaultBoost, vector);
//
//
//
//                entity.setDeltaMovement(movement.multiply(defaultBoost, 1.0F, defaultBoost));
//            }
//        }

        if (entity.isInWaterRainOrBubble()) {
            for (EquipmentSlotType equipmentslottype : EquipmentSlotType.values()) {
                if (equipmentslottype.getType() == EquipmentSlotType.Group.ARMOR) {
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
            CuriosApi.getCuriosHelper().findEquippedCurio(AetherItems.PHOENIX_GLOVES.get(), entity).ifPresent((triple) -> breakPhoenixGloves(entity, triple, new ItemStack(AetherItems.OBSIDIAN_GLOVES.get())));
        }
    }

    private static void breakPhoenixArmor(LivingEntity entity, ItemStack equippedStack, ItemStack outcomeStack, EquipmentSlotType equipmentslottype) {
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

    private static void breakPhoenixGloves(LivingEntity entity, ImmutableTriple<String, Integer, ItemStack> triple, ItemStack outcomeStack) {
        if (entity.level.getGameTime() % 10 == 0) {
            triple.getRight().hurtAndBreak(1, entity, (p) -> p.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
            if (!CuriosApi.getCuriosHelper().findEquippedCurio(AetherItems.PHOENIX_GLOVES.get(), entity).isPresent()) {
                EnchantmentHelper.setEnchantments(EnchantmentHelper.getEnchantments(triple.getRight()), outcomeStack);
                if (triple.getRight().hasTag()) {
                    outcomeStack.setTag(triple.getRight().getTag());
                }
                CuriosApi.getCuriosHelper().getCuriosHandler(entity).ifPresent(iCuriosItemHandler -> {
                    Map<String, ICurioStacksHandler> curios = iCuriosItemHandler.getCurios();
                    ICurioStacksHandler inv = curios.get(triple.getLeft());
                    if (inv != null) {
                        IDynamicStackHandler stackHandler = inv.getStacks();
                        stackHandler.setStackInSlot(triple.getMiddle(), outcomeStack);
                    }
                });
            }
        }
    }

    @SubscribeEvent
    public static void onEntityJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (EquipmentUtil.hasFullGravititeSet(entity)) {
            if (entity instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) entity;
                if (!player.isShiftKeyDown()) {
                    player.push(0.0, 1.0, 0.0);
                    if (player instanceof ServerPlayerEntity) {
                        ((ServerPlayerEntity) player).connection.send(new SEntityVelocityPacket(player));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntityFall(LivingFallEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.getItemBySlot(EquipmentSlotType.FEET).getItem() == AetherItems.SENTRY_BOOTS.get() || EquipmentUtil.hasFullGravititeSet(entity) || EquipmentUtil.hasFullValkyrieSet(entity)) {
            event.setCanceled(true);
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
}
