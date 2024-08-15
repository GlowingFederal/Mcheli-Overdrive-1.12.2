/*    */ package mcheli.wrapper;
/*    */ 
/*    */ import mcheli.__helper.MCH_SoundEvents;
/*    */ import net.minecraft.client.audio.MovingSound;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.SoundCategory;
/*    */ import net.minecraft.util.SoundEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class W_Sound
/*    */   extends MovingSound
/*    */ {
/*    */   protected W_Sound(ResourceLocation r, float volume, float pitch, double x, double y, double z) {
/* 21 */     super(MCH_SoundEvents.getSound(r), SoundCategory.MASTER);
/* 22 */     setVolumeAndPitch(volume, pitch);
/* 23 */     setPosition(x, y, z);
/*    */   }
/*    */ 
/*    */   
/*    */   protected W_Sound(ResourceLocation r, float volume, float pitch) {
/* 28 */     this(MCH_SoundEvents.getSound(r), volume, pitch);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected W_Sound(SoundEvent soundEvent, float volume, float pitch) {
/* 35 */     super(soundEvent, SoundCategory.MASTER);
/* 36 */     setVolumeAndPitch(volume, pitch);
/* 37 */     Entity entity = W_McClient.getRenderEntity();
/* 38 */     if (entity != null)
/*    */     {
/* 40 */       setPosition(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void setRepeat(boolean b) {
/* 46 */     this.field_147659_g = b;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setSoundParam(Entity e, float v, float p) {
/* 51 */     setPosition(e);
/* 52 */     setVolumeAndPitch(v, p);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setVolumeAndPitch(float v, float p) {
/* 57 */     setVolume(v);
/* 58 */     setPitch(p);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setVolume(float v) {
/* 63 */     this.field_147662_b = v;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setPitch(float p) {
/* 68 */     this.field_147663_c = p;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setPosition(double x, double y, double z) {
/* 73 */     this.field_147660_d = (float)x;
/* 74 */     this.field_147661_e = (float)y;
/* 75 */     this.field_147658_f = (float)z;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setPosition(Entity e) {
/* 80 */     setPosition(e.field_70165_t, e.field_70163_u, e.field_70161_v);
/*    */   }
/*    */   
/*    */   public void func_73660_a() {}
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\wrapper\W_Sound.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */