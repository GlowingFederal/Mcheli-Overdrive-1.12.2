/*     */ package mcheli;
/*     */ 
/*     */ import mcheli.__helper.client.MCH_CameraManager;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.stats.RecipeBook;
/*     */ import net.minecraft.stats.StatisticsManager;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_ViewEntityDummy
/*     */   extends EntityPlayerSP
/*     */ {
/*  19 */   private static final AxisAlignedBB ZERO_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
/*     */   
/*  21 */   private static MCH_ViewEntityDummy instance = null;
/*     */   
/*     */   private float zoom;
/*     */ 
/*     */   
/*     */   private MCH_ViewEntityDummy(World world) {
/*  27 */     super(Minecraft.func_71410_x(), world, Minecraft.func_71410_x().func_147114_u(), new StatisticsManager(), new RecipeBook());
/*     */     
/*  29 */     this.field_70737_aN = 0;
/*  30 */     this.field_70738_aO = 1;
/*  31 */     func_70105_a(1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public static MCH_ViewEntityDummy getInstance(World w) {
/*  36 */     if (instance == null || instance.field_70128_L)
/*     */     {
/*  38 */       if (w.field_72995_K) {
/*     */         
/*  40 */         instance = new MCH_ViewEntityDummy(w);
/*  41 */         if ((Minecraft.func_71410_x()).field_71439_g != null)
/*     */         {
/*  43 */           instance.field_71158_b = (Minecraft.func_71410_x()).field_71439_g.field_71158_b;
/*     */         }
/*  45 */         instance.func_70107_b(0.0D, -4.0D, 0.0D);
/*  46 */         w.func_72838_d((Entity)instance);
/*     */       } 
/*     */     }
/*  49 */     return instance;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void onUnloadWorld() {
/*  54 */     if (instance != null) {
/*     */       
/*  56 */       instance.func_70106_y();
/*  57 */       instance = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_174813_aQ() {
/*  64 */     return ZERO_AABB;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(MCH_Camera camera) {
/*  74 */     if (camera == null)
/*     */       return; 
/*  76 */     this.zoom = camera.getCameraZoom();
/*  77 */     this.field_70126_B = this.field_70177_z;
/*  78 */     this.field_70127_C = this.field_70125_A;
/*     */     
/*  80 */     this.field_70177_z = camera.rotationYaw;
/*  81 */     this.field_70125_A = camera.rotationPitch;
/*  82 */     this.field_70169_q = camera.posX;
/*  83 */     this.field_70167_r = camera.posY;
/*  84 */     this.field_70166_s = camera.posZ;
/*  85 */     this.field_70165_t = camera.posX;
/*  86 */     this.field_70163_u = camera.posY;
/*  87 */     this.field_70161_v = camera.posZ;
/*     */     
/*  89 */     MCH_CameraManager.setCameraZoom(this.zoom);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setCameraPosition(double x, double y, double z) {
/*  94 */     if (instance == null)
/*     */       return; 
/*  96 */     instance.field_70169_q = x;
/*  97 */     instance.field_70167_r = y;
/*  98 */     instance.field_70166_s = z;
/*  99 */     instance.field_70142_S = x;
/* 100 */     instance.field_70137_T = y;
/* 101 */     instance.field_70136_U = z;
/* 102 */     instance.field_70165_t = x;
/* 103 */     instance.field_70163_u = y;
/* 104 */     instance.field_70161_v = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float func_175156_o() {
/* 115 */     return super.func_175156_o() * 1.0F / this.zoom;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float func_70047_e() {
/* 121 */     return 0.0F;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\MCH_ViewEntityDummy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */