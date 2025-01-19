/*    */ package net.optifine.shaders.config;
/*    */ 
/*    */ public class ShaderMacro {
/*    */   private String name;
/*    */   private String value;
/*    */   
/*    */   public ShaderMacro(String name, String value) {
/*  8 */     this.name = name;
/*  9 */     this.value = value;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 13 */     return this.name;
/*    */   }
/*    */   
/*    */   public String getValue() {
/* 17 */     return this.value;
/*    */   }
/*    */   
/*    */   public String getSourceLine() {
/* 21 */     return "#define " + this.name + " " + this.value;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 25 */     return getSourceLine();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\config\ShaderMacro.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */