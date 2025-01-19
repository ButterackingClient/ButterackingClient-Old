/*     */ package net.minecraft.block;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityBeacon;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.HttpUtil;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ 
/*     */ public class BlockBeacon extends BlockContainer {
/*     */   public BlockBeacon() {
/*  24 */     super(Material.glass, MapColor.diamondColor);
/*  25 */     setHardness(3.0F);
/*  26 */     setCreativeTab(CreativeTabs.tabMisc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/*  33 */     return (TileEntity)new TileEntityBeacon();
/*     */   }
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  37 */     if (worldIn.isRemote) {
/*  38 */       return true;
/*     */     }
/*  40 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/*  42 */     if (tileentity instanceof TileEntityBeacon) {
/*  43 */       playerIn.displayGUIChest((IInventory)tileentity);
/*  44 */       playerIn.triggerAchievement(StatList.field_181730_N);
/*     */     } 
/*     */     
/*  47 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  55 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isFullCube() {
/*  59 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRenderType() {
/*  66 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/*  73 */     super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
/*     */     
/*  75 */     if (stack.hasDisplayName()) {
/*  76 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/*  78 */       if (tileentity instanceof TileEntityBeacon) {
/*  79 */         ((TileEntityBeacon)tileentity).setName(stack.getDisplayName());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/*  88 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/*  90 */     if (tileentity instanceof TileEntityBeacon) {
/*  91 */       ((TileEntityBeacon)tileentity).updateBeacon();
/*  92 */       worldIn.addBlockEvent(pos, this, 1, 0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/*  97 */     return EnumWorldBlockLayer.CUTOUT;
/*     */   }
/*     */   
/*     */   public static void updateColorAsync(final World worldIn, final BlockPos glassPos) {
/* 101 */     HttpUtil.field_180193_a.submit(new Runnable() {
/*     */           public void run() {
/* 103 */             Chunk chunk = worldIn.getChunkFromBlockCoords(glassPos);
/*     */             
/* 105 */             for (int i = glassPos.getY() - 1; i >= 0; i--) {
/* 106 */               final BlockPos blockpos = new BlockPos(glassPos.getX(), i, glassPos.getZ());
/*     */               
/* 108 */               if (!chunk.canSeeSky(blockpos)) {
/*     */                 break;
/*     */               }
/*     */               
/* 112 */               IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*     */               
/* 114 */               if (iblockstate.getBlock() == Blocks.beacon)
/* 115 */                 ((WorldServer)worldIn).addScheduledTask(new Runnable() {
/*     */                       public void run() {
/* 117 */                         TileEntity tileentity = worldIn.getTileEntity(blockpos);
/*     */                         
/* 119 */                         if (tileentity instanceof TileEntityBeacon) {
/* 120 */                           ((TileEntityBeacon)tileentity).updateBeacon();
/* 121 */                           worldIn.addBlockEvent(blockpos, Blocks.beacon, 1, 0);
/*     */                         } 
/*     */                       }
/*     */                     }); 
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockBeacon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */