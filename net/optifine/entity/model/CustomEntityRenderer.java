/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class CustomEntityRenderer {
/*  6 */   private String name = null;
/*  7 */   private String basePath = null;
/*  8 */   private ResourceLocation textureLocation = null;
/*  9 */   private CustomModelRenderer[] customModelRenderers = null;
/* 10 */   private float shadowSize = 0.0F;
/*    */   
/*    */   public CustomEntityRenderer(String name, String basePath, ResourceLocation textureLocation, CustomModelRenderer[] customModelRenderers, float shadowSize) {
/* 13 */     this.name = name;
/* 14 */     this.basePath = basePath;
/* 15 */     this.textureLocation = textureLocation;
/* 16 */     this.customModelRenderers = customModelRenderers;
/* 17 */     this.shadowSize = shadowSize;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 21 */     return this.name;
/*    */   }
/*    */   
/*    */   public String getBasePath() {
/* 25 */     return this.basePath;
/*    */   }
/*    */   
/*    */   public ResourceLocation getTextureLocation() {
/* 29 */     return this.textureLocation;
/*    */   }
/*    */   
/*    */   public CustomModelRenderer[] getCustomModelRenderers() {
/* 33 */     return this.customModelRenderers;
/*    */   }
/*    */   
/*    */   public float getShadowSize() {
/* 37 */     return this.shadowSize;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\CustomEntityRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */