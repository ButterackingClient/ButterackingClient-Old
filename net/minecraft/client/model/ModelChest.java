/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ 
/*    */ public class ModelChest
/*    */   extends ModelBase
/*    */ {
/*  7 */   public ModelRenderer chestLid = (new ModelRenderer(this, 0, 0)).setTextureSize(64, 64);
/*    */ 
/*    */ 
/*    */   
/*    */   public ModelRenderer chestBelow;
/*    */ 
/*    */ 
/*    */   
/*    */   public ModelRenderer chestKnob;
/*    */ 
/*    */ 
/*    */   
/*    */   public ModelChest() {
/* 20 */     this.chestLid.addBox(0.0F, -5.0F, -14.0F, 14, 5, 14, 0.0F);
/* 21 */     this.chestLid.rotationPointX = 1.0F;
/* 22 */     this.chestLid.rotationPointY = 7.0F;
/* 23 */     this.chestLid.rotationPointZ = 15.0F;
/* 24 */     this.chestKnob = (new ModelRenderer(this, 0, 0)).setTextureSize(64, 64);
/* 25 */     this.chestKnob.addBox(-1.0F, -2.0F, -15.0F, 2, 4, 1, 0.0F);
/* 26 */     this.chestKnob.rotationPointX = 8.0F;
/* 27 */     this.chestKnob.rotationPointY = 7.0F;
/* 28 */     this.chestKnob.rotationPointZ = 15.0F;
/* 29 */     this.chestBelow = (new ModelRenderer(this, 0, 19)).setTextureSize(64, 64);
/* 30 */     this.chestBelow.addBox(0.0F, 0.0F, 0.0F, 14, 10, 14, 0.0F);
/* 31 */     this.chestBelow.rotationPointX = 1.0F;
/* 32 */     this.chestBelow.rotationPointY = 6.0F;
/* 33 */     this.chestBelow.rotationPointZ = 1.0F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderAll() {
/* 40 */     this.chestKnob.rotateAngleX = this.chestLid.rotateAngleX;
/* 41 */     this.chestLid.render(0.0625F);
/* 42 */     this.chestKnob.render(0.0625F);
/* 43 */     this.chestBelow.render(0.0625F);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\model\ModelChest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */