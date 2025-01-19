/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Queue;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.util.Tuple;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockSponge
/*     */   extends Block {
/*  27 */   public static final PropertyBool WET = PropertyBool.create("wet");
/*     */   
/*     */   protected BlockSponge() {
/*  30 */     super(Material.sponge);
/*  31 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)WET, Boolean.valueOf(false)));
/*  32 */     setCreativeTab(CreativeTabs.tabBlock);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalizedName() {
/*  39 */     return StatCollector.translateToLocal(String.valueOf(getUnlocalizedName()) + ".dry.name");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int damageDropped(IBlockState state) {
/*  47 */     return ((Boolean)state.getValue((IProperty)WET)).booleanValue() ? 1 : 0;
/*     */   }
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/*  51 */     tryAbsorb(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/*  58 */     tryAbsorb(worldIn, pos, state);
/*  59 */     super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
/*     */   }
/*     */   
/*     */   protected void tryAbsorb(World worldIn, BlockPos pos, IBlockState state) {
/*  63 */     if (!((Boolean)state.getValue((IProperty)WET)).booleanValue() && absorb(worldIn, pos)) {
/*  64 */       worldIn.setBlockState(pos, state.withProperty((IProperty)WET, Boolean.valueOf(true)), 2);
/*  65 */       worldIn.playAuxSFX(2001, pos, Block.getIdFromBlock(Blocks.water));
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean absorb(World worldIn, BlockPos pos) {
/*  70 */     Queue<Tuple<BlockPos, Integer>> queue = Lists.newLinkedList();
/*  71 */     ArrayList<BlockPos> arraylist = Lists.newArrayList();
/*  72 */     queue.add(new Tuple(pos, Integer.valueOf(0)));
/*  73 */     int i = 0;
/*     */     
/*  75 */     while (!queue.isEmpty()) {
/*  76 */       Tuple<BlockPos, Integer> tuple = queue.poll();
/*  77 */       BlockPos blockpos = (BlockPos)tuple.getFirst();
/*  78 */       int j = ((Integer)tuple.getSecond()).intValue(); byte b; int k;
/*     */       EnumFacing[] arrayOfEnumFacing;
/*  80 */       for (k = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < k; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*  81 */         BlockPos blockpos1 = blockpos.offset(enumfacing);
/*     */         
/*  83 */         if (worldIn.getBlockState(blockpos1).getBlock().getMaterial() == Material.water) {
/*  84 */           worldIn.setBlockState(blockpos1, Blocks.air.getDefaultState(), 2);
/*  85 */           arraylist.add(blockpos1);
/*  86 */           i++;
/*     */           
/*  88 */           if (j < 6) {
/*  89 */             queue.add(new Tuple(blockpos1, Integer.valueOf(j + 1)));
/*     */           }
/*     */         } 
/*     */         b++; }
/*     */       
/*  94 */       if (i > 64) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/*  99 */     for (BlockPos blockpos2 : arraylist) {
/* 100 */       worldIn.notifyNeighborsOfStateChange(blockpos2, Blocks.air);
/*     */     }
/*     */     
/* 103 */     return (i > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
/* 110 */     list.add(new ItemStack(itemIn, 1, 0));
/* 111 */     list.add(new ItemStack(itemIn, 1, 1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 118 */     return getDefaultState().withProperty((IProperty)WET, Boolean.valueOf(((meta & 0x1) == 1)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 125 */     return ((Boolean)state.getValue((IProperty)WET)).booleanValue() ? 1 : 0;
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 129 */     return new BlockState(this, new IProperty[] { (IProperty)WET });
/*     */   }
/*     */   
/*     */   public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 133 */     if (((Boolean)state.getValue((IProperty)WET)).booleanValue()) {
/* 134 */       EnumFacing enumfacing = EnumFacing.random(rand);
/*     */       
/* 136 */       if (enumfacing != EnumFacing.UP && !World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.offset(enumfacing))) {
/* 137 */         double d0 = pos.getX();
/* 138 */         double d1 = pos.getY();
/* 139 */         double d2 = pos.getZ();
/*     */         
/* 141 */         if (enumfacing == EnumFacing.DOWN) {
/* 142 */           d1 -= 0.05D;
/* 143 */           d0 += rand.nextDouble();
/* 144 */           d2 += rand.nextDouble();
/*     */         } else {
/* 146 */           d1 += rand.nextDouble() * 0.8D;
/*     */           
/* 148 */           if (enumfacing.getAxis() == EnumFacing.Axis.X) {
/* 149 */             d2 += rand.nextDouble();
/*     */             
/* 151 */             if (enumfacing == EnumFacing.EAST) {
/* 152 */               d0++;
/*     */             } else {
/* 154 */               d0 += 0.05D;
/*     */             } 
/*     */           } else {
/* 157 */             d0 += rand.nextDouble();
/*     */             
/* 159 */             if (enumfacing == EnumFacing.SOUTH) {
/* 160 */               d2++;
/*     */             } else {
/* 162 */               d2 += 0.05D;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 167 */         worldIn.spawnParticle(EnumParticleTypes.DRIP_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockSponge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */