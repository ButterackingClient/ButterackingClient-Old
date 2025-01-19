/*    */ package net.optifine.shaders.uniform;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import net.optifine.shaders.Shaders;
/*    */ import org.lwjgl.opengl.ARBShaderObjects;
/*    */ 
/*    */ public abstract class ShaderUniformBase
/*    */ {
/*    */   private String name;
/* 10 */   private int program = 0;
/* 11 */   private int[] locations = new int[] { -1 };
/*    */   private static final int LOCATION_UNDEFINED = -1;
/*    */   private static final int LOCATION_UNKNOWN = -2147483648;
/*    */   
/*    */   public ShaderUniformBase(String name) {
/* 16 */     this.name = name;
/*    */   }
/*    */   
/*    */   public void setProgram(int program) {
/* 20 */     if (this.program != program) {
/* 21 */       this.program = program;
/* 22 */       expandLocations();
/* 23 */       onProgramSet(program);
/*    */     } 
/*    */   }
/*    */   
/*    */   private void expandLocations() {
/* 28 */     if (this.program >= this.locations.length) {
/* 29 */       int[] aint = new int[this.program * 2];
/* 30 */       Arrays.fill(aint, -2147483648);
/* 31 */       System.arraycopy(this.locations, 0, aint, 0, this.locations.length);
/* 32 */       this.locations = aint;
/*    */     } 
/*    */   }
/*    */   
/*    */   protected abstract void onProgramSet(int paramInt);
/*    */   
/*    */   public String getName() {
/* 39 */     return this.name;
/*    */   }
/*    */   
/*    */   public int getProgram() {
/* 43 */     return this.program;
/*    */   }
/*    */   
/*    */   public int getLocation() {
/* 47 */     if (this.program <= 0) {
/* 48 */       return -1;
/*    */     }
/* 50 */     int i = this.locations[this.program];
/*    */     
/* 52 */     if (i == Integer.MIN_VALUE) {
/* 53 */       i = ARBShaderObjects.glGetUniformLocationARB(this.program, this.name);
/* 54 */       this.locations[this.program] = i;
/*    */     } 
/*    */     
/* 57 */     return i;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isDefined() {
/* 62 */     return (getLocation() >= 0);
/*    */   }
/*    */   
/*    */   public void disable() {
/* 66 */     this.locations[this.program] = -1;
/*    */   }
/*    */   
/*    */   public void reset() {
/* 70 */     this.program = 0;
/* 71 */     this.locations = new int[] { -1 };
/* 72 */     resetValue();
/*    */   }
/*    */   
/*    */   protected abstract void resetValue();
/*    */   
/*    */   protected void checkGLError() {
/* 78 */     if (Shaders.checkGLError(this.name) != 0) {
/* 79 */       disable();
/*    */     }
/*    */   }
/*    */   
/*    */   public String toString() {
/* 84 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shader\\uniform\ShaderUniformBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */