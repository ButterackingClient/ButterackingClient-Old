/*     */ package net.optifine.reflect;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.RenderGlobal;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemMap;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.MapData;
/*     */ 
/*     */ public class ReflectorForge
/*     */ {
/*  21 */   public static Object EVENT_RESULT_ALLOW = Reflector.getFieldValue(Reflector.Event_Result_ALLOW);
/*  22 */   public static Object EVENT_RESULT_DENY = Reflector.getFieldValue(Reflector.Event_Result_DENY);
/*  23 */   public static Object EVENT_RESULT_DEFAULT = Reflector.getFieldValue(Reflector.Event_Result_DEFAULT);
/*     */   
/*     */   public static void FMLClientHandler_trackBrokenTexture(ResourceLocation loc, String message) {
/*  26 */     if (!Reflector.FMLClientHandler_trackBrokenTexture.exists()) {
/*  27 */       Object object = Reflector.call(Reflector.FMLClientHandler_instance, new Object[0]);
/*  28 */       Reflector.call(object, Reflector.FMLClientHandler_trackBrokenTexture, new Object[] { loc, message });
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void FMLClientHandler_trackMissingTexture(ResourceLocation loc) {
/*  33 */     if (!Reflector.FMLClientHandler_trackMissingTexture.exists()) {
/*  34 */       Object object = Reflector.call(Reflector.FMLClientHandler_instance, new Object[0]);
/*  35 */       Reflector.call(object, Reflector.FMLClientHandler_trackMissingTexture, new Object[] { loc });
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void putLaunchBlackboard(String key, Object value) {
/*  40 */     Map<String, Object> map = (Map)Reflector.getFieldValue(Reflector.Launch_blackboard);
/*     */     
/*  42 */     if (map != null) {
/*  43 */       map.put(key, value);
/*     */     }
/*     */   }
/*     */   
/*     */   public static boolean renderFirstPersonHand(RenderGlobal renderGlobal, float partialTicks, int pass) {
/*  48 */     return !Reflector.ForgeHooksClient_renderFirstPersonHand.exists() ? false : Reflector.callBoolean(Reflector.ForgeHooksClient_renderFirstPersonHand, new Object[] { renderGlobal, Float.valueOf(partialTicks), Integer.valueOf(pass) });
/*     */   }
/*     */   
/*     */   public static InputStream getOptiFineResourceStream(String path) {
/*  52 */     if (!Reflector.OptiFineClassTransformer_instance.exists()) {
/*  53 */       return null;
/*     */     }
/*  55 */     Object object = Reflector.getFieldValue(Reflector.OptiFineClassTransformer_instance);
/*     */     
/*  57 */     if (object == null) {
/*  58 */       return null;
/*     */     }
/*  60 */     if (path.startsWith("/")) {
/*  61 */       path = path.substring(1);
/*     */     }
/*     */     
/*  64 */     byte[] abyte = (byte[])Reflector.call(object, Reflector.OptiFineClassTransformer_getOptiFineResource, new Object[] { path });
/*     */     
/*  66 */     if (abyte == null) {
/*  67 */       return null;
/*     */     }
/*  69 */     InputStream inputstream = new ByteArrayInputStream(abyte);
/*  70 */     return inputstream;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean blockHasTileEntity(IBlockState state) {
/*  77 */     Block block = state.getBlock();
/*  78 */     return !Reflector.ForgeBlock_hasTileEntity.exists() ? block.hasTileEntity() : Reflector.callBoolean(block, Reflector.ForgeBlock_hasTileEntity, new Object[] { state });
/*     */   }
/*     */   
/*     */   public static boolean isItemDamaged(ItemStack stack) {
/*  82 */     return !Reflector.ForgeItem_showDurabilityBar.exists() ? stack.isItemDamaged() : Reflector.callBoolean(stack.getItem(), Reflector.ForgeItem_showDurabilityBar, new Object[] { stack });
/*     */   }
/*     */   
/*     */   public static boolean armorHasOverlay(ItemArmor itemArmor, ItemStack itemStack) {
/*  86 */     int i = itemArmor.getColor(itemStack);
/*  87 */     return (i != -1);
/*     */   }
/*     */   
/*     */   public static MapData getMapData(ItemMap itemMap, ItemStack stack, World world) {
/*  91 */     return Reflector.ForgeHooksClient.exists() ? ((ItemMap)stack.getItem()).getMapData(stack, world) : itemMap.getMapData(stack, world);
/*     */   }
/*     */   
/*     */   public static String[] getForgeModIds() {
/*  95 */     if (!Reflector.Loader.exists()) {
/*  96 */       return new String[0];
/*     */     }
/*  98 */     Object object = Reflector.call(Reflector.Loader_instance, new Object[0]);
/*  99 */     List list = (List)Reflector.call(object, Reflector.Loader_getActiveModList, new Object[0]);
/*     */     
/* 101 */     if (list == null) {
/* 102 */       return new String[0];
/*     */     }
/* 104 */     List<String> list1 = new ArrayList<>();
/*     */     
/* 106 */     for (Object object1 : list) {
/* 107 */       if (Reflector.ModContainer.isInstance(object1)) {
/* 108 */         String s = Reflector.callString(object1, Reflector.ModContainer_getModId, new Object[0]);
/*     */         
/* 110 */         if (s != null) {
/* 111 */           list1.add(s);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 116 */     String[] astring = list1.<String>toArray(new String[list1.size()]);
/* 117 */     return astring;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean canEntitySpawn(EntityLiving entityliving, World world, float x, float y, float z) {
/* 123 */     Object object = Reflector.call(Reflector.ForgeEventFactory_canEntitySpawn, new Object[] { entityliving, world, Float.valueOf(x), Float.valueOf(y), Float.valueOf(z) });
/* 124 */     return !(object != EVENT_RESULT_ALLOW && (object != EVENT_RESULT_DEFAULT || !entityliving.getCanSpawnHere() || !entityliving.isNotColliding()));
/*     */   }
/*     */   
/*     */   public static boolean doSpecialSpawn(EntityLiving entityliving, World world, float x, int y, float z) {
/* 128 */     return Reflector.ForgeEventFactory_doSpecialSpawn.exists() ? Reflector.callBoolean(Reflector.ForgeEventFactory_doSpecialSpawn, new Object[] { entityliving, world, Float.valueOf(x), Integer.valueOf(y), Float.valueOf(z) }) : false;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\reflect\ReflectorForge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */