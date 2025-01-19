/*    */ package net.optifine.shaders.config;
/*    */ 
/*    */ import net.minecraft.src.Config;
/*    */ 
/*    */ public class PropertyDefaultFastFancyOff extends Property {
/*  6 */   public static final String[] PROPERTY_VALUES = new String[] { "default", "fast", "fancy", "off" };
/*  7 */   public static final String[] USER_VALUES = new String[] { "Default", "Fast", "Fancy", "OFF" };
/*    */   
/*    */   public PropertyDefaultFastFancyOff(String propertyName, String userName, int defaultValue) {
/* 10 */     super(propertyName, PROPERTY_VALUES, userName, USER_VALUES, defaultValue);
/*    */   }
/*    */   
/*    */   public boolean isDefault() {
/* 14 */     return (getValue() == 0);
/*    */   }
/*    */   
/*    */   public boolean isFast() {
/* 18 */     return (getValue() == 1);
/*    */   }
/*    */   
/*    */   public boolean isFancy() {
/* 22 */     return (getValue() == 2);
/*    */   }
/*    */   
/*    */   public boolean isOff() {
/* 26 */     return (getValue() == 3);
/*    */   }
/*    */   
/*    */   public boolean setPropertyValue(String propVal) {
/* 30 */     if (Config.equals(propVal, "none")) {
/* 31 */       propVal = "off";
/*    */     }
/*    */     
/* 34 */     return super.setPropertyValue(propVal);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\config\PropertyDefaultFastFancyOff.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */