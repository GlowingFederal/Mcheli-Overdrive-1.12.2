package mcheli.wrapper;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.math.MathHelper;

public abstract class W_GuiContainer extends GuiContainer {
  private float time;
  
  public W_GuiContainer(Container par1Container) {
    super(par1Container);
  }
  
  public void drawItemStack(ItemStack item, int x, int y) {
    if (item.isEmpty())
      return; 
    if (item.getItem() == null)
      return; 
    FontRenderer font = item.getItem().getFontRenderer(item);
    if (font == null)
      font = this.fontRendererObj; 
    GlStateManager.enableDepth();
    GlStateManager.disableLighting();
    this.itemRender.renderItemAndEffectIntoGUI(item, x, y);
    this.itemRender.renderItemOverlayIntoGUI(font, item, x, y, null);
    this.zLevel = 0.0F;
    this.itemRender.zLevel = 0.0F;
  }
  
  public void drawIngredient(Ingredient ingredient, int x, int y) {
    if (ingredient != Ingredient.field_193370_a) {
      ItemStack[] itemstacks = ingredient.func_193365_a();
      int index = MathHelper.floor(this.time / 20.0F) % itemstacks.length;
      drawItemStack(itemstacks[index], x, y);
    } 
  }
  
  public void drawString(String s, int x, int y, int color) {
    drawString(this.fontRendererObj, s, x, y, color);
  }
  
  public void drawCenteredString(String s, int x, int y, int color) {
    drawCenteredString(this.fontRendererObj, s, x, y, color);
  }
  
  public int getStringWidth(String s) {
    return this.fontRendererObj.getStringWidth(s);
  }
  
  public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    this.time += partialTicks;
    super.drawScreen(mouseX, mouseY, partialTicks);
  }
  
  public float getAnimationTime() {
    return this.time;
  }
}
