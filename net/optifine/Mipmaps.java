/*     */ package net.optifine;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.GLAllocation;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.util.TextureUtils;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ public class Mipmaps
/*     */ {
/*     */   private final String iconName;
/*     */   private final int width;
/*     */   private final int height;
/*     */   private final int[] data;
/*     */   private final boolean direct;
/*     */   private int[][] mipmapDatas;
/*     */   private IntBuffer[] mipmapBuffers;
/*     */   private Dimension[] mipmapDimensions;
/*     */   
/*     */   public Mipmaps(String iconName, int width, int height, int[] data, boolean direct) {
/*  25 */     this.iconName = iconName;
/*  26 */     this.width = width;
/*  27 */     this.height = height;
/*  28 */     this.data = data;
/*  29 */     this.direct = direct;
/*  30 */     this.mipmapDimensions = makeMipmapDimensions(width, height, iconName);
/*  31 */     this.mipmapDatas = generateMipMapData(data, width, height, this.mipmapDimensions);
/*     */     
/*  33 */     if (direct) {
/*  34 */       this.mipmapBuffers = makeMipmapBuffers(this.mipmapDimensions, this.mipmapDatas);
/*     */     }
/*     */   }
/*     */   
/*     */   public static Dimension[] makeMipmapDimensions(int width, int height, String iconName) {
/*  39 */     int i = TextureUtils.ceilPowerOfTwo(width);
/*  40 */     int j = TextureUtils.ceilPowerOfTwo(height);
/*     */     
/*  42 */     if (i == width && j == height) {
/*  43 */       List<Dimension> list = new ArrayList();
/*  44 */       int k = i;
/*  45 */       int l = j;
/*     */       
/*     */       while (true) {
/*  48 */         k /= 2;
/*  49 */         l /= 2;
/*     */         
/*  51 */         if (k <= 0 && l <= 0) {
/*  52 */           Dimension[] adimension = (Dimension[])list.toArray((Object[])new Dimension[list.size()]);
/*  53 */           return adimension;
/*     */         } 
/*     */         
/*  56 */         if (k <= 0) {
/*  57 */           k = 1;
/*     */         }
/*     */         
/*  60 */         if (l <= 0) {
/*  61 */           l = 1;
/*     */         }
/*     */         
/*  64 */         int i1 = k * l * 4;
/*  65 */         Dimension dimension = new Dimension(k, l);
/*  66 */         list.add(dimension);
/*     */       } 
/*     */     } 
/*  69 */     Config.warn("Mipmaps not possible (power of 2 dimensions needed), texture: " + iconName + ", dim: " + width + "x" + height);
/*  70 */     return new Dimension[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public static int[][] generateMipMapData(int[] data, int width, int height, Dimension[] mipmapDimensions) {
/*  75 */     int[] aint = data;
/*  76 */     int i = width;
/*  77 */     boolean flag = true;
/*  78 */     int[][] aint1 = new int[mipmapDimensions.length][];
/*     */     
/*  80 */     for (int j = 0; j < mipmapDimensions.length; j++) {
/*  81 */       Dimension dimension = mipmapDimensions[j];
/*  82 */       int k = dimension.width;
/*  83 */       int l = dimension.height;
/*  84 */       int[] aint2 = new int[k * l];
/*  85 */       aint1[j] = aint2;
/*  86 */       int i1 = j + 1;
/*     */       
/*  88 */       if (flag) {
/*  89 */         for (int j1 = 0; j1 < k; j1++) {
/*  90 */           for (int k1 = 0; k1 < l; k1++) {
/*  91 */             int l1 = aint[j1 * 2 + 0 + (k1 * 2 + 0) * i];
/*  92 */             int i2 = aint[j1 * 2 + 1 + (k1 * 2 + 0) * i];
/*  93 */             int j2 = aint[j1 * 2 + 1 + (k1 * 2 + 1) * i];
/*  94 */             int k2 = aint[j1 * 2 + 0 + (k1 * 2 + 1) * i];
/*  95 */             int l2 = alphaBlend(l1, i2, j2, k2);
/*  96 */             aint2[j1 + k1 * k] = l2;
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/* 101 */       aint = aint2;
/* 102 */       i = k;
/*     */       
/* 104 */       if (k <= 1 || l <= 1) {
/* 105 */         flag = false;
/*     */       }
/*     */     } 
/*     */     
/* 109 */     return aint1;
/*     */   }
/*     */   
/*     */   public static int alphaBlend(int c1, int c2, int c3, int c4) {
/* 113 */     int i = alphaBlend(c1, c2);
/* 114 */     int j = alphaBlend(c3, c4);
/* 115 */     int k = alphaBlend(i, j);
/* 116 */     return k;
/*     */   }
/*     */   
/*     */   private static int alphaBlend(int c1, int c2) {
/* 120 */     int i = (c1 & 0xFF000000) >> 24 & 0xFF;
/* 121 */     int j = (c2 & 0xFF000000) >> 24 & 0xFF;
/* 122 */     int k = (i + j) / 2;
/*     */     
/* 124 */     if (i == 0 && j == 0) {
/* 125 */       i = 1;
/* 126 */       j = 1;
/*     */     } else {
/* 128 */       if (i == 0) {
/* 129 */         c1 = c2;
/* 130 */         k /= 2;
/*     */       } 
/*     */       
/* 133 */       if (j == 0) {
/* 134 */         c2 = c1;
/* 135 */         k /= 2;
/*     */       } 
/*     */     } 
/*     */     
/* 139 */     int l = (c1 >> 16 & 0xFF) * i;
/* 140 */     int i1 = (c1 >> 8 & 0xFF) * i;
/* 141 */     int j1 = (c1 & 0xFF) * i;
/* 142 */     int k1 = (c2 >> 16 & 0xFF) * j;
/* 143 */     int l1 = (c2 >> 8 & 0xFF) * j;
/* 144 */     int i2 = (c2 & 0xFF) * j;
/* 145 */     int j2 = (l + k1) / (i + j);
/* 146 */     int k2 = (i1 + l1) / (i + j);
/* 147 */     int l2 = (j1 + i2) / (i + j);
/* 148 */     return k << 24 | j2 << 16 | k2 << 8 | l2;
/*     */   }
/*     */   
/*     */   private int averageColor(int i1, int j1) {
/* 152 */     int i = (i1 & 0xFF000000) >> 24 & 0xFF;
/* 153 */     int j = (j1 & 0xFF000000) >> 24 & 0xFF;
/* 154 */     return (i + j >> 1 << 24) + ((i1 & 0xFEFEFE) + (j1 & 0xFEFEFE) >> 1);
/*     */   }
/*     */   
/*     */   public static IntBuffer[] makeMipmapBuffers(Dimension[] mipmapDimensions, int[][] mipmapDatas) {
/* 158 */     if (mipmapDimensions == null) {
/* 159 */       return null;
/*     */     }
/* 161 */     IntBuffer[] aintbuffer = new IntBuffer[mipmapDimensions.length];
/*     */     
/* 163 */     for (int i = 0; i < mipmapDimensions.length; i++) {
/* 164 */       Dimension dimension = mipmapDimensions[i];
/* 165 */       int j = dimension.width * dimension.height;
/* 166 */       IntBuffer intbuffer = GLAllocation.createDirectIntBuffer(j);
/* 167 */       int[] aint = mipmapDatas[i];
/* 168 */       intbuffer.clear();
/* 169 */       intbuffer.put(aint);
/* 170 */       intbuffer.clear();
/* 171 */       aintbuffer[i] = intbuffer;
/*     */     } 
/*     */     
/* 174 */     return aintbuffer;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void allocateMipmapTextures(int width, int height, String name) {
/* 179 */     Dimension[] adimension = makeMipmapDimensions(width, height, name);
/*     */     
/* 181 */     for (int i = 0; i < adimension.length; i++) {
/* 182 */       Dimension dimension = adimension[i];
/* 183 */       int j = dimension.width;
/* 184 */       int k = dimension.height;
/* 185 */       int l = i + 1;
/* 186 */       GL11.glTexImage2D(3553, l, 6408, j, k, 0, 32993, 33639, null);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\Mipmaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */