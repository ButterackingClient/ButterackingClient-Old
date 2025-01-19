/*     */ package net.minecraft.block.state;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockPistonBase;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class BlockPistonStructureHelper
/*     */ {
/*     */   private final World world;
/*     */   private final BlockPos pistonPos;
/*     */   private final BlockPos blockToMove;
/*     */   private final EnumFacing moveDirection;
/*  20 */   private final List<BlockPos> toMove = Lists.newArrayList();
/*  21 */   private final List<BlockPos> toDestroy = Lists.newArrayList();
/*     */   
/*     */   public BlockPistonStructureHelper(World worldIn, BlockPos posIn, EnumFacing pistonFacing, boolean extending) {
/*  24 */     this.world = worldIn;
/*  25 */     this.pistonPos = posIn;
/*     */     
/*  27 */     if (extending) {
/*  28 */       this.moveDirection = pistonFacing;
/*  29 */       this.blockToMove = posIn.offset(pistonFacing);
/*     */     } else {
/*  31 */       this.moveDirection = pistonFacing.getOpposite();
/*  32 */       this.blockToMove = posIn.offset(pistonFacing, 2);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean canMove() {
/*  37 */     this.toMove.clear();
/*  38 */     this.toDestroy.clear();
/*  39 */     Block block = this.world.getBlockState(this.blockToMove).getBlock();
/*     */     
/*  41 */     if (!BlockPistonBase.canPush(block, this.world, this.blockToMove, this.moveDirection, false)) {
/*  42 */       if (block.getMobilityFlag() != 1) {
/*  43 */         return false;
/*     */       }
/*  45 */       this.toDestroy.add(this.blockToMove);
/*  46 */       return true;
/*     */     } 
/*  48 */     if (!func_177251_a(this.blockToMove)) {
/*  49 */       return false;
/*     */     }
/*  51 */     for (int i = 0; i < this.toMove.size(); i++) {
/*  52 */       BlockPos blockpos = this.toMove.get(i);
/*     */       
/*  54 */       if (this.world.getBlockState(blockpos).getBlock() == Blocks.slime_block && !func_177250_b(blockpos)) {
/*  55 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  59 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean func_177251_a(BlockPos origin) {
/*  64 */     Block block = this.world.getBlockState(origin).getBlock();
/*     */     
/*  66 */     if (block.getMaterial() == Material.air)
/*  67 */       return true; 
/*  68 */     if (!BlockPistonBase.canPush(block, this.world, origin, this.moveDirection, false))
/*  69 */       return true; 
/*  70 */     if (origin.equals(this.pistonPos))
/*  71 */       return true; 
/*  72 */     if (this.toMove.contains(origin)) {
/*  73 */       return true;
/*     */     }
/*  75 */     int i = 1;
/*     */     
/*  77 */     if (i + this.toMove.size() > 12) {
/*  78 */       return false;
/*     */     }
/*  80 */     while (block == Blocks.slime_block) {
/*  81 */       BlockPos blockpos = origin.offset(this.moveDirection.getOpposite(), i);
/*  82 */       block = this.world.getBlockState(blockpos).getBlock();
/*     */       
/*  84 */       if (block.getMaterial() == Material.air || !BlockPistonBase.canPush(block, this.world, blockpos, this.moveDirection, false) || blockpos.equals(this.pistonPos)) {
/*     */         break;
/*     */       }
/*     */       
/*  88 */       i++;
/*     */       
/*  90 */       if (i + this.toMove.size() > 12) {
/*  91 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  95 */     int i1 = 0;
/*     */     
/*  97 */     for (int j = i - 1; j >= 0; j--) {
/*  98 */       this.toMove.add(origin.offset(this.moveDirection.getOpposite(), j));
/*  99 */       i1++;
/*     */     } 
/*     */     
/* 102 */     int j1 = 1;
/*     */     
/*     */     while (true) {
/* 105 */       BlockPos blockpos1 = origin.offset(this.moveDirection, j1);
/* 106 */       int k = this.toMove.indexOf(blockpos1);
/*     */       
/* 108 */       if (k > -1) {
/* 109 */         func_177255_a(i1, k);
/*     */         
/* 111 */         for (int l = 0; l <= k + i1; l++) {
/* 112 */           BlockPos blockpos2 = this.toMove.get(l);
/*     */           
/* 114 */           if (this.world.getBlockState(blockpos2).getBlock() == Blocks.slime_block && !func_177250_b(blockpos2)) {
/* 115 */             return false;
/*     */           }
/*     */         } 
/*     */         
/* 119 */         return true;
/*     */       } 
/*     */       
/* 122 */       block = this.world.getBlockState(blockpos1).getBlock();
/*     */       
/* 124 */       if (block.getMaterial() == Material.air) {
/* 125 */         return true;
/*     */       }
/*     */       
/* 128 */       if (!BlockPistonBase.canPush(block, this.world, blockpos1, this.moveDirection, true) || blockpos1.equals(this.pistonPos)) {
/* 129 */         return false;
/*     */       }
/*     */       
/* 132 */       if (block.getMobilityFlag() == 1) {
/* 133 */         this.toDestroy.add(blockpos1);
/* 134 */         return true;
/*     */       } 
/*     */       
/* 137 */       if (this.toMove.size() >= 12) {
/* 138 */         return false;
/*     */       }
/*     */       
/* 141 */       this.toMove.add(blockpos1);
/* 142 */       i1++;
/* 143 */       j1++;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void func_177255_a(int p_177255_1_, int p_177255_2_) {
/* 150 */     List<BlockPos> list = Lists.newArrayList();
/* 151 */     List<BlockPos> list1 = Lists.newArrayList();
/* 152 */     List<BlockPos> list2 = Lists.newArrayList();
/* 153 */     list.addAll(this.toMove.subList(0, p_177255_2_));
/* 154 */     list1.addAll(this.toMove.subList(this.toMove.size() - p_177255_1_, this.toMove.size()));
/* 155 */     list2.addAll(this.toMove.subList(p_177255_2_, this.toMove.size() - p_177255_1_));
/* 156 */     this.toMove.clear();
/* 157 */     this.toMove.addAll(list);
/* 158 */     this.toMove.addAll(list1);
/* 159 */     this.toMove.addAll(list2); } private boolean func_177250_b(BlockPos p_177250_1_) {
/*     */     byte b;
/*     */     int i;
/*     */     EnumFacing[] arrayOfEnumFacing;
/* 163 */     for (i = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/* 164 */       if (enumfacing.getAxis() != this.moveDirection.getAxis() && !func_177251_a(p_177250_1_.offset(enumfacing))) {
/* 165 */         return false;
/*     */       }
/*     */       b++; }
/*     */     
/* 169 */     return true;
/*     */   }
/*     */   
/*     */   public List<BlockPos> getBlocksToMove() {
/* 173 */     return this.toMove;
/*     */   }
/*     */   
/*     */   public List<BlockPos> getBlocksToDestroy() {
/* 177 */     return this.toDestroy;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\state\BlockPistonStructureHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */