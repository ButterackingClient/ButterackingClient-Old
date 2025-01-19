/*     */ package net.minecraft.client.renderer.vertex;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.reflect.ReflectorClass;
/*     */ import net.optifine.reflect.ReflectorField;
/*     */ import net.optifine.shaders.SVertexFormat;
/*     */ 
/*     */ public class DefaultVertexFormats
/*     */ {
/*  11 */   public static VertexFormat BLOCK = new VertexFormat();
/*  12 */   public static VertexFormat ITEM = new VertexFormat();
/*  13 */   private static final VertexFormat BLOCK_VANILLA = BLOCK;
/*  14 */   private static final VertexFormat ITEM_VANILLA = ITEM;
/*  15 */   public static ReflectorClass Attributes = new ReflectorClass("net.minecraftforge.client.model.Attributes");
/*  16 */   public static ReflectorField Attributes_DEFAULT_BAKED_FORMAT = new ReflectorField(Attributes, "DEFAULT_BAKED_FORMAT");
/*  17 */   private static final VertexFormat FORGE_BAKED = SVertexFormat.duplicate((VertexFormat)getFieldValue(Attributes_DEFAULT_BAKED_FORMAT));
/*  18 */   public static final VertexFormat OLDMODEL_POSITION_TEX_NORMAL = new VertexFormat();
/*  19 */   public static final VertexFormat PARTICLE_POSITION_TEX_COLOR_LMAP = new VertexFormat();
/*  20 */   public static final VertexFormat POSITION = new VertexFormat();
/*  21 */   public static final VertexFormat POSITION_COLOR = new VertexFormat();
/*  22 */   public static final VertexFormat POSITION_TEX = new VertexFormat();
/*  23 */   public static final VertexFormat POSITION_NORMAL = new VertexFormat();
/*  24 */   public static final VertexFormat POSITION_TEX_COLOR = new VertexFormat();
/*  25 */   public static final VertexFormat POSITION_TEX_NORMAL = new VertexFormat();
/*  26 */   public static final VertexFormat POSITION_TEX_LMAP_COLOR = new VertexFormat();
/*  27 */   public static final VertexFormat POSITION_TEX_COLOR_NORMAL = new VertexFormat();
/*  28 */   public static final VertexFormatElement POSITION_3F = new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.POSITION, 3);
/*  29 */   public static final VertexFormatElement COLOR_4UB = new VertexFormatElement(0, VertexFormatElement.EnumType.UBYTE, VertexFormatElement.EnumUsage.COLOR, 4);
/*  30 */   public static final VertexFormatElement TEX_2F = new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.UV, 2);
/*  31 */   public static final VertexFormatElement TEX_2S = new VertexFormatElement(1, VertexFormatElement.EnumType.SHORT, VertexFormatElement.EnumUsage.UV, 2);
/*  32 */   public static final VertexFormatElement NORMAL_3B = new VertexFormatElement(0, VertexFormatElement.EnumType.BYTE, VertexFormatElement.EnumUsage.NORMAL, 3);
/*  33 */   public static final VertexFormatElement PADDING_1B = new VertexFormatElement(0, VertexFormatElement.EnumType.BYTE, VertexFormatElement.EnumUsage.PADDING, 1);
/*     */   
/*     */   public static void updateVertexFormats() {
/*  36 */     if (Config.isShaders()) {
/*  37 */       BLOCK = SVertexFormat.makeDefVertexFormatBlock();
/*  38 */       ITEM = SVertexFormat.makeDefVertexFormatItem();
/*     */       
/*  40 */       if (Attributes_DEFAULT_BAKED_FORMAT.exists()) {
/*  41 */         SVertexFormat.setDefBakedFormat((VertexFormat)Attributes_DEFAULT_BAKED_FORMAT.getValue());
/*     */       }
/*     */     } else {
/*  44 */       BLOCK = BLOCK_VANILLA;
/*  45 */       ITEM = ITEM_VANILLA;
/*     */       
/*  47 */       if (Attributes_DEFAULT_BAKED_FORMAT.exists()) {
/*  48 */         SVertexFormat.copy(FORGE_BAKED, (VertexFormat)Attributes_DEFAULT_BAKED_FORMAT.getValue());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Object getFieldValue(ReflectorField p_getFieldValue_0_) {
/*     */     try {
/*  55 */       Field field = p_getFieldValue_0_.getTargetField();
/*     */       
/*  57 */       if (field == null) {
/*  58 */         return null;
/*     */       }
/*  60 */       Object object = field.get(null);
/*  61 */       return object;
/*     */     }
/*  63 */     catch (Throwable throwable) {
/*  64 */       throwable.printStackTrace();
/*  65 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   static {
/*  70 */     BLOCK.addElement(POSITION_3F);
/*  71 */     BLOCK.addElement(COLOR_4UB);
/*  72 */     BLOCK.addElement(TEX_2F);
/*  73 */     BLOCK.addElement(TEX_2S);
/*  74 */     ITEM.addElement(POSITION_3F);
/*  75 */     ITEM.addElement(COLOR_4UB);
/*  76 */     ITEM.addElement(TEX_2F);
/*  77 */     ITEM.addElement(NORMAL_3B);
/*  78 */     ITEM.addElement(PADDING_1B);
/*  79 */     OLDMODEL_POSITION_TEX_NORMAL.addElement(POSITION_3F);
/*  80 */     OLDMODEL_POSITION_TEX_NORMAL.addElement(TEX_2F);
/*  81 */     OLDMODEL_POSITION_TEX_NORMAL.addElement(NORMAL_3B);
/*  82 */     OLDMODEL_POSITION_TEX_NORMAL.addElement(PADDING_1B);
/*  83 */     PARTICLE_POSITION_TEX_COLOR_LMAP.addElement(POSITION_3F);
/*  84 */     PARTICLE_POSITION_TEX_COLOR_LMAP.addElement(TEX_2F);
/*  85 */     PARTICLE_POSITION_TEX_COLOR_LMAP.addElement(COLOR_4UB);
/*  86 */     PARTICLE_POSITION_TEX_COLOR_LMAP.addElement(TEX_2S);
/*  87 */     POSITION.addElement(POSITION_3F);
/*  88 */     POSITION_COLOR.addElement(POSITION_3F);
/*  89 */     POSITION_COLOR.addElement(COLOR_4UB);
/*  90 */     POSITION_TEX.addElement(POSITION_3F);
/*  91 */     POSITION_TEX.addElement(TEX_2F);
/*  92 */     POSITION_NORMAL.addElement(POSITION_3F);
/*  93 */     POSITION_NORMAL.addElement(NORMAL_3B);
/*  94 */     POSITION_NORMAL.addElement(PADDING_1B);
/*  95 */     POSITION_TEX_COLOR.addElement(POSITION_3F);
/*  96 */     POSITION_TEX_COLOR.addElement(TEX_2F);
/*  97 */     POSITION_TEX_COLOR.addElement(COLOR_4UB);
/*  98 */     POSITION_TEX_NORMAL.addElement(POSITION_3F);
/*  99 */     POSITION_TEX_NORMAL.addElement(TEX_2F);
/* 100 */     POSITION_TEX_NORMAL.addElement(NORMAL_3B);
/* 101 */     POSITION_TEX_NORMAL.addElement(PADDING_1B);
/* 102 */     POSITION_TEX_LMAP_COLOR.addElement(POSITION_3F);
/* 103 */     POSITION_TEX_LMAP_COLOR.addElement(TEX_2F);
/* 104 */     POSITION_TEX_LMAP_COLOR.addElement(TEX_2S);
/* 105 */     POSITION_TEX_LMAP_COLOR.addElement(COLOR_4UB);
/* 106 */     POSITION_TEX_COLOR_NORMAL.addElement(POSITION_3F);
/* 107 */     POSITION_TEX_COLOR_NORMAL.addElement(TEX_2F);
/* 108 */     POSITION_TEX_COLOR_NORMAL.addElement(COLOR_4UB);
/* 109 */     POSITION_TEX_COLOR_NORMAL.addElement(NORMAL_3B);
/* 110 */     POSITION_TEX_COLOR_NORMAL.addElement(PADDING_1B);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\vertex\DefaultVertexFormats.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */