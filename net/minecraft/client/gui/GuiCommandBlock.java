/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.command.server.CommandBlockLogic;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.client.C17PacketCustomPayload;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public class GuiCommandBlock
/*     */   extends GuiScreen {
/*  17 */   private static final Logger field_146488_a = LogManager.getLogger();
/*     */ 
/*     */   
/*     */   private GuiTextField commandTextField;
/*     */ 
/*     */   
/*     */   private GuiTextField previousOutputTextField;
/*     */ 
/*     */   
/*     */   private final CommandBlockLogic localCommandBlock;
/*     */ 
/*     */   
/*     */   private GuiButton doneBtn;
/*     */   
/*     */   private GuiButton cancelBtn;
/*     */   
/*     */   private GuiButton field_175390_s;
/*     */   
/*     */   private boolean field_175389_t;
/*     */ 
/*     */   
/*     */   public GuiCommandBlock(CommandBlockLogic p_i45032_1_) {
/*  39 */     this.localCommandBlock = p_i45032_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  46 */     this.commandTextField.updateCursorCounter();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  54 */     Keyboard.enableRepeatEvents(true);
/*  55 */     this.buttonList.clear();
/*  56 */     this.buttonList.add(this.doneBtn = new GuiButton(0, width / 2 - 4 - 150, height / 4 + 120 + 12, 150, 20, I18n.format("gui.done", new Object[0])));
/*  57 */     this.buttonList.add(this.cancelBtn = new GuiButton(1, width / 2 + 4, height / 4 + 120 + 12, 150, 20, I18n.format("gui.cancel", new Object[0])));
/*  58 */     this.buttonList.add(this.field_175390_s = new GuiButton(4, width / 2 + 150 - 20, 150, 20, 20, "O"));
/*  59 */     this.commandTextField = new GuiTextField(2, this.fontRendererObj, width / 2 - 150, 50, 300, 20);
/*  60 */     this.commandTextField.setMaxStringLength(32767);
/*  61 */     this.commandTextField.setFocused(true);
/*  62 */     this.commandTextField.setText(this.localCommandBlock.getCommand());
/*  63 */     this.previousOutputTextField = new GuiTextField(3, this.fontRendererObj, width / 2 - 150, 150, 276, 20);
/*  64 */     this.previousOutputTextField.setMaxStringLength(32767);
/*  65 */     this.previousOutputTextField.setEnabled(false);
/*  66 */     this.previousOutputTextField.setText("-");
/*  67 */     this.field_175389_t = this.localCommandBlock.shouldTrackOutput();
/*  68 */     func_175388_a();
/*  69 */     this.doneBtn.enabled = (this.commandTextField.getText().trim().length() > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/*  76 */     Keyboard.enableRepeatEvents(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*  83 */     if (button.enabled) {
/*  84 */       if (button.id == 1) {
/*  85 */         this.localCommandBlock.setTrackOutput(this.field_175389_t);
/*  86 */         this.mc.displayGuiScreen(null);
/*  87 */       } else if (button.id == 0) {
/*  88 */         PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
/*  89 */         packetbuffer.writeByte(this.localCommandBlock.func_145751_f());
/*  90 */         this.localCommandBlock.func_145757_a((ByteBuf)packetbuffer);
/*  91 */         packetbuffer.writeString(this.commandTextField.getText());
/*  92 */         packetbuffer.writeBoolean(this.localCommandBlock.shouldTrackOutput());
/*  93 */         this.mc.getNetHandler().addToSendQueue((Packet)new C17PacketCustomPayload("MC|AdvCdm", packetbuffer));
/*     */         
/*  95 */         if (!this.localCommandBlock.shouldTrackOutput()) {
/*  96 */           this.localCommandBlock.setLastOutput(null);
/*     */         }
/*     */         
/*  99 */         this.mc.displayGuiScreen(null);
/* 100 */       } else if (button.id == 4) {
/* 101 */         this.localCommandBlock.setTrackOutput(!this.localCommandBlock.shouldTrackOutput());
/* 102 */         func_175388_a();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 112 */     this.commandTextField.textboxKeyTyped(typedChar, keyCode);
/* 113 */     this.previousOutputTextField.textboxKeyTyped(typedChar, keyCode);
/* 114 */     this.doneBtn.enabled = (this.commandTextField.getText().trim().length() > 0);
/*     */     
/* 116 */     if (keyCode != 28 && keyCode != 156) {
/* 117 */       if (keyCode == 1) {
/* 118 */         actionPerformed(this.cancelBtn);
/*     */       }
/*     */     } else {
/* 121 */       actionPerformed(this.doneBtn);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 129 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/* 130 */     this.commandTextField.mouseClicked(mouseX, mouseY, mouseButton);
/* 131 */     this.previousOutputTextField.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 138 */     drawDefaultBackground();
/* 139 */     drawCenteredString(this.fontRendererObj, I18n.format("advMode.setCommand", new Object[0]), width / 2, 20, 16777215);
/* 140 */     drawString(this.fontRendererObj, I18n.format("advMode.command", new Object[0]), width / 2 - 150, 37, 10526880);
/* 141 */     this.commandTextField.drawTextBox();
/* 142 */     int i = 75;
/* 143 */     int j = 0;
/* 144 */     drawString(this.fontRendererObj, I18n.format("advMode.nearestPlayer", new Object[0]), width / 2 - 150, i + j++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
/* 145 */     drawString(this.fontRendererObj, I18n.format("advMode.randomPlayer", new Object[0]), width / 2 - 150, i + j++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
/* 146 */     drawString(this.fontRendererObj, I18n.format("advMode.allPlayers", new Object[0]), width / 2 - 150, i + j++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
/* 147 */     drawString(this.fontRendererObj, I18n.format("advMode.allEntities", new Object[0]), width / 2 - 150, i + j++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
/* 148 */     drawString(this.fontRendererObj, "", width / 2 - 150, i + j++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
/*     */     
/* 150 */     if (this.previousOutputTextField.getText().length() > 0) {
/* 151 */       i = i + j * this.fontRendererObj.FONT_HEIGHT + 16;
/* 152 */       drawString(this.fontRendererObj, I18n.format("advMode.previousOutput", new Object[0]), width / 2 - 150, i, 10526880);
/* 153 */       this.previousOutputTextField.drawTextBox();
/*     */     } 
/*     */     
/* 156 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */   
/*     */   private void func_175388_a() {
/* 160 */     if (this.localCommandBlock.shouldTrackOutput()) {
/* 161 */       this.field_175390_s.displayString = "O";
/*     */       
/* 163 */       if (this.localCommandBlock.getLastOutput() != null) {
/* 164 */         this.previousOutputTextField.setText(this.localCommandBlock.getLastOutput().getUnformattedText());
/*     */       }
/*     */     } else {
/* 167 */       this.field_175390_s.displayString = "X";
/* 168 */       this.previousOutputTextField.setText("-");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiCommandBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */