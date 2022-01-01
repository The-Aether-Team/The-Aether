package com.gildedgames.aether.client.event.listeners.abilities;

import com.gildedgames.aether.common.registry.AetherItems;
import com.gildedgames.aether.common.registry.AetherTags;
import com.gildedgames.aether.core.network.AetherPacketHandler;
import com.gildedgames.aether.core.network.packet.server.ExtendedAttackPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ToolAbilityClientListener
{
    @SubscribeEvent
    public static void onPlayerLeftClick(PlayerInteractEvent.LeftClickEmpty event) {
        if (event.getItemStack().is(AetherTags.Items.VALKYRIE_TOOLS) || event.getItemStack().getItem() == AetherItems.VALKYRIE_LANCE.get()) {
            handleExtendedReach(event.getPlayer());
        }
    }

    @SubscribeEvent
    public static void onPlayerLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        if (event.getItemStack().is(AetherTags.Items.VALKYRIE_TOOLS) || event.getItemStack().getItem() == AetherItems.VALKYRIE_LANCE.get()) {
            event.setCanceled(handleExtendedReach(event.getPlayer()));
        }
    }

    private static boolean handleExtendedReach(Player player) {
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
        return false;
    }
}
