package com.gildedgames.aether.network.packet.client;

import com.gildedgames.aether.network.AetherPacket;
import com.gildedgames.aether.api.DimensionTagTracking;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

public record DimensionTypeHolderPacket(ResourceKey<Level> dimensionKey, TagKey<DimensionType> tagKey, boolean value) implements AetherPacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeResourceLocation(this.dimensionKey.location());
        buf.writeResourceLocation(this.tagKey.location());
        buf.writeBoolean(this.value);
    }

    public static DimensionTypeHolderPacket decode(FriendlyByteBuf buf) {
        ResourceKey<Level> dimensionKey = ResourceKey.create(Registry.DIMENSION_REGISTRY, buf.readResourceLocation());
        TagKey<DimensionType> tagKey = TagKey.create(Registry.DIMENSION_TYPE_REGISTRY, buf.readResourceLocation());
        boolean value = buf.readBoolean();
        return new DimensionTypeHolderPacket(dimensionKey, tagKey, value);
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            DimensionTagTracking.getTagTrackers().put(this.dimensionKey, this.tagKey, this.value);
        }
    }
}
