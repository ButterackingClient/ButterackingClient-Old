/*     */ package net.minecraft.item;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.BlockStandingSign;
/*     */ import net.minecraft.block.BlockWallSign;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityBanner;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemBanner extends ItemBlock {
/*     */   public ItemBanner() {
/*  22 */     super(Blocks.standing_banner);
/*  23 */     this.maxStackSize = 16;
/*  24 */     setCreativeTab(CreativeTabs.tabDecorations);
/*  25 */     setHasSubtypes(true);
/*  26 */     setMaxDamage(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  33 */     if (side == EnumFacing.DOWN)
/*  34 */       return false; 
/*  35 */     if (!worldIn.getBlockState(pos).getBlock().getMaterial().isSolid()) {
/*  36 */       return false;
/*     */     }
/*  38 */     pos = pos.offset(side);
/*     */     
/*  40 */     if (!playerIn.canPlayerEdit(pos, side, stack))
/*  41 */       return false; 
/*  42 */     if (!Blocks.standing_banner.canPlaceBlockAt(worldIn, pos))
/*  43 */       return false; 
/*  44 */     if (worldIn.isRemote) {
/*  45 */       return true;
/*     */     }
/*  47 */     if (side == EnumFacing.UP) {
/*  48 */       int i = MathHelper.floor_double(((playerIn.rotationYaw + 180.0F) * 16.0F / 360.0F) + 0.5D) & 0xF;
/*  49 */       worldIn.setBlockState(pos, Blocks.standing_banner.getDefaultState().withProperty((IProperty)BlockStandingSign.ROTATION, Integer.valueOf(i)), 3);
/*     */     } else {
/*  51 */       worldIn.setBlockState(pos, Blocks.wall_banner.getDefaultState().withProperty((IProperty)BlockWallSign.FACING, (Comparable)side), 3);
/*     */     } 
/*     */     
/*  54 */     stack.stackSize--;
/*  55 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/*  57 */     if (tileentity instanceof TileEntityBanner) {
/*  58 */       ((TileEntityBanner)tileentity).setItemValues(stack);
/*     */     }
/*     */     
/*  61 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getItemStackDisplayName(ItemStack stack) {
/*  67 */     String s = "item.banner.";
/*  68 */     EnumDyeColor enumdyecolor = getBaseColor(stack);
/*  69 */     s = String.valueOf(s) + enumdyecolor.getUnlocalizedName() + ".name";
/*  70 */     return StatCollector.translateToLocal(s);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
/*  77 */     NBTTagCompound nbttagcompound = stack.getSubCompound("BlockEntityTag", false);
/*     */     
/*  79 */     if (nbttagcompound != null && nbttagcompound.hasKey("Patterns")) {
/*  80 */       NBTTagList nbttaglist = nbttagcompound.getTagList("Patterns", 10);
/*     */       
/*  82 */       for (int i = 0; i < nbttaglist.tagCount() && i < 6; i++) {
/*  83 */         NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
/*  84 */         EnumDyeColor enumdyecolor = EnumDyeColor.byDyeDamage(nbttagcompound1.getInteger("Color"));
/*  85 */         TileEntityBanner.EnumBannerPattern tileentitybanner$enumbannerpattern = TileEntityBanner.EnumBannerPattern.getPatternByID(nbttagcompound1.getString("Pattern"));
/*     */         
/*  87 */         if (tileentitybanner$enumbannerpattern != null) {
/*  88 */           tooltip.add(StatCollector.translateToLocal("item.banner." + tileentitybanner$enumbannerpattern.getPatternName() + "." + enumdyecolor.getUnlocalizedName()));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getColorFromItemStack(ItemStack stack, int renderPass) {
/*  95 */     if (renderPass == 0) {
/*  96 */       return 16777215;
/*     */     }
/*  98 */     EnumDyeColor enumdyecolor = getBaseColor(stack);
/*  99 */     return (enumdyecolor.getMapColor()).colorValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
/*     */     byte b;
/*     */     int i;
/*     */     EnumDyeColor[] arrayOfEnumDyeColor;
/* 107 */     for (i = (arrayOfEnumDyeColor = EnumDyeColor.values()).length, b = 0; b < i; ) { EnumDyeColor enumdyecolor = arrayOfEnumDyeColor[b];
/* 108 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 109 */       TileEntityBanner.setBaseColorAndPatterns(nbttagcompound, enumdyecolor.getDyeDamage(), null);
/* 110 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 111 */       nbttagcompound1.setTag("BlockEntityTag", (NBTBase)nbttagcompound);
/* 112 */       ItemStack itemstack = new ItemStack(itemIn, 1, enumdyecolor.getDyeDamage());
/* 113 */       itemstack.setTagCompound(nbttagcompound1);
/* 114 */       subItems.add(itemstack);
/*     */       b++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CreativeTabs getCreativeTab() {
/* 122 */     return CreativeTabs.tabDecorations;
/*     */   }
/*     */   
/*     */   private EnumDyeColor getBaseColor(ItemStack stack) {
/* 126 */     NBTTagCompound nbttagcompound = stack.getSubCompound("BlockEntityTag", false);
/* 127 */     EnumDyeColor enumdyecolor = null;
/*     */     
/* 129 */     if (nbttagcompound != null && nbttagcompound.hasKey("Base")) {
/* 130 */       enumdyecolor = EnumDyeColor.byDyeDamage(nbttagcompound.getInteger("Base"));
/*     */     } else {
/* 132 */       enumdyecolor = EnumDyeColor.byDyeDamage(stack.getMetadata());
/*     */     } 
/*     */     
/* 135 */     return enumdyecolor;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemBanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */