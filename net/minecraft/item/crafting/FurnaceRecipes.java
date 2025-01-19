/*     */ package net.minecraft.item.crafting;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockStoneBrick;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemFishFood;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FurnaceRecipes
/*     */ {
/*  18 */   private static final FurnaceRecipes smeltingBase = new FurnaceRecipes();
/*  19 */   private Map<ItemStack, ItemStack> smeltingList = Maps.newHashMap();
/*  20 */   private Map<ItemStack, Float> experienceList = Maps.newHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FurnaceRecipes instance() {
/*  26 */     return smeltingBase;
/*     */   }
/*     */   
/*     */   private FurnaceRecipes() {
/*  30 */     addSmeltingRecipeForBlock(Blocks.iron_ore, new ItemStack(Items.iron_ingot), 0.7F);
/*  31 */     addSmeltingRecipeForBlock(Blocks.gold_ore, new ItemStack(Items.gold_ingot), 1.0F);
/*  32 */     addSmeltingRecipeForBlock(Blocks.diamond_ore, new ItemStack(Items.diamond), 1.0F);
/*  33 */     addSmeltingRecipeForBlock((Block)Blocks.sand, new ItemStack(Blocks.glass), 0.1F);
/*  34 */     addSmelting(Items.porkchop, new ItemStack(Items.cooked_porkchop), 0.35F);
/*  35 */     addSmelting(Items.beef, new ItemStack(Items.cooked_beef), 0.35F);
/*  36 */     addSmelting(Items.chicken, new ItemStack(Items.cooked_chicken), 0.35F);
/*  37 */     addSmelting(Items.rabbit, new ItemStack(Items.cooked_rabbit), 0.35F);
/*  38 */     addSmelting(Items.mutton, new ItemStack(Items.cooked_mutton), 0.35F);
/*  39 */     addSmeltingRecipeForBlock(Blocks.cobblestone, new ItemStack(Blocks.stone), 0.1F);
/*  40 */     addSmeltingRecipe(new ItemStack(Blocks.stonebrick, 1, BlockStoneBrick.DEFAULT_META), new ItemStack(Blocks.stonebrick, 1, BlockStoneBrick.CRACKED_META), 0.1F);
/*  41 */     addSmelting(Items.clay_ball, new ItemStack(Items.brick), 0.3F);
/*  42 */     addSmeltingRecipeForBlock(Blocks.clay, new ItemStack(Blocks.hardened_clay), 0.35F);
/*  43 */     addSmeltingRecipeForBlock((Block)Blocks.cactus, new ItemStack(Items.dye, 1, EnumDyeColor.GREEN.getDyeDamage()), 0.2F);
/*  44 */     addSmeltingRecipeForBlock(Blocks.log, new ItemStack(Items.coal, 1, 1), 0.15F);
/*  45 */     addSmeltingRecipeForBlock(Blocks.log2, new ItemStack(Items.coal, 1, 1), 0.15F);
/*  46 */     addSmeltingRecipeForBlock(Blocks.emerald_ore, new ItemStack(Items.emerald), 1.0F);
/*  47 */     addSmelting(Items.potato, new ItemStack(Items.baked_potato), 0.35F);
/*  48 */     addSmeltingRecipeForBlock(Blocks.netherrack, new ItemStack(Items.netherbrick), 0.1F);
/*  49 */     addSmeltingRecipe(new ItemStack(Blocks.sponge, 1, 1), new ItemStack(Blocks.sponge, 1, 0), 0.15F); byte b; int i;
/*     */     ItemFishFood.FishType[] arrayOfFishType;
/*  51 */     for (i = (arrayOfFishType = ItemFishFood.FishType.values()).length, b = 0; b < i; ) { ItemFishFood.FishType itemfishfood$fishtype = arrayOfFishType[b];
/*  52 */       if (itemfishfood$fishtype.canCook()) {
/*  53 */         addSmeltingRecipe(new ItemStack(Items.fish, 1, itemfishfood$fishtype.getMetadata()), new ItemStack(Items.cooked_fish, 1, itemfishfood$fishtype.getMetadata()), 0.35F);
/*     */       }
/*     */       b++; }
/*     */     
/*  57 */     addSmeltingRecipeForBlock(Blocks.coal_ore, new ItemStack(Items.coal), 0.1F);
/*  58 */     addSmeltingRecipeForBlock(Blocks.redstone_ore, new ItemStack(Items.redstone), 0.7F);
/*  59 */     addSmeltingRecipeForBlock(Blocks.lapis_ore, new ItemStack(Items.dye, 1, EnumDyeColor.BLUE.getDyeDamage()), 0.2F);
/*  60 */     addSmeltingRecipeForBlock(Blocks.quartz_ore, new ItemStack(Items.quartz), 0.2F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSmeltingRecipeForBlock(Block input, ItemStack stack, float experience) {
/*  67 */     addSmelting(Item.getItemFromBlock(input), stack, experience);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSmelting(Item input, ItemStack stack, float experience) {
/*  74 */     addSmeltingRecipe(new ItemStack(input, 1, 32767), stack, experience);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSmeltingRecipe(ItemStack input, ItemStack stack, float experience) {
/*  81 */     this.smeltingList.put(input, stack);
/*  82 */     this.experienceList.put(stack, Float.valueOf(experience));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getSmeltingResult(ItemStack stack) {
/*  89 */     for (Map.Entry<ItemStack, ItemStack> entry : this.smeltingList.entrySet()) {
/*  90 */       if (compareItemStacks(stack, entry.getKey())) {
/*  91 */         return entry.getValue();
/*     */       }
/*     */     } 
/*     */     
/*  95 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
/* 102 */     return (stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata()));
/*     */   }
/*     */   
/*     */   public Map<ItemStack, ItemStack> getSmeltingList() {
/* 106 */     return this.smeltingList;
/*     */   }
/*     */   
/*     */   public float getSmeltingExperience(ItemStack stack) {
/* 110 */     for (Map.Entry<ItemStack, Float> entry : this.experienceList.entrySet()) {
/* 111 */       if (compareItemStacks(stack, entry.getKey())) {
/* 112 */         return ((Float)entry.getValue()).floatValue();
/*     */       }
/*     */     } 
/*     */     
/* 116 */     return 0.0F;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\crafting\FurnaceRecipes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */