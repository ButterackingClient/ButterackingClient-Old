/*    */ package net.optifine.config;
/*    */ 
/*    */ import net.minecraft.src.Config;
/*    */ 
/*    */ public class VillagerProfession {
/*    */   private int profession;
/*    */   private int[] careers;
/*    */   
/*    */   public VillagerProfession(int profession) {
/* 10 */     this(profession, (int[])null);
/*    */   }
/*    */   
/*    */   public VillagerProfession(int profession, int career) {
/* 14 */     this(profession, new int[] { career });
/*    */   }
/*    */   
/*    */   public VillagerProfession(int profession, int[] careers) {
/* 18 */     this.profession = profession;
/* 19 */     this.careers = careers;
/*    */   }
/*    */   
/*    */   public boolean matches(int prof, int car) {
/* 23 */     return (this.profession != prof) ? false : (!(this.careers != null && !Config.equalsOne(car, this.careers)));
/*    */   }
/*    */   
/*    */   private boolean hasCareer(int car) {
/* 27 */     return (this.careers == null) ? false : Config.equalsOne(car, this.careers);
/*    */   }
/*    */   
/*    */   public boolean addCareer(int car) {
/* 31 */     if (this.careers == null) {
/* 32 */       this.careers = new int[] { car };
/* 33 */       return true;
/* 34 */     }  if (hasCareer(car)) {
/* 35 */       return false;
/*    */     }
/* 37 */     this.careers = Config.addIntToArray(this.careers, car);
/* 38 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getProfession() {
/* 43 */     return this.profession;
/*    */   }
/*    */   
/*    */   public int[] getCareers() {
/* 47 */     return this.careers;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 51 */     return (this.careers == null) ? this.profession : (this.profession + ":" + Config.arrayToString(this.careers));
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\config\VillagerProfession.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */