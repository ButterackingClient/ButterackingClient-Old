/*    */ package net.minecraft.client.renderer.chunk;
/*    */ 
/*    */ import java.util.Set;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ 
/*    */ public class SetVisibility
/*    */ {
/*  8 */   private static final int COUNT_FACES = (EnumFacing.values()).length;
/*    */   private long bits;
/*    */   
/*    */   public void setManyVisible(Set<EnumFacing> p_178620_1_) {
/* 12 */     for (EnumFacing enumfacing : p_178620_1_) {
/* 13 */       for (EnumFacing enumfacing1 : p_178620_1_) {
/* 14 */         setVisible(enumfacing, enumfacing1, true);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public void setVisible(EnumFacing facing, EnumFacing facing2, boolean p_178619_3_) {
/* 20 */     setBit(facing.ordinal() + facing2.ordinal() * COUNT_FACES, p_178619_3_);
/* 21 */     setBit(facing2.ordinal() + facing.ordinal() * COUNT_FACES, p_178619_3_);
/*    */   }
/*    */   
/*    */   public void setAllVisible(boolean visible) {
/* 25 */     if (visible) {
/* 26 */       this.bits = -1L;
/*    */     } else {
/* 28 */       this.bits = 0L;
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean isVisible(EnumFacing facing, EnumFacing facing2) {
/* 33 */     return getBit(facing.ordinal() + facing2.ordinal() * COUNT_FACES);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 37 */     StringBuilder stringbuilder = new StringBuilder();
/* 38 */     stringbuilder.append(' '); byte b; int i;
/*    */     EnumFacing[] arrayOfEnumFacing;
/* 40 */     for (i = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/* 41 */       stringbuilder.append(' ').append(enumfacing.toString().toUpperCase().charAt(0));
/*    */       b++; }
/*    */     
/* 44 */     stringbuilder.append('\n');
/*    */     
/* 46 */     for (i = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < i; ) { EnumFacing enumfacing2 = arrayOfEnumFacing[b];
/* 47 */       stringbuilder.append(enumfacing2.toString().toUpperCase().charAt(0)); byte b1; int j;
/*    */       EnumFacing[] arrayOfEnumFacing1;
/* 49 */       for (j = (arrayOfEnumFacing1 = EnumFacing.values()).length, b1 = 0; b1 < j; ) { EnumFacing enumfacing1 = arrayOfEnumFacing1[b1];
/* 50 */         if (enumfacing2 == enumfacing1) {
/* 51 */           stringbuilder.append("  ");
/*    */         } else {
/* 53 */           boolean flag = isVisible(enumfacing2, enumfacing1);
/* 54 */           stringbuilder.append(' ').append(flag ? 89 : 110);
/*    */         } 
/*    */         b1++; }
/*    */       
/* 58 */       stringbuilder.append('\n');
/*    */       b++; }
/*    */     
/* 61 */     return stringbuilder.toString();
/*    */   }
/*    */   
/*    */   private boolean getBit(int p_getBit_1_) {
/* 65 */     return ((this.bits & (1 << p_getBit_1_)) != 0L);
/*    */   }
/*    */   
/*    */   private void setBit(int p_setBit_1_, boolean p_setBit_2_) {
/* 69 */     if (p_setBit_2_) {
/* 70 */       setBit(p_setBit_1_);
/*    */     } else {
/* 72 */       clearBit(p_setBit_1_);
/*    */     } 
/*    */   }
/*    */   
/*    */   private void setBit(int p_setBit_1_) {
/* 77 */     this.bits |= (1 << p_setBit_1_);
/*    */   }
/*    */   
/*    */   private void clearBit(int p_clearBit_1_) {
/* 81 */     this.bits &= (1 << p_clearBit_1_ ^ 0xFFFFFFFF);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\chunk\SetVisibility.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */