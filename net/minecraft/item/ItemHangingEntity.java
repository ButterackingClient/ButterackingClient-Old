/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityHanging;
/*    */ import net.minecraft.entity.item.EntityItemFrame;
/*    */ import net.minecraft.entity.item.EntityPainting;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemHangingEntity
/*    */   extends Item {
/*    */   public ItemHangingEntity(Class<? extends EntityHanging> entityClass) {
/* 16 */     this.hangingEntityClass = entityClass;
/* 17 */     setCreativeTab(CreativeTabs.tabDecorations);
/*    */   }
/*    */ 
/*    */   
/*    */   private final Class<? extends EntityHanging> hangingEntityClass;
/*    */   
/*    */   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 24 */     if (side == EnumFacing.DOWN)
/* 25 */       return false; 
/* 26 */     if (side == EnumFacing.UP) {
/* 27 */       return false;
/*    */     }
/* 29 */     BlockPos blockpos = pos.offset(side);
/*    */     
/* 31 */     if (!playerIn.canPlayerEdit(blockpos, side, stack)) {
/* 32 */       return false;
/*    */     }
/* 34 */     EntityHanging entityhanging = createEntity(worldIn, blockpos, side);
/*    */     
/* 36 */     if (entityhanging != null && entityhanging.onValidSurface()) {
/* 37 */       if (!worldIn.isRemote) {
/* 38 */         worldIn.spawnEntityInWorld((Entity)entityhanging);
/*    */       }
/*    */       
/* 41 */       stack.stackSize--;
/*    */     } 
/*    */     
/* 44 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private EntityHanging createEntity(World worldIn, BlockPos pos, EnumFacing clickedSide) {
/* 50 */     return (this.hangingEntityClass == EntityPainting.class) ? (EntityHanging)new EntityPainting(worldIn, pos, clickedSide) : ((this.hangingEntityClass == EntityItemFrame.class) ? (EntityHanging)new EntityItemFrame(worldIn, pos, clickedSide) : null);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemHangingEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */