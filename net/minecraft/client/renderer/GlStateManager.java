/*      */ package net.minecraft.client.renderer;
/*      */ 
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.FloatBuffer;
/*      */ import java.nio.IntBuffer;
/*      */ import net.minecraft.src.Config;
/*      */ import net.optifine.SmartAnimations;
/*      */ import net.optifine.render.GlAlphaState;
/*      */ import net.optifine.render.GlBlendState;
/*      */ import net.optifine.shaders.Shaders;
/*      */ import net.optifine.util.LockCounter;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ import org.lwjgl.opengl.GL14;
/*      */ 
/*      */ 
/*      */ public class GlStateManager
/*      */ {
/*   18 */   private static AlphaState alphaState = new AlphaState(null);
/*   19 */   private static BooleanState lightingState = new BooleanState(2896);
/*   20 */   private static BooleanState[] lightState = new BooleanState[8];
/*   21 */   private static ColorMaterialState colorMaterialState = new ColorMaterialState(null);
/*   22 */   private static BlendState blendState = new BlendState(null);
/*   23 */   private static DepthState depthState = new DepthState(null);
/*   24 */   private static FogState fogState = new FogState(null);
/*   25 */   private static CullState cullState = new CullState(null);
/*   26 */   private static PolygonOffsetState polygonOffsetState = new PolygonOffsetState(null);
/*   27 */   private static ColorLogicState colorLogicState = new ColorLogicState(null);
/*   28 */   private static TexGenState texGenState = new TexGenState(null);
/*   29 */   private static ClearState clearState = new ClearState(null);
/*   30 */   private static StencilState stencilState = new StencilState(null);
/*   31 */   private static BooleanState normalizeState = new BooleanState(2977);
/*   32 */   public static int activeTextureUnit = 0;
/*   33 */   public static TextureState[] textureState = new TextureState[32];
/*   34 */   private static int activeShadeModel = 7425;
/*   35 */   private static BooleanState rescaleNormalState = new BooleanState(32826);
/*   36 */   private static ColorMask colorMaskState = new ColorMask(null);
/*   37 */   private static Color colorState = new Color();
/*      */   public static boolean clearEnabled = true;
/*   39 */   private static LockCounter alphaLock = new LockCounter();
/*   40 */   private static GlAlphaState alphaLockState = new GlAlphaState();
/*   41 */   private static LockCounter blendLock = new LockCounter();
/*   42 */   private static GlBlendState blendLockState = new GlBlendState();
/*      */   private static boolean creatingDisplayList = false;
/*      */   
/*      */   public static void pushAttrib() {
/*   46 */     GL11.glPushAttrib(8256);
/*      */   }
/*      */   
/*      */   public static void popAttrib() {
/*   50 */     GL11.glPopAttrib();
/*      */   }
/*      */   
/*      */   public static void disableAlpha() {
/*   54 */     if (alphaLock.isLocked()) {
/*   55 */       alphaLockState.setDisabled();
/*      */     } else {
/*   57 */       alphaState.alphaTest.setDisabled();
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void enableAlpha() {
/*   62 */     if (alphaLock.isLocked()) {
/*   63 */       alphaLockState.setEnabled();
/*      */     } else {
/*   65 */       alphaState.alphaTest.setEnabled();
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void alphaFunc(int func, float ref) {
/*   70 */     if (alphaLock.isLocked()) {
/*   71 */       alphaLockState.setFuncRef(func, ref);
/*      */     }
/*   73 */     else if (func != alphaState.func || ref != alphaState.ref) {
/*   74 */       alphaState.func = func;
/*   75 */       alphaState.ref = ref;
/*   76 */       GL11.glAlphaFunc(func, ref);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableLighting() {
/*   82 */     lightingState.setEnabled();
/*      */   }
/*      */   
/*      */   public static void disableLighting() {
/*   86 */     lightingState.setDisabled();
/*      */   }
/*      */   
/*      */   public static void enableLight(int light) {
/*   90 */     lightState[light].setEnabled();
/*      */   }
/*      */   
/*      */   public static void disableLight(int light) {
/*   94 */     lightState[light].setDisabled();
/*      */   }
/*      */   
/*      */   public static void enableColorMaterial() {
/*   98 */     colorMaterialState.colorMaterial.setEnabled();
/*      */   }
/*      */   
/*      */   public static void disableColorMaterial() {
/*  102 */     colorMaterialState.colorMaterial.setDisabled();
/*      */   }
/*      */   
/*      */   public static void colorMaterial(int face, int mode) {
/*  106 */     if (face != colorMaterialState.face || mode != colorMaterialState.mode) {
/*  107 */       colorMaterialState.face = face;
/*  108 */       colorMaterialState.mode = mode;
/*  109 */       GL11.glColorMaterial(face, mode);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void disableDepth() {
/*  114 */     depthState.depthTest.setDisabled();
/*      */   }
/*      */   
/*      */   public static void enableDepth() {
/*  118 */     depthState.depthTest.setEnabled();
/*      */   }
/*      */   
/*      */   public static void depthFunc(int depthFunc) {
/*  122 */     if (depthFunc != depthState.depthFunc) {
/*  123 */       depthState.depthFunc = depthFunc;
/*  124 */       GL11.glDepthFunc(depthFunc);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void depthMask(boolean flagIn) {
/*  129 */     if (flagIn != depthState.maskEnabled) {
/*  130 */       depthState.maskEnabled = flagIn;
/*  131 */       GL11.glDepthMask(flagIn);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void disableBlend() {
/*  136 */     if (blendLock.isLocked()) {
/*  137 */       blendLockState.setDisabled();
/*      */     } else {
/*  139 */       blendState.blend.setDisabled();
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void enableBlend() {
/*  144 */     if (blendLock.isLocked()) {
/*  145 */       blendLockState.setEnabled();
/*      */     } else {
/*  147 */       blendState.blend.setEnabled();
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void blendFunc(int srcFactor, int dstFactor) {
/*  152 */     if (blendLock.isLocked()) {
/*  153 */       blendLockState.setFactors(srcFactor, dstFactor);
/*      */     }
/*  155 */     else if (srcFactor != blendState.srcFactor || dstFactor != blendState.dstFactor || srcFactor != blendState.srcFactorAlpha || dstFactor != blendState.dstFactorAlpha) {
/*  156 */       blendState.srcFactor = srcFactor;
/*  157 */       blendState.dstFactor = dstFactor;
/*  158 */       blendState.srcFactorAlpha = srcFactor;
/*  159 */       blendState.dstFactorAlpha = dstFactor;
/*      */       
/*  161 */       if (Config.isShaders()) {
/*  162 */         Shaders.uniform_blendFunc.setValue(srcFactor, dstFactor, srcFactor, dstFactor);
/*      */       }
/*      */       
/*  165 */       GL11.glBlendFunc(srcFactor, dstFactor);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void tryBlendFuncSeparate(int srcFactor, int dstFactor, int srcFactorAlpha, int dstFactorAlpha) {
/*  171 */     if (blendLock.isLocked()) {
/*  172 */       blendLockState.setFactors(srcFactor, dstFactor, srcFactorAlpha, dstFactorAlpha);
/*      */     }
/*  174 */     else if (srcFactor != blendState.srcFactor || dstFactor != blendState.dstFactor || srcFactorAlpha != blendState.srcFactorAlpha || dstFactorAlpha != blendState.dstFactorAlpha) {
/*  175 */       blendState.srcFactor = srcFactor;
/*  176 */       blendState.dstFactor = dstFactor;
/*  177 */       blendState.srcFactorAlpha = srcFactorAlpha;
/*  178 */       blendState.dstFactorAlpha = dstFactorAlpha;
/*      */       
/*  180 */       if (Config.isShaders()) {
/*  181 */         Shaders.uniform_blendFunc.setValue(srcFactor, dstFactor, srcFactorAlpha, dstFactorAlpha);
/*      */       }
/*      */       
/*  184 */       OpenGlHelper.glBlendFunc(srcFactor, dstFactor, srcFactorAlpha, dstFactorAlpha);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableFog() {
/*  190 */     fogState.fog.setEnabled();
/*      */   }
/*      */   
/*      */   public static void disableFog() {
/*  194 */     fogState.fog.setDisabled();
/*      */   }
/*      */   
/*      */   public static void setFog(int param) {
/*  198 */     if (param != fogState.mode) {
/*  199 */       fogState.mode = param;
/*  200 */       GL11.glFogi(2917, param);
/*      */       
/*  202 */       if (Config.isShaders()) {
/*  203 */         Shaders.setFogMode(param);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void setFogDensity(float param) {
/*  209 */     if (param < 0.0F) {
/*  210 */       param = 0.0F;
/*      */     }
/*      */     
/*  213 */     if (param != fogState.density) {
/*  214 */       fogState.density = param;
/*  215 */       GL11.glFogf(2914, param);
/*      */       
/*  217 */       if (Config.isShaders()) {
/*  218 */         Shaders.setFogDensity(param);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void setFogStart(float param) {
/*  224 */     if (param != fogState.start) {
/*  225 */       fogState.start = param;
/*  226 */       GL11.glFogf(2915, param);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void setFogEnd(float param) {
/*  231 */     if (param != fogState.end) {
/*  232 */       fogState.end = param;
/*  233 */       GL11.glFogf(2916, param);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void glFog(int p_glFog_0_, FloatBuffer p_glFog_1_) {
/*  238 */     GL11.glFog(p_glFog_0_, p_glFog_1_);
/*      */   }
/*      */   
/*      */   public static void glFogi(int p_glFogi_0_, int p_glFogi_1_) {
/*  242 */     GL11.glFogi(p_glFogi_0_, p_glFogi_1_);
/*      */   }
/*      */   
/*      */   public static void enableCull() {
/*  246 */     cullState.cullFace.setEnabled();
/*      */   }
/*      */   
/*      */   public static void disableCull() {
/*  250 */     cullState.cullFace.setDisabled();
/*      */   }
/*      */   
/*      */   public static void cullFace(int mode) {
/*  254 */     if (mode != cullState.mode) {
/*  255 */       cullState.mode = mode;
/*  256 */       GL11.glCullFace(mode);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void enablePolygonOffset() {
/*  261 */     polygonOffsetState.polygonOffsetFill.setEnabled();
/*      */   }
/*      */   
/*      */   public static void disablePolygonOffset() {
/*  265 */     polygonOffsetState.polygonOffsetFill.setDisabled();
/*      */   }
/*      */   
/*      */   public static void doPolygonOffset(float factor, float units) {
/*  269 */     if (factor != polygonOffsetState.factor || units != polygonOffsetState.units) {
/*  270 */       polygonOffsetState.factor = factor;
/*  271 */       polygonOffsetState.units = units;
/*  272 */       GL11.glPolygonOffset(factor, units);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void enableColorLogic() {
/*  277 */     colorLogicState.colorLogicOp.setEnabled();
/*      */   }
/*      */   
/*      */   public static void disableColorLogic() {
/*  281 */     colorLogicState.colorLogicOp.setDisabled();
/*      */   }
/*      */   
/*      */   public static void colorLogicOp(int opcode) {
/*  285 */     if (opcode != colorLogicState.opcode) {
/*  286 */       colorLogicState.opcode = opcode;
/*  287 */       GL11.glLogicOp(opcode);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void enableTexGenCoord(TexGen p_179087_0_) {
/*  292 */     (texGenCoord(p_179087_0_)).textureGen.setEnabled();
/*      */   }
/*      */   
/*      */   public static void disableTexGenCoord(TexGen p_179100_0_) {
/*  296 */     (texGenCoord(p_179100_0_)).textureGen.setDisabled();
/*      */   }
/*      */   
/*      */   public static void texGen(TexGen texGen, int param) {
/*  300 */     TexGenCoord glstatemanager$texgencoord = texGenCoord(texGen);
/*      */     
/*  302 */     if (param != glstatemanager$texgencoord.param) {
/*  303 */       glstatemanager$texgencoord.param = param;
/*  304 */       GL11.glTexGeni(glstatemanager$texgencoord.coord, 9472, param);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void texGen(TexGen p_179105_0_, int pname, FloatBuffer params) {
/*  309 */     GL11.glTexGen((texGenCoord(p_179105_0_)).coord, pname, params);
/*      */   }
/*      */   
/*      */   private static TexGenCoord texGenCoord(TexGen p_179125_0_) {
/*  313 */     switch (p_179125_0_) {
/*      */       case S:
/*  315 */         return texGenState.s;
/*      */       
/*      */       case T:
/*  318 */         return texGenState.t;
/*      */       
/*      */       case R:
/*  321 */         return texGenState.r;
/*      */       
/*      */       case null:
/*  324 */         return texGenState.q;
/*      */     } 
/*      */     
/*  327 */     return texGenState.s;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setActiveTexture(int texture) {
/*  332 */     if (activeTextureUnit != texture - OpenGlHelper.defaultTexUnit) {
/*  333 */       activeTextureUnit = texture - OpenGlHelper.defaultTexUnit;
/*  334 */       OpenGlHelper.setActiveTexture(texture);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void enableTexture2D() {
/*  339 */     (textureState[activeTextureUnit]).texture2DState.setEnabled();
/*      */   }
/*      */   
/*      */   public static void disableTexture2D() {
/*  343 */     (textureState[activeTextureUnit]).texture2DState.setDisabled();
/*      */   }
/*      */   
/*      */   public static int generateTexture() {
/*  347 */     return GL11.glGenTextures();
/*      */   }
/*      */   
/*      */   public static void deleteTexture(int texture) {
/*  351 */     if (texture != 0) {
/*  352 */       GL11.glDeleteTextures(texture); byte b; int i;
/*      */       TextureState[] arrayOfTextureState;
/*  354 */       for (i = (arrayOfTextureState = textureState).length, b = 0; b < i; ) { TextureState glstatemanager$texturestate = arrayOfTextureState[b];
/*  355 */         if (glstatemanager$texturestate.textureName == texture)
/*  356 */           glstatemanager$texturestate.textureName = 0; 
/*      */         b++; }
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void bindTexture(int texture) {
/*  363 */     if (texture != (textureState[activeTextureUnit]).textureName) {
/*  364 */       (textureState[activeTextureUnit]).textureName = texture;
/*  365 */       GL11.glBindTexture(3553, texture);
/*      */       
/*  367 */       if (SmartAnimations.isActive()) {
/*  368 */         SmartAnimations.textureRendered(texture);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void enableNormalize() {
/*  374 */     normalizeState.setEnabled();
/*      */   }
/*      */   
/*      */   public static void disableNormalize() {
/*  378 */     normalizeState.setDisabled();
/*      */   }
/*      */   
/*      */   public static void shadeModel(int mode) {
/*  382 */     if (mode != activeShadeModel) {
/*  383 */       activeShadeModel = mode;
/*  384 */       GL11.glShadeModel(mode);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void enableRescaleNormal() {
/*  389 */     rescaleNormalState.setEnabled();
/*      */   }
/*      */   
/*      */   public static void disableRescaleNormal() {
/*  393 */     rescaleNormalState.setDisabled();
/*      */   }
/*      */   
/*      */   public static void viewport(int x, int y, int width, int height) {
/*  397 */     GL11.glViewport(x, y, width, height);
/*      */   }
/*      */   
/*      */   public static void colorMask(boolean red, boolean green, boolean blue, boolean alpha) {
/*  401 */     if (red != colorMaskState.red || green != colorMaskState.green || blue != colorMaskState.blue || alpha != colorMaskState.alpha) {
/*  402 */       colorMaskState.red = red;
/*  403 */       colorMaskState.green = green;
/*  404 */       colorMaskState.blue = blue;
/*  405 */       colorMaskState.alpha = alpha;
/*  406 */       GL11.glColorMask(red, green, blue, alpha);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void clearDepth(double depth) {
/*  411 */     if (depth != clearState.depth) {
/*  412 */       clearState.depth = depth;
/*  413 */       GL11.glClearDepth(depth);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void clearColor(float red, float green, float blue, float alpha) {
/*  418 */     if (red != clearState.color.red || green != clearState.color.green || blue != clearState.color.blue || alpha != clearState.color.alpha) {
/*  419 */       clearState.color.red = red;
/*  420 */       clearState.color.green = green;
/*  421 */       clearState.color.blue = blue;
/*  422 */       clearState.color.alpha = alpha;
/*  423 */       GL11.glClearColor(red, green, blue, alpha);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void clear(int mask) {
/*  428 */     if (clearEnabled) {
/*  429 */       GL11.glClear(mask);
/*      */     }
/*      */   }
/*      */   
/*      */   public static void matrixMode(int mode) {
/*  434 */     GL11.glMatrixMode(mode);
/*      */   }
/*      */   
/*      */   public static void loadIdentity() {
/*  438 */     GL11.glLoadIdentity();
/*      */   }
/*      */   
/*      */   public static void pushMatrix() {
/*  442 */     GL11.glPushMatrix();
/*      */   }
/*      */   
/*      */   public static void popMatrix() {
/*  446 */     GL11.glPopMatrix();
/*      */   }
/*      */   
/*      */   public static void getFloat(int pname, FloatBuffer params) {
/*  450 */     GL11.glGetFloat(pname, params);
/*      */   }
/*      */   
/*      */   public static void ortho(double left, double right, double bottom, double top, double zNear, double zFar) {
/*  454 */     GL11.glOrtho(left, right, bottom, top, zNear, zFar);
/*      */   }
/*      */   
/*      */   public static void rotate(float angle, float x, float y, float z) {
/*  458 */     GL11.glRotatef(angle, x, y, z);
/*      */   }
/*      */   
/*      */   public static void scale(float x, float y, float z) {
/*  462 */     GL11.glScalef(x, y, z);
/*      */   }
/*      */   
/*      */   public static void scale(double x, double y, double z) {
/*  466 */     GL11.glScaled(x, y, z);
/*      */   }
/*      */   
/*      */   public static void translate(float x, float y, float z) {
/*  470 */     GL11.glTranslatef(x, y, z);
/*      */   }
/*      */   
/*      */   public static void translate(double x, double y, double z) {
/*  474 */     GL11.glTranslated(x, y, z);
/*      */   }
/*      */   
/*      */   public static void multMatrix(FloatBuffer matrix) {
/*  478 */     GL11.glMultMatrix(matrix);
/*      */   }
/*      */   
/*      */   public static void color(float colorRed, float colorGreen, float colorBlue, float colorAlpha) {
/*  482 */     if (colorRed != colorState.red || colorGreen != colorState.green || colorBlue != colorState.blue || colorAlpha != colorState.alpha) {
/*  483 */       colorState.red = colorRed;
/*  484 */       colorState.green = colorGreen;
/*  485 */       colorState.blue = colorBlue;
/*  486 */       colorState.alpha = colorAlpha;
/*  487 */       GL11.glColor4f(colorRed, colorGreen, colorBlue, colorAlpha);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void color(float colorRed, float colorGreen, float colorBlue) {
/*  492 */     color(colorRed, colorGreen, colorBlue, 1.0F);
/*      */   }
/*      */   
/*      */   public static void resetColor() {
/*  496 */     colorState.red = colorState.green = colorState.blue = colorState.alpha = -1.0F;
/*      */   }
/*      */   
/*      */   public static void glNormalPointer(int p_glNormalPointer_0_, int p_glNormalPointer_1_, ByteBuffer p_glNormalPointer_2_) {
/*  500 */     GL11.glNormalPointer(p_glNormalPointer_0_, p_glNormalPointer_1_, p_glNormalPointer_2_);
/*      */   }
/*      */   
/*      */   public static void glTexCoordPointer(int p_glTexCoordPointer_0_, int p_glTexCoordPointer_1_, int p_glTexCoordPointer_2_, int p_glTexCoordPointer_3_) {
/*  504 */     GL11.glTexCoordPointer(p_glTexCoordPointer_0_, p_glTexCoordPointer_1_, p_glTexCoordPointer_2_, p_glTexCoordPointer_3_);
/*      */   }
/*      */   
/*      */   public static void glTexCoordPointer(int p_glTexCoordPointer_0_, int p_glTexCoordPointer_1_, int p_glTexCoordPointer_2_, ByteBuffer p_glTexCoordPointer_3_) {
/*  508 */     GL11.glTexCoordPointer(p_glTexCoordPointer_0_, p_glTexCoordPointer_1_, p_glTexCoordPointer_2_, p_glTexCoordPointer_3_);
/*      */   }
/*      */   
/*      */   public static void glVertexPointer(int p_glVertexPointer_0_, int p_glVertexPointer_1_, int p_glVertexPointer_2_, int p_glVertexPointer_3_) {
/*  512 */     GL11.glVertexPointer(p_glVertexPointer_0_, p_glVertexPointer_1_, p_glVertexPointer_2_, p_glVertexPointer_3_);
/*      */   }
/*      */   
/*      */   public static void glVertexPointer(int p_glVertexPointer_0_, int p_glVertexPointer_1_, int p_glVertexPointer_2_, ByteBuffer p_glVertexPointer_3_) {
/*  516 */     GL11.glVertexPointer(p_glVertexPointer_0_, p_glVertexPointer_1_, p_glVertexPointer_2_, p_glVertexPointer_3_);
/*      */   }
/*      */   
/*      */   public static void glColorPointer(int p_glColorPointer_0_, int p_glColorPointer_1_, int p_glColorPointer_2_, int p_glColorPointer_3_) {
/*  520 */     GL11.glColorPointer(p_glColorPointer_0_, p_glColorPointer_1_, p_glColorPointer_2_, p_glColorPointer_3_);
/*      */   }
/*      */   
/*      */   public static void glColorPointer(int p_glColorPointer_0_, int p_glColorPointer_1_, int p_glColorPointer_2_, ByteBuffer p_glColorPointer_3_) {
/*  524 */     GL11.glColorPointer(p_glColorPointer_0_, p_glColorPointer_1_, p_glColorPointer_2_, p_glColorPointer_3_);
/*      */   }
/*      */   
/*      */   public static void glDisableClientState(int p_glDisableClientState_0_) {
/*  528 */     GL11.glDisableClientState(p_glDisableClientState_0_);
/*      */   }
/*      */   
/*      */   public static void glEnableClientState(int p_glEnableClientState_0_) {
/*  532 */     GL11.glEnableClientState(p_glEnableClientState_0_);
/*      */   }
/*      */   
/*      */   public static void glBegin(int p_glBegin_0_) {
/*  536 */     GL11.glBegin(p_glBegin_0_);
/*      */   }
/*      */   
/*      */   public static void glEnd() {
/*  540 */     GL11.glEnd();
/*      */   }
/*      */   
/*      */   public static void glDrawArrays(int p_glDrawArrays_0_, int p_glDrawArrays_1_, int p_glDrawArrays_2_) {
/*  544 */     GL11.glDrawArrays(p_glDrawArrays_0_, p_glDrawArrays_1_, p_glDrawArrays_2_);
/*      */     
/*  546 */     if (Config.isShaders() && !creatingDisplayList) {
/*  547 */       int i = Shaders.activeProgram.getCountInstances();
/*      */       
/*  549 */       if (i > 1) {
/*  550 */         for (int j = 1; j < i; j++) {
/*  551 */           Shaders.uniform_instanceId.setValue(j);
/*  552 */           GL11.glDrawArrays(p_glDrawArrays_0_, p_glDrawArrays_1_, p_glDrawArrays_2_);
/*      */         } 
/*      */         
/*  555 */         Shaders.uniform_instanceId.setValue(0);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void callList(int list) {
/*  561 */     GL11.glCallList(list);
/*      */     
/*  563 */     if (Config.isShaders() && !creatingDisplayList) {
/*  564 */       int i = Shaders.activeProgram.getCountInstances();
/*      */       
/*  566 */       if (i > 1) {
/*  567 */         for (int j = 1; j < i; j++) {
/*  568 */           Shaders.uniform_instanceId.setValue(j);
/*  569 */           GL11.glCallList(list);
/*      */         } 
/*      */         
/*  572 */         Shaders.uniform_instanceId.setValue(0);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void callLists(IntBuffer p_callLists_0_) {
/*  578 */     GL11.glCallLists(p_callLists_0_);
/*      */     
/*  580 */     if (Config.isShaders() && !creatingDisplayList) {
/*  581 */       int i = Shaders.activeProgram.getCountInstances();
/*      */       
/*  583 */       if (i > 1) {
/*  584 */         for (int j = 1; j < i; j++) {
/*  585 */           Shaders.uniform_instanceId.setValue(j);
/*  586 */           GL11.glCallLists(p_callLists_0_);
/*      */         } 
/*      */         
/*  589 */         Shaders.uniform_instanceId.setValue(0);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void glDeleteLists(int p_glDeleteLists_0_, int p_glDeleteLists_1_) {
/*  595 */     GL11.glDeleteLists(p_glDeleteLists_0_, p_glDeleteLists_1_);
/*      */   }
/*      */   
/*      */   public static void glNewList(int p_glNewList_0_, int p_glNewList_1_) {
/*  599 */     GL11.glNewList(p_glNewList_0_, p_glNewList_1_);
/*  600 */     creatingDisplayList = true;
/*      */   }
/*      */   
/*      */   public static void glEndList() {
/*  604 */     GL11.glEndList();
/*  605 */     creatingDisplayList = false;
/*      */   }
/*      */   
/*      */   public static int glGetError() {
/*  609 */     return GL11.glGetError();
/*      */   }
/*      */   
/*      */   public static void glTexImage2D(int p_glTexImage2D_0_, int p_glTexImage2D_1_, int p_glTexImage2D_2_, int p_glTexImage2D_3_, int p_glTexImage2D_4_, int p_glTexImage2D_5_, int p_glTexImage2D_6_, int p_glTexImage2D_7_, IntBuffer p_glTexImage2D_8_) {
/*  613 */     GL11.glTexImage2D(p_glTexImage2D_0_, p_glTexImage2D_1_, p_glTexImage2D_2_, p_glTexImage2D_3_, p_glTexImage2D_4_, p_glTexImage2D_5_, p_glTexImage2D_6_, p_glTexImage2D_7_, p_glTexImage2D_8_);
/*      */   }
/*      */   
/*      */   public static void glTexSubImage2D(int p_glTexSubImage2D_0_, int p_glTexSubImage2D_1_, int p_glTexSubImage2D_2_, int p_glTexSubImage2D_3_, int p_glTexSubImage2D_4_, int p_glTexSubImage2D_5_, int p_glTexSubImage2D_6_, int p_glTexSubImage2D_7_, IntBuffer p_glTexSubImage2D_8_) {
/*  617 */     GL11.glTexSubImage2D(p_glTexSubImage2D_0_, p_glTexSubImage2D_1_, p_glTexSubImage2D_2_, p_glTexSubImage2D_3_, p_glTexSubImage2D_4_, p_glTexSubImage2D_5_, p_glTexSubImage2D_6_, p_glTexSubImage2D_7_, p_glTexSubImage2D_8_);
/*      */   }
/*      */   
/*      */   public static void glCopyTexSubImage2D(int p_glCopyTexSubImage2D_0_, int p_glCopyTexSubImage2D_1_, int p_glCopyTexSubImage2D_2_, int p_glCopyTexSubImage2D_3_, int p_glCopyTexSubImage2D_4_, int p_glCopyTexSubImage2D_5_, int p_glCopyTexSubImage2D_6_, int p_glCopyTexSubImage2D_7_) {
/*  621 */     GL11.glCopyTexSubImage2D(p_glCopyTexSubImage2D_0_, p_glCopyTexSubImage2D_1_, p_glCopyTexSubImage2D_2_, p_glCopyTexSubImage2D_3_, p_glCopyTexSubImage2D_4_, p_glCopyTexSubImage2D_5_, p_glCopyTexSubImage2D_6_, p_glCopyTexSubImage2D_7_);
/*      */   }
/*      */   
/*      */   public static void glGetTexImage(int p_glGetTexImage_0_, int p_glGetTexImage_1_, int p_glGetTexImage_2_, int p_glGetTexImage_3_, IntBuffer p_glGetTexImage_4_) {
/*  625 */     GL11.glGetTexImage(p_glGetTexImage_0_, p_glGetTexImage_1_, p_glGetTexImage_2_, p_glGetTexImage_3_, p_glGetTexImage_4_);
/*      */   }
/*      */   
/*      */   public static void glTexParameterf(int p_glTexParameterf_0_, int p_glTexParameterf_1_, float p_glTexParameterf_2_) {
/*  629 */     GL11.glTexParameterf(p_glTexParameterf_0_, p_glTexParameterf_1_, p_glTexParameterf_2_);
/*      */   }
/*      */   
/*      */   public static void glTexParameteri(int p_glTexParameteri_0_, int p_glTexParameteri_1_, int p_glTexParameteri_2_) {
/*  633 */     GL11.glTexParameteri(p_glTexParameteri_0_, p_glTexParameteri_1_, p_glTexParameteri_2_);
/*      */   }
/*      */   
/*      */   public static int glGetTexLevelParameteri(int p_glGetTexLevelParameteri_0_, int p_glGetTexLevelParameteri_1_, int p_glGetTexLevelParameteri_2_) {
/*  637 */     return GL11.glGetTexLevelParameteri(p_glGetTexLevelParameteri_0_, p_glGetTexLevelParameteri_1_, p_glGetTexLevelParameteri_2_);
/*      */   }
/*      */   
/*      */   public static int getActiveTextureUnit() {
/*  641 */     return OpenGlHelper.defaultTexUnit + activeTextureUnit;
/*      */   }
/*      */   
/*      */   public static void bindCurrentTexture() {
/*  645 */     GL11.glBindTexture(3553, (textureState[activeTextureUnit]).textureName);
/*      */   }
/*      */   
/*      */   public static int getBoundTexture() {
/*  649 */     return (textureState[activeTextureUnit]).textureName;
/*      */   }
/*      */   
/*      */   public static void checkBoundTexture() {
/*  653 */     if (Config.isMinecraftThread()) {
/*  654 */       int i = GL11.glGetInteger(34016);
/*  655 */       int j = GL11.glGetInteger(32873);
/*  656 */       int k = getActiveTextureUnit();
/*  657 */       int l = getBoundTexture();
/*      */       
/*  659 */       if (l > 0 && (
/*  660 */         i != k || j != l)) {
/*  661 */         Config.dbg("checkTexture: act: " + k + ", glAct: " + i + ", tex: " + l + ", glTex: " + j);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void deleteTextures(IntBuffer p_deleteTextures_0_) {
/*  668 */     p_deleteTextures_0_.rewind();
/*      */     
/*  670 */     while (p_deleteTextures_0_.position() < p_deleteTextures_0_.limit()) {
/*  671 */       int i = p_deleteTextures_0_.get();
/*  672 */       deleteTexture(i);
/*      */     } 
/*      */     
/*  675 */     p_deleteTextures_0_.rewind();
/*      */   }
/*      */   
/*      */   public static boolean isFogEnabled() {
/*  679 */     return fogState.fog.currentState;
/*      */   }
/*      */   
/*      */   public static void setFogEnabled(boolean p_setFogEnabled_0_) {
/*  683 */     fogState.fog.setState(p_setFogEnabled_0_);
/*      */   }
/*      */   
/*      */   public static void lockAlpha(GlAlphaState p_lockAlpha_0_) {
/*  687 */     if (!alphaLock.isLocked()) {
/*  688 */       getAlphaState(alphaLockState);
/*  689 */       setAlphaState(p_lockAlpha_0_);
/*  690 */       alphaLock.lock();
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void unlockAlpha() {
/*  695 */     if (alphaLock.unlock()) {
/*  696 */       setAlphaState(alphaLockState);
/*      */     }
/*      */   }
/*      */   
/*      */   public static void getAlphaState(GlAlphaState p_getAlphaState_0_) {
/*  701 */     if (alphaLock.isLocked()) {
/*  702 */       p_getAlphaState_0_.setState(alphaLockState);
/*      */     } else {
/*  704 */       p_getAlphaState_0_.setState(alphaState.alphaTest.currentState, alphaState.func, alphaState.ref);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void setAlphaState(GlAlphaState p_setAlphaState_0_) {
/*  709 */     if (alphaLock.isLocked()) {
/*  710 */       alphaLockState.setState(p_setAlphaState_0_);
/*      */     } else {
/*  712 */       alphaState.alphaTest.setState(p_setAlphaState_0_.isEnabled());
/*  713 */       alphaFunc(p_setAlphaState_0_.getFunc(), p_setAlphaState_0_.getRef());
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void lockBlend(GlBlendState p_lockBlend_0_) {
/*  718 */     if (!blendLock.isLocked()) {
/*  719 */       getBlendState(blendLockState);
/*  720 */       setBlendState(p_lockBlend_0_);
/*  721 */       blendLock.lock();
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void unlockBlend() {
/*  726 */     if (blendLock.unlock()) {
/*  727 */       setBlendState(blendLockState);
/*      */     }
/*      */   }
/*      */   
/*      */   public static void getBlendState(GlBlendState p_getBlendState_0_) {
/*  732 */     if (blendLock.isLocked()) {
/*  733 */       p_getBlendState_0_.setState(blendLockState);
/*      */     } else {
/*  735 */       p_getBlendState_0_.setState(blendState.blend.currentState, blendState.srcFactor, blendState.dstFactor, blendState.srcFactorAlpha, blendState.dstFactorAlpha);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void setBlendState(GlBlendState p_setBlendState_0_) {
/*  740 */     if (blendLock.isLocked()) {
/*  741 */       blendLockState.setState(p_setBlendState_0_);
/*      */     } else {
/*  743 */       blendState.blend.setState(p_setBlendState_0_.isEnabled());
/*      */       
/*  745 */       if (!p_setBlendState_0_.isSeparate()) {
/*  746 */         blendFunc(p_setBlendState_0_.getSrcFactor(), p_setBlendState_0_.getDstFactor());
/*      */       } else {
/*  748 */         tryBlendFuncSeparate(p_setBlendState_0_.getSrcFactor(), p_setBlendState_0_.getDstFactor(), p_setBlendState_0_.getSrcFactorAlpha(), p_setBlendState_0_.getDstFactorAlpha());
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void glMultiDrawArrays(int p_glMultiDrawArrays_0_, IntBuffer p_glMultiDrawArrays_1_, IntBuffer p_glMultiDrawArrays_2_) {
/*  754 */     GL14.glMultiDrawArrays(p_glMultiDrawArrays_0_, p_glMultiDrawArrays_1_, p_glMultiDrawArrays_2_);
/*      */     
/*  756 */     if (Config.isShaders() && !creatingDisplayList) {
/*  757 */       int i = Shaders.activeProgram.getCountInstances();
/*      */       
/*  759 */       if (i > 1) {
/*  760 */         for (int j = 1; j < i; j++) {
/*  761 */           Shaders.uniform_instanceId.setValue(j);
/*  762 */           GL14.glMultiDrawArrays(p_glMultiDrawArrays_0_, p_glMultiDrawArrays_1_, p_glMultiDrawArrays_2_);
/*      */         } 
/*      */         
/*  765 */         Shaders.uniform_instanceId.setValue(0);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   static {
/*  771 */     for (int i = 0; i < 8; i++) {
/*  772 */       lightState[i] = new BooleanState(16384 + i);
/*      */     }
/*      */     
/*  775 */     for (int j = 0; j < textureState.length; j++) {
/*  776 */       textureState[j] = new TextureState(null);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class AlphaState
/*      */   {
/*      */     private AlphaState() {}
/*      */ 
/*      */     
/*  786 */     public GlStateManager.BooleanState alphaTest = new GlStateManager.BooleanState(3008);
/*  787 */     public int func = 519;
/*  788 */     public float ref = -1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class BlendState
/*      */   {
/*      */     private BlendState() {}
/*      */ 
/*      */ 
/*      */     
/*  800 */     public GlStateManager.BooleanState blend = new GlStateManager.BooleanState(3042);
/*  801 */     public int srcFactor = 1;
/*  802 */     public int dstFactor = 0;
/*  803 */     public int srcFactorAlpha = 1;
/*  804 */     public int dstFactorAlpha = 0;
/*      */   }
/*      */   
/*      */   static class BooleanState
/*      */   {
/*      */     private final int capability;
/*      */     private boolean currentState = false;
/*      */     
/*      */     public BooleanState(int capabilityIn) {
/*  813 */       this.capability = capabilityIn;
/*      */     }
/*      */     
/*      */     public void setDisabled() {
/*  817 */       setState(false);
/*      */     }
/*      */     
/*      */     public void setEnabled() {
/*  821 */       setState(true);
/*      */     }
/*      */     
/*      */     public void setState(boolean state) {
/*  825 */       if (state != this.currentState) {
/*  826 */         this.currentState = state;
/*      */         
/*  828 */         if (state) {
/*  829 */           GL11.glEnable(this.capability);
/*      */         } else {
/*  831 */           GL11.glDisable(this.capability);
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   static class ClearState {
/*      */     public double depth;
/*      */     public GlStateManager.Color color;
/*      */     public int field_179204_c;
/*      */     
/*      */     private ClearState() {
/*  843 */       this.depth = 1.0D;
/*  844 */       this.color = new GlStateManager.Color(0.0F, 0.0F, 0.0F, 0.0F);
/*  845 */       this.field_179204_c = 0;
/*      */     }
/*      */   }
/*      */   
/*      */   static class Color {
/*  850 */     public float red = 1.0F;
/*  851 */     public float green = 1.0F;
/*  852 */     public float blue = 1.0F;
/*  853 */     public float alpha = 1.0F;
/*      */ 
/*      */     
/*      */     public Color() {}
/*      */     
/*      */     public Color(float redIn, float greenIn, float blueIn, float alphaIn) {
/*  859 */       this.red = redIn;
/*  860 */       this.green = greenIn;
/*  861 */       this.blue = blueIn;
/*  862 */       this.alpha = alphaIn;
/*      */     }
/*      */   }
/*      */   
/*      */   static class ColorLogicState {
/*      */     public GlStateManager.BooleanState colorLogicOp;
/*      */     public int opcode;
/*      */     
/*      */     private ColorLogicState() {
/*  871 */       this.colorLogicOp = new GlStateManager.BooleanState(3058);
/*  872 */       this.opcode = 5379;
/*      */     }
/*      */   }
/*      */   
/*      */   static class ColorMask {
/*      */     public boolean red;
/*      */     public boolean green;
/*      */     public boolean blue;
/*      */     public boolean alpha;
/*      */     
/*      */     private ColorMask() {
/*  883 */       this.red = true;
/*  884 */       this.green = true;
/*  885 */       this.blue = true;
/*  886 */       this.alpha = true;
/*      */     }
/*      */   }
/*      */   
/*      */   static class ColorMaterialState {
/*      */     public GlStateManager.BooleanState colorMaterial;
/*      */     public int face;
/*      */     public int mode;
/*      */     
/*      */     private ColorMaterialState() {
/*  896 */       this.colorMaterial = new GlStateManager.BooleanState(2903);
/*  897 */       this.face = 1032;
/*  898 */       this.mode = 5634;
/*      */     }
/*      */   }
/*      */   
/*      */   static class CullState {
/*      */     public GlStateManager.BooleanState cullFace;
/*      */     public int mode;
/*      */     
/*      */     private CullState() {
/*  907 */       this.cullFace = new GlStateManager.BooleanState(2884);
/*  908 */       this.mode = 1029;
/*      */     }
/*      */   }
/*      */   
/*      */   static class DepthState {
/*      */     public GlStateManager.BooleanState depthTest;
/*      */     public boolean maskEnabled;
/*      */     public int depthFunc;
/*      */     
/*      */     private DepthState() {
/*  918 */       this.depthTest = new GlStateManager.BooleanState(2929);
/*  919 */       this.maskEnabled = true;
/*  920 */       this.depthFunc = 513;
/*      */     }
/*      */   }
/*      */   
/*      */   static class FogState {
/*      */     public GlStateManager.BooleanState fog;
/*      */     public int mode;
/*      */     public float density;
/*      */     public float start;
/*      */     public float end;
/*      */     
/*      */     private FogState() {
/*  932 */       this.fog = new GlStateManager.BooleanState(2912);
/*  933 */       this.mode = 2048;
/*  934 */       this.density = 1.0F;
/*  935 */       this.start = 0.0F;
/*  936 */       this.end = 1.0F;
/*      */     }
/*      */   }
/*      */   
/*      */   static class PolygonOffsetState {
/*      */     public GlStateManager.BooleanState polygonOffsetFill;
/*      */     public GlStateManager.BooleanState polygonOffsetLine;
/*      */     public float factor;
/*      */     public float units;
/*      */     
/*      */     private PolygonOffsetState() {
/*  947 */       this.polygonOffsetFill = new GlStateManager.BooleanState(32823);
/*  948 */       this.polygonOffsetLine = new GlStateManager.BooleanState(10754);
/*  949 */       this.factor = 0.0F;
/*  950 */       this.units = 0.0F;
/*      */     }
/*      */   }
/*      */   
/*      */   static class StencilFunc {
/*      */     public int field_179081_a;
/*      */     public int field_179079_b;
/*      */     public int field_179080_c;
/*      */     
/*      */     private StencilFunc() {
/*  960 */       this.field_179081_a = 519;
/*  961 */       this.field_179079_b = 0;
/*  962 */       this.field_179080_c = -1;
/*      */     }
/*      */   }
/*      */   
/*      */   static class StencilState {
/*      */     public GlStateManager.StencilFunc field_179078_a;
/*      */     public int field_179076_b;
/*      */     public int field_179077_c;
/*      */     public int field_179074_d;
/*      */     public int field_179075_e;
/*      */     
/*      */     private StencilState() {
/*  974 */       this.field_179078_a = new GlStateManager.StencilFunc(null);
/*  975 */       this.field_179076_b = -1;
/*  976 */       this.field_179077_c = 7680;
/*  977 */       this.field_179074_d = 7680;
/*  978 */       this.field_179075_e = 7680;
/*      */     }
/*      */   }
/*      */   
/*      */   public enum TexGen {
/*  983 */     S,
/*  984 */     T,
/*  985 */     R,
/*  986 */     Q;
/*      */   }
/*      */   
/*      */   static class TexGenCoord {
/*      */     public GlStateManager.BooleanState textureGen;
/*      */     public int coord;
/*  992 */     public int param = -1;
/*      */     
/*      */     public TexGenCoord(int p_i46254_1_, int p_i46254_2_) {
/*  995 */       this.coord = p_i46254_1_;
/*  996 */       this.textureGen = new GlStateManager.BooleanState(p_i46254_2_);
/*      */     }
/*      */   }
/*      */   
/*      */   static class TexGenState {
/*      */     public GlStateManager.TexGenCoord s;
/*      */     public GlStateManager.TexGenCoord t;
/*      */     public GlStateManager.TexGenCoord r;
/*      */     public GlStateManager.TexGenCoord q;
/*      */     
/*      */     private TexGenState() {
/* 1007 */       this.s = new GlStateManager.TexGenCoord(8192, 3168);
/* 1008 */       this.t = new GlStateManager.TexGenCoord(8193, 3169);
/* 1009 */       this.r = new GlStateManager.TexGenCoord(8194, 3170);
/* 1010 */       this.q = new GlStateManager.TexGenCoord(8195, 3171);
/*      */     }
/*      */   }
/*      */   
/*      */   public static class TextureState {
/*      */     public GlStateManager.BooleanState texture2DState;
/*      */     public int textureName;
/*      */     
/*      */     private TextureState() {
/* 1019 */       this.texture2DState = new GlStateManager.BooleanState(3553);
/* 1020 */       this.textureName = 0;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\GlStateManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */