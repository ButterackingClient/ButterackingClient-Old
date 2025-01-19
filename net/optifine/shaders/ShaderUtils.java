/*    */ package net.optifine.shaders;
/*    */ 
/*    */ import net.minecraft.src.Config;
/*    */ import net.optifine.shaders.config.ShaderOption;
/*    */ import net.optifine.shaders.config.ShaderProfile;
/*    */ 
/*    */ public class ShaderUtils {
/*    */   public static ShaderOption getShaderOption(String name, ShaderOption[] opts) {
/*  9 */     if (opts == null) {
/* 10 */       return null;
/*    */     }
/* 12 */     for (int i = 0; i < opts.length; i++) {
/* 13 */       ShaderOption shaderoption = opts[i];
/*    */       
/* 15 */       if (shaderoption.getName().equals(name)) {
/* 16 */         return shaderoption;
/*    */       }
/*    */     } 
/*    */     
/* 20 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public static ShaderProfile detectProfile(ShaderProfile[] profs, ShaderOption[] opts, boolean def) {
/* 25 */     if (profs == null) {
/* 26 */       return null;
/*    */     }
/* 28 */     for (int i = 0; i < profs.length; i++) {
/* 29 */       ShaderProfile shaderprofile = profs[i];
/*    */       
/* 31 */       if (matchProfile(shaderprofile, opts, def)) {
/* 32 */         return shaderprofile;
/*    */       }
/*    */     } 
/*    */     
/* 36 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean matchProfile(ShaderProfile prof, ShaderOption[] opts, boolean def) {
/* 41 */     if (prof == null)
/* 42 */       return false; 
/* 43 */     if (opts == null) {
/* 44 */       return false;
/*    */     }
/* 46 */     String[] astring = prof.getOptions();
/*    */     
/* 48 */     for (int i = 0; i < astring.length; i++) {
/* 49 */       String s = astring[i];
/* 50 */       ShaderOption shaderoption = getShaderOption(s, opts);
/*    */       
/* 52 */       if (shaderoption != null) {
/* 53 */         String s1 = def ? shaderoption.getValueDefault() : shaderoption.getValue();
/* 54 */         String s2 = prof.getValue(s);
/*    */         
/* 56 */         if (!Config.equals(s1, s2)) {
/* 57 */           return false;
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 62 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\ShaderUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */