/*   */ package net.optifine.config;
/*   */ 
/*   */ import net.minecraft.util.ResourceLocation;
/*   */ import net.optifine.util.EntityUtils;
/*   */ 
/*   */ public class EntityClassLocator implements IObjectLocator {
/*   */   public Object getObject(ResourceLocation loc) {
/* 8 */     Class oclass = EntityUtils.getEntityClassByName(loc.getResourcePath());
/* 9 */     return oclass;
/*   */   }
/*   */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\config\EntityClassLocator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */