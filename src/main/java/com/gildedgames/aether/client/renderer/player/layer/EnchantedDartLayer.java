package com.gildedgames.aether.client.renderer.player.layer;

import com.gildedgames.aether.common.entity.projectile.dart.EnchantedDartEntity;
import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.StuckInBodyLayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class EnchantedDartLayer<T extends LivingEntity, M extends PlayerModel<T>> extends StuckInBodyLayer<T, M>
{
    private final EntityRenderDispatcher dispatcher;

    public EnchantedDartLayer(EntityRenderDispatcher renderDispatcher, LivingEntityRenderer<T, M> renderer) {
        super(renderer);
        this.dispatcher = renderDispatcher;
    }

    @Override
    protected int numStuck(T entity) {
        if (entity instanceof Player) {
            IAetherPlayer aetherPlayer = IAetherPlayer.get((Player) entity).orElse(null);
            return aetherPlayer.getEnchantedDartCount();
        } else {
            return 0;
        }
    }

    @Override
    public void render(PoseStack p_225628_1_, MultiBufferSource p_225628_2_, int p_225628_3_, T p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
        int i = this.numStuck(p_225628_4_);
        Random random = new Random((long) (p_225628_4_.getId() * 0.75));
        if (i > 0) {
            for(int j = 0; j < i; ++j) {
                p_225628_1_.pushPose();
                ModelPart modelrenderer = this.getParentModel().getRandomModelPart(random);
                ModelPart.Cube modelrenderer$modelbox = modelrenderer.getRandomCube(random);
                modelrenderer.translateAndRotate(p_225628_1_);
                float f = random.nextFloat();
                float f1 = random.nextFloat();
                float f2 = random.nextFloat();
                float f3 = Mth.lerp(f, modelrenderer$modelbox.minX, modelrenderer$modelbox.maxX) / 16.0F;
                float f4 = Mth.lerp(f1, modelrenderer$modelbox.minY, modelrenderer$modelbox.maxY) / 16.0F;
                float f5 = Mth.lerp(f2, modelrenderer$modelbox.minZ, modelrenderer$modelbox.maxZ) / 16.0F;
                p_225628_1_.translate(f3, f4, f5);
                f = -1.0F * (f * 2.0F - 1.0F);
                f1 = -1.0F * (f1 * 2.0F - 1.0F);
                f2 = -1.0F * (f2 * 2.0F - 1.0F);
                this.renderStuckItem(p_225628_1_, p_225628_2_, p_225628_3_, p_225628_4_, f, f1, f2, p_225628_7_);
                p_225628_1_.popPose();
            }
        }
    }

    @Override
    protected void renderStuckItem(PoseStack p_225632_1_, MultiBufferSource p_225632_2_, int p_225632_3_, Entity p_225632_4_, float p_225632_5_, float p_225632_6_, float p_225632_7_, float p_225632_8_) {
        float f = Mth.sqrt(p_225632_5_ * p_225632_5_ + p_225632_7_ * p_225632_7_);
        EnchantedDartEntity dart = new EnchantedDartEntity(p_225632_4_.level);
        dart.setPos(p_225632_4_.getX(), p_225632_4_.getY(), p_225632_4_.getZ());
        dart.setYRot((float)(Math.atan2(p_225632_5_, p_225632_7_) * (double)(180F / (float)Math.PI)));
        dart.setXRot((float)(Math.atan2(p_225632_6_, f) * (double)(180F / (float)Math.PI)));
        dart.yRotO = dart.getYRot();
        dart.xRotO = dart.getXRot();
        this.dispatcher.render(dart, 0.0D, 0.0D, 0.0D, 0.0F, p_225632_8_, p_225632_1_, p_225632_2_, p_225632_3_);
    }
}
