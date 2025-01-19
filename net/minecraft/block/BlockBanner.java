/*     */ package net.minecraft.block;
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityBanner;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockBanner extends BlockContainer {
/*  26 */   public static final PropertyDirection FACING = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
/*  27 */   public static final PropertyInteger ROTATION = PropertyInteger.create("rotation", 0, 15);
/*     */   
/*     */   protected BlockBanner() {
/*  30 */     super(Material.wood);
/*  31 */     float f = 0.25F;
/*  32 */     float f1 = 1.0F;
/*  33 */     setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalizedName() {
/*  40 */     return StatCollector.translateToLocal("item.banner.white.name");
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  44 */     return null;
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos) {
/*  48 */     setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/*  49 */     return super.getSelectedBoundingBox(worldIn, pos);
/*     */   }
/*     */   
/*     */   public boolean isFullCube() {
/*  53 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
/*  57 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  64 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canSpawnInBlock() {
/*  71 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/*  78 */     return (TileEntity)new TileEntityBanner();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  85 */     return Items.banner;
/*     */   }
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/*  89 */     return Items.banner;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
/*  96 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/*  98 */     if (tileentity instanceof TileEntityBanner) {
/*  99 */       ItemStack itemstack = new ItemStack(Items.banner, 1, ((TileEntityBanner)tileentity).getBaseColor());
/* 100 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 101 */       tileentity.writeToNBT(nbttagcompound);
/* 102 */       nbttagcompound.removeTag("x");
/* 103 */       nbttagcompound.removeTag("y");
/* 104 */       nbttagcompound.removeTag("z");
/* 105 */       nbttagcompound.removeTag("id");
/* 106 */       itemstack.setTagInfo("BlockEntityTag", (NBTBase)nbttagcompound);
/* 107 */       spawnAsEntity(worldIn, pos, itemstack);
/*     */     } else {
/* 109 */       super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/* 114 */     return (!hasInvalidNeighbor(worldIn, pos) && super.canPlaceBlockAt(worldIn, pos));
/*     */   }
/*     */   
/*     */   public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te) {
/* 118 */     if (te instanceof TileEntityBanner) {
/* 119 */       TileEntityBanner tileentitybanner = (TileEntityBanner)te;
/* 120 */       ItemStack itemstack = new ItemStack(Items.banner, 1, ((TileEntityBanner)te).getBaseColor());
/* 121 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 122 */       TileEntityBanner.setBaseColorAndPatterns(nbttagcompound, tileentitybanner.getBaseColor(), tileentitybanner.getPatterns());
/* 123 */       itemstack.setTagInfo("BlockEntityTag", (NBTBase)nbttagcompound);
/* 124 */       spawnAsEntity(worldIn, pos, itemstack);
/*     */     } else {
/* 126 */       super.harvestBlock(worldIn, player, pos, state, null);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static class BlockBannerHanging extends BlockBanner {
/*     */     public BlockBannerHanging() {
/* 132 */       setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH));
/*     */     }
/*     */     
/*     */     public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/* 136 */       EnumFacing enumfacing = (EnumFacing)worldIn.getBlockState(pos).getValue((IProperty)FACING);
/* 137 */       float f = 0.0F;
/* 138 */       float f1 = 0.78125F;
/* 139 */       float f2 = 0.0F;
/* 140 */       float f3 = 1.0F;
/* 141 */       float f4 = 0.125F;
/* 142 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */       
/* 144 */       switch (enumfacing) {
/*     */         
/*     */         default:
/* 147 */           setBlockBounds(f2, f, 1.0F - f4, f3, f1, 1.0F);
/*     */           return;
/*     */         
/*     */         case SOUTH:
/* 151 */           setBlockBounds(f2, f, 0.0F, f3, f1, f4);
/*     */           return;
/*     */         
/*     */         case WEST:
/* 155 */           setBlockBounds(1.0F - f4, f, f2, 1.0F, f1, f3); return;
/*     */         case EAST:
/*     */           break;
/*     */       } 
/* 159 */       setBlockBounds(0.0F, f, f2, f4, f1, f3);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 164 */       EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/*     */       
/* 166 */       if (!worldIn.getBlockState(pos.offset(enumfacing.getOpposite())).getBlock().getMaterial().isSolid()) {
/* 167 */         dropBlockAsItem(worldIn, pos, state, 0);
/* 168 */         worldIn.setBlockToAir(pos);
/*     */       } 
/*     */       
/* 171 */       super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
/*     */     }
/*     */     
/*     */     public IBlockState getStateFromMeta(int meta) {
/* 175 */       EnumFacing enumfacing = EnumFacing.getFront(meta);
/*     */       
/* 177 */       if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
/* 178 */         enumfacing = EnumFacing.NORTH;
/*     */       }
/*     */       
/* 181 */       return getDefaultState().withProperty((IProperty)FACING, (Comparable)enumfacing);
/*     */     }
/*     */     
/*     */     public int getMetaFromState(IBlockState state) {
/* 185 */       return ((EnumFacing)state.getValue((IProperty)FACING)).getIndex();
/*     */     }
/*     */     
/*     */     protected BlockState createBlockState() {
/* 189 */       return new BlockState(this, new IProperty[] { (IProperty)FACING });
/*     */     }
/*     */   }
/*     */   
/*     */   public static class BlockBannerStanding extends BlockBanner {
/*     */     public BlockBannerStanding() {
/* 195 */       setDefaultState(this.blockState.getBaseState().withProperty((IProperty)ROTATION, Integer.valueOf(0)));
/*     */     }
/*     */     
/*     */     public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 199 */       if (!worldIn.getBlockState(pos.down()).getBlock().getMaterial().isSolid()) {
/* 200 */         dropBlockAsItem(worldIn, pos, state, 0);
/* 201 */         worldIn.setBlockToAir(pos);
/*     */       } 
/*     */       
/* 204 */       super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
/*     */     }
/*     */     
/*     */     public IBlockState getStateFromMeta(int meta) {
/* 208 */       return getDefaultState().withProperty((IProperty)ROTATION, Integer.valueOf(meta));
/*     */     }
/*     */     
/*     */     public int getMetaFromState(IBlockState state) {
/* 212 */       return ((Integer)state.getValue((IProperty)ROTATION)).intValue();
/*     */     }
/*     */     
/*     */     protected BlockState createBlockState() {
/* 216 */       return new BlockState(this, new IProperty[] { (IProperty)ROTATION });
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockBanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */