/*    */ package net.minecraft.item;
/*    */ 
/*    */ import com.google.common.collect.Multimap;
/*    */ import java.util.Set;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.SharedMonsterAttributes;
/*    */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemTool
/*    */   extends Item
/*    */ {
/*    */   private Set<Block> effectiveBlocks;
/* 17 */   protected float efficiencyOnProperMaterial = 4.0F;
/*    */ 
/*    */ 
/*    */   
/*    */   private float damageVsEntity;
/*    */ 
/*    */ 
/*    */   
/*    */   protected Item.ToolMaterial toolMaterial;
/*    */ 
/*    */ 
/*    */   
/*    */   protected ItemTool(float attackDamage, Item.ToolMaterial material, Set<Block> effectiveBlocks) {
/* 30 */     this.toolMaterial = material;
/* 31 */     this.effectiveBlocks = effectiveBlocks;
/* 32 */     this.maxStackSize = 1;
/* 33 */     setMaxDamage(material.getMaxUses());
/* 34 */     this.efficiencyOnProperMaterial = material.getEfficiencyOnProperMaterial();
/* 35 */     this.damageVsEntity = attackDamage + material.getDamageVsEntity();
/* 36 */     setCreativeTab(CreativeTabs.tabTools);
/*    */   }
/*    */   
/*    */   public float getStrVsBlock(ItemStack stack, Block state) {
/* 40 */     return this.effectiveBlocks.contains(state) ? this.efficiencyOnProperMaterial : 1.0F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
/* 48 */     stack.damageItem(2, attacker);
/* 49 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onBlockDestroyed(ItemStack stack, World worldIn, Block blockIn, BlockPos pos, EntityLivingBase playerIn) {
/* 56 */     if (blockIn.getBlockHardness(worldIn, pos) != 0.0D) {
/* 57 */       stack.damageItem(1, playerIn);
/*    */     }
/*    */     
/* 60 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isFull3D() {
/* 67 */     return true;
/*    */   }
/*    */   
/*    */   public Item.ToolMaterial getToolMaterial() {
/* 71 */     return this.toolMaterial;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getItemEnchantability() {
/* 78 */     return this.toolMaterial.getEnchantability();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getToolMaterialName() {
/* 85 */     return this.toolMaterial.toString();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
/* 92 */     return (this.toolMaterial.getRepairItem() == repair.getItem()) ? true : super.getIsRepairable(toRepair, repair);
/*    */   }
/*    */   
/*    */   public Multimap<String, AttributeModifier> getItemAttributeModifiers() {
/* 96 */     Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers();
/* 97 */     multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(itemModifierUUID, "Tool modifier", this.damageVsEntity, 0));
/* 98 */     return multimap;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemTool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */