/*    */ package net.optifine.shaders.config;
/*    */ 
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import net.optifine.util.StrUtils;
/*    */ 
/*    */ public class ShaderOptionVariableConst
/*    */   extends ShaderOptionVariable {
/*  9 */   private String type = null;
/* 10 */   private static final Pattern PATTERN_CONST = Pattern.compile("^\\s*const\\s*(float|int)\\s*([A-Za-z0-9_]+)\\s*=\\s*(-?[0-9\\.]+f?F?)\\s*;\\s*(//.*)?$");
/*    */   
/*    */   public ShaderOptionVariableConst(String name, String type, String description, String value, String[] values, String path) {
/* 13 */     super(name, description, value, values, path);
/* 14 */     this.type = type;
/*    */   }
/*    */   
/*    */   public String getSourceLine() {
/* 18 */     return "const " + this.type + " " + getName() + " = " + getValue() + "; // Shader option " + getValue();
/*    */   }
/*    */   
/*    */   public boolean matchesLine(String line) {
/* 22 */     Matcher matcher = PATTERN_CONST.matcher(line);
/*    */     
/* 24 */     if (!matcher.matches()) {
/* 25 */       return false;
/*    */     }
/* 27 */     String s = matcher.group(2);
/* 28 */     return s.matches(getName());
/*    */   }
/*    */ 
/*    */   
/*    */   public static ShaderOption parseOption(String line, String path) {
/* 33 */     Matcher matcher = PATTERN_CONST.matcher(line);
/*    */     
/* 35 */     if (!matcher.matches()) {
/* 36 */       return null;
/*    */     }
/* 38 */     String s = matcher.group(1);
/* 39 */     String s1 = matcher.group(2);
/* 40 */     String s2 = matcher.group(3);
/* 41 */     String s3 = matcher.group(4);
/* 42 */     String s4 = StrUtils.getSegment(s3, "[", "]");
/*    */     
/* 44 */     if (s4 != null && s4.length() > 0) {
/* 45 */       s3 = s3.replace(s4, "").trim();
/*    */     }
/*    */     
/* 48 */     String[] astring = parseValues(s2, s4);
/*    */     
/* 50 */     if (s1 != null && s1.length() > 0) {
/* 51 */       path = StrUtils.removePrefix(path, "/shaders/");
/* 52 */       ShaderOption shaderoption = new ShaderOptionVariableConst(s1, s, s3, s2, astring, path);
/* 53 */       return shaderoption;
/*    */     } 
/* 55 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\config\ShaderOptionVariableConst.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */