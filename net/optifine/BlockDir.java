/*    */ package net.optifine;
/*    */ 
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ 
/*    */ public enum BlockDir {
/*  7 */   DOWN(EnumFacing.DOWN),
/*  8 */   UP(EnumFacing.UP),
/*  9 */   NORTH(EnumFacing.NORTH),
/* 10 */   SOUTH(EnumFacing.SOUTH),
/* 11 */   WEST(EnumFacing.WEST),
/* 12 */   EAST(EnumFacing.EAST),
/* 13 */   NORTH_WEST(EnumFacing.NORTH, EnumFacing.WEST),
/* 14 */   NORTH_EAST(EnumFacing.NORTH, EnumFacing.EAST),
/* 15 */   SOUTH_WEST(EnumFacing.SOUTH, EnumFacing.WEST),
/* 16 */   SOUTH_EAST(EnumFacing.SOUTH, EnumFacing.EAST),
/* 17 */   DOWN_NORTH(EnumFacing.DOWN, EnumFacing.NORTH),
/* 18 */   DOWN_SOUTH(EnumFacing.DOWN, EnumFacing.SOUTH),
/* 19 */   UP_NORTH(EnumFacing.UP, EnumFacing.NORTH),
/* 20 */   UP_SOUTH(EnumFacing.UP, EnumFacing.SOUTH),
/* 21 */   DOWN_WEST(EnumFacing.DOWN, EnumFacing.WEST),
/* 22 */   DOWN_EAST(EnumFacing.DOWN, EnumFacing.EAST),
/* 23 */   UP_WEST(EnumFacing.UP, EnumFacing.WEST),
/* 24 */   UP_EAST(EnumFacing.UP, EnumFacing.EAST);
/*    */   
/*    */   private EnumFacing facing1;
/*    */   private EnumFacing facing2;
/*    */   
/*    */   BlockDir(EnumFacing facing1) {
/* 30 */     this.facing1 = facing1;
/*    */   }
/*    */   
/*    */   BlockDir(EnumFacing facing1, EnumFacing facing2) {
/* 34 */     this.facing1 = facing1;
/* 35 */     this.facing2 = facing2;
/*    */   }
/*    */   
/*    */   public EnumFacing getFacing1() {
/* 39 */     return this.facing1;
/*    */   }
/*    */   
/*    */   public EnumFacing getFacing2() {
/* 43 */     return this.facing2;
/*    */   }
/*    */   
/*    */   BlockPos offset(BlockPos pos) {
/* 47 */     pos = pos.offset(this.facing1, 1);
/*    */     
/* 49 */     if (this.facing2 != null) {
/* 50 */       pos = pos.offset(this.facing2, 1);
/*    */     }
/*    */     
/* 53 */     return pos;
/*    */   }
/*    */   
/*    */   public int getOffsetX() {
/* 57 */     int i = this.facing1.getFrontOffsetX();
/*    */     
/* 59 */     if (this.facing2 != null) {
/* 60 */       i += this.facing2.getFrontOffsetX();
/*    */     }
/*    */     
/* 63 */     return i;
/*    */   }
/*    */   
/*    */   public int getOffsetY() {
/* 67 */     int i = this.facing1.getFrontOffsetY();
/*    */     
/* 69 */     if (this.facing2 != null) {
/* 70 */       i += this.facing2.getFrontOffsetY();
/*    */     }
/*    */     
/* 73 */     return i;
/*    */   }
/*    */   
/*    */   public int getOffsetZ() {
/* 77 */     int i = this.facing1.getFrontOffsetZ();
/*    */     
/* 79 */     if (this.facing2 != null) {
/* 80 */       i += this.facing2.getFrontOffsetZ();
/*    */     }
/*    */     
/* 83 */     return i;
/*    */   }
/*    */   
/*    */   public boolean isDouble() {
/* 87 */     return (this.facing2 != null);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\BlockDir.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */