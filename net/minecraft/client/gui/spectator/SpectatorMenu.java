/*     */ package net.minecraft.client.gui.spectator;
/*     */ 
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.GuiSpectator;
/*     */ import net.minecraft.client.gui.spectator.categories.SpectatorDetails;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ 
/*     */ 
/*     */ public class SpectatorMenu
/*     */ {
/*  16 */   private static final ISpectatorMenuObject field_178655_b = new EndSpectatorObject(null);
/*  17 */   private static final ISpectatorMenuObject field_178656_c = new MoveMenuObject(-1, true);
/*  18 */   private static final ISpectatorMenuObject field_178653_d = new MoveMenuObject(1, true);
/*  19 */   private static final ISpectatorMenuObject field_178654_e = new MoveMenuObject(1, false);
/*  20 */   public static final ISpectatorMenuObject field_178657_a = new ISpectatorMenuObject()
/*     */     {
/*     */       public void func_178661_a(SpectatorMenu menu) {}
/*     */       
/*     */       public IChatComponent getSpectatorName() {
/*  25 */         return (IChatComponent)new ChatComponentText("");
/*     */       }
/*     */ 
/*     */       
/*     */       public void func_178663_a(float p_178663_1_, int alpha) {}
/*     */       
/*     */       public boolean func_178662_A_() {
/*  32 */         return false;
/*     */       }
/*     */     };
/*     */   private final ISpectatorMenuRecipient field_178651_f;
/*  36 */   private final List<SpectatorDetails> field_178652_g = Lists.newArrayList();
/*  37 */   private ISpectatorMenuView field_178659_h = new BaseSpectatorGroup();
/*  38 */   private int field_178660_i = -1;
/*     */   private int field_178658_j;
/*     */   
/*     */   public SpectatorMenu(ISpectatorMenuRecipient p_i45497_1_) {
/*  42 */     this.field_178651_f = p_i45497_1_;
/*     */   }
/*     */   
/*     */   public ISpectatorMenuObject func_178643_a(int p_178643_1_) {
/*  46 */     int i = p_178643_1_ + this.field_178658_j * 6;
/*  47 */     return (this.field_178658_j > 0 && p_178643_1_ == 0) ? field_178656_c : ((p_178643_1_ == 7) ? ((i < this.field_178659_h.func_178669_a().size()) ? field_178653_d : field_178654_e) : ((p_178643_1_ == 8) ? field_178655_b : ((i >= 0 && i < this.field_178659_h.func_178669_a().size()) ? (ISpectatorMenuObject)Objects.firstNonNull(this.field_178659_h.func_178669_a().get(i), field_178657_a) : field_178657_a)));
/*     */   }
/*     */   
/*     */   public List<ISpectatorMenuObject> func_178642_a() {
/*  51 */     List<ISpectatorMenuObject> list = Lists.newArrayList();
/*     */     
/*  53 */     for (int i = 0; i <= 8; i++) {
/*  54 */       list.add(func_178643_a(i));
/*     */     }
/*     */     
/*  57 */     return list;
/*     */   }
/*     */   
/*     */   public ISpectatorMenuObject func_178645_b() {
/*  61 */     return func_178643_a(this.field_178660_i);
/*     */   }
/*     */   
/*     */   public ISpectatorMenuView func_178650_c() {
/*  65 */     return this.field_178659_h;
/*     */   }
/*     */   
/*     */   public void func_178644_b(int p_178644_1_) {
/*  69 */     ISpectatorMenuObject ispectatormenuobject = func_178643_a(p_178644_1_);
/*     */     
/*  71 */     if (ispectatormenuobject != field_178657_a) {
/*  72 */       if (this.field_178660_i == p_178644_1_ && ispectatormenuobject.func_178662_A_()) {
/*  73 */         ispectatormenuobject.func_178661_a(this);
/*     */       } else {
/*  75 */         this.field_178660_i = p_178644_1_;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void func_178641_d() {
/*  81 */     this.field_178651_f.func_175257_a(this);
/*     */   }
/*     */   
/*     */   public int func_178648_e() {
/*  85 */     return this.field_178660_i;
/*     */   }
/*     */   
/*     */   public void func_178647_a(ISpectatorMenuView p_178647_1_) {
/*  89 */     this.field_178652_g.add(func_178646_f());
/*  90 */     this.field_178659_h = p_178647_1_;
/*  91 */     this.field_178660_i = -1;
/*  92 */     this.field_178658_j = 0;
/*     */   }
/*     */   
/*     */   public SpectatorDetails func_178646_f() {
/*  96 */     return new SpectatorDetails(this.field_178659_h, func_178642_a(), this.field_178660_i);
/*     */   }
/*     */   
/*     */   static class EndSpectatorObject
/*     */     implements ISpectatorMenuObject {
/*     */     private EndSpectatorObject() {}
/*     */     
/*     */     public void func_178661_a(SpectatorMenu menu) {
/* 104 */       menu.func_178641_d();
/*     */     }
/*     */     
/*     */     public IChatComponent getSpectatorName() {
/* 108 */       return (IChatComponent)new ChatComponentText("Close menu");
/*     */     }
/*     */     
/*     */     public void func_178663_a(float p_178663_1_, int alpha) {
/* 112 */       Minecraft.getMinecraft().getTextureManager().bindTexture(GuiSpectator.field_175269_a);
/* 113 */       Gui.drawModalRectWithCustomSizedTexture(0, 0, 128.0F, 0.0F, 16, 16, 256.0F, 256.0F);
/*     */     }
/*     */     
/*     */     public boolean func_178662_A_() {
/* 117 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   static class MoveMenuObject implements ISpectatorMenuObject {
/*     */     private final int field_178666_a;
/*     */     private final boolean field_178665_b;
/*     */     
/*     */     public MoveMenuObject(int p_i45495_1_, boolean p_i45495_2_) {
/* 126 */       this.field_178666_a = p_i45495_1_;
/* 127 */       this.field_178665_b = p_i45495_2_;
/*     */     }
/*     */     
/*     */     public void func_178661_a(SpectatorMenu menu) {
/* 131 */       menu.field_178658_j = this.field_178666_a;
/*     */     }
/*     */     
/*     */     public IChatComponent getSpectatorName() {
/* 135 */       return (this.field_178666_a < 0) ? (IChatComponent)new ChatComponentText("Previous Page") : (IChatComponent)new ChatComponentText("Next Page");
/*     */     }
/*     */     
/*     */     public void func_178663_a(float p_178663_1_, int alpha) {
/* 139 */       Minecraft.getMinecraft().getTextureManager().bindTexture(GuiSpectator.field_175269_a);
/*     */       
/* 141 */       if (this.field_178666_a < 0) {
/* 142 */         Gui.drawModalRectWithCustomSizedTexture(0, 0, 144.0F, 0.0F, 16, 16, 256.0F, 256.0F);
/*     */       } else {
/* 144 */         Gui.drawModalRectWithCustomSizedTexture(0, 0, 160.0F, 0.0F, 16, 16, 256.0F, 256.0F);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean func_178662_A_() {
/* 149 */       return this.field_178665_b;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\spectator\SpectatorMenu.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */