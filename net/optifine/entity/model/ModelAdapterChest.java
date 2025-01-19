/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelChest;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityChestRenderer;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.tileentity.TileEntityChest;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ public class ModelAdapterChest extends ModelAdapter {
/*    */   public ModelAdapterChest() {
/* 15 */     super(TileEntityChest.class, "chest", 0.0F);
/*    */   }
/*    */   
/*    */   public ModelBase makeModel() {
/* 19 */     return (ModelBase)new ModelChest();
/*    */   }
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 23 */     if (!(model instanceof ModelChest)) {
/* 24 */       return null;
/*    */     }
/* 26 */     ModelChest modelchest = (ModelChest)model;
/* 27 */     return modelPart.equals("lid") ? modelchest.chestLid : (modelPart.equals("base") ? modelchest.chestBelow : (modelPart.equals("knob") ? modelchest.chestKnob : null));
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 32 */     return new String[] { "lid", "base", "knob" };
/*    */   }
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/*    */     TileEntityChestRenderer tileEntityChestRenderer;
/* 36 */     TileEntityRendererDispatcher tileentityrendererdispatcher = TileEntityRendererDispatcher.instance;
/* 37 */     TileEntitySpecialRenderer tileentityspecialrenderer = tileentityrendererdispatcher.getSpecialRendererByClass(TileEntityChest.class);
/*    */     
/* 39 */     if (!(tileentityspecialrenderer instanceof TileEntityChestRenderer)) {
/* 40 */       return null;
/*    */     }
/* 42 */     if (tileentityspecialrenderer.getEntityClass() == null) {
/* 43 */       tileEntityChestRenderer = new TileEntityChestRenderer();
/* 44 */       tileEntityChestRenderer.setRendererDispatcher(tileentityrendererdispatcher);
/*    */     } 
/*    */     
/* 47 */     if (!Reflector.TileEntityChestRenderer_simpleChest.exists()) {
/* 48 */       Config.warn("Field not found: TileEntityChestRenderer.simpleChest");
/* 49 */       return null;
/*    */     } 
/* 51 */     Reflector.setFieldValue(tileEntityChestRenderer, Reflector.TileEntityChestRenderer_simpleChest, modelBase);
/* 52 */     return (IEntityRenderer)tileEntityChestRenderer;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\ModelAdapterChest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */