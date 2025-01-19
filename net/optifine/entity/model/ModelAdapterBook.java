/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelBook;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityEnchantmentTableRenderer;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.tileentity.TileEntityEnchantmentTable;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ public class ModelAdapterBook extends ModelAdapter {
/*    */   public ModelAdapterBook() {
/* 15 */     super(TileEntityEnchantmentTable.class, "book", 0.0F);
/*    */   }
/*    */   
/*    */   public ModelBase makeModel() {
/* 19 */     return (ModelBase)new ModelBook();
/*    */   }
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 23 */     if (!(model instanceof ModelBook)) {
/* 24 */       return null;
/*    */     }
/* 26 */     ModelBook modelbook = (ModelBook)model;
/* 27 */     return modelPart.equals("cover_right") ? modelbook.coverRight : (modelPart.equals("cover_left") ? modelbook.coverLeft : (modelPart.equals("pages_right") ? modelbook.pagesRight : (modelPart.equals("pages_left") ? modelbook.pagesLeft : (modelPart.equals("flipping_page_right") ? modelbook.flippingPageRight : (modelPart.equals("flipping_page_left") ? modelbook.flippingPageLeft : (modelPart.equals("book_spine") ? modelbook.bookSpine : null))))));
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 32 */     return new String[] { "cover_right", "cover_left", "pages_right", "pages_left", "flipping_page_right", "flipping_page_left", "book_spine" };
/*    */   }
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/*    */     TileEntityEnchantmentTableRenderer tileEntityEnchantmentTableRenderer;
/* 36 */     TileEntityRendererDispatcher tileentityrendererdispatcher = TileEntityRendererDispatcher.instance;
/* 37 */     TileEntitySpecialRenderer tileentityspecialrenderer = tileentityrendererdispatcher.getSpecialRendererByClass(TileEntityEnchantmentTable.class);
/*    */     
/* 39 */     if (!(tileentityspecialrenderer instanceof TileEntityEnchantmentTableRenderer)) {
/* 40 */       return null;
/*    */     }
/* 42 */     if (tileentityspecialrenderer.getEntityClass() == null) {
/* 43 */       tileEntityEnchantmentTableRenderer = new TileEntityEnchantmentTableRenderer();
/* 44 */       tileEntityEnchantmentTableRenderer.setRendererDispatcher(tileentityrendererdispatcher);
/*    */     } 
/*    */     
/* 47 */     if (!Reflector.TileEntityEnchantmentTableRenderer_modelBook.exists()) {
/* 48 */       Config.warn("Field not found: TileEntityEnchantmentTableRenderer.modelBook");
/* 49 */       return null;
/*    */     } 
/* 51 */     Reflector.setFieldValue(tileEntityEnchantmentTableRenderer, Reflector.TileEntityEnchantmentTableRenderer_modelBook, modelBase);
/* 52 */     return (IEntityRenderer)tileEntityEnchantmentTableRenderer;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\ModelAdapterBook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */