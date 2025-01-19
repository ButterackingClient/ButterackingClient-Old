/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.util.EnumChatFormatting;
/*    */ 
/*    */ public enum EnumRarity {
/*  6 */   COMMON(EnumChatFormatting.WHITE, "Common"),
/*  7 */   UNCOMMON(EnumChatFormatting.YELLOW, "Uncommon"),
/*  8 */   RARE(EnumChatFormatting.AQUA, "Rare"),
/*  9 */   EPIC(EnumChatFormatting.LIGHT_PURPLE, "Epic");
/*    */ 
/*    */ 
/*    */   
/*    */   public final EnumChatFormatting rarityColor;
/*    */ 
/*    */ 
/*    */   
/*    */   public final String rarityName;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   EnumRarity(EnumChatFormatting color, String name) {
/* 23 */     this.rarityColor = color;
/* 24 */     this.rarityName = name;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\EnumRarity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */