/*    */ package mcheli.aircraft;
/*    */ 
/*    */ import mcheli.__helper.MCH_Utils;
/*    */ import net.minecraft.command.ICommandManager;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_DummyCommandSender
/*    */   implements ICommandSender
/*    */ {
/* 20 */   public static MCH_DummyCommandSender instance = new MCH_DummyCommandSender();
/*    */ 
/*    */ 
/*    */   
/*    */   public static void execCommand(String s) {
/* 25 */     ICommandManager icommandmanager = MCH_Utils.getServer().func_71187_D();
/* 26 */     icommandmanager.func_71556_a(instance, s);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String func_70005_c_() {
/* 32 */     return "";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ITextComponent func_145748_c_() {
/* 39 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_145747_a(ITextComponent component) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_70003_b(int permLevel, String commandName) {
/* 51 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public World func_130014_f_() {
/* 62 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public MinecraftServer func_184102_h() {
/* 68 */     return MCH_Utils.getServer();
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\aircraft\MCH_DummyCommandSender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */