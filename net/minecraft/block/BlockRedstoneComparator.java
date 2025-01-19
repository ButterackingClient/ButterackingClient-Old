/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.item.EntityItemFrame;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityComparator;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockRedstoneComparator
/*     */   extends BlockRedstoneDiode
/*     */   implements ITileEntityProvider {
/*  32 */   public static final PropertyBool POWERED = PropertyBool.create("powered");
/*  33 */   public static final PropertyEnum<Mode> MODE = PropertyEnum.create("mode", Mode.class);
/*     */   
/*     */   public BlockRedstoneComparator(boolean powered) {
/*  36 */     super(powered);
/*  37 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)POWERED, Boolean.valueOf(false)).withProperty((IProperty)MODE, Mode.COMPARE));
/*  38 */     this.isBlockContainer = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalizedName() {
/*  45 */     return StatCollector.translateToLocal("item.comparator.name");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  52 */     return Items.comparator;
/*     */   }
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/*  56 */     return Items.comparator;
/*     */   }
/*     */   
/*     */   protected int getDelay(IBlockState state) {
/*  60 */     return 2;
/*     */   }
/*     */   
/*     */   protected IBlockState getPoweredState(IBlockState unpoweredState) {
/*  64 */     Boolean obool = (Boolean)unpoweredState.getValue((IProperty)POWERED);
/*  65 */     Mode blockredstonecomparator$mode = (Mode)unpoweredState.getValue((IProperty)MODE);
/*  66 */     EnumFacing enumfacing = (EnumFacing)unpoweredState.getValue((IProperty)FACING);
/*  67 */     return Blocks.powered_comparator.getDefaultState().withProperty((IProperty)FACING, (Comparable)enumfacing).withProperty((IProperty)POWERED, obool).withProperty((IProperty)MODE, blockredstonecomparator$mode);
/*     */   }
/*     */   
/*     */   protected IBlockState getUnpoweredState(IBlockState poweredState) {
/*  71 */     Boolean obool = (Boolean)poweredState.getValue((IProperty)POWERED);
/*  72 */     Mode blockredstonecomparator$mode = (Mode)poweredState.getValue((IProperty)MODE);
/*  73 */     EnumFacing enumfacing = (EnumFacing)poweredState.getValue((IProperty)FACING);
/*  74 */     return Blocks.unpowered_comparator.getDefaultState().withProperty((IProperty)FACING, (Comparable)enumfacing).withProperty((IProperty)POWERED, obool).withProperty((IProperty)MODE, blockredstonecomparator$mode);
/*     */   }
/*     */   
/*     */   protected boolean isPowered(IBlockState state) {
/*  78 */     return !(!this.isRepeaterPowered && !((Boolean)state.getValue((IProperty)POWERED)).booleanValue());
/*     */   }
/*     */   
/*     */   protected int getActiveSignal(IBlockAccess worldIn, BlockPos pos, IBlockState state) {
/*  82 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*  83 */     return (tileentity instanceof TileEntityComparator) ? ((TileEntityComparator)tileentity).getOutputSignal() : 0;
/*     */   }
/*     */   
/*     */   private int calculateOutput(World worldIn, BlockPos pos, IBlockState state) {
/*  87 */     return (state.getValue((IProperty)MODE) == Mode.SUBTRACT) ? Math.max(calculateInputStrength(worldIn, pos, state) - getPowerOnSides((IBlockAccess)worldIn, pos, state), 0) : calculateInputStrength(worldIn, pos, state);
/*     */   }
/*     */   
/*     */   protected boolean shouldBePowered(World worldIn, BlockPos pos, IBlockState state) {
/*  91 */     int i = calculateInputStrength(worldIn, pos, state);
/*     */     
/*  93 */     if (i >= 15)
/*  94 */       return true; 
/*  95 */     if (i == 0) {
/*  96 */       return false;
/*     */     }
/*  98 */     int j = getPowerOnSides((IBlockAccess)worldIn, pos, state);
/*  99 */     return (j == 0) ? true : ((i >= j));
/*     */   }
/*     */ 
/*     */   
/*     */   protected int calculateInputStrength(World worldIn, BlockPos pos, IBlockState state) {
/* 104 */     int i = super.calculateInputStrength(worldIn, pos, state);
/* 105 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/* 106 */     BlockPos blockpos = pos.offset(enumfacing);
/* 107 */     Block block = worldIn.getBlockState(blockpos).getBlock();
/*     */     
/* 109 */     if (block.hasComparatorInputOverride()) {
/* 110 */       i = block.getComparatorInputOverride(worldIn, blockpos);
/* 111 */     } else if (i < 15 && block.isNormalCube()) {
/* 112 */       blockpos = blockpos.offset(enumfacing);
/* 113 */       block = worldIn.getBlockState(blockpos).getBlock();
/*     */       
/* 115 */       if (block.hasComparatorInputOverride()) {
/* 116 */         i = block.getComparatorInputOverride(worldIn, blockpos);
/* 117 */       } else if (block.getMaterial() == Material.air) {
/* 118 */         EntityItemFrame entityitemframe = findItemFrame(worldIn, enumfacing, blockpos);
/*     */         
/* 120 */         if (entityitemframe != null) {
/* 121 */           i = entityitemframe.func_174866_q();
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 126 */     return i;
/*     */   }
/*     */   
/*     */   private EntityItemFrame findItemFrame(World worldIn, final EnumFacing facing, BlockPos pos) {
/* 130 */     List<EntityItemFrame> list = worldIn.getEntitiesWithinAABB(EntityItemFrame.class, new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), (pos.getX() + 1), (pos.getY() + 1), (pos.getZ() + 1)), new Predicate<Entity>() {
/*     */           public boolean apply(Entity p_apply_1_) {
/* 132 */             return (p_apply_1_ != null && p_apply_1_.getHorizontalFacing() == facing);
/*     */           }
/*     */         });
/* 135 */     return (list.size() == 1) ? list.get(0) : null;
/*     */   }
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 139 */     if (!playerIn.capabilities.allowEdit) {
/* 140 */       return false;
/*     */     }
/* 142 */     state = state.cycleProperty((IProperty)MODE);
/* 143 */     worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "random.click", 0.3F, (state.getValue((IProperty)MODE) == Mode.SUBTRACT) ? 0.55F : 0.5F);
/* 144 */     worldIn.setBlockState(pos, state, 2);
/* 145 */     onStateChange(worldIn, pos, state);
/* 146 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateState(World worldIn, BlockPos pos, IBlockState state) {
/* 151 */     if (!worldIn.isBlockTickPending(pos, this)) {
/* 152 */       int i = calculateOutput(worldIn, pos, state);
/* 153 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/* 154 */       int j = (tileentity instanceof TileEntityComparator) ? ((TileEntityComparator)tileentity).getOutputSignal() : 0;
/*     */       
/* 156 */       if (i != j || isPowered(state) != shouldBePowered(worldIn, pos, state)) {
/* 157 */         if (isFacingTowardsRepeater(worldIn, pos, state)) {
/* 158 */           worldIn.updateBlockTick(pos, this, 2, -1);
/*     */         } else {
/* 160 */           worldIn.updateBlockTick(pos, this, 2, 0);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void onStateChange(World worldIn, BlockPos pos, IBlockState state) {
/* 167 */     int i = calculateOutput(worldIn, pos, state);
/* 168 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/* 169 */     int j = 0;
/*     */     
/* 171 */     if (tileentity instanceof TileEntityComparator) {
/* 172 */       TileEntityComparator tileentitycomparator = (TileEntityComparator)tileentity;
/* 173 */       j = tileentitycomparator.getOutputSignal();
/* 174 */       tileentitycomparator.setOutputSignal(i);
/*     */     } 
/*     */     
/* 177 */     if (j != i || state.getValue((IProperty)MODE) == Mode.COMPARE) {
/* 178 */       boolean flag1 = shouldBePowered(worldIn, pos, state);
/* 179 */       boolean flag = isPowered(state);
/*     */       
/* 181 */       if (flag && !flag1) {
/* 182 */         worldIn.setBlockState(pos, state.withProperty((IProperty)POWERED, Boolean.valueOf(false)), 2);
/* 183 */       } else if (!flag && flag1) {
/* 184 */         worldIn.setBlockState(pos, state.withProperty((IProperty)POWERED, Boolean.valueOf(true)), 2);
/*     */       } 
/*     */       
/* 187 */       notifyNeighbors(worldIn, pos, state);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 192 */     if (this.isRepeaterPowered) {
/* 193 */       worldIn.setBlockState(pos, getUnpoweredState(state).withProperty((IProperty)POWERED, Boolean.valueOf(true)), 4);
/*     */     }
/*     */     
/* 196 */     onStateChange(worldIn, pos, state);
/*     */   }
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 200 */     super.onBlockAdded(worldIn, pos, state);
/* 201 */     worldIn.setTileEntity(pos, createNewTileEntity(worldIn, 0));
/*     */   }
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 205 */     super.breakBlock(worldIn, pos, state);
/* 206 */     worldIn.removeTileEntity(pos);
/* 207 */     notifyNeighbors(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onBlockEventReceived(World worldIn, BlockPos pos, IBlockState state, int eventID, int eventParam) {
/* 214 */     super.onBlockEventReceived(worldIn, pos, state, eventID, eventParam);
/* 215 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/* 216 */     return (tileentity == null) ? false : tileentity.receiveClientEvent(eventID, eventParam);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/* 223 */     return (TileEntity)new TileEntityComparator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 230 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)EnumFacing.getHorizontal(meta)).withProperty((IProperty)POWERED, Boolean.valueOf(((meta & 0x8) > 0))).withProperty((IProperty)MODE, ((meta & 0x4) > 0) ? Mode.SUBTRACT : Mode.COMPARE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 237 */     int i = 0;
/* 238 */     i |= ((EnumFacing)state.getValue((IProperty)FACING)).getHorizontalIndex();
/*     */     
/* 240 */     if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue()) {
/* 241 */       i |= 0x8;
/*     */     }
/*     */     
/* 244 */     if (state.getValue((IProperty)MODE) == Mode.SUBTRACT) {
/* 245 */       i |= 0x4;
/*     */     }
/*     */     
/* 248 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 252 */     return new BlockState(this, new IProperty[] { (IProperty)FACING, (IProperty)MODE, (IProperty)POWERED });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 260 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)placer.getHorizontalFacing().getOpposite()).withProperty((IProperty)POWERED, Boolean.valueOf(false)).withProperty((IProperty)MODE, Mode.COMPARE);
/*     */   }
/*     */   
/*     */   public enum Mode implements IStringSerializable {
/* 264 */     COMPARE("compare"),
/* 265 */     SUBTRACT("subtract");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     Mode(String name) {
/* 270 */       this.name = name;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 274 */       return this.name;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 278 */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockRedstoneComparator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */