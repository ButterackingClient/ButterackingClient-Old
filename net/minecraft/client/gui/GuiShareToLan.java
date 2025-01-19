/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ import net.minecraft.util.ChatComponentText;
/*    */ import net.minecraft.util.ChatComponentTranslation;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ import net.minecraft.world.WorldSettings;
/*    */ 
/*    */ public class GuiShareToLan
/*    */   extends GuiScreen {
/*    */   private final GuiScreen field_146598_a;
/*    */   private GuiButton field_146596_f;
/*    */   private GuiButton field_146597_g;
/* 15 */   private String field_146599_h = "survival";
/*    */   private boolean field_146600_i;
/*    */   
/*    */   public GuiShareToLan(GuiScreen p_i1055_1_) {
/* 19 */     this.field_146598_a = p_i1055_1_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initGui() {
/* 27 */     this.buttonList.clear();
/* 28 */     this.buttonList.add(new GuiButton(101, width / 2 - 155, height - 28, 150, 20, I18n.format("lanServer.start", new Object[0])));
/* 29 */     this.buttonList.add(new GuiButton(102, width / 2 + 5, height - 28, 150, 20, I18n.format("gui.cancel", new Object[0])));
/* 30 */     this.buttonList.add(this.field_146597_g = new GuiButton(104, width / 2 - 155, 100, 150, 20, I18n.format("selectWorld.gameMode", new Object[0])));
/* 31 */     this.buttonList.add(this.field_146596_f = new GuiButton(103, width / 2 + 5, 100, 150, 20, I18n.format("selectWorld.allowCommands", new Object[0])));
/* 32 */     func_146595_g();
/*    */   }
/*    */   
/*    */   private void func_146595_g() {
/* 36 */     this.field_146597_g.displayString = String.valueOf(I18n.format("selectWorld.gameMode", new Object[0])) + " " + I18n.format("selectWorld.gameMode." + this.field_146599_h, new Object[0]);
/* 37 */     this.field_146596_f.displayString = String.valueOf(I18n.format("selectWorld.allowCommands", new Object[0])) + " ";
/*    */     
/* 39 */     if (this.field_146600_i) {
/* 40 */       this.field_146596_f.displayString = String.valueOf(this.field_146596_f.displayString) + I18n.format("options.on", new Object[0]);
/*    */     } else {
/* 42 */       this.field_146596_f.displayString = String.valueOf(this.field_146596_f.displayString) + I18n.format("options.off", new Object[0]);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void actionPerformed(GuiButton button) throws IOException {
/* 50 */     if (button.id == 102) {
/* 51 */       this.mc.displayGuiScreen(this.field_146598_a);
/* 52 */     } else if (button.id == 104) {
/* 53 */       if (this.field_146599_h.equals("spectator")) {
/* 54 */         this.field_146599_h = "creative";
/* 55 */       } else if (this.field_146599_h.equals("creative")) {
/* 56 */         this.field_146599_h = "adventure";
/* 57 */       } else if (this.field_146599_h.equals("adventure")) {
/* 58 */         this.field_146599_h = "survival";
/*    */       } else {
/* 60 */         this.field_146599_h = "spectator";
/*    */       } 
/*    */       
/* 63 */       func_146595_g();
/* 64 */     } else if (button.id == 103) {
/* 65 */       this.field_146600_i = !this.field_146600_i;
/* 66 */       func_146595_g();
/* 67 */     } else if (button.id == 101) {
/* 68 */       ChatComponentText chatComponentText; this.mc.displayGuiScreen(null);
/* 69 */       String s = this.mc.getIntegratedServer().shareToLAN(WorldSettings.GameType.getByName(this.field_146599_h), this.field_146600_i);
/*    */ 
/*    */       
/* 72 */       if (s != null) {
/* 73 */         ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.publish.started", new Object[] { s });
/*    */       } else {
/* 75 */         chatComponentText = new ChatComponentText("commands.publish.failed");
/*    */       } 
/*    */       
/* 78 */       this.mc.ingameGUI.getChatGUI().printChatMessage((IChatComponent)chatComponentText);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 86 */     drawDefaultBackground();
/* 87 */     drawCenteredString(this.fontRendererObj, I18n.format("lanServer.title", new Object[0]), width / 2, 50, 16777215);
/* 88 */     drawCenteredString(this.fontRendererObj, I18n.format("lanServer.otherPlayers", new Object[0]), width / 2, 82, 16777215);
/* 89 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiShareToLan.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */