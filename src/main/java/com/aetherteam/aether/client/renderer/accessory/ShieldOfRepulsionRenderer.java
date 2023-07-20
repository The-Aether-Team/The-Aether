package com.aetherteam.aether.client.renderer.accessory;

import com.aetherteam.aether.capability.player.AetherPlayer;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.item.accessories.miscellaneous.ShieldOfRepulsionItem;
import com.aetherteam.aether.mixin.mixins.client.accessor.PlayerModelAccessor;
import com.aetherteam.nitrogen.ConstantsUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
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

import java.util.Optional;

public class ShieldOfRepulsionRenderer implements ICurioRenderer {
    private final HumanoidModel<LivingEntity> shieldModel;
    private final PlayerModel<LivingEntity> shieldModelSlim;
    public final HumanoidModel<LivingEntity> shieldModelArm;
    public final PlayerModel<LivingEntity> shieldModelArmSlim;
    public final PlayerModel<LivingEntity> dummyArm;
    public final PlayerModel<LivingEntity> dummyArmSlim;

    public ShieldOfRepulsionRenderer() {
        this.shieldModel = new HumanoidModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(AetherModelLayers.SHIELD_OF_REPULSION));
        this.shieldModelSlim = new PlayerModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(AetherModelLayers.SHIELD_OF_REPULSION_SLIM) , true);
        this.shieldModelArm = new HumanoidModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(AetherModelLayers.SHIELD_OF_REPULSION_ARM));
        this.shieldModelArmSlim = new PlayerModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(AetherModelLayers.SHIELD_OF_REPULSION_ARM_SLIM) , true);
        this.dummyArm = new PlayerModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.PLAYER), false);
        this.dummyArmSlim = new PlayerModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.PLAYER_SLIM), true);
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack poseStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource buffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        LivingEntity livingEntity = slotContext.entity();
        CuriosApi.getCuriosHelper().findFirstCurio(livingEntity, AetherItems.SHIELD_OF_REPULSION.get()).ifPresent((slotResult) -> CuriosApi.getCuriosHelper().getCuriosHandler(livingEntity).ifPresent(handler ->
                handler.getStacksHandler(slotResult.slotContext().identifier()).ifPresent(stacksHandler -> {
                    ShieldOfRepulsionItem shield = (ShieldOfRepulsionItem) slotResult.stack().getItem();
                    if (stacksHandler.getRenders().get(slotResult.slotContext().index())) {
                        ResourceLocation texture;
                        HumanoidModel<LivingEntity> model;

                        if (livingEntity instanceof Player player && renderLayerParent.getModel() instanceof PlayerModel<?> playerModel) {
                            PlayerModelAccessor playerModelAccessor = (PlayerModelAccessor) playerModel;
                            model = playerModelAccessor.aether$getSlim() ? this.shieldModelSlim : this.shieldModel;
                            Optional<AetherPlayer> aetherPlayerOptional = AetherPlayer.get(player).resolve();
                            if (aetherPlayerOptional.isPresent()) {
                                if (!aetherPlayerOptional.get().isMoving()) {
                                    texture = playerModelAccessor.aether$getSlim() ? shield.getShieldOfRepulsionSlimTexture() : shield.getShieldOfRepulsionTexture();
                                } else {
                                    texture = playerModelAccessor.aether$getSlim() ? shield.getShieldOfRepulsionSlimInactiveTexture() : shield.getShieldOfRepulsionInactiveTexture();
                                }
                            } else {
                                texture = playerModelAccessor.aether$getSlim() ? shield.getShieldOfRepulsionSlimInactiveTexture() : shield.getShieldOfRepulsionInactiveTexture();
                            }
                        } else {
                            model = this.shieldModel;
                            Vec3 motion = livingEntity.getDeltaMovement();
                            if (motion.x() == 0.0 && (motion.y() == ConstantsUtil.DEFAULT_DELTA_MOVEMENT_Y || motion.y() == 0.0) && motion.z() == 0.0) {
                                texture = shield.getShieldOfRepulsionTexture();
                            } else {
                                texture = shield.getShieldOfRepulsionInactiveTexture();
                            }
                        }

                        ICurioRenderer.followHeadRotations(slotContext.entity(), model.head);
                        ICurioRenderer.followBodyRotations(slotContext.entity(), model);
                        VertexConsumer consumer = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.entityTranslucent(texture), false, false);
                        model.renderToBuffer(poseStack, consumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
                    }
                })
        ));
    }

    public void renderFirstPerson(ItemStack stack, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, AbstractClientPlayer player, HumanoidArm arm) {
        boolean isSlim = player.getModelName().equals("slim");
        if (!player.isInvisible()) {
            this.setupHand(isSlim ? this.dummyArmSlim : this.dummyArm, poseStack, buffer, combinedLight, player, arm);
        }
        this.setupShield(stack, isSlim ? this.shieldModelArmSlim : this.shieldModelArm, poseStack, buffer, combinedLight, player, arm, isSlim);
    }

    private void setupShield(ItemStack stack, HumanoidModel<LivingEntity> model, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, AbstractClientPlayer player, HumanoidArm arm, boolean isSlim) {
        this.setupModel(model, player);

        ResourceLocation texture;
        ShieldOfRepulsionItem shield = (ShieldOfRepulsionItem) stack.getItem();

        Optional<AetherPlayer> aetherPlayerOptional = AetherPlayer.get(player).resolve();
        if (aetherPlayerOptional.isPresent()) {
            if (!aetherPlayerOptional.get().isMoving()) {
                texture = isSlim ? shield.getShieldOfRepulsionSlimTexture() : shield.getShieldOfRepulsionTexture();
            } else {
                texture = isSlim ? shield.getShieldOfRepulsionSlimInactiveTexture() : shield.getShieldOfRepulsionInactiveTexture();
            }
        } else {
            texture = isSlim ? shield.getShieldOfRepulsionSlimInactiveTexture() : shield.getShieldOfRepulsionInactiveTexture();
        }

        VertexConsumer consumer = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.entityTranslucent(texture), false, stack.isEnchanted());
        if (arm == HumanoidArm.RIGHT) {
            this.renderShield(model.rightArm, poseStack, combinedLight, consumer);
        } else if (arm == HumanoidArm.LEFT) {
            this.renderShield(model.leftArm, poseStack, combinedLight, consumer);
        }
    }

    private void setupHand(PlayerModel<LivingEntity> model, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, AbstractClientPlayer player, HumanoidArm arm) {
        this.setupModel(model, player);

        Optional<AetherPlayer> aetherPlayerOptional = AetherPlayer.get(player).resolve();
        if (aetherPlayerOptional.isPresent()) {
            if (!aetherPlayerOptional.get().isMoving()) {
                VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityTranslucent(player.getSkinTextureLocation()));
                if (arm == HumanoidArm.RIGHT) {
                    this.renderHand(model.rightArm, model.rightSleeve, poseStack, combinedLight, vertexConsumer);
                } else if (arm == HumanoidArm.LEFT) {
                    this.renderHand(model.leftArm, model.leftSleeve, poseStack, combinedLight, vertexConsumer);
                }
            }
        }
    }

    private void setupModel(HumanoidModel<LivingEntity> model, AbstractClientPlayer player) {
        model.setAllVisible(false);
        model.attackTime = 0.0F;
        model.crouching = false;
        model.swimAmount = 0.0F;
        model.setupAnim(player, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
    }

    private void renderShield(ModelPart shieldArm, PoseStack poseStack, int combinedLight, VertexConsumer consumer) {
        shieldArm.visible = true;
        shieldArm.xRot = 0.0F;
        shieldArm.render(poseStack, consumer, combinedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    private void renderHand(ModelPart dummyArm, ModelPart dummySleeve, PoseStack poseStack, int combinedLight, VertexConsumer consumer) {
        dummyArm.visible = true;
        dummySleeve.visible = true;
        dummyArm.xRot = 0.0F;
        dummySleeve.xRot = 0.0F;
        dummyArm.render(poseStack, consumer, combinedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        dummySleeve.render(poseStack, consumer, combinedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
