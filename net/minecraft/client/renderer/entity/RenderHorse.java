/*    */ package net.minecraft.client.renderer.entity;
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelHorse;
/*    */ import net.minecraft.client.renderer.texture.ITextureObject;
/*    */ import net.minecraft.client.renderer.texture.LayeredTexture;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.passive.EntityHorse;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderHorse extends RenderLiving<EntityHorse> {
/* 15 */   private static final Map<String, ResourceLocation> field_110852_a = Maps.newHashMap();
/* 16 */   private static final ResourceLocation whiteHorseTextures = new ResourceLocation("textures/entity/horse/horse_white.png");
/* 17 */   private static final ResourceLocation muleTextures = new ResourceLocation("textures/entity/horse/mule.png");
/* 18 */   private static final ResourceLocation donkeyTextures = new ResourceLocation("textures/entity/horse/donkey.png");
/* 19 */   private static final ResourceLocation zombieHorseTextures = new ResourceLocation("textures/entity/horse/horse_zombie.png");
/* 20 */   private static final ResourceLocation skeletonHorseTextures = new ResourceLocation("textures/entity/horse/horse_skeleton.png");
/*    */   
/*    */   public RenderHorse(RenderManager rendermanagerIn, ModelHorse model, float shadowSizeIn) {
/* 23 */     super(rendermanagerIn, (ModelBase)model, shadowSizeIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void preRenderCallback(EntityHorse entitylivingbaseIn, float partialTickTime) {
/* 31 */     float f = 1.0F;
/* 32 */     int i = entitylivingbaseIn.getHorseType();
/*    */     
/* 34 */     if (i == 1) {
/* 35 */       f *= 0.87F;
/* 36 */     } else if (i == 2) {
/* 37 */       f *= 0.92F;
/*    */     } 
/*    */     
/* 40 */     GlStateManager.scale(f, f, f);
/* 41 */     super.preRenderCallback(entitylivingbaseIn, partialTickTime);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityHorse entity) {
/* 48 */     if (!entity.func_110239_cn()) {
/* 49 */       switch (entity.getHorseType()) {
/*    */         
/*    */         default:
/* 52 */           return whiteHorseTextures;
/*    */         
/*    */         case 1:
/* 55 */           return donkeyTextures;
/*    */         
/*    */         case 2:
/* 58 */           return muleTextures;
/*    */         
/*    */         case 3:
/* 61 */           return zombieHorseTextures;
/*    */         case 4:
/*    */           break;
/* 64 */       }  return skeletonHorseTextures;
/*    */     } 
/*    */     
/* 67 */     return func_110848_b(entity);
/*    */   }
/*    */ 
/*    */   
/*    */   private ResourceLocation func_110848_b(EntityHorse horse) {
/* 72 */     String s = horse.getHorseTexture();
/*    */     
/* 74 */     if (!horse.func_175507_cI()) {
/* 75 */       return null;
/*    */     }
/* 77 */     ResourceLocation resourcelocation = field_110852_a.get(s);
/*    */     
/* 79 */     if (resourcelocation == null) {
/* 80 */       resourcelocation = new ResourceLocation(s);
/* 81 */       Minecraft.getMinecraft().getTextureManager().loadTexture(resourcelocation, (ITextureObject)new LayeredTexture(horse.getVariantTexturePaths()));
/* 82 */       field_110852_a.put(s, resourcelocation);
/*    */     } 
/*    */     
/* 85 */     return resourcelocation;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\RenderHorse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */