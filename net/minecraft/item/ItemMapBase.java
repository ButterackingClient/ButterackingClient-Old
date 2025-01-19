/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ public class ItemMapBase
/*    */   extends Item
/*    */ {
/*    */   public boolean isMap() {
/* 12 */     return true;
/*    */   }
/*    */   
/*    */   public Packet createMapDataPacket(ItemStack stack, World worldIn, EntityPlayer player) {
/* 16 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemMapBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */