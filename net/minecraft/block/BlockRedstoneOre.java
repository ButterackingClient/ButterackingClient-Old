/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockRedstoneOre
/*     */   extends Block {
/*     */   private final boolean isOn;
/*     */   
/*     */   public BlockRedstoneOre(boolean isOn) {
/*  22 */     super(Material.rock);
/*     */     
/*  24 */     if (isOn) {
/*  25 */       setTickRandomly(true);
/*     */     }
/*     */     
/*  28 */     this.isOn = isOn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int tickRate(World worldIn) {
/*  35 */     return 30;
/*     */   }
/*     */   
/*     */   public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
/*  39 */     activate(worldIn, pos);
/*  40 */     super.onBlockClicked(worldIn, pos, playerIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, Entity entityIn) {
/*  47 */     activate(worldIn, pos);
/*  48 */     super.onEntityCollidedWithBlock(worldIn, pos, entityIn);
/*     */   }
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  52 */     activate(worldIn, pos);
/*  53 */     return super.onBlockActivated(worldIn, pos, state, playerIn, side, hitX, hitY, hitZ);
/*     */   }
/*     */   
/*     */   private void activate(World worldIn, BlockPos pos) {
/*  57 */     spawnParticles(worldIn, pos);
/*     */     
/*  59 */     if (this == Blocks.redstone_ore) {
/*  60 */       worldIn.setBlockState(pos, Blocks.lit_redstone_ore.getDefaultState());
/*     */     }
/*     */   }
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  65 */     if (this == Blocks.lit_redstone_ore) {
/*  66 */       worldIn.setBlockState(pos, Blocks.redstone_ore.getDefaultState());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  74 */     return Items.redstone;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDroppedWithBonus(int fortune, Random random) {
/*  81 */     return quantityDropped(random) + random.nextInt(fortune + 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/*  88 */     return 4 + random.nextInt(2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
/*  95 */     super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
/*     */     
/*  97 */     if (getItemDropped(state, worldIn.rand, fortune) != Item.getItemFromBlock(this)) {
/*  98 */       int i = 1 + worldIn.rand.nextInt(5);
/*  99 */       dropXpOnBlockBreak(worldIn, pos, i);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 104 */     if (this.isOn) {
/* 105 */       spawnParticles(worldIn, pos);
/*     */     }
/*     */   }
/*     */   
/*     */   private void spawnParticles(World worldIn, BlockPos pos) {
/* 110 */     Random random = worldIn.rand;
/* 111 */     double d0 = 0.0625D;
/*     */     
/* 113 */     for (int i = 0; i < 6; i++) {
/* 114 */       double d1 = (pos.getX() + random.nextFloat());
/* 115 */       double d2 = (pos.getY() + random.nextFloat());
/* 116 */       double d3 = (pos.getZ() + random.nextFloat());
/*     */       
/* 118 */       if (i == 0 && !worldIn.getBlockState(pos.up()).getBlock().isOpaqueCube()) {
/* 119 */         d2 = pos.getY() + d0 + 1.0D;
/*     */       }
/*     */       
/* 122 */       if (i == 1 && !worldIn.getBlockState(pos.down()).getBlock().isOpaqueCube()) {
/* 123 */         d2 = pos.getY() - d0;
/*     */       }
/*     */       
/* 126 */       if (i == 2 && !worldIn.getBlockState(pos.south()).getBlock().isOpaqueCube()) {
/* 127 */         d3 = pos.getZ() + d0 + 1.0D;
/*     */       }
/*     */       
/* 130 */       if (i == 3 && !worldIn.getBlockState(pos.north()).getBlock().isOpaqueCube()) {
/* 131 */         d3 = pos.getZ() - d0;
/*     */       }
/*     */       
/* 134 */       if (i == 4 && !worldIn.getBlockState(pos.east()).getBlock().isOpaqueCube()) {
/* 135 */         d1 = pos.getX() + d0 + 1.0D;
/*     */       }
/*     */       
/* 138 */       if (i == 5 && !worldIn.getBlockState(pos.west()).getBlock().isOpaqueCube()) {
/* 139 */         d1 = pos.getX() - d0;
/*     */       }
/*     */       
/* 142 */       if (d1 < pos.getX() || d1 > (pos.getX() + 1) || d2 < 0.0D || d2 > (pos.getY() + 1) || d3 < pos.getZ() || d3 > (pos.getZ() + 1)) {
/* 143 */         worldIn.spawnParticle(EnumParticleTypes.REDSTONE, d1, d2, d3, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected ItemStack createStackedBlock(IBlockState state) {
/* 149 */     return new ItemStack(Blocks.redstone_ore);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockRedstoneOre.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */