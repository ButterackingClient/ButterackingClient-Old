/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.optifine.entity.model.anim.ModelUpdater;
/*    */ 
/*    */ public class CustomModelRenderer {
/*    */   private String modelPart;
/*    */   private boolean attach;
/*    */   private ModelRenderer modelRenderer;
/*    */   private ModelUpdater modelUpdater;
/*    */   
/*    */   public CustomModelRenderer(String modelPart, boolean attach, ModelRenderer modelRenderer, ModelUpdater modelUpdater) {
/* 13 */     this.modelPart = modelPart;
/* 14 */     this.attach = attach;
/* 15 */     this.modelRenderer = modelRenderer;
/* 16 */     this.modelUpdater = modelUpdater;
/*    */   }
/*    */   
/*    */   public ModelRenderer getModelRenderer() {
/* 20 */     return this.modelRenderer;
/*    */   }
/*    */   
/*    */   public String getModelPart() {
/* 24 */     return this.modelPart;
/*    */   }
/*    */   
/*    */   public boolean isAttach() {
/* 28 */     return this.attach;
/*    */   }
/*    */   
/*    */   public ModelUpdater getModelUpdater() {
/* 32 */     return this.modelUpdater;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\CustomModelRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */