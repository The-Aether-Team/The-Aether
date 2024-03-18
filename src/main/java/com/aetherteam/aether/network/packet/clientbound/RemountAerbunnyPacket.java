package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.aether.capability.player.AetherPlayer;
import com.aetherteam.aether.capability.player.AetherPlayerCapability;
import com.aetherteam.aether.entity.passive.Aerbunny;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * Mounts an Aerbunny to the player using stored NBT data if the player previously logged out with a mounted Aerbunny. This is called by {@link AetherPlayerCapability#remountAerbunny()}.<br><br>
 * This also stores the summoned Aerbunny back into the capability so the player is tracked as having a mounted Aerbunny.
 */
public record RemountAerbunnyPacket(int vehicleID, int aerbunnyID) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.vehicleID());
        buf.writeInt(this.aerbunnyID());
    }

    public static RemountAerbunnyPacket decode(FriendlyByteBuf buf) {
        int vehicleID = buf.readInt();
        int aerbunnyID = buf.readInt();
        return new RemountAerbunnyPacket(vehicleID, aerbunnyID);
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Level world = Minecraft.getInstance().player.level();
            if (world.getEntity(this.vehicleID()) instanceof Player player && world.getEntity(this.aerbunnyID()) instanceof Aerbunny aerbunny) {
                aerbunny.startRiding(player);
                AetherPlayer.getOptional(player).ifPresent(aetherPlayer -> aetherPlayer.setMountedAerbunny(aerbunny));
            }
        }
    }
}
