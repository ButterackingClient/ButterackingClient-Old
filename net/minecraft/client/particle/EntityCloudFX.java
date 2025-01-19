/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityCloudFX extends EntityFX {
/*    */   float field_70569_a;
/*    */   
/*    */   protected EntityCloudFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double p_i1221_8_, double p_i1221_10_, double p_i1221_12_) {
/* 13 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
/* 14 */     float f = 2.5F;
/* 15 */     this.motionX *= 0.10000000149011612D;
/* 16 */     this.motionY *= 0.10000000149011612D;
/* 17 */     this.motionZ *= 0.10000000149011612D;
/* 18 */     this.motionX += p_i1221_8_;
/* 19 */     this.motionY += p_i1221_10_;
/* 20 */     this.motionZ += p_i1221_12_;
/* 21 */     this.particleRed = this.particleGreen = this.particleBlue = 1.0F - (float)(Math.random() * 0.30000001192092896D);
/* 22 */     this.particleScale *= 0.75F;
/* 23 */     this.particleScale *= f;
/* 24 */     this.field_70569_a = this.particleScale;
/* 25 */     this.particleMaxAge = (int)(8.0D / (Math.random() * 0.8D + 0.3D));
/* 26 */     this.particleMaxAge = (int)(this.particleMaxAge * f);
/* 27 */     this.noClip = false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/* 34 */     float f = (this.particleAge + partialTicks) / this.particleMaxAge * 32.0F;
/* 35 */     f = MathHelper.clamp_float(f, 0.0F, 1.0F);
/* 36 */     this.particleScale = this.field_70569_a * f;
/* 37 */     super.renderParticle(worldRendererIn, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 44 */     this.prevPosX = this.posX;
/* 45 */     this.prevPosY = this.posY;
/* 46 */     this.prevPosZ = this.posZ;
/*    */     
/* 48 */     if (this.particleAge++ >= this.particleMaxAge) {
/* 49 */       setDead();
/*    */     }
/*    */     
/* 52 */     setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
/* 53 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/* 54 */     this.motionX *= 0.9599999785423279D;
/* 55 */     this.motionY *= 0.9599999785423279D;
/* 56 */     this.motionZ *= 0.9599999785423279D;
/* 57 */     EntityPlayer entityplayer = this.worldObj.getClosestPlayerToEntity(this, 2.0D);
/*    */     
/* 59 */     if (entityplayer != null && this.posY > (entityplayer.getEntityBoundingBox()).minY) {
/* 60 */       this.posY += ((entityplayer.getEntityBoundingBox()).minY - this.posY) * 0.2D;
/* 61 */       this.motionY += (entityplayer.motionY - this.motionY) * 0.2D;
/* 62 */       setPosition(this.posX, this.posY, this.posZ);
/*    */     } 
/*    */     
/* 65 */     if (this.onGround) {
/* 66 */       this.motionX *= 0.699999988079071D;
/* 67 */       this.motionZ *= 0.699999988079071D;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static class Factory implements IParticleFactory {
/*    */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 73 */       return new EntityCloudFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\particle\EntityCloudFX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */