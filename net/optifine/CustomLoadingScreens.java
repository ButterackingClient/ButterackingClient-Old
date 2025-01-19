/*    */ package net.optifine;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Properties;
/*    */ import java.util.Set;
/*    */ import net.minecraft.network.PacketThreadUtil;
/*    */ import net.minecraft.src.Config;
/*    */ import net.optifine.util.ResUtils;
/*    */ import net.optifine.util.StrUtils;
/*    */ import org.apache.commons.lang3.tuple.ImmutablePair;
/*    */ import org.apache.commons.lang3.tuple.Pair;
/*    */ 
/*    */ public class CustomLoadingScreens
/*    */ {
/* 17 */   private static CustomLoadingScreen[] screens = null;
/* 18 */   private static int screensMinDimensionId = 0;
/*    */   
/*    */   public static CustomLoadingScreen getCustomLoadingScreen() {
/* 21 */     if (screens == null) {
/* 22 */       return null;
/*    */     }
/* 24 */     int i = PacketThreadUtil.lastDimensionId;
/* 25 */     int j = i - screensMinDimensionId;
/* 26 */     CustomLoadingScreen customloadingscreen = null;
/*    */     
/* 28 */     if (j >= 0 && j < screens.length) {
/* 29 */       customloadingscreen = screens[j];
/*    */     }
/*    */     
/* 32 */     return customloadingscreen;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void update() {
/* 37 */     screens = null;
/* 38 */     screensMinDimensionId = 0;
/* 39 */     Pair<CustomLoadingScreen[], Integer> pair = parseScreens();
/* 40 */     screens = (CustomLoadingScreen[])pair.getLeft();
/* 41 */     screensMinDimensionId = ((Integer)pair.getRight()).intValue();
/*    */   }
/*    */   
/*    */   private static Pair<CustomLoadingScreen[], Integer> parseScreens() {
/* 45 */     String s = "optifine/gui/loading/background";
/* 46 */     String s1 = ".png";
/* 47 */     String[] astring = ResUtils.collectFiles(s, s1);
/* 48 */     Map<Integer, String> map = new HashMap<>();
/*    */     
/* 50 */     for (int i = 0; i < astring.length; i++) {
/* 51 */       String s2 = astring[i];
/* 52 */       String s3 = StrUtils.removePrefixSuffix(s2, s, s1);
/* 53 */       int j = Config.parseInt(s3, -2147483648);
/*    */       
/* 55 */       if (j == Integer.MIN_VALUE) {
/* 56 */         warn("Invalid dimension ID: " + s3 + ", path: " + s2);
/*    */       } else {
/* 58 */         map.put(Integer.valueOf(j), s2);
/*    */       } 
/*    */     } 
/*    */     
/* 62 */     Set<Integer> set = map.keySet();
/* 63 */     Integer[] ainteger = set.<Integer>toArray(new Integer[set.size()]);
/* 64 */     Arrays.sort((Object[])ainteger);
/*    */     
/* 66 */     if (ainteger.length <= 0) {
/* 67 */       return (Pair<CustomLoadingScreen[], Integer>)new ImmutablePair(null, Integer.valueOf(0));
/*    */     }
/* 69 */     String s5 = "optifine/gui/loading/loading.properties";
/* 70 */     Properties properties = ResUtils.readProperties(s5, "CustomLoadingScreens");
/* 71 */     int k = ainteger[0].intValue();
/* 72 */     int l = ainteger[ainteger.length - 1].intValue();
/* 73 */     int i1 = l - k + 1;
/* 74 */     CustomLoadingScreen[] acustomloadingscreen = new CustomLoadingScreen[i1];
/*    */     
/* 76 */     for (int j1 = 0; j1 < ainteger.length; j1++) {
/* 77 */       Integer integer = ainteger[j1];
/* 78 */       String s4 = map.get(integer);
/* 79 */       acustomloadingscreen[integer.intValue() - k] = CustomLoadingScreen.parseScreen(s4, integer.intValue(), properties);
/*    */     } 
/*    */     
/* 82 */     return (Pair<CustomLoadingScreen[], Integer>)new ImmutablePair(acustomloadingscreen, Integer.valueOf(k));
/*    */   }
/*    */ 
/*    */   
/*    */   public static void warn(String str) {
/* 87 */     Config.warn("CustomLoadingScreen: " + str);
/*    */   }
/*    */   
/*    */   public static void dbg(String str) {
/* 91 */     Config.dbg("CustomLoadingScreen: " + str);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\CustomLoadingScreens.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */