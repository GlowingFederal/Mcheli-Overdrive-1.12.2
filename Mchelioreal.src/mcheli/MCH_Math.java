/*     */ package mcheli;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.FloatBuffer;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_Math
/*     */ {
/*  16 */   public static float PI = 3.1415927F;
/*  17 */   public static MCH_Math instance = new MCH_Math();
/*     */ 
/*     */   
/*     */   public FVector3D privateNewVec3D(float x, float y, float z) {
/*  21 */     FVector3D v = new FVector3D(this);
/*  22 */     v.x = x;
/*  23 */     v.y = y;
/*  24 */     v.z = z;
/*  25 */     return v;
/*     */   }
/*     */ 
/*     */   
/*     */   public static FVector3D newVec3D() {
/*  30 */     return instance.privateNewVec3D(0.0F, 0.0F, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public static FVector3D newVec3D(float x, float y, float z) {
/*  35 */     return instance.privateNewVec3D(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   private FQuat privateNewQuat() {
/*  40 */     FQuat q = new FQuat(this);
/*  41 */     QuatIdentity(q);
/*  42 */     return new FQuat(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public static FQuat newQuat() {
/*  47 */     return instance.privateNewQuat();
/*     */   }
/*     */ 
/*     */   
/*     */   private FMatrix privateNewMatrix() {
/*  52 */     FMatrix m = new FMatrix(this);
/*  53 */     MatIdentity(m);
/*  54 */     return m;
/*     */   }
/*     */ 
/*     */   
/*     */   public static FMatrix newMatrix() {
/*  59 */     return instance.privateNewMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   public static FQuat EulerToQuatTestNG(float yaw, float pitch, float roll) {
/*  64 */     FVector3D axis = newVec3D();
/*     */     
/*  66 */     float rot = VecNormalize(axis);
/*     */     
/*  68 */     FQuat dqtn = newQuat();
/*     */     
/*  70 */     QuatRotation(dqtn, rot, axis.x, axis.y, axis.z);
/*     */     
/*  72 */     return dqtn;
/*     */   }
/*     */ 
/*     */   
/*     */   public static FMatrix EulerToMatrix(float yaw, float pitch, float roll) {
/*  77 */     FMatrix m = newMatrix();
/*     */     
/*  79 */     MatTurnZ(m, roll / 180.0F * PI);
/*  80 */     MatTurnX(m, pitch / 180.0F * PI);
/*  81 */     MatTurnY(m, yaw / 180.0F * PI);
/*     */     
/*  83 */     return m;
/*     */   }
/*     */ 
/*     */   
/*     */   public static FQuat EulerToQuat(float yaw, float pitch, float roll) {
/*  88 */     FQuat dqtn = newQuat();
/*  89 */     MatrixToQuat(dqtn, EulerToMatrix(yaw, pitch, roll));
/*  90 */     return dqtn;
/*     */   }
/*     */ 
/*     */   
/*     */   public static FVector3D QuatToEuler(FQuat q) {
/*  95 */     FMatrix m = QuatToMatrix(q);
/*  96 */     return MatrixToEuler(m);
/*     */   }
/*     */ 
/*     */   
/*     */   public static FVector3D MatrixToEuler(FMatrix m) {
/* 101 */     float xx = m.m00;
/* 102 */     float xy = m.m01;
/* 103 */     float xz = m.m02;
/*     */     
/* 105 */     float yy = m.m11;
/*     */     
/* 107 */     float zx = m.m20;
/* 108 */     float zy = m.m21;
/* 109 */     float zz = m.m22;
/*     */ 
/*     */     
/* 112 */     float b = (float)-Math.asin(zy);
/* 113 */     float cosB = Cos(b);
/*     */     
/* 115 */     if (Math.abs(cosB) >= 1.0E-4D) {
/*     */       
/* 117 */       c = Atan2(zx, zz);
/*     */       
/* 119 */       float xy_cos = xy / cosB;
/*     */       
/* 121 */       if (xy_cos > 1.0F) {
/*     */         
/* 123 */         xy_cos = 1.0F;
/*     */       }
/* 125 */       else if (xy_cos < -1.0F) {
/*     */         
/* 127 */         xy_cos = -1.0F;
/*     */       } 
/*     */       
/* 130 */       a = (float)Math.asin(xy_cos);
/*     */       
/* 132 */       if (Float.isNaN(a))
/*     */       {
/* 134 */         a = 0.0F;
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 139 */       c = Atan2(-xz, xx);
/* 140 */       a = 0.0F;
/*     */     } 
/*     */     
/* 143 */     float a = (float)(a * 180.0D / PI);
/* 144 */     b = (float)(b * 180.0D / PI);
/* 145 */     float c = (float)(c * 180.0D / PI);
/*     */     
/* 147 */     if (yy < 0.0F)
/*     */     {
/* 149 */       a = 180.0F - a;
/*     */     }
/* 151 */     return newVec3D(-b, -c, -a);
/*     */   }
/*     */ 
/*     */   
/*     */   public float atan2(float y, float x) {
/* 156 */     return Atan2(y, x);
/*     */   }
/*     */ 
/*     */   
/*     */   public static float SIGN(float x) {
/* 161 */     return (x >= 0.0F) ? 1.0F : -1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public static float NORM(float a, float b, float c, float d) {
/* 166 */     return (float)Math.sqrt((a * a + b * b + c * c + d * d));
/*     */   }
/*     */ 
/*     */   
/*     */   public static void QuatNormalize(FQuat q) {
/* 171 */     float r = NORM(q.w, q.x, q.y, q.z);
/*     */     
/* 173 */     if (MathHelper.func_76135_e(r) > 1.0E-4D) {
/*     */       
/* 175 */       q.w /= r;
/* 176 */       q.x /= r;
/* 177 */       q.y /= r;
/* 178 */       q.z /= r;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean MatrixToQuat(FQuat q, FMatrix m) {
/* 184 */     q.w = (m.m00 + m.m11 + m.m22 + 1.0F) / 4.0F;
/* 185 */     q.x = (m.m00 - m.m11 - m.m22 + 1.0F) / 4.0F;
/* 186 */     q.y = (-m.m00 + m.m11 - m.m22 + 1.0F) / 4.0F;
/* 187 */     q.z = (-m.m00 - m.m11 + m.m22 + 1.0F) / 4.0F;
/* 188 */     if (q.w < 0.0F)
/* 189 */       q.w = 0.0F; 
/* 190 */     if (q.x < 0.0F)
/* 191 */       q.x = 0.0F; 
/* 192 */     if (q.y < 0.0F)
/* 193 */       q.y = 0.0F; 
/* 194 */     if (q.z < 0.0F)
/* 195 */       q.z = 0.0F; 
/* 196 */     q.w = (float)Math.sqrt(q.w);
/* 197 */     q.x = (float)Math.sqrt(q.x);
/* 198 */     q.y = (float)Math.sqrt(q.y);
/* 199 */     q.z = (float)Math.sqrt(q.z);
/* 200 */     if (q.w >= q.x && q.w >= q.y && q.w >= q.z) {
/*     */       
/* 202 */       q.w *= 1.0F;
/* 203 */       q.x *= SIGN(m.m21 - m.m12);
/* 204 */       q.y *= SIGN(m.m02 - m.m20);
/* 205 */       q.z *= SIGN(m.m10 - m.m01);
/*     */     }
/* 207 */     else if (q.x >= q.w && q.x >= q.y && q.x >= q.z) {
/*     */       
/* 209 */       q.w *= SIGN(m.m21 - m.m12);
/* 210 */       q.x *= 1.0F;
/* 211 */       q.y *= SIGN(m.m10 + m.m01);
/* 212 */       q.z *= SIGN(m.m02 + m.m20);
/*     */     }
/* 214 */     else if (q.y >= q.w && q.y >= q.x && q.y >= q.z) {
/*     */       
/* 216 */       q.w *= SIGN(m.m02 - m.m20);
/* 217 */       q.x *= SIGN(m.m10 + m.m01);
/* 218 */       q.y *= 1.0F;
/* 219 */       q.z *= SIGN(m.m21 + m.m12);
/*     */     }
/* 221 */     else if (q.z >= q.w && q.z >= q.x && q.z >= q.y) {
/*     */       
/* 223 */       q.w *= SIGN(m.m10 - m.m01);
/* 224 */       q.x *= SIGN(m.m20 + m.m02);
/* 225 */       q.y *= SIGN(m.m21 + m.m12);
/* 226 */       q.z *= 1.0F;
/*     */     }
/*     */     else {
/*     */       
/* 230 */       QuatIdentity(q);
/* 231 */       return false;
/*     */     } 
/*     */     
/* 234 */     correctQuat(q);
/*     */     
/* 236 */     float r = NORM(q.w, q.x, q.y, q.z);
/* 237 */     q.w /= r;
/* 238 */     q.x /= r;
/* 239 */     q.y /= r;
/* 240 */     q.z /= r;
/*     */     
/* 242 */     correctQuat(q);
/*     */     
/* 244 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void correctQuat(FQuat q) {
/* 249 */     if (Float.isNaN(q.w) || Float.isInfinite(q.w))
/*     */     {
/* 251 */       q.w = 0.0F;
/*     */     }
/* 253 */     if (Float.isNaN(q.x) || Float.isInfinite(q.x))
/*     */     {
/* 255 */       q.x = 0.0F;
/*     */     }
/* 257 */     if (Float.isNaN(q.y) || Float.isInfinite(q.y))
/*     */     {
/* 259 */       q.y = 0.0F;
/*     */     }
/* 261 */     if (Float.isNaN(q.z) || Float.isInfinite(q.z))
/*     */     {
/* 263 */       q.z = 0.0F;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static FQuat motionTest(int x, int y, FQuat prevQtn) {
/* 269 */     FVector3D axis = newVec3D();
/* 270 */     FQuat dqtn = newQuat();
/*     */     
/* 272 */     int dx = x;
/* 273 */     int dy = y;
/*     */     
/* 275 */     axis.x = 2.0F * PI * dy / 200.0F;
/* 276 */     axis.y = 2.0F * PI * dx / 200.0F;
/* 277 */     axis.z = 0.0F;
/*     */     
/* 279 */     float rot = VecNormalize(axis);
/*     */     
/* 281 */     QuatRotation(dqtn, rot, axis.x, axis.y, axis.z);
/*     */     
/* 283 */     return QuatMult(dqtn, prevQtn);
/*     */   }
/*     */ 
/*     */   
/*     */   public static float Sin(float rad) {
/* 288 */     return (float)Math.sin(rad);
/*     */   }
/*     */ 
/*     */   
/*     */   public static float Cos(float rad) {
/* 293 */     return (float)Math.cos(rad);
/*     */   }
/*     */ 
/*     */   
/*     */   public static float Tan(float rad) {
/* 298 */     return (float)Math.tan(rad);
/*     */   }
/*     */ 
/*     */   
/*     */   public static float Floor(float x) {
/* 303 */     return (float)Math.floor(x);
/*     */   }
/*     */ 
/*     */   
/*     */   public static float Atan(float x) {
/* 308 */     return (float)Math.atan(x);
/*     */   }
/*     */ 
/*     */   
/*     */   public static float Atan2(float y, float x) {
/* 313 */     return (float)Math.atan2(y, x);
/*     */   }
/*     */ 
/*     */   
/*     */   public static float Fabs(float x) {
/* 318 */     return (x >= 0.0F) ? x : -x;
/*     */   }
/*     */ 
/*     */   
/*     */   public static float Sqrt(float x) {
/* 323 */     return (float)Math.sqrt(x);
/*     */   }
/*     */ 
/*     */   
/*     */   public static float InvSqrt(float x) {
/* 328 */     return 1.0F / (float)Math.sqrt(x);
/*     */   }
/*     */ 
/*     */   
/*     */   public static float Pow(float a, float b) {
/* 333 */     return (float)Math.pow(a, b);
/*     */   }
/*     */ 
/*     */   
/*     */   public static float VecNormalize(FVector3D lpV) {
/* 338 */     float len2 = lpV.x * lpV.x + lpV.y * lpV.y + lpV.z * lpV.z;
/* 339 */     float length = Sqrt(len2);
/* 340 */     if (length == 0.0F)
/*     */     {
/* 342 */       return 0.0F;
/*     */     }
/*     */     
/* 345 */     float invLength = 1.0F / length;
/* 346 */     lpV.x *= invLength;
/* 347 */     lpV.y *= invLength;
/* 348 */     lpV.z *= invLength;
/*     */     
/* 350 */     return length;
/*     */   }
/*     */ 
/*     */   
/*     */   public static float Vec2DNormalize(FVector2D lpV) {
/* 355 */     float len2 = lpV.x * lpV.x + lpV.y * lpV.y;
/* 356 */     float length = Sqrt(len2);
/*     */     
/* 358 */     if (length == 0.0F)
/*     */     {
/* 360 */       return 0.0F;
/*     */     }
/*     */     
/* 363 */     float invLength = 1.0F / length;
/* 364 */     lpV.x *= invLength;
/* 365 */     lpV.y *= invLength;
/*     */     
/* 367 */     return length;
/*     */   }
/*     */ 
/*     */   
/*     */   public static FVector3D MatVector(FMatrix lpM, FVector3D lpV) {
/* 372 */     FVector3D lpS = newVec3D();
/*     */     
/* 374 */     float x = lpV.x;
/* 375 */     float y = lpV.y;
/* 376 */     float z = lpV.z;
/*     */     
/* 378 */     lpS.x = lpM.m00 * x + lpM.m01 * y + lpM.m02 * z + lpM.m03;
/* 379 */     lpS.y = lpM.m10 * x + lpM.m11 * y + lpM.m12 * z + lpM.m13;
/* 380 */     lpS.z = lpM.m20 * x + lpM.m21 * y + lpM.m22 * z + lpM.m23;
/*     */     
/* 382 */     return lpS;
/*     */   }
/*     */ 
/*     */   
/*     */   public static FVector3D MatDirection(FMatrix lpM, FVector3D lpDir) {
/* 387 */     FVector3D lpSDir = newVec3D();
/*     */     
/* 389 */     float x = lpDir.x;
/* 390 */     float y = lpDir.y;
/* 391 */     float z = lpDir.z;
/*     */     
/* 393 */     lpSDir.x = lpM.m00 * x + lpM.m01 * y + lpM.m02 * z;
/* 394 */     lpSDir.y = lpM.m10 * x + lpM.m11 * y + lpM.m12 * z;
/* 395 */     lpSDir.z = lpM.m20 * x + lpM.m21 * y + lpM.m22 * z;
/*     */     
/* 397 */     return lpSDir;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void MatIdentity(FMatrix lpM) {
/* 402 */     lpM.m01 = lpM.m02 = lpM.m03 = lpM.m10 = lpM.m12 = lpM.m13 = lpM.m20 = lpM.m21 = lpM.m23 = lpM.m30 = lpM.m31 = lpM.m32 = 0.0F;
/*     */     
/* 404 */     lpM.m00 = lpM.m11 = lpM.m22 = lpM.m33 = 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void MatCopy(FMatrix lpMa, FMatrix lpMb) {
/* 409 */     lpMa.m00 = lpMb.m00;
/* 410 */     lpMa.m10 = lpMb.m10;
/* 411 */     lpMa.m20 = lpMb.m20;
/* 412 */     lpMa.m30 = lpMb.m30;
/*     */     
/* 414 */     lpMa.m01 = lpMb.m01;
/* 415 */     lpMa.m11 = lpMb.m11;
/* 416 */     lpMa.m21 = lpMb.m21;
/* 417 */     lpMa.m31 = lpMb.m31;
/*     */     
/* 419 */     lpMa.m02 = lpMb.m02;
/* 420 */     lpMa.m12 = lpMb.m12;
/* 421 */     lpMa.m22 = lpMb.m22;
/* 422 */     lpMa.m32 = lpMb.m32;
/*     */     
/* 424 */     lpMa.m03 = lpMb.m03;
/* 425 */     lpMa.m13 = lpMb.m13;
/* 426 */     lpMa.m23 = lpMb.m23;
/* 427 */     lpMa.m33 = lpMb.m33;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void MatTranslate(FMatrix m, float x, float y, float z) {
/* 432 */     float m30 = m.m30;
/* 433 */     float m31 = m.m31;
/* 434 */     float m32 = m.m32;
/* 435 */     float m33 = m.m33;
/*     */     
/* 437 */     m.m00 += m30 * x;
/* 438 */     m.m01 += m31 * x;
/* 439 */     m.m02 += m32 * x;
/* 440 */     m.m03 += m33 * x;
/*     */     
/* 442 */     m.m10 += m30 * y;
/* 443 */     m.m11 += m31 * y;
/* 444 */     m.m12 += m32 * y;
/* 445 */     m.m13 += m33 * y;
/*     */     
/* 447 */     m.m20 += m30 * z;
/* 448 */     m.m21 += m31 * z;
/* 449 */     m.m22 += m32 * z;
/* 450 */     m.m23 += m33 * z;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void MatMove(FMatrix m, float x, float y, float z) {
/* 455 */     m.m03 += m.m00 * x + m.m01 * y + m.m02 * z;
/* 456 */     m.m13 += m.m10 * x + m.m11 * y + m.m12 * z;
/* 457 */     m.m23 += m.m20 * x + m.m21 * y + m.m22 * z;
/* 458 */     m.m33 += m.m30 * x + m.m31 * y + m.m32 * z;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void MatRotateX(FMatrix m, float rad) {
/* 463 */     if (rad > 2.0F * PI || rad < -2.0F * PI)
/*     */     {
/* 465 */       rad -= 2.0F * PI * (int)(rad / 2.0F * PI);
/*     */     }
/*     */     
/* 468 */     float cosA = Cos(rad);
/* 469 */     float sinA = Sin(rad);
/*     */     
/* 471 */     float tmp1 = m.m10;
/* 472 */     float tmp2 = m.m20;
/* 473 */     m.m10 = cosA * tmp1 - sinA * tmp2;
/* 474 */     m.m20 = sinA * tmp1 + cosA * tmp2;
/*     */     
/* 476 */     tmp1 = m.m11;
/* 477 */     tmp2 = m.m21;
/* 478 */     m.m11 = cosA * tmp1 - sinA * tmp2;
/* 479 */     m.m21 = sinA * tmp1 + cosA * tmp2;
/*     */     
/* 481 */     tmp1 = m.m12;
/* 482 */     tmp2 = m.m22;
/* 483 */     m.m12 = cosA * tmp1 - sinA * tmp2;
/* 484 */     m.m22 = sinA * tmp1 + cosA * tmp2;
/*     */     
/* 486 */     tmp1 = m.m13;
/* 487 */     tmp2 = m.m23;
/* 488 */     m.m13 = cosA * tmp1 - sinA * tmp2;
/* 489 */     m.m23 = sinA * tmp1 + cosA * tmp2;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void MatRotateY(FMatrix m, float rad) {
/* 494 */     if (rad > 2.0F * PI || rad < -2.0F * PI)
/*     */     {
/* 496 */       rad -= 2.0F * PI * (int)(rad / 2.0F * PI);
/*     */     }
/*     */     
/* 499 */     float cosA = Cos(rad);
/* 500 */     float sinA = Sin(rad);
/*     */     
/* 502 */     float tmp1 = m.m00;
/* 503 */     float tmp2 = m.m20;
/* 504 */     m.m00 = cosA * tmp1 + sinA * tmp2;
/* 505 */     m.m20 = -sinA * tmp1 + cosA * tmp2;
/*     */     
/* 507 */     tmp1 = m.m01;
/* 508 */     tmp2 = m.m21;
/* 509 */     m.m01 = cosA * tmp1 + sinA * tmp2;
/* 510 */     m.m21 = -sinA * tmp1 + cosA * tmp2;
/*     */     
/* 512 */     tmp1 = m.m02;
/* 513 */     tmp2 = m.m22;
/* 514 */     m.m02 = cosA * tmp1 + sinA * tmp2;
/* 515 */     m.m22 = -sinA * tmp1 + cosA * tmp2;
/*     */     
/* 517 */     tmp1 = m.m03;
/* 518 */     tmp2 = m.m23;
/* 519 */     m.m03 = cosA * tmp1 + sinA * tmp2;
/* 520 */     m.m23 = -sinA * tmp1 + cosA * tmp2;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void MatRotateZ(FMatrix m, float rad) {
/* 525 */     if (rad > 2.0F * PI || rad < -2.0F * PI)
/*     */     {
/* 527 */       rad -= 2.0F * PI * (int)(rad / 2.0F * PI);
/*     */     }
/*     */     
/* 530 */     float cosA = Cos(rad);
/* 531 */     float sinA = Sin(rad);
/*     */     
/* 533 */     float tmp1 = m.m00;
/* 534 */     float tmp2 = m.m10;
/* 535 */     m.m00 = cosA * tmp1 - sinA * tmp2;
/* 536 */     m.m10 = sinA * tmp1 + cosA * tmp2;
/*     */     
/* 538 */     tmp1 = m.m01;
/* 539 */     tmp2 = m.m11;
/* 540 */     m.m01 = cosA * tmp1 - sinA * tmp2;
/* 541 */     m.m11 = sinA * tmp1 + cosA * tmp2;
/*     */     
/* 543 */     tmp1 = m.m02;
/* 544 */     tmp2 = m.m12;
/* 545 */     m.m02 = cosA * tmp1 - sinA * tmp2;
/* 546 */     m.m12 = sinA * tmp1 + cosA * tmp2;
/*     */     
/* 548 */     tmp1 = m.m03;
/* 549 */     tmp2 = m.m13;
/* 550 */     m.m03 = cosA * tmp1 - sinA * tmp2;
/* 551 */     m.m13 = sinA * tmp1 + cosA * tmp2;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void MatTurnX(FMatrix m, float rad) {
/* 556 */     if (rad > 2.0F * PI || rad < -2.0F * PI)
/*     */     {
/* 558 */       rad -= 2.0F * PI * (int)(rad / 2.0F * PI);
/*     */     }
/*     */     
/* 561 */     float cosA = Cos(rad);
/* 562 */     float sinA = Sin(rad);
/*     */     
/* 564 */     float tmp1 = m.m01;
/* 565 */     float tmp2 = m.m02;
/* 566 */     m.m01 = cosA * tmp1 + sinA * tmp2;
/* 567 */     m.m02 = -sinA * tmp1 + cosA * tmp2;
/*     */     
/* 569 */     tmp1 = m.m11;
/* 570 */     tmp2 = m.m12;
/* 571 */     m.m11 = cosA * tmp1 + sinA * tmp2;
/* 572 */     m.m12 = -sinA * tmp1 + cosA * tmp2;
/*     */     
/* 574 */     tmp1 = m.m21;
/* 575 */     tmp2 = m.m22;
/* 576 */     m.m21 = cosA * tmp1 + sinA * tmp2;
/* 577 */     m.m22 = -sinA * tmp1 + cosA * tmp2;
/*     */     
/* 579 */     tmp1 = m.m31;
/* 580 */     tmp2 = m.m32;
/* 581 */     m.m31 = cosA * tmp1 + sinA * tmp2;
/* 582 */     m.m32 = -sinA * tmp1 + cosA * tmp2;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void MatTurnY(FMatrix m, float rad) {
/* 587 */     if (rad > 2.0F * PI || rad < -2.0F * PI)
/*     */     {
/* 589 */       rad -= 2.0F * PI * (int)(rad / 2.0F * PI);
/*     */     }
/*     */     
/* 592 */     float cosA = Cos(rad);
/* 593 */     float sinA = Sin(rad);
/*     */     
/* 595 */     float tmp1 = m.m00;
/* 596 */     float tmp2 = m.m02;
/* 597 */     m.m00 = cosA * tmp1 - sinA * tmp2;
/* 598 */     m.m02 = sinA * tmp1 + cosA * tmp2;
/*     */     
/* 600 */     tmp1 = m.m10;
/* 601 */     tmp2 = m.m12;
/* 602 */     m.m10 = cosA * tmp1 - sinA * tmp2;
/* 603 */     m.m12 = sinA * tmp1 + cosA * tmp2;
/*     */     
/* 605 */     tmp1 = m.m20;
/* 606 */     tmp2 = m.m22;
/* 607 */     m.m20 = cosA * tmp1 - sinA * tmp2;
/* 608 */     m.m22 = sinA * tmp1 + cosA * tmp2;
/*     */     
/* 610 */     tmp1 = m.m30;
/* 611 */     tmp2 = m.m32;
/* 612 */     m.m30 = cosA * tmp1 - sinA * tmp2;
/* 613 */     m.m32 = sinA * tmp1 + cosA * tmp2;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void MatTurnZ(FMatrix m, float rad) {
/* 618 */     if (rad > 2.0F * PI || rad < -2.0F * PI)
/*     */     {
/* 620 */       rad -= 2.0F * PI * (int)(rad / 2.0F * PI);
/*     */     }
/*     */     
/* 623 */     float cosA = Cos(rad);
/* 624 */     float sinA = Sin(rad);
/*     */     
/* 626 */     float tmp1 = m.m00;
/* 627 */     float tmp2 = m.m01;
/* 628 */     m.m00 = cosA * tmp1 + sinA * tmp2;
/* 629 */     m.m01 = -sinA * tmp1 + cosA * tmp2;
/*     */     
/* 631 */     tmp1 = m.m10;
/* 632 */     tmp2 = m.m11;
/* 633 */     m.m10 = cosA * tmp1 + sinA * tmp2;
/* 634 */     m.m11 = -sinA * tmp1 + cosA * tmp2;
/*     */     
/* 636 */     tmp1 = m.m20;
/* 637 */     tmp2 = m.m21;
/* 638 */     m.m20 = cosA * tmp1 + sinA * tmp2;
/* 639 */     m.m21 = -sinA * tmp1 + cosA * tmp2;
/*     */     
/* 641 */     tmp1 = m.m30;
/* 642 */     tmp2 = m.m31;
/* 643 */     m.m30 = cosA * tmp1 + sinA * tmp2;
/* 644 */     m.m31 = -sinA * tmp1 + cosA * tmp2;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void MatScale(FMatrix lpM, float scalex, float scaley, float scalez) {
/* 649 */     lpM.m00 = scalex * lpM.m00;
/* 650 */     lpM.m01 = scalex * lpM.m01;
/* 651 */     lpM.m02 = scalex * lpM.m02;
/* 652 */     lpM.m03 = scalex * lpM.m03;
/*     */     
/* 654 */     lpM.m10 = scaley * lpM.m10;
/* 655 */     lpM.m11 = scaley * lpM.m11;
/* 656 */     lpM.m12 = scaley * lpM.m12;
/* 657 */     lpM.m13 = scaley * lpM.m13;
/*     */     
/* 659 */     lpM.m20 = scalez * lpM.m20;
/* 660 */     lpM.m21 = scalez * lpM.m21;
/* 661 */     lpM.m22 = scalez * lpM.m22;
/* 662 */     lpM.m23 = scalez * lpM.m23;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void MatSize(FMatrix lpM, float scalex, float scaley, float scalez) {
/* 667 */     lpM.m00 = scalex * lpM.m00;
/* 668 */     lpM.m01 = scaley * lpM.m01;
/* 669 */     lpM.m02 = scalez * lpM.m02;
/*     */     
/* 671 */     lpM.m10 = scalex * lpM.m10;
/* 672 */     lpM.m11 = scaley * lpM.m11;
/* 673 */     lpM.m12 = scalez * lpM.m12;
/*     */     
/* 675 */     lpM.m20 = scalex * lpM.m20;
/* 676 */     lpM.m21 = scaley * lpM.m21;
/* 677 */     lpM.m22 = scalez * lpM.m22;
/*     */     
/* 679 */     lpM.m30 = scalex * lpM.m30;
/* 680 */     lpM.m31 = scaley * lpM.m31;
/* 681 */     lpM.m32 = scalez * lpM.m32;
/*     */   }
/*     */ 
/*     */   
/*     */   public static FQuat QuatMult(FQuat lpP, FQuat lpQ) {
/* 686 */     FQuat lpR = newQuat();
/*     */     
/* 688 */     float pw = lpP.w;
/* 689 */     float px = lpP.x;
/* 690 */     float py = lpP.y;
/* 691 */     float pz = lpP.z;
/* 692 */     float qw = lpQ.w;
/* 693 */     float qx = lpQ.x;
/* 694 */     float qy = lpQ.y;
/* 695 */     float qz = lpQ.z;
/*     */     
/* 697 */     lpR.w = pw * qw - px * qx - py * qy - pz * qz;
/* 698 */     lpR.x = pw * qx + px * qw + py * qz - pz * qy;
/* 699 */     lpR.y = pw * qy - px * qz + py * qw + pz * qx;
/* 700 */     lpR.z = pw * qz + px * qy - py * qx + pz * qw;
/*     */     
/* 702 */     return lpR;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void QuatAdd(FQuat q_out, FQuat q) {
/* 707 */     q_out.w += q.w;
/* 708 */     q_out.x += q.x;
/* 709 */     q_out.y += q.y;
/* 710 */     q_out.z += q.z;
/*     */   }
/*     */ 
/*     */   
/*     */   public static FMatrix QuatToMatrix(FQuat lpQ) {
/* 715 */     FMatrix lpM = newMatrix();
/*     */     
/* 717 */     float qw = lpQ.w;
/* 718 */     float qx = lpQ.x;
/* 719 */     float qy = lpQ.y;
/* 720 */     float qz = lpQ.z;
/*     */     
/* 722 */     float x2 = 2.0F * qx * qx;
/* 723 */     float y2 = 2.0F * qy * qy;
/* 724 */     float z2 = 2.0F * qz * qz;
/*     */     
/* 726 */     float xy = 2.0F * qx * qy;
/* 727 */     float yz = 2.0F * qy * qz;
/* 728 */     float zx = 2.0F * qz * qx;
/*     */     
/* 730 */     float wx = 2.0F * qw * qx;
/* 731 */     float wy = 2.0F * qw * qy;
/* 732 */     float wz = 2.0F * qw * qz;
/*     */     
/* 734 */     lpM.m00 = 1.0F - y2 - z2;
/* 735 */     lpM.m01 = xy - wz;
/* 736 */     lpM.m02 = zx + wy;
/* 737 */     lpM.m03 = 0.0F;
/*     */     
/* 739 */     lpM.m10 = xy + wz;
/* 740 */     lpM.m11 = 1.0F - z2 - x2;
/* 741 */     lpM.m12 = yz - wx;
/* 742 */     lpM.m13 = 0.0F;
/*     */     
/* 744 */     lpM.m20 = zx - wy;
/* 745 */     lpM.m21 = yz + wx;
/* 746 */     lpM.m22 = 1.0F - x2 - y2;
/* 747 */     lpM.m23 = 0.0F;
/*     */     
/* 749 */     lpM.m30 = lpM.m31 = lpM.m32 = 0.0F;
/* 750 */     lpM.m33 = 1.0F;
/*     */     
/* 752 */     return lpM;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void QuatRotation(FQuat lpQ, float rad, float ax, float ay, float az) {
/* 757 */     float hrad = 0.5F * rad;
/* 758 */     float s = Sin(hrad);
/*     */     
/* 760 */     lpQ.w = Cos(hrad);
/* 761 */     lpQ.x = s * ax;
/* 762 */     lpQ.y = s * ay;
/* 763 */     lpQ.z = s * az;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void QuatIdentity(FQuat lpQ) {
/* 768 */     lpQ.w = 1.0F;
/* 769 */     lpQ.x = 0.0F;
/* 770 */     lpQ.y = 0.0F;
/* 771 */     lpQ.z = 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void QuatCopy(FQuat lpTo, FQuat lpFrom) {
/* 776 */     lpTo.w = lpFrom.w;
/* 777 */     lpTo.x = lpFrom.x;
/* 778 */     lpTo.y = lpFrom.y;
/* 779 */     lpTo.z = lpFrom.z;
/*     */   }
/*     */ 
/*     */   
/*     */   public class FMatrix
/*     */   {
/*     */     float m00;
/*     */     
/*     */     float m10;
/*     */     
/*     */     float m20;
/*     */     
/*     */     float m30;
/*     */     float m01;
/*     */     float m11;
/*     */     float m21;
/*     */     float m31;
/*     */     float m02;
/*     */     float m12;
/*     */     float m22;
/*     */     float m32;
/*     */     float m03;
/*     */     float m13;
/*     */     float m23;
/*     */     float m33;
/*     */     
/*     */     public FMatrix(MCH_Math paramMCH_Math) {}
/*     */     
/*     */     public FloatBuffer toFloatBuffer() {
/* 808 */       ByteBuffer bb = ByteBuffer.allocateDirect(64);
/* 809 */       FloatBuffer fb = bb.asFloatBuffer();
/* 810 */       fb.put(this.m00);
/* 811 */       fb.put(this.m10);
/* 812 */       fb.put(this.m20);
/* 813 */       fb.put(this.m30);
/* 814 */       fb.put(this.m01);
/* 815 */       fb.put(this.m11);
/* 816 */       fb.put(this.m21);
/* 817 */       fb.put(this.m31);
/* 818 */       fb.put(this.m02);
/* 819 */       fb.put(this.m12);
/* 820 */       fb.put(this.m22);
/* 821 */       fb.put(this.m32);
/* 822 */       fb.put(this.m03);
/* 823 */       fb.put(this.m13);
/* 824 */       fb.put(this.m23);
/* 825 */       fb.put(this.m33);
/*     */       
/* 827 */       float f = fb.get(0);
/* 828 */       f = fb.get(1);
/*     */       
/* 830 */       fb.position(0);
/*     */       
/* 832 */       return fb;
/*     */     }
/*     */   }
/*     */   
/*     */   public class FQuat {
/*     */     public float w;
/*     */     public float x;
/*     */     public float y;
/*     */     public float z;
/*     */     
/*     */     public FQuat(MCH_Math paramMCH_Math) {}
/*     */   }
/*     */   
/*     */   public class FVector2D {
/*     */     public float x;
/*     */     public float y;
/*     */     
/*     */     public FVector2D(MCH_Math paramMCH_Math) {}
/*     */   }
/*     */   
/*     */   public class FVector3D {
/*     */     public float x;
/*     */     public float y;
/*     */     public float z;
/*     */     
/*     */     public FVector3D(MCH_Math paramMCH_Math) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\MCH_Math.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */