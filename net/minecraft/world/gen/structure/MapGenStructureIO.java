/*    */ package net.minecraft.world.gen.structure;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.world.World;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class MapGenStructureIO
/*    */ {
/* 13 */   private static final Logger logger = LogManager.getLogger();
/* 14 */   private static Map<String, Class<? extends StructureStart>> startNameToClassMap = Maps.newHashMap();
/* 15 */   private static Map<Class<? extends StructureStart>, String> startClassToNameMap = Maps.newHashMap();
/* 16 */   private static Map<String, Class<? extends StructureComponent>> componentNameToClassMap = Maps.newHashMap();
/* 17 */   private static Map<Class<? extends StructureComponent>, String> componentClassToNameMap = Maps.newHashMap();
/*    */   
/*    */   private static void registerStructure(Class<? extends StructureStart> startClass, String structureName) {
/* 20 */     startNameToClassMap.put(structureName, startClass);
/* 21 */     startClassToNameMap.put(startClass, structureName);
/*    */   }
/*    */   
/*    */   static void registerStructureComponent(Class<? extends StructureComponent> componentClass, String componentName) {
/* 25 */     componentNameToClassMap.put(componentName, componentClass);
/* 26 */     componentClassToNameMap.put(componentClass, componentName);
/*    */   }
/*    */   
/*    */   public static String getStructureStartName(StructureStart start) {
/* 30 */     return startClassToNameMap.get(start.getClass());
/*    */   }
/*    */   
/*    */   public static String getStructureComponentName(StructureComponent component) {
/* 34 */     return componentClassToNameMap.get(component.getClass());
/*    */   }
/*    */   
/*    */   public static StructureStart getStructureStart(NBTTagCompound tagCompound, World worldIn) {
/* 38 */     StructureStart structurestart = null;
/*    */     
/*    */     try {
/* 41 */       Class<? extends StructureStart> oclass = startNameToClassMap.get(tagCompound.getString("id"));
/*    */       
/* 43 */       if (oclass != null) {
/* 44 */         structurestart = oclass.newInstance();
/*    */       }
/* 46 */     } catch (Exception exception) {
/* 47 */       logger.warn("Failed Start with id " + tagCompound.getString("id"));
/* 48 */       exception.printStackTrace();
/*    */     } 
/*    */     
/* 51 */     if (structurestart != null) {
/* 52 */       structurestart.readStructureComponentsFromNBT(worldIn, tagCompound);
/*    */     } else {
/* 54 */       logger.warn("Skipping Structure with id " + tagCompound.getString("id"));
/*    */     } 
/*    */     
/* 57 */     return structurestart;
/*    */   }
/*    */   
/*    */   public static StructureComponent getStructureComponent(NBTTagCompound tagCompound, World worldIn) {
/* 61 */     StructureComponent structurecomponent = null;
/*    */     
/*    */     try {
/* 64 */       Class<? extends StructureComponent> oclass = componentNameToClassMap.get(tagCompound.getString("id"));
/*    */       
/* 66 */       if (oclass != null) {
/* 67 */         structurecomponent = oclass.newInstance();
/*    */       }
/* 69 */     } catch (Exception exception) {
/* 70 */       logger.warn("Failed Piece with id " + tagCompound.getString("id"));
/* 71 */       exception.printStackTrace();
/*    */     } 
/*    */     
/* 74 */     if (structurecomponent != null) {
/* 75 */       structurecomponent.readStructureBaseNBT(worldIn, tagCompound);
/*    */     } else {
/* 77 */       logger.warn("Skipping Piece with id " + tagCompound.getString("id"));
/*    */     } 
/*    */     
/* 80 */     return structurecomponent;
/*    */   }
/*    */   
/*    */   static {
/* 84 */     registerStructure((Class)StructureMineshaftStart.class, "Mineshaft");
/* 85 */     registerStructure((Class)MapGenVillage.Start.class, "Village");
/* 86 */     registerStructure((Class)MapGenNetherBridge.Start.class, "Fortress");
/* 87 */     registerStructure((Class)MapGenStronghold.Start.class, "Stronghold");
/* 88 */     registerStructure((Class)MapGenScatteredFeature.Start.class, "Temple");
/* 89 */     registerStructure((Class)StructureOceanMonument.StartMonument.class, "Monument");
/* 90 */     StructureMineshaftPieces.registerStructurePieces();
/* 91 */     StructureVillagePieces.registerVillagePieces();
/* 92 */     StructureNetherBridgePieces.registerNetherFortressPieces();
/* 93 */     StructureStrongholdPieces.registerStrongholdPieces();
/* 94 */     ComponentScatteredFeaturePieces.registerScatteredFeaturePieces();
/* 95 */     StructureOceanMonumentPieces.registerOceanMonumentPieces();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\structure\MapGenStructureIO.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */