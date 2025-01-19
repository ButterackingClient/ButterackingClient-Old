/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandEffect
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  17 */     return "effect";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  24 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  31 */     return "commands.effect.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*  38 */     if (args.length < 2) {
/*  39 */       throw new WrongUsageException("commands.effect.usage", new Object[0]);
/*     */     }
/*  41 */     EntityLivingBase entitylivingbase = getEntity(sender, args[0], EntityLivingBase.class);
/*     */     
/*  43 */     if (args[1].equals("clear")) {
/*  44 */       if (entitylivingbase.getActivePotionEffects().isEmpty()) {
/*  45 */         throw new CommandException("commands.effect.failure.notActive.all", new Object[] { entitylivingbase.getName() });
/*     */       }
/*  47 */       entitylivingbase.clearActivePotions();
/*  48 */       notifyOperators(sender, this, "commands.effect.success.removed.all", new Object[] { entitylivingbase.getName() });
/*     */     } else {
/*     */       int i;
/*     */ 
/*     */       
/*     */       try {
/*  54 */         i = parseInt(args[1], 1);
/*  55 */       } catch (NumberInvalidException numberinvalidexception) {
/*  56 */         Potion potion = Potion.getPotionFromResourceLocation(args[1]);
/*     */         
/*  58 */         if (potion == null) {
/*  59 */           throw numberinvalidexception;
/*     */         }
/*     */         
/*  62 */         i = potion.id;
/*     */       } 
/*     */       
/*  65 */       int j = 600;
/*  66 */       int l = 30;
/*  67 */       int k = 0;
/*     */       
/*  69 */       if (i >= 0 && i < Potion.potionTypes.length && Potion.potionTypes[i] != null) {
/*  70 */         Potion potion1 = Potion.potionTypes[i];
/*     */         
/*  72 */         if (args.length >= 3) {
/*  73 */           l = parseInt(args[2], 0, 1000000);
/*     */           
/*  75 */           if (potion1.isInstant()) {
/*  76 */             j = l;
/*     */           } else {
/*  78 */             j = l * 20;
/*     */           } 
/*  80 */         } else if (potion1.isInstant()) {
/*  81 */           j = 1;
/*     */         } 
/*     */         
/*  84 */         if (args.length >= 4) {
/*  85 */           k = parseInt(args[3], 0, 255);
/*     */         }
/*     */         
/*  88 */         boolean flag = true;
/*     */         
/*  90 */         if (args.length >= 5 && "true".equalsIgnoreCase(args[4])) {
/*  91 */           flag = false;
/*     */         }
/*     */         
/*  94 */         if (l > 0) {
/*  95 */           PotionEffect potioneffect = new PotionEffect(i, j, k, false, flag);
/*  96 */           entitylivingbase.addPotionEffect(potioneffect);
/*  97 */           notifyOperators(sender, this, "commands.effect.success", new Object[] { new ChatComponentTranslation(potioneffect.getEffectName(), new Object[0]), Integer.valueOf(i), Integer.valueOf(k), entitylivingbase.getName(), Integer.valueOf(l) });
/*  98 */         } else if (entitylivingbase.isPotionActive(i)) {
/*  99 */           entitylivingbase.removePotionEffect(i);
/* 100 */           notifyOperators(sender, this, "commands.effect.success.removed", new Object[] { new ChatComponentTranslation(potion1.getName(), new Object[0]), entitylivingbase.getName() });
/*     */         } else {
/* 102 */           throw new CommandException("commands.effect.failure.notActive", new Object[] { new ChatComponentTranslation(potion1.getName(), new Object[0]), entitylivingbase.getName() });
/*     */         } 
/*     */       } else {
/* 105 */         throw new NumberInvalidException("commands.effect.notFound", new Object[] { Integer.valueOf(i) });
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 112 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, getAllUsernames()) : ((args.length == 2) ? getListOfStringsMatchingLastWord(args, Potion.getPotionLocations()) : ((args.length == 5) ? getListOfStringsMatchingLastWord(args, new String[] { "true", "false" }) : null));
/*     */   }
/*     */   
/*     */   protected String[] getAllUsernames() {
/* 116 */     return MinecraftServer.getServer().getAllUsernames();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsernameIndex(String[] args, int index) {
/* 123 */     return (index == 0);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\CommandEffect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */