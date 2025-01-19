/*    */ package net.minecraft.block.state;
/*    */ 
/*    */ import com.google.common.base.Predicate;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockWorldState {
/*    */   private final World world;
/*    */   private final BlockPos pos;
/*    */   private final boolean field_181628_c;
/*    */   private IBlockState state;
/*    */   private TileEntity tileEntity;
/*    */   private boolean tileEntityInitialized;
/*    */   
/*    */   public BlockWorldState(World worldIn, BlockPos posIn, boolean p_i46451_3_) {
/* 17 */     this.world = worldIn;
/* 18 */     this.pos = posIn;
/* 19 */     this.field_181628_c = p_i46451_3_;
/*    */   }
/*    */   
/*    */   public IBlockState getBlockState() {
/* 23 */     if (this.state == null && (this.field_181628_c || this.world.isBlockLoaded(this.pos))) {
/* 24 */       this.state = this.world.getBlockState(this.pos);
/*    */     }
/*    */     
/* 27 */     return this.state;
/*    */   }
/*    */   
/*    */   public TileEntity getTileEntity() {
/* 31 */     if (this.tileEntity == null && !this.tileEntityInitialized) {
/* 32 */       this.tileEntity = this.world.getTileEntity(this.pos);
/* 33 */       this.tileEntityInitialized = true;
/*    */     } 
/*    */     
/* 36 */     return this.tileEntity;
/*    */   }
/*    */   
/*    */   public BlockPos getPos() {
/* 40 */     return this.pos;
/*    */   }
/*    */   
/*    */   public static Predicate<BlockWorldState> hasState(final Predicate<IBlockState> predicatesIn) {
/* 44 */     return new Predicate<BlockWorldState>() {
/*    */         public boolean apply(BlockWorldState p_apply_1_) {
/* 46 */           return (p_apply_1_ != null && predicatesIn.apply(p_apply_1_.getBlockState()));
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\state\BlockWorldState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */