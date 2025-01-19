/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.URI;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ import net.minecraft.client.settings.GameSettings;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class GuiScreenDemo
/*    */   extends GuiScreen {
/* 14 */   private static final Logger logger = LogManager.getLogger();
/* 15 */   private static final ResourceLocation field_146348_f = new ResourceLocation("textures/gui/demo_background.png");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initGui() {
/* 22 */     this.buttonList.clear();
/* 23 */     int i = -16;
/* 24 */     this.buttonList.add(new GuiButton(1, width / 2 - 116, height / 2 + 62 + i, 114, 20, I18n.format("demo.help.buy", new Object[0])));
/* 25 */     this.buttonList.add(new GuiButton(2, width / 2 + 2, height / 2 + 62 + i, 114, 20, I18n.format("demo.help.later", new Object[0])));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void actionPerformed(GuiButton button) throws IOException {
/* 32 */     switch (button.id) {
/*    */       case 1:
/* 34 */         button.enabled = false;
/*    */         
/*    */         try {
/* 37 */           Class<?> oclass = Class.forName("java.awt.Desktop");
/* 38 */           Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
/* 39 */           oclass.getMethod("browse", new Class[] { URI.class }).invoke(object, new Object[] { new URI("http://www.minecraft.net/store?source=demo") });
/* 40 */         } catch (Throwable throwable) {
/* 41 */           logger.error("Couldn't open link", throwable);
/*    */         } 
/*    */         break;
/*    */ 
/*    */       
/*    */       case 2:
/* 47 */         this.mc.displayGuiScreen(null);
/* 48 */         this.mc.setIngameFocus();
/*    */         break;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateScreen() {
/* 56 */     super.updateScreen();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawDefaultBackground() {
/* 63 */     super.drawDefaultBackground();
/* 64 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 65 */     this.mc.getTextureManager().bindTexture(field_146348_f);
/* 66 */     int i = (width - 248) / 2;
/* 67 */     int j = (height - 166) / 2;
/* 68 */     drawTexturedModalRect(i, j, 0, 0, 248, 166);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 75 */     drawDefaultBackground();
/* 76 */     int i = (width - 248) / 2 + 10;
/* 77 */     int j = (height - 166) / 2 + 8;
/* 78 */     this.fontRendererObj.drawString(I18n.format("demo.help.title", new Object[0]), i, j, 2039583);
/* 79 */     j += 12;
/* 80 */     GameSettings gamesettings = this.mc.gameSettings;
/* 81 */     this.fontRendererObj.drawString(I18n.format("demo.help.movementShort", new Object[] { GameSettings.getKeyDisplayString(gamesettings.keyBindLeft.getKeyCode()), GameSettings.getKeyDisplayString(gamesettings.keyBindBack.getKeyCode()), GameSettings.getKeyDisplayString(gamesettings.keyBindRight.getKeyCode()), GameSettings.getKeyDisplayString(gamesettings.keyBindJump.getKeyCode()) }), i, j, 5197647);
/* 82 */     this.fontRendererObj.drawString(I18n.format("demo.help.movementMouse", new Object[0]), i, j + 12, 5197647);
/* 83 */     this.fontRendererObj.drawString(I18n.format("demo.help.jump", new Object[] { GameSettings.getKeyDisplayString(gamesettings.keyBindSneak.getKeyCode()) }), i, j + 24, 5197647);
/* 84 */     this.fontRendererObj.drawString(I18n.format("demo.help.inventory", new Object[] { GameSettings.getKeyDisplayString(gamesettings.keyBindUseItem.getKeyCode()) }), i, j + 36, 5197647);
/* 85 */     this.fontRendererObj.drawSplitString(I18n.format("demo.help.fullWrapped", new Object[0]), i, j + 68, 218, 2039583);
/* 86 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiScreenDemo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */