package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.AetherClient;
import com.aetherteam.aether.client.gui.screen.inventory.SunAltarScreen;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

/**
 * Opens {@link SunAltarScreen} from {@link com.aetherteam.aether.block.utility.SunAltarBlock}.
 */
public record OpenSunAltarPacket(Component name, int timeScale) implements BasePacket {
    public static final ResourceLocation ID = new ResourceLocation(Aether.MODID, "open_sun_altar");

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeComponent(this.name());
        buf.writeInt(this.timeScale());
    }

    public static OpenSunAltarPacket decode(FriendlyByteBuf buf) {
        Component name = buf.readComponent();
        int timeScale = buf.readInt();
        return new OpenSunAltarPacket(name, timeScale);
    }

    @Override
    public void execute(Player player) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            AetherClient.setToSunAltarScreen(this.name(), this.timeScale());
        }
    }
}
