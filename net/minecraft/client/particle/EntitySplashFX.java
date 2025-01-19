/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntitySplashFX extends EntityRainFX {
/*    */   protected EntitySplashFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
/*  7 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn);
/*  8 */     this.particleGravity = 0.04F;
/*  9 */     nextTextureIndexX();
/*    */     
/* 11 */     if (ySpeedIn == 0.0D && (xSpeedIn != 0.0D || zSpeedIn != 0.0D)) {
/* 12 */       this.motionX = xSpeedIn;
/* 13 */       this.motionY = ySpeedIn + 0.1D;
/* 14 */       this.motionZ = zSpeedIn;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static class Factory implements IParticleFactory {
/*    */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 20 */       return new EntitySplashFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\particle\EntitySplashFX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */