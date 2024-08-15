/*     */ package mcheli.weapon;
/*     */ 
/*     */ import mcheli.aircraft.MCH_EntityAircraft;
/*     */ import mcheli.aircraft.MCH_PacketNotifyTVMissileEntity;
/*     */ import mcheli.wrapper.W_Entity;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_WeaponTvMissile
/*     */   extends MCH_WeaponBase
/*     */ {
/*  19 */   protected MCH_EntityTvMissile lastShotTvMissile = null;
/*  20 */   protected Entity lastShotEntity = null;
/*     */   
/*     */   protected boolean isTVGuided = false;
/*     */   
/*     */   public MCH_WeaponTvMissile(World w, Vec3d v, float yaw, float pitch, String nm, MCH_WeaponInfo wi) {
/*  25 */     super(w, v, yaw, pitch, nm, wi);
/*  26 */     this.power = 32;
/*  27 */     this.acceleration = 2.0F;
/*  28 */     this.explosionPower = 4;
/*  29 */     this.interval = -100;
/*     */     
/*  31 */     if (w.field_72995_K) {
/*  32 */       this.interval -= 10;
/*     */     }
/*  34 */     this.numMode = 2;
/*  35 */     this.lastShotEntity = null;
/*  36 */     this.lastShotTvMissile = null;
/*  37 */     this.isTVGuided = false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  43 */     String opt = "";
/*     */     
/*  45 */     if (getCurrentMode() == 0) {
/*  46 */       opt = " [TV]";
/*     */     }
/*  48 */     if (getCurrentMode() == 2) {
/*  49 */       opt = " [TA]";
/*     */     }
/*  51 */     return super.getName() + opt;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(int countWait) {
/*  57 */     super.update(countWait);
/*     */     
/*  59 */     if (!this.worldObj.field_72995_K) {
/*     */       
/*  61 */       if (this.isTVGuided)
/*     */       {
/*  63 */         if (this.tick <= 9) {
/*     */           
/*  65 */           if (this.tick % 3 == 0)
/*     */           {
/*  67 */             if (this.lastShotTvMissile != null && !this.lastShotTvMissile.field_70128_L && this.lastShotEntity != null && !this.lastShotEntity.field_70128_L)
/*     */             {
/*     */               
/*  70 */               MCH_PacketNotifyTVMissileEntity.send(W_Entity.getEntityId(this.lastShotEntity), 
/*  71 */                   W_Entity.getEntityId((Entity)this.lastShotTvMissile));
/*     */             }
/*     */           }
/*     */           
/*  75 */           if (this.tick == 9) {
/*     */             
/*  77 */             this.lastShotEntity = null;
/*  78 */             this.lastShotTvMissile = null;
/*     */           } 
/*     */         } 
/*     */       }
/*  82 */       if (this.tick <= 2)
/*     */       {
/*  84 */         if (this.lastShotEntity instanceof MCH_EntityAircraft)
/*     */         {
/*  86 */           ((MCH_EntityAircraft)this.lastShotEntity).setTVMissile(this.lastShotTvMissile);
/*     */         }
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shot(MCH_WeaponParam prm) {
/*  95 */     if (this.worldObj.field_72995_K)
/*     */     {
/*  97 */       return shotClient(prm.entity, prm.user);
/*     */     }
/*  99 */     return shotServer(prm);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean shotClient(Entity entity, Entity user) {
/* 104 */     this.optionParameter2 = 0;
/* 105 */     this.optionParameter1 = getCurrentMode();
/* 106 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean shotServer(MCH_WeaponParam prm) {
/* 111 */     float yaw = prm.user.field_70177_z + this.fixRotationYaw;
/* 112 */     float pitch = prm.user.field_70125_A + this.fixRotationPitch;
/* 113 */     double tX = (-MathHelper.func_76126_a(yaw / 180.0F * 3.1415927F) * MathHelper.func_76134_b(pitch / 180.0F * 3.1415927F));
/* 114 */     double tZ = (MathHelper.func_76134_b(yaw / 180.0F * 3.1415927F) * MathHelper.func_76134_b(pitch / 180.0F * 3.1415927F));
/* 115 */     double tY = -MathHelper.func_76126_a(pitch / 180.0F * 3.1415927F);
/*     */     
/* 117 */     this.isTVGuided = (prm.option1 == 0);
/*     */     
/* 119 */     float acr = this.acceleration;
/*     */     
/* 121 */     if (!this.isTVGuided)
/*     */     {
/* 123 */       acr = (float)(acr * 1.5D);
/*     */     }
/*     */     
/* 126 */     MCH_EntityTvMissile e = new MCH_EntityTvMissile(this.worldObj, prm.posX, prm.posY, prm.posZ, tX, tY, tZ, yaw, pitch, acr);
/*     */ 
/*     */     
/* 129 */     e.setName(this.name);
/* 130 */     e.setParameterFromWeapon(this, prm.entity, prm.user);
/*     */     
/* 132 */     this.lastShotEntity = prm.entity;
/* 133 */     this.lastShotTvMissile = e;
/*     */     
/* 135 */     this.worldObj.func_72838_d((Entity)e);
/*     */     
/* 137 */     playSound(prm.entity);
/*     */     
/* 139 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\weapon\MCH_WeaponTvMissile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */