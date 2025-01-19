/*    */ package client.mod.impl;
/*    */ 
/*    */ import client.hud.HudMod;
/*    */ import net.minecraft.client.renderer.RenderHelper;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class ArrowCounter extends HudMod {
/*    */   public ArrowCounter() {
/* 11 */     super("ArrowCounter", 350, 110, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWidth() {
/* 16 */     return 20 + fr.getStringWidth("150");
/*    */   }
/*    */ 
/*    */   
/*    */   public int getHeight() {
/* 21 */     return 20;
/*    */   }
/*    */ 
/*    */   
/*    */   public void draw() {
/* 26 */     ItemStack itemStack = new ItemStack(Items.arrow);
/* 27 */     renderItemStack(itemStack);
/* 28 */     super.draw();
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderDummy(int mouseX, int mouseY) {
/* 33 */     renderItemStack(new ItemStack(Items.arrow));
/* 34 */     super.renderDummy(mouseX, mouseY);
/*    */   }
/*    */   
/*    */   private int getArrows() {
/* 38 */     int i = 0;
/*    */     ItemStack[] mainInventory;
/* 40 */     for (int length = (mainInventory = mc.thePlayer.inventory.mainInventory).length, j = 0; j < length; j++) {
/* 41 */       ItemStack itemstack = mainInventory[j];
/* 42 */       if (itemstack != null && itemstack.getItem().equals(Items.arrow)) {
/* 43 */         i += itemstack.stackSize;
/*    */       }
/*    */     } 
/* 46 */     return i;
/*    */   }
/*    */   private void renderItemStack(ItemStack is) {
/* 49 */     if (is == null) {
/*    */       return;
/*    */     }
/* 52 */     GL11.glPushMatrix();
/* 53 */     fr.drawStringWithShadow(String.valueOf(getArrows()), (x() + 20), (y() + 5), -1);
/* 54 */     RenderHelper.enableGUIStandardItemLighting();
/* 55 */     mc.getRenderItem().renderItemAndEffectIntoGUI(is, x(), y());
/* 56 */     GL11.glPopMatrix();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\mod\impl\ArrowCounter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */