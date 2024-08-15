/*     */ package mcheli.eval.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NumberUtil
/*     */ {
/*     */   public static long parseLong(String str) {
/*  13 */     if (str == null)
/*  14 */       return 0L; 
/*  15 */     str = str.trim();
/*  16 */     int len = str.length();
/*  17 */     if (len <= 0)
/*     */     {
/*  19 */       return 0L;
/*     */     }
/*     */     
/*  22 */     switch (str.charAt(len - 1)) {
/*     */       
/*     */       case '.':
/*     */       case 'L':
/*     */       case 'l':
/*  27 */         len--;
/*     */         break;
/*     */     } 
/*  30 */     if (len >= 3 && str.charAt(0) == '0')
/*     */     {
/*  32 */       switch (str.charAt(1)) {
/*     */         
/*     */         case 'B':
/*     */         case 'b':
/*  36 */           return parseLongBin(str, 2, len - 2);
/*     */         case 'O':
/*     */         case 'o':
/*  39 */           return parseLongOct(str, 2, len - 2);
/*     */         case 'X':
/*     */         case 'x':
/*  42 */           return parseLongHex(str, 2, len - 2);
/*     */       } 
/*     */     }
/*  45 */     return parseLongDec(str, 0, len);
/*     */   }
/*     */ 
/*     */   
/*     */   public static long parseLongBin(String str) {
/*  50 */     if (str == null)
/*  51 */       return 0L; 
/*  52 */     return parseLongBin(str, 0, str.length());
/*     */   }
/*     */ 
/*     */   
/*     */   public static long parseLongBin(String str, int pos, int len) {
/*  57 */     long ret = 0L;
/*  58 */     for (int i = 0; i < len; i++) {
/*     */       
/*  60 */       ret *= 2L;
/*  61 */       char c = str.charAt(pos++);
/*  62 */       switch (c) {
/*     */         case '0':
/*     */           break;
/*     */         
/*     */         case '1':
/*  67 */           ret++;
/*     */           break;
/*     */         default:
/*  70 */           throw new NumberFormatException(str.substring(pos, len));
/*     */       } 
/*     */     } 
/*  73 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public static long parseLongOct(String str) {
/*  78 */     if (str == null)
/*  79 */       return 0L; 
/*  80 */     return parseLongOct(str, 0, str.length());
/*     */   }
/*     */ 
/*     */   
/*     */   public static long parseLongOct(String str, int pos, int len) {
/*  85 */     long ret = 0L;
/*  86 */     for (int i = 0; i < len; i++) {
/*     */       
/*  88 */       ret *= 8L;
/*  89 */       char c = str.charAt(pos++);
/*  90 */       switch (c) {
/*     */         case '0':
/*     */           break;
/*     */         
/*     */         case '1':
/*     */         case '2':
/*     */         case '3':
/*     */         case '4':
/*     */         case '5':
/*     */         case '6':
/*     */         case '7':
/* 101 */           ret += (c - 48);
/*     */           break;
/*     */         default:
/* 104 */           throw new NumberFormatException(str.substring(pos, len));
/*     */       } 
/*     */     } 
/* 107 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public static long parseLongDec(String str) {
/* 112 */     if (str == null)
/* 113 */       return 0L; 
/* 114 */     return parseLongDec(str, 0, str.length());
/*     */   }
/*     */ 
/*     */   
/*     */   public static long parseLongDec(String str, int pos, int len) {
/* 119 */     long ret = 0L;
/* 120 */     for (int i = 0; i < len; i++) {
/*     */       
/* 122 */       ret *= 10L;
/* 123 */       char c = str.charAt(pos++);
/* 124 */       switch (c) {
/*     */         case '0':
/*     */           break;
/*     */         
/*     */         case '1':
/*     */         case '2':
/*     */         case '3':
/*     */         case '4':
/*     */         case '5':
/*     */         case '6':
/*     */         case '7':
/*     */         case '8':
/*     */         case '9':
/* 137 */           ret += (c - 48);
/*     */           break;
/*     */         default:
/* 140 */           throw new NumberFormatException(str.substring(pos, len));
/*     */       } 
/*     */     } 
/* 143 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public static long parseLongHex(String str) {
/* 148 */     if (str == null)
/* 149 */       return 0L; 
/* 150 */     return parseLongHex(str, 0, str.length());
/*     */   }
/*     */ 
/*     */   
/*     */   public static long parseLongHex(String str, int pos, int len) {
/* 155 */     long ret = 0L;
/* 156 */     for (int i = 0; i < len; i++) {
/*     */       
/* 158 */       ret *= 16L;
/* 159 */       char c = str.charAt(pos++);
/* 160 */       switch (c) {
/*     */         case '0':
/*     */           break;
/*     */         
/*     */         case '1':
/*     */         case '2':
/*     */         case '3':
/*     */         case '4':
/*     */         case '5':
/*     */         case '6':
/*     */         case '7':
/*     */         case '8':
/*     */         case '9':
/* 173 */           ret += (c - 48);
/*     */           break;
/*     */         case 'a':
/*     */         case 'b':
/*     */         case 'c':
/*     */         case 'd':
/*     */         case 'e':
/*     */         case 'f':
/* 181 */           ret += (c - 97 + 10);
/*     */           break;
/*     */         case 'A':
/*     */         case 'B':
/*     */         case 'C':
/*     */         case 'D':
/*     */         case 'E':
/*     */         case 'F':
/* 189 */           ret += (c - 65 + 10);
/*     */           break;
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
/*     */         default:
/* 225 */           throw new NumberFormatException(str.substring(pos, len));
/*     */       } 
/*     */     } 
/* 228 */     return ret;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eva\\util\NumberUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */