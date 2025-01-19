/*    */ package net.minecraft.stats;
/*    */ 
/*    */ import net.minecraft.util.IChatComponent;
/*    */ 
/*    */ public class StatBasic extends StatBase {
/*    */   public StatBasic(String statIdIn, IChatComponent statNameIn, IStatType typeIn) {
/*  7 */     super(statIdIn, statNameIn, typeIn);
/*    */   }
/*    */   
/*    */   public StatBasic(String statIdIn, IChatComponent statNameIn) {
/* 11 */     super(statIdIn, statNameIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public StatBase registerStat() {
/* 18 */     super.registerStat();
/* 19 */     StatList.generalStats.add(this);
/* 20 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\stats\StatBasic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */