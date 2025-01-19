/*    */ package net.minecraft.realms;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ 
/*    */ public class DisconnectedRealmsScreen
/*    */   extends RealmsScreen {
/*    */   private String title;
/*    */   private IChatComponent reason;
/*    */   private List<String> lines;
/*    */   private final RealmsScreen parent;
/*    */   private int textHeight;
/*    */   
/*    */   public DisconnectedRealmsScreen(RealmsScreen parentIn, String unlocalizedTitle, IChatComponent reasonIn) {
/* 15 */     this.parent = parentIn;
/* 16 */     this.title = getLocalizedString(unlocalizedTitle);
/* 17 */     this.reason = reasonIn;
/*    */   }
/*    */   
/*    */   public void init() {
/* 21 */     Realms.setConnectedToRealms(false);
/* 22 */     buttonsClear();
/* 23 */     this.lines = fontSplit(this.reason.getFormattedText(), width() - 50);
/* 24 */     this.textHeight = this.lines.size() * fontLineHeight();
/* 25 */     buttonsAdd(newButton(0, width() / 2 - 100, height() / 2 + this.textHeight / 2 + fontLineHeight(), getLocalizedString("gui.back")));
/*    */   }
/*    */   
/*    */   public void keyPressed(char p_keyPressed_1_, int p_keyPressed_2_) {
/* 29 */     if (p_keyPressed_2_ == 1) {
/* 30 */       Realms.setScreen(this.parent);
/*    */     }
/*    */   }
/*    */   
/*    */   public void buttonClicked(RealmsButton p_buttonClicked_1_) {
/* 35 */     if (p_buttonClicked_1_.id() == 0) {
/* 36 */       Realms.setScreen(this.parent);
/*    */     }
/*    */   }
/*    */   
/*    */   public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
/* 41 */     renderBackground();
/* 42 */     drawCenteredString(this.title, width() / 2, height() / 2 - this.textHeight / 2 - fontLineHeight() * 2, 11184810);
/* 43 */     int i = height() / 2 - this.textHeight / 2;
/*    */     
/* 45 */     if (this.lines != null) {
/* 46 */       for (String s : this.lines) {
/* 47 */         drawCenteredString(s, width() / 2, i, 16777215);
/* 48 */         i += fontLineHeight();
/*    */       } 
/*    */     }
/*    */     
/* 52 */     super.render(p_render_1_, p_render_2_, p_render_3_);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\realms\DisconnectedRealmsScreen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */