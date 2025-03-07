/*    */ package net.minecraft.client.gui.spectator.categories;
/*    */ 
/*    */ import com.google.common.base.Objects;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
/*    */ import net.minecraft.client.gui.spectator.ISpectatorMenuView;
/*    */ import net.minecraft.client.gui.spectator.SpectatorMenu;
/*    */ 
/*    */ 
/*    */ public class SpectatorDetails
/*    */ {
/*    */   private final ISpectatorMenuView field_178684_a;
/*    */   private final List<ISpectatorMenuObject> field_178682_b;
/*    */   private final int field_178683_c;
/*    */   
/*    */   public SpectatorDetails(ISpectatorMenuView p_i45494_1_, List<ISpectatorMenuObject> p_i45494_2_, int p_i45494_3_) {
/* 17 */     this.field_178684_a = p_i45494_1_;
/* 18 */     this.field_178682_b = p_i45494_2_;
/* 19 */     this.field_178683_c = p_i45494_3_;
/*    */   }
/*    */   
/*    */   public ISpectatorMenuObject func_178680_a(int p_178680_1_) {
/* 23 */     return (p_178680_1_ >= 0 && p_178680_1_ < this.field_178682_b.size()) ? (ISpectatorMenuObject)Objects.firstNonNull(this.field_178682_b.get(p_178680_1_), SpectatorMenu.field_178657_a) : SpectatorMenu.field_178657_a;
/*    */   }
/*    */   
/*    */   public int func_178681_b() {
/* 27 */     return this.field_178683_c;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\spectator\categories\SpectatorDetails.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */