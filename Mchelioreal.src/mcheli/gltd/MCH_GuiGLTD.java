/*     */ package mcheli.gltd;
/*     */ 
/*     */ import mcheli.MCH_Camera;
/*     */ import mcheli.MCH_Config;
/*     */ import mcheli.MCH_KeyName;
/*     */ import mcheli.gui.MCH_Gui;
/*     */ import mcheli.wrapper.W_McClient;
/*     */ import mcheli.wrapper.W_WorldFunc;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
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
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class MCH_GuiGLTD
/*     */   extends MCH_Gui
/*     */ {
/*     */   public MCH_GuiGLTD(Minecraft minecraft) {
/*  33 */     super(minecraft);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73866_w_() {
/*  39 */     super.func_73866_w_();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_73868_f() {
/*  45 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDrawGui(EntityPlayer player) {
/*  51 */     return (player.func_184187_bx() != null && player.func_184187_bx() instanceof MCH_EntityGLTD);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawGui(EntityPlayer player, boolean isThirdPersonView) {
/*  57 */     if (isThirdPersonView && !MCH_Config.DisplayHUDThirdPerson.prmBool) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  62 */     GL11.glLineWidth(scaleFactor);
/*     */     
/*  64 */     if (!isDrawGui(player)) {
/*     */       return;
/*     */     }
/*  67 */     MCH_EntityGLTD gltd = (MCH_EntityGLTD)player.func_184187_bx();
/*     */     
/*  69 */     if (gltd.camera.getMode(0) == 1) {
/*     */       
/*  71 */       GL11.glEnable(3042);
/*  72 */       GL11.glColor4f(0.0F, 1.0F, 0.0F, 0.3F);
/*  73 */       int srcBlend = GL11.glGetInteger(3041);
/*  74 */       int dstBlend = GL11.glGetInteger(3040);
/*     */       
/*  76 */       GL11.glBlendFunc(1, 1);
/*     */       
/*  78 */       W_McClient.MOD_bindTexture("textures/gui/alpha.png");
/*  79 */       drawTexturedModalRectRotate(0.0D, 0.0D, this.field_146294_l, this.field_146295_m, this.rand.nextInt(256), this.rand
/*  80 */           .nextInt(256), 256.0D, 256.0D, 0.0F);
/*     */       
/*  82 */       GL11.glBlendFunc(srcBlend, dstBlend);
/*  83 */       GL11.glDisable(3042);
/*     */     } 
/*     */     
/*  86 */     drawString(String.format("x%.1f", new Object[] {
/*     */             
/*  88 */             Float.valueOf(gltd.camera.getCameraZoom())
/*     */           }), this.centerX - 70, this.centerY + 10, -805306369);
/*     */     
/*  91 */     drawString(gltd.weaponCAS.getName(), this.centerX - 200, this.centerY + 65, (gltd.countWait == 0) ? -819986657 : -807468024);
/*     */ 
/*     */     
/*  94 */     drawCommonPosition(gltd, -819986657);
/*  95 */     drawString(gltd.camera.getModeName(0), this.centerX + 30, this.centerY - 50, -819986657);
/*  96 */     drawSight(gltd.camera, -819986657);
/*  97 */     drawTargetPosition(gltd, -819986657, -807468024);
/*  98 */     drawKeyBind(gltd.camera, -805306369, -813727873);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawKeyBind(MCH_Camera camera, int color, int colorCannotUse) {
/* 103 */     int OffX = this.centerX + 55;
/* 104 */     int OffY = this.centerY + 40;
/* 105 */     drawString("DISMOUNT :", OffX, OffY + 0, color);
/* 106 */     drawString("CAM MODE :", OffX, OffY + 10, color);
/* 107 */     drawString("ZOOM IN   :", OffX, OffY + 20, (camera.getCameraZoom() < 10.0F) ? color : colorCannotUse);
/* 108 */     drawString("ZOOM OUT :", OffX, OffY + 30, (camera.getCameraZoom() > 1.0F) ? color : colorCannotUse);
/*     */     
/* 110 */     OffX += 60;
/* 111 */     drawString(MCH_KeyName.getDescOrName(42) + " or " + MCH_KeyName.getDescOrName(MCH_Config.KeyUnmount.prmInt), OffX, OffY + 0, color);
/*     */ 
/*     */     
/* 114 */     drawString(MCH_KeyName.getDescOrName(MCH_Config.KeyCameraMode.prmInt), OffX, OffY + 10, color);
/* 115 */     drawString(MCH_KeyName.getDescOrName(MCH_Config.KeyZoom.prmInt), OffX, OffY + 20, 
/* 116 */         (camera.getCameraZoom() < 10.0F) ? color : colorCannotUse);
/*     */     
/* 118 */     drawString(MCH_KeyName.getDescOrName(MCH_Config.KeySwWeaponMode.prmInt), OffX, OffY + 30, 
/* 119 */         (camera.getCameraZoom() > 1.0F) ? color : colorCannotUse);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawCommonPosition(MCH_EntityGLTD gltd, int color) {
/* 125 */     Entity riddenByEntity = gltd.getRiddenByEntity();
/*     */     
/* 127 */     drawString(String.format("X: %+.1f", new Object[] {
/*     */             
/* 129 */             Double.valueOf(gltd.field_70165_t)
/*     */           }), this.centerX - 145, this.centerY + 0, color);
/* 131 */     drawString(String.format("Y: %+.1f", new Object[] {
/*     */             
/* 133 */             Double.valueOf(gltd.field_70163_u)
/*     */           }), this.centerX - 145, this.centerY + 10, color);
/* 135 */     drawString(String.format("Z: %+.1f", new Object[] {
/*     */             
/* 137 */             Double.valueOf(gltd.field_70161_v)
/*     */           }), this.centerX - 145, this.centerY + 20, color);
/* 139 */     drawString(String.format("AX: %+.1f", new Object[] {
/*     */ 
/*     */             
/* 142 */             Float.valueOf(riddenByEntity.field_70177_z)
/*     */           }), this.centerX - 145, this.centerY + 40, color);
/* 144 */     drawString(String.format("AY: %+.1f", new Object[] {
/*     */ 
/*     */             
/* 147 */             Float.valueOf(riddenByEntity.field_70125_A)
/*     */           }), this.centerX - 145, this.centerY + 50, color);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawTargetPosition(MCH_EntityGLTD gltd, int color, int colorDanger) {
/* 153 */     Entity riddenByEntity = gltd.getRiddenByEntity();
/*     */ 
/*     */     
/* 156 */     if (riddenByEntity == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 162 */     World w = riddenByEntity.field_70170_p;
/* 163 */     float yaw = riddenByEntity.field_70177_z;
/* 164 */     float pitch = riddenByEntity.field_70125_A;
/* 165 */     double tX = (-MathHelper.func_76126_a(yaw / 180.0F * 3.1415927F) * MathHelper.func_76134_b(pitch / 180.0F * 3.1415927F));
/* 166 */     double tZ = (MathHelper.func_76134_b(yaw / 180.0F * 3.1415927F) * MathHelper.func_76134_b(pitch / 180.0F * 3.1415927F));
/* 167 */     double tY = -MathHelper.func_76126_a(pitch / 180.0F * 3.1415927F);
/* 168 */     double dist = MathHelper.func_76133_a(tX * tX + tY * tY + tZ * tZ);
/*     */     
/* 170 */     tX = tX * 80.0D / dist;
/* 171 */     tY = tY * 80.0D / dist;
/* 172 */     tZ = tZ * 80.0D / dist;
/*     */     
/* 174 */     MCH_Camera c = gltd.camera;
/*     */     
/* 176 */     Vec3d src = W_WorldFunc.getWorldVec3(w, c.posX, c.posY, c.posZ);
/* 177 */     Vec3d dst = W_WorldFunc.getWorldVec3(w, c.posX + tX, c.posY + tY, c.posZ + tZ);
/*     */     
/* 179 */     RayTraceResult m = W_WorldFunc.clip(w, src, dst);
/*     */ 
/*     */     
/* 182 */     if (m != null) {
/*     */       
/* 184 */       drawString(String.format("X: %+.2fm", new Object[] {
/*     */               
/* 186 */               Double.valueOf(m.field_72307_f.field_72450_a)
/*     */             }), this.centerX + 50, this.centerY - 5 - 15, color);
/* 188 */       drawString(String.format("Y: %+.2fm", new Object[] {
/*     */               
/* 190 */               Double.valueOf(m.field_72307_f.field_72448_b)
/*     */             }), this.centerX + 50, this.centerY - 5, color);
/* 192 */       drawString(String.format("Z: %+.2fm", new Object[] {
/*     */               
/* 194 */               Double.valueOf(m.field_72307_f.field_72449_c)
/*     */             }), this.centerX + 50, this.centerY - 5 + 15, color);
/*     */       
/* 197 */       double x = m.field_72307_f.field_72450_a - c.posX;
/* 198 */       double y = m.field_72307_f.field_72448_b - c.posY;
/* 199 */       double z = m.field_72307_f.field_72449_c - c.posZ;
/* 200 */       double len = Math.sqrt(x * x + y * y + z * z);
/*     */       
/* 202 */       drawCenteredString(String.format("[%.2fm]", new Object[] {
/*     */               
/* 204 */               Double.valueOf(len)
/*     */             }), this.centerX, this.centerY + 30, (len > 20.0D) ? color : colorDanger);
/*     */     }
/*     */     else {
/*     */       
/* 209 */       drawString("X: --.--m", this.centerX + 50, this.centerY - 5 - 15, color);
/* 210 */       drawString("Y: --.--m", this.centerX + 50, this.centerY - 5, color);
/* 211 */       drawString("Z: --.--m", this.centerX + 50, this.centerY - 5 + 15, color);
/* 212 */       drawCenteredString("[--.--m]", this.centerX, this.centerY + 30, colorDanger);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawSight(MCH_Camera camera, int color) {
/* 218 */     double posX = this.centerX;
/* 219 */     double posY = this.centerY;
/*     */ 
/*     */ 
/*     */     
/* 223 */     double[] line2 = { posX - 30.0D, posY - 10.0D, posX - 30.0D, posY - 20.0D, posX - 30.0D, posY - 20.0D, posX - 10.0D, posY - 20.0D, posX - 30.0D, posY + 10.0D, posX - 30.0D, posY + 20.0D, posX - 30.0D, posY + 20.0D, posX - 10.0D, posY + 20.0D, posX + 30.0D, posY - 10.0D, posX + 30.0D, posY - 20.0D, posX + 30.0D, posY - 20.0D, posX + 10.0D, posY - 20.0D, posX + 30.0D, posY + 10.0D, posX + 30.0D, posY + 20.0D, posX + 30.0D, posY + 20.0D, posX + 10.0D, posY + 20.0D };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 232 */     drawLine(line2, color);
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\gltd\MCH_GuiGLTD.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */