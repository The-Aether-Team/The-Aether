package com.aetherteam.aetherfabric.mixin;

import com.aetherteam.aetherfabric.pond.LootContextExtension;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayDeque;
import java.util.Deque;

@Mixin(LootContext.class)
public abstract class LootContextMixin implements LootContextExtension {
    @Nullable
    @Unique
    private Deque<ResourceLocation> aetherFabric$tableId = new ArrayDeque<>();

    @Override
    public @Nullable ResourceLocation getTableId() {
        if (this.aetherFabric$tableId.isEmpty()) return null;

        return this.aetherFabric$tableId.peek();
    }

    @Override
    public void pushTableId(ResourceLocation tableId) {
        this.aetherFabric$tableId.push(tableId);
    }

    @Override
    public void popTableId() {
        this.aetherFabric$tableId.pop();
    }
}
