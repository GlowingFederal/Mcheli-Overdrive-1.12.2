package mcheli.flare;

import mcheli.wrapper.W_ModelBase;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MCH_ModelFlare extends W_ModelBase {
  public ModelRenderer model;
  
  public MCH_ModelFlare() {
    this.model = (new ModelRenderer((ModelBase)this, 0, 0)).setTextureSize(4, 4);
    this.model.addBox(-2.0F, -2.0F, -2.0F, 4, 4, 4, 0.0F);
  }
  
  public void renderModel(double yaw, double pitch, float par7) {
    this.model.render(par7);
  }
}
