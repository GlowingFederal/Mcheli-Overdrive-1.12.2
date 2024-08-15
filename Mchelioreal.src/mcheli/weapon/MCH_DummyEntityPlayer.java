/*    */ package mcheli.weapon;
/*    */ 
/*    */ import mcheli.wrapper.ChatMessageComponent;
/*    */ import mcheli.wrapper.W_EntityPlayer;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_DummyEntityPlayer
/*    */   extends W_EntityPlayer
/*    */ {
/*    */   public MCH_DummyEntityPlayer(World worldIn, EntityPlayer player) {
/* 20 */     super(worldIn, player);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_145747_a(ITextComponent var1) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_70003_b(int var1, String var2) {
/* 32 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Entity func_174793_f() {
/* 42 */     return super.func_174793_f();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_175149_v() {
/* 48 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_184812_l_() {
/* 54 */     return false;
/*    */   }
/*    */   
/*    */   public void sendChatToPlayer(ChatMessageComponent chatmessagecomponent) {}
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\weapon\MCH_DummyEntityPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */