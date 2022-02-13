package com.gildedgames.aether.client.renderer.player.layer;

import com.gildedgames.aether.common.entity.projectile.dart.AbstractDart;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nonnull;
import java.util.Random;

public abstract class AbstractDartLayer<T extends LivingEntity, M extends PlayerModel<T>> extends RenderLayer<T, M> {
    private final EntityRenderDispatcher dispatcher;

    public AbstractDartLayer(EntityRenderDispatcher renderDispatcher, LivingEntityRenderer<T, M> renderer) {
        super(renderer);
        this.dispatcher = renderDispatcher;
    }

    protected void renderDart(@Nonnull PoseStack poseStack, @Nonnull MultiBufferSource buffer, AbstractDart dart, int packedLight, T entity, float partialTicks, float offset) {
        int i = this.numStuck(entity);
        Random random = new Random((long) (entity.getId() * (0.25 * offset)));
        if (i > 0) {
            for (int j = 0; j < i; ++j) {
                poseStack.pushPose();
                ModelPart modelPart = this.getParentModel().getRandomModelPart(random);
                ModelPart.Cube modelCube = modelPart.getRandomCube(random);
                modelPart.translateAndRotate(poseStack);
                float f = random.nextFloat();
                float f1 = random.nextFloat();
                float f2 = random.nextFloat();
                float f3 = Mth.lerp(f, modelCube.minX, modelCube.maxX) / 16.0F;
                float f4 = Mth.lerp(f1, modelCube.minY, modelCube.maxY) / 16.0F;
                float f5 = Mth.lerp(f2, modelCube.minZ, modelCube.maxZ) / 16.0F;
                poseStack.translate(f3, f4, f5);
                f = -1.0F * (f * 2.0F - 1.0F);
                f1 = -1.0F * (f1 * 2.0F - 1.0F);
                f2 = -1.0F * (f2 * 2.0F - 1.0F);
                this.renderStuckDart(poseStack, buffer, dart, packedLight, entity, f, f1, f2, partialTicks);
                poseStack.popPose();
            }
        }
    }

    protected abstract int numStuck(T entity);

    protected void renderStuckDart(@Nonnull PoseStack poseStack, @Nonnull MultiBufferSource buffer, AbstractDart dart, int packedLight, T entity, float f, float f1, float f2, float partialTicks) {
        float f3 = Mth.sqrt(f * f + f2 * f2);
        dart.setPos(entity.getX(), entity.getY(), entity.getZ());
        dart.setYRot((float) (Math.atan2(f, f2) * (double) (180.0F / (float) Math.PI)));
        dart.setXRot((float) (Math.atan2(f1, f3) * (double) (180.0F / (float) Math.PI)));
        dart.yRotO = dart.getYRot();
        dart.xRotO = dart.getXRot();
        this.dispatcher.render(dart, 0.0, 0.0, 0.0, 0.0F, partialTicks, poseStack, buffer, packedLight);
    }
}
