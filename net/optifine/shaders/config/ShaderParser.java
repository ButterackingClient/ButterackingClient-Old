/*     */ package net.optifine.shaders.config;
/*     */ 
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public class ShaderParser {
/*   7 */   public static Pattern PATTERN_UNIFORM = Pattern.compile("\\s*uniform\\s+\\w+\\s+(\\w+).*");
/*   8 */   public static Pattern PATTERN_ATTRIBUTE = Pattern.compile("\\s*attribute\\s+\\w+\\s+(\\w+).*");
/*   9 */   public static Pattern PATTERN_CONST_INT = Pattern.compile("\\s*const\\s+int\\s+(\\w+)\\s*=\\s*([-+.\\w]+)\\s*;.*");
/*  10 */   public static Pattern PATTERN_CONST_FLOAT = Pattern.compile("\\s*const\\s+float\\s+(\\w+)\\s*=\\s*([-+.\\w]+)\\s*;.*");
/*  11 */   public static Pattern PATTERN_CONST_VEC4 = Pattern.compile("\\s*const\\s+vec4\\s+(\\w+)\\s*=\\s*(.+)\\s*;.*");
/*  12 */   public static Pattern PATTERN_CONST_BOOL = Pattern.compile("\\s*const\\s+bool\\s+(\\w+)\\s*=\\s*(\\w+)\\s*;.*");
/*  13 */   public static Pattern PATTERN_PROPERTY = Pattern.compile("\\s*(/\\*|//)?\\s*([A-Z]+):\\s*(\\w+)\\s*(\\*/.*|\\s*)");
/*  14 */   public static Pattern PATTERN_EXTENSION = Pattern.compile("\\s*#\\s*extension\\s+(\\w+)\\s*:\\s*(\\w+).*");
/*  15 */   public static Pattern PATTERN_DEFERRED_FSH = Pattern.compile(".*deferred[0-9]*\\.fsh");
/*  16 */   public static Pattern PATTERN_COMPOSITE_FSH = Pattern.compile(".*composite[0-9]*\\.fsh");
/*  17 */   public static Pattern PATTERN_FINAL_FSH = Pattern.compile(".*final\\.fsh");
/*  18 */   public static Pattern PATTERN_DRAW_BUFFERS = Pattern.compile("[0-7N]*");
/*     */   
/*     */   public static ShaderLine parseLine(String line) {
/*  21 */     Matcher matcher = PATTERN_UNIFORM.matcher(line);
/*     */     
/*  23 */     if (matcher.matches()) {
/*  24 */       return new ShaderLine(1, matcher.group(1), "", line);
/*     */     }
/*  26 */     Matcher matcher1 = PATTERN_ATTRIBUTE.matcher(line);
/*     */     
/*  28 */     if (matcher1.matches()) {
/*  29 */       return new ShaderLine(2, matcher1.group(1), "", line);
/*     */     }
/*  31 */     Matcher matcher2 = PATTERN_PROPERTY.matcher(line);
/*     */     
/*  33 */     if (matcher2.matches()) {
/*  34 */       return new ShaderLine(6, matcher2.group(2), matcher2.group(3), line);
/*     */     }
/*  36 */     Matcher matcher3 = PATTERN_CONST_INT.matcher(line);
/*     */     
/*  38 */     if (matcher3.matches()) {
/*  39 */       return new ShaderLine(3, matcher3.group(1), matcher3.group(2), line);
/*     */     }
/*  41 */     Matcher matcher4 = PATTERN_CONST_FLOAT.matcher(line);
/*     */     
/*  43 */     if (matcher4.matches()) {
/*  44 */       return new ShaderLine(4, matcher4.group(1), matcher4.group(2), line);
/*     */     }
/*  46 */     Matcher matcher5 = PATTERN_CONST_BOOL.matcher(line);
/*     */     
/*  48 */     if (matcher5.matches()) {
/*  49 */       return new ShaderLine(5, matcher5.group(1), matcher5.group(2), line);
/*     */     }
/*  51 */     Matcher matcher6 = PATTERN_EXTENSION.matcher(line);
/*     */     
/*  53 */     if (matcher6.matches()) {
/*  54 */       return new ShaderLine(7, matcher6.group(1), matcher6.group(2), line);
/*     */     }
/*  56 */     Matcher matcher7 = PATTERN_CONST_VEC4.matcher(line);
/*  57 */     return matcher7.matches() ? new ShaderLine(8, matcher7.group(1), matcher7.group(2), line) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getIndex(String uniform, String prefix, int minIndex, int maxIndex) {
/*  68 */     if (uniform.length() != prefix.length() + 1)
/*  69 */       return -1; 
/*  70 */     if (!uniform.startsWith(prefix)) {
/*  71 */       return -1;
/*     */     }
/*  73 */     int i = uniform.charAt(prefix.length()) - 48;
/*  74 */     return (i >= minIndex && i <= maxIndex) ? i : -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getShadowDepthIndex(String uniform) {
/*  79 */     return uniform.equals("shadow") ? 0 : (uniform.equals("watershadow") ? 1 : getIndex(uniform, "shadowtex", 0, 1));
/*     */   }
/*     */   
/*     */   public static int getShadowColorIndex(String uniform) {
/*  83 */     return uniform.equals("shadowcolor") ? 0 : getIndex(uniform, "shadowcolor", 0, 1);
/*     */   }
/*     */   
/*     */   public static int getDepthIndex(String uniform) {
/*  87 */     return getIndex(uniform, "depthtex", 0, 2);
/*     */   }
/*     */   
/*     */   public static int getColorIndex(String uniform) {
/*  91 */     int i = getIndex(uniform, "gaux", 1, 4);
/*  92 */     return (i > 0) ? (i + 3) : getIndex(uniform, "colortex", 4, 7);
/*     */   }
/*     */   
/*     */   public static boolean isDeferred(String filename) {
/*  96 */     return PATTERN_DEFERRED_FSH.matcher(filename).matches();
/*     */   }
/*     */   
/*     */   public static boolean isComposite(String filename) {
/* 100 */     return PATTERN_COMPOSITE_FSH.matcher(filename).matches();
/*     */   }
/*     */   
/*     */   public static boolean isFinal(String filename) {
/* 104 */     return PATTERN_FINAL_FSH.matcher(filename).matches();
/*     */   }
/*     */   
/*     */   public static boolean isValidDrawBuffers(String str) {
/* 108 */     return PATTERN_DRAW_BUFFERS.matcher(str).matches();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\config\ShaderParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */