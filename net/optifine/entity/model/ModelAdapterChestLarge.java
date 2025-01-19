/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelChest;
/*    */ import net.minecraft.client.model.ModelLargeChest;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityChestRenderer;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.tileentity.TileEntityChest;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ public class ModelAdapterChestLarge extends ModelAdapter {
/*    */   public ModelAdapterChestLarge() {
/* 16 */     super(TileEntityChest.class, "chest_large", 0.0F);
/*    */   }
/*    */   
/*    */   public ModelBase makeModel() {
/* 20 */     return (ModelBase)new ModelLargeChest();
/*    */   }
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 24 */     if (!(model instanceof ModelChest)) {
/* 25 */       return null;
/*    */     }
/* 27 */     ModelChest modelchest = (ModelChest)model;
/* 28 */     return modelPart.equals("lid") ? modelchest.chestLid : (modelPart.equals("base") ? modelchest.chestBelow : (modelPart.equals("knob") ? modelchest.chestKnob : null));
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 33 */     return new String[] { "lid", "base", "knob" };
/*    */   }
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/*    */     TileEntityChestRenderer tileEntityChestRenderer;
/* 37 */     TileEntityRendererDispatcher tileentityrendererdispatcher = TileEntityRendererDispatcher.instance;
/* 38 */     TileEntitySpecialRenderer tileentityspecialrenderer = tileentityrendererdispatcher.getSpecialRendererByClass(TileEntityChest.class);
/*    */     
/* 40 */     if (!(tileentityspecialrenderer instanceof TileEntityChestRenderer)) {
/* 41 */       return null;
/*    */     }
/* 43 */     if (tileentityspecialrenderer.getEntityClass() == null) {
/* 44 */       tileEntityChestRenderer = new TileEntityChestRenderer();
/* 45 */       tileEntityChestRenderer.setRendererDispatcher(tileentityrendererdispatcher);
/*    */     } 
/*    */     
/* 48 */     if (!Reflector.TileEntityChestRenderer_largeChest.exists()) {
/* 49 */       Config.warn("Field not found: TileEntityChestRenderer.largeChest");
/* 50 */       return null;
/*    */     } 
/* 52 */     Reflector.setFieldValue(tileEntityChestRenderer, Reflector.TileEntityChestRenderer_largeChest, modelBase);
/* 53 */     return (IEntityRenderer)tileEntityChestRenderer;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\ModelAdapterChestLarge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */