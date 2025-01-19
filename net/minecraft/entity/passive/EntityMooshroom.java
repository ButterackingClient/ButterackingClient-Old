/*    */ package net.minecraft.entity.passive;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityAgeable;
/*    */ import net.minecraft.entity.item.EntityItem;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityMooshroom extends EntityCow {
/*    */   public EntityMooshroom(World worldIn) {
/* 14 */     super(worldIn);
/* 15 */     setSize(0.9F, 1.3F);
/* 16 */     this.spawnableBlock = (Block)Blocks.mycelium;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean interact(EntityPlayer player) {
/* 23 */     ItemStack itemstack = player.inventory.getCurrentItem();
/*    */     
/* 25 */     if (itemstack != null && itemstack.getItem() == Items.bowl && getGrowingAge() >= 0) {
/* 26 */       if (itemstack.stackSize == 1) {
/* 27 */         player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(Items.mushroom_stew));
/* 28 */         return true;
/*    */       } 
/*    */       
/* 31 */       if (player.inventory.addItemStackToInventory(new ItemStack(Items.mushroom_stew)) && !player.capabilities.isCreativeMode) {
/* 32 */         player.inventory.decrStackSize(player.inventory.currentItem, 1);
/* 33 */         return true;
/*    */       } 
/*    */     } 
/*    */     
/* 37 */     if (itemstack != null && itemstack.getItem() == Items.shears && getGrowingAge() >= 0) {
/* 38 */       setDead();
/* 39 */       this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX, this.posY + (this.height / 2.0F), this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
/*    */       
/* 41 */       if (!this.worldObj.isRemote) {
/* 42 */         EntityCow entitycow = new EntityCow(this.worldObj);
/* 43 */         entitycow.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
/* 44 */         entitycow.setHealth(getHealth());
/* 45 */         entitycow.renderYawOffset = this.renderYawOffset;
/*    */         
/* 47 */         if (hasCustomName()) {
/* 48 */           entitycow.setCustomNameTag(getCustomNameTag());
/*    */         }
/*    */         
/* 51 */         this.worldObj.spawnEntityInWorld((Entity)entitycow);
/*    */         
/* 53 */         for (int i = 0; i < 5; i++) {
/* 54 */           this.worldObj.spawnEntityInWorld((Entity)new EntityItem(this.worldObj, this.posX, this.posY + this.height, this.posZ, new ItemStack((Block)Blocks.red_mushroom)));
/*    */         }
/*    */         
/* 57 */         itemstack.damageItem(1, (EntityLivingBase)player);
/* 58 */         playSound("mob.sheep.shear", 1.0F, 1.0F);
/*    */       } 
/*    */       
/* 61 */       return true;
/*    */     } 
/* 63 */     return super.interact(player);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityMooshroom createChild(EntityAgeable ageable) {
/* 68 */     return new EntityMooshroom(this.worldObj);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\passive\EntityMooshroom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */