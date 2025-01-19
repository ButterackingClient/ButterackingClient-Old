/*    */ package net.minecraft.realms;
/*    */ 
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ 
/*    */ public class Tezzelator {
/*  6 */   public static Tessellator t = Tessellator.getInstance();
/*  7 */   public static final Tezzelator instance = new Tezzelator();
/*    */   
/*    */   public void end() {
/* 10 */     t.draw();
/*    */   }
/*    */   
/*    */   public Tezzelator vertex(double p_vertex_1_, double p_vertex_3_, double p_vertex_5_) {
/* 14 */     t.getWorldRenderer().pos(p_vertex_1_, p_vertex_3_, p_vertex_5_);
/* 15 */     return this;
/*    */   }
/*    */   
/*    */   public void color(float p_color_1_, float p_color_2_, float p_color_3_, float p_color_4_) {
/* 19 */     t.getWorldRenderer().color(p_color_1_, p_color_2_, p_color_3_, p_color_4_);
/*    */   }
/*    */   
/*    */   public void tex2(short p_tex2_1_, short p_tex2_2_) {
/* 23 */     t.getWorldRenderer().lightmap(p_tex2_1_, p_tex2_2_);
/*    */   }
/*    */   
/*    */   public void normal(float p_normal_1_, float p_normal_2_, float p_normal_3_) {
/* 27 */     t.getWorldRenderer().normal(p_normal_1_, p_normal_2_, p_normal_3_);
/*    */   }
/*    */   
/*    */   public void begin(int p_begin_1_, RealmsVertexFormat p_begin_2_) {
/* 31 */     t.getWorldRenderer().begin(p_begin_1_, p_begin_2_.getVertexFormat());
/*    */   }
/*    */   
/*    */   public void endVertex() {
/* 35 */     t.getWorldRenderer().endVertex();
/*    */   }
/*    */   
/*    */   public void offset(double p_offset_1_, double p_offset_3_, double p_offset_5_) {
/* 39 */     t.getWorldRenderer().setTranslation(p_offset_1_, p_offset_3_, p_offset_5_);
/*    */   }
/*    */   
/*    */   public RealmsBufferBuilder color(int p_color_1_, int p_color_2_, int p_color_3_, int p_color_4_) {
/* 43 */     return new RealmsBufferBuilder(t.getWorldRenderer().color(p_color_1_, p_color_2_, p_color_3_, p_color_4_));
/*    */   }
/*    */   
/*    */   public Tezzelator tex(double p_tex_1_, double p_tex_3_) {
/* 47 */     t.getWorldRenderer().tex(p_tex_1_, p_tex_3_);
/* 48 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\realms\Tezzelator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */