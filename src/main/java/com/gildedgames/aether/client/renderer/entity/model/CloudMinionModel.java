package com.gildedgames.aether.client.renderer.entity.model;

import com.gildedgames.aether.common.entity.miscellaneous.CloudMinionEntity;
import com.google.common.collect.ImmutableList;

import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CloudMinionModel extends ListModel<CloudMinionEntity>
{
    public ModelPart main = new ModelPart(this);

    public CloudMinionModel() {
        this.main.texOffs(0, 0).addBox(-4.5F, 14.0F, -4.5F, 9, 9, 9, 0.0F);
        this.main.texOffs(36, 0).addBox(-3.5F, 15.0F, -5.5F, 7, 7, 1, 0.0F);
        this.main.texOffs(36, 0).addBox(-3.5F, 15.0F, 4.5F, 7, 7, 1, 0.0F);
        this.main.texOffs(36, 8).addBox(-5.5F, 15.0F, -3.5F, 1, 7, 7, 0.0F);
        this.main.texOffs(36, 8).addBox(4.5F, 15.0F, -3.5F, 1, 7, 7, 0.0F);
        this.main.setPos(0.0F, 0.0F, 0.0F);
    }

    @Override
    public Iterable<ModelPart> parts() {
        return ImmutableList.of(this.main);
    }

    @Override
    public void setupAnim(CloudMinionEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.main.yRot = 0F;
        this.main.xRot = 0F;
    }
}
