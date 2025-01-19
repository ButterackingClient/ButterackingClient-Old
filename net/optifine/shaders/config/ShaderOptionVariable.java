/*    */ package net.optifine.shaders.config;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import net.minecraft.src.Config;
/*    */ import net.optifine.shaders.Shaders;
/*    */ import net.optifine.util.StrUtils;
/*    */ 
/*    */ public class ShaderOptionVariable
/*    */   extends ShaderOption {
/* 12 */   private static final Pattern PATTERN_VARIABLE = Pattern.compile("^\\s*#define\\s+(\\w+)\\s+(-?[0-9\\.Ff]+|\\w+)\\s*(//.*)?$");
/*    */   
/*    */   public ShaderOptionVariable(String name, String description, String value, String[] values, String path) {
/* 15 */     super(name, description, value, values, value, path);
/* 16 */     setVisible(((getValues()).length > 1));
/*    */   }
/*    */   
/*    */   public String getSourceLine() {
/* 20 */     return "#define " + getName() + " " + getValue() + " // Shader option " + getValue();
/*    */   }
/*    */   
/*    */   public String getValueText(String val) {
/* 24 */     String s = Shaders.translate("prefix." + getName(), "");
/* 25 */     String s1 = super.getValueText(val);
/* 26 */     String s2 = Shaders.translate("suffix." + getName(), "");
/* 27 */     String s3 = String.valueOf(s) + s1 + s2;
/* 28 */     return s3;
/*    */   }
/*    */   
/*    */   public String getValueColor(String val) {
/* 32 */     String s = val.toLowerCase();
/* 33 */     return (!s.equals("false") && !s.equals("off")) ? "§a" : "§c";
/*    */   }
/*    */   
/*    */   public boolean matchesLine(String line) {
/* 37 */     Matcher matcher = PATTERN_VARIABLE.matcher(line);
/*    */     
/* 39 */     if (!matcher.matches()) {
/* 40 */       return false;
/*    */     }
/* 42 */     String s = matcher.group(1);
/* 43 */     return s.matches(getName());
/*    */   }
/*    */ 
/*    */   
/*    */   public static ShaderOption parseOption(String line, String path) {
/* 48 */     Matcher matcher = PATTERN_VARIABLE.matcher(line);
/*    */     
/* 50 */     if (!matcher.matches()) {
/* 51 */       return null;
/*    */     }
/* 53 */     String s = matcher.group(1);
/* 54 */     String s1 = matcher.group(2);
/* 55 */     String s2 = matcher.group(3);
/* 56 */     String s3 = StrUtils.getSegment(s2, "[", "]");
/*    */     
/* 58 */     if (s3 != null && s3.length() > 0) {
/* 59 */       s2 = s2.replace(s3, "").trim();
/*    */     }
/*    */     
/* 62 */     String[] astring = parseValues(s1, s3);
/*    */     
/* 64 */     if (s != null && s.length() > 0) {
/* 65 */       path = StrUtils.removePrefix(path, "/shaders/");
/* 66 */       ShaderOption shaderoption = new ShaderOptionVariable(s, s2, s1, astring, path);
/* 67 */       return shaderoption;
/*    */     } 
/* 69 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static String[] parseValues(String value, String valuesStr) {
/* 75 */     String[] astring = { value };
/*    */     
/* 77 */     if (valuesStr == null) {
/* 78 */       return astring;
/*    */     }
/* 80 */     valuesStr = valuesStr.trim();
/* 81 */     valuesStr = StrUtils.removePrefix(valuesStr, "[");
/* 82 */     valuesStr = StrUtils.removeSuffix(valuesStr, "]");
/* 83 */     valuesStr = valuesStr.trim();
/*    */     
/* 85 */     if (valuesStr.length() <= 0) {
/* 86 */       return astring;
/*    */     }
/* 88 */     String[] astring1 = Config.tokenize(valuesStr, " ");
/*    */     
/* 90 */     if (astring1.length <= 0) {
/* 91 */       return astring;
/*    */     }
/* 93 */     if (!Arrays.<String>asList(astring1).contains(value)) {
/* 94 */       astring1 = (String[])Config.addObjectToArray((Object[])astring1, value, 0);
/*    */     }
/*    */     
/* 97 */     return astring1;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\config\ShaderOptionVariable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */