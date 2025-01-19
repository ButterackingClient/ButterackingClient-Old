/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandParticle
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  16 */     return "particle";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  23 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  30 */     return "commands.particle.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*  37 */     if (args.length < 8) {
/*  38 */       throw new WrongUsageException("commands.particle.usage", new Object[0]);
/*     */     }
/*  40 */     boolean flag = false;
/*  41 */     EnumParticleTypes enumparticletypes = null; byte b; int j;
/*     */     EnumParticleTypes[] arrayOfEnumParticleTypes;
/*  43 */     for (j = (arrayOfEnumParticleTypes = EnumParticleTypes.values()).length, b = 0; b < j; ) { EnumParticleTypes enumparticletypes1 = arrayOfEnumParticleTypes[b];
/*  44 */       if (enumparticletypes1.hasArguments()) {
/*  45 */         if (args[0].startsWith(enumparticletypes1.getParticleName())) {
/*  46 */           flag = true;
/*  47 */           enumparticletypes = enumparticletypes1;
/*     */           break;
/*     */         } 
/*  50 */       } else if (args[0].equals(enumparticletypes1.getParticleName())) {
/*  51 */         flag = true;
/*  52 */         enumparticletypes = enumparticletypes1;
/*     */         break;
/*     */       } 
/*     */       b++; }
/*     */     
/*  57 */     if (!flag) {
/*  58 */       throw new CommandException("commands.particle.notFound", new Object[] { args[0] });
/*     */     }
/*  60 */     String s = args[0];
/*  61 */     Vec3 vec3 = sender.getPositionVector();
/*  62 */     double d6 = (float)parseDouble(vec3.xCoord, args[1], true);
/*  63 */     double d0 = (float)parseDouble(vec3.yCoord, args[2], true);
/*  64 */     double d1 = (float)parseDouble(vec3.zCoord, args[3], true);
/*  65 */     double d2 = (float)parseDouble(args[4]);
/*  66 */     double d3 = (float)parseDouble(args[5]);
/*  67 */     double d4 = (float)parseDouble(args[6]);
/*  68 */     double d5 = (float)parseDouble(args[7]);
/*  69 */     int i = 0;
/*     */     
/*  71 */     if (args.length > 8) {
/*  72 */       i = parseInt(args[8], 0);
/*     */     }
/*     */     
/*  75 */     boolean flag1 = false;
/*     */     
/*  77 */     if (args.length > 9 && "force".equals(args[9])) {
/*  78 */       flag1 = true;
/*     */     }
/*     */     
/*  81 */     World world = sender.getEntityWorld();
/*     */     
/*  83 */     if (world instanceof WorldServer) {
/*  84 */       WorldServer worldserver = (WorldServer)world;
/*  85 */       int[] aint = new int[enumparticletypes.getArgumentCount()];
/*     */       
/*  87 */       if (enumparticletypes.hasArguments()) {
/*  88 */         String[] astring = args[0].split("_", 3);
/*     */         
/*  90 */         for (int k = 1; k < astring.length; k++) {
/*     */           try {
/*  92 */             aint[k - 1] = Integer.parseInt(astring[k]);
/*  93 */           } catch (NumberFormatException var29) {
/*  94 */             throw new CommandException("commands.particle.notFound", new Object[] { args[0] });
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  99 */       worldserver.spawnParticle(enumparticletypes, flag1, d6, d0, d1, i, d2, d3, d4, d5, aint);
/* 100 */       notifyOperators(sender, this, "commands.particle.success", new Object[] { s, Integer.valueOf(Math.max(i, 1)) });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 107 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, EnumParticleTypes.getParticleNames()) : ((args.length > 1 && args.length <= 4) ? func_175771_a(args, 1, pos) : ((args.length == 10) ? getListOfStringsMatchingLastWord(args, new String[] { "normal", "force" }) : null));
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\CommandParticle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */