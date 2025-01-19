/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockTripWire
/*     */   extends Block {
/*  24 */   public static final PropertyBool POWERED = PropertyBool.create("powered");
/*  25 */   public static final PropertyBool SUSPENDED = PropertyBool.create("suspended");
/*  26 */   public static final PropertyBool ATTACHED = PropertyBool.create("attached");
/*  27 */   public static final PropertyBool DISARMED = PropertyBool.create("disarmed");
/*  28 */   public static final PropertyBool NORTH = PropertyBool.create("north");
/*  29 */   public static final PropertyBool EAST = PropertyBool.create("east");
/*  30 */   public static final PropertyBool SOUTH = PropertyBool.create("south");
/*  31 */   public static final PropertyBool WEST = PropertyBool.create("west");
/*     */   
/*     */   public BlockTripWire() {
/*  34 */     super(Material.circuits);
/*  35 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)POWERED, Boolean.valueOf(false)).withProperty((IProperty)SUSPENDED, Boolean.valueOf(false)).withProperty((IProperty)ATTACHED, Boolean.valueOf(false)).withProperty((IProperty)DISARMED, Boolean.valueOf(false)).withProperty((IProperty)NORTH, Boolean.valueOf(false)).withProperty((IProperty)EAST, Boolean.valueOf(false)).withProperty((IProperty)SOUTH, Boolean.valueOf(false)).withProperty((IProperty)WEST, Boolean.valueOf(false)));
/*  36 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.15625F, 1.0F);
/*  37 */     setTickRandomly(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/*  45 */     return state.withProperty((IProperty)NORTH, Boolean.valueOf(isConnectedTo(worldIn, pos, state, EnumFacing.NORTH))).withProperty((IProperty)EAST, Boolean.valueOf(isConnectedTo(worldIn, pos, state, EnumFacing.EAST))).withProperty((IProperty)SOUTH, Boolean.valueOf(isConnectedTo(worldIn, pos, state, EnumFacing.SOUTH))).withProperty((IProperty)WEST, Boolean.valueOf(isConnectedTo(worldIn, pos, state, EnumFacing.WEST)));
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  49 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  56 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isFullCube() {
/*  60 */     return false;
/*     */   }
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/*  64 */     return EnumWorldBlockLayer.TRANSLUCENT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  71 */     return Items.string;
/*     */   }
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/*  75 */     return Items.string;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/*  82 */     boolean flag = ((Boolean)state.getValue((IProperty)SUSPENDED)).booleanValue();
/*  83 */     boolean flag1 = !World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down());
/*     */     
/*  85 */     if (flag != flag1) {
/*  86 */       dropBlockAsItem(worldIn, pos, state, 0);
/*  87 */       worldIn.setBlockToAir(pos);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/*  92 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*  93 */     boolean flag = ((Boolean)iblockstate.getValue((IProperty)ATTACHED)).booleanValue();
/*  94 */     boolean flag1 = ((Boolean)iblockstate.getValue((IProperty)SUSPENDED)).booleanValue();
/*     */     
/*  96 */     if (!flag1) {
/*  97 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.09375F, 1.0F);
/*  98 */     } else if (!flag) {
/*  99 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
/*     */     } else {
/* 101 */       setBlockBounds(0.0F, 0.0625F, 0.0F, 1.0F, 0.15625F, 1.0F);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 106 */     state = state.withProperty((IProperty)SUSPENDED, Boolean.valueOf(!World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down())));
/* 107 */     worldIn.setBlockState(pos, state, 3);
/* 108 */     notifyHook(worldIn, pos, state);
/*     */   }
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 112 */     notifyHook(worldIn, pos, state.withProperty((IProperty)POWERED, Boolean.valueOf(true)));
/*     */   }
/*     */   
/*     */   public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
/* 116 */     if (!worldIn.isRemote && 
/* 117 */       player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == Items.shears)
/* 118 */       worldIn.setBlockState(pos, state.withProperty((IProperty)DISARMED, Boolean.valueOf(true)), 4); 
/*     */   }
/*     */   private void notifyHook(World worldIn, BlockPos pos, IBlockState state) {
/*     */     byte b;
/*     */     int i;
/*     */     EnumFacing[] arrayOfEnumFacing;
/* 124 */     for (i = (arrayOfEnumFacing = new EnumFacing[] { EnumFacing.SOUTH, EnumFacing.WEST }, ).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/* 125 */       for (int j = 1; j < 42; j++) {
/* 126 */         BlockPos blockpos = pos.offset(enumfacing, j);
/* 127 */         IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*     */         
/* 129 */         if (iblockstate.getBlock() == Blocks.tripwire_hook) {
/* 130 */           if (iblockstate.getValue((IProperty)BlockTripWireHook.FACING) == enumfacing.getOpposite()) {
/* 131 */             Blocks.tripwire_hook.func_176260_a(worldIn, blockpos, iblockstate, false, true, j, state);
/*     */           }
/*     */           
/*     */           break;
/*     */         } 
/*     */         
/* 137 */         if (iblockstate.getBlock() != Blocks.tripwire) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */       b++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
/* 148 */     if (!worldIn.isRemote && 
/* 149 */       !((Boolean)state.getValue((IProperty)POWERED)).booleanValue()) {
/* 150 */       updateState(worldIn, pos);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 162 */     if (!worldIn.isRemote && (
/* 163 */       (Boolean)worldIn.getBlockState(pos).getValue((IProperty)POWERED)).booleanValue()) {
/* 164 */       updateState(worldIn, pos);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateState(World worldIn, BlockPos pos) {
/* 170 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/* 171 */     boolean flag = ((Boolean)iblockstate.getValue((IProperty)POWERED)).booleanValue();
/* 172 */     boolean flag1 = false;
/* 173 */     List<? extends Entity> list = worldIn.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(pos.getX() + this.minX, pos.getY() + this.minY, pos.getZ() + this.minZ, pos.getX() + this.maxX, pos.getY() + this.maxY, pos.getZ() + this.maxZ));
/*     */     
/* 175 */     if (!list.isEmpty()) {
/* 176 */       for (Entity entity : list) {
/* 177 */         if (!entity.doesEntityNotTriggerPressurePlate()) {
/* 178 */           flag1 = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 184 */     if (flag1 != flag) {
/* 185 */       iblockstate = iblockstate.withProperty((IProperty)POWERED, Boolean.valueOf(flag1));
/* 186 */       worldIn.setBlockState(pos, iblockstate, 3);
/* 187 */       notifyHook(worldIn, pos, iblockstate);
/*     */     } 
/*     */     
/* 190 */     if (flag1) {
/* 191 */       worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
/*     */     }
/*     */   }
/*     */   
/*     */   public static boolean isConnectedTo(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing direction) {
/* 196 */     BlockPos blockpos = pos.offset(direction);
/* 197 */     IBlockState iblockstate = worldIn.getBlockState(blockpos);
/* 198 */     Block block = iblockstate.getBlock();
/*     */     
/* 200 */     if (block == Blocks.tripwire_hook) {
/* 201 */       EnumFacing enumfacing = direction.getOpposite();
/* 202 */       return (iblockstate.getValue((IProperty)BlockTripWireHook.FACING) == enumfacing);
/* 203 */     }  if (block == Blocks.tripwire) {
/* 204 */       boolean flag = ((Boolean)state.getValue((IProperty)SUSPENDED)).booleanValue();
/* 205 */       boolean flag1 = ((Boolean)iblockstate.getValue((IProperty)SUSPENDED)).booleanValue();
/* 206 */       return (flag == flag1);
/*     */     } 
/* 208 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 216 */     return getDefaultState().withProperty((IProperty)POWERED, Boolean.valueOf(((meta & 0x1) > 0))).withProperty((IProperty)SUSPENDED, Boolean.valueOf(((meta & 0x2) > 0))).withProperty((IProperty)ATTACHED, Boolean.valueOf(((meta & 0x4) > 0))).withProperty((IProperty)DISARMED, Boolean.valueOf(((meta & 0x8) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 223 */     int i = 0;
/*     */     
/* 225 */     if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue()) {
/* 226 */       i |= 0x1;
/*     */     }
/*     */     
/* 229 */     if (((Boolean)state.getValue((IProperty)SUSPENDED)).booleanValue()) {
/* 230 */       i |= 0x2;
/*     */     }
/*     */     
/* 233 */     if (((Boolean)state.getValue((IProperty)ATTACHED)).booleanValue()) {
/* 234 */       i |= 0x4;
/*     */     }
/*     */     
/* 237 */     if (((Boolean)state.getValue((IProperty)DISARMED)).booleanValue()) {
/* 238 */       i |= 0x8;
/*     */     }
/*     */     
/* 241 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 245 */     return new BlockState(this, new IProperty[] { (IProperty)POWERED, (IProperty)SUSPENDED, (IProperty)ATTACHED, (IProperty)DISARMED, (IProperty)NORTH, (IProperty)EAST, (IProperty)WEST, (IProperty)SOUTH });
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockTripWire.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */