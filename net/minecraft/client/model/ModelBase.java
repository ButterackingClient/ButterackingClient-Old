/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ 
/*    */ 
/*    */ public abstract class ModelBase
/*    */ {
/*    */   public float swingProgress;
/*    */   public boolean isRiding;
/*    */   public boolean isChild = true;
/* 17 */   public List<ModelRenderer> boxList = Lists.newArrayList();
/* 18 */   private Map<String, TextureOffset> modelTextureMap = Maps.newHashMap();
/* 19 */   public int textureWidth = 64;
/* 20 */   public int textureHeight = 32;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float p_78086_2_, float p_78086_3_, float partialTickTime) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ModelRenderer getRandomModelBox(Random rand) {
/* 44 */     return this.boxList.get(rand.nextInt(this.boxList.size()));
/*    */   }
/*    */   
/*    */   protected void setTextureOffset(String partName, int x, int y) {
/* 48 */     this.modelTextureMap.put(partName, new TextureOffset(x, y));
/*    */   }
/*    */   
/*    */   public TextureOffset getTextureOffset(String partName) {
/* 52 */     return this.modelTextureMap.get(partName);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void copyModelAngles(ModelRenderer source, ModelRenderer dest) {
/* 60 */     dest.rotateAngleX = source.rotateAngleX;
/* 61 */     dest.rotateAngleY = source.rotateAngleY;
/* 62 */     dest.rotateAngleZ = source.rotateAngleZ;
/* 63 */     dest.rotationPointX = source.rotationPointX;
/* 64 */     dest.rotationPointY = source.rotationPointY;
/* 65 */     dest.rotationPointZ = source.rotationPointZ;
/*    */   }
/*    */   
/*    */   public void setModelAttributes(ModelBase model) {
/* 69 */     this.swingProgress = model.swingProgress;
/* 70 */     this.isRiding = model.isRiding;
/* 71 */     this.isChild = model.isChild;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\model\ModelBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */