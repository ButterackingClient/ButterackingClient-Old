/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ 
/*    */ 
/*    */ public class EntitySenses
/*    */ {
/*    */   EntityLiving entityObj;
/* 12 */   List<Entity> seenEntities = Lists.newArrayList();
/* 13 */   List<Entity> unseenEntities = Lists.newArrayList();
/*    */   
/*    */   public EntitySenses(EntityLiving entityObjIn) {
/* 16 */     this.entityObj = entityObjIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void clearSensingCache() {
/* 23 */     this.seenEntities.clear();
/* 24 */     this.unseenEntities.clear();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canSee(Entity entityIn) {
/* 31 */     if (this.seenEntities.contains(entityIn))
/* 32 */       return true; 
/* 33 */     if (this.unseenEntities.contains(entityIn)) {
/* 34 */       return false;
/*    */     }
/* 36 */     this.entityObj.worldObj.theProfiler.startSection("canSee");
/* 37 */     boolean flag = this.entityObj.canEntityBeSeen(entityIn);
/* 38 */     this.entityObj.worldObj.theProfiler.endSection();
/*    */     
/* 40 */     if (flag) {
/* 41 */       this.seenEntities.add(entityIn);
/*    */     } else {
/* 43 */       this.unseenEntities.add(entityIn);
/*    */     } 
/*    */     
/* 46 */     return flag;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntitySenses.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */