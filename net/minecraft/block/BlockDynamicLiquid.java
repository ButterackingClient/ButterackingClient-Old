/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.EnumSet;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockDynamicLiquid
/*     */   extends BlockLiquid {
/*     */   protected BlockDynamicLiquid(Material materialIn) {
/*  18 */     super(materialIn);
/*     */   }
/*     */   int adjacentSourceBlocks;
/*     */   private void placeStaticBlock(World worldIn, BlockPos pos, IBlockState currentState) {
/*  22 */     worldIn.setBlockState(pos, getStaticBlock(this.blockMaterial).getDefaultState().withProperty((IProperty)LEVEL, currentState.getValue((IProperty)LEVEL)), 2);
/*     */   }
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  26 */     int i = ((Integer)state.getValue((IProperty)LEVEL)).intValue();
/*  27 */     int j = 1;
/*     */     
/*  29 */     if (this.blockMaterial == Material.lava && !worldIn.provider.doesWaterVaporize()) {
/*  30 */       j = 2;
/*     */     }
/*     */     
/*  33 */     int k = tickRate(worldIn);
/*     */     
/*  35 */     if (i > 0) {
/*  36 */       int l = -100;
/*  37 */       this.adjacentSourceBlocks = 0;
/*     */       
/*  39 */       for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*  40 */         l = checkAdjacentBlock(worldIn, pos.offset(enumfacing), l);
/*     */       }
/*     */       
/*  43 */       int i1 = l + j;
/*     */       
/*  45 */       if (i1 >= 8 || l < 0) {
/*  46 */         i1 = -1;
/*     */       }
/*     */       
/*  49 */       if (getLevel((IBlockAccess)worldIn, pos.up()) >= 0) {
/*  50 */         int j1 = getLevel((IBlockAccess)worldIn, pos.up());
/*     */         
/*  52 */         if (j1 >= 8) {
/*  53 */           i1 = j1;
/*     */         } else {
/*  55 */           i1 = j1 + 8;
/*     */         } 
/*     */       } 
/*     */       
/*  59 */       if (this.adjacentSourceBlocks >= 2 && this.blockMaterial == Material.water) {
/*  60 */         IBlockState iblockstate1 = worldIn.getBlockState(pos.down());
/*     */         
/*  62 */         if (iblockstate1.getBlock().getMaterial().isSolid()) {
/*  63 */           i1 = 0;
/*  64 */         } else if (iblockstate1.getBlock().getMaterial() == this.blockMaterial && ((Integer)iblockstate1.getValue((IProperty)LEVEL)).intValue() == 0) {
/*  65 */           i1 = 0;
/*     */         } 
/*     */       } 
/*     */       
/*  69 */       if (this.blockMaterial == Material.lava && i < 8 && i1 < 8 && i1 > i && rand.nextInt(4) != 0) {
/*  70 */         k *= 4;
/*     */       }
/*     */       
/*  73 */       if (i1 == i) {
/*  74 */         placeStaticBlock(worldIn, pos, state);
/*     */       } else {
/*  76 */         i = i1;
/*     */         
/*  78 */         if (i1 < 0) {
/*  79 */           worldIn.setBlockToAir(pos);
/*     */         } else {
/*  81 */           state = state.withProperty((IProperty)LEVEL, Integer.valueOf(i1));
/*  82 */           worldIn.setBlockState(pos, state, 2);
/*  83 */           worldIn.scheduleUpdate(pos, this, k);
/*  84 */           worldIn.notifyNeighborsOfStateChange(pos, this);
/*     */         } 
/*     */       } 
/*     */     } else {
/*  88 */       placeStaticBlock(worldIn, pos, state);
/*     */     } 
/*     */     
/*  91 */     IBlockState iblockstate = worldIn.getBlockState(pos.down());
/*     */     
/*  93 */     if (canFlowInto(worldIn, pos.down(), iblockstate)) {
/*  94 */       if (this.blockMaterial == Material.lava && worldIn.getBlockState(pos.down()).getBlock().getMaterial() == Material.water) {
/*  95 */         worldIn.setBlockState(pos.down(), Blocks.stone.getDefaultState());
/*  96 */         triggerMixEffects(worldIn, pos.down());
/*     */         
/*     */         return;
/*     */       } 
/* 100 */       if (i >= 8) {
/* 101 */         tryFlowInto(worldIn, pos.down(), iblockstate, i);
/*     */       } else {
/* 103 */         tryFlowInto(worldIn, pos.down(), iblockstate, i + 8);
/*     */       } 
/* 105 */     } else if (i >= 0 && (i == 0 || isBlocked(worldIn, pos.down(), iblockstate))) {
/* 106 */       Set<EnumFacing> set = getPossibleFlowDirections(worldIn, pos);
/* 107 */       int k1 = i + j;
/*     */       
/* 109 */       if (i >= 8) {
/* 110 */         k1 = 1;
/*     */       }
/*     */       
/* 113 */       if (k1 >= 8) {
/*     */         return;
/*     */       }
/*     */       
/* 117 */       for (EnumFacing enumfacing1 : set) {
/* 118 */         tryFlowInto(worldIn, pos.offset(enumfacing1), worldIn.getBlockState(pos.offset(enumfacing1)), k1);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void tryFlowInto(World worldIn, BlockPos pos, IBlockState state, int level) {
/* 124 */     if (canFlowInto(worldIn, pos, state)) {
/* 125 */       if (state.getBlock() != Blocks.air) {
/* 126 */         if (this.blockMaterial == Material.lava) {
/* 127 */           triggerMixEffects(worldIn, pos);
/*     */         } else {
/* 129 */           state.getBlock().dropBlockAsItem(worldIn, pos, state, 0);
/*     */         } 
/*     */       }
/*     */       
/* 133 */       worldIn.setBlockState(pos, getDefaultState().withProperty((IProperty)LEVEL, Integer.valueOf(level)), 3);
/*     */     } 
/*     */   }
/*     */   
/*     */   private int func_176374_a(World worldIn, BlockPos pos, int distance, EnumFacing calculateFlowCost) {
/* 138 */     int i = 1000;
/*     */     
/* 140 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/* 141 */       if (enumfacing != calculateFlowCost) {
/* 142 */         BlockPos blockpos = pos.offset(enumfacing);
/* 143 */         IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*     */         
/* 145 */         if (!isBlocked(worldIn, blockpos, iblockstate) && (iblockstate.getBlock().getMaterial() != this.blockMaterial || ((Integer)iblockstate.getValue((IProperty)LEVEL)).intValue() > 0)) {
/* 146 */           if (!isBlocked(worldIn, blockpos.down(), iblockstate)) {
/* 147 */             return distance;
/*     */           }
/*     */           
/* 150 */           if (distance < 4) {
/* 151 */             int j = func_176374_a(worldIn, blockpos, distance + 1, enumfacing.getOpposite());
/*     */             
/* 153 */             if (j < i) {
/* 154 */               i = j;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 161 */     return i;
/*     */   }
/*     */   
/*     */   private Set<EnumFacing> getPossibleFlowDirections(World worldIn, BlockPos pos) {
/* 165 */     int i = 1000;
/* 166 */     Set<EnumFacing> set = EnumSet.noneOf(EnumFacing.class);
/*     */     
/* 168 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/* 169 */       BlockPos blockpos = pos.offset(enumfacing);
/* 170 */       IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*     */       
/* 172 */       if (!isBlocked(worldIn, blockpos, iblockstate) && (iblockstate.getBlock().getMaterial() != this.blockMaterial || ((Integer)iblockstate.getValue((IProperty)LEVEL)).intValue() > 0)) {
/*     */         int j;
/*     */         
/* 175 */         if (isBlocked(worldIn, blockpos.down(), worldIn.getBlockState(blockpos.down()))) {
/* 176 */           j = func_176374_a(worldIn, blockpos, 1, enumfacing.getOpposite());
/*     */         } else {
/* 178 */           j = 0;
/*     */         } 
/*     */         
/* 181 */         if (j < i) {
/* 182 */           set.clear();
/*     */         }
/*     */         
/* 185 */         if (j <= i) {
/* 186 */           set.add(enumfacing);
/* 187 */           i = j;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 192 */     return set;
/*     */   }
/*     */   
/*     */   private boolean isBlocked(World worldIn, BlockPos pos, IBlockState state) {
/* 196 */     Block block = worldIn.getBlockState(pos).getBlock();
/* 197 */     return (!(block instanceof BlockDoor) && block != Blocks.standing_sign && block != Blocks.ladder && block != Blocks.reeds) ? ((block.blockMaterial == Material.portal) ? true : block.blockMaterial.blocksMovement()) : true;
/*     */   }
/*     */   
/*     */   protected int checkAdjacentBlock(World worldIn, BlockPos pos, int currentMinLevel) {
/* 201 */     int i = getLevel((IBlockAccess)worldIn, pos);
/*     */     
/* 203 */     if (i < 0) {
/* 204 */       return currentMinLevel;
/*     */     }
/* 206 */     if (i == 0) {
/* 207 */       this.adjacentSourceBlocks++;
/*     */     }
/*     */     
/* 210 */     if (i >= 8) {
/* 211 */       i = 0;
/*     */     }
/*     */     
/* 214 */     return (currentMinLevel >= 0 && i >= currentMinLevel) ? currentMinLevel : i;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean canFlowInto(World worldIn, BlockPos pos, IBlockState state) {
/* 219 */     Material material = state.getBlock().getMaterial();
/* 220 */     return (material != this.blockMaterial && material != Material.lava && !isBlocked(worldIn, pos, state));
/*     */   }
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 224 */     if (!checkForMixing(worldIn, pos, state))
/* 225 */       worldIn.scheduleUpdate(pos, this, tickRate(worldIn)); 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockDynamicLiquid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */