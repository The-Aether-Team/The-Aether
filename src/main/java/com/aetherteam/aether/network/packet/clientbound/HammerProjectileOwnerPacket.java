package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.aether.entity.projectile.weapon.HammerProjectile;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public record HammerProjectileOwnerPacket(int projectileID, int ownerID) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.projectileID());
        buf.writeInt(this.ownerID());
    }

    public static HammerProjectileOwnerPacket decode(FriendlyByteBuf buf) {
        int projectileID = buf.readInt();
        int ownerID = buf.readInt();
        return new HammerProjectileOwnerPacket(projectileID, ownerID);
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Entity projectile = Minecraft.getInstance().player.getLevel().getEntity(this.projectileID());
            Entity owner = Minecraft.getInstance().player.getLevel().getEntity(this.ownerID());
            if (projectile instanceof HammerProjectile hammerProjectile && owner != null) {
                hammerProjectile.setOwner(owner);
            }
        }
    }
}
