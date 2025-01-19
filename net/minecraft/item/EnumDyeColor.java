/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.block.material.MapColor;
/*    */ import net.minecraft.util.EnumChatFormatting;
/*    */ import net.minecraft.util.IStringSerializable;
/*    */ 
/*    */ public enum EnumDyeColor implements IStringSerializable {
/*  8 */   WHITE(0, 15, "white", "white", MapColor.snowColor, EnumChatFormatting.WHITE),
/*  9 */   ORANGE(1, 14, "orange", "orange", MapColor.adobeColor, EnumChatFormatting.GOLD),
/* 10 */   MAGENTA(2, 13, "magenta", "magenta", MapColor.magentaColor, EnumChatFormatting.AQUA),
/* 11 */   LIGHT_BLUE(3, 12, "light_blue", "lightBlue", MapColor.lightBlueColor, EnumChatFormatting.BLUE),
/* 12 */   YELLOW(4, 11, "yellow", "yellow", MapColor.yellowColor, EnumChatFormatting.YELLOW),
/* 13 */   LIME(5, 10, "lime", "lime", MapColor.limeColor, EnumChatFormatting.GREEN),
/* 14 */   PINK(6, 9, "pink", "pink", MapColor.pinkColor, EnumChatFormatting.LIGHT_PURPLE),
/* 15 */   GRAY(7, 8, "gray", "gray", MapColor.grayColor, EnumChatFormatting.DARK_GRAY),
/* 16 */   SILVER(8, 7, "silver", "silver", MapColor.silverColor, EnumChatFormatting.GRAY),
/* 17 */   CYAN(9, 6, "cyan", "cyan", MapColor.cyanColor, EnumChatFormatting.DARK_AQUA),
/* 18 */   PURPLE(10, 5, "purple", "purple", MapColor.purpleColor, EnumChatFormatting.DARK_PURPLE),
/* 19 */   BLUE(11, 4, "blue", "blue", MapColor.blueColor, EnumChatFormatting.DARK_BLUE),
/* 20 */   BROWN(12, 3, "brown", "brown", MapColor.brownColor, EnumChatFormatting.GOLD),
/* 21 */   GREEN(13, 2, "green", "green", MapColor.greenColor, EnumChatFormatting.DARK_GREEN),
/* 22 */   RED(14, 1, "red", "red", MapColor.redColor, EnumChatFormatting.DARK_RED),
/* 23 */   BLACK(15, 0, "black", "black", MapColor.blackColor, EnumChatFormatting.BLACK);
/*    */   static {
/* 25 */     META_LOOKUP = new EnumDyeColor[(values()).length];
/* 26 */     DYE_DMG_LOOKUP = new EnumDyeColor[(values()).length];
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
/*    */     byte b;
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
/*    */     int i;
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
/*    */     EnumDyeColor[] arrayOfEnumDyeColor;
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
/* 84 */     for (i = (arrayOfEnumDyeColor = values()).length, b = 0; b < i; ) { EnumDyeColor enumdyecolor = arrayOfEnumDyeColor[b];
/* 85 */       META_LOOKUP[enumdyecolor.getMetadata()] = enumdyecolor;
/* 86 */       DYE_DMG_LOOKUP[enumdyecolor.getDyeDamage()] = enumdyecolor;
/*    */       b++; }
/*    */   
/*    */   }
/*    */   
/*    */   private static final EnumDyeColor[] META_LOOKUP;
/*    */   private static final EnumDyeColor[] DYE_DMG_LOOKUP;
/*    */   private final int meta;
/*    */   private final int dyeDamage;
/*    */   private final String name;
/*    */   private final String unlocalizedName;
/*    */   private final MapColor mapColor;
/*    */   private final EnumChatFormatting chatColor;
/*    */   
/*    */   EnumDyeColor(int meta, int dyeDamage, String name, String unlocalizedName, MapColor mapColorIn, EnumChatFormatting chatColor) {
/*    */     this.meta = meta;
/*    */     this.dyeDamage = dyeDamage;
/*    */     this.name = name;
/*    */     this.unlocalizedName = unlocalizedName;
/*    */     this.mapColor = mapColorIn;
/*    */     this.chatColor = chatColor;
/*    */   }
/*    */   
/*    */   public int getMetadata() {
/*    */     return this.meta;
/*    */   }
/*    */   
/*    */   public int getDyeDamage() {
/*    */     return this.dyeDamage;
/*    */   }
/*    */   
/*    */   public String getUnlocalizedName() {
/*    */     return this.unlocalizedName;
/*    */   }
/*    */   
/*    */   public MapColor getMapColor() {
/*    */     return this.mapColor;
/*    */   }
/*    */   
/*    */   public static EnumDyeColor byDyeDamage(int damage) {
/*    */     if (damage < 0 || damage >= DYE_DMG_LOOKUP.length)
/*    */       damage = 0; 
/*    */     return DYE_DMG_LOOKUP[damage];
/*    */   }
/*    */   
/*    */   public static EnumDyeColor byMetadata(int meta) {
/*    */     if (meta < 0 || meta >= META_LOOKUP.length)
/*    */       meta = 0; 
/*    */     return META_LOOKUP[meta];
/*    */   }
/*    */   
/*    */   public String toString() {
/*    */     return this.unlocalizedName;
/*    */   }
/*    */   
/*    */   public String getName() {
/*    */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\EnumDyeColor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */