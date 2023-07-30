package com.aetherteam.aether.client.renderer.player.layer;

import com.aetherteam.aether.capability.player.AetherPlayer;
import com.aetherteam.aether.entity.projectile.dart.AbstractDart;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.StuckInBodyLayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;
import java.util.function.Function;

public class DartLayer<T extends LivingEntity, M extends PlayerModel<T>> extends StuckInBodyLayer<T, M> {
    private final EntityRenderDispatcher dispatcher;
    private final Function<Entity, AbstractDart> dart;
    private final Function<AetherPlayer, Integer> dartCount;
    private final float offset;

    public DartLayer(EntityRenderDispatcher renderDispatcher, LivingEntityRenderer<T, M> renderer, Function<Entity, AbstractDart> dart, Function<AetherPlayer, Integer> dartCount, float offset) {
        super(renderer);
        this.dispatcher = renderDispatcher;
        this.dart = dart;
        this.dartCount = dartCount;
        this.offset = offset;
    }

    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        int i = this.numStuck(livingEntity);
        float offset = this.offset;
        RandomSource randomSource = RandomSource.create((long) (livingEntity.getId() * (0.25 * offset)));
        if (i > 0) {
            for(int j = 0; j < i; ++j) {
                poseStack.pushPose();
                ModelPart modelPart = this.getParentModel().getRandomModelPart(randomSource);
                ModelPart.Cube cube = modelPart.getRandomCube(randomSource);
                modelPart.translateAndRotate(poseStack);
                float f = randomSource.nextFloat();
                float f1 = randomSource.nextFloat();
                float f2 = randomSource.nextFloat();
                float f3 = Mth.lerp(f, cube.minX, cube.maxX) / 16.0F;
                float f4 = Mth.lerp(f1, cube.minY, cube.maxY) / 16.0F;
                float f5 = Mth.lerp(f2, cube.minZ, cube.maxZ) / 16.0F;
                poseStack.translate(f3, f4, f5);
                f = -1.0F * (f * 2.0F - 1.0F);
                f1 = -1.0F * (f1 * 2.0F - 1.0F);
                f2 = -1.0F * (f2 * 2.0F - 1.0F);
                this.renderStuckItem(poseStack, buffer, packedLight, livingEntity, f, f1, f2, partialTicks);
                poseStack.popPose();
            }
        }
    }

    @Override
    protected void renderStuckItem(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Entity entity, float x, float y, float z, float partialTick) {
        float f = Mth.sqrt(x * x + z * z);
        AbstractDart dart = this.dart.apply(entity);
        dart.setPos(entity.position());
        dart.setYRot((float) (Math.atan2(x, z) * Mth.RAD_TO_DEG));
        dart.setXRot((float) (Math.atan2(y, f) * Mth.RAD_TO_DEG));
        dart.yRotO = dart.getYRot();
        dart.xRotO = dart.getXRot();
        this.dispatcher.render(dart, 0.0, 0.0, 0.0, 0.0F, partialTick, poseStack, buffer, packedLight);
    }

    @Override
    protected int numStuck(T entity) {
        if (entity instanceof Player player) {
            Optional<AetherPlayer> aetherPlayerOptional = AetherPlayer.get(player).resolve();
            if (aetherPlayerOptional.isPresent()) {
                return this.dartCount.apply(aetherPlayerOptional.get());
            }
        }
        return 0;
    }
}
