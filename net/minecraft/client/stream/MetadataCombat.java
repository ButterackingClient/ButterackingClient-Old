/*    */ package net.minecraft.client.stream;
/*    */ 
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ 
/*    */ public class MetadataCombat extends Metadata {
/*    */   public MetadataCombat(EntityLivingBase p_i46067_1_, EntityLivingBase p_i46067_2_) {
/*  7 */     super("player_combat");
/*  8 */     func_152808_a("player", p_i46067_1_.getName());
/*    */     
/* 10 */     if (p_i46067_2_ != null) {
/* 11 */       func_152808_a("primary_opponent", p_i46067_2_.getName());
/*    */     }
/*    */     
/* 14 */     if (p_i46067_2_ != null) {
/* 15 */       func_152807_a("Combat between " + p_i46067_1_.getName() + " and " + p_i46067_2_.getName());
/*    */     } else {
/* 17 */       func_152807_a("Combat between " + p_i46067_1_.getName() + " and others");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\stream\MetadataCombat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */