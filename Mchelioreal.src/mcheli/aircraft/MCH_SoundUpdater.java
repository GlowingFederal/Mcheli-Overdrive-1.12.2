/*     */ package mcheli.aircraft;
/*     */ 
/*     */ import mcheli.wrapper.W_SoundUpdater;
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
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class MCH_SoundUpdater
/*     */   extends W_SoundUpdater
/*     */ {
/*     */   private final MCH_EntityAircraft theAircraft;
/*     */   private final EntityPlayerSP thePlayer;
/*     */   private boolean isMoving;
/*     */   private boolean silent;
/*     */   private float aircraftPitch;
/*     */   private float aircraftVolume;
/*     */   private float addPitch;
/*     */   private boolean isFirstUpdate;
/*     */   private double prevDist;
/*  27 */   private int soundDelay = 0;
/*     */ 
/*     */   
/*     */   public MCH_SoundUpdater(Minecraft mc, MCH_EntityAircraft aircraft, EntityPlayerSP entityPlayerSP) {
/*  31 */     super(mc, (Entity)aircraft);
/*  32 */     this.theAircraft = aircraft;
/*  33 */     this.thePlayer = entityPlayerSP;
/*  34 */     this.isFirstUpdate = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void update() {
/*  39 */     if (this.theAircraft.getSoundName().isEmpty() || this.theAircraft.getAcInfo() == null) {
/*     */       return;
/*     */     }
/*     */     
/*  43 */     if (this.isFirstUpdate) {
/*     */       
/*  45 */       this.isFirstUpdate = false;
/*  46 */       initEntitySound(this.theAircraft.getSoundName());
/*     */     } 
/*     */     
/*  49 */     MCH_AircraftInfo info = this.theAircraft.getAcInfo();
/*     */     
/*  51 */     boolean isBeforeMoving = this.isMoving;
/*  52 */     boolean isDead = this.theAircraft.field_70128_L;
/*     */     
/*  54 */     if (isDead || (!this.silent && this.aircraftVolume == 0.0F)) {
/*     */ 
/*     */       
/*  57 */       if (isDead)
/*     */       {
/*  59 */         stopEntitySound((Entity)this.theAircraft);
/*     */       }
/*     */       
/*  62 */       this.silent = true;
/*     */       
/*  64 */       if (isDead) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  70 */     boolean isRide = (this.theAircraft.getSeatIdByEntity((Entity)this.thePlayer) >= 0);
/*  71 */     boolean isPlaying = isEntitySoundPlaying((Entity)this.theAircraft);
/*     */ 
/*     */     
/*  74 */     if (!isPlaying && this.aircraftVolume > 0.0F) {
/*     */ 
/*     */       
/*  77 */       if (this.soundDelay > 0) {
/*     */         
/*  79 */         this.soundDelay--;
/*     */       }
/*     */       else {
/*     */         
/*  83 */         this.soundDelay = 20;
/*  84 */         playEntitySound(this.theAircraft.getSoundName(), (Entity)this.theAircraft, this.aircraftVolume, this.aircraftPitch, true);
/*     */       } 
/*     */ 
/*     */       
/*  88 */       this.silent = false;
/*     */     } 
/*     */ 
/*     */     
/*  92 */     float prevVolume = this.aircraftVolume;
/*  93 */     float prevPitch = this.aircraftPitch;
/*  94 */     this.isMoving = ((info.soundVolume * this.theAircraft.getSoundVolume()) >= 0.01D);
/*  95 */     if (this.isMoving) {
/*     */       
/*  97 */       this.aircraftVolume = info.soundVolume * this.theAircraft.getSoundVolume();
/*  98 */       this.aircraftPitch = info.soundPitch * this.theAircraft.getSoundPitch();
/*  99 */       if (!isRide) {
/*     */         
/* 101 */         double dist = this.thePlayer.func_70011_f(this.theAircraft.field_70165_t, this.thePlayer.field_70163_u, this.theAircraft.field_70161_v);
/*     */         
/* 103 */         double pitch = this.prevDist - dist;
/* 104 */         if (Math.abs(pitch) > 0.3D) {
/*     */           
/* 106 */           this.addPitch = (float)(this.addPitch + pitch / 40.0D);
/* 107 */           float maxAddPitch = 0.2F;
/* 108 */           if (this.addPitch < -maxAddPitch)
/* 109 */             this.addPitch = -maxAddPitch; 
/* 110 */           if (this.addPitch > maxAddPitch)
/* 111 */             this.addPitch = maxAddPitch; 
/*     */         } 
/* 113 */         this.addPitch = (float)(this.addPitch * 0.9D);
/* 114 */         this.aircraftPitch += this.addPitch;
/* 115 */         this.prevDist = dist;
/*     */       } 
/* 117 */       if (this.aircraftPitch < 0.0F)
/*     */       {
/* 119 */         this.aircraftPitch = 0.0F;
/*     */       }
/*     */     }
/* 122 */     else if (isBeforeMoving) {
/*     */       
/* 124 */       this.aircraftVolume = 0.0F;
/* 125 */       this.aircraftPitch = 0.0F;
/*     */     } 
/*     */     
/* 128 */     if (!this.silent) {
/*     */       
/* 130 */       if (this.aircraftPitch != prevPitch)
/*     */       {
/*     */         
/* 133 */         setEntitySoundPitch((Entity)this.theAircraft, this.aircraftPitch);
/*     */       }
/*     */       
/* 136 */       if (this.aircraftVolume != prevVolume)
/*     */       {
/* 138 */         setEntitySoundVolume((Entity)this.theAircraft, this.aircraftVolume);
/*     */       }
/*     */     } 
/*     */     
/* 142 */     boolean updateLocation = false;
/*     */     
/* 144 */     updateLocation = true;
/*     */     
/* 146 */     if (updateLocation && this.aircraftVolume > 0.0F) {
/*     */       
/* 148 */       if (isRide)
/*     */       {
/* 150 */         updateSoundLocation((Entity)this.theAircraft);
/*     */       }
/*     */       else
/*     */       {
/* 154 */         double px = this.thePlayer.field_70165_t;
/* 155 */         double py = this.thePlayer.field_70163_u;
/* 156 */         double pz = this.thePlayer.field_70161_v;
/* 157 */         double dx = this.theAircraft.field_70165_t - px;
/* 158 */         double dy = this.theAircraft.field_70163_u - py;
/* 159 */         double dz = this.theAircraft.field_70161_v - pz;
/* 160 */         double dist = info.soundRange / 16.0D;
/* 161 */         dx /= dist;
/* 162 */         dy /= dist;
/* 163 */         dz /= dist;
/* 164 */         updateSoundLocation(px + dx, py + dy, pz + dz);
/*     */       }
/*     */     
/*     */     }
/* 168 */     else if (isEntitySoundPlaying((Entity)this.theAircraft)) {
/*     */       
/* 170 */       stopEntitySound((Entity)this.theAircraft);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\aircraft\MCH_SoundUpdater.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */