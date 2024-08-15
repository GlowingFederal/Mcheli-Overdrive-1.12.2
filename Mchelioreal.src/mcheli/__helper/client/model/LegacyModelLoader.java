/*    */ package mcheli.__helper.client.model;
/*    */ 
/*    */ import net.minecraft.client.renderer.block.model.ModelBlock;
/*    */ import net.minecraft.client.renderer.block.model.ModelResourceLocation;
/*    */ import net.minecraft.client.resources.IResourceManager;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.client.model.ICustomModelLoader;
/*    */ import net.minecraftforge.client.model.IModel;
/*    */ import net.minecraftforge.client.model.ModelLoaderRegistry;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum LegacyModelLoader
/*    */   implements ICustomModelLoader
/*    */ {
/* 19 */   INSTANCE;
/*    */   
/*    */   static {
/* 22 */     TEMPLATE = "{'parent':'item/generated','textures':{'layer0':'__item__'}}".replaceAll("'", "\"");
/*    */   }
/*    */ 
/*    */   
/*    */   static final String TEMPLATE;
/*    */   public static final String VARIANT = "mcheli_legacy";
/*    */   
/*    */   public void func_110549_a(IResourceManager resourceManager) {}
/*    */   
/*    */   public boolean accepts(ResourceLocation modelLocation) {
/* 32 */     if (modelLocation instanceof ModelResourceLocation) {
/*    */       
/* 34 */       ModelResourceLocation location = (ModelResourceLocation)modelLocation;
/*    */       
/* 36 */       return (location.func_110624_b().equals("mcheli") && location.func_177518_c().equals("mcheli_legacy"));
/*    */     } 
/*    */     
/* 39 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IModel loadModel(ResourceLocation modelLocation) throws Exception {
/* 45 */     String path = modelLocation.func_110624_b() + ":items/" + modelLocation.func_110623_a();
/* 46 */     ModelBlock modelblock = ModelBlock.func_178294_a(TEMPLATE.replaceAll("__item__", path));
/* 47 */     ModelBlock parent = ModelLoaderRegistry.getModel(modelblock.func_178305_e()).asVanillaModel().get();
/* 48 */     modelblock.field_178315_d = parent;
/*    */     
/* 50 */     return new MCH_WrapperItemLayerModel(modelblock);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\client\model\LegacyModelLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */