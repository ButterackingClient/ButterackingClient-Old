/*    */ package net.minecraft.entity.item;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockChest;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.inventory.ContainerChest;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.util.DamageSource;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityMinecartChest extends EntityMinecartContainer {
/*    */   public EntityMinecartChest(World worldIn) {
/* 17 */     super(worldIn);
/*    */   }
/*    */   
/*    */   public EntityMinecartChest(World worldIn, double x, double y, double z) {
/* 21 */     super(worldIn, x, y, z);
/*    */   }
/*    */   
/*    */   public void killMinecart(DamageSource source) {
/* 25 */     super.killMinecart(source);
/*    */     
/* 27 */     if (this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
/* 28 */       dropItemWithOffset(Item.getItemFromBlock((Block)Blocks.chest), 1, 0.0F);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getSizeInventory() {
/* 36 */     return 27;
/*    */   }
/*    */   
/*    */   public EntityMinecart.EnumMinecartType getMinecartType() {
/* 40 */     return EntityMinecart.EnumMinecartType.CHEST;
/*    */   }
/*    */   
/*    */   public IBlockState getDefaultDisplayTile() {
/* 44 */     return Blocks.chest.getDefaultState().withProperty((IProperty)BlockChest.FACING, (Comparable)EnumFacing.NORTH);
/*    */   }
/*    */   
/*    */   public int getDefaultDisplayTileOffset() {
/* 48 */     return 8;
/*    */   }
/*    */   
/*    */   public String getGuiID() {
/* 52 */     return "minecraft:chest";
/*    */   }
/*    */   
/*    */   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
/* 56 */     return (Container)new ContainerChest((IInventory)playerInventory, (IInventory)this, playerIn);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\item\EntityMinecartChest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */