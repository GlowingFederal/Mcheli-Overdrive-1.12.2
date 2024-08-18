package mcheli;

import java.util.HashMap;
import java.util.Random;
import javax.annotation.Nullable;
import mcheli.__helper.client.MCH_Models;
import mcheli.__helper.client._IModelCustom;
import mcheli.wrapper.W_ModelBase;
import mcheli.wrapper.modelloader.W_ModelCustom;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MCH_ModelManager extends W_ModelBase {
  private static MCH_ModelManager instance = new MCH_ModelManager();
  
  private static HashMap<String, _IModelCustom> map;
  
  private static ModelRenderer defaultModel;
  
  private static boolean forceReloadMode = false;
  
  private static Random rand;
  
  static {
    map = new HashMap<>();
    defaultModel = null;
    defaultModel = new ModelRenderer((ModelBase)instance, 0, 0);
    defaultModel.func_78790_a(-5.0F, -5.0F, -5.0F, 10, 10, 10, 0.0F);
    rand = new Random();
  }
  
  public static void setForceReloadMode(boolean b) {
    forceReloadMode = b;
  }
  
  @Nullable
  public static _IModelCustom load(String path, String name) {
    if (name == null || name.isEmpty())
      return null; 
    return load(path + "/" + name);
  }
  
  @Nullable
  public static _IModelCustom load(String name) {
    if (name == null || name.isEmpty())
      return null; 
    _IModelCustom obj = map.get(name);
    if (obj != null)
      if (forceReloadMode) {
        map.remove(name);
      } else {
        return obj;
      }  
    _IModelCustom model = null;
    try {
      model = MCH_Models.loadModel(name);
    } catch (Exception e) {
      e.printStackTrace();
      model = null;
    } 
    if (model != null) {
      map.put(name, model);
      return model;
    } 
    return null;
  }
  
  public static void render(String path, String name) {
    render(path + "/" + name);
  }
  
  public static void render(String name) {
    _IModelCustom model = map.get(name);
    if (model != null) {
      model.renderAll();
    } else if (defaultModel == null) {
    
    } 
  }
  
  public static void renderPart(String name, String partName) {
    _IModelCustom model = map.get(name);
    if (model != null)
      model.renderPart(partName); 
  }
  
  public static void renderLine(String path, String name, int startLine, int maxLine) {
    _IModelCustom model = map.get(path + "/" + name);
    if (model instanceof W_ModelCustom)
      ((W_ModelCustom)model).renderAllLine(startLine, maxLine); 
  }
  
  public static void render(String path, String name, int startFace, int maxFace) {
    _IModelCustom model = map.get(path + "/" + name);
    if (model instanceof W_ModelCustom)
      ((W_ModelCustom)model).renderAll(startFace, maxFace); 
  }
  
  public static int getVertexNum(String path, String name) {
    _IModelCustom model = map.get(path + "/" + name);
    if (model instanceof W_ModelCustom)
      return ((W_ModelCustom)model).getVertexNum(); 
    return 0;
  }
  
  public static W_ModelCustom get(String path, String name) {
    _IModelCustom model = map.get(path + "/" + name);
    if (model instanceof W_ModelCustom)
      return (W_ModelCustom)model; 
    return null;
  }
  
  public static W_ModelCustom getRandome() {
    int size = map.size();
    for (int i = 0; i < 10; i++) {
      int idx = 0;
      int index = rand.nextInt(size);
      for (_IModelCustom model : map.values()) {
        if (idx >= index && model instanceof W_ModelCustom)
          return (W_ModelCustom)model; 
        idx++;
      } 
    } 
    return null;
  }
  
  public static boolean containsModel(String path, String name) {
    return containsModel(path + "/" + name);
  }
  
  public static boolean containsModel(String name) {
    return map.containsKey(name);
  }
}
