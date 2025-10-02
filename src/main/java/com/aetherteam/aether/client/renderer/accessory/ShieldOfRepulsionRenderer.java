package com.aetherteam.aether.client.renderer.accessory;

import com.aetherteam.aether.capability.player.AetherPlayer;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
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
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import java.util.Optional;

public class ShieldOfRepulsionRenderer implements ICurioRenderer, FirstPersonRendering {
    private final HumanoidModel<LivingEntity> shieldModel;
    private final PlayerModel<LivingEntity> shieldModelSlim;
    @Deprecated
    public final HumanoidModel<LivingEntity> shieldModelArm;
    @Deprecated
    public final PlayerModel<LivingEntity> dummyArm;
    @Deprecated
    public final PlayerModel<LivingEntity> dummyArmSlim;

    public ShieldOfRepulsionRenderer() {
        this.shieldModel = new HumanoidModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(AetherModelLayers.SHIELD_OF_REPULSION));
        this.shieldModelSlim = new PlayerModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(AetherModelLayers.SHIELD_OF_REPULSION_SLIM) , true);
        this.shieldModelArm = new HumanoidModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(AetherModelLayers.SHIELD_OF_REPULSION_ARM));
        this.dummyArm = new PlayerModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.PLAYER), false);
        this.dummyArmSlim = new PlayerModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.PLAYER_SLIM), true);
    }

    /**
     * Renders the Shield of Repulsion overlay over the player's model in third person.
     * @param stack The {@link ItemStack} for the Curio.
     * @param slotContext The {@link SlotContext} for the Curio.
     * @param poseStack The rendering {@link PoseStack}.
     * @param renderLayerParent The {@link RenderLayerParent} for the renderer.
     * @param buffer The rendering {@link MultiBufferSource}.
     * @param packedLight The {@link Integer} for the packed lighting for rendering.
     * @param limbSwing The {@link Float} for the limb swing rotation.
     * @param limbSwingAmount The {@link Float} for the limb swing amount.
     * @param partialTicks The {@link Float} for the game's partial ticks.
     * @param ageInTicks The {@link Float} for the entity's age in ticks.
     * @param netHeadYaw The {@link Float} for the head yaw rotation.
     * @param headPitch The {@link Float} for the head pitch rotation.
     */
    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack poseStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource buffer, int packedLight, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        LivingEntity livingEntity = slotContext.entity();
        ShieldOfRepulsionItem shield = (ShieldOfRepulsionItem) stack.getItem();
        ResourceLocation texture;
        HumanoidModel<LivingEntity> model;

        if (livingEntity instanceof Player player && renderLayerParent.getModel() instanceof PlayerModel<?> playerModel) {
            PlayerModelAccessor playerModelAccessor = (PlayerModelAccessor) playerModel;
            Vec3 motion = player.getDeltaMovement();
            model = playerModelAccessor.aether$getSlim() ? this.shieldModelSlim : this.shieldModel;
            Optional<AetherPlayer> aetherPlayerOptional = AetherPlayer.get(player).resolve();
            if (aetherPlayerOptional.isPresent()) {
                if (!aetherPlayerOptional.get().isMoving() || (aetherPlayerOptional.get().isMoving() && motion.x() == 0.0 && (motion.y() == ConstantsUtil.DEFAULT_DELTA_MOVEMENT_Y || motion.y() == 0.0) && motion.z() == 0.0)) {
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
        model.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public <M extends LivingEntity> void renderOnFirstPerson(HumanoidArm arm, ItemStack stack, LivingEntity livingEntity, PoseStack matrices, EntityModel<M> model, MultiBufferSource multiBufferSource, int light) {
        if (livingEntity instanceof AbstractClientPlayer player && model instanceof PlayerModel<M> playerModel) {
            this.renderFirstPerson(stack, matrices, multiBufferSource, light, player, playerModel, arm);
        }
    }

    /**
     * Renders the Shield of Repulsion overlay over the player's hands in first person.
     * @param stack The {@link ItemStack} for the Curio.
     * @param poseStack The rendering {@link PoseStack}.
     * @param buffer The rendering {@link MultiBufferSource}.
     * @param packedLight The {@link Integer} for the packed lighting for rendering.
     * @param player The {@link AbstractClientPlayer} to render for.
     * @param arm The {@link HumanoidArm} to render on.
     */
    public void renderFirstPerson(ItemStack stack, PoseStack poseStack, MultiBufferSource buffer, int packedLight, AbstractClientPlayer player, PlayerModel<?> playerModel, HumanoidArm arm) {
        ShieldOfRepulsionItem shield = (ShieldOfRepulsionItem) stack.getItem();
        ResourceLocation texture;
        Vec3 motion = player.getDeltaMovement();
        Optional<AetherPlayer> aetherPlayerOptional = AetherPlayer.get(player).resolve();
        if (aetherPlayerOptional.isPresent()) {
            if (!aetherPlayerOptional.get().isMoving() || (aetherPlayerOptional.get().isMoving() && motion.x() == 0.0 && (motion.y() == ConstantsUtil.DEFAULT_DELTA_MOVEMENT_Y || motion.y() == 0.0) && motion.z() == 0.0)) {
                texture = shield.getShieldOfRepulsionTexture();
            } else {
                texture = shield.getShieldOfRepulsionInactiveTexture();
            }
        } else {
            texture = shield.getShieldOfRepulsionInactiveTexture();
        }
        VertexConsumer consumer = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.entityTranslucent(texture), false, stack.isEnchanted());

        HumanoidModel<LivingEntity> model = player.getModelName().equals("slim") ? this.shieldModelSlim : shieldModel;
        ModelPart shieldArm = arm == HumanoidArm.RIGHT ? model.rightArm : model.leftArm;
        ModelPart playerArm = arm == HumanoidArm.RIGHT ? playerModel.rightArm : playerModel.leftArm;
        model.setupAnim(player, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        shieldArm.copyFrom(playerArm);
        shieldArm.xRot = 0.0F;
        shieldArm.render(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
