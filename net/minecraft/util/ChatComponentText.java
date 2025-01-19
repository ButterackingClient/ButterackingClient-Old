/*    */ package net.minecraft.util;
/*    */ 
/*    */ public class ChatComponentText extends ChatComponentStyle {
/*    */   private final String text;
/*    */   
/*    */   public ChatComponentText(String msg) {
/*  7 */     this.text = msg;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getChatComponentText_TextValue() {
/* 15 */     return this.text;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getUnformattedTextForChat() {
/* 23 */     return this.text;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ChatComponentText createCopy() {
/* 30 */     ChatComponentText chatcomponenttext = new ChatComponentText(this.text);
/* 31 */     chatcomponenttext.setChatStyle(getChatStyle().createShallowCopy());
/*    */     
/* 33 */     for (IChatComponent ichatcomponent : getSiblings()) {
/* 34 */       chatcomponenttext.appendSibling(ichatcomponent.createCopy());
/*    */     }
/*    */     
/* 37 */     return chatcomponenttext;
/*    */   }
/*    */   
/*    */   public boolean equals(Object p_equals_1_) {
/* 41 */     if (this == p_equals_1_)
/* 42 */       return true; 
/* 43 */     if (!(p_equals_1_ instanceof ChatComponentText)) {
/* 44 */       return false;
/*    */     }
/* 46 */     ChatComponentText chatcomponenttext = (ChatComponentText)p_equals_1_;
/* 47 */     return (this.text.equals(chatcomponenttext.getChatComponentText_TextValue()) && super.equals(p_equals_1_));
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 52 */     return "TextComponent{text='" + this.text + '\'' + ", siblings=" + this.siblings + ", style=" + getChatStyle() + '}';
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\ChatComponentText.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */