/*     */ package net.optifine.config;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagByte;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagDouble;
/*     */ import net.minecraft.nbt.NBTTagFloat;
/*     */ import net.minecraft.nbt.NBTTagInt;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.nbt.NBTTagLong;
/*     */ import net.minecraft.nbt.NBTTagShort;
/*     */ import net.minecraft.nbt.NBTTagString;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.util.StrUtils;
/*     */ import org.apache.commons.lang3.StringEscapeUtils;
/*     */ 
/*     */ public class NbtTagValue
/*     */ {
/*  21 */   private String[] parents = null;
/*  22 */   private String name = null;
/*     */   private boolean negative = false;
/*  24 */   private int type = 0;
/*  25 */   private String value = null;
/*  26 */   private int valueFormat = 0;
/*     */   private static final int TYPE_TEXT = 0;
/*     */   private static final int TYPE_PATTERN = 1;
/*     */   private static final int TYPE_IPATTERN = 2;
/*     */   private static final int TYPE_REGEX = 3;
/*     */   private static final int TYPE_IREGEX = 4;
/*     */   private static final String PREFIX_PATTERN = "pattern:";
/*     */   private static final String PREFIX_IPATTERN = "ipattern:";
/*     */   private static final String PREFIX_REGEX = "regex:";
/*     */   private static final String PREFIX_IREGEX = "iregex:";
/*     */   private static final int FORMAT_DEFAULT = 0;
/*     */   private static final int FORMAT_HEX_COLOR = 1;
/*     */   private static final String PREFIX_HEX_COLOR = "#";
/*  39 */   private static final Pattern PATTERN_HEX_COLOR = Pattern.compile("^#[0-9a-f]{6}+$");
/*     */   
/*     */   public NbtTagValue(String tag, String value) {
/*  42 */     String[] astring = Config.tokenize(tag, ".");
/*  43 */     this.parents = Arrays.<String>copyOfRange(astring, 0, astring.length - 1);
/*  44 */     this.name = astring[astring.length - 1];
/*     */     
/*  46 */     if (value.startsWith("!")) {
/*  47 */       this.negative = true;
/*  48 */       value = value.substring(1);
/*     */     } 
/*     */     
/*  51 */     if (value.startsWith("pattern:")) {
/*  52 */       this.type = 1;
/*  53 */       value = value.substring("pattern:".length());
/*  54 */     } else if (value.startsWith("ipattern:")) {
/*  55 */       this.type = 2;
/*  56 */       value = value.substring("ipattern:".length()).toLowerCase();
/*  57 */     } else if (value.startsWith("regex:")) {
/*  58 */       this.type = 3;
/*  59 */       value = value.substring("regex:".length());
/*  60 */     } else if (value.startsWith("iregex:")) {
/*  61 */       this.type = 4;
/*  62 */       value = value.substring("iregex:".length()).toLowerCase();
/*     */     } else {
/*  64 */       this.type = 0;
/*     */     } 
/*     */     
/*  67 */     value = StringEscapeUtils.unescapeJava(value);
/*     */     
/*  69 */     if (this.type == 0 && PATTERN_HEX_COLOR.matcher(value).matches()) {
/*  70 */       this.valueFormat = 1;
/*     */     }
/*     */     
/*  73 */     this.value = value;
/*     */   }
/*     */   
/*     */   public boolean matches(NBTTagCompound nbt) {
/*  77 */     return this.negative ? (!matchesCompound(nbt)) : matchesCompound(nbt);
/*     */   }
/*     */   
/*     */   public boolean matchesCompound(NBTTagCompound nbt) {
/*  81 */     if (nbt == null) {
/*  82 */       return false;
/*     */     }
/*  84 */     NBTTagCompound nBTTagCompound = nbt;
/*     */     
/*  86 */     for (int i = 0; i < this.parents.length; i++) {
/*  87 */       String s = this.parents[i];
/*  88 */       nBTBase = getChildTag((NBTBase)nBTTagCompound, s);
/*     */       
/*  90 */       if (nBTBase == null) {
/*  91 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  95 */     if (this.name.equals("*")) {
/*  96 */       return matchesAnyChild(nBTBase);
/*     */     }
/*  98 */     NBTBase nBTBase = getChildTag(nBTBase, this.name);
/*     */     
/* 100 */     if (nBTBase == null)
/* 101 */       return false; 
/* 102 */     if (matchesBase(nBTBase)) {
/* 103 */       return true;
/*     */     }
/* 105 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean matchesAnyChild(NBTBase tagBase) {
/* 112 */     if (tagBase instanceof NBTTagCompound) {
/* 113 */       NBTTagCompound nbttagcompound = (NBTTagCompound)tagBase;
/*     */       
/* 115 */       for (String s : nbttagcompound.getKeySet()) {
/* 116 */         NBTBase nbtbase = nbttagcompound.getTag(s);
/*     */         
/* 118 */         if (matchesBase(nbtbase)) {
/* 119 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 124 */     if (tagBase instanceof NBTTagList) {
/* 125 */       NBTTagList nbttaglist = (NBTTagList)tagBase;
/* 126 */       int i = nbttaglist.tagCount();
/*     */       
/* 128 */       for (int j = 0; j < i; j++) {
/* 129 */         NBTBase nbtbase1 = nbttaglist.get(j);
/*     */         
/* 131 */         if (matchesBase(nbtbase1)) {
/* 132 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 137 */     return false;
/*     */   }
/*     */   
/*     */   private static NBTBase getChildTag(NBTBase tagBase, String tag) {
/* 141 */     if (tagBase instanceof NBTTagCompound) {
/* 142 */       NBTTagCompound nbttagcompound = (NBTTagCompound)tagBase;
/* 143 */       return nbttagcompound.getTag(tag);
/* 144 */     }  if (tagBase instanceof NBTTagList) {
/* 145 */       NBTTagList nbttaglist = (NBTTagList)tagBase;
/*     */       
/* 147 */       if (tag.equals("count")) {
/* 148 */         return (NBTBase)new NBTTagInt(nbttaglist.tagCount());
/*     */       }
/* 150 */       int i = Config.parseInt(tag, -1);
/* 151 */       return (i >= 0 && i < nbttaglist.tagCount()) ? nbttaglist.get(i) : null;
/*     */     } 
/*     */     
/* 154 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean matchesBase(NBTBase nbtBase) {
/* 159 */     if (nbtBase == null) {
/* 160 */       return false;
/*     */     }
/* 162 */     String s = getNbtString(nbtBase, this.valueFormat);
/* 163 */     return matchesValue(s);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean matchesValue(String nbtValue) {
/* 168 */     if (nbtValue == null) {
/* 169 */       return false;
/*     */     }
/* 171 */     switch (this.type) {
/*     */       case 0:
/* 173 */         return nbtValue.equals(this.value);
/*     */       
/*     */       case 1:
/* 176 */         return matchesPattern(nbtValue, this.value);
/*     */       
/*     */       case 2:
/* 179 */         return matchesPattern(nbtValue.toLowerCase(), this.value);
/*     */       
/*     */       case 3:
/* 182 */         return matchesRegex(nbtValue, this.value);
/*     */       
/*     */       case 4:
/* 185 */         return matchesRegex(nbtValue.toLowerCase(), this.value);
/*     */     } 
/*     */     
/* 188 */     throw new IllegalArgumentException("Unknown NbtTagValue type: " + this.type);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean matchesPattern(String str, String pattern) {
/* 194 */     return StrUtils.equalsMask(str, pattern, '*', '?');
/*     */   }
/*     */   
/*     */   private boolean matchesRegex(String str, String regex) {
/* 198 */     return str.matches(regex);
/*     */   }
/*     */   
/*     */   private static String getNbtString(NBTBase nbtBase, int format) {
/* 202 */     if (nbtBase == null)
/* 203 */       return null; 
/* 204 */     if (nbtBase instanceof NBTTagString) {
/* 205 */       NBTTagString nbttagstring = (NBTTagString)nbtBase;
/* 206 */       return nbttagstring.getString();
/* 207 */     }  if (nbtBase instanceof NBTTagInt) {
/* 208 */       NBTTagInt nbttagint = (NBTTagInt)nbtBase;
/* 209 */       return (format == 1) ? ("#" + StrUtils.fillLeft(Integer.toHexString(nbttagint.getInt()), 6, '0')) : Integer.toString(nbttagint.getInt());
/* 210 */     }  if (nbtBase instanceof NBTTagByte) {
/* 211 */       NBTTagByte nbttagbyte = (NBTTagByte)nbtBase;
/* 212 */       return Byte.toString(nbttagbyte.getByte());
/* 213 */     }  if (nbtBase instanceof NBTTagShort) {
/* 214 */       NBTTagShort nbttagshort = (NBTTagShort)nbtBase;
/* 215 */       return Short.toString(nbttagshort.getShort());
/* 216 */     }  if (nbtBase instanceof NBTTagLong) {
/* 217 */       NBTTagLong nbttaglong = (NBTTagLong)nbtBase;
/* 218 */       return Long.toString(nbttaglong.getLong());
/* 219 */     }  if (nbtBase instanceof NBTTagFloat) {
/* 220 */       NBTTagFloat nbttagfloat = (NBTTagFloat)nbtBase;
/* 221 */       return Float.toString(nbttagfloat.getFloat());
/* 222 */     }  if (nbtBase instanceof NBTTagDouble) {
/* 223 */       NBTTagDouble nbttagdouble = (NBTTagDouble)nbtBase;
/* 224 */       return Double.toString(nbttagdouble.getDouble());
/*     */     } 
/* 226 */     return nbtBase.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 231 */     StringBuffer stringbuffer = new StringBuffer();
/*     */     
/* 233 */     for (int i = 0; i < this.parents.length; i++) {
/* 234 */       String s = this.parents[i];
/*     */       
/* 236 */       if (i > 0) {
/* 237 */         stringbuffer.append(".");
/*     */       }
/*     */       
/* 240 */       stringbuffer.append(s);
/*     */     } 
/*     */     
/* 243 */     if (stringbuffer.length() > 0) {
/* 244 */       stringbuffer.append(".");
/*     */     }
/*     */     
/* 247 */     stringbuffer.append(this.name);
/* 248 */     stringbuffer.append(" = ");
/* 249 */     stringbuffer.append(this.value);
/* 250 */     return stringbuffer.toString();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\config\NbtTagValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */