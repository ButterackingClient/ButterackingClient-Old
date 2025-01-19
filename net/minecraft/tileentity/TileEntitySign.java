/*     */ package net.minecraft.tileentity;
/*     */ 
/*     */ import com.google.gson.JsonParseException;
/*     */ import net.minecraft.command.CommandException;
/*     */ import net.minecraft.command.CommandResultStats;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.event.ClickEvent;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S33PacketUpdateSign;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentProcessor;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.ChatStyle;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class TileEntitySign extends TileEntity {
/*  23 */   public final IChatComponent[] signText = new IChatComponent[] { (IChatComponent)new ChatComponentText(""), (IChatComponent)new ChatComponentText(""), (IChatComponent)new ChatComponentText(""), (IChatComponent)new ChatComponentText("") };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  29 */   public int lineBeingEdited = -1;
/*     */   private boolean isEditable = true;
/*     */   private EntityPlayer player;
/*  32 */   private final CommandResultStats stats = new CommandResultStats();
/*     */   
/*     */   public void writeToNBT(NBTTagCompound compound) {
/*  35 */     super.writeToNBT(compound);
/*     */     
/*  37 */     for (int i = 0; i < 4; i++) {
/*  38 */       String s = IChatComponent.Serializer.componentToJson(this.signText[i]);
/*  39 */       compound.setString("Text" + (i + 1), s);
/*     */     } 
/*     */     
/*  42 */     this.stats.writeStatsToNBT(compound);
/*     */   }
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/*  46 */     this.isEditable = false;
/*  47 */     super.readFromNBT(compound);
/*  48 */     ICommandSender icommandsender = new ICommandSender() {
/*     */         public String getName() {
/*  50 */           return "Sign";
/*     */         }
/*     */         
/*     */         public IChatComponent getDisplayName() {
/*  54 */           return (IChatComponent)new ChatComponentText(getName());
/*     */         }
/*     */ 
/*     */         
/*     */         public void addChatMessage(IChatComponent component) {}
/*     */         
/*     */         public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
/*  61 */           return true;
/*     */         }
/*     */         
/*     */         public BlockPos getPosition() {
/*  65 */           return TileEntitySign.this.pos;
/*     */         }
/*     */         
/*     */         public Vec3 getPositionVector() {
/*  69 */           return new Vec3(TileEntitySign.this.pos.getX() + 0.5D, TileEntitySign.this.pos.getY() + 0.5D, TileEntitySign.this.pos.getZ() + 0.5D);
/*     */         }
/*     */         
/*     */         public World getEntityWorld() {
/*  73 */           return TileEntitySign.this.worldObj;
/*     */         }
/*     */         
/*     */         public Entity getCommandSenderEntity() {
/*  77 */           return null;
/*     */         }
/*     */         
/*     */         public boolean sendCommandFeedback() {
/*  81 */           return false;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public void setCommandStat(CommandResultStats.Type type, int amount) {}
/*     */       };
/*  88 */     for (int i = 0; i < 4; i++) {
/*  89 */       String s = compound.getString("Text" + (i + 1));
/*     */       
/*     */       try {
/*  92 */         IChatComponent ichatcomponent = IChatComponent.Serializer.jsonToComponent(s);
/*     */         
/*     */         try {
/*  95 */           this.signText[i] = ChatComponentProcessor.processComponent(icommandsender, ichatcomponent, null);
/*  96 */         } catch (CommandException var7) {
/*  97 */           this.signText[i] = ichatcomponent;
/*     */         } 
/*  99 */       } catch (JsonParseException var8) {
/* 100 */         this.signText[i] = (IChatComponent)new ChatComponentText(s);
/*     */       } 
/*     */     } 
/*     */     
/* 104 */     this.stats.readStatsFromNBT(compound);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Packet getDescriptionPacket() {
/* 112 */     IChatComponent[] aichatcomponent = new IChatComponent[4];
/* 113 */     System.arraycopy(this.signText, 0, aichatcomponent, 0, 4);
/* 114 */     return (Packet)new S33PacketUpdateSign(this.worldObj, this.pos, aichatcomponent);
/*     */   }
/*     */   
/*     */   public boolean func_183000_F() {
/* 118 */     return true;
/*     */   }
/*     */   
/*     */   public boolean getIsEditable() {
/* 122 */     return this.isEditable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEditable(boolean isEditableIn) {
/* 129 */     this.isEditable = isEditableIn;
/*     */     
/* 131 */     if (!isEditableIn) {
/* 132 */       this.player = null;
/*     */     }
/*     */   }
/*     */   
/*     */   public void setPlayer(EntityPlayer playerIn) {
/* 137 */     this.player = playerIn;
/*     */   }
/*     */   
/*     */   public EntityPlayer getPlayer() {
/* 141 */     return this.player;
/*     */   }
/*     */   
/*     */   public boolean executeCommand(final EntityPlayer playerIn) {
/* 145 */     ICommandSender icommandsender = new ICommandSender() {
/*     */         public String getName() {
/* 147 */           return playerIn.getName();
/*     */         }
/*     */         
/*     */         public IChatComponent getDisplayName() {
/* 151 */           return playerIn.getDisplayName();
/*     */         }
/*     */ 
/*     */         
/*     */         public void addChatMessage(IChatComponent component) {}
/*     */         
/*     */         public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
/* 158 */           return (permLevel <= 2);
/*     */         }
/*     */         
/*     */         public BlockPos getPosition() {
/* 162 */           return TileEntitySign.this.pos;
/*     */         }
/*     */         
/*     */         public Vec3 getPositionVector() {
/* 166 */           return new Vec3(TileEntitySign.this.pos.getX() + 0.5D, TileEntitySign.this.pos.getY() + 0.5D, TileEntitySign.this.pos.getZ() + 0.5D);
/*     */         }
/*     */         
/*     */         public World getEntityWorld() {
/* 170 */           return playerIn.getEntityWorld();
/*     */         }
/*     */         
/*     */         public Entity getCommandSenderEntity() {
/* 174 */           return (Entity)playerIn;
/*     */         }
/*     */         
/*     */         public boolean sendCommandFeedback() {
/* 178 */           return false;
/*     */         }
/*     */         
/*     */         public void setCommandStat(CommandResultStats.Type type, int amount) {
/* 182 */           TileEntitySign.this.stats.setCommandStatScore(this, type, amount);
/*     */         }
/*     */       };
/*     */     
/* 186 */     for (int i = 0; i < this.signText.length; i++) {
/* 187 */       ChatStyle chatstyle = (this.signText[i] == null) ? null : this.signText[i].getChatStyle();
/*     */       
/* 189 */       if (chatstyle != null && chatstyle.getChatClickEvent() != null) {
/* 190 */         ClickEvent clickevent = chatstyle.getChatClickEvent();
/*     */         
/* 192 */         if (clickevent.getAction() == ClickEvent.Action.RUN_COMMAND) {
/* 193 */           MinecraftServer.getServer().getCommandManager().executeCommand(icommandsender, clickevent.getValue());
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 198 */     return true;
/*     */   }
/*     */   
/*     */   public CommandResultStats getStats() {
/* 202 */     return this.stats;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\tileentity\TileEntitySign.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */