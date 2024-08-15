/*     */ package mcheli;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import mcheli.wrapper.W_Entity;
/*     */ import mcheli.wrapper.W_EntityRenderer;
/*     */ import mcheli.wrapper.W_Lib;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_Camera
/*     */ {
/*     */   private final World worldObj;
/*     */   private float zoom;
/*     */   private int[] mode;
/*     */   private boolean[] canUseShader;
/*     */   private int[] lastMode;
/*     */   public double posX;
/*     */   public double posY;
/*     */   public double posZ;
/*     */   public float rotationYaw;
/*     */   public float rotationPitch;
/*     */   public float prevRotationYaw;
/*     */   public float prevRotationPitch;
/*     */   private int lastZoomDir;
/*     */   public float partRotationYaw;
/*     */   public float partRotationPitch;
/*     */   public float prevPartRotationYaw;
/*     */   public float prevPartRotationPitch;
/*     */   public static final int MODE_NORMAL = 0;
/*     */   public static final int MODE_NIGHTVISION = 1;
/*     */   public static final int MODE_THERMALVISION = 2;
/*     */   
/*     */   public MCH_Camera(World w, Entity p) {
/*  44 */     this.worldObj = w;
/*  45 */     this.mode = new int[] { 0, 0 };
/*     */ 
/*     */ 
/*     */     
/*  49 */     this.zoom = 1.0F;
/*  50 */     this.lastMode = new int[getUserMax()];
/*  51 */     this.lastZoomDir = 0;
/*  52 */     this.canUseShader = new boolean[getUserMax()];
/*     */   }
/*     */ 
/*     */   
/*     */   public MCH_Camera(World w, Entity p, double x, double y, double z) {
/*  57 */     this(w, p);
/*  58 */     setPosition(x, y, z);
/*  59 */     setCameraZoom(1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getUserMax() {
/*  64 */     return this.mode.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public void initCamera(int uid, @Nullable Entity viewer) {
/*  69 */     setCameraZoom(1.0F);
/*     */     
/*  71 */     setMode(uid, 0);
/*  72 */     updateViewer(uid, viewer);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMode(int uid, int m) {
/*  77 */     if (!isValidUid(uid))
/*     */       return; 
/*  79 */     this.mode[uid] = (m < 0) ? 0 : (m % getModeNum(uid));
/*     */     
/*  81 */     switch (this.mode[uid]) {
/*     */       
/*     */       case 2:
/*  84 */         if (this.worldObj.field_72995_K)
/*     */         {
/*  86 */           W_EntityRenderer.activateShader("pencil");
/*     */         }
/*     */         break;
/*     */       case 0:
/*     */       case 1:
/*  91 */         if (this.worldObj.field_72995_K)
/*     */         {
/*  93 */           W_EntityRenderer.deactivateShader();
/*     */         }
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setShaderSupport(int uid, Boolean b) {
/* 103 */     if (isValidUid(uid)) {
/*     */       
/* 105 */       setMode(uid, 0);
/* 106 */       this.canUseShader[uid] = b.booleanValue();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValidUid(int uid) {
/* 112 */     return (uid >= 0 && uid < getUserMax());
/*     */   }
/*     */ 
/*     */   
/*     */   public int getModeNum(int uid) {
/* 117 */     if (!isValidUid(uid))
/* 118 */       return 2; 
/* 119 */     return this.canUseShader[uid] ? 3 : 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMode(int uid) {
/* 124 */     return isValidUid(uid) ? this.mode[uid] : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getModeName(int uid) {
/* 129 */     if (getMode(uid) == 1)
/* 130 */       return "NIGHT VISION"; 
/* 131 */     if (getMode(uid) == 2)
/* 132 */       return "THERMAL VISION"; 
/* 133 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateViewer(int uid, @Nullable Entity viewer) {
/* 138 */     if (!isValidUid(uid) || viewer == null) {
/*     */       return;
/*     */     }
/* 141 */     if (W_Lib.isEntityLivingBase(viewer) && !viewer.field_70128_L) {
/*     */ 
/*     */       
/* 144 */       if (getMode(uid) == 0 && this.lastMode[uid] != 0) {
/*     */ 
/*     */         
/* 147 */         PotionEffect pe = W_Entity.getActivePotionEffect(viewer, MobEffects.field_76439_r);
/*     */         
/* 149 */         if (pe != null && pe.func_76459_b() > 0 && pe.func_76459_b() < 500)
/*     */         {
/* 151 */           if (viewer.field_70170_p.field_72995_K) {
/*     */ 
/*     */             
/* 154 */             W_Entity.removePotionEffectClient(viewer, MobEffects.field_76439_r);
/*     */           
/*     */           }
/*     */           else {
/*     */             
/* 159 */             W_Entity.removePotionEffect(viewer, MobEffects.field_76439_r);
/*     */           } 
/*     */         }
/*     */       } 
/* 163 */       if (getMode(uid) == 1 || getMode(uid) == 2) {
/*     */ 
/*     */         
/* 166 */         PotionEffect pe = W_Entity.getActivePotionEffect(viewer, MobEffects.field_76439_r);
/*     */         
/* 168 */         if (pe == null || (pe != null && pe.func_76459_b() < 500))
/*     */         {
/* 170 */           if (!viewer.field_70170_p.field_72995_K)
/*     */           {
/*     */             
/* 173 */             W_Entity.addPotionEffect(viewer, new PotionEffect(MobEffects.field_76439_r, 250, 0, true, false));
/*     */           }
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 180 */     this.lastMode[uid] = getMode(uid);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPosition(double x, double y, double z) {
/* 185 */     this.posX = x;
/* 186 */     this.posY = y;
/* 187 */     this.posZ = z;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCameraZoom(float z) {
/* 192 */     float prevZoom = this.zoom;
/* 193 */     this.zoom = (z < 1.0F) ? 1.0F : z;
/* 194 */     if (this.zoom > prevZoom) {
/*     */       
/* 196 */       this.lastZoomDir = 1;
/*     */     }
/* 198 */     else if (this.zoom < prevZoom) {
/*     */       
/* 200 */       this.lastZoomDir = -1;
/*     */     }
/*     */     else {
/*     */       
/* 204 */       this.lastZoomDir = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public float getCameraZoom() {
/* 210 */     return this.zoom;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLastZoomDir() {
/* 216 */     return this.lastZoomDir;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\MCH_Camera.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */