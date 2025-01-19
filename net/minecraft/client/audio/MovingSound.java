/*    */ package net.minecraft.client.audio;
/*    */ 
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public abstract class MovingSound extends PositionedSound implements ITickableSound {
/*    */   protected boolean donePlaying = false;
/*    */   
/*    */   protected MovingSound(ResourceLocation location) {
/*  9 */     super(location);
/*    */   }
/*    */   
/*    */   public boolean isDonePlaying() {
/* 13 */     return this.donePlaying;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\audio\MovingSound.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */