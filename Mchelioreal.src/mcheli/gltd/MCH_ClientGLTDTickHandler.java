/*     */ package mcheli.gltd;
/*     */ 
/*     */ import mcheli.MCH_ClientTickHandlerBase;
/*     */ import mcheli.MCH_Config;
/*     */ import mcheli.MCH_Key;
/*     */ import mcheli.MCH_Lib;
/*     */ import mcheli.MCH_ViewEntityDummy;
/*     */ import mcheli.wrapper.W_Network;
/*     */ import mcheli.wrapper.W_PacketBase;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_ClientGLTDTickHandler
/*     */   extends MCH_ClientTickHandlerBase
/*     */ {
/*     */   protected boolean isRiding = false;
/*     */   protected boolean isBeforeRiding = false;
/*     */   public MCH_Key KeyUseWeapon;
/*     */   public MCH_Key KeySwitchWeapon1;
/*     */   public MCH_Key KeySwitchWeapon2;
/*     */   public MCH_Key KeySwWeaponMode;
/*     */   public MCH_Key KeyZoom;
/*     */   public MCH_Key KeyCameraMode;
/*     */   public MCH_Key KeyUnmount;
/*     */   public MCH_Key KeyUnmount_1_6;
/*     */   public MCH_Key[] Keys;
/*     */   
/*     */   public MCH_ClientGLTDTickHandler(Minecraft minecraft, MCH_Config config) {
/*  35 */     super(minecraft);
/*  36 */     updateKeybind(config);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateKeybind(MCH_Config config) {
/*  42 */     this.KeyUseWeapon = new MCH_Key(MCH_Config.KeyUseWeapon.prmInt);
/*  43 */     this.KeySwitchWeapon1 = new MCH_Key(MCH_Config.KeySwitchWeapon1.prmInt);
/*  44 */     this.KeySwitchWeapon2 = new MCH_Key(MCH_Config.KeySwitchWeapon2.prmInt);
/*  45 */     this.KeySwWeaponMode = new MCH_Key(MCH_Config.KeySwWeaponMode.prmInt);
/*  46 */     this.KeyZoom = new MCH_Key(MCH_Config.KeyZoom.prmInt);
/*  47 */     this.KeyCameraMode = new MCH_Key(MCH_Config.KeyCameraMode.prmInt);
/*  48 */     this.KeyUnmount = new MCH_Key(MCH_Config.KeyUnmount.prmInt);
/*  49 */     this.KeyUnmount_1_6 = new MCH_Key(42);
/*     */     
/*  51 */     this.Keys = new MCH_Key[] { this.KeyUseWeapon, this.KeySwWeaponMode, this.KeySwitchWeapon1, this.KeySwitchWeapon2, this.KeyZoom, this.KeyCameraMode, this.KeyUnmount, this.KeyUnmount_1_6 };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateGLTD(EntityPlayer player, MCH_EntityGLTD gltd) {
/*  60 */     if (player.field_70125_A < -70.0F)
/*  61 */       player.field_70125_A = -70.0F; 
/*  62 */     if (player.field_70125_A > 70.0F)
/*     */     {
/*  64 */       player.field_70125_A = 70.0F;
/*     */     }
/*  66 */     float yaw = gltd.field_70177_z;
/*  67 */     if (player.field_70177_z < yaw - 70.0F)
/*  68 */       player.field_70177_z = yaw - 70.0F; 
/*  69 */     if (player.field_70177_z > yaw + 70.0F)
/*     */     {
/*  71 */       player.field_70177_z = yaw + 70.0F;
/*     */     }
/*  73 */     gltd.camera.rotationYaw = player.field_70177_z;
/*  74 */     gltd.camera.rotationPitch = player.field_70125_A;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onTick(boolean inGUI) {
/*  80 */     for (MCH_Key k : this.Keys)
/*     */     {
/*  82 */       k.update();
/*     */     }
/*     */     
/*  85 */     this.isBeforeRiding = this.isRiding;
/*     */     
/*  87 */     EntityPlayerSP entityPlayerSP = this.mc.field_71439_g;
/*  88 */     MCH_ViewEntityDummy viewEntityDummy = null;
/*     */     
/*  90 */     if (entityPlayerSP != null && entityPlayerSP.func_184187_bx() instanceof MCH_EntityGLTD) {
/*     */       
/*  92 */       MCH_EntityGLTD gltd = (MCH_EntityGLTD)entityPlayerSP.func_184187_bx();
/*     */       
/*  94 */       updateGLTD((EntityPlayer)entityPlayerSP, gltd);
/*     */ 
/*     */       
/*  97 */       MCH_Lib.disableFirstPersonItemRender(entityPlayerSP.func_184614_ca());
/*     */       
/*  99 */       viewEntityDummy = MCH_ViewEntityDummy.getInstance((World)this.mc.field_71441_e);
/* 100 */       viewEntityDummy.update(gltd.camera);
/*     */       
/* 102 */       if (!inGUI)
/*     */       {
/* 104 */         playerControl((EntityPlayer)entityPlayerSP, gltd);
/*     */       }
/*     */       
/* 107 */       MCH_Lib.setRenderViewEntity((EntityLivingBase)viewEntityDummy);
/*     */       
/* 109 */       this.isRiding = true;
/*     */     }
/*     */     else {
/*     */       
/* 113 */       this.isRiding = false;
/*     */     } 
/*     */     
/* 116 */     if (this.isBeforeRiding != this.isRiding)
/*     */     {
/* 118 */       if (this.isRiding) {
/*     */         
/* 120 */         if (viewEntityDummy != null)
/*     */         {
/* 122 */           viewEntityDummy.field_70169_q = viewEntityDummy.field_70165_t;
/* 123 */           viewEntityDummy.field_70167_r = viewEntityDummy.field_70163_u;
/* 124 */           viewEntityDummy.field_70166_s = viewEntityDummy.field_70161_v;
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 129 */         MCH_Lib.enableFirstPersonItemRender();
/* 130 */         MCH_Lib.setRenderViewEntity((EntityLivingBase)entityPlayerSP);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playerControl(EntityPlayer player, MCH_EntityGLTD gltd) {
/* 137 */     MCH_PacketGLTDPlayerControl pc = new MCH_PacketGLTDPlayerControl();
/* 138 */     boolean send = false;
/*     */     
/* 140 */     if (this.KeyUnmount.isKeyDown()) {
/*     */       
/* 142 */       pc.unmount = true;
/* 143 */       send = true;
/*     */     } 
/*     */     
/* 146 */     if (!this.KeySwitchWeapon1.isKeyDown() || !this.KeySwitchWeapon2.isKeyDown())
/*     */     {
/* 148 */       if (this.KeyUseWeapon.isKeyPress())
/*     */       {
/* 150 */         if (gltd.useCurrentWeapon(0, 0)) {
/*     */           
/* 152 */           pc.useWeapon = true;
/* 153 */           send = true;
/*     */         }
/* 155 */         else if (this.KeyUseWeapon.isKeyDown()) {
/*     */           
/* 157 */           playSoundNG();
/*     */         } 
/*     */       }
/*     */     }
/*     */     
/* 162 */     float prevZoom = gltd.camera.getCameraZoom();
/*     */     
/* 164 */     if (this.KeyZoom.isKeyPress() && !this.KeySwWeaponMode.isKeyPress())
/*     */     {
/* 166 */       gltd.zoomCamera(0.1F * gltd.camera.getCameraZoom());
/*     */     }
/*     */     
/* 169 */     if (!this.KeyZoom.isKeyPress() && this.KeySwWeaponMode.isKeyPress())
/*     */     {
/* 171 */       gltd.zoomCamera(-0.1F * gltd.camera.getCameraZoom());
/*     */     }
/*     */     
/* 174 */     if (prevZoom != gltd.camera.getCameraZoom())
/*     */     {
/* 176 */       playSound("zoom", 0.1F, (prevZoom < gltd.camera.getCameraZoom()) ? 1.0F : 0.85F);
/*     */     }
/*     */     
/* 179 */     if (this.KeyCameraMode.isKeyDown()) {
/*     */       
/* 181 */       int beforeMode = gltd.camera.getMode(0);
/* 182 */       gltd.camera.setMode(0, gltd.camera.getMode(0) + 1);
/* 183 */       int mode = gltd.camera.getMode(0);
/*     */       
/* 185 */       if (mode != beforeMode) {
/*     */         
/* 187 */         pc.switchCameraMode = (byte)mode;
/* 188 */         playSoundOK();
/* 189 */         send = true;
/*     */       } 
/*     */     } 
/*     */     
/* 193 */     if (send)
/*     */     {
/* 195 */       W_Network.sendToServer((W_PacketBase)pc);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\gltd\MCH_ClientGLTDTickHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */