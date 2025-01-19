/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
/*     */ import net.minecraft.dispenser.IBehaviorDispenseItem;
/*     */ import net.minecraft.dispenser.IBlockSource;
/*     */ import net.minecraft.dispenser.IPosition;
/*     */ import net.minecraft.dispenser.PositionImpl;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.InventoryHelper;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityDispenser;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.RegistryDefaulted;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockDispenser
/*     */   extends BlockContainer {
/*  33 */   public static final PropertyDirection FACING = PropertyDirection.create("facing");
/*  34 */   public static final PropertyBool TRIGGERED = PropertyBool.create("triggered");
/*  35 */   public static final RegistryDefaulted<Item, IBehaviorDispenseItem> dispenseBehaviorRegistry = new RegistryDefaulted(new BehaviorDefaultDispenseItem());
/*  36 */   protected Random rand = new Random();
/*     */   
/*     */   protected BlockDispenser() {
/*  39 */     super(Material.rock);
/*  40 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)TRIGGERED, Boolean.valueOf(false)));
/*  41 */     setCreativeTab(CreativeTabs.tabRedstone);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int tickRate(World worldIn) {
/*  48 */     return 4;
/*     */   }
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/*  52 */     super.onBlockAdded(worldIn, pos, state);
/*  53 */     setDefaultDirection(worldIn, pos, state);
/*     */   }
/*     */   
/*     */   private void setDefaultDirection(World worldIn, BlockPos pos, IBlockState state) {
/*  57 */     if (!worldIn.isRemote) {
/*  58 */       EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/*  59 */       boolean flag = worldIn.getBlockState(pos.north()).getBlock().isFullBlock();
/*  60 */       boolean flag1 = worldIn.getBlockState(pos.south()).getBlock().isFullBlock();
/*     */       
/*  62 */       if (enumfacing == EnumFacing.NORTH && flag && !flag1) {
/*  63 */         enumfacing = EnumFacing.SOUTH;
/*  64 */       } else if (enumfacing == EnumFacing.SOUTH && flag1 && !flag) {
/*  65 */         enumfacing = EnumFacing.NORTH;
/*     */       } else {
/*  67 */         boolean flag2 = worldIn.getBlockState(pos.west()).getBlock().isFullBlock();
/*  68 */         boolean flag3 = worldIn.getBlockState(pos.east()).getBlock().isFullBlock();
/*     */         
/*  70 */         if (enumfacing == EnumFacing.WEST && flag2 && !flag3) {
/*  71 */           enumfacing = EnumFacing.EAST;
/*  72 */         } else if (enumfacing == EnumFacing.EAST && flag3 && !flag2) {
/*  73 */           enumfacing = EnumFacing.WEST;
/*     */         } 
/*     */       } 
/*     */       
/*  77 */       worldIn.setBlockState(pos, state.withProperty((IProperty)FACING, (Comparable)enumfacing).withProperty((IProperty)TRIGGERED, Boolean.valueOf(false)), 2);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  82 */     if (worldIn.isRemote) {
/*  83 */       return true;
/*     */     }
/*  85 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/*  87 */     if (tileentity instanceof TileEntityDispenser) {
/*  88 */       playerIn.displayGUIChest((IInventory)tileentity);
/*     */       
/*  90 */       if (tileentity instanceof net.minecraft.tileentity.TileEntityDropper) {
/*  91 */         playerIn.triggerAchievement(StatList.field_181731_O);
/*     */       } else {
/*  93 */         playerIn.triggerAchievement(StatList.field_181733_Q);
/*     */       } 
/*     */     } 
/*     */     
/*  97 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void dispense(World worldIn, BlockPos pos) {
/* 102 */     BlockSourceImpl blocksourceimpl = new BlockSourceImpl(worldIn, pos);
/* 103 */     TileEntityDispenser tileentitydispenser = blocksourceimpl.<TileEntityDispenser>getBlockTileEntity();
/*     */     
/* 105 */     if (tileentitydispenser != null) {
/* 106 */       int i = tileentitydispenser.getDispenseSlot();
/*     */       
/* 108 */       if (i < 0) {
/* 109 */         worldIn.playAuxSFX(1001, pos, 0);
/*     */       } else {
/* 111 */         ItemStack itemstack = tileentitydispenser.getStackInSlot(i);
/* 112 */         IBehaviorDispenseItem ibehaviordispenseitem = getBehavior(itemstack);
/*     */         
/* 114 */         if (ibehaviordispenseitem != IBehaviorDispenseItem.itemDispenseBehaviorProvider) {
/* 115 */           ItemStack itemstack1 = ibehaviordispenseitem.dispense(blocksourceimpl, itemstack);
/* 116 */           tileentitydispenser.setInventorySlotContents(i, (itemstack1.stackSize <= 0) ? null : itemstack1);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected IBehaviorDispenseItem getBehavior(ItemStack stack) {
/* 123 */     return (IBehaviorDispenseItem)dispenseBehaviorRegistry.getObject((stack == null) ? null : stack.getItem());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 130 */     boolean flag = !(!worldIn.isBlockPowered(pos) && !worldIn.isBlockPowered(pos.up()));
/* 131 */     boolean flag1 = ((Boolean)state.getValue((IProperty)TRIGGERED)).booleanValue();
/*     */     
/* 133 */     if (flag && !flag1) {
/* 134 */       worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
/* 135 */       worldIn.setBlockState(pos, state.withProperty((IProperty)TRIGGERED, Boolean.valueOf(true)), 4);
/* 136 */     } else if (!flag && flag1) {
/* 137 */       worldIn.setBlockState(pos, state.withProperty((IProperty)TRIGGERED, Boolean.valueOf(false)), 4);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 142 */     if (!worldIn.isRemote) {
/* 143 */       dispense(worldIn, pos);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/* 151 */     return (TileEntity)new TileEntityDispenser();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 159 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)BlockPistonBase.getFacingFromEntity(worldIn, pos, placer)).withProperty((IProperty)TRIGGERED, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/* 166 */     worldIn.setBlockState(pos, state.withProperty((IProperty)FACING, (Comparable)BlockPistonBase.getFacingFromEntity(worldIn, pos, placer)), 2);
/*     */     
/* 168 */     if (stack.hasDisplayName()) {
/* 169 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/* 171 */       if (tileentity instanceof TileEntityDispenser) {
/* 172 */         ((TileEntityDispenser)tileentity).setCustomName(stack.getDisplayName());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 178 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 180 */     if (tileentity instanceof TileEntityDispenser) {
/* 181 */       InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileentity);
/* 182 */       worldIn.updateComparatorOutputLevel(pos, this);
/*     */     } 
/*     */     
/* 185 */     super.breakBlock(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IPosition getDispensePosition(IBlockSource coords) {
/* 192 */     EnumFacing enumfacing = getFacing(coords.getBlockMetadata());
/* 193 */     double d0 = coords.getX() + 0.7D * enumfacing.getFrontOffsetX();
/* 194 */     double d1 = coords.getY() + 0.7D * enumfacing.getFrontOffsetY();
/* 195 */     double d2 = coords.getZ() + 0.7D * enumfacing.getFrontOffsetZ();
/* 196 */     return (IPosition)new PositionImpl(d0, d1, d2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EnumFacing getFacing(int meta) {
/* 203 */     return EnumFacing.getFront(meta & 0x7);
/*     */   }
/*     */   
/*     */   public boolean hasComparatorInputOverride() {
/* 207 */     return true;
/*     */   }
/*     */   
/*     */   public int getComparatorInputOverride(World worldIn, BlockPos pos) {
/* 211 */     return Container.calcRedstone(worldIn.getTileEntity(pos));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRenderType() {
/* 218 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateForEntityRender(IBlockState state) {
/* 225 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)EnumFacing.SOUTH);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 232 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)getFacing(meta)).withProperty((IProperty)TRIGGERED, Boolean.valueOf(((meta & 0x8) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 239 */     int i = 0;
/* 240 */     i |= ((EnumFacing)state.getValue((IProperty)FACING)).getIndex();
/*     */     
/* 242 */     if (((Boolean)state.getValue((IProperty)TRIGGERED)).booleanValue()) {
/* 243 */       i |= 0x8;
/*     */     }
/*     */     
/* 246 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 250 */     return new BlockState(this, new IProperty[] { (IProperty)FACING, (IProperty)TRIGGERED });
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockDispenser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */