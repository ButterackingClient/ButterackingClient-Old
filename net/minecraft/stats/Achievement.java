/*     */ package net.minecraft.stats;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.IJsonSerializable;
/*     */ import net.minecraft.util.StatCollector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Achievement
/*     */   extends StatBase
/*     */ {
/*     */   public final int displayColumn;
/*     */   public final int displayRow;
/*     */   public final Achievement parentAchievement;
/*     */   private final String achievementDescription;
/*     */   private IStatStringFormat statStringFormatter;
/*     */   public final ItemStack theItemStack;
/*     */   private boolean isSpecial;
/*     */   
/*     */   public Achievement(String statIdIn, String unlocalizedName, int column, int row, Item itemIn, Achievement parent) {
/*  51 */     this(statIdIn, unlocalizedName, column, row, new ItemStack(itemIn), parent);
/*     */   }
/*     */   
/*     */   public Achievement(String statIdIn, String unlocalizedName, int column, int row, Block blockIn, Achievement parent) {
/*  55 */     this(statIdIn, unlocalizedName, column, row, new ItemStack(blockIn), parent);
/*     */   }
/*     */   
/*     */   public Achievement(String statIdIn, String unlocalizedName, int column, int row, ItemStack stack, Achievement parent) {
/*  59 */     super(statIdIn, (IChatComponent)new ChatComponentTranslation("achievement." + unlocalizedName, new Object[0]));
/*  60 */     this.theItemStack = stack;
/*  61 */     this.achievementDescription = "achievement." + unlocalizedName + ".desc";
/*  62 */     this.displayColumn = column;
/*  63 */     this.displayRow = row;
/*     */     
/*  65 */     if (column < AchievementList.minDisplayColumn) {
/*  66 */       AchievementList.minDisplayColumn = column;
/*     */     }
/*     */     
/*  69 */     if (row < AchievementList.minDisplayRow) {
/*  70 */       AchievementList.minDisplayRow = row;
/*     */     }
/*     */     
/*  73 */     if (column > AchievementList.maxDisplayColumn) {
/*  74 */       AchievementList.maxDisplayColumn = column;
/*     */     }
/*     */     
/*  77 */     if (row > AchievementList.maxDisplayRow) {
/*  78 */       AchievementList.maxDisplayRow = row;
/*     */     }
/*     */     
/*  81 */     this.parentAchievement = parent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Achievement initIndependentStat() {
/*  89 */     this.isIndependent = true;
/*  90 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Achievement setSpecial() {
/*  98 */     this.isSpecial = true;
/*  99 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Achievement registerStat() {
/* 106 */     super.registerStat();
/* 107 */     AchievementList.achievementList.add(this);
/* 108 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAchievement() {
/* 115 */     return true;
/*     */   }
/*     */   
/*     */   public IChatComponent getStatName() {
/* 119 */     IChatComponent ichatcomponent = super.getStatName();
/* 120 */     ichatcomponent.getChatStyle().setColor(getSpecial() ? EnumChatFormatting.DARK_PURPLE : EnumChatFormatting.GREEN);
/* 121 */     return ichatcomponent;
/*     */   }
/*     */   
/*     */   public Achievement func_150953_b(Class<? extends IJsonSerializable> p_150953_1_) {
/* 125 */     return (Achievement)super.func_150953_b(p_150953_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 132 */     return (this.statStringFormatter != null) ? this.statStringFormatter.formatString(StatCollector.translateToLocal(this.achievementDescription)) : StatCollector.translateToLocal(this.achievementDescription);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Achievement setStatStringFormatter(IStatStringFormat statStringFormatterIn) {
/* 141 */     this.statStringFormatter = statStringFormatterIn;
/* 142 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getSpecial() {
/* 150 */     return this.isSpecial;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\stats\Achievement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */