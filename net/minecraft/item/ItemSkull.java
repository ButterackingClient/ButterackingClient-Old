/*     */ package net.minecraft.item;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockSkull;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTUtil;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntitySkull;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemSkull
/*     */   extends Item {
/*  25 */   private static final String[] skullTypes = new String[] { "skeleton", "wither", "zombie", "char", "creeper" };
/*     */   
/*     */   public ItemSkull() {
/*  28 */     setCreativeTab(CreativeTabs.tabDecorations);
/*  29 */     setMaxDamage(0);
/*  30 */     setHasSubtypes(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  37 */     if (side == EnumFacing.DOWN) {
/*  38 */       return false;
/*     */     }
/*  40 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*  41 */     Block block = iblockstate.getBlock();
/*  42 */     boolean flag = block.isReplaceable(worldIn, pos);
/*     */     
/*  44 */     if (!flag) {
/*  45 */       if (!worldIn.getBlockState(pos).getBlock().getMaterial().isSolid()) {
/*  46 */         return false;
/*     */       }
/*     */       
/*  49 */       pos = pos.offset(side);
/*     */     } 
/*     */     
/*  52 */     if (!playerIn.canPlayerEdit(pos, side, stack))
/*  53 */       return false; 
/*  54 */     if (!Blocks.skull.canPlaceBlockAt(worldIn, pos)) {
/*  55 */       return false;
/*     */     }
/*  57 */     if (!worldIn.isRemote) {
/*  58 */       worldIn.setBlockState(pos, Blocks.skull.getDefaultState().withProperty((IProperty)BlockSkull.FACING, (Comparable)side), 3);
/*  59 */       int i = 0;
/*     */       
/*  61 */       if (side == EnumFacing.UP) {
/*  62 */         i = MathHelper.floor_double((playerIn.rotationYaw * 16.0F / 360.0F) + 0.5D) & 0xF;
/*     */       }
/*     */       
/*  65 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/*  67 */       if (tileentity instanceof TileEntitySkull) {
/*  68 */         TileEntitySkull tileentityskull = (TileEntitySkull)tileentity;
/*     */         
/*  70 */         if (stack.getMetadata() == 3) {
/*  71 */           GameProfile gameprofile = null;
/*     */           
/*  73 */           if (stack.hasTagCompound()) {
/*  74 */             NBTTagCompound nbttagcompound = stack.getTagCompound();
/*     */             
/*  76 */             if (nbttagcompound.hasKey("SkullOwner", 10)) {
/*  77 */               gameprofile = NBTUtil.readGameProfileFromNBT(nbttagcompound.getCompoundTag("SkullOwner"));
/*  78 */             } else if (nbttagcompound.hasKey("SkullOwner", 8) && nbttagcompound.getString("SkullOwner").length() > 0) {
/*  79 */               gameprofile = new GameProfile(null, nbttagcompound.getString("SkullOwner"));
/*     */             } 
/*     */           } 
/*     */           
/*  83 */           tileentityskull.setPlayerProfile(gameprofile);
/*     */         } else {
/*  85 */           tileentityskull.setType(stack.getMetadata());
/*     */         } 
/*     */         
/*  88 */         tileentityskull.setSkullRotation(i);
/*  89 */         Blocks.skull.checkWitherSpawn(worldIn, pos, tileentityskull);
/*     */       } 
/*     */       
/*  92 */       stack.stackSize--;
/*     */     } 
/*     */     
/*  95 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
/* 104 */     for (int i = 0; i < skullTypes.length; i++) {
/* 105 */       subItems.add(new ItemStack(itemIn, 1, i));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetadata(int damage) {
/* 114 */     return damage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUnlocalizedName(ItemStack stack) {
/* 122 */     int i = stack.getMetadata();
/*     */     
/* 124 */     if (i < 0 || i >= skullTypes.length) {
/* 125 */       i = 0;
/*     */     }
/*     */     
/* 128 */     return String.valueOf(getUnlocalizedName()) + "." + skullTypes[i];
/*     */   }
/*     */   
/*     */   public String getItemStackDisplayName(ItemStack stack) {
/* 132 */     if (stack.getMetadata() == 3 && stack.hasTagCompound()) {
/* 133 */       if (stack.getTagCompound().hasKey("SkullOwner", 8)) {
/* 134 */         return StatCollector.translateToLocalFormatted("item.skull.player.name", new Object[] { stack.getTagCompound().getString("SkullOwner") });
/*     */       }
/*     */       
/* 137 */       if (stack.getTagCompound().hasKey("SkullOwner", 10)) {
/* 138 */         NBTTagCompound nbttagcompound = stack.getTagCompound().getCompoundTag("SkullOwner");
/*     */         
/* 140 */         if (nbttagcompound.hasKey("Name", 8)) {
/* 141 */           return StatCollector.translateToLocalFormatted("item.skull.player.name", new Object[] { nbttagcompound.getString("Name") });
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 146 */     return super.getItemStackDisplayName(stack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean updateItemStackNBT(NBTTagCompound nbt) {
/* 153 */     super.updateItemStackNBT(nbt);
/*     */     
/* 155 */     if (nbt.hasKey("SkullOwner", 8) && nbt.getString("SkullOwner").length() > 0) {
/* 156 */       GameProfile gameprofile = new GameProfile(null, nbt.getString("SkullOwner"));
/* 157 */       gameprofile = TileEntitySkull.updateGameprofile(gameprofile);
/* 158 */       nbt.setTag("SkullOwner", (NBTBase)NBTUtil.writeGameProfile(new NBTTagCompound(), gameprofile));
/* 159 */       return true;
/*     */     } 
/* 161 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemSkull.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */