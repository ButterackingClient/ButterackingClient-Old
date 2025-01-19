/*     */ package net.minecraft.world;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.enchantment.EnchantmentProtection;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.item.EntityTNTPrimed;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Explosion
/*     */ {
/*     */   private final boolean isFlaming;
/*     */   private final boolean isSmoking;
/*     */   private final Random explosionRNG;
/*     */   private final World worldObj;
/*     */   private final double explosionX;
/*     */   private final double explosionY;
/*     */   private final double explosionZ;
/*     */   private final Entity exploder;
/*     */   private final float explosionSize;
/*     */   private final List<BlockPos> affectedBlockPositions;
/*     */   private final Map<EntityPlayer, Vec3> playerKnockbackMap;
/*     */   
/*     */   public Explosion(World worldIn, Entity entityIn, double x, double y, double z, float size, List<BlockPos> affectedPositions) {
/*  49 */     this(worldIn, entityIn, x, y, z, size, false, true, affectedPositions);
/*     */   }
/*     */   
/*     */   public Explosion(World worldIn, Entity entityIn, double x, double y, double z, float size, boolean flaming, boolean smoking, List<BlockPos> affectedPositions) {
/*  53 */     this(worldIn, entityIn, x, y, z, size, flaming, smoking);
/*  54 */     this.affectedBlockPositions.addAll(affectedPositions);
/*     */   }
/*     */   
/*     */   public Explosion(World worldIn, Entity entityIn, double x, double y, double z, float size, boolean flaming, boolean smoking) {
/*  58 */     this.explosionRNG = new Random();
/*  59 */     this.affectedBlockPositions = Lists.newArrayList();
/*  60 */     this.playerKnockbackMap = Maps.newHashMap();
/*  61 */     this.worldObj = worldIn;
/*  62 */     this.exploder = entityIn;
/*  63 */     this.explosionSize = size;
/*  64 */     this.explosionX = x;
/*  65 */     this.explosionY = y;
/*  66 */     this.explosionZ = z;
/*  67 */     this.isFlaming = flaming;
/*  68 */     this.isSmoking = smoking;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doExplosionA() {
/*  75 */     Set<BlockPos> set = Sets.newHashSet();
/*  76 */     int i = 16;
/*     */     
/*  78 */     for (int j = 0; j < 16; j++) {
/*  79 */       for (int k = 0; k < 16; k++) {
/*  80 */         for (int l = 0; l < 16; l++) {
/*  81 */           if (j == 0 || j == 15 || k == 0 || k == 15 || l == 0 || l == 15) {
/*  82 */             double d0 = (j / 15.0F * 2.0F - 1.0F);
/*  83 */             double d1 = (k / 15.0F * 2.0F - 1.0F);
/*  84 */             double d2 = (l / 15.0F * 2.0F - 1.0F);
/*  85 */             double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
/*  86 */             d0 /= d3;
/*  87 */             d1 /= d3;
/*  88 */             d2 /= d3;
/*  89 */             float f = this.explosionSize * (0.7F + this.worldObj.rand.nextFloat() * 0.6F);
/*  90 */             double d4 = this.explosionX;
/*  91 */             double d6 = this.explosionY;
/*  92 */             double d8 = this.explosionZ;
/*     */             
/*  94 */             for (float f1 = 0.3F; f > 0.0F; f -= 0.22500001F) {
/*  95 */               BlockPos blockpos = new BlockPos(d4, d6, d8);
/*  96 */               IBlockState iblockstate = this.worldObj.getBlockState(blockpos);
/*     */               
/*  98 */               if (iblockstate.getBlock().getMaterial() != Material.air) {
/*  99 */                 float f2 = (this.exploder != null) ? this.exploder.getExplosionResistance(this, this.worldObj, blockpos, iblockstate) : iblockstate.getBlock().getExplosionResistance(null);
/* 100 */                 f -= (f2 + 0.3F) * 0.3F;
/*     */               } 
/*     */               
/* 103 */               if (f > 0.0F && (this.exploder == null || this.exploder.verifyExplosion(this, this.worldObj, blockpos, iblockstate, f))) {
/* 104 */                 set.add(blockpos);
/*     */               }
/*     */               
/* 107 */               d4 += d0 * 0.30000001192092896D;
/* 108 */               d6 += d1 * 0.30000001192092896D;
/* 109 */               d8 += d2 * 0.30000001192092896D;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 116 */     this.affectedBlockPositions.addAll(set);
/* 117 */     float f3 = this.explosionSize * 2.0F;
/* 118 */     int k1 = MathHelper.floor_double(this.explosionX - f3 - 1.0D);
/* 119 */     int l1 = MathHelper.floor_double(this.explosionX + f3 + 1.0D);
/* 120 */     int i2 = MathHelper.floor_double(this.explosionY - f3 - 1.0D);
/* 121 */     int i1 = MathHelper.floor_double(this.explosionY + f3 + 1.0D);
/* 122 */     int j2 = MathHelper.floor_double(this.explosionZ - f3 - 1.0D);
/* 123 */     int j1 = MathHelper.floor_double(this.explosionZ + f3 + 1.0D);
/* 124 */     List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this.exploder, new AxisAlignedBB(k1, i2, j2, l1, i1, j1));
/* 125 */     Vec3 vec3 = new Vec3(this.explosionX, this.explosionY, this.explosionZ);
/*     */     
/* 127 */     for (int k2 = 0; k2 < list.size(); k2++) {
/* 128 */       Entity entity = list.get(k2);
/*     */       
/* 130 */       if (!entity.isImmuneToExplosions()) {
/* 131 */         double d12 = entity.getDistance(this.explosionX, this.explosionY, this.explosionZ) / f3;
/*     */         
/* 133 */         if (d12 <= 1.0D) {
/* 134 */           double d5 = entity.posX - this.explosionX;
/* 135 */           double d7 = entity.posY + entity.getEyeHeight() - this.explosionY;
/* 136 */           double d9 = entity.posZ - this.explosionZ;
/* 137 */           double d13 = MathHelper.sqrt_double(d5 * d5 + d7 * d7 + d9 * d9);
/*     */           
/* 139 */           if (d13 != 0.0D) {
/* 140 */             d5 /= d13;
/* 141 */             d7 /= d13;
/* 142 */             d9 /= d13;
/* 143 */             double d14 = this.worldObj.getBlockDensity(vec3, entity.getEntityBoundingBox());
/* 144 */             double d10 = (1.0D - d12) * d14;
/* 145 */             entity.attackEntityFrom(DamageSource.setExplosionSource(this), (int)((d10 * d10 + d10) / 2.0D * 8.0D * f3 + 1.0D));
/* 146 */             double d11 = EnchantmentProtection.func_92092_a(entity, d10);
/* 147 */             entity.motionX += d5 * d11;
/* 148 */             entity.motionY += d7 * d11;
/* 149 */             entity.motionZ += d9 * d11;
/*     */             
/* 151 */             if (entity instanceof EntityPlayer && !((EntityPlayer)entity).capabilities.disableDamage) {
/* 152 */               this.playerKnockbackMap.put((EntityPlayer)entity, new Vec3(d5 * d10, d7 * d10, d9 * d10));
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doExplosionB(boolean spawnParticles) {
/* 164 */     this.worldObj.playSoundEffect(this.explosionX, this.explosionY, this.explosionZ, "random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
/*     */     
/* 166 */     if (this.explosionSize >= 2.0F && this.isSmoking) {
/* 167 */       this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D, new int[0]);
/*     */     } else {
/* 169 */       this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D, new int[0]);
/*     */     } 
/*     */     
/* 172 */     if (this.isSmoking) {
/* 173 */       for (BlockPos blockpos : this.affectedBlockPositions) {
/* 174 */         Block block = this.worldObj.getBlockState(blockpos).getBlock();
/*     */         
/* 176 */         if (spawnParticles) {
/* 177 */           double d0 = (blockpos.getX() + this.worldObj.rand.nextFloat());
/* 178 */           double d1 = (blockpos.getY() + this.worldObj.rand.nextFloat());
/* 179 */           double d2 = (blockpos.getZ() + this.worldObj.rand.nextFloat());
/* 180 */           double d3 = d0 - this.explosionX;
/* 181 */           double d4 = d1 - this.explosionY;
/* 182 */           double d5 = d2 - this.explosionZ;
/* 183 */           double d6 = MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
/* 184 */           d3 /= d6;
/* 185 */           d4 /= d6;
/* 186 */           d5 /= d6;
/* 187 */           double d7 = 0.5D / (d6 / this.explosionSize + 0.1D);
/* 188 */           d7 *= (this.worldObj.rand.nextFloat() * this.worldObj.rand.nextFloat() + 0.3F);
/* 189 */           d3 *= d7;
/* 190 */           d4 *= d7;
/* 191 */           d5 *= d7;
/* 192 */           this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (d0 + this.explosionX * 1.0D) / 2.0D, (d1 + this.explosionY * 1.0D) / 2.0D, (d2 + this.explosionZ * 1.0D) / 2.0D, d3, d4, d5, new int[0]);
/* 193 */           this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, d3, d4, d5, new int[0]);
/*     */         } 
/*     */         
/* 196 */         if (block.getMaterial() != Material.air) {
/* 197 */           if (block.canDropFromExplosion(this)) {
/* 198 */             block.dropBlockAsItemWithChance(this.worldObj, blockpos, this.worldObj.getBlockState(blockpos), 1.0F / this.explosionSize, 0);
/*     */           }
/*     */           
/* 201 */           this.worldObj.setBlockState(blockpos, Blocks.air.getDefaultState(), 3);
/* 202 */           block.onBlockDestroyedByExplosion(this.worldObj, blockpos, this);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 207 */     if (this.isFlaming) {
/* 208 */       for (BlockPos blockpos1 : this.affectedBlockPositions) {
/* 209 */         if (this.worldObj.getBlockState(blockpos1).getBlock().getMaterial() == Material.air && this.worldObj.getBlockState(blockpos1.down()).getBlock().isFullBlock() && this.explosionRNG.nextInt(3) == 0) {
/* 210 */           this.worldObj.setBlockState(blockpos1, Blocks.fire.getDefaultState());
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public Map<EntityPlayer, Vec3> getPlayerKnockbackMap() {
/* 217 */     return this.playerKnockbackMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityLivingBase getExplosivePlacedBy() {
/* 224 */     return (this.exploder == null) ? null : ((this.exploder instanceof EntityTNTPrimed) ? ((EntityTNTPrimed)this.exploder).getTntPlacedBy() : ((this.exploder instanceof EntityLivingBase) ? (EntityLivingBase)this.exploder : null));
/*     */   }
/*     */   
/*     */   public void clearAffectedBlockPositions() {
/* 228 */     this.affectedBlockPositions.clear();
/*     */   }
/*     */   
/*     */   public List<BlockPos> getAffectedBlockPositions() {
/* 232 */     return this.affectedBlockPositions;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\Explosion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */