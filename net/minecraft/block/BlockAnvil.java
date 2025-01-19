/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.item.EntityFallingBlock;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ContainerRepair;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.IInteractionObject;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockAnvil extends BlockFalling {
/*  30 */   public static final PropertyDirection FACING = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
/*  31 */   public static final PropertyInteger DAMAGE = PropertyInteger.create("damage", 0, 2);
/*     */   
/*     */   protected BlockAnvil() {
/*  34 */     super(Material.anvil);
/*  35 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)DAMAGE, Integer.valueOf(0)));
/*  36 */     setLightOpacity(0);
/*  37 */     setCreativeTab(CreativeTabs.tabDecorations);
/*     */   }
/*     */   
/*     */   public boolean isFullCube() {
/*  41 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  48 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/*  56 */     EnumFacing enumfacing = placer.getHorizontalFacing().rotateY();
/*  57 */     return super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty((IProperty)FACING, (Comparable)enumfacing).withProperty((IProperty)DAMAGE, Integer.valueOf(meta >> 2));
/*     */   }
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  61 */     if (!worldIn.isRemote) {
/*  62 */       playerIn.displayGui(new Anvil(worldIn, pos));
/*     */     }
/*     */     
/*  65 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int damageDropped(IBlockState state) {
/*  73 */     return ((Integer)state.getValue((IProperty)DAMAGE)).intValue();
/*     */   }
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/*  77 */     EnumFacing enumfacing = (EnumFacing)worldIn.getBlockState(pos).getValue((IProperty)FACING);
/*     */     
/*  79 */     if (enumfacing.getAxis() == EnumFacing.Axis.X) {
/*  80 */       setBlockBounds(0.0F, 0.0F, 0.125F, 1.0F, 1.0F, 0.875F);
/*     */     } else {
/*  82 */       setBlockBounds(0.125F, 0.0F, 0.0F, 0.875F, 1.0F, 1.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
/*  90 */     list.add(new ItemStack(itemIn, 1, 0));
/*  91 */     list.add(new ItemStack(itemIn, 1, 1));
/*  92 */     list.add(new ItemStack(itemIn, 1, 2));
/*     */   }
/*     */   
/*     */   protected void onStartFalling(EntityFallingBlock fallingEntity) {
/*  96 */     fallingEntity.setHurtEntities(true);
/*     */   }
/*     */   
/*     */   public void onEndFalling(World worldIn, BlockPos pos) {
/* 100 */     worldIn.playAuxSFX(1022, pos, 0);
/*     */   }
/*     */   
/*     */   public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/* 104 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateForEntityRender(IBlockState state) {
/* 111 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)EnumFacing.SOUTH);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 118 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)EnumFacing.getHorizontal(meta & 0x3)).withProperty((IProperty)DAMAGE, Integer.valueOf((meta & 0xF) >> 2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 125 */     int i = 0;
/* 126 */     i |= ((EnumFacing)state.getValue((IProperty)FACING)).getHorizontalIndex();
/* 127 */     i |= ((Integer)state.getValue((IProperty)DAMAGE)).intValue() << 2;
/* 128 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 132 */     return new BlockState(this, new IProperty[] { (IProperty)FACING, (IProperty)DAMAGE });
/*     */   }
/*     */   
/*     */   public static class Anvil implements IInteractionObject {
/*     */     private final World world;
/*     */     private final BlockPos position;
/*     */     
/*     */     public Anvil(World worldIn, BlockPos pos) {
/* 140 */       this.world = worldIn;
/* 141 */       this.position = pos;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 145 */       return "anvil";
/*     */     }
/*     */     
/*     */     public boolean hasCustomName() {
/* 149 */       return false;
/*     */     }
/*     */     
/*     */     public IChatComponent getDisplayName() {
/* 153 */       return (IChatComponent)new ChatComponentTranslation(String.valueOf(Blocks.anvil.getUnlocalizedName()) + ".name", new Object[0]);
/*     */     }
/*     */     
/*     */     public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
/* 157 */       return (Container)new ContainerRepair(playerInventory, this.world, this.position, playerIn);
/*     */     }
/*     */     
/*     */     public String getGuiID() {
/* 161 */       return "minecraft:anvil";
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockAnvil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */