/*     */ package net.minecraft.client.gui.stream;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.stream.IStream;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import tv.twitch.chat.ChatUserInfo;
/*     */ import tv.twitch.chat.ChatUserMode;
/*     */ import tv.twitch.chat.ChatUserSubscription;
/*     */ 
/*     */ public class GuiTwitchUserMode
/*     */   extends GuiScreen
/*     */ {
/*  22 */   private static final EnumChatFormatting field_152331_a = EnumChatFormatting.DARK_GREEN;
/*  23 */   private static final EnumChatFormatting field_152335_f = EnumChatFormatting.RED;
/*  24 */   private static final EnumChatFormatting field_152336_g = EnumChatFormatting.DARK_PURPLE;
/*     */   private final ChatUserInfo field_152337_h;
/*     */   private final IChatComponent field_152338_i;
/*  27 */   private final List<IChatComponent> field_152332_r = Lists.newArrayList();
/*     */   private final IStream stream;
/*     */   private int field_152334_t;
/*     */   
/*     */   public GuiTwitchUserMode(IStream streamIn, ChatUserInfo p_i1064_2_) {
/*  32 */     this.stream = streamIn;
/*  33 */     this.field_152337_h = p_i1064_2_;
/*  34 */     this.field_152338_i = (IChatComponent)new ChatComponentText(p_i1064_2_.displayName);
/*  35 */     this.field_152332_r.addAll(func_152328_a(p_i1064_2_.modes, p_i1064_2_.subscriptions, streamIn));
/*     */   }
/*     */   
/*     */   public static List<IChatComponent> func_152328_a(Set<ChatUserMode> p_152328_0_, Set<ChatUserSubscription> p_152328_1_, IStream p_152328_2_) {
/*  39 */     String s = (p_152328_2_ == null) ? null : p_152328_2_.func_152921_C();
/*  40 */     boolean flag = (p_152328_2_ != null && p_152328_2_.func_152927_B());
/*  41 */     List<IChatComponent> list = Lists.newArrayList();
/*     */     
/*  43 */     for (ChatUserMode chatusermode : p_152328_0_) {
/*  44 */       IChatComponent ichatcomponent = func_152329_a(chatusermode, s, flag);
/*     */       
/*  46 */       if (ichatcomponent != null) {
/*  47 */         ChatComponentText chatComponentText = new ChatComponentText("- ");
/*  48 */         chatComponentText.appendSibling(ichatcomponent);
/*  49 */         list.add(chatComponentText);
/*     */       } 
/*     */     } 
/*     */     
/*  53 */     for (ChatUserSubscription chatusersubscription : p_152328_1_) {
/*  54 */       IChatComponent ichatcomponent2 = func_152330_a(chatusersubscription, s, flag);
/*     */       
/*  56 */       if (ichatcomponent2 != null) {
/*  57 */         ChatComponentText chatComponentText = new ChatComponentText("- ");
/*  58 */         chatComponentText.appendSibling(ichatcomponent2);
/*  59 */         list.add(chatComponentText);
/*     */       } 
/*     */     } 
/*     */     
/*  63 */     return list;
/*     */   }
/*     */   public static IChatComponent func_152330_a(ChatUserSubscription p_152330_0_, String p_152330_1_, boolean p_152330_2_) {
/*     */     ChatComponentTranslation chatComponentTranslation;
/*  67 */     IChatComponent ichatcomponent = null;
/*     */     
/*  69 */     if (p_152330_0_ == ChatUserSubscription.TTV_CHAT_USERSUB_SUBSCRIBER) {
/*  70 */       if (p_152330_1_ == null) {
/*  71 */         chatComponentTranslation = new ChatComponentTranslation("stream.user.subscription.subscriber", new Object[0]);
/*  72 */       } else if (p_152330_2_) {
/*  73 */         chatComponentTranslation = new ChatComponentTranslation("stream.user.subscription.subscriber.self", new Object[0]);
/*     */       } else {
/*  75 */         chatComponentTranslation = new ChatComponentTranslation("stream.user.subscription.subscriber.other", new Object[] { p_152330_1_ });
/*     */       } 
/*     */       
/*  78 */       chatComponentTranslation.getChatStyle().setColor(field_152331_a);
/*  79 */     } else if (p_152330_0_ == ChatUserSubscription.TTV_CHAT_USERSUB_TURBO) {
/*  80 */       chatComponentTranslation = new ChatComponentTranslation("stream.user.subscription.turbo", new Object[0]);
/*  81 */       chatComponentTranslation.getChatStyle().setColor(field_152336_g);
/*     */     } 
/*     */     
/*  84 */     return (IChatComponent)chatComponentTranslation;
/*     */   }
/*     */   public static IChatComponent func_152329_a(ChatUserMode p_152329_0_, String p_152329_1_, boolean p_152329_2_) {
/*     */     ChatComponentTranslation chatComponentTranslation;
/*  88 */     IChatComponent ichatcomponent = null;
/*     */     
/*  90 */     if (p_152329_0_ == ChatUserMode.TTV_CHAT_USERMODE_ADMINSTRATOR) {
/*  91 */       chatComponentTranslation = new ChatComponentTranslation("stream.user.mode.administrator", new Object[0]);
/*  92 */       chatComponentTranslation.getChatStyle().setColor(field_152336_g);
/*  93 */     } else if (p_152329_0_ == ChatUserMode.TTV_CHAT_USERMODE_BANNED) {
/*  94 */       if (p_152329_1_ == null) {
/*  95 */         chatComponentTranslation = new ChatComponentTranslation("stream.user.mode.banned", new Object[0]);
/*  96 */       } else if (p_152329_2_) {
/*  97 */         chatComponentTranslation = new ChatComponentTranslation("stream.user.mode.banned.self", new Object[0]);
/*     */       } else {
/*  99 */         chatComponentTranslation = new ChatComponentTranslation("stream.user.mode.banned.other", new Object[] { p_152329_1_ });
/*     */       } 
/*     */       
/* 102 */       chatComponentTranslation.getChatStyle().setColor(field_152335_f);
/* 103 */     } else if (p_152329_0_ == ChatUserMode.TTV_CHAT_USERMODE_BROADCASTER) {
/* 104 */       if (p_152329_1_ == null) {
/* 105 */         chatComponentTranslation = new ChatComponentTranslation("stream.user.mode.broadcaster", new Object[0]);
/* 106 */       } else if (p_152329_2_) {
/* 107 */         chatComponentTranslation = new ChatComponentTranslation("stream.user.mode.broadcaster.self", new Object[0]);
/*     */       } else {
/* 109 */         chatComponentTranslation = new ChatComponentTranslation("stream.user.mode.broadcaster.other", new Object[0]);
/*     */       } 
/*     */       
/* 112 */       chatComponentTranslation.getChatStyle().setColor(field_152331_a);
/* 113 */     } else if (p_152329_0_ == ChatUserMode.TTV_CHAT_USERMODE_MODERATOR) {
/* 114 */       if (p_152329_1_ == null) {
/* 115 */         chatComponentTranslation = new ChatComponentTranslation("stream.user.mode.moderator", new Object[0]);
/* 116 */       } else if (p_152329_2_) {
/* 117 */         chatComponentTranslation = new ChatComponentTranslation("stream.user.mode.moderator.self", new Object[0]);
/*     */       } else {
/* 119 */         chatComponentTranslation = new ChatComponentTranslation("stream.user.mode.moderator.other", new Object[] { p_152329_1_ });
/*     */       } 
/*     */       
/* 122 */       chatComponentTranslation.getChatStyle().setColor(field_152331_a);
/* 123 */     } else if (p_152329_0_ == ChatUserMode.TTV_CHAT_USERMODE_STAFF) {
/* 124 */       chatComponentTranslation = new ChatComponentTranslation("stream.user.mode.staff", new Object[0]);
/* 125 */       chatComponentTranslation.getChatStyle().setColor(field_152336_g);
/*     */     } 
/*     */     
/* 128 */     return (IChatComponent)chatComponentTranslation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/* 136 */     int i = width / 3;
/* 137 */     int j = i - 130;
/* 138 */     this.buttonList.add(new GuiButton(1, i * 0 + j / 2, height - 70, 130, 20, I18n.format("stream.userinfo.timeout", new Object[0])));
/* 139 */     this.buttonList.add(new GuiButton(0, i * 1 + j / 2, height - 70, 130, 20, I18n.format("stream.userinfo.ban", new Object[0])));
/* 140 */     this.buttonList.add(new GuiButton(2, i * 2 + j / 2, height - 70, 130, 20, I18n.format("stream.userinfo.mod", new Object[0])));
/* 141 */     this.buttonList.add(new GuiButton(5, i * 0 + j / 2, height - 45, 130, 20, I18n.format("gui.cancel", new Object[0])));
/* 142 */     this.buttonList.add(new GuiButton(3, i * 1 + j / 2, height - 45, 130, 20, I18n.format("stream.userinfo.unban", new Object[0])));
/* 143 */     this.buttonList.add(new GuiButton(4, i * 2 + j / 2, height - 45, 130, 20, I18n.format("stream.userinfo.unmod", new Object[0])));
/* 144 */     int k = 0;
/*     */     
/* 146 */     for (IChatComponent ichatcomponent : this.field_152332_r) {
/* 147 */       k = Math.max(k, this.fontRendererObj.getStringWidth(ichatcomponent.getFormattedText()));
/*     */     }
/*     */     
/* 150 */     this.field_152334_t = width / 2 - k / 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 157 */     if (button.enabled) {
/* 158 */       if (button.id == 0) {
/* 159 */         this.stream.func_152917_b("/ban " + this.field_152337_h.displayName);
/* 160 */       } else if (button.id == 3) {
/* 161 */         this.stream.func_152917_b("/unban " + this.field_152337_h.displayName);
/* 162 */       } else if (button.id == 2) {
/* 163 */         this.stream.func_152917_b("/mod " + this.field_152337_h.displayName);
/* 164 */       } else if (button.id == 4) {
/* 165 */         this.stream.func_152917_b("/unmod " + this.field_152337_h.displayName);
/* 166 */       } else if (button.id == 1) {
/* 167 */         this.stream.func_152917_b("/timeout " + this.field_152337_h.displayName);
/*     */       } 
/*     */       
/* 170 */       this.mc.displayGuiScreen(null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 178 */     drawDefaultBackground();
/* 179 */     drawCenteredString(this.fontRendererObj, this.field_152338_i.getUnformattedText(), width / 2, 70, 16777215);
/* 180 */     int i = 80;
/*     */     
/* 182 */     for (IChatComponent ichatcomponent : this.field_152332_r) {
/* 183 */       drawString(this.fontRendererObj, ichatcomponent.getFormattedText(), this.field_152334_t, i, 16777215);
/* 184 */       i += this.fontRendererObj.FONT_HEIGHT;
/*     */     } 
/*     */     
/* 187 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\stream\GuiTwitchUserMode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */