/*     */ package net.minecraft.client.renderer.block.model;
/*     */ 
/*     */ import net.minecraft.client.renderer.EnumFaceDirection;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.resources.model.ModelRotation;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3i;
/*     */ import net.minecraftforge.client.model.ITransformation;
/*     */ import net.optifine.model.BlockModelUtils;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.shaders.Shaders;
/*     */ import org.lwjgl.util.vector.Matrix4f;
/*     */ import org.lwjgl.util.vector.ReadableVector3f;
/*     */ import org.lwjgl.util.vector.Vector3f;
/*     */ import org.lwjgl.util.vector.Vector4f;
/*     */ 
/*     */ public class FaceBakery {
/*  20 */   private static final float SCALE_ROTATION_22_5 = 1.0F / (float)Math.cos(0.39269909262657166D) - 1.0F;
/*  21 */   private static final float SCALE_ROTATION_GENERAL = 1.0F / (float)Math.cos(0.7853981633974483D) - 1.0F;
/*     */   
/*     */   public BakedQuad makeBakedQuad(Vector3f posFrom, Vector3f posTo, BlockPartFace face, TextureAtlasSprite sprite, EnumFacing facing, ModelRotation modelRotationIn, BlockPartRotation partRotation, boolean uvLocked, boolean shade) {
/*  24 */     return makeBakedQuad(posFrom, posTo, face, sprite, facing, (ITransformation)modelRotationIn, partRotation, uvLocked, shade);
/*     */   }
/*     */   
/*     */   public BakedQuad makeBakedQuad(Vector3f p_makeBakedQuad_1_, Vector3f p_makeBakedQuad_2_, BlockPartFace p_makeBakedQuad_3_, TextureAtlasSprite p_makeBakedQuad_4_, EnumFacing p_makeBakedQuad_5_, ITransformation p_makeBakedQuad_6_, BlockPartRotation p_makeBakedQuad_7_, boolean p_makeBakedQuad_8_, boolean p_makeBakedQuad_9_) {
/*  28 */     int[] aint = makeQuadVertexData(p_makeBakedQuad_3_, p_makeBakedQuad_4_, p_makeBakedQuad_5_, getPositionsDiv16(p_makeBakedQuad_1_, p_makeBakedQuad_2_), p_makeBakedQuad_6_, p_makeBakedQuad_7_, p_makeBakedQuad_8_, p_makeBakedQuad_9_);
/*  29 */     EnumFacing enumfacing = getFacingFromVertexData(aint);
/*     */     
/*  31 */     if (p_makeBakedQuad_8_) {
/*  32 */       lockUv(aint, enumfacing, p_makeBakedQuad_3_.blockFaceUV, p_makeBakedQuad_4_);
/*     */     }
/*     */     
/*  35 */     if (p_makeBakedQuad_7_ == null) {
/*  36 */       applyFacing(aint, enumfacing);
/*     */     }
/*     */     
/*  39 */     if (Reflector.ForgeHooksClient_fillNormal.exists()) {
/*  40 */       Reflector.call(Reflector.ForgeHooksClient_fillNormal, new Object[] { aint, enumfacing });
/*     */     }
/*     */     
/*  43 */     return new BakedQuad(aint, p_makeBakedQuad_3_.tintIndex, enumfacing);
/*     */   }
/*     */   
/*     */   private int[] makeQuadVertexData(BlockPartFace p_makeQuadVertexData_1_, TextureAtlasSprite p_makeQuadVertexData_2_, EnumFacing p_makeQuadVertexData_3_, float[] p_makeQuadVertexData_4_, ITransformation p_makeQuadVertexData_5_, BlockPartRotation p_makeQuadVertexData_6_, boolean p_makeQuadVertexData_7_, boolean p_makeQuadVertexData_8_) {
/*  47 */     int i = 28;
/*     */     
/*  49 */     if (Config.isShaders()) {
/*  50 */       i = 56;
/*     */     }
/*     */     
/*  53 */     int[] aint = new int[i];
/*     */     
/*  55 */     for (int j = 0; j < 4; j++) {
/*  56 */       fillVertexData(aint, j, p_makeQuadVertexData_3_, p_makeQuadVertexData_1_, p_makeQuadVertexData_4_, p_makeQuadVertexData_2_, p_makeQuadVertexData_5_, p_makeQuadVertexData_6_, p_makeQuadVertexData_7_, p_makeQuadVertexData_8_);
/*     */     }
/*     */     
/*  59 */     return aint;
/*     */   }
/*     */   
/*     */   private int getFaceShadeColor(EnumFacing facing) {
/*  63 */     float f = getFaceBrightness(facing);
/*  64 */     int i = MathHelper.clamp_int((int)(f * 255.0F), 0, 255);
/*  65 */     return 0xFF000000 | i << 16 | i << 8 | i;
/*     */   }
/*     */   
/*     */   public static float getFaceBrightness(EnumFacing p_178412_0_) {
/*  69 */     switch (p_178412_0_) {
/*     */       case null:
/*  71 */         if (Config.isShaders()) {
/*  72 */           return Shaders.blockLightLevel05;
/*     */         }
/*     */         
/*  75 */         return 0.5F;
/*     */       
/*     */       case UP:
/*  78 */         return 1.0F;
/*     */       
/*     */       case NORTH:
/*     */       case SOUTH:
/*  82 */         if (Config.isShaders()) {
/*  83 */           return Shaders.blockLightLevel08;
/*     */         }
/*     */         
/*  86 */         return 0.8F;
/*     */       
/*     */       case WEST:
/*     */       case EAST:
/*  90 */         if (Config.isShaders()) {
/*  91 */           return Shaders.blockLightLevel06;
/*     */         }
/*     */         
/*  94 */         return 0.6F;
/*     */     } 
/*     */     
/*  97 */     return 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   private float[] getPositionsDiv16(Vector3f pos1, Vector3f pos2) {
/* 102 */     float[] afloat = new float[(EnumFacing.values()).length];
/* 103 */     afloat[EnumFaceDirection.Constants.WEST_INDEX] = pos1.x / 16.0F;
/* 104 */     afloat[EnumFaceDirection.Constants.DOWN_INDEX] = pos1.y / 16.0F;
/* 105 */     afloat[EnumFaceDirection.Constants.NORTH_INDEX] = pos1.z / 16.0F;
/* 106 */     afloat[EnumFaceDirection.Constants.EAST_INDEX] = pos2.x / 16.0F;
/* 107 */     afloat[EnumFaceDirection.Constants.UP_INDEX] = pos2.y / 16.0F;
/* 108 */     afloat[EnumFaceDirection.Constants.SOUTH_INDEX] = pos2.z / 16.0F;
/* 109 */     return afloat;
/*     */   }
/*     */   
/*     */   private void fillVertexData(int[] p_fillVertexData_1_, int p_fillVertexData_2_, EnumFacing p_fillVertexData_3_, BlockPartFace p_fillVertexData_4_, float[] p_fillVertexData_5_, TextureAtlasSprite p_fillVertexData_6_, ITransformation p_fillVertexData_7_, BlockPartRotation p_fillVertexData_8_, boolean p_fillVertexData_9_, boolean p_fillVertexData_10_) {
/* 113 */     EnumFacing enumfacing = p_fillVertexData_7_.rotate(p_fillVertexData_3_);
/* 114 */     int i = p_fillVertexData_10_ ? getFaceShadeColor(enumfacing) : -1;
/* 115 */     EnumFaceDirection.VertexInformation enumfacedirection$vertexinformation = EnumFaceDirection.getFacing(p_fillVertexData_3_).getVertexInformation(p_fillVertexData_2_);
/* 116 */     Vector3f vector3f = new Vector3f(p_fillVertexData_5_[enumfacedirection$vertexinformation.xIndex], p_fillVertexData_5_[enumfacedirection$vertexinformation.yIndex], p_fillVertexData_5_[enumfacedirection$vertexinformation.zIndex]);
/* 117 */     rotatePart(vector3f, p_fillVertexData_8_);
/* 118 */     int j = rotateVertex(vector3f, p_fillVertexData_3_, p_fillVertexData_2_, p_fillVertexData_7_, p_fillVertexData_9_);
/* 119 */     BlockModelUtils.snapVertexPosition(vector3f);
/* 120 */     storeVertexData(p_fillVertexData_1_, j, p_fillVertexData_2_, vector3f, i, p_fillVertexData_6_, p_fillVertexData_4_.blockFaceUV);
/*     */   }
/*     */   
/*     */   private void storeVertexData(int[] faceData, int storeIndex, int vertexIndex, Vector3f position, int shadeColor, TextureAtlasSprite sprite, BlockFaceUV faceUV) {
/* 124 */     int i = faceData.length / 4;
/* 125 */     int j = storeIndex * i;
/* 126 */     faceData[j] = Float.floatToRawIntBits(position.x);
/* 127 */     faceData[j + 1] = Float.floatToRawIntBits(position.y);
/* 128 */     faceData[j + 2] = Float.floatToRawIntBits(position.z);
/* 129 */     faceData[j + 3] = shadeColor;
/* 130 */     faceData[j + 4] = Float.floatToRawIntBits(sprite.getInterpolatedU(faceUV.func_178348_a(vertexIndex) * 0.999D + faceUV.func_178348_a((vertexIndex + 2) % 4) * 0.001D));
/* 131 */     faceData[j + 4 + 1] = Float.floatToRawIntBits(sprite.getInterpolatedV(faceUV.func_178346_b(vertexIndex) * 0.999D + faceUV.func_178346_b((vertexIndex + 2) % 4) * 0.001D));
/*     */   }
/*     */   
/*     */   private void rotatePart(Vector3f p_178407_1_, BlockPartRotation partRotation) {
/* 135 */     if (partRotation != null) {
/* 136 */       Matrix4f matrix4f = getMatrixIdentity();
/* 137 */       Vector3f vector3f = new Vector3f(0.0F, 0.0F, 0.0F);
/*     */       
/* 139 */       switch (partRotation.axis) {
/*     */         case null:
/* 141 */           Matrix4f.rotate(partRotation.angle * 0.017453292F, new Vector3f(1.0F, 0.0F, 0.0F), matrix4f, matrix4f);
/* 142 */           vector3f.set(0.0F, 1.0F, 1.0F);
/*     */           break;
/*     */         
/*     */         case Y:
/* 146 */           Matrix4f.rotate(partRotation.angle * 0.017453292F, new Vector3f(0.0F, 1.0F, 0.0F), matrix4f, matrix4f);
/* 147 */           vector3f.set(1.0F, 0.0F, 1.0F);
/*     */           break;
/*     */         
/*     */         case Z:
/* 151 */           Matrix4f.rotate(partRotation.angle * 0.017453292F, new Vector3f(0.0F, 0.0F, 1.0F), matrix4f, matrix4f);
/* 152 */           vector3f.set(1.0F, 1.0F, 0.0F); break;
/* 153 */       }  if (partRotation
/*     */         
/* 155 */         .rescale) {
/* 156 */         if (Math.abs(partRotation.angle) == 22.5F) {
/* 157 */           vector3f.scale(SCALE_ROTATION_22_5);
/*     */         } else {
/* 159 */           vector3f.scale(SCALE_ROTATION_GENERAL);
/*     */         } 
/*     */         
/* 162 */         Vector3f.add(vector3f, new Vector3f(1.0F, 1.0F, 1.0F), vector3f);
/*     */       } else {
/* 164 */         vector3f.set(1.0F, 1.0F, 1.0F);
/*     */       } 
/*     */       
/* 167 */       rotateScale(p_178407_1_, new Vector3f((ReadableVector3f)partRotation.origin), matrix4f, vector3f);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int rotateVertex(Vector3f position, EnumFacing facing, int vertexIndex, ModelRotation modelRotationIn, boolean uvLocked) {
/* 172 */     return rotateVertex(position, facing, vertexIndex, modelRotationIn, uvLocked);
/*     */   }
/*     */   
/*     */   public int rotateVertex(Vector3f p_rotateVertex_1_, EnumFacing p_rotateVertex_2_, int p_rotateVertex_3_, ITransformation p_rotateVertex_4_, boolean p_rotateVertex_5_) {
/* 176 */     if (p_rotateVertex_4_ == ModelRotation.X0_Y0) {
/* 177 */       return p_rotateVertex_3_;
/*     */     }
/* 179 */     if (Reflector.ForgeHooksClient_transform.exists()) {
/* 180 */       Reflector.call(Reflector.ForgeHooksClient_transform, new Object[] { p_rotateVertex_1_, p_rotateVertex_4_.getMatrix() });
/*     */     } else {
/* 182 */       rotateScale(p_rotateVertex_1_, new Vector3f(0.5F, 0.5F, 0.5F), ((ModelRotation)p_rotateVertex_4_).getMatrix4d(), new Vector3f(1.0F, 1.0F, 1.0F));
/*     */     } 
/*     */     
/* 185 */     return p_rotateVertex_4_.rotate(p_rotateVertex_2_, p_rotateVertex_3_);
/*     */   }
/*     */ 
/*     */   
/*     */   private void rotateScale(Vector3f position, Vector3f rotationOrigin, Matrix4f rotationMatrix, Vector3f scale) {
/* 190 */     Vector4f vector4f = new Vector4f(position.x - rotationOrigin.x, position.y - rotationOrigin.y, position.z - rotationOrigin.z, 1.0F);
/* 191 */     Matrix4f.transform(rotationMatrix, vector4f, vector4f);
/* 192 */     vector4f.x *= scale.x;
/* 193 */     vector4f.y *= scale.y;
/* 194 */     vector4f.z *= scale.z;
/* 195 */     position.set(vector4f.x + rotationOrigin.x, vector4f.y + rotationOrigin.y, vector4f.z + rotationOrigin.z);
/*     */   }
/*     */   
/*     */   private Matrix4f getMatrixIdentity() {
/* 199 */     Matrix4f matrix4f = new Matrix4f();
/* 200 */     matrix4f.setIdentity();
/* 201 */     return matrix4f;
/*     */   }
/*     */   
/*     */   public static EnumFacing getFacingFromVertexData(int[] faceData) {
/* 205 */     int i = faceData.length / 4;
/* 206 */     int j = i * 2;
/* 207 */     int k = i * 3;
/* 208 */     Vector3f vector3f = new Vector3f(Float.intBitsToFloat(faceData[0]), Float.intBitsToFloat(faceData[1]), Float.intBitsToFloat(faceData[2]));
/* 209 */     Vector3f vector3f1 = new Vector3f(Float.intBitsToFloat(faceData[i]), Float.intBitsToFloat(faceData[i + 1]), Float.intBitsToFloat(faceData[i + 2]));
/* 210 */     Vector3f vector3f2 = new Vector3f(Float.intBitsToFloat(faceData[j]), Float.intBitsToFloat(faceData[j + 1]), Float.intBitsToFloat(faceData[j + 2]));
/* 211 */     Vector3f vector3f3 = new Vector3f();
/* 212 */     Vector3f vector3f4 = new Vector3f();
/* 213 */     Vector3f vector3f5 = new Vector3f();
/* 214 */     Vector3f.sub(vector3f, vector3f1, vector3f3);
/* 215 */     Vector3f.sub(vector3f2, vector3f1, vector3f4);
/* 216 */     Vector3f.cross(vector3f4, vector3f3, vector3f5);
/* 217 */     float f = (float)Math.sqrt((vector3f5.x * vector3f5.x + vector3f5.y * vector3f5.y + vector3f5.z * vector3f5.z));
/* 218 */     vector3f5.x /= f;
/* 219 */     vector3f5.y /= f;
/* 220 */     vector3f5.z /= f;
/* 221 */     EnumFacing enumfacing = null;
/* 222 */     float f1 = 0.0F; byte b; int m;
/*     */     EnumFacing[] arrayOfEnumFacing;
/* 224 */     for (m = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < m; ) { EnumFacing enumfacing1 = arrayOfEnumFacing[b];
/* 225 */       Vec3i vec3i = enumfacing1.getDirectionVec();
/* 226 */       Vector3f vector3f6 = new Vector3f(vec3i.getX(), vec3i.getY(), vec3i.getZ());
/* 227 */       float f2 = Vector3f.dot(vector3f5, vector3f6);
/*     */       
/* 229 */       if (f2 >= 0.0F && f2 > f1) {
/* 230 */         f1 = f2;
/* 231 */         enumfacing = enumfacing1;
/*     */       } 
/*     */       b++; }
/*     */     
/* 235 */     if (enumfacing == null) {
/* 236 */       return EnumFacing.UP;
/*     */     }
/* 238 */     return enumfacing;
/*     */   }
/*     */ 
/*     */   
/*     */   public void lockUv(int[] p_178409_1_, EnumFacing facing, BlockFaceUV p_178409_3_, TextureAtlasSprite p_178409_4_) {
/* 243 */     for (int i = 0; i < 4; i++) {
/* 244 */       lockVertexUv(i, p_178409_1_, facing, p_178409_3_, p_178409_4_);
/*     */     }
/*     */   }
/*     */   
/*     */   private void applyFacing(int[] p_178408_1_, EnumFacing p_178408_2_) {
/* 249 */     int[] aint = new int[p_178408_1_.length];
/* 250 */     System.arraycopy(p_178408_1_, 0, aint, 0, p_178408_1_.length);
/* 251 */     float[] afloat = new float[(EnumFacing.values()).length];
/* 252 */     afloat[EnumFaceDirection.Constants.WEST_INDEX] = 999.0F;
/* 253 */     afloat[EnumFaceDirection.Constants.DOWN_INDEX] = 999.0F;
/* 254 */     afloat[EnumFaceDirection.Constants.NORTH_INDEX] = 999.0F;
/* 255 */     afloat[EnumFaceDirection.Constants.EAST_INDEX] = -999.0F;
/* 256 */     afloat[EnumFaceDirection.Constants.UP_INDEX] = -999.0F;
/* 257 */     afloat[EnumFaceDirection.Constants.SOUTH_INDEX] = -999.0F;
/* 258 */     int i = p_178408_1_.length / 4;
/*     */     
/* 260 */     for (int j = 0; j < 4; j++) {
/* 261 */       int k = i * j;
/* 262 */       float f = Float.intBitsToFloat(aint[k]);
/* 263 */       float f1 = Float.intBitsToFloat(aint[k + 1]);
/* 264 */       float f2 = Float.intBitsToFloat(aint[k + 2]);
/*     */       
/* 266 */       if (f < afloat[EnumFaceDirection.Constants.WEST_INDEX]) {
/* 267 */         afloat[EnumFaceDirection.Constants.WEST_INDEX] = f;
/*     */       }
/*     */       
/* 270 */       if (f1 < afloat[EnumFaceDirection.Constants.DOWN_INDEX]) {
/* 271 */         afloat[EnumFaceDirection.Constants.DOWN_INDEX] = f1;
/*     */       }
/*     */       
/* 274 */       if (f2 < afloat[EnumFaceDirection.Constants.NORTH_INDEX]) {
/* 275 */         afloat[EnumFaceDirection.Constants.NORTH_INDEX] = f2;
/*     */       }
/*     */       
/* 278 */       if (f > afloat[EnumFaceDirection.Constants.EAST_INDEX]) {
/* 279 */         afloat[EnumFaceDirection.Constants.EAST_INDEX] = f;
/*     */       }
/*     */       
/* 282 */       if (f1 > afloat[EnumFaceDirection.Constants.UP_INDEX]) {
/* 283 */         afloat[EnumFaceDirection.Constants.UP_INDEX] = f1;
/*     */       }
/*     */       
/* 286 */       if (f2 > afloat[EnumFaceDirection.Constants.SOUTH_INDEX]) {
/* 287 */         afloat[EnumFaceDirection.Constants.SOUTH_INDEX] = f2;
/*     */       }
/*     */     } 
/*     */     
/* 291 */     EnumFaceDirection enumfacedirection = EnumFaceDirection.getFacing(p_178408_2_);
/*     */     
/* 293 */     for (int j1 = 0; j1 < 4; j1++) {
/* 294 */       int k1 = i * j1;
/* 295 */       EnumFaceDirection.VertexInformation enumfacedirection$vertexinformation = enumfacedirection.getVertexInformation(j1);
/* 296 */       float f8 = afloat[enumfacedirection$vertexinformation.xIndex];
/* 297 */       float f3 = afloat[enumfacedirection$vertexinformation.yIndex];
/* 298 */       float f4 = afloat[enumfacedirection$vertexinformation.zIndex];
/* 299 */       p_178408_1_[k1] = Float.floatToRawIntBits(f8);
/* 300 */       p_178408_1_[k1 + 1] = Float.floatToRawIntBits(f3);
/* 301 */       p_178408_1_[k1 + 2] = Float.floatToRawIntBits(f4);
/*     */       
/* 303 */       for (int l = 0; l < 4; l++) {
/* 304 */         int i1 = i * l;
/* 305 */         float f5 = Float.intBitsToFloat(aint[i1]);
/* 306 */         float f6 = Float.intBitsToFloat(aint[i1 + 1]);
/* 307 */         float f7 = Float.intBitsToFloat(aint[i1 + 2]);
/*     */         
/* 309 */         if (MathHelper.epsilonEquals(f8, f5) && MathHelper.epsilonEquals(f3, f6) && MathHelper.epsilonEquals(f4, f7)) {
/* 310 */           p_178408_1_[k1 + 4] = aint[i1 + 4];
/* 311 */           p_178408_1_[k1 + 4 + 1] = aint[i1 + 4 + 1];
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void lockVertexUv(int p_178401_1_, int[] p_178401_2_, EnumFacing facing, BlockFaceUV p_178401_4_, TextureAtlasSprite p_178401_5_) {
/* 318 */     int i = p_178401_2_.length / 4;
/* 319 */     int j = i * p_178401_1_;
/* 320 */     float f = Float.intBitsToFloat(p_178401_2_[j]);
/* 321 */     float f1 = Float.intBitsToFloat(p_178401_2_[j + 1]);
/* 322 */     float f2 = Float.intBitsToFloat(p_178401_2_[j + 2]);
/*     */     
/* 324 */     if (f < -0.1F || f >= 1.1F) {
/* 325 */       f -= MathHelper.floor_float(f);
/*     */     }
/*     */     
/* 328 */     if (f1 < -0.1F || f1 >= 1.1F) {
/* 329 */       f1 -= MathHelper.floor_float(f1);
/*     */     }
/*     */     
/* 332 */     if (f2 < -0.1F || f2 >= 1.1F) {
/* 333 */       f2 -= MathHelper.floor_float(f2);
/*     */     }
/*     */     
/* 336 */     float f3 = 0.0F;
/* 337 */     float f4 = 0.0F;
/*     */     
/* 339 */     switch (facing) {
/*     */       case null:
/* 341 */         f3 = f * 16.0F;
/* 342 */         f4 = (1.0F - f2) * 16.0F;
/*     */         break;
/*     */       
/*     */       case UP:
/* 346 */         f3 = f * 16.0F;
/* 347 */         f4 = f2 * 16.0F;
/*     */         break;
/*     */       
/*     */       case NORTH:
/* 351 */         f3 = (1.0F - f) * 16.0F;
/* 352 */         f4 = (1.0F - f1) * 16.0F;
/*     */         break;
/*     */       
/*     */       case SOUTH:
/* 356 */         f3 = f * 16.0F;
/* 357 */         f4 = (1.0F - f1) * 16.0F;
/*     */         break;
/*     */       
/*     */       case WEST:
/* 361 */         f3 = f2 * 16.0F;
/* 362 */         f4 = (1.0F - f1) * 16.0F;
/*     */         break;
/*     */       
/*     */       case EAST:
/* 366 */         f3 = (1.0F - f2) * 16.0F;
/* 367 */         f4 = (1.0F - f1) * 16.0F;
/*     */         break;
/*     */     } 
/* 370 */     int k = p_178401_4_.func_178345_c(p_178401_1_) * i;
/* 371 */     p_178401_2_[k + 4] = Float.floatToRawIntBits(p_178401_5_.getInterpolatedU(f3));
/* 372 */     p_178401_2_[k + 4 + 1] = Float.floatToRawIntBits(p_178401_5_.getInterpolatedV(f4));
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\block\model\FaceBakery.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */