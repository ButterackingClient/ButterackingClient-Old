/*     */ package net.minecraft.nbt;
/*     */ 
/*     */ import com.google.common.base.Splitter;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Stack;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class JsonToNBT
/*     */ {
/*  14 */   private static final Logger logger = LogManager.getLogger();
/*  15 */   private static final Pattern field_179273_b = Pattern.compile("\\[[-+\\d|,\\s]+\\]");
/*     */   
/*     */   public static NBTTagCompound getTagFromJson(String jsonString) throws NBTException {
/*  18 */     jsonString = jsonString.trim();
/*     */     
/*  20 */     if (!jsonString.startsWith("{"))
/*  21 */       throw new NBTException("Invalid tag encountered, expected '{' as first char."); 
/*  22 */     if (func_150310_b(jsonString) != 1) {
/*  23 */       throw new NBTException("Encountered multiple top tags, only one expected");
/*     */     }
/*  25 */     return (NBTTagCompound)func_150316_a("tag", jsonString).parse();
/*     */   }
/*     */ 
/*     */   
/*     */   static int func_150310_b(String p_150310_0_) throws NBTException {
/*  30 */     int i = 0;
/*  31 */     boolean flag = false;
/*  32 */     Stack<Character> stack = new Stack<>();
/*     */     
/*  34 */     for (int j = 0; j < p_150310_0_.length(); j++) {
/*  35 */       char c0 = p_150310_0_.charAt(j);
/*     */       
/*  37 */       if (c0 == '"') {
/*  38 */         if (func_179271_b(p_150310_0_, j)) {
/*  39 */           if (!flag) {
/*  40 */             throw new NBTException("Illegal use of \\\": " + p_150310_0_);
/*     */           }
/*     */         } else {
/*  43 */           flag = !flag;
/*     */         } 
/*  45 */       } else if (!flag) {
/*  46 */         if (c0 != '{' && c0 != '[') {
/*  47 */           if (c0 == '}' && (stack.isEmpty() || ((Character)stack.pop()).charValue() != '{')) {
/*  48 */             throw new NBTException("Unbalanced curly brackets {}: " + p_150310_0_);
/*     */           }
/*     */           
/*  51 */           if (c0 == ']' && (stack.isEmpty() || ((Character)stack.pop()).charValue() != '[')) {
/*  52 */             throw new NBTException("Unbalanced square brackets []: " + p_150310_0_);
/*     */           }
/*     */         } else {
/*  55 */           if (stack.isEmpty()) {
/*  56 */             i++;
/*     */           }
/*     */           
/*  59 */           stack.push(Character.valueOf(c0));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  64 */     if (flag)
/*  65 */       throw new NBTException("Unbalanced quotation: " + p_150310_0_); 
/*  66 */     if (!stack.isEmpty()) {
/*  67 */       throw new NBTException("Unbalanced brackets: " + p_150310_0_);
/*     */     }
/*  69 */     if (i == 0 && !p_150310_0_.isEmpty()) {
/*  70 */       i = 1;
/*     */     }
/*     */     
/*  73 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   static Any func_179272_a(String... p_179272_0_) throws NBTException {
/*  78 */     return func_150316_a(p_179272_0_[0], p_179272_0_[1]);
/*     */   }
/*     */   
/*     */   static Any func_150316_a(String p_150316_0_, String p_150316_1_) throws NBTException {
/*  82 */     p_150316_1_ = p_150316_1_.trim();
/*     */     
/*  84 */     if (p_150316_1_.startsWith("{")) {
/*  85 */       p_150316_1_ = p_150316_1_.substring(1, p_150316_1_.length() - 1);
/*     */ 
/*     */ 
/*     */       
/*  89 */       for (Compound jsontonbt$compound = new Compound(p_150316_0_); p_150316_1_.length() > 0; p_150316_1_ = p_150316_1_.substring(s1.length() + 1)) {
/*  90 */         String s1 = func_150314_a(p_150316_1_, true);
/*     */         
/*  92 */         if (s1.length() > 0) {
/*  93 */           boolean flag1 = false;
/*  94 */           jsontonbt$compound.field_150491_b.add(func_179270_a(s1, flag1));
/*     */         } 
/*     */         
/*  97 */         if (p_150316_1_.length() < s1.length() + 1) {
/*     */           break;
/*     */         }
/*     */         
/* 101 */         char c1 = p_150316_1_.charAt(s1.length());
/*     */         
/* 103 */         if (c1 != ',' && c1 != '{' && c1 != '}' && c1 != '[' && c1 != ']') {
/* 104 */           throw new NBTException("Unexpected token '" + c1 + "' at: " + p_150316_1_.substring(s1.length()));
/*     */         }
/*     */       } 
/*     */       
/* 108 */       return jsontonbt$compound;
/* 109 */     }  if (p_150316_1_.startsWith("[") && !field_179273_b.matcher(p_150316_1_).matches()) {
/* 110 */       p_150316_1_ = p_150316_1_.substring(1, p_150316_1_.length() - 1);
/*     */ 
/*     */ 
/*     */       
/* 114 */       for (List jsontonbt$list = new List(p_150316_0_); p_150316_1_.length() > 0; p_150316_1_ = p_150316_1_.substring(s.length() + 1)) {
/* 115 */         String s = func_150314_a(p_150316_1_, false);
/*     */         
/* 117 */         if (s.length() > 0) {
/* 118 */           boolean flag = true;
/* 119 */           jsontonbt$list.field_150492_b.add(func_179270_a(s, flag));
/*     */         } 
/*     */         
/* 122 */         if (p_150316_1_.length() < s.length() + 1) {
/*     */           break;
/*     */         }
/*     */         
/* 126 */         char c0 = p_150316_1_.charAt(s.length());
/*     */         
/* 128 */         if (c0 != ',' && c0 != '{' && c0 != '}' && c0 != '[' && c0 != ']') {
/* 129 */           throw new NBTException("Unexpected token '" + c0 + "' at: " + p_150316_1_.substring(s.length()));
/*     */         }
/*     */       } 
/*     */       
/* 133 */       return jsontonbt$list;
/*     */     } 
/* 135 */     return new Primitive(p_150316_0_, p_150316_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   private static Any func_179270_a(String p_179270_0_, boolean p_179270_1_) throws NBTException {
/* 140 */     String s = func_150313_b(p_179270_0_, p_179270_1_);
/* 141 */     String s1 = func_150311_c(p_179270_0_, p_179270_1_);
/* 142 */     return func_179272_a(new String[] { s, s1 });
/*     */   }
/*     */   
/*     */   private static String func_150314_a(String p_150314_0_, boolean p_150314_1_) throws NBTException {
/* 146 */     int i = func_150312_a(p_150314_0_, ':');
/* 147 */     int j = func_150312_a(p_150314_0_, ',');
/*     */     
/* 149 */     if (p_150314_1_) {
/* 150 */       if (i == -1) {
/* 151 */         throw new NBTException("Unable to locate name/value separator for string: " + p_150314_0_);
/*     */       }
/*     */       
/* 154 */       if (j != -1 && j < i) {
/* 155 */         throw new NBTException("Name error at: " + p_150314_0_);
/*     */       }
/* 157 */     } else if (i == -1 || i > j) {
/* 158 */       i = -1;
/*     */     } 
/*     */     
/* 161 */     return func_179269_a(p_150314_0_, i);
/*     */   }
/*     */   
/*     */   private static String func_179269_a(String p_179269_0_, int p_179269_1_) throws NBTException {
/* 165 */     Stack<Character> stack = new Stack<>();
/* 166 */     int i = p_179269_1_ + 1;
/* 167 */     boolean flag = false;
/* 168 */     boolean flag1 = false;
/* 169 */     boolean flag2 = false;
/*     */     
/* 171 */     for (int j = 0; i < p_179269_0_.length(); i++) {
/* 172 */       char c0 = p_179269_0_.charAt(i);
/*     */       
/* 174 */       if (c0 == '"') {
/* 175 */         if (func_179271_b(p_179269_0_, i)) {
/* 176 */           if (!flag) {
/* 177 */             throw new NBTException("Illegal use of \\\": " + p_179269_0_);
/*     */           }
/*     */         } else {
/* 180 */           flag = !flag;
/*     */           
/* 182 */           if (flag && !flag2) {
/* 183 */             flag1 = true;
/*     */           }
/*     */           
/* 186 */           if (!flag) {
/* 187 */             j = i;
/*     */           }
/*     */         } 
/* 190 */       } else if (!flag) {
/* 191 */         if (c0 != '{' && c0 != '[') {
/* 192 */           if (c0 == '}' && (stack.isEmpty() || ((Character)stack.pop()).charValue() != '{')) {
/* 193 */             throw new NBTException("Unbalanced curly brackets {}: " + p_179269_0_);
/*     */           }
/*     */           
/* 196 */           if (c0 == ']' && (stack.isEmpty() || ((Character)stack.pop()).charValue() != '[')) {
/* 197 */             throw new NBTException("Unbalanced square brackets []: " + p_179269_0_);
/*     */           }
/*     */           
/* 200 */           if (c0 == ',' && stack.isEmpty()) {
/* 201 */             return p_179269_0_.substring(0, i);
/*     */           }
/*     */         } else {
/* 204 */           stack.push(Character.valueOf(c0));
/*     */         } 
/*     */       } 
/*     */       
/* 208 */       if (!Character.isWhitespace(c0)) {
/* 209 */         if (!flag && flag1 && j != i) {
/* 210 */           return p_179269_0_.substring(0, j + 1);
/*     */         }
/*     */         
/* 213 */         flag2 = true;
/*     */       } 
/*     */     } 
/*     */     
/* 217 */     return p_179269_0_.substring(0, i);
/*     */   }
/*     */   
/*     */   private static String func_150313_b(String p_150313_0_, boolean p_150313_1_) throws NBTException {
/* 221 */     if (p_150313_1_) {
/* 222 */       p_150313_0_ = p_150313_0_.trim();
/*     */       
/* 224 */       if (p_150313_0_.startsWith("{") || p_150313_0_.startsWith("[")) {
/* 225 */         return "";
/*     */       }
/*     */     } 
/*     */     
/* 229 */     int i = func_150312_a(p_150313_0_, ':');
/*     */     
/* 231 */     if (i == -1) {
/* 232 */       if (p_150313_1_) {
/* 233 */         return "";
/*     */       }
/* 235 */       throw new NBTException("Unable to locate name/value separator for string: " + p_150313_0_);
/*     */     } 
/*     */     
/* 238 */     return p_150313_0_.substring(0, i).trim();
/*     */   }
/*     */ 
/*     */   
/*     */   private static String func_150311_c(String p_150311_0_, boolean p_150311_1_) throws NBTException {
/* 243 */     if (p_150311_1_) {
/* 244 */       p_150311_0_ = p_150311_0_.trim();
/*     */       
/* 246 */       if (p_150311_0_.startsWith("{") || p_150311_0_.startsWith("[")) {
/* 247 */         return p_150311_0_;
/*     */       }
/*     */     } 
/*     */     
/* 251 */     int i = func_150312_a(p_150311_0_, ':');
/*     */     
/* 253 */     if (i == -1) {
/* 254 */       if (p_150311_1_) {
/* 255 */         return p_150311_0_;
/*     */       }
/* 257 */       throw new NBTException("Unable to locate name/value separator for string: " + p_150311_0_);
/*     */     } 
/*     */     
/* 260 */     return p_150311_0_.substring(i + 1).trim();
/*     */   }
/*     */ 
/*     */   
/*     */   private static int func_150312_a(String p_150312_0_, char p_150312_1_) {
/* 265 */     int i = 0;
/*     */     
/* 267 */     for (boolean flag = true; i < p_150312_0_.length(); i++) {
/* 268 */       char c0 = p_150312_0_.charAt(i);
/*     */       
/* 270 */       if (c0 == '"') {
/* 271 */         if (!func_179271_b(p_150312_0_, i)) {
/* 272 */           flag = !flag;
/*     */         }
/* 274 */       } else if (flag) {
/* 275 */         if (c0 == p_150312_1_) {
/* 276 */           return i;
/*     */         }
/*     */         
/* 279 */         if (c0 == '{' || c0 == '[') {
/* 280 */           return -1;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 285 */     return -1;
/*     */   }
/*     */   
/*     */   private static boolean func_179271_b(String p_179271_0_, int p_179271_1_) {
/* 289 */     return (p_179271_1_ > 0 && p_179271_0_.charAt(p_179271_1_ - 1) == '\\' && !func_179271_b(p_179271_0_, p_179271_1_ - 1));
/*     */   }
/*     */   
/*     */   static abstract class Any {
/*     */     protected String json;
/*     */     
/*     */     public abstract NBTBase parse() throws NBTException;
/*     */   }
/*     */   
/*     */   static class Compound extends Any {
/* 299 */     protected java.util.List<JsonToNBT.Any> field_150491_b = Lists.newArrayList();
/*     */     
/*     */     public Compound(String p_i45137_1_) {
/* 302 */       this.json = p_i45137_1_;
/*     */     }
/*     */     
/*     */     public NBTBase parse() throws NBTException {
/* 306 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */       
/* 308 */       for (JsonToNBT.Any jsontonbt$any : this.field_150491_b) {
/* 309 */         nbttagcompound.setTag(jsontonbt$any.json, jsontonbt$any.parse());
/*     */       }
/*     */       
/* 312 */       return nbttagcompound;
/*     */     }
/*     */   }
/*     */   
/*     */   static class List extends Any {
/* 317 */     protected java.util.List<JsonToNBT.Any> field_150492_b = Lists.newArrayList();
/*     */     
/*     */     public List(String json) {
/* 320 */       this.json = json;
/*     */     }
/*     */     
/*     */     public NBTBase parse() throws NBTException {
/* 324 */       NBTTagList nbttaglist = new NBTTagList();
/*     */       
/* 326 */       for (JsonToNBT.Any jsontonbt$any : this.field_150492_b) {
/* 327 */         nbttaglist.appendTag(jsontonbt$any.parse());
/*     */       }
/*     */       
/* 330 */       return nbttaglist;
/*     */     }
/*     */   }
/*     */   
/*     */   static class Primitive extends Any {
/* 335 */     private static final Pattern DOUBLE = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+[d|D]");
/* 336 */     private static final Pattern FLOAT = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+[f|F]");
/* 337 */     private static final Pattern BYTE = Pattern.compile("[-+]?[0-9]+[b|B]");
/* 338 */     private static final Pattern LONG = Pattern.compile("[-+]?[0-9]+[l|L]");
/* 339 */     private static final Pattern SHORT = Pattern.compile("[-+]?[0-9]+[s|S]");
/* 340 */     private static final Pattern INTEGER = Pattern.compile("[-+]?[0-9]+");
/* 341 */     private static final Pattern DOUBLE_UNTYPED = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+");
/* 342 */     private static final Splitter SPLITTER = Splitter.on(',').omitEmptyStrings();
/*     */     protected String jsonValue;
/*     */     
/*     */     public Primitive(String p_i45139_1_, String p_i45139_2_) {
/* 346 */       this.json = p_i45139_1_;
/* 347 */       this.jsonValue = p_i45139_2_;
/*     */     }
/*     */     
/*     */     public NBTBase parse() throws NBTException {
/*     */       try {
/* 352 */         if (DOUBLE.matcher(this.jsonValue).matches()) {
/* 353 */           return new NBTTagDouble(Double.parseDouble(this.jsonValue.substring(0, this.jsonValue.length() - 1)));
/*     */         }
/*     */         
/* 356 */         if (FLOAT.matcher(this.jsonValue).matches()) {
/* 357 */           return new NBTTagFloat(Float.parseFloat(this.jsonValue.substring(0, this.jsonValue.length() - 1)));
/*     */         }
/*     */         
/* 360 */         if (BYTE.matcher(this.jsonValue).matches()) {
/* 361 */           return new NBTTagByte(Byte.parseByte(this.jsonValue.substring(0, this.jsonValue.length() - 1)));
/*     */         }
/*     */         
/* 364 */         if (LONG.matcher(this.jsonValue).matches()) {
/* 365 */           return new NBTTagLong(Long.parseLong(this.jsonValue.substring(0, this.jsonValue.length() - 1)));
/*     */         }
/*     */         
/* 368 */         if (SHORT.matcher(this.jsonValue).matches()) {
/* 369 */           return new NBTTagShort(Short.parseShort(this.jsonValue.substring(0, this.jsonValue.length() - 1)));
/*     */         }
/*     */         
/* 372 */         if (INTEGER.matcher(this.jsonValue).matches()) {
/* 373 */           return new NBTTagInt(Integer.parseInt(this.jsonValue));
/*     */         }
/*     */         
/* 376 */         if (DOUBLE_UNTYPED.matcher(this.jsonValue).matches()) {
/* 377 */           return new NBTTagDouble(Double.parseDouble(this.jsonValue));
/*     */         }
/*     */         
/* 380 */         if (this.jsonValue.equalsIgnoreCase("true") || this.jsonValue.equalsIgnoreCase("false")) {
/* 381 */           return new NBTTagByte((byte)(Boolean.parseBoolean(this.jsonValue) ? 1 : 0));
/*     */         }
/* 383 */       } catch (NumberFormatException var6) {
/* 384 */         this.jsonValue = this.jsonValue.replaceAll("\\\\\"", "\"");
/* 385 */         return new NBTTagString(this.jsonValue);
/*     */       } 
/*     */       
/* 388 */       if (this.jsonValue.startsWith("[") && this.jsonValue.endsWith("]")) {
/* 389 */         String s = this.jsonValue.substring(1, this.jsonValue.length() - 1);
/* 390 */         String[] astring = (String[])Iterables.toArray(SPLITTER.split(s), String.class);
/*     */         
/*     */         try {
/* 393 */           int[] aint = new int[astring.length];
/*     */           
/* 395 */           for (int j = 0; j < astring.length; j++) {
/* 396 */             aint[j] = Integer.parseInt(astring[j].trim());
/*     */           }
/*     */           
/* 399 */           return new NBTTagIntArray(aint);
/* 400 */         } catch (NumberFormatException var5) {
/* 401 */           return new NBTTagString(this.jsonValue);
/*     */         } 
/*     */       } 
/* 404 */       if (this.jsonValue.startsWith("\"") && this.jsonValue.endsWith("\"")) {
/* 405 */         this.jsonValue = this.jsonValue.substring(1, this.jsonValue.length() - 1);
/*     */       }
/*     */       
/* 408 */       this.jsonValue = this.jsonValue.replaceAll("\\\\\"", "\"");
/* 409 */       StringBuilder stringbuilder = new StringBuilder();
/*     */       
/* 411 */       for (int i = 0; i < this.jsonValue.length(); i++) {
/* 412 */         if (i < this.jsonValue.length() - 1 && this.jsonValue.charAt(i) == '\\' && this.jsonValue.charAt(i + 1) == '\\') {
/* 413 */           stringbuilder.append('\\');
/* 414 */           i++;
/*     */         } else {
/* 416 */           stringbuilder.append(this.jsonValue.charAt(i));
/*     */         } 
/*     */       } 
/*     */       
/* 420 */       return new NBTTagString(stringbuilder.toString());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\nbt\JsonToNBT.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */