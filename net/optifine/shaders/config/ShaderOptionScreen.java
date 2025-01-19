/*    */ package net.optifine.shaders.config;
/*    */ 
/*    */ import net.optifine.shaders.Shaders;
/*    */ 
/*    */ public class ShaderOptionScreen extends ShaderOption {
/*    */   public ShaderOptionScreen(String name) {
/*  7 */     super(name, null, null, new String[0], null, null);
/*    */   }
/*    */   
/*    */   public String getNameText() {
/* 11 */     return Shaders.translate("screen." + getName(), getName());
/*    */   }
/*    */   
/*    */   public String getDescriptionText() {
/* 15 */     return Shaders.translate("screen." + getName() + ".comment", null);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\config\ShaderOptionScreen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */