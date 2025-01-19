/*     */ package net.minecraft.item;
/*     */ 
/*     */ import com.google.common.collect.Multimap;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemSword extends Item {
/*     */   private float attackDamage;
/*     */   private final Item.ToolMaterial material;
/*     */   
/*     */   public ItemSword(Item.ToolMaterial material) {
/*  20 */     this.material = material;
/*  21 */     this.maxStackSize = 1;
/*  22 */     setMaxDamage(material.getMaxUses());
/*  23 */     setCreativeTab(CreativeTabs.tabCombat);
/*  24 */     this.attackDamage = 4.0F + material.getDamageVsEntity();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getDamageVsEntity() {
/*  31 */     return this.material.getDamageVsEntity();
/*     */   }
/*     */   
/*     */   public float getStrVsBlock(ItemStack stack, Block state) {
/*  35 */     if (state == Blocks.web) {
/*  36 */       return 15.0F;
/*     */     }
/*  38 */     Material material = state.getMaterial();
/*  39 */     return (material != Material.plants && material != Material.vine && material != Material.coral && material != Material.leaves && material != Material.gourd) ? 1.0F : 1.5F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
/*  48 */     stack.damageItem(1, attacker);
/*  49 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onBlockDestroyed(ItemStack stack, World worldIn, Block blockIn, BlockPos pos, EntityLivingBase playerIn) {
/*  56 */     if (blockIn.getBlockHardness(worldIn, pos) != 0.0D) {
/*  57 */       stack.damageItem(2, playerIn);
/*     */     }
/*     */     
/*  60 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFull3D() {
/*  67 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumAction getItemUseAction(ItemStack stack) {
/*  74 */     return EnumAction.BLOCK;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxItemUseDuration(ItemStack stack) {
/*  81 */     return 72000;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
/*  88 */     playerIn.setItemInUse(itemStackIn, getMaxItemUseDuration(itemStackIn));
/*  89 */     return itemStackIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canHarvestBlock(Block blockIn) {
/*  96 */     return (blockIn == Blocks.web);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getItemEnchantability() {
/* 103 */     return this.material.getEnchantability();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getToolMaterialName() {
/* 110 */     return this.material.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
/* 117 */     return (this.material.getRepairItem() == repair.getItem()) ? true : super.getIsRepairable(toRepair, repair);
/*     */   }
/*     */   
/*     */   public Multimap<String, AttributeModifier> getItemAttributeModifiers() {
/* 121 */     Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers();
/* 122 */     multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(itemModifierUUID, "Weapon modifier", this.attackDamage, 0));
/* 123 */     return multimap;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemSword.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */