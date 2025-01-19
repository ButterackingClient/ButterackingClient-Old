/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import net.minecraft.util.IProgressUpdate;
/*    */ import net.optifine.CustomLoadingScreen;
/*    */ import net.optifine.CustomLoadingScreens;
/*    */ 
/*    */ public class GuiScreenWorking extends GuiScreen implements IProgressUpdate {
/*  8 */   private String field_146591_a = "";
/*  9 */   private String field_146589_f = "";
/*    */   private int progress;
/*    */   private boolean doneWorking;
/* 12 */   private CustomLoadingScreen customLoadingScreen = CustomLoadingScreens.getCustomLoadingScreen();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void displaySavingString(String message) {
/* 18 */     resetProgressAndMessage(message);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void resetProgressAndMessage(String message) {
/* 26 */     this.field_146591_a = message;
/* 27 */     displayLoadingString("Working...");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void displayLoadingString(String message) {
/* 34 */     this.field_146589_f = message;
/* 35 */     setLoadingProgress(0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setLoadingProgress(int progress) {
/* 42 */     this.progress = progress;
/*    */   }
/*    */   
/*    */   public void setDoneWorking() {
/* 46 */     this.doneWorking = true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 53 */     if (this.doneWorking) {
/* 54 */       if (!this.mc.isConnectedToRealms()) {
/* 55 */         this.mc.displayGuiScreen(null);
/*    */       }
/*    */     } else {
/* 58 */       if (this.customLoadingScreen != null && this.mc.theWorld == null) {
/* 59 */         this.customLoadingScreen.drawBackground(width, height);
/*    */       } else {
/* 61 */         drawDefaultBackground();
/*    */       } 
/*    */       
/* 64 */       if (this.progress > 0) {
/* 65 */         drawCenteredString(this.fontRendererObj, this.field_146591_a, width / 2, 70, 16777215);
/* 66 */         drawCenteredString(this.fontRendererObj, String.valueOf(this.field_146589_f) + " " + this.progress + "%", width / 2, 90, 16777215);
/*    */       } 
/*    */       
/* 69 */       super.drawScreen(mouseX, mouseY, partialTicks);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiScreenWorking.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */