/*    */ package net.minecraft.util;
/*    */ 
/*    */ import net.minecraft.client.settings.GameSettings;
/*    */ 
/*    */ public class MovementInputFromOptions extends MovementInput {
/*    */   private final GameSettings gameSettings;
/*    */   
/*    */   public MovementInputFromOptions(GameSettings gameSettingsIn) {
/*  9 */     this.gameSettings = gameSettingsIn;
/*    */   }
/*    */   
/*    */   public void updatePlayerMoveState() {
/* 13 */     this.moveStrafe = 0.0F;
/* 14 */     this.moveForward = 0.0F;
/*    */     
/* 16 */     if (this.gameSettings.keyBindLeft.isKeyDown()) {
/* 17 */       this.moveForward++;
/*    */     }
/*    */     
/* 20 */     if (this.gameSettings.keyBindRight.isKeyDown()) {
/* 21 */       this.moveForward--;
/*    */     }
/*    */     
/* 24 */     if (this.gameSettings.keyBindBack.isKeyDown()) {
/* 25 */       this.moveStrafe++;
/*    */     }
/*    */     
/* 28 */     if (this.gameSettings.keyBindJump.isKeyDown()) {
/* 29 */       this.moveStrafe--;
/*    */     }
/*    */     
/* 32 */     this.jump = this.gameSettings.keyBindSneak.isKeyDown();
/* 33 */     this.sneak = this.gameSettings.keyBindSprint.isKeyDown();
/*    */     
/* 35 */     if (this.sneak) {
/* 36 */       this.moveStrafe = (float)(this.moveStrafe * 0.3D);
/* 37 */       this.moveForward = (float)(this.moveForward * 0.3D);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\MovementInputFromOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */