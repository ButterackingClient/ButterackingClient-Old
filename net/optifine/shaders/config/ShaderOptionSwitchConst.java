/*    */ package net.optifine.shaders.config;
/*    */ 
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import net.optifine.util.StrUtils;
/*    */ 
/*    */ public class ShaderOptionSwitchConst
/*    */   extends ShaderOptionSwitch {
/*  9 */   private static final Pattern PATTERN_CONST = Pattern.compile("^\\s*const\\s*bool\\s*([A-Za-z0-9_]+)\\s*=\\s*(true|false)\\s*;\\s*(//.*)?$");
/*    */   
/*    */   public ShaderOptionSwitchConst(String name, String description, String value, String path) {
/* 12 */     super(name, description, value, path);
/*    */   }
/*    */   
/*    */   public String getSourceLine() {
/* 16 */     return "const bool " + getName() + " = " + getValue() + "; // Shader option " + getValue();
/*    */   }
/*    */   
/*    */   public static ShaderOption parseOption(String line, String path) {
/* 20 */     Matcher matcher = PATTERN_CONST.matcher(line);
/*    */     
/* 22 */     if (!matcher.matches()) {
/* 23 */       return null;
/*    */     }
/* 25 */     String s = matcher.group(1);
/* 26 */     String s1 = matcher.group(2);
/* 27 */     String s2 = matcher.group(3);
/*    */     
/* 29 */     if (s != null && s.length() > 0) {
/* 30 */       path = StrUtils.removePrefix(path, "/shaders/");
/* 31 */       ShaderOption shaderoption = new ShaderOptionSwitchConst(s, s2, s1, path);
/* 32 */       shaderoption.setVisible(false);
/* 33 */       return shaderoption;
/*    */     } 
/* 35 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean matchesLine(String line) {
/* 41 */     Matcher matcher = PATTERN_CONST.matcher(line);
/*    */     
/* 43 */     if (!matcher.matches()) {
/* 44 */       return false;
/*    */     }
/* 46 */     String s = matcher.group(1);
/* 47 */     return s.matches(getName());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean checkUsed() {
/* 52 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\config\ShaderOptionSwitchConst.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */