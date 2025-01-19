/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockRedstoneTorch
/*     */   extends BlockTorch
/*     */ {
/*  21 */   private static Map<World, List<Toggle>> toggles = Maps.newHashMap();
/*     */   private final boolean isOn;
/*     */   
/*     */   private boolean isBurnedOut(World worldIn, BlockPos pos, boolean turnOff) {
/*  25 */     if (!toggles.containsKey(worldIn)) {
/*  26 */       toggles.put(worldIn, Lists.newArrayList());
/*     */     }
/*     */     
/*  29 */     List<Toggle> list = toggles.get(worldIn);
/*     */     
/*  31 */     if (turnOff) {
/*  32 */       list.add(new Toggle(pos, worldIn.getTotalWorldTime()));
/*     */     }
/*     */     
/*  35 */     int i = 0;
/*     */     
/*  37 */     for (int j = 0; j < list.size(); j++) {
/*  38 */       Toggle blockredstonetorch$toggle = list.get(j);
/*     */ 
/*     */       
/*  41 */       i++;
/*     */       
/*  43 */       if (blockredstonetorch$toggle.pos.equals(pos) && i >= 8) {
/*  44 */         return true;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  49 */     return false;
/*     */   }
/*     */   
/*     */   protected BlockRedstoneTorch(boolean isOn) {
/*  53 */     this.isOn = isOn;
/*  54 */     setTickRandomly(true);
/*  55 */     setCreativeTab(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int tickRate(World worldIn) {
/*  62 */     return 2;
/*     */   }
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/*  66 */     if (this.isOn) {
/*  67 */       byte b; int i; EnumFacing[] arrayOfEnumFacing; for (i = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*  68 */         worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this);
/*     */         b++; }
/*     */     
/*     */     } 
/*     */   }
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/*  74 */     if (this.isOn) {
/*  75 */       byte b; int i; EnumFacing[] arrayOfEnumFacing; for (i = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*  76 */         worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this);
/*     */         b++; }
/*     */     
/*     */     } 
/*     */   }
/*     */   public int getWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/*  82 */     return (this.isOn && state.getValue((IProperty)FACING) != side) ? 15 : 0;
/*     */   }
/*     */   
/*     */   private boolean shouldBeOff(World worldIn, BlockPos pos, IBlockState state) {
/*  86 */     EnumFacing enumfacing = ((EnumFacing)state.getValue((IProperty)FACING)).getOpposite();
/*  87 */     return worldIn.isSidePowered(pos.offset(enumfacing), enumfacing);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  97 */     boolean flag = shouldBeOff(worldIn, pos, state);
/*  98 */     List<Toggle> list = toggles.get(worldIn);
/*     */     
/* 100 */     while (list != null && !list.isEmpty() && worldIn.getTotalWorldTime() - ((Toggle)list.get(0)).time > 60L) {
/* 101 */       list.remove(0);
/*     */     }
/*     */     
/* 104 */     if (this.isOn) {
/* 105 */       if (flag) {
/* 106 */         worldIn.setBlockState(pos, Blocks.unlit_redstone_torch.getDefaultState().withProperty((IProperty)FACING, state.getValue((IProperty)FACING)), 3);
/*     */         
/* 108 */         if (isBurnedOut(worldIn, pos, true)) {
/* 109 */           worldIn.playSoundEffect((pos.getX() + 0.5F), (pos.getY() + 0.5F), (pos.getZ() + 0.5F), "random.fizz", 0.5F, 2.6F + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.8F);
/*     */           
/* 111 */           for (int i = 0; i < 5; i++) {
/* 112 */             double d0 = pos.getX() + rand.nextDouble() * 0.6D + 0.2D;
/* 113 */             double d1 = pos.getY() + rand.nextDouble() * 0.6D + 0.2D;
/* 114 */             double d2 = pos.getZ() + rand.nextDouble() * 0.6D + 0.2D;
/* 115 */             worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */           } 
/*     */           
/* 118 */           worldIn.scheduleUpdate(pos, worldIn.getBlockState(pos).getBlock(), 160);
/*     */         } 
/*     */       } 
/* 121 */     } else if (!flag && !isBurnedOut(worldIn, pos, false)) {
/* 122 */       worldIn.setBlockState(pos, Blocks.redstone_torch.getDefaultState().withProperty((IProperty)FACING, state.getValue((IProperty)FACING)), 3);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 130 */     if (!onNeighborChangeInternal(worldIn, pos, state) && 
/* 131 */       this.isOn == shouldBeOff(worldIn, pos, state)) {
/* 132 */       worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getStrongPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/* 138 */     return (side == EnumFacing.DOWN) ? getWeakPower(worldIn, pos, state, side) : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 145 */     return Item.getItemFromBlock(Blocks.redstone_torch);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canProvidePower() {
/* 152 */     return true;
/*     */   }
/*     */   
/*     */   public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 156 */     if (this.isOn) {
/* 157 */       double d0 = pos.getX() + 0.5D + (rand.nextDouble() - 0.5D) * 0.2D;
/* 158 */       double d1 = pos.getY() + 0.7D + (rand.nextDouble() - 0.5D) * 0.2D;
/* 159 */       double d2 = pos.getZ() + 0.5D + (rand.nextDouble() - 0.5D) * 0.2D;
/* 160 */       EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/*     */       
/* 162 */       if (enumfacing.getAxis().isHorizontal()) {
/* 163 */         EnumFacing enumfacing1 = enumfacing.getOpposite();
/* 164 */         double d3 = 0.27D;
/* 165 */         d0 += 0.27D * enumfacing1.getFrontOffsetX();
/* 166 */         d1 += 0.22D;
/* 167 */         d2 += 0.27D * enumfacing1.getFrontOffsetZ();
/*     */       } 
/*     */       
/* 170 */       worldIn.spawnParticle(EnumParticleTypes.REDSTONE, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 175 */     return Item.getItemFromBlock(Blocks.redstone_torch);
/*     */   }
/*     */   
/*     */   public boolean isAssociatedBlock(Block other) {
/* 179 */     return !(other != Blocks.unlit_redstone_torch && other != Blocks.redstone_torch);
/*     */   }
/*     */   
/*     */   static class Toggle {
/*     */     BlockPos pos;
/*     */     long time;
/*     */     
/*     */     public Toggle(BlockPos pos, long time) {
/* 187 */       this.pos = pos;
/* 188 */       this.time = time;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockRedstoneTorch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */