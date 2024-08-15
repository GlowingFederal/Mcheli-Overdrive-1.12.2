/*     */ package mcheli;
/*     */ 
/*     */ import mcheli.aircraft.MCH_EntityAircraft;
/*     */ import mcheli.wrapper.W_McClient;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public abstract class MCH_ClientTickHandlerBase
/*     */ {
/*     */   protected Minecraft mc;
/*  21 */   public static float playerRotMinPitch = -90.0F;
/*  22 */   public static float playerRotMaxPitch = 90.0F;
/*     */   public static boolean playerRotLimitPitch = false;
/*  24 */   public static float playerRotMinYaw = -180.0F;
/*  25 */   public static float playerRotMaxYaw = 180.0F;
/*     */   
/*     */   public static boolean playerRotLimitYaw = false;
/*  28 */   private static int mouseWheel = 0;
/*     */ 
/*     */   
/*     */   public abstract void updateKeybind(MCH_Config paramMCH_Config);
/*     */   
/*     */   public static void setRotLimitPitch(float min, float max, Entity player) {
/*  34 */     playerRotMinPitch = min;
/*  35 */     playerRotMaxPitch = max;
/*  36 */     playerRotLimitPitch = true;
/*     */     
/*  38 */     if (player != null)
/*     */     {
/*  40 */       player.field_70125_A = MCH_Lib.RNG(player.field_70125_A, playerRotMinPitch, playerRotMaxPitch);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setRotLimitYaw(float min, float max, Entity e) {
/*  46 */     playerRotMinYaw = min;
/*  47 */     playerRotMaxYaw = max;
/*  48 */     playerRotLimitYaw = true;
/*     */     
/*  50 */     if (e != null)
/*     */     {
/*  52 */       if (e.field_70125_A < playerRotMinPitch) {
/*     */         
/*  54 */         e.field_70125_A = playerRotMinPitch;
/*  55 */         e.field_70127_C = playerRotMinPitch;
/*     */       }
/*  57 */       else if (e.field_70125_A > playerRotMaxPitch) {
/*     */         
/*  59 */         e.field_70125_A = playerRotMaxPitch;
/*  60 */         e.field_70127_C = playerRotMaxPitch;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void initRotLimit() {
/*  67 */     playerRotMinPitch = -90.0F;
/*  68 */     playerRotMaxPitch = 90.0F;
/*  69 */     playerRotLimitYaw = false;
/*  70 */     playerRotMinYaw = -180.0F;
/*  71 */     playerRotMaxYaw = 180.0F;
/*  72 */     playerRotLimitYaw = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void applyRotLimit(Entity e) {
/*  77 */     if (e != null) {
/*     */       
/*  79 */       if (playerRotLimitPitch)
/*     */       {
/*  81 */         if (e.field_70125_A < playerRotMinPitch) {
/*     */           
/*  83 */           e.field_70125_A = playerRotMinPitch;
/*  84 */           e.field_70127_C = playerRotMinPitch;
/*     */         }
/*  86 */         else if (e.field_70125_A > playerRotMaxPitch) {
/*     */           
/*  88 */           e.field_70125_A = playerRotMaxPitch;
/*  89 */           e.field_70127_C = playerRotMaxPitch;
/*     */         } 
/*     */       }
/*     */       
/*  93 */       if (!playerRotLimitYaw);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MCH_ClientTickHandlerBase(Minecraft minecraft) {
/* 101 */     this.mc = minecraft;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean updateMouseWheel(int wheel) {
/* 106 */     boolean cancelEvent = false;
/*     */     
/* 108 */     if (wheel != 0 && MCH_Config.SwitchWeaponWithMouseWheel.prmBool) {
/*     */       
/* 110 */       setMouseWheel(0);
/* 111 */       EntityPlayerSP entityPlayerSP = (Minecraft.func_71410_x()).field_71439_g;
/*     */       
/* 113 */       if (entityPlayerSP != null) {
/*     */         
/* 115 */         MCH_EntityAircraft ac = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)entityPlayerSP);
/*     */         
/* 117 */         if (ac != null) {
/*     */           
/* 119 */           int cwid = ac.getWeaponIDBySeatID(ac.getSeatIdByEntity((Entity)entityPlayerSP));
/* 120 */           int nwid = ac.getNextWeaponID((Entity)entityPlayerSP, 1);
/*     */           
/* 122 */           if (cwid != nwid) {
/*     */             
/* 124 */             setMouseWheel(wheel);
/* 125 */             cancelEvent = true;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 130 */     return cancelEvent;
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract void onTick(boolean paramBoolean);
/*     */ 
/*     */   
/*     */   public static void playSoundOK() {
/* 138 */     W_McClient.playSoundClick(1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void playSoundNG() {
/* 143 */     W_McClient.MOD_playSoundFX("ng", 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void playSound(String name) {
/* 148 */     W_McClient.MOD_playSoundFX(name, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void playSound(String name, float vol, float pitch) {
/* 153 */     W_McClient.MOD_playSoundFX(name, vol, pitch);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getMouseWheel() {
/* 158 */     return mouseWheel;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setMouseWheel(int mouseWheel) {
/* 163 */     MCH_ClientTickHandlerBase.mouseWheel = mouseWheel;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\MCH_ClientTickHandlerBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */