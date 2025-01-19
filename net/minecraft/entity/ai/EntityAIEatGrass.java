/*     */ package net.minecraft.entity.ai;
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Predicates;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockTallGrass;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.block.state.pattern.BlockStateHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ 
/*     */ public class EntityAIEatGrass extends EntityAIBase {
/*  15 */   private static final Predicate<IBlockState> field_179505_b = (Predicate<IBlockState>)BlockStateHelper.forBlock((Block)Blocks.tallgrass).where((IProperty)BlockTallGrass.TYPE, Predicates.equalTo(BlockTallGrass.EnumType.GRASS));
/*     */ 
/*     */ 
/*     */   
/*     */   private EntityLiving grassEaterEntity;
/*     */ 
/*     */ 
/*     */   
/*     */   private World entityWorld;
/*     */ 
/*     */ 
/*     */   
/*     */   int eatingGrassTimer;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityAIEatGrass(EntityLiving grassEaterEntityIn) {
/*  33 */     this.grassEaterEntity = grassEaterEntityIn;
/*  34 */     this.entityWorld = grassEaterEntityIn.worldObj;
/*  35 */     setMutexBits(7);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldExecute() {
/*  42 */     if (this.grassEaterEntity.getRNG().nextInt(this.grassEaterEntity.isChild() ? 50 : 1000) != 0) {
/*  43 */       return false;
/*     */     }
/*  45 */     BlockPos blockpos = new BlockPos(this.grassEaterEntity.posX, this.grassEaterEntity.posY, this.grassEaterEntity.posZ);
/*  46 */     return field_179505_b.apply(this.entityWorld.getBlockState(blockpos)) ? true : ((this.entityWorld.getBlockState(blockpos.down()).getBlock() == Blocks.grass));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startExecuting() {
/*  54 */     this.eatingGrassTimer = 40;
/*  55 */     this.entityWorld.setEntityState((Entity)this.grassEaterEntity, (byte)10);
/*  56 */     this.grassEaterEntity.getNavigator().clearPathEntity();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetTask() {
/*  63 */     this.eatingGrassTimer = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean continueExecuting() {
/*  70 */     return (this.eatingGrassTimer > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEatingGrassTimer() {
/*  77 */     return this.eatingGrassTimer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTask() {
/*  84 */     this.eatingGrassTimer = Math.max(0, this.eatingGrassTimer - 1);
/*     */     
/*  86 */     if (this.eatingGrassTimer == 4) {
/*  87 */       BlockPos blockpos = new BlockPos(this.grassEaterEntity.posX, this.grassEaterEntity.posY, this.grassEaterEntity.posZ);
/*     */       
/*  89 */       if (field_179505_b.apply(this.entityWorld.getBlockState(blockpos))) {
/*  90 */         if (this.entityWorld.getGameRules().getBoolean("mobGriefing")) {
/*  91 */           this.entityWorld.destroyBlock(blockpos, false);
/*     */         }
/*     */         
/*  94 */         this.grassEaterEntity.eatGrassBonus();
/*     */       } else {
/*  96 */         BlockPos blockpos1 = blockpos.down();
/*     */         
/*  98 */         if (this.entityWorld.getBlockState(blockpos1).getBlock() == Blocks.grass) {
/*  99 */           if (this.entityWorld.getGameRules().getBoolean("mobGriefing")) {
/* 100 */             this.entityWorld.playAuxSFX(2001, blockpos1, Block.getIdFromBlock((Block)Blocks.grass));
/* 101 */             this.entityWorld.setBlockState(blockpos1, Blocks.dirt.getDefaultState(), 2);
/*     */           } 
/*     */           
/* 104 */           this.grassEaterEntity.eatGrassBonus();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAIEatGrass.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */