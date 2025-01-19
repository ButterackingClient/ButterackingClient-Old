/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.EnumParticleTypes;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityHugeExplodeFX
/*    */   extends EntityFX
/*    */ {
/*    */   private int timeSinceStart;
/* 14 */   private int maximumTime = 8;
/*    */   
/*    */   protected EntityHugeExplodeFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double p_i1214_8_, double p_i1214_10_, double p_i1214_12_) {
/* 17 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 30 */     for (int i = 0; i < 6; i++) {
/* 31 */       double d0 = this.posX + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0D;
/* 32 */       double d1 = this.posY + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0D;
/* 33 */       double d2 = this.posZ + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0D;
/* 34 */       this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, d0, d1, d2, (this.timeSinceStart / this.maximumTime), 0.0D, 0.0D, new int[0]);
/*    */     } 
/*    */     
/* 37 */     this.timeSinceStart++;
/*    */     
/* 39 */     if (this.timeSinceStart == this.maximumTime) {
/* 40 */       setDead();
/*    */     }
/*    */   }
/*    */   
/*    */   public int getFXLayer() {
/* 45 */     return 1;
/*    */   }
/*    */   
/*    */   public static class Factory implements IParticleFactory {
/*    */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 50 */       return new EntityHugeExplodeFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\particle\EntityHugeExplodeFX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */