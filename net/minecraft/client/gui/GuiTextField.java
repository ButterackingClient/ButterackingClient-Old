/*      */ package net.minecraft.client.gui;
/*      */ 
/*      */ import com.google.common.base.Predicate;
/*      */ import com.google.common.base.Predicates;
/*      */ import kp.input.IInputTarget;
/*      */ import kp.input.KoreanIME;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.Tessellator;
/*      */ import net.minecraft.client.renderer.WorldRenderer;
/*      */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*      */ import net.minecraft.util.ChatAllowedCharacters;
/*      */ import net.minecraft.util.MathHelper;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class GuiTextField
/*      */   extends Gui
/*      */   implements IInputTarget, IInputTarget.WriteTextFunc, IInputTarget.CursorSelectionFunc
/*      */ {
/*      */   private final int id;
/*      */   private final FontRenderer fontRendererInstance;
/*      */   public int xPosition;
/*      */   public int yPosition;
/*      */   private final int width;
/*      */   private final int height;
/*  836 */   private String text = "";
/*  837 */   private int maxStringLength = 32;
/*      */ 
/*      */ 
/*      */   
/*      */   private int cursorCounter;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean enableBackgroundDrawing = true;
/*      */ 
/*      */   
/*      */   private boolean canLoseFocus = true;
/*      */ 
/*      */   
/*      */   private boolean isFocused;
/*      */ 
/*      */   
/*      */   private boolean isEnabled = true;
/*      */ 
/*      */   
/*      */   private int lineScrollOffset;
/*      */ 
/*      */   
/*      */   private int cursorPosition;
/*      */ 
/*      */   
/*      */   private int selectionEnd;
/*      */ 
/*      */   
/*  866 */   private int enabledColor = 14737632;
/*  867 */   private int disabledColor = 7368816;
/*      */ 
/*      */   
/*      */   private boolean visible = true;
/*      */   
/*      */   private GuiPageButtonList.GuiResponder field_175210_x;
/*      */   
/*  874 */   private Predicate field_175209_y = Predicates.alwaysTrue();
/*  875 */   private KoreanIME ime = new KoreanIME(this);
/*      */   
/*      */   public GuiTextField(int componentId, FontRenderer fontrendererObj, int x, int y, int par5Width, int par6Height) {
/*  878 */     this.id = componentId;
/*  879 */     this.fontRendererInstance = fontrendererObj;
/*  880 */     this.xPosition = x;
/*  881 */     this.yPosition = y;
/*  882 */     this.width = par5Width;
/*  883 */     this.height = par6Height;
/*  884 */     this.ime.setWriteTextFunc(this);
/*  885 */     this.ime.setCursorSelectionFunc(this);
/*      */   }
/*      */   
/*      */   public void func_175207_a(GuiPageButtonList.GuiResponder p_175207_1_) {
/*  889 */     this.field_175210_x = p_175207_1_;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateCursorCounter() {
/*  896 */     this.cursorCounter++;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setText(String p_146180_1_) {
/*  903 */     if (this.field_175209_y.apply(p_146180_1_)) {
/*  904 */       if (p_146180_1_.length() > this.maxStringLength) {
/*  905 */         this.text = p_146180_1_.substring(0, this.maxStringLength);
/*      */       } else {
/*  907 */         this.text = p_146180_1_;
/*      */       } 
/*      */       
/*  910 */       setCursorPositionEnd();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getText() {
/*  918 */     return this.text;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSelectedText() {
/*  925 */     int i = (this.cursorPosition < this.selectionEnd) ? this.cursorPosition : this.selectionEnd;
/*  926 */     int j = (this.cursorPosition < this.selectionEnd) ? this.selectionEnd : this.cursorPosition;
/*  927 */     return this.text.substring(i, j);
/*      */   }
/*      */   
/*      */   public void setValidator(Predicate p_175205_1_) {
/*  931 */     this.field_175209_y = p_175205_1_;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeText(String p_146191_1_) {
/*  938 */     String s = "";
/*  939 */     String s1 = ChatAllowedCharacters.filterAllowedCharacters(p_146191_1_);
/*  940 */     int i = (this.cursorPosition < this.selectionEnd) ? this.cursorPosition : this.selectionEnd;
/*  941 */     int j = (this.cursorPosition < this.selectionEnd) ? this.selectionEnd : this.cursorPosition;
/*  942 */     int k = this.maxStringLength - this.text.length() - i - j;
/*  943 */     int l = 0;
/*      */     
/*  945 */     if (this.text.length() > 0) {
/*  946 */       s = String.valueOf(s) + this.text.substring(0, i);
/*      */     }
/*      */     
/*  949 */     if (k < s1.length()) {
/*  950 */       s = String.valueOf(s) + s1.substring(0, k);
/*  951 */       l = k;
/*      */     } else {
/*  953 */       s = String.valueOf(s) + s1;
/*  954 */       l = s1.length();
/*      */     } 
/*      */     
/*  957 */     if (this.text.length() > 0 && j < this.text.length()) {
/*  958 */       s = String.valueOf(s) + this.text.substring(j);
/*      */     }
/*      */     
/*  961 */     if (this.field_175209_y.apply(s)) {
/*  962 */       this.text = s;
/*  963 */       moveCursorBy(i - this.selectionEnd + l);
/*      */       
/*  965 */       if (this.field_175210_x != null) {
/*  966 */         this.field_175210_x.func_175319_a(this.id, this.text);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void deleteWords(int p_146177_1_) {
/*  976 */     if (this.text.length() != 0) {
/*  977 */       if (this.selectionEnd != this.cursorPosition) {
/*  978 */         writeText("");
/*      */       } else {
/*  980 */         deleteFromCursor(getNthWordFromCursor(p_146177_1_) - this.cursorPosition);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void deleteFromCursor(int p_146175_1_) {
/*  989 */     if (this.text.length() != 0) {
/*  990 */       if (this.selectionEnd != this.cursorPosition) {
/*  991 */         writeText("");
/*      */       } else {
/*  993 */         boolean flag = (p_146175_1_ < 0);
/*  994 */         int i = flag ? (this.cursorPosition + p_146175_1_) : this.cursorPosition;
/*  995 */         int j = flag ? this.cursorPosition : (this.cursorPosition + p_146175_1_);
/*  996 */         String s = "";
/*      */         
/*  998 */         if (i >= 0) {
/*  999 */           s = this.text.substring(0, i);
/*      */         }
/*      */         
/* 1002 */         if (j < this.text.length()) {
/* 1003 */           s = String.valueOf(s) + this.text.substring(j);
/*      */         }
/*      */         
/* 1006 */         if (this.field_175209_y.apply(s)) {
/* 1007 */           this.text = s;
/*      */           
/* 1009 */           if (flag) {
/* 1010 */             moveCursorBy(p_146175_1_);
/*      */           }
/*      */           
/* 1013 */           if (this.field_175210_x != null) {
/* 1014 */             this.field_175210_x.func_175319_a(this.id, this.text);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public int getId() {
/* 1022 */     return this.id;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNthWordFromCursor(int p_146187_1_) {
/* 1029 */     return getNthWordFromPos(p_146187_1_, getCursorPosition());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNthWordFromPos(int p_146183_1_, int p_146183_2_) {
/* 1036 */     return func_146197_a(p_146183_1_, p_146183_2_, true);
/*      */   }
/*      */   
/*      */   public int func_146197_a(int p_146197_1_, int p_146197_2_, boolean p_146197_3_) {
/* 1040 */     int i = p_146197_2_;
/* 1041 */     boolean flag = (p_146197_1_ < 0);
/* 1042 */     int j = Math.abs(p_146197_1_);
/*      */     
/* 1044 */     for (int k = 0; k < j; k++) {
/* 1045 */       if (!flag) {
/* 1046 */         int l = this.text.length();
/* 1047 */         i = this.text.indexOf(' ', i);
/*      */         
/* 1049 */         if (i == -1) {
/* 1050 */           i = l;
/*      */         } else {
/* 1052 */           while (p_146197_3_ && i < l && this.text.charAt(i) == ' ') {
/* 1053 */             i++;
/*      */           }
/*      */         } 
/*      */       } else {
/* 1057 */         while (p_146197_3_ && i > 0 && this.text.charAt(i - 1) == ' ') {
/* 1058 */           i--;
/*      */         }
/*      */         
/* 1061 */         while (i > 0 && this.text.charAt(i - 1) != ' ') {
/* 1062 */           i--;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1067 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void moveCursorBy(int p_146182_1_) {
/* 1074 */     setCursorPosition(this.selectionEnd + p_146182_1_);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCursorPosition(int p_146190_1_) {
/* 1081 */     this.cursorPosition = p_146190_1_;
/* 1082 */     int i = this.text.length();
/* 1083 */     this.cursorPosition = MathHelper.clamp_int(this.cursorPosition, 0, i);
/* 1084 */     setSelectionPos(this.cursorPosition);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCursorPositionZero() {
/* 1091 */     setCursorPosition(0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCursorPositionEnd() {
/* 1098 */     setCursorPosition(this.text.length());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean textboxKeyTyped(char p_146201_1_, int p_146201_2_) {
/* 1105 */     return !this.isFocused ? false : this.ime.onTyped(p_146201_2_, p_146201_1_);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void mouseClicked(int p_146192_1_, int p_146192_2_, int p_146192_3_) {
/* 1112 */     boolean flag = (p_146192_1_ >= this.xPosition && p_146192_1_ < this.xPosition + this.width && p_146192_2_ >= this.yPosition && p_146192_2_ < this.yPosition + this.height);
/*      */     
/* 1114 */     if (this.canLoseFocus) {
/* 1115 */       setFocused(flag);
/*      */     }
/*      */     
/* 1118 */     if (this.isFocused && flag && p_146192_3_ == 0) {
/* 1119 */       int i = p_146192_1_ - this.xPosition;
/*      */       
/* 1121 */       if (this.enableBackgroundDrawing) {
/* 1122 */         i -= 4;
/*      */       }
/*      */       
/* 1125 */       String s = this.fontRendererInstance.trimStringToWidth(this.text.substring(this.lineScrollOffset), getWidth());
/* 1126 */       setCursorPosition(this.fontRendererInstance.trimStringToWidth(s, i).length() + this.lineScrollOffset);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawTextBox() {
/* 1134 */     if (getVisible()) {
/* 1135 */       if (getEnableBackgroundDrawing()) {
/* 1136 */         drawRect(this.xPosition - 1, this.yPosition - 1, this.xPosition + this.width + 1, this.yPosition + this.height + 1, -6250336);
/* 1137 */         drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, -16777216);
/*      */       } 
/*      */       
/* 1140 */       int i = this.isEnabled ? this.enabledColor : this.disabledColor;
/* 1141 */       int j = this.cursorPosition - this.lineScrollOffset;
/* 1142 */       int k = this.selectionEnd - this.lineScrollOffset;
/* 1143 */       String s = this.fontRendererInstance.trimStringToWidth(this.text.substring(this.lineScrollOffset), getWidth());
/* 1144 */       boolean flag = (j >= 0 && j <= s.length());
/* 1145 */       boolean flag1 = (this.isFocused && this.cursorCounter / 6 % 2 == 0 && flag);
/* 1146 */       int l = this.enableBackgroundDrawing ? (this.xPosition + 4) : this.xPosition;
/* 1147 */       int i1 = this.enableBackgroundDrawing ? (this.yPosition + (this.height - 8) / 2) : this.yPosition;
/* 1148 */       int j1 = l;
/*      */       
/* 1150 */       if (k > s.length()) {
/* 1151 */         k = s.length();
/*      */       }
/*      */       
/* 1154 */       if (s.length() > 0) {
/* 1155 */         String s1 = flag ? s.substring(0, j) : s;
/* 1156 */         j1 = this.fontRendererInstance.drawStringWithShadow(s1, l, i1, i);
/*      */       } 
/*      */       
/* 1159 */       boolean flag2 = !(this.cursorPosition >= this.text.length() && this.text.length() < getMaxStringLength());
/* 1160 */       int k1 = j1;
/*      */       
/* 1162 */       if (!flag) {
/* 1163 */         k1 = (j > 0) ? (l + this.width) : l;
/* 1164 */       } else if (flag2) {
/* 1165 */         k1 = j1 - 1;
/* 1166 */         j1--;
/*      */       } 
/*      */       
/* 1169 */       if (s.length() > 0 && flag && j < s.length()) {
/* 1170 */         j1 = this.fontRendererInstance.drawStringWithShadow(s.substring(j), j1, i1, i);
/*      */       }
/*      */       
/* 1173 */       if (flag1) {
/* 1174 */         if (flag2) {
/* 1175 */           Gui.drawRect(k1, i1 - 1, k1 + 1, i1 + 1 + this.fontRendererInstance.FONT_HEIGHT, -3092272);
/*      */         } else {
/* 1177 */           this.fontRendererInstance.drawStringWithShadow("_", k1, i1, i);
/*      */         } 
/*      */       }
/*      */       
/* 1181 */       if (k != j) {
/* 1182 */         int l1 = l + this.fontRendererInstance.getStringWidth(s.substring(0, k));
/* 1183 */         drawCursorVertical(k1, i1 - 1, l1 - 1, i1 + 1 + this.fontRendererInstance.FONT_HEIGHT);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void drawCursorVertical(int p_146188_1_, int p_146188_2_, int p_146188_3_, int p_146188_4_) {
/* 1192 */     if (p_146188_1_ < p_146188_3_) {
/* 1193 */       int i = p_146188_1_;
/* 1194 */       p_146188_1_ = p_146188_3_;
/* 1195 */       p_146188_3_ = i;
/*      */     } 
/*      */     
/* 1198 */     if (p_146188_2_ < p_146188_4_) {
/* 1199 */       int j = p_146188_2_;
/* 1200 */       p_146188_2_ = p_146188_4_;
/* 1201 */       p_146188_4_ = j;
/*      */     } 
/*      */     
/* 1204 */     if (p_146188_3_ > this.xPosition + this.width) {
/* 1205 */       p_146188_3_ = this.xPosition + this.width;
/*      */     }
/*      */     
/* 1208 */     if (p_146188_1_ > this.xPosition + this.width) {
/* 1209 */       p_146188_1_ = this.xPosition + this.width;
/*      */     }
/*      */     
/* 1212 */     Tessellator tessellator = Tessellator.getInstance();
/* 1213 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 1214 */     GlStateManager.color(0.0F, 0.0F, 255.0F, 255.0F);
/* 1215 */     GlStateManager.disableTexture2D();
/* 1216 */     GlStateManager.enableColorLogic();
/* 1217 */     GlStateManager.colorLogicOp(5387);
/* 1218 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION);
/* 1219 */     worldrenderer.pos(p_146188_1_, p_146188_4_, 0.0D).endVertex();
/* 1220 */     worldrenderer.pos(p_146188_3_, p_146188_4_, 0.0D).endVertex();
/* 1221 */     worldrenderer.pos(p_146188_3_, p_146188_2_, 0.0D).endVertex();
/* 1222 */     worldrenderer.pos(p_146188_1_, p_146188_2_, 0.0D).endVertex();
/* 1223 */     tessellator.draw();
/* 1224 */     GlStateManager.disableColorLogic();
/* 1225 */     GlStateManager.enableTexture2D();
/*      */   }
/*      */   
/*      */   public void setMaxStringLength(int p_146203_1_) {
/* 1229 */     this.maxStringLength = p_146203_1_;
/*      */     
/* 1231 */     if (this.text.length() > p_146203_1_) {
/* 1232 */       this.text = this.text.substring(0, p_146203_1_);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxStringLength() {
/* 1240 */     return this.maxStringLength;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getCursorPosition() {
/* 1247 */     return this.cursorPosition;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getEnableBackgroundDrawing() {
/* 1254 */     return this.enableBackgroundDrawing;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEnableBackgroundDrawing(boolean p_146185_1_) {
/* 1261 */     this.enableBackgroundDrawing = p_146185_1_;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTextColor(int p_146193_1_) {
/* 1268 */     this.enabledColor = p_146193_1_;
/*      */   }
/*      */   
/*      */   public void setDisabledTextColour(int p_146204_1_) {
/* 1272 */     this.disabledColor = p_146204_1_;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFocused(boolean p_146195_1_) {
/* 1279 */     if (p_146195_1_ && !this.isFocused) {
/* 1280 */       this.cursorCounter = 0;
/*      */     }
/*      */     
/* 1283 */     this.isFocused = p_146195_1_;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFocused() {
/* 1290 */     return this.isFocused;
/*      */   }
/*      */   
/*      */   public void setEnabled(boolean p_146184_1_) {
/* 1294 */     this.isEnabled = p_146184_1_;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSelectionEnd() {
/* 1301 */     return this.selectionEnd;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getWidth() {
/* 1308 */     return getEnableBackgroundDrawing() ? (this.width - 8) : this.width;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSelectionPos(int p_146199_1_) {
/* 1315 */     int i = this.text.length();
/*      */     
/* 1317 */     if (p_146199_1_ > i) {
/* 1318 */       p_146199_1_ = i;
/*      */     }
/*      */     
/* 1321 */     if (p_146199_1_ < 0) {
/* 1322 */       p_146199_1_ = 0;
/*      */     }
/*      */     
/* 1325 */     this.selectionEnd = p_146199_1_;
/*      */     
/* 1327 */     if (this.fontRendererInstance != null) {
/* 1328 */       if (this.lineScrollOffset > i) {
/* 1329 */         this.lineScrollOffset = i;
/*      */       }
/*      */       
/* 1332 */       int j = getWidth();
/* 1333 */       String s = this.fontRendererInstance.trimStringToWidth(this.text.substring(this.lineScrollOffset), j);
/* 1334 */       int k = s.length() + this.lineScrollOffset;
/*      */       
/* 1336 */       if (p_146199_1_ == this.lineScrollOffset) {
/* 1337 */         this.lineScrollOffset -= this.fontRendererInstance.trimStringToWidth(this.text, j, true).length();
/*      */       }
/*      */       
/* 1340 */       if (p_146199_1_ > k) {
/* 1341 */         this.lineScrollOffset += p_146199_1_ - k;
/* 1342 */       } else if (p_146199_1_ <= this.lineScrollOffset) {
/* 1343 */         this.lineScrollOffset -= this.lineScrollOffset - p_146199_1_;
/*      */       } 
/*      */       
/* 1346 */       this.lineScrollOffset = MathHelper.clamp_int(this.lineScrollOffset, 0, i);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCanLoseFocus(boolean p_146205_1_) {
/* 1354 */     this.canLoseFocus = p_146205_1_;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getVisible() {
/* 1361 */     return this.visible;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setVisible(boolean p_146189_1_) {
/* 1368 */     this.visible = p_146189_1_;
/*      */   }
/*      */   
/*      */   public String getTargetText() {
/* 1372 */     return this.text;
/*      */   }
/*      */   
/*      */   public boolean setTargetText(String p_setTargetText_1_) {
/* 1376 */     if (this.field_175209_y.apply(p_setTargetText_1_)) {
/* 1377 */       if (p_setTargetText_1_.length() > this.maxStringLength) {
/* 1378 */         this.text = p_setTargetText_1_.substring(0, this.maxStringLength);
/*      */       } else {
/* 1380 */         this.text = p_setTargetText_1_;
/*      */       } 
/*      */       
/* 1383 */       return true;
/*      */     } 
/* 1385 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getCursor() {
/* 1390 */     return getCursorPosition();
/*      */   }
/*      */   
/*      */   public void setCursor(int p_setCursor_1_) {
/* 1394 */     setCursorPosition(p_setCursor_1_);
/*      */   }
/*      */   
/*      */   public int getSelection() {
/* 1398 */     return getSelectionEnd();
/*      */   }
/*      */   
/*      */   public void setSelection(int p_setSelection_1_) {
/* 1402 */     setSelectionPos(p_setSelection_1_);
/*      */   }
/*      */   
/*      */   public void writeTextFunc(String p_writeTextFunc_1_) {
/* 1406 */     writeText(p_writeTextFunc_1_);
/*      */   }
/*      */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiTextField.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */