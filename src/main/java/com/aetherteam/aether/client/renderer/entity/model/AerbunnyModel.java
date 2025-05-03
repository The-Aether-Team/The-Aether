package com.aetherteam.aether.client.renderer.entity.model;

import com.aetherteam.aether.client.renderer.entity.state.AerbunnyRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import org.joml.Vector3f;

public class AerbunnyModel extends EntityModel<AerbunnyRenderState> {
    public final ModelPart head;
    public final ModelPart rightEar;
    public final ModelPart leftEar;
    public final ModelPart rightWhiskers;
    public final ModelPart leftWhiskers;
    public final ModelPart body;
    public final ModelPart puff;
    public final ModelPart tail;
    public final ModelPart rightFrontLeg;
    public final ModelPart leftFrontLeg;
    public final ModelPart rightBackLeg;
    public final ModelPart leftBackLeg;

    public AerbunnyModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
        this.rightEar = this.head.getChild("right_ear");
        this.leftEar = this.head.getChild("left_ear");
        this.rightWhiskers = this.head.getChild("right_whiskers");
        this.leftWhiskers = this.head.getChild("left_whiskers");
        this.body = root.getChild("body");
        this.puff = root.getChild("puff");
        this.tail = this.body.getChild("tail");
        this.rightFrontLeg = this.body.getChild("right_front_leg");
        this.leftFrontLeg = this.body.getChild("left_front_leg");
        this.rightBackLeg = this.body.getChild("right_back_leg");
        this.leftBackLeg = this.body.getChild("left_back_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition head = partDefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -1.0F, -4.0F, 4.0F, 4.0F, 6.0F), PartPose.offset(0.0F, 15.0F, -4.0F));
        head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(14, 0).addBox(-2.0F, -5.0F, -3.0F, 1.0F, 4.0F, 2.0F), PartPose.ZERO);
        head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(14, 0).addBox(1.0F, -5.0F, -3.0F, 1.0F, 4.0F, 2.0F), PartPose.ZERO);
        head.addOrReplaceChild("right_whiskers", CubeListBuilder.create().texOffs(20, 0).addBox(-4.0F, 0.0F, -3.0F, 2.0F, 3.0F, 2.0F), PartPose.ZERO);
        head.addOrReplaceChild("left_whiskers", CubeListBuilder.create().texOffs(20, 0).addBox(2.0F, 0.0F, -3.0F, 2.0F, 3.0F, 2.0F), PartPose.ZERO);
        PartDefinition body = partDefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 10).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 8.0F, 6.0F), PartPose.offsetAndRotation(0.0F, 16.0F, 0.0F, Mth.HALF_PI, 0.0F, 0.0F));
        partDefinition.addOrReplaceChild("puff", CubeListBuilder.create().texOffs(29, 0).addBox(-3.5F, -3.5F, -3.5F, 7.0F, 7.0F, 7.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, Mth.HALF_PI, 0.0F, 0.0F));
        body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 24).addBox(-2.0F, 4.0F, -2.0F, 4.0F, 3.0F, 4.0F), PartPose.ZERO);
        body.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(24, 16).addBox(0.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F), PartPose.offset(-3.0F, -3.0F, -3.0F));
        body.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(24, 16).addBox(-2.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F), PartPose.offset(3.0F, -3.0F, -3.0F));
        body.addOrReplaceChild("right_back_leg", CubeListBuilder.create().texOffs(16, 24).addBox(0.0F, 0.0F, -4.0F, 2.0F, 2.0F, 4.0F), PartPose.offset(-3.0F, 4.0F, -3.0F));
        body.addOrReplaceChild("left_back_leg", CubeListBuilder.create().texOffs(16, 24).addBox(-2.0F, 0.0F, -4.0F, 2.0F, 2.0F, 4.0F), PartPose.offset(3.0F, 4.0F, -3.0F));
        return LayerDefinition.create(meshDefinition, 64, 32);
    }


    @Override
    public void setupAnim(AerbunnyRenderState aerbunny) {
        super.setupAnim(aerbunny);
        this.head.xRot = aerbunny.xRot * Mth.DEG_TO_RAD;
        this.head.yRot = aerbunny.yRot * Mth.DEG_TO_RAD;
        this.rightFrontLeg.xRot = (Mth.cos(aerbunny.walkAnimationPos * 0.6662F) * 1.0F * aerbunny.walkAnimationSpeed) - this.body.xRot;
        this.leftFrontLeg.xRot = (Mth.cos(aerbunny.walkAnimationPos * 0.6662F) * 1.0F * aerbunny.walkAnimationSpeed) - this.body.xRot;
        this.rightBackLeg.xRot = (Mth.cos(aerbunny.walkAnimationPos * 0.6662F + Mth.PI) * 1.2F * aerbunny.walkAnimationSpeed) - this.body.xRot;
        this.leftBackLeg.xRot = (Mth.cos(aerbunny.walkAnimationPos * 0.6662F + Mth.PI) * 1.2F * aerbunny.walkAnimationSpeed) - this.body.xRot;
        float a = 1.0F + aerbunny.puffiness * 0.5F;
        this.puff.offsetScale(new Vector3f(a, a, a));
    }
}
