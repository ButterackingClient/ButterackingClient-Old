/*     */ package net.optifine.entity.model.anim;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.src.Config;
/*     */ 
/*     */ public enum ModelVariableType {
/*     */   private String name;
/*   7 */   POS_X("tx"),
/*   8 */   POS_Y("ty"),
/*   9 */   POS_Z("tz"),
/*  10 */   ANGLE_X("rx"),
/*  11 */   ANGLE_Y("ry"),
/*  12 */   ANGLE_Z("rz"),
/*  13 */   OFFSET_X("ox"),
/*  14 */   OFFSET_Y("oy"),
/*  15 */   OFFSET_Z("oz"),
/*  16 */   SCALE_X("sx"),
/*  17 */   SCALE_Y("sy"),
/*  18 */   SCALE_Z("sz"); public static ModelVariableType[] VALUES;
/*     */   
/*     */   static {
/*  21 */     VALUES = values();
/*     */   }
/*     */   ModelVariableType(String name) {
/*  24 */     this.name = name;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  28 */     return this.name;
/*     */   }
/*     */   
/*     */   public float getFloat(ModelRenderer mr) {
/*  32 */     switch (this) {
/*     */       case POS_X:
/*  34 */         return mr.rotationPointX;
/*     */       
/*     */       case POS_Y:
/*  37 */         return mr.rotationPointY;
/*     */       
/*     */       case POS_Z:
/*  40 */         return mr.rotationPointZ;
/*     */       
/*     */       case null:
/*  43 */         return mr.rotateAngleX;
/*     */       
/*     */       case ANGLE_Y:
/*  46 */         return mr.rotateAngleY;
/*     */       
/*     */       case ANGLE_Z:
/*  49 */         return mr.rotateAngleZ;
/*     */       
/*     */       case OFFSET_X:
/*  52 */         return mr.offsetX;
/*     */       
/*     */       case OFFSET_Y:
/*  55 */         return mr.offsetY;
/*     */       
/*     */       case OFFSET_Z:
/*  58 */         return mr.offsetZ;
/*     */       
/*     */       case SCALE_X:
/*  61 */         return mr.scaleX;
/*     */       
/*     */       case SCALE_Y:
/*  64 */         return mr.scaleY;
/*     */       
/*     */       case SCALE_Z:
/*  67 */         return mr.scaleZ;
/*     */     } 
/*     */     
/*  70 */     Config.warn("GetFloat not supported for: " + this);
/*  71 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFloat(ModelRenderer mr, float val) {
/*  76 */     switch (this) {
/*     */       case POS_X:
/*  78 */         mr.rotationPointX = val;
/*     */         return;
/*     */       
/*     */       case POS_Y:
/*  82 */         mr.rotationPointY = val;
/*     */         return;
/*     */       
/*     */       case POS_Z:
/*  86 */         mr.rotationPointZ = val;
/*     */         return;
/*     */       
/*     */       case null:
/*  90 */         mr.rotateAngleX = val;
/*     */         return;
/*     */       
/*     */       case ANGLE_Y:
/*  94 */         mr.rotateAngleY = val;
/*     */         return;
/*     */       
/*     */       case ANGLE_Z:
/*  98 */         mr.rotateAngleZ = val;
/*     */         return;
/*     */       
/*     */       case OFFSET_X:
/* 102 */         mr.offsetX = val;
/*     */         return;
/*     */       
/*     */       case OFFSET_Y:
/* 106 */         mr.offsetY = val;
/*     */         return;
/*     */       
/*     */       case OFFSET_Z:
/* 110 */         mr.offsetZ = val;
/*     */         return;
/*     */       
/*     */       case SCALE_X:
/* 114 */         mr.scaleX = val;
/*     */         return;
/*     */       
/*     */       case SCALE_Y:
/* 118 */         mr.scaleY = val;
/*     */         return;
/*     */       
/*     */       case SCALE_Z:
/* 122 */         mr.scaleZ = val;
/*     */         return;
/*     */     } 
/*     */     
/* 126 */     Config.warn("SetFloat not supported for: " + this);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ModelVariableType parse(String str) {
/* 131 */     for (int i = 0; i < VALUES.length; i++) {
/* 132 */       ModelVariableType modelvariabletype = VALUES[i];
/*     */       
/* 134 */       if (modelvariabletype.getName().equals(str)) {
/* 135 */         return modelvariabletype;
/*     */       }
/*     */     } 
/*     */     
/* 139 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\anim\ModelVariableType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */