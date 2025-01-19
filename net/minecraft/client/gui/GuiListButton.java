/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiListButton
/*    */   extends GuiButton
/*    */ {
/*    */   private boolean field_175216_o;
/*    */   private String localizationStr;
/*    */   private final GuiPageButtonList.GuiResponder guiResponder;
/*    */   
/*    */   public GuiListButton(GuiPageButtonList.GuiResponder responder, int p_i45539_2_, int p_i45539_3_, int p_i45539_4_, String p_i45539_5_, boolean p_i45539_6_) {
/* 20 */     super(p_i45539_2_, p_i45539_3_, p_i45539_4_, 150, 20, "");
/* 21 */     this.localizationStr = p_i45539_5_;
/* 22 */     this.field_175216_o = p_i45539_6_;
/* 23 */     this.displayString = buildDisplayString();
/* 24 */     this.guiResponder = responder;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private String buildDisplayString() {
/* 31 */     return String.valueOf(I18n.format(this.localizationStr, new Object[0])) + ": " + (this.field_175216_o ? I18n.format("gui.yes", new Object[0]) : I18n.format("gui.no", new Object[0]));
/*    */   }
/*    */   
/*    */   public void func_175212_b(boolean p_175212_1_) {
/* 35 */     this.field_175216_o = p_175212_1_;
/* 36 */     this.displayString = buildDisplayString();
/* 37 */     this.guiResponder.func_175321_a(this.id, p_175212_1_);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
/* 45 */     if (super.mousePressed(mc, mouseX, mouseY)) {
/* 46 */       this.field_175216_o = !this.field_175216_o;
/* 47 */       this.displayString = buildDisplayString();
/* 48 */       this.guiResponder.func_175321_a(this.id, this.field_175216_o);
/* 49 */       return true;
/*    */     } 
/* 51 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiListButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */