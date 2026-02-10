package com.ombremoon.spellbound.client.renderer.entity.familiar;

import com.ombremoon.spellbound.common.world.entity.living.familiars.CatEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.ModelUtils;
import net.minecraft.client.model.OcelotModel;
import net.minecraft.client.model.geom.ModelPart;

public class CatModel extends OcelotModel<CatEntity> {
    private boolean isLying;

    public CatModel(ModelPart root) {
        super(root);
    }

    public void prepareMobModel(CatEntity entity, float limbSwing, float limbSwingAmount, float partialTick) {
//        this.isLying = entity.isLying();
//        if (this.isLying) {
//            this.head.xRot = 0.0F;
//            this.head.zRot = 0.0F;
//            this.leftFrontLeg.xRot = 0.0F;
//            this.leftFrontLeg.zRot = 0.0F;
//            this.rightFrontLeg.xRot = 0.0F;
//            this.rightFrontLeg.zRot = 0.0F;
//            this.rightFrontLeg.x = -1.2F;
//            this.leftHindLeg.xRot = 0.0F;
//            this.rightHindLeg.xRot = 0.0F;
//            this.rightHindLeg.zRot = 0.0F;
//            this.rightHindLeg.x = -1.1F;
//            this.rightHindLeg.y = 18.0F;
//        }

        super.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTick);
//        if (entity.isSitting()) {
//            this.body.xRot = ((float)Math.PI / 4F);
//            ModelPart var10000 = this.body;
//            var10000.y += -4.0F;
//            var10000 = this.body;
//            var10000.z += 5.0F;
//            var10000 = this.head;
//            var10000.y += -3.3F;
//            ++this.head.z;
//            var10000 = this.tail1;
//            var10000.y += 8.0F;
//            var10000 = this.tail1;
//            var10000.z += -2.0F;
//            var10000 = this.tail2;
//            var10000.y += 2.0F;
//            var10000 = this.tail2;
//            var10000.z += -0.8F;
//            this.tail1.xRot = 1.7278761F;
//            this.tail2.xRot = 2.670354F;
//            this.leftFrontLeg.xRot = -0.15707964F;
//            this.leftFrontLeg.y = 16.1F;
//            this.leftFrontLeg.z = -7.0F;
//            this.rightFrontLeg.xRot = -0.15707964F;
//            this.rightFrontLeg.y = 16.1F;
//            this.rightFrontLeg.z = -7.0F;
//            this.leftHindLeg.xRot = (-(float)Math.PI / 2F);
//            this.leftHindLeg.y = 21.0F;
//            this.leftHindLeg.z = 1.0F;
//            this.rightHindLeg.xRot = (-(float)Math.PI / 2F);
//            this.rightHindLeg.y = 21.0F;
//            this.rightHindLeg.z = 1.0F;
//            this.state = 3;
//        }

    }

    public void setupAnim(CatEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
//        if (this.isLying) {
//            this.head.zRot = ModelUtils.rotlerpRad(this.head.zRot, -1.2707963F, 1);
//            this.head.yRot = ModelUtils.rotlerpRad(this.head.yRot, 1.2707963F, 1);
//            this.leftFrontLeg.xRot = -1.2707963F;
//            this.rightFrontLeg.xRot = -0.47079635F;
//            this.rightFrontLeg.zRot = -0.2F;
//            this.rightFrontLeg.x = -0.2F;
//            this.leftHindLeg.xRot = -0.4F;
//            this.rightHindLeg.xRot = 0.5F;
//            this.rightHindLeg.zRot = -0.5F;
//            this.rightHindLeg.x = -0.3F;
//            this.rightHindLeg.y = 20.0F;
//            this.tail1.xRot = ModelUtils.rotlerpRad(this.tail1.xRot, 0.8F, 1);
//            this.tail2.xRot = ModelUtils.rotlerpRad(this.tail2.xRot, -0.4F, 1);
//        }

    }
}
