/*     */ package net.optifine.shaders.config;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.CharArrayReader;
/*     */ import java.io.CharArrayWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.expr.ExpressionFloatArrayCached;
/*     */ import net.optifine.expr.ExpressionFloatCached;
/*     */ import net.optifine.expr.ExpressionParser;
/*     */ import net.optifine.expr.ExpressionType;
/*     */ import net.optifine.expr.IExpression;
/*     */ import net.optifine.expr.IExpressionBool;
/*     */ import net.optifine.expr.IExpressionFloat;
/*     */ import net.optifine.expr.IExpressionFloatArray;
/*     */ import net.optifine.expr.IExpressionResolver;
/*     */ import net.optifine.expr.ParseException;
/*     */ import net.optifine.render.GlAlphaState;
/*     */ import net.optifine.render.GlBlendState;
/*     */ import net.optifine.shaders.IShaderPack;
/*     */ import net.optifine.shaders.Program;
/*     */ import net.optifine.shaders.SMCLog;
/*     */ import net.optifine.shaders.ShaderUtils;
/*     */ import net.optifine.shaders.Shaders;
/*     */ import net.optifine.shaders.uniform.CustomUniform;
/*     */ import net.optifine.shaders.uniform.CustomUniforms;
/*     */ import net.optifine.shaders.uniform.ShaderExpressionResolver;
/*     */ import net.optifine.shaders.uniform.UniformType;
/*     */ import net.optifine.util.StrUtils;
/*     */ 
/*     */ public class ShaderPackParser {
/*  50 */   private static final Pattern PATTERN_VERSION = Pattern.compile("^\\s*#version\\s+.*$");
/*  51 */   private static final Pattern PATTERN_INCLUDE = Pattern.compile("^\\s*#include\\s+\"([A-Za-z0-9_/\\.]+)\".*$");
/*  52 */   private static final Set<String> setConstNames = makeSetConstNames();
/*  53 */   private static final Map<String, Integer> mapAlphaFuncs = makeMapAlphaFuncs();
/*  54 */   private static final Map<String, Integer> mapBlendFactors = makeMapBlendFactors();
/*     */   
/*     */   public static ShaderOption[] parseShaderPackOptions(IShaderPack shaderPack, String[] programNames, List<Integer> listDimensions) {
/*  57 */     if (shaderPack == null) {
/*  58 */       return new ShaderOption[0];
/*     */     }
/*  60 */     Map<String, ShaderOption> map = new HashMap<>();
/*  61 */     collectShaderOptions(shaderPack, "/shaders", programNames, map);
/*  62 */     Iterator<Integer> iterator = listDimensions.iterator();
/*     */     
/*  64 */     while (iterator.hasNext()) {
/*  65 */       int i = ((Integer)iterator.next()).intValue();
/*  66 */       String s = "/shaders/world" + i;
/*  67 */       collectShaderOptions(shaderPack, s, programNames, map);
/*     */     } 
/*     */     
/*  70 */     Collection<ShaderOption> collection = map.values();
/*  71 */     ShaderOption[] ashaderoption = collection.<ShaderOption>toArray(new ShaderOption[collection.size()]);
/*  72 */     Comparator<ShaderOption> comparator = new Comparator<ShaderOption>() {
/*     */         public int compare(ShaderOption o1, ShaderOption o2) {
/*  74 */           return o1.getName().compareToIgnoreCase(o2.getName());
/*     */         }
/*     */       };
/*  77 */     Arrays.sort(ashaderoption, comparator);
/*  78 */     return ashaderoption;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void collectShaderOptions(IShaderPack shaderPack, String dir, String[] programNames, Map<String, ShaderOption> mapOptions) {
/*  83 */     for (int i = 0; i < programNames.length; i++) {
/*  84 */       String s = programNames[i];
/*     */       
/*  86 */       if (!s.equals("")) {
/*  87 */         String s1 = String.valueOf(dir) + "/" + s + ".vsh";
/*  88 */         String s2 = String.valueOf(dir) + "/" + s + ".fsh";
/*  89 */         collectShaderOptions(shaderPack, s1, mapOptions);
/*  90 */         collectShaderOptions(shaderPack, s2, mapOptions);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void collectShaderOptions(IShaderPack sp, String path, Map<String, ShaderOption> mapOptions) {
/*  96 */     String[] astring = getLines(sp, path);
/*     */     
/*  98 */     for (int i = 0; i < astring.length; i++) {
/*  99 */       String s = astring[i];
/* 100 */       ShaderOption shaderoption = getShaderOption(s, path);
/*     */       
/* 102 */       if (shaderoption != null && !shaderoption.getName().startsWith(ShaderMacros.getPrefixMacro()) && (!shaderoption.checkUsed() || isOptionUsed(shaderoption, astring))) {
/* 103 */         String s1 = shaderoption.getName();
/* 104 */         ShaderOption shaderoption1 = mapOptions.get(s1);
/*     */         
/* 106 */         if (shaderoption1 != null) {
/* 107 */           if (!Config.equals(shaderoption1.getValueDefault(), shaderoption.getValueDefault())) {
/* 108 */             Config.warn("Ambiguous shader option: " + shaderoption.getName());
/* 109 */             Config.warn(" - in " + Config.arrayToString((Object[])shaderoption1.getPaths()) + ": " + shaderoption1.getValueDefault());
/* 110 */             Config.warn(" - in " + Config.arrayToString((Object[])shaderoption.getPaths()) + ": " + shaderoption.getValueDefault());
/* 111 */             shaderoption1.setEnabled(false);
/*     */           } 
/*     */           
/* 114 */           if (shaderoption1.getDescription() == null || shaderoption1.getDescription().length() <= 0) {
/* 115 */             shaderoption1.setDescription(shaderoption.getDescription());
/*     */           }
/*     */           
/* 118 */           shaderoption1.addPaths(shaderoption.getPaths());
/*     */         } else {
/* 120 */           mapOptions.put(s1, shaderoption);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean isOptionUsed(ShaderOption so, String[] lines) {
/* 127 */     for (int i = 0; i < lines.length; i++) {
/* 128 */       String s = lines[i];
/*     */       
/* 130 */       if (so.isUsedInLine(s)) {
/* 131 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 135 */     return false;
/*     */   }
/*     */   
/*     */   private static String[] getLines(IShaderPack sp, String path) {
/*     */     try {
/* 140 */       List<String> list = new ArrayList<>();
/* 141 */       String s = loadFile(path, sp, 0, list, 0);
/*     */       
/* 143 */       if (s == null) {
/* 144 */         return new String[0];
/*     */       }
/* 146 */       ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(s.getBytes());
/* 147 */       String[] astring = Config.readLines(bytearrayinputstream);
/* 148 */       return astring;
/*     */     }
/* 150 */     catch (IOException ioexception) {
/* 151 */       Config.dbg(String.valueOf(ioexception.getClass().getName()) + ": " + ioexception.getMessage());
/* 152 */       return new String[0];
/*     */     } 
/*     */   }
/*     */   
/*     */   private static ShaderOption getShaderOption(String line, String path) {
/* 157 */     ShaderOption shaderoption = null;
/*     */     
/* 159 */     if (shaderoption == null) {
/* 160 */       shaderoption = ShaderOptionSwitch.parseOption(line, path);
/*     */     }
/*     */     
/* 163 */     if (shaderoption == null) {
/* 164 */       shaderoption = ShaderOptionVariable.parseOption(line, path);
/*     */     }
/*     */     
/* 167 */     if (shaderoption != null) {
/* 168 */       return shaderoption;
/*     */     }
/* 170 */     if (shaderoption == null) {
/* 171 */       shaderoption = ShaderOptionSwitchConst.parseOption(line, path);
/*     */     }
/*     */     
/* 174 */     if (shaderoption == null) {
/* 175 */       shaderoption = ShaderOptionVariableConst.parseOption(line, path);
/*     */     }
/*     */     
/* 178 */     return (shaderoption != null && setConstNames.contains(shaderoption.getName())) ? shaderoption : null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Set<String> makeSetConstNames() {
/* 183 */     Set<String> set = new HashSet<>();
/* 184 */     set.add("shadowMapResolution");
/* 185 */     set.add("shadowMapFov");
/* 186 */     set.add("shadowDistance");
/* 187 */     set.add("shadowDistanceRenderMul");
/* 188 */     set.add("shadowIntervalSize");
/* 189 */     set.add("generateShadowMipmap");
/* 190 */     set.add("generateShadowColorMipmap");
/* 191 */     set.add("shadowHardwareFiltering");
/* 192 */     set.add("shadowHardwareFiltering0");
/* 193 */     set.add("shadowHardwareFiltering1");
/* 194 */     set.add("shadowtex0Mipmap");
/* 195 */     set.add("shadowtexMipmap");
/* 196 */     set.add("shadowtex1Mipmap");
/* 197 */     set.add("shadowcolor0Mipmap");
/* 198 */     set.add("shadowColor0Mipmap");
/* 199 */     set.add("shadowcolor1Mipmap");
/* 200 */     set.add("shadowColor1Mipmap");
/* 201 */     set.add("shadowtex0Nearest");
/* 202 */     set.add("shadowtexNearest");
/* 203 */     set.add("shadow0MinMagNearest");
/* 204 */     set.add("shadowtex1Nearest");
/* 205 */     set.add("shadow1MinMagNearest");
/* 206 */     set.add("shadowcolor0Nearest");
/* 207 */     set.add("shadowColor0Nearest");
/* 208 */     set.add("shadowColor0MinMagNearest");
/* 209 */     set.add("shadowcolor1Nearest");
/* 210 */     set.add("shadowColor1Nearest");
/* 211 */     set.add("shadowColor1MinMagNearest");
/* 212 */     set.add("wetnessHalflife");
/* 213 */     set.add("drynessHalflife");
/* 214 */     set.add("eyeBrightnessHalflife");
/* 215 */     set.add("centerDepthHalflife");
/* 216 */     set.add("sunPathRotation");
/* 217 */     set.add("ambientOcclusionLevel");
/* 218 */     set.add("superSamplingLevel");
/* 219 */     set.add("noiseTextureResolution");
/* 220 */     return set;
/*     */   }
/*     */   
/*     */   public static ShaderProfile[] parseProfiles(Properties props, ShaderOption[] shaderOptions) {
/* 224 */     String s = "profile.";
/* 225 */     List<ShaderProfile> list = new ArrayList<>();
/*     */     
/* 227 */     for (Object e : props.keySet()) {
/* 228 */       String s1 = (String)e;
/* 229 */       if (s1.startsWith(s)) {
/* 230 */         String s2 = s1.substring(s.length());
/* 231 */         props.getProperty(s1);
/* 232 */         Set<String> set = new HashSet<>();
/* 233 */         ShaderProfile shaderprofile = parseProfile(s2, props, set, shaderOptions);
/*     */         
/* 235 */         if (shaderprofile != null) {
/* 236 */           list.add(shaderprofile);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 241 */     if (list.size() <= 0) {
/* 242 */       return null;
/*     */     }
/* 244 */     ShaderProfile[] ashaderprofile = list.<ShaderProfile>toArray(new ShaderProfile[list.size()]);
/* 245 */     return ashaderprofile;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Map<String, IExpressionBool> parseProgramConditions(Properties props, ShaderOption[] shaderOptions) {
/* 250 */     String s = "program.";
/* 251 */     Pattern pattern = Pattern.compile("program\\.([^.]+)\\.enabled");
/* 252 */     Map<String, IExpressionBool> map = new HashMap<>();
/*     */     
/* 254 */     for (Object e : props.keySet()) {
/* 255 */       String s1 = (String)e;
/* 256 */       Matcher matcher = pattern.matcher(s1);
/*     */       
/* 258 */       if (matcher.matches()) {
/* 259 */         String s2 = matcher.group(1);
/* 260 */         String s3 = props.getProperty(s1).trim();
/* 261 */         IExpressionBool iexpressionbool = parseOptionExpression(s3, shaderOptions);
/*     */         
/* 263 */         if (iexpressionbool == null) {
/* 264 */           SMCLog.severe("Error parsing program condition: " + s1); continue;
/*     */         } 
/* 266 */         map.put(s2, iexpressionbool);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 271 */     return map;
/*     */   }
/*     */   
/*     */   private static IExpressionBool parseOptionExpression(String val, ShaderOption[] shaderOptions) {
/*     */     try {
/* 276 */       ShaderOptionResolver shaderoptionresolver = new ShaderOptionResolver(shaderOptions);
/* 277 */       ExpressionParser expressionparser = new ExpressionParser(shaderoptionresolver);
/* 278 */       IExpressionBool iexpressionbool = expressionparser.parseBool(val);
/* 279 */       return iexpressionbool;
/* 280 */     } catch (ParseException parseexception) {
/* 281 */       SMCLog.warning(String.valueOf(parseexception.getClass().getName()) + ": " + parseexception.getMessage());
/* 282 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Set<String> parseOptionSliders(Properties props, ShaderOption[] shaderOptions) {
/* 287 */     Set<String> set = new HashSet<>();
/* 288 */     String s = props.getProperty("sliders");
/*     */     
/* 290 */     if (s == null) {
/* 291 */       return set;
/*     */     }
/* 293 */     String[] astring = Config.tokenize(s, " ");
/*     */     
/* 295 */     for (int i = 0; i < astring.length; i++) {
/* 296 */       String s1 = astring[i];
/* 297 */       ShaderOption shaderoption = ShaderUtils.getShaderOption(s1, shaderOptions);
/*     */       
/* 299 */       if (shaderoption == null) {
/* 300 */         Config.warn("Invalid shader option: " + s1);
/*     */       } else {
/* 302 */         set.add(s1);
/*     */       } 
/*     */     } 
/*     */     
/* 306 */     return set;
/*     */   }
/*     */ 
/*     */   
/*     */   private static ShaderProfile parseProfile(String name, Properties props, Set<String> parsedProfiles, ShaderOption[] shaderOptions) {
/* 311 */     String s = "profile.";
/* 312 */     String s1 = String.valueOf(s) + name;
/*     */     
/* 314 */     if (parsedProfiles.contains(s1)) {
/* 315 */       Config.warn("[Shaders] Profile already parsed: " + name);
/* 316 */       return null;
/*     */     } 
/* 318 */     parsedProfiles.add(name);
/* 319 */     ShaderProfile shaderprofile = new ShaderProfile(name);
/* 320 */     String s2 = props.getProperty(s1);
/* 321 */     String[] astring = Config.tokenize(s2, " ");
/*     */     
/* 323 */     for (int i = 0; i < astring.length; i++) {
/* 324 */       String s3 = astring[i];
/*     */       
/* 326 */       if (s3.startsWith(s)) {
/* 327 */         String s4 = s3.substring(s.length());
/* 328 */         ShaderProfile shaderprofile1 = parseProfile(s4, props, parsedProfiles, shaderOptions);
/*     */         
/* 330 */         if (shaderprofile != null) {
/* 331 */           shaderprofile.addOptionValues(shaderprofile1);
/* 332 */           shaderprofile.addDisabledPrograms(shaderprofile1.getDisabledPrograms());
/*     */         } 
/*     */       } else {
/* 335 */         String[] astring1 = Config.tokenize(s3, ":=");
/*     */         
/* 337 */         if (astring1.length == 1) {
/* 338 */           String s7 = astring1[0];
/* 339 */           boolean flag = true;
/*     */           
/* 341 */           if (s7.startsWith("!")) {
/* 342 */             flag = false;
/* 343 */             s7 = s7.substring(1);
/*     */           } 
/*     */           
/* 346 */           String s5 = "program.";
/*     */           
/* 348 */           if (s7.startsWith(s5)) {
/* 349 */             String s6 = s7.substring(s5.length());
/*     */             
/* 351 */             if (!Shaders.isProgramPath(s6)) {
/* 352 */               Config.warn("Invalid program: " + s6 + " in profile: " + shaderprofile.getName());
/* 353 */             } else if (flag) {
/* 354 */               shaderprofile.removeDisabledProgram(s6);
/*     */             } else {
/* 356 */               shaderprofile.addDisabledProgram(s6);
/*     */             } 
/*     */           } else {
/* 359 */             ShaderOption shaderoption1 = ShaderUtils.getShaderOption(s7, shaderOptions);
/*     */             
/* 361 */             if (!(shaderoption1 instanceof ShaderOptionSwitch)) {
/* 362 */               Config.warn("[Shaders] Invalid option: " + s7);
/*     */             } else {
/* 364 */               shaderprofile.addOptionValue(s7, String.valueOf(flag));
/* 365 */               shaderoption1.setVisible(true);
/*     */             } 
/*     */           } 
/* 368 */         } else if (astring1.length != 2) {
/* 369 */           Config.warn("[Shaders] Invalid option value: " + s3);
/*     */         } else {
/* 371 */           String s8 = astring1[0];
/* 372 */           String s9 = astring1[1];
/* 373 */           ShaderOption shaderoption = ShaderUtils.getShaderOption(s8, shaderOptions);
/*     */           
/* 375 */           if (shaderoption == null) {
/* 376 */             Config.warn("[Shaders] Invalid option: " + s3);
/* 377 */           } else if (!shaderoption.isValidValue(s9)) {
/* 378 */             Config.warn("[Shaders] Invalid value: " + s3);
/*     */           } else {
/* 380 */             shaderoption.setVisible(true);
/* 381 */             shaderprofile.addOptionValue(s8, s9);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 387 */     return shaderprofile;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Map<String, ScreenShaderOptions> parseGuiScreens(Properties props, ShaderProfile[] shaderProfiles, ShaderOption[] shaderOptions) {
/* 392 */     Map<String, ScreenShaderOptions> map = new HashMap<>();
/* 393 */     parseGuiScreen("screen", props, map, shaderProfiles, shaderOptions);
/* 394 */     return map.isEmpty() ? null : map;
/*     */   }
/*     */   
/*     */   private static boolean parseGuiScreen(String key, Properties props, Map<String, ScreenShaderOptions> map, ShaderProfile[] shaderProfiles, ShaderOption[] shaderOptions) {
/* 398 */     String s = props.getProperty(key);
/*     */     
/* 400 */     if (s == null) {
/* 401 */       return false;
/*     */     }
/* 403 */     List<ShaderOption> list = new ArrayList<>();
/* 404 */     Set<String> set = new HashSet<>();
/* 405 */     String[] astring = Config.tokenize(s, " ");
/*     */     
/* 407 */     for (int i = 0; i < astring.length; i++) {
/* 408 */       String s1 = astring[i];
/*     */       
/* 410 */       if (s1.equals("<empty>")) {
/* 411 */         list.add(null);
/* 412 */       } else if (set.contains(s1)) {
/* 413 */         Config.warn("[Shaders] Duplicate option: " + s1 + ", key: " + key);
/*     */       } else {
/* 415 */         set.add(s1);
/*     */         
/* 417 */         if (s1.equals("<profile>")) {
/* 418 */           if (shaderProfiles == null) {
/* 419 */             Config.warn("[Shaders] Option profile can not be used, no profiles defined: " + s1 + ", key: " + key);
/*     */           } else {
/* 421 */             ShaderOptionProfile shaderoptionprofile = new ShaderOptionProfile(shaderProfiles, shaderOptions);
/* 422 */             list.add(shaderoptionprofile);
/*     */           } 
/* 424 */         } else if (s1.equals("*")) {
/* 425 */           ShaderOption shaderoption1 = new ShaderOptionRest("<rest>");
/* 426 */           list.add(shaderoption1);
/* 427 */         } else if (s1.startsWith("[") && s1.endsWith("]")) {
/* 428 */           String s3 = StrUtils.removePrefixSuffix(s1, "[", "]");
/*     */           
/* 430 */           if (!s3.matches("^[a-zA-Z0-9_]+$")) {
/* 431 */             Config.warn("[Shaders] Invalid screen: " + s1 + ", key: " + key);
/* 432 */           } else if (!parseGuiScreen("screen." + s3, props, map, shaderProfiles, shaderOptions)) {
/* 433 */             Config.warn("[Shaders] Invalid screen: " + s1 + ", key: " + key);
/*     */           } else {
/* 435 */             ShaderOptionScreen shaderoptionscreen = new ShaderOptionScreen(s3);
/* 436 */             list.add(shaderoptionscreen);
/*     */           } 
/*     */         } else {
/* 439 */           ShaderOption shaderoption = ShaderUtils.getShaderOption(s1, shaderOptions);
/*     */           
/* 441 */           if (shaderoption == null) {
/* 442 */             Config.warn("[Shaders] Invalid option: " + s1 + ", key: " + key);
/* 443 */             list.add(null);
/*     */           } else {
/* 445 */             shaderoption.setVisible(true);
/* 446 */             list.add(shaderoption);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 452 */     ShaderOption[] ashaderoption = list.<ShaderOption>toArray(new ShaderOption[list.size()]);
/* 453 */     String s2 = props.getProperty(String.valueOf(key) + ".columns");
/* 454 */     int j = Config.parseInt(s2, 2);
/* 455 */     ScreenShaderOptions screenshaderoptions = new ScreenShaderOptions(key, ashaderoption, j);
/* 456 */     map.put(key, screenshaderoptions);
/* 457 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static BufferedReader resolveIncludes(BufferedReader reader, String filePath, IShaderPack shaderPack, int fileIndex, List<String> listFiles, int includeLevel) throws IOException {
/* 462 */     String s = "/";
/* 463 */     int i = filePath.lastIndexOf("/");
/*     */     
/* 465 */     if (i >= 0) {
/* 466 */       s = filePath.substring(0, i);
/*     */     }
/*     */     
/* 469 */     CharArrayWriter chararraywriter = new CharArrayWriter();
/* 470 */     int j = -1;
/* 471 */     Set<ShaderMacro> set = new LinkedHashSet<>();
/* 472 */     int k = 1;
/*     */     
/*     */     while (true) {
/* 475 */       String s1 = reader.readLine();
/*     */       
/* 477 */       if (s1 == null) {
/* 478 */         char[] achar = chararraywriter.toCharArray();
/*     */         
/* 480 */         if (j >= 0 && set.size() > 0) {
/* 481 */           StringBuilder stringbuilder = new StringBuilder();
/*     */           
/* 483 */           for (ShaderMacro shadermacro : set) {
/* 484 */             stringbuilder.append("#define ");
/* 485 */             stringbuilder.append(shadermacro.getName());
/* 486 */             stringbuilder.append(" ");
/* 487 */             stringbuilder.append(shadermacro.getValue());
/* 488 */             stringbuilder.append("\n");
/*     */           } 
/*     */           
/* 491 */           String s7 = stringbuilder.toString();
/* 492 */           StringBuilder stringbuilder1 = new StringBuilder(new String(achar));
/* 493 */           stringbuilder1.insert(j, s7);
/* 494 */           String s9 = stringbuilder1.toString();
/* 495 */           achar = s9.toCharArray();
/*     */         } 
/*     */         
/* 498 */         CharArrayReader chararrayreader = new CharArrayReader(achar);
/* 499 */         return new BufferedReader(chararrayreader);
/*     */       } 
/*     */       
/* 502 */       if (j < 0) {
/* 503 */         Matcher matcher = PATTERN_VERSION.matcher(s1);
/*     */         
/* 505 */         if (matcher.matches()) {
/* 506 */           String s2 = String.valueOf(ShaderMacros.getFixedMacroLines()) + ShaderMacros.getOptionMacroLines();
/* 507 */           String s3 = String.valueOf(s1) + "\n" + s2;
/* 508 */           String s4 = "#line " + (k + 1) + " " + fileIndex;
/* 509 */           s1 = String.valueOf(s3) + s4;
/* 510 */           j = chararraywriter.size() + s3.length();
/*     */         } 
/*     */       } 
/*     */       
/* 514 */       Matcher matcher1 = PATTERN_INCLUDE.matcher(s1);
/*     */       
/* 516 */       if (matcher1.matches()) {
/* 517 */         String s6 = matcher1.group(1);
/* 518 */         boolean flag = s6.startsWith("/");
/* 519 */         String s8 = flag ? ("/shaders" + s6) : (String.valueOf(s) + "/" + s6);
/*     */         
/* 521 */         if (!listFiles.contains(s8)) {
/* 522 */           listFiles.add(s8);
/*     */         }
/*     */         
/* 525 */         int l = listFiles.indexOf(s8) + 1;
/* 526 */         s1 = loadFile(s8, shaderPack, l, listFiles, includeLevel);
/*     */         
/* 528 */         if (s1 == null) {
/* 529 */           throw new IOException("Included file not found: " + filePath);
/*     */         }
/*     */         
/* 532 */         if (s1.endsWith("\n")) {
/* 533 */           s1 = s1.substring(0, s1.length() - 1);
/*     */         }
/*     */         
/* 536 */         String s5 = "#line 1 " + l + "\n";
/*     */         
/* 538 */         if (s1.startsWith("#version ")) {
/* 539 */           s5 = "";
/*     */         }
/*     */         
/* 542 */         s1 = String.valueOf(s5) + s1 + "\n" + "#line " + (k + 1) + " " + fileIndex;
/*     */       } 
/*     */       
/* 545 */       if (j >= 0 && s1.contains(ShaderMacros.getPrefixMacro())) {
/* 546 */         ShaderMacro[] ashadermacro = findMacros(s1, ShaderMacros.getExtensions());
/*     */         
/* 548 */         for (int i1 = 0; i1 < ashadermacro.length; i1++) {
/* 549 */           ShaderMacro shadermacro1 = ashadermacro[i1];
/* 550 */           set.add(shadermacro1);
/*     */         } 
/*     */       } 
/*     */       
/* 554 */       chararraywriter.write(s1);
/* 555 */       chararraywriter.write("\n");
/* 556 */       k++;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static ShaderMacro[] findMacros(String line, ShaderMacro[] macros) {
/* 561 */     List<ShaderMacro> list = new ArrayList<>();
/*     */     
/* 563 */     for (int i = 0; i < macros.length; i++) {
/* 564 */       ShaderMacro shadermacro = macros[i];
/*     */       
/* 566 */       if (line.contains(shadermacro.getName())) {
/* 567 */         list.add(shadermacro);
/*     */       }
/*     */     } 
/*     */     
/* 571 */     ShaderMacro[] ashadermacro = list.<ShaderMacro>toArray(new ShaderMacro[list.size()]);
/* 572 */     return ashadermacro;
/*     */   }
/*     */   
/*     */   private static String loadFile(String filePath, IShaderPack shaderPack, int fileIndex, List<String> listFiles, int includeLevel) throws IOException {
/* 576 */     if (includeLevel >= 10) {
/* 577 */       throw new IOException("#include depth exceeded: " + includeLevel + ", file: " + filePath);
/*     */     }
/* 579 */     includeLevel++;
/* 580 */     InputStream inputstream = shaderPack.getResourceAsStream(filePath);
/*     */     
/* 582 */     if (inputstream == null) {
/* 583 */       return null;
/*     */     }
/* 585 */     InputStreamReader inputstreamreader = new InputStreamReader(inputstream, "ASCII");
/* 586 */     BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
/* 587 */     bufferedreader = resolveIncludes(bufferedreader, filePath, shaderPack, fileIndex, listFiles, includeLevel);
/* 588 */     CharArrayWriter chararraywriter = new CharArrayWriter();
/*     */     
/*     */     while (true) {
/* 591 */       String s = bufferedreader.readLine();
/*     */       
/* 593 */       if (s == null) {
/* 594 */         return chararraywriter.toString();
/*     */       }
/*     */       
/* 597 */       chararraywriter.write(s);
/* 598 */       chararraywriter.write("\n");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static CustomUniforms parseCustomUniforms(Properties props) {
/* 605 */     String s = "uniform";
/* 606 */     String s1 = "variable";
/* 607 */     String s2 = String.valueOf(s) + ".";
/* 608 */     String s3 = String.valueOf(s1) + ".";
/* 609 */     Map<String, IExpression> map = new HashMap<>();
/* 610 */     List<CustomUniform> list = new ArrayList<>();
/*     */     
/* 612 */     for (Object e : props.keySet()) {
/* 613 */       String s4 = (String)e;
/* 614 */       String[] astring = Config.tokenize(s4, ".");
/*     */       
/* 616 */       if (astring.length == 3) {
/* 617 */         String s5 = astring[0];
/* 618 */         String s6 = astring[1];
/* 619 */         String s7 = astring[2];
/* 620 */         String s8 = props.getProperty(s4).trim();
/*     */         
/* 622 */         if (map.containsKey(s7)) {
/* 623 */           SMCLog.warning("Expression already defined: " + s7); continue;
/* 624 */         }  if (s5.equals(s) || s5.equals(s1)) {
/* 625 */           SMCLog.info("Custom " + s5 + ": " + s7);
/* 626 */           CustomUniform customuniform = parseCustomUniform(s5, s7, s6, s8, map);
/*     */           
/* 628 */           if (customuniform != null) {
/* 629 */             map.put(s7, customuniform.getExpression());
/*     */             
/* 631 */             if (!s5.equals(s1)) {
/* 632 */               list.add(customuniform);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 639 */     if (list.size() <= 0) {
/* 640 */       return null;
/*     */     }
/* 642 */     CustomUniform[] acustomuniform = list.<CustomUniform>toArray(new CustomUniform[list.size()]);
/* 643 */     CustomUniforms customuniforms = new CustomUniforms(acustomuniform, map);
/* 644 */     return customuniforms;
/*     */   }
/*     */ 
/*     */   
/*     */   private static CustomUniform parseCustomUniform(String kind, String name, String type, String src, Map<String, IExpression> mapExpressions) {
/*     */     try {
/* 650 */       UniformType uniformtype = UniformType.parse(type);
/*     */       
/* 652 */       if (uniformtype == null) {
/* 653 */         SMCLog.warning("Unknown " + kind + " type: " + uniformtype);
/* 654 */         return null;
/*     */       } 
/* 656 */       ShaderExpressionResolver shaderexpressionresolver = new ShaderExpressionResolver(mapExpressions);
/* 657 */       ExpressionParser expressionparser = new ExpressionParser((IExpressionResolver)shaderexpressionresolver);
/* 658 */       IExpression iexpression = expressionparser.parse(src);
/* 659 */       ExpressionType expressiontype = iexpression.getExpressionType();
/*     */       
/* 661 */       if (!uniformtype.matchesExpressionType(expressiontype)) {
/* 662 */         SMCLog.warning("Expression type does not match " + kind + " type, expression: " + expressiontype + ", " + kind + ": " + uniformtype + " " + name);
/* 663 */         return null;
/*     */       } 
/* 665 */       iexpression = makeExpressionCached(iexpression);
/* 666 */       CustomUniform customuniform = new CustomUniform(name, uniformtype, iexpression);
/* 667 */       return customuniform;
/*     */     
/*     */     }
/* 670 */     catch (ParseException parseexception) {
/* 671 */       SMCLog.warning(String.valueOf(parseexception.getClass().getName()) + ": " + parseexception.getMessage());
/* 672 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static IExpression makeExpressionCached(IExpression expr) {
/* 677 */     return (expr instanceof IExpressionFloat) ? (IExpression)new ExpressionFloatCached((IExpressionFloat)expr) : ((expr instanceof IExpressionFloatArray) ? (IExpression)new ExpressionFloatArrayCached((IExpressionFloatArray)expr) : expr);
/*     */   }
/*     */   
/*     */   public static void parseAlphaStates(Properties props) {
/* 681 */     for (Object e : props.keySet()) {
/* 682 */       String s = (String)e;
/* 683 */       String[] astring = Config.tokenize(s, ".");
/*     */       
/* 685 */       if (astring.length == 2) {
/* 686 */         String s1 = astring[0];
/* 687 */         String s2 = astring[1];
/*     */         
/* 689 */         if (s1.equals("alphaTest")) {
/* 690 */           Program program = Shaders.getProgram(s2);
/*     */           
/* 692 */           if (program == null) {
/* 693 */             SMCLog.severe("Invalid program name: " + s2); continue;
/*     */           } 
/* 695 */           String s3 = props.getProperty(s).trim();
/* 696 */           GlAlphaState glalphastate = parseAlphaState(s3);
/*     */           
/* 698 */           if (glalphastate != null) {
/* 699 */             program.setAlphaState(glalphastate);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static GlAlphaState parseAlphaState(String str) {
/* 708 */     String[] astring = Config.tokenize(str, " ");
/*     */     
/* 710 */     if (astring.length == 1) {
/* 711 */       String s = astring[0];
/*     */       
/* 713 */       if (s.equals("off") || s.equals("false")) {
/* 714 */         return new GlAlphaState(false);
/*     */       }
/* 716 */     } else if (astring.length == 2) {
/* 717 */       String s2 = astring[0];
/* 718 */       String s1 = astring[1];
/* 719 */       Integer integer = mapAlphaFuncs.get(s2);
/* 720 */       float f = Config.parseFloat(s1, -1.0F);
/*     */       
/* 722 */       if (integer != null && f >= 0.0F) {
/* 723 */         return new GlAlphaState(true, integer.intValue(), f);
/*     */       }
/*     */     } 
/*     */     
/* 727 */     SMCLog.severe("Invalid alpha test: " + str);
/* 728 */     return null;
/*     */   }
/*     */   
/*     */   public static void parseBlendStates(Properties props) {
/* 732 */     for (Object e : props.keySet()) {
/* 733 */       String s = (String)e;
/* 734 */       String[] astring = Config.tokenize(s, ".");
/*     */       
/* 736 */       if (astring.length == 2) {
/* 737 */         String s1 = astring[0];
/* 738 */         String s2 = astring[1];
/*     */         
/* 740 */         if (s1.equals("blend")) {
/* 741 */           Program program = Shaders.getProgram(s2);
/*     */           
/* 743 */           if (program == null) {
/* 744 */             SMCLog.severe("Invalid program name: " + s2); continue;
/*     */           } 
/* 746 */           String s3 = props.getProperty(s).trim();
/* 747 */           GlBlendState glblendstate = parseBlendState(s3);
/*     */           
/* 749 */           if (glblendstate != null) {
/* 750 */             program.setBlendState(glblendstate);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static GlBlendState parseBlendState(String str) {
/* 759 */     String[] astring = Config.tokenize(str, " ");
/*     */     
/* 761 */     if (astring.length == 1) {
/* 762 */       String s = astring[0];
/*     */       
/* 764 */       if (s.equals("off") || s.equals("false")) {
/* 765 */         return new GlBlendState(false);
/*     */       }
/* 767 */     } else if (astring.length == 2 || astring.length == 4) {
/* 768 */       String s4 = astring[0];
/* 769 */       String s1 = astring[1];
/* 770 */       String s2 = s4;
/* 771 */       String s3 = s1;
/*     */       
/* 773 */       if (astring.length == 4) {
/* 774 */         s2 = astring[2];
/* 775 */         s3 = astring[3];
/*     */       } 
/*     */       
/* 778 */       Integer integer = mapBlendFactors.get(s4);
/* 779 */       Integer integer1 = mapBlendFactors.get(s1);
/* 780 */       Integer integer2 = mapBlendFactors.get(s2);
/* 781 */       Integer integer3 = mapBlendFactors.get(s3);
/*     */       
/* 783 */       if (integer != null && integer1 != null && integer2 != null && integer3 != null) {
/* 784 */         return new GlBlendState(true, integer.intValue(), integer1.intValue(), integer2.intValue(), integer3.intValue());
/*     */       }
/*     */     } 
/*     */     
/* 788 */     SMCLog.severe("Invalid blend mode: " + str);
/* 789 */     return null;
/*     */   }
/*     */   
/*     */   public static void parseRenderScales(Properties props) {
/* 793 */     for (Object e : props.keySet()) {
/* 794 */       String s = (String)e;
/* 795 */       String[] astring = Config.tokenize(s, ".");
/*     */       
/* 797 */       if (astring.length == 2) {
/* 798 */         String s1 = astring[0];
/* 799 */         String s2 = astring[1];
/*     */         
/* 801 */         if (s1.equals("scale")) {
/* 802 */           Program program = Shaders.getProgram(s2);
/*     */           
/* 804 */           if (program == null) {
/* 805 */             SMCLog.severe("Invalid program name: " + s2); continue;
/*     */           } 
/* 807 */           String s3 = props.getProperty(s).trim();
/* 808 */           RenderScale renderscale = parseRenderScale(s3);
/*     */           
/* 810 */           if (renderscale != null) {
/* 811 */             program.setRenderScale(renderscale);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static RenderScale parseRenderScale(String str) {
/* 820 */     String[] astring = Config.tokenize(str, " ");
/* 821 */     float f = Config.parseFloat(astring[0], -1.0F);
/* 822 */     float f1 = 0.0F;
/* 823 */     float f2 = 0.0F;
/*     */     
/* 825 */     if (astring.length > 1) {
/* 826 */       if (astring.length != 3) {
/* 827 */         SMCLog.severe("Invalid render scale: " + str);
/* 828 */         return null;
/*     */       } 
/*     */       
/* 831 */       f1 = Config.parseFloat(astring[1], -1.0F);
/* 832 */       f2 = Config.parseFloat(astring[2], -1.0F);
/*     */     } 
/*     */     
/* 835 */     if (Config.between(f, 0.0F, 1.0F) && Config.between(f1, 0.0F, 1.0F) && Config.between(f2, 0.0F, 1.0F)) {
/* 836 */       return new RenderScale(f, f1, f2);
/*     */     }
/* 838 */     SMCLog.severe("Invalid render scale: " + str);
/* 839 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void parseBuffersFlip(Properties props) {
/* 844 */     for (Object e : props.keySet()) {
/* 845 */       String s = (String)e;
/* 846 */       String[] astring = Config.tokenize(s, ".");
/*     */       
/* 848 */       if (astring.length == 3) {
/* 849 */         String s1 = astring[0];
/* 850 */         String s2 = astring[1];
/* 851 */         String s3 = astring[2];
/*     */         
/* 853 */         if (s1.equals("flip")) {
/* 854 */           Program program = Shaders.getProgram(s2);
/*     */           
/* 856 */           if (program == null) {
/* 857 */             SMCLog.severe("Invalid program name: " + s2); continue;
/*     */           } 
/* 859 */           Boolean[] aboolean = program.getBuffersFlip();
/* 860 */           int i = Shaders.getBufferIndexFromString(s3);
/*     */           
/* 862 */           if (i >= 0 && i < aboolean.length) {
/* 863 */             String s4 = props.getProperty(s).trim();
/* 864 */             Boolean obool = Config.parseBoolean(s4, null);
/*     */             
/* 866 */             if (obool == null) {
/* 867 */               SMCLog.severe("Invalid boolean value: " + s4); continue;
/*     */             } 
/* 869 */             aboolean[i] = obool;
/*     */             continue;
/*     */           } 
/* 872 */           SMCLog.severe("Invalid buffer name: " + s3);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Map<String, Integer> makeMapAlphaFuncs() {
/* 881 */     Map<String, Integer> map = new HashMap<>();
/* 882 */     map.put("NEVER", new Integer(512));
/* 883 */     map.put("LESS", new Integer(513));
/* 884 */     map.put("EQUAL", new Integer(514));
/* 885 */     map.put("LEQUAL", new Integer(515));
/* 886 */     map.put("GREATER", new Integer(516));
/* 887 */     map.put("NOTEQUAL", new Integer(517));
/* 888 */     map.put("GEQUAL", new Integer(518));
/* 889 */     map.put("ALWAYS", new Integer(519));
/* 890 */     return Collections.unmodifiableMap(map);
/*     */   }
/*     */   
/*     */   private static Map<String, Integer> makeMapBlendFactors() {
/* 894 */     Map<String, Integer> map = new HashMap<>();
/* 895 */     map.put("ZERO", new Integer(0));
/* 896 */     map.put("ONE", new Integer(1));
/* 897 */     map.put("SRC_COLOR", new Integer(768));
/* 898 */     map.put("ONE_MINUS_SRC_COLOR", new Integer(769));
/* 899 */     map.put("DST_COLOR", new Integer(774));
/* 900 */     map.put("ONE_MINUS_DST_COLOR", new Integer(775));
/* 901 */     map.put("SRC_ALPHA", new Integer(770));
/* 902 */     map.put("ONE_MINUS_SRC_ALPHA", new Integer(771));
/* 903 */     map.put("DST_ALPHA", new Integer(772));
/* 904 */     map.put("ONE_MINUS_DST_ALPHA", new Integer(773));
/* 905 */     map.put("CONSTANT_COLOR", new Integer(32769));
/* 906 */     map.put("ONE_MINUS_CONSTANT_COLOR", new Integer(32770));
/* 907 */     map.put("CONSTANT_ALPHA", new Integer(32771));
/* 908 */     map.put("ONE_MINUS_CONSTANT_ALPHA", new Integer(32772));
/* 909 */     map.put("SRC_ALPHA_SATURATE", new Integer(776));
/* 910 */     return Collections.unmodifiableMap(map);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\config\ShaderPackParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */