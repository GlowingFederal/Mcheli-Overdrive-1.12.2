/*    */ package mcheli.gltd;
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
/*    */ 
/*    */ 
/*    */ public class MCH_GLTDPacketHandler
/*    */ {
/*    */   @HandleSide({Side.SERVER})
/*    */   public static void onPacket_GLTDPlayerControl(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
/* 24 */     if (!(player.func_184187_bx() instanceof MCH_EntityGLTD)) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 29 */     if (player.field_70170_p.field_72995_K) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 34 */     MCH_PacketGLTDPlayerControl pc = new MCH_PacketGLTDPlayerControl();
/* 35 */     pc.readData(data);
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
/*    */ 
/*    */ 
/*    */     
/* 61 */     scheduler.func_152344_a(() -> {
/*    */           MCH_EntityGLTD gltd = (MCH_EntityGLTD)player.func_184187_bx();
/*    */           if (pc.unmount) {
/*    */             Entity riddenByEntity = gltd.getRiddenByEntity();
/*    */             if (riddenByEntity != null)
/*    */               riddenByEntity.func_184210_p(); 
/*    */           } else {
/*    */             if (pc.switchCameraMode >= 0)
/*    */               gltd.camera.setMode(0, pc.switchCameraMode); 
/*    */             if (pc.switchWeapon >= 0)
/*    */               gltd.switchWeapon(pc.switchWeapon); 
/*    */             if (pc.useWeapon)
/*    */               gltd.useCurrentWeapon(pc.useWeaponOption1, pc.useWeaponOption2); 
/*    */           } 
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\gltd\MCH_GLTDPacketHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */