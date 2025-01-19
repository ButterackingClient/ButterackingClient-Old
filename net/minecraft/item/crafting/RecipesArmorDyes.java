/*     */ package net.minecraft.item.crafting;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.passive.EntitySheep;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RecipesArmorDyes
/*     */   implements IRecipe
/*     */ {
/*     */   public boolean matches(InventoryCrafting inv, World worldIn) {
/*  20 */     ItemStack itemstack = null;
/*  21 */     List<ItemStack> list = Lists.newArrayList();
/*     */     
/*  23 */     for (int i = 0; i < inv.getSizeInventory(); i++) {
/*  24 */       ItemStack itemstack1 = inv.getStackInSlot(i);
/*     */       
/*  26 */       if (itemstack1 != null) {
/*  27 */         if (itemstack1.getItem() instanceof ItemArmor) {
/*  28 */           ItemArmor itemarmor = (ItemArmor)itemstack1.getItem();
/*     */           
/*  30 */           if (itemarmor.getArmorMaterial() != ItemArmor.ArmorMaterial.LEATHER || itemstack != null) {
/*  31 */             return false;
/*     */           }
/*     */           
/*  34 */           itemstack = itemstack1;
/*     */         } else {
/*  36 */           if (itemstack1.getItem() != Items.dye) {
/*  37 */             return false;
/*     */           }
/*     */           
/*  40 */           list.add(itemstack1);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/*  45 */     return (itemstack != null && !list.isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getCraftingResult(InventoryCrafting inv) {
/*  52 */     ItemStack itemstack = null;
/*  53 */     int[] aint = new int[3];
/*  54 */     int i = 0;
/*  55 */     int j = 0;
/*  56 */     ItemArmor itemarmor = null;
/*     */     
/*  58 */     for (int k = 0; k < inv.getSizeInventory(); k++) {
/*  59 */       ItemStack itemstack1 = inv.getStackInSlot(k);
/*     */       
/*  61 */       if (itemstack1 != null) {
/*  62 */         if (itemstack1.getItem() instanceof ItemArmor) {
/*  63 */           itemarmor = (ItemArmor)itemstack1.getItem();
/*     */           
/*  65 */           if (itemarmor.getArmorMaterial() != ItemArmor.ArmorMaterial.LEATHER || itemstack != null) {
/*  66 */             return null;
/*     */           }
/*     */           
/*  69 */           itemstack = itemstack1.copy();
/*  70 */           itemstack.stackSize = 1;
/*     */           
/*  72 */           if (itemarmor.hasColor(itemstack1)) {
/*  73 */             int l = itemarmor.getColor(itemstack);
/*  74 */             float f = (l >> 16 & 0xFF) / 255.0F;
/*  75 */             float f1 = (l >> 8 & 0xFF) / 255.0F;
/*  76 */             float f2 = (l & 0xFF) / 255.0F;
/*  77 */             i = (int)(i + Math.max(f, Math.max(f1, f2)) * 255.0F);
/*  78 */             aint[0] = (int)(aint[0] + f * 255.0F);
/*  79 */             aint[1] = (int)(aint[1] + f1 * 255.0F);
/*  80 */             aint[2] = (int)(aint[2] + f2 * 255.0F);
/*  81 */             j++;
/*     */           } 
/*     */         } else {
/*  84 */           if (itemstack1.getItem() != Items.dye) {
/*  85 */             return null;
/*     */           }
/*     */           
/*  88 */           float[] afloat = EntitySheep.getDyeRgb(EnumDyeColor.byDyeDamage(itemstack1.getMetadata()));
/*  89 */           int l1 = (int)(afloat[0] * 255.0F);
/*  90 */           int i2 = (int)(afloat[1] * 255.0F);
/*  91 */           int j2 = (int)(afloat[2] * 255.0F);
/*  92 */           i += Math.max(l1, Math.max(i2, j2));
/*  93 */           aint[0] = aint[0] + l1;
/*  94 */           aint[1] = aint[1] + i2;
/*  95 */           aint[2] = aint[2] + j2;
/*  96 */           j++;
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 101 */     if (itemarmor == null) {
/* 102 */       return null;
/*     */     }
/* 104 */     int i1 = aint[0] / j;
/* 105 */     int j1 = aint[1] / j;
/* 106 */     int k1 = aint[2] / j;
/* 107 */     float f3 = i / j;
/* 108 */     float f4 = Math.max(i1, Math.max(j1, k1));
/* 109 */     i1 = (int)(i1 * f3 / f4);
/* 110 */     j1 = (int)(j1 * f3 / f4);
/* 111 */     k1 = (int)(k1 * f3 / f4);
/* 112 */     int lvt_12_3_ = (i1 << 8) + j1;
/* 113 */     lvt_12_3_ = (lvt_12_3_ << 8) + k1;
/* 114 */     itemarmor.setColor(itemstack, lvt_12_3_);
/* 115 */     return itemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRecipeSize() {
/* 123 */     return 10;
/*     */   }
/*     */   
/*     */   public ItemStack getRecipeOutput() {
/* 127 */     return null;
/*     */   }
/*     */   
/*     */   public ItemStack[] getRemainingItems(InventoryCrafting inv) {
/* 131 */     ItemStack[] aitemstack = new ItemStack[inv.getSizeInventory()];
/*     */     
/* 133 */     for (int i = 0; i < aitemstack.length; i++) {
/* 134 */       ItemStack itemstack = inv.getStackInSlot(i);
/*     */       
/* 136 */       if (itemstack != null && itemstack.getItem().hasContainerItem()) {
/* 137 */         aitemstack[i] = new ItemStack(itemstack.getItem().getContainerItem());
/*     */       }
/*     */     } 
/*     */     
/* 141 */     return aitemstack;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\crafting\RecipesArmorDyes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */