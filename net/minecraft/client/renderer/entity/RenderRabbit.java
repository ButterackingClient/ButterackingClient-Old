/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.passive.EntityRabbit;
/*    */ import net.minecraft.util.EnumChatFormatting;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderRabbit extends RenderLiving<EntityRabbit> {
/*  9 */   private static final ResourceLocation BROWN = new ResourceLocation("textures/entity/rabbit/brown.png");
/* 10 */   private static final ResourceLocation WHITE = new ResourceLocation("textures/entity/rabbit/white.png");
/* 11 */   private static final ResourceLocation BLACK = new ResourceLocation("textures/entity/rabbit/black.png");
/* 12 */   private static final ResourceLocation GOLD = new ResourceLocation("textures/entity/rabbit/gold.png");
/* 13 */   private static final ResourceLocation SALT = new ResourceLocation("textures/entity/rabbit/salt.png");
/* 14 */   private static final ResourceLocation WHITE_SPLOTCHED = new ResourceLocation("textures/entity/rabbit/white_splotched.png");
/* 15 */   private static final ResourceLocation TOAST = new ResourceLocation("textures/entity/rabbit/toast.png");
/* 16 */   private static final ResourceLocation CAERBANNOG = new ResourceLocation("textures/entity/rabbit/caerbannog.png");
/*    */   
/*    */   public RenderRabbit(RenderManager renderManagerIn, ModelBase modelBaseIn, float shadowSizeIn) {
/* 19 */     super(renderManagerIn, modelBaseIn, shadowSizeIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityRabbit entity) {
/* 26 */     String s = EnumChatFormatting.getTextWithoutFormattingCodes(entity.getName());
/*    */     
/* 28 */     if (s != null && s.equals("Toast")) {
/* 29 */       return TOAST;
/*    */     }
/* 31 */     switch (entity.getRabbitType()) {
/*    */       
/*    */       default:
/* 34 */         return BROWN;
/*    */       
/*    */       case 1:
/* 37 */         return WHITE;
/*    */       
/*    */       case 2:
/* 40 */         return BLACK;
/*    */       
/*    */       case 3:
/* 43 */         return WHITE_SPLOTCHED;
/*    */       
/*    */       case 4:
/* 46 */         return GOLD;
/*    */       
/*    */       case 5:
/* 49 */         return SALT;
/*    */       case 99:
/*    */         break;
/* 52 */     }  return CAERBANNOG;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\RenderRabbit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */