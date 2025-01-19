/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelGuardian;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderGuardian;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.monster.EntityGuardian;
/*    */ import net.minecraft.src.Config;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ public class ModelAdapterGuardian extends ModelAdapter {
/*    */   public ModelAdapterGuardian() {
/* 15 */     super(EntityGuardian.class, "guardian", 0.5F);
/*    */   }
/*    */   
/*    */   public ModelBase makeModel() {
/* 19 */     return (ModelBase)new ModelGuardian();
/*    */   }
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 23 */     if (!(model instanceof ModelGuardian)) {
/* 24 */       return null;
/*    */     }
/* 26 */     ModelGuardian modelguardian = (ModelGuardian)model;
/*    */     
/* 28 */     if (modelPart.equals("body"))
/* 29 */       return (ModelRenderer)Reflector.getFieldValue(modelguardian, Reflector.ModelGuardian_body); 
/* 30 */     if (modelPart.equals("eye")) {
/* 31 */       return (ModelRenderer)Reflector.getFieldValue(modelguardian, Reflector.ModelGuardian_eye);
/*    */     }
/* 33 */     String s = "spine";
/*    */     
/* 35 */     if (modelPart.startsWith(s)) {
/* 36 */       ModelRenderer[] amodelrenderer1 = (ModelRenderer[])Reflector.getFieldValue(modelguardian, Reflector.ModelGuardian_spines);
/*    */       
/* 38 */       if (amodelrenderer1 == null) {
/* 39 */         return null;
/*    */       }
/* 41 */       String s3 = modelPart.substring(s.length());
/* 42 */       int j = Config.parseInt(s3, -1);
/* 43 */       j--; return (
/* 44 */         j >= 0 && j < amodelrenderer1.length) ? amodelrenderer1[j] : null;
/*    */     } 
/*    */     
/* 47 */     String s1 = "tail";
/*    */     
/* 49 */     if (modelPart.startsWith(s1)) {
/* 50 */       ModelRenderer[] amodelrenderer = (ModelRenderer[])Reflector.getFieldValue(modelguardian, Reflector.ModelGuardian_tail);
/*    */       
/* 52 */       if (amodelrenderer == null) {
/* 53 */         return null;
/*    */       }
/* 55 */       String s2 = modelPart.substring(s1.length());
/* 56 */       int i = Config.parseInt(s2, -1);
/* 57 */       i--; return (
/* 58 */         i >= 0 && i < amodelrenderer.length) ? amodelrenderer[i] : null;
/*    */     } 
/*    */     
/* 61 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 69 */     return new String[] { "body", "eye", "spine1", "spine2", "spine3", "spine4", "spine5", "spine6", "spine7", "spine8", "spine9", "spine10", "spine11", "spine12", "tail1", "tail2", "tail3" };
/*    */   }
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 73 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 74 */     RenderGuardian renderguardian = new RenderGuardian(rendermanager);
/* 75 */     renderguardian.mainModel = modelBase;
/* 76 */     renderguardian.shadowSize = shadowSize;
/* 77 */     return (IEntityRenderer)renderguardian;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\ModelAdapterGuardian.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */