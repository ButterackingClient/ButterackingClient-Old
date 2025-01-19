/*     */ package net.optifine;
/*     */ 
/*     */ import net.minecraft.src.Config;
/*     */ import org.lwjgl.LWJGLException;
/*     */ import org.lwjgl.opengl.ARBDebugOutput;
/*     */ import org.lwjgl.opengl.ARBDebugOutputCallback;
/*     */ import org.lwjgl.opengl.ContextAttribs;
/*     */ import org.lwjgl.opengl.Display;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GLContext;
/*     */ import org.lwjgl.opengl.PixelFormat;
/*     */ 
/*     */ 
/*     */ public class GlDebugHandler
/*     */   implements ARBDebugOutputCallback.Handler
/*     */ {
/*     */   public static void createDisplayDebug() throws LWJGLException {
/*  18 */     boolean flag = (GLContext.getCapabilities()).GL_ARB_debug_output;
/*  19 */     ContextAttribs contextattribs = (new ContextAttribs()).withDebug(true);
/*  20 */     Display.create((new PixelFormat()).withDepthBits(24), contextattribs);
/*  21 */     ARBDebugOutput.glDebugMessageCallbackARB(new ARBDebugOutputCallback(new GlDebugHandler()));
/*  22 */     ARBDebugOutput.glDebugMessageControlARB(4352, 4352, 4352, null, true);
/*  23 */     GL11.glEnable(33346);
/*     */   }
/*     */   
/*     */   public void handleMessage(int source, int type, int id, int severity, String message) {
/*  27 */     if (!message.contains("glBindFramebuffer") && 
/*  28 */       !message.contains("Wide lines") && 
/*  29 */       !message.contains("shader recompiled")) {
/*  30 */       Config.dbg("[LWJGL] source: " + getSource(source) + ", type: " + getType(type) + ", id: " + id + ", severity: " + getSeverity(severity) + ", message: " + message);
/*  31 */       (new Throwable("StackTrace")).printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSource(int source) {
/*  38 */     switch (source) {
/*     */       case 33350:
/*  40 */         return "API";
/*     */       
/*     */       case 33351:
/*  43 */         return "WIN";
/*     */       
/*     */       case 33352:
/*  46 */         return "SHADER";
/*     */       
/*     */       case 33353:
/*  49 */         return "EXT";
/*     */       
/*     */       case 33354:
/*  52 */         return "APP";
/*     */       
/*     */       case 33355:
/*  55 */         return "OTHER";
/*     */     } 
/*     */     
/*  58 */     return getUnknown(source);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getType(int type) {
/*  63 */     switch (type) {
/*     */       case 33356:
/*  65 */         return "ERROR";
/*     */       
/*     */       case 33357:
/*  68 */         return "DEPRECATED";
/*     */       
/*     */       case 33358:
/*  71 */         return "UNDEFINED";
/*     */       
/*     */       case 33359:
/*  74 */         return "PORTABILITY";
/*     */       
/*     */       case 33360:
/*  77 */         return "PERFORMANCE";
/*     */       
/*     */       case 33361:
/*  80 */         return "OTHER";
/*     */     } 
/*     */     
/*  83 */     return getUnknown(type);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSeverity(int severity) {
/*  88 */     switch (severity) {
/*     */       case 37190:
/*  90 */         return "HIGH";
/*     */       
/*     */       case 37191:
/*  93 */         return "MEDIUM";
/*     */       
/*     */       case 37192:
/*  96 */         return "LOW";
/*     */     } 
/*     */     
/*  99 */     return getUnknown(severity);
/*     */   }
/*     */ 
/*     */   
/*     */   private String getUnknown(int token) {
/* 104 */     return "Unknown (0x" + Integer.toHexString(token).toUpperCase() + ")";
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\GlDebugHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */