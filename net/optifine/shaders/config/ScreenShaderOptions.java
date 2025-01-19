/*    */ package net.optifine.shaders.config;
/*    */ 
/*    */ public class ScreenShaderOptions {
/*    */   private String name;
/*    */   private ShaderOption[] shaderOptions;
/*    */   private int columns;
/*    */   
/*    */   public ScreenShaderOptions(String name, ShaderOption[] shaderOptions, int columns) {
/*  9 */     this.name = name;
/* 10 */     this.shaderOptions = shaderOptions;
/* 11 */     this.columns = columns;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 15 */     return this.name;
/*    */   }
/*    */   
/*    */   public ShaderOption[] getShaderOptions() {
/* 19 */     return this.shaderOptions;
/*    */   }
/*    */   
/*    */   public int getColumns() {
/* 23 */     return this.columns;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\config\ScreenShaderOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */