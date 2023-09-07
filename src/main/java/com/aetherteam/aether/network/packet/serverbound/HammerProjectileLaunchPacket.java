package com.aetherteam.aether.network.packet.serverbound;

import com.aetherteam.aether.entity.projectile.weapon.HammerProjectile;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

/**
 * Handles syncing {@link HammerProjectile} damage to the server.
 */
public record HammerProjectileLaunchPacket(int targetID, int projectileID) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.targetID());
        buf.writeInt(this.projectileID());
    }

    public static HammerProjectileLaunchPacket decode(FriendlyByteBuf buf) {
        int targetID = buf.readInt();
        int projectileID = buf.readInt();
        return new HammerProjectileLaunchPacket(targetID, projectileID);
    }

    @Override
    public void execute(Player playerEntity) {
        if (playerEntity != null && playerEntity.getServer() != null) {
            Entity target = playerEntity.getLevel().getEntity(this.targetID());
            Entity projectile = playerEntity.getLevel().getEntity(this.projectileID());
            if (projectile instanceof HammerProjectile hammerProjectile) {
                hammerProjectile.launchTarget(target);
            }
        }
    }
}
