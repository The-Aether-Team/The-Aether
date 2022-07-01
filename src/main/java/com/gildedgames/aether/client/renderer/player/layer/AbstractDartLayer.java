package com.gildedgames.aether.client.renderer.player.layer;

import com.gildedgames.aether.common.entity.projectile.dart.AbstractDart;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.StuckInBodyLayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nonnull;

public abstract class AbstractDartLayer<T extends LivingEntity, M extends PlayerModel<T>> extends StuckInBodyLayer<T, M> {
    private final EntityRenderDispatcher dispatcher;

    public AbstractDartLayer(EntityRenderDispatcher renderDispatcher, LivingEntityRenderer<T, M> renderer) {
        super(renderer);
        this.dispatcher = renderDispatcher;
    }

    protected abstract int numStuck(@Nonnull T entity);

    protected void renderStuckDart(AbstractDart dart, @Nonnull PoseStack poseStack, @Nonnull MultiBufferSource buffer, int packedLight, Entity entity, float f, float f1, float f2, float partialTicks) {
        float f3 = Mth.sqrt(f * f + f2 * f2);
        dart.setPos(entity.getX(), entity.getY(), entity.getZ());
        dart.setYRot((float) (Math.atan2(f, f2) * (double) (180.0F / (float) Math.PI)));
        dart.setXRot((float) (Math.atan2(f1, f3) * (double) (180.0F / (float) Math.PI)));
        dart.yRotO = dart.getYRot();
        dart.xRotO = dart.getXRot();
        this.dispatcher.render(dart, 0.0, 0.0, 0.0, 0.0F, partialTicks, poseStack, buffer, packedLight);
    }
}
