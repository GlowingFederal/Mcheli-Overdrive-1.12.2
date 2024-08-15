/*     */ package mcheli.aircraft;
/*     */ 
/*     */ import mcheli.MCH_ClientTickHandlerBase;
/*     */ import mcheli.MCH_Config;
/*     */ import mcheli.MCH_Key;
/*     */ import mcheli.MCH_Lib;
/*     */ import mcheli.wrapper.W_Network;
/*     */ import mcheli.wrapper.W_PacketBase;
/*     */ import mcheli.wrapper.W_Reflection;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_ClientSeatTickHandler
/*     */   extends MCH_ClientTickHandlerBase
/*     */ {
/*     */   protected boolean isRiding = false;
/*     */   protected boolean isBeforeRiding = false;
/*     */   public MCH_Key KeySwitchNextSeat;
/*     */   public MCH_Key KeySwitchPrevSeat;
/*     */   public MCH_Key KeyParachuting;
/*     */   public MCH_Key KeyFreeLook;
/*     */   public MCH_Key KeyUnmountForce;
/*     */   public MCH_Key[] Keys;
/*     */   
/*     */   public MCH_ClientSeatTickHandler(Minecraft minecraft, MCH_Config config) {
/*  33 */     super(minecraft);
/*  34 */     updateKeybind(config);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateKeybind(MCH_Config config) {
/*  40 */     this.KeySwitchNextSeat = new MCH_Key(MCH_Config.KeyExtra.prmInt);
/*  41 */     this.KeySwitchPrevSeat = new MCH_Key(MCH_Config.KeyGUI.prmInt);
/*  42 */     this.KeyParachuting = new MCH_Key(MCH_Config.KeySwitchHovering.prmInt);
/*  43 */     this.KeyUnmountForce = new MCH_Key(42);
/*  44 */     this.KeyFreeLook = new MCH_Key(MCH_Config.KeyFreeLook.prmInt);
/*     */     
/*  46 */     this.Keys = new MCH_Key[] { this.KeySwitchNextSeat, this.KeySwitchPrevSeat, this.KeyParachuting, this.KeyUnmountForce, this.KeyFreeLook };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onTick(boolean inGUI) {
/*  56 */     for (MCH_Key k : this.Keys)
/*     */     {
/*  58 */       k.update();
/*     */     }
/*  60 */     this.isBeforeRiding = this.isRiding;
/*     */     
/*  62 */     EntityPlayerSP entityPlayerSP = this.mc.field_71439_g;
/*     */     
/*  64 */     MCH_EntityAircraft ac = null;
/*     */     
/*  66 */     if (entityPlayerSP != null && entityPlayerSP.func_184187_bx() instanceof MCH_EntitySeat) {
/*     */       
/*  68 */       MCH_EntitySeat seat = (MCH_EntitySeat)entityPlayerSP.func_184187_bx();
/*  69 */       if (seat.getParent() == null || seat.getParent().getAcInfo() == null)
/*     */         return; 
/*  71 */       ac = seat.getParent();
/*     */       
/*  73 */       if (!inGUI)
/*     */       {
/*  75 */         if (!ac.isDestroyed())
/*     */         {
/*  77 */           playerControl((EntityPlayer)entityPlayerSP, seat, ac);
/*     */         }
/*     */       }
/*     */       
/*  81 */       this.isRiding = true;
/*     */     }
/*     */     else {
/*     */       
/*  85 */       this.isRiding = false;
/*     */     } 
/*     */     
/*  88 */     if (this.isBeforeRiding != this.isRiding)
/*     */     {
/*  90 */       if (this.isRiding) {
/*     */         
/*  92 */         W_Reflection.setThirdPersonDistance(ac.thirdPersonDist);
/*     */       }
/*     */       else {
/*     */         
/*  96 */         if (entityPlayerSP == null || !(entityPlayerSP.func_184187_bx() instanceof MCH_EntityAircraft))
/*     */         {
/*  98 */           W_Reflection.restoreDefaultThirdPersonDistance();
/*     */         }
/* 100 */         MCH_Lib.setRenderViewEntity((EntityLivingBase)entityPlayerSP);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void playerControl(EntityPlayer player, MCH_EntitySeat seat, MCH_EntityAircraft ac) {
/* 111 */     MCH_PacketSeatPlayerControl pc = new MCH_PacketSeatPlayerControl();
/* 112 */     boolean send = false;
/*     */     
/* 114 */     if (this.KeyFreeLook.isKeyDown())
/*     */     {
/* 116 */       if (ac.canSwitchGunnerFreeLook(player))
/*     */       {
/*     */         
/* 119 */         ac.switchGunnerFreeLookMode();
/*     */       }
/*     */     }
/*     */     
/* 123 */     if (this.KeyParachuting.isKeyDown())
/*     */     {
/* 125 */       if (ac.canParachuting((Entity)player)) {
/*     */ 
/*     */         
/* 128 */         pc.parachuting = true;
/* 129 */         send = true;
/*     */       }
/* 131 */       else if (ac.canRepelling((Entity)player)) {
/*     */ 
/*     */         
/* 134 */         pc.parachuting = true;
/* 135 */         send = true;
/*     */       }
/*     */       else {
/*     */         
/* 139 */         playSoundNG();
/*     */       } 
/*     */     }
/*     */     
/* 143 */     if (send)
/*     */     {
/* 145 */       W_Network.sendToServer((W_PacketBase)pc);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\aircraft\MCH_ClientSeatTickHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */