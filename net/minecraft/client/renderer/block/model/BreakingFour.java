/*    */ package net.minecraft.client.renderer.block.model;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ 
/*    */ public class BreakingFour extends BakedQuad {
/*    */   private final TextureAtlasSprite texture;
/*    */   
/*    */   public BreakingFour(BakedQuad quad, TextureAtlasSprite textureIn) {
/* 11 */     super(Arrays.copyOf(quad.getVertexData(), (quad.getVertexData()).length), quad.tintIndex, FaceBakery.getFacingFromVertexData(quad.getVertexData()));
/* 12 */     this.texture = textureIn;
/* 13 */     remapQuad();
/* 14 */     fixVertexData();
/*    */   }
/*    */   
/*    */   private void remapQuad() {
/* 18 */     for (int i = 0; i < 4; i++) {
/* 19 */       remapVert(i);
/*    */     }
/*    */   }
/*    */   
/*    */   private void remapVert(int vertex) {
/* 24 */     int i = this.vertexData.length / 4;
/* 25 */     int j = i * vertex;
/* 26 */     float f = Float.intBitsToFloat(this.vertexData[j]);
/* 27 */     float f1 = Float.intBitsToFloat(this.vertexData[j + 1]);
/* 28 */     float f2 = Float.intBitsToFloat(this.vertexData[j + 2]);
/* 29 */     float f3 = 0.0F;
/* 30 */     float f4 = 0.0F;
/*    */     
/* 32 */     switch (this.face) {
/*    */       case null:
/* 34 */         f3 = f * 16.0F;
/* 35 */         f4 = (1.0F - f2) * 16.0F;
/*    */         break;
/*    */       
/*    */       case UP:
/* 39 */         f3 = f * 16.0F;
/* 40 */         f4 = f2 * 16.0F;
/*    */         break;
/*    */       
/*    */       case NORTH:
/* 44 */         f3 = (1.0F - f) * 16.0F;
/* 45 */         f4 = (1.0F - f1) * 16.0F;
/*    */         break;
/*    */       
/*    */       case SOUTH:
/* 49 */         f3 = f * 16.0F;
/* 50 */         f4 = (1.0F - f1) * 16.0F;
/*    */         break;
/*    */       
/*    */       case WEST:
/* 54 */         f3 = f2 * 16.0F;
/* 55 */         f4 = (1.0F - f1) * 16.0F;
/*    */         break;
/*    */       
/*    */       case EAST:
/* 59 */         f3 = (1.0F - f2) * 16.0F;
/* 60 */         f4 = (1.0F - f1) * 16.0F;
/*    */         break;
/*    */     } 
/* 63 */     this.vertexData[j + 4] = Float.floatToRawIntBits(this.texture.getInterpolatedU(f3));
/* 64 */     this.vertexData[j + 4 + 1] = Float.floatToRawIntBits(this.texture.getInterpolatedV(f4));
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\block\model\BreakingFour.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */