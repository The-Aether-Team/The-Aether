package com.aetherteam.aether.client.renderer.entity.model;

import com.aetherteam.aether.entity.passive.Aerwhale;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class ClassicAerwhaleModel extends EntityModel<Aerwhale> {
    public final ModelPart middleBody;
    public final ModelPart leftFin;
    public final ModelPart head;
    public final ModelPart backFinLeft;
    public final ModelPart backBody;
    public final ModelPart backFinRight;
    public final ModelPart rightFin;

    public ClassicAerwhaleModel(ModelPart root) {
        this.middleBody = root.getChild("middle_body");
        this.head = root.getChild("head");
        this.backBody = root.getChild("back_body");
        this.backFinRight = root.getChild("back_fin_right");
        this.backFinLeft = root.getChild("back_fin_left");
        this.rightFin = root.getChild("fin_right");
        this.leftFin = root.getChild("fin_left");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild("middle_body", CubeListBuilder.create().texOffs(0, 0).addBox(-9.0F, -6.0F, 1.0F, 15.0F, 15.0F, 15.0F), PartPose.offset(0.0F, -1.0F, 14.0F));
        partDefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(60, 0).addBox(-12.0F, -9.0F, -14.0F, 21.0F, 18.0F, 30.0F), PartPose.ZERO);
        partDefinition.addOrReplaceChild("back_body", CubeListBuilder.create().texOffs(0, 30).addBox(-6.0F, -9.0F, -8.5F, 9.0F, 9.0F, 12.0F), PartPose.offset(0.0F, 2.2F, 38.0F));
        partDefinition.addOrReplaceChild("back_fin_right", CubeListBuilder.create().texOffs(0, 51).addBox(-4.0F, 0.0F, -6.0F, 24.0F, 3.0F, 12.0F), PartPose.offsetAndRotation(-5.0F, 2.2F, 38.0F, -0.10471975511965977F, -2.5497515042385164F, 0.0F));
        partDefinition.addOrReplaceChild("back_fin_left", CubeListBuilder.create().texOffs(0, 51).addBox(-4.0F, 0.0F, -6.0F, 24.0F, 3.0F, 12.0F), PartPose.offsetAndRotation(3.0F, 2.2F, 38.0F, 0.10471975511965977F, -0.593411945678072F, 0.0F));
        partDefinition.addOrReplaceChild("fin_right", CubeListBuilder.create().texOffs(0, 66).addBox(-12.0F, 1.4F, -6.0F, 12.0F, 3.0F, 6.0F), PartPose.offsetAndRotation(-10.0F, 4.0F, 10.0F, 0.0F, 0.17453292519943295F, 0.0F));
        partDefinition.addOrReplaceChild("fin_left", CubeListBuilder.create().texOffs(0, 66).addBox(0.0F, 1.4F, -6.0F, 12.0F, 3.0F, 6.0F), PartPose.offsetAndRotation(7.0F, 4.0F, 10.0F, 0.0F, -0.17453292519943295F, 0.0F));
        return LayerDefinition.create(meshDefinition, 192, 96);
    }

    @Override
    public void setupAnim(Aerwhale aerwhale, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.middleBody.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.head.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.backBody.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.backFinRight.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.backFinLeft.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.rightFin.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.leftFin.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
