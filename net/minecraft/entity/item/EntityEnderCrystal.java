/*     */ package net.minecraft.entity.item;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityEnderCrystal
/*     */   extends Entity
/*     */ {
/*     */   public int innerRotation;
/*     */   public int health;
/*     */   
/*     */   public EntityEnderCrystal(World worldIn) {
/*  20 */     super(worldIn);
/*  21 */     this.preventEntitySpawning = true;
/*  22 */     setSize(2.0F, 2.0F);
/*  23 */     this.health = 5;
/*  24 */     this.innerRotation = this.rand.nextInt(100000);
/*     */   }
/*     */   
/*     */   public EntityEnderCrystal(World worldIn, double x, double y, double z) {
/*  28 */     this(worldIn);
/*  29 */     setPosition(x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canTriggerWalking() {
/*  37 */     return false;
/*     */   }
/*     */   
/*     */   protected void entityInit() {
/*  41 */     this.dataWatcher.addObject(8, Integer.valueOf(this.health));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  48 */     this.prevPosX = this.posX;
/*  49 */     this.prevPosY = this.posY;
/*  50 */     this.prevPosZ = this.posZ;
/*  51 */     this.innerRotation++;
/*  52 */     this.dataWatcher.updateObject(8, Integer.valueOf(this.health));
/*  53 */     int i = MathHelper.floor_double(this.posX);
/*  54 */     int j = MathHelper.floor_double(this.posY);
/*  55 */     int k = MathHelper.floor_double(this.posZ);
/*     */     
/*  57 */     if (this.worldObj.provider instanceof net.minecraft.world.WorldProviderEnd && this.worldObj.getBlockState(new BlockPos(i, j, k)).getBlock() != Blocks.fire) {
/*  58 */       this.worldObj.setBlockState(new BlockPos(i, j, k), Blocks.fire.getDefaultState());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeEntityToNBT(NBTTagCompound tagCompound) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readEntityFromNBT(NBTTagCompound tagCompund) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeCollidedWith() {
/*  78 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/*  85 */     if (isEntityInvulnerable(source)) {
/*  86 */       return false;
/*     */     }
/*  88 */     if (!this.isDead && !this.worldObj.isRemote) {
/*  89 */       this.health = 0;
/*     */       
/*  91 */       if (this.health <= 0) {
/*  92 */         setDead();
/*     */         
/*  94 */         if (!this.worldObj.isRemote) {
/*  95 */           this.worldObj.createExplosion(null, this.posX, this.posY, this.posZ, 6.0F, true);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 100 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\item\EntityEnderCrystal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */