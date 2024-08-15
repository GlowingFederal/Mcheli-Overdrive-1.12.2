/*     */ package mcheli.wrapper;
/*     */ 
/*     */ import javax.annotation.Nonnull;
/*     */ import mcheli.__helper.client.MCH_CameraManager;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.multiplayer.PlayerControllerMP;
/*     */ import net.minecraft.client.renderer.EntityRenderer;
/*     */ import net.minecraft.client.renderer.ItemRenderer;
/*     */ import net.minecraft.client.renderer.entity.Render;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class W_Reflection
/*     */ {
/*     */   public static RenderManager getRenderManager(Render<?> render) {
/*  31 */     return render.func_177068_d();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void restoreDefaultThirdPersonDistance() {
/*  36 */     setThirdPersonDistance(4.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setThirdPersonDistance(float dist) {
/*  41 */     if (dist < 0.1D) {
/*     */       return;
/*     */     }
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
/*     */     
/*  57 */     MCH_CameraManager.setThirdPeasonCameraDistance(dist);
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float getThirdPersonDistance() {
/*  76 */     return MCH_CameraManager.getThirdPeasonCameraDistance();
/*     */   }
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
/*     */   public static void setCameraRoll(float roll) {
/* 102 */     MCH_CameraManager.setCameraRoll(roll);
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void restoreCameraZoom() {
/* 125 */     setCameraZoom(1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setCameraZoom(float zoom) {
/*     */     try {
/* 132 */       Minecraft mc = Minecraft.func_71410_x();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 138 */       ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, mc.field_71460_t, Float.valueOf(zoom), "field_78503_V");
/* 139 */       MCH_CameraManager.setCameraZoom(zoom);
/*     */     }
/* 141 */     catch (Exception e) {
/*     */       
/* 143 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static void setItemRenderer(ItemRenderer r) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setCreativeDigSpeed(int n) {
/*     */     try {
/* 164 */       Minecraft mc = Minecraft.func_71410_x();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 170 */       ObfuscationReflectionHelper.setPrivateValue(PlayerControllerMP.class, mc.field_71442_b, Integer.valueOf(n), "field_78781_i");
/*     */     
/*     */     }
/* 173 */     catch (Exception e) {
/*     */       
/* 175 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static ItemRenderer getItemRenderer() {
/* 181 */     return (Minecraft.func_71410_x()).field_71460_t.field_78516_c;
/*     */   }
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
/*     */   public static void setItemRendererMainHand(ItemStack itemToRender) {
/*     */     try {
/* 196 */       ObfuscationReflectionHelper.setPrivateValue(ItemRenderer.class, getItemRenderer(), itemToRender, "field_187467_d");
/*     */     
/*     */     }
/* 199 */     catch (Exception e) {
/*     */       
/* 201 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static ItemStack getItemRendererMainHand() {
/*     */     try {
/* 216 */       return (ItemStack)ObfuscationReflectionHelper.getPrivateValue(ItemRenderer.class, getItemRenderer(), "field_187467_d");
/*     */     }
/* 218 */     catch (Exception e) {
/*     */       
/* 220 */       e.printStackTrace();
/*     */ 
/*     */       
/* 223 */       return ItemStack.field_190927_a;
/*     */     } 
/*     */   }
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
/*     */   public static void setItemRendererMainProgress(float equippedProgress) {
/*     */     try {
/* 238 */       ObfuscationReflectionHelper.setPrivateValue(ItemRenderer.class, getItemRenderer(), Float.valueOf(equippedProgress), "field_187469_f");
/*     */     
/*     */     }
/* 241 */     catch (Exception e) {
/*     */       
/* 243 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\wrapper\W_Reflection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */