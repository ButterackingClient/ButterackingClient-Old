/*     */ package net.minecraft.network;
/*     */ 
/*     */ import com.google.common.base.Charsets;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInboundHandlerAdapter;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import java.net.InetSocketAddress;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class PingResponseHandler
/*     */   extends ChannelInboundHandlerAdapter {
/*  17 */   private static final Logger logger = LogManager.getLogger();
/*     */   private NetworkSystem networkSystem;
/*     */   
/*     */   public PingResponseHandler(NetworkSystem networkSystemIn) {
/*  21 */     this.networkSystem = networkSystemIn;
/*     */   }
/*     */   
/*     */   public void channelRead(ChannelHandlerContext p_channelRead_1_, Object p_channelRead_2_) throws Exception {
/*  25 */     ByteBuf bytebuf = (ByteBuf)p_channelRead_2_;
/*  26 */     bytebuf.markReaderIndex();
/*  27 */     boolean flag = true;
/*     */     
/*     */     try {
/*  30 */       if (bytebuf.readUnsignedByte() == 254) {
/*  31 */         String s2, s; boolean flag1; int m; boolean bool1; int k, j; String s1; ByteBuf bytebuf1; InetSocketAddress inetsocketaddress = (InetSocketAddress)p_channelRead_1_.channel().remoteAddress();
/*  32 */         MinecraftServer minecraftserver = this.networkSystem.getServer();
/*  33 */         int i = bytebuf.readableBytes();
/*     */         
/*  35 */         switch (i) {
/*     */           case 0:
/*  37 */             logger.debug("Ping: (<1.3.x) from {}:{}", new Object[] { inetsocketaddress.getAddress(), Integer.valueOf(inetsocketaddress.getPort()) });
/*  38 */             s2 = String.format("%s§%d§%d", new Object[] { minecraftserver.getMOTD(), Integer.valueOf(minecraftserver.getCurrentPlayerCount()), Integer.valueOf(minecraftserver.getMaxPlayers()) });
/*  39 */             writeAndFlush(p_channelRead_1_, getStringBuffer(s2));
/*     */             break;
/*     */           
/*     */           case 1:
/*  43 */             if (bytebuf.readUnsignedByte() != 1) {
/*     */               return;
/*     */             }
/*     */             
/*  47 */             logger.debug("Ping: (1.4-1.5.x) from {}:{}", new Object[] { inetsocketaddress.getAddress(), Integer.valueOf(inetsocketaddress.getPort()) });
/*  48 */             s = String.format("§1\000%d\000%s\000%s\000%d\000%d", new Object[] { Integer.valueOf(127), minecraftserver.getMinecraftVersion(), minecraftserver.getMOTD(), Integer.valueOf(minecraftserver.getCurrentPlayerCount()), Integer.valueOf(minecraftserver.getMaxPlayers()) });
/*  49 */             writeAndFlush(p_channelRead_1_, getStringBuffer(s));
/*     */             break;
/*     */           
/*     */           default:
/*  53 */             flag1 = (bytebuf.readUnsignedByte() == 1);
/*  54 */             m = flag1 & ((bytebuf.readUnsignedByte() == 250) ? 1 : 0);
/*  55 */             bool1 = m & "MC|PingHost".equals(new String(bytebuf.readBytes(bytebuf.readShort() * 2).array(), Charsets.UTF_16BE));
/*  56 */             j = bytebuf.readUnsignedShort();
/*  57 */             k = bool1 & ((bytebuf.readUnsignedByte() >= 73) ? 1 : 0);
/*  58 */             k &= (3 + (bytebuf.readBytes(bytebuf.readShort() * 2).array()).length + 4 == j) ? 1 : 0;
/*  59 */             k &= (bytebuf.readInt() <= 65535) ? 1 : 0;
/*  60 */             k &= (bytebuf.readableBytes() == 0) ? 1 : 0;
/*     */             
/*  62 */             if (k == 0) {
/*     */               return;
/*     */             }
/*     */             
/*  66 */             logger.debug("Ping: (1.6) from {}:{}", new Object[] { inetsocketaddress.getAddress(), Integer.valueOf(inetsocketaddress.getPort()) });
/*  67 */             s1 = String.format("§1\000%d\000%s\000%s\000%d\000%d", new Object[] { Integer.valueOf(127), minecraftserver.getMinecraftVersion(), minecraftserver.getMOTD(), Integer.valueOf(minecraftserver.getCurrentPlayerCount()), Integer.valueOf(minecraftserver.getMaxPlayers()) });
/*  68 */             bytebuf1 = getStringBuffer(s1);
/*     */             
/*     */             try {
/*  71 */               writeAndFlush(p_channelRead_1_, bytebuf1);
/*     */             } finally {
/*  73 */               bytebuf1.release();
/*     */             } 
/*     */             break;
/*     */         } 
/*  77 */         bytebuf.release();
/*  78 */         flag = false;
/*     */         return;
/*     */       } 
/*  81 */     } catch (RuntimeException var21) {
/*     */       return;
/*     */     } finally {
/*  84 */       if (flag) {
/*  85 */         bytebuf.resetReaderIndex();
/*  86 */         p_channelRead_1_.channel().pipeline().remove("legacy_query");
/*  87 */         p_channelRead_1_.fireChannelRead(p_channelRead_2_);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void writeAndFlush(ChannelHandlerContext ctx, ByteBuf data) {
/*  93 */     ctx.pipeline().firstContext().writeAndFlush(data).addListener((GenericFutureListener)ChannelFutureListener.CLOSE);
/*     */   }
/*     */   
/*     */   private ByteBuf getStringBuffer(String string) {
/*  97 */     ByteBuf bytebuf = Unpooled.buffer();
/*  98 */     bytebuf.writeByte(255);
/*  99 */     char[] achar = string.toCharArray();
/* 100 */     bytebuf.writeShort(achar.length); byte b; int i;
/*     */     char[] arrayOfChar1;
/* 102 */     for (i = (arrayOfChar1 = achar).length, b = 0; b < i; ) { char c0 = arrayOfChar1[b];
/* 103 */       bytebuf.writeChar(c0);
/*     */       b++; }
/*     */     
/* 106 */     return bytebuf;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\PingResponseHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */