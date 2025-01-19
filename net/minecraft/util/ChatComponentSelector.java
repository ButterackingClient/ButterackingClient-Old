/*    */ package net.minecraft.util;
/*    */ 
/*    */ 
/*    */ public class ChatComponentSelector
/*    */   extends ChatComponentStyle
/*    */ {
/*    */   private final String selector;
/*    */   
/*    */   public ChatComponentSelector(String selectorIn) {
/* 10 */     this.selector = selectorIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getSelector() {
/* 17 */     return this.selector;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getUnformattedTextForChat() {
/* 25 */     return this.selector;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ChatComponentSelector createCopy() {
/* 32 */     ChatComponentSelector chatcomponentselector = new ChatComponentSelector(this.selector);
/* 33 */     chatcomponentselector.setChatStyle(getChatStyle().createShallowCopy());
/*    */     
/* 35 */     for (IChatComponent ichatcomponent : getSiblings()) {
/* 36 */       chatcomponentselector.appendSibling(ichatcomponent.createCopy());
/*    */     }
/*    */     
/* 39 */     return chatcomponentselector;
/*    */   }
/*    */   
/*    */   public boolean equals(Object p_equals_1_) {
/* 43 */     if (this == p_equals_1_)
/* 44 */       return true; 
/* 45 */     if (!(p_equals_1_ instanceof ChatComponentSelector)) {
/* 46 */       return false;
/*    */     }
/* 48 */     ChatComponentSelector chatcomponentselector = (ChatComponentSelector)p_equals_1_;
/* 49 */     return (this.selector.equals(chatcomponentselector.selector) && super.equals(p_equals_1_));
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 54 */     return "SelectorComponent{pattern='" + this.selector + '\'' + ", siblings=" + this.siblings + ", style=" + getChatStyle() + '}';
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\ChatComponentSelector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */