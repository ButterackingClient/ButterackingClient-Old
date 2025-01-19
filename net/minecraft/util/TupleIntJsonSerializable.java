/*    */ package net.minecraft.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TupleIntJsonSerializable
/*    */ {
/*    */   private int integerValue;
/*    */   private IJsonSerializable jsonSerializableValue;
/*    */   
/*    */   public int getIntegerValue() {
/* 11 */     return this.integerValue;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setIntegerValue(int integerValueIn) {
/* 18 */     this.integerValue = integerValueIn;
/*    */   }
/*    */   
/*    */   public <T extends IJsonSerializable> T getJsonSerializableValue() {
/* 22 */     return (T)this.jsonSerializableValue;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setJsonSerializableValue(IJsonSerializable jsonSerializableValueIn) {
/* 29 */     this.jsonSerializableValue = jsonSerializableValueIn;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\TupleIntJsonSerializable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */