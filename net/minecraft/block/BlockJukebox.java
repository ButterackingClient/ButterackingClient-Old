/*     */ package net.minecraft.block;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockJukebox extends BlockContainer {
/*  22 */   public static final PropertyBool HAS_RECORD = PropertyBool.create("has_record");
/*     */   
/*     */   protected BlockJukebox() {
/*  25 */     super(Material.wood, MapColor.dirtColor);
/*  26 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)HAS_RECORD, Boolean.valueOf(false)));
/*  27 */     setCreativeTab(CreativeTabs.tabDecorations);
/*     */   }
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  31 */     if (((Boolean)state.getValue((IProperty)HAS_RECORD)).booleanValue()) {
/*  32 */       dropRecord(worldIn, pos, state);
/*  33 */       state = state.withProperty((IProperty)HAS_RECORD, Boolean.valueOf(false));
/*  34 */       worldIn.setBlockState(pos, state, 2);
/*  35 */       return true;
/*     */     } 
/*  37 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void insertRecord(World worldIn, BlockPos pos, IBlockState state, ItemStack recordStack) {
/*  42 */     if (!worldIn.isRemote) {
/*  43 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/*  45 */       if (tileentity instanceof TileEntityJukebox) {
/*  46 */         ((TileEntityJukebox)tileentity).setRecord(new ItemStack(recordStack.getItem(), 1, recordStack.getMetadata()));
/*  47 */         worldIn.setBlockState(pos, state.withProperty((IProperty)HAS_RECORD, Boolean.valueOf(true)), 2);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void dropRecord(World worldIn, BlockPos pos, IBlockState state) {
/*  53 */     if (!worldIn.isRemote) {
/*  54 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/*  56 */       if (tileentity instanceof TileEntityJukebox) {
/*  57 */         TileEntityJukebox blockjukebox$tileentityjukebox = (TileEntityJukebox)tileentity;
/*  58 */         ItemStack itemstack = blockjukebox$tileentityjukebox.getRecord();
/*     */         
/*  60 */         if (itemstack != null) {
/*  61 */           worldIn.playAuxSFX(1005, pos, 0);
/*  62 */           worldIn.playRecord(pos, null);
/*  63 */           blockjukebox$tileentityjukebox.setRecord(null);
/*  64 */           float f = 0.7F;
/*  65 */           double d0 = (worldIn.rand.nextFloat() * f) + (1.0F - f) * 0.5D;
/*  66 */           double d1 = (worldIn.rand.nextFloat() * f) + (1.0F - f) * 0.2D + 0.6D;
/*  67 */           double d2 = (worldIn.rand.nextFloat() * f) + (1.0F - f) * 0.5D;
/*  68 */           ItemStack itemstack1 = itemstack.copy();
/*  69 */           EntityItem entityitem = new EntityItem(worldIn, pos.getX() + d0, pos.getY() + d1, pos.getZ() + d2, itemstack1);
/*  70 */           entityitem.setDefaultPickupDelay();
/*  71 */           worldIn.spawnEntityInWorld((Entity)entityitem);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/*  78 */     dropRecord(worldIn, pos, state);
/*  79 */     super.breakBlock(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
/*  86 */     if (!worldIn.isRemote) {
/*  87 */       super.dropBlockAsItemWithChance(worldIn, pos, state, chance, 0);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/*  95 */     return new TileEntityJukebox();
/*     */   }
/*     */   
/*     */   public boolean hasComparatorInputOverride() {
/*  99 */     return true;
/*     */   }
/*     */   
/*     */   public int getComparatorInputOverride(World worldIn, BlockPos pos) {
/* 103 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 105 */     if (tileentity instanceof TileEntityJukebox) {
/* 106 */       ItemStack itemstack = ((TileEntityJukebox)tileentity).getRecord();
/*     */       
/* 108 */       if (itemstack != null) {
/* 109 */         return Item.getIdFromItem(itemstack.getItem()) + 1 - Item.getIdFromItem(Items.record_13);
/*     */       }
/*     */     } 
/*     */     
/* 113 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRenderType() {
/* 120 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 127 */     return getDefaultState().withProperty((IProperty)HAS_RECORD, Boolean.valueOf((meta > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 134 */     return ((Boolean)state.getValue((IProperty)HAS_RECORD)).booleanValue() ? 1 : 0;
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 138 */     return new BlockState(this, new IProperty[] { (IProperty)HAS_RECORD });
/*     */   }
/*     */   
/*     */   public static class TileEntityJukebox extends TileEntity {
/*     */     private ItemStack record;
/*     */     
/*     */     public void readFromNBT(NBTTagCompound compound) {
/* 145 */       super.readFromNBT(compound);
/*     */       
/* 147 */       if (compound.hasKey("RecordItem", 10)) {
/* 148 */         setRecord(ItemStack.loadItemStackFromNBT(compound.getCompoundTag("RecordItem")));
/* 149 */       } else if (compound.getInteger("Record") > 0) {
/* 150 */         setRecord(new ItemStack(Item.getItemById(compound.getInteger("Record")), 1, 0));
/*     */       } 
/*     */     }
/*     */     
/*     */     public void writeToNBT(NBTTagCompound compound) {
/* 155 */       super.writeToNBT(compound);
/*     */       
/* 157 */       if (getRecord() != null) {
/* 158 */         compound.setTag("RecordItem", (NBTBase)getRecord().writeToNBT(new NBTTagCompound()));
/*     */       }
/*     */     }
/*     */     
/*     */     public ItemStack getRecord() {
/* 163 */       return this.record;
/*     */     }
/*     */     
/*     */     public void setRecord(ItemStack recordStack) {
/* 167 */       this.record = recordStack;
/* 168 */       markDirty();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockJukebox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */