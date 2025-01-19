/*    */ package net.minecraft.entity.passive;
/*    */ 
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public abstract class EntityAmbientCreature extends EntityLiving implements IAnimals {
/*    */   public EntityAmbientCreature(World worldIn) {
/*  9 */     super(worldIn);
/*    */   }
/*    */   
/*    */   public boolean allowLeashing() {
/* 13 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean interact(EntityPlayer player) {
/* 20 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\passive\EntityAmbientCreature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */