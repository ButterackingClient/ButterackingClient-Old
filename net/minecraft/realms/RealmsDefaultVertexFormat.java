/*    */ package net.minecraft.realms;
/*    */ 
/*    */ import net.minecraft.client.renderer.vertex.VertexFormat;
/*    */ import net.minecraft.client.renderer.vertex.VertexFormatElement;
/*    */ 
/*    */ public class RealmsDefaultVertexFormat {
/*  7 */   public static final RealmsVertexFormat BLOCK = new RealmsVertexFormat(new VertexFormat());
/*  8 */   public static final RealmsVertexFormat BLOCK_NORMALS = new RealmsVertexFormat(new VertexFormat());
/*  9 */   public static final RealmsVertexFormat ENTITY = new RealmsVertexFormat(new VertexFormat());
/* 10 */   public static final RealmsVertexFormat PARTICLE = new RealmsVertexFormat(new VertexFormat());
/* 11 */   public static final RealmsVertexFormat POSITION = new RealmsVertexFormat(new VertexFormat());
/* 12 */   public static final RealmsVertexFormat POSITION_COLOR = new RealmsVertexFormat(new VertexFormat());
/* 13 */   public static final RealmsVertexFormat POSITION_TEX = new RealmsVertexFormat(new VertexFormat());
/* 14 */   public static final RealmsVertexFormat POSITION_NORMAL = new RealmsVertexFormat(new VertexFormat());
/* 15 */   public static final RealmsVertexFormat POSITION_TEX_COLOR = new RealmsVertexFormat(new VertexFormat());
/* 16 */   public static final RealmsVertexFormat POSITION_TEX_NORMAL = new RealmsVertexFormat(new VertexFormat());
/* 17 */   public static final RealmsVertexFormat POSITION_TEX2_COLOR = new RealmsVertexFormat(new VertexFormat());
/* 18 */   public static final RealmsVertexFormat POSITION_TEX_COLOR_NORMAL = new RealmsVertexFormat(new VertexFormat());
/* 19 */   public static final RealmsVertexFormatElement ELEMENT_POSITION = new RealmsVertexFormatElement(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.POSITION, 3));
/* 20 */   public static final RealmsVertexFormatElement ELEMENT_COLOR = new RealmsVertexFormatElement(new VertexFormatElement(0, VertexFormatElement.EnumType.UBYTE, VertexFormatElement.EnumUsage.COLOR, 4));
/* 21 */   public static final RealmsVertexFormatElement ELEMENT_UV0 = new RealmsVertexFormatElement(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.UV, 2));
/* 22 */   public static final RealmsVertexFormatElement ELEMENT_UV1 = new RealmsVertexFormatElement(new VertexFormatElement(1, VertexFormatElement.EnumType.SHORT, VertexFormatElement.EnumUsage.UV, 2));
/* 23 */   public static final RealmsVertexFormatElement ELEMENT_NORMAL = new RealmsVertexFormatElement(new VertexFormatElement(0, VertexFormatElement.EnumType.BYTE, VertexFormatElement.EnumUsage.NORMAL, 3));
/* 24 */   public static final RealmsVertexFormatElement ELEMENT_PADDING = new RealmsVertexFormatElement(new VertexFormatElement(0, VertexFormatElement.EnumType.BYTE, VertexFormatElement.EnumUsage.PADDING, 1));
/*    */   
/*    */   static {
/* 27 */     BLOCK.addElement(ELEMENT_POSITION);
/* 28 */     BLOCK.addElement(ELEMENT_COLOR);
/* 29 */     BLOCK.addElement(ELEMENT_UV0);
/* 30 */     BLOCK.addElement(ELEMENT_UV1);
/* 31 */     BLOCK_NORMALS.addElement(ELEMENT_POSITION);
/* 32 */     BLOCK_NORMALS.addElement(ELEMENT_COLOR);
/* 33 */     BLOCK_NORMALS.addElement(ELEMENT_UV0);
/* 34 */     BLOCK_NORMALS.addElement(ELEMENT_NORMAL);
/* 35 */     BLOCK_NORMALS.addElement(ELEMENT_PADDING);
/* 36 */     ENTITY.addElement(ELEMENT_POSITION);
/* 37 */     ENTITY.addElement(ELEMENT_UV0);
/* 38 */     ENTITY.addElement(ELEMENT_NORMAL);
/* 39 */     ENTITY.addElement(ELEMENT_PADDING);
/* 40 */     PARTICLE.addElement(ELEMENT_POSITION);
/* 41 */     PARTICLE.addElement(ELEMENT_UV0);
/* 42 */     PARTICLE.addElement(ELEMENT_COLOR);
/* 43 */     PARTICLE.addElement(ELEMENT_UV1);
/* 44 */     POSITION.addElement(ELEMENT_POSITION);
/* 45 */     POSITION_COLOR.addElement(ELEMENT_POSITION);
/* 46 */     POSITION_COLOR.addElement(ELEMENT_COLOR);
/* 47 */     POSITION_TEX.addElement(ELEMENT_POSITION);
/* 48 */     POSITION_TEX.addElement(ELEMENT_UV0);
/* 49 */     POSITION_NORMAL.addElement(ELEMENT_POSITION);
/* 50 */     POSITION_NORMAL.addElement(ELEMENT_NORMAL);
/* 51 */     POSITION_NORMAL.addElement(ELEMENT_PADDING);
/* 52 */     POSITION_TEX_COLOR.addElement(ELEMENT_POSITION);
/* 53 */     POSITION_TEX_COLOR.addElement(ELEMENT_UV0);
/* 54 */     POSITION_TEX_COLOR.addElement(ELEMENT_COLOR);
/* 55 */     POSITION_TEX_NORMAL.addElement(ELEMENT_POSITION);
/* 56 */     POSITION_TEX_NORMAL.addElement(ELEMENT_UV0);
/* 57 */     POSITION_TEX_NORMAL.addElement(ELEMENT_NORMAL);
/* 58 */     POSITION_TEX_NORMAL.addElement(ELEMENT_PADDING);
/* 59 */     POSITION_TEX2_COLOR.addElement(ELEMENT_POSITION);
/* 60 */     POSITION_TEX2_COLOR.addElement(ELEMENT_UV0);
/* 61 */     POSITION_TEX2_COLOR.addElement(ELEMENT_UV1);
/* 62 */     POSITION_TEX2_COLOR.addElement(ELEMENT_COLOR);
/* 63 */     POSITION_TEX_COLOR_NORMAL.addElement(ELEMENT_POSITION);
/* 64 */     POSITION_TEX_COLOR_NORMAL.addElement(ELEMENT_UV0);
/* 65 */     POSITION_TEX_COLOR_NORMAL.addElement(ELEMENT_COLOR);
/* 66 */     POSITION_TEX_COLOR_NORMAL.addElement(ELEMENT_NORMAL);
/* 67 */     POSITION_TEX_COLOR_NORMAL.addElement(ELEMENT_PADDING);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\realms\RealmsDefaultVertexFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */