/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockCrops;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.passive.EntityVillager;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.InventoryBasic;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityAIHarvestFarmland
/*     */   extends EntityAIMoveToBlock {
/*     */   private final EntityVillager theVillager;
/*     */   private boolean hasFarmItem;
/*     */   private boolean field_179503_e;
/*     */   private int field_179501_f;
/*     */   
/*     */   public EntityAIHarvestFarmland(EntityVillager theVillagerIn, double speedIn) {
/*  24 */     super((EntityCreature)theVillagerIn, speedIn, 16);
/*  25 */     this.theVillager = theVillagerIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldExecute() {
/*  32 */     if (this.runDelay <= 0) {
/*  33 */       if (!this.theVillager.worldObj.getGameRules().getBoolean("mobGriefing")) {
/*  34 */         return false;
/*     */       }
/*     */       
/*  37 */       this.field_179501_f = -1;
/*  38 */       this.hasFarmItem = this.theVillager.isFarmItemInInventory();
/*  39 */       this.field_179503_e = this.theVillager.func_175557_cr();
/*     */     } 
/*     */     
/*  42 */     return super.shouldExecute();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean continueExecuting() {
/*  49 */     return (this.field_179501_f >= 0 && super.continueExecuting());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startExecuting() {
/*  56 */     super.startExecuting();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetTask() {
/*  63 */     super.resetTask();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTask() {
/*  70 */     super.updateTask();
/*  71 */     this.theVillager.getLookHelper().setLookPosition(this.destinationBlock.getX() + 0.5D, (this.destinationBlock.getY() + 1), this.destinationBlock.getZ() + 0.5D, 10.0F, this.theVillager.getVerticalFaceSpeed());
/*     */     
/*  73 */     if (getIsAboveDestination()) {
/*  74 */       World world = this.theVillager.worldObj;
/*  75 */       BlockPos blockpos = this.destinationBlock.up();
/*  76 */       IBlockState iblockstate = world.getBlockState(blockpos);
/*  77 */       Block block = iblockstate.getBlock();
/*     */       
/*  79 */       if (this.field_179501_f == 0 && block instanceof BlockCrops && ((Integer)iblockstate.getValue((IProperty)BlockCrops.AGE)).intValue() == 7) {
/*  80 */         world.destroyBlock(blockpos, true);
/*  81 */       } else if (this.field_179501_f == 1 && block == Blocks.air) {
/*  82 */         InventoryBasic inventorybasic = this.theVillager.getVillagerInventory();
/*     */         
/*  84 */         for (int i = 0; i < inventorybasic.getSizeInventory(); i++) {
/*  85 */           ItemStack itemstack = inventorybasic.getStackInSlot(i);
/*  86 */           boolean flag = false;
/*     */           
/*  88 */           if (itemstack != null) {
/*  89 */             if (itemstack.getItem() == Items.wheat_seeds) {
/*  90 */               world.setBlockState(blockpos, Blocks.wheat.getDefaultState(), 3);
/*  91 */               flag = true;
/*  92 */             } else if (itemstack.getItem() == Items.potato) {
/*  93 */               world.setBlockState(blockpos, Blocks.potatoes.getDefaultState(), 3);
/*  94 */               flag = true;
/*  95 */             } else if (itemstack.getItem() == Items.carrot) {
/*  96 */               world.setBlockState(blockpos, Blocks.carrots.getDefaultState(), 3);
/*  97 */               flag = true;
/*     */             } 
/*     */           }
/*     */           
/* 101 */           if (flag) {
/* 102 */             itemstack.stackSize--;
/*     */             
/* 104 */             if (itemstack.stackSize <= 0) {
/* 105 */               inventorybasic.setInventorySlotContents(i, null);
/*     */             }
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 113 */       this.field_179501_f = -1;
/* 114 */       this.runDelay = 10;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean shouldMoveTo(World worldIn, BlockPos pos) {
/* 122 */     Block block = worldIn.getBlockState(pos).getBlock();
/*     */     
/* 124 */     if (block == Blocks.farmland) {
/* 125 */       pos = pos.up();
/* 126 */       IBlockState iblockstate = worldIn.getBlockState(pos);
/* 127 */       block = iblockstate.getBlock();
/*     */       
/* 129 */       if (block instanceof BlockCrops && ((Integer)iblockstate.getValue((IProperty)BlockCrops.AGE)).intValue() == 7 && this.field_179503_e && (this.field_179501_f == 0 || this.field_179501_f < 0)) {
/* 130 */         this.field_179501_f = 0;
/* 131 */         return true;
/*     */       } 
/*     */       
/* 134 */       if (block == Blocks.air && this.hasFarmItem && (this.field_179501_f == 1 || this.field_179501_f < 0)) {
/* 135 */         this.field_179501_f = 1;
/* 136 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 140 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAIHarvestFarmland.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */