/*    */ package client.mod.impl;
/*    */ import client.Client;
/*    */ import client.event.SubscribeEvent;
/*    */ import client.event.impl.EventUpdate;
/*    */ import client.mod.Category;
/*    */ import client.mod.Mod;
/*    */ 
/*    */ public class ToggleSprint extends Mod {
/*    */   public ToggleSprint() {
/* 10 */     super("TestMod", "Test Mod", Category.MISC);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onUpdate(EventUpdate event) {
/* 15 */     if (isEnabled() && (Client.getInstance()).hudManager.toggleSprintHud.isEnabled()) {
/* 16 */       this.mc.gameSettings.keyBindInventory.setPressed(true);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 22 */     super.onDisable();
/* 23 */     this.mc.thePlayer.setSprinting(false);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\mod\impl\ToggleSprint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */