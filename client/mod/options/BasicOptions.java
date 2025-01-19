/*    */ package client.mod.options;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.io.IOException;
/*    */ import java.util.ArrayList;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.FontRenderer;
/*    */ import net.minecraft.client.gui.Gui;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ 
/*    */ public class BasicOptions extends GuiScreen {
/* 12 */   public FontRenderer fr = (Minecraft.getMinecraft()).fontRendererObj;
/*    */   public String infotext1;
/*    */   public ArrayList<OptionGuiButton> optionbutton;
/*    */   
/*    */   public BasicOptions(String inf1) {
/* 17 */     this.infotext1 = inf1;
/* 18 */     this.optionbutton = new ArrayList<>();
/*    */   }
/*    */ 
/*    */   
/*    */   public void initGui() {
/* 23 */     super.initGui();
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 28 */     super.drawScreen(mouseX, mouseY, partialTicks);
/* 29 */     Gui.drawRect(20, 20, GuiScreen.width - 20, GuiScreen.height - 20, (new Color(0, 0, 0, 190)).getRGB());
/* 30 */     this.fr.drawString(this.infotext1, 40, 40, -1);
/* 31 */     this.optionbutton.add(new OptionGuiButton(160, 40));
/* 32 */     for (OptionGuiButton og : this.optionbutton) {
/* 33 */       og.draw(mouseX, mouseY);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 39 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/* 40 */     for (OptionGuiButton og : this.optionbutton)
/* 41 */       og.onClick(mouseX, mouseY); 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\mod\options\BasicOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */