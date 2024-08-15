/*     */ package mcheli.tool;
/*     */ 
/*     */ import mcheli.aircraft.MCH_EntityAircraft;
/*     */ import mcheli.gui.MCH_Gui;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class MCH_GuiWrench
/*     */   extends MCH_Gui
/*     */ {
/*     */   public MCH_GuiWrench(Minecraft minecraft) {
/*  23 */     super(minecraft);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73866_w_() {
/*  29 */     super.func_73866_w_();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_73868_f() {
/*  35 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDrawGui(EntityPlayer player) {
/*  43 */     return (player != null && player.field_70170_p != null && !player.func_184614_ca().func_190926_b() && player
/*  44 */       .func_184614_ca().func_77973_b() instanceof MCH_ItemWrench);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawGui(EntityPlayer player, boolean isThirdPersonView) {
/*  50 */     if (isThirdPersonView) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  55 */     GL11.glLineWidth(scaleFactor);
/*     */     
/*  57 */     if (!isDrawGui(player)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  62 */     GL11.glDisable(3042);
/*     */ 
/*     */     
/*  65 */     MCH_EntityAircraft ac = ((MCH_ItemWrench)player.func_184614_ca().func_77973_b()).getMouseOverAircraft((EntityLivingBase)player);
/*     */     
/*  67 */     if (ac != null && ac.getMaxHP() > 0) {
/*     */ 
/*     */       
/*  70 */       int color = ((ac.getHP() / ac.getMaxHP()) > 0.3D) ? -14101432 : -2161656;
/*  71 */       drawHP(color, -15433180, ac.getHP(), ac.getMaxHP());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void drawHP(int color, int colorBG, int hp, int hpmax) {
/*  77 */     int posX = this.centerX;
/*  78 */     int posY = this.centerY + 20;
/*     */ 
/*     */ 
/*     */     
/*  82 */     func_73734_a(posX - 20, posY + 20 + 1, posX - 20 + 40, posY + 20 + 1 + 1 + 3 + 1, colorBG);
/*     */     
/*  84 */     if (hp > hpmax)
/*     */     {
/*  86 */       hp = hpmax;
/*     */     }
/*     */ 
/*     */     
/*  90 */     float hpp = hp / hpmax;
/*  91 */     func_73734_a(posX - 20 + 1, posY + 20 + 1 + 1, posX - 20 + 1 + (int)(38.0D * hpp), posY + 20 + 1 + 1 + 3, color);
/*     */     
/*  93 */     int hppn = (int)(hpp * 100.0F);
/*  94 */     if (hp < hpmax && hppn >= 100)
/*     */     {
/*  96 */       hppn = 99;
/*     */     }
/*  98 */     drawCenteredString(String.format("%d %%", new Object[] {
/*     */             
/* 100 */             Integer.valueOf(hppn)
/*     */           }), posX, posY + 30, color);
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\tool\MCH_GuiWrench.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */