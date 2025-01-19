/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityEnchantmentTableParticleFX extends EntityFX {
/*    */   private float field_70565_a;
/*    */   private double coordX;
/*    */   private double coordY;
/*    */   private double coordZ;
/*    */   
/*    */   protected EntityEnchantmentTableParticleFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
/* 12 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/* 13 */     this.motionX = xSpeedIn;
/* 14 */     this.motionY = ySpeedIn;
/* 15 */     this.motionZ = zSpeedIn;
/* 16 */     this.coordX = xCoordIn;
/* 17 */     this.coordY = yCoordIn;
/* 18 */     this.coordZ = zCoordIn;
/* 19 */     this.posX = this.prevPosX = xCoordIn + xSpeedIn;
/* 20 */     this.posY = this.prevPosY = yCoordIn + ySpeedIn;
/* 21 */     this.posZ = this.prevPosZ = zCoordIn + zSpeedIn;
/* 22 */     float f = this.rand.nextFloat() * 0.6F + 0.4F;
/* 23 */     this.field_70565_a = this.particleScale = this.rand.nextFloat() * 0.5F + 0.2F;
/* 24 */     this.particleRed = this.particleGreen = this.particleBlue = 1.0F * f;
/* 25 */     this.particleGreen *= 0.9F;
/* 26 */     this.particleRed *= 0.9F;
/* 27 */     this.particleMaxAge = (int)(Math.random() * 10.0D) + 30;
/* 28 */     this.noClip = true;
/* 29 */     setParticleTextureIndex((int)(Math.random() * 26.0D + 1.0D + 224.0D));
/*    */   }
/*    */   
/*    */   public int getBrightnessForRender(float partialTicks) {
/* 33 */     int i = super.getBrightnessForRender(partialTicks);
/* 34 */     float f = this.particleAge / this.particleMaxAge;
/* 35 */     f *= f;
/* 36 */     f *= f;
/* 37 */     int j = i & 0xFF;
/* 38 */     int k = i >> 16 & 0xFF;
/* 39 */     k += (int)(f * 15.0F * 16.0F);
/*    */     
/* 41 */     if (k > 240) {
/* 42 */       k = 240;
/*    */     }
/*    */     
/* 45 */     return j | k << 16;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getBrightness(float partialTicks) {
/* 52 */     float f = super.getBrightness(partialTicks);
/* 53 */     float f1 = this.particleAge / this.particleMaxAge;
/* 54 */     f1 *= f1;
/* 55 */     f1 *= f1;
/* 56 */     return f * (1.0F - f1) + f1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 63 */     this.prevPosX = this.posX;
/* 64 */     this.prevPosY = this.posY;
/* 65 */     this.prevPosZ = this.posZ;
/* 66 */     float f = this.particleAge / this.particleMaxAge;
/* 67 */     f = 1.0F - f;
/* 68 */     float f1 = 1.0F - f;
/* 69 */     f1 *= f1;
/* 70 */     f1 *= f1;
/* 71 */     this.posX = this.coordX + this.motionX * f;
/* 72 */     this.posY = this.coordY + this.motionY * f - (f1 * 1.2F);
/* 73 */     this.posZ = this.coordZ + this.motionZ * f;
/*    */     
/* 75 */     if (this.particleAge++ >= this.particleMaxAge)
/* 76 */       setDead(); 
/*    */   }
/*    */   
/*    */   public static class EnchantmentTable
/*    */     implements IParticleFactory {
/*    */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 82 */       return new EntityEnchantmentTableParticleFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\particle\EntityEnchantmentTableParticleFX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */