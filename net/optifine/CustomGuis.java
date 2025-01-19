/*     */ package net.optifine;
/*     */ 
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Calendar;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.client.resources.IResourcePack;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.optifine.override.PlayerControllerOF;
/*     */ import net.optifine.util.PropertiesOrdered;
/*     */ import net.optifine.util.ResUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CustomGuis
/*     */ {
/*  38 */   private static Minecraft mc = Config.getMinecraft();
/*  39 */   private static PlayerControllerOF playerControllerOF = null;
/*  40 */   private static CustomGuiProperties[][] guiProperties = null;
/*  41 */   public static boolean isChristmas = isChristmas();
/*     */   
/*     */   public static ResourceLocation getTextureLocation(ResourceLocation loc) {
/*  44 */     if (guiProperties == null) {
/*  45 */       return loc;
/*     */     }
/*  47 */     GuiScreen guiscreen = mc.currentScreen;
/*     */     
/*  49 */     if (!(guiscreen instanceof net.minecraft.client.gui.inventory.GuiContainer))
/*  50 */       return loc; 
/*  51 */     if (loc.getResourceDomain().equals("minecraft") && loc.getResourcePath().startsWith("textures/gui/")) {
/*  52 */       if (playerControllerOF == null) {
/*  53 */         return loc;
/*     */       }
/*  55 */       WorldClient worldClient = mc.theWorld;
/*     */       
/*  57 */       if (worldClient == null)
/*  58 */         return loc; 
/*  59 */       if (guiscreen instanceof net.minecraft.client.gui.inventory.GuiContainerCreative)
/*  60 */         return getTexturePos(CustomGuiProperties.EnumContainer.CREATIVE, mc.thePlayer.getPosition(), (IBlockAccess)worldClient, loc, guiscreen); 
/*  61 */       if (guiscreen instanceof net.minecraft.client.gui.inventory.GuiInventory) {
/*  62 */         return getTexturePos(CustomGuiProperties.EnumContainer.INVENTORY, mc.thePlayer.getPosition(), (IBlockAccess)worldClient, loc, guiscreen);
/*     */       }
/*  64 */       BlockPos blockpos = playerControllerOF.getLastClickBlockPos();
/*     */       
/*  66 */       if (blockpos != null) {
/*  67 */         if (guiscreen instanceof net.minecraft.client.gui.GuiRepair) {
/*  68 */           return getTexturePos(CustomGuiProperties.EnumContainer.ANVIL, blockpos, (IBlockAccess)worldClient, loc, guiscreen);
/*     */         }
/*     */         
/*  71 */         if (guiscreen instanceof net.minecraft.client.gui.inventory.GuiBeacon) {
/*  72 */           return getTexturePos(CustomGuiProperties.EnumContainer.BEACON, blockpos, (IBlockAccess)worldClient, loc, guiscreen);
/*     */         }
/*     */         
/*  75 */         if (guiscreen instanceof net.minecraft.client.gui.inventory.GuiBrewingStand) {
/*  76 */           return getTexturePos(CustomGuiProperties.EnumContainer.BREWING_STAND, blockpos, (IBlockAccess)worldClient, loc, guiscreen);
/*     */         }
/*     */         
/*  79 */         if (guiscreen instanceof net.minecraft.client.gui.inventory.GuiChest) {
/*  80 */           return getTexturePos(CustomGuiProperties.EnumContainer.CHEST, blockpos, (IBlockAccess)worldClient, loc, guiscreen);
/*     */         }
/*     */         
/*  83 */         if (guiscreen instanceof net.minecraft.client.gui.inventory.GuiCrafting) {
/*  84 */           return getTexturePos(CustomGuiProperties.EnumContainer.CRAFTING, blockpos, (IBlockAccess)worldClient, loc, guiscreen);
/*     */         }
/*     */         
/*  87 */         if (guiscreen instanceof net.minecraft.client.gui.inventory.GuiDispenser) {
/*  88 */           return getTexturePos(CustomGuiProperties.EnumContainer.DISPENSER, blockpos, (IBlockAccess)worldClient, loc, guiscreen);
/*     */         }
/*     */         
/*  91 */         if (guiscreen instanceof net.minecraft.client.gui.GuiEnchantment) {
/*  92 */           return getTexturePos(CustomGuiProperties.EnumContainer.ENCHANTMENT, blockpos, (IBlockAccess)worldClient, loc, guiscreen);
/*     */         }
/*     */         
/*  95 */         if (guiscreen instanceof net.minecraft.client.gui.inventory.GuiFurnace) {
/*  96 */           return getTexturePos(CustomGuiProperties.EnumContainer.FURNACE, blockpos, (IBlockAccess)worldClient, loc, guiscreen);
/*     */         }
/*     */         
/*  99 */         if (guiscreen instanceof net.minecraft.client.gui.GuiHopper) {
/* 100 */           return getTexturePos(CustomGuiProperties.EnumContainer.HOPPER, blockpos, (IBlockAccess)worldClient, loc, guiscreen);
/*     */         }
/*     */       } 
/*     */       
/* 104 */       Entity entity = playerControllerOF.getLastClickEntity();
/*     */       
/* 106 */       if (entity != null) {
/* 107 */         if (guiscreen instanceof net.minecraft.client.gui.inventory.GuiScreenHorseInventory) {
/* 108 */           return getTextureEntity(CustomGuiProperties.EnumContainer.HORSE, entity, (IBlockAccess)worldClient, loc);
/*     */         }
/*     */         
/* 111 */         if (guiscreen instanceof net.minecraft.client.gui.GuiMerchant) {
/* 112 */           return getTextureEntity(CustomGuiProperties.EnumContainer.VILLAGER, entity, (IBlockAccess)worldClient, loc);
/*     */         }
/*     */       } 
/*     */       
/* 116 */       return loc;
/*     */     } 
/*     */ 
/*     */     
/* 120 */     return loc;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static ResourceLocation getTexturePos(CustomGuiProperties.EnumContainer container, BlockPos pos, IBlockAccess blockAccess, ResourceLocation loc, GuiScreen screen) {
/* 126 */     CustomGuiProperties[] acustomguiproperties = guiProperties[container.ordinal()];
/*     */     
/* 128 */     if (acustomguiproperties == null) {
/* 129 */       return loc;
/*     */     }
/* 131 */     for (int i = 0; i < acustomguiproperties.length; i++) {
/* 132 */       CustomGuiProperties customguiproperties = acustomguiproperties[i];
/*     */       
/* 134 */       if (customguiproperties.matchesPos(container, pos, blockAccess, screen)) {
/* 135 */         return customguiproperties.getTextureLocation(loc);
/*     */       }
/*     */     } 
/*     */     
/* 139 */     return loc;
/*     */   }
/*     */ 
/*     */   
/*     */   private static ResourceLocation getTextureEntity(CustomGuiProperties.EnumContainer container, Entity entity, IBlockAccess blockAccess, ResourceLocation loc) {
/* 144 */     CustomGuiProperties[] acustomguiproperties = guiProperties[container.ordinal()];
/*     */     
/* 146 */     if (acustomguiproperties == null) {
/* 147 */       return loc;
/*     */     }
/* 149 */     for (int i = 0; i < acustomguiproperties.length; i++) {
/* 150 */       CustomGuiProperties customguiproperties = acustomguiproperties[i];
/*     */       
/* 152 */       if (customguiproperties.matchesEntity(container, entity, blockAccess)) {
/* 153 */         return customguiproperties.getTextureLocation(loc);
/*     */       }
/*     */     } 
/*     */     
/* 157 */     return loc;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void update() {
/* 162 */     guiProperties = null;
/*     */     
/* 164 */     if (Config.isCustomGuis()) {
/* 165 */       List<List<CustomGuiProperties>> list = new ArrayList<>();
/* 166 */       IResourcePack[] airesourcepack = Config.getResourcePacks();
/*     */       
/* 168 */       for (int i = airesourcepack.length - 1; i >= 0; i--) {
/* 169 */         IResourcePack iresourcepack = airesourcepack[i];
/* 170 */         update(iresourcepack, list);
/*     */       } 
/*     */       
/* 173 */       guiProperties = propertyListToArray(list);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static CustomGuiProperties[][] propertyListToArray(List<List<CustomGuiProperties>> listProps) {
/* 178 */     if (listProps.isEmpty()) {
/* 179 */       return null;
/*     */     }
/* 181 */     CustomGuiProperties[][] acustomguiproperties = new CustomGuiProperties[CustomGuiProperties.EnumContainer.VALUES.length][];
/*     */     
/* 183 */     for (int i = 0; i < acustomguiproperties.length; i++) {
/* 184 */       if (listProps.size() > i) {
/* 185 */         List<CustomGuiProperties> list = listProps.get(i);
/*     */         
/* 187 */         if (list != null) {
/* 188 */           CustomGuiProperties[] acustomguiproperties1 = list.<CustomGuiProperties>toArray(new CustomGuiProperties[list.size()]);
/* 189 */           acustomguiproperties[i] = acustomguiproperties1;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 194 */     return acustomguiproperties;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void update(IResourcePack rp, List<List<CustomGuiProperties>> listProps) {
/* 199 */     String[] astring = ResUtils.collectFiles(rp, "optifine/gui/container/", ".properties", null);
/* 200 */     Arrays.sort((Object[])astring);
/*     */     
/* 202 */     for (int i = 0; i < astring.length; i++) {
/* 203 */       String s = astring[i];
/* 204 */       Config.dbg("CustomGuis: " + s);
/*     */       
/*     */       try {
/* 207 */         ResourceLocation resourcelocation = new ResourceLocation(s);
/* 208 */         InputStream inputstream = rp.getInputStream(resourcelocation);
/*     */         
/* 210 */         if (inputstream == null) {
/* 211 */           Config.warn("CustomGuis file not found: " + s);
/*     */         } else {
/* 213 */           PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/* 214 */           propertiesOrdered.load(inputstream);
/* 215 */           inputstream.close();
/* 216 */           CustomGuiProperties customguiproperties = new CustomGuiProperties((Properties)propertiesOrdered, s);
/*     */           
/* 218 */           if (customguiproperties.isValid(s)) {
/* 219 */             addToList(customguiproperties, listProps);
/*     */           }
/*     */         } 
/* 222 */       } catch (FileNotFoundException var9) {
/* 223 */         Config.warn("CustomGuis file not found: " + s);
/* 224 */       } catch (Exception exception) {
/* 225 */         exception.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void addToList(CustomGuiProperties cgp, List<List<CustomGuiProperties>> listProps) {
/* 231 */     if (cgp.getContainer() == null) {
/* 232 */       warn("Invalid container: " + cgp.getContainer());
/*     */     } else {
/* 234 */       int i = cgp.getContainer().ordinal();
/*     */       
/* 236 */       while (listProps.size() <= i) {
/* 237 */         listProps.add(null);
/*     */       }
/*     */       
/* 240 */       List<CustomGuiProperties> list = listProps.get(i);
/*     */       
/* 242 */       if (list == null) {
/* 243 */         list = new ArrayList<>();
/* 244 */         listProps.set(i, list);
/*     */       } 
/*     */       
/* 247 */       list.add(cgp);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static PlayerControllerOF getPlayerControllerOF() {
/* 252 */     return playerControllerOF;
/*     */   }
/*     */   
/*     */   public static void setPlayerControllerOF(PlayerControllerOF playerControllerOF) {
/* 256 */     playerControllerOF = playerControllerOF;
/*     */   }
/*     */   
/*     */   private static boolean isChristmas() {
/* 260 */     Calendar calendar = Calendar.getInstance();
/* 261 */     return (calendar.get(2) + 1 == 12 && calendar.get(5) >= 24 && calendar.get(5) <= 26);
/*     */   }
/*     */   
/*     */   private static void warn(String str) {
/* 265 */     Config.warn("[CustomGuis] " + str);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\CustomGuis.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */