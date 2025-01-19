/*    */ package client.mod.impl;
/*    */ import client.hud.HudMod;
/*    */ import net.minecraft.client.renderer.RenderHelper;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class ModArmorStatus extends HudMod {
/*    */   public ModArmorStatus() {
/* 11 */     super("Armor", 350, 170, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWidth() {
/* 16 */     return 70;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getHeight() {
/* 21 */     return 64;
/*    */   }
/*    */ 
/*    */   
/*    */   public void draw() {
/* 26 */     for (int i = 0; i < mc.thePlayer.inventory.armorInventory.length; i++) {
/* 27 */       ItemStack itemStack = mc.thePlayer.inventory.armorInventory[i];
/* 28 */       renderItemStack(i, itemStack);
/*    */     } 
/* 30 */     super.draw();
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderDummy(int mouseX, int mouseY) {
/* 35 */     renderItemStack(3, new ItemStack((Item)Items.diamond_helmet));
/* 36 */     renderItemStack(2, new ItemStack((Item)Items.diamond_chestplate));
/* 37 */     renderItemStack(1, new ItemStack((Item)Items.diamond_leggings));
/* 38 */     renderItemStack(0, new ItemStack((Item)Items.diamond_boots));
/* 39 */     super.renderDummy(mouseX, mouseY);
/*    */   }
/*    */   
/*    */   private void renderItemStack(int i, ItemStack is) {
/* 43 */     if (is == null) {
/*    */       return;
/*    */     }
/* 46 */     GL11.glPushMatrix();
/* 47 */     int yAdd = -16 * i + 48;
/* 48 */     if (is.getItem().isDamageable()) {
/* 49 */       double damage = (is.getMaxDamage() - is.getItemDamage());
/* 50 */       fr.drawStringWithShadow(String.valueOf(String.format("%.0f", new Object[] { Double.valueOf(damage) })) + " / " + is.getMaxDamage(), (x() + 20), (y() + yAdd + 5), -1);
/*    */     } 
/* 52 */     RenderHelper.enableGUIStandardItemLighting();
/* 53 */     mc.getRenderItem().renderItemAndEffectIntoGUI(is, x(), y() + yAdd);
/* 54 */     GL11.glPopMatrix();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\mod\impl\ModArmorStatus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */