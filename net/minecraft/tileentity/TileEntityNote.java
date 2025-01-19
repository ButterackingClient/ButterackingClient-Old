/*    */ package net.minecraft.tileentity;
/*    */ 
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TileEntityNote
/*    */   extends TileEntity
/*    */ {
/*    */   public byte note;
/*    */   public boolean previousRedstoneState;
/*    */   
/*    */   public void writeToNBT(NBTTagCompound compound) {
/* 22 */     super.writeToNBT(compound);
/* 23 */     compound.setByte("note", this.note);
/*    */   }
/*    */   
/*    */   public void readFromNBT(NBTTagCompound compound) {
/* 27 */     super.readFromNBT(compound);
/* 28 */     this.note = compound.getByte("note");
/* 29 */     this.note = (byte)MathHelper.clamp_int(this.note, 0, 24);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void changePitch() {
/* 36 */     this.note = (byte)((this.note + 1) % 25);
/* 37 */     markDirty();
/*    */   }
/*    */   
/*    */   public void triggerNote(World worldIn, BlockPos p_175108_2_) {
/* 41 */     if (worldIn.getBlockState(p_175108_2_.up()).getBlock().getMaterial() == Material.air) {
/* 42 */       Material material = worldIn.getBlockState(p_175108_2_.down()).getBlock().getMaterial();
/* 43 */       int i = 0;
/*    */       
/* 45 */       if (material == Material.rock) {
/* 46 */         i = 1;
/*    */       }
/*    */       
/* 49 */       if (material == Material.sand) {
/* 50 */         i = 2;
/*    */       }
/*    */       
/* 53 */       if (material == Material.glass) {
/* 54 */         i = 3;
/*    */       }
/*    */       
/* 57 */       if (material == Material.wood) {
/* 58 */         i = 4;
/*    */       }
/*    */       
/* 61 */       worldIn.addBlockEvent(p_175108_2_, Blocks.noteblock, i, this.note);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\tileentity\TileEntityNote.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */