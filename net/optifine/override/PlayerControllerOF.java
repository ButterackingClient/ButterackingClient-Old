/*    */ package net.optifine.override;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.entity.EntityPlayerSP;
/*    */ import net.minecraft.client.multiplayer.PlayerControllerMP;
/*    */ import net.minecraft.client.multiplayer.WorldClient;
/*    */ import net.minecraft.client.network.NetHandlerPlayClient;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.MovingObjectPosition;
/*    */ import net.minecraft.util.Vec3;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class PlayerControllerOF extends PlayerControllerMP {
/*    */   private boolean acting = false;
/* 19 */   private BlockPos lastClickBlockPos = null;
/* 20 */   private Entity lastClickEntity = null;
/*    */   
/*    */   public PlayerControllerOF(Minecraft mcIn, NetHandlerPlayClient netHandler) {
/* 23 */     super(mcIn, netHandler);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean clickBlock(BlockPos loc, EnumFacing face) {
/* 30 */     this.acting = true;
/* 31 */     this.lastClickBlockPos = loc;
/* 32 */     boolean flag = super.clickBlock(loc, face);
/* 33 */     this.acting = false;
/* 34 */     return flag;
/*    */   }
/*    */   
/*    */   public boolean onPlayerDamageBlock(BlockPos posBlock, EnumFacing directionFacing) {
/* 38 */     this.acting = true;
/* 39 */     this.lastClickBlockPos = posBlock;
/* 40 */     boolean flag = super.onPlayerDamageBlock(posBlock, directionFacing);
/* 41 */     this.acting = false;
/* 42 */     return flag;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean sendUseItem(EntityPlayer player, World worldIn, ItemStack stack) {
/* 49 */     this.acting = true;
/* 50 */     boolean flag = super.sendUseItem(player, worldIn, stack);
/* 51 */     this.acting = false;
/* 52 */     return flag;
/*    */   }
/*    */   
/*    */   public boolean onPlayerRightClick(EntityPlayerSP p_178890_1, WorldClient p_178890_2, ItemStack p_178890_3, BlockPos p_178890_4, EnumFacing p_178890_5, Vec3 p_178890_6) {
/* 56 */     this.acting = true;
/* 57 */     this.lastClickBlockPos = p_178890_4;
/* 58 */     boolean flag = super.onPlayerRightClick(p_178890_1, p_178890_2, p_178890_3, p_178890_4, p_178890_5, p_178890_6);
/* 59 */     this.acting = false;
/* 60 */     return flag;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean interactWithEntitySendPacket(EntityPlayer player, Entity target) {
/* 67 */     this.lastClickEntity = target;
/* 68 */     return super.interactWithEntitySendPacket(player, target);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPlayerRightClickingOnEntity(EntityPlayer player, Entity target, MovingObjectPosition ray) {
/* 79 */     this.lastClickEntity = target;
/* 80 */     return super.isPlayerRightClickingOnEntity(player, target, ray);
/*    */   }
/*    */   
/*    */   public boolean isActing() {
/* 84 */     return this.acting;
/*    */   }
/*    */   
/*    */   public BlockPos getLastClickBlockPos() {
/* 88 */     return this.lastClickBlockPos;
/*    */   }
/*    */   
/*    */   public Entity getLastClickEntity() {
/* 92 */     return this.lastClickEntity;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\override\PlayerControllerOF.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */