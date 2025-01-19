/*    */ package client.mod.impl;
/*    */ 
/*    */ import client.event.SubscribeEvent;
/*    */ import client.mod.Category;
/*    */ import client.mod.Mod;
/*    */ 
/*    */ public class Snaplook2
/*    */   extends Mod {
/*    */   public Snaplook2() {
/* 10 */     super("TestMod", "Test Mod", Category.MISC);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onEnable() {
/* 15 */     if (this.mc.gameSettings.keyBindSnaplook.isKeyDown()) {
/* 16 */       this.mc.gameSettings.showDebugInfo = 1;
/*    */     } else {
/* 18 */       this.mc.gameSettings.showDebugInfo = 0;
/*    */     } 
/* 20 */     super.onEnable();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 25 */     this.mc.gameSettings.showDebugInfo = 0;
/* 26 */     super.onDisable();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\mod\impl\Snaplook2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */