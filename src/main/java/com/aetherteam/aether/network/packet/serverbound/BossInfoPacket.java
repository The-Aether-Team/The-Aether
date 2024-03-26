package com.aetherteam.aether.network.packet.serverbound;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.event.hooks.GuiHooks;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

/**
 * Packets to help sync the server's Aether boss bars with the client's.
 */
public abstract class BossInfoPacket implements BasePacket {
    protected final UUID bossEvent;
    protected final int entityID;

    public BossInfoPacket(UUID bossEvent, int entityID) {
        this.bossEvent = bossEvent;
        this.entityID = entityID;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeUUID(this.bossEvent);
        buf.writeInt(this.entityID);
    }

    /**
     * Adds a boss bar for the client.
     */
    public static class Display extends BossInfoPacket {

        public static final ResourceLocation ID = new ResourceLocation(Aether.MODID, "add_custom_bossbar");

        public Display(UUID bossEvent, int entityID) {
            super(bossEvent, entityID);
        }

        @Override
        public ResourceLocation id() {
            return ID;
        }

        public static Display decode(FriendlyByteBuf buf) {
            UUID bossEvent = buf.readUUID();
            int entityID = buf.readInt();
            return new Display(bossEvent, entityID);
        }

        @Override
        public void execute(Player playerEntity) {
            GuiHooks.BOSS_EVENTS.put(this.bossEvent, this.entityID);
        }
    }

    /**
     * Removes a boss bar for the client.
     */
    public static class Remove extends BossInfoPacket {

        public static final ResourceLocation ID = new ResourceLocation(Aether.MODID, "remove_custom_bossbar");

        public Remove(UUID bossEvent, int entityID) {
            super(bossEvent, entityID);
        }

        @Override
        public ResourceLocation id() {
            return ID;
        }

        public static Remove decode(FriendlyByteBuf buf) {
            UUID bossEvent = buf.readUUID();
            int entityID = buf.readInt();
            return new Remove(bossEvent, entityID);
        }

        @Override
        public void execute(Player playerEntity) {
            GuiHooks.BOSS_EVENTS.remove(this.bossEvent);
        }
    }
}
