/*    */ package net.optifine.reflect;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ReflectorResolver {
/*  8 */   private static final List<IResolvable> RESOLVABLES = Collections.synchronizedList(new ArrayList<>());
/*    */   private static boolean resolved = false;
/*    */   
/*    */   protected static void register(IResolvable resolvable) {
/* 12 */     if (!resolved) {
/* 13 */       RESOLVABLES.add(resolvable);
/*    */     } else {
/* 15 */       resolvable.resolve();
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void resolve() {
/* 20 */     if (!resolved) {
/* 21 */       for (IResolvable iresolvable : RESOLVABLES) {
/* 22 */         iresolvable.resolve();
/*    */       }
/*    */       
/* 25 */       resolved = true;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\reflect\ReflectorResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */