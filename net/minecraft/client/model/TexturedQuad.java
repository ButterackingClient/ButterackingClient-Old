/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.util.Vec3;
/*    */ import net.optifine.shaders.SVertexFormat;
/*    */ 
/*    */ public class TexturedQuad {
/*    */   public PositionTextureVertex[] vertexPositions;
/*    */   public int nVertices;
/*    */   private boolean invertNormal;
/*    */   
/*    */   public TexturedQuad(PositionTextureVertex[] vertices) {
/* 16 */     this.vertexPositions = vertices;
/* 17 */     this.nVertices = vertices.length;
/*    */   }
/*    */   
/*    */   public TexturedQuad(PositionTextureVertex[] vertices, int texcoordU1, int texcoordV1, int texcoordU2, int texcoordV2, float textureWidth, float textureHeight) {
/* 21 */     this(vertices);
/* 22 */     float f = 0.0F / textureWidth;
/* 23 */     float f1 = 0.0F / textureHeight;
/* 24 */     vertices[0] = vertices[0].setTexturePosition(texcoordU2 / textureWidth - f, texcoordV1 / textureHeight + f1);
/* 25 */     vertices[1] = vertices[1].setTexturePosition(texcoordU1 / textureWidth + f, texcoordV1 / textureHeight + f1);
/* 26 */     vertices[2] = vertices[2].setTexturePosition(texcoordU1 / textureWidth + f, texcoordV2 / textureHeight - f1);
/* 27 */     vertices[3] = vertices[3].setTexturePosition(texcoordU2 / textureWidth - f, texcoordV2 / textureHeight - f1);
/*    */   }
/*    */   
/*    */   public void flipFace() {
/* 31 */     PositionTextureVertex[] apositiontexturevertex = new PositionTextureVertex[this.vertexPositions.length];
/*    */     
/* 33 */     for (int i = 0; i < this.vertexPositions.length; i++) {
/* 34 */       apositiontexturevertex[i] = this.vertexPositions[this.vertexPositions.length - i - 1];
/*    */     }
/*    */     
/* 37 */     this.vertexPositions = apositiontexturevertex;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void draw(WorldRenderer renderer, float scale) {
/* 45 */     Vec3 vec3 = (this.vertexPositions[1]).vector3D.subtractReverse((this.vertexPositions[0]).vector3D);
/* 46 */     Vec3 vec31 = (this.vertexPositions[1]).vector3D.subtractReverse((this.vertexPositions[2]).vector3D);
/* 47 */     Vec3 vec32 = vec31.crossProduct(vec3).normalize();
/* 48 */     float f = (float)vec32.xCoord;
/* 49 */     float f1 = (float)vec32.yCoord;
/* 50 */     float f2 = (float)vec32.zCoord;
/*    */     
/* 52 */     if (this.invertNormal) {
/* 53 */       f = -f;
/* 54 */       f1 = -f1;
/* 55 */       f2 = -f2;
/*    */     } 
/*    */     
/* 58 */     if (Config.isShaders()) {
/* 59 */       renderer.begin(7, SVertexFormat.defVertexFormatTextured);
/*    */     } else {
/* 61 */       renderer.begin(7, DefaultVertexFormats.OLDMODEL_POSITION_TEX_NORMAL);
/*    */     } 
/*    */     
/* 64 */     for (int i = 0; i < 4; i++) {
/* 65 */       PositionTextureVertex positiontexturevertex = this.vertexPositions[i];
/* 66 */       renderer.pos(positiontexturevertex.vector3D.xCoord * scale, positiontexturevertex.vector3D.yCoord * scale, positiontexturevertex.vector3D.zCoord * scale).tex(positiontexturevertex.texturePositionX, positiontexturevertex.texturePositionY).normal(f, f1, f2).endVertex();
/*    */     } 
/*    */     
/* 69 */     Tessellator.getInstance().draw();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\model\TexturedQuad.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */