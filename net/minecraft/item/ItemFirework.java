/*    */ package net.minecraft.item;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityFireworkRocket;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.nbt.NBTTagList;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.StatCollector;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemFirework
/*    */   extends Item
/*    */ {
/*    */   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 21 */     if (!worldIn.isRemote) {
/* 22 */       EntityFireworkRocket entityfireworkrocket = new EntityFireworkRocket(worldIn, (pos.getX() + hitX), (pos.getY() + hitY), (pos.getZ() + hitZ), stack);
/* 23 */       worldIn.spawnEntityInWorld((Entity)entityfireworkrocket);
/*    */       
/* 25 */       if (!playerIn.capabilities.isCreativeMode) {
/* 26 */         stack.stackSize--;
/*    */       }
/*    */       
/* 29 */       return true;
/*    */     } 
/* 31 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
/* 39 */     if (stack.hasTagCompound()) {
/* 40 */       NBTTagCompound nbttagcompound = stack.getTagCompound().getCompoundTag("Fireworks");
/*    */       
/* 42 */       if (nbttagcompound != null) {
/* 43 */         if (nbttagcompound.hasKey("Flight", 99)) {
/* 44 */           tooltip.add(String.valueOf(StatCollector.translateToLocal("item.fireworks.flight")) + " " + nbttagcompound.getByte("Flight"));
/*    */         }
/*    */         
/* 47 */         NBTTagList nbttaglist = nbttagcompound.getTagList("Explosions", 10);
/*    */         
/* 49 */         if (nbttaglist != null && nbttaglist.tagCount() > 0)
/* 50 */           for (int i = 0; i < nbttaglist.tagCount(); i++) {
/* 51 */             NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
/* 52 */             List<String> list = Lists.newArrayList();
/* 53 */             ItemFireworkCharge.addExplosionInfo(nbttagcompound1, list);
/*    */             
/* 55 */             if (list.size() > 0) {
/* 56 */               for (int j = 1; j < list.size(); j++) {
/* 57 */                 list.set(j, "  " + (String)list.get(j));
/*    */               }
/*    */               
/* 60 */               tooltip.addAll(list);
/*    */             } 
/*    */           }  
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemFirework.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */