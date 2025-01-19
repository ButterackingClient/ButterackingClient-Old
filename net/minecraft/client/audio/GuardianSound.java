/*    */ package net.minecraft.client.audio;
/*    */ 
/*    */ import net.minecraft.entity.monster.EntityGuardian;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class GuardianSound extends MovingSound {
/*    */   private final EntityGuardian guardian;
/*    */   
/*    */   public GuardianSound(EntityGuardian guardian) {
/* 10 */     super(new ResourceLocation("minecraft:mob.guardian.attack"));
/* 11 */     this.guardian = guardian;
/* 12 */     this.attenuationType = ISound.AttenuationType.NONE;
/* 13 */     this.repeat = true;
/* 14 */     this.repeatDelay = 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void update() {
/* 21 */     if (!this.guardian.isDead && this.guardian.hasTargetedEntity()) {
/* 22 */       this.xPosF = (float)this.guardian.posX;
/* 23 */       this.yPosF = (float)this.guardian.posY;
/* 24 */       this.zPosF = (float)this.guardian.posZ;
/* 25 */       float f = this.guardian.func_175477_p(0.0F);
/* 26 */       this.volume = 0.0F + 1.0F * f * f;
/* 27 */       this.pitch = 0.7F + 0.5F * f;
/*    */     } else {
/* 29 */       this.donePlaying = true;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\audio\GuardianSound.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */