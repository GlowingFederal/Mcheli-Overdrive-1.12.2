/*    */ package mcheli;
/*    */ 
/*    */ import mcheli.aircraft.MCH_AircraftCommonGui;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class MCH_GuiCommon
/*    */   extends MCH_AircraftCommonGui
/*    */ {
/* 20 */   public int hitCount = 0;
/*    */ 
/*    */   
/*    */   public MCH_GuiCommon(Minecraft minecraft) {
/* 24 */     super(minecraft);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isDrawGui(EntityPlayer player) {
/* 30 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawGui(EntityPlayer player, boolean isThirdPersonView) {
/* 36 */     GL11.glLineWidth(scaleFactor);
/*    */     
/* 38 */     drawHitBullet(this.hitCount, 15, -805306369);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onTick() {
/* 44 */     super.onTick();
/* 45 */     if (this.hitCount > 0) {
/* 46 */       this.hitCount--;
/*    */     }
/*    */   }
/*    */   
/*    */   public void hitBullet() {
/* 51 */     this.hitCount = 15;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\MCH_GuiCommon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */