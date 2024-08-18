package mcheli.debug;

import mcheli.wrapper.W_ModelBase;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MCH_ModelTest extends W_ModelBase {
  public ModelRenderer test;
  
  public MCH_ModelTest() {
    this.test = new ModelRenderer((ModelBase)this, 0, 0);
    this.test.func_78790_a(-5.0F, -5.0F, -5.0F, 10, 10, 10, 0.0F);
  }
  
  public void renderModel(double yaw, double pitch, float par7) {
    this.test.func_78785_a(par7);
  }
}
