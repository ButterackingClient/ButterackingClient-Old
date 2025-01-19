/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.command.server.CommandBlockLogic;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityCommandBlock;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockCommandBlock
/*     */   extends BlockContainer {
/*  22 */   public static final PropertyBool TRIGGERED = PropertyBool.create("triggered");
/*     */   
/*     */   public BlockCommandBlock() {
/*  25 */     super(Material.iron, MapColor.adobeColor);
/*  26 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)TRIGGERED, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/*  33 */     return (TileEntity)new TileEntityCommandBlock();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/*  40 */     if (!worldIn.isRemote) {
/*  41 */       boolean flag = worldIn.isBlockPowered(pos);
/*  42 */       boolean flag1 = ((Boolean)state.getValue((IProperty)TRIGGERED)).booleanValue();
/*     */       
/*  44 */       if (flag && !flag1) {
/*  45 */         worldIn.setBlockState(pos, state.withProperty((IProperty)TRIGGERED, Boolean.valueOf(true)), 4);
/*  46 */         worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
/*  47 */       } else if (!flag && flag1) {
/*  48 */         worldIn.setBlockState(pos, state.withProperty((IProperty)TRIGGERED, Boolean.valueOf(false)), 4);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  54 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/*  56 */     if (tileentity instanceof TileEntityCommandBlock) {
/*  57 */       ((TileEntityCommandBlock)tileentity).getCommandBlockLogic().trigger(worldIn);
/*  58 */       worldIn.updateComparatorOutputLevel(pos, this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int tickRate(World worldIn) {
/*  66 */     return 1;
/*     */   }
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  70 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*  71 */     return (tileentity instanceof TileEntityCommandBlock) ? ((TileEntityCommandBlock)tileentity).getCommandBlockLogic().tryOpenEditCommandBlock(playerIn) : false;
/*     */   }
/*     */   
/*     */   public boolean hasComparatorInputOverride() {
/*  75 */     return true;
/*     */   }
/*     */   
/*     */   public int getComparatorInputOverride(World worldIn, BlockPos pos) {
/*  79 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*  80 */     return (tileentity instanceof TileEntityCommandBlock) ? ((TileEntityCommandBlock)tileentity).getCommandBlockLogic().getSuccessCount() : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/*  87 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/*  89 */     if (tileentity instanceof TileEntityCommandBlock) {
/*  90 */       CommandBlockLogic commandblocklogic = ((TileEntityCommandBlock)tileentity).getCommandBlockLogic();
/*     */       
/*  92 */       if (stack.hasDisplayName()) {
/*  93 */         commandblocklogic.setName(stack.getDisplayName());
/*     */       }
/*     */       
/*  96 */       if (!worldIn.isRemote) {
/*  97 */         commandblocklogic.setTrackOutput(worldIn.getGameRules().getBoolean("sendCommandFeedback"));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/* 106 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRenderType() {
/* 113 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 120 */     return getDefaultState().withProperty((IProperty)TRIGGERED, Boolean.valueOf(((meta & 0x1) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 127 */     int i = 0;
/*     */     
/* 129 */     if (((Boolean)state.getValue((IProperty)TRIGGERED)).booleanValue()) {
/* 130 */       i |= 0x1;
/*     */     }
/*     */     
/* 133 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 137 */     return new BlockState(this, new IProperty[] { (IProperty)TRIGGERED });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 145 */     return getDefaultState().withProperty((IProperty)TRIGGERED, Boolean.valueOf(false));
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockCommandBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */