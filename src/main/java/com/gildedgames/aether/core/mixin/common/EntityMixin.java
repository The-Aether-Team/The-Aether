package com.gildedgames.aether.core.mixin.common;

import com.gildedgames.aether.common.registry.AetherEntityTypes;
import com.google.common.collect.Lists;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(Entity.class)
public abstract class EntityMixin
{
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;baseTick()V"), method = "tick", cancellable = true)
    private void tick(CallbackInfo ci) {
//        Entity entity = (Entity) (Object) this;
//        List<Entity> entityList = Lists.newArrayList(entity.level.getEntities(entity, entity.getBoundingBox().expandTowards(0.0D, -0.1D, 0.0D)));
//        List<EntityType<?>> entityTypesList = new ArrayList<>();
//        entityList.forEach(e -> entityTypesList.add(e.getType()));
//        entity.setNoGravity(entityTypesList.contains(AetherEntityTypes.FLOATING_BLOCK.get()));
    }
}
