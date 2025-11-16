package com.aetherteam.aether.mixin.mixins.common.aether_fabric;

import com.aetherteam.aether.item.combat.loot.ValkyrieLanceItem;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.SwordItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;

@Mixin(Player.class)
public abstract class PlayerMixin {
    @WrapOperation(method = "attack", constant = @Constant(classValue = SwordItem.class))
    private boolean aetherFabric$preventSweeping(Object object, Operation<Boolean> original) {
        return original.call(object) && !(object instanceof ValkyrieLanceItem);
    }
}
