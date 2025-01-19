package net.optifine.gui;

import java.awt.Rectangle;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public interface TooltipProvider {
  Rectangle getTooltipBounds(GuiScreen paramGuiScreen, int paramInt1, int paramInt2);
  
  String[] getTooltipLines(GuiButton paramGuiButton, int paramInt);
  
  boolean isRenderBorder();
}


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\gui\TooltipProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */