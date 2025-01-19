/*     */ package net.optifine.player;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonParser;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import javax.imageio.ImageIO;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.http.HttpPipeline;
/*     */ import net.optifine.http.HttpUtils;
/*     */ import net.optifine.util.Json;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlayerConfigurationParser
/*     */ {
/*  23 */   private String player = null;
/*     */   public static final String CONFIG_ITEMS = "items";
/*     */   public static final String ITEM_TYPE = "type";
/*     */   public static final String ITEM_ACTIVE = "active";
/*     */   
/*     */   public PlayerConfigurationParser(String player) {
/*  29 */     this.player = player;
/*     */   }
/*     */   
/*     */   public PlayerConfiguration parsePlayerConfiguration(JsonElement je) {
/*  33 */     if (je == null) {
/*  34 */       throw new JsonParseException("JSON object is null, player: " + this.player);
/*     */     }
/*  36 */     JsonObject jsonobject = (JsonObject)je;
/*  37 */     PlayerConfiguration playerconfiguration = new PlayerConfiguration();
/*  38 */     JsonArray jsonarray = (JsonArray)jsonobject.get("items");
/*     */     
/*  40 */     if (jsonarray != null) {
/*  41 */       for (int i = 0; i < jsonarray.size(); i++) {
/*  42 */         JsonObject jsonobject1 = (JsonObject)jsonarray.get(i);
/*  43 */         boolean flag = Json.getBoolean(jsonobject1, "active", true);
/*     */         
/*  45 */         if (flag) {
/*  46 */           String s = Json.getString(jsonobject1, "type");
/*     */           
/*  48 */           if (s == null) {
/*  49 */             Config.warn("Item type is null, player: " + this.player); continue;
/*     */           } 
/*  51 */           String s1 = Json.getString(jsonobject1, "model");
/*     */           
/*  53 */           if (s1 == null) {
/*  54 */             s1 = "items/" + s + "/model.cfg";
/*     */           }
/*     */           
/*  57 */           PlayerItemModel playeritemmodel = downloadModel(s1);
/*     */           
/*  59 */           if (playeritemmodel != null) {
/*  60 */             if (!playeritemmodel.isUsePlayerTexture()) {
/*  61 */               String s2 = Json.getString(jsonobject1, "texture");
/*     */               
/*  63 */               if (s2 == null) {
/*  64 */                 s2 = "items/" + s + "/users/" + this.player + ".png";
/*     */               }
/*     */               
/*  67 */               BufferedImage bufferedimage = downloadTextureImage(s2);
/*     */               
/*  69 */               if (bufferedimage == null) {
/*     */                 continue;
/*     */               }
/*     */               
/*  73 */               playeritemmodel.setTextureImage(bufferedimage);
/*  74 */               ResourceLocation resourcelocation = new ResourceLocation("optifine.net", s2);
/*  75 */               playeritemmodel.setTextureLocation(resourcelocation);
/*     */             } 
/*     */             
/*  78 */             playerconfiguration.addPlayerItemModel(playeritemmodel);
/*     */           } 
/*     */         } 
/*     */         
/*     */         continue;
/*     */       } 
/*     */     }
/*  85 */     return playerconfiguration;
/*     */   }
/*     */ 
/*     */   
/*     */   private BufferedImage downloadTextureImage(String texturePath) {
/*  90 */     String s = String.valueOf(HttpUtils.getPlayerItemsUrl()) + "/" + texturePath;
/*     */     
/*     */     try {
/*  93 */       byte[] abyte = HttpPipeline.get(s, Minecraft.getMinecraft().getProxy());
/*  94 */       BufferedImage bufferedimage = ImageIO.read(new ByteArrayInputStream(abyte));
/*  95 */       return bufferedimage;
/*  96 */     } catch (IOException ioexception) {
/*  97 */       Config.warn("Error loading item texture " + texturePath + ": " + ioexception.getClass().getName() + ": " + ioexception.getMessage());
/*  98 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private PlayerItemModel downloadModel(String modelPath) {
/* 103 */     String s = String.valueOf(HttpUtils.getPlayerItemsUrl()) + "/" + modelPath;
/*     */     
/*     */     try {
/* 106 */       byte[] abyte = HttpPipeline.get(s, Minecraft.getMinecraft().getProxy());
/* 107 */       String s1 = new String(abyte, "ASCII");
/* 108 */       JsonParser jsonparser = new JsonParser();
/* 109 */       JsonObject jsonobject = (JsonObject)jsonparser.parse(s1);
/* 110 */       PlayerItemModel playeritemmodel = PlayerItemParser.parseItemModel(jsonobject);
/* 111 */       return playeritemmodel;
/* 112 */     } catch (Exception exception) {
/* 113 */       Config.warn("Error loading item model " + modelPath + ": " + exception.getClass().getName() + ": " + exception.getMessage());
/* 114 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\player\PlayerConfigurationParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */