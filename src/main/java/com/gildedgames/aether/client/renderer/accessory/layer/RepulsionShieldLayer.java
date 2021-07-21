package com.gildedgames.aether.client.renderer.accessory.layer;

import com.gildedgames.aether.common.item.accessories.miscellaneous.RepulsionShieldItem;
import com.gildedgames.aether.common.registry.AetherItems;
import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import top.theillusivec4.curios.api.CuriosApi;

public class RepulsionShieldLayer<T extends LivingEntity, M extends BipedModel<T>, A extends BipedModel<T>> extends LayerRenderer<T, M>
{
    private final A shieldModel;

    public RepulsionShieldLayer(IEntityRenderer<T, M> renderer, A glovesModel) {
        super(renderer);
        this.shieldModel = glovesModel;
    }

    @Override
    public void render(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, T p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
        CuriosApi.getCuriosHelper().findEquippedCurio(AetherItems.REPULSION_SHIELD.get(), p_225628_4_).ifPresent((triple) -> CuriosApi.getCuriosHelper().getCuriosHandler(p_225628_4_).ifPresent(handler ->
                handler.getStacksHandler(triple.getLeft()).ifPresent(stacksHandler -> {
                    RepulsionShieldItem shield = (RepulsionShieldItem) triple.getRight().getItem();
                    if (stacksHandler.getRenders().get(triple.getMiddle())) {
                        ResourceLocation texture;
                        if (p_225628_4_ instanceof PlayerEntity) {
                            IAetherPlayer aetherPlayer = IAetherPlayer.get((PlayerEntity) p_225628_4_).orElse(null);
                            if (!aetherPlayer.isMoving()) {
                                texture = shield.getRepulsionShieldTexture();
                            } else {
                                texture = shield.getRepulsionShieldInactiveTexture();
                            }
                        } else {
                            Vector3d motion = p_225628_4_.getDeltaMovement();
                            if (motion.x() == 0.0 && (motion.y() == -0.0784000015258789 || motion.y() == 0.0) && motion.z() == 0.0) {
                                texture = shield.getRepulsionShieldTexture();
                            } else {
                                texture = shield.getRepulsionShieldInactiveTexture();
                            }
                        }
                        this.getParentModel().copyPropertiesTo(this.shieldModel);
                        IVertexBuilder ivertexbuilder = ItemRenderer.getArmorFoilBuffer(p_225628_2_, RenderType.entityTranslucent(texture), false, false);
                        this.shieldModel.renderToBuffer(p_225628_1_, ivertexbuilder, p_225628_3_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
                    }
                })
        ));
    }
}
