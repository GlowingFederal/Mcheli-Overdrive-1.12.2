/*    */ package mcheli.block;
/*    */ 
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import mcheli.MCH_Lib;
/*    */ import mcheli.__helper.network.HandleSide;
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
/*    */ 
/*    */ public class MCH_DraftingTablePacketHandler
/*    */ {
/*    */   @HandleSide({Side.SERVER})
/*    */   public static void onPacketCreate(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
/* 23 */     if (!player.field_70170_p.field_72995_K) {
/*    */       
/* 25 */       MCH_DraftingTableCreatePacket packet = new MCH_DraftingTableCreatePacket();
/* 26 */       packet.readData(data);
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
/* 37 */       scheduler.func_152344_a(() -> {
/*    */             boolean openScreen = player.field_71070_bA instanceof MCH_DraftingTableGuiContainer;
/*    */             MCH_Lib.DbgLog(false, "MCH_DraftingTablePacketHandler.onPacketCreate : " + openScreen, new Object[0]);
/*    */             if (openScreen)
/*    */               ((MCH_DraftingTableGuiContainer)player.field_71070_bA).createRecipeItem(packet.recipe); 
/*    */           });
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\block\MCH_DraftingTablePacketHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */