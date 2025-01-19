/*     */ package net.optifine.shaders.config;
/*     */ 
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Arrays;
/*     */ import java.util.Deque;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.expr.ExpressionParser;
/*     */ import net.optifine.expr.ExpressionType;
/*     */ import net.optifine.expr.IExpression;
/*     */ import net.optifine.expr.IExpressionBool;
/*     */ import net.optifine.expr.IExpressionFloat;
/*     */ import net.optifine.expr.IExpressionResolver;
/*     */ import net.optifine.expr.ParseException;
/*     */ 
/*     */ public class MacroState
/*     */ {
/*     */   private boolean active = true;
/*  24 */   private Deque<Boolean> dequeState = new ArrayDeque<>();
/*  25 */   private Deque<Boolean> dequeResolved = new ArrayDeque<>();
/*  26 */   private Map<String, String> mapMacroValues = new HashMap<>();
/*  27 */   private static final Pattern PATTERN_DIRECTIVE = Pattern.compile("\\s*#\\s*(\\w+)\\s*(.*)");
/*  28 */   private static final Pattern PATTERN_DEFINED = Pattern.compile("defined\\s+(\\w+)");
/*  29 */   private static final Pattern PATTERN_DEFINED_FUNC = Pattern.compile("defined\\s*\\(\\s*(\\w+)\\s*\\)");
/*  30 */   private static final Pattern PATTERN_MACRO = Pattern.compile("(\\w+)");
/*     */   private static final String DEFINE = "define";
/*     */   private static final String UNDEF = "undef";
/*     */   private static final String IFDEF = "ifdef";
/*     */   private static final String IFNDEF = "ifndef";
/*     */   private static final String IF = "if";
/*     */   private static final String ELSE = "else";
/*     */   private static final String ELIF = "elif";
/*     */   private static final String ENDIF = "endif";
/*  39 */   private static final List<String> MACRO_NAMES = Arrays.asList(new String[] { "define", "undef", "ifdef", "ifndef", "if", "else", "elif", "endif" });
/*     */   
/*     */   public boolean processLine(String line) {
/*  42 */     Matcher matcher = PATTERN_DIRECTIVE.matcher(line);
/*     */     
/*  44 */     if (!matcher.matches()) {
/*  45 */       return this.active;
/*     */     }
/*  47 */     String s = matcher.group(1);
/*  48 */     String s1 = matcher.group(2);
/*  49 */     int i = s1.indexOf("//");
/*     */     
/*  51 */     if (i >= 0) {
/*  52 */       s1 = s1.substring(0, i);
/*     */     }
/*     */     
/*  55 */     boolean flag = this.active;
/*  56 */     processMacro(s, s1);
/*  57 */     this.active = !this.dequeState.contains(Boolean.FALSE);
/*  58 */     return !(!this.active && !flag);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isMacroLine(String line) {
/*  63 */     Matcher matcher = PATTERN_DIRECTIVE.matcher(line);
/*     */     
/*  65 */     if (!matcher.matches()) {
/*  66 */       return false;
/*     */     }
/*  68 */     String s = matcher.group(1);
/*  69 */     return MACRO_NAMES.contains(s);
/*     */   }
/*     */ 
/*     */   
/*     */   private void processMacro(String name, String param) {
/*  74 */     StringTokenizer stringtokenizer = new StringTokenizer(param, " \t");
/*  75 */     String s = stringtokenizer.hasMoreTokens() ? stringtokenizer.nextToken() : "";
/*  76 */     String s1 = stringtokenizer.hasMoreTokens() ? stringtokenizer.nextToken("").trim() : "";
/*     */     
/*  78 */     if (name.equals("define")) {
/*  79 */       this.mapMacroValues.put(s, s1);
/*  80 */     } else if (name.equals("undef")) {
/*  81 */       this.mapMacroValues.remove(s);
/*  82 */     } else if (name.equals("ifdef")) {
/*  83 */       boolean flag6 = this.mapMacroValues.containsKey(s);
/*  84 */       this.dequeState.add(Boolean.valueOf(flag6));
/*  85 */       this.dequeResolved.add(Boolean.valueOf(flag6));
/*  86 */     } else if (name.equals("ifndef")) {
/*  87 */       boolean flag5 = !this.mapMacroValues.containsKey(s);
/*  88 */       this.dequeState.add(Boolean.valueOf(flag5));
/*  89 */       this.dequeResolved.add(Boolean.valueOf(flag5));
/*  90 */     } else if (name.equals("if")) {
/*  91 */       boolean flag4 = eval(param);
/*  92 */       this.dequeState.add(Boolean.valueOf(flag4));
/*  93 */       this.dequeResolved.add(Boolean.valueOf(flag4));
/*  94 */     } else if (!this.dequeState.isEmpty()) {
/*  95 */       if (name.equals("elif")) {
/*  96 */         boolean flag3 = ((Boolean)this.dequeState.removeLast()).booleanValue();
/*  97 */         boolean flag7 = ((Boolean)this.dequeResolved.removeLast()).booleanValue();
/*     */         
/*  99 */         if (flag7) {
/* 100 */           this.dequeState.add(Boolean.valueOf(false));
/* 101 */           this.dequeResolved.add(Boolean.valueOf(flag7));
/*     */         } else {
/* 103 */           boolean flag8 = eval(param);
/* 104 */           this.dequeState.add(Boolean.valueOf(flag8));
/* 105 */           this.dequeResolved.add(Boolean.valueOf(flag8));
/*     */         } 
/* 107 */       } else if (name.equals("else")) {
/* 108 */         boolean flag = ((Boolean)this.dequeState.removeLast()).booleanValue();
/* 109 */         boolean flag1 = ((Boolean)this.dequeResolved.removeLast()).booleanValue();
/* 110 */         boolean flag2 = !flag1;
/* 111 */         this.dequeState.add(Boolean.valueOf(flag2));
/* 112 */         this.dequeResolved.add(Boolean.valueOf(true));
/* 113 */       } else if (name.equals("endif")) {
/* 114 */         this.dequeState.removeLast();
/* 115 */         this.dequeResolved.removeLast();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean eval(String str) {
/* 121 */     Matcher matcher = PATTERN_DEFINED.matcher(str);
/* 122 */     str = matcher.replaceAll("defined_$1");
/* 123 */     Matcher matcher1 = PATTERN_DEFINED_FUNC.matcher(str);
/* 124 */     str = matcher1.replaceAll("defined_$1");
/* 125 */     boolean flag = false;
/* 126 */     int i = 0;
/*     */     
/*     */     do {
/* 129 */       flag = false;
/* 130 */       Matcher matcher2 = PATTERN_MACRO.matcher(str);
/*     */       
/* 132 */       while (matcher2.find()) {
/* 133 */         String s = matcher2.group();
/*     */         
/* 135 */         if (s.length() > 0) {
/* 136 */           char c0 = s.charAt(0);
/*     */           
/* 138 */           if ((Character.isLetter(c0) || c0 == '_') && this.mapMacroValues.containsKey(s)) {
/* 139 */             String s1 = this.mapMacroValues.get(s);
/*     */             
/* 141 */             if (s1 == null) {
/* 142 */               s1 = "1";
/*     */             }
/*     */             
/* 145 */             int j = matcher2.start();
/* 146 */             int k = matcher2.end();
/* 147 */             str = String.valueOf(str.substring(0, j)) + " " + s1 + " " + str.substring(k);
/* 148 */             flag = true;
/* 149 */             i++;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/* 155 */     } while (flag && i < 100);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 160 */     if (i >= 100) {
/* 161 */       Config.warn("Too many iterations: " + i + ", when resolving: " + str);
/* 162 */       return true;
/*     */     } 
/*     */     try {
/* 165 */       IExpressionResolver iexpressionresolver = new MacroExpressionResolver(this.mapMacroValues);
/* 166 */       ExpressionParser expressionparser = new ExpressionParser(iexpressionresolver);
/* 167 */       IExpression iexpression = expressionparser.parse(str);
/*     */       
/* 169 */       if (iexpression.getExpressionType() == ExpressionType.BOOL) {
/* 170 */         IExpressionBool iexpressionbool = (IExpressionBool)iexpression;
/* 171 */         boolean flag1 = iexpressionbool.eval();
/* 172 */         return flag1;
/* 173 */       }  if (iexpression.getExpressionType() == ExpressionType.FLOAT) {
/* 174 */         IExpressionFloat iexpressionfloat = (IExpressionFloat)iexpression;
/* 175 */         float f = iexpressionfloat.eval();
/* 176 */         boolean flag2 = (f != 0.0F);
/* 177 */         return flag2;
/*     */       } 
/* 179 */       throw new ParseException("Not a boolean or float expression: " + iexpression.getExpressionType());
/*     */     }
/* 181 */     catch (ParseException parseexception) {
/* 182 */       Config.warn("Invalid macro expression: " + str);
/* 183 */       Config.warn("Error: " + parseexception.getMessage());
/* 184 */       return false;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\config\MacroState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */