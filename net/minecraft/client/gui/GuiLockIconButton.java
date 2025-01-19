/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ 
/*    */ public class GuiLockIconButton extends GuiButton {
/*    */   private boolean field_175231_o = false;
/*    */   
/*    */   public GuiLockIconButton(int p_i45538_1_, int p_i45538_2_, int p_i45538_3_) {
/* 10 */     super(p_i45538_1_, p_i45538_2_, p_i45538_3_, 20, 20, "");
/*    */   }
/*    */   
/*    */   public boolean func_175230_c() {
/* 14 */     return this.field_175231_o;
/*    */   }
/*    */   
/*    */   public void func_175229_b(boolean p_175229_1_) {
/* 18 */     this.field_175231_o = p_175229_1_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawButton(Minecraft mc, int mouseX, int mouseY) {
/* 25 */     if (this.visible) {
/* 26 */       Icon guilockiconbutton$icon; mc.getTextureManager().bindTexture(GuiButton.buttonTextures);
/* 27 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 28 */       boolean flag = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height);
/*    */ 
/*    */       
/* 31 */       if (this.field_175231_o) {
/* 32 */         if (!this.enabled) {
/* 33 */           guilockiconbutton$icon = Icon.LOCKED_DISABLED;
/* 34 */         } else if (flag) {
/* 35 */           guilockiconbutton$icon = Icon.LOCKED_HOVER;
/*    */         } else {
/* 37 */           guilockiconbutton$icon = Icon.LOCKED;
/*    */         } 
/* 39 */       } else if (!this.enabled) {
/* 40 */         guilockiconbutton$icon = Icon.UNLOCKED_DISABLED;
/* 41 */       } else if (flag) {
/* 42 */         guilockiconbutton$icon = Icon.UNLOCKED_HOVER;
/*    */       } else {
/* 44 */         guilockiconbutton$icon = Icon.UNLOCKED;
/*    */       } 
/*    */       
/* 47 */       drawTexturedModalRect(this.xPosition, this.yPosition, guilockiconbutton$icon.func_178910_a(), guilockiconbutton$icon.func_178912_b(), this.width, this.height);
/*    */     } 
/*    */   }
/*    */   
/*    */   enum Icon {
/* 52 */     LOCKED(0, 146),
/* 53 */     LOCKED_HOVER(0, 166),
/* 54 */     LOCKED_DISABLED(0, 186),
/* 55 */     UNLOCKED(20, 146),
/* 56 */     UNLOCKED_HOVER(20, 166),
/* 57 */     UNLOCKED_DISABLED(20, 186);
/*    */     
/*    */     private final int field_178914_g;
/*    */     private final int field_178920_h;
/*    */     
/*    */     Icon(int p_i45537_3_, int p_i45537_4_) {
/* 63 */       this.field_178914_g = p_i45537_3_;
/* 64 */       this.field_178920_h = p_i45537_4_;
/*    */     }
/*    */     
/*    */     public int func_178910_a() {
/* 68 */       return this.field_178914_g;
/*    */     }
/*    */     
/*    */     public int func_178912_b() {
/* 72 */       return this.field_178920_h;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiLockIconButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */