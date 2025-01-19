/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityFishWakeFX extends EntityFX {
/*    */   protected EntityFishWakeFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double p_i45073_8_, double p_i45073_10_, double p_i45073_12_) {
/*  7 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
/*  8 */     this.motionX *= 0.30000001192092896D;
/*  9 */     this.motionY = Math.random() * 0.20000000298023224D + 0.10000000149011612D;
/* 10 */     this.motionZ *= 0.30000001192092896D;
/* 11 */     this.particleRed = 1.0F;
/* 12 */     this.particleGreen = 1.0F;
/* 13 */     this.particleBlue = 1.0F;
/* 14 */     setParticleTextureIndex(19);
/* 15 */     setSize(0.01F, 0.01F);
/* 16 */     this.particleMaxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
/* 17 */     this.particleGravity = 0.0F;
/* 18 */     this.motionX = p_i45073_8_;
/* 19 */     this.motionY = p_i45073_10_;
/* 20 */     this.motionZ = p_i45073_12_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 27 */     this.prevPosX = this.posX;
/* 28 */     this.prevPosY = this.posY;
/* 29 */     this.prevPosZ = this.posZ;
/* 30 */     this.motionY -= this.particleGravity;
/* 31 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/* 32 */     this.motionX *= 0.9800000190734863D;
/* 33 */     this.motionY *= 0.9800000190734863D;
/* 34 */     this.motionZ *= 0.9800000190734863D;
/* 35 */     int i = 60 - this.particleMaxAge;
/* 36 */     float f = i * 0.001F;
/* 37 */     setSize(f, f);
/* 38 */     setParticleTextureIndex(19 + i % 4);
/*    */     
/* 40 */     if (this.particleMaxAge-- <= 0)
/* 41 */       setDead(); 
/*    */   }
/*    */   
/*    */   public static class Factory
/*    */     implements IParticleFactory {
/*    */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 47 */       return new EntityFishWakeFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\particle\EntityFishWakeFX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */