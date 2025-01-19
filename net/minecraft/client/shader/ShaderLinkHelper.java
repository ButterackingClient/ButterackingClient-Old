/*    */ package net.minecraft.client.shader;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.client.renderer.OpenGlHelper;
/*    */ import net.minecraft.client.util.JsonException;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class ShaderLinkHelper
/*    */ {
/* 11 */   private static final Logger logger = LogManager.getLogger();
/*    */   private static ShaderLinkHelper staticShaderLinkHelper;
/*    */   
/*    */   public static void setNewStaticShaderLinkHelper() {
/* 15 */     staticShaderLinkHelper = new ShaderLinkHelper();
/*    */   }
/*    */   
/*    */   public static ShaderLinkHelper getStaticShaderLinkHelper() {
/* 19 */     return staticShaderLinkHelper;
/*    */   }
/*    */   
/*    */   public void deleteShader(ShaderManager p_148077_1_) {
/* 23 */     p_148077_1_.getFragmentShaderLoader().deleteShader(p_148077_1_);
/* 24 */     p_148077_1_.getVertexShaderLoader().deleteShader(p_148077_1_);
/* 25 */     OpenGlHelper.glDeleteProgram(p_148077_1_.getProgram());
/*    */   }
/*    */   
/*    */   public int createProgram() throws JsonException {
/* 29 */     int i = OpenGlHelper.glCreateProgram();
/*    */     
/* 31 */     if (i <= 0) {
/* 32 */       throw new JsonException("Could not create shader program (returned program ID " + i + ")");
/*    */     }
/* 34 */     return i;
/*    */   }
/*    */ 
/*    */   
/*    */   public void linkProgram(ShaderManager manager) throws IOException {
/* 39 */     manager.getFragmentShaderLoader().attachShader(manager);
/* 40 */     manager.getVertexShaderLoader().attachShader(manager);
/* 41 */     OpenGlHelper.glLinkProgram(manager.getProgram());
/* 42 */     int i = OpenGlHelper.glGetProgrami(manager.getProgram(), OpenGlHelper.GL_LINK_STATUS);
/*    */     
/* 44 */     if (i == 0) {
/* 45 */       logger.warn("Error encountered when linking program containing VS " + manager.getVertexShaderLoader().getShaderFilename() + " and FS " + manager.getFragmentShaderLoader().getShaderFilename() + ". Log output:");
/* 46 */       logger.warn(OpenGlHelper.glGetProgramInfoLog(manager.getProgram(), 32768));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\shader\ShaderLinkHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */