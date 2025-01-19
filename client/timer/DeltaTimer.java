/*     */ package client.timer;
/*     */ 
/*     */ import java.awt.Font;
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.Timer;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DeltaTimer
/*     */   implements ActionListener
/*     */ {
/*     */   JFrame frame;
/*     */   JButton button;
/*     */   JButton rbutton;
/*     */   JLabel label;
/*     */   public int elapsedTime;
/*     */   public int seconds;
/*  24 */   public static DeltaTimer D = new DeltaTimer(); public int minutes; public int hours; boolean started; String seconds_string; String minutes_string; String hours_string;
/*     */   Timer timer;
/*     */   
/*     */   DeltaTimer() {
/*  28 */     this.frame = new JFrame();
/*  29 */     this.button = new JButton("Start");
/*  30 */     this.rbutton = new JButton("Reset");
/*  31 */     this.label = new JLabel();
/*  32 */     this.elapsedTime = 0;
/*  33 */     this.seconds = 0;
/*  34 */     this.minutes = 0;
/*  35 */     this.hours = 0;
/*  36 */     this.started = false;
/*  37 */     this.seconds_string = String.format("%02d", new Object[] { Integer.valueOf(this.seconds) });
/*  38 */     this.minutes_string = String.format("%02d", new Object[] { Integer.valueOf(this.minutes) });
/*  39 */     this.hours_string = String.format("%02d", new Object[] { Integer.valueOf(this.hours) });
/*  40 */     this.timer = new Timer(1000, new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e)
/*     */           {
/*  44 */             DeltaTimer this$2 = DeltaTimer.this, this$0 = DeltaTimer.this;
/*  45 */             DeltaTimer.this.elapsedTime += 1000;
/*  46 */             DeltaTimer.this.hours = DeltaTimer.this.elapsedTime / 3600000;
/*  47 */             DeltaTimer.this.minutes = DeltaTimer.this.elapsedTime / 60000 % 60;
/*  48 */             DeltaTimer.this.seconds = DeltaTimer.this.elapsedTime / 1000 % 60;
/*  49 */             DeltaTimer.this.seconds_string = String.format("%02d", new Object[] { Integer.valueOf(this.this$0.seconds) });
/*  50 */             DeltaTimer.this.minutes_string = String.format("%02d", new Object[] { Integer.valueOf(this.this$0.minutes) });
/*  51 */             DeltaTimer.this.hours_string = String.format("%02d", new Object[] { Integer.valueOf(this.this$0.hours) });
/*  52 */             DeltaTimer.this.label.setText(String.valueOf(String.valueOf(DeltaTimer.this.hours_string)) + ":" + DeltaTimer.this.minutes_string + ":" + DeltaTimer.this.seconds_string);
/*     */           }
/*     */         });
/*  55 */     this.label.setText(String.valueOf(String.valueOf(this.hours_string)) + ":" + this.minutes_string + ":" + this.seconds_string);
/*  56 */     this.label.setBounds(100, 100, 200, 100);
/*  57 */     this.label.setFont(new Font("Comic Sans MS", 0, 35));
/*  58 */     this.label.setBorder(BorderFactory.createBevelBorder(1));
/*  59 */     this.label.setOpaque(true);
/*  60 */     this.label.setHorizontalAlignment(0);
/*  61 */     this.button.setBounds(100, 200, 100, 50);
/*  62 */     this.button.setFont(new Font("Comic Sans MS", 0, 20));
/*  63 */     this.button.setFocusable(false);
/*  64 */     this.button.addActionListener(this);
/*  65 */     this.rbutton.setBounds(200, 200, 100, 50);
/*  66 */     this.rbutton.setFont(new Font("Comic Sans MS", 0, 20));
/*  67 */     this.rbutton.setFocusable(false);
/*  68 */     this.rbutton.addActionListener(this);
/*  69 */     this.frame.add(this.button);
/*  70 */     this.frame.add(this.rbutton);
/*  71 */     this.frame.add(this.label);
/*  72 */     this.frame.setDefaultCloseOperation(3);
/*  73 */     this.frame.setSize(400, 400);
/*  74 */     this.frame.setLayout((LayoutManager)null);
/*  75 */     this.frame.setVisible(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void actionPerformed(ActionEvent e) {
/*  80 */     if (e.getSource() == this.button) {
/*  81 */       start();
/*  82 */       if (!this.started) {
/*  83 */         this.started = true;
/*  84 */         this.button.setText("Stop");
/*  85 */         start();
/*     */       } else {
/*  87 */         this.started = false;
/*  88 */         this.button.setText("Start");
/*  89 */         stop();
/*     */       } 
/*     */     } 
/*  92 */     if (e.getSource() == this.rbutton) {
/*  93 */       this.started = false;
/*  94 */       this.button.setText("Start");
/*  95 */       reset();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void start() {
/* 100 */     this.timer.start();
/*     */   }
/*     */   
/*     */   public void stop() {
/* 104 */     this.timer.stop();
/*     */   }
/*     */   
/*     */   void reset() {
/* 108 */     this.timer.stop();
/* 109 */     this.elapsedTime = 0;
/* 110 */     this.seconds = 0;
/* 111 */     this.hours = 0;
/* 112 */     this.minutes = 0;
/* 113 */     this.seconds_string = String.format("%02d", new Object[] { Integer.valueOf(this.seconds) });
/* 114 */     this.minutes_string = String.format("%02d", new Object[] { Integer.valueOf(this.minutes) });
/* 115 */     this.hours_string = String.format("%02d", new Object[] { Integer.valueOf(this.hours) });
/* 116 */     this.label.setText(String.valueOf(String.valueOf(this.hours_string)) + ":" + this.minutes_string + ":" + this.seconds_string);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\timer\DeltaTimer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */