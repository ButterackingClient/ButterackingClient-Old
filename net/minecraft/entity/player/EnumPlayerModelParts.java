/*    */ package net.minecraft.entity.player;
/*    */ 
/*    */ import net.minecraft.util.ChatComponentTranslation;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ 
/*    */ public enum EnumPlayerModelParts {
/*  7 */   CAPE(0, "cape"),
/*  8 */   JACKET(1, "jacket"),
/*  9 */   LEFT_SLEEVE(2, "left_sleeve"),
/* 10 */   RIGHT_SLEEVE(3, "right_sleeve"),
/* 11 */   LEFT_PANTS_LEG(4, "left_pants_leg"),
/* 12 */   RIGHT_PANTS_LEG(5, "right_pants_leg"),
/* 13 */   HAT(6, "hat");
/*    */   
/*    */   private final int partId;
/*    */   private final int partMask;
/*    */   private final String partName;
/*    */   private final IChatComponent field_179339_k;
/*    */   
/*    */   EnumPlayerModelParts(int partIdIn, String partNameIn) {
/* 21 */     this.partId = partIdIn;
/* 22 */     this.partMask = 1 << partIdIn;
/* 23 */     this.partName = partNameIn;
/* 24 */     this.field_179339_k = (IChatComponent)new ChatComponentTranslation("options.modelPart." + partNameIn, new Object[0]);
/*    */   }
/*    */   
/*    */   public int getPartMask() {
/* 28 */     return this.partMask;
/*    */   }
/*    */   
/*    */   public int getPartId() {
/* 32 */     return this.partId;
/*    */   }
/*    */   
/*    */   public String getPartName() {
/* 36 */     return this.partName;
/*    */   }
/*    */   
/*    */   public IChatComponent func_179326_d() {
/* 40 */     return this.field_179339_k;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\player\EnumPlayerModelParts.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */