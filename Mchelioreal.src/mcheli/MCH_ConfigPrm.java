/*     */ package mcheli;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_ConfigPrm
/*     */ {
/*     */   public final int type;
/*     */   public final String name;
/*  13 */   public int prmInt = 0;
/*  14 */   public String prmString = "";
/*     */   public boolean prmBool = false;
/*  16 */   public double prmDouble = 0.0D;
/*  17 */   public String desc = "";
/*  18 */   public int prmIntDefault = 0;
/*  19 */   public String validVer = "";
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  24 */     if (this.type == 0)
/*  25 */       return String.valueOf(this.prmInt); 
/*  26 */     if (this.type == 1)
/*  27 */       return this.prmString; 
/*  28 */     if (this.type == 2)
/*     */     {
/*  30 */       return String.valueOf(this.prmBool);
/*     */     }
/*  32 */     if (this.type == 3)
/*  33 */       return String.format("%.2f", new Object[] {
/*     */             
/*  35 */             Double.valueOf(this.prmDouble)
/*  36 */           }).replace(',', '.'); 
/*  37 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public MCH_ConfigPrm(String parameterName, int defaultParameter) {
/*  42 */     this.prmInt = defaultParameter;
/*  43 */     this.prmIntDefault = defaultParameter;
/*  44 */     this.type = 0;
/*  45 */     this.name = parameterName;
/*     */   }
/*     */ 
/*     */   
/*     */   public MCH_ConfigPrm(String parameterName, String defaultParameter) {
/*  50 */     this.prmString = defaultParameter;
/*  51 */     this.type = 1;
/*  52 */     this.name = parameterName;
/*     */   }
/*     */ 
/*     */   
/*     */   public MCH_ConfigPrm(String parameterName, boolean defaultParameter) {
/*  57 */     this.prmBool = defaultParameter;
/*  58 */     this.type = 2;
/*  59 */     this.name = parameterName;
/*     */   }
/*     */ 
/*     */   
/*     */   public MCH_ConfigPrm(String parameterName, double defaultParameter) {
/*  64 */     this.prmDouble = defaultParameter;
/*  65 */     this.type = 3;
/*  66 */     this.name = parameterName;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean compare(String s) {
/*  71 */     return this.name.equalsIgnoreCase(s);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValidVer(String configVer) {
/*  76 */     if (configVer.length() >= 5 && this.validVer.length() >= 5) {
/*     */       
/*  78 */       String[] configVerSplit = configVer.split("\\.");
/*  79 */       String[] validVerSplit = this.validVer.split("\\.");
/*     */       
/*  81 */       if (configVerSplit.length == 3 && validVerSplit.length == 3)
/*     */       {
/*  83 */         for (int i = 0; i < 3; i++) {
/*     */           
/*  85 */           int n1 = Integer.valueOf(configVerSplit[i].replaceAll("[a-zA-Z-_]", "").trim()).intValue();
/*  86 */           int n2 = Integer.valueOf(validVerSplit[i].replaceAll("[a-zA-Z-_]", "").trim()).intValue();
/*     */           
/*  88 */           if (n1 > n2)
/*     */           {
/*  90 */             return true;
/*     */           }
/*     */           
/*  93 */           if (n1 < n2)
/*     */           {
/*  95 */             return false;
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/* 100 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPrm(int n) {
/* 105 */     if (this.type == 0) {
/* 106 */       this.prmInt = n;
/*     */     }
/*     */   }
/*     */   
/*     */   public void setPrm(String s) {
/* 111 */     if (this.type == 0)
/* 112 */       this.prmInt = Integer.parseInt(s); 
/* 113 */     if (this.type == 1)
/* 114 */       this.prmString = s; 
/* 115 */     if (this.type == 2) {
/*     */       
/* 117 */       s = s.toLowerCase();
/* 118 */       if (s.compareTo("true") == 0)
/* 119 */         this.prmBool = true; 
/* 120 */       if (s.compareTo("false") == 0)
/* 121 */         this.prmBool = false; 
/*     */     } 
/* 123 */     if (this.type == 3 && !s.isEmpty()) {
/* 124 */       this.prmDouble = MCH_Lib.parseDouble(s);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setPrm(boolean b) {
/* 129 */     if (this.type == 2) {
/* 130 */       this.prmBool = b;
/*     */     }
/*     */   }
/*     */   
/*     */   public void setPrm(double f) {
/* 135 */     if (this.type == 3)
/* 136 */       this.prmDouble = f; 
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\MCH_ConfigPrm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */