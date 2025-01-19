/*    */ package net.minecraft.client.renderer;
/*    */ 
/*    */ public enum EnumFaceDirection {
/*    */   private static final EnumFaceDirection[] facings;
/*    */   private final VertexInformation[] vertexInfos;
/*  6 */   DOWN(new VertexInformation[] { new VertexInformation(Constants.WEST_INDEX, Constants.DOWN_INDEX, Constants.SOUTH_INDEX, null), new VertexInformation(Constants.WEST_INDEX, Constants.DOWN_INDEX, Constants.NORTH_INDEX, null), new VertexInformation(Constants.EAST_INDEX, Constants.DOWN_INDEX, Constants.NORTH_INDEX, null), new VertexInformation(Constants.EAST_INDEX, Constants.DOWN_INDEX, Constants.SOUTH_INDEX, null) }),
/*  7 */   UP(new VertexInformation[] { new VertexInformation(Constants.WEST_INDEX, Constants.UP_INDEX, Constants.NORTH_INDEX, null), new VertexInformation(Constants.WEST_INDEX, Constants.UP_INDEX, Constants.SOUTH_INDEX, null), new VertexInformation(Constants.EAST_INDEX, Constants.UP_INDEX, Constants.SOUTH_INDEX, null), new VertexInformation(Constants.EAST_INDEX, Constants.UP_INDEX, Constants.NORTH_INDEX, null) }),
/*  8 */   NORTH(new VertexInformation[] { new VertexInformation(Constants.EAST_INDEX, Constants.UP_INDEX, Constants.NORTH_INDEX, null), new VertexInformation(Constants.EAST_INDEX, Constants.DOWN_INDEX, Constants.NORTH_INDEX, null), new VertexInformation(Constants.WEST_INDEX, Constants.DOWN_INDEX, Constants.NORTH_INDEX, null), new VertexInformation(Constants.WEST_INDEX, Constants.UP_INDEX, Constants.NORTH_INDEX, null) }),
/*  9 */   SOUTH(new VertexInformation[] { new VertexInformation(Constants.WEST_INDEX, Constants.UP_INDEX, Constants.SOUTH_INDEX, null), new VertexInformation(Constants.WEST_INDEX, Constants.DOWN_INDEX, Constants.SOUTH_INDEX, null), new VertexInformation(Constants.EAST_INDEX, Constants.DOWN_INDEX, Constants.SOUTH_INDEX, null), new VertexInformation(Constants.EAST_INDEX, Constants.UP_INDEX, Constants.SOUTH_INDEX, null) }),
/* 10 */   WEST(new VertexInformation[] { new VertexInformation(Constants.WEST_INDEX, Constants.UP_INDEX, Constants.NORTH_INDEX, null), new VertexInformation(Constants.WEST_INDEX, Constants.DOWN_INDEX, Constants.NORTH_INDEX, null), new VertexInformation(Constants.WEST_INDEX, Constants.DOWN_INDEX, Constants.SOUTH_INDEX, null), new VertexInformation(Constants.WEST_INDEX, Constants.UP_INDEX, Constants.SOUTH_INDEX, null) }),
/* 11 */   EAST(new VertexInformation[] { new VertexInformation(Constants.EAST_INDEX, Constants.UP_INDEX, Constants.SOUTH_INDEX, null), new VertexInformation(Constants.EAST_INDEX, Constants.DOWN_INDEX, Constants.SOUTH_INDEX, null), new VertexInformation(Constants.EAST_INDEX, Constants.DOWN_INDEX, Constants.NORTH_INDEX, null), new VertexInformation(Constants.EAST_INDEX, Constants.UP_INDEX, Constants.NORTH_INDEX, null) });
/*    */   public static EnumFaceDirection getFacing(EnumFacing facing) { return facings[facing.getIndex()]; }
/* 13 */   EnumFaceDirection(VertexInformation[] vertexInfosIn) { this.vertexInfos = vertexInfosIn; } static { facings = new EnumFaceDirection[6];
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 29 */     facings[Constants.DOWN_INDEX] = DOWN;
/* 30 */     facings[Constants.UP_INDEX] = UP;
/* 31 */     facings[Constants.NORTH_INDEX] = NORTH;
/* 32 */     facings[Constants.SOUTH_INDEX] = SOUTH;
/* 33 */     facings[Constants.WEST_INDEX] = WEST;
/* 34 */     facings[Constants.EAST_INDEX] = EAST; }
/*    */    public VertexInformation getVertexInformation(int index) {
/*    */     return this.vertexInfos[index];
/*    */   }
/* 38 */   public static final class Constants { public static final int SOUTH_INDEX = EnumFacing.SOUTH.getIndex();
/* 39 */     public static final int UP_INDEX = EnumFacing.UP.getIndex();
/* 40 */     public static final int EAST_INDEX = EnumFacing.EAST.getIndex();
/* 41 */     public static final int NORTH_INDEX = EnumFacing.NORTH.getIndex();
/* 42 */     public static final int DOWN_INDEX = EnumFacing.DOWN.getIndex();
/* 43 */     public static final int WEST_INDEX = EnumFacing.WEST.getIndex(); }
/*    */ 
/*    */   
/*    */   public static class VertexInformation {
/*    */     public final int xIndex;
/*    */     public final int yIndex;
/*    */     public final int zIndex;
/*    */     
/*    */     private VertexInformation(int xIndexIn, int yIndexIn, int zIndexIn) {
/* 52 */       this.xIndex = xIndexIn;
/* 53 */       this.yIndex = yIndexIn;
/* 54 */       this.zIndex = zIndexIn;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\EnumFaceDirection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */