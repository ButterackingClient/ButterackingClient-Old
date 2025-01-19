/*     */ package net.minecraft.world.gen.feature;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockHugeMushroom;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class WorldGenBigMushroom
/*     */   extends WorldGenerator
/*     */ {
/*     */   private Block mushroomType;
/*     */   
/*     */   public WorldGenBigMushroom(Block p_i46449_1_) {
/*  19 */     super(true);
/*  20 */     this.mushroomType = p_i46449_1_;
/*     */   }
/*     */   
/*     */   public WorldGenBigMushroom() {
/*  24 */     super(false);
/*     */   }
/*     */   
/*     */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/*  28 */     if (this.mushroomType == null) {
/*  29 */       this.mushroomType = rand.nextBoolean() ? Blocks.brown_mushroom_block : Blocks.red_mushroom_block;
/*     */     }
/*     */     
/*  32 */     int i = rand.nextInt(3) + 4;
/*  33 */     boolean flag = true;
/*     */     
/*  35 */     if (position.getY() >= 1 && position.getY() + i + 1 < 256) {
/*  36 */       for (int j = position.getY(); j <= position.getY() + 1 + i; j++) {
/*  37 */         int k = 3;
/*     */         
/*  39 */         if (j <= position.getY() + 3) {
/*  40 */           k = 0;
/*     */         }
/*     */         
/*  43 */         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */         
/*  45 */         for (int l = position.getX() - k; l <= position.getX() + k && flag; l++) {
/*  46 */           for (int i1 = position.getZ() - k; i1 <= position.getZ() + k && flag; i1++) {
/*  47 */             if (j >= 0 && j < 256) {
/*  48 */               Block block = worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.set(l, j, i1)).getBlock();
/*     */               
/*  50 */               if (block.getMaterial() != Material.air && block.getMaterial() != Material.leaves) {
/*  51 */                 flag = false;
/*     */               }
/*     */             } else {
/*  54 */               flag = false;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  60 */       if (!flag) {
/*  61 */         return false;
/*     */       }
/*  63 */       Block block1 = worldIn.getBlockState(position.down()).getBlock();
/*     */       
/*  65 */       if (block1 != Blocks.dirt && block1 != Blocks.grass && block1 != Blocks.mycelium) {
/*  66 */         return false;
/*     */       }
/*  68 */       int k2 = position.getY() + i;
/*     */       
/*  70 */       if (this.mushroomType == Blocks.red_mushroom_block) {
/*  71 */         k2 = position.getY() + i - 3;
/*     */       }
/*     */       
/*  74 */       for (int l2 = k2; l2 <= position.getY() + i; l2++) {
/*  75 */         int j3 = 1;
/*     */         
/*  77 */         if (l2 < position.getY() + i) {
/*  78 */           j3++;
/*     */         }
/*     */         
/*  81 */         if (this.mushroomType == Blocks.brown_mushroom_block) {
/*  82 */           j3 = 3;
/*     */         }
/*     */         
/*  85 */         int k3 = position.getX() - j3;
/*  86 */         int l3 = position.getX() + j3;
/*  87 */         int j1 = position.getZ() - j3;
/*  88 */         int k1 = position.getZ() + j3;
/*     */         
/*  90 */         for (int l1 = k3; l1 <= l3; l1++) {
/*  91 */           for (int i2 = j1; i2 <= k1; i2++) {
/*  92 */             int j2 = 5;
/*     */             
/*  94 */             if (l1 == k3) {
/*  95 */               j2--;
/*  96 */             } else if (l1 == l3) {
/*  97 */               j2++;
/*     */             } 
/*     */             
/* 100 */             if (i2 == j1) {
/* 101 */               j2 -= 3;
/* 102 */             } else if (i2 == k1) {
/* 103 */               j2 += 3;
/*     */             } 
/*     */             
/* 106 */             BlockHugeMushroom.EnumType blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.byMetadata(j2);
/*     */             
/* 108 */             if (this.mushroomType == Blocks.brown_mushroom_block || l2 < position.getY() + i) {
/* 109 */               if ((l1 == k3 || l1 == l3) && (i2 == j1 || i2 == k1)) {
/*     */                 continue;
/*     */               }
/*     */               
/* 113 */               if (l1 == position.getX() - j3 - 1 && i2 == j1) {
/* 114 */                 blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.NORTH_WEST;
/*     */               }
/*     */               
/* 117 */               if (l1 == k3 && i2 == position.getZ() - j3 - 1) {
/* 118 */                 blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.NORTH_WEST;
/*     */               }
/*     */               
/* 121 */               if (l1 == position.getX() + j3 - 1 && i2 == j1) {
/* 122 */                 blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.NORTH_EAST;
/*     */               }
/*     */               
/* 125 */               if (l1 == l3 && i2 == position.getZ() - j3 - 1) {
/* 126 */                 blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.NORTH_EAST;
/*     */               }
/*     */               
/* 129 */               if (l1 == position.getX() - j3 - 1 && i2 == k1) {
/* 130 */                 blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.SOUTH_WEST;
/*     */               }
/*     */               
/* 133 */               if (l1 == k3 && i2 == position.getZ() + j3 - 1) {
/* 134 */                 blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.SOUTH_WEST;
/*     */               }
/*     */               
/* 137 */               if (l1 == position.getX() + j3 - 1 && i2 == k1) {
/* 138 */                 blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.SOUTH_EAST;
/*     */               }
/*     */               
/* 141 */               if (l1 == l3 && i2 == position.getZ() + j3 - 1) {
/* 142 */                 blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.SOUTH_EAST;
/*     */               }
/*     */             } 
/*     */             
/* 146 */             if (blockhugemushroom$enumtype == BlockHugeMushroom.EnumType.CENTER && l2 < position.getY() + i) {
/* 147 */               blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.ALL_INSIDE;
/*     */             }
/*     */             
/* 150 */             if (position.getY() >= position.getY() + i - 1 || blockhugemushroom$enumtype != BlockHugeMushroom.EnumType.ALL_INSIDE) {
/* 151 */               BlockPos blockpos = new BlockPos(l1, l2, i2);
/*     */               
/* 153 */               if (!worldIn.getBlockState(blockpos).getBlock().isFullBlock()) {
/* 154 */                 setBlockAndNotifyAdequately(worldIn, blockpos, this.mushroomType.getDefaultState().withProperty((IProperty)BlockHugeMushroom.VARIANT, (Comparable)blockhugemushroom$enumtype));
/*     */               }
/*     */             } 
/*     */             continue;
/*     */           } 
/*     */         } 
/*     */       } 
/* 161 */       for (int i3 = 0; i3 < i; i3++) {
/* 162 */         Block block2 = worldIn.getBlockState(position.up(i3)).getBlock();
/*     */         
/* 164 */         if (!block2.isFullBlock()) {
/* 165 */           setBlockAndNotifyAdequately(worldIn, position.up(i3), this.mushroomType.getDefaultState().withProperty((IProperty)BlockHugeMushroom.VARIANT, (Comparable)BlockHugeMushroom.EnumType.STEM));
/*     */         }
/*     */       } 
/*     */       
/* 169 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 173 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\feature\WorldGenBigMushroom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */