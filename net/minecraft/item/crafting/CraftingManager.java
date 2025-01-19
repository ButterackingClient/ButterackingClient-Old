/*     */ package net.minecraft.item.crafting;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockPlanks;
/*     */ import net.minecraft.block.BlockStone;
/*     */ import net.minecraft.block.BlockStoneSlab;
/*     */ import net.minecraft.block.BlockStoneSlabNew;
/*     */ import net.minecraft.block.BlockWall;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CraftingManager
/*     */ {
/*  29 */   private static final CraftingManager instance = new CraftingManager();
/*  30 */   private final List<IRecipe> recipes = Lists.newArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CraftingManager getInstance() {
/*  36 */     return instance;
/*     */   }
/*     */   
/*     */   private CraftingManager() {
/*  40 */     (new RecipesTools()).addRecipes(this);
/*  41 */     (new RecipesWeapons()).addRecipes(this);
/*  42 */     (new RecipesIngots()).addRecipes(this);
/*  43 */     (new RecipesFood()).addRecipes(this);
/*  44 */     (new RecipesCrafting()).addRecipes(this);
/*  45 */     (new RecipesArmor()).addRecipes(this);
/*  46 */     (new RecipesDyes()).addRecipes(this);
/*  47 */     this.recipes.add(new RecipesArmorDyes());
/*  48 */     this.recipes.add(new RecipeBookCloning());
/*  49 */     this.recipes.add(new RecipesMapCloning());
/*  50 */     this.recipes.add(new RecipesMapExtending());
/*  51 */     this.recipes.add(new RecipeFireworks());
/*  52 */     this.recipes.add(new RecipeRepairItem());
/*  53 */     (new RecipesBanners()).addRecipes(this);
/*  54 */     addRecipe(new ItemStack(Items.paper, 3), new Object[] { "###", Character.valueOf('#'), Items.reeds });
/*  55 */     addShapelessRecipe(new ItemStack(Items.book, 1), new Object[] { Items.paper, Items.paper, Items.paper, Items.leather });
/*  56 */     addShapelessRecipe(new ItemStack(Items.writable_book, 1), new Object[] { Items.book, new ItemStack(Items.dye, 1, EnumDyeColor.BLACK.getDyeDamage()), Items.feather });
/*  57 */     addRecipe(new ItemStack(Blocks.oak_fence, 3), new Object[] { "W#W", "W#W", Character.valueOf('#'), Items.stick, Character.valueOf('W'), new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.OAK.getMetadata()) });
/*  58 */     addRecipe(new ItemStack(Blocks.birch_fence, 3), new Object[] { "W#W", "W#W", Character.valueOf('#'), Items.stick, Character.valueOf('W'), new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.BIRCH.getMetadata()) });
/*  59 */     addRecipe(new ItemStack(Blocks.spruce_fence, 3), new Object[] { "W#W", "W#W", Character.valueOf('#'), Items.stick, Character.valueOf('W'), new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.SPRUCE.getMetadata()) });
/*  60 */     addRecipe(new ItemStack(Blocks.jungle_fence, 3), new Object[] { "W#W", "W#W", Character.valueOf('#'), Items.stick, Character.valueOf('W'), new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.JUNGLE.getMetadata()) });
/*  61 */     addRecipe(new ItemStack(Blocks.acacia_fence, 3), new Object[] { "W#W", "W#W", Character.valueOf('#'), Items.stick, Character.valueOf('W'), new ItemStack(Blocks.planks, 1, 4 + BlockPlanks.EnumType.ACACIA.getMetadata() - 4) });
/*  62 */     addRecipe(new ItemStack(Blocks.dark_oak_fence, 3), new Object[] { "W#W", "W#W", Character.valueOf('#'), Items.stick, Character.valueOf('W'), new ItemStack(Blocks.planks, 1, 4 + BlockPlanks.EnumType.DARK_OAK.getMetadata() - 4) });
/*  63 */     addRecipe(new ItemStack(Blocks.cobblestone_wall, 6, BlockWall.EnumType.NORMAL.getMetadata()), new Object[] { "###", "###", Character.valueOf('#'), Blocks.cobblestone });
/*  64 */     addRecipe(new ItemStack(Blocks.cobblestone_wall, 6, BlockWall.EnumType.MOSSY.getMetadata()), new Object[] { "###", "###", Character.valueOf('#'), Blocks.mossy_cobblestone });
/*  65 */     addRecipe(new ItemStack(Blocks.nether_brick_fence, 6), new Object[] { "###", "###", Character.valueOf('#'), Blocks.nether_brick });
/*  66 */     addRecipe(new ItemStack(Blocks.oak_fence_gate, 1), new Object[] { "#W#", "#W#", Character.valueOf('#'), Items.stick, Character.valueOf('W'), new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.OAK.getMetadata()) });
/*  67 */     addRecipe(new ItemStack(Blocks.birch_fence_gate, 1), new Object[] { "#W#", "#W#", Character.valueOf('#'), Items.stick, Character.valueOf('W'), new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.BIRCH.getMetadata()) });
/*  68 */     addRecipe(new ItemStack(Blocks.spruce_fence_gate, 1), new Object[] { "#W#", "#W#", Character.valueOf('#'), Items.stick, Character.valueOf('W'), new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.SPRUCE.getMetadata()) });
/*  69 */     addRecipe(new ItemStack(Blocks.jungle_fence_gate, 1), new Object[] { "#W#", "#W#", Character.valueOf('#'), Items.stick, Character.valueOf('W'), new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.JUNGLE.getMetadata()) });
/*  70 */     addRecipe(new ItemStack(Blocks.acacia_fence_gate, 1), new Object[] { "#W#", "#W#", Character.valueOf('#'), Items.stick, Character.valueOf('W'), new ItemStack(Blocks.planks, 1, 4 + BlockPlanks.EnumType.ACACIA.getMetadata() - 4) });
/*  71 */     addRecipe(new ItemStack(Blocks.dark_oak_fence_gate, 1), new Object[] { "#W#", "#W#", Character.valueOf('#'), Items.stick, Character.valueOf('W'), new ItemStack(Blocks.planks, 1, 4 + BlockPlanks.EnumType.DARK_OAK.getMetadata() - 4) });
/*  72 */     addRecipe(new ItemStack(Blocks.jukebox, 1), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Blocks.planks, Character.valueOf('X'), Items.diamond });
/*  73 */     addRecipe(new ItemStack(Items.lead, 2), new Object[] { "~~ ", "~O ", "  ~", Character.valueOf('~'), Items.string, Character.valueOf('O'), Items.slime_ball });
/*  74 */     addRecipe(new ItemStack(Blocks.noteblock, 1), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Blocks.planks, Character.valueOf('X'), Items.redstone });
/*  75 */     addRecipe(new ItemStack(Blocks.bookshelf, 1), new Object[] { "###", "XXX", "###", Character.valueOf('#'), Blocks.planks, Character.valueOf('X'), Items.book });
/*  76 */     addRecipe(new ItemStack(Blocks.snow, 1), new Object[] { "##", "##", Character.valueOf('#'), Items.snowball });
/*  77 */     addRecipe(new ItemStack(Blocks.snow_layer, 6), new Object[] { "###", Character.valueOf('#'), Blocks.snow });
/*  78 */     addRecipe(new ItemStack(Blocks.clay, 1), new Object[] { "##", "##", Character.valueOf('#'), Items.clay_ball });
/*  79 */     addRecipe(new ItemStack(Blocks.brick_block, 1), new Object[] { "##", "##", Character.valueOf('#'), Items.brick });
/*  80 */     addRecipe(new ItemStack(Blocks.glowstone, 1), new Object[] { "##", "##", Character.valueOf('#'), Items.glowstone_dust });
/*  81 */     addRecipe(new ItemStack(Blocks.quartz_block, 1), new Object[] { "##", "##", Character.valueOf('#'), Items.quartz });
/*  82 */     addRecipe(new ItemStack(Blocks.wool, 1), new Object[] { "##", "##", Character.valueOf('#'), Items.string });
/*  83 */     addRecipe(new ItemStack(Blocks.tnt, 1), new Object[] { "X#X", "#X#", "X#X", Character.valueOf('X'), Items.gunpowder, Character.valueOf('#'), Blocks.sand });
/*  84 */     addRecipe(new ItemStack((Block)Blocks.stone_slab, 6, BlockStoneSlab.EnumType.COBBLESTONE.getMetadata()), new Object[] { "###", Character.valueOf('#'), Blocks.cobblestone });
/*  85 */     addRecipe(new ItemStack((Block)Blocks.stone_slab, 6, BlockStoneSlab.EnumType.STONE.getMetadata()), new Object[] { "###", Character.valueOf('#'), new ItemStack(Blocks.stone, BlockStone.EnumType.STONE.getMetadata()) });
/*  86 */     addRecipe(new ItemStack((Block)Blocks.stone_slab, 6, BlockStoneSlab.EnumType.SAND.getMetadata()), new Object[] { "###", Character.valueOf('#'), Blocks.sandstone });
/*  87 */     addRecipe(new ItemStack((Block)Blocks.stone_slab, 6, BlockStoneSlab.EnumType.BRICK.getMetadata()), new Object[] { "###", Character.valueOf('#'), Blocks.brick_block });
/*  88 */     addRecipe(new ItemStack((Block)Blocks.stone_slab, 6, BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata()), new Object[] { "###", Character.valueOf('#'), Blocks.stonebrick });
/*  89 */     addRecipe(new ItemStack((Block)Blocks.stone_slab, 6, BlockStoneSlab.EnumType.NETHERBRICK.getMetadata()), new Object[] { "###", Character.valueOf('#'), Blocks.nether_brick });
/*  90 */     addRecipe(new ItemStack((Block)Blocks.stone_slab, 6, BlockStoneSlab.EnumType.QUARTZ.getMetadata()), new Object[] { "###", Character.valueOf('#'), Blocks.quartz_block });
/*  91 */     addRecipe(new ItemStack((Block)Blocks.stone_slab2, 6, BlockStoneSlabNew.EnumType.RED_SANDSTONE.getMetadata()), new Object[] { "###", Character.valueOf('#'), Blocks.red_sandstone });
/*  92 */     addRecipe(new ItemStack((Block)Blocks.wooden_slab, 6, 0), new Object[] { "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.OAK.getMetadata()) });
/*  93 */     addRecipe(new ItemStack((Block)Blocks.wooden_slab, 6, BlockPlanks.EnumType.BIRCH.getMetadata()), new Object[] { "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.BIRCH.getMetadata()) });
/*  94 */     addRecipe(new ItemStack((Block)Blocks.wooden_slab, 6, BlockPlanks.EnumType.SPRUCE.getMetadata()), new Object[] { "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.SPRUCE.getMetadata()) });
/*  95 */     addRecipe(new ItemStack((Block)Blocks.wooden_slab, 6, BlockPlanks.EnumType.JUNGLE.getMetadata()), new Object[] { "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.JUNGLE.getMetadata()) });
/*  96 */     addRecipe(new ItemStack((Block)Blocks.wooden_slab, 6, 4 + BlockPlanks.EnumType.ACACIA.getMetadata() - 4), new Object[] { "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, 4 + BlockPlanks.EnumType.ACACIA.getMetadata() - 4) });
/*  97 */     addRecipe(new ItemStack((Block)Blocks.wooden_slab, 6, 4 + BlockPlanks.EnumType.DARK_OAK.getMetadata() - 4), new Object[] { "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, 4 + BlockPlanks.EnumType.DARK_OAK.getMetadata() - 4) });
/*  98 */     addRecipe(new ItemStack(Blocks.ladder, 3), new Object[] { "# #", "###", "# #", Character.valueOf('#'), Items.stick });
/*  99 */     addRecipe(new ItemStack(Items.oak_door, 3), new Object[] { "##", "##", "##", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.OAK.getMetadata()) });
/* 100 */     addRecipe(new ItemStack(Items.spruce_door, 3), new Object[] { "##", "##", "##", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.SPRUCE.getMetadata()) });
/* 101 */     addRecipe(new ItemStack(Items.birch_door, 3), new Object[] { "##", "##", "##", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.BIRCH.getMetadata()) });
/* 102 */     addRecipe(new ItemStack(Items.jungle_door, 3), new Object[] { "##", "##", "##", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.JUNGLE.getMetadata()) });
/* 103 */     addRecipe(new ItemStack(Items.acacia_door, 3), new Object[] { "##", "##", "##", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.ACACIA.getMetadata()) });
/* 104 */     addRecipe(new ItemStack(Items.dark_oak_door, 3), new Object[] { "##", "##", "##", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.DARK_OAK.getMetadata()) });
/* 105 */     addRecipe(new ItemStack(Blocks.trapdoor, 2), new Object[] { "###", "###", Character.valueOf('#'), Blocks.planks });
/* 106 */     addRecipe(new ItemStack(Items.iron_door, 3), new Object[] { "##", "##", "##", Character.valueOf('#'), Items.iron_ingot });
/* 107 */     addRecipe(new ItemStack(Blocks.iron_trapdoor, 1), new Object[] { "##", "##", Character.valueOf('#'), Items.iron_ingot });
/* 108 */     addRecipe(new ItemStack(Items.sign, 3), new Object[] { "###", "###", " X ", Character.valueOf('#'), Blocks.planks, Character.valueOf('X'), Items.stick });
/* 109 */     addRecipe(new ItemStack(Items.cake, 1), new Object[] { "AAA", "BEB", "CCC", Character.valueOf('A'), Items.milk_bucket, Character.valueOf('B'), Items.sugar, Character.valueOf('C'), Items.wheat, Character.valueOf('E'), Items.egg });
/* 110 */     addRecipe(new ItemStack(Items.sugar, 1), new Object[] { "#", Character.valueOf('#'), Items.reeds });
/* 111 */     addRecipe(new ItemStack(Blocks.planks, 4, BlockPlanks.EnumType.OAK.getMetadata()), new Object[] { "#", Character.valueOf('#'), new ItemStack(Blocks.log, 1, BlockPlanks.EnumType.OAK.getMetadata()) });
/* 112 */     addRecipe(new ItemStack(Blocks.planks, 4, BlockPlanks.EnumType.SPRUCE.getMetadata()), new Object[] { "#", Character.valueOf('#'), new ItemStack(Blocks.log, 1, BlockPlanks.EnumType.SPRUCE.getMetadata()) });
/* 113 */     addRecipe(new ItemStack(Blocks.planks, 4, BlockPlanks.EnumType.BIRCH.getMetadata()), new Object[] { "#", Character.valueOf('#'), new ItemStack(Blocks.log, 1, BlockPlanks.EnumType.BIRCH.getMetadata()) });
/* 114 */     addRecipe(new ItemStack(Blocks.planks, 4, BlockPlanks.EnumType.JUNGLE.getMetadata()), new Object[] { "#", Character.valueOf('#'), new ItemStack(Blocks.log, 1, BlockPlanks.EnumType.JUNGLE.getMetadata()) });
/* 115 */     addRecipe(new ItemStack(Blocks.planks, 4, 4 + BlockPlanks.EnumType.ACACIA.getMetadata() - 4), new Object[] { "#", Character.valueOf('#'), new ItemStack(Blocks.log2, 1, BlockPlanks.EnumType.ACACIA.getMetadata() - 4) });
/* 116 */     addRecipe(new ItemStack(Blocks.planks, 4, 4 + BlockPlanks.EnumType.DARK_OAK.getMetadata() - 4), new Object[] { "#", Character.valueOf('#'), new ItemStack(Blocks.log2, 1, BlockPlanks.EnumType.DARK_OAK.getMetadata() - 4) });
/* 117 */     addRecipe(new ItemStack(Items.stick, 4), new Object[] { "#", "#", Character.valueOf('#'), Blocks.planks });
/* 118 */     addRecipe(new ItemStack(Blocks.torch, 4), new Object[] { "X", "#", Character.valueOf('X'), Items.coal, Character.valueOf('#'), Items.stick });
/* 119 */     addRecipe(new ItemStack(Blocks.torch, 4), new Object[] { "X", "#", Character.valueOf('X'), new ItemStack(Items.coal, 1, 1), Character.valueOf('#'), Items.stick });
/* 120 */     addRecipe(new ItemStack(Items.bowl, 4), new Object[] { "# #", " # ", Character.valueOf('#'), Blocks.planks });
/* 121 */     addRecipe(new ItemStack(Items.glass_bottle, 3), new Object[] { "# #", " # ", Character.valueOf('#'), Blocks.glass });
/* 122 */     addRecipe(new ItemStack(Blocks.rail, 16), new Object[] { "X X", "X#X", "X X", Character.valueOf('X'), Items.iron_ingot, Character.valueOf('#'), Items.stick });
/* 123 */     addRecipe(new ItemStack(Blocks.golden_rail, 6), new Object[] { "X X", "X#X", "XRX", Character.valueOf('X'), Items.gold_ingot, Character.valueOf('R'), Items.redstone, Character.valueOf('#'), Items.stick });
/* 124 */     addRecipe(new ItemStack(Blocks.activator_rail, 6), new Object[] { "XSX", "X#X", "XSX", Character.valueOf('X'), Items.iron_ingot, Character.valueOf('#'), Blocks.redstone_torch, Character.valueOf('S'), Items.stick });
/* 125 */     addRecipe(new ItemStack(Blocks.detector_rail, 6), new Object[] { "X X", "X#X", "XRX", Character.valueOf('X'), Items.iron_ingot, Character.valueOf('R'), Items.redstone, Character.valueOf('#'), Blocks.stone_pressure_plate });
/* 126 */     addRecipe(new ItemStack(Items.minecart, 1), new Object[] { "# #", "###", Character.valueOf('#'), Items.iron_ingot });
/* 127 */     addRecipe(new ItemStack(Items.cauldron, 1), new Object[] { "# #", "# #", "###", Character.valueOf('#'), Items.iron_ingot });
/* 128 */     addRecipe(new ItemStack(Items.brewing_stand, 1), new Object[] { " B ", "###", Character.valueOf('#'), Blocks.cobblestone, Character.valueOf('B'), Items.blaze_rod });
/* 129 */     addRecipe(new ItemStack(Blocks.lit_pumpkin, 1), new Object[] { "A", "B", Character.valueOf('A'), Blocks.pumpkin, Character.valueOf('B'), Blocks.torch });
/* 130 */     addRecipe(new ItemStack(Items.chest_minecart, 1), new Object[] { "A", "B", Character.valueOf('A'), Blocks.chest, Character.valueOf('B'), Items.minecart });
/* 131 */     addRecipe(new ItemStack(Items.furnace_minecart, 1), new Object[] { "A", "B", Character.valueOf('A'), Blocks.furnace, Character.valueOf('B'), Items.minecart });
/* 132 */     addRecipe(new ItemStack(Items.tnt_minecart, 1), new Object[] { "A", "B", Character.valueOf('A'), Blocks.tnt, Character.valueOf('B'), Items.minecart });
/* 133 */     addRecipe(new ItemStack(Items.hopper_minecart, 1), new Object[] { "A", "B", Character.valueOf('A'), Blocks.hopper, Character.valueOf('B'), Items.minecart });
/* 134 */     addRecipe(new ItemStack(Items.boat, 1), new Object[] { "# #", "###", Character.valueOf('#'), Blocks.planks });
/* 135 */     addRecipe(new ItemStack(Items.bucket, 1), new Object[] { "# #", " # ", Character.valueOf('#'), Items.iron_ingot });
/* 136 */     addRecipe(new ItemStack(Items.flower_pot, 1), new Object[] { "# #", " # ", Character.valueOf('#'), Items.brick });
/* 137 */     addShapelessRecipe(new ItemStack(Items.flint_and_steel, 1), new Object[] { new ItemStack(Items.iron_ingot, 1), new ItemStack(Items.flint, 1) });
/* 138 */     addRecipe(new ItemStack(Items.bread, 1), new Object[] { "###", Character.valueOf('#'), Items.wheat });
/* 139 */     addRecipe(new ItemStack(Blocks.oak_stairs, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.OAK.getMetadata()) });
/* 140 */     addRecipe(new ItemStack(Blocks.birch_stairs, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.BIRCH.getMetadata()) });
/* 141 */     addRecipe(new ItemStack(Blocks.spruce_stairs, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.SPRUCE.getMetadata()) });
/* 142 */     addRecipe(new ItemStack(Blocks.jungle_stairs, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.JUNGLE.getMetadata()) });
/* 143 */     addRecipe(new ItemStack(Blocks.acacia_stairs, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, 4 + BlockPlanks.EnumType.ACACIA.getMetadata() - 4) });
/* 144 */     addRecipe(new ItemStack(Blocks.dark_oak_stairs, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, 4 + BlockPlanks.EnumType.DARK_OAK.getMetadata() - 4) });
/* 145 */     addRecipe(new ItemStack((Item)Items.fishing_rod, 1), new Object[] { "  #", " #X", "# X", Character.valueOf('#'), Items.stick, Character.valueOf('X'), Items.string });
/* 146 */     addRecipe(new ItemStack(Items.carrot_on_a_stick, 1), new Object[] { "# ", " X", Character.valueOf('#'), Items.fishing_rod, Character.valueOf('X'), Items.carrot });
/* 147 */     addRecipe(new ItemStack(Blocks.stone_stairs, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), Blocks.cobblestone });
/* 148 */     addRecipe(new ItemStack(Blocks.brick_stairs, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), Blocks.brick_block });
/* 149 */     addRecipe(new ItemStack(Blocks.stone_brick_stairs, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), Blocks.stonebrick });
/* 150 */     addRecipe(new ItemStack(Blocks.nether_brick_stairs, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), Blocks.nether_brick });
/* 151 */     addRecipe(new ItemStack(Blocks.sandstone_stairs, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), Blocks.sandstone });
/* 152 */     addRecipe(new ItemStack(Blocks.red_sandstone_stairs, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), Blocks.red_sandstone });
/* 153 */     addRecipe(new ItemStack(Blocks.quartz_stairs, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), Blocks.quartz_block });
/* 154 */     addRecipe(new ItemStack(Items.painting, 1), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Items.stick, Character.valueOf('X'), Blocks.wool });
/* 155 */     addRecipe(new ItemStack(Items.item_frame, 1), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Items.stick, Character.valueOf('X'), Items.leather });
/* 156 */     addRecipe(new ItemStack(Items.golden_apple, 1, 0), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Items.gold_ingot, Character.valueOf('X'), Items.apple });
/* 157 */     addRecipe(new ItemStack(Items.golden_apple, 1, 1), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Blocks.gold_block, Character.valueOf('X'), Items.apple });
/* 158 */     addRecipe(new ItemStack(Items.golden_carrot, 1, 0), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Items.gold_nugget, Character.valueOf('X'), Items.carrot });
/* 159 */     addRecipe(new ItemStack(Items.speckled_melon, 1), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Items.gold_nugget, Character.valueOf('X'), Items.melon });
/* 160 */     addRecipe(new ItemStack(Blocks.lever, 1), new Object[] { "X", "#", Character.valueOf('#'), Blocks.cobblestone, Character.valueOf('X'), Items.stick });
/* 161 */     addRecipe(new ItemStack((Block)Blocks.tripwire_hook, 2), new Object[] { "I", "S", "#", Character.valueOf('#'), Blocks.planks, Character.valueOf('S'), Items.stick, Character.valueOf('I'), Items.iron_ingot });
/* 162 */     addRecipe(new ItemStack(Blocks.redstone_torch, 1), new Object[] { "X", "#", Character.valueOf('#'), Items.stick, Character.valueOf('X'), Items.redstone });
/* 163 */     addRecipe(new ItemStack(Items.repeater, 1), new Object[] { "#X#", "III", Character.valueOf('#'), Blocks.redstone_torch, Character.valueOf('X'), Items.redstone, Character.valueOf('I'), new ItemStack(Blocks.stone, 1, BlockStone.EnumType.STONE.getMetadata()) });
/* 164 */     addRecipe(new ItemStack(Items.comparator, 1), new Object[] { " # ", "#X#", "III", Character.valueOf('#'), Blocks.redstone_torch, Character.valueOf('X'), Items.quartz, Character.valueOf('I'), new ItemStack(Blocks.stone, 1, BlockStone.EnumType.STONE.getMetadata()) });
/* 165 */     addRecipe(new ItemStack(Items.clock, 1), new Object[] { " # ", "#X#", " # ", Character.valueOf('#'), Items.gold_ingot, Character.valueOf('X'), Items.redstone });
/* 166 */     addRecipe(new ItemStack(Items.compass, 1), new Object[] { " # ", "#X#", " # ", Character.valueOf('#'), Items.iron_ingot, Character.valueOf('X'), Items.redstone });
/* 167 */     addRecipe(new ItemStack((Item)Items.map, 1), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Items.paper, Character.valueOf('X'), Items.compass });
/* 168 */     addRecipe(new ItemStack(Blocks.stone_button, 1), new Object[] { "#", Character.valueOf('#'), new ItemStack(Blocks.stone, 1, BlockStone.EnumType.STONE.getMetadata()) });
/* 169 */     addRecipe(new ItemStack(Blocks.wooden_button, 1), new Object[] { "#", Character.valueOf('#'), Blocks.planks });
/* 170 */     addRecipe(new ItemStack(Blocks.stone_pressure_plate, 1), new Object[] { "##", Character.valueOf('#'), new ItemStack(Blocks.stone, 1, BlockStone.EnumType.STONE.getMetadata()) });
/* 171 */     addRecipe(new ItemStack(Blocks.wooden_pressure_plate, 1), new Object[] { "##", Character.valueOf('#'), Blocks.planks });
/* 172 */     addRecipe(new ItemStack(Blocks.heavy_weighted_pressure_plate, 1), new Object[] { "##", Character.valueOf('#'), Items.iron_ingot });
/* 173 */     addRecipe(new ItemStack(Blocks.light_weighted_pressure_plate, 1), new Object[] { "##", Character.valueOf('#'), Items.gold_ingot });
/* 174 */     addRecipe(new ItemStack(Blocks.dispenser, 1), new Object[] { "###", "#X#", "#R#", Character.valueOf('#'), Blocks.cobblestone, Character.valueOf('X'), Items.bow, Character.valueOf('R'), Items.redstone });
/* 175 */     addRecipe(new ItemStack(Blocks.dropper, 1), new Object[] { "###", "# #", "#R#", Character.valueOf('#'), Blocks.cobblestone, Character.valueOf('R'), Items.redstone });
/* 176 */     addRecipe(new ItemStack((Block)Blocks.piston, 1), new Object[] { "TTT", "#X#", "#R#", Character.valueOf('#'), Blocks.cobblestone, Character.valueOf('X'), Items.iron_ingot, Character.valueOf('R'), Items.redstone, Character.valueOf('T'), Blocks.planks });
/* 177 */     addRecipe(new ItemStack((Block)Blocks.sticky_piston, 1), new Object[] { "S", "P", Character.valueOf('S'), Items.slime_ball, Character.valueOf('P'), Blocks.piston });
/* 178 */     addRecipe(new ItemStack(Items.bed, 1), new Object[] { "###", "XXX", Character.valueOf('#'), Blocks.wool, Character.valueOf('X'), Blocks.planks });
/* 179 */     addRecipe(new ItemStack(Blocks.enchanting_table, 1), new Object[] { " B ", "D#D", "###", Character.valueOf('#'), Blocks.obsidian, Character.valueOf('B'), Items.book, Character.valueOf('D'), Items.diamond });
/* 180 */     addRecipe(new ItemStack(Blocks.anvil, 1), new Object[] { "III", " i ", "iii", Character.valueOf('I'), Blocks.iron_block, Character.valueOf('i'), Items.iron_ingot });
/* 181 */     addRecipe(new ItemStack(Items.leather), new Object[] { "##", "##", Character.valueOf('#'), Items.rabbit_hide });
/* 182 */     addShapelessRecipe(new ItemStack(Items.ender_eye, 1), new Object[] { Items.ender_pearl, Items.blaze_powder });
/* 183 */     addShapelessRecipe(new ItemStack(Items.fire_charge, 3), new Object[] { Items.gunpowder, Items.blaze_powder, Items.coal });
/* 184 */     addShapelessRecipe(new ItemStack(Items.fire_charge, 3), new Object[] { Items.gunpowder, Items.blaze_powder, new ItemStack(Items.coal, 1, 1) });
/* 185 */     addRecipe(new ItemStack((Block)Blocks.daylight_detector), new Object[] { "GGG", "QQQ", "WWW", Character.valueOf('G'), Blocks.glass, Character.valueOf('Q'), Items.quartz, Character.valueOf('W'), Blocks.wooden_slab });
/* 186 */     addRecipe(new ItemStack((Block)Blocks.hopper), new Object[] { "I I", "ICI", " I ", Character.valueOf('I'), Items.iron_ingot, Character.valueOf('C'), Blocks.chest });
/* 187 */     addRecipe(new ItemStack((Item)Items.armor_stand, 1), new Object[] { "///", " / ", "/_/", Character.valueOf('/'), Items.stick, Character.valueOf('_'), new ItemStack((Block)Blocks.stone_slab, 1, BlockStoneSlab.EnumType.STONE.getMetadata()) });
/* 188 */     Collections.sort(this.recipes, new Comparator<IRecipe>() {
/*     */           public int compare(IRecipe p_compare_1_, IRecipe p_compare_2_) {
/* 190 */             return (p_compare_1_ instanceof ShapelessRecipes && p_compare_2_ instanceof ShapedRecipes) ? 1 : ((p_compare_2_ instanceof ShapelessRecipes && p_compare_1_ instanceof ShapedRecipes) ? -1 : ((p_compare_2_.getRecipeSize() < p_compare_1_.getRecipeSize()) ? -1 : ((p_compare_2_.getRecipeSize() > p_compare_1_.getRecipeSize()) ? 1 : 0)));
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShapedRecipes addRecipe(ItemStack stack, Object... recipeComponents) {
/* 199 */     String s = "";
/* 200 */     int i = 0;
/* 201 */     int j = 0;
/* 202 */     int k = 0;
/*     */     
/* 204 */     if (recipeComponents[i] instanceof String[]) {
/* 205 */       String[] astring = (String[])recipeComponents[i++];
/*     */       
/* 207 */       for (int l = 0; l < astring.length; l++) {
/* 208 */         String s2 = astring[l];
/* 209 */         k++;
/* 210 */         j = s2.length();
/* 211 */         s = String.valueOf(s) + s2;
/*     */       } 
/*     */     } else {
/* 214 */       while (recipeComponents[i] instanceof String) {
/* 215 */         String s1 = (String)recipeComponents[i++];
/* 216 */         k++;
/* 217 */         j = s1.length();
/* 218 */         s = String.valueOf(s) + s1;
/*     */       } 
/*     */     } 
/*     */     
/*     */     Map<Character, ItemStack> map;
/*     */     
/* 224 */     for (map = Maps.newHashMap(); i < recipeComponents.length; i += 2) {
/* 225 */       Character character = (Character)recipeComponents[i];
/* 226 */       ItemStack itemstack = null;
/*     */       
/* 228 */       if (recipeComponents[i + 1] instanceof Item) {
/* 229 */         itemstack = new ItemStack((Item)recipeComponents[i + 1]);
/* 230 */       } else if (recipeComponents[i + 1] instanceof Block) {
/* 231 */         itemstack = new ItemStack((Block)recipeComponents[i + 1], 1, 32767);
/* 232 */       } else if (recipeComponents[i + 1] instanceof ItemStack) {
/* 233 */         itemstack = (ItemStack)recipeComponents[i + 1];
/*     */       } 
/*     */       
/* 236 */       map.put(character, itemstack);
/*     */     } 
/*     */     
/* 239 */     ItemStack[] aitemstack = new ItemStack[j * k];
/*     */     
/* 241 */     for (int i1 = 0; i1 < j * k; i1++) {
/* 242 */       char c0 = s.charAt(i1);
/*     */       
/* 244 */       if (map.containsKey(Character.valueOf(c0))) {
/* 245 */         aitemstack[i1] = ((ItemStack)map.get(Character.valueOf(c0))).copy();
/*     */       } else {
/* 247 */         aitemstack[i1] = null;
/*     */       } 
/*     */     } 
/*     */     
/* 251 */     ShapedRecipes shapedrecipes = new ShapedRecipes(j, k, aitemstack, stack);
/* 252 */     this.recipes.add(shapedrecipes);
/* 253 */     return shapedrecipes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addShapelessRecipe(ItemStack stack, Object... recipeComponents) {
/* 260 */     List<ItemStack> list = Lists.newArrayList(); byte b; int i;
/*     */     Object[] arrayOfObject;
/* 262 */     for (i = (arrayOfObject = recipeComponents).length, b = 0; b < i; ) { Object object = arrayOfObject[b];
/* 263 */       if (object instanceof ItemStack) {
/* 264 */         list.add(((ItemStack)object).copy());
/* 265 */       } else if (object instanceof Item) {
/* 266 */         list.add(new ItemStack((Item)object));
/*     */       } else {
/* 268 */         if (!(object instanceof Block)) {
/* 269 */           throw new IllegalArgumentException("Invalid shapeless recipe: unknown type " + object.getClass().getName() + "!");
/*     */         }
/*     */         
/* 272 */         list.add(new ItemStack((Block)object));
/*     */       } 
/*     */       b++; }
/*     */     
/* 276 */     this.recipes.add(new ShapelessRecipes(stack, list));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addRecipe(IRecipe recipe) {
/* 283 */     this.recipes.add(recipe);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack findMatchingRecipe(InventoryCrafting p_82787_1_, World worldIn) {
/* 290 */     for (IRecipe irecipe : this.recipes) {
/* 291 */       if (irecipe.matches(p_82787_1_, worldIn)) {
/* 292 */         return irecipe.getCraftingResult(p_82787_1_);
/*     */       }
/*     */     } 
/*     */     
/* 296 */     return null;
/*     */   }
/*     */   
/*     */   public ItemStack[] func_180303_b(InventoryCrafting p_180303_1_, World worldIn) {
/* 300 */     for (IRecipe irecipe : this.recipes) {
/* 301 */       if (irecipe.matches(p_180303_1_, worldIn)) {
/* 302 */         return irecipe.getRemainingItems(p_180303_1_);
/*     */       }
/*     */     } 
/*     */     
/* 306 */     ItemStack[] aitemstack = new ItemStack[p_180303_1_.getSizeInventory()];
/*     */     
/* 308 */     for (int i = 0; i < aitemstack.length; i++) {
/* 309 */       aitemstack[i] = p_180303_1_.getStackInSlot(i);
/*     */     }
/*     */     
/* 312 */     return aitemstack;
/*     */   }
/*     */   
/*     */   public List<IRecipe> getRecipeList() {
/* 316 */     return this.recipes;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\crafting\CraftingManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */