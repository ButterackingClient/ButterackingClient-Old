/*    */ package client.mod.impl;
/*    */ 
/*    */ import client.hud.HudMod;
/*    */ import net.minecraft.client.renderer.RenderHelper;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class ItemHeld extends HudMod {
/*    */   public ItemHeld() {
/* 11 */     super("HeldItem", 350, 130, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWidth() {
/* 16 */     return 20 + fr.getStringWidth("1500 / 1500");
/*    */   }
/*    */ 
/*    */   
/*    */   public int getHeight() {
/* 21 */     return 20;
/*    */   }
/*    */ 
/*    */   
/*    */   public void draw() {
/* 26 */     ItemStack itemStack = mc.thePlayer.getHeldItem();
/* 27 */     renderItemStack(itemStack);
/* 28 */     super.draw();
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderDummy(int mouseX, int mouseY) {
/* 33 */     renderItemStack(new ItemStack(Items.diamond_sword));
/* 34 */     super.renderDummy(mouseX, mouseY);
/*    */   }
/*    */   
/*    */   private void renderItemStack(ItemStack is) {
/* 38 */     if (is == null) {
/*    */       return;
/*    */     }
/* 41 */     GL11.glPushMatrix();
/* 42 */     if (is.stackSize >= 1 && !is.getItem().isDamageable()) {
/* 43 */       fr.drawStringWithShadow(String.valueOf(is.stackSize), (x() + 20), (y() + 5), -1);
/*    */     }
/* 45 */     if (is.getItem().isDamageable()) {
/* 46 */       double damage = (is.getMaxDamage() - is.getItemDamage());
/* 47 */       ModArmorStatus.fr.drawStringWithShadow(String.valueOf(String.format("%.0f", new Object[] { Double.valueOf(damage) })) + " / " + is.getMaxDamage(), (x() + 20), (y() + 5), -1);
/*    */     } 
/* 49 */     RenderHelper.enableGUIStandardItemLighting();
/* 50 */     mc.getRenderItem().renderItemAndEffectIntoGUI(is, x(), y());
/* 51 */     GL11.glPopMatrix();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\mod\impl\ItemHeld.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */