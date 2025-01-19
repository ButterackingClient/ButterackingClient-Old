/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.item.EntityItem;
/*    */ import net.minecraft.entity.passive.EntityVillager;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.inventory.InventoryBasic;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ public class EntityAIVillagerInteract
/*    */   extends EntityAIWatchClosest2 {
/*    */   private int interactionDelay;
/*    */   private EntityVillager villager;
/*    */   
/*    */   public EntityAIVillagerInteract(EntityVillager villagerIn) {
/* 19 */     super((EntityLiving)villagerIn, (Class)EntityVillager.class, 3.0F, 0.02F);
/* 20 */     this.villager = villagerIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 27 */     super.startExecuting();
/*    */     
/* 29 */     if (this.villager.canAbondonItems() && this.closestEntity instanceof EntityVillager && ((EntityVillager)this.closestEntity).func_175557_cr()) {
/* 30 */       this.interactionDelay = 10;
/*    */     } else {
/* 32 */       this.interactionDelay = 0;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateTask() {
/* 40 */     super.updateTask();
/*    */     
/* 42 */     if (this.interactionDelay > 0) {
/* 43 */       this.interactionDelay--;
/*    */       
/* 45 */       if (this.interactionDelay == 0) {
/* 46 */         InventoryBasic inventorybasic = this.villager.getVillagerInventory();
/*    */         
/* 48 */         for (int i = 0; i < inventorybasic.getSizeInventory(); i++) {
/* 49 */           ItemStack itemstack = inventorybasic.getStackInSlot(i);
/* 50 */           ItemStack itemstack1 = null;
/*    */           
/* 52 */           if (itemstack != null) {
/* 53 */             Item item = itemstack.getItem();
/*    */             
/* 55 */             if ((item == Items.bread || item == Items.potato || item == Items.carrot) && itemstack.stackSize > 3) {
/* 56 */               int l = itemstack.stackSize / 2;
/* 57 */               itemstack.stackSize -= l;
/* 58 */               itemstack1 = new ItemStack(item, l, itemstack.getMetadata());
/* 59 */             } else if (item == Items.wheat && itemstack.stackSize > 5) {
/* 60 */               int j = itemstack.stackSize / 2 / 3 * 3;
/* 61 */               int k = j / 3;
/* 62 */               itemstack.stackSize -= j;
/* 63 */               itemstack1 = new ItemStack(Items.bread, k, 0);
/*    */             } 
/*    */             
/* 66 */             if (itemstack.stackSize <= 0) {
/* 67 */               inventorybasic.setInventorySlotContents(i, null);
/*    */             }
/*    */           } 
/*    */           
/* 71 */           if (itemstack1 != null) {
/* 72 */             double d0 = this.villager.posY - 0.30000001192092896D + this.villager.getEyeHeight();
/* 73 */             EntityItem entityitem = new EntityItem(this.villager.worldObj, this.villager.posX, d0, this.villager.posZ, itemstack1);
/* 74 */             float f = 0.3F;
/* 75 */             float f1 = this.villager.rotationYawHead;
/* 76 */             float f2 = this.villager.rotationPitch;
/* 77 */             entityitem.motionX = (-MathHelper.sin(f1 / 180.0F * 3.1415927F) * MathHelper.cos(f2 / 180.0F * 3.1415927F) * f);
/* 78 */             entityitem.motionZ = (MathHelper.cos(f1 / 180.0F * 3.1415927F) * MathHelper.cos(f2 / 180.0F * 3.1415927F) * f);
/* 79 */             entityitem.motionY = (-MathHelper.sin(f2 / 180.0F * 3.1415927F) * f + 0.1F);
/* 80 */             entityitem.setDefaultPickupDelay();
/* 81 */             this.villager.worldObj.spawnEntityInWorld((Entity)entityitem);
/*    */             break;
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAIVillagerInteract.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */