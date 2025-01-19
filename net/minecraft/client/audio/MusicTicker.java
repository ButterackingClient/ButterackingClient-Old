/*    */ package net.minecraft.client.audio;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.util.ITickable;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class MusicTicker
/*    */   implements ITickable {
/* 11 */   private final Random rand = new Random();
/*    */   private final Minecraft mc;
/*    */   private ISound currentMusic;
/* 14 */   private int timeUntilNextMusic = 100;
/*    */   
/*    */   public MusicTicker(Minecraft mcIn) {
/* 17 */     this.mc = mcIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void update() {
/* 24 */     MusicType musicticker$musictype = this.mc.getAmbientMusicType();
/*    */     
/* 26 */     if (this.currentMusic != null) {
/* 27 */       if (!musicticker$musictype.getMusicLocation().equals(this.currentMusic.getSoundLocation())) {
/* 28 */         this.mc.getSoundHandler().stopSound(this.currentMusic);
/* 29 */         this.timeUntilNextMusic = MathHelper.getRandomIntegerInRange(this.rand, 0, musicticker$musictype.getMinDelay() / 2);
/*    */       } 
/*    */       
/* 32 */       if (!this.mc.getSoundHandler().isSoundPlaying(this.currentMusic)) {
/* 33 */         this.currentMusic = null;
/* 34 */         this.timeUntilNextMusic = Math.min(MathHelper.getRandomIntegerInRange(this.rand, musicticker$musictype.getMinDelay(), musicticker$musictype.getMaxDelay()), this.timeUntilNextMusic);
/*    */       } 
/*    */     } 
/*    */     
/* 38 */     if (this.currentMusic == null && this.timeUntilNextMusic-- <= 0) {
/* 39 */       func_181558_a(musicticker$musictype);
/*    */     }
/*    */   }
/*    */   
/*    */   public void func_181558_a(MusicType p_181558_1_) {
/* 44 */     this.currentMusic = PositionedSoundRecord.create(p_181558_1_.getMusicLocation());
/* 45 */     this.mc.getSoundHandler().playSound(this.currentMusic);
/* 46 */     this.timeUntilNextMusic = Integer.MAX_VALUE;
/*    */   }
/*    */   
/*    */   public void func_181557_a() {
/* 50 */     if (this.currentMusic != null) {
/* 51 */       this.mc.getSoundHandler().stopSound(this.currentMusic);
/* 52 */       this.currentMusic = null;
/* 53 */       this.timeUntilNextMusic = 0;
/*    */     } 
/*    */   }
/*    */   
/*    */   public enum MusicType {
/* 58 */     MENU((String)new ResourceLocation("minecraft:music.menu"), 20, 600),
/* 59 */     GAME((String)new ResourceLocation("minecraft:music.game"), 12000, 24000),
/* 60 */     CREATIVE((String)new ResourceLocation("minecraft:music.game.creative"), 1200, 3600),
/* 61 */     CREDITS((String)new ResourceLocation("minecraft:music.game.end.credits"), 2147483647, 2147483647),
/* 62 */     NETHER((String)new ResourceLocation("minecraft:music.game.nether"), 1200, 3600),
/* 63 */     END_BOSS((String)new ResourceLocation("minecraft:music.game.end.dragon"), 0, 0),
/* 64 */     END((String)new ResourceLocation("minecraft:music.game.end"), 6000, 24000);
/*    */     
/*    */     private final ResourceLocation musicLocation;
/*    */     private final int minDelay;
/*    */     private final int maxDelay;
/*    */     
/*    */     MusicType(ResourceLocation location, int minDelayIn, int maxDelayIn) {
/* 71 */       this.musicLocation = location;
/* 72 */       this.minDelay = minDelayIn;
/* 73 */       this.maxDelay = maxDelayIn;
/*    */     }
/*    */     
/*    */     public ResourceLocation getMusicLocation() {
/* 77 */       return this.musicLocation;
/*    */     }
/*    */     
/*    */     public int getMinDelay() {
/* 81 */       return this.minDelay;
/*    */     }
/*    */     
/*    */     public int getMaxDelay() {
/* 85 */       return this.maxDelay;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\audio\MusicTicker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */