/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import net.minecraft.util.IChatComponent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChatLine
/*    */ {
/*    */   private final int updateCounterCreated;
/*    */   private final IChatComponent lineString;
/*    */   private final int chatLineID;
/*    */   
/*    */   public ChatLine(int p_i45000_1_, IChatComponent p_i45000_2_, int p_i45000_3_) {
/* 18 */     this.lineString = p_i45000_2_;
/* 19 */     this.updateCounterCreated = p_i45000_1_;
/* 20 */     this.chatLineID = p_i45000_3_;
/*    */   }
/*    */   
/*    */   public IChatComponent getChatComponent() {
/* 24 */     return this.lineString;
/*    */   }
/*    */   
/*    */   public int getUpdatedCounter() {
/* 28 */     return this.updateCounterCreated;
/*    */   }
/*    */   
/*    */   public int getChatLineID() {
/* 32 */     return this.chatLineID;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\ChatLine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */