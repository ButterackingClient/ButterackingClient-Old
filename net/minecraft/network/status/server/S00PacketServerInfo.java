/*    */ package net.minecraft.network.status.server;
/*    */ 
/*    */ import com.google.gson.Gson;
/*    */ import com.google.gson.GsonBuilder;
/*    */ import com.google.gson.TypeAdapterFactory;
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.ServerStatusResponse;
/*    */ import net.minecraft.network.status.INetHandlerStatusClient;
/*    */ import net.minecraft.util.ChatStyle;
/*    */ import net.minecraft.util.EnumTypeAdapterFactory;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ 
/*    */ public class S00PacketServerInfo implements Packet<INetHandlerStatusClient> {
/* 17 */   private static final Gson GSON = (new GsonBuilder()).registerTypeAdapter(ServerStatusResponse.MinecraftProtocolVersionIdentifier.class, new ServerStatusResponse.MinecraftProtocolVersionIdentifier.Serializer()).registerTypeAdapter(ServerStatusResponse.PlayerCountData.class, new ServerStatusResponse.PlayerCountData.Serializer()).registerTypeAdapter(ServerStatusResponse.class, new ServerStatusResponse.Serializer()).registerTypeHierarchyAdapter(IChatComponent.class, new IChatComponent.Serializer()).registerTypeHierarchyAdapter(ChatStyle.class, new ChatStyle.Serializer()).registerTypeAdapterFactory((TypeAdapterFactory)new EnumTypeAdapterFactory()).create();
/*    */   
/*    */   private ServerStatusResponse response;
/*    */   
/*    */   public S00PacketServerInfo() {}
/*    */   
/*    */   public S00PacketServerInfo(ServerStatusResponse responseIn) {
/* 24 */     this.response = responseIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 31 */     this.response = (ServerStatusResponse)GSON.fromJson(buf.readStringFromBuffer(32767), ServerStatusResponse.class);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 38 */     buf.writeString(GSON.toJson(this.response));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerStatusClient handler) {
/* 45 */     handler.handleServerInfo(this);
/*    */   }
/*    */   
/*    */   public ServerStatusResponse getResponse() {
/* 49 */     return this.response;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\status\server\S00PacketServerInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */