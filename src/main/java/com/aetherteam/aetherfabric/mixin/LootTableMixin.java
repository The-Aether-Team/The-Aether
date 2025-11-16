package com.aetherteam.aetherfabric.mixin;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.loot.modifiers.AetherLootTableModifications;
import com.aetherteam.aetherfabric.pond.LootContextExtension;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.Optional;
import java.util.function.Consumer;

@Mixin(LootTable.class)
public abstract class LootTableMixin {
    @Unique
    private static final ResourceLocation UNKNOWN_TABLE_ID = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "unknown");

    @WrapMethod(method = "getRandomItemsRaw(Lnet/minecraft/world/level/storage/loot/LootContext;Ljava/util/function/Consumer;)V")
    private void finishCollectingLoot(LootContext context, Consumer<ItemStack> consumer, Operation<Void> original) {
        var lootTableId = context.getLevel().getServer().reloadableRegistries().get()
            .registry(Registries.LOOT_TABLE)
            .flatMap(lootTables -> {
                return lootTables.getResourceKey((LootTable)(Object)this)
                    .map(ResourceKey::location);
            });

        var ext = ((LootContextExtension) context);

        // Handles case where the given loot table is Unknown at all meaning an unknown location is pushed to the top.
        // Typically, occurs when the table is a nested one or injected in manor where its it not registered
        ext.pushTableId(lootTableId.orElse(UNKNOWN_TABLE_ID));

        ObjectArrayList<ItemStack> stacks = new ObjectArrayList<>();
        original.call(context, (Consumer<ItemStack>) stacks::add);
        AetherLootTableModifications.apply(stacks, context).forEach(consumer);

        ext.popTableId();
    }
}
