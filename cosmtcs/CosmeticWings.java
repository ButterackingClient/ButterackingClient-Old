package cosmtcs;

import client.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.util.ResourceLocation;

public class CosmeticWings extends Cosmetic {
    private ModelRenderer wing;
    private ModelRenderer wingTip;
    private RenderPlayer playerRenderer;

    public CosmeticWings(final RenderPlayer player) {
        super(player);
        this.playerRenderer = player;
        this.textureWidth = 256;
        this.textureHeight = 256;
        this.setTextureOffset("wing.skin", -56, 88);
        this.setTextureOffset("wing.bone", 112, 88);
        this.setTextureOffset("wingtip.skin", -56, 144);
        this.setTextureOffset("wingtip.bone", 112, 136);
        (this.wing = new ModelRenderer(this, "wing")).setTextureSize(256, 256);
        this.wing.setRotationPoint(-12.0f, 5.0f, 2.0f);
        this.wing.addBox("bone", -56.0f, -4.0f, -4.0f, 56, 8, 8);
        this.wing.addBox("skin", -56.0f, 0.0f, 2.0f, 56, 0, 56);
        this.wing.isHidden = true;
        (this.wingTip = new ModelRenderer(this, "wingtip")).setTextureSize(256, 256);
        this.wingTip.setRotationPoint(-56.0f, 0.0f, 0.0f);
        this.wingTip.isHidden = true;
        this.wingTip.addBox("bone", -56.0f, -2.0f, -2.0f, 56, 4, 4);
        this.wingTip.addBox("skin", -56.0f, 0.0f, 2.0f, 56, 0, 56);
        this.wing.addChild(this.wingTip);
    }

    @Override
    public boolean shouldCombineTextures() {
        return true;
    }

    @Override
    public void render(final AbstractClientPlayer player, final float limbSwing, final float limbSwingAmount, final float partialTicks, final float ageInTicks, final float headYaw, final float headPitch, final float scale) {
        if (player.getName().equals(Minecraft.getMinecraft().getSession().getUsername()) && Client.I.hudManager.w.isEnabled()) {
            GlStateManager.pushMatrix();
            final float f1 = ageInTicks / 75.0f;
            Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("client/wing.png"));
            GlStateManager.disableLighting();
            GlStateManager.scale(0.16, 0.16, 0.16);
            GlStateManager.translate(0.0, -0.3, 1.1);
            GlStateManager.rotate(50.0f, -50.0f, 0.0f, 0.0f);
            GlStateManager.blendFunc(1, 1);
            if (player.isSneaking()) {
                GlStateManager.translate(0.0f, 0.142f, 1.2f);
            }
            for (int i = 0; i < 2; ++i) {
                final float f2 = f1 * 9.141593f * 1.2f;
                this.wing.rotateAngleX = 0.125f - (float) Math.cos(f2) * 0.2f;
                this.wing.rotateAngleY = 0.25f;
                this.wing.rotateAngleZ = (float) (Math.sin(f2) + 1.225) * 0.45f;
                this.wingTip.rotateAngleZ = -(float) (Math.sin(f2 + 2.0f) + 0.5) * 0.95f;
                this.wing.isHidden = false;
                this.wingTip.isHidden = false;
                this.wing.render(scale);
                this.wing.isHidden = true;
                this.wingTip.isHidden = true;
                if (i == 0) {
                    GlStateManager.scale(-1.0f, 1.0f, 1.0f);
                }
            }
            GlStateManager.blendFunc(0, 0);
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
        if (player.getName().equals(Minecraft.getMinecraft().getSession().getUsername()) && Client.I.hudManager.w2.isEnabled() && !Client.I.hudManager.w.isEnabled()) {
            GlStateManager.pushMatrix();
            final float f1 = ageInTicks / 75.0f;
            Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("client/wing2.png"));
            GlStateManager.disableLighting();
            GlStateManager.scale(0.16, 0.16, 0.16);
            GlStateManager.translate(0.0, -0.3, 1.1);
            GlStateManager.rotate(50.0f, -50.0f, 0.0f, 0.0f);
            GlStateManager.blendFunc(1, 1);
            if (player.isSneaking()) {
                GlStateManager.translate(0.0f, 0.142f, 1.2f);
            }
            for (int i = 0; i < 2; ++i) {
                final float f2 = f1 * 9.141593f * 1.2f;
                this.wing.rotateAngleX = 0.125f - (float) Math.cos(f2) * 0.2f;
                this.wing.rotateAngleY = 0.25f;
                this.wing.rotateAngleZ = (float) (Math.sin(f2) + 1.225) * 0.45f;
                this.wingTip.rotateAngleZ = -(float) (Math.sin(f2 + 2.0f) + 0.5) * 0.95f;
                this.wing.isHidden = false;
                this.wingTip.isHidden = false;
                this.wing.render(scale);
                this.wing.isHidden = true;
                this.wingTip.isHidden = true;
                if (i == 0) {
                    GlStateManager.scale(-1.0f, 1.0f, 1.0f);
                }
            }
            GlStateManager.blendFunc(0, 0);
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }


}
