/*     */ package net.minecraft.client.multiplayer;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.File;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.nbt.CompressedStreamTools;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class ServerList
/*     */ {
/*  16 */   private static final Logger logger = LogManager.getLogger();
/*     */ 
/*     */   
/*     */   private final Minecraft mc;
/*     */ 
/*     */   
/*  22 */   private final List<ServerData> servers = Lists.newArrayList();
/*     */   
/*     */   public ServerList(Minecraft mcIn) {
/*  25 */     this.mc = mcIn;
/*  26 */     loadServerList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadServerList() {
/*     */     try {
/*  35 */       this.servers.clear();
/*  36 */       NBTTagCompound nbttagcompound = CompressedStreamTools.read(new File(this.mc.mcDataDir, "servers.dat"));
/*     */       
/*  38 */       if (nbttagcompound == null) {
/*     */         return;
/*     */       }
/*     */       
/*  42 */       NBTTagList nbttaglist = nbttagcompound.getTagList("servers", 10);
/*     */       
/*  44 */       for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*  45 */         this.servers.add(ServerData.getServerDataFromNBTCompound(nbttaglist.getCompoundTagAt(i)));
/*     */       }
/*  47 */     } catch (Exception exception) {
/*  48 */       logger.error("Couldn't load server list", exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveServerList() {
/*     */     try {
/*  58 */       NBTTagList nbttaglist = new NBTTagList();
/*     */       
/*  60 */       for (ServerData serverdata : this.servers) {
/*  61 */         nbttaglist.appendTag((NBTBase)serverdata.getNBTCompound());
/*     */       }
/*     */       
/*  64 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  65 */       nbttagcompound.setTag("servers", (NBTBase)nbttaglist);
/*  66 */       CompressedStreamTools.safeWrite(nbttagcompound, new File(this.mc.mcDataDir, "servers.dat"));
/*  67 */     } catch (Exception exception) {
/*  68 */       logger.error("Couldn't save server list", exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerData getServerData(int index) {
/*  76 */     return this.servers.get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeServerData(int index) {
/*  83 */     this.servers.remove(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addServerData(ServerData server) {
/*  90 */     this.servers.add(server);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int countServers() {
/*  97 */     return this.servers.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void swapServers(int p_78857_1_, int p_78857_2_) {
/* 104 */     ServerData serverdata = getServerData(p_78857_1_);
/* 105 */     this.servers.set(p_78857_1_, getServerData(p_78857_2_));
/* 106 */     this.servers.set(p_78857_2_, serverdata);
/* 107 */     saveServerList();
/*     */   }
/*     */   
/*     */   public void func_147413_a(int index, ServerData server) {
/* 111 */     this.servers.set(index, server);
/*     */   }
/*     */   
/*     */   public static void func_147414_b(ServerData p_147414_0_) {
/* 115 */     ServerList serverlist = new ServerList(Minecraft.getMinecraft());
/* 116 */     serverlist.loadServerList();
/*     */     
/* 118 */     for (int i = 0; i < serverlist.countServers(); i++) {
/* 119 */       ServerData serverdata = serverlist.getServerData(i);
/*     */       
/* 121 */       if (serverdata.serverName.equals(p_147414_0_.serverName) && serverdata.serverIP.equals(p_147414_0_.serverIP)) {
/* 122 */         serverlist.func_147413_a(i, p_147414_0_);
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 127 */     serverlist.saveServerList();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\multiplayer\ServerList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */