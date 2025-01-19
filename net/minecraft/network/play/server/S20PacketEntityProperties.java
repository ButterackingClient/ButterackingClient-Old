/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ 
/*     */ public class S20PacketEntityProperties
/*     */   implements Packet<INetHandlerPlayClient> {
/*     */   private int entityId;
/*  18 */   private final List<Snapshot> field_149444_b = Lists.newArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public S20PacketEntityProperties(int entityIdIn, Collection<IAttributeInstance> p_i45236_2_) {
/*  24 */     this.entityId = entityIdIn;
/*     */     
/*  26 */     for (IAttributeInstance iattributeinstance : p_i45236_2_) {
/*  27 */       this.field_149444_b.add(new Snapshot(iattributeinstance.getAttribute().getAttributeUnlocalizedName(), iattributeinstance.getBaseValue(), iattributeinstance.func_111122_c()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  35 */     this.entityId = buf.readVarIntFromBuffer();
/*  36 */     int i = buf.readInt();
/*     */     
/*  38 */     for (int j = 0; j < i; j++) {
/*  39 */       String s = buf.readStringFromBuffer(64);
/*  40 */       double d0 = buf.readDouble();
/*  41 */       List<AttributeModifier> list = Lists.newArrayList();
/*  42 */       int k = buf.readVarIntFromBuffer();
/*     */       
/*  44 */       for (int l = 0; l < k; l++) {
/*  45 */         UUID uuid = buf.readUuid();
/*  46 */         list.add(new AttributeModifier(uuid, "Unknown synced attribute modifier", buf.readDouble(), buf.readByte()));
/*     */       } 
/*     */       
/*  49 */       this.field_149444_b.add(new Snapshot(s, d0, list));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  57 */     buf.writeVarIntToBuffer(this.entityId);
/*  58 */     buf.writeInt(this.field_149444_b.size());
/*     */     
/*  60 */     for (Snapshot s20packetentityproperties$snapshot : this.field_149444_b) {
/*  61 */       buf.writeString(s20packetentityproperties$snapshot.func_151409_a());
/*  62 */       buf.writeDouble(s20packetentityproperties$snapshot.func_151410_b());
/*  63 */       buf.writeVarIntToBuffer(s20packetentityproperties$snapshot.func_151408_c().size());
/*     */       
/*  65 */       for (AttributeModifier attributemodifier : s20packetentityproperties$snapshot.func_151408_c()) {
/*  66 */         buf.writeUuid(attributemodifier.getID());
/*  67 */         buf.writeDouble(attributemodifier.getAmount());
/*  68 */         buf.writeByte(attributemodifier.getOperation());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/*  77 */     handler.handleEntityProperties(this);
/*     */   }
/*     */   
/*     */   public int getEntityId() {
/*  81 */     return this.entityId;
/*     */   }
/*     */   
/*     */   public List<Snapshot> func_149441_d() {
/*  85 */     return this.field_149444_b;
/*     */   }
/*     */   
/*     */   public S20PacketEntityProperties() {}
/*     */   
/*     */   public class Snapshot {
/*     */     private final String field_151412_b;
/*     */     
/*     */     public Snapshot(String p_i45235_2_, double p_i45235_3_, Collection<AttributeModifier> p_i45235_5_) {
/*  94 */       this.field_151412_b = p_i45235_2_;
/*  95 */       this.field_151413_c = p_i45235_3_;
/*  96 */       this.field_151411_d = p_i45235_5_;
/*     */     }
/*     */     private final double field_151413_c; private final Collection<AttributeModifier> field_151411_d;
/*     */     public String func_151409_a() {
/* 100 */       return this.field_151412_b;
/*     */     }
/*     */     
/*     */     public double func_151410_b() {
/* 104 */       return this.field_151413_c;
/*     */     }
/*     */     
/*     */     public Collection<AttributeModifier> func_151408_c() {
/* 108 */       return this.field_151411_d;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S20PacketEntityProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */