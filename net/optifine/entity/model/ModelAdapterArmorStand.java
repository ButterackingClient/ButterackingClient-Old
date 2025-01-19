/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelArmorStand;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.entity.ArmorStandRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.item.EntityArmorStand;
/*    */ import net.minecraft.src.Config;
/*    */ 
/*    */ public class ModelAdapterArmorStand extends ModelAdapterBiped {
/*    */   public ModelAdapterArmorStand() {
/* 14 */     super(EntityArmorStand.class, "armor_stand", 0.0F);
/*    */   }
/*    */   
/*    */   public ModelBase makeModel() {
/* 18 */     return (ModelBase)new ModelArmorStand();
/*    */   }
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 22 */     if (!(model instanceof ModelArmorStand)) {
/* 23 */       return null;
/*    */     }
/* 25 */     ModelArmorStand modelarmorstand = (ModelArmorStand)model;
/* 26 */     return modelPart.equals("right") ? modelarmorstand.standRightSide : (modelPart.equals("left") ? modelarmorstand.standLeftSide : (modelPart.equals("waist") ? modelarmorstand.standWaist : (modelPart.equals("base") ? modelarmorstand.standBase : super.getModelRenderer((ModelBase)modelarmorstand, modelPart))));
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 31 */     String[] astring = super.getModelRendererNames();
/* 32 */     astring = (String[])Config.addObjectsToArray((Object[])astring, (Object[])new String[] { "right", "left", "waist", "base" });
/* 33 */     return astring;
/*    */   }
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 37 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 38 */     ArmorStandRenderer armorstandrenderer = new ArmorStandRenderer(rendermanager);
/* 39 */     armorstandrenderer.mainModel = modelBase;
/* 40 */     armorstandrenderer.shadowSize = shadowSize;
/* 41 */     return (IEntityRenderer)armorstandrenderer;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\ModelAdapterArmorStand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */