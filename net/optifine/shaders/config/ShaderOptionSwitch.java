/*    */ package net.optifine.shaders.config;
/*    */ 
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import net.minecraft.src.Config;
/*    */ import net.optifine.Lang;
/*    */ import net.optifine.util.StrUtils;
/*    */ 
/*    */ public class ShaderOptionSwitch
/*    */   extends ShaderOption {
/* 11 */   private static final Pattern PATTERN_DEFINE = Pattern.compile("^\\s*(//)?\\s*#define\\s+([A-Za-z0-9_]+)\\s*(//.*)?$");
/* 12 */   private static final Pattern PATTERN_IFDEF = Pattern.compile("^\\s*#if(n)?def\\s+([A-Za-z0-9_]+)(\\s*)?$");
/*    */   
/*    */   public ShaderOptionSwitch(String name, String description, String value, String path) {
/* 15 */     super(name, description, value, new String[] { "false", "true" }, value, path);
/*    */   }
/*    */   
/*    */   public String getSourceLine() {
/* 19 */     return isTrue(getValue()) ? ("#define " + getName() + " // Shader option ON") : ("//#define " + getName() + " // Shader option OFF");
/*    */   }
/*    */   
/*    */   public String getValueText(String val) {
/* 23 */     String s = super.getValueText(val);
/* 24 */     return (s != val) ? s : (isTrue(val) ? Lang.getOn() : Lang.getOff());
/*    */   }
/*    */   
/*    */   public String getValueColor(String val) {
/* 28 */     return isTrue(val) ? "§a" : "§c";
/*    */   }
/*    */   
/*    */   public static ShaderOption parseOption(String line, String path) {
/* 32 */     Matcher matcher = PATTERN_DEFINE.matcher(line);
/*    */     
/* 34 */     if (!matcher.matches()) {
/* 35 */       return null;
/*    */     }
/* 37 */     String s = matcher.group(1);
/* 38 */     String s1 = matcher.group(2);
/* 39 */     String s2 = matcher.group(3);
/*    */     
/* 41 */     if (s1 != null && s1.length() > 0) {
/* 42 */       boolean flag = Config.equals(s, "//");
/* 43 */       boolean flag1 = !flag;
/* 44 */       path = StrUtils.removePrefix(path, "/shaders/");
/* 45 */       ShaderOption shaderoption = new ShaderOptionSwitch(s1, s2, String.valueOf(flag1), path);
/* 46 */       return shaderoption;
/*    */     } 
/* 48 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean matchesLine(String line) {
/* 54 */     Matcher matcher = PATTERN_DEFINE.matcher(line);
/*    */     
/* 56 */     if (!matcher.matches()) {
/* 57 */       return false;
/*    */     }
/* 59 */     String s = matcher.group(2);
/* 60 */     return s.matches(getName());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean checkUsed() {
/* 65 */     return true;
/*    */   }
/*    */   
/*    */   public boolean isUsedInLine(String line) {
/* 69 */     Matcher matcher = PATTERN_IFDEF.matcher(line);
/*    */     
/* 71 */     if (matcher.matches()) {
/* 72 */       String s = matcher.group(2);
/*    */       
/* 74 */       if (s.equals(getName())) {
/* 75 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 79 */     return false;
/*    */   }
/*    */   
/*    */   public static boolean isTrue(String val) {
/* 83 */     return Boolean.valueOf(val).booleanValue();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\config\ShaderOptionSwitch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */