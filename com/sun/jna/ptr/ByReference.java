/*    */ package com.sun.jna.ptr;
/*    */ 
/*    */ import com.sun.jna.Memory;
/*    */ import com.sun.jna.Pointer;
/*    */ import com.sun.jna.PointerType;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ByReference
/*    */   extends PointerType
/*    */ {
/*    */   protected ByReference(int dataSize) {
/* 42 */     setPointer((Pointer)new Memory(dataSize));
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\com\sun\jna\ptr\ByReference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */