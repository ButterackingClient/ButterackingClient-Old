/*    */ package net.minecraft.entity.effect;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.EnumDifficulty;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityLightningBolt
/*    */   extends EntityWeatherEffect
/*    */ {
/*    */   private int lightningState;
/*    */   public long boltVertex;
/*    */   private int boltLivingTime;
/*    */   
/*    */   public EntityLightningBolt(World worldIn, double posX, double posY, double posZ) {
/* 31 */     super(worldIn);
/* 32 */     setLocationAndAngles(posX, posY, posZ, 0.0F, 0.0F);
/* 33 */     this.lightningState = 2;
/* 34 */     this.boltVertex = this.rand.nextLong();
/* 35 */     this.boltLivingTime = this.rand.nextInt(3) + 1;
/* 36 */     BlockPos blockpos = new BlockPos(this);
/*    */     
/* 38 */     if (!worldIn.isRemote && worldIn.getGameRules().getBoolean("doFireTick") && (worldIn.getDifficulty() == EnumDifficulty.NORMAL || worldIn.getDifficulty() == EnumDifficulty.HARD) && worldIn.isAreaLoaded(blockpos, 10)) {
/* 39 */       if (worldIn.getBlockState(blockpos).getBlock().getMaterial() == Material.air && Blocks.fire.canPlaceBlockAt(worldIn, blockpos)) {
/* 40 */         worldIn.setBlockState(blockpos, Blocks.fire.getDefaultState());
/*    */       }
/*    */       
/* 43 */       for (int i = 0; i < 4; i++) {
/* 44 */         BlockPos blockpos1 = blockpos.add(this.rand.nextInt(3) - 1, this.rand.nextInt(3) - 1, this.rand.nextInt(3) - 1);
/*    */         
/* 46 */         if (worldIn.getBlockState(blockpos1).getBlock().getMaterial() == Material.air && Blocks.fire.canPlaceBlockAt(worldIn, blockpos1)) {
/* 47 */           worldIn.setBlockState(blockpos1, Blocks.fire.getDefaultState());
/*    */         }
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 57 */     super.onUpdate();
/*    */     
/* 59 */     if (this.lightningState == 2) {
/* 60 */       this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "ambient.weather.thunder", 10000.0F, 0.8F + this.rand.nextFloat() * 0.2F);
/* 61 */       this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "random.explode", 2.0F, 0.5F + this.rand.nextFloat() * 0.2F);
/*    */     } 
/*    */     
/* 64 */     this.lightningState--;
/*    */     
/* 66 */     if (this.lightningState < 0) {
/* 67 */       if (this.boltLivingTime == 0) {
/* 68 */         setDead();
/* 69 */       } else if (this.lightningState < -this.rand.nextInt(10)) {
/* 70 */         this.boltLivingTime--;
/* 71 */         this.lightningState = 1;
/* 72 */         this.boltVertex = this.rand.nextLong();
/* 73 */         BlockPos blockpos = new BlockPos(this);
/*    */         
/* 75 */         if (!this.worldObj.isRemote && this.worldObj.getGameRules().getBoolean("doFireTick") && this.worldObj.isAreaLoaded(blockpos, 10) && this.worldObj.getBlockState(blockpos).getBlock().getMaterial() == Material.air && Blocks.fire.canPlaceBlockAt(this.worldObj, blockpos)) {
/* 76 */           this.worldObj.setBlockState(blockpos, Blocks.fire.getDefaultState());
/*    */         }
/*    */       } 
/*    */     }
/*    */     
/* 81 */     if (this.lightningState >= 0)
/* 82 */       if (this.worldObj.isRemote) {
/* 83 */         this.worldObj.setLastLightningBolt(2);
/*    */       } else {
/* 85 */         double d0 = 3.0D;
/* 86 */         List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, new AxisAlignedBB(this.posX - d0, this.posY - d0, this.posZ - d0, this.posX + d0, this.posY + 6.0D + d0, this.posZ + d0));
/*    */         
/* 88 */         for (int i = 0; i < list.size(); i++) {
/* 89 */           Entity entity = list.get(i);
/* 90 */           entity.onStruckByLightning(this);
/*    */         } 
/*    */       }  
/*    */   }
/*    */   
/*    */   protected void entityInit() {}
/*    */   
/*    */   protected void readEntityFromNBT(NBTTagCompound tagCompund) {}
/*    */   
/*    */   protected void writeEntityToNBT(NBTTagCompound tagCompound) {}
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\effect\EntityLightningBolt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */