/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import net.minecraft.client.renderer.WorldRenderer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModelBox
/*     */ {
/*     */   private PositionTextureVertex[] vertexPositions;
/*     */   private TexturedQuad[] quadList;
/*     */   public final float posX1;
/*     */   public final float posY1;
/*     */   public final float posZ1;
/*     */   public final float posX2;
/*     */   public final float posY2;
/*     */   public final float posZ2;
/*     */   public String boxName;
/*     */   
/*     */   public ModelBox(ModelRenderer renderer, int p_i46359_2_, int p_i46359_3_, float p_i46359_4_, float p_i46359_5_, float p_i46359_6_, int p_i46359_7_, int p_i46359_8_, int p_i46359_9_, float p_i46359_10_) {
/*  48 */     this(renderer, p_i46359_2_, p_i46359_3_, p_i46359_4_, p_i46359_5_, p_i46359_6_, p_i46359_7_, p_i46359_8_, p_i46359_9_, p_i46359_10_, renderer.mirror);
/*     */   }
/*     */   
/*     */   public ModelBox(ModelRenderer p_i0_1_, int[][] p_i0_2_, float p_i0_3_, float p_i0_4_, float p_i0_5_, float p_i0_6_, float p_i0_7_, float p_i0_8_, float p_i0_9_, boolean p_i0_10_) {
/*  52 */     this.posX1 = p_i0_3_;
/*  53 */     this.posY1 = p_i0_4_;
/*  54 */     this.posZ1 = p_i0_5_;
/*  55 */     this.posX2 = p_i0_3_ + p_i0_6_;
/*  56 */     this.posY2 = p_i0_4_ + p_i0_7_;
/*  57 */     this.posZ2 = p_i0_5_ + p_i0_8_;
/*  58 */     this.vertexPositions = new PositionTextureVertex[8];
/*  59 */     this.quadList = new TexturedQuad[6];
/*  60 */     float f = p_i0_3_ + p_i0_6_;
/*  61 */     float f1 = p_i0_4_ + p_i0_7_;
/*  62 */     float f2 = p_i0_5_ + p_i0_8_;
/*  63 */     p_i0_3_ -= p_i0_9_;
/*  64 */     p_i0_4_ -= p_i0_9_;
/*  65 */     p_i0_5_ -= p_i0_9_;
/*  66 */     f += p_i0_9_;
/*  67 */     f1 += p_i0_9_;
/*  68 */     f2 += p_i0_9_;
/*     */     
/*  70 */     if (p_i0_10_) {
/*  71 */       float f3 = f;
/*  72 */       f = p_i0_3_;
/*  73 */       p_i0_3_ = f3;
/*     */     } 
/*     */     
/*  76 */     PositionTextureVertex positiontexturevertex7 = new PositionTextureVertex(p_i0_3_, p_i0_4_, p_i0_5_, 0.0F, 0.0F);
/*  77 */     PositionTextureVertex positiontexturevertex = new PositionTextureVertex(f, p_i0_4_, p_i0_5_, 0.0F, 8.0F);
/*  78 */     PositionTextureVertex positiontexturevertex1 = new PositionTextureVertex(f, f1, p_i0_5_, 8.0F, 8.0F);
/*  79 */     PositionTextureVertex positiontexturevertex2 = new PositionTextureVertex(p_i0_3_, f1, p_i0_5_, 8.0F, 0.0F);
/*  80 */     PositionTextureVertex positiontexturevertex3 = new PositionTextureVertex(p_i0_3_, p_i0_4_, f2, 0.0F, 0.0F);
/*  81 */     PositionTextureVertex positiontexturevertex4 = new PositionTextureVertex(f, p_i0_4_, f2, 0.0F, 8.0F);
/*  82 */     PositionTextureVertex positiontexturevertex5 = new PositionTextureVertex(f, f1, f2, 8.0F, 8.0F);
/*  83 */     PositionTextureVertex positiontexturevertex6 = new PositionTextureVertex(p_i0_3_, f1, f2, 8.0F, 0.0F);
/*  84 */     this.vertexPositions[0] = positiontexturevertex7;
/*  85 */     this.vertexPositions[1] = positiontexturevertex;
/*  86 */     this.vertexPositions[2] = positiontexturevertex1;
/*  87 */     this.vertexPositions[3] = positiontexturevertex2;
/*  88 */     this.vertexPositions[4] = positiontexturevertex3;
/*  89 */     this.vertexPositions[5] = positiontexturevertex4;
/*  90 */     this.vertexPositions[6] = positiontexturevertex5;
/*  91 */     this.vertexPositions[7] = positiontexturevertex6;
/*  92 */     this.quadList[0] = makeTexturedQuad(new PositionTextureVertex[] { positiontexturevertex4, positiontexturevertex, positiontexturevertex1, positiontexturevertex5 }, p_i0_2_[4], false, p_i0_1_.textureWidth, p_i0_1_.textureHeight);
/*  93 */     this.quadList[1] = makeTexturedQuad(new PositionTextureVertex[] { positiontexturevertex7, positiontexturevertex3, positiontexturevertex6, positiontexturevertex2 }, p_i0_2_[5], false, p_i0_1_.textureWidth, p_i0_1_.textureHeight);
/*  94 */     this.quadList[2] = makeTexturedQuad(new PositionTextureVertex[] { positiontexturevertex4, positiontexturevertex3, positiontexturevertex7, positiontexturevertex }, p_i0_2_[1], true, p_i0_1_.textureWidth, p_i0_1_.textureHeight);
/*  95 */     this.quadList[3] = makeTexturedQuad(new PositionTextureVertex[] { positiontexturevertex1, positiontexturevertex2, positiontexturevertex6, positiontexturevertex5 }, p_i0_2_[0], true, p_i0_1_.textureWidth, p_i0_1_.textureHeight);
/*  96 */     this.quadList[4] = makeTexturedQuad(new PositionTextureVertex[] { positiontexturevertex, positiontexturevertex7, positiontexturevertex2, positiontexturevertex1 }, p_i0_2_[2], false, p_i0_1_.textureWidth, p_i0_1_.textureHeight);
/*  97 */     this.quadList[5] = makeTexturedQuad(new PositionTextureVertex[] { positiontexturevertex3, positiontexturevertex4, positiontexturevertex5, positiontexturevertex6 }, p_i0_2_[3], false, p_i0_1_.textureWidth, p_i0_1_.textureHeight);
/*     */     
/*  99 */     if (p_i0_10_) {
/* 100 */       byte b; int i; TexturedQuad[] arrayOfTexturedQuad; for (i = (arrayOfTexturedQuad = this.quadList).length, b = 0; b < i; ) { TexturedQuad texturedquad = arrayOfTexturedQuad[b];
/* 101 */         texturedquad.flipFace();
/*     */         b++; }
/*     */     
/*     */     } 
/*     */   }
/*     */   private TexturedQuad makeTexturedQuad(PositionTextureVertex[] p_makeTexturedQuad_1_, int[] p_makeTexturedQuad_2_, boolean p_makeTexturedQuad_3_, float p_makeTexturedQuad_4_, float p_makeTexturedQuad_5_) {
/* 107 */     return (p_makeTexturedQuad_2_ == null) ? null : (p_makeTexturedQuad_3_ ? new TexturedQuad(p_makeTexturedQuad_1_, p_makeTexturedQuad_2_[2], p_makeTexturedQuad_2_[3], p_makeTexturedQuad_2_[0], p_makeTexturedQuad_2_[1], p_makeTexturedQuad_4_, p_makeTexturedQuad_5_) : new TexturedQuad(p_makeTexturedQuad_1_, p_makeTexturedQuad_2_[0], p_makeTexturedQuad_2_[1], p_makeTexturedQuad_2_[2], p_makeTexturedQuad_2_[3], p_makeTexturedQuad_4_, p_makeTexturedQuad_5_));
/*     */   }
/*     */   
/*     */   public ModelBox(ModelRenderer renderer, int textureX, int textureY, float p_i46301_4_, float p_i46301_5_, float p_i46301_6_, int p_i46301_7_, int p_i46301_8_, int p_i46301_9_, float p_i46301_10_, boolean p_i46301_11_) {
/* 111 */     this.posX1 = p_i46301_4_;
/* 112 */     this.posY1 = p_i46301_5_;
/* 113 */     this.posZ1 = p_i46301_6_;
/* 114 */     this.posX2 = p_i46301_4_ + p_i46301_7_;
/* 115 */     this.posY2 = p_i46301_5_ + p_i46301_8_;
/* 116 */     this.posZ2 = p_i46301_6_ + p_i46301_9_;
/* 117 */     this.vertexPositions = new PositionTextureVertex[8];
/* 118 */     this.quadList = new TexturedQuad[6];
/* 119 */     float f = p_i46301_4_ + p_i46301_7_;
/* 120 */     float f1 = p_i46301_5_ + p_i46301_8_;
/* 121 */     float f2 = p_i46301_6_ + p_i46301_9_;
/* 122 */     p_i46301_4_ -= p_i46301_10_;
/* 123 */     p_i46301_5_ -= p_i46301_10_;
/* 124 */     p_i46301_6_ -= p_i46301_10_;
/* 125 */     f += p_i46301_10_;
/* 126 */     f1 += p_i46301_10_;
/* 127 */     f2 += p_i46301_10_;
/*     */     
/* 129 */     if (p_i46301_11_) {
/* 130 */       float f3 = f;
/* 131 */       f = p_i46301_4_;
/* 132 */       p_i46301_4_ = f3;
/*     */     } 
/*     */     
/* 135 */     PositionTextureVertex positiontexturevertex7 = new PositionTextureVertex(p_i46301_4_, p_i46301_5_, p_i46301_6_, 0.0F, 0.0F);
/* 136 */     PositionTextureVertex positiontexturevertex = new PositionTextureVertex(f, p_i46301_5_, p_i46301_6_, 0.0F, 8.0F);
/* 137 */     PositionTextureVertex positiontexturevertex1 = new PositionTextureVertex(f, f1, p_i46301_6_, 8.0F, 8.0F);
/* 138 */     PositionTextureVertex positiontexturevertex2 = new PositionTextureVertex(p_i46301_4_, f1, p_i46301_6_, 8.0F, 0.0F);
/* 139 */     PositionTextureVertex positiontexturevertex3 = new PositionTextureVertex(p_i46301_4_, p_i46301_5_, f2, 0.0F, 0.0F);
/* 140 */     PositionTextureVertex positiontexturevertex4 = new PositionTextureVertex(f, p_i46301_5_, f2, 0.0F, 8.0F);
/* 141 */     PositionTextureVertex positiontexturevertex5 = new PositionTextureVertex(f, f1, f2, 8.0F, 8.0F);
/* 142 */     PositionTextureVertex positiontexturevertex6 = new PositionTextureVertex(p_i46301_4_, f1, f2, 8.0F, 0.0F);
/* 143 */     this.vertexPositions[0] = positiontexturevertex7;
/* 144 */     this.vertexPositions[1] = positiontexturevertex;
/* 145 */     this.vertexPositions[2] = positiontexturevertex1;
/* 146 */     this.vertexPositions[3] = positiontexturevertex2;
/* 147 */     this.vertexPositions[4] = positiontexturevertex3;
/* 148 */     this.vertexPositions[5] = positiontexturevertex4;
/* 149 */     this.vertexPositions[6] = positiontexturevertex5;
/* 150 */     this.vertexPositions[7] = positiontexturevertex6;
/* 151 */     this.quadList[0] = new TexturedQuad(new PositionTextureVertex[] { positiontexturevertex4, positiontexturevertex, positiontexturevertex1, positiontexturevertex5 }, textureX + p_i46301_9_ + p_i46301_7_, textureY + p_i46301_9_, textureX + p_i46301_9_ + p_i46301_7_ + p_i46301_9_, textureY + p_i46301_9_ + p_i46301_8_, renderer.textureWidth, renderer.textureHeight);
/* 152 */     this.quadList[1] = new TexturedQuad(new PositionTextureVertex[] { positiontexturevertex7, positiontexturevertex3, positiontexturevertex6, positiontexturevertex2 }, textureX, textureY + p_i46301_9_, textureX + p_i46301_9_, textureY + p_i46301_9_ + p_i46301_8_, renderer.textureWidth, renderer.textureHeight);
/* 153 */     this.quadList[2] = new TexturedQuad(new PositionTextureVertex[] { positiontexturevertex4, positiontexturevertex3, positiontexturevertex7, positiontexturevertex }, textureX + p_i46301_9_, textureY, textureX + p_i46301_9_ + p_i46301_7_, textureY + p_i46301_9_, renderer.textureWidth, renderer.textureHeight);
/* 154 */     this.quadList[3] = new TexturedQuad(new PositionTextureVertex[] { positiontexturevertex1, positiontexturevertex2, positiontexturevertex6, positiontexturevertex5 }, textureX + p_i46301_9_ + p_i46301_7_, textureY + p_i46301_9_, textureX + p_i46301_9_ + p_i46301_7_ + p_i46301_7_, textureY, renderer.textureWidth, renderer.textureHeight);
/* 155 */     this.quadList[4] = new TexturedQuad(new PositionTextureVertex[] { positiontexturevertex, positiontexturevertex7, positiontexturevertex2, positiontexturevertex1 }, textureX + p_i46301_9_, textureY + p_i46301_9_, textureX + p_i46301_9_ + p_i46301_7_, textureY + p_i46301_9_ + p_i46301_8_, renderer.textureWidth, renderer.textureHeight);
/* 156 */     this.quadList[5] = new TexturedQuad(new PositionTextureVertex[] { positiontexturevertex3, positiontexturevertex4, positiontexturevertex5, positiontexturevertex6 }, textureX + p_i46301_9_ + p_i46301_7_ + p_i46301_9_, textureY + p_i46301_9_, textureX + p_i46301_9_ + p_i46301_7_ + p_i46301_9_ + p_i46301_7_, textureY + p_i46301_9_ + p_i46301_8_, renderer.textureWidth, renderer.textureHeight);
/*     */     
/* 158 */     if (p_i46301_11_) {
/* 159 */       for (int i = 0; i < this.quadList.length; i++) {
/* 160 */         this.quadList[i].flipFace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void render(WorldRenderer renderer, float scale) {
/* 166 */     for (int i = 0; i < this.quadList.length; i++) {
/* 167 */       TexturedQuad texturedquad = this.quadList[i];
/*     */       
/* 169 */       if (texturedquad != null) {
/* 170 */         texturedquad.draw(renderer, scale);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public ModelBox setBoxName(String name) {
/* 176 */     this.boxName = name;
/* 177 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\model\ModelBox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */