/*     */ package net.optifine.shaders.config;
/*     */ 
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.util.StrUtils;
/*     */ import org.lwjgl.util.vector.Vector4f;
/*     */ 
/*     */ public class ShaderLine {
/*     */   private int type;
/*     */   private String name;
/*     */   private String value;
/*     */   private String line;
/*     */   public static final int TYPE_UNIFORM = 1;
/*     */   public static final int TYPE_ATTRIBUTE = 2;
/*     */   public static final int TYPE_CONST_INT = 3;
/*     */   public static final int TYPE_CONST_FLOAT = 4;
/*     */   public static final int TYPE_CONST_BOOL = 5;
/*     */   public static final int TYPE_PROPERTY = 6;
/*     */   public static final int TYPE_EXTENSION = 7;
/*     */   public static final int TYPE_CONST_VEC4 = 8;
/*     */   
/*     */   public ShaderLine(int type, String name, String value, String line) {
/*  22 */     this.type = type;
/*  23 */     this.name = name;
/*  24 */     this.value = value;
/*  25 */     this.line = line;
/*     */   }
/*     */   
/*     */   public int getType() {
/*  29 */     return this.type;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  33 */     return this.name;
/*     */   }
/*     */   
/*     */   public String getValue() {
/*  37 */     return this.value;
/*     */   }
/*     */   
/*     */   public boolean isUniform() {
/*  41 */     return (this.type == 1);
/*     */   }
/*     */   
/*     */   public boolean isUniform(String name) {
/*  45 */     return (isUniform() && name.equals(this.name));
/*     */   }
/*     */   
/*     */   public boolean isAttribute() {
/*  49 */     return (this.type == 2);
/*     */   }
/*     */   
/*     */   public boolean isAttribute(String name) {
/*  53 */     return (isAttribute() && name.equals(this.name));
/*     */   }
/*     */   
/*     */   public boolean isProperty() {
/*  57 */     return (this.type == 6);
/*     */   }
/*     */   
/*     */   public boolean isConstInt() {
/*  61 */     return (this.type == 3);
/*     */   }
/*     */   
/*     */   public boolean isConstFloat() {
/*  65 */     return (this.type == 4);
/*     */   }
/*     */   
/*     */   public boolean isConstBool() {
/*  69 */     return (this.type == 5);
/*     */   }
/*     */   
/*     */   public boolean isExtension() {
/*  73 */     return (this.type == 7);
/*     */   }
/*     */   
/*     */   public boolean isConstVec4() {
/*  77 */     return (this.type == 8);
/*     */   }
/*     */   
/*     */   public boolean isProperty(String name) {
/*  81 */     return (isProperty() && name.equals(this.name));
/*     */   }
/*     */   
/*     */   public boolean isProperty(String name, String value) {
/*  85 */     return (isProperty(name) && value.equals(this.value));
/*     */   }
/*     */   
/*     */   public boolean isConstInt(String name) {
/*  89 */     return (isConstInt() && name.equals(this.name));
/*     */   }
/*     */   
/*     */   public boolean isConstIntSuffix(String suffix) {
/*  93 */     return (isConstInt() && this.name.endsWith(suffix));
/*     */   }
/*     */   
/*     */   public boolean isConstFloat(String name) {
/*  97 */     return (isConstFloat() && name.equals(this.name));
/*     */   }
/*     */   
/*     */   public boolean isConstBool(String name) {
/* 101 */     return (isConstBool() && name.equals(this.name));
/*     */   }
/*     */   
/*     */   public boolean isExtension(String name) {
/* 105 */     return (isExtension() && name.equals(this.name));
/*     */   }
/*     */   
/*     */   public boolean isConstBoolSuffix(String suffix) {
/* 109 */     return (isConstBool() && this.name.endsWith(suffix));
/*     */   }
/*     */   
/*     */   public boolean isConstBoolSuffix(String suffix, boolean val) {
/* 113 */     return (isConstBoolSuffix(suffix) && getValueBool() == val);
/*     */   }
/*     */   
/*     */   public boolean isConstBool(String name1, String name2) {
/* 117 */     return !(!isConstBool(name1) && !isConstBool(name2));
/*     */   }
/*     */   
/*     */   public boolean isConstBool(String name1, String name2, String name3) {
/* 121 */     return !(!isConstBool(name1) && !isConstBool(name2) && !isConstBool(name3));
/*     */   }
/*     */   
/*     */   public boolean isConstBool(String name, boolean val) {
/* 125 */     return (isConstBool(name) && getValueBool() == val);
/*     */   }
/*     */   
/*     */   public boolean isConstBool(String name1, String name2, boolean val) {
/* 129 */     return (isConstBool(name1, name2) && getValueBool() == val);
/*     */   }
/*     */   
/*     */   public boolean isConstBool(String name1, String name2, String name3, boolean val) {
/* 133 */     return (isConstBool(name1, name2, name3) && getValueBool() == val);
/*     */   }
/*     */   
/*     */   public boolean isConstVec4Suffix(String suffix) {
/* 137 */     return (isConstVec4() && this.name.endsWith(suffix));
/*     */   }
/*     */   
/*     */   public int getValueInt() {
/*     */     try {
/* 142 */       return Integer.parseInt(this.value);
/* 143 */     } catch (NumberFormatException var2) {
/* 144 */       throw new NumberFormatException("Invalid integer: " + this.value + ", line: " + this.line);
/*     */     } 
/*     */   }
/*     */   
/*     */   public float getValueFloat() {
/*     */     try {
/* 150 */       return Float.parseFloat(this.value);
/* 151 */     } catch (NumberFormatException var2) {
/* 152 */       throw new NumberFormatException("Invalid float: " + this.value + ", line: " + this.line);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Vector4f getValueVec4() {
/* 157 */     if (this.value == null) {
/* 158 */       return null;
/*     */     }
/* 160 */     String s = this.value.trim();
/* 161 */     s = StrUtils.removePrefix(s, "vec4");
/* 162 */     s = StrUtils.trim(s, " ()");
/* 163 */     String[] astring = Config.tokenize(s, ", ");
/*     */     
/* 165 */     if (astring.length != 4) {
/* 166 */       return null;
/*     */     }
/* 168 */     float[] afloat = new float[4];
/*     */     
/* 170 */     for (int i = 0; i < astring.length; i++) {
/* 171 */       String s1 = astring[i];
/* 172 */       s1 = StrUtils.removeSuffix(s1, new String[] { "F", "f" });
/* 173 */       float f = Config.parseFloat(s1, Float.MAX_VALUE);
/*     */       
/* 175 */       if (f == Float.MAX_VALUE) {
/* 176 */         return null;
/*     */       }
/*     */       
/* 179 */       afloat[i] = f;
/*     */     } 
/*     */     
/* 182 */     return new Vector4f(afloat[0], afloat[1], afloat[2], afloat[3]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getValueBool() {
/* 188 */     String s = this.value.toLowerCase();
/*     */     
/* 190 */     if (!s.equals("true") && !s.equals("false")) {
/* 191 */       throw new RuntimeException("Invalid boolean: " + this.value + ", line: " + this.line);
/*     */     }
/* 193 */     return Boolean.valueOf(this.value).booleanValue();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\config\ShaderLine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */