package com.aetherteam.aether.mixin.mixins.common;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.event.hooks.EntityHooks;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.Curios;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.HashMap;
import java.util.Map;

@Mixin(Mob.class)
public class MobMixin {
    @Unique
    public final Map<String, Float> accessoryDropChances = new HashMap<>();

    @Inject(at = @At(value = "HEAD"), method = "canTakeItem(Lnet/minecraft/world/item/ItemStack;)Z", cancellable = true)
    private void canTakeItem(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        Mob mob = (Mob) (Object) this;
        String identifier = "";
        if (AetherConfig.COMMON.use_curios_menu.get()) {
            TagKey<Item> glovesTag = TagKey.create(Registries.ITEM, new ResourceLocation(Curios.MODID, "hands"));
            TagKey<Item> pendantTag = TagKey.create(Registries.ITEM, new ResourceLocation(Curios.MODID, "necklace"));
            if (stack.is(glovesTag)) {
                identifier = "hands";
            } else if (stack.is(pendantTag) && (mob.getType() == EntityType.PIGLIN || mob.getType() == EntityType.ZOMBIFIED_PIGLIN)) {
                identifier = "necklace";
            }
        } else {
            if (stack.is(AetherTags.Items.AETHER_GLOVES)) {
                identifier = "aether_gloves";
            } else if (stack.is(AetherTags.Items.AETHER_PENDANT) && (mob.getType() == EntityType.PIGLIN || mob.getType() == EntityType.ZOMBIFIED_PIGLIN)) {
                identifier = "aether_pendant";
            }
        }
        if (EntityHooks.canMobSpawnWithAccessories(mob)) {
            String finalIdentifier = identifier;
            CuriosApi.getCuriosInventory(mob).ifPresent((handler) -> {
                if (!finalIdentifier.isEmpty()) {
                    handler.findCurio(finalIdentifier, 0).ifPresent((slotResult) -> {
                        if (slotResult.stack().isEmpty() && mob.canPickUpLoot()) {
                            cir.setReturnValue(true);
                        }
                    });
                }
            });
        }
    }

    //todo canReplaceCurrentItem

    //todo equipItemIfPossible
}
