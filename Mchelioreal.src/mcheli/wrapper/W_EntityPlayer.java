/*    */ package mcheli.wrapper;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityItem;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import net.minecraft.util.text.TextComponentString;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class W_EntityPlayer
/*    */   extends EntityPlayer
/*    */ {
/*    */   public W_EntityPlayer(World par1World, EntityPlayer player) {
/* 23 */     super(par1World, player.func_146103_bH());
/*    */   }
/*    */ 
/*    */   
/*    */   public static void closeScreen(Entity p) {
/* 28 */     if (p != null)
/*    */     {
/* 30 */       if (p.field_70170_p.field_72995_K) {
/*    */         
/* 32 */         W_EntityPlayerSP.closeScreen(p);
/*    */       
/*    */       }
/* 35 */       else if (p instanceof EntityPlayerMP) {
/*    */         
/* 37 */         ((EntityPlayerMP)p).func_71053_j();
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean hasItem(EntityPlayer player, Item item) {
/* 45 */     return (item != null && player.field_71071_by.func_70431_c(new ItemStack(item)));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean consumeInventoryItem(EntityPlayer player, Item item) {
/* 51 */     int index = player.field_71071_by.func_194014_c(new ItemStack(item));
/* 52 */     return (item != null && player.field_71071_by.func_70298_a(index, 1).func_190926_b());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static void addChatMessage(EntityPlayer player, String s) {
/* 58 */     player.func_145747_a((ITextComponent)new TextComponentString(s));
/*    */   }
/*    */ 
/*    */   
/*    */   public static EntityItem dropPlayerItemWithRandomChoice(EntityPlayer player, ItemStack item, boolean b1, boolean b2) {
/* 63 */     return player.func_146097_a(item, b1, b2);
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean isPlayer(Entity entity) {
/* 68 */     if (entity instanceof EntityPlayer)
/*    */     {
/* 70 */       return true;
/*    */     }
/* 72 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\wrapper\W_EntityPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */