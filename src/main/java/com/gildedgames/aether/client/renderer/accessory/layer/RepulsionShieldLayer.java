package com.gildedgames.aether.client.renderer.accessory.layer;

import com.gildedgames.aether.common.item.accessories.miscellaneous.RepulsionShieldItem;
import com.gildedgames.aether.common.registry.AetherItems;
import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.CuriosApi;

public class RepulsionShieldLayer<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> //extends RenderLayer<T, M>
{
//    private final A shieldModel;
//
//    public RepulsionShieldLayer(RenderLayerParent<T, M> renderer, A glovesModel) {
//        super(renderer);
//        this.shieldModel = glovesModel;
//    }
//
//    @Override
//    public void render(PoseStack p_225628_1_, MultiBufferSource p_225628_2_, int p_225628_3_, T p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
//        CuriosApi.getCuriosHelper().findEquippedCurio(AetherItems.REPULSION_SHIELD.get(), p_225628_4_).ifPresent((triple) -> CuriosApi.getCuriosHelper().getCuriosHandler(p_225628_4_).ifPresent(handler ->
//                handler.getStacksHandler(triple.getLeft()).ifPresent(stacksHandler -> {
//                    RepulsionShieldItem shield = (RepulsionShieldItem) triple.getRight().getItem();
//                    if (stacksHandler.getRenders().get(triple.getMiddle())) {
//                        ResourceLocation texture;
//                        if (p_225628_4_ instanceof Player) {
//                            IAetherPlayer aetherPlayer = IAetherPlayer.get((Player) p_225628_4_).orElse(null);
//                            if (!aetherPlayer.isMoving()) {
//                                texture = shield.getRepulsionShieldTexture();
//                            } else {
//                                texture = shield.getRepulsionShieldInactiveTexture();
//                            }
//                        } else {
//                            Vec3 motion = p_225628_4_.getDeltaMovement();
//                            if (motion.x() == 0.0 && (motion.y() == -0.0784000015258789 || motion.y() == 0.0) && motion.z() == 0.0) {
//                                texture = shield.getRepulsionShieldTexture();
//                            } else {
//                                texture = shield.getRepulsionShieldInactiveTexture();
//                            }
//                        }
//                        this.getParentModel().copyPropertiesTo(this.shieldModel);
//                        VertexConsumer ivertexbuilder = ItemRenderer.getArmorFoilBuffer(p_225628_2_, RenderType.entityTranslucent(texture), false, false);
//                        this.shieldModel.renderToBuffer(p_225628_1_, ivertexbuilder, p_225628_3_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
//                    }
//                })
//        ));
//    }
}
