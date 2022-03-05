package com.gildedgames.aether.client.renderer.player.layer;

import com.gildedgames.aether.common.entity.projectile.dart.GoldenDart;
import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nonnull;

public class GoldenDartLayer<T extends LivingEntity, M extends PlayerModel<T>> extends AbstractDartLayer<T, M> {
    public GoldenDartLayer(EntityRenderDispatcher renderDispatcher, LivingEntityRenderer<T, M> renderer) {
        super(renderDispatcher, renderer);
    }

    @Override
    protected int numStuck(@Nonnull T entity) {
        if (entity instanceof Player) {
            IAetherPlayer aetherPlayer = IAetherPlayer.get((Player) entity).orElse(null);
            return aetherPlayer.getGoldenDartCount();
        } else {
            return 0;
        }
    }

    @Override
    public void render(@Nonnull PoseStack poseStack, @Nonnull MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        GoldenDart goldenDart = new GoldenDart(entity.level);
        this.renderDart(poseStack, buffer, goldenDart, packedLight, entity, partialTicks, 1.0F);
    }
}
