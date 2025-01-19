/*     */ package net.minecraft.client.renderer.block.model;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraftforge.client.model.pipeline.IVertexConsumer;
/*     */ import net.minecraftforge.client.model.pipeline.IVertexProducer;
/*     */ import net.optifine.model.QuadBounds;
/*     */ import net.optifine.reflect.Reflector;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BakedQuad
/*     */   implements IVertexProducer
/*     */ {
/*     */   protected int[] vertexData;
/*     */   protected final int tintIndex;
/*     */   protected EnumFacing face;
/*     */   protected TextureAtlasSprite sprite;
/*  21 */   private int[] vertexDataSingle = null;
/*     */   private QuadBounds quadBounds;
/*     */   private boolean quadEmissiveChecked;
/*     */   private BakedQuad quadEmissive;
/*     */   
/*     */   public BakedQuad(int[] p_i3_1_, int p_i3_2_, EnumFacing p_i3_3_, TextureAtlasSprite p_i3_4_) {
/*  27 */     this.vertexData = p_i3_1_;
/*  28 */     this.tintIndex = p_i3_2_;
/*  29 */     this.face = p_i3_3_;
/*  30 */     this.sprite = p_i3_4_;
/*  31 */     fixVertexData();
/*     */   }
/*     */   
/*     */   public BakedQuad(int[] vertexDataIn, int tintIndexIn, EnumFacing faceIn) {
/*  35 */     this.vertexData = vertexDataIn;
/*  36 */     this.tintIndex = tintIndexIn;
/*  37 */     this.face = faceIn;
/*  38 */     fixVertexData();
/*     */   }
/*     */   
/*     */   public TextureAtlasSprite getSprite() {
/*  42 */     if (this.sprite == null) {
/*  43 */       this.sprite = getSpriteByUv(getVertexData());
/*     */     }
/*     */     
/*  46 */     return this.sprite;
/*     */   }
/*     */   
/*     */   public int[] getVertexData() {
/*  50 */     fixVertexData();
/*  51 */     return this.vertexData;
/*     */   }
/*     */   
/*     */   public boolean hasTintIndex() {
/*  55 */     return (this.tintIndex != -1);
/*     */   }
/*     */   
/*     */   public int getTintIndex() {
/*  59 */     return this.tintIndex;
/*     */   }
/*     */   
/*     */   public EnumFacing getFace() {
/*  63 */     if (this.face == null) {
/*  64 */       this.face = FaceBakery.getFacingFromVertexData(getVertexData());
/*     */     }
/*     */     
/*  67 */     return this.face;
/*     */   }
/*     */   
/*     */   public int[] getVertexDataSingle() {
/*  71 */     if (this.vertexDataSingle == null) {
/*  72 */       this.vertexDataSingle = makeVertexDataSingle(getVertexData(), getSprite());
/*     */     }
/*     */     
/*  75 */     return this.vertexDataSingle;
/*     */   }
/*     */   
/*     */   private static int[] makeVertexDataSingle(int[] p_makeVertexDataSingle_0_, TextureAtlasSprite p_makeVertexDataSingle_1_) {
/*  79 */     int[] aint = (int[])p_makeVertexDataSingle_0_.clone();
/*  80 */     int i = aint.length / 4;
/*     */     
/*  82 */     for (int j = 0; j < 4; j++) {
/*  83 */       int k = j * i;
/*  84 */       float f = Float.intBitsToFloat(aint[k + 4]);
/*  85 */       float f1 = Float.intBitsToFloat(aint[k + 4 + 1]);
/*  86 */       float f2 = p_makeVertexDataSingle_1_.toSingleU(f);
/*  87 */       float f3 = p_makeVertexDataSingle_1_.toSingleV(f1);
/*  88 */       aint[k + 4] = Float.floatToRawIntBits(f2);
/*  89 */       aint[k + 4 + 1] = Float.floatToRawIntBits(f3);
/*     */     } 
/*     */     
/*  92 */     return aint;
/*     */   }
/*     */   
/*     */   public void pipe(IVertexConsumer p_pipe_1_) {
/*  96 */     Reflector.callVoid(Reflector.LightUtil_putBakedQuad, new Object[] { p_pipe_1_, this });
/*     */   }
/*     */   
/*     */   private static TextureAtlasSprite getSpriteByUv(int[] p_getSpriteByUv_0_) {
/* 100 */     float f = 1.0F;
/* 101 */     float f1 = 1.0F;
/* 102 */     float f2 = 0.0F;
/* 103 */     float f3 = 0.0F;
/* 104 */     int i = p_getSpriteByUv_0_.length / 4;
/*     */     
/* 106 */     for (int j = 0; j < 4; j++) {
/* 107 */       int k = j * i;
/* 108 */       float f4 = Float.intBitsToFloat(p_getSpriteByUv_0_[k + 4]);
/* 109 */       float f5 = Float.intBitsToFloat(p_getSpriteByUv_0_[k + 4 + 1]);
/* 110 */       f = Math.min(f, f4);
/* 111 */       f1 = Math.min(f1, f5);
/* 112 */       f2 = Math.max(f2, f4);
/* 113 */       f3 = Math.max(f3, f5);
/*     */     } 
/*     */     
/* 116 */     float f6 = (f + f2) / 2.0F;
/* 117 */     float f7 = (f1 + f3) / 2.0F;
/* 118 */     TextureAtlasSprite textureatlassprite = Minecraft.getMinecraft().getTextureMapBlocks().getIconByUV(f6, f7);
/* 119 */     return textureatlassprite;
/*     */   }
/*     */   
/*     */   protected void fixVertexData() {
/* 123 */     if (Config.isShaders()) {
/* 124 */       if (this.vertexData.length == 28) {
/* 125 */         this.vertexData = expandVertexData(this.vertexData);
/*     */       }
/* 127 */     } else if (this.vertexData.length == 56) {
/* 128 */       this.vertexData = compactVertexData(this.vertexData);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static int[] expandVertexData(int[] p_expandVertexData_0_) {
/* 133 */     int i = p_expandVertexData_0_.length / 4;
/* 134 */     int j = i * 2;
/* 135 */     int[] aint = new int[j * 4];
/*     */     
/* 137 */     for (int k = 0; k < 4; k++) {
/* 138 */       System.arraycopy(p_expandVertexData_0_, k * i, aint, k * j, i);
/*     */     }
/*     */     
/* 141 */     return aint;
/*     */   }
/*     */   
/*     */   private static int[] compactVertexData(int[] p_compactVertexData_0_) {
/* 145 */     int i = p_compactVertexData_0_.length / 4;
/* 146 */     int j = i / 2;
/* 147 */     int[] aint = new int[j * 4];
/*     */     
/* 149 */     for (int k = 0; k < 4; k++) {
/* 150 */       System.arraycopy(p_compactVertexData_0_, k * i, aint, k * j, j);
/*     */     }
/*     */     
/* 153 */     return aint;
/*     */   }
/*     */   
/*     */   public QuadBounds getQuadBounds() {
/* 157 */     if (this.quadBounds == null) {
/* 158 */       this.quadBounds = new QuadBounds(getVertexData());
/*     */     }
/*     */     
/* 161 */     return this.quadBounds;
/*     */   }
/*     */   
/*     */   public float getMidX() {
/* 165 */     QuadBounds quadbounds = getQuadBounds();
/* 166 */     return (quadbounds.getMaxX() + quadbounds.getMinX()) / 2.0F;
/*     */   }
/*     */   
/*     */   public double getMidY() {
/* 170 */     QuadBounds quadbounds = getQuadBounds();
/* 171 */     return ((quadbounds.getMaxY() + quadbounds.getMinY()) / 2.0F);
/*     */   }
/*     */   
/*     */   public double getMidZ() {
/* 175 */     QuadBounds quadbounds = getQuadBounds();
/* 176 */     return ((quadbounds.getMaxZ() + quadbounds.getMinZ()) / 2.0F);
/*     */   }
/*     */   
/*     */   public boolean isFaceQuad() {
/* 180 */     QuadBounds quadbounds = getQuadBounds();
/* 181 */     return quadbounds.isFaceQuad(this.face);
/*     */   }
/*     */   
/*     */   public boolean isFullQuad() {
/* 185 */     QuadBounds quadbounds = getQuadBounds();
/* 186 */     return quadbounds.isFullQuad(this.face);
/*     */   }
/*     */   
/*     */   public boolean isFullFaceQuad() {
/* 190 */     return (isFullQuad() && isFaceQuad());
/*     */   }
/*     */   
/*     */   public BakedQuad getQuadEmissive() {
/* 194 */     if (this.quadEmissiveChecked) {
/* 195 */       return this.quadEmissive;
/*     */     }
/* 197 */     if (this.quadEmissive == null && this.sprite != null && this.sprite.spriteEmissive != null) {
/* 198 */       this.quadEmissive = new BreakingFour(this, this.sprite.spriteEmissive);
/*     */     }
/*     */     
/* 201 */     this.quadEmissiveChecked = true;
/* 202 */     return this.quadEmissive;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 207 */     return "vertex: " + (this.vertexData.length / 7) + ", tint: " + this.tintIndex + ", facing: " + this.face + ", sprite: " + this.sprite;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\block\model\BakedQuad.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */