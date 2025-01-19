/*     */ package net.optifine.shaders;
/*     */ 
/*     */ import net.minecraft.client.renderer.culling.ClippingHelper;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ public class ClippingHelperShadow extends ClippingHelper {
/*   7 */   private static ClippingHelperShadow instance = new ClippingHelperShadow();
/*   8 */   float[] frustumTest = new float[6];
/*   9 */   float[][] shadowClipPlanes = new float[10][4];
/*     */   int shadowClipPlaneCount;
/*  11 */   float[] matInvMP = new float[16];
/*  12 */   float[] vecIntersection = new float[4];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBoxInFrustum(double x1, double y1, double z1, double x2, double y2, double z2) {
/*  18 */     for (int i = 0; i < this.shadowClipPlaneCount; i++) {
/*  19 */       float[] afloat = this.shadowClipPlanes[i];
/*     */       
/*  21 */       if (dot4(afloat, x1, y1, z1) <= 0.0D && dot4(afloat, x2, y1, z1) <= 0.0D && dot4(afloat, x1, y2, z1) <= 0.0D && dot4(afloat, x2, y2, z1) <= 0.0D && dot4(afloat, x1, y1, z2) <= 0.0D && dot4(afloat, x2, y1, z2) <= 0.0D && dot4(afloat, x1, y2, z2) <= 0.0D && dot4(afloat, x2, y2, z2) <= 0.0D) {
/*  22 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  26 */     return true;
/*     */   }
/*     */   
/*     */   private double dot4(float[] plane, double x, double y, double z) {
/*  30 */     return plane[0] * x + plane[1] * y + plane[2] * z + plane[3];
/*     */   }
/*     */   
/*     */   private double dot3(float[] vecA, float[] vecB) {
/*  34 */     return vecA[0] * vecB[0] + vecA[1] * vecB[1] + vecA[2] * vecB[2];
/*     */   }
/*     */   
/*     */   public static ClippingHelper getInstance() {
/*  38 */     instance.init();
/*  39 */     return instance;
/*     */   }
/*     */   
/*     */   private void normalizePlane(float[] plane) {
/*  43 */     float f = MathHelper.sqrt_float(plane[0] * plane[0] + plane[1] * plane[1] + plane[2] * plane[2]);
/*  44 */     plane[0] = plane[0] / f;
/*  45 */     plane[1] = plane[1] / f;
/*  46 */     plane[2] = plane[2] / f;
/*  47 */     plane[3] = plane[3] / f;
/*     */   }
/*     */   
/*     */   private void normalize3(float[] plane) {
/*  51 */     float f = MathHelper.sqrt_float(plane[0] * plane[0] + plane[1] * plane[1] + plane[2] * plane[2]);
/*     */     
/*  53 */     if (f == 0.0F) {
/*  54 */       f = 1.0F;
/*     */     }
/*     */     
/*  57 */     plane[0] = plane[0] / f;
/*  58 */     plane[1] = plane[1] / f;
/*  59 */     plane[2] = plane[2] / f;
/*     */   }
/*     */   
/*     */   private void assignPlane(float[] plane, float a, float b, float c, float d) {
/*  63 */     float f = (float)Math.sqrt((a * a + b * b + c * c));
/*  64 */     plane[0] = a / f;
/*  65 */     plane[1] = b / f;
/*  66 */     plane[2] = c / f;
/*  67 */     plane[3] = d / f;
/*     */   }
/*     */   
/*     */   private void copyPlane(float[] dst, float[] src) {
/*  71 */     dst[0] = src[0];
/*  72 */     dst[1] = src[1];
/*  73 */     dst[2] = src[2];
/*  74 */     dst[3] = src[3];
/*     */   }
/*     */   
/*     */   private void cross3(float[] out, float[] a, float[] b) {
/*  78 */     out[0] = a[1] * b[2] - a[2] * b[1];
/*  79 */     out[1] = a[2] * b[0] - a[0] * b[2];
/*  80 */     out[2] = a[0] * b[1] - a[1] * b[0];
/*     */   }
/*     */   
/*     */   private void addShadowClipPlane(float[] plane) {
/*  84 */     copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], plane);
/*     */   }
/*     */   
/*     */   private float length(float x, float y, float z) {
/*  88 */     return (float)Math.sqrt((x * x + y * y + z * z));
/*     */   }
/*     */   
/*     */   private float distance(float x1, float y1, float z1, float x2, float y2, float z2) {
/*  92 */     return length(x1 - x2, y1 - y2, z1 - z2);
/*     */   }
/*     */   
/*     */   private void makeShadowPlane(float[] shadowPlane, float[] positivePlane, float[] negativePlane, float[] vecSun) {
/*  96 */     cross3(this.vecIntersection, positivePlane, negativePlane);
/*  97 */     cross3(shadowPlane, this.vecIntersection, vecSun);
/*  98 */     normalize3(shadowPlane);
/*  99 */     float f = (float)dot3(positivePlane, negativePlane);
/* 100 */     float f1 = (float)dot3(shadowPlane, negativePlane);
/* 101 */     float f2 = distance(shadowPlane[0], shadowPlane[1], shadowPlane[2], negativePlane[0] * f1, negativePlane[1] * f1, negativePlane[2] * f1);
/* 102 */     float f3 = distance(positivePlane[0], positivePlane[1], positivePlane[2], negativePlane[0] * f, negativePlane[1] * f, negativePlane[2] * f);
/* 103 */     float f4 = f2 / f3;
/* 104 */     float f5 = (float)dot3(shadowPlane, positivePlane);
/* 105 */     float f6 = distance(shadowPlane[0], shadowPlane[1], shadowPlane[2], positivePlane[0] * f5, positivePlane[1] * f5, positivePlane[2] * f5);
/* 106 */     float f7 = distance(negativePlane[0], negativePlane[1], negativePlane[2], positivePlane[0] * f, positivePlane[1] * f, positivePlane[2] * f);
/* 107 */     float f8 = f6 / f7;
/* 108 */     shadowPlane[3] = positivePlane[3] * f4 + negativePlane[3] * f8;
/*     */   }
/*     */   
/*     */   public void init() {
/* 112 */     float[] afloat = this.projectionMatrix;
/* 113 */     float[] afloat1 = this.modelviewMatrix;
/* 114 */     float[] afloat2 = this.clippingMatrix;
/* 115 */     System.arraycopy(Shaders.faProjection, 0, afloat, 0, 16);
/* 116 */     System.arraycopy(Shaders.faModelView, 0, afloat1, 0, 16);
/* 117 */     SMath.multiplyMat4xMat4(afloat2, afloat1, afloat);
/* 118 */     assignPlane(this.frustum[0], afloat2[3] - afloat2[0], afloat2[7] - afloat2[4], afloat2[11] - afloat2[8], afloat2[15] - afloat2[12]);
/* 119 */     assignPlane(this.frustum[1], afloat2[3] + afloat2[0], afloat2[7] + afloat2[4], afloat2[11] + afloat2[8], afloat2[15] + afloat2[12]);
/* 120 */     assignPlane(this.frustum[2], afloat2[3] + afloat2[1], afloat2[7] + afloat2[5], afloat2[11] + afloat2[9], afloat2[15] + afloat2[13]);
/* 121 */     assignPlane(this.frustum[3], afloat2[3] - afloat2[1], afloat2[7] - afloat2[5], afloat2[11] - afloat2[9], afloat2[15] - afloat2[13]);
/* 122 */     assignPlane(this.frustum[4], afloat2[3] - afloat2[2], afloat2[7] - afloat2[6], afloat2[11] - afloat2[10], afloat2[15] - afloat2[14]);
/* 123 */     assignPlane(this.frustum[5], afloat2[3] + afloat2[2], afloat2[7] + afloat2[6], afloat2[11] + afloat2[10], afloat2[15] + afloat2[14]);
/* 124 */     float[] afloat3 = Shaders.shadowLightPositionVector;
/* 125 */     float f = (float)dot3(this.frustum[0], afloat3);
/* 126 */     float f1 = (float)dot3(this.frustum[1], afloat3);
/* 127 */     float f2 = (float)dot3(this.frustum[2], afloat3);
/* 128 */     float f3 = (float)dot3(this.frustum[3], afloat3);
/* 129 */     float f4 = (float)dot3(this.frustum[4], afloat3);
/* 130 */     float f5 = (float)dot3(this.frustum[5], afloat3);
/* 131 */     this.shadowClipPlaneCount = 0;
/*     */     
/* 133 */     if (f >= 0.0F) {
/* 134 */       copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[0]);
/*     */       
/* 136 */       if (f > 0.0F) {
/* 137 */         if (f2 < 0.0F) {
/* 138 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[0], this.frustum[2], afloat3);
/*     */         }
/*     */         
/* 141 */         if (f3 < 0.0F) {
/* 142 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[0], this.frustum[3], afloat3);
/*     */         }
/*     */         
/* 145 */         if (f4 < 0.0F) {
/* 146 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[0], this.frustum[4], afloat3);
/*     */         }
/*     */         
/* 149 */         if (f5 < 0.0F) {
/* 150 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[0], this.frustum[5], afloat3);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 155 */     if (f1 >= 0.0F) {
/* 156 */       copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[1]);
/*     */       
/* 158 */       if (f1 > 0.0F) {
/* 159 */         if (f2 < 0.0F) {
/* 160 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[1], this.frustum[2], afloat3);
/*     */         }
/*     */         
/* 163 */         if (f3 < 0.0F) {
/* 164 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[1], this.frustum[3], afloat3);
/*     */         }
/*     */         
/* 167 */         if (f4 < 0.0F) {
/* 168 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[1], this.frustum[4], afloat3);
/*     */         }
/*     */         
/* 171 */         if (f5 < 0.0F) {
/* 172 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[1], this.frustum[5], afloat3);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 177 */     if (f2 >= 0.0F) {
/* 178 */       copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[2]);
/*     */       
/* 180 */       if (f2 > 0.0F) {
/* 181 */         if (f < 0.0F) {
/* 182 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[2], this.frustum[0], afloat3);
/*     */         }
/*     */         
/* 185 */         if (f1 < 0.0F) {
/* 186 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[2], this.frustum[1], afloat3);
/*     */         }
/*     */         
/* 189 */         if (f4 < 0.0F) {
/* 190 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[2], this.frustum[4], afloat3);
/*     */         }
/*     */         
/* 193 */         if (f5 < 0.0F) {
/* 194 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[2], this.frustum[5], afloat3);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 199 */     if (f3 >= 0.0F) {
/* 200 */       copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[3]);
/*     */       
/* 202 */       if (f3 > 0.0F) {
/* 203 */         if (f < 0.0F) {
/* 204 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[3], this.frustum[0], afloat3);
/*     */         }
/*     */         
/* 207 */         if (f1 < 0.0F) {
/* 208 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[3], this.frustum[1], afloat3);
/*     */         }
/*     */         
/* 211 */         if (f4 < 0.0F) {
/* 212 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[3], this.frustum[4], afloat3);
/*     */         }
/*     */         
/* 215 */         if (f5 < 0.0F) {
/* 216 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[3], this.frustum[5], afloat3);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 221 */     if (f4 >= 0.0F) {
/* 222 */       copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[4]);
/*     */       
/* 224 */       if (f4 > 0.0F) {
/* 225 */         if (f < 0.0F) {
/* 226 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[4], this.frustum[0], afloat3);
/*     */         }
/*     */         
/* 229 */         if (f1 < 0.0F) {
/* 230 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[4], this.frustum[1], afloat3);
/*     */         }
/*     */         
/* 233 */         if (f2 < 0.0F) {
/* 234 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[4], this.frustum[2], afloat3);
/*     */         }
/*     */         
/* 237 */         if (f3 < 0.0F) {
/* 238 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[4], this.frustum[3], afloat3);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 243 */     if (f5 >= 0.0F) {
/* 244 */       copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[5]);
/*     */       
/* 246 */       if (f5 > 0.0F) {
/* 247 */         if (f < 0.0F) {
/* 248 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[5], this.frustum[0], afloat3);
/*     */         }
/*     */         
/* 251 */         if (f1 < 0.0F) {
/* 252 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[5], this.frustum[1], afloat3);
/*     */         }
/*     */         
/* 255 */         if (f2 < 0.0F) {
/* 256 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[5], this.frustum[2], afloat3);
/*     */         }
/*     */         
/* 259 */         if (f3 < 0.0F)
/* 260 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[5], this.frustum[3], afloat3); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\ClippingHelperShadow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */