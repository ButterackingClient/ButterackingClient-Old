/*     */ package net.minecraft.item;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagIntArray;
/*     */ import net.minecraft.util.StatCollector;
/*     */ 
/*     */ public class ItemFireworkCharge
/*     */   extends Item {
/*     */   public int getColorFromItemStack(ItemStack stack, int renderPass) {
/*  13 */     if (renderPass != 1) {
/*  14 */       return super.getColorFromItemStack(stack, renderPass);
/*     */     }
/*  16 */     NBTBase nbtbase = getExplosionTag(stack, "Colors");
/*     */     
/*  18 */     if (!(nbtbase instanceof NBTTagIntArray)) {
/*  19 */       return 9079434;
/*     */     }
/*  21 */     NBTTagIntArray nbttagintarray = (NBTTagIntArray)nbtbase;
/*  22 */     int[] aint = nbttagintarray.getIntArray();
/*     */     
/*  24 */     if (aint.length == 1) {
/*  25 */       return aint[0];
/*     */     }
/*  27 */     int i = 0;
/*  28 */     int j = 0;
/*  29 */     int k = 0; byte b;
/*     */     int m, arrayOfInt1[];
/*  31 */     for (m = (arrayOfInt1 = aint).length, b = 0; b < m; ) { int l = arrayOfInt1[b];
/*  32 */       i += (l & 0xFF0000) >> 16;
/*  33 */       j += (l & 0xFF00) >> 8;
/*  34 */       k += (l & 0xFF) >> 0;
/*     */       b++; }
/*     */     
/*  37 */     i /= aint.length;
/*  38 */     j /= aint.length;
/*  39 */     k /= aint.length;
/*  40 */     return i << 16 | j << 8 | k;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NBTBase getExplosionTag(ItemStack stack, String key) {
/*  47 */     if (stack.hasTagCompound()) {
/*  48 */       NBTTagCompound nbttagcompound = stack.getTagCompound().getCompoundTag("Explosion");
/*     */       
/*  50 */       if (nbttagcompound != null) {
/*  51 */         return nbttagcompound.getTag(key);
/*     */       }
/*     */     } 
/*     */     
/*  55 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
/*  62 */     if (stack.hasTagCompound()) {
/*  63 */       NBTTagCompound nbttagcompound = stack.getTagCompound().getCompoundTag("Explosion");
/*     */       
/*  65 */       if (nbttagcompound != null) {
/*  66 */         addExplosionInfo(nbttagcompound, tooltip);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void addExplosionInfo(NBTTagCompound nbt, List<String> tooltip) {
/*  72 */     byte b0 = nbt.getByte("Type");
/*     */     
/*  74 */     if (b0 >= 0 && b0 <= 4) {
/*  75 */       tooltip.add(StatCollector.translateToLocal("item.fireworksCharge.type." + b0).trim());
/*     */     } else {
/*  77 */       tooltip.add(StatCollector.translateToLocal("item.fireworksCharge.type").trim());
/*     */     } 
/*     */     
/*  80 */     int[] aint = nbt.getIntArray("Colors");
/*     */     
/*  82 */     if (aint.length > 0) {
/*  83 */       boolean flag = true;
/*  84 */       String s = ""; byte b;
/*     */       int i, arrayOfInt[];
/*  86 */       for (i = (arrayOfInt = aint).length, b = 0; b < i; ) { int k = arrayOfInt[b];
/*  87 */         if (!flag) {
/*  88 */           s = String.valueOf(s) + ", ";
/*     */         }
/*     */         
/*  91 */         flag = false;
/*  92 */         boolean flag1 = false;
/*     */         
/*  94 */         for (int j = 0; j < ItemDye.dyeColors.length; j++) {
/*  95 */           if (k == ItemDye.dyeColors[j]) {
/*  96 */             flag1 = true;
/*  97 */             s = String.valueOf(s) + StatCollector.translateToLocal("item.fireworksCharge." + EnumDyeColor.byDyeDamage(j).getUnlocalizedName());
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 102 */         if (!flag1) {
/* 103 */           s = String.valueOf(s) + StatCollector.translateToLocal("item.fireworksCharge.customColor");
/*     */         }
/*     */         b++; }
/*     */       
/* 107 */       tooltip.add(s);
/*     */     } 
/*     */     
/* 110 */     int[] aint1 = nbt.getIntArray("FadeColors");
/*     */     
/* 112 */     if (aint1.length > 0) {
/* 113 */       boolean flag2 = true;
/* 114 */       String s1 = String.valueOf(StatCollector.translateToLocal("item.fireworksCharge.fadeTo")) + " "; byte b;
/*     */       int i, arrayOfInt[];
/* 116 */       for (i = (arrayOfInt = aint1).length, b = 0; b < i; ) { int l = arrayOfInt[b];
/* 117 */         if (!flag2) {
/* 118 */           s1 = String.valueOf(s1) + ", ";
/*     */         }
/*     */         
/* 121 */         flag2 = false;
/* 122 */         boolean flag5 = false;
/*     */         
/* 124 */         for (int k = 0; k < 16; k++) {
/* 125 */           if (l == ItemDye.dyeColors[k]) {
/* 126 */             flag5 = true;
/* 127 */             s1 = String.valueOf(s1) + StatCollector.translateToLocal("item.fireworksCharge." + EnumDyeColor.byDyeDamage(k).getUnlocalizedName());
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 132 */         if (!flag5) {
/* 133 */           s1 = String.valueOf(s1) + StatCollector.translateToLocal("item.fireworksCharge.customColor");
/*     */         }
/*     */         b++; }
/*     */       
/* 137 */       tooltip.add(s1);
/*     */     } 
/*     */     
/* 140 */     boolean flag3 = nbt.getBoolean("Trail");
/*     */     
/* 142 */     if (flag3) {
/* 143 */       tooltip.add(StatCollector.translateToLocal("item.fireworksCharge.trail"));
/*     */     }
/*     */     
/* 146 */     boolean flag4 = nbt.getBoolean("Flicker");
/*     */     
/* 148 */     if (flag4)
/* 149 */       tooltip.add(StatCollector.translateToLocal("item.fireworksCharge.flicker")); 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemFireworkCharge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */