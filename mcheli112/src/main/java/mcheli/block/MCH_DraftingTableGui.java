package mcheli.block;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import mcheli.MCH_IRecipeList;
import mcheli.MCH_ItemRecipe;
import mcheli.MCH_Lib;
import mcheli.__helper.MCH_Recipes;
import mcheli.__helper.client.renderer.GlUtil;
import mcheli.aircraft.MCH_RenderAircraft;
import mcheli.gui.MCH_GuiSliderVertical;
import mcheli.helicopter.MCH_HeliInfoManager;
import mcheli.plane.MCP_PlaneInfoManager;
import mcheli.tank.MCH_TankInfoManager;
import mcheli.vehicle.MCH_VehicleInfoManager;
import mcheli.wrapper.W_GuiButton;
import mcheli.wrapper.W_GuiContainer;
import mcheli.wrapper.W_KeyBinding;
import mcheli.wrapper.W_McClient;
import mcheli.wrapper.modelloader.W_ModelCustom;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Mouse;

public class MCH_DraftingTableGui extends W_GuiContainer {
  private final EntityPlayer thePlayer;
  
  private MCH_GuiSliderVertical listSlider;
  
  private GuiButton buttonCreate;
  
  private GuiButton buttonNext;
  
  private GuiButton buttonPrev;
  
  private GuiButton buttonNextPage;
  
  private GuiButton buttonPrevPage;
  
  private int drawFace;
  
  private int buttonClickWait;
  
  public static final int RECIPE_HELI = 0;
  
  public static final int RECIPE_PLANE = 1;
  
  public static final int RECIPE_VEHICLE = 2;
  
  public static final int RECIPE_TANK = 3;
  
  public static final int RECIPE_ITEM = 4;
  
  public MCH_IRecipeList currentList;
  
  public MCH_CurrentRecipe current;
  
  public static final int BUTTON_HELI = 10;
  
  public static final int BUTTON_PLANE = 11;
  
  public static final int BUTTON_VEHICLE = 12;
  
  public static final int BUTTON_TANK = 13;
  
  public static final int BUTTON_ITEM = 14;
  
  public static final int BUTTON_NEXT = 20;
  
  public static final int BUTTON_PREV = 21;
  
  public static final int BUTTON_CREATE = 30;
  
  public static final int BUTTON_SELECT = 40;
  
  public static final int BUTTON_NEXT_PAGE = 50;
  
  public static final int BUTTON_PREV_PAGE = 51;
  
  public List<List<GuiButton>> screenButtonList;
  
  public int screenId = 0;
  
  public static final int SCREEN_MAIN = 0;
  
  public static final int SCREEN_LIST = 1;
  
  public static float modelZoom = 1.0F;
  
  public static float modelRotX = 0.0F;
  
  public static float modelRotY = 0.0F;
  
  public static float modelPosX = 0.0F;
  
  public static float modelPosY = 0.0F;
  
  public MCH_DraftingTableGui(EntityPlayer player, int posX, int posY, int posZ) {
    super(new MCH_DraftingTableGuiContainer(player, posX, posY, posZ));
    this.thePlayer = player;
    this.xSize = 400;
    this.ySize = 240;
    this.screenButtonList = new ArrayList<>();
    this.drawFace = 0;
    this.buttonClickWait = 0;
    MCH_Lib.DbgLog(player.world, "MCH_DraftingTableGui.MCH_DraftingTableGui", new Object[0]);
  }
  
  public void initGui() {
    super.initGui();
    this.buttonList.clear();
    this.screenButtonList.clear();
    this.screenButtonList.add(new ArrayList<>());
    this.screenButtonList.add(new ArrayList<>());
    List<GuiButton> list = this.screenButtonList.get(0);
    GuiButton btnHeli = new GuiButton(10, this.guiLeft + 20, this.guiTop + 20, 90, 20, "Helicopter List");
    GuiButton btnPlane = new GuiButton(11, this.guiLeft + 20, this.guiTop + 40, 90, 20, "Plane List");
    GuiButton btnVehicle = new GuiButton(12, this.guiLeft + 20, this.guiTop + 60, 90, 20, "Vehicle List");
    GuiButton btnTank = new GuiButton(13, this.guiLeft + 20, this.guiTop + 80, 90, 20, "Tank List");
    GuiButton btnItem = new GuiButton(14, this.guiLeft + 20, this.guiTop + 100, 90, 20, "Item List");
    btnHeli.enabled = (MCH_HeliInfoManager.getInstance().getRecipeListSize() > 0);
    btnPlane.enabled = (MCP_PlaneInfoManager.getInstance().getRecipeListSize() > 0);
    btnVehicle.enabled = (MCH_VehicleInfoManager.getInstance().getRecipeListSize() > 0);
    btnTank.enabled = (MCH_TankInfoManager.getInstance().getRecipeListSize() > 0);
    btnItem.enabled = (MCH_ItemRecipe.getInstance().getRecipeListSize() > 0);
    list.add(btnHeli);
    list.add(btnPlane);
    list.add(btnVehicle);
    list.add(btnTank);
    list.add(btnItem);
    this.buttonCreate = new GuiButton(30, this.guiLeft + 120, this.guiTop + 89, 50, 20, "Create");
    this.buttonPrev = new GuiButton(21, this.guiLeft + 120, this.guiTop + 111, 36, 20, "<<");
    this.buttonNext = new GuiButton(20, this.guiLeft + 155, this.guiTop + 111, 35, 20, ">>");
    list.add(this.buttonCreate);
    list.add(this.buttonPrev);
    list.add(this.buttonNext);
    this.buttonPrevPage = new GuiButton(51, this.guiLeft + 210, this.guiTop + 210, 60, 20, "Prev Page");
    this.buttonNextPage = new GuiButton(50, this.guiLeft + 270, this.guiTop + 210, 60, 20, "Next Page");
    list.add(this.buttonPrevPage);
    list.add(this.buttonNextPage);
    list = this.screenButtonList.get(1);
    int y = 0;
    int i;
    for (i = 0; y < 3; y++) {
      for (int x = 0; x < 2; i++) {
        int px = this.guiLeft + 30 + x * 140;
        int py = this.guiTop + 40 + y * 70;
        list.add(new GuiButton(40 + i, px, py, 45, 20, "Select"));
        x++;
      } 
    } 
    this.listSlider = new MCH_GuiSliderVertical(0, this.guiLeft + 360, this.guiTop + 20, 20, 200, "", 0.0F, 0.0F, 0.0F, 1.0F);
    list.add(this.listSlider);
    for (i = 0; i < this.screenButtonList.size(); i++) {
      list = this.screenButtonList.get(i);
      for (int j = 0; j < list.size(); j++)
        this.buttonList.add(list.get(j)); 
    } 
    switchScreen(0);
    initModelTransform();
    modelRotX = 180.0F;
    modelRotY = 90.0F;
    if (MCH_ItemRecipe.getInstance().getRecipeListSize() > 0) {
      switchRecipeList((MCH_IRecipeList)MCH_ItemRecipe.getInstance());
    } else if (MCH_HeliInfoManager.getInstance().getRecipeListSize() > 0) {
      switchRecipeList((MCH_IRecipeList)MCH_HeliInfoManager.getInstance());
    } else if (MCP_PlaneInfoManager.getInstance().getRecipeListSize() > 0) {
      switchRecipeList((MCH_IRecipeList)MCP_PlaneInfoManager.getInstance());
    } else if (MCH_VehicleInfoManager.getInstance().getRecipeListSize() > 0) {
      switchRecipeList((MCH_IRecipeList)MCH_VehicleInfoManager.getInstance());
    } else if (MCH_TankInfoManager.getInstance().getRecipeListSize() > 0) {
      switchRecipeList((MCH_IRecipeList)MCH_TankInfoManager.getInstance());
    } else {
      switchRecipeList((MCH_IRecipeList)MCH_ItemRecipe.getInstance());
    } 
  }
  
  public static void initModelTransform() {
    modelRotX = 0.0F;
    modelRotY = 0.0F;
    modelPosX = 0.0F;
    modelPosY = 0.0F;
    modelZoom = 1.0F;
  }
  
  public void updateListSliderSize(int listSize) {
    int s = listSize / 2;
    if (listSize % 2 != 0)
      s++; 
    if (s > 3) {
      this.listSlider.valueMax = (s - 3);
    } else {
      this.listSlider.valueMax = 0.0F;
    } 
    this.listSlider.setSliderValue(0.0F);
  }
  
  public void switchScreen(int id) {
    this.screenId = id;
    for (int i = 0; i < this.buttonList.size(); i++)
      W_GuiButton.setVisible(this.buttonList.get(i), false); 
    if (id < this.screenButtonList.size()) {
      List<GuiButton> list = this.screenButtonList.get(id);
      for (GuiButton b : list)
        W_GuiButton.setVisible(b, true); 
    } 
    if (getScreenId() == 0 && this.current != null && this.current.getDescMaxPage() > 1) {
      W_GuiButton.setVisible(this.buttonNextPage, true);
      W_GuiButton.setVisible(this.buttonPrevPage, true);
    } else {
      W_GuiButton.setVisible(this.buttonNextPage, false);
      W_GuiButton.setVisible(this.buttonPrevPage, false);
    } 
  }
  
  public void setCurrentRecipe(MCH_CurrentRecipe currentRecipe) {
    modelPosX = 0.0F;
    modelPosY = 0.0F;
    if (this.current == null || currentRecipe == null || 
      !this.current.recipe.getRecipeOutput().isItemEqual(currentRecipe.recipe.getRecipeOutput()))
      this.drawFace = 0; 
    this.current = currentRecipe;
    if (getScreenId() == 0 && this.current != null && this.current.getDescMaxPage() > 1) {
      W_GuiButton.setVisible(this.buttonNextPage, true);
      W_GuiButton.setVisible(this.buttonPrevPage, true);
    } else {
      W_GuiButton.setVisible(this.buttonNextPage, false);
      W_GuiButton.setVisible(this.buttonPrevPage, false);
    } 
    updateEnableCreateButton();
  }
  
  public MCH_IRecipeList getCurrentList() {
    return this.currentList;
  }
  
  public void switchRecipeList(MCH_IRecipeList list) {
    if (getCurrentList() != list) {
      setCurrentRecipe(new MCH_CurrentRecipe(list, 0));
      this.currentList = list;
      updateListSliderSize(list.getRecipeListSize());
    } else {
      this.listSlider.setSliderValue((this.current.index / 2));
    } 
  }
  
  public void updateScreen() {
    super.updateScreen();
    if (this.buttonClickWait > 0)
      this.buttonClickWait--; 
  }
  
  public void onGuiClosed() {
    super.onGuiClosed();
    MCH_Lib.DbgLog(this.thePlayer.world, "MCH_DraftingTableGui.onGuiClosed", new Object[0]);
  }
  
  protected void actionPerformed(GuiButton button) throws IOException {
    super.actionPerformed(button);
    if (this.buttonClickWait > 0)
      return; 
    if (!button.enabled)
      return; 
    this.buttonClickWait = 3;
    int index = 0;
    int page = this.current.getDescCurrentPage();
    switch (button.id) {
      case 30:
        MCH_DraftingTableCreatePacket.send(this.current.recipe);
        break;
      case 21:
        if (this.current.isCurrentPageTexture())
          page = 0; 
        index = this.current.index - 1;
        if (index < 0)
          index = getCurrentList().getRecipeListSize() - 1; 
        setCurrentRecipe(new MCH_CurrentRecipe(getCurrentList(), index));
        this.current.setDescCurrentPage(page);
        break;
      case 20:
        if (this.current.isCurrentPageTexture())
          page = 0; 
        index = (this.current.index + 1) % getCurrentList().getRecipeListSize();
        setCurrentRecipe(new MCH_CurrentRecipe(getCurrentList(), index));
        this.current.setDescCurrentPage(page);
        break;
      case 10:
        initModelTransform();
        modelRotX = 180.0F;
        modelRotY = 90.0F;
        switchRecipeList((MCH_IRecipeList)MCH_HeliInfoManager.getInstance());
        switchScreen(1);
        break;
      case 11:
        initModelTransform();
        modelRotX = 90.0F;
        modelRotY = 180.0F;
        switchRecipeList((MCH_IRecipeList)MCP_PlaneInfoManager.getInstance());
        switchScreen(1);
        break;
      case 13:
        initModelTransform();
        modelRotX = 180.0F;
        modelRotY = 90.0F;
        switchRecipeList((MCH_IRecipeList)MCH_TankInfoManager.getInstance());
        switchScreen(1);
        break;
      case 12:
        initModelTransform();
        modelRotX = 180.0F;
        modelRotY = 90.0F;
        switchRecipeList((MCH_IRecipeList)MCH_VehicleInfoManager.getInstance());
        switchScreen(1);
        break;
      case 14:
        switchRecipeList((MCH_IRecipeList)MCH_ItemRecipe.getInstance());
        switchScreen(1);
        break;
      case 50:
        if (this.current != null)
          this.current.switchNextPage(); 
        break;
      case 51:
        if (this.current != null)
          this.current.switchPrevPage(); 
        break;
      case 40:
      case 41:
      case 42:
      case 43:
      case 44:
      case 45:
        index = (int)this.listSlider.getSliderValue() * 2 + button.id - 40;
        if (index < getCurrentList().getRecipeListSize()) {
          setCurrentRecipe(new MCH_CurrentRecipe(getCurrentList(), index));
          switchScreen(0);
        } 
        break;
    } 
  }
  
  private void updateEnableCreateButton() {
    MCH_DraftingTableGuiContainer container = (MCH_DraftingTableGuiContainer)this.inventorySlots;
    this.buttonCreate.enabled = false;
    if (!container.getSlot(container.outputSlotIndex).getHasStack())
      this.buttonCreate.enabled = MCH_Recipes.canCraft(this.thePlayer, this.current.recipe); 
    if (this.thePlayer.capabilities.isCreativeMode)
      this.buttonCreate.enabled = true; 
  }
  
  protected void keyTyped(char par1, int keycode) throws IOException {
    if (keycode == 1 || keycode == W_KeyBinding.getKeyCode((Minecraft.getMinecraft()).gameSettings.keyBindInventory))
      if (getScreenId() == 0) {
        this.mc.player.closeScreen();
      } else {
        switchScreen(0);
      }  
    if (getScreenId() == 0) {
      if (keycode == 205)
        actionPerformed(this.buttonNext); 
      if (keycode == 203)
        actionPerformed(this.buttonPrev); 
    } else if (getScreenId() == 1) {
      if (keycode == 200)
        this.listSlider.scrollDown(1.0F); 
      if (keycode == 208)
        this.listSlider.scrollUp(1.0F); 
    } 
  }
  
  protected void drawGuiContainerForegroundLayer(int mx, int my) {
    super.drawGuiContainerForegroundLayer(mx, my);
    this.zLevel = 0.0F;
    GlStateManager.enableBlend();
    if (getScreenId() == 0) {
      ArrayList<String> list = new ArrayList<>();
      if (this.current != null)
        if (this.current.isCurrentPageTexture()) {
          GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
          this.mc.getTextureManager().bindTexture(this.current.getCurrentPageTexture());
          drawTexturedModalRect(210, 20, 170, 190, 0, 0, 340, 380);
        } else if (this.current.isCurrentPageAcInfo()) {
          for (int i = 0; i < this.current.infoItem.size(); i++) {
            this.fontRenderer.drawString(this.current.infoItem.get(i), 210, 40 + 10 * i, -9491968);
            String data = this.current.infoData.get(i);
            if (!data.isEmpty())
              this.fontRenderer.drawString(data, 280, 40 + 10 * i, -9491968); 
          } 
        } else {
          W_McClient.MOD_bindTexture("textures/gui/drafting_table.png");
          drawTexturedModalRect(340, 215, 45, 15, 400, 60, 90, 30);
          if (mx >= 350 && mx <= 400 && my >= 214 && my <= 230) {
            boolean lb = Mouse.isButtonDown(0);
            boolean rb = Mouse.isButtonDown(1);
            boolean mb = Mouse.isButtonDown(2);
            list.add((lb ? TextFormatting.AQUA + "Mouse left button drag : Rotation model" : "Mouse left button drag : Rotation model"));
            list.add((rb ? TextFormatting.AQUA + "Mouse right button drag : Zoom model" : "Mouse right button drag : Zoom model"));
            list.add((mb ? TextFormatting.AQUA + "Mouse middle button drag : Move model" : "Mouse middle button drag : Move model"));
          }
        }  
      drawString(this.current.displayName, 120, 20, -1);
      drawItemRecipe(this.current.recipe, 121, 34);
      if (list.size() > 0)
        drawHoveringText(list, mx - 30, my - 0, this.fontRenderer); 
    } 
    if (getScreenId() == 1) {
      int index = 2 * (int)this.listSlider.getSliderValue();
      int i = 0;
      int r;
      for (r = 0; r < 3; r++) {
        for (int c = 0; c < 2; c++) {
          if (index + i < getCurrentList().getRecipeListSize()) {
            int rx = 110 + 140 * c;
            int ry = 20 + 70 * r;
            String s = getCurrentList().getRecipe(index + i).getRecipeOutput().getDisplayName();
            drawCenteredString(s, rx, ry, -1);
          } 
          i++;
        } 
      } 
      W_McClient.MOD_bindTexture("textures/gui/drafting_table.png");
      i = 0;
      for (r = 0; r < 3; r++) {
        for (int c = 0; c < 2; c++) {
          if (index + i < getCurrentList().getRecipeListSize()) {
            int rx = 80 + 140 * c - 1;
            int ry = 30 + 70 * r - 1;
            drawTexturedModalRect(rx, ry, 400, 0, 75, 54);
          } 
          i++;
        } 
      } 
      i = 0;
      for (r = 0; r < 3; r++) {
        for (int c = 0; c < 2; c++) {
          if (index + i < getCurrentList().getRecipeListSize()) {
            int rx = 80 + 140 * c;
            int ry = 30 + 70 * r;
            drawItemRecipe(getCurrentList().getRecipe(index + i), rx, ry);
          } 
          i++;
        } 
      } 
    } 
  }
  
  protected void handleMouseClick(Slot slotIn, int slotId, int clickedButton, ClickType clickType) {
    if (getScreenId() != 1)
      super.handleMouseClick(slotIn, slotId, clickedButton, clickType); 
  }
  
  private int getScreenId() {
    return this.screenId;
  }
  
  public void drawItemRecipe(IRecipe recipe, int x, int y) {
    if (recipe == null)
      return; 
    if (recipe.getRecipeOutput().isEmpty())
      return; 
    if (recipe.getRecipeOutput().getItem() == null)
      return; 
    RenderHelper.enableGUIStandardItemLighting();
    NonNullList<Ingredient> ingredients = recipe.getIngredients();
    for (int i = 0; i < ingredients.size(); i++)
      drawIngredient((Ingredient)ingredients.get(i), x + i % 3 * 18, y + i / 3 * 18); 
    drawItemStack(recipe.getRecipeOutput(), x + 54 + 3, y + 18);
    RenderHelper.disableStandardItemLighting();
  }
  
  public void handleMouseInput() throws IOException {
    super.handleMouseInput();
    int dx = Mouse.getEventDX();
    int dy = Mouse.getEventDY();
    if (getScreenId() == 0 && Mouse.getX() > this.mc.displayWidth / 2) {
      if (Mouse.isButtonDown(0) && (dx != 0 || dy != 0)) {
        modelRotX = (float)(modelRotX - dy / 2.0D);
        modelRotY = (float)(modelRotY - dx / 2.0D);
        if (modelRotX > 360.0F)
          modelRotX -= 360.0F; 
        if (modelRotX < -360.0F)
          modelRotX += 360.0F; 
        if (modelRotY > 360.0F)
          modelRotY -= 360.0F; 
        if (modelRotY < -360.0F)
          modelRotY += 360.0F; 
      } 
      if (Mouse.isButtonDown(2) && (dx != 0 || dy != 0)) {
        modelPosX = (float)(modelPosX + dx / 2.0D);
        modelPosY = (float)(modelPosY - dy / 2.0D);
        if (modelRotX > 1000.0F)
          modelRotX = 1000.0F; 
        if (modelRotX < -1000.0F)
          modelRotX = -1000.0F; 
        if (modelRotY > 1000.0F)
          modelRotY = 1000.0F; 
        if (modelRotY < -1000.0F)
          modelRotY = -1000.0F; 
      } 
      if (Mouse.isButtonDown(1) && dy != 0) {
        modelZoom = (float)(modelZoom + dy / 100.0D);
        if (modelZoom < 0.1D)
          modelZoom = 0.1F; 
        if (modelZoom > 10.0F)
          modelZoom = 10.0F; 
      } 
    } 
    int wheel = Mouse.getEventDWheel();
    if (wheel != 0)
      if (getScreenId() == 1) {
        if (wheel > 0) {
          this.listSlider.scrollDown(1.0F);
        } else if (wheel < 0) {
          this.listSlider.scrollUp(1.0F);
        } 
      } else if (getScreenId() == 0) {
        if (wheel > 0) {
          actionPerformed(this.buttonPrev);
        } else if (wheel < 0) {
          actionPerformed(this.buttonNext);
        } 
      }  
  }
  
  public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    GlStateManager.enableBlend();
    GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    if (getScreenId() == 0) {
      super.drawScreen(mouseX, mouseY, partialTicks);
    } else {
      List<Slot> inventory = this.inventorySlots.inventorySlots;
      this.inventorySlots.inventorySlots = new ArrayList();
      super.drawScreen(mouseX, mouseY, partialTicks);
      this.inventorySlots.inventorySlots = inventory;
    } 
    if (getScreenId() == 0 && this.current.isCurrentPageModel()) {
      RenderHelper.enableGUIStandardItemLighting();
      drawModel(partialTicks);
    } 
  }
  
  public void drawModel(float partialTicks) {
    W_ModelCustom model = this.current.getModel();
    double scl = 162.0D / ((MathHelper.abs(model.size) < 0.01D) ? 0.01D : model.size);
    this.mc.getTextureManager().bindTexture(this.current.getModelTexture());
    GlStateManager.pushMatrix();
    double cx = (model.maxX - model.minX) * 0.5D + model.minX;
    double cy = (model.maxY - model.minY) * 0.5D + model.minY;
    double cz = (model.maxZ - model.minZ) * 0.5D + model.minZ;
    if (this.current.modelRot == 0) {
      GlStateManager.translate(cx * scl, cz * scl, 0.0D);
    } else {
      GlStateManager.translate(cz * scl, cy * scl, 0.0D);
    } 
    GlStateManager.translate(((this.guiLeft + 300) + modelPosX), ((this.guiTop + 110) + modelPosY), 550.0D);
    GlStateManager.rotate(modelRotX, 1.0F, 0.0F, 0.0F);
    GlStateManager.rotate(modelRotY, 0.0F, 1.0F, 0.0F);
    GlStateManager.scale(scl * modelZoom, scl * modelZoom, -scl * modelZoom);
    GlStateManager.disableRescaleNormal();
    GlStateManager.disableLighting();
    GlStateManager.enableAlpha();
    GlStateManager.enableBlend();
    int faceNum = model.getFaceNum();
    if (this.drawFace < faceNum * 2) {
      GlStateManager.color(0.1F, 0.1F, 0.1F, 1.0F);
      GlStateManager.disableTexture2D();
      GlUtil.polygonMode(GlStateManager.CullFace.FRONT_AND_BACK, GlUtil.RasterizeType.LINE);
      GlUtil.pushLineWidth(1.0F);
      model.renderAll(this.drawFace - faceNum, this.drawFace);
      MCH_RenderAircraft.renderCrawlerTrack(null, this.current.getAcInfo(), partialTicks);
      GlUtil.popLineWidth();
      GlUtil.polygonMode(GlStateManager.CullFace.FRONT_AND_BACK, GlUtil.RasterizeType.FILL);
      GlStateManager.enableTexture2D();
    } 
    if (this.drawFace >= faceNum) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      model.renderAll(0, this.drawFace - faceNum);
      MCH_RenderAircraft.renderCrawlerTrack(null, this.current.getAcInfo(), partialTicks);
    } 
    GlStateManager.enableRescaleNormal();
    GlStateManager.enableLighting();
    GlStateManager.popMatrix();
    if (this.drawFace < 10000000)
      this.drawFace = (int)(this.drawFace + 20.0F); 
  }
  
  protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    float z = this.zLevel;
    this.zLevel = 0.0F;
    W_McClient.MOD_bindTexture("textures/gui/drafting_table.png");
    if (getScreenId() == 0)
      drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize); 
    if (getScreenId() == 1) {
      drawTexturedModalRect(this.guiLeft, this.guiTop, 0, this.ySize, this.xSize, this.ySize);
      List<GuiButton> list = this.screenButtonList.get(1);
      int index = (int)this.listSlider.getSliderValue() * 2;
      for (int i = 0; i < 6; i++)
        W_GuiButton.setVisible(list.get(i), (index + i < getCurrentList().getRecipeListSize())); 
    } 
    this.zLevel = z;
  }
  
  public void drawTexturedModalRect(int par1, int par2, int par3, int par4, int par5, int par6) {
    float w = 0.001953125F;
    float h = 0.001953125F;
    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder buffer = tessellator.getBuffer();
    buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
    buffer.pos((par1 + 0), (par2 + par6), this.zLevel).tex(((par3 + 0) * w), ((par4 + par6) * h)).endVertex();
    buffer.pos((par1 + par5), (par2 + par6), this.zLevel).tex(((par3 + par5) * w), ((par4 + par6) * h)).endVertex();
    buffer.pos((par1 + par5), (par2 + 0), this.zLevel).tex(((par3 + par5) * w), ((par4 + 0) * h)).endVertex();
    buffer.pos((par1 + 0), (par2 + 0), this.zLevel).tex(((par3 + 0) * w), ((par4 + 0) * h)).endVertex();
    tessellator.draw();
  }
  
  public void drawTexturedModalRect(int dx, int dy, int dw, int dh, int u, int v, int tw, int th) {
    float w = 0.001953125F;
    float h = 0.001953125F;
    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder buffer = tessellator.getBuffer();
    buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
    buffer.pos((dx + 0), (dy + dh), this.zLevel).tex(((u + 0) * w), ((v + th) * h)).endVertex();
    buffer.pos((dx + dw), (dy + dh), this.zLevel).tex(((u + tw) * w), ((v + th) * h)).endVertex();
    buffer.pos((dx + dw), (dy + 0), this.zLevel).tex(((u + tw) * w), ((v + 0) * h)).endVertex();
    buffer.pos((dx + 0), (dy + 0), this.zLevel).tex(((u + 0) * w), ((v + 0) * h)).endVertex();
    tessellator.draw();
  }
  
  public void drawTexturedModalRectWithColor(int x, int y, int width, int height, int u, int v, int uWidth, int vHeight, int color) {
    float w = 0.001953125F;
    float h = 0.001953125F;
    float f = (color >> 16 & 0xFF) / 255.0F;
    float f1 = (color >> 8 & 0xFF) / 255.0F;
    float f2 = (color & 0xFF) / 255.0F;
    float f3 = (color >> 24 & 0xFF) / 255.0F;
    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder buf = tessellator.getBuffer();
    buf.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
    buf.pos(x, (y + height), this.zLevel).tex((u * w), ((v + vHeight) * h)).color(f, f1, f2, f3).endVertex();
    buf.pos((x + width), (y + height), this.zLevel).tex(((u + uWidth) * w), ((v + vHeight) * h)).color(f, f1, f2, f3)
      .endVertex();
    buf.pos((x + width), y, this.zLevel).tex(((u + uWidth) * w), (v * h)).color(f, f1, f2, f3).endVertex();
    buf.pos(x, y, this.zLevel).tex((u * w), (v * h)).color(f, f1, f2, f3).endVertex();
    tessellator.draw();
  }
}
