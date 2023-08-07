package com.aetherteam.aether.client.renderer.entity.model;

import com.aetherteam.aether.entity.passive.Aerwhale;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class AerwhaleModel extends EntityModel<Aerwhale> {
	public final ModelPart head;

	public AerwhaleModel(ModelPart root) {
		this.head = root.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition partDefinition = meshDefinition.getRoot();
		PartDefinition head = partDefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(104, 36).addBox(-12.0F, -6.0F, -4.0F, 24.0F, 6.0F, 26.0F, CubeDeformation.NONE).texOffs(104, 0).addBox(-13.0F, -6.0F, -32.0F, 26.0F, 6.0F, 30.0F, CubeDeformation.NONE).texOffs(0, 0).addBox(-12.0F, -19.0F, -31.0F, 24.0F, 18.0F, 28.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, -0.0436F, 0.0F, 0.0F));
		head.addOrReplaceChild("fin", CubeListBuilder.create().texOffs(0, 46).addBox(-2.0F, -5.0F, 0.0F, 2.0F, 7.0F, 8.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(1.0F, -16.5F, 4.0F, -0.1309F, 0.0F, 0.0F));
		head.addOrReplaceChild("middle_top", CubeListBuilder.create().texOffs(0, 46).addBox(1.0F, 2.0F, 0.5F, 22.0F, 14.0F, 25.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-12.0F, -19.0F, -4.0F, -0.0873F, 0.0F, 0.0F));
		head.addOrReplaceChild("flipper_right", CubeListBuilder.create().texOffs(104, 94).mirror().addBox(-19.0F, 2.0F, 0.0F, 19.0F, 3.0F, 14.0F, CubeDeformation.NONE).mirror(false), PartPose.offsetAndRotation(-12.0F, -11.0F, -12.0F, -0.1309F, 0.2618F, -0.0873F));
		head.addOrReplaceChild("flipper_left", CubeListBuilder.create().texOffs(104, 94).addBox(0.0F, 2.0F, 0.0F, 19.0F, 3.0F, 14.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(12.0F, -11.0F, -12.0F, -0.1309F, -0.2618F, 0.0873F));
		head.addOrReplaceChild("tail_right", CubeListBuilder.create().texOffs(170, 84).mirror().addBox(0.0F, 2.0F, 0.0F, 15.0F, 3.0F, 24.0F, CubeDeformation.NONE).mirror(false), PartPose.offsetAndRotation(-8.5F, -7.0F, 30.0F, -0.1309F, -0.7854F, -0.0436F));
		head.addOrReplaceChild("tail_left", CubeListBuilder.create().texOffs(170, 84).addBox(-15.0F, 2.0F, 0.0F, 15.0F, 3.0F, 24.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(8.5F, -7.0F, 30.0F, -0.1309F, 0.7854F, 0.0436F));
		head.addOrReplaceChild("back", CubeListBuilder.create().texOffs(104, 68).addBox(2.0F, 11.0F, 2.5F, 19.0F, 5.0F, 21.0F, CubeDeformation.NONE).texOffs(0, 85).addBox(3.0F, 2.0F, 1.0F, 17.0F, 9.0F, 22.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-11.5F, -17.0F, 20.0F, -0.2182F, 0.0F, 0.0F));
		return LayerDefinition.create(meshDefinition, 256, 128);
	}

	@Override
	public void setupAnim(Aerwhale aerwhale, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) { }

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.head.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
