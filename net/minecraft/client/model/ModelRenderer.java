/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.GLAllocation;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderGlobal;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.entity.model.anim.ModelUpdater;
/*     */ import net.optifine.model.ModelSprite;
/*     */ import net.optifine.shaders.Shaders;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModelRenderer
/*     */ {
/*     */   public float textureWidth;
/*     */   public float textureHeight;
/*     */   private int textureOffsetX;
/*     */   private int textureOffsetY;
/*     */   public float rotationPointX;
/*     */   public float rotationPointY;
/*     */   public float rotationPointZ;
/*     */   public float rotateAngleX;
/*     */   public float rotateAngleY;
/*     */   public float rotateAngleZ;
/*     */   private boolean compiled;
/*     */   private int displayList;
/*     */   public boolean mirror;
/*     */   public boolean showModel;
/*     */   public boolean isHidden;
/*     */   public List<ModelBox> cubeList;
/*     */   public List<ModelRenderer> childModels;
/*     */   public final String boxName;
/*     */   private ModelBase baseModel;
/*     */   public float offsetX;
/*     */   public float offsetY;
/*     */   public float offsetZ;
/*     */   public List spriteList;
/*     */   public boolean mirrorV;
/*     */   public float scaleX;
/*     */   public float scaleY;
/*     */   public float scaleZ;
/*     */   private int countResetDisplayList;
/*     */   private ResourceLocation textureLocation;
/*     */   private String id;
/*     */   private ModelUpdater modelUpdater;
/*     */   private RenderGlobal renderGlobal;
/*     */   
/*     */   public ModelRenderer(ModelBase model, String boxNameIn) {
/*  78 */     this.spriteList = new ArrayList();
/*  79 */     this.mirrorV = false;
/*  80 */     this.scaleX = 1.0F;
/*  81 */     this.scaleY = 1.0F;
/*  82 */     this.scaleZ = 1.0F;
/*  83 */     this.textureLocation = null;
/*  84 */     this.id = null;
/*  85 */     this.renderGlobal = Config.getRenderGlobal();
/*  86 */     this.textureWidth = 64.0F;
/*  87 */     this.textureHeight = 32.0F;
/*  88 */     this.showModel = true;
/*  89 */     this.cubeList = Lists.newArrayList();
/*  90 */     this.baseModel = model;
/*  91 */     model.boxList.add(this);
/*  92 */     this.boxName = boxNameIn;
/*  93 */     setTextureSize(model.textureWidth, model.textureHeight);
/*     */   }
/*     */   
/*     */   public ModelRenderer(ModelBase model) {
/*  97 */     this(model, null);
/*     */   }
/*     */   
/*     */   public ModelRenderer(ModelBase model, int texOffX, int texOffY) {
/* 101 */     this(model);
/* 102 */     setTextureOffset(texOffX, texOffY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addChild(ModelRenderer renderer) {
/* 109 */     if (this.childModels == null) {
/* 110 */       this.childModels = Lists.newArrayList();
/*     */     }
/*     */     
/* 113 */     this.childModels.add(renderer);
/*     */   }
/*     */   
/*     */   public ModelRenderer setTextureOffset(int x, int y) {
/* 117 */     this.textureOffsetX = x;
/* 118 */     this.textureOffsetY = y;
/* 119 */     return this;
/*     */   }
/*     */   
/*     */   public ModelRenderer addBox(String partName, float offX, float offY, float offZ, int width, int height, int depth) {
/* 123 */     partName = String.valueOf(this.boxName) + "." + partName;
/* 124 */     TextureOffset textureoffset = this.baseModel.getTextureOffset(partName);
/* 125 */     setTextureOffset(textureoffset.textureOffsetX, textureoffset.textureOffsetY);
/* 126 */     this.cubeList.add((new ModelBox(this, this.textureOffsetX, this.textureOffsetY, offX, offY, offZ, width, height, depth, 0.0F)).setBoxName(partName));
/* 127 */     return this;
/*     */   }
/*     */   
/*     */   public ModelRenderer addBox(float offX, float offY, float offZ, int width, int height, int depth) {
/* 131 */     this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, offX, offY, offZ, width, height, depth, 0.0F));
/* 132 */     return this;
/*     */   }
/*     */   
/*     */   public ModelRenderer addBox(float p_178769_1_, float p_178769_2_, float p_178769_3_, int p_178769_4_, int p_178769_5_, int p_178769_6_, boolean p_178769_7_) {
/* 136 */     this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, p_178769_1_, p_178769_2_, p_178769_3_, p_178769_4_, p_178769_5_, p_178769_6_, 0.0F, p_178769_7_));
/* 137 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBox(float p_78790_1_, float p_78790_2_, float p_78790_3_, int width, int height, int depth, float scaleFactor) {
/* 144 */     this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, p_78790_1_, p_78790_2_, p_78790_3_, width, height, depth, scaleFactor));
/*     */   }
/*     */   
/*     */   public void setRotationPoint(float rotationPointXIn, float rotationPointYIn, float rotationPointZIn) {
/* 148 */     this.rotationPointX = rotationPointXIn;
/* 149 */     this.rotationPointY = rotationPointYIn;
/* 150 */     this.rotationPointZ = rotationPointZIn;
/*     */   }
/*     */   
/*     */   public void render(float p_78785_1_) {
/* 154 */     if (!this.isHidden && this.showModel) {
/* 155 */       checkResetDisplayList();
/*     */       
/* 157 */       if (!this.compiled) {
/* 158 */         compileDisplayList(p_78785_1_);
/*     */       }
/*     */       
/* 161 */       int i = 0;
/*     */       
/* 163 */       if (this.textureLocation != null && !this.renderGlobal.renderOverlayDamaged) {
/* 164 */         if (this.renderGlobal.renderOverlayEyes) {
/*     */           return;
/*     */         }
/*     */         
/* 168 */         i = GlStateManager.getBoundTexture();
/* 169 */         Config.getTextureManager().bindTexture(this.textureLocation);
/*     */       } 
/*     */       
/* 172 */       if (this.modelUpdater != null) {
/* 173 */         this.modelUpdater.update();
/*     */       }
/*     */       
/* 176 */       boolean flag = !(this.scaleX == 1.0F && this.scaleY == 1.0F && this.scaleZ == 1.0F);
/* 177 */       GlStateManager.translate(this.offsetX, this.offsetY, this.offsetZ);
/*     */       
/* 179 */       if (this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F) {
/* 180 */         if (this.rotationPointX == 0.0F && this.rotationPointY == 0.0F && this.rotationPointZ == 0.0F) {
/* 181 */           if (flag) {
/* 182 */             GlStateManager.scale(this.scaleX, this.scaleY, this.scaleZ);
/*     */           }
/*     */           
/* 185 */           GlStateManager.callList(this.displayList);
/*     */           
/* 187 */           if (this.childModels != null) {
/* 188 */             for (int l = 0; l < this.childModels.size(); l++) {
/* 189 */               ((ModelRenderer)this.childModels.get(l)).render(p_78785_1_);
/*     */             }
/*     */           }
/*     */           
/* 193 */           if (flag) {
/* 194 */             GlStateManager.scale(1.0F / this.scaleX, 1.0F / this.scaleY, 1.0F / this.scaleZ);
/*     */           }
/*     */         } else {
/* 197 */           GlStateManager.translate(this.rotationPointX * p_78785_1_, this.rotationPointY * p_78785_1_, this.rotationPointZ * p_78785_1_);
/*     */           
/* 199 */           if (flag) {
/* 200 */             GlStateManager.scale(this.scaleX, this.scaleY, this.scaleZ);
/*     */           }
/*     */           
/* 203 */           GlStateManager.callList(this.displayList);
/*     */           
/* 205 */           if (this.childModels != null) {
/* 206 */             for (int k = 0; k < this.childModels.size(); k++) {
/* 207 */               ((ModelRenderer)this.childModels.get(k)).render(p_78785_1_);
/*     */             }
/*     */           }
/*     */           
/* 211 */           if (flag) {
/* 212 */             GlStateManager.scale(1.0F / this.scaleX, 1.0F / this.scaleY, 1.0F / this.scaleZ);
/*     */           }
/*     */           
/* 215 */           GlStateManager.translate(-this.rotationPointX * p_78785_1_, -this.rotationPointY * p_78785_1_, -this.rotationPointZ * p_78785_1_);
/*     */         } 
/*     */       } else {
/* 218 */         GlStateManager.pushMatrix();
/* 219 */         GlStateManager.translate(this.rotationPointX * p_78785_1_, this.rotationPointY * p_78785_1_, this.rotationPointZ * p_78785_1_);
/*     */         
/* 221 */         if (this.rotateAngleZ != 0.0F) {
/* 222 */           GlStateManager.rotate(this.rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
/*     */         }
/*     */         
/* 225 */         if (this.rotateAngleY != 0.0F) {
/* 226 */           GlStateManager.rotate(this.rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
/*     */         }
/*     */         
/* 229 */         if (this.rotateAngleX != 0.0F) {
/* 230 */           GlStateManager.rotate(this.rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
/*     */         }
/*     */         
/* 233 */         if (flag) {
/* 234 */           GlStateManager.scale(this.scaleX, this.scaleY, this.scaleZ);
/*     */         }
/*     */         
/* 237 */         GlStateManager.callList(this.displayList);
/*     */         
/* 239 */         if (this.childModels != null) {
/* 240 */           for (int j = 0; j < this.childModels.size(); j++) {
/* 241 */             ((ModelRenderer)this.childModels.get(j)).render(p_78785_1_);
/*     */           }
/*     */         }
/*     */         
/* 245 */         GlStateManager.popMatrix();
/*     */       } 
/*     */       
/* 248 */       GlStateManager.translate(-this.offsetX, -this.offsetY, -this.offsetZ);
/*     */       
/* 250 */       if (i != 0) {
/* 251 */         GlStateManager.bindTexture(i);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void renderWithRotation(float p_78791_1_) {
/* 257 */     if (!this.isHidden && this.showModel) {
/* 258 */       checkResetDisplayList();
/*     */       
/* 260 */       if (!this.compiled) {
/* 261 */         compileDisplayList(p_78791_1_);
/*     */       }
/*     */       
/* 264 */       int i = 0;
/*     */       
/* 266 */       if (this.textureLocation != null && !this.renderGlobal.renderOverlayDamaged) {
/* 267 */         if (this.renderGlobal.renderOverlayEyes) {
/*     */           return;
/*     */         }
/*     */         
/* 271 */         i = GlStateManager.getBoundTexture();
/* 272 */         Config.getTextureManager().bindTexture(this.textureLocation);
/*     */       } 
/*     */       
/* 275 */       if (this.modelUpdater != null) {
/* 276 */         this.modelUpdater.update();
/*     */       }
/*     */       
/* 279 */       boolean flag = !(this.scaleX == 1.0F && this.scaleY == 1.0F && this.scaleZ == 1.0F);
/* 280 */       GlStateManager.pushMatrix();
/* 281 */       GlStateManager.translate(this.rotationPointX * p_78791_1_, this.rotationPointY * p_78791_1_, this.rotationPointZ * p_78791_1_);
/*     */       
/* 283 */       if (this.rotateAngleY != 0.0F) {
/* 284 */         GlStateManager.rotate(this.rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
/*     */       }
/*     */       
/* 287 */       if (this.rotateAngleX != 0.0F) {
/* 288 */         GlStateManager.rotate(this.rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
/*     */       }
/*     */       
/* 291 */       if (this.rotateAngleZ != 0.0F) {
/* 292 */         GlStateManager.rotate(this.rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
/*     */       }
/*     */       
/* 295 */       if (flag) {
/* 296 */         GlStateManager.scale(this.scaleX, this.scaleY, this.scaleZ);
/*     */       }
/*     */       
/* 299 */       GlStateManager.callList(this.displayList);
/*     */       
/* 301 */       if (this.childModels != null) {
/* 302 */         for (int j = 0; j < this.childModels.size(); j++) {
/* 303 */           ((ModelRenderer)this.childModels.get(j)).render(p_78791_1_);
/*     */         }
/*     */       }
/*     */       
/* 307 */       GlStateManager.popMatrix();
/*     */       
/* 309 */       if (i != 0) {
/* 310 */         GlStateManager.bindTexture(i);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postRender(float scale) {
/* 319 */     if (!this.isHidden && this.showModel) {
/* 320 */       checkResetDisplayList();
/*     */       
/* 322 */       if (!this.compiled) {
/* 323 */         compileDisplayList(scale);
/*     */       }
/*     */       
/* 326 */       if (this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F) {
/* 327 */         if (this.rotationPointX != 0.0F || this.rotationPointY != 0.0F || this.rotationPointZ != 0.0F) {
/* 328 */           GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
/*     */         }
/*     */       } else {
/* 331 */         GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
/*     */         
/* 333 */         if (this.rotateAngleZ != 0.0F) {
/* 334 */           GlStateManager.rotate(this.rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
/*     */         }
/*     */         
/* 337 */         if (this.rotateAngleY != 0.0F) {
/* 338 */           GlStateManager.rotate(this.rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
/*     */         }
/*     */         
/* 341 */         if (this.rotateAngleX != 0.0F) {
/* 342 */           GlStateManager.rotate(this.rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void compileDisplayList(float scale) {
/* 352 */     if (this.displayList == 0) {
/* 353 */       this.displayList = GLAllocation.generateDisplayLists(1);
/*     */     }
/*     */     
/* 356 */     GL11.glNewList(this.displayList, 4864);
/* 357 */     WorldRenderer worldrenderer = Tessellator.getInstance().getWorldRenderer();
/*     */     
/* 359 */     for (int i = 0; i < this.cubeList.size(); i++) {
/* 360 */       ((ModelBox)this.cubeList.get(i)).render(worldrenderer, scale);
/*     */     }
/*     */     
/* 363 */     for (int j = 0; j < this.spriteList.size(); j++) {
/* 364 */       ModelSprite modelsprite = this.spriteList.get(j);
/* 365 */       modelsprite.render(Tessellator.getInstance(), scale);
/*     */     } 
/*     */     
/* 368 */     GL11.glEndList();
/* 369 */     this.compiled = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelRenderer setTextureSize(int textureWidthIn, int textureHeightIn) {
/* 376 */     this.textureWidth = textureWidthIn;
/* 377 */     this.textureHeight = textureHeightIn;
/* 378 */     return this;
/*     */   }
/*     */   
/*     */   public void addSprite(float p_addSprite_1_, float p_addSprite_2_, float p_addSprite_3_, int p_addSprite_4_, int p_addSprite_5_, int p_addSprite_6_, float p_addSprite_7_) {
/* 382 */     this.spriteList.add(new ModelSprite(this, this.textureOffsetX, this.textureOffsetY, p_addSprite_1_, p_addSprite_2_, p_addSprite_3_, p_addSprite_4_, p_addSprite_5_, p_addSprite_6_, p_addSprite_7_));
/*     */   }
/*     */   
/*     */   public boolean getCompiled() {
/* 386 */     return this.compiled;
/*     */   }
/*     */   
/*     */   public int getDisplayList() {
/* 390 */     return this.displayList;
/*     */   }
/*     */   
/*     */   private void checkResetDisplayList() {
/* 394 */     if (this.countResetDisplayList != Shaders.countResetDisplayLists) {
/* 395 */       this.compiled = false;
/* 396 */       this.countResetDisplayList = Shaders.countResetDisplayLists;
/*     */     } 
/*     */   }
/*     */   
/*     */   public ResourceLocation getTextureLocation() {
/* 401 */     return this.textureLocation;
/*     */   }
/*     */   
/*     */   public void setTextureLocation(ResourceLocation p_setTextureLocation_1_) {
/* 405 */     this.textureLocation = p_setTextureLocation_1_;
/*     */   }
/*     */   
/*     */   public String getId() {
/* 409 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String p_setId_1_) {
/* 413 */     this.id = p_setId_1_;
/*     */   }
/*     */   
/*     */   public void addBox(int[][] p_addBox_1_, float p_addBox_2_, float p_addBox_3_, float p_addBox_4_, float p_addBox_5_, float p_addBox_6_, float p_addBox_7_, float p_addBox_8_) {
/* 417 */     this.cubeList.add(new ModelBox(this, p_addBox_1_, p_addBox_2_, p_addBox_3_, p_addBox_4_, p_addBox_5_, p_addBox_6_, p_addBox_7_, p_addBox_8_, this.mirror));
/*     */   }
/*     */   
/*     */   public ModelRenderer getChild(String p_getChild_1_) {
/* 421 */     if (p_getChild_1_ == null) {
/* 422 */       return null;
/*     */     }
/* 424 */     if (this.childModels != null) {
/* 425 */       for (int i = 0; i < this.childModels.size(); i++) {
/* 426 */         ModelRenderer modelrenderer = this.childModels.get(i);
/*     */         
/* 428 */         if (p_getChild_1_.equals(modelrenderer.getId())) {
/* 429 */           return modelrenderer;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 434 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelRenderer getChildDeep(String p_getChildDeep_1_) {
/* 439 */     if (p_getChildDeep_1_ == null) {
/* 440 */       return null;
/*     */     }
/* 442 */     ModelRenderer modelrenderer = getChild(p_getChildDeep_1_);
/*     */     
/* 444 */     if (modelrenderer != null) {
/* 445 */       return modelrenderer;
/*     */     }
/* 447 */     if (this.childModels != null) {
/* 448 */       for (int i = 0; i < this.childModels.size(); i++) {
/* 449 */         ModelRenderer modelrenderer1 = this.childModels.get(i);
/* 450 */         ModelRenderer modelrenderer2 = modelrenderer1.getChildDeep(p_getChildDeep_1_);
/*     */         
/* 452 */         if (modelrenderer2 != null) {
/* 453 */           return modelrenderer2;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 458 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setModelUpdater(ModelUpdater p_setModelUpdater_1_) {
/* 464 */     this.modelUpdater = p_setModelUpdater_1_;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 468 */     StringBuffer stringbuffer = new StringBuffer();
/* 469 */     stringbuffer.append("id: " + this.id + ", boxes: " + ((this.cubeList != null) ? (String)Integer.valueOf(this.cubeList.size()) : null) + ", submodels: " + ((this.childModels != null) ? (String)Integer.valueOf(this.childModels.size()) : null));
/* 470 */     return stringbuffer.toString();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\model\ModelRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */