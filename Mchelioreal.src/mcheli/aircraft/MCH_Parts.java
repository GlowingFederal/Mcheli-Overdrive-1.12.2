/*     */ package mcheli.aircraft;
/*     */ 
/*     */ import mcheli.MCH_Lib;
/*     */ import mcheli.wrapper.W_WorldFunc;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
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
/*     */ public class MCH_Parts
/*     */ {
/*     */   public final Entity parent;
/*     */   public final EntityDataManager dataManager;
/*     */   public final int shift;
/*     */   public final DataParameter<Integer> dataKey;
/*     */   public final String partName;
/*  25 */   public float prevRotation = 0.0F;
/*  26 */   public float rotation = 0.0F;
/*  27 */   public float rotationMax = 90.0F;
/*  28 */   public float rotationInv = 1.0F;
/*     */   
/*  30 */   public Sound soundStartSwichOn = new Sound(this);
/*  31 */   public Sound soundEndSwichOn = new Sound(this);
/*  32 */   public Sound soundSwitching = new Sound(this);
/*  33 */   public Sound soundStartSwichOff = new Sound(this);
/*  34 */   public Sound soundEndSwichOff = new Sound(this);
/*     */ 
/*     */   
/*     */   private boolean status = false;
/*     */ 
/*     */   
/*     */   public MCH_Parts(Entity parent, int shiftBit, DataParameter<Integer> dataKey, String name) {
/*  41 */     this.parent = parent;
/*     */     
/*  43 */     this.dataManager = parent.func_184212_Q();
/*  44 */     this.shift = shiftBit;
/*     */     
/*  46 */     this.dataKey = dataKey;
/*  47 */     this.status = ((getDataWatcherValue() & 1 << this.shift) != 0);
/*  48 */     this.partName = name;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDataWatcherValue() {
/*  54 */     return ((Integer)this.dataManager.func_187225_a(this.dataKey)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setStatusServer(boolean stat) {
/*  59 */     setStatusServer(stat, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setStatusServer(boolean stat, boolean playSound) {
/*  64 */     if (!this.parent.field_70170_p.field_72995_K)
/*     */     {
/*  66 */       if (getStatus() != stat) {
/*     */         
/*  68 */         MCH_Lib.DbgLog(false, "setStatusServer(ID=%d %s :%s -> %s)", new Object[] {
/*     */               
/*  70 */               Integer.valueOf(this.shift), this.partName, getStatus() ? "ON" : "OFF", stat ? "ON" : "OFF"
/*     */             });
/*     */         
/*  73 */         updateDataWatcher(stat);
/*  74 */         playSound(this.soundSwitching);
/*  75 */         if (!stat) {
/*     */           
/*  77 */           playSound(this.soundStartSwichOff);
/*     */         }
/*     */         else {
/*     */           
/*  81 */           playSound(this.soundStartSwichOn);
/*     */         } 
/*  83 */         update();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateDataWatcher(boolean stat) {
/*  91 */     int currentStatus = getDataWatcherValue();
/*  92 */     int mask = 1 << this.shift;
/*     */     
/*  94 */     if (!stat) {
/*     */ 
/*     */       
/*  97 */       this.dataManager.func_187227_b(this.dataKey, Integer.valueOf(currentStatus & (mask ^ 0xFFFFFFFF)));
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 102 */       this.dataManager.func_187227_b(this.dataKey, Integer.valueOf(currentStatus | mask));
/*     */     } 
/* 104 */     this.status = stat;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getStatus() {
/* 109 */     return this.status;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOFF() {
/* 114 */     return (!this.status && this.rotation <= 0.02F);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isON() {
/* 119 */     return (this.status == true && this.rotation >= this.rotationMax - 0.02F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateStatusClient(int statFromDataWatcher) {
/* 124 */     if (this.parent.field_70170_p.field_72995_K)
/*     */     {
/* 126 */       this.status = ((statFromDataWatcher & 1 << this.shift) != 0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void update() {
/* 132 */     this.prevRotation = this.rotation;
/*     */     
/* 134 */     if (getStatus()) {
/*     */       
/* 136 */       if (this.rotation < this.rotationMax)
/*     */       {
/* 138 */         this.rotation += this.rotationInv;
/* 139 */         if (this.rotation >= this.rotationMax)
/*     */         {
/* 141 */           playSound(this.soundEndSwichOn);
/*     */         
/*     */         }
/*     */       }
/*     */     
/*     */     }
/* 147 */     else if (this.rotation > 0.0F) {
/*     */       
/* 149 */       this.rotation -= this.rotationInv;
/* 150 */       if (this.rotation <= 0.0F)
/*     */       {
/* 152 */         playSound(this.soundEndSwichOff);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void forceSwitch(boolean onoff) {
/* 159 */     updateDataWatcher(onoff);
/* 160 */     this.rotation = this.prevRotation = this.rotationMax;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getFactor() {
/* 165 */     if (this.rotationMax > 0.0F)
/*     */     {
/* 167 */       return this.rotation / this.rotationMax;
/*     */     }
/* 169 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void playSound(Sound snd) {
/* 174 */     if (!snd.name.isEmpty() && !this.parent.field_70170_p.field_72995_K)
/*     */     {
/* 176 */       W_WorldFunc.MOD_playSoundAtEntity(this.parent, snd.name, snd.volume, snd.pitch);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public class Sound
/*     */   {
/* 186 */     public String name = "";
/* 187 */     public float volume = 1.0F;
/* 188 */     public float pitch = 1.0F;
/*     */ 
/*     */     
/*     */     public void setPrm(String n, float v, float p) {
/* 192 */       this.name = n;
/* 193 */       this.volume = v;
/* 194 */       this.pitch = p;
/*     */     }
/*     */     
/*     */     public Sound(MCH_Parts paramMCH_Parts) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\aircraft\MCH_Parts.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */