/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelVillager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.passive.EntityVillager;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderVillager extends RenderLiving<EntityVillager> {
/* 10 */   private static final ResourceLocation villagerTextures = new ResourceLocation("textures/entity/villager/villager.png");
/* 11 */   private static final ResourceLocation farmerVillagerTextures = new ResourceLocation("textures/entity/villager/farmer.png");
/* 12 */   private static final ResourceLocation librarianVillagerTextures = new ResourceLocation("textures/entity/villager/librarian.png");
/* 13 */   private static final ResourceLocation priestVillagerTextures = new ResourceLocation("textures/entity/villager/priest.png");
/* 14 */   private static final ResourceLocation smithVillagerTextures = new ResourceLocation("textures/entity/villager/smith.png");
/* 15 */   private static final ResourceLocation butcherVillagerTextures = new ResourceLocation("textures/entity/villager/butcher.png");
/*    */   
/*    */   public RenderVillager(RenderManager renderManagerIn) {
/* 18 */     super(renderManagerIn, (ModelBase)new ModelVillager(0.0F), 0.5F);
/* 19 */     addLayer(new LayerCustomHead((getMainModel()).villagerHead));
/*    */   }
/*    */   
/*    */   public ModelVillager getMainModel() {
/* 23 */     return (ModelVillager)super.getMainModel();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityVillager entity) {
/* 30 */     switch (entity.getProfession()) {
/*    */       case 0:
/* 32 */         return farmerVillagerTextures;
/*    */       
/*    */       case 1:
/* 35 */         return librarianVillagerTextures;
/*    */       
/*    */       case 2:
/* 38 */         return priestVillagerTextures;
/*    */       
/*    */       case 3:
/* 41 */         return smithVillagerTextures;
/*    */       
/*    */       case 4:
/* 44 */         return butcherVillagerTextures;
/*    */     } 
/*    */     
/* 47 */     return villagerTextures;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void preRenderCallback(EntityVillager entitylivingbaseIn, float partialTickTime) {
/* 56 */     float f = 0.9375F;
/*    */     
/* 58 */     if (entitylivingbaseIn.getGrowingAge() < 0) {
/* 59 */       f = (float)(f * 0.5D);
/* 60 */       this.shadowSize = 0.25F;
/*    */     } else {
/* 62 */       this.shadowSize = 0.5F;
/*    */     } 
/*    */     
/* 65 */     GlStateManager.scale(f, f, f);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\RenderVillager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */