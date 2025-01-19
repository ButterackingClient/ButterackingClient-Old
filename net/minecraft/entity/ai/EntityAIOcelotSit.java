/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockBed;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.EntityCreature;
/*    */ import net.minecraft.entity.passive.EntityOcelot;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.tileentity.TileEntityChest;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityAIOcelotSit extends EntityAIMoveToBlock {
/*    */   public EntityAIOcelotSit(EntityOcelot ocelotIn, double p_i45315_2_) {
/* 17 */     super((EntityCreature)ocelotIn, p_i45315_2_, 8);
/* 18 */     this.ocelot = ocelotIn;
/*    */   }
/*    */ 
/*    */   
/*    */   private final EntityOcelot ocelot;
/*    */   
/*    */   public boolean shouldExecute() {
/* 25 */     return (this.ocelot.isTamed() && !this.ocelot.isSitting() && super.shouldExecute());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean continueExecuting() {
/* 32 */     return super.continueExecuting();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 39 */     super.startExecuting();
/* 40 */     this.ocelot.getAISit().setSitting(false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void resetTask() {
/* 47 */     super.resetTask();
/* 48 */     this.ocelot.setSitting(false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateTask() {
/* 55 */     super.updateTask();
/* 56 */     this.ocelot.getAISit().setSitting(false);
/*    */     
/* 58 */     if (!getIsAboveDestination()) {
/* 59 */       this.ocelot.setSitting(false);
/* 60 */     } else if (!this.ocelot.isSitting()) {
/* 61 */       this.ocelot.setSitting(true);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean shouldMoveTo(World worldIn, BlockPos pos) {
/* 69 */     if (!worldIn.isAirBlock(pos.up())) {
/* 70 */       return false;
/*    */     }
/* 72 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/* 73 */     Block block = iblockstate.getBlock();
/*    */     
/* 75 */     if (block == Blocks.chest) {
/* 76 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*    */       
/* 78 */       if (tileentity instanceof TileEntityChest && ((TileEntityChest)tileentity).numPlayersUsing < 1) {
/* 79 */         return true;
/*    */       }
/*    */     } else {
/* 82 */       if (block == Blocks.lit_furnace) {
/* 83 */         return true;
/*    */       }
/*    */       
/* 86 */       if (block == Blocks.bed && iblockstate.getValue((IProperty)BlockBed.PART) != BlockBed.EnumPartType.HEAD) {
/* 87 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 91 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAIOcelotSit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */