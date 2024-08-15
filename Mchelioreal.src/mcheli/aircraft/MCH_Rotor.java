/*     */ package mcheli.aircraft;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_Rotor
/*     */ {
/*     */   public MCH_Blade[] blades;
/*     */   private int numBlade;
/*     */   private int invRot;
/*     */   private boolean isFoldBlade;
/*     */   private boolean isFoldBladeTarget;
/*     */   private boolean haveFoldBladeFunc;
/*     */   
/*     */   public MCH_Rotor(int numBlade, int invRot, int foldSpeed, float posx, float posy, float posz, float rotx, float roty, float rotz, boolean canFoldBlade) {
/*  21 */     setupBlade(numBlade, invRot, foldSpeed);
/*  22 */     this.isFoldBlade = false;
/*  23 */     this.isFoldBladeTarget = false;
/*  24 */     this.haveFoldBladeFunc = canFoldBlade;
/*  25 */     setPostion(posx, posy, posz);
/*  26 */     setRotation(rotx, roty, rotz);
/*     */   }
/*     */ 
/*     */   
/*     */   public MCH_Rotor setPostion(float x, float y, float z) {
/*  31 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public MCH_Rotor setRotation(float x, float y, float z) {
/*  36 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBladeNum() {
/*  41 */     return this.numBlade;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupBlade(int num, int invRot, int foldSpeed) {
/*  46 */     this.invRot = (invRot > 0) ? invRot : 1;
/*  47 */     this.numBlade = (num > 0) ? num : 1;
/*  48 */     this.blades = new MCH_Blade[this.numBlade];
/*  49 */     for (int i = 0; i < this.numBlade; i++) {
/*     */       
/*  51 */       this.blades[i] = new MCH_Blade((i * this.invRot));
/*  52 */       this.blades[i].setFoldRotation((5 + i * 3)).setFoldSpeed(foldSpeed);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFoldingOrUnfolding() {
/*  58 */     return (this.isFoldBlade != this.isFoldBladeTarget);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getBladeRotaion(int bladeIndex) {
/*  63 */     if (bladeIndex >= this.numBlade)
/*  64 */       return 0.0F; 
/*  65 */     return this.blades[bladeIndex].getRotation();
/*     */   }
/*     */ 
/*     */   
/*     */   public void startUnfold() {
/*  70 */     this.isFoldBladeTarget = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void startFold() {
/*  75 */     if (this.haveFoldBladeFunc)
/*     */     {
/*  77 */       this.isFoldBladeTarget = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void forceFold() {
/*  83 */     if (this.haveFoldBladeFunc) {
/*     */       
/*  85 */       this.isFoldBladeTarget = true;
/*  86 */       this.isFoldBlade = true;
/*  87 */       for (MCH_Blade b : this.blades)
/*     */       {
/*  89 */         b.forceFold();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void update(float rot) {
/*  96 */     boolean isCmpFoldUnfold = true;
/*  97 */     for (MCH_Blade b : this.blades) {
/*     */       
/*  99 */       b.setPrevRotation(b.getRotation());
/*     */       
/* 101 */       if (!this.isFoldBlade) {
/*     */ 
/*     */         
/* 104 */         if (!this.isFoldBladeTarget)
/*     */         {
/* 106 */           b.setRotation(rot + b.getBaseRotation());
/* 107 */           isCmpFoldUnfold = false;
/*     */         
/*     */         }
/* 110 */         else if (!b.folding())
/*     */         {
/* 112 */           isCmpFoldUnfold = false;
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 117 */       else if (this.isFoldBladeTarget == true) {
/*     */         
/* 119 */         isCmpFoldUnfold = false;
/*     */       
/*     */       }
/* 122 */       else if (!b.unfolding(rot)) {
/*     */         
/* 124 */         isCmpFoldUnfold = false;
/*     */       } 
/*     */     } 
/*     */     
/* 128 */     if (isCmpFoldUnfold)
/*     */     {
/* 130 */       this.isFoldBlade = this.isFoldBladeTarget;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\aircraft\MCH_Rotor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */