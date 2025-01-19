/*     */ package net.optifine.shaders;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.BlockStateBase;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.VertexFormat;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import org.lwjgl.opengl.GL20;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SVertexBuilder
/*     */ {
/*     */   int vertexSize;
/*     */   int offsetNormal;
/*     */   int offsetUV;
/*     */   int offsetUVCenter;
/*     */   boolean hasNormal;
/*     */   boolean hasTangent;
/*     */   boolean hasUV;
/*     */   boolean hasUVCenter;
/*  28 */   long[] entityData = new long[10];
/*  29 */   int entityDataIndex = 0;
/*     */   
/*     */   public SVertexBuilder() {
/*  32 */     this.entityData[this.entityDataIndex] = 0L;
/*     */   }
/*     */   
/*     */   public static void initVertexBuilder(WorldRenderer wrr) {
/*  36 */     wrr.sVertexBuilder = new SVertexBuilder();
/*     */   }
/*     */   
/*     */   public void pushEntity(long data) {
/*  40 */     this.entityDataIndex++;
/*  41 */     this.entityData[this.entityDataIndex] = data;
/*     */   }
/*     */   
/*     */   public void popEntity() {
/*  45 */     this.entityData[this.entityDataIndex] = 0L;
/*  46 */     this.entityDataIndex--;
/*     */   }
/*     */   public static void pushEntity(IBlockState blockState, BlockPos blockPos, IBlockAccess blockAccess, WorldRenderer wrr) {
/*     */     int i, j;
/*  50 */     Block block = blockState.getBlock();
/*     */ 
/*     */ 
/*     */     
/*  54 */     if (blockState instanceof BlockStateBase) {
/*  55 */       BlockStateBase blockstatebase = (BlockStateBase)blockState;
/*  56 */       i = blockstatebase.getBlockId();
/*  57 */       j = blockstatebase.getMetadata();
/*     */     } else {
/*  59 */       i = Block.getIdFromBlock(block);
/*  60 */       j = block.getMetaFromState(blockState);
/*     */     } 
/*     */     
/*  63 */     int j1 = BlockAliases.getBlockAliasId(i, j);
/*     */     
/*  65 */     if (j1 >= 0) {
/*  66 */       i = j1;
/*     */     }
/*     */     
/*  69 */     int k = block.getRenderType();
/*  70 */     int l = ((k & 0xFFFF) << 16) + (i & 0xFFFF);
/*  71 */     int i1 = j & 0xFFFF;
/*  72 */     wrr.sVertexBuilder.pushEntity((i1 << 32L) + l);
/*     */   }
/*     */   
/*     */   public static void popEntity(WorldRenderer wrr) {
/*  76 */     wrr.sVertexBuilder.popEntity();
/*     */   }
/*     */   
/*     */   public static boolean popEntity(boolean value, WorldRenderer wrr) {
/*  80 */     wrr.sVertexBuilder.popEntity();
/*  81 */     return value;
/*     */   }
/*     */   
/*     */   public static void endSetVertexFormat(WorldRenderer wrr) {
/*  85 */     SVertexBuilder svertexbuilder = wrr.sVertexBuilder;
/*  86 */     VertexFormat vertexformat = wrr.getVertexFormat();
/*  87 */     svertexbuilder.vertexSize = vertexformat.getNextOffset() / 4;
/*  88 */     svertexbuilder.hasNormal = vertexformat.hasNormal();
/*  89 */     svertexbuilder.hasTangent = svertexbuilder.hasNormal;
/*  90 */     svertexbuilder.hasUV = vertexformat.hasUvOffset(0);
/*  91 */     svertexbuilder.offsetNormal = svertexbuilder.hasNormal ? (vertexformat.getNormalOffset() / 4) : 0;
/*  92 */     svertexbuilder.offsetUV = svertexbuilder.hasUV ? (vertexformat.getUvOffsetById(0) / 4) : 0;
/*  93 */     svertexbuilder.offsetUVCenter = 8;
/*     */   }
/*     */   
/*     */   public static void beginAddVertex(WorldRenderer wrr) {
/*  97 */     if (wrr.vertexCount == 0) {
/*  98 */       endSetVertexFormat(wrr);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void endAddVertex(WorldRenderer wrr) {
/* 103 */     SVertexBuilder svertexbuilder = wrr.sVertexBuilder;
/*     */     
/* 105 */     if (svertexbuilder.vertexSize == 14) {
/* 106 */       if (wrr.drawMode == 7 && wrr.vertexCount % 4 == 0) {
/* 107 */         svertexbuilder.calcNormal(wrr, wrr.getBufferSize() - 4 * svertexbuilder.vertexSize);
/*     */       }
/*     */       
/* 110 */       long i = svertexbuilder.entityData[svertexbuilder.entityDataIndex];
/* 111 */       int j = wrr.getBufferSize() - 14 + 12;
/* 112 */       wrr.rawIntBuffer.put(j, (int)i);
/* 113 */       wrr.rawIntBuffer.put(j + 1, (int)(i >> 32L));
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void beginAddVertexData(WorldRenderer wrr, int[] data) {
/* 118 */     if (wrr.vertexCount == 0) {
/* 119 */       endSetVertexFormat(wrr);
/*     */     }
/*     */     
/* 122 */     SVertexBuilder svertexbuilder = wrr.sVertexBuilder;
/*     */     
/* 124 */     if (svertexbuilder.vertexSize == 14) {
/* 125 */       long i = svertexbuilder.entityData[svertexbuilder.entityDataIndex];
/*     */       
/* 127 */       for (int j = 12; j + 1 < data.length; j += 14) {
/* 128 */         data[j] = (int)i;
/* 129 */         data[j + 1] = (int)(i >> 32L);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void beginAddVertexData(WorldRenderer wrr, ByteBuffer byteBuffer) {
/* 135 */     if (wrr.vertexCount == 0) {
/* 136 */       endSetVertexFormat(wrr);
/*     */     }
/*     */     
/* 139 */     SVertexBuilder svertexbuilder = wrr.sVertexBuilder;
/*     */     
/* 141 */     if (svertexbuilder.vertexSize == 14) {
/* 142 */       long i = svertexbuilder.entityData[svertexbuilder.entityDataIndex];
/* 143 */       int j = byteBuffer.limit() / 4;
/*     */       
/* 145 */       for (int k = 12; k + 1 < j; k += 14) {
/* 146 */         int l = (int)i;
/* 147 */         int i1 = (int)(i >> 32L);
/* 148 */         byteBuffer.putInt(k * 4, l);
/* 149 */         byteBuffer.putInt((k + 1) * 4, i1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void endAddVertexData(WorldRenderer wrr) {
/* 155 */     SVertexBuilder svertexbuilder = wrr.sVertexBuilder;
/*     */     
/* 157 */     if (svertexbuilder.vertexSize == 14 && wrr.drawMode == 7 && wrr.vertexCount % 4 == 0) {
/* 158 */       svertexbuilder.calcNormal(wrr, wrr.getBufferSize() - 4 * svertexbuilder.vertexSize);
/*     */     }
/*     */   }
/*     */   
/*     */   public void calcNormal(WorldRenderer wrr, int baseIndex) {
/* 163 */     FloatBuffer floatbuffer = wrr.rawFloatBuffer;
/* 164 */     IntBuffer intbuffer = wrr.rawIntBuffer;
/* 165 */     int i = wrr.getBufferSize();
/* 166 */     float f = floatbuffer.get(baseIndex + 0 * this.vertexSize);
/* 167 */     float f1 = floatbuffer.get(baseIndex + 0 * this.vertexSize + 1);
/* 168 */     float f2 = floatbuffer.get(baseIndex + 0 * this.vertexSize + 2);
/* 169 */     float f3 = floatbuffer.get(baseIndex + 0 * this.vertexSize + this.offsetUV);
/* 170 */     float f4 = floatbuffer.get(baseIndex + 0 * this.vertexSize + this.offsetUV + 1);
/* 171 */     float f5 = floatbuffer.get(baseIndex + 1 * this.vertexSize);
/* 172 */     float f6 = floatbuffer.get(baseIndex + 1 * this.vertexSize + 1);
/* 173 */     float f7 = floatbuffer.get(baseIndex + 1 * this.vertexSize + 2);
/* 174 */     float f8 = floatbuffer.get(baseIndex + 1 * this.vertexSize + this.offsetUV);
/* 175 */     float f9 = floatbuffer.get(baseIndex + 1 * this.vertexSize + this.offsetUV + 1);
/* 176 */     float f10 = floatbuffer.get(baseIndex + 2 * this.vertexSize);
/* 177 */     float f11 = floatbuffer.get(baseIndex + 2 * this.vertexSize + 1);
/* 178 */     float f12 = floatbuffer.get(baseIndex + 2 * this.vertexSize + 2);
/* 179 */     float f13 = floatbuffer.get(baseIndex + 2 * this.vertexSize + this.offsetUV);
/* 180 */     float f14 = floatbuffer.get(baseIndex + 2 * this.vertexSize + this.offsetUV + 1);
/* 181 */     float f15 = floatbuffer.get(baseIndex + 3 * this.vertexSize);
/* 182 */     float f16 = floatbuffer.get(baseIndex + 3 * this.vertexSize + 1);
/* 183 */     float f17 = floatbuffer.get(baseIndex + 3 * this.vertexSize + 2);
/* 184 */     float f18 = floatbuffer.get(baseIndex + 3 * this.vertexSize + this.offsetUV);
/* 185 */     float f19 = floatbuffer.get(baseIndex + 3 * this.vertexSize + this.offsetUV + 1);
/* 186 */     float f20 = f10 - f;
/* 187 */     float f21 = f11 - f1;
/* 188 */     float f22 = f12 - f2;
/* 189 */     float f23 = f15 - f5;
/* 190 */     float f24 = f16 - f6;
/* 191 */     float f25 = f17 - f7;
/* 192 */     float f30 = f21 * f25 - f24 * f22;
/* 193 */     float f31 = f22 * f23 - f25 * f20;
/* 194 */     float f32 = f20 * f24 - f23 * f21;
/* 195 */     float f33 = f30 * f30 + f31 * f31 + f32 * f32;
/* 196 */     float f34 = (f33 != 0.0D) ? (float)(1.0D / Math.sqrt(f33)) : 1.0F;
/* 197 */     f30 *= f34;
/* 198 */     f31 *= f34;
/* 199 */     f32 *= f34;
/* 200 */     f20 = f5 - f;
/* 201 */     f21 = f6 - f1;
/* 202 */     f22 = f7 - f2;
/* 203 */     float f26 = f8 - f3;
/* 204 */     float f27 = f9 - f4;
/* 205 */     f23 = f10 - f;
/* 206 */     f24 = f11 - f1;
/* 207 */     f25 = f12 - f2;
/* 208 */     float f28 = f13 - f3;
/* 209 */     float f29 = f14 - f4;
/* 210 */     float f35 = f26 * f29 - f28 * f27;
/* 211 */     float f36 = (f35 != 0.0F) ? (1.0F / f35) : 1.0F;
/* 212 */     float f37 = (f29 * f20 - f27 * f23) * f36;
/* 213 */     float f38 = (f29 * f21 - f27 * f24) * f36;
/* 214 */     float f39 = (f29 * f22 - f27 * f25) * f36;
/* 215 */     float f40 = (f26 * f23 - f28 * f20) * f36;
/* 216 */     float f41 = (f26 * f24 - f28 * f21) * f36;
/* 217 */     float f42 = (f26 * f25 - f28 * f22) * f36;
/* 218 */     f33 = f37 * f37 + f38 * f38 + f39 * f39;
/* 219 */     f34 = (f33 != 0.0D) ? (float)(1.0D / Math.sqrt(f33)) : 1.0F;
/* 220 */     f37 *= f34;
/* 221 */     f38 *= f34;
/* 222 */     f39 *= f34;
/* 223 */     f33 = f40 * f40 + f41 * f41 + f42 * f42;
/* 224 */     f34 = (f33 != 0.0D) ? (float)(1.0D / Math.sqrt(f33)) : 1.0F;
/* 225 */     f40 *= f34;
/* 226 */     f41 *= f34;
/* 227 */     f42 *= f34;
/* 228 */     float f43 = f32 * f38 - f31 * f39;
/* 229 */     float f44 = f30 * f39 - f32 * f37;
/* 230 */     float f45 = f31 * f37 - f30 * f38;
/* 231 */     float f46 = (f40 * f43 + f41 * f44 + f42 * f45 < 0.0F) ? -1.0F : 1.0F;
/* 232 */     int j = (int)(f30 * 127.0F) & 0xFF;
/* 233 */     int k = (int)(f31 * 127.0F) & 0xFF;
/* 234 */     int l = (int)(f32 * 127.0F) & 0xFF;
/* 235 */     int i1 = (l << 16) + (k << 8) + j;
/* 236 */     intbuffer.put(baseIndex + 0 * this.vertexSize + this.offsetNormal, i1);
/* 237 */     intbuffer.put(baseIndex + 1 * this.vertexSize + this.offsetNormal, i1);
/* 238 */     intbuffer.put(baseIndex + 2 * this.vertexSize + this.offsetNormal, i1);
/* 239 */     intbuffer.put(baseIndex + 3 * this.vertexSize + this.offsetNormal, i1);
/* 240 */     int j1 = ((int)(f37 * 32767.0F) & 0xFFFF) + (((int)(f38 * 32767.0F) & 0xFFFF) << 16);
/* 241 */     int k1 = ((int)(f39 * 32767.0F) & 0xFFFF) + (((int)(f46 * 32767.0F) & 0xFFFF) << 16);
/* 242 */     intbuffer.put(baseIndex + 0 * this.vertexSize + 10, j1);
/* 243 */     intbuffer.put(baseIndex + 0 * this.vertexSize + 10 + 1, k1);
/* 244 */     intbuffer.put(baseIndex + 1 * this.vertexSize + 10, j1);
/* 245 */     intbuffer.put(baseIndex + 1 * this.vertexSize + 10 + 1, k1);
/* 246 */     intbuffer.put(baseIndex + 2 * this.vertexSize + 10, j1);
/* 247 */     intbuffer.put(baseIndex + 2 * this.vertexSize + 10 + 1, k1);
/* 248 */     intbuffer.put(baseIndex + 3 * this.vertexSize + 10, j1);
/* 249 */     intbuffer.put(baseIndex + 3 * this.vertexSize + 10 + 1, k1);
/* 250 */     float f47 = (f3 + f8 + f13 + f18) / 4.0F;
/* 251 */     float f48 = (f4 + f9 + f14 + f19) / 4.0F;
/* 252 */     floatbuffer.put(baseIndex + 0 * this.vertexSize + 8, f47);
/* 253 */     floatbuffer.put(baseIndex + 0 * this.vertexSize + 8 + 1, f48);
/* 254 */     floatbuffer.put(baseIndex + 1 * this.vertexSize + 8, f47);
/* 255 */     floatbuffer.put(baseIndex + 1 * this.vertexSize + 8 + 1, f48);
/* 256 */     floatbuffer.put(baseIndex + 2 * this.vertexSize + 8, f47);
/* 257 */     floatbuffer.put(baseIndex + 2 * this.vertexSize + 8 + 1, f48);
/* 258 */     floatbuffer.put(baseIndex + 3 * this.vertexSize + 8, f47);
/* 259 */     floatbuffer.put(baseIndex + 3 * this.vertexSize + 8 + 1, f48);
/*     */   }
/*     */   
/*     */   public static void calcNormalChunkLayer(WorldRenderer wrr) {
/* 263 */     if (wrr.getVertexFormat().hasNormal() && wrr.drawMode == 7 && wrr.vertexCount % 4 == 0) {
/* 264 */       SVertexBuilder svertexbuilder = wrr.sVertexBuilder;
/* 265 */       endSetVertexFormat(wrr);
/* 266 */       int i = wrr.vertexCount * svertexbuilder.vertexSize;
/*     */       
/* 268 */       for (int j = 0; j < i; j += svertexbuilder.vertexSize * 4) {
/* 269 */         svertexbuilder.calcNormal(wrr, j);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void drawArrays(int drawMode, int first, int count, WorldRenderer wrr) {
/* 275 */     if (count != 0) {
/* 276 */       VertexFormat vertexformat = wrr.getVertexFormat();
/* 277 */       int i = vertexformat.getNextOffset();
/*     */       
/* 279 */       if (i == 56) {
/* 280 */         ByteBuffer bytebuffer = wrr.getByteBuffer();
/* 281 */         bytebuffer.position(32);
/* 282 */         GL20.glVertexAttribPointer(Shaders.midTexCoordAttrib, 2, 5126, false, i, bytebuffer);
/* 283 */         bytebuffer.position(40);
/* 284 */         GL20.glVertexAttribPointer(Shaders.tangentAttrib, 4, 5122, false, i, bytebuffer);
/* 285 */         bytebuffer.position(48);
/* 286 */         GL20.glVertexAttribPointer(Shaders.entityAttrib, 3, 5122, false, i, bytebuffer);
/* 287 */         bytebuffer.position(0);
/* 288 */         GL20.glEnableVertexAttribArray(Shaders.midTexCoordAttrib);
/* 289 */         GL20.glEnableVertexAttribArray(Shaders.tangentAttrib);
/* 290 */         GL20.glEnableVertexAttribArray(Shaders.entityAttrib);
/* 291 */         GlStateManager.glDrawArrays(drawMode, first, count);
/* 292 */         GL20.glDisableVertexAttribArray(Shaders.midTexCoordAttrib);
/* 293 */         GL20.glDisableVertexAttribArray(Shaders.tangentAttrib);
/* 294 */         GL20.glDisableVertexAttribArray(Shaders.entityAttrib);
/*     */       } else {
/* 296 */         GlStateManager.glDrawArrays(drawMode, first, count);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\SVertexBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */