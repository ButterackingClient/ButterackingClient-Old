/*    */ package net.minecraft.entity.player;
/*    */ 
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerCapabilities
/*    */ {
/*    */   public boolean disableDamage;
/*    */   public boolean isFlying;
/*    */   public boolean allowFlying;
/*    */   public boolean isCreativeMode;
/*    */   public boolean allowEdit = true;
/* 30 */   private float flySpeed = 0.05F;
/* 31 */   private float walkSpeed = 0.1F;
/*    */   
/*    */   public void writeCapabilitiesToNBT(NBTTagCompound tagCompound) {
/* 34 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 35 */     nbttagcompound.setBoolean("invulnerable", this.disableDamage);
/* 36 */     nbttagcompound.setBoolean("flying", this.isFlying);
/* 37 */     nbttagcompound.setBoolean("mayfly", this.allowFlying);
/* 38 */     nbttagcompound.setBoolean("instabuild", this.isCreativeMode);
/* 39 */     nbttagcompound.setBoolean("mayBuild", this.allowEdit);
/* 40 */     nbttagcompound.setFloat("flySpeed", this.flySpeed);
/* 41 */     nbttagcompound.setFloat("walkSpeed", this.walkSpeed);
/* 42 */     tagCompound.setTag("abilities", (NBTBase)nbttagcompound);
/*    */   }
/*    */   
/*    */   public void readCapabilitiesFromNBT(NBTTagCompound tagCompound) {
/* 46 */     if (tagCompound.hasKey("abilities", 10)) {
/* 47 */       NBTTagCompound nbttagcompound = tagCompound.getCompoundTag("abilities");
/* 48 */       this.disableDamage = nbttagcompound.getBoolean("invulnerable");
/* 49 */       this.isFlying = nbttagcompound.getBoolean("flying");
/* 50 */       this.allowFlying = nbttagcompound.getBoolean("mayfly");
/* 51 */       this.isCreativeMode = nbttagcompound.getBoolean("instabuild");
/*    */       
/* 53 */       if (nbttagcompound.hasKey("flySpeed", 99)) {
/* 54 */         this.flySpeed = nbttagcompound.getFloat("flySpeed");
/* 55 */         this.walkSpeed = nbttagcompound.getFloat("walkSpeed");
/*    */       } 
/*    */       
/* 58 */       if (nbttagcompound.hasKey("mayBuild", 1)) {
/* 59 */         this.allowEdit = nbttagcompound.getBoolean("mayBuild");
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public float getFlySpeed() {
/* 65 */     return this.flySpeed;
/*    */   }
/*    */   
/*    */   public void setFlySpeed(float speed) {
/* 69 */     this.flySpeed = speed;
/*    */   }
/*    */   
/*    */   public float getWalkSpeed() {
/* 73 */     return this.walkSpeed;
/*    */   }
/*    */   
/*    */   public void setPlayerWalkSpeed(float speed) {
/* 77 */     this.walkSpeed = speed;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\player\PlayerCapabilities.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */