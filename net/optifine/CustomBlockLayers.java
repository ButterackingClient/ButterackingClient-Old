/*    */ package net.optifine;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Properties;
/*    */ import net.minecraft.block.state.BlockStateBase;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.util.EnumWorldBlockLayer;
/*    */ import net.optifine.config.ConnectedParser;
/*    */ import net.optifine.config.MatchBlock;
/*    */ import net.optifine.shaders.BlockAliases;
/*    */ import net.optifine.util.PropertiesOrdered;
/*    */ import net.optifine.util.ResUtils;
/*    */ 
/*    */ public class CustomBlockLayers
/*    */ {
/* 18 */   private static EnumWorldBlockLayer[] renderLayers = null;
/*    */   public static boolean active = false;
/*    */   
/*    */   public static EnumWorldBlockLayer getRenderLayer(IBlockState blockState) {
/* 22 */     if (renderLayers == null)
/* 23 */       return null; 
/* 24 */     if (blockState.getBlock().isOpaqueCube())
/* 25 */       return null; 
/* 26 */     if (!(blockState instanceof BlockStateBase)) {
/* 27 */       return null;
/*    */     }
/* 29 */     BlockStateBase blockstatebase = (BlockStateBase)blockState;
/* 30 */     int i = blockstatebase.getBlockId();
/* 31 */     return (i > 0 && i < renderLayers.length) ? renderLayers[i] : null;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void update() {
/* 36 */     renderLayers = null;
/* 37 */     active = false;
/* 38 */     List<EnumWorldBlockLayer> list = new ArrayList<>();
/* 39 */     String s = "optifine/block.properties";
/* 40 */     Properties properties = ResUtils.readProperties(s, "CustomBlockLayers");
/*    */     
/* 42 */     if (properties != null) {
/* 43 */       readLayers(s, properties, list);
/*    */     }
/*    */     
/* 46 */     if (Config.isShaders()) {
/* 47 */       PropertiesOrdered propertiesordered = BlockAliases.getBlockLayerPropertes();
/*    */       
/* 49 */       if (propertiesordered != null) {
/* 50 */         String s1 = "shaders/block.properties";
/* 51 */         readLayers(s1, (Properties)propertiesordered, list);
/*    */       } 
/*    */     } 
/*    */     
/* 55 */     if (!list.isEmpty()) {
/* 56 */       renderLayers = list.<EnumWorldBlockLayer>toArray(new EnumWorldBlockLayer[list.size()]);
/* 57 */       active = true;
/*    */     } 
/*    */   }
/*    */   
/*    */   private static void readLayers(String pathProps, Properties props, List<EnumWorldBlockLayer> list) {
/* 62 */     Config.dbg("CustomBlockLayers: " + pathProps);
/* 63 */     readLayer("solid", EnumWorldBlockLayer.SOLID, props, list);
/* 64 */     readLayer("cutout", EnumWorldBlockLayer.CUTOUT, props, list);
/* 65 */     readLayer("cutout_mipped", EnumWorldBlockLayer.CUTOUT_MIPPED, props, list);
/* 66 */     readLayer("translucent", EnumWorldBlockLayer.TRANSLUCENT, props, list);
/*    */   }
/*    */   
/*    */   private static void readLayer(String name, EnumWorldBlockLayer layer, Properties props, List<EnumWorldBlockLayer> listLayers) {
/* 70 */     String s = "layer." + name;
/* 71 */     String s1 = props.getProperty(s);
/*    */     
/* 73 */     if (s1 != null) {
/* 74 */       ConnectedParser connectedparser = new ConnectedParser("CustomBlockLayers");
/* 75 */       MatchBlock[] amatchblock = connectedparser.parseMatchBlocks(s1);
/*    */       
/* 77 */       if (amatchblock != null) {
/* 78 */         for (int i = 0; i < amatchblock.length; i++) {
/* 79 */           MatchBlock matchblock = amatchblock[i];
/* 80 */           int j = matchblock.getBlockId();
/*    */           
/* 82 */           if (j > 0) {
/* 83 */             while (listLayers.size() < j + 1) {
/* 84 */               listLayers.add(null);
/*    */             }
/*    */             
/* 87 */             if (listLayers.get(j) != null) {
/* 88 */               Config.warn("CustomBlockLayers: Block layer is already set, block: " + j + ", layer: " + name);
/*    */             }
/*    */             
/* 91 */             listLayers.set(j, layer);
/*    */           } 
/*    */         } 
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public static boolean isActive() {
/* 99 */     return active;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\CustomBlockLayers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */