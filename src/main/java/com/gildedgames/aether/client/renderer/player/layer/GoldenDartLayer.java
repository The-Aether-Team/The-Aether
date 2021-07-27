package com.gildedgames.aether.client.renderer.player.layer;

import com.gildedgames.aether.common.entity.projectile.dart.GoldenDartEntity;
import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.StuckInBodyLayer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class GoldenDartLayer<T extends LivingEntity, M extends PlayerModel<T>> extends StuckInBodyLayer<T, M>
{
    private final EntityRendererManager dispatcher;
    private GoldenDartEntity dart;

    public GoldenDartLayer(LivingRenderer<T, M> renderer) {
        super(renderer);
        this.dispatcher = renderer.getDispatcher();
    }

    @Override
    protected int numStuck(T entity) {
        if (entity instanceof PlayerEntity) {
            IAetherPlayer aetherPlayer = IAetherPlayer.get((PlayerEntity) entity).orElse(null);
            return aetherPlayer.getGoldenDartCount();
        } else {
            return 0;
        }
    }

    @Override
    public void render(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, T p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
        int i = this.numStuck(p_225628_4_);
        Random random = new Random((long) (p_225628_4_.getId() * 0.25));
        if (i > 0) {
            for(int j = 0; j < i; ++j) {
                p_225628_1_.pushPose();
                ModelRenderer modelrenderer = this.getParentModel().getRandomModelPart(random);
                ModelRenderer.ModelBox modelrenderer$modelbox = modelrenderer.getRandomCube(random);
                modelrenderer.translateAndRotate(p_225628_1_);
                float f = random.nextFloat();
                float f1 = random.nextFloat();
                float f2 = random.nextFloat();
                float f3 = MathHelper.lerp(f, modelrenderer$modelbox.minX, modelrenderer$modelbox.maxX) / 16.0F;
                float f4 = MathHelper.lerp(f1, modelrenderer$modelbox.minY, modelrenderer$modelbox.maxY) / 16.0F;
                float f5 = MathHelper.lerp(f2, modelrenderer$modelbox.minZ, modelrenderer$modelbox.maxZ) / 16.0F;
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
    protected void renderStuckItem(MatrixStack p_225632_1_, IRenderTypeBuffer p_225632_2_, int p_225632_3_, Entity p_225632_4_, float p_225632_5_, float p_225632_6_, float p_225632_7_, float p_225632_8_) {
        float f = MathHelper.sqrt(p_225632_5_ * p_225632_5_ + p_225632_7_ * p_225632_7_);
        this.dart = new GoldenDartEntity(p_225632_4_.level);
        this.dart.setPos(p_225632_4_.getX(), p_225632_4_.getY(), p_225632_4_.getZ());
        this.dart.yRot = (float)(Math.atan2(p_225632_5_, p_225632_7_) * (double)(180F / (float)Math.PI));
        this.dart.xRot = (float)(Math.atan2(p_225632_6_, f) * (double)(180F / (float)Math.PI));
        this.dart.yRotO = this.dart.yRot;
        this.dart.xRotO = this.dart.xRot;
        this.dispatcher.render(this.dart, 0.0D, 0.0D, 0.0D, 0.0F, p_225632_8_, p_225632_1_, p_225632_2_, p_225632_3_);
    }
}
