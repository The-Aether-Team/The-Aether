package com.aetherteam.aether.mixin.mixins.common.accessor;

import net.minecraft.server.players.StoredUserEntry;
import net.minecraft.server.players.StoredUserList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(StoredUserList.class)
public interface StoredUserListAccessor {
    @Invoker
    <K, V extends StoredUserEntry<K>> boolean callContains(K entry);
}