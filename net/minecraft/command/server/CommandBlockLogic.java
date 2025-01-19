/*     */ package net.minecraft.command.server;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.concurrent.Callable;
/*     */ import net.minecraft.command.CommandResultStats;
/*     */ import net.minecraft.command.ICommandManager;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class CommandBlockLogic
/*     */   implements ICommandSender
/*     */ {
/*     */   private static final SimpleDateFormat timestampFormat = new SimpleDateFormat("HH:mm:ss");
/*     */   private int successCount;
/*     */   
/*     */   public CommandBlockLogic() {
/*  32 */     this.trackOutput = true;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  37 */     this.lastOutput = null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  42 */     this.commandStored = "";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  47 */     this.customName = "@";
/*  48 */     this.resultStats = new CommandResultStats();
/*     */   }
/*     */   
/*     */   private boolean trackOutput;
/*     */   
/*     */   public int getSuccessCount() {
/*  54 */     return this.successCount;
/*     */   }
/*     */   
/*     */   private IChatComponent lastOutput;
/*     */   private String commandStored;
/*     */   
/*     */   public IChatComponent getLastOutput() {
/*  61 */     return this.lastOutput;
/*     */   }
/*     */   
/*     */   private String customName;
/*     */   private final CommandResultStats resultStats;
/*     */   
/*     */   public void writeDataToNBT(NBTTagCompound tagCompound) {
/*  68 */     tagCompound.setString("Command", this.commandStored);
/*  69 */     tagCompound.setInteger("SuccessCount", this.successCount);
/*  70 */     tagCompound.setString("CustomName", this.customName);
/*  71 */     tagCompound.setBoolean("TrackOutput", this.trackOutput);
/*     */     
/*  73 */     if (this.lastOutput != null && this.trackOutput) {
/*  74 */       tagCompound.setString("LastOutput", IChatComponent.Serializer.componentToJson(this.lastOutput));
/*     */     }
/*     */     
/*  77 */     this.resultStats.writeStatsToNBT(tagCompound);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readDataFromNBT(NBTTagCompound nbt) {
/*  84 */     this.commandStored = nbt.getString("Command");
/*  85 */     this.successCount = nbt.getInteger("SuccessCount");
/*     */     
/*  87 */     if (nbt.hasKey("CustomName", 8)) {
/*  88 */       this.customName = nbt.getString("CustomName");
/*     */     }
/*     */     
/*  91 */     if (nbt.hasKey("TrackOutput", 1)) {
/*  92 */       this.trackOutput = nbt.getBoolean("TrackOutput");
/*     */     }
/*     */     
/*  95 */     if (nbt.hasKey("LastOutput", 8) && this.trackOutput) {
/*  96 */       this.lastOutput = IChatComponent.Serializer.jsonToComponent(nbt.getString("LastOutput"));
/*     */     }
/*     */     
/*  99 */     this.resultStats.readStatsFromNBT(nbt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
/* 106 */     return (permLevel <= 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCommand(String command) {
/* 113 */     this.commandStored = command;
/* 114 */     this.successCount = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommand() {
/* 121 */     return this.commandStored;
/*     */   }
/*     */   
/*     */   public void trigger(World worldIn) {
/* 125 */     if (worldIn.isRemote) {
/* 126 */       this.successCount = 0;
/*     */     }
/*     */     
/* 129 */     MinecraftServer minecraftserver = MinecraftServer.getServer();
/*     */     
/* 131 */     if (minecraftserver != null && minecraftserver.isAnvilFileSet() && minecraftserver.isCommandBlockEnabled()) {
/* 132 */       ICommandManager icommandmanager = minecraftserver.getCommandManager();
/*     */       
/*     */       try {
/* 135 */         this.lastOutput = null;
/* 136 */         this.successCount = icommandmanager.executeCommand(this, this.commandStored);
/* 137 */       } catch (Throwable throwable) {
/* 138 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Executing command block");
/* 139 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("Command to be executed");
/* 140 */         crashreportcategory.addCrashSectionCallable("Command", new Callable<String>() {
/*     */               public String call() throws Exception {
/* 142 */                 return CommandBlockLogic.this.getCommand();
/*     */               }
/*     */             });
/* 145 */         crashreportcategory.addCrashSectionCallable("Name", new Callable<String>() {
/*     */               public String call() throws Exception {
/* 147 */                 return CommandBlockLogic.this.getName();
/*     */               }
/*     */             });
/* 150 */         throw new ReportedException(crashreport);
/*     */       } 
/*     */     } else {
/* 153 */       this.successCount = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 161 */     return this.customName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IChatComponent getDisplayName() {
/* 168 */     return (IChatComponent)new ChatComponentText(getName());
/*     */   }
/*     */   
/*     */   public void setName(String p_145754_1_) {
/* 172 */     this.customName = p_145754_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addChatMessage(IChatComponent component) {
/* 179 */     if (this.trackOutput && getEntityWorld() != null && !(getEntityWorld()).isRemote) {
/* 180 */       this.lastOutput = (new ChatComponentText("[" + timestampFormat.format(new Date()) + "] ")).appendSibling(component);
/* 181 */       updateCommand();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean sendCommandFeedback() {
/* 189 */     MinecraftServer minecraftserver = MinecraftServer.getServer();
/* 190 */     return !(minecraftserver != null && minecraftserver.isAnvilFileSet() && !minecraftserver.worldServers[0].getGameRules().getBoolean("commandBlockOutput"));
/*     */   }
/*     */   
/*     */   public void setCommandStat(CommandResultStats.Type type, int amount) {
/* 194 */     this.resultStats.setCommandStatScore(this, type, amount);
/*     */   }
/*     */   
/*     */   public abstract void updateCommand();
/*     */   
/*     */   public abstract int func_145751_f();
/*     */   
/*     */   public abstract void func_145757_a(ByteBuf paramByteBuf);
/*     */   
/*     */   public void setLastOutput(IChatComponent lastOutputMessage) {
/* 204 */     this.lastOutput = lastOutputMessage;
/*     */   }
/*     */   
/*     */   public void setTrackOutput(boolean shouldTrackOutput) {
/* 208 */     this.trackOutput = shouldTrackOutput;
/*     */   }
/*     */   
/*     */   public boolean shouldTrackOutput() {
/* 212 */     return this.trackOutput;
/*     */   }
/*     */   
/*     */   public boolean tryOpenEditCommandBlock(EntityPlayer playerIn) {
/* 216 */     if (!playerIn.capabilities.isCreativeMode) {
/* 217 */       return false;
/*     */     }
/* 219 */     if ((playerIn.getEntityWorld()).isRemote) {
/* 220 */       playerIn.openEditCommandBlock(this);
/*     */     }
/*     */     
/* 223 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public CommandResultStats getCommandResultStats() {
/* 228 */     return this.resultStats;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\server\CommandBlockLogic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */