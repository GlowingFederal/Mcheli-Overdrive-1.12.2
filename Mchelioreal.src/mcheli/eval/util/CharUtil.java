/*     */ package mcheli.eval.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CharUtil
/*     */ {
/*     */   public static String escapeString(String str) {
/*  13 */     return escapeString(str, 0, str.length());
/*     */   }
/*     */ 
/*     */   
/*     */   public static String escapeString(String str, int pos, int len) {
/*  18 */     StringBuffer sb = new StringBuffer(len);
/*  19 */     int end_pos = pos + len;
/*  20 */     int[] ret = new int[1];
/*  21 */     while (pos < end_pos) {
/*     */       
/*  23 */       char c = escapeChar(str, pos, end_pos, ret);
/*  24 */       if (ret[0] <= 0)
/*     */         break; 
/*  26 */       sb.append(c);
/*  27 */       pos += ret[0];
/*     */     } 
/*  29 */     return sb.toString();
/*     */   }
/*     */   public static char escapeChar(String str, int pos, int end_pos, int[] ret) {
/*     */     long code;
/*     */     int i;
/*  34 */     if (pos >= end_pos) {
/*     */       
/*  36 */       ret[0] = 0;
/*  37 */       return Character.MIN_VALUE;
/*     */     } 
/*  39 */     char c = str.charAt(pos);
/*  40 */     if (c != '\\') {
/*     */       
/*  42 */       ret[0] = 1;
/*  43 */       return c;
/*     */     } 
/*     */     
/*  46 */     pos++;
/*  47 */     if (pos >= end_pos) {
/*     */       
/*  49 */       ret[0] = 1;
/*  50 */       return c;
/*     */     } 
/*     */     
/*  53 */     ret[0] = 2;
/*     */     
/*  55 */     c = str.charAt(pos);
/*  56 */     switch (c) {
/*     */ 
/*     */       
/*     */       case '0':
/*     */       case '1':
/*     */       case '2':
/*     */       case '3':
/*     */       case '4':
/*     */       case '5':
/*     */       case '6':
/*     */       case '7':
/*  67 */         code = (c - 48);
/*  68 */         for (i = 1; i < 3; i++) {
/*     */           
/*  70 */           pos++;
/*  71 */           if (pos >= end_pos)
/*     */             break; 
/*  73 */           c = str.charAt(pos);
/*  74 */           if (c < '0' || c > '7')
/*     */             break; 
/*  76 */           ret[0] = ret[0] + 1;
/*  77 */           code *= 8L;
/*  78 */           code += (c - 48);
/*     */         } 
/*  80 */         return (char)(int)code;
/*     */       
/*     */       case 'b':
/*  83 */         return '\b';
/*     */       case 'f':
/*  85 */         return '\f';
/*     */       case 'n':
/*  87 */         return '\n';
/*     */       case 'r':
/*  89 */         return '\r';
/*     */       case 't':
/*  91 */         return '\t';
/*     */       
/*     */       case 'u':
/*  94 */         code = 0L;
/*  95 */         for (i = 0; i < 4; i++) {
/*     */           
/*  97 */           pos++;
/*  98 */           if (pos >= end_pos)
/*     */             break; 
/* 100 */           c = str.charAt(pos);
/* 101 */           if ('0' <= c && c <= '9') {
/*     */             
/* 103 */             ret[0] = ret[0] + 1;
/* 104 */             code *= 16L;
/* 105 */             code += (c - 48);
/*     */           }
/* 107 */           else if ('a' <= c && c <= 'f') {
/*     */             
/* 109 */             ret[0] = ret[0] + 1;
/* 110 */             code *= 16L;
/* 111 */             code += (c - 97 + 10);
/*     */           }
/*     */           else {
/*     */             
/* 115 */             if ('A' > c || c > 'F')
/*     */               break; 
/* 117 */             ret[0] = ret[0] + 1;
/* 118 */             code *= 16L;
/* 119 */             code += (c - 65 + 10);
/*     */           } 
/*     */         } 
/*     */         
/* 123 */         return (char)(int)code;
/*     */     } 
/*     */     
/* 126 */     return c;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eva\\util\CharUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */