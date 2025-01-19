/*    */ package client.mod.impl;
/*    */ 
/*    */ import client.Client;
/*    */ import client.hud.HudMod;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.function.Predicate;
/*    */ import org.lwjgl.input.Mouse;
/*    */ 
/*    */ public class ModCPS
/*    */   extends HudMod
/*    */ {
/*    */   public List<Long> clicks;
/*    */   public boolean wasPressed;
/*    */   public long lastPressed;
/*    */   private List<Long> clicks2;
/*    */   private boolean wasPressed2;
/*    */   private long lastPressed2;
/*    */   
/*    */   public ModCPS() {
/* 21 */     super("Cps", 5, 45, false);
/* 22 */     this.clicks = new ArrayList<>();
/* 23 */     this.clicks2 = new ArrayList<>();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWidth() {
/* 28 */     return fr.getStringWidth("[Cps] : 10");
/*    */   }
/*    */ 
/*    */   
/*    */   public void draw() {
/* 33 */     boolean pressed = Mouse.isButtonDown(0);
/* 34 */     boolean rpressed = Mouse.isButtonDown(1);
/*    */     
/* 36 */     this.lastPressed = System.currentTimeMillis();
/* 37 */     if (pressed != this.wasPressed && (this.wasPressed = pressed)) {
/* 38 */       this.clicks.add(Long.valueOf(this.lastPressed));
/*    */     }
/*    */ 
/*    */     
/* 42 */     this.lastPressed2 = System.currentTimeMillis() + 10L;
/* 43 */     if (rpressed != this.wasPressed2 && (this.wasPressed2 = rpressed)) {
/* 44 */       this.clicks2.add(Long.valueOf(this.lastPressed2));
/*    */     }
/*    */     
/* 47 */     if (!(Client.getInstance()).isGuiCovered) {
/* 48 */       fr.drawStringWithShadow(String.valueOf((Client.getInstance()).coverColor) + "[" + (Client.getInstance()).mainColor + "Cps" + (Client.getInstance()).coverColor + "] : " + (Client.getInstance()).subColor + getCPS() + " | " + getCPS2(), x(), y(), -1);
/*    */     } else {
/* 50 */       fr.drawStringWithShadow(String.valueOf((Client.getInstance()).mainColor) + getCPS() + (Client.getInstance()).subColor + " | " + (Client.getInstance()).mainColor + getCPS2(), x(), y(), -1);
/*    */     } 
/*    */     
/* 53 */     super.draw();
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderDummy(int mouseX, int mouseY) {
/* 58 */     draw();
/* 59 */     super.renderDummy(mouseX, mouseY);
/*    */   }
/*    */   
/*    */   public int getCPS() {
/* 63 */     final long time = System.currentTimeMillis();
/* 64 */     this.clicks.removeIf(new Predicate<Long>()
/*    */         {
/*    */           public boolean test(Long aLong) {
/* 67 */             return (aLong.longValue() + 1000L < time);
/*    */           }
/*    */         });
/* 70 */     return this.clicks.size();
/*    */   }
/*    */   
/*    */   public int getCPS2() {
/* 74 */     final long time2 = System.currentTimeMillis();
/* 75 */     this.clicks2.removeIf(new Predicate<Long>()
/*    */         {
/*    */           public boolean test(Long aLong2) {
/* 78 */             return (aLong2.longValue() + 1000L < time2);
/*    */           }
/*    */         });
/* 81 */     return this.clicks2.size();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\mod\impl\ModCPS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */