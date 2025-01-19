/*     */ package net.minecraft.tileentity;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ITickable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileEntityPiston
/*     */   extends TileEntity
/*     */   implements ITickable
/*     */ {
/*     */   private IBlockState pistonState;
/*     */   private EnumFacing pistonFacing;
/*     */   private boolean extending;
/*     */   private boolean shouldHeadBeRendered;
/*     */   private float progress;
/*     */   private float lastProgress;
/*  31 */   private List<Entity> field_174933_k = Lists.newArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntityPiston(IBlockState pistonStateIn, EnumFacing pistonFacingIn, boolean extendingIn, boolean shouldHeadBeRenderedIn) {
/*  37 */     this.pistonState = pistonStateIn;
/*  38 */     this.pistonFacing = pistonFacingIn;
/*  39 */     this.extending = extendingIn;
/*  40 */     this.shouldHeadBeRendered = shouldHeadBeRenderedIn;
/*     */   }
/*     */   
/*     */   public IBlockState getPistonState() {
/*  44 */     return this.pistonState;
/*     */   }
/*     */   
/*     */   public int getBlockMetadata() {
/*  48 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isExtending() {
/*  55 */     return this.extending;
/*     */   }
/*     */   
/*     */   public EnumFacing getFacing() {
/*  59 */     return this.pistonFacing;
/*     */   }
/*     */   
/*     */   public boolean shouldPistonHeadBeRendered() {
/*  63 */     return this.shouldHeadBeRendered;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getProgress(float ticks) {
/*  71 */     if (ticks > 1.0F) {
/*  72 */       ticks = 1.0F;
/*     */     }
/*     */     
/*  75 */     return this.lastProgress + (this.progress - this.lastProgress) * ticks;
/*     */   }
/*     */   
/*     */   public float getOffsetX(float ticks) {
/*  79 */     return this.extending ? ((getProgress(ticks) - 1.0F) * this.pistonFacing.getFrontOffsetX()) : ((1.0F - getProgress(ticks)) * this.pistonFacing.getFrontOffsetX());
/*     */   }
/*     */   
/*     */   public float getOffsetY(float ticks) {
/*  83 */     return this.extending ? ((getProgress(ticks) - 1.0F) * this.pistonFacing.getFrontOffsetY()) : ((1.0F - getProgress(ticks)) * this.pistonFacing.getFrontOffsetY());
/*     */   }
/*     */   
/*     */   public float getOffsetZ(float ticks) {
/*  87 */     return this.extending ? ((getProgress(ticks) - 1.0F) * this.pistonFacing.getFrontOffsetZ()) : ((1.0F - getProgress(ticks)) * this.pistonFacing.getFrontOffsetZ());
/*     */   }
/*     */   
/*     */   private void launchWithSlimeBlock(float p_145863_1_, float p_145863_2_) {
/*  91 */     if (this.extending) {
/*  92 */       p_145863_1_ = 1.0F - p_145863_1_;
/*     */     } else {
/*  94 */       p_145863_1_--;
/*     */     } 
/*     */     
/*  97 */     AxisAlignedBB axisalignedbb = Blocks.piston_extension.getBoundingBox(this.worldObj, this.pos, this.pistonState, p_145863_1_, this.pistonFacing);
/*     */     
/*  99 */     if (axisalignedbb != null) {
/* 100 */       List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(null, axisalignedbb);
/*     */       
/* 102 */       if (!list.isEmpty()) {
/* 103 */         this.field_174933_k.addAll(list);
/*     */         
/* 105 */         for (Entity entity : this.field_174933_k) {
/* 106 */           if (this.pistonState.getBlock() == Blocks.slime_block && this.extending) {
/* 107 */             switch (this.pistonFacing.getAxis()) {
/*     */               case null:
/* 109 */                 entity.motionX = this.pistonFacing.getFrontOffsetX();
/*     */                 continue;
/*     */               
/*     */               case Y:
/* 113 */                 entity.motionY = this.pistonFacing.getFrontOffsetY();
/*     */                 continue;
/*     */               
/*     */               case Z:
/* 117 */                 entity.motionZ = this.pistonFacing.getFrontOffsetZ(); continue;
/*     */             }  continue;
/*     */           } 
/* 120 */           entity.moveEntity((p_145863_2_ * this.pistonFacing.getFrontOffsetX()), (p_145863_2_ * this.pistonFacing.getFrontOffsetY()), (p_145863_2_ * this.pistonFacing.getFrontOffsetZ()));
/*     */         } 
/*     */ 
/*     */         
/* 124 */         this.field_174933_k.clear();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearPistonTileEntity() {
/* 133 */     if (this.lastProgress < 1.0F && this.worldObj != null) {
/* 134 */       this.lastProgress = this.progress = 1.0F;
/* 135 */       this.worldObj.removeTileEntity(this.pos);
/* 136 */       invalidate();
/*     */       
/* 138 */       if (this.worldObj.getBlockState(this.pos).getBlock() == Blocks.piston_extension) {
/* 139 */         this.worldObj.setBlockState(this.pos, this.pistonState, 3);
/* 140 */         this.worldObj.notifyBlockOfStateChange(this.pos, this.pistonState.getBlock());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/* 149 */     this.lastProgress = this.progress;
/*     */     
/* 151 */     if (this.lastProgress >= 1.0F) {
/* 152 */       launchWithSlimeBlock(1.0F, 0.25F);
/* 153 */       this.worldObj.removeTileEntity(this.pos);
/* 154 */       invalidate();
/*     */       
/* 156 */       if (this.worldObj.getBlockState(this.pos).getBlock() == Blocks.piston_extension) {
/* 157 */         this.worldObj.setBlockState(this.pos, this.pistonState, 3);
/* 158 */         this.worldObj.notifyBlockOfStateChange(this.pos, this.pistonState.getBlock());
/*     */       } 
/*     */     } else {
/* 161 */       this.progress += 0.5F;
/*     */       
/* 163 */       if (this.progress >= 1.0F) {
/* 164 */         this.progress = 1.0F;
/*     */       }
/*     */       
/* 167 */       if (this.extending) {
/* 168 */         launchWithSlimeBlock(this.progress, this.progress - this.lastProgress + 0.0625F);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/* 174 */     super.readFromNBT(compound);
/* 175 */     this.pistonState = Block.getBlockById(compound.getInteger("blockId")).getStateFromMeta(compound.getInteger("blockData"));
/* 176 */     this.pistonFacing = EnumFacing.getFront(compound.getInteger("facing"));
/* 177 */     this.lastProgress = this.progress = compound.getFloat("progress");
/* 178 */     this.extending = compound.getBoolean("extending");
/*     */   }
/*     */   
/*     */   public void writeToNBT(NBTTagCompound compound) {
/* 182 */     super.writeToNBT(compound);
/* 183 */     compound.setInteger("blockId", Block.getIdFromBlock(this.pistonState.getBlock()));
/* 184 */     compound.setInteger("blockData", this.pistonState.getBlock().getMetaFromState(this.pistonState));
/* 185 */     compound.setInteger("facing", this.pistonFacing.getIndex());
/* 186 */     compound.setFloat("progress", this.lastProgress);
/* 187 */     compound.setBoolean("extending", this.extending);
/*     */   }
/*     */   
/*     */   public TileEntityPiston() {}
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\tileentity\TileEntityPiston.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */