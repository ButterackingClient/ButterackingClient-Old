/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import com.google.common.primitives.Floats;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.nio.ShortBuffer;
/*     */ import java.util.Arrays;
/*     */ import java.util.BitSet;
/*     */ import java.util.Comparator;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.vertex.VertexFormat;
/*     */ import net.minecraft.client.renderer.vertex.VertexFormatElement;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.optifine.SmartAnimations;
/*     */ import net.optifine.render.RenderEnv;
/*     */ import net.optifine.shaders.SVertexBuilder;
/*     */ import net.optifine.util.TextureUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldRenderer
/*     */ {
/*     */   private ByteBuffer byteBuffer;
/*     */   public IntBuffer rawIntBuffer;
/*     */   private ShortBuffer rawShortBuffer;
/*     */   public FloatBuffer rawFloatBuffer;
/*     */   public int vertexCount;
/*     */   private VertexFormatElement vertexFormatElement;
/*     */   private int vertexFormatIndex;
/*     */   private boolean noColor;
/*     */   public int drawMode;
/*     */   private double xOffset;
/*     */   private double yOffset;
/*     */   private double zOffset;
/*     */   private VertexFormat vertexFormat;
/*     */   private boolean isDrawing;
/*  48 */   private EnumWorldBlockLayer blockLayer = null;
/*  49 */   private boolean[] drawnIcons = new boolean[256];
/*  50 */   private TextureAtlasSprite[] quadSprites = null;
/*  51 */   private TextureAtlasSprite[] quadSpritesPrev = null;
/*  52 */   private TextureAtlasSprite quadSprite = null;
/*     */   public SVertexBuilder sVertexBuilder;
/*  54 */   public RenderEnv renderEnv = null;
/*  55 */   public BitSet animatedSprites = null;
/*  56 */   public BitSet animatedSpritesCached = new BitSet();
/*     */   private boolean modeTriangles = false;
/*     */   private ByteBuffer byteBufferTriangles;
/*     */   
/*     */   public WorldRenderer(int bufferSizeIn) {
/*  61 */     this.byteBuffer = GLAllocation.createDirectByteBuffer(bufferSizeIn * 4);
/*  62 */     this.rawIntBuffer = this.byteBuffer.asIntBuffer();
/*  63 */     this.rawShortBuffer = this.byteBuffer.asShortBuffer();
/*  64 */     this.rawFloatBuffer = this.byteBuffer.asFloatBuffer();
/*  65 */     SVertexBuilder.initVertexBuilder(this);
/*     */   }
/*     */   
/*     */   private void growBuffer(int p_181670_1_) {
/*  69 */     if (p_181670_1_ > this.rawIntBuffer.remaining()) {
/*  70 */       int i = this.byteBuffer.capacity();
/*  71 */       int j = i % 2097152;
/*  72 */       int k = j + (((this.rawIntBuffer.position() + p_181670_1_) * 4 - j) / 2097152 + 1) * 2097152;
/*  73 */       LogManager.getLogger().warn("Needed to grow BufferBuilder buffer: Old size " + i + " bytes, new size " + k + " bytes.");
/*  74 */       int l = this.rawIntBuffer.position();
/*  75 */       ByteBuffer bytebuffer = GLAllocation.createDirectByteBuffer(k);
/*  76 */       this.byteBuffer.position(0);
/*  77 */       bytebuffer.put(this.byteBuffer);
/*  78 */       bytebuffer.rewind();
/*  79 */       this.byteBuffer = bytebuffer;
/*  80 */       this.rawFloatBuffer = this.byteBuffer.asFloatBuffer();
/*  81 */       this.rawIntBuffer = this.byteBuffer.asIntBuffer();
/*  82 */       this.rawIntBuffer.position(l);
/*  83 */       this.rawShortBuffer = this.byteBuffer.asShortBuffer();
/*  84 */       this.rawShortBuffer.position(l << 1);
/*     */       
/*  86 */       if (this.quadSprites != null) {
/*  87 */         TextureAtlasSprite[] atextureatlassprite = this.quadSprites;
/*  88 */         int i1 = getBufferQuadSize();
/*  89 */         this.quadSprites = new TextureAtlasSprite[i1];
/*  90 */         System.arraycopy(atextureatlassprite, 0, this.quadSprites, 0, Math.min(atextureatlassprite.length, this.quadSprites.length));
/*  91 */         this.quadSpritesPrev = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void sortVertexData(float p_181674_1_, float p_181674_2_, float p_181674_3_) {
/*  97 */     int i = this.vertexCount / 4;
/*  98 */     final float[] afloat = new float[i];
/*     */     
/* 100 */     for (int j = 0; j < i; j++) {
/* 101 */       afloat[j] = getDistanceSq(this.rawFloatBuffer, (float)(p_181674_1_ + this.xOffset), (float)(p_181674_2_ + this.yOffset), (float)(p_181674_3_ + this.zOffset), this.vertexFormat.getIntegerSize(), j * this.vertexFormat.getNextOffset());
/*     */     }
/*     */     
/* 104 */     Integer[] ainteger = new Integer[i];
/*     */     
/* 106 */     for (int k = 0; k < ainteger.length; k++) {
/* 107 */       ainteger[k] = Integer.valueOf(k);
/*     */     }
/*     */     
/* 110 */     Arrays.sort(ainteger, new Comparator<Integer>() {
/*     */           public int compare(Integer p_compare_1_, Integer p_compare_2_) {
/* 112 */             return Floats.compare(afloat[p_compare_2_.intValue()], afloat[p_compare_1_.intValue()]);
/*     */           }
/*     */         });
/* 115 */     BitSet bitset = new BitSet();
/* 116 */     int l = this.vertexFormat.getNextOffset();
/* 117 */     int[] aint = new int[l];
/*     */     
/* 119 */     for (int l1 = 0; (l1 = bitset.nextClearBit(l1)) < ainteger.length; l1++) {
/* 120 */       int i1 = ainteger[l1].intValue();
/*     */       
/* 122 */       if (i1 != l1) {
/* 123 */         this.rawIntBuffer.limit(i1 * l + l);
/* 124 */         this.rawIntBuffer.position(i1 * l);
/* 125 */         this.rawIntBuffer.get(aint);
/* 126 */         int j1 = i1;
/*     */         
/* 128 */         for (int k1 = ainteger[i1].intValue(); j1 != l1; k1 = ainteger[k1].intValue()) {
/* 129 */           this.rawIntBuffer.limit(k1 * l + l);
/* 130 */           this.rawIntBuffer.position(k1 * l);
/* 131 */           IntBuffer intbuffer = this.rawIntBuffer.slice();
/* 132 */           this.rawIntBuffer.limit(j1 * l + l);
/* 133 */           this.rawIntBuffer.position(j1 * l);
/* 134 */           this.rawIntBuffer.put(intbuffer);
/* 135 */           bitset.set(j1);
/* 136 */           j1 = k1;
/*     */         } 
/*     */         
/* 139 */         this.rawIntBuffer.limit(l1 * l + l);
/* 140 */         this.rawIntBuffer.position(l1 * l);
/* 141 */         this.rawIntBuffer.put(aint);
/*     */       } 
/*     */       
/* 144 */       bitset.set(l1);
/*     */     } 
/*     */     
/* 147 */     this.rawIntBuffer.limit(this.rawIntBuffer.capacity());
/* 148 */     this.rawIntBuffer.position(getBufferSize());
/*     */     
/* 150 */     if (this.quadSprites != null) {
/* 151 */       TextureAtlasSprite[] atextureatlassprite = new TextureAtlasSprite[this.vertexCount / 4];
/* 152 */       int i2 = this.vertexFormat.getNextOffset() / 4 * 4;
/*     */       
/* 154 */       for (int j2 = 0; j2 < ainteger.length; j2++) {
/* 155 */         int k2 = ainteger[j2].intValue();
/* 156 */         atextureatlassprite[j2] = this.quadSprites[k2];
/*     */       } 
/*     */       
/* 159 */       System.arraycopy(atextureatlassprite, 0, this.quadSprites, 0, atextureatlassprite.length);
/*     */     } 
/*     */   }
/*     */   
/*     */   public State getVertexState() {
/* 164 */     this.rawIntBuffer.rewind();
/* 165 */     int i = getBufferSize();
/* 166 */     this.rawIntBuffer.limit(i);
/* 167 */     int[] aint = new int[i];
/* 168 */     this.rawIntBuffer.get(aint);
/* 169 */     this.rawIntBuffer.limit(this.rawIntBuffer.capacity());
/* 170 */     this.rawIntBuffer.position(i);
/* 171 */     TextureAtlasSprite[] atextureatlassprite = null;
/*     */     
/* 173 */     if (this.quadSprites != null) {
/* 174 */       int j = this.vertexCount / 4;
/* 175 */       atextureatlassprite = new TextureAtlasSprite[j];
/* 176 */       System.arraycopy(this.quadSprites, 0, atextureatlassprite, 0, j);
/*     */     } 
/*     */     
/* 179 */     return new State(aint, new VertexFormat(this.vertexFormat), atextureatlassprite);
/*     */   }
/*     */   
/*     */   public int getBufferSize() {
/* 183 */     return this.vertexCount * this.vertexFormat.getIntegerSize();
/*     */   }
/*     */   
/*     */   private static float getDistanceSq(FloatBuffer p_181665_0_, float p_181665_1_, float p_181665_2_, float p_181665_3_, int p_181665_4_, int p_181665_5_) {
/* 187 */     float f = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 0 + 0);
/* 188 */     float f1 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 0 + 1);
/* 189 */     float f2 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 0 + 2);
/* 190 */     float f3 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 1 + 0);
/* 191 */     float f4 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 1 + 1);
/* 192 */     float f5 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 1 + 2);
/* 193 */     float f6 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 2 + 0);
/* 194 */     float f7 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 2 + 1);
/* 195 */     float f8 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 2 + 2);
/* 196 */     float f9 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 3 + 0);
/* 197 */     float f10 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 3 + 1);
/* 198 */     float f11 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 3 + 2);
/* 199 */     float f12 = (f + f3 + f6 + f9) * 0.25F - p_181665_1_;
/* 200 */     float f13 = (f1 + f4 + f7 + f10) * 0.25F - p_181665_2_;
/* 201 */     float f14 = (f2 + f5 + f8 + f11) * 0.25F - p_181665_3_;
/* 202 */     return f12 * f12 + f13 * f13 + f14 * f14;
/*     */   }
/*     */   
/*     */   public void setVertexState(State state) {
/* 206 */     this.rawIntBuffer.clear();
/* 207 */     growBuffer((state.getRawBuffer()).length);
/* 208 */     this.rawIntBuffer.put(state.getRawBuffer());
/* 209 */     this.vertexCount = state.getVertexCount();
/* 210 */     this.vertexFormat = new VertexFormat(state.getVertexFormat());
/*     */     
/* 212 */     if (state.stateQuadSprites != null) {
/* 213 */       if (this.quadSprites == null) {
/* 214 */         this.quadSprites = this.quadSpritesPrev;
/*     */       }
/*     */       
/* 217 */       if (this.quadSprites == null || this.quadSprites.length < getBufferQuadSize()) {
/* 218 */         this.quadSprites = new TextureAtlasSprite[getBufferQuadSize()];
/*     */       }
/*     */       
/* 221 */       TextureAtlasSprite[] atextureatlassprite = state.stateQuadSprites;
/* 222 */       System.arraycopy(atextureatlassprite, 0, this.quadSprites, 0, atextureatlassprite.length);
/*     */     } else {
/* 224 */       if (this.quadSprites != null) {
/* 225 */         this.quadSpritesPrev = this.quadSprites;
/*     */       }
/*     */       
/* 228 */       this.quadSprites = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void reset() {
/* 233 */     this.vertexCount = 0;
/* 234 */     this.vertexFormatElement = null;
/* 235 */     this.vertexFormatIndex = 0;
/* 236 */     this.quadSprite = null;
/*     */     
/* 238 */     if (SmartAnimations.isActive()) {
/* 239 */       if (this.animatedSprites == null) {
/* 240 */         this.animatedSprites = this.animatedSpritesCached;
/*     */       }
/*     */       
/* 243 */       this.animatedSprites.clear();
/* 244 */     } else if (this.animatedSprites != null) {
/* 245 */       this.animatedSprites = null;
/*     */     } 
/*     */     
/* 248 */     this.modeTriangles = false;
/*     */   }
/*     */   
/*     */   public void begin(int glMode, VertexFormat format) {
/* 252 */     if (this.isDrawing) {
/* 253 */       throw new IllegalStateException("Already building!");
/*     */     }
/* 255 */     this.isDrawing = true;
/* 256 */     reset();
/* 257 */     this.drawMode = glMode;
/* 258 */     this.vertexFormat = format;
/* 259 */     this.vertexFormatElement = format.getElement(this.vertexFormatIndex);
/* 260 */     this.noColor = false;
/* 261 */     this.byteBuffer.limit(this.byteBuffer.capacity());
/*     */     
/* 263 */     if (Config.isShaders()) {
/* 264 */       SVertexBuilder.endSetVertexFormat(this);
/*     */     }
/*     */     
/* 267 */     if (Config.isMultiTexture()) {
/* 268 */       if (this.blockLayer != null) {
/* 269 */         if (this.quadSprites == null) {
/* 270 */           this.quadSprites = this.quadSpritesPrev;
/*     */         }
/*     */         
/* 273 */         if (this.quadSprites == null || this.quadSprites.length < getBufferQuadSize()) {
/* 274 */           this.quadSprites = new TextureAtlasSprite[getBufferQuadSize()];
/*     */         }
/*     */       } 
/*     */     } else {
/* 278 */       if (this.quadSprites != null) {
/* 279 */         this.quadSpritesPrev = this.quadSprites;
/*     */       }
/*     */       
/* 282 */       this.quadSprites = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldRenderer tex(double u, double v) {
/* 288 */     if (this.quadSprite != null && this.quadSprites != null) {
/* 289 */       u = this.quadSprite.toSingleU((float)u);
/* 290 */       v = this.quadSprite.toSingleV((float)v);
/* 291 */       this.quadSprites[this.vertexCount / 4] = this.quadSprite;
/*     */     } 
/*     */     
/* 294 */     int i = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.getOffset(this.vertexFormatIndex);
/*     */     
/* 296 */     switch (this.vertexFormatElement.getType()) {
/*     */       case FLOAT:
/* 298 */         this.byteBuffer.putFloat(i, (float)u);
/* 299 */         this.byteBuffer.putFloat(i + 4, (float)v);
/*     */         break;
/*     */       
/*     */       case UINT:
/*     */       case INT:
/* 304 */         this.byteBuffer.putInt(i, (int)u);
/* 305 */         this.byteBuffer.putInt(i + 4, (int)v);
/*     */         break;
/*     */       
/*     */       case USHORT:
/*     */       case SHORT:
/* 310 */         this.byteBuffer.putShort(i, (short)(int)v);
/* 311 */         this.byteBuffer.putShort(i + 2, (short)(int)u);
/*     */         break;
/*     */       
/*     */       case UBYTE:
/*     */       case null:
/* 316 */         this.byteBuffer.put(i, (byte)(int)v);
/* 317 */         this.byteBuffer.put(i + 1, (byte)(int)u);
/*     */         break;
/*     */     } 
/* 320 */     nextVertexFormatIndex();
/* 321 */     return this;
/*     */   }
/*     */   
/*     */   public WorldRenderer lightmap(int p_181671_1_, int p_181671_2_) {
/* 325 */     int i = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.getOffset(this.vertexFormatIndex);
/*     */     
/* 327 */     switch (this.vertexFormatElement.getType()) {
/*     */       case FLOAT:
/* 329 */         this.byteBuffer.putFloat(i, p_181671_1_);
/* 330 */         this.byteBuffer.putFloat(i + 4, p_181671_2_);
/*     */         break;
/*     */       
/*     */       case UINT:
/*     */       case INT:
/* 335 */         this.byteBuffer.putInt(i, p_181671_1_);
/* 336 */         this.byteBuffer.putInt(i + 4, p_181671_2_);
/*     */         break;
/*     */       
/*     */       case USHORT:
/*     */       case SHORT:
/* 341 */         this.byteBuffer.putShort(i, (short)p_181671_2_);
/* 342 */         this.byteBuffer.putShort(i + 2, (short)p_181671_1_);
/*     */         break;
/*     */       
/*     */       case UBYTE:
/*     */       case null:
/* 347 */         this.byteBuffer.put(i, (byte)p_181671_2_);
/* 348 */         this.byteBuffer.put(i + 1, (byte)p_181671_1_);
/*     */         break;
/*     */     } 
/* 351 */     nextVertexFormatIndex();
/* 352 */     return this;
/*     */   }
/*     */   
/*     */   public void putBrightness4(int p_178962_1_, int p_178962_2_, int p_178962_3_, int p_178962_4_) {
/* 356 */     int i = (this.vertexCount - 4) * this.vertexFormat.getIntegerSize() + this.vertexFormat.getUvOffsetById(1) / 4;
/* 357 */     int j = this.vertexFormat.getNextOffset() >> 2;
/* 358 */     this.rawIntBuffer.put(i, p_178962_1_);
/* 359 */     this.rawIntBuffer.put(i + j, p_178962_2_);
/* 360 */     this.rawIntBuffer.put(i + j * 2, p_178962_3_);
/* 361 */     this.rawIntBuffer.put(i + j * 3, p_178962_4_);
/*     */   }
/*     */   
/*     */   public void putPosition(double x, double y, double z) {
/* 365 */     int i = this.vertexFormat.getIntegerSize();
/* 366 */     int j = (this.vertexCount - 4) * i;
/*     */     
/* 368 */     for (int k = 0; k < 4; k++) {
/* 369 */       int l = j + k * i;
/* 370 */       int i1 = l + 1;
/* 371 */       int j1 = i1 + 1;
/* 372 */       this.rawIntBuffer.put(l, Float.floatToRawIntBits((float)(x + this.xOffset) + Float.intBitsToFloat(this.rawIntBuffer.get(l))));
/* 373 */       this.rawIntBuffer.put(i1, Float.floatToRawIntBits((float)(y + this.yOffset) + Float.intBitsToFloat(this.rawIntBuffer.get(i1))));
/* 374 */       this.rawIntBuffer.put(j1, Float.floatToRawIntBits((float)(z + this.zOffset) + Float.intBitsToFloat(this.rawIntBuffer.get(j1))));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColorIndex(int p_78909_1_) {
/* 382 */     return ((this.vertexCount - p_78909_1_) * this.vertexFormat.getNextOffset() + this.vertexFormat.getColorOffset()) / 4;
/*     */   }
/*     */   
/*     */   public void putColorMultiplier(float red, float green, float blue, int p_178978_4_) {
/* 386 */     int i = getColorIndex(p_178978_4_);
/* 387 */     int j = -1;
/*     */     
/* 389 */     if (!this.noColor) {
/* 390 */       j = this.rawIntBuffer.get(i);
/*     */       
/* 392 */       if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
/* 393 */         int k = (int)((j & 0xFF) * red);
/* 394 */         int l = (int)((j >> 8 & 0xFF) * green);
/* 395 */         int i1 = (int)((j >> 16 & 0xFF) * blue);
/* 396 */         j &= 0xFF000000;
/* 397 */         j = j | i1 << 16 | l << 8 | k;
/*     */       } else {
/* 399 */         int j1 = (int)((j >> 24 & 0xFF) * red);
/* 400 */         int k1 = (int)((j >> 16 & 0xFF) * green);
/* 401 */         int l1 = (int)((j >> 8 & 0xFF) * blue);
/* 402 */         j &= 0xFF;
/* 403 */         j = j | j1 << 24 | k1 << 16 | l1 << 8;
/*     */       } 
/*     */     } 
/*     */     
/* 407 */     this.rawIntBuffer.put(i, j);
/*     */   }
/*     */   
/*     */   private void putColor(int argb, int p_178988_2_) {
/* 411 */     int i = getColorIndex(p_178988_2_);
/* 412 */     int j = argb >> 16 & 0xFF;
/* 413 */     int k = argb >> 8 & 0xFF;
/* 414 */     int l = argb & 0xFF;
/* 415 */     int i1 = argb >> 24 & 0xFF;
/* 416 */     putColorRGBA(i, j, k, l, i1);
/*     */   }
/*     */   
/*     */   public void putColorRGB_F(float red, float green, float blue, int p_178994_4_) {
/* 420 */     int i = getColorIndex(p_178994_4_);
/* 421 */     int j = MathHelper.clamp_int((int)(red * 255.0F), 0, 255);
/* 422 */     int k = MathHelper.clamp_int((int)(green * 255.0F), 0, 255);
/* 423 */     int l = MathHelper.clamp_int((int)(blue * 255.0F), 0, 255);
/* 424 */     putColorRGBA(i, j, k, l, 255);
/*     */   }
/*     */   
/*     */   public void putColorRGBA(int index, int red, int p_178972_3_, int p_178972_4_, int p_178972_5_) {
/* 428 */     if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
/* 429 */       this.rawIntBuffer.put(index, p_178972_5_ << 24 | p_178972_4_ << 16 | p_178972_3_ << 8 | red);
/*     */     } else {
/* 431 */       this.rawIntBuffer.put(index, red << 24 | p_178972_3_ << 16 | p_178972_4_ << 8 | p_178972_5_);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void noColor() {
/* 439 */     this.noColor = true;
/*     */   }
/*     */   
/*     */   public WorldRenderer color(float red, float green, float blue, float alpha) {
/* 443 */     return color((int)(red * 255.0F), (int)(green * 255.0F), (int)(blue * 255.0F), (int)(alpha * 255.0F));
/*     */   }
/*     */   
/*     */   public WorldRenderer color(int red, int green, int blue, int alpha) {
/* 447 */     if (this.noColor) {
/* 448 */       return this;
/*     */     }
/* 450 */     int i = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.getOffset(this.vertexFormatIndex);
/*     */     
/* 452 */     switch (this.vertexFormatElement.getType()) {
/*     */       case FLOAT:
/* 454 */         this.byteBuffer.putFloat(i, red / 255.0F);
/* 455 */         this.byteBuffer.putFloat(i + 4, green / 255.0F);
/* 456 */         this.byteBuffer.putFloat(i + 8, blue / 255.0F);
/* 457 */         this.byteBuffer.putFloat(i + 12, alpha / 255.0F);
/*     */         break;
/*     */       
/*     */       case UINT:
/*     */       case INT:
/* 462 */         this.byteBuffer.putFloat(i, red);
/* 463 */         this.byteBuffer.putFloat(i + 4, green);
/* 464 */         this.byteBuffer.putFloat(i + 8, blue);
/* 465 */         this.byteBuffer.putFloat(i + 12, alpha);
/*     */         break;
/*     */       
/*     */       case USHORT:
/*     */       case SHORT:
/* 470 */         this.byteBuffer.putShort(i, (short)red);
/* 471 */         this.byteBuffer.putShort(i + 2, (short)green);
/* 472 */         this.byteBuffer.putShort(i + 4, (short)blue);
/* 473 */         this.byteBuffer.putShort(i + 6, (short)alpha);
/*     */         break;
/*     */       
/*     */       case UBYTE:
/*     */       case null:
/* 478 */         if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
/* 479 */           this.byteBuffer.put(i, (byte)red);
/* 480 */           this.byteBuffer.put(i + 1, (byte)green);
/* 481 */           this.byteBuffer.put(i + 2, (byte)blue);
/* 482 */           this.byteBuffer.put(i + 3, (byte)alpha); break;
/*     */         } 
/* 484 */         this.byteBuffer.put(i, (byte)alpha);
/* 485 */         this.byteBuffer.put(i + 1, (byte)blue);
/* 486 */         this.byteBuffer.put(i + 2, (byte)green);
/* 487 */         this.byteBuffer.put(i + 3, (byte)red);
/*     */         break;
/*     */     } 
/*     */     
/* 491 */     nextVertexFormatIndex();
/* 492 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addVertexData(int[] vertexData) {
/* 497 */     if (Config.isShaders()) {
/* 498 */       SVertexBuilder.beginAddVertexData(this, vertexData);
/*     */     }
/*     */     
/* 501 */     growBuffer(vertexData.length);
/* 502 */     this.rawIntBuffer.position(getBufferSize());
/* 503 */     this.rawIntBuffer.put(vertexData);
/* 504 */     this.vertexCount += vertexData.length / this.vertexFormat.getIntegerSize();
/*     */     
/* 506 */     if (Config.isShaders()) {
/* 507 */       SVertexBuilder.endAddVertexData(this);
/*     */     }
/*     */   }
/*     */   
/*     */   public void endVertex() {
/* 512 */     this.vertexCount++;
/* 513 */     growBuffer(this.vertexFormat.getIntegerSize());
/* 514 */     this.vertexFormatIndex = 0;
/* 515 */     this.vertexFormatElement = this.vertexFormat.getElement(this.vertexFormatIndex);
/*     */     
/* 517 */     if (Config.isShaders()) {
/* 518 */       SVertexBuilder.endAddVertex(this);
/*     */     }
/*     */   }
/*     */   
/*     */   public WorldRenderer pos(double x, double y, double z) {
/* 523 */     if (Config.isShaders()) {
/* 524 */       SVertexBuilder.beginAddVertex(this);
/*     */     }
/*     */     
/* 527 */     int i = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.getOffset(this.vertexFormatIndex);
/*     */     
/* 529 */     switch (this.vertexFormatElement.getType()) {
/*     */       case FLOAT:
/* 531 */         this.byteBuffer.putFloat(i, (float)(x + this.xOffset));
/* 532 */         this.byteBuffer.putFloat(i + 4, (float)(y + this.yOffset));
/* 533 */         this.byteBuffer.putFloat(i + 8, (float)(z + this.zOffset));
/*     */         break;
/*     */       
/*     */       case UINT:
/*     */       case INT:
/* 538 */         this.byteBuffer.putInt(i, Float.floatToRawIntBits((float)(x + this.xOffset)));
/* 539 */         this.byteBuffer.putInt(i + 4, Float.floatToRawIntBits((float)(y + this.yOffset)));
/* 540 */         this.byteBuffer.putInt(i + 8, Float.floatToRawIntBits((float)(z + this.zOffset)));
/*     */         break;
/*     */       
/*     */       case USHORT:
/*     */       case SHORT:
/* 545 */         this.byteBuffer.putShort(i, (short)(int)(x + this.xOffset));
/* 546 */         this.byteBuffer.putShort(i + 2, (short)(int)(y + this.yOffset));
/* 547 */         this.byteBuffer.putShort(i + 4, (short)(int)(z + this.zOffset));
/*     */         break;
/*     */       
/*     */       case UBYTE:
/*     */       case null:
/* 552 */         this.byteBuffer.put(i, (byte)(int)(x + this.xOffset));
/* 553 */         this.byteBuffer.put(i + 1, (byte)(int)(y + this.yOffset));
/* 554 */         this.byteBuffer.put(i + 2, (byte)(int)(z + this.zOffset));
/*     */         break;
/*     */     } 
/* 557 */     nextVertexFormatIndex();
/* 558 */     return this;
/*     */   }
/*     */   
/*     */   public void putNormal(float x, float y, float z) {
/* 562 */     int i = (byte)(int)(x * 127.0F) & 0xFF;
/* 563 */     int j = (byte)(int)(y * 127.0F) & 0xFF;
/* 564 */     int k = (byte)(int)(z * 127.0F) & 0xFF;
/* 565 */     int l = i | j << 8 | k << 16;
/* 566 */     int i1 = this.vertexFormat.getNextOffset() >> 2;
/* 567 */     int j1 = (this.vertexCount - 4) * i1 + this.vertexFormat.getNormalOffset() / 4;
/* 568 */     this.rawIntBuffer.put(j1, l);
/* 569 */     this.rawIntBuffer.put(j1 + i1, l);
/* 570 */     this.rawIntBuffer.put(j1 + i1 * 2, l);
/* 571 */     this.rawIntBuffer.put(j1 + i1 * 3, l);
/*     */   }
/*     */   
/*     */   private void nextVertexFormatIndex() {
/* 575 */     this.vertexFormatIndex++;
/* 576 */     this.vertexFormatIndex %= this.vertexFormat.getElementCount();
/* 577 */     this.vertexFormatElement = this.vertexFormat.getElement(this.vertexFormatIndex);
/*     */     
/* 579 */     if (this.vertexFormatElement.getUsage() == VertexFormatElement.EnumUsage.PADDING) {
/* 580 */       nextVertexFormatIndex();
/*     */     }
/*     */   }
/*     */   
/*     */   public WorldRenderer normal(float p_181663_1_, float p_181663_2_, float p_181663_3_) {
/* 585 */     int i = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.getOffset(this.vertexFormatIndex);
/*     */     
/* 587 */     switch (this.vertexFormatElement.getType()) {
/*     */       case FLOAT:
/* 589 */         this.byteBuffer.putFloat(i, p_181663_1_);
/* 590 */         this.byteBuffer.putFloat(i + 4, p_181663_2_);
/* 591 */         this.byteBuffer.putFloat(i + 8, p_181663_3_);
/*     */         break;
/*     */       
/*     */       case UINT:
/*     */       case INT:
/* 596 */         this.byteBuffer.putInt(i, (int)p_181663_1_);
/* 597 */         this.byteBuffer.putInt(i + 4, (int)p_181663_2_);
/* 598 */         this.byteBuffer.putInt(i + 8, (int)p_181663_3_);
/*     */         break;
/*     */       
/*     */       case USHORT:
/*     */       case SHORT:
/* 603 */         this.byteBuffer.putShort(i, (short)((int)(p_181663_1_ * 32767.0F) & 0xFFFF));
/* 604 */         this.byteBuffer.putShort(i + 2, (short)((int)(p_181663_2_ * 32767.0F) & 0xFFFF));
/* 605 */         this.byteBuffer.putShort(i + 4, (short)((int)(p_181663_3_ * 32767.0F) & 0xFFFF));
/*     */         break;
/*     */       
/*     */       case UBYTE:
/*     */       case null:
/* 610 */         this.byteBuffer.put(i, (byte)((int)(p_181663_1_ * 127.0F) & 0xFF));
/* 611 */         this.byteBuffer.put(i + 1, (byte)((int)(p_181663_2_ * 127.0F) & 0xFF));
/* 612 */         this.byteBuffer.put(i + 2, (byte)((int)(p_181663_3_ * 127.0F) & 0xFF));
/*     */         break;
/*     */     } 
/* 615 */     nextVertexFormatIndex();
/* 616 */     return this;
/*     */   }
/*     */   
/*     */   public void setTranslation(double x, double y, double z) {
/* 620 */     this.xOffset = x;
/* 621 */     this.yOffset = y;
/* 622 */     this.zOffset = z;
/*     */   }
/*     */   
/*     */   public void finishDrawing() {
/* 626 */     if (!this.isDrawing) {
/* 627 */       throw new IllegalStateException("Not building!");
/*     */     }
/* 629 */     this.isDrawing = false;
/* 630 */     this.byteBuffer.position(0);
/* 631 */     this.byteBuffer.limit(getBufferSize() * 4);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer getByteBuffer() {
/* 636 */     return this.modeTriangles ? this.byteBufferTriangles : this.byteBuffer;
/*     */   }
/*     */   
/*     */   public VertexFormat getVertexFormat() {
/* 640 */     return this.vertexFormat;
/*     */   }
/*     */   
/*     */   public int getVertexCount() {
/* 644 */     return this.modeTriangles ? (this.vertexCount / 4 * 6) : this.vertexCount;
/*     */   }
/*     */   
/*     */   public int getDrawMode() {
/* 648 */     return this.modeTriangles ? 4 : this.drawMode;
/*     */   }
/*     */   
/*     */   public void putColor4(int argb) {
/* 652 */     for (int i = 0; i < 4; i++) {
/* 653 */       putColor(argb, i + 1);
/*     */     }
/*     */   }
/*     */   
/*     */   public void putColorRGB_F4(float red, float green, float blue) {
/* 658 */     for (int i = 0; i < 4; i++) {
/* 659 */       putColorRGB_F(red, green, blue, i + 1);
/*     */     }
/*     */   }
/*     */   
/*     */   public void putSprite(TextureAtlasSprite p_putSprite_1_) {
/* 664 */     if (this.animatedSprites != null && p_putSprite_1_ != null && p_putSprite_1_.getAnimationIndex() >= 0) {
/* 665 */       this.animatedSprites.set(p_putSprite_1_.getAnimationIndex());
/*     */     }
/*     */     
/* 668 */     if (this.quadSprites != null) {
/* 669 */       int i = this.vertexCount / 4;
/* 670 */       this.quadSprites[i - 1] = p_putSprite_1_;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setSprite(TextureAtlasSprite p_setSprite_1_) {
/* 675 */     if (this.animatedSprites != null && p_setSprite_1_ != null && p_setSprite_1_.getAnimationIndex() >= 0) {
/* 676 */       this.animatedSprites.set(p_setSprite_1_.getAnimationIndex());
/*     */     }
/*     */     
/* 679 */     if (this.quadSprites != null) {
/* 680 */       this.quadSprite = p_setSprite_1_;
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isMultiTexture() {
/* 685 */     return (this.quadSprites != null);
/*     */   }
/*     */   
/*     */   public void drawMultiTexture() {
/* 689 */     if (this.quadSprites != null) {
/* 690 */       int i = Config.getMinecraft().getTextureMapBlocks().getCountRegisteredSprites();
/*     */       
/* 692 */       if (this.drawnIcons.length <= i) {
/* 693 */         this.drawnIcons = new boolean[i + 1];
/*     */       }
/*     */       
/* 696 */       Arrays.fill(this.drawnIcons, false);
/* 697 */       int j = 0;
/* 698 */       int k = -1;
/* 699 */       int l = this.vertexCount / 4;
/*     */       
/* 701 */       for (int i1 = 0; i1 < l; i1++) {
/* 702 */         TextureAtlasSprite textureatlassprite = this.quadSprites[i1];
/*     */         
/* 704 */         if (textureatlassprite != null) {
/* 705 */           int j1 = textureatlassprite.getIndexInMap();
/*     */           
/* 707 */           if (!this.drawnIcons[j1]) {
/* 708 */             if (textureatlassprite == TextureUtils.iconGrassSideOverlay) {
/* 709 */               if (k < 0) {
/* 710 */                 k = i1;
/*     */               }
/*     */             } else {
/* 713 */               i1 = drawForIcon(textureatlassprite, i1) - 1;
/* 714 */               j++;
/*     */               
/* 716 */               if (this.blockLayer != EnumWorldBlockLayer.TRANSLUCENT) {
/* 717 */                 this.drawnIcons[j1] = true;
/*     */               }
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 724 */       if (k >= 0) {
/* 725 */         drawForIcon(TextureUtils.iconGrassSideOverlay, k);
/* 726 */         j++;
/*     */       } 
/*     */       
/* 729 */       if (j > 0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int drawForIcon(TextureAtlasSprite p_drawForIcon_1_, int p_drawForIcon_2_) {
/* 736 */     GL11.glBindTexture(3553, p_drawForIcon_1_.glSpriteTextureId);
/* 737 */     int i = -1;
/* 738 */     int j = -1;
/* 739 */     int k = this.vertexCount / 4;
/*     */     
/* 741 */     for (int l = p_drawForIcon_2_; l < k; l++) {
/* 742 */       TextureAtlasSprite textureatlassprite = this.quadSprites[l];
/*     */       
/* 744 */       if (textureatlassprite == p_drawForIcon_1_) {
/* 745 */         if (j < 0) {
/* 746 */           j = l;
/*     */         }
/* 748 */       } else if (j >= 0) {
/* 749 */         draw(j, l);
/*     */         
/* 751 */         if (this.blockLayer == EnumWorldBlockLayer.TRANSLUCENT) {
/* 752 */           return l;
/*     */         }
/*     */         
/* 755 */         j = -1;
/*     */         
/* 757 */         if (i < 0) {
/* 758 */           i = l;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 763 */     if (j >= 0) {
/* 764 */       draw(j, k);
/*     */     }
/*     */     
/* 767 */     if (i < 0) {
/* 768 */       i = k;
/*     */     }
/*     */     
/* 771 */     return i;
/*     */   }
/*     */   
/*     */   private void draw(int p_draw_1_, int p_draw_2_) {
/* 775 */     int i = p_draw_2_ - p_draw_1_;
/*     */     
/* 777 */     if (i > 0) {
/* 778 */       int j = p_draw_1_ * 4;
/* 779 */       int k = i * 4;
/* 780 */       GL11.glDrawArrays(this.drawMode, j, k);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setBlockLayer(EnumWorldBlockLayer p_setBlockLayer_1_) {
/* 785 */     this.blockLayer = p_setBlockLayer_1_;
/*     */     
/* 787 */     if (p_setBlockLayer_1_ == null) {
/* 788 */       if (this.quadSprites != null) {
/* 789 */         this.quadSpritesPrev = this.quadSprites;
/*     */       }
/*     */       
/* 792 */       this.quadSprites = null;
/* 793 */       this.quadSprite = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private int getBufferQuadSize() {
/* 798 */     int i = this.rawIntBuffer.capacity() * 4 / this.vertexFormat.getIntegerSize() * 4;
/* 799 */     return i;
/*     */   }
/*     */   
/*     */   public RenderEnv getRenderEnv(IBlockState p_getRenderEnv_1_, BlockPos p_getRenderEnv_2_) {
/* 803 */     if (this.renderEnv == null) {
/* 804 */       this.renderEnv = new RenderEnv(p_getRenderEnv_1_, p_getRenderEnv_2_);
/* 805 */       return this.renderEnv;
/*     */     } 
/* 807 */     this.renderEnv.reset(p_getRenderEnv_1_, p_getRenderEnv_2_);
/* 808 */     return this.renderEnv;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDrawing() {
/* 813 */     return this.isDrawing;
/*     */   }
/*     */   
/*     */   public double getXOffset() {
/* 817 */     return this.xOffset;
/*     */   }
/*     */   
/*     */   public double getYOffset() {
/* 821 */     return this.yOffset;
/*     */   }
/*     */   
/*     */   public double getZOffset() {
/* 825 */     return this.zOffset;
/*     */   }
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 829 */     return this.blockLayer;
/*     */   }
/*     */   
/*     */   public void putColorMultiplierRgba(float p_putColorMultiplierRgba_1_, float p_putColorMultiplierRgba_2_, float p_putColorMultiplierRgba_3_, float p_putColorMultiplierRgba_4_, int p_putColorMultiplierRgba_5_) {
/* 833 */     int i = getColorIndex(p_putColorMultiplierRgba_5_);
/* 834 */     int j = -1;
/*     */     
/* 836 */     if (!this.noColor) {
/* 837 */       j = this.rawIntBuffer.get(i);
/*     */       
/* 839 */       if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
/* 840 */         int k = (int)((j & 0xFF) * p_putColorMultiplierRgba_1_);
/* 841 */         int l = (int)((j >> 8 & 0xFF) * p_putColorMultiplierRgba_2_);
/* 842 */         int i1 = (int)((j >> 16 & 0xFF) * p_putColorMultiplierRgba_3_);
/* 843 */         int j1 = (int)((j >> 24 & 0xFF) * p_putColorMultiplierRgba_4_);
/* 844 */         j = j1 << 24 | i1 << 16 | l << 8 | k;
/*     */       } else {
/* 846 */         int k1 = (int)((j >> 24 & 0xFF) * p_putColorMultiplierRgba_1_);
/* 847 */         int l1 = (int)((j >> 16 & 0xFF) * p_putColorMultiplierRgba_2_);
/* 848 */         int i2 = (int)((j >> 8 & 0xFF) * p_putColorMultiplierRgba_3_);
/* 849 */         int j2 = (int)((j & 0xFF) * p_putColorMultiplierRgba_4_);
/* 850 */         j = k1 << 24 | l1 << 16 | i2 << 8 | j2;
/*     */       } 
/*     */     } 
/*     */     
/* 854 */     this.rawIntBuffer.put(i, j);
/*     */   }
/*     */   
/*     */   public void quadsToTriangles() {
/* 858 */     if (this.drawMode == 7) {
/* 859 */       if (this.byteBufferTriangles == null) {
/* 860 */         this.byteBufferTriangles = GLAllocation.createDirectByteBuffer(this.byteBuffer.capacity() * 2);
/*     */       }
/*     */       
/* 863 */       if (this.byteBufferTriangles.capacity() < this.byteBuffer.capacity() * 2) {
/* 864 */         this.byteBufferTriangles = GLAllocation.createDirectByteBuffer(this.byteBuffer.capacity() * 2);
/*     */       }
/*     */       
/* 867 */       int i = this.vertexFormat.getNextOffset();
/* 868 */       int j = this.byteBuffer.limit();
/* 869 */       this.byteBuffer.rewind();
/* 870 */       this.byteBufferTriangles.clear();
/*     */       
/* 872 */       for (int k = 0; k < this.vertexCount; k += 4) {
/* 873 */         this.byteBuffer.limit((k + 3) * i);
/* 874 */         this.byteBuffer.position(k * i);
/* 875 */         this.byteBufferTriangles.put(this.byteBuffer);
/* 876 */         this.byteBuffer.limit((k + 1) * i);
/* 877 */         this.byteBuffer.position(k * i);
/* 878 */         this.byteBufferTriangles.put(this.byteBuffer);
/* 879 */         this.byteBuffer.limit((k + 2 + 2) * i);
/* 880 */         this.byteBuffer.position((k + 2) * i);
/* 881 */         this.byteBufferTriangles.put(this.byteBuffer);
/*     */       } 
/*     */       
/* 884 */       this.byteBuffer.limit(j);
/* 885 */       this.byteBuffer.rewind();
/* 886 */       this.byteBufferTriangles.flip();
/* 887 */       this.modeTriangles = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isColorDisabled() {
/* 892 */     return this.noColor;
/*     */   }
/*     */   
/*     */   public class State {
/*     */     private final int[] stateRawBuffer;
/*     */     private final VertexFormat stateVertexFormat;
/*     */     private TextureAtlasSprite[] stateQuadSprites;
/*     */     
/*     */     public State(int[] p_i1_2_, VertexFormat p_i1_3_, TextureAtlasSprite[] p_i1_4_) {
/* 901 */       this.stateRawBuffer = p_i1_2_;
/* 902 */       this.stateVertexFormat = p_i1_3_;
/* 903 */       this.stateQuadSprites = p_i1_4_;
/*     */     }
/*     */     
/*     */     public State(int[] buffer, VertexFormat format) {
/* 907 */       this.stateRawBuffer = buffer;
/* 908 */       this.stateVertexFormat = format;
/*     */     }
/*     */     
/*     */     public int[] getRawBuffer() {
/* 912 */       return this.stateRawBuffer;
/*     */     }
/*     */     
/*     */     public int getVertexCount() {
/* 916 */       return this.stateRawBuffer.length / this.stateVertexFormat.getIntegerSize();
/*     */     }
/*     */     
/*     */     public VertexFormat getVertexFormat() {
/* 920 */       return this.stateVertexFormat;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\WorldRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */