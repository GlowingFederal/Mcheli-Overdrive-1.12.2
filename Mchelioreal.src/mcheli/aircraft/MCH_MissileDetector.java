/*     */ package mcheli.aircraft;
/*     */ 
/*     */ import java.util.List;
/*     */ import mcheli.MCH_PacketNotifyLock;
/*     */ import mcheli.weapon.MCH_EntityBaseBullet;
/*     */ import mcheli.wrapper.W_Lib;
/*     */ import mcheli.wrapper.W_McClient;
/*     */ import mcheli.wrapper.W_WorldFunc;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_MissileDetector
/*     */ {
/*     */   private MCH_EntityAircraft ac;
/*     */   private World world;
/*     */   private int alertCount;
/*     */   public static final int SEARCH_RANGE = 60;
/*     */   
/*     */   public MCH_MissileDetector(MCH_EntityAircraft aircraft, World w) {
/*  28 */     this.world = w;
/*  29 */     this.ac = aircraft;
/*  30 */     this.alertCount = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void update() {
/*  35 */     if (!this.ac.haveFlare()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  40 */     if (this.alertCount > 0)
/*     */     {
/*  42 */       this.alertCount--;
/*     */     }
/*     */     
/*  45 */     boolean isLocked = this.ac.getEntityData().func_74767_n("Tracking");
/*  46 */     if (isLocked)
/*     */     {
/*  48 */       this.ac.getEntityData().func_74757_a("Tracking", false);
/*     */     }
/*     */     
/*  51 */     if (this.ac.getEntityData().func_74767_n("LockOn")) {
/*     */       
/*  53 */       if (this.alertCount == 0) {
/*     */         
/*  55 */         this.alertCount = 10;
/*  56 */         if (this.ac != null && this.ac.haveFlare() && !this.ac.isDestroyed())
/*     */         {
/*  58 */           for (int i = 0; i < 2; i++) {
/*     */             
/*  60 */             Entity entity = this.ac.getEntityBySeatId(i);
/*  61 */             if (entity instanceof net.minecraft.entity.player.EntityPlayerMP)
/*     */             {
/*  63 */               MCH_PacketNotifyLock.sendToPlayer((EntityPlayer)entity);
/*     */             }
/*     */           } 
/*     */         }
/*     */       } 
/*  68 */       this.ac.getEntityData().func_74757_a("LockOn", false);
/*     */     } 
/*     */     
/*  71 */     if (this.ac.isDestroyed()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  76 */     Entity rider = this.ac.getRiddenByEntity();
/*     */     
/*  78 */     if (rider == null) {
/*  79 */       rider = this.ac.getEntityBySeatId(1);
/*     */     }
/*  81 */     if (rider != null)
/*     */     {
/*  83 */       if (this.ac.isFlareUsing()) {
/*     */         
/*  85 */         destroyMissile();
/*     */       }
/*  87 */       else if (!this.ac.isUAV() && !this.world.field_72995_K) {
/*     */         
/*  89 */         if (this.alertCount == 0 && (isLocked || isLockedByMissile()))
/*     */         {
/*  91 */           this.alertCount = 20;
/*  92 */           W_WorldFunc.MOD_playSoundAtEntity((Entity)this.ac, "alert", 1.0F, 1.0F);
/*     */         }
/*     */       
/*     */       }
/*  96 */       else if (this.ac.isUAV() && this.world.field_72995_K) {
/*     */         
/*  98 */         if (this.alertCount == 0 && (isLocked || isLockedByMissile())) {
/*     */           
/* 100 */           this.alertCount = 20;
/*     */           
/* 102 */           if (W_Lib.isClientPlayer(rider))
/*     */           {
/* 104 */             W_McClient.MOD_playSoundFX("alert", 1.0F, 1.0F);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean destroyMissile() {
/* 113 */     List<MCH_EntityBaseBullet> list = this.world.func_72872_a(MCH_EntityBaseBullet.class, this.ac
/*     */         
/* 115 */         .func_174813_aQ().func_72314_b(60.0D, 60.0D, 60.0D));
/*     */     
/* 117 */     if (list != null)
/*     */     {
/* 119 */       for (int i = 0; i < list.size(); i++) {
/*     */         
/* 121 */         MCH_EntityBaseBullet msl = list.get(i);
/*     */         
/* 123 */         if (msl.targetEntity != null)
/*     */         {
/* 125 */           if (this.ac.isMountedEntity(msl.targetEntity) || msl.targetEntity.equals(this.ac)) {
/*     */             
/* 127 */             msl.targetEntity = null;
/* 128 */             msl.func_70106_y();
/*     */           } 
/*     */         }
/*     */       } 
/*     */     }
/* 133 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLockedByMissile() {
/* 138 */     List<MCH_EntityBaseBullet> list = this.world.func_72872_a(MCH_EntityBaseBullet.class, this.ac
/*     */         
/* 140 */         .func_174813_aQ().func_72314_b(60.0D, 60.0D, 60.0D));
/*     */     
/* 142 */     if (list != null)
/*     */     {
/* 144 */       for (int i = 0; i < list.size(); i++) {
/*     */         
/* 146 */         MCH_EntityBaseBullet msl = list.get(i);
/*     */         
/* 148 */         if (msl.targetEntity != null)
/*     */         {
/* 150 */           if (this.ac.isMountedEntity(msl.targetEntity) || msl.targetEntity.equals(this.ac))
/*     */           {
/* 152 */             return true;
/*     */           }
/*     */         }
/*     */       } 
/*     */     }
/* 157 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\aircraft\MCH_MissileDetector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */