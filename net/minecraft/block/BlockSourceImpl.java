/*    */ package net.minecraft.block;
/*    */ 
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.dispenser.IBlockSource;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockSourceImpl
/*    */   implements IBlockSource {
/*    */   private final World worldObj;
/*    */   private final BlockPos pos;
/*    */   
/*    */   public BlockSourceImpl(World worldIn, BlockPos posIn) {
/* 14 */     this.worldObj = worldIn;
/* 15 */     this.pos = posIn;
/*    */   }
/*    */   
/*    */   public World getWorld() {
/* 19 */     return this.worldObj;
/*    */   }
/*    */   
/*    */   public double getX() {
/* 23 */     return this.pos.getX() + 0.5D;
/*    */   }
/*    */   
/*    */   public double getY() {
/* 27 */     return this.pos.getY() + 0.5D;
/*    */   }
/*    */   
/*    */   public double getZ() {
/* 31 */     return this.pos.getZ() + 0.5D;
/*    */   }
/*    */   
/*    */   public BlockPos getBlockPos() {
/* 35 */     return this.pos;
/*    */   }
/*    */   
/*    */   public int getBlockMetadata() {
/* 39 */     IBlockState iblockstate = this.worldObj.getBlockState(this.pos);
/* 40 */     return iblockstate.getBlock().getMetaFromState(iblockstate);
/*    */   }
/*    */   
/*    */   public <T extends net.minecraft.tileentity.TileEntity> T getBlockTileEntity() {
/* 44 */     return (T)this.worldObj.getTileEntity(this.pos);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockSourceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */