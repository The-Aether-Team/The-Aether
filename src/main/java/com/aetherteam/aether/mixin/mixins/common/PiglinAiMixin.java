package com.aetherteam.aether.mixin.mixins.common;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketsApi;
import io.github.fabricators_of_create.porting_lib.item.PiglinsNeutralItem;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PiglinAi.class)
public abstract class PiglinAiMixin {

    // Similar to porting lib but just checks trinket items
    @Inject(method = "isWearingGold", at = @At("HEAD"), cancellable = true)
    private static void aether$isNeutralItemForTrinkets(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        var comp = TrinketsApi.getTrinketComponent(entity).orElse(null);

        for (var tuple : comp.getAllEquipped()) {
            var stack = tuple.getB();

            if(stack.getItem() instanceof PiglinsNeutralItem piglinsNeutralItem && piglinsNeutralItem.makesPiglinsNeutral(stack, entity)) {
                cir.setReturnValue(true);
            }
        }
    }
}
