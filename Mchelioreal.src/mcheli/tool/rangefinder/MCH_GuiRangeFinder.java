/*     */ package mcheli.tool.rangefinder;
/*     */ 
/*     */ import mcheli.MCH_Config;
/*     */ import mcheli.MCH_KeyName;
/*     */ import mcheli.MCH_Lib;
/*     */ import mcheli.gui.MCH_Gui;
/*     */ import mcheli.wrapper.W_McClient;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
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
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class MCH_GuiRangeFinder
/*     */   extends MCH_Gui
/*     */ {
/*     */   public MCH_GuiRangeFinder(Minecraft minecraft) {
/*  29 */     super(minecraft);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73866_w_() {
/*  35 */     super.func_73866_w_();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_73868_f() {
/*  41 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDrawGui(EntityPlayer player) {
/*  47 */     return MCH_ItemRangeFinder.canUse(player);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawGui(EntityPlayer player, boolean isThirdPersonView) {
/*  53 */     if (isThirdPersonView) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  58 */     GL11.glLineWidth(scaleFactor);
/*     */     
/*  60 */     if (!isDrawGui(player)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  65 */     GL11.glDisable(3042);
/*     */     
/*  67 */     if (MCH_ItemRangeFinder.isUsingScope(player))
/*     */     {
/*  69 */       drawRF(player);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   void drawRF(EntityPlayer player) {
/*  75 */     GL11.glEnable(3042);
/*  76 */     GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
/*  77 */     int srcBlend = GL11.glGetInteger(3041);
/*  78 */     int dstBlend = GL11.glGetInteger(3040);
/*  79 */     GL11.glBlendFunc(770, 771);
/*     */     
/*  81 */     W_McClient.MOD_bindTexture("textures/gui/rangefinder.png");
/*  82 */     double size = 512.0D;
/*  83 */     while (size < this.field_146294_l || size < this.field_146295_m)
/*  84 */       size *= 2.0D; 
/*  85 */     drawTexturedModalRectRotate(-(size - this.field_146294_l) / 2.0D, -(size - this.field_146295_m) / 2.0D, size, size, 0.0D, 0.0D, 256.0D, 256.0D, 0.0F);
/*     */ 
/*     */     
/*  88 */     GL11.glBlendFunc(srcBlend, dstBlend);
/*  89 */     GL11.glDisable(3042);
/*     */     
/*  91 */     double factor = size / 512.0D;
/*  92 */     double SCALE_FACTOR = scaleFactor * factor;
/*  93 */     double CX = (this.field_146297_k.field_71443_c / 2);
/*  94 */     double CY = (this.field_146297_k.field_71440_d / 2);
/*     */     
/*  96 */     double px = (CX - 80.0D * SCALE_FACTOR) / SCALE_FACTOR;
/*  97 */     double py = (CY + 55.0D * SCALE_FACTOR) / SCALE_FACTOR;
/*     */     
/*  99 */     GL11.glPushMatrix();
/* 100 */     GL11.glScaled(factor, factor, factor);
/*     */ 
/*     */     
/* 103 */     ItemStack item = player.func_184614_ca();
/*     */     
/* 105 */     int damage = (int)((item.func_77958_k() - item.func_77960_j()) / item.func_77958_k() * 100.0D);
/*     */     
/* 107 */     drawDigit(String.format("%3d", new Object[] {
/*     */             
/* 109 */             Integer.valueOf(damage)
/*     */           }), (int)px, (int)py, 13, (damage > 0) ? -15663328 : -61424);
/*     */     
/* 112 */     if (damage <= 0) {
/*     */       
/* 114 */       drawString("Please craft", (int)px + 40, (int)py + 0, -65536);
/* 115 */       drawString("redstone", (int)px + 40, (int)py + 10, -65536);
/*     */     } 
/*     */     
/* 118 */     px = (CX - 20.0D * SCALE_FACTOR) / SCALE_FACTOR;
/* 119 */     if (damage > 0) {
/*     */       
/* 121 */       Vec3d vs = new Vec3d(player.field_70165_t, player.field_70163_u + player.func_70047_e(), player.field_70161_v);
/* 122 */       Vec3d ve = MCH_Lib.Rot2Vec3(player.field_70177_z, player.field_70125_A);
/* 123 */       ve = vs.func_72441_c(ve.field_72450_a * 300.0D, ve.field_72448_b * 300.0D, ve.field_72449_c * 300.0D);
/*     */       
/* 125 */       RayTraceResult mop = player.field_70170_p.func_72901_a(vs, ve, true);
/*     */ 
/*     */       
/* 128 */       if (mop != null && mop.field_72313_a != RayTraceResult.Type.MISS) {
/*     */         
/* 130 */         int range = (int)player.func_70011_f(mop.field_72307_f.field_72450_a, mop.field_72307_f.field_72448_b, mop.field_72307_f.field_72449_c);
/* 131 */         drawDigit(String.format("%4d", new Object[] {
/*     */                 
/* 133 */                 Integer.valueOf(range)
/*     */               }), (int)px, (int)py, 13, -15663328);
/*     */       }
/*     */       else {
/*     */         
/* 138 */         drawDigit(String.format("----", new Object[0]), (int)px, (int)py, 13, -61424);
/*     */       } 
/*     */     } 
/*     */     
/* 142 */     py -= 4.0D;
/* 143 */     px -= 80.0D;
/* 144 */     func_73734_a((int)px, (int)py, (int)px + 30, (int)py + 2, -15663328);
/* 145 */     func_73734_a((int)px, (int)py, (int)px + MCH_ItemRangeFinder.rangeFinderUseCooldown / 2, (int)py + 2, -61424);
/* 146 */     drawString(String.format("x%.1f", new Object[] {
/*     */             
/* 148 */             Float.valueOf(MCH_ItemRangeFinder.zoom)
/*     */           }), (int)px, (int)py - 20, -1);
/*     */     
/* 151 */     px += 130.0D;
/* 152 */     int mode = MCH_ItemRangeFinder.mode;
/* 153 */     drawString(">", (int)px, (int)py - 30 + mode * 10, -1);
/* 154 */     px += 10.0D;
/* 155 */     drawString("Players/Vehicles", (int)px, (int)py - 30, (mode == 0) ? -1 : -12566464);
/* 156 */     drawString("Monsters/Mobs", (int)px, (int)py - 20, (mode == 1) ? -1 : -12566464);
/* 157 */     drawString("Mark Point", (int)px, (int)py - 10, (mode == 2) ? -1 : -12566464);
/*     */     
/* 159 */     GL11.glPopMatrix();
/*     */     
/* 161 */     px = (CX - 160.0D * SCALE_FACTOR) / scaleFactor;
/* 162 */     py = (CY - 100.0D * SCALE_FACTOR) / scaleFactor;
/* 163 */     if (px < 10.0D) {
/* 164 */       px = 10.0D;
/*     */     }
/* 166 */     if (py < 10.0D)
/*     */     {
/* 168 */       py = 10.0D;
/*     */     }
/* 170 */     String s = "Spot      : " + MCH_KeyName.getDescOrName(MCH_Config.KeyAttack.prmInt);
/* 171 */     drawString(s, (int)px, (int)py + 0, -1);
/* 172 */     s = "Zoom in   : " + MCH_KeyName.getDescOrName(MCH_Config.KeyZoom.prmInt);
/* 173 */     drawString(s, (int)px, (int)py + 10, (MCH_ItemRangeFinder.zoom < 10.0F) ? -1 : -12566464);
/* 174 */     s = "Zoom out : " + MCH_KeyName.getDescOrName(MCH_Config.KeySwWeaponMode.prmInt);
/* 175 */     drawString(s, (int)px, (int)py + 20, (MCH_ItemRangeFinder.zoom > 1.2F) ? -1 : -12566464);
/* 176 */     s = "Mode      : " + MCH_KeyName.getDescOrName(MCH_Config.KeyFlare.prmInt);
/* 177 */     drawString(s, (int)px, (int)py + 30, -1);
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\tool\rangefinder\MCH_GuiRangeFinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */