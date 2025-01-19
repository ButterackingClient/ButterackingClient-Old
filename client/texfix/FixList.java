/*    */ package client.texfix;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*    */ 
/*    */ public class FixList
/*    */   extends ArrayList<int[][]> {
/*    */   TextureAtlasSprite sprite;
/*    */   boolean reload;
/*    */   
/*    */   public FixList(TextureAtlasSprite data) {
/* 12 */     this.reload = true;
/* 13 */     this.sprite = data;
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 18 */     if (this.reload) {
/* 19 */       reload();
/*    */     }
/* 21 */     return super.size();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isEmpty() {
/* 26 */     return (size() == 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public int[][] get(int index) {
/* 31 */     if (this.reload) {
/* 32 */       reload();
/*    */     }
/* 34 */     return super.get(index);
/*    */   }
/*    */ 
/*    */   
/*    */   public void clear() {
/* 39 */     this.reload = true;
/* 40 */     super.clear();
/*    */   }
/*    */   
/*    */   public void reload() {
/* 44 */     this.reload = false;
/* 45 */     TextureFix.reloadTextureData(this.sprite);
/* 46 */     TextureFix.markForUnload(this.sprite);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\texfix\FixList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */