/*    */ package net.minecraft.entity.ai.attributes;
/*    */ 
/*    */ import io.netty.util.internal.ThreadLocalRandom;
/*    */ import java.util.Random;
/*    */ import java.util.UUID;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import org.apache.commons.lang3.Validate;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AttributeModifier
/*    */ {
/*    */   private final double amount;
/*    */   private final int operation;
/*    */   private final String name;
/*    */   private final UUID id;
/*    */   private boolean isSaved;
/*    */   
/*    */   public AttributeModifier(String nameIn, double amountIn, int operationIn) {
/* 22 */     this(MathHelper.getRandomUuid((Random)ThreadLocalRandom.current()), nameIn, amountIn, operationIn);
/*    */   }
/*    */   
/*    */   public AttributeModifier(UUID idIn, String nameIn, double amountIn, int operationIn) {
/* 26 */     this.isSaved = true;
/* 27 */     this.id = idIn;
/* 28 */     this.name = nameIn;
/* 29 */     this.amount = amountIn;
/* 30 */     this.operation = operationIn;
/* 31 */     Validate.notEmpty(nameIn, "Modifier name cannot be empty", new Object[0]);
/* 32 */     Validate.inclusiveBetween(0L, 2L, operationIn, "Invalid operation");
/*    */   }
/*    */   
/*    */   public UUID getID() {
/* 36 */     return this.id;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 40 */     return this.name;
/*    */   }
/*    */   
/*    */   public int getOperation() {
/* 44 */     return this.operation;
/*    */   }
/*    */   
/*    */   public double getAmount() {
/* 48 */     return this.amount;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isSaved() {
/* 55 */     return this.isSaved;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AttributeModifier setSaved(boolean saved) {
/* 62 */     this.isSaved = saved;
/* 63 */     return this;
/*    */   }
/*    */   
/*    */   public boolean equals(Object p_equals_1_) {
/* 67 */     if (this == p_equals_1_)
/* 68 */       return true; 
/* 69 */     if (p_equals_1_ != null && getClass() == p_equals_1_.getClass()) {
/* 70 */       AttributeModifier attributemodifier = (AttributeModifier)p_equals_1_;
/*    */       
/* 72 */       if (this.id != null) {
/* 73 */         if (!this.id.equals(attributemodifier.id)) {
/* 74 */           return false;
/*    */         }
/* 76 */       } else if (attributemodifier.id != null) {
/* 77 */         return false;
/*    */       } 
/*    */       
/* 80 */       return true;
/*    */     } 
/* 82 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 87 */     return (this.id != null) ? this.id.hashCode() : 0;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 91 */     return "AttributeModifier{amount=" + this.amount + ", operation=" + this.operation + ", name='" + this.name + '\'' + ", id=" + this.id + ", serialize=" + this.isSaved + '}';
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\attributes\AttributeModifier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */