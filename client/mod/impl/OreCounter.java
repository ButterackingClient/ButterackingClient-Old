/*    */ package client.mod.impl;
/*    */ 
/*    */ import client.Client;
/*    */ import client.hud.HudMod;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.scoreboard.ScoreObjective;
/*    */ import net.minecraft.scoreboard.Scoreboard;
/*    */ import net.minecraft.util.EnumChatFormatting;
/*    */ 
/*    */ public class OreCounter extends HudMod {
/*    */   public OreCounter() {
/* 13 */     super("OreCount", 90, 5, false);
/*    */   }
/*    */   
/*    */   private boolean isPlayingBedwars() {
/* 17 */     Scoreboard scoreboard = mc.theWorld.getScoreboard();
/* 18 */     ScoreObjective sidebarObjective = scoreboard.getObjectiveInDisplaySlot(1);
/* 19 */     if (sidebarObjective != null && EnumChatFormatting.getTextWithoutFormattingCodes(sidebarObjective.getDisplayName()).contains("BED WARS")) {
/* 20 */       return true;
/*    */     }
/* 22 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getWidth() {
/* 28 */     return fr.getStringWidth("Emerald : 64 ");
/*    */   }
/*    */ 
/*    */   
/*    */   public int getHeight() {
/* 33 */     return 40;
/*    */   }
/*    */ 
/*    */   
/*    */   public void draw() {
/* 38 */     fr.drawStringWithShadow(String.valueOf((Client.getInstance()).colorbase) + "aEmerald" + (Client.getInstance()).colorbase + "f : " + getEM(), x(), y(), -1);
/* 39 */     fr.drawStringWithShadow(String.valueOf((Client.getInstance()).colorbase) + "bDiamond" + (Client.getInstance()).colorbase + "f : " + getDM(), x(), y() + 10.0F, -1);
/* 40 */     fr.drawStringWithShadow(String.valueOf((Client.getInstance()).colorbase) + "eGold" + (Client.getInstance()).colorbase + "f : " + getGL(), x(), y() + 20.0F, -1);
/* 41 */     fr.drawStringWithShadow(String.valueOf((Client.getInstance()).colorbase) + "7Iron" + (Client.getInstance()).colorbase + "f : " + getIR(), x(), y() + 30.0F, -1);
/* 42 */     super.draw();
/*    */   }
/*    */   
/*    */   private int getEM() {
/* 46 */     int i = 0;
/*    */     ItemStack[] mainInventory;
/* 48 */     for (int length = (mainInventory = mc.thePlayer.inventory.mainInventory).length, j = 0; j < length; j++) {
/* 49 */       ItemStack itemstack = mainInventory[j];
/* 50 */       if (itemstack != null && itemstack.getItem().equals(Items.emerald)) {
/* 51 */         i += itemstack.stackSize;
/*    */       }
/*    */     } 
/* 54 */     return i;
/*    */   }
/*    */   private int getDM() {
/* 57 */     int i = 0;
/*    */     ItemStack[] mainInventory;
/* 59 */     for (int length = (mainInventory = mc.thePlayer.inventory.mainInventory).length, j = 0; j < length; j++) {
/* 60 */       ItemStack itemstack = mainInventory[j];
/* 61 */       if (itemstack != null && itemstack.getItem().equals(Items.diamond)) {
/* 62 */         i += itemstack.stackSize;
/*    */       }
/*    */     } 
/* 65 */     return i;
/*    */   }
/*    */   private int getGL() {
/* 68 */     int i = 0;
/*    */     ItemStack[] mainInventory;
/* 70 */     for (int length = (mainInventory = mc.thePlayer.inventory.mainInventory).length, j = 0; j < length; j++) {
/* 71 */       ItemStack itemstack = mainInventory[j];
/* 72 */       if (itemstack != null && itemstack.getItem().equals(Items.gold_ingot)) {
/* 73 */         i += itemstack.stackSize;
/*    */       }
/*    */     } 
/* 76 */     return i;
/*    */   }
/*    */   private int getIR() {
/* 79 */     int i = 0;
/*    */     ItemStack[] mainInventory;
/* 81 */     for (int length = (mainInventory = mc.thePlayer.inventory.mainInventory).length, j = 0; j < length; j++) {
/* 82 */       ItemStack itemstack = mainInventory[j];
/* 83 */       if (itemstack != null && itemstack.getItem().equals(Items.iron_ingot)) {
/* 84 */         i += itemstack.stackSize;
/*    */       }
/*    */     } 
/* 87 */     return i;
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderDummy(int mouseX, int mouseY) {
/* 92 */     draw();
/* 93 */     super.renderDummy(mouseX, mouseY);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\mod\impl\OreCounter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */