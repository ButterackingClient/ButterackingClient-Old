/*     */ package net.minecraft.stats;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonParser;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S37PacketStatistics;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.IJsonSerializable;
/*     */ import net.minecraft.util.TupleIntJsonSerializable;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class StatisticsFile
/*     */   extends StatFileWriter {
/*  29 */   private static final Logger logger = LogManager.getLogger();
/*     */   private final MinecraftServer mcServer;
/*     */   private final File statsFile;
/*  32 */   private final Set<StatBase> field_150888_e = Sets.newHashSet();
/*  33 */   private int field_150885_f = -300;
/*     */   private boolean field_150886_g = false;
/*     */   
/*     */   public StatisticsFile(MinecraftServer serverIn, File statsFileIn) {
/*  37 */     this.mcServer = serverIn;
/*  38 */     this.statsFile = statsFileIn;
/*     */   }
/*     */   
/*     */   public void readStatFile() {
/*  42 */     if (this.statsFile.isFile()) {
/*     */       try {
/*  44 */         this.statsData.clear();
/*  45 */         this.statsData.putAll(parseJson(FileUtils.readFileToString(this.statsFile)));
/*  46 */       } catch (IOException ioexception) {
/*  47 */         logger.error("Couldn't read statistics file " + this.statsFile, ioexception);
/*  48 */       } catch (JsonParseException jsonparseexception) {
/*  49 */         logger.error("Couldn't parse statistics file " + this.statsFile, (Throwable)jsonparseexception);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void saveStatFile() {
/*     */     try {
/*  56 */       FileUtils.writeStringToFile(this.statsFile, dumpJson(this.statsData));
/*  57 */     } catch (IOException ioexception) {
/*  58 */       logger.error("Couldn't save stats", ioexception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unlockAchievement(EntityPlayer playerIn, StatBase statIn, int p_150873_3_) {
/*  66 */     int i = statIn.isAchievement() ? readStat(statIn) : 0;
/*  67 */     super.unlockAchievement(playerIn, statIn, p_150873_3_);
/*  68 */     this.field_150888_e.add(statIn);
/*     */     
/*  70 */     if (statIn.isAchievement() && i == 0 && p_150873_3_ > 0) {
/*  71 */       this.field_150886_g = true;
/*     */       
/*  73 */       if (this.mcServer.isAnnouncingPlayerAchievements()) {
/*  74 */         this.mcServer.getConfigurationManager().sendChatMsg((IChatComponent)new ChatComponentTranslation("chat.type.achievement", new Object[] { playerIn.getDisplayName(), statIn.createChatComponent() }));
/*     */       }
/*     */     } 
/*     */     
/*  78 */     if (statIn.isAchievement() && i > 0 && p_150873_3_ == 0) {
/*  79 */       this.field_150886_g = true;
/*     */       
/*  81 */       if (this.mcServer.isAnnouncingPlayerAchievements()) {
/*  82 */         this.mcServer.getConfigurationManager().sendChatMsg((IChatComponent)new ChatComponentTranslation("chat.type.achievement.taken", new Object[] { playerIn.getDisplayName(), statIn.createChatComponent() }));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public Set<StatBase> func_150878_c() {
/*  88 */     Set<StatBase> set = Sets.newHashSet(this.field_150888_e);
/*  89 */     this.field_150888_e.clear();
/*  90 */     this.field_150886_g = false;
/*  91 */     return set;
/*     */   }
/*     */   
/*     */   public Map<StatBase, TupleIntJsonSerializable> parseJson(String p_150881_1_) {
/*  95 */     JsonElement jsonelement = (new JsonParser()).parse(p_150881_1_);
/*     */     
/*  97 */     if (!jsonelement.isJsonObject()) {
/*  98 */       return Maps.newHashMap();
/*     */     }
/* 100 */     JsonObject jsonobject = jsonelement.getAsJsonObject();
/* 101 */     Map<StatBase, TupleIntJsonSerializable> map = Maps.newHashMap();
/*     */     
/* 103 */     for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)jsonobject.entrySet()) {
/* 104 */       StatBase statbase = StatList.getOneShotStat(entry.getKey());
/*     */       
/* 106 */       if (statbase != null) {
/* 107 */         TupleIntJsonSerializable tupleintjsonserializable = new TupleIntJsonSerializable();
/*     */         
/* 109 */         if (((JsonElement)entry.getValue()).isJsonPrimitive() && ((JsonElement)entry.getValue()).getAsJsonPrimitive().isNumber()) {
/* 110 */           tupleintjsonserializable.setIntegerValue(((JsonElement)entry.getValue()).getAsInt());
/* 111 */         } else if (((JsonElement)entry.getValue()).isJsonObject()) {
/* 112 */           JsonObject jsonobject1 = ((JsonElement)entry.getValue()).getAsJsonObject();
/*     */           
/* 114 */           if (jsonobject1.has("value") && jsonobject1.get("value").isJsonPrimitive() && jsonobject1.get("value").getAsJsonPrimitive().isNumber()) {
/* 115 */             tupleintjsonserializable.setIntegerValue(jsonobject1.getAsJsonPrimitive("value").getAsInt());
/*     */           }
/*     */           
/* 118 */           if (jsonobject1.has("progress") && statbase.func_150954_l() != null) {
/*     */             try {
/* 120 */               Constructor<? extends IJsonSerializable> constructor = statbase.func_150954_l().getConstructor(new Class[0]);
/* 121 */               IJsonSerializable ijsonserializable = constructor.newInstance(new Object[0]);
/* 122 */               ijsonserializable.fromJson(jsonobject1.get("progress"));
/* 123 */               tupleintjsonserializable.setJsonSerializableValue(ijsonserializable);
/* 124 */             } catch (Throwable throwable) {
/* 125 */               logger.warn("Invalid statistic progress in " + this.statsFile, throwable);
/*     */             } 
/*     */           }
/*     */         } 
/*     */         
/* 130 */         map.put(statbase, tupleintjsonserializable); continue;
/*     */       } 
/* 132 */       logger.warn("Invalid statistic in " + this.statsFile + ": Don't know what " + (String)entry.getKey() + " is");
/*     */     } 
/*     */ 
/*     */     
/* 136 */     return map;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String dumpJson(Map<StatBase, TupleIntJsonSerializable> p_150880_0_) {
/* 141 */     JsonObject jsonobject = new JsonObject();
/*     */     
/* 143 */     for (Map.Entry<StatBase, TupleIntJsonSerializable> entry : p_150880_0_.entrySet()) {
/* 144 */       if (((TupleIntJsonSerializable)entry.getValue()).getJsonSerializableValue() != null) {
/* 145 */         JsonObject jsonobject1 = new JsonObject();
/* 146 */         jsonobject1.addProperty("value", Integer.valueOf(((TupleIntJsonSerializable)entry.getValue()).getIntegerValue()));
/*     */         
/*     */         try {
/* 149 */           jsonobject1.add("progress", ((TupleIntJsonSerializable)entry.getValue()).getJsonSerializableValue().getSerializableElement());
/* 150 */         } catch (Throwable throwable) {
/* 151 */           logger.warn("Couldn't save statistic " + ((StatBase)entry.getKey()).getStatName() + ": error serializing progress", throwable);
/*     */         } 
/*     */         
/* 154 */         jsonobject.add(((StatBase)entry.getKey()).statId, (JsonElement)jsonobject1); continue;
/*     */       } 
/* 156 */       jsonobject.addProperty(((StatBase)entry.getKey()).statId, Integer.valueOf(((TupleIntJsonSerializable)entry.getValue()).getIntegerValue()));
/*     */     } 
/*     */ 
/*     */     
/* 160 */     return jsonobject.toString();
/*     */   }
/*     */   
/*     */   public void func_150877_d() {
/* 164 */     for (StatBase statbase : this.statsData.keySet()) {
/* 165 */       this.field_150888_e.add(statbase);
/*     */     }
/*     */   }
/*     */   
/*     */   public void func_150876_a(EntityPlayerMP p_150876_1_) {
/* 170 */     int i = this.mcServer.getTickCounter();
/* 171 */     Map<StatBase, Integer> map = Maps.newHashMap();
/*     */     
/* 173 */     if (this.field_150886_g || i - this.field_150885_f > 300) {
/* 174 */       this.field_150885_f = i;
/*     */       
/* 176 */       for (StatBase statbase : func_150878_c()) {
/* 177 */         map.put(statbase, Integer.valueOf(readStat(statbase)));
/*     */       }
/*     */     } 
/*     */     
/* 181 */     p_150876_1_.playerNetServerHandler.sendPacket((Packet)new S37PacketStatistics(map));
/*     */   }
/*     */   
/*     */   public void sendAchievements(EntityPlayerMP player) {
/* 185 */     Map<StatBase, Integer> map = Maps.newHashMap();
/*     */     
/* 187 */     for (Achievement achievement : AchievementList.achievementList) {
/* 188 */       if (hasAchievementUnlocked(achievement)) {
/* 189 */         map.put(achievement, Integer.valueOf(readStat(achievement)));
/* 190 */         this.field_150888_e.remove(achievement);
/*     */       } 
/*     */     } 
/*     */     
/* 194 */     player.playerNetServerHandler.sendPacket((Packet)new S37PacketStatistics(map));
/*     */   }
/*     */   
/*     */   public boolean func_150879_e() {
/* 198 */     return this.field_150886_g;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\stats\StatisticsFile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */