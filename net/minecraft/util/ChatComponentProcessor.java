/*    */ package net.minecraft.util;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.command.CommandException;
/*    */ import net.minecraft.command.EntityNotFoundException;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.command.PlayerSelector;
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ public class ChatComponentProcessor
/*    */ {
/*    */   public static IChatComponent processComponent(ICommandSender commandSender, IChatComponent component, Entity entityIn) throws CommandException {
/* 13 */     IChatComponent ichatcomponent = null;
/*    */     
/* 15 */     if (component instanceof ChatComponentScore) {
/* 16 */       ChatComponentScore chatcomponentscore = (ChatComponentScore)component;
/* 17 */       String s = chatcomponentscore.getName();
/*    */       
/* 19 */       if (PlayerSelector.hasArguments(s)) {
/* 20 */         List<Entity> list = PlayerSelector.matchEntities(commandSender, s, Entity.class);
/*    */         
/* 22 */         if (list.size() != 1) {
/* 23 */           throw new EntityNotFoundException();
/*    */         }
/*    */         
/* 26 */         s = ((Entity)list.get(0)).getName();
/*    */       } 
/*    */       
/* 29 */       ichatcomponent = (entityIn != null && s.equals("*")) ? new ChatComponentScore(entityIn.getName(), chatcomponentscore.getObjective()) : new ChatComponentScore(s, chatcomponentscore.getObjective());
/* 30 */       ((ChatComponentScore)ichatcomponent).setValue(chatcomponentscore.getUnformattedTextForChat());
/* 31 */     } else if (component instanceof ChatComponentSelector) {
/* 32 */       String s1 = ((ChatComponentSelector)component).getSelector();
/* 33 */       ichatcomponent = PlayerSelector.matchEntitiesToChatComponent(commandSender, s1);
/*    */       
/* 35 */       if (ichatcomponent == null) {
/* 36 */         ichatcomponent = new ChatComponentText("");
/*    */       }
/* 38 */     } else if (component instanceof ChatComponentText) {
/* 39 */       ichatcomponent = new ChatComponentText(((ChatComponentText)component).getChatComponentText_TextValue());
/*    */     } else {
/* 41 */       if (!(component instanceof ChatComponentTranslation)) {
/* 42 */         return component;
/*    */       }
/*    */       
/* 45 */       Object[] aobject = ((ChatComponentTranslation)component).getFormatArgs();
/*    */       
/* 47 */       for (int i = 0; i < aobject.length; i++) {
/* 48 */         Object object = aobject[i];
/*    */         
/* 50 */         if (object instanceof IChatComponent) {
/* 51 */           aobject[i] = processComponent(commandSender, (IChatComponent)object, entityIn);
/*    */         }
/*    */       } 
/*    */       
/* 55 */       ichatcomponent = new ChatComponentTranslation(((ChatComponentTranslation)component).getKey(), aobject);
/*    */     } 
/*    */     
/* 58 */     ChatStyle chatstyle = component.getChatStyle();
/*    */     
/* 60 */     if (chatstyle != null) {
/* 61 */       ichatcomponent.setChatStyle(chatstyle.createShallowCopy());
/*    */     }
/*    */     
/* 64 */     for (IChatComponent ichatcomponent1 : component.getSiblings()) {
/* 65 */       ichatcomponent.appendSibling(processComponent(commandSender, ichatcomponent1, entityIn));
/*    */     }
/*    */     
/* 68 */     return ichatcomponent;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\ChatComponentProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */