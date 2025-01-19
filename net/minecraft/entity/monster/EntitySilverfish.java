/*     */ package net.minecraft.entity.monster;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockSilverfish;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EnumCreatureAttribute;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIAttackOnCollide;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.ai.EntityAISwimming;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntitySilverfish
/*     */   extends EntityMob {
/*     */   public EntitySilverfish(World worldIn) {
/*  29 */     super(worldIn);
/*  30 */     setSize(0.4F, 0.3F);
/*  31 */     this.tasks.addTask(1, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  32 */     this.tasks.addTask(3, this.summonSilverfish = new AISummonSilverfish(this));
/*  33 */     this.tasks.addTask(4, (EntityAIBase)new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
/*  34 */     this.tasks.addTask(5, (EntityAIBase)new AIHideInStone(this));
/*  35 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget(this, true, new Class[0]));
/*  36 */     this.targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
/*     */   }
/*     */ 
/*     */   
/*     */   private AISummonSilverfish summonSilverfish;
/*     */   
/*     */   public double getYOffset() {
/*  43 */     return 0.2D;
/*     */   }
/*     */   
/*     */   public float getEyeHeight() {
/*  47 */     return 0.1F;
/*     */   }
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  51 */     super.applyEntityAttributes();
/*  52 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
/*  53 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
/*  54 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canTriggerWalking() {
/*  62 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLivingSound() {
/*  69 */     return "mob.silverfish.say";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/*  76 */     return "mob.silverfish.hit";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/*  83 */     return "mob.silverfish.kill";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/*  90 */     if (isEntityInvulnerable(source)) {
/*  91 */       return false;
/*     */     }
/*  93 */     if (source instanceof net.minecraft.util.EntityDamageSource || source == DamageSource.magic) {
/*  94 */       this.summonSilverfish.func_179462_f();
/*     */     }
/*     */     
/*  97 */     return super.attackEntityFrom(source, amount);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos pos, Block blockIn) {
/* 102 */     playSound("mob.silverfish.step", 0.15F, 1.0F);
/*     */   }
/*     */   
/*     */   protected Item getDropItem() {
/* 106 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 113 */     this.renderYawOffset = this.rotationYaw;
/* 114 */     super.onUpdate();
/*     */   }
/*     */   
/*     */   public float getBlockPathWeight(BlockPos pos) {
/* 118 */     return (this.worldObj.getBlockState(pos.down()).getBlock() == Blocks.stone) ? 10.0F : super.getBlockPathWeight(pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isValidLightLevel() {
/* 125 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanSpawnHere() {
/* 132 */     if (super.getCanSpawnHere()) {
/* 133 */       EntityPlayer entityplayer = this.worldObj.getClosestPlayerToEntity((Entity)this, 5.0D);
/* 134 */       return (entityplayer == null);
/*     */     } 
/* 136 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumCreatureAttribute getCreatureAttribute() {
/* 144 */     return EnumCreatureAttribute.ARTHROPOD;
/*     */   }
/*     */   
/*     */   static class AIHideInStone extends EntityAIWander {
/*     */     private final EntitySilverfish silverfish;
/*     */     private EnumFacing facing;
/*     */     private boolean field_179484_c;
/*     */     
/*     */     public AIHideInStone(EntitySilverfish silverfishIn) {
/* 153 */       super(silverfishIn, 1.0D, 10);
/* 154 */       this.silverfish = silverfishIn;
/* 155 */       setMutexBits(1);
/*     */     }
/*     */     
/*     */     public boolean shouldExecute() {
/* 159 */       if (this.silverfish.getAttackTarget() != null)
/* 160 */         return false; 
/* 161 */       if (!this.silverfish.getNavigator().noPath()) {
/* 162 */         return false;
/*     */       }
/* 164 */       Random random = this.silverfish.getRNG();
/*     */       
/* 166 */       if (random.nextInt(10) == 0) {
/* 167 */         this.facing = EnumFacing.random(random);
/* 168 */         BlockPos blockpos = (new BlockPos(this.silverfish.posX, this.silverfish.posY + 0.5D, this.silverfish.posZ)).offset(this.facing);
/* 169 */         IBlockState iblockstate = this.silverfish.worldObj.getBlockState(blockpos);
/*     */         
/* 171 */         if (BlockSilverfish.canContainSilverfish(iblockstate)) {
/* 172 */           this.field_179484_c = true;
/* 173 */           return true;
/*     */         } 
/*     */       } 
/*     */       
/* 177 */       this.field_179484_c = false;
/* 178 */       return super.shouldExecute();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean continueExecuting() {
/* 183 */       return this.field_179484_c ? false : super.continueExecuting();
/*     */     }
/*     */     
/*     */     public void startExecuting() {
/* 187 */       if (!this.field_179484_c) {
/* 188 */         super.startExecuting();
/*     */       } else {
/* 190 */         World world = this.silverfish.worldObj;
/* 191 */         BlockPos blockpos = (new BlockPos(this.silverfish.posX, this.silverfish.posY + 0.5D, this.silverfish.posZ)).offset(this.facing);
/* 192 */         IBlockState iblockstate = world.getBlockState(blockpos);
/*     */         
/* 194 */         if (BlockSilverfish.canContainSilverfish(iblockstate)) {
/* 195 */           world.setBlockState(blockpos, Blocks.monster_egg.getDefaultState().withProperty((IProperty)BlockSilverfish.VARIANT, (Comparable)BlockSilverfish.EnumType.forModelBlock(iblockstate)), 3);
/* 196 */           this.silverfish.spawnExplosionParticle();
/* 197 */           this.silverfish.setDead();
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   static class AISummonSilverfish extends EntityAIBase {
/*     */     private EntitySilverfish silverfish;
/*     */     private int field_179463_b;
/*     */     
/*     */     public AISummonSilverfish(EntitySilverfish silverfishIn) {
/* 208 */       this.silverfish = silverfishIn;
/*     */     }
/*     */     
/*     */     public void func_179462_f() {
/* 212 */       if (this.field_179463_b == 0) {
/* 213 */         this.field_179463_b = 20;
/*     */       }
/*     */     }
/*     */     
/*     */     public boolean shouldExecute() {
/* 218 */       return (this.field_179463_b > 0);
/*     */     }
/*     */     
/*     */     public void updateTask() {
/* 222 */       this.field_179463_b--;
/*     */       
/* 224 */       if (this.field_179463_b <= 0) {
/* 225 */         World world = this.silverfish.worldObj;
/* 226 */         Random random = this.silverfish.getRNG();
/* 227 */         BlockPos blockpos = new BlockPos((Entity)this.silverfish);
/*     */         
/* 229 */         for (int i = 0; i <= 5 && i >= -5; i = (i <= 0) ? (1 - i) : (0 - i)) {
/* 230 */           for (int j = 0; j <= 10 && j >= -10; j = (j <= 0) ? (1 - j) : (0 - j)) {
/* 231 */             for (int k = 0; k <= 10 && k >= -10; k = (k <= 0) ? (1 - k) : (0 - k)) {
/* 232 */               BlockPos blockpos1 = blockpos.add(j, i, k);
/* 233 */               IBlockState iblockstate = world.getBlockState(blockpos1);
/*     */               
/* 235 */               if (iblockstate.getBlock() == Blocks.monster_egg) {
/* 236 */                 if (world.getGameRules().getBoolean("mobGriefing")) {
/* 237 */                   world.destroyBlock(blockpos1, true);
/*     */                 } else {
/* 239 */                   world.setBlockState(blockpos1, ((BlockSilverfish.EnumType)iblockstate.getValue((IProperty)BlockSilverfish.VARIANT)).getModelBlock(), 3);
/*     */                 } 
/*     */                 
/* 242 */                 if (random.nextBoolean())
/*     */                   return; 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\monster\EntitySilverfish.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */