/*    */ package net.minecraft.client.renderer;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.potion.Potion;
/*    */ import net.minecraft.potion.PotionEffect;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class InventoryEffectRenderer
/*    */   extends GuiContainer
/*    */ {
/*    */   private boolean hasActivePotionEffects;
/*    */   
/*    */   public InventoryEffectRenderer(Container inventorySlotsIn) {
/* 18 */     super(inventorySlotsIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initGui() {
/* 26 */     super.initGui();
/* 27 */     updateActivePotionEffects();
/*    */   }
/*    */   
/*    */   protected void updateActivePotionEffects() {
/* 31 */     if (!this.mc.thePlayer.getActivePotionEffects().isEmpty()) {
/* 32 */       this.guiLeft = 160 + (width - this.xSize - 200) / 2;
/* 33 */       this.hasActivePotionEffects = true;
/*    */     } else {
/* 35 */       this.guiLeft = (width - this.xSize) / 2;
/* 36 */       this.hasActivePotionEffects = false;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 44 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*    */     
/* 46 */     if (this.hasActivePotionEffects) {
/* 47 */       drawActivePotionEffects();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void drawActivePotionEffects() {
/* 55 */     int i = this.guiLeft - 124;
/* 56 */     int j = this.guiTop;
/* 57 */     int k = 166;
/* 58 */     Collection<PotionEffect> collection = this.mc.thePlayer.getActivePotionEffects();
/*    */     
/* 60 */     if (!collection.isEmpty()) {
/* 61 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 62 */       GlStateManager.disableLighting();
/* 63 */       int l = 33;
/*    */       
/* 65 */       if (collection.size() > 5) {
/* 66 */         l = 132 / (collection.size() - 1);
/*    */       }
/*    */       
/* 69 */       for (PotionEffect potioneffect : this.mc.thePlayer.getActivePotionEffects()) {
/* 70 */         Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
/* 71 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 72 */         this.mc.getTextureManager().bindTexture(inventoryBackground);
/* 73 */         drawTexturedModalRect(i, j, 0, 166, 140, 32);
/*    */         
/* 75 */         if (potion.hasStatusIcon()) {
/* 76 */           int i1 = potion.getStatusIconIndex();
/* 77 */           drawTexturedModalRect(i + 6, j + 7, 0 + i1 % 8 * 18, 198 + i1 / 8 * 18, 18, 18);
/*    */         } 
/*    */         
/* 80 */         String s1 = I18n.format(potion.getName(), new Object[0]);
/*    */         
/* 82 */         if (potioneffect.getAmplifier() == 1) {
/* 83 */           s1 = String.valueOf(s1) + " " + I18n.format("enchantment.level.2", new Object[0]);
/* 84 */         } else if (potioneffect.getAmplifier() == 2) {
/* 85 */           s1 = String.valueOf(s1) + " " + I18n.format("enchantment.level.3", new Object[0]);
/* 86 */         } else if (potioneffect.getAmplifier() == 3) {
/* 87 */           s1 = String.valueOf(s1) + " " + I18n.format("enchantment.level.4", new Object[0]);
/*    */         } 
/*    */         
/* 90 */         this.fontRendererObj.drawStringWithShadow(s1, (i + 10 + 18), (j + 6), 16777215);
/* 91 */         String s = Potion.getDurationString(potioneffect);
/* 92 */         this.fontRendererObj.drawStringWithShadow(s, (i + 10 + 18), (j + 6 + 10), 8355711);
/* 93 */         j += l;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\InventoryEffectRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */