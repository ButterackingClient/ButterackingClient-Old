/*    */ package net.optifine.reflect;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ import java.util.Arrays;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.src.Config;
/*    */ 
/*    */ public class FieldLocatorActionKeyF3
/*    */   implements IFieldLocator {
/*    */   public Field getField() {
/* 13 */     Class<Minecraft> oclass = Minecraft.class;
/* 14 */     Field field = getFieldRenderChunksMany();
/*    */     
/* 16 */     if (field == null) {
/* 17 */       Config.log("(Reflector) Field not present: " + oclass.getName() + ".actionKeyF3 (field renderChunksMany not found)");
/* 18 */       return null;
/*    */     } 
/* 20 */     Field field1 = ReflectorRaw.getFieldAfter(Minecraft.class, field, boolean.class, 0);
/*    */     
/* 22 */     if (field1 == null) {
/* 23 */       Config.log("(Reflector) Field not present: " + oclass.getName() + ".actionKeyF3");
/* 24 */       return null;
/*    */     } 
/* 26 */     return field1;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private Field getFieldRenderChunksMany() {
/* 32 */     Minecraft minecraft = Minecraft.getMinecraft();
/* 33 */     boolean flag = minecraft.renderChunksMany;
/* 34 */     Field[] afield = Minecraft.class.getDeclaredFields();
/* 35 */     minecraft.renderChunksMany = true;
/* 36 */     Field[] afield1 = ReflectorRaw.getFields(minecraft, afield, boolean.class, Boolean.TRUE);
/* 37 */     minecraft.renderChunksMany = false;
/* 38 */     Field[] afield2 = ReflectorRaw.getFields(minecraft, afield, boolean.class, Boolean.FALSE);
/* 39 */     minecraft.renderChunksMany = flag;
/* 40 */     Set<Field> set = new HashSet<>(Arrays.asList(afield1));
/* 41 */     Set<Field> set1 = new HashSet<>(Arrays.asList(afield2));
/* 42 */     Set<Field> set2 = new HashSet<>(set);
/* 43 */     set2.retainAll(set1);
/* 44 */     Field[] afield3 = set2.<Field>toArray(new Field[set2.size()]);
/* 45 */     return (afield3.length != 1) ? null : afield3[0];
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\reflect\FieldLocatorActionKeyF3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */