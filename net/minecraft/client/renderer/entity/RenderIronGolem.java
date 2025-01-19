/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelIronGolem;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.monster.EntityIronGolem;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderIronGolem extends RenderLiving<EntityIronGolem> {
/* 10 */   private static final ResourceLocation ironGolemTextures = new ResourceLocation("textures/entity/iron_golem.png");
/*    */   
/*    */   public RenderIronGolem(RenderManager renderManagerIn) {
/* 13 */     super(renderManagerIn, (ModelBase)new ModelIronGolem(), 0.5F);
/* 14 */     addLayer(new LayerIronGolemFlower(this));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityIronGolem entity) {
/* 21 */     return ironGolemTextures;
/*    */   }
/*    */   
/*    */   protected void rotateCorpse(EntityIronGolem bat, float p_77043_2_, float p_77043_3_, float partialTicks) {
/* 25 */     super.rotateCorpse(bat, p_77043_2_, p_77043_3_, partialTicks);
/*    */     
/* 27 */     if (bat.limbSwingAmount >= 0.01D) {
/* 28 */       float f = 13.0F;
/* 29 */       float f1 = bat.limbSwing - bat.limbSwingAmount * (1.0F - partialTicks) + 6.0F;
/* 30 */       float f2 = (Math.abs(f1 % f - f * 0.5F) - f * 0.25F) / f * 0.25F;
/* 31 */       GlStateManager.rotate(6.5F * f2, 0.0F, 0.0F, 1.0F);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\RenderIronGolem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */