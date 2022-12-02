package com.gildedgames.aether.mixin.mixins.debug;

import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.Target;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;

@Mixin(Path.class)
public interface PathAccessor {
    @Accessor
    Set<Target> getTargetNodes();

    @Accessor
    void setTargetNodes(Set<Target> targetSet);
}
