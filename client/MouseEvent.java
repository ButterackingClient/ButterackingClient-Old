/*    */ package client;
/*    */ 
/*    */ import client.event.EventCancelable;
/*    */ import org.lwjgl.input.Mouse;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MouseEvent
/*    */   extends EventCancelable
/*    */ {
/* 19 */   public final int x = Mouse.getEventX();
/* 20 */   public final int y = Mouse.getEventY();
/* 21 */   public final int dx = Mouse.getEventDX();
/* 22 */   public final int dy = Mouse.getEventDY();
/* 23 */   public final int dwheel = Mouse.getEventDWheel();
/* 24 */   public final int button = Mouse.getEventButton();
/* 25 */   public final boolean buttonstate = Mouse.getEventButtonState();
/* 26 */   public final long nanoseconds = Mouse.getEventNanoseconds();
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\MouseEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */