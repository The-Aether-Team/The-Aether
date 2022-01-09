package com.gildedgames.aether.client.renderer.accessory;

import com.gildedgames.aether.client.registry.AetherModelLayers;
import com.gildedgames.aether.common.item.accessories.miscellaneous.RepulsionShieldItem;
import com.gildedgames.aether.common.registry.AetherItems;
import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class RepulsionShieldRenderer implements ICurioRenderer
{
    private final HumanoidModel<LivingEntity> shieldModel;
    private final PlayerModel<LivingEntity> shieldModelSlim;
//    public final HumanoidModel<LivingEntity> shieldModelArm;
//    public final PlayerModel<LivingEntity> shieldModelArmSlim;

    public RepulsionShieldRenderer() {
        this.shieldModel = new HumanoidModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(AetherModelLayers.REPULSION_SHIELD));
        this.shieldModelSlim = new PlayerModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(AetherModelLayers.REPULSION_SHIELD_SLIM) , true);
//        this.shieldModelArm = new HumanoidModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(AetherModelLayers.REPULSION_SHIELD_ARM));
//        this.shieldModelArmSlim = new PlayerModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(AetherModelLayers.REPULSION_SHIELD_ARM_SLIM) , true);
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack poseStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        LivingEntity livingEntity = slotContext.entity();
        CuriosApi.getCuriosHelper().findFirstCurio(livingEntity, AetherItems.REPULSION_SHIELD.get()).ifPresent((slotResult) -> CuriosApi.getCuriosHelper().getCuriosHandler(livingEntity).ifPresent(handler ->
                handler.getStacksHandler(slotResult.slotContext().identifier()).ifPresent(stacksHandler -> {
                    RepulsionShieldItem shield = (RepulsionShieldItem) slotResult.stack().getItem();
                    if (stacksHandler.getRenders().get(slotResult.slotContext().index())) {
                        ResourceLocation texture;
                        HumanoidModel<LivingEntity> model;

                        if (livingEntity instanceof Player player && renderLayerParent.getModel() instanceof PlayerModel<?> playerModel) {
                            model = playerModel.slim ? this.shieldModelSlim : this.shieldModel;
                            IAetherPlayer aetherPlayer = IAetherPlayer.get(player).orElse(null);
                            if (!aetherPlayer.isMoving()) {
                                texture = playerModel.slim ? shield.getRepulsionShieldSlimTexture() : shield.getRepulsionShieldTexture();
                            } else {
                                texture = playerModel.slim ? shield.getRepulsionShieldSlimInactiveTexture() : shield.getRepulsionShieldInactiveTexture();
                            }
                        } else {
                            model = this.shieldModel;
                            Vec3 motion = livingEntity.getDeltaMovement();
                            if (motion.x() == 0.0 && (motion.y() == -0.0784000015258789 || motion.y() == 0.0) && motion.z() == 0.0) {
                                texture = shield.getRepulsionShieldTexture();
                            } else {
                                texture = shield.getRepulsionShieldInactiveTexture();
                            }
                        }

                        ICurioRenderer.followHeadRotations(slotContext.entity(), model.head);
                        ICurioRenderer.followBodyRotations(slotContext.entity(), model);
                        VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(renderTypeBuffer, RenderType.entityTranslucent(texture), false, false);
                        model.renderToBuffer(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
                    }
                })
        ));
    }

//    public void renderFirstPerson(ItemStack stack, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, AbstractClientPlayer player, HumanoidArm arm) {
//        boolean isSlim = player.getModelName().equals("slim");
//        HumanoidModel<LivingEntity> model = isSlim ? this.shieldModelArmSlim : this.shieldModelArm;
//        ResourceLocation texture;
//
//        model.setAllVisible(false);
//        model.attackTime = 0.0F;
//        model.crouching = false;
//        model.swimAmount = 0.0F;
//        model.setupAnim(player, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
//
//        RepulsionShieldItem shield = (RepulsionShieldItem) stack.getItem();
//
//        IAetherPlayer aetherPlayer = IAetherPlayer.get(player).orElse(null);
//        if (!aetherPlayer.isMoving()) {
//            texture = isSlim ? shield.getRepulsionShieldSlimTexture() : shield.getRepulsionShieldTexture();
//        } else {
//            texture = isSlim ? shield.getRepulsionShieldSlimInactiveTexture() : shield.getRepulsionShieldInactiveTexture();
//        }
//
//        VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(bufferSource, RenderType.entityTranslucent(texture), false, stack.isEnchanted());
//        if (arm == HumanoidArm.RIGHT) {
//            this.renderArm(model.rightArm, poseStack, combinedLight, vertexConsumer);
//        } else if (arm == HumanoidArm.LEFT) {
//            this.renderArm(model.leftArm, poseStack, combinedLight, vertexConsumer);
//        }
//    }
//
//    private void renderArm(ModelPart shieldArm, PoseStack poseStack, int combinedLight, VertexConsumer vertexConsumer) {
//        shieldArm.visible = true;
//        shieldArm.xRot = 0.0F;
//        shieldArm.render(poseStack, vertexConsumer, combinedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
//    }
}
