/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ 
/*    */ public abstract class ModelAdapter
/*    */ {
/*    */   private Class entityClass;
/*    */   private String name;
/*    */   private float shadowSize;
/*    */   private String[] aliases;
/*    */   
/*    */   public ModelAdapter(Class entityClass, String name, float shadowSize) {
/* 16 */     this.entityClass = entityClass;
/* 17 */     this.name = name;
/* 18 */     this.shadowSize = shadowSize;
/*    */   }
/*    */   
/*    */   public ModelAdapter(Class entityClass, String name, float shadowSize, String[] aliases) {
/* 22 */     this.entityClass = entityClass;
/* 23 */     this.name = name;
/* 24 */     this.shadowSize = shadowSize;
/* 25 */     this.aliases = aliases;
/*    */   }
/*    */   
/*    */   public Class getEntityClass() {
/* 29 */     return this.entityClass;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 33 */     return this.name;
/*    */   }
/*    */   
/*    */   public String[] getAliases() {
/* 37 */     return this.aliases;
/*    */   }
/*    */   
/*    */   public float getShadowSize() {
/* 41 */     return this.shadowSize;
/*    */   }
/*    */   
/*    */   public abstract ModelBase makeModel();
/*    */   
/*    */   public abstract ModelRenderer getModelRenderer(ModelBase paramModelBase, String paramString);
/*    */   
/*    */   public abstract String[] getModelRendererNames();
/*    */   
/*    */   public abstract IEntityRenderer makeEntityRender(ModelBase paramModelBase, float paramFloat);
/*    */   
/*    */   public ModelRenderer[] getModelRenderers(ModelBase model) {
/* 53 */     String[] astring = getModelRendererNames();
/* 54 */     List<ModelRenderer> list = new ArrayList<>();
/*    */     
/* 56 */     for (int i = 0; i < astring.length; i++) {
/* 57 */       String s = astring[i];
/* 58 */       ModelRenderer modelrenderer = getModelRenderer(model, s);
/*    */       
/* 60 */       if (modelrenderer != null) {
/* 61 */         list.add(modelrenderer);
/*    */       }
/*    */     } 
/*    */     
/* 65 */     ModelRenderer[] amodelrenderer = list.<ModelRenderer>toArray(new ModelRenderer[list.size()]);
/* 66 */     return amodelrenderer;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\ModelAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */