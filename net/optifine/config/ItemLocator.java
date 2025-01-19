/*   */ package net.optifine.config;
/*   */ 
/*   */ import net.minecraft.item.Item;
/*   */ import net.minecraft.util.ResourceLocation;
/*   */ 
/*   */ public class ItemLocator implements IObjectLocator {
/*   */   public Object getObject(ResourceLocation loc) {
/* 8 */     Item item = Item.getByNameOrId(loc.toString());
/* 9 */     return item;
/*   */   }
/*   */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\config\ItemLocator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */