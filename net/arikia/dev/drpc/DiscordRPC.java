/*     */ package net.arikia.dev.drpc;
/*     */ 
/*     */ import com.sun.jna.Library;
/*     */ import com.sun.jna.Native;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DiscordRPC
/*     */ {
/*     */   private static final String DLL_VERSION = "3.4.0";
/*     */   private static final String LIB_VERSION = "1.6.2";
/*     */   
/*     */   static {
/*  21 */     loadDLL();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void discordInitialize(String applicationId, DiscordEventHandlers handlers, boolean autoRegister) {
/*  32 */     DLL.INSTANCE.Discord_Initialize(applicationId, handlers, autoRegister ? 1 : 0, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void discordRegister(String applicationId, String command) {
/*  43 */     DLL.INSTANCE.Discord_Register(applicationId, command);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void discordInitialize(String applicationId, DiscordEventHandlers handlers, boolean autoRegister, String steamId) {
/*  56 */     DLL.INSTANCE.Discord_Initialize(applicationId, handlers, autoRegister ? 1 : 0, steamId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void discordRegisterSteam(String applicationId, String steamId) {
/*  67 */     DLL.INSTANCE.Discord_RegisterSteamGame(applicationId, steamId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void discordUpdateEventHandlers(DiscordEventHandlers handlers) {
/*  77 */     DLL.INSTANCE.Discord_UpdateHandlers(handlers);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void discordShutdown() {
/*  84 */     DLL.INSTANCE.Discord_Shutdown();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void discordRunCallbacks() {
/*  92 */     DLL.INSTANCE.Discord_RunCallbacks();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void discordUpdatePresence(DiscordRichPresence presence) {
/* 102 */     DLL.INSTANCE.Discord_UpdatePresence(presence);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void discordClearPresence() {
/* 110 */     DLL.INSTANCE.Discord_ClearPresence();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void discordRespond(String userId, DiscordReply reply) {
/* 121 */     DLL.INSTANCE.Discord_Respond(userId, reply.reply);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void loadDLL() {
/* 126 */     String tempPath, dir, name = System.mapLibraryName("discord-rpc");
/* 127 */     OSUtil osUtil = new OSUtil();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 133 */     if (osUtil.isMac()) {
/* 134 */       File homeDir = new File(System.getProperty("user.home") + File.separator + "Library" + File.separator + "Application Support" + File.separator);
/* 135 */       dir = "darwin";
/* 136 */       tempPath = homeDir + File.separator + "discord-rpc" + File.separator + name;
/* 137 */     } else if (osUtil.isWindows()) {
/* 138 */       File homeDir = new File(System.getenv("TEMP"));
/* 139 */       boolean is64bit = System.getProperty("sun.arch.data.model").equals("64");
/* 140 */       dir = is64bit ? "win-x64" : "win-x86";
/* 141 */       tempPath = homeDir + File.separator + "discord-rpc" + File.separator + name;
/*     */     } else {
/* 143 */       File homeDir = new File(System.getProperty("user.home"), ".discord-rpc");
/* 144 */       dir = "linux";
/* 145 */       tempPath = homeDir + File.separator + name;
/*     */     } 
/*     */     
/* 148 */     String finalPath = "/" + dir + "/" + name;
/*     */     
/* 150 */     File f = new File(tempPath);
/*     */     
/* 152 */     try { InputStream in = DiscordRPC.class.getResourceAsStream(finalPath); try { OutputStream out = openOutputStream(f); 
/* 153 */         try { copyFile(in, out);
/* 154 */           f.deleteOnExit();
/* 155 */           if (out != null) out.close();  } catch (Throwable throwable) { if (out != null) try { out.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (in != null) in.close();  } catch (Throwable throwable) { if (in != null) try { in.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException e)
/* 156 */     { e.printStackTrace(); }
/*     */ 
/*     */     
/* 159 */     System.load(f.getAbsolutePath());
/*     */   }
/*     */   
/*     */   private static void copyFile(InputStream input, OutputStream output) throws IOException {
/* 163 */     byte[] buffer = new byte[4096];
/*     */     int n;
/* 165 */     while (-1 != (n = input.read(buffer))) {
/* 166 */       output.write(buffer, 0, n);
/*     */     }
/*     */   }
/*     */   
/*     */   private static FileOutputStream openOutputStream(File file) throws IOException {
/* 171 */     if (file.exists()) {
/* 172 */       if (file.isDirectory()) {
/* 173 */         throw new IOException("File '" + file + "' exists but is a directory");
/*     */       }
/* 175 */       if (!file.canWrite()) {
/* 176 */         throw new IOException("File '" + file + "' cannot be written to");
/*     */       }
/*     */     } else {
/* 179 */       File parent = file.getParentFile();
/* 180 */       if (parent != null && 
/* 181 */         !parent.mkdirs() && !parent.isDirectory()) {
/* 182 */         throw new IOException("Directory '" + parent + "' could not be created");
/*     */       }
/*     */     } 
/*     */     
/* 186 */     return new FileOutputStream(file);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum DiscordReply
/*     */   {
/* 201 */     NO(0),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 207 */     YES(1),
/*     */ 
/*     */ 
/*     */     
/* 211 */     IGNORE(2);
/*     */ 
/*     */     
/*     */     public final int reply;
/*     */ 
/*     */ 
/*     */     
/*     */     DiscordReply(int reply) {
/* 219 */       this.reply = reply;
/*     */     }
/*     */   }
/*     */   
/*     */   private static interface DLL
/*     */     extends Library
/*     */   {
/* 226 */     public static final DLL INSTANCE = (DLL)Native.loadLibrary("discord-rpc", DLL.class);
/*     */     
/*     */     void Discord_Initialize(String param1String1, DiscordEventHandlers param1DiscordEventHandlers, int param1Int, String param1String2);
/*     */     
/*     */     void Discord_Register(String param1String1, String param1String2);
/*     */     
/*     */     void Discord_RegisterSteamGame(String param1String1, String param1String2);
/*     */     
/*     */     void Discord_UpdateHandlers(DiscordEventHandlers param1DiscordEventHandlers);
/*     */     
/*     */     void Discord_Shutdown();
/*     */     
/*     */     void Discord_RunCallbacks();
/*     */     
/*     */     void Discord_UpdatePresence(DiscordRichPresence param1DiscordRichPresence);
/*     */     
/*     */     void Discord_ClearPresence();
/*     */     
/*     */     void Discord_Respond(String param1String, int param1Int);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\arikia\dev\drpc\DiscordRPC.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */