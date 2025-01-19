/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import net.minecraft.profiler.Profiler;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class CommandDebug
/*     */   extends CommandBase {
/*  16 */   private static final Logger logger = LogManager.getLogger();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long profileStartTime;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int profileStartTick;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandName() {
/*  32 */     return "debug";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  39 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  46 */     return "commands.debug.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*  53 */     if (args.length < 1) {
/*  54 */       throw new WrongUsageException("commands.debug.usage", new Object[0]);
/*     */     }
/*  56 */     if (args[0].equals("start")) {
/*  57 */       if (args.length != 1) {
/*  58 */         throw new WrongUsageException("commands.debug.usage", new Object[0]);
/*     */       }
/*     */       
/*  61 */       notifyOperators(sender, this, "commands.debug.start", new Object[0]);
/*  62 */       MinecraftServer.getServer().enableProfiling();
/*  63 */       this.profileStartTime = MinecraftServer.getCurrentTimeMillis();
/*  64 */       this.profileStartTick = MinecraftServer.getServer().getTickCounter();
/*     */     } else {
/*  66 */       if (!args[0].equals("stop")) {
/*  67 */         throw new WrongUsageException("commands.debug.usage", new Object[0]);
/*     */       }
/*     */       
/*  70 */       if (args.length != 1) {
/*  71 */         throw new WrongUsageException("commands.debug.usage", new Object[0]);
/*     */       }
/*     */       
/*  74 */       if (!(MinecraftServer.getServer()).theProfiler.profilingEnabled) {
/*  75 */         throw new CommandException("commands.debug.notStarted", new Object[0]);
/*     */       }
/*     */       
/*  78 */       long i = MinecraftServer.getCurrentTimeMillis();
/*  79 */       int j = MinecraftServer.getServer().getTickCounter();
/*  80 */       long k = i - this.profileStartTime;
/*  81 */       int l = j - this.profileStartTick;
/*  82 */       saveProfileResults(k, l);
/*  83 */       (MinecraftServer.getServer()).theProfiler.profilingEnabled = false;
/*  84 */       notifyOperators(sender, this, "commands.debug.stop", new Object[] { Float.valueOf((float)k / 1000.0F), Integer.valueOf(l) });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void saveProfileResults(long timeSpan, int tickSpan) {
/*  93 */     File file1 = new File(MinecraftServer.getServer().getFile("debug"), "profile-results-" + (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()) + ".txt");
/*  94 */     file1.getParentFile().mkdirs();
/*     */     
/*     */     try {
/*  97 */       FileWriter filewriter = new FileWriter(file1);
/*  98 */       filewriter.write(getProfileResults(timeSpan, tickSpan));
/*  99 */       filewriter.close();
/* 100 */     } catch (Throwable throwable) {
/* 101 */       logger.error("Could not save profiler results to " + file1, throwable);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getProfileResults(long timeSpan, int tickSpan) {
/* 109 */     StringBuilder stringbuilder = new StringBuilder();
/* 110 */     stringbuilder.append("---- Minecraft Profiler Results ----\n");
/* 111 */     stringbuilder.append("// ");
/* 112 */     stringbuilder.append(getWittyComment());
/* 113 */     stringbuilder.append("\n\n");
/* 114 */     stringbuilder.append("Time span: ").append(timeSpan).append(" ms\n");
/* 115 */     stringbuilder.append("Tick span: ").append(tickSpan).append(" ticks\n");
/* 116 */     stringbuilder.append("// This is approximately ").append(String.format("%.2f", new Object[] { Float.valueOf(tickSpan / (float)timeSpan / 1000.0F) })).append(" ticks per second. It should be ").append(20).append(" ticks per second\n\n");
/* 117 */     stringbuilder.append("--- BEGIN PROFILE DUMP ---\n\n");
/* 118 */     func_147202_a(0, "root", stringbuilder);
/* 119 */     stringbuilder.append("--- END PROFILE DUMP ---\n\n");
/* 120 */     return stringbuilder.toString();
/*     */   }
/*     */   
/*     */   private void func_147202_a(int p_147202_1_, String p_147202_2_, StringBuilder stringBuilder) {
/* 124 */     List<Profiler.Result> list = (MinecraftServer.getServer()).theProfiler.getProfilingData(p_147202_2_);
/*     */     
/* 126 */     if (list != null && list.size() >= 3) {
/* 127 */       for (int i = 1; i < list.size(); i++) {
/* 128 */         Profiler.Result profiler$result = list.get(i);
/* 129 */         stringBuilder.append(String.format("[%02d] ", new Object[] { Integer.valueOf(p_147202_1_) }));
/*     */         
/* 131 */         for (int j = 0; j < p_147202_1_; j++) {
/* 132 */           stringBuilder.append(" ");
/*     */         }
/*     */         
/* 135 */         stringBuilder.append(profiler$result.field_76331_c).append(" - ").append(String.format("%.2f", new Object[] { Double.valueOf(profiler$result.field_76332_a) })).append("%/").append(String.format("%.2f", new Object[] { Double.valueOf(profiler$result.field_76330_b) })).append("%\n");
/*     */         
/* 137 */         if (!profiler$result.field_76331_c.equals("unspecified")) {
/*     */           try {
/* 139 */             func_147202_a(p_147202_1_ + 1, String.valueOf(p_147202_2_) + "." + profiler$result.field_76331_c, stringBuilder);
/* 140 */           } catch (Exception exception) {
/* 141 */             stringBuilder.append("[[ EXCEPTION ").append(exception).append(" ]]");
/*     */           } 
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getWittyComment() {
/* 152 */     String[] astring = { "Shiny numbers!", "Am I not running fast enough? :(", "I'm working as hard as I can!", "Will I ever be good enough for you? :(", "Speedy. Zoooooom!", "Hello world", "40% better than a crash report.", "Now with extra numbers", "Now with less numbers", "Now with the same numbers", "You should add flames to things, it makes them go faster!", "Do you feel the need for... optimization?", "*cracks redstone whip*", "Maybe if you treated it better then it'll have more motivation to work faster! Poor server." };
/*     */     
/*     */     try {
/* 155 */       return astring[(int)(System.nanoTime() % astring.length)];
/* 156 */     } catch (Throwable var2) {
/* 157 */       return "Witty comment unavailable :(";
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 162 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, new String[] { "start", "stop" }) : null;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\CommandDebug.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */