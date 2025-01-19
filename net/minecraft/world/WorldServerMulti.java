/*    */ package net.minecraft.world;
/*    */ 
/*    */ import net.minecraft.profiler.Profiler;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.village.VillageCollection;
/*    */ import net.minecraft.world.border.IBorderListener;
/*    */ import net.minecraft.world.border.WorldBorder;
/*    */ import net.minecraft.world.storage.DerivedWorldInfo;
/*    */ import net.minecraft.world.storage.ISaveHandler;
/*    */ import net.minecraft.world.storage.WorldInfo;
/*    */ 
/*    */ public class WorldServerMulti
/*    */   extends WorldServer {
/*    */   public WorldServerMulti(MinecraftServer server, ISaveHandler saveHandlerIn, int dimensionId, WorldServer delegate, Profiler profilerIn) {
/* 15 */     super(server, saveHandlerIn, (WorldInfo)new DerivedWorldInfo(delegate.getWorldInfo()), dimensionId, profilerIn);
/* 16 */     this.delegate = delegate;
/* 17 */     delegate.getWorldBorder().addListener(new IBorderListener() {
/*    */           public void onSizeChanged(WorldBorder border, double newSize) {
/* 19 */             WorldServerMulti.this.getWorldBorder().setTransition(newSize);
/*    */           }
/*    */           
/*    */           public void onTransitionStarted(WorldBorder border, double oldSize, double newSize, long time) {
/* 23 */             WorldServerMulti.this.getWorldBorder().setTransition(oldSize, newSize, time);
/*    */           }
/*    */           
/*    */           public void onCenterChanged(WorldBorder border, double x, double z) {
/* 27 */             WorldServerMulti.this.getWorldBorder().setCenter(x, z);
/*    */           }
/*    */           
/*    */           public void onWarningTimeChanged(WorldBorder border, int newTime) {
/* 31 */             WorldServerMulti.this.getWorldBorder().setWarningTime(newTime);
/*    */           }
/*    */           
/*    */           public void onWarningDistanceChanged(WorldBorder border, int newDistance) {
/* 35 */             WorldServerMulti.this.getWorldBorder().setWarningDistance(newDistance);
/*    */           }
/*    */           
/*    */           public void onDamageAmountChanged(WorldBorder border, double newAmount) {
/* 39 */             WorldServerMulti.this.getWorldBorder().setDamageAmount(newAmount);
/*    */           }
/*    */           
/*    */           public void onDamageBufferChanged(WorldBorder border, double newSize) {
/* 43 */             WorldServerMulti.this.getWorldBorder().setDamageBuffer(newSize);
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   private WorldServer delegate;
/*    */ 
/*    */   
/*    */   protected void saveLevel() throws MinecraftException {}
/*    */   
/*    */   public World init() {
/* 55 */     this.mapStorage = this.delegate.getMapStorage();
/* 56 */     this.worldScoreboard = this.delegate.getScoreboard();
/* 57 */     String s = VillageCollection.fileNameForProvider(this.provider);
/* 58 */     VillageCollection villagecollection = (VillageCollection)this.mapStorage.loadData(VillageCollection.class, s);
/*    */     
/* 60 */     if (villagecollection == null) {
/* 61 */       this.villageCollectionObj = new VillageCollection(this);
/* 62 */       this.mapStorage.setData(s, (WorldSavedData)this.villageCollectionObj);
/*    */     } else {
/* 64 */       this.villageCollectionObj = villagecollection;
/* 65 */       this.villageCollectionObj.setWorldsForAll(this);
/*    */     } 
/*    */     
/* 68 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\WorldServerMulti.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */