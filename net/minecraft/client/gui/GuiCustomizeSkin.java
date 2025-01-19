/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ import net.minecraft.entity.player.EnumPlayerModelParts;
/*    */ import net.optifine.gui.GuiButtonOF;
/*    */ import net.optifine.gui.GuiScreenCapeOF;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiCustomizeSkin
/*    */   extends GuiScreen
/*    */ {
/*    */   private final GuiScreen parentScreen;
/*    */   private String title;
/*    */   
/*    */   public GuiCustomizeSkin(GuiScreen parentScreenIn) {
/* 22 */     this.parentScreen = parentScreenIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initGui() {
/* 30 */     int i = 0;
/* 31 */     this.title = I18n.format("options.skinCustomisation.title", new Object[0]); byte b; int j;
/*    */     EnumPlayerModelParts[] arrayOfEnumPlayerModelParts;
/* 33 */     for (j = (arrayOfEnumPlayerModelParts = EnumPlayerModelParts.values()).length, b = 0; b < j; ) { EnumPlayerModelParts enumplayermodelparts = arrayOfEnumPlayerModelParts[b];
/* 34 */       this.buttonList.add(new ButtonPart(enumplayermodelparts.getPartId(), width / 2 - 155 + i % 2 * 160, height / 6 + 24 * (i >> 1), 150, 20, enumplayermodelparts, null));
/* 35 */       i++;
/*    */       b++; }
/*    */     
/* 38 */     if (i % 2 == 1) {
/* 39 */       i++;
/*    */     }
/*    */     
/* 42 */     this.buttonList.add(new GuiButtonOF(210, width / 2 - 100, height / 6 + 24 * (i >> 1), I18n.format("of.options.skinCustomisation.ofCape", new Object[0])));
/* 43 */     i += 2;
/* 44 */     this.buttonList.add(new GuiButton(200, width / 2 - 100, height / 6 + 24 * (i >> 1), I18n.format("gui.done", new Object[0])));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void actionPerformed(GuiButton button) throws IOException {
/* 51 */     if (button.enabled) {
/* 52 */       if (button.id == 210) {
/* 53 */         this.mc.displayGuiScreen((GuiScreen)new GuiScreenCapeOF(this));
/*    */       }
/*    */       
/* 56 */       if (button.id == 200) {
/* 57 */         this.mc.gameSettings.saveOptions();
/* 58 */         this.mc.displayGuiScreen(this.parentScreen);
/* 59 */       } else if (button instanceof ButtonPart) {
/* 60 */         EnumPlayerModelParts enumplayermodelparts = ((ButtonPart)button).playerModelParts;
/* 61 */         this.mc.gameSettings.switchModelPartEnabled(enumplayermodelparts);
/* 62 */         button.displayString = func_175358_a(enumplayermodelparts);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 71 */     drawDefaultBackground();
/* 72 */     drawCenteredString(this.fontRendererObj, this.title, width / 2, 20, 16777215);
/* 73 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*    */   }
/*    */ 
/*    */   
/*    */   private String func_175358_a(EnumPlayerModelParts playerModelParts) {
/*    */     String s;
/* 79 */     if (this.mc.gameSettings.getModelParts().contains(playerModelParts)) {
/* 80 */       s = I18n.format("options.on", new Object[0]);
/*    */     } else {
/* 82 */       s = I18n.format("options.off", new Object[0]);
/*    */     } 
/*    */     
/* 85 */     return String.valueOf(playerModelParts.func_179326_d().getFormattedText()) + ": " + s;
/*    */   }
/*    */   
/*    */   class ButtonPart extends GuiButton {
/*    */     private final EnumPlayerModelParts playerModelParts;
/*    */     
/*    */     private ButtonPart(int p_i45514_2_, int p_i45514_3_, int p_i45514_4_, int p_i45514_5_, int p_i45514_6_, EnumPlayerModelParts playerModelParts) {
/* 92 */       super(p_i45514_2_, p_i45514_3_, p_i45514_4_, p_i45514_5_, p_i45514_6_, GuiCustomizeSkin.this.func_175358_a(playerModelParts));
/* 93 */       this.playerModelParts = playerModelParts;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiCustomizeSkin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */