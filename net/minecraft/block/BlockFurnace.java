/*     */ package net.minecraft.block;
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.InventoryHelper;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityFurnace;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockFurnace extends BlockContainer {
/*  26 */   public static final PropertyDirection FACING = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
/*     */   private final boolean isBurning;
/*     */   private static boolean keepInventory;
/*     */   
/*     */   protected BlockFurnace(boolean isBurning) {
/*  31 */     super(Material.rock);
/*  32 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH));
/*  33 */     this.isBurning = isBurning;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  40 */     return Item.getItemFromBlock(Blocks.furnace);
/*     */   }
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/*  44 */     setDefaultFacing(worldIn, pos, state);
/*     */   }
/*     */   
/*     */   private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state) {
/*  48 */     if (!worldIn.isRemote) {
/*  49 */       Block block = worldIn.getBlockState(pos.north()).getBlock();
/*  50 */       Block block1 = worldIn.getBlockState(pos.south()).getBlock();
/*  51 */       Block block2 = worldIn.getBlockState(pos.west()).getBlock();
/*  52 */       Block block3 = worldIn.getBlockState(pos.east()).getBlock();
/*  53 */       EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/*     */       
/*  55 */       if (enumfacing == EnumFacing.NORTH && block.isFullBlock() && !block1.isFullBlock()) {
/*  56 */         enumfacing = EnumFacing.SOUTH;
/*  57 */       } else if (enumfacing == EnumFacing.SOUTH && block1.isFullBlock() && !block.isFullBlock()) {
/*  58 */         enumfacing = EnumFacing.NORTH;
/*  59 */       } else if (enumfacing == EnumFacing.WEST && block2.isFullBlock() && !block3.isFullBlock()) {
/*  60 */         enumfacing = EnumFacing.EAST;
/*  61 */       } else if (enumfacing == EnumFacing.EAST && block3.isFullBlock() && !block2.isFullBlock()) {
/*  62 */         enumfacing = EnumFacing.WEST;
/*     */       } 
/*     */       
/*  65 */       worldIn.setBlockState(pos, state.withProperty((IProperty)FACING, (Comparable)enumfacing), 2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  71 */     if (this.isBurning) {
/*  72 */       EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/*  73 */       double d0 = pos.getX() + 0.5D;
/*  74 */       double d1 = pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
/*  75 */       double d2 = pos.getZ() + 0.5D;
/*  76 */       double d3 = 0.52D;
/*  77 */       double d4 = rand.nextDouble() * 0.6D - 0.3D;
/*     */       
/*  79 */       switch (enumfacing) {
/*     */         case WEST:
/*  81 */           worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
/*  82 */           worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 - d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */           break;
/*     */         
/*     */         case EAST:
/*  86 */           worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
/*  87 */           worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */           break;
/*     */         
/*     */         case NORTH:
/*  91 */           worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 - d3, 0.0D, 0.0D, 0.0D, new int[0]);
/*  92 */           worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 - d3, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */           break;
/*     */         
/*     */         case SOUTH:
/*  96 */           worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + d3, 0.0D, 0.0D, 0.0D, new int[0]);
/*  97 */           worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 + d3, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 103 */     if (worldIn.isRemote) {
/* 104 */       return true;
/*     */     }
/* 106 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 108 */     if (tileentity instanceof TileEntityFurnace) {
/* 109 */       playerIn.displayGUIChest((IInventory)tileentity);
/* 110 */       playerIn.triggerAchievement(StatList.field_181741_Y);
/*     */     } 
/*     */     
/* 113 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setState(boolean active, World worldIn, BlockPos pos) {
/* 118 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/* 119 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/* 120 */     keepInventory = true;
/*     */     
/* 122 */     if (active) {
/* 123 */       worldIn.setBlockState(pos, Blocks.lit_furnace.getDefaultState().withProperty((IProperty)FACING, iblockstate.getValue((IProperty)FACING)), 3);
/* 124 */       worldIn.setBlockState(pos, Blocks.lit_furnace.getDefaultState().withProperty((IProperty)FACING, iblockstate.getValue((IProperty)FACING)), 3);
/*     */     } else {
/* 126 */       worldIn.setBlockState(pos, Blocks.furnace.getDefaultState().withProperty((IProperty)FACING, iblockstate.getValue((IProperty)FACING)), 3);
/* 127 */       worldIn.setBlockState(pos, Blocks.furnace.getDefaultState().withProperty((IProperty)FACING, iblockstate.getValue((IProperty)FACING)), 3);
/*     */     } 
/*     */     
/* 130 */     keepInventory = false;
/*     */     
/* 132 */     if (tileentity != null) {
/* 133 */       tileentity.validate();
/* 134 */       worldIn.setTileEntity(pos, tileentity);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/* 142 */     return (TileEntity)new TileEntityFurnace();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 150 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)placer.getHorizontalFacing().getOpposite());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/* 157 */     worldIn.setBlockState(pos, state.withProperty((IProperty)FACING, (Comparable)placer.getHorizontalFacing().getOpposite()), 2);
/*     */     
/* 159 */     if (stack.hasDisplayName()) {
/* 160 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/* 162 */       if (tileentity instanceof TileEntityFurnace) {
/* 163 */         ((TileEntityFurnace)tileentity).setCustomInventoryName(stack.getDisplayName());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 169 */     if (!keepInventory) {
/* 170 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/* 172 */       if (tileentity instanceof TileEntityFurnace) {
/* 173 */         InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileentity);
/* 174 */         worldIn.updateComparatorOutputLevel(pos, this);
/*     */       } 
/*     */     } 
/*     */     
/* 178 */     super.breakBlock(worldIn, pos, state);
/*     */   }
/*     */   
/*     */   public boolean hasComparatorInputOverride() {
/* 182 */     return true;
/*     */   }
/*     */   
/*     */   public int getComparatorInputOverride(World worldIn, BlockPos pos) {
/* 186 */     return Container.calcRedstone(worldIn.getTileEntity(pos));
/*     */   }
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 190 */     return Item.getItemFromBlock(Blocks.furnace);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRenderType() {
/* 197 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateForEntityRender(IBlockState state) {
/* 204 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)EnumFacing.SOUTH);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 211 */     EnumFacing enumfacing = EnumFacing.getFront(meta);
/*     */     
/* 213 */     if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
/* 214 */       enumfacing = EnumFacing.NORTH;
/*     */     }
/*     */     
/* 217 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)enumfacing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 224 */     return ((EnumFacing)state.getValue((IProperty)FACING)).getIndex();
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 228 */     return new BlockState(this, new IProperty[] { (IProperty)FACING });
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockFurnace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */