/*     */ package mcheli.aircraft;
/*     */ 
/*     */ import mcheli.MCH_Lib;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_Blade
/*     */ {
/*     */   private float baseRotation;
/*     */   private float rotation;
/*     */   private float prevRotation;
/*     */   private float foldSpeed;
/*     */   private float foldRotation;
/*     */   
/*     */   public MCH_Blade(float baseRotation) {
/*  21 */     this.rotation = 0.0F;
/*  22 */     this.prevRotation = 0.0F;
/*  23 */     this.baseRotation = baseRotation;
/*  24 */     this.foldSpeed = 3.0F;
/*  25 */     this.foldRotation = 5.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getRotation() {
/*  30 */     return this.rotation;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRotation(float rotation) {
/*  35 */     this.rotation = (float)MCH_Lib.getRotate360(rotation);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getPrevRotation() {
/*  40 */     return this.prevRotation;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPrevRotation(float rotation) {
/*  45 */     this.prevRotation = (float)MCH_Lib.getRotate360(rotation);
/*     */   }
/*     */ 
/*     */   
/*     */   public MCH_Blade setBaseRotation(float rotation) {
/*  50 */     if (rotation >= 0.0D)
/*  51 */       this.baseRotation = rotation; 
/*  52 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getBaseRotation() {
/*  57 */     return this.baseRotation;
/*     */   }
/*     */ 
/*     */   
/*     */   public MCH_Blade setFoldSpeed(float speed) {
/*  62 */     if (speed > 0.1D)
/*  63 */       this.foldSpeed = speed; 
/*  64 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public MCH_Blade setFoldRotation(float rotation) {
/*  69 */     if (rotation > this.foldSpeed * 2.0F)
/*  70 */       this.foldRotation = rotation; 
/*  71 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void forceFold() {
/*  76 */     if (this.rotation > this.foldRotation && this.rotation < 360.0F - this.foldRotation) {
/*     */       
/*  78 */       if (this.rotation < 180.0F) {
/*     */         
/*  80 */         this.rotation = this.foldRotation;
/*     */       }
/*     */       else {
/*     */         
/*  84 */         this.rotation = 360.0F - this.foldRotation;
/*     */       } 
/*  86 */       this.rotation %= 360.0F;
/*  87 */       this.prevRotation = this.rotation;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean folding() {
/*  93 */     boolean isCmpFolding = false;
/*  94 */     if (this.rotation > this.foldRotation && this.rotation < 360.0F - this.foldRotation) {
/*     */       
/*  96 */       if (this.rotation < 180.0F) {
/*     */         
/*  98 */         this.rotation -= this.foldSpeed;
/*     */       }
/*     */       else {
/*     */         
/* 102 */         this.rotation += this.foldSpeed;
/*     */       } 
/* 104 */       this.rotation %= 360.0F;
/*     */     }
/*     */     else {
/*     */       
/* 108 */       isCmpFolding = true;
/*     */     } 
/* 110 */     return isCmpFolding;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean unfolding(float rot) {
/* 115 */     boolean isCmpUnfolding = false;
/* 116 */     float targetRot = (float)MCH_Lib.getRotate360((rot + this.baseRotation));
/* 117 */     float prevRot = this.rotation;
/* 118 */     if (targetRot <= 180.0F) {
/*     */       
/* 120 */       this.rotation = (float)MCH_Lib.getRotate360((this.rotation + this.foldSpeed));
/* 121 */       if (this.rotation >= targetRot && prevRot <= targetRot)
/*     */       {
/* 123 */         this.rotation = targetRot;
/* 124 */         isCmpUnfolding = true;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 129 */       this.rotation = (float)MCH_Lib.getRotate360((this.rotation - this.foldSpeed));
/* 130 */       if (this.rotation <= targetRot && prevRot >= targetRot) {
/*     */         
/* 132 */         this.rotation = targetRot;
/* 133 */         isCmpUnfolding = true;
/*     */       } 
/*     */     } 
/* 136 */     return isCmpUnfolding;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\aircraft\MCH_Blade.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */