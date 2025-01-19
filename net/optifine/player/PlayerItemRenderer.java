/*    */ package net.optifine.player;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBiped;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ 
/*    */ public class PlayerItemRenderer {
/*  7 */   private int attachTo = 0;
/*  8 */   private ModelRenderer modelRenderer = null;
/*    */   
/*    */   public PlayerItemRenderer(int attachTo, ModelRenderer modelRenderer) {
/* 11 */     this.attachTo = attachTo;
/* 12 */     this.modelRenderer = modelRenderer;
/*    */   }
/*    */   
/*    */   public ModelRenderer getModelRenderer() {
/* 16 */     return this.modelRenderer;
/*    */   }
/*    */   
/*    */   public void render(ModelBiped modelBiped, float scale) {
/* 20 */     ModelRenderer modelrenderer = PlayerItemModel.getAttachModel(modelBiped, this.attachTo);
/*    */     
/* 22 */     if (modelrenderer != null) {
/* 23 */       modelrenderer.postRender(scale);
/*    */     }
/*    */     
/* 26 */     this.modelRenderer.render(scale);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\player\PlayerItemRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */