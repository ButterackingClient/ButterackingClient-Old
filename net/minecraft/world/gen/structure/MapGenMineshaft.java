/*    */ package net.minecraft.world.gen.structure;
/*    */ 
/*    */ import java.util.Map;
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ public class MapGenMineshaft
/*    */   extends MapGenStructure
/*    */ {
/*  9 */   private double field_82673_e = 0.004D;
/*    */ 
/*    */   
/*    */   public MapGenMineshaft() {}
/*    */   
/*    */   public String getStructureName() {
/* 15 */     return "Mineshaft";
/*    */   }
/*    */   
/*    */   public MapGenMineshaft(Map<String, String> p_i2034_1_) {
/* 19 */     for (Map.Entry<String, String> entry : p_i2034_1_.entrySet()) {
/* 20 */       if (((String)entry.getKey()).equals("chance")) {
/* 21 */         this.field_82673_e = MathHelper.parseDoubleWithDefault(entry.getValue(), this.field_82673_e);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
/* 27 */     return (this.rand.nextDouble() < this.field_82673_e && this.rand.nextInt(80) < Math.max(Math.abs(chunkX), Math.abs(chunkZ)));
/*    */   }
/*    */   
/*    */   protected StructureStart getStructureStart(int chunkX, int chunkZ) {
/* 31 */     return new StructureMineshaftStart(this.worldObj, this.rand, chunkX, chunkZ);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\structure\MapGenMineshaft.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */