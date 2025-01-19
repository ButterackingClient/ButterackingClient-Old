/*    */ package net.optifine.util;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.multiplayer.WorldClient;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.server.integrated.IntegratedServer;
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.WorldProvider;
/*    */ import net.minecraft.world.WorldServer;
/*    */ import net.minecraft.world.chunk.Chunk;
/*    */ 
/*    */ public class IntegratedServerUtils {
/*    */   public static WorldServer getWorldServer() {
/* 18 */     Minecraft minecraft = Config.getMinecraft();
/* 19 */     WorldClient worldClient = minecraft.theWorld;
/*    */     
/* 21 */     if (worldClient == null)
/* 22 */       return null; 
/* 23 */     if (!minecraft.isIntegratedServerRunning()) {
/* 24 */       return null;
/*    */     }
/* 26 */     IntegratedServer integratedserver = minecraft.getIntegratedServer();
/*    */     
/* 28 */     if (integratedserver == null) {
/* 29 */       return null;
/*    */     }
/* 31 */     WorldProvider worldprovider = ((World)worldClient).provider;
/*    */     
/* 33 */     if (worldprovider == null) {
/* 34 */       return null;
/*    */     }
/* 36 */     int i = worldprovider.getDimensionId();
/*    */     
/*    */     try {
/* 39 */       WorldServer worldserver = integratedserver.worldServerForDimension(i);
/* 40 */       return worldserver;
/* 41 */     } catch (NullPointerException var6) {
/* 42 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Entity getEntity(UUID uuid) {
/* 50 */     WorldServer worldserver = getWorldServer();
/*    */     
/* 52 */     if (worldserver == null) {
/* 53 */       return null;
/*    */     }
/* 55 */     Entity entity = worldserver.getEntityFromUuid(uuid);
/* 56 */     return entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public static TileEntity getTileEntity(BlockPos pos) {
/* 61 */     WorldServer worldserver = getWorldServer();
/*    */     
/* 63 */     if (worldserver == null) {
/* 64 */       return null;
/*    */     }
/* 66 */     Chunk chunk = worldserver.getChunkProvider().provideChunk(pos.getX() >> 4, pos.getZ() >> 4);
/*    */     
/* 68 */     if (chunk == null) {
/* 69 */       return null;
/*    */     }
/* 71 */     TileEntity tileentity = chunk.getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK);
/* 72 */     return tileentity;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifin\\util\IntegratedServerUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */