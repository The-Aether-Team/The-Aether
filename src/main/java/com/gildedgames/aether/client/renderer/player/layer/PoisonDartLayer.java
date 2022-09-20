package com.gildedgames.aether.client.renderer.player.layer;

import com.gildedgames.aether.entity.projectile.dart.AbstractDart;
import com.gildedgames.aether.entity.projectile.dart.PoisonDart;
import com.gildedgames.aether.entity.AetherEntityTypes;
import com.gildedgames.aether.capability.player.AetherPlayer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nonnull;
import java.util.Optional;

public class PoisonDartLayer<T extends LivingEntity, M extends PlayerModel<T>> extends AbstractDartLayer<T, M> {
    public PoisonDartLayer(EntityRenderDispatcher renderDispatcher, LivingEntityRenderer<T, M> renderer) {
        super(renderDispatcher, renderer);
    }

    @Override
    protected int numStuck(@Nonnull T entity) {
        if (entity instanceof Player player) {
            Optional<AetherPlayer> aetherPlayerOptional = AetherPlayer.get(player).resolve();
            if (aetherPlayerOptional.isPresent()) {
                return aetherPlayerOptional.get().getPoisonDartCount();
            }
        }
        return 0;
    }

    @Override
    protected void renderStuckItem(@Nonnull PoseStack poseStack, @Nonnull MultiBufferSource buffer, int packedLight, @Nonnull Entity entity, float f, float f1, float f2, float partialTicks) {
        AbstractDart dart = new PoisonDart(AetherEntityTypes.POISON_DART.get(), entity.level);
        dart.setPos(entity.getX(), entity.getY(), entity.getZ());
        this.renderStuckDart(dart, poseStack, buffer, packedLight, entity, f, f1, f2, partialTicks);
    }
}
