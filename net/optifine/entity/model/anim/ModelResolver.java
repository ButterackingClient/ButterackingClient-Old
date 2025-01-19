/*     */ package net.optifine.entity.model.anim;
/*     */ 
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.optifine.entity.model.CustomModelRenderer;
/*     */ import net.optifine.entity.model.ModelAdapter;
/*     */ import net.optifine.expr.IExpression;
/*     */ 
/*     */ public class ModelResolver implements IModelResolver {
/*     */   private ModelAdapter modelAdapter;
/*     */   private ModelBase model;
/*     */   private CustomModelRenderer[] customModelRenderers;
/*     */   private ModelRenderer thisModelRenderer;
/*     */   private ModelRenderer partModelRenderer;
/*     */   private IRenderResolver renderResolver;
/*     */   
/*     */   public ModelResolver(ModelAdapter modelAdapter, ModelBase model, CustomModelRenderer[] customModelRenderers) {
/*  20 */     this.modelAdapter = modelAdapter;
/*  21 */     this.model = model;
/*  22 */     this.customModelRenderers = customModelRenderers;
/*  23 */     Class<?> oclass = modelAdapter.getEntityClass();
/*     */     
/*  25 */     if (TileEntity.class.isAssignableFrom(oclass)) {
/*  26 */       this.renderResolver = new RenderResolverTileEntity();
/*     */     } else {
/*  28 */       this.renderResolver = new RenderResolverEntity();
/*     */     } 
/*     */   }
/*     */   
/*     */   public IExpression getExpression(String name) {
/*  33 */     ModelVariableFloat modelVariableFloat = getModelVariable(name);
/*     */     
/*  35 */     if (modelVariableFloat != null) {
/*  36 */       return (IExpression)modelVariableFloat;
/*     */     }
/*  38 */     IExpression iexpression1 = this.renderResolver.getParameter(name);
/*  39 */     return (iexpression1 != null) ? iexpression1 : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelRenderer getModelRenderer(String name) {
/*  44 */     if (name == null)
/*  45 */       return null; 
/*  46 */     if (name.indexOf(":") >= 0) {
/*  47 */       String[] astring = Config.tokenize(name, ":");
/*  48 */       ModelRenderer modelrenderer3 = getModelRenderer(astring[0]);
/*     */       
/*  50 */       for (int j = 1; j < astring.length; j++) {
/*  51 */         String s = astring[j];
/*  52 */         ModelRenderer modelrenderer4 = modelrenderer3.getChildDeep(s);
/*     */         
/*  54 */         if (modelrenderer4 == null) {
/*  55 */           return null;
/*     */         }
/*     */         
/*  58 */         modelrenderer3 = modelrenderer4;
/*     */       } 
/*     */       
/*  61 */       return modelrenderer3;
/*  62 */     }  if (this.thisModelRenderer != null && name.equals("this"))
/*  63 */       return this.thisModelRenderer; 
/*  64 */     if (this.partModelRenderer != null && name.equals("part")) {
/*  65 */       return this.partModelRenderer;
/*     */     }
/*  67 */     ModelRenderer modelrenderer = this.modelAdapter.getModelRenderer(this.model, name);
/*     */     
/*  69 */     if (modelrenderer != null) {
/*  70 */       return modelrenderer;
/*     */     }
/*  72 */     for (int i = 0; i < this.customModelRenderers.length; i++) {
/*  73 */       CustomModelRenderer custommodelrenderer = this.customModelRenderers[i];
/*  74 */       ModelRenderer modelrenderer1 = custommodelrenderer.getModelRenderer();
/*     */       
/*  76 */       if (name.equals(modelrenderer1.getId())) {
/*  77 */         return modelrenderer1;
/*     */       }
/*     */       
/*  80 */       ModelRenderer modelrenderer2 = modelrenderer1.getChildDeep(name);
/*     */       
/*  82 */       if (modelrenderer2 != null) {
/*  83 */         return modelrenderer2;
/*     */       }
/*     */     } 
/*     */     
/*  87 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelVariableFloat getModelVariable(String name) {
/*  93 */     String[] astring = Config.tokenize(name, ".");
/*     */     
/*  95 */     if (astring.length != 2) {
/*  96 */       return null;
/*     */     }
/*  98 */     String s = astring[0];
/*  99 */     String s1 = astring[1];
/* 100 */     ModelRenderer modelrenderer = getModelRenderer(s);
/*     */     
/* 102 */     if (modelrenderer == null) {
/* 103 */       return null;
/*     */     }
/* 105 */     ModelVariableType modelvariabletype = ModelVariableType.parse(s1);
/* 106 */     return (modelvariabletype == null) ? null : new ModelVariableFloat(name, modelrenderer, modelvariabletype);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPartModelRenderer(ModelRenderer partModelRenderer) {
/* 112 */     this.partModelRenderer = partModelRenderer;
/*     */   }
/*     */   
/*     */   public void setThisModelRenderer(ModelRenderer thisModelRenderer) {
/* 116 */     this.thisModelRenderer = thisModelRenderer;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\anim\ModelResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */