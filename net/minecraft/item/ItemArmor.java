/*     */ package net.minecraft.item;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Predicates;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.BlockDispenser;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
/*     */ import net.minecraft.dispenser.IBehaviorDispenseItem;
/*     */ import net.minecraft.dispenser.IBlockSource;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EntitySelectors;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class ItemArmor
/*     */   extends Item
/*     */ {
/*  26 */   private static final int[] maxDamageArray = new int[] { 11, 16, 15, 13 };
/*  27 */   public static final String[] EMPTY_SLOT_NAMES = new String[] { "minecraft:items/empty_armor_slot_helmet", "minecraft:items/empty_armor_slot_chestplate", "minecraft:items/empty_armor_slot_leggings", "minecraft:items/empty_armor_slot_boots" };
/*  28 */   private static final IBehaviorDispenseItem dispenserBehavior = (IBehaviorDispenseItem)new BehaviorDefaultDispenseItem() {
/*     */       protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
/*  30 */         BlockPos blockpos = source.getBlockPos().offset(BlockDispenser.getFacing(source.getBlockMetadata()));
/*  31 */         int i = blockpos.getX();
/*  32 */         int j = blockpos.getY();
/*  33 */         int k = blockpos.getZ();
/*  34 */         AxisAlignedBB axisalignedbb = new AxisAlignedBB(i, j, k, (i + 1), (j + 1), (k + 1));
/*  35 */         List<EntityLivingBase> list = source.getWorld().getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb, Predicates.and(EntitySelectors.NOT_SPECTATING, (Predicate)new EntitySelectors.ArmoredMob(stack)));
/*     */         
/*  37 */         if (list.size() > 0) {
/*  38 */           EntityLivingBase entitylivingbase = list.get(0);
/*  39 */           int l = (entitylivingbase instanceof EntityPlayer) ? 1 : 0;
/*  40 */           int i1 = EntityLiving.getArmorPosition(stack);
/*  41 */           ItemStack itemstack = stack.copy();
/*  42 */           itemstack.stackSize = 1;
/*  43 */           entitylivingbase.setCurrentItemOrArmor(i1 - l, itemstack);
/*     */           
/*  45 */           if (entitylivingbase instanceof EntityLiving) {
/*  46 */             ((EntityLiving)entitylivingbase).setEquipmentDropChance(i1, 2.0F);
/*     */           }
/*     */           
/*  49 */           stack.stackSize--;
/*  50 */           return stack;
/*     */         } 
/*  52 */         return super.dispenseStack(source, stack);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int armorType;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int damageReduceAmount;
/*     */ 
/*     */ 
/*     */   
/*     */   public final int renderIndex;
/*     */ 
/*     */ 
/*     */   
/*     */   private final ArmorMaterial material;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemArmor(ArmorMaterial material, int renderIndex, int armorType) {
/*  79 */     this.material = material;
/*  80 */     this.armorType = armorType;
/*  81 */     this.renderIndex = renderIndex;
/*  82 */     this.damageReduceAmount = material.getDamageReductionAmount(armorType);
/*  83 */     setMaxDamage(material.getDurability(armorType));
/*  84 */     this.maxStackSize = 1;
/*  85 */     setCreativeTab(CreativeTabs.tabCombat);
/*  86 */     BlockDispenser.dispenseBehaviorRegistry.putObject(this, dispenserBehavior);
/*     */   }
/*     */   
/*     */   public int getColorFromItemStack(ItemStack stack, int renderPass) {
/*  90 */     if (renderPass > 0) {
/*  91 */       return 16777215;
/*     */     }
/*  93 */     int i = getColor(stack);
/*     */     
/*  95 */     if (i < 0) {
/*  96 */       i = 16777215;
/*     */     }
/*     */     
/*  99 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getItemEnchantability() {
/* 107 */     return this.material.getEnchantability();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArmorMaterial getArmorMaterial() {
/* 114 */     return this.material;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasColor(ItemStack stack) {
/* 121 */     return (this.material != ArmorMaterial.LEATHER) ? false : (!stack.hasTagCompound() ? false : (!stack.getTagCompound().hasKey("display", 10) ? false : stack.getTagCompound().getCompoundTag("display").hasKey("color", 3)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColor(ItemStack stack) {
/* 128 */     if (this.material != ArmorMaterial.LEATHER) {
/* 129 */       return -1;
/*     */     }
/* 131 */     NBTTagCompound nbttagcompound = stack.getTagCompound();
/*     */     
/* 133 */     if (nbttagcompound != null) {
/* 134 */       NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
/*     */       
/* 136 */       if (nbttagcompound1 != null && nbttagcompound1.hasKey("color", 3)) {
/* 137 */         return nbttagcompound1.getInteger("color");
/*     */       }
/*     */     } 
/*     */     
/* 141 */     return 10511680;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeColor(ItemStack stack) {
/* 149 */     if (this.material == ArmorMaterial.LEATHER) {
/* 150 */       NBTTagCompound nbttagcompound = stack.getTagCompound();
/*     */       
/* 152 */       if (nbttagcompound != null) {
/* 153 */         NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
/*     */         
/* 155 */         if (nbttagcompound1.hasKey("color")) {
/* 156 */           nbttagcompound1.removeTag("color");
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColor(ItemStack stack, int color) {
/* 166 */     if (this.material != ArmorMaterial.LEATHER) {
/* 167 */       throw new UnsupportedOperationException("Can't dye non-leather!");
/*     */     }
/* 169 */     NBTTagCompound nbttagcompound = stack.getTagCompound();
/*     */     
/* 171 */     if (nbttagcompound == null) {
/* 172 */       nbttagcompound = new NBTTagCompound();
/* 173 */       stack.setTagCompound(nbttagcompound);
/*     */     } 
/*     */     
/* 176 */     NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
/*     */     
/* 178 */     if (!nbttagcompound.hasKey("display", 10)) {
/* 179 */       nbttagcompound.setTag("display", (NBTBase)nbttagcompound1);
/*     */     }
/*     */     
/* 182 */     nbttagcompound1.setInteger("color", color);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
/* 190 */     return (this.material.getRepairItem() == repair.getItem()) ? true : super.getIsRepairable(toRepair, repair);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
/* 197 */     int i = EntityLiving.getArmorPosition(itemStackIn) - 1;
/* 198 */     ItemStack itemstack = playerIn.getCurrentArmor(i);
/*     */     
/* 200 */     if (itemstack == null) {
/* 201 */       playerIn.setCurrentItemOrArmor(i, itemStackIn.copy());
/* 202 */       itemStackIn.stackSize = 0;
/*     */     } 
/*     */     
/* 205 */     return itemStackIn;
/*     */   }
/*     */   
/*     */   public enum ArmorMaterial {
/* 209 */     LEATHER("leather", 5, (String)new int[] { 1, 3, 2, 1 }, 15),
/* 210 */     CHAIN("chainmail", 15, (String)new int[] { 2, 5, 4, 1 }, 12),
/* 211 */     IRON("iron", 15, (String)new int[] { 2, 6, 5, 2 }, 9),
/* 212 */     GOLD("gold", 7, (String)new int[] { 2, 5, 3, 1 }, 25),
/* 213 */     DIAMOND("diamond", 33, (String)new int[] { 3, 8, 6, 3 }, 10);
/*     */     
/*     */     private final String name;
/*     */     private final int maxDamageFactor;
/*     */     private final int[] damageReductionAmountArray;
/*     */     private final int enchantability;
/*     */     
/*     */     ArmorMaterial(String name, int maxDamage, int[] reductionAmounts, int enchantability) {
/* 221 */       this.name = name;
/* 222 */       this.maxDamageFactor = maxDamage;
/* 223 */       this.damageReductionAmountArray = reductionAmounts;
/* 224 */       this.enchantability = enchantability;
/*     */     }
/*     */     
/*     */     public int getDurability(int armorType) {
/* 228 */       return ItemArmor.maxDamageArray[armorType] * this.maxDamageFactor;
/*     */     }
/*     */     
/*     */     public int getDamageReductionAmount(int armorType) {
/* 232 */       return this.damageReductionAmountArray[armorType];
/*     */     }
/*     */     
/*     */     public int getEnchantability() {
/* 236 */       return this.enchantability;
/*     */     }
/*     */     
/*     */     public Item getRepairItem() {
/* 240 */       return (this == LEATHER) ? Items.leather : ((this == CHAIN) ? Items.iron_ingot : ((this == GOLD) ? Items.gold_ingot : ((this == IRON) ? Items.iron_ingot : ((this == DIAMOND) ? Items.diamond : null))));
/*     */     }
/*     */     
/*     */     public String getName() {
/* 244 */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemArmor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */