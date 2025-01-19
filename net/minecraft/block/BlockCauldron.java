/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.TileEntityBanner;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockCauldron
/*     */   extends Block
/*     */ {
/*  30 */   public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 3);
/*     */   
/*     */   public BlockCauldron() {
/*  33 */     super(Material.iron, MapColor.stoneColor);
/*  34 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)LEVEL, Integer.valueOf(0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity) {
/*  41 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.3125F, 1.0F);
/*  42 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*  43 */     float f = 0.125F;
/*  44 */     setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
/*  45 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*  46 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
/*  47 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*  48 */     setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  49 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*  50 */     setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
/*  51 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*  52 */     setBlockBoundsForItemRender();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockBoundsForItemRender() {
/*  59 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  66 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isFullCube() {
/*  70 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
/*  77 */     int i = ((Integer)state.getValue((IProperty)LEVEL)).intValue();
/*  78 */     float f = pos.getY() + (6.0F + (3 * i)) / 16.0F;
/*     */     
/*  80 */     if (!worldIn.isRemote && entityIn.isBurning() && i > 0 && (entityIn.getEntityBoundingBox()).minY <= f) {
/*  81 */       entityIn.extinguish();
/*  82 */       setWaterLevel(worldIn, pos, state, i - 1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  87 */     if (worldIn.isRemote) {
/*  88 */       return true;
/*     */     }
/*  90 */     ItemStack itemstack = playerIn.inventory.getCurrentItem();
/*     */     
/*  92 */     if (itemstack == null) {
/*  93 */       return true;
/*     */     }
/*  95 */     int i = ((Integer)state.getValue((IProperty)LEVEL)).intValue();
/*  96 */     Item item = itemstack.getItem();
/*     */     
/*  98 */     if (item == Items.water_bucket) {
/*  99 */       if (i < 3) {
/* 100 */         if (!playerIn.capabilities.isCreativeMode) {
/* 101 */           playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, new ItemStack(Items.bucket));
/*     */         }
/*     */         
/* 104 */         playerIn.triggerAchievement(StatList.field_181725_I);
/* 105 */         setWaterLevel(worldIn, pos, state, 3);
/*     */       } 
/*     */       
/* 108 */       return true;
/* 109 */     }  if (item == Items.glass_bottle) {
/* 110 */       if (i > 0) {
/* 111 */         if (!playerIn.capabilities.isCreativeMode) {
/* 112 */           ItemStack itemstack2 = new ItemStack((Item)Items.potionitem, 1, 0);
/*     */           
/* 114 */           if (!playerIn.inventory.addItemStackToInventory(itemstack2)) {
/* 115 */             worldIn.spawnEntityInWorld((Entity)new EntityItem(worldIn, pos.getX() + 0.5D, pos.getY() + 1.5D, pos.getZ() + 0.5D, itemstack2));
/* 116 */           } else if (playerIn instanceof EntityPlayerMP) {
/* 117 */             ((EntityPlayerMP)playerIn).sendContainerToPlayer(playerIn.inventoryContainer);
/*     */           } 
/*     */           
/* 120 */           playerIn.triggerAchievement(StatList.field_181726_J);
/* 121 */           itemstack.stackSize--;
/*     */           
/* 123 */           if (itemstack.stackSize <= 0) {
/* 124 */             playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, null);
/*     */           }
/*     */         } 
/*     */         
/* 128 */         setWaterLevel(worldIn, pos, state, i - 1);
/*     */       } 
/*     */       
/* 131 */       return true;
/*     */     } 
/* 133 */     if (i > 0 && item instanceof ItemArmor) {
/* 134 */       ItemArmor itemarmor = (ItemArmor)item;
/*     */       
/* 136 */       if (itemarmor.getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER && itemarmor.hasColor(itemstack)) {
/* 137 */         itemarmor.removeColor(itemstack);
/* 138 */         setWaterLevel(worldIn, pos, state, i - 1);
/* 139 */         playerIn.triggerAchievement(StatList.field_181727_K);
/* 140 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 144 */     if (i > 0 && item instanceof net.minecraft.item.ItemBanner && TileEntityBanner.getPatterns(itemstack) > 0) {
/* 145 */       ItemStack itemstack1 = itemstack.copy();
/* 146 */       itemstack1.stackSize = 1;
/* 147 */       TileEntityBanner.removeBannerData(itemstack1);
/*     */       
/* 149 */       if (itemstack.stackSize <= 1 && !playerIn.capabilities.isCreativeMode) {
/* 150 */         playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, itemstack1);
/*     */       } else {
/* 152 */         if (!playerIn.inventory.addItemStackToInventory(itemstack1)) {
/* 153 */           worldIn.spawnEntityInWorld((Entity)new EntityItem(worldIn, pos.getX() + 0.5D, pos.getY() + 1.5D, pos.getZ() + 0.5D, itemstack1));
/* 154 */         } else if (playerIn instanceof EntityPlayerMP) {
/* 155 */           ((EntityPlayerMP)playerIn).sendContainerToPlayer(playerIn.inventoryContainer);
/*     */         } 
/*     */         
/* 158 */         playerIn.triggerAchievement(StatList.field_181728_L);
/*     */         
/* 160 */         if (!playerIn.capabilities.isCreativeMode) {
/* 161 */           itemstack.stackSize--;
/*     */         }
/*     */       } 
/*     */       
/* 165 */       if (!playerIn.capabilities.isCreativeMode) {
/* 166 */         setWaterLevel(worldIn, pos, state, i - 1);
/*     */       }
/*     */       
/* 169 */       return true;
/*     */     } 
/* 171 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWaterLevel(World worldIn, BlockPos pos, IBlockState state, int level) {
/* 179 */     worldIn.setBlockState(pos, state.withProperty((IProperty)LEVEL, Integer.valueOf(MathHelper.clamp_int(level, 0, 3))), 2);
/* 180 */     worldIn.updateComparatorOutputLevel(pos, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fillWithRain(World worldIn, BlockPos pos) {
/* 187 */     if (worldIn.rand.nextInt(20) == 1) {
/* 188 */       IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */       
/* 190 */       if (((Integer)iblockstate.getValue((IProperty)LEVEL)).intValue() < 3) {
/* 191 */         worldIn.setBlockState(pos, iblockstate.cycleProperty((IProperty)LEVEL), 2);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 200 */     return Items.cauldron;
/*     */   }
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 204 */     return Items.cauldron;
/*     */   }
/*     */   
/*     */   public boolean hasComparatorInputOverride() {
/* 208 */     return true;
/*     */   }
/*     */   
/*     */   public int getComparatorInputOverride(World worldIn, BlockPos pos) {
/* 212 */     return ((Integer)worldIn.getBlockState(pos).getValue((IProperty)LEVEL)).intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 219 */     return getDefaultState().withProperty((IProperty)LEVEL, Integer.valueOf(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 226 */     return ((Integer)state.getValue((IProperty)LEVEL)).intValue();
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 230 */     return new BlockState(this, new IProperty[] { (IProperty)LEVEL });
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockCauldron.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */