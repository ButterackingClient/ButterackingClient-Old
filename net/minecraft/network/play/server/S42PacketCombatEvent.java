/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.util.CombatTracker;
/*    */ 
/*    */ public class S42PacketCombatEvent
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   public Event eventType;
/*    */   public int field_179774_b;
/*    */   public int field_179775_c;
/*    */   public int field_179772_d;
/*    */   public String deathMessage;
/*    */   
/*    */   public S42PacketCombatEvent() {}
/*    */   
/*    */   public S42PacketCombatEvent(CombatTracker combatTrackerIn, Event combatEventType) {
/* 23 */     this.eventType = combatEventType;
/* 24 */     EntityLivingBase entitylivingbase = combatTrackerIn.func_94550_c();
/*    */     
/* 26 */     switch (combatEventType) {
/*    */       case null:
/* 28 */         this.field_179772_d = combatTrackerIn.func_180134_f();
/* 29 */         this.field_179775_c = (entitylivingbase == null) ? -1 : entitylivingbase.getEntityId();
/*    */         break;
/*    */       
/*    */       case ENTITY_DIED:
/* 33 */         this.field_179774_b = combatTrackerIn.getFighter().getEntityId();
/* 34 */         this.field_179775_c = (entitylivingbase == null) ? -1 : entitylivingbase.getEntityId();
/* 35 */         this.deathMessage = combatTrackerIn.getDeathMessage().getUnformattedText();
/*    */         break;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 43 */     this.eventType = (Event)buf.readEnumValue(Event.class);
/*    */     
/* 45 */     if (this.eventType == Event.END_COMBAT) {
/* 46 */       this.field_179772_d = buf.readVarIntFromBuffer();
/* 47 */       this.field_179775_c = buf.readInt();
/* 48 */     } else if (this.eventType == Event.ENTITY_DIED) {
/* 49 */       this.field_179774_b = buf.readVarIntFromBuffer();
/* 50 */       this.field_179775_c = buf.readInt();
/* 51 */       this.deathMessage = buf.readStringFromBuffer(32767);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 59 */     buf.writeEnumValue(this.eventType);
/*    */     
/* 61 */     if (this.eventType == Event.END_COMBAT) {
/* 62 */       buf.writeVarIntToBuffer(this.field_179772_d);
/* 63 */       buf.writeInt(this.field_179775_c);
/* 64 */     } else if (this.eventType == Event.ENTITY_DIED) {
/* 65 */       buf.writeVarIntToBuffer(this.field_179774_b);
/* 66 */       buf.writeInt(this.field_179775_c);
/* 67 */       buf.writeString(this.deathMessage);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 75 */     handler.handleCombatEvent(this);
/*    */   }
/*    */   
/*    */   public enum Event {
/* 79 */     ENTER_COMBAT,
/* 80 */     END_COMBAT,
/* 81 */     ENTITY_DIED;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S42PacketCombatEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */