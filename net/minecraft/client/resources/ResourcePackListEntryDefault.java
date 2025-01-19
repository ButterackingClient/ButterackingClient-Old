/*    */ package net.minecraft.client.resources;
/*    */ 
/*    */ import com.google.gson.JsonParseException;
/*    */ import java.io.IOException;
/*    */ import net.minecraft.client.gui.GuiScreenResourcePacks;
/*    */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*    */ import net.minecraft.client.renderer.texture.TextureUtil;
/*    */ import net.minecraft.client.resources.data.PackMetadataSection;
/*    */ import net.minecraft.util.EnumChatFormatting;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ResourcePackListEntryDefault
/*    */   extends ResourcePackListEntry
/*    */ {
/* 19 */   private static final Logger logger = LogManager.getLogger();
/*    */   private final IResourcePack field_148320_d;
/*    */   private final ResourceLocation resourcePackIcon;
/*    */   
/*    */   public ResourcePackListEntryDefault(GuiScreenResourcePacks resourcePacksGUIIn) {
/* 24 */     super(resourcePacksGUIIn); DynamicTexture dynamictexture;
/* 25 */     this.field_148320_d = (this.mc.getResourcePackRepository()).rprDefaultResourcePack;
/*    */ 
/*    */     
/*    */     try {
/* 29 */       dynamictexture = new DynamicTexture(this.field_148320_d.getPackImage());
/* 30 */     } catch (IOException var4) {
/* 31 */       dynamictexture = TextureUtil.missingTexture;
/*    */     } 
/*    */     
/* 34 */     this.resourcePackIcon = this.mc.getTextureManager().getDynamicTextureLocation("texturepackicon", dynamictexture);
/*    */   }
/*    */   
/*    */   protected int func_183019_a() {
/* 38 */     return 1;
/*    */   }
/*    */   
/*    */   protected String func_148311_a() {
/*    */     try {
/* 43 */       PackMetadataSection packmetadatasection = this.field_148320_d.<PackMetadataSection>getPackMetadata((this.mc.getResourcePackRepository()).rprMetadataSerializer, "pack");
/*    */       
/* 45 */       if (packmetadatasection != null) {
/* 46 */         return packmetadatasection.getPackDescription().getFormattedText();
/*    */       }
/* 48 */     } catch (JsonParseException jsonparseexception) {
/* 49 */       logger.error("Couldn't load metadata info", (Throwable)jsonparseexception);
/* 50 */     } catch (IOException ioexception) {
/* 51 */       logger.error("Couldn't load metadata info", ioexception);
/*    */     } 
/*    */     
/* 54 */     return EnumChatFormatting.RED + "Missing " + "pack.mcmeta" + " :(";
/*    */   }
/*    */   
/*    */   protected boolean func_148309_e() {
/* 58 */     return false;
/*    */   }
/*    */   
/*    */   protected boolean func_148308_f() {
/* 62 */     return false;
/*    */   }
/*    */   
/*    */   protected boolean func_148314_g() {
/* 66 */     return false;
/*    */   }
/*    */   
/*    */   protected boolean func_148307_h() {
/* 70 */     return false;
/*    */   }
/*    */   
/*    */   protected String func_148312_b() {
/* 74 */     return "Default";
/*    */   }
/*    */   
/*    */   protected void func_148313_c() {
/* 78 */     this.mc.getTextureManager().bindTexture(this.resourcePackIcon);
/*    */   }
/*    */   
/*    */   protected boolean func_148310_d() {
/* 82 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\resources\ResourcePackListEntryDefault.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */