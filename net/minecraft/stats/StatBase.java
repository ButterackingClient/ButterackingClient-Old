/*     */ package net.minecraft.stats;
/*     */ 
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.NumberFormat;
/*     */ import java.util.Locale;
/*     */ import net.minecraft.event.HoverEvent;
/*     */ import net.minecraft.scoreboard.IScoreObjectiveCriteria;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.IJsonSerializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StatBase
/*     */ {
/*     */   public final String statId;
/*     */   private final IChatComponent statName;
/*     */   public boolean isIndependent;
/*     */   private final IStatType type;
/*     */   private final IScoreObjectiveCriteria objectiveCriteria;
/*     */   private Class<? extends IJsonSerializable> field_150956_d;
/*  28 */   private static NumberFormat numberFormat = NumberFormat.getIntegerInstance(Locale.US);
/*  29 */   public static IStatType simpleStatType = new IStatType() {
/*     */       public String format(int number) {
/*  31 */         return StatBase.numberFormat.format(number);
/*     */       }
/*     */     };
/*  34 */   private static DecimalFormat decimalFormat = new DecimalFormat("########0.00");
/*  35 */   public static IStatType timeStatType = new IStatType() {
/*     */       public String format(int number) {
/*  37 */         double d0 = number / 20.0D;
/*  38 */         double d1 = d0 / 60.0D;
/*  39 */         double d2 = d1 / 60.0D;
/*  40 */         double d3 = d2 / 24.0D;
/*  41 */         double d4 = d3 / 365.0D;
/*  42 */         return (d4 > 0.5D) ? (String.valueOf(StatBase.decimalFormat.format(d4)) + " y") : ((d3 > 0.5D) ? (String.valueOf(StatBase.decimalFormat.format(d3)) + " d") : ((d2 > 0.5D) ? (String.valueOf(StatBase.decimalFormat.format(d2)) + " h") : ((d1 > 0.5D) ? (String.valueOf(StatBase.decimalFormat.format(d1)) + " m") : (String.valueOf(d0) + " s"))));
/*     */       }
/*     */     };
/*  45 */   public static IStatType distanceStatType = new IStatType() {
/*     */       public String format(int number) {
/*  47 */         double d0 = number / 100.0D;
/*  48 */         double d1 = d0 / 1000.0D;
/*  49 */         return (d1 > 0.5D) ? (String.valueOf(StatBase.decimalFormat.format(d1)) + " km") : ((d0 > 0.5D) ? (String.valueOf(StatBase.decimalFormat.format(d0)) + " m") : (String.valueOf(number) + " cm"));
/*     */       }
/*     */     };
/*  52 */   public static IStatType field_111202_k = new IStatType() {
/*     */       public String format(int number) {
/*  54 */         return StatBase.decimalFormat.format(number * 0.1D);
/*     */       }
/*     */     };
/*     */   
/*     */   public StatBase(String statIdIn, IChatComponent statNameIn, IStatType typeIn) {
/*  59 */     this.statId = statIdIn;
/*  60 */     this.statName = statNameIn;
/*  61 */     this.type = typeIn;
/*  62 */     this.objectiveCriteria = (IScoreObjectiveCriteria)new ObjectiveStat(this);
/*  63 */     IScoreObjectiveCriteria.INSTANCES.put(this.objectiveCriteria.getName(), this.objectiveCriteria);
/*     */   }
/*     */   
/*     */   public StatBase(String statIdIn, IChatComponent statNameIn) {
/*  67 */     this(statIdIn, statNameIn, simpleStatType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StatBase initIndependentStat() {
/*  75 */     this.isIndependent = true;
/*  76 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StatBase registerStat() {
/*  83 */     if (StatList.oneShotStats.containsKey(this.statId)) {
/*  84 */       throw new RuntimeException("Duplicate stat id: \"" + ((StatBase)StatList.oneShotStats.get(this.statId)).statName + "\" and \"" + this.statName + "\" at id " + this.statId);
/*     */     }
/*  86 */     StatList.allStats.add(this);
/*  87 */     StatList.oneShotStats.put(this.statId, this);
/*  88 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAchievement() {
/*  96 */     return false;
/*     */   }
/*     */   
/*     */   public String format(int p_75968_1_) {
/* 100 */     return this.type.format(p_75968_1_);
/*     */   }
/*     */   
/*     */   public IChatComponent getStatName() {
/* 104 */     IChatComponent ichatcomponent = this.statName.createCopy();
/* 105 */     ichatcomponent.getChatStyle().setColor(EnumChatFormatting.GRAY);
/* 106 */     ichatcomponent.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ACHIEVEMENT, (IChatComponent)new ChatComponentText(this.statId)));
/* 107 */     return ichatcomponent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IChatComponent createChatComponent() {
/* 114 */     IChatComponent ichatcomponent = getStatName();
/* 115 */     IChatComponent ichatcomponent1 = (new ChatComponentText("[")).appendSibling(ichatcomponent).appendText("]");
/* 116 */     ichatcomponent1.setChatStyle(ichatcomponent.getChatStyle());
/* 117 */     return ichatcomponent1;
/*     */   }
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/* 121 */     if (this == p_equals_1_)
/* 122 */       return true; 
/* 123 */     if (p_equals_1_ != null && getClass() == p_equals_1_.getClass()) {
/* 124 */       StatBase statbase = (StatBase)p_equals_1_;
/* 125 */       return this.statId.equals(statbase.statId);
/*     */     } 
/* 127 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 132 */     return this.statId.hashCode();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 136 */     return "Stat{id=" + this.statId + ", nameId=" + this.statName + ", awardLocallyOnly=" + this.isIndependent + ", formatter=" + this.type + ", objectiveCriteria=" + this.objectiveCriteria + '}';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IScoreObjectiveCriteria getCriteria() {
/* 143 */     return this.objectiveCriteria;
/*     */   }
/*     */   
/*     */   public Class<? extends IJsonSerializable> func_150954_l() {
/* 147 */     return this.field_150956_d;
/*     */   }
/*     */   
/*     */   public StatBase func_150953_b(Class<? extends IJsonSerializable> p_150953_1_) {
/* 151 */     this.field_150956_d = p_150953_1_;
/* 152 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\stats\StatBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */