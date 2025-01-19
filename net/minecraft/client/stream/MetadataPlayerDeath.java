/*    */ package net.minecraft.client.stream;
/*    */ 
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ 
/*    */ public class MetadataPlayerDeath extends Metadata {
/*    */   public MetadataPlayerDeath(EntityLivingBase p_i46066_1_, EntityLivingBase p_i46066_2_) {
/*  7 */     super("player_death");
/*    */     
/*  9 */     if (p_i46066_1_ != null) {
/* 10 */       func_152808_a("player", p_i46066_1_.getName());
/*    */     }
/*    */     
/* 13 */     if (p_i46066_2_ != null)
/* 14 */       func_152808_a("killer", p_i46066_2_.getName()); 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\stream\MetadataPlayerDeath.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */