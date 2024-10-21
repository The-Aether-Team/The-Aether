package com.aetherteam.aether.client.renderer.accessory;

import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.accessory.model.GlovesModel;
import com.aetherteam.aether.item.accessories.gloves.GlovesItem;
import com.aetherteam.aether.mixin.mixins.client.accessor.ItemInHandRendererAccessor;
import com.aetherteam.aether.mixin.mixins.client.accessor.PlayerModelAccessor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.wispforest.accessories.api.client.AccessoryRenderer;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.FastColor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.item.component.DyedItemColor;

public class GlovesRenderer implements AccessoryRenderer {
    private final GlovesModel glovesModel;
    private final GlovesModel glovesTrimModel;
    private final GlovesModel glovesModelSlim;
    private final GlovesModel glovesTrimModelSlim;
    private final GlovesModel glovesFirstPerson;
    private final GlovesModel glovesTrimFirstPerson;
    private final TextureAtlas armorTrimAtlas;

    public GlovesRenderer() {
        this.glovesModel = new GlovesModel(Minecraft.getInstance().getEntityModels().bakeLayer(AetherModelLayers.GLOVES));
        this.glovesTrimModel = new GlovesModel(Minecraft.getInstance().getEntityModels().bakeLayer(AetherModelLayers.GLOVES_TRIM));
        this.glovesModelSlim = new GlovesModel(Minecraft.getInstance().getEntityModels().bakeLayer(AetherModelLayers.GLOVES_SLIM));
        this.glovesTrimModelSlim = new GlovesModel(Minecraft.getInstance().getEntityModels().bakeLayer(AetherModelLayers.GLOVES_TRIM_SLIM));
        this.glovesFirstPerson = new GlovesModel(Minecraft.getInstance().getEntityModels().bakeLayer(AetherModelLayers.GLOVES_FIRST_PERSON));
        this.glovesTrimFirstPerson = new GlovesModel(Minecraft.getInstance().getEntityModels().bakeLayer(AetherModelLayers.GLOVES_TRIM_FIRST_PERSON));
        this.armorTrimAtlas = Minecraft.getInstance().getModelManager().getAtlas(Sheets.ARMOR_TRIMS_SHEET);
    }

    /**
     * Renders gloves in third person on the player's model.
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
        GlovesItem glovesItem = (GlovesItem) stack.getItem();
        GlovesModel model = this.glovesModel;
        GlovesModel trimModel = this.glovesTrimModel;
        ResourceLocation texture = glovesItem.getGlovesTexture();

        if (entityModel instanceof PlayerModel<?> playerModel) {
            PlayerModelAccessor playerModelAccessor = (PlayerModelAccessor) playerModel;
            model = playerModelAccessor.aether$getSlim() ? this.glovesModelSlim : this.glovesModel;
            trimModel = playerModelAccessor.aether$getSlim() ? this.glovesTrimModelSlim : this.glovesTrimModel;
        }

        AccessoryRenderer.followBodyRotations(reference.entity(), model);
        AccessoryRenderer.followBodyRotations(reference.entity(), trimModel);

        int color = stack.is(ItemTags.DYEABLE) ? FastColor.ARGB32.opaque(DyedItemColor.getOrDefault(stack, -6265536)) : -1;
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.armorCutoutNoCull(texture));
        model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, color);

        GlovesModel finalTrimModel = trimModel;

        ArmorTrim trim = stack.get(DataComponents.TRIM);
        if (trim != null) {
            TextureAtlasSprite textureAtlasSprite = this.armorTrimAtlas.getSprite(trim.outerTexture(glovesItem.getMaterial()));
            VertexConsumer trimConsumer = textureAtlasSprite.wrap(buffer.getBuffer(Sheets.armorTrimsSheet(trim.pattern().value().decal())));
            finalTrimModel.renderToBuffer(poseStack, trimConsumer, packedLight, OverlayTexture.NO_OVERLAY);
        }

        if (stack.hasFoil()) {
            model.renderToBuffer(poseStack, buffer.getBuffer(RenderType.armorEntityGlint()), packedLight, OverlayTexture.NO_OVERLAY);
        }
    }

    @Override
    public boolean shouldRenderInFirstPerson(HumanoidArm arm, ItemStack stack, SlotReference reference) {
        return !(reference.entity() instanceof Player player) || !player.getData(AetherDataAttachments.AETHER_PLAYER).isWearingInvisibilityCloak();
    }

    @Override
    public <M extends LivingEntity> void renderOnFirstPerson(HumanoidArm arm, ItemStack stack, SlotReference reference, PoseStack matrices, EntityModel<M> model, MultiBufferSource multiBufferSource, int light) {
        LivingEntity livingEntity = reference.entity();
        if (livingEntity instanceof AbstractClientPlayer player) {
            this.renderFirstPerson(stack, matrices, multiBufferSource, light, player, arm);
        }
    }

    /**
     * Renders a glove in the player's hand in first person.
     *
     * @param stack       The {@link ItemStack} for the Curio.
     * @param poseStack   The rendering {@link PoseStack}.
     * @param buffer      The rendering {@link MultiBufferSource}.
     * @param packedLight The {@link Integer} for the packed lighting for rendering.
     * @param player      The {@link AbstractClientPlayer} to render for.
     * @param arm         The {@link HumanoidArm} to render on.
     */
    public void renderFirstPerson(ItemStack stack, PoseStack poseStack, MultiBufferSource buffer, int packedLight, AbstractClientPlayer player, HumanoidArm arm) {
        poseStack.pushPose();
        GlovesModel model = this.glovesFirstPerson;
        GlovesModel trimModel = this.glovesTrimFirstPerson;

        GlovesItem glovesItem = (GlovesItem) stack.getItem();
        VertexConsumer consumer = buffer.getBuffer(RenderType.armorCutoutNoCull(glovesItem.getGlovesTexture()));

        int color = stack.is(ItemTags.DYEABLE) ? FastColor.ARGB32.opaque(DyedItemColor.getOrDefault(stack, -6265536)) : -1;

        model.setAllVisible(false);
        model.attackTime = 0.0F;
        model.crouching = false;
        model.swimAmount = 0.0F;
        model.setupAnim(player, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);

        ModelPart gloveArm = arm == HumanoidArm.RIGHT ? model.rightArm : model.leftArm;
        gloveArm.visible = true;
        gloveArm.xRot = 0.0F;

        boolean isSlim = player.getSkin().model() == PlayerSkin.Model.SLIM;
        boolean flag = arm != HumanoidArm.LEFT;
        float f = flag ? 1.0F : -1.0F;
        float offset = 0.0375F;
        if (isSlim) {
            offset = 0.0425F;
        }
        poseStack.translate((f * offset) - 0.0025, 0.0025, -0.0025);

        gloveArm.render(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, color);

        ArmorTrim trim = stack.get(DataComponents.TRIM);
        if (trim != null) {
            trimModel.setAllVisible(false);
            trimModel.attackTime = 0.0F;
            trimModel.crouching = false;
            trimModel.swimAmount = 0.0F;
            trimModel.setupAnim(player, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);

            ModelPart gloveTrimArm = arm == HumanoidArm.RIGHT ? trimModel.rightArm : trimModel.leftArm;
            gloveTrimArm.visible = true;
            gloveTrimArm.xRot = 0.0F;

            TextureAtlasSprite textureAtlasSprite = this.armorTrimAtlas.getSprite(trim.outerTexture(glovesItem.getMaterial()));
            VertexConsumer trimConsumer = textureAtlasSprite.wrap(buffer.getBuffer(Sheets.armorTrimsSheet(trim.pattern().value().decal())));
            gloveTrimArm.render(poseStack, trimConsumer, packedLight, OverlayTexture.NO_OVERLAY);
        }
        if (stack.hasFoil()) {
            gloveArm.render(poseStack, buffer.getBuffer(RenderType.armorEntityGlint()), packedLight, OverlayTexture.NO_OVERLAY);
        }
        poseStack.popPose();
    }
}
