/*    */ package mcheli.command;
/*    */ 
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import mcheli.MCH_MOD;
/*    */ import mcheli.__helper.network.HandleSide;
/*    */ import mcheli.aircraft.MCH_EntityAircraft;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.IThreadListener;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_CommandPacketHandler
/*    */ {
/*    */   @HandleSide({Side.CLIENT})
/*    */   public static void onPacketTitle(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
/* 24 */     if (player == null || !player.field_70170_p.field_72995_K) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 29 */     MCH_PacketTitle req = new MCH_PacketTitle();
/* 30 */     req.readData(data);
/*    */ 
/*    */     
/* 33 */     scheduler.func_152344_a(() -> MCH_MOD.proxy.printChatMessage(req.chatComponent, req.showTime, req.position));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @HandleSide({Side.SERVER})
/*    */   public static void onPacketSave(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
/* 43 */     if (player == null || player.field_70170_p.field_72995_K) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 48 */     MCH_PacketCommandSave req = new MCH_PacketCommandSave();
/* 49 */     req.readData(data);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 57 */     scheduler.func_152344_a(() -> {
/*    */           MCH_EntityAircraft ac = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)player);
/*    */           if (ac != null)
/*    */             ac.setCommand(req.str, player); 
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\command\MCH_CommandPacketHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */