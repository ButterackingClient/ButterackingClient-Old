/*     */ package net.optifine.shaders.config;
/*     */ 
/*     */ import java.util.Properties;
/*     */ import net.minecraft.src.Config;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ 
/*     */ public class Property
/*     */ {
/*   9 */   private int defaultValue = 0;
/*  10 */   private String propertyName = null;
/*  11 */   private String[] propertyValues = null;
/*  12 */   private String userName = null;
/*  13 */   private String[] userValues = null;
/*  14 */   private int value = 0;
/*     */   
/*     */   public Property(String propertyName, String[] propertyValues, String userName, String[] userValues, int defaultValue) {
/*  17 */     this.propertyName = propertyName;
/*  18 */     this.propertyValues = propertyValues;
/*  19 */     this.userName = userName;
/*  20 */     this.userValues = userValues;
/*  21 */     this.defaultValue = defaultValue;
/*     */     
/*  23 */     if (propertyValues.length != userValues.length)
/*  24 */       throw new IllegalArgumentException("Property and user values have different lengths: " + propertyValues.length + " != " + userValues.length); 
/*  25 */     if (defaultValue >= 0 && defaultValue < propertyValues.length) {
/*  26 */       this.value = defaultValue;
/*     */     } else {
/*  28 */       throw new IllegalArgumentException("Invalid default value: " + defaultValue);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean setPropertyValue(String propVal) {
/*  33 */     if (propVal == null) {
/*  34 */       this.value = this.defaultValue;
/*  35 */       return false;
/*     */     } 
/*  37 */     this.value = ArrayUtils.indexOf((Object[])this.propertyValues, propVal);
/*     */     
/*  39 */     if (this.value >= 0 && this.value < this.propertyValues.length) {
/*  40 */       return true;
/*     */     }
/*  42 */     this.value = this.defaultValue;
/*  43 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void nextValue(boolean forward) {
/*  49 */     int i = 0;
/*  50 */     int j = this.propertyValues.length - 1;
/*  51 */     this.value = Config.limit(this.value, i, j);
/*     */     
/*  53 */     if (forward) {
/*  54 */       this.value++;
/*     */       
/*  56 */       if (this.value > j) {
/*  57 */         this.value = i;
/*     */       }
/*     */     } else {
/*  60 */       this.value--;
/*     */       
/*  62 */       if (this.value < i) {
/*  63 */         this.value = j;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setValue(int val) {
/*  69 */     this.value = val;
/*     */     
/*  71 */     if (this.value < 0 || this.value >= this.propertyValues.length) {
/*  72 */       this.value = this.defaultValue;
/*     */     }
/*     */   }
/*     */   
/*     */   public int getValue() {
/*  77 */     return this.value;
/*     */   }
/*     */   
/*     */   public String getUserValue() {
/*  81 */     return this.userValues[this.value];
/*     */   }
/*     */   
/*     */   public String getPropertyValue() {
/*  85 */     return this.propertyValues[this.value];
/*     */   }
/*     */   
/*     */   public String getUserName() {
/*  89 */     return this.userName;
/*     */   }
/*     */   
/*     */   public String getPropertyName() {
/*  93 */     return this.propertyName;
/*     */   }
/*     */   
/*     */   public void resetValue() {
/*  97 */     this.value = this.defaultValue;
/*     */   }
/*     */   
/*     */   public boolean loadFrom(Properties props) {
/* 101 */     resetValue();
/*     */     
/* 103 */     if (props == null) {
/* 104 */       return false;
/*     */     }
/* 106 */     String s = props.getProperty(this.propertyName);
/* 107 */     return (s == null) ? false : setPropertyValue(s);
/*     */   }
/*     */ 
/*     */   
/*     */   public void saveTo(Properties props) {
/* 112 */     if (props != null) {
/* 113 */       props.setProperty(getPropertyName(), getPropertyValue());
/*     */     }
/*     */   }
/*     */   
/*     */   public String toString() {
/* 118 */     return this.propertyName + "=" + getPropertyValue() + " [" + Config.arrayToString((Object[])this.propertyValues) + "], value: " + this.value;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\config\Property.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */