/*    */ package mcheli;
/*    */ 
/*    */ import mcheli.__helper.MCH_Utils;
/*    */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*    */ import net.minecraft.client.renderer.texture.ITextureObject;
/*    */ import net.minecraft.client.renderer.texture.ITickableTextureObject;
/*    */ import net.minecraft.client.renderer.texture.TextureManager;
/*    */ import net.minecraft.client.resources.IResourceManager;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_TextureManagerDummy
/*    */   extends TextureManager
/*    */ {
/* 20 */   public static ResourceLocation R = MCH_Utils.suffix("textures/test.png");
/*    */   
/*    */   private TextureManager tm;
/*    */   
/*    */   public MCH_TextureManagerDummy(TextureManager t) {
/* 25 */     super(null);
/* 26 */     this.tm = t;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_110577_a(ResourceLocation resouce) {
/* 32 */     if (MCH_ClientCommonTickHandler.cameraMode == 2) {
/*    */       
/* 34 */       this.tm.func_110577_a(R);
/*    */     }
/*    */     else {
/*    */       
/* 38 */       this.tm.func_110577_a(resouce);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_110580_a(ResourceLocation textureLocation, ITickableTextureObject textureObj) {
/* 55 */     return this.tm.func_110580_a(textureLocation, textureObj);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_110579_a(ResourceLocation textureLocation, ITextureObject textureObj) {
/* 61 */     return this.tm.func_110579_a(textureLocation, textureObj);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ITextureObject func_110581_b(ResourceLocation textureLocation) {
/* 67 */     return this.tm.func_110581_b(textureLocation);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ResourceLocation func_110578_a(String name, DynamicTexture texture) {
/* 73 */     return this.tm.func_110578_a(name, texture);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_110550_d() {
/* 79 */     this.tm.func_110550_d();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_147645_c(ResourceLocation textureLocation) {
/* 85 */     this.tm.func_147645_c(textureLocation);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_110549_a(IResourceManager resourceManager) {
/* 91 */     this.tm.func_110549_a(resourceManager);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\MCH_TextureManagerDummy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */