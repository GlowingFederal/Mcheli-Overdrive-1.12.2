/*    */ package mcheli.hud;
/*    */ 
/*    */ import mcheli.wrapper.W_TextureUtil;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_HudItemTexture
/*    */   extends MCH_HudItem
/*    */ {
/*    */   private final String name;
/*    */   private final String left;
/*    */   private final String top;
/*    */   private final String width;
/*    */   private final String height;
/*    */   private final String uLeft;
/*    */   private final String vTop;
/*    */   private final String uWidth;
/*    */   private final String vHeight;
/*    */   private final String rot;
/*    */   private int textureWidth;
/*    */   private int textureHeight;
/*    */   
/*    */   public MCH_HudItemTexture(int fileLine, String name, String left, String top, String width, String height, String uLeft, String vTop, String uWidth, String vHeight, String rot) {
/* 31 */     super(fileLine);
/* 32 */     this.name = name;
/* 33 */     this.left = toFormula(left);
/* 34 */     this.top = toFormula(top);
/* 35 */     this.width = toFormula(width);
/* 36 */     this.height = toFormula(height);
/* 37 */     this.uLeft = toFormula(uLeft);
/* 38 */     this.vTop = toFormula(vTop);
/* 39 */     this.uWidth = toFormula(uWidth);
/* 40 */     this.vHeight = toFormula(vHeight);
/* 41 */     this.rot = toFormula(rot);
/* 42 */     this.textureWidth = this.textureHeight = 0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute() {
/* 48 */     GL11.glEnable(3042);
/* 49 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*    */     
/* 51 */     if (this.textureWidth == 0 || this.textureHeight == 0) {
/*    */       
/* 53 */       int w = 0;
/* 54 */       int h = 0;
/* 55 */       W_TextureUtil.TextureParam prm = W_TextureUtil.getTextureInfo("mcheli", "textures/gui/" + this.name + ".png");
/*    */ 
/*    */       
/* 58 */       if (prm != null) {
/*    */         
/* 60 */         w = prm.width;
/* 61 */         h = prm.height;
/*    */       } 
/*    */       
/* 64 */       this.textureWidth = (w > 0) ? w : 256;
/* 65 */       this.textureHeight = (h > 0) ? h : 256;
/*    */     } 
/*    */     
/* 68 */     drawTexture(this.name, centerX + calc(this.left), centerY + calc(this.top), calc(this.width), calc(this.height), 
/* 69 */         calc(this.uLeft), calc(this.vTop), calc(this.uWidth), calc(this.vHeight), (float)calc(this.rot), this.textureWidth, this.textureHeight);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\hud\MCH_HudItemTexture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */