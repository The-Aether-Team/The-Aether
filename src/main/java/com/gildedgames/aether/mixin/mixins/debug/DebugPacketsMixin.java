package com.gildedgames.aether.mixin.mixins.debug;

//import com.mojang.blaze3d.vertex.PoseStack;
//import io.netty.buffer.Unpooled;
//import net.minecraft.client.renderer.MultiBufferSource;
//import net.minecraft.network.FriendlyByteBuf;
//import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
//import net.minecraft.network.protocol.game.DebugPackets;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.world.entity.Mob;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.pathfinder.Path;
//import net.minecraft.world.level.pathfinder.Target;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Shadow;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//
//import java.behavior.HashSet;
//
//@Mixin(DebugPackets.class)
//public class DebugPacketsMixin {
//    @Shadow
//    private static void sendPacketToAllPlayers(ServerLevel serverLevel, FriendlyByteBuf friendlyByteBuf, ResourceLocation resourceLocation) { }
//
//    @Inject(at = @At("HEAD"), method = "sendPathFindingPacket", remap = false)
//    private static void sendPathFindingPacket(Level level, Mob mob, Path path, float maxDistanceToWaypoint, CallbackInfo ci) {
//        if (level instanceof ServerLevel serverLevel && path != null) {
//            path.targetNodes = new HashSet<>();
//            path.targetNodes.add(new Target(0, 0, 0));
//            FriendlyByteBuf friendlyByteBuf = new FriendlyByteBuf(Unpooled.buffer());
//            friendlyByteBuf.writeInt(mob.getId());
//            friendlyByteBuf.writeFloat(maxDistanceToWaypoint);
//            path.writeToStream(friendlyByteBuf);
//            sendPacketToAllPlayers(serverLevel, friendlyByteBuf, ClientboundCustomPayloadPacket.DEBUG_PATHFINDING_PACKET);
//        }
//    }
//}
