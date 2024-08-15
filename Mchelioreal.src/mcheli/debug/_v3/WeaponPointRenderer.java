/*     */ package mcheli.debug._v3;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import javax.vecmath.Color4f;
/*     */ import mcheli.aircraft.MCH_AircraftInfo;
/*     */ import mcheli.aircraft.MCH_EntityAircraft;
/*     */ import mcheli.weapon.MCH_WeaponBase;
/*     */ import mcheli.weapon.MCH_WeaponSet;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
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
/*     */ public class WeaponPointRenderer
/*     */ {
/*  29 */   private static final Color4f[] C = new Color4f[] { new Color4f(1.0F, 0.0F, 0.0F, 1.0F), new Color4f(0.0F, 1.0F, 0.0F, 1.0F), new Color4f(0.0F, 0.0F, 1.0F, 1.0F), new Color4f(1.0F, 1.0F, 0.0F, 1.0F), new Color4f(1.0F, 0.0F, 1.0F, 1.0F), new Color4f(0.0F, 1.0F, 1.0F, 1.0F), new Color4f(0.95686275F, 0.6431373F, 0.3764706F, 1.0F), new Color4f(0.5411765F, 0.16862746F, 0.42477876F, 1.0F) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void renderWeaponPoints(MCH_EntityAircraft ac, MCH_AircraftInfo info, double x, double y, double z) {
/*  43 */     int prevPointSize = GlStateManager.func_187397_v(2833);
/*  44 */     int id = 0;
/*  45 */     int prevFunc = GlStateManager.func_187397_v(2932);
/*     */     
/*  47 */     Map<Vec3d, Integer> poses = Maps.newHashMap();
/*     */     
/*  49 */     GlStateManager.func_179090_x();
/*  50 */     GlStateManager.func_179147_l();
/*  51 */     GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
/*  52 */     GlStateManager.func_179145_e();
/*  53 */     GlStateManager.func_179132_a(false);
/*  54 */     GlStateManager.func_179143_c(519);
/*  55 */     GL11.glPointSize(20.0F);
/*     */     
/*  57 */     GlStateManager.func_179094_E();
/*  58 */     GlStateManager.func_179137_b(x, y, z);
/*     */     
/*  60 */     for (MCH_AircraftInfo.WeaponSet wsInfo : info.weaponSetList) {
/*     */       
/*  62 */       MCH_WeaponSet ws = ac.getWeaponByName(wsInfo.type);
/*     */       
/*  64 */       if (ws != null) {
/*     */         
/*  66 */         Tessellator tessellator = Tessellator.func_178181_a();
/*  67 */         BufferBuilder builder = tessellator.func_178180_c();
/*     */         
/*  69 */         builder.func_181668_a(0, DefaultVertexFormats.field_181706_f);
/*     */         
/*  71 */         for (int i = 0; i < ws.getWeaponNum(); i++) {
/*     */           
/*  73 */           MCH_WeaponBase weapon = ws.getWeapon(i);
/*     */           
/*  75 */           if (weapon != null) {
/*     */             
/*  77 */             int j = 0;
/*     */             
/*  79 */             if (poses.containsKey(weapon.position)) {
/*     */               
/*  81 */               j = ((Integer)poses.get(weapon.position)).intValue();
/*  82 */               j++;
/*     */             } 
/*     */             
/*  85 */             poses.put(weapon.position, Integer.valueOf(j));
/*     */             
/*  87 */             Vec3d vec3d = weapon.getShotPos((Entity)ac);
/*  88 */             Color4f c = C[id % C.length];
/*  89 */             float f = i * 0.1F;
/*  90 */             double d = j * 0.04D;
/*     */             
/*  92 */             builder.func_181662_b(vec3d.field_72450_a, vec3d.field_72448_b + d, vec3d.field_72449_c)
/*  93 */               .func_181666_a(in(c.x + f), in(c.y + f), in(c.z + f), c.w).func_181675_d();
/*     */           } 
/*     */         } 
/*     */         
/*  97 */         tessellator.func_78381_a();
/*  98 */         id++;
/*     */       } 
/*     */     } 
/*     */     
/* 102 */     GlStateManager.func_179121_F();
/*     */     
/* 104 */     GL11.glPointSize(prevPointSize);
/* 105 */     GlStateManager.func_179143_c(prevFunc);
/* 106 */     GlStateManager.func_179132_a(true);
/* 107 */     GlStateManager.func_179098_w();
/* 108 */     GlStateManager.func_179084_k();
/*     */   }
/*     */ 
/*     */   
/*     */   static float in(float value) {
/* 113 */     return MathHelper.func_76131_a(value, 0.0F, 1.0F);
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\debug\_v3\WeaponPointRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */