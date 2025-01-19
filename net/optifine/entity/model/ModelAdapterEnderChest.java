/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelChest;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityEnderChestRenderer;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.tileentity.TileEntityEnderChest;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ public class ModelAdapterEnderChest extends ModelAdapter {
/*    */   public ModelAdapterEnderChest() {
/* 15 */     super(TileEntityEnderChest.class, "ender_chest", 0.0F);
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
/*    */     TileEntityEnderChestRenderer tileEntityEnderChestRenderer;
/* 36 */     TileEntityRendererDispatcher tileentityrendererdispatcher = TileEntityRendererDispatcher.instance;
/* 37 */     TileEntitySpecialRenderer tileentityspecialrenderer = tileentityrendererdispatcher.getSpecialRendererByClass(TileEntityEnderChest.class);
/*    */     
/* 39 */     if (!(tileentityspecialrenderer instanceof TileEntityEnderChestRenderer)) {
/* 40 */       return null;
/*    */     }
/* 42 */     if (tileentityspecialrenderer.getEntityClass() == null) {
/* 43 */       tileEntityEnderChestRenderer = new TileEntityEnderChestRenderer();
/* 44 */       tileEntityEnderChestRenderer.setRendererDispatcher(tileentityrendererdispatcher);
/*    */     } 
/*    */     
/* 47 */     if (!Reflector.TileEntityEnderChestRenderer_modelChest.exists()) {
/* 48 */       Config.warn("Field not found: TileEntityEnderChestRenderer.modelChest");
/* 49 */       return null;
/*    */     } 
/* 51 */     Reflector.setFieldValue(tileEntityEnderChestRenderer, Reflector.TileEntityEnderChestRenderer_modelChest, modelBase);
/* 52 */     return (IEntityRenderer)tileEntityEnderChestRenderer;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\ModelAdapterEnderChest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */