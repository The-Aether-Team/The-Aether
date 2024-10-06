package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.event.hooks.AbilityHooks;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

/**
 * Stores a client value for whether tools are debuffed in the Aether for {@link com.aetherteam.aether.event.hooks.AbilityHooks.ToolHooks}.
 */
public record ToolDebuffPacket(boolean debuffTools) implements BasePacket {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "apply_tool_debuff");

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeBoolean(this.debuffTools());
    }

    public static ToolDebuffPacket decode(FriendlyByteBuf buf) {
        boolean debuffTools = buf.readBoolean();
        return new ToolDebuffPacket(debuffTools);
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            AbilityHooks.ToolHooks.debuffTools = this.debuffTools();
        }
    }
}
