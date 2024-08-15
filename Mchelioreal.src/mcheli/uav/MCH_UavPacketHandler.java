/*    */ package mcheli.uav;
/*    */ 
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import mcheli.__helper.network.HandleSide;
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
/*    */ public class MCH_UavPacketHandler
/*    */ {
/*    */   @HandleSide({Side.SERVER})
/*    */   public static void onPacketUavStatus(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
/* 22 */     if (!player.field_70170_p.field_72995_K) {
/*    */       
/* 24 */       MCH_UavPacketStatus status = new MCH_UavPacketStatus();
/*    */       
/* 26 */       status.readData(data);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 38 */       scheduler.func_152344_a(() -> {
/*    */             if (player.func_184187_bx() instanceof MCH_EntityUavStation) {
/*    */               ((MCH_EntityUavStation)player.func_184187_bx()).setUavPosition(status.posUavX, status.posUavY, status.posUavZ);
/*    */               if (status.continueControl)
/*    */                 ((MCH_EntityUavStation)player.func_184187_bx()).controlLastAircraft((Entity)player); 
/*    */             } 
/*    */           });
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mchel\\uav\MCH_UavPacketHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */