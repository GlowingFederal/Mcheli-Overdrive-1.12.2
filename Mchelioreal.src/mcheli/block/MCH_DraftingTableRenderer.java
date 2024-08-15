/*    */ package mcheli.block;
/*    */ 
/*    */ import mcheli.MCH_Config;
/*    */ import mcheli.MCH_ModelManager;
/*    */ import mcheli.wrapper.W_McClient;
/*    */ import net.minecraft.client.renderer.RenderHelper;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntity;
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
/*    */ 
/*    */ public class MCH_DraftingTableRenderer
/*    */   extends TileEntitySpecialRenderer<MCH_DraftingTableTileEntity>
/*    */ {
/*    */   @SideOnly(Side.CLIENT)
/*    */   private static DraftingTableStackRenderer stackRenderer;
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public static DraftingTableStackRenderer getStackRenderer() {
/* 31 */     if (stackRenderer == null)
/*    */     {
/* 33 */       stackRenderer = new DraftingTableStackRenderer();
/*    */     }
/*    */     
/* 36 */     return stackRenderer;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(MCH_DraftingTableTileEntity tile, double posX, double posY, double posZ, float partialTicks, int destroyStage, float alpha) {
/* 44 */     GL11.glPushMatrix();
/* 45 */     GL11.glEnable(2884);
/* 46 */     GL11.glTranslated(posX + 0.5D, posY, posZ + 0.5D);
/*    */ 
/*    */     
/* 49 */     float yaw = getYawAngle(tile);
/* 50 */     GL11.glRotatef(yaw, 0.0F, 1.0F, 0.0F);
/* 51 */     RenderHelper.func_74519_b();
/*    */     
/* 53 */     GL11.glColor4f(0.75F, 0.75F, 0.75F, 1.0F);
/*    */     
/* 55 */     GL11.glEnable(3042);
/* 56 */     int srcBlend = GL11.glGetInteger(3041);
/* 57 */     int dstBlend = GL11.glGetInteger(3040);
/* 58 */     GL11.glBlendFunc(770, 771);
/*    */     
/* 60 */     if (MCH_Config.SmoothShading.prmBool)
/*    */     {
/* 62 */       GL11.glShadeModel(7425);
/*    */     }
/*    */     
/* 65 */     W_McClient.MOD_bindTexture("textures/blocks/drafting_table.png");
/* 66 */     MCH_ModelManager.render("blocks", "drafting_table");
/*    */     
/* 68 */     GL11.glBlendFunc(srcBlend, dstBlend);
/* 69 */     GL11.glDisable(3042);
/* 70 */     GL11.glShadeModel(7424);
/*    */     
/* 72 */     GL11.glPopMatrix();
/*    */   }
/*    */ 
/*    */   
/*    */   private float getYawAngle(MCH_DraftingTableTileEntity tile) {
/* 77 */     if (tile.func_145830_o())
/*    */     {
/* 79 */       return -tile.func_145832_p() * 45.0F + 180.0F;
/*    */     }
/*    */ 
/*    */     
/* 83 */     return 0.0F;
/*    */   }
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   private static class DraftingTableStackRenderer
/*    */     extends TileEntityItemStackRenderer
/*    */   {
/* 90 */     private MCH_DraftingTableTileEntity draftingTable = new MCH_DraftingTableTileEntity();
/*    */ 
/*    */ 
/*    */     
/*    */     public void func_192838_a(ItemStack p_192838_1_, float partialTicks) {
/* 95 */       TileEntityRendererDispatcher.field_147556_a.func_192855_a(this.draftingTable, 0.0D, 0.0D, 0.0D, partialTicks, 0.0F);
/*    */     }
/*    */     
/*    */     private DraftingTableStackRenderer() {}
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\block\MCH_DraftingTableRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */