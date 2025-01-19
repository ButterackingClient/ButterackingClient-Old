/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityMinecartCommandBlock;
/*     */ import net.minecraft.entity.item.EntityMinecart;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EntitySelectors;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockRailDetector
/*     */   extends BlockRailBase
/*     */ {
/*  26 */   public static final PropertyEnum<BlockRailBase.EnumRailDirection> SHAPE = PropertyEnum.create("shape", BlockRailBase.EnumRailDirection.class, new Predicate<BlockRailBase.EnumRailDirection>() {
/*     */         public boolean apply(BlockRailBase.EnumRailDirection p_apply_1_) {
/*  28 */           return (p_apply_1_ != BlockRailBase.EnumRailDirection.NORTH_EAST && p_apply_1_ != BlockRailBase.EnumRailDirection.NORTH_WEST && p_apply_1_ != BlockRailBase.EnumRailDirection.SOUTH_EAST && p_apply_1_ != BlockRailBase.EnumRailDirection.SOUTH_WEST);
/*     */         }
/*     */       });
/*  31 */   public static final PropertyBool POWERED = PropertyBool.create("powered");
/*     */   
/*     */   public BlockRailDetector() {
/*  34 */     super(true);
/*  35 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)POWERED, Boolean.valueOf(false)).withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.NORTH_SOUTH));
/*  36 */     setTickRandomly(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int tickRate(World worldIn) {
/*  43 */     return 20;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canProvidePower() {
/*  50 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
/*  57 */     if (!worldIn.isRemote && 
/*  58 */       !((Boolean)state.getValue((IProperty)POWERED)).booleanValue()) {
/*  59 */       updatePoweredState(worldIn, pos, state);
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
/*  71 */     if (!worldIn.isRemote && ((Boolean)state.getValue((IProperty)POWERED)).booleanValue()) {
/*  72 */       updatePoweredState(worldIn, pos, state);
/*     */     }
/*     */   }
/*     */   
/*     */   public int getWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/*  77 */     return ((Boolean)state.getValue((IProperty)POWERED)).booleanValue() ? 15 : 0;
/*     */   }
/*     */   
/*     */   public int getStrongPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/*  81 */     return !((Boolean)state.getValue((IProperty)POWERED)).booleanValue() ? 0 : ((side == EnumFacing.UP) ? 15 : 0);
/*     */   }
/*     */   
/*     */   private void updatePoweredState(World worldIn, BlockPos pos, IBlockState state) {
/*  85 */     boolean flag = ((Boolean)state.getValue((IProperty)POWERED)).booleanValue();
/*  86 */     boolean flag1 = false;
/*  87 */     List<EntityMinecart> list = findMinecarts(worldIn, pos, EntityMinecart.class, (Predicate<Entity>[])new Predicate[0]);
/*     */     
/*  89 */     if (!list.isEmpty()) {
/*  90 */       flag1 = true;
/*     */     }
/*     */     
/*  93 */     if (flag1 && !flag) {
/*  94 */       worldIn.setBlockState(pos, state.withProperty((IProperty)POWERED, Boolean.valueOf(true)), 3);
/*  95 */       worldIn.notifyNeighborsOfStateChange(pos, this);
/*  96 */       worldIn.notifyNeighborsOfStateChange(pos.down(), this);
/*  97 */       worldIn.markBlockRangeForRenderUpdate(pos, pos);
/*     */     } 
/*     */     
/* 100 */     if (!flag1 && flag) {
/* 101 */       worldIn.setBlockState(pos, state.withProperty((IProperty)POWERED, Boolean.valueOf(false)), 3);
/* 102 */       worldIn.notifyNeighborsOfStateChange(pos, this);
/* 103 */       worldIn.notifyNeighborsOfStateChange(pos.down(), this);
/* 104 */       worldIn.markBlockRangeForRenderUpdate(pos, pos);
/*     */     } 
/*     */     
/* 107 */     if (flag1) {
/* 108 */       worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
/*     */     }
/*     */     
/* 111 */     worldIn.updateComparatorOutputLevel(pos, this);
/*     */   }
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 115 */     super.onBlockAdded(worldIn, pos, state);
/* 116 */     updatePoweredState(worldIn, pos, state);
/*     */   }
/*     */   
/*     */   public IProperty<BlockRailBase.EnumRailDirection> getShapeProperty() {
/* 120 */     return (IProperty<BlockRailBase.EnumRailDirection>)SHAPE;
/*     */   }
/*     */   
/*     */   public boolean hasComparatorInputOverride() {
/* 124 */     return true;
/*     */   }
/*     */   
/*     */   public int getComparatorInputOverride(World worldIn, BlockPos pos) {
/* 128 */     if (((Boolean)worldIn.getBlockState(pos).getValue((IProperty)POWERED)).booleanValue()) {
/* 129 */       List<EntityMinecartCommandBlock> list = findMinecarts(worldIn, pos, EntityMinecartCommandBlock.class, (Predicate<Entity>[])new Predicate[0]);
/*     */       
/* 131 */       if (!list.isEmpty()) {
/* 132 */         return ((EntityMinecartCommandBlock)list.get(0)).getCommandBlockLogic().getSuccessCount();
/*     */       }
/*     */       
/* 135 */       List<EntityMinecart> list1 = findMinecarts(worldIn, pos, EntityMinecart.class, (Predicate<Entity>[])new Predicate[] { EntitySelectors.selectInventories });
/*     */       
/* 137 */       if (!list1.isEmpty()) {
/* 138 */         return Container.calcRedstoneFromInventory((IInventory)list1.get(0));
/*     */       }
/*     */     } 
/*     */     
/* 142 */     return 0;
/*     */   }
/*     */   
/*     */   protected <T extends EntityMinecart> List<T> findMinecarts(World worldIn, BlockPos pos, Class<T> clazz, Predicate... filter) {
/* 146 */     AxisAlignedBB axisalignedbb = getDectectionBox(pos);
/* 147 */     return (filter.length != 1) ? worldIn.getEntitiesWithinAABB(clazz, axisalignedbb) : worldIn.getEntitiesWithinAABB(clazz, axisalignedbb, filter[0]);
/*     */   }
/*     */   
/*     */   private AxisAlignedBB getDectectionBox(BlockPos pos) {
/* 151 */     float f = 0.2F;
/* 152 */     return new AxisAlignedBB((pos.getX() + 0.2F), pos.getY(), (pos.getZ() + 0.2F), ((pos.getX() + 1) - 0.2F), ((pos.getY() + 1) - 0.2F), ((pos.getZ() + 1) - 0.2F));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 159 */     return getDefaultState().withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.byMetadata(meta & 0x7)).withProperty((IProperty)POWERED, Boolean.valueOf(((meta & 0x8) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 166 */     int i = 0;
/* 167 */     i |= ((BlockRailBase.EnumRailDirection)state.getValue((IProperty)SHAPE)).getMetadata();
/*     */     
/* 169 */     if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue()) {
/* 170 */       i |= 0x8;
/*     */     }
/*     */     
/* 173 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 177 */     return new BlockState(this, new IProperty[] { (IProperty)SHAPE, (IProperty)POWERED });
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockRailDetector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */