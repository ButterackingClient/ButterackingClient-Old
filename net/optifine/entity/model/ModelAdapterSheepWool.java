/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelSheep1;
/*    */ import net.minecraft.client.model.ModelSheep2;
/*    */ import net.minecraft.client.renderer.entity.Render;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderSheep;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerRenderer;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerSheepWool;
/*    */ import net.minecraft.entity.passive.EntitySheep;
/*    */ import net.minecraft.src.Config;
/*    */ 
/*    */ public class ModelAdapterSheepWool
/*    */   extends ModelAdapterQuadruped {
/*    */   public ModelAdapterSheepWool() {
/* 20 */     super(EntitySheep.class, "sheep_wool", 0.7F);
/*    */   }
/*    */   
/*    */   public ModelBase makeModel() {
/* 24 */     return (ModelBase)new ModelSheep1();
/*    */   }
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/*    */     RenderSheep renderSheep1;
/* 28 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 29 */     Render render = (Render)rendermanager.getEntityRenderMap().get(EntitySheep.class);
/*    */     
/* 31 */     if (!(render instanceof RenderSheep)) {
/* 32 */       Config.warn("Not a RenderSheep: " + render);
/* 33 */       return null;
/*    */     } 
/* 35 */     if (render.getEntityClass() == null) {
/* 36 */       renderSheep1 = new RenderSheep(rendermanager, (ModelBase)new ModelSheep2(), 0.7F);
/*    */     }
/*    */     
/* 39 */     RenderSheep rendersheep = renderSheep1;
/* 40 */     List<LayerRenderer<EntitySheep>> list = rendersheep.getLayerRenderers();
/* 41 */     Iterator<LayerRenderer<EntitySheep>> iterator = list.iterator();
/*    */     
/* 43 */     while (iterator.hasNext()) {
/* 44 */       LayerRenderer layerrenderer = iterator.next();
/*    */       
/* 46 */       if (layerrenderer instanceof LayerSheepWool) {
/* 47 */         iterator.remove();
/*    */       }
/*    */     } 
/*    */     
/* 51 */     LayerSheepWool layersheepwool = new LayerSheepWool(rendersheep);
/* 52 */     layersheepwool.sheepModel = (ModelSheep1)modelBase;
/* 53 */     rendersheep.addLayer((LayerRenderer)layersheepwool);
/* 54 */     return (IEntityRenderer)rendersheep;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\ModelAdapterSheepWool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */