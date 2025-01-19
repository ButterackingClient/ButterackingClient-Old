/*    */ package net.minecraft.network.rcon;
/*    */ 
/*    */ import net.minecraft.command.CommandResultStats;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.ChatComponentText;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ import net.minecraft.util.Vec3;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ public class RConConsoleSource
/*    */   implements ICommandSender
/*    */ {
/* 17 */   private static final RConConsoleSource instance = new RConConsoleSource();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 22 */   private StringBuffer buffer = new StringBuffer();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 28 */     return "Rcon";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IChatComponent getDisplayName() {
/* 35 */     return (IChatComponent)new ChatComponentText(getName());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addChatMessage(IChatComponent component) {
/* 42 */     this.buffer.append(component.getUnformattedText());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
/* 49 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BlockPos getPosition() {
/* 57 */     return new BlockPos(0, 0, 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Vec3 getPositionVector() {
/* 65 */     return new Vec3(0.0D, 0.0D, 0.0D);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public World getEntityWorld() {
/* 73 */     return MinecraftServer.getServer().getEntityWorld();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Entity getCommandSenderEntity() {
/* 80 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean sendCommandFeedback() {
/* 87 */     return true;
/*    */   }
/*    */   
/*    */   public void setCommandStat(CommandResultStats.Type type, int amount) {}
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\rcon\RConConsoleSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */