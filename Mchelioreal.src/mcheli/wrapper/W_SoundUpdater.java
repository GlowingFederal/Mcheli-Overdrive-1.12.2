/*    */ package mcheli.wrapper;
/*    */ 
/*    */ import mcheli.__helper.MCH_Utils;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.audio.ISound;
/*    */ import net.minecraft.client.audio.SoundHandler;
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class W_SoundUpdater
/*    */ {
/*    */   protected final SoundHandler theSoundHnadler;
/*    */   protected W_Sound es;
/*    */   
/*    */   public W_SoundUpdater(Minecraft minecraft, Entity entity) {
/* 21 */     this.theSoundHnadler = minecraft.func_147118_V();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void initEntitySound(String name) {
/* 27 */     this.es = new W_Sound(MCH_Utils.suffix(name), 1.0F, 1.0F);
/*    */     
/* 29 */     this.es.setRepeat(true);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isValidSound() {
/* 34 */     return (this.es != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public void playEntitySound(String name, Entity entity, float volume, float pitch, boolean par5) {
/* 39 */     if (isValidSound()) {
/*    */       
/* 41 */       this.es.setSoundParam(entity, volume, pitch);
/* 42 */       this.theSoundHnadler.func_147682_a((ISound)this.es);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void stopEntitySound(Entity entity) {
/* 48 */     if (isValidSound())
/*    */     {
/* 50 */       this.theSoundHnadler.func_147683_b((ISound)this.es);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isEntitySoundPlaying(Entity entity) {
/* 56 */     return isValidSound() ? this.theSoundHnadler.func_147692_c((ISound)this.es) : false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setEntitySoundPitch(Entity entity, float pitch) {
/* 61 */     if (isValidSound())
/*    */     {
/* 63 */       this.es.setPitch(pitch);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void setEntitySoundVolume(Entity entity, float volume) {
/* 69 */     if (isValidSound())
/*    */     {
/* 71 */       this.es.setVolume(volume);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateSoundLocation(Entity entity) {
/* 77 */     if (isValidSound())
/*    */     {
/* 79 */       this.es.setPosition(entity);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateSoundLocation(double x, double y, double z) {
/* 85 */     if (isValidSound())
/*    */     {
/* 87 */       this.es.setPosition(x, y, z);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void _updateSoundLocation(Entity entityListener, Entity entity) {
/* 93 */     if (isValidSound())
/* 94 */       this.es.setPosition(entity); 
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\wrapper\W_SoundUpdater.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */