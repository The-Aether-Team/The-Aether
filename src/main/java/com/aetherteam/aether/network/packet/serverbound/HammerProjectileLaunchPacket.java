package com.aetherteam.aether.network.packet.serverbound;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.entity.projectile.weapon.HammerProjectile;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

/**
 * Handles syncing {@link HammerProjectile} damage to the server.
 */
public record HammerProjectileLaunchPacket(int targetID, int projectileID) implements BasePacket {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "launch_hammer_projectile");

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
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
            Entity target = playerEntity.level().getEntity(this.targetID());
            Entity projectile = playerEntity.level().getEntity(this.projectileID());
            if (projectile instanceof HammerProjectile hammerProjectile) {
                hammerProjectile.launchTarget(target);
            }
        }
    }
}
