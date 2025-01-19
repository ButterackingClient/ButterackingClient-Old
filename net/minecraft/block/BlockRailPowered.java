/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockRailPowered extends BlockRailBase {
/*  13 */   public static final PropertyEnum<BlockRailBase.EnumRailDirection> SHAPE = PropertyEnum.create("shape", BlockRailBase.EnumRailDirection.class, new Predicate<BlockRailBase.EnumRailDirection>() {
/*     */         public boolean apply(BlockRailBase.EnumRailDirection p_apply_1_) {
/*  15 */           return (p_apply_1_ != BlockRailBase.EnumRailDirection.NORTH_EAST && p_apply_1_ != BlockRailBase.EnumRailDirection.NORTH_WEST && p_apply_1_ != BlockRailBase.EnumRailDirection.SOUTH_EAST && p_apply_1_ != BlockRailBase.EnumRailDirection.SOUTH_WEST);
/*     */         }
/*     */       });
/*  18 */   public static final PropertyBool POWERED = PropertyBool.create("powered");
/*     */   
/*     */   protected BlockRailPowered() {
/*  21 */     super(true);
/*  22 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.NORTH_SOUTH).withProperty((IProperty)POWERED, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean func_176566_a(World worldIn, BlockPos pos, IBlockState state, boolean p_176566_4_, int p_176566_5_) {
/*  27 */     if (p_176566_5_ >= 8) {
/*  28 */       return false;
/*     */     }
/*  30 */     int i = pos.getX();
/*  31 */     int j = pos.getY();
/*  32 */     int k = pos.getZ();
/*  33 */     boolean flag = true;
/*  34 */     BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = (BlockRailBase.EnumRailDirection)state.getValue((IProperty)SHAPE);
/*     */     
/*  36 */     switch (blockrailbase$enumraildirection) {
/*     */       case NORTH_SOUTH:
/*  38 */         if (p_176566_4_) {
/*  39 */           k++; break;
/*     */         } 
/*  41 */         k--;
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case EAST_WEST:
/*  47 */         if (p_176566_4_) {
/*  48 */           i--; break;
/*     */         } 
/*  50 */         i++;
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case null:
/*  56 */         if (p_176566_4_) {
/*  57 */           i--;
/*     */         } else {
/*  59 */           i++;
/*  60 */           j++;
/*  61 */           flag = false;
/*     */         } 
/*     */         
/*  64 */         blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.EAST_WEST;
/*     */         break;
/*     */       
/*     */       case ASCENDING_WEST:
/*  68 */         if (p_176566_4_) {
/*  69 */           i--;
/*  70 */           j++;
/*  71 */           flag = false;
/*     */         } else {
/*  73 */           i++;
/*     */         } 
/*     */         
/*  76 */         blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.EAST_WEST;
/*     */         break;
/*     */       
/*     */       case ASCENDING_NORTH:
/*  80 */         if (p_176566_4_) {
/*  81 */           k++;
/*     */         } else {
/*  83 */           k--;
/*  84 */           j++;
/*  85 */           flag = false;
/*     */         } 
/*     */         
/*  88 */         blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
/*     */         break;
/*     */       
/*     */       case ASCENDING_SOUTH:
/*  92 */         if (p_176566_4_) {
/*  93 */           k++;
/*  94 */           j++;
/*  95 */           flag = false;
/*     */         } else {
/*  97 */           k--;
/*     */         } 
/*     */         
/* 100 */         blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
/*     */         break;
/*     */     } 
/* 103 */     return func_176567_a(worldIn, new BlockPos(i, j, k), p_176566_4_, p_176566_5_, blockrailbase$enumraildirection) ? true : ((flag && func_176567_a(worldIn, new BlockPos(i, j - 1, k), p_176566_4_, p_176566_5_, blockrailbase$enumraildirection)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean func_176567_a(World worldIn, BlockPos p_176567_2_, boolean p_176567_3_, int distance, BlockRailBase.EnumRailDirection p_176567_5_) {
/* 108 */     IBlockState iblockstate = worldIn.getBlockState(p_176567_2_);
/*     */     
/* 110 */     if (iblockstate.getBlock() != this) {
/* 111 */       return false;
/*     */     }
/* 113 */     BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = (BlockRailBase.EnumRailDirection)iblockstate.getValue((IProperty)SHAPE);
/* 114 */     return (p_176567_5_ != BlockRailBase.EnumRailDirection.EAST_WEST || (blockrailbase$enumraildirection != BlockRailBase.EnumRailDirection.NORTH_SOUTH && blockrailbase$enumraildirection != BlockRailBase.EnumRailDirection.ASCENDING_NORTH && blockrailbase$enumraildirection != BlockRailBase.EnumRailDirection.ASCENDING_SOUTH)) ? ((p_176567_5_ != BlockRailBase.EnumRailDirection.NORTH_SOUTH || (blockrailbase$enumraildirection != BlockRailBase.EnumRailDirection.EAST_WEST && blockrailbase$enumraildirection != BlockRailBase.EnumRailDirection.ASCENDING_EAST && blockrailbase$enumraildirection != BlockRailBase.EnumRailDirection.ASCENDING_WEST)) ? (((Boolean)iblockstate.getValue((IProperty)POWERED)).booleanValue() ? (worldIn.isBlockPowered(p_176567_2_) ? true : func_176566_a(worldIn, p_176567_2_, iblockstate, p_176567_3_, distance + 1)) : false) : false) : false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onNeighborChangedInternal(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 119 */     boolean flag = ((Boolean)state.getValue((IProperty)POWERED)).booleanValue();
/* 120 */     boolean flag1 = !(!worldIn.isBlockPowered(pos) && !func_176566_a(worldIn, pos, state, true, 0) && !func_176566_a(worldIn, pos, state, false, 0));
/*     */     
/* 122 */     if (flag1 != flag) {
/* 123 */       worldIn.setBlockState(pos, state.withProperty((IProperty)POWERED, Boolean.valueOf(flag1)), 3);
/* 124 */       worldIn.notifyNeighborsOfStateChange(pos.down(), this);
/*     */       
/* 126 */       if (((BlockRailBase.EnumRailDirection)state.getValue((IProperty)SHAPE)).isAscending()) {
/* 127 */         worldIn.notifyNeighborsOfStateChange(pos.up(), this);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public IProperty<BlockRailBase.EnumRailDirection> getShapeProperty() {
/* 133 */     return (IProperty<BlockRailBase.EnumRailDirection>)SHAPE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 140 */     return getDefaultState().withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.byMetadata(meta & 0x7)).withProperty((IProperty)POWERED, Boolean.valueOf(((meta & 0x8) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 147 */     int i = 0;
/* 148 */     i |= ((BlockRailBase.EnumRailDirection)state.getValue((IProperty)SHAPE)).getMetadata();
/*     */     
/* 150 */     if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue()) {
/* 151 */       i |= 0x8;
/*     */     }
/*     */     
/* 154 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 158 */     return new BlockState(this, new IProperty[] { (IProperty)SHAPE, (IProperty)POWERED });
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockRailPowered.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */