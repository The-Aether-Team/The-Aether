package com.gildedgames.aether.client.renderer.player.layer;

import com.gildedgames.aether.common.entity.projectile.combat.PoisonDartEntity;
import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.StuckInBodyLayer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PoisonDartLayer<T extends LivingEntity, M extends PlayerModel<T>> extends StuckInBodyLayer<T, M>
{
    private final EntityRendererManager dispatcher;
    private PoisonDartEntity dart;

    public PoisonDartLayer(LivingRenderer<T, M> renderer) {
        super(renderer);
        this.dispatcher = renderer.getDispatcher();
    }

    @Override
    protected int numStuck(T entity) {
        if (entity instanceof PlayerEntity) {
            IAetherPlayer aetherPlayer = IAetherPlayer.get((PlayerEntity) entity).orElse(null);
            return aetherPlayer.getPoisonDartCount();
        } else {
            return 0;
        }
    }

    @Override
    protected void renderStuckItem(MatrixStack p_225632_1_, IRenderTypeBuffer p_225632_2_, int p_225632_3_, Entity p_225632_4_, float p_225632_5_, float p_225632_6_, float p_225632_7_, float p_225632_8_) {
        float f = MathHelper.sqrt(p_225632_5_ * p_225632_5_ + p_225632_7_ * p_225632_7_);
        this.dart = new PoisonDartEntity(p_225632_4_.level);
        this.dart.setPos(p_225632_4_.getX(), p_225632_4_.getY(), p_225632_4_.getZ());
        this.dart.yRot = (float)(Math.atan2(p_225632_5_, p_225632_7_) * (double)(180F / (float)Math.PI));
        this.dart.xRot = (float)(Math.atan2(p_225632_6_, f) * (double)(180F / (float)Math.PI));
        this.dart.yRotO = this.dart.yRot;
        this.dart.xRotO = this.dart.xRot;
        this.dispatcher.render(this.dart, 0.0D, 0.0D, 0.0D, 0.0F, p_225632_8_, p_225632_1_, p_225632_2_, p_225632_3_);
    }
}
