package com.gildedgames.aether.common.item.tools.abilities;

import com.gildedgames.aether.common.registry.AetherTags;
import com.gildedgames.aether.core.network.AetherPacketHandler;
import com.gildedgames.aether.core.network.packet.server.ExtendedAttackPacket;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;

import java.util.UUID;

public interface ValkyrieTool {
    UUID REACH_MODIFIER_UUID = UUID.fromString("0A87A51E-A43F-4EEF-A770-07C2160D373D");

    static Multimap<Attribute, AttributeModifier> extendReachModifier(Multimap<Attribute, AttributeModifier> map, ItemStack stack, EquipmentSlot slot) {
        if ((stack.is(AetherTags.Items.VALKYRIE_TOOLS) || stack.is(AetherTags.Items.VALKYRIE_WEAPONS)) && slot == EquipmentSlot.MAINHAND) {
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

    static boolean extendHitReach(ItemStack stack, Player player) {
        if (stack.is(AetherTags.Items.VALKYRIE_TOOLS) || stack.is(AetherTags.Items.VALKYRIE_WEAPONS)) {
            double reach = player.getAttribute(ForgeMod.REACH_DISTANCE.get()).getValue();
            Vec3 eyePos = player.getEyePosition(1.0F);
            Vec3 lookVec = player.getLookAngle();
            Vec3 reachVec = eyePos.add(lookVec.x * reach, lookVec.y * reach, lookVec.z * reach);
            AABB playerBox = player.getBoundingBox().expandTowards(lookVec.scale(reach)).inflate(1.0D, 1.0D, 1.0D);
            EntityHitResult traceResult = ProjectileUtil.getEntityHitResult(player, eyePos, reachVec, playerBox, (target) -> !target.isSpectator() && target.isPickable(), reach * reach);
            if (traceResult != null) {
                Entity target = traceResult.getEntity();
                Vec3 hitVec = traceResult.getLocation();
                double distance = eyePos.distanceToSqr(hitVec);
                if (distance < reach * reach) {
                    AetherPacketHandler.sendToServer(new ExtendedAttackPacket(player.getUUID(), target.getId()));
                    return true;
                }
            }
        }
        return false;
    }
}
