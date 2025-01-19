/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.util.ChatComponentText;
/*    */ import net.minecraft.util.EnumChatFormatting;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ 
/*    */ 
/*    */ public class GuiUtilRenderComponents
/*    */ {
/*    */   public static String func_178909_a(String p_178909_0_, boolean p_178909_1_) {
/* 14 */     return (!p_178909_1_ && !(Minecraft.getMinecraft()).gameSettings.chatColours) ? EnumChatFormatting.getTextWithoutFormattingCodes(p_178909_0_) : p_178909_0_;
/*    */   }
/*    */   
/*    */   public static List<IChatComponent> splitText(IChatComponent p_178908_0_, int p_178908_1_, FontRenderer p_178908_2_, boolean p_178908_3_, boolean p_178908_4_) {
/* 18 */     int i = 0;
/* 19 */     ChatComponentText chatComponentText = new ChatComponentText("");
/* 20 */     List<IChatComponent> list = Lists.newArrayList();
/* 21 */     List<IChatComponent> list1 = Lists.newArrayList((Iterable)p_178908_0_);
/*    */     
/* 23 */     for (int j = 0; j < list1.size(); j++) {
/* 24 */       IChatComponent ichatcomponent1 = list1.get(j);
/* 25 */       String s = ichatcomponent1.getUnformattedTextForChat();
/* 26 */       boolean flag = false;
/*    */       
/* 28 */       if (s.contains("\n")) {
/* 29 */         int k = s.indexOf('\n');
/* 30 */         String s1 = s.substring(k + 1);
/* 31 */         s = s.substring(0, k + 1);
/* 32 */         ChatComponentText chatcomponenttext = new ChatComponentText(s1);
/* 33 */         chatcomponenttext.setChatStyle(ichatcomponent1.getChatStyle().createShallowCopy());
/* 34 */         list1.add(j + 1, chatcomponenttext);
/* 35 */         flag = true;
/*    */       } 
/*    */       
/* 38 */       String s4 = func_178909_a(String.valueOf(ichatcomponent1.getChatStyle().getFormattingCode()) + s, p_178908_4_);
/* 39 */       String s5 = s4.endsWith("\n") ? s4.substring(0, s4.length() - 1) : s4;
/* 40 */       int i1 = p_178908_2_.getStringWidth(s5);
/* 41 */       ChatComponentText chatcomponenttext1 = new ChatComponentText(s5);
/* 42 */       chatcomponenttext1.setChatStyle(ichatcomponent1.getChatStyle().createShallowCopy());
/*    */       
/* 44 */       if (i + i1 > p_178908_1_) {
/* 45 */         String s2 = p_178908_2_.trimStringToWidth(s4, p_178908_1_ - i, false);
/* 46 */         String s3 = (s2.length() < s4.length()) ? s4.substring(s2.length()) : null;
/*    */         
/* 48 */         if (s3 != null && s3.length() > 0) {
/* 49 */           int l = s2.lastIndexOf(" ");
/*    */           
/* 51 */           if (l >= 0 && p_178908_2_.getStringWidth(s4.substring(0, l)) > 0) {
/* 52 */             s2 = s4.substring(0, l);
/*    */             
/* 54 */             if (p_178908_3_) {
/* 55 */               l++;
/*    */             }
/*    */             
/* 58 */             s3 = s4.substring(l);
/* 59 */           } else if (i > 0 && !s4.contains(" ")) {
/* 60 */             s2 = "";
/* 61 */             s3 = s4;
/*    */           } 
/*    */           
/* 64 */           ChatComponentText chatcomponenttext2 = new ChatComponentText(s3);
/* 65 */           chatcomponenttext2.setChatStyle(ichatcomponent1.getChatStyle().createShallowCopy());
/* 66 */           list1.add(j + 1, chatcomponenttext2);
/*    */         } 
/*    */         
/* 69 */         i1 = p_178908_2_.getStringWidth(s2);
/* 70 */         chatcomponenttext1 = new ChatComponentText(s2);
/* 71 */         chatcomponenttext1.setChatStyle(ichatcomponent1.getChatStyle().createShallowCopy());
/* 72 */         flag = true;
/*    */       } 
/*    */       
/* 75 */       if (i + i1 <= p_178908_1_) {
/* 76 */         i += i1;
/* 77 */         chatComponentText.appendSibling((IChatComponent)chatcomponenttext1);
/*    */       } else {
/* 79 */         flag = true;
/*    */       } 
/*    */       
/* 82 */       if (flag) {
/* 83 */         list.add(chatComponentText);
/* 84 */         i = 0;
/* 85 */         chatComponentText = new ChatComponentText("");
/*    */       } 
/*    */     } 
/*    */     
/* 89 */     list.add(chatComponentText);
/* 90 */     return list;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiUtilRenderComponents.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */