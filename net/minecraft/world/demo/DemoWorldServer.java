/*    */ package net.minecraft.world.demo;
/*    */ 
/*    */ import net.minecraft.profiler.Profiler;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.world.WorldServer;
/*    */ import net.minecraft.world.WorldSettings;
/*    */ import net.minecraft.world.WorldType;
/*    */ import net.minecraft.world.storage.ISaveHandler;
/*    */ import net.minecraft.world.storage.WorldInfo;
/*    */ 
/*    */ public class DemoWorldServer extends WorldServer {
/* 12 */   private static final long demoWorldSeed = "North Carolina".hashCode();
/* 13 */   public static final WorldSettings demoWorldSettings = (new WorldSettings(demoWorldSeed, WorldSettings.GameType.SURVIVAL, true, false, WorldType.DEFAULT)).enableBonusChest();
/*    */   
/*    */   public DemoWorldServer(MinecraftServer server, ISaveHandler saveHandlerIn, WorldInfo worldInfoIn, int dimensionId, Profiler profilerIn) {
/* 16 */     super(server, saveHandlerIn, worldInfoIn, dimensionId, profilerIn);
/* 17 */     this.worldInfo.populateFromWorldSettings(demoWorldSettings);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\demo\DemoWorldServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */