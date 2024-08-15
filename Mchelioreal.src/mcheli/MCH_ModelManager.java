/*     */ package mcheli;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import mcheli.__helper.client._IModelCustom;
/*     */ import mcheli.wrapper.modelloader.W_ModelCustom;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class MCH_ModelManager extends W_ModelBase {
/*     */   private static HashMap<String, _IModelCustom> map;
/*     */   private static ModelRenderer defaultModel;
/*     */   private static boolean forceReloadMode = false;
/*     */   private static Random rand;
/*     */   
/*     */   public static void setForceReloadMode(boolean b) {
/*     */     forceReloadMode = b;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static _IModelCustom load(String path, String name) {
/*     */     if (name == null || name.isEmpty())
/*     */       return null; 
/*     */     return load(path + "/" + name);
/*     */   }
/*     */   
/*  26 */   private static MCH_ModelManager instance = new MCH_ModelManager();
/*     */   @Nullable public static _IModelCustom load(String name) { if (name == null || name.isEmpty())
/*     */       return null;  _IModelCustom obj = map.get(name); if (obj != null)
/*     */       if (forceReloadMode) {
/*     */         map.remove(name);
/*     */       } else {
/*     */         return obj;
/*     */       }   _IModelCustom model = null; try {
/*     */       model = MCH_Models.loadModel(name);
/*     */     } catch (Exception e) {
/*     */       e.printStackTrace();
/*     */       model = null;
/*     */     } 
/*     */     if (model != null) {
/*     */       map.put(name, model);
/*     */       return model;
/*     */     } 
/*     */     return null; }
/*  44 */   public static void render(String path, String name) { render(path + "/" + name); } static { map = new HashMap<>();
/*  45 */     defaultModel = null;
/*  46 */     defaultModel = new ModelRenderer((ModelBase)instance, 0, 0);
/*  47 */     defaultModel.func_78790_a(-5.0F, -5.0F, -5.0F, 10, 10, 10, 0.0F);
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
/* 209 */     rand = new Random(); }
/*     */   public static void render(String name) { _IModelCustom model = map.get(name); if (model != null) { model.renderAll(); }
/*     */     else if (defaultModel == null)
/*     */     {  }
/* 213 */      } public static W_ModelCustom getRandome() { int size = map.size();
/*     */ 
/*     */ 
/*     */     
/* 217 */     for (int i = 0; i < 10; i++) {
/*     */       
/* 219 */       int idx = 0;
/* 220 */       int index = rand.nextInt(size);
/*     */ 
/*     */       
/* 223 */       for (_IModelCustom model : map.values()) {
/*     */         
/* 225 */         if (idx >= index && model instanceof W_ModelCustom)
/*     */         {
/* 227 */           return (W_ModelCustom)model;
/*     */         }
/* 229 */         idx++;
/*     */       } 
/*     */     } 
/* 232 */     return null; } public static void renderPart(String name, String partName) { _IModelCustom model = map.get(name); if (model != null)
/*     */       model.renderPart(partName);  }
/*     */   public static void renderLine(String path, String name, int startLine, int maxLine) { _IModelCustom model = map.get(path + "/" + name);
/*     */     if (model instanceof W_ModelCustom)
/*     */       ((W_ModelCustom)model).renderAllLine(startLine, maxLine);  }
/* 237 */   public static boolean containsModel(String path, String name) { return containsModel(path + "/" + name); } public static void render(String path, String name, int startFace, int maxFace) {
/*     */     _IModelCustom model = map.get(path + "/" + name);
/*     */     if (model instanceof W_ModelCustom)
/*     */       ((W_ModelCustom)model).renderAll(startFace, maxFace); 
/*     */   } public static boolean containsModel(String name) {
/* 242 */     return map.containsKey(name);
/*     */   }
/*     */   
/*     */   public static int getVertexNum(String path, String name) {
/*     */     _IModelCustom model = map.get(path + "/" + name);
/*     */     if (model instanceof W_ModelCustom)
/*     */       return ((W_ModelCustom)model).getVertexNum(); 
/*     */     return 0;
/*     */   }
/*     */   
/*     */   public static W_ModelCustom get(String path, String name) {
/*     */     _IModelCustom model = map.get(path + "/" + name);
/*     */     if (model instanceof W_ModelCustom)
/*     */       return (W_ModelCustom)model; 
/*     */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\MCH_ModelManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */