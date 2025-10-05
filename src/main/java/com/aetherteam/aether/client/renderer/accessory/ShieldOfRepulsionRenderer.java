package com.aetherteam.aether.client.renderer.accessory;

import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.item.accessories.miscellaneous.ShieldOfRepulsionItem;
import com.aetherteam.aether.mixin.mixins.client.accessor.PlayerModelAccessor;
import com.aetherteam.nitrogen.ConstantsUtil;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import io.wispforest.accessories.api.client.AccessoryRenderer;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.function.BiFunction;

public class ShieldOfRepulsionRenderer implements AccessoryRenderer {
    public static final BiFunction<ResourceLocation, Boolean, RenderType> SHIELD_OF_REPULSION_RENDER_TYPE = Util.memoize(
        (location, state) -> {
            RenderType.CompositeState renderType = RenderType.CompositeState.builder()
                .setShaderState(RenderType.RENDERTYPE_ENTITY_TRANSLUCENT_SHADER)
                .setTextureState(new RenderStateShard.TextureStateShard(location, false, false))
                .setTransparencyState(RenderType.TRANSLUCENT_TRANSPARENCY)
                .setCullState(RenderType.NO_CULL)
                .setDepthTestState(RenderType.LEQUAL_DEPTH_TEST)
                .setLightmapState(RenderType.LIGHTMAP)
                .setOverlayState(RenderType.OVERLAY)
                .createCompositeState(state);
            return RenderType.create("aether:shield_of_repulsion", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 1536, true, true, renderType);
        }
    );

    private final HumanoidModel<LivingEntity> shieldModel;
    private final PlayerModel<LivingEntity> shieldModelSlim;
    @Deprecated
    public final HumanoidModel<LivingEntity> shieldModelArm;

    public ShieldOfRepulsionRenderer() {
        this.shieldModel = new HumanoidModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(AetherModelLayers.SHIELD_OF_REPULSION));
        this.shieldModelSlim = new PlayerModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(AetherModelLayers.SHIELD_OF_REPULSION_SLIM) , true);
        this.shieldModelArm = new HumanoidModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(AetherModelLayers.SHIELD_OF_REPULSION_ARM));
    }

    /**
     * Renders the Shield of Repulsion overlay over the player's model in third person.
     *
     * @param stack             The {@link ItemStack} for the accessory.
     * @param reference         The {@link SlotReference} for the accessory.
     * @param poseStack         The rendering {@link PoseStack}.
     * @param entityModel       The {@link EntityModel} for the renderer.
     * @param buffer            The rendering {@link MultiBufferSource}.
     * @param packedLight       The {@link Integer} for the packed lighting for rendering.
     * @param limbSwing         The {@link Float} for the limb swing rotation.
     * @param limbSwingAmount   The {@link Float} for the limb swing amount.
     * @param partialTicks      The {@link Float} for the game's partial ticks.
     * @param ageInTicks        The {@link Float} for the entity's age in ticks.
     * @param netHeadYaw        The {@link Float} for the head yaw rotation.
     * @param headPitch         The {@link Float} for the head pitch rotation.
     */
    @Override
    public <M extends LivingEntity> void render(ItemStack stack, SlotReference reference, PoseStack poseStack, EntityModel<M> entityModel, MultiBufferSource buffer, int packedLight, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        LivingEntity livingEntity = reference.entity();
        ShieldOfRepulsionItem shield = (ShieldOfRepulsionItem) stack.getItem();
        ResourceLocation texture;
        HumanoidModel<LivingEntity> model;

        if (livingEntity instanceof AbstractClientPlayer player && entityModel instanceof PlayerModel<?>) {
            var data = player.getData(AetherDataAttachments.AETHER_PLAYER);
            Vec3 motion = player.getDeltaMovement();
            model = player.getSkin().model() == PlayerSkin.Model.SLIM ? this.shieldModelSlim : this.shieldModel;
            if (!data.isMoving() || (data.isMoving() && motion.x() == 0.0 && (motion.y() == ConstantsUtil.DEFAULT_DELTA_MOVEMENT_Y || motion.y() == 0.0) && motion.z() == 0.0)) {
                texture = player.getSkin().model() == PlayerSkin.Model.SLIM ? shield.getShieldOfRepulsionSlimTexture() : shield.getShieldOfRepulsionTexture();
            } else {
                texture = player.getSkin().model() == PlayerSkin.Model.SLIM ? shield.getShieldOfRepulsionSlimInactiveTexture() : shield.getShieldOfRepulsionInactiveTexture();
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
        entityModel.copyPropertiesTo((EntityModel<M>) model);

        AccessoryRenderer.followBodyRotations(reference.entity(), model);
        VertexConsumer consumer = ItemRenderer.getArmorFoilBuffer(buffer, shieldOfRepulsionRenderType(texture), false);
        model.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY);
    }

    @Override
    public boolean shouldRenderInFirstPerson(HumanoidArm arm, ItemStack stack, SlotReference reference) {
        return !(reference.entity() instanceof Player player) || !player.getData(AetherDataAttachments.AETHER_PLAYER).isWearingInvisibilityCloak();
    }

    @Override
    public <M extends LivingEntity> void renderOnFirstPerson(HumanoidArm arm, ItemStack stack, SlotReference reference, PoseStack matrices, EntityModel<M> model, MultiBufferSource multiBufferSource, int light) {
        LivingEntity livingEntity = reference.entity();
        if (livingEntity instanceof AbstractClientPlayer player && model instanceof PlayerModel<M> playerModel) {
            this.renderFirstPerson(stack, matrices, multiBufferSource, light, player, playerModel, arm);
        }
    }

    /**
     * Renders the Shield of Repulsion overlay over the player's hands in first person.
     * This also renders a dummy model of the player's hand to get around an issue with transparency culling
     * normally making the player's hand invisible.
     *
     * @param stack       The {@link ItemStack} for the accessory.
     * @param poseStack   The rendering {@link PoseStack}.
     * @param buffer      The rendering {@link MultiBufferSource}.
     * @param packedLight The {@link Integer} for the packed lighting for rendering.
     * @param player      The {@link AbstractClientPlayer} to render for.
     * @param arm         The {@link HumanoidArm} to render on.
     */
    public void renderFirstPerson(ItemStack stack, PoseStack poseStack, MultiBufferSource buffer, int packedLight, AbstractClientPlayer player, PlayerModel<?> playerModel, HumanoidArm arm) {
        ShieldOfRepulsionItem shield = (ShieldOfRepulsionItem) stack.getItem();
        ResourceLocation texture;
        Vec3 motion = player.getDeltaMovement();
        var data = player.getData(AetherDataAttachments.AETHER_PLAYER);
        if (!data.isMoving() || (data.isMoving() && motion.x() == 0.0 && (motion.y() == ConstantsUtil.DEFAULT_DELTA_MOVEMENT_Y || motion.y() == 0.0) && motion.z() == 0.0)) {
            texture = shield.getShieldOfRepulsionTexture();
        } else {
            texture = shield.getShieldOfRepulsionInactiveTexture();
        }
        VertexConsumer consumer = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.entityTranslucent(texture), false);

        HumanoidModel<LivingEntity> model = player.getSkin().model() == PlayerSkin.Model.SLIM ? this.shieldModelSlim : this.shieldModel;
        ModelPart shieldArm = arm == HumanoidArm.RIGHT ? model.rightArm : model.leftArm;
        ModelPart playerArm = arm == HumanoidArm.RIGHT ? playerModel.rightArm : playerModel.leftArm;
        this.shieldModel.setupAnim(player, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        shieldArm.copyFrom(playerArm);
        shieldArm.xRot = 0.0F;
        shieldArm.render(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY);
    }

    public static RenderType shieldOfRepulsionRenderType(ResourceLocation location) {
        return SHIELD_OF_REPULSION_RENDER_TYPE.apply(location, true);
    }
}
