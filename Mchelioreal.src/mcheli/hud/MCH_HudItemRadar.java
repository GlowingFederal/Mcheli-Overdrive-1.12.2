/*    */ package mcheli.hud;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import mcheli.MCH_Lib;
/*    */ import mcheli.MCH_Vector2;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_HudItemRadar
/*    */   extends MCH_HudItem
/*    */ {
/*    */   private final String rot;
/*    */   private final String left;
/*    */   private final String top;
/*    */   private final String width;
/*    */   private final String height;
/*    */   private final boolean isEntityRadar;
/*    */   
/*    */   public MCH_HudItemRadar(int fileLine, boolean isEntityRadar, String rot, String left, String top, String width, String height) {
/* 26 */     super(fileLine);
/* 27 */     this.isEntityRadar = isEntityRadar;
/* 28 */     this.rot = toFormula(rot);
/* 29 */     this.left = toFormula(left);
/* 30 */     this.top = toFormula(top);
/* 31 */     this.width = toFormula(width);
/* 32 */     this.height = toFormula(height);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute() {
/* 38 */     if (this.isEntityRadar) {
/*    */       
/* 40 */       if (EntityList != null && EntityList.size() > 0)
/*    */       {
/* 42 */         drawEntityList(EntityList, (float)calc(this.rot), centerX + calc(this.left), centerY + calc(this.top), 
/* 43 */             calc(this.width), calc(this.height));
/*    */       
/*    */       }
/*    */     
/*    */     }
/* 48 */     else if (EnemyList != null && EnemyList.size() > 0) {
/*    */       
/* 50 */       drawEntityList(EnemyList, (float)calc(this.rot), centerX + calc(this.left), centerY + calc(this.top), 
/* 51 */           calc(this.width), calc(this.height));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void drawEntityList(ArrayList<MCH_Vector2> src, float r, double left, double top, double w, double h) {
/* 57 */     double w1 = -w / 2.0D;
/* 58 */     double w2 = w / 2.0D;
/* 59 */     double h1 = -h / 2.0D;
/* 60 */     double h2 = h / 2.0D;
/* 61 */     double w_factor = w / 64.0D;
/* 62 */     double h_factor = h / 64.0D;
/* 63 */     double[] list = new double[src.size() * 2];
/* 64 */     int idx = 0;
/* 65 */     for (MCH_Vector2 v : src) {
/*    */       
/* 67 */       list[idx + 0] = v.x / 2.0D * w_factor;
/* 68 */       list[idx + 1] = v.y / 2.0D * h_factor;
/* 69 */       idx += 2;
/*    */     } 
/* 71 */     MCH_Lib.rotatePoints(list, r);
/* 72 */     ArrayList<Double> drawList = new ArrayList<>();
/*    */     
/* 74 */     for (int i = 0; i + 1 < list.length; i += 2) {
/*    */       
/* 76 */       if (list[i + 0] > w1 && list[i + 0] < w2 && list[i + 1] > h1 && list[i + 1] < h2) {
/*    */         
/* 78 */         drawList.add(Double.valueOf(list[i + 0] + left + w / 2.0D));
/* 79 */         drawList.add(Double.valueOf(list[i + 1] + top + h / 2.0D));
/*    */       } 
/*    */     } 
/* 82 */     drawPoints(drawList, colorSetting, scaleFactor * 2);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\hud\MCH_HudItemRadar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */