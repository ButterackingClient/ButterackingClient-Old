/*    */ package net.minecraft.client.audio;
/*    */ 
/*    */ import net.minecraft.entity.item.EntityMinecart;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class MovingSoundMinecartRiding extends MovingSound {
/*    */   private final EntityPlayer player;
/*    */   private final EntityMinecart minecart;
/*    */   
/*    */   public MovingSoundMinecartRiding(EntityPlayer playerRiding, EntityMinecart minecart) {
/* 13 */     super(new ResourceLocation("minecraft:minecart.inside"));
/* 14 */     this.player = playerRiding;
/* 15 */     this.minecart = minecart;
/* 16 */     this.attenuationType = ISound.AttenuationType.NONE;
/* 17 */     this.repeat = true;
/* 18 */     this.repeatDelay = 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void update() {
/* 25 */     if (!this.minecart.isDead && this.player.isRiding() && this.player.ridingEntity == this.minecart) {
/* 26 */       float f = MathHelper.sqrt_double(this.minecart.motionX * this.minecart.motionX + this.minecart.motionZ * this.minecart.motionZ);
/*    */       
/* 28 */       if (f >= 0.01D) {
/* 29 */         this.volume = 0.0F + MathHelper.clamp_float(f, 0.0F, 1.0F) * 0.75F;
/*    */       } else {
/* 31 */         this.volume = 0.0F;
/*    */       } 
/*    */     } else {
/* 34 */       this.donePlaying = true;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\audio\MovingSoundMinecartRiding.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */