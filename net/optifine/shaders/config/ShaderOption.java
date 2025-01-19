/*     */ package net.optifine.shaders.config;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.shaders.Shaders;
/*     */ import net.optifine.util.StrUtils;
/*     */ 
/*     */ public abstract class ShaderOption
/*     */ {
/*  11 */   private String name = null;
/*  12 */   private String description = null;
/*  13 */   private String value = null;
/*  14 */   private String[] values = null;
/*  15 */   private String valueDefault = null;
/*  16 */   private String[] paths = null;
/*     */   private boolean enabled = true;
/*     */   private boolean visible = true;
/*     */   public static final String COLOR_GREEN = "§a";
/*     */   public static final String COLOR_RED = "§c";
/*     */   public static final String COLOR_BLUE = "§9";
/*     */   
/*     */   public ShaderOption(String name, String description, String value, String[] values, String valueDefault, String path) {
/*  24 */     this.name = name;
/*  25 */     this.description = description;
/*  26 */     this.value = value;
/*  27 */     this.values = values;
/*  28 */     this.valueDefault = valueDefault;
/*     */     
/*  30 */     if (path != null) {
/*  31 */       this.paths = new String[] { path };
/*     */     }
/*     */   }
/*     */   
/*     */   public String getName() {
/*  36 */     return this.name;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/*  40 */     return this.description;
/*     */   }
/*     */   
/*     */   public String getDescriptionText() {
/*  44 */     String s = Config.normalize(this.description);
/*  45 */     s = StrUtils.removePrefix(s, "//");
/*  46 */     s = Shaders.translate("option." + getName() + ".comment", s);
/*  47 */     return s;
/*     */   }
/*     */   
/*     */   public void setDescription(String description) {
/*  51 */     this.description = description;
/*     */   }
/*     */   
/*     */   public String getValue() {
/*  55 */     return this.value;
/*     */   }
/*     */   
/*     */   public boolean setValue(String value) {
/*  59 */     int i = getIndex(value, this.values);
/*     */     
/*  61 */     if (i < 0) {
/*  62 */       return false;
/*     */     }
/*  64 */     this.value = value;
/*  65 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getValueDefault() {
/*  70 */     return this.valueDefault;
/*     */   }
/*     */   
/*     */   public void resetValue() {
/*  74 */     this.value = this.valueDefault;
/*     */   }
/*     */   
/*     */   public void nextValue() {
/*  78 */     int i = getIndex(this.value, this.values);
/*     */     
/*  80 */     if (i >= 0) {
/*  81 */       i = (i + 1) % this.values.length;
/*  82 */       this.value = this.values[i];
/*     */     } 
/*     */   }
/*     */   
/*     */   public void prevValue() {
/*  87 */     int i = getIndex(this.value, this.values);
/*     */     
/*  89 */     if (i >= 0) {
/*  90 */       i = (i - 1 + this.values.length) % this.values.length;
/*  91 */       this.value = this.values[i];
/*     */     } 
/*     */   }
/*     */   
/*     */   private static int getIndex(String str, String[] strs) {
/*  96 */     for (int i = 0; i < strs.length; i++) {
/*  97 */       String s = strs[i];
/*     */       
/*  99 */       if (s.equals(str)) {
/* 100 */         return i;
/*     */       }
/*     */     } 
/*     */     
/* 104 */     return -1;
/*     */   }
/*     */   
/*     */   public String[] getPaths() {
/* 108 */     return this.paths;
/*     */   }
/*     */   
/*     */   public void addPaths(String[] newPaths) {
/* 112 */     List<String> list = Arrays.asList(this.paths);
/*     */     
/* 114 */     for (int i = 0; i < newPaths.length; i++) {
/* 115 */       String s = newPaths[i];
/*     */       
/* 117 */       if (!list.contains(s)) {
/* 118 */         this.paths = (String[])Config.addObjectToArray((Object[])this.paths, s);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isEnabled() {
/* 124 */     return this.enabled;
/*     */   }
/*     */   
/*     */   public void setEnabled(boolean enabled) {
/* 128 */     this.enabled = enabled;
/*     */   }
/*     */   
/*     */   public boolean isChanged() {
/* 132 */     return !Config.equals(this.value, this.valueDefault);
/*     */   }
/*     */   
/*     */   public boolean isVisible() {
/* 136 */     return this.visible;
/*     */   }
/*     */   
/*     */   public void setVisible(boolean visible) {
/* 140 */     this.visible = visible;
/*     */   }
/*     */   
/*     */   public boolean isValidValue(String val) {
/* 144 */     return (getIndex(val, this.values) >= 0);
/*     */   }
/*     */   
/*     */   public String getNameText() {
/* 148 */     return Shaders.translate("option." + this.name, this.name);
/*     */   }
/*     */   
/*     */   public String getValueText(String val) {
/* 152 */     return Shaders.translate("value." + this.name + "." + val, val);
/*     */   }
/*     */   
/*     */   public String getValueColor(String val) {
/* 156 */     return "";
/*     */   }
/*     */   
/*     */   public boolean matchesLine(String line) {
/* 160 */     return false;
/*     */   }
/*     */   
/*     */   public boolean checkUsed() {
/* 164 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isUsedInLine(String line) {
/* 168 */     return false;
/*     */   }
/*     */   
/*     */   public String getSourceLine() {
/* 172 */     return null;
/*     */   }
/*     */   
/*     */   public String[] getValues() {
/* 176 */     return (String[])this.values.clone();
/*     */   }
/*     */   
/*     */   public float getIndexNormalized() {
/* 180 */     if (this.values.length <= 1) {
/* 181 */       return 0.0F;
/*     */     }
/* 183 */     int i = getIndex(this.value, this.values);
/*     */     
/* 185 */     if (i < 0) {
/* 186 */       return 0.0F;
/*     */     }
/* 188 */     float f = 1.0F * i / (this.values.length - 1.0F);
/* 189 */     return f;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIndexNormalized(float f) {
/* 195 */     if (this.values.length > 1) {
/* 196 */       f = Config.limit(f, 0.0F, 1.0F);
/* 197 */       int i = Math.round(f * (this.values.length - 1));
/* 198 */       this.value = this.values[i];
/*     */     } 
/*     */   }
/*     */   
/*     */   public String toString() {
/* 203 */     return this.name + ", value: " + this.value + ", valueDefault: " + this.valueDefault + ", paths: " + Config.arrayToString((Object[])this.paths);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\config\ShaderOption.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */