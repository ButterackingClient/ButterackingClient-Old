/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityEnchantmentTable;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.world.IInteractionObject;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockEnchantmentTable extends BlockContainer {
/*     */   protected BlockEnchantmentTable() {
/*  22 */     super(Material.rock, MapColor.redColor);
/*  23 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
/*  24 */     setLightOpacity(0);
/*  25 */     setCreativeTab(CreativeTabs.tabDecorations);
/*     */   }
/*     */   
/*     */   public boolean isFullCube() {
/*  29 */     return false;
/*     */   }
/*     */   
/*     */   public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  33 */     super.randomDisplayTick(worldIn, pos, state, rand);
/*     */     
/*  35 */     for (int i = -2; i <= 2; i++) {
/*  36 */       for (int j = -2; j <= 2; j++) {
/*  37 */         if (i > -2 && i < 2 && j == -1) {
/*  38 */           j = 2;
/*     */         }
/*     */         
/*  41 */         if (rand.nextInt(16) == 0) {
/*  42 */           for (int k = 0; k <= 1; k++) {
/*  43 */             BlockPos blockpos = pos.add(i, k, j);
/*     */             
/*  45 */             if (worldIn.getBlockState(blockpos).getBlock() == Blocks.bookshelf) {
/*  46 */               if (!worldIn.isAirBlock(pos.add(i / 2, 0, j / 2))) {
/*     */                 break;
/*     */               }
/*     */               
/*  50 */               worldIn.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, pos.getX() + 0.5D, pos.getY() + 2.0D, pos.getZ() + 0.5D, (i + rand.nextFloat()) - 0.5D, (k - rand.nextFloat() - 1.0F), (j + rand.nextFloat()) - 0.5D, new int[0]);
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  62 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRenderType() {
/*  69 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/*  76 */     return (TileEntity)new TileEntityEnchantmentTable();
/*     */   }
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  80 */     if (worldIn.isRemote) {
/*  81 */       return true;
/*     */     }
/*  83 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/*  85 */     if (tileentity instanceof TileEntityEnchantmentTable) {
/*  86 */       playerIn.displayGui((IInteractionObject)tileentity);
/*     */     }
/*     */     
/*  89 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/*  97 */     super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
/*     */     
/*  99 */     if (stack.hasDisplayName()) {
/* 100 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/* 102 */       if (tileentity instanceof TileEntityEnchantmentTable)
/* 103 */         ((TileEntityEnchantmentTable)tileentity).setCustomName(stack.getDisplayName()); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockEnchantmentTable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */