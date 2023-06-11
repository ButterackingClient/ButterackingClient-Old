//package net.minecraft.client.gui.inventory;
//
//import java.io.IOException;
//import net.minecraft.block.Block;
//import net.minecraft.client.gui.GuiButton;
//import net.minecraft.client.gui.GuiScreen;
//import net.minecraft.client.network.NetHandlerPlayClient;
//import net.minecraft.client.renderer.GlStateManager;
//import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
//import net.minecraft.client.resources.I18n;
//import net.minecraft.init.Blocks;
//import net.minecraft.network.play.client.C12PacketUpdateSign;
//import net.minecraft.tileentity.TileEntitySign;
//import net.minecraft.util.ChatAllowedCharacters;
//import net.minecraft.util.ChatComponentText;
//import org.lwjgl.input.Keyboard;
//
//public class GuiEditSign extends GuiScreen
//{
//    /** Reference to the sign object. */
//    private TileEntitySign tileSign;
//
//    /** Counts the number of screen updates. */
//    private int updateCounter;
//
//    /** The index of the line that is being edited. */
//    private int editLine;
//
//    /** "Done" button for the GUI. */
//    private GuiButton doneBtn;
//
//    public GuiEditSign(TileEntitySign teSign)
//    {
//        this.tileSign = teSign;
//    }
//
//    /**
//     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
//     * window resizes, the buttonList is cleared beforehand.
//     */
//    public void initGui()
//    {
//        this.buttonList.clear();
//        Keyboard.enableRepeatEvents(true);
//        this.buttonList.add(this.doneBtn = new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120, I18n.format("gui.done", new Object[0])));
//        this.tileSign.setEditable(false);
//    }
//
//    /**
//     * Called when the screen is unloaded. Used to disable keyboard repeat events
//     */
//    public void onGuiClosed()
//    {
//        Keyboard.enableRepeatEvents(false);
//        NetHandlerPlayClient nethandlerplayclient = this.mc.getNetHandler();
//
//        if (nethandlerplayclient != null)
//        {
//            nethandlerplayclient.addToSendQueue(new C12PacketUpdateSign(this.tileSign.getPos(), this.tileSign.signText));
//        }
//
//        this.tileSign.setEditable(true);
//    }
//
//    /**
//     * Called from the main game loop to update the screen.
//     */
//    public void updateScreen()
//    {
//        ++this.updateCounter;
//    }
//
//    /**
//     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
//     */
//    protected void actionPerformed(GuiButton button) throws IOException
//    {
//        if (button.enabled)
//        {
//            if (button.id == 0)
//            {
//                this.tileSign.markDirty();
//                this.mc.displayGuiScreen((GuiScreen)null);
//            }
//        }
//    }
//
//    /**
//     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
//     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
//     */
//    protected void keyTyped(char typedChar, int keyCode) throws IOException
//    {
//        if (keyCode == 200)
//        {
//            this.editLine = this.editLine - 1 & 3;
//        }
//
//        if (keyCode == 208 || keyCode == 28 || keyCode == 156)
//        {
//            this.editLine = this.editLine + 1 & 3;
//        }
//
//        String s = this.tileSign.signText[this.editLine].getUnformattedText();
//
//        if (keyCode == 14 && s.length() > 0)
//        {
//            s = s.substring(0, s.length() - 1);
//        }
//
//        if (ChatAllowedCharacters.isAllowedCharacter(typedChar) && this.fontRendererObj.getStringWidth(s + typedChar) <= 90)
//        {
//            s = s + typedChar;
//        }
//
//        this.tileSign.signText[this.editLine] = new ChatComponentText(s);
//
//        if (keyCode == 1)
//        {
//            this.actionPerformed(this.doneBtn);
//        }
//    }
//
//    /**
//     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
//     */
//    public void drawScreen(int mouseX, int mouseY, float partialTicks)
//    {
//        this.drawDefaultBackground();
//        this.drawCenteredString(this.fontRendererObj, I18n.format("sign.edit", new Object[0]), this.width / 2, 40, 16777215);
//        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
//        GlStateManager.pushMatrix();
//        GlStateManager.translate((float)(this.width / 2), 0.0F, 50.0F);
//        float f = 93.75F;
//        GlStateManager.scale(-f, -f, -f);
//        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
//        Block block = this.tileSign.getBlockType();
//
//        if (block == Blocks.standing_sign)
//        {
//            float f1 = (float)(this.tileSign.getBlockMetadata() * 360) / 16.0F;
//            GlStateManager.rotate(f1, 0.0F, 1.0F, 0.0F);
//            GlStateManager.translate(0.0F, -1.0625F, 0.0F);
//        }
//        else
//        {
//            int i = this.tileSign.getBlockMetadata();
//            float f2 = 0.0F;
//
//            if (i == 2)
//            {
//                f2 = 180.0F;
//            }
//
//            if (i == 4)
//            {
//                f2 = 90.0F;
//            }
//
//            if (i == 5)
//            {
//                f2 = -90.0F;
//            }
//
//            GlStateManager.rotate(f2, 0.0F, 1.0F, 0.0F);
//            GlStateManager.translate(0.0F, -1.0625F, 0.0F);
//        }
//
//        if (this.updateCounter / 6 % 2 == 0)
//        {
//            this.tileSign.lineBeingEdited = this.editLine;
//        }
//
//        TileEntityRendererDispatcher.instance.renderTileEntityAt(this.tileSign, -0.5D, -0.75D, -0.5D, 0.0F);
//        this.tileSign.lineBeingEdited = -1;
//        GlStateManager.popMatrix();
//        super.drawScreen(mouseX, mouseY, partialTicks);
//    }
//}
//package net.minecraft.client.gui.inventory;
//
//import java.io.IOException;
//import net.minecraft.block.Block;
//import net.minecraft.client.gui.GuiButton;
//import net.minecraft.client.gui.GuiScreen;
//import net.minecraft.client.network.NetHandlerPlayClient;
//import net.minecraft.client.renderer.GlStateManager;
//import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
//import net.minecraft.client.resources.I18n;
//import net.minecraft.init.Blocks;
//import net.minecraft.network.play.client.C12PacketUpdateSign;
//import net.minecraft.tileentity.TileEntitySign;
//import net.minecraft.util.ChatAllowedCharacters;
//import net.minecraft.util.ChatComponentText;
//import org.lwjgl.input.Keyboard;
//
//public class GuiEditSign extends GuiScreen
//{
//    /** Reference to the sign object. */
//    private TileEntitySign tileSign;
//
//    /** Counts the number of screen updates. */
//    private int updateCounter;
//
//    /** The index of the line that is being edited. */
//    private int editLine;
//
//    /** "Done" button for the GUI. */
//    private GuiButton doneBtn;
//
//    public GuiEditSign(TileEntitySign teSign)
//    {
//        this.tileSign = teSign;
//    }
//
//    /**
//     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
//     * window resizes, the buttonList is cleared beforehand.
//     */
//    public void initGui()
//    {
//        this.buttonList.clear();
//        Keyboard.enableRepeatEvents(true);
//        this.buttonList.add(this.doneBtn = new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120, I18n.format("gui.done", new Object[0])));
//        this.tileSign.setEditable(false);
//    }
//
//    /**
//     * Called when the screen is unloaded. Used to disable keyboard repeat events
//     */
//    public void onGuiClosed()
//    {
//        Keyboard.enableRepeatEvents(false);
//        NetHandlerPlayClient nethandlerplayclient = this.mc.getNetHandler();
//
//        if (nethandlerplayclient != null)
//        {
//            nethandlerplayclient.addToSendQueue(new C12PacketUpdateSign(this.tileSign.getPos(), this.tileSign.signText));
//        }
//
//        this.tileSign.setEditable(true);
//    }
//
//    /**
//     * Called from the main game loop to update the screen.
//     */
//    public void updateScreen()
//    {
//        ++this.updateCounter;
//    }
//
//    /**
//     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
//     */
//    protected void actionPerformed(GuiButton button) throws IOException
//    {
//        if (button.enabled)
//        {
//            if (button.id == 0)
//            {
//                this.tileSign.markDirty();
//                this.mc.displayGuiScreen((GuiScreen)null);
//            }
//        }
//    }
//
//    /**
//     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
//     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
//     */
//    protected void keyTyped(char typedChar, int keyCode) throws IOException
//    {
//        if (keyCode == 200)
//        {
//            this.editLine = this.editLine - 1 & 3;
//        }
//
//        if (keyCode == 208 || keyCode == 28 || keyCode == 156)
//        {
//            this.editLine = this.editLine + 1 & 3;
//        }
//
//        String s = this.tileSign.signText[this.editLine].getUnformattedText();
//
//        if (keyCode == 14 && s.length() > 0)
//        {
//            s = s.substring(0, s.length() - 1);
//        }
//
//        if (ChatAllowedCharacters.isAllowedCharacter(typedChar) && this.fontRendererObj.getStringWidth(s + typedChar) <= 90)
//        {
//            s = s + typedChar;
//        }
//
//        this.tileSign.signText[this.editLine] = new ChatComponentText(s);
//
//        if (keyCode == 1)
//        {
//            this.actionPerformed(this.doneBtn);
//        }
//    }
//
//    /**
//     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
//     */
//    public void drawScreen(int mouseX, int mouseY, float partialTicks)
//    {
//        this.drawDefaultBackground();
//        this.drawCenteredString(this.fontRendererObj, I18n.format("sign.edit", new Object[0]), this.width / 2, 40, 16777215);
//        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
//        GlStateManager.pushMatrix();
//        GlStateManager.translate((float)(this.width / 2), 0.0F, 50.0F);
//        float f = 93.75F;
//        GlStateManager.scale(-f, -f, -f);
//        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
//        Block block = this.tileSign.getBlockType();
//
//        if (block == Blocks.standing_sign)
//        {
//            float f1 = (float)(this.tileSign.getBlockMetadata() * 360) / 16.0F;
//            GlStateManager.rotate(f1, 0.0F, 1.0F, 0.0F);
//            GlStateManager.translate(0.0F, -1.0625F, 0.0F);
//        }
//        else
//        {
//            int i = this.tileSign.getBlockMetadata();
//            float f2 = 0.0F;
//
//            if (i == 2)
//            {
//                f2 = 180.0F;
//            }
//
//            if (i == 4)
//            {
//                f2 = 90.0F;
//            }
//
//            if (i == 5)
//            {
//                f2 = -90.0F;
//            }
//
//            GlStateManager.rotate(f2, 0.0F, 1.0F, 0.0F);
//            GlStateManager.translate(0.0F, -1.0625F, 0.0F);
//        }
//
//        if (this.updateCounter / 6 % 2 == 0)
//        {
//            this.tileSign.lineBeingEdited = this.editLine;
//        }
//
//        TileEntityRendererDispatcher.instance.renderTileEntityAt(this.tileSign, -0.5D, -0.75D, -0.5D, 0.0F);
//        this.tileSign.lineBeingEdited = -1;
//        GlStateManager.popMatrix();
//        super.drawScreen(mouseX, mouseY, partialTicks);
//    }
//}
package net.minecraft.client.gui.inventory;

import java.io.IOException;

import kp.input.IInputTarget;
import kp.input.KoreanIME;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C12PacketUpdateSign;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.ChatComponentText;
import org.lwjgl.input.Keyboard;

public class GuiEditSign extends GuiScreen implements IInputTarget, IInputTarget.CursorSelectionFunc, IInputTarget.InputIdentifier {
    /**
     * Reference to the sign object.
     */
    private TileEntitySign tileSign;
    private int f2;

    /**
     * The index of the line that is being edited.
     */
    private int editLine;

    /**
     * "Done" button for the GUI.
     */
    private GuiButton doneBtn;
    private KoreanIME ime = new KoreanIME(this);

    public GuiEditSign(TileEntitySign teSign) {
        this.tileSign = teSign;
        this.ime.setCursorSelectionFunc(this);
        this.ime.setIdentifier(this);
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui() {
        this.buttonList.clear();
        Keyboard.enableRepeatEvents(true);
        this.buttonList.add(this.doneBtn = new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120, I18n.format("gui.done", new Object[0])));
        this.tileSign.setEditable(false);
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        NetHandlerPlayClient nethandlerplayclient = this.mc.getNetHandler();

        if (nethandlerplayclient != null) {
            nethandlerplayclient.addToSendQueue(new C12PacketUpdateSign(this.tileSign.getPos(), this.tileSign.signText));
        }

        this.tileSign.setEditable(true);
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen() {
        ++this.f2;
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.enabled && button.id == 0) {
            this.tileSign.markDirty();
            this.mc.displayGuiScreen((GuiScreen) null);
        }
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == 200 || keyCode == 208 || keyCode == 28 || keyCode == 156) {
            if (keyCode == 200) {
                this.editLine = this.editLine - 1 & 3;
            } else {
                this.editLine = this.editLine + 1 & 3;
            }

            this.ime.setCursor(this.getTargetText().length());
            this.ime.setSelection(this.getTargetText().length());
        }

        this.ime.onTyped(keyCode, typedChar);

        if (keyCode == 1) {
            this.actionPerformed(this.doneBtn);
        }
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format("sign.edit", new Object[0]), this.width / 2, 40, 16777215);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) (this.width / 2), 0.0F, 50.0F);
        float f = 93.75F;
        GlStateManager.scale(-f, -f, -f);
        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
        Block block = this.tileSign.getBlockType();

        if (block == Blocks.standing_sign) {
            float f1 = (float) (this.tileSign.getBlockMetadata() * 360) / 16.0F;
            GlStateManager.rotate(f1, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(0.0F, -1.0625F, 0.0F);
        } else {
            int i = this.tileSign.getBlockMetadata();
            float f2 = 0.0F;

            if (i == 2) {
                f2 = 180.0F;
            }

            if (i == 4) {
                f2 = 90.0F;
            }

            if (i == 5) {
                f2 = -90.0F;
            }

            GlStateManager.rotate(f2, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(0.0F, -1.0625F, 0.0F);
        }

        if (this.f2 / 6 % 2 == 0) {
            this.tileSign.lineBeingEdited = this.editLine;
        }

        String s1 = this.getTargetText();

        if (this.ime.getCursor() < s1.length()) {
            String s2 = s1.substring(0, this.ime.getCursor());
            String s = s1.substring(this.ime.getCursor());
            this.setTargetText(s2 + "_" + s);
        }

        TileEntityRendererDispatcher.instance.renderTileEntityAt(this.tileSign, -0.5D, -0.75D, -0.5D, 0.0F);
        this.setTargetText(s1);
        this.tileSign.lineBeingEdited = -1;
        GlStateManager.popMatrix();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public String getTargetText() {
        return this.tileSign.signText[this.editLine].getUnformattedText();
    }

    public boolean setTargetText(String p_setTargetText_1_) {
        this.tileSign.signText[this.editLine] = new ChatComponentText(p_setTargetText_1_);
        return true;
    }

    public int getCursor() {
        return this.ime.getCursor();
    }

    public void setCursor(int p_setCursor_1_) {
        this.ime.setCursor(p_setCursor_1_);
    }

    public int getSelection() {
        return this.getCursor();
    }

    public void setSelection(int p_setSelection_1_) {
    }

    public boolean apply(String p_apply_1_) {
        return this.fontRendererObj.getStringWidth(this.getTargetText() + p_apply_1_) <= 90;
    }
}
