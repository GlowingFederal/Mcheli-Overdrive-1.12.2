/*    */ package mcheli.wrapper;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.audio.ISound;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.init.SoundEvents;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.SoundEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class W_McClient
/*    */ {
/*    */   public static void playSoundClick(float volume, float pitch) {
/* 20 */     playSound(SoundEvents.field_187909_gi, volume, pitch);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void playSound(SoundEvent sound, float volume, float pitch) {
/* 25 */     Minecraft.func_71410_x().func_147118_V().func_147682_a((ISound)new W_Sound(sound, volume, pitch));
/*    */   }
/*    */ 
/*    */   
/*    */   public static void DEF_playSoundFX(String name, float volume, float pitch) {
/* 30 */     Minecraft.func_71410_x().func_147118_V().func_147682_a((ISound)new W_Sound(new ResourceLocation(name), volume, pitch));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static void MOD_playSoundFX(String name, float volume, float pitch) {
/* 36 */     DEF_playSoundFX(W_MOD.DOMAIN + ":" + name, volume, pitch);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void addSound(String name) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public static void DEF_bindTexture(String tex) {
/* 47 */     (Minecraft.func_71410_x()).field_71446_o.func_110577_a(new ResourceLocation(tex));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static void MOD_bindTexture(String tex) {
/* 53 */     (Minecraft.func_71410_x()).field_71446_o.func_110577_a(new ResourceLocation(W_MOD.DOMAIN, tex));
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean isGamePaused() {
/* 58 */     Minecraft mc = Minecraft.func_71410_x();
/*    */     
/* 60 */     return mc.func_147113_T();
/*    */   }
/*    */ 
/*    */   
/*    */   public static Entity getRenderEntity() {
/* 65 */     return Minecraft.func_71410_x().func_175606_aa();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static void setRenderEntity(EntityLivingBase entity) {
/* 71 */     Minecraft.func_71410_x().func_175607_a((Entity)entity);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\wrapper\W_McClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */