package com.aetherteam.aether.mixin.mixins.common.accessor;

import io.netty.channel.Channel;
import net.minecraft.network.Connection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Connection.class)
public interface ConnectionAccessor {

    @Accessor("channel")
    Channel nitrogen_fabric$getChannel();
}
