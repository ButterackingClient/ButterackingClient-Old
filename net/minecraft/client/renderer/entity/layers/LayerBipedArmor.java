/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelBiped;
/*    */ import net.minecraft.client.renderer.entity.RendererLivingEntity;
/*    */ 
/*    */ public class LayerBipedArmor extends LayerArmorBase<ModelBiped> {
/*    */   public LayerBipedArmor(RendererLivingEntity<?> rendererIn) {
/*  8 */     super(rendererIn);
/*    */   }
/*    */   
/*    */   protected void initArmor() {
/* 12 */     this.modelLeggings = new ModelBiped(0.5F);
/* 13 */     this.modelArmor = new ModelBiped(1.0F);
/*    */   }
/*    */   
/*    */   protected void setModelPartVisible(ModelBiped model, int armorSlot) {
/* 17 */     setModelVisible(model);
/*    */     
/* 19 */     switch (armorSlot) {
/*    */       case 1:
/* 21 */         model.bipedRightLeg.showModel = true;
/* 22 */         model.bipedLeftLeg.showModel = true;
/*    */         break;
/*    */       
/*    */       case 2:
/* 26 */         model.bipedBody.showModel = true;
/* 27 */         model.bipedRightLeg.showModel = true;
/* 28 */         model.bipedLeftLeg.showModel = true;
/*    */         break;
/*    */       
/*    */       case 3:
/* 32 */         model.bipedBody.showModel = true;
/* 33 */         model.bipedRightArm.showModel = true;
/* 34 */         model.bipedLeftArm.showModel = true;
/*    */         break;
/*    */       
/*    */       case 4:
/* 38 */         model.bipedHead.showModel = true;
/* 39 */         model.bipedHeadwear.showModel = true;
/*    */         break;
/*    */     } 
/*    */   }
/*    */   protected void setModelVisible(ModelBiped model) {
/* 44 */     model.setInvisible(false);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\layers\LayerBipedArmor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */