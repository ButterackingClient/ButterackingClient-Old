/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.ColorizerFoliage;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockOldLeaf
/*     */   extends BlockLeaves
/*     */ {
/*  24 */   public static final PropertyEnum<BlockPlanks.EnumType> VARIANT = PropertyEnum.create("variant", BlockPlanks.EnumType.class, new Predicate<BlockPlanks.EnumType>() {
/*     */         public boolean apply(BlockPlanks.EnumType p_apply_1_) {
/*  26 */           return (p_apply_1_.getMetadata() < 4);
/*     */         }
/*     */       });
/*     */   
/*     */   public BlockOldLeaf() {
/*  31 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)VARIANT, BlockPlanks.EnumType.OAK).withProperty((IProperty)CHECK_DECAY, Boolean.valueOf(true)).withProperty((IProperty)DECAYABLE, Boolean.valueOf(true)));
/*     */   }
/*     */   
/*     */   public int getRenderColor(IBlockState state) {
/*  35 */     if (state.getBlock() != this) {
/*  36 */       return super.getRenderColor(state);
/*     */     }
/*  38 */     BlockPlanks.EnumType blockplanks$enumtype = (BlockPlanks.EnumType)state.getValue((IProperty)VARIANT);
/*  39 */     return (blockplanks$enumtype == BlockPlanks.EnumType.SPRUCE) ? ColorizerFoliage.getFoliageColorPine() : ((blockplanks$enumtype == BlockPlanks.EnumType.BIRCH) ? ColorizerFoliage.getFoliageColorBirch() : super.getRenderColor(state));
/*     */   }
/*     */ 
/*     */   
/*     */   public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass) {
/*  44 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */     
/*  46 */     if (iblockstate.getBlock() == this) {
/*  47 */       BlockPlanks.EnumType blockplanks$enumtype = (BlockPlanks.EnumType)iblockstate.getValue((IProperty)VARIANT);
/*     */       
/*  49 */       if (blockplanks$enumtype == BlockPlanks.EnumType.SPRUCE) {
/*  50 */         return ColorizerFoliage.getFoliageColorPine();
/*     */       }
/*     */       
/*  53 */       if (blockplanks$enumtype == BlockPlanks.EnumType.BIRCH) {
/*  54 */         return ColorizerFoliage.getFoliageColorBirch();
/*     */       }
/*     */     } 
/*     */     
/*  58 */     return super.colorMultiplier(worldIn, pos, renderPass);
/*     */   }
/*     */   
/*     */   protected void dropApple(World worldIn, BlockPos pos, IBlockState state, int chance) {
/*  62 */     if (state.getValue((IProperty)VARIANT) == BlockPlanks.EnumType.OAK && worldIn.rand.nextInt(chance) == 0) {
/*  63 */       spawnAsEntity(worldIn, pos, new ItemStack(Items.apple, 1, 0));
/*     */     }
/*     */   }
/*     */   
/*     */   protected int getSaplingDropChance(IBlockState state) {
/*  68 */     return (state.getValue((IProperty)VARIANT) == BlockPlanks.EnumType.JUNGLE) ? 40 : super.getSaplingDropChance(state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
/*  75 */     list.add(new ItemStack(itemIn, 1, BlockPlanks.EnumType.OAK.getMetadata()));
/*  76 */     list.add(new ItemStack(itemIn, 1, BlockPlanks.EnumType.SPRUCE.getMetadata()));
/*  77 */     list.add(new ItemStack(itemIn, 1, BlockPlanks.EnumType.BIRCH.getMetadata()));
/*  78 */     list.add(new ItemStack(itemIn, 1, BlockPlanks.EnumType.JUNGLE.getMetadata()));
/*     */   }
/*     */   
/*     */   protected ItemStack createStackedBlock(IBlockState state) {
/*  82 */     return new ItemStack(Item.getItemFromBlock(this), 1, ((BlockPlanks.EnumType)state.getValue((IProperty)VARIANT)).getMetadata());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/*  89 */     return getDefaultState().withProperty((IProperty)VARIANT, getWoodType(meta)).withProperty((IProperty)DECAYABLE, Boolean.valueOf(((meta & 0x4) == 0))).withProperty((IProperty)CHECK_DECAY, Boolean.valueOf(((meta & 0x8) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/*  96 */     int i = 0;
/*  97 */     i |= ((BlockPlanks.EnumType)state.getValue((IProperty)VARIANT)).getMetadata();
/*     */     
/*  99 */     if (!((Boolean)state.getValue((IProperty)DECAYABLE)).booleanValue()) {
/* 100 */       i |= 0x4;
/*     */     }
/*     */     
/* 103 */     if (((Boolean)state.getValue((IProperty)CHECK_DECAY)).booleanValue()) {
/* 104 */       i |= 0x8;
/*     */     }
/*     */     
/* 107 */     return i;
/*     */   }
/*     */   
/*     */   public BlockPlanks.EnumType getWoodType(int meta) {
/* 111 */     return BlockPlanks.EnumType.byMetadata((meta & 0x3) % 4);
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 115 */     return new BlockState(this, new IProperty[] { (IProperty)VARIANT, (IProperty)CHECK_DECAY, (IProperty)DECAYABLE });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int damageDropped(IBlockState state) {
/* 123 */     return ((BlockPlanks.EnumType)state.getValue((IProperty)VARIANT)).getMetadata();
/*     */   }
/*     */   
/*     */   public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te) {
/* 127 */     if (!worldIn.isRemote && player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == Items.shears) {
/* 128 */       player.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
/* 129 */       spawnAsEntity(worldIn, pos, new ItemStack(Item.getItemFromBlock(this), 1, ((BlockPlanks.EnumType)state.getValue((IProperty)VARIANT)).getMetadata()));
/*     */     } else {
/* 131 */       super.harvestBlock(worldIn, player, pos, state, te);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockOldLeaf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */