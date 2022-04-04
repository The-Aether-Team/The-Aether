package com.gildedgames.aether.common.item.tools.abilities;

import com.gildedgames.aether.common.registry.AetherTags;
import com.gildedgames.aether.core.network.AetherPacketHandler;
import com.gildedgames.aether.core.network.packet.server.ExtendedAttackPacket;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;

import java.util.Iterator;
import java.util.UUID;

public interface ValkyrieTool {
    UUID REACH_MODIFIER_UUID = UUID.fromString("0A87A51E-A43F-4EEF-A770-07C2160D373D");

    static Multimap<Attribute, AttributeModifier> extendReachModifier(Multimap<Attribute, AttributeModifier> map, ItemStack stack, EquipmentSlot slot) {
        if ((stack.is(AetherTags.Items.VALKYRIE_TOOLS) || stack.is(AetherTags.Items.VALKYRIE_WEAPONS)) && (slot == EquipmentSlot.MAINHAND || slot == EquipmentSlot.OFFHAND)) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> attributeBuilder = ImmutableMultimap.builder();
            attributeBuilder.putAll(map);
            attributeBuilder.put(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(REACH_MODIFIER_UUID, "Reach modifier", getReachDistanceModifier(), AttributeModifier.Operation.ADDITION));
            map = attributeBuilder.build();
        }
        return map;
    }

    private static double getReachDistanceModifier() {
        return 3.0D;
    }

    static boolean attackEntityWithReach(ItemStack heldStack, Player player) {
        Entity entity = extendHitReach(heldStack, player);
        if (entity != null) {
            AetherPacketHandler.sendToServer(new ExtendedAttackPacket(player.getUUID(), entity.getId()));
            return true;
        }
        return false;
    }

    static void checkForEntityWithReach(Player player) {
        if (player != null) {
            ItemStack heldStack = player.getMainHandItem();
            Entity entity = extendHitReach(heldStack, player);
            if (entity != null) {
                Minecraft.getInstance().crosshairPickEntity = entity;
            }
        }
    }

    private static Entity extendHitReach(ItemStack heldStack, Player player) {
        if (heldStack.is(AetherTags.Items.VALKYRIE_TOOLS) || heldStack.is(AetherTags.Items.VALKYRIE_WEAPONS)) {
            double currentReach = player.getAttribute(ForgeMod.REACH_DISTANCE.get()).getValue();
            Vec3 eyePos = player.getEyePosition(1.0F);
            Vec3 lookVec = player.getLookAngle();
            Vec3 reachVec = eyePos.add(lookVec.x * currentReach, lookVec.y * currentReach, lookVec.z * currentReach);
            AABB playerBox = player.getBoundingBox().expandTowards(lookVec.scale(currentReach)).inflate(1.0D, 1.0D, 1.0D);
            EntityHitResult hitResult = ProjectileUtil.getEntityHitResult(player, eyePos, reachVec, playerBox, (target) -> !target.isSpectator() && target.isPickable(), currentReach * currentReach);
            if (hitResult != null) {
                Entity target = hitResult.getEntity();
                Vec3 hitVec = hitResult.getLocation();
                double distance = eyePos.distanceToSqr(hitVec);
                if (distance <= currentReach * currentReach) {
                    return target;
                }
            }
        }
        return null;
    }

    static boolean tooFar(ItemStack heldStack, Player player, InteractionHand interactionHand) {
        if (hasValkyrieItemInOneHand(player) && !heldStack.is(AetherTags.Items.VALKYRIE_TOOLS) && !heldStack.is(AetherTags.Items.VALKYRIE_WEAPONS)) {
            double valkyrieReach = getValkyrieReachAttribute(player, interactionHand);
            double baseReach = player.getAttribute(ForgeMod.REACH_DISTANCE.get()).getValue() - valkyrieReach;
            double currentReach = player.getAttribute(ForgeMod.REACH_DISTANCE.get()).getValue();
            HitResult hitResult = player.pick(currentReach, 0.0F, false);
            Vec3 hitVec = hitResult.getLocation();
            Vec3 eyePos = player.getEyePosition(1.0F);
            double distance = eyePos.distanceToSqr(hitVec);
            return distance > baseReach * baseReach;
        }
        return false;
    }

    private static boolean hasValkyrieItemInOneHand(Player player) {
        ItemStack mainHandStack = player.getMainHandItem();
        ItemStack offHandStack = player.getOffhandItem();
        return ((mainHandStack.is(AetherTags.Items.VALKYRIE_TOOLS) || mainHandStack.is(AetherTags.Items.VALKYRIE_WEAPONS)) && (!offHandStack.is(AetherTags.Items.VALKYRIE_TOOLS) && !offHandStack.is(AetherTags.Items.VALKYRIE_WEAPONS)))
                || ((offHandStack.is(AetherTags.Items.VALKYRIE_TOOLS) || offHandStack.is(AetherTags.Items.VALKYRIE_WEAPONS)) && (!mainHandStack.is(AetherTags.Items.VALKYRIE_TOOLS) && !mainHandStack.is(AetherTags.Items.VALKYRIE_WEAPONS)));
    }

    private static double getValkyrieReachAttribute(Player player, InteractionHand interactionHand) {
        EquipmentSlot oppositeSlot = interactionHand == InteractionHand.MAIN_HAND ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND;
        ItemStack oppositeStack = player.getItemBySlot(oppositeSlot);
        Multimap<Attribute, AttributeModifier> attributes = oppositeStack.getAttributeModifiers(oppositeSlot);
        double distance = 0.0F;
        for (Iterator<AttributeModifier> it = attributes.get(ForgeMod.REACH_DISTANCE.get()).stream().iterator(); it.hasNext();) {
            AttributeModifier modifier = it.next();
            distance += modifier.getAmount();
        }
        return distance;
    }
}