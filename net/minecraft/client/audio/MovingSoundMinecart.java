/*    */ package net.minecraft.client.audio;
/*    */ 
/*    */ import net.minecraft.entity.item.EntityMinecart;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class MovingSoundMinecart extends MovingSound {
/*    */   private final EntityMinecart minecart;
/*  9 */   private float distance = 0.0F;
/*    */   
/*    */   public MovingSoundMinecart(EntityMinecart minecartIn) {
/* 12 */     super(new ResourceLocation("minecraft:minecart.base"));
/* 13 */     this.minecart = minecartIn;
/* 14 */     this.repeat = true;
/* 15 */     this.repeatDelay = 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void update() {
/* 22 */     if (this.minecart.isDead) {
/* 23 */       this.donePlaying = true;
/*    */     } else {
/* 25 */       this.xPosF = (float)this.minecart.posX;
/* 26 */       this.yPosF = (float)this.minecart.posY;
/* 27 */       this.zPosF = (float)this.minecart.posZ;
/* 28 */       float f = MathHelper.sqrt_double(this.minecart.motionX * this.minecart.motionX + this.minecart.motionZ * this.minecart.motionZ);
/*    */       
/* 30 */       if (f >= 0.01D) {
/* 31 */         this.distance = MathHelper.clamp_float(this.distance + 0.0025F, 0.0F, 1.0F);
/* 32 */         this.volume = 0.0F + MathHelper.clamp_float(f, 0.0F, 0.5F) * 0.7F;
/*    */       } else {
/* 34 */         this.distance = 0.0F;
/* 35 */         this.volume = 0.0F;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\audio\MovingSoundMinecart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */