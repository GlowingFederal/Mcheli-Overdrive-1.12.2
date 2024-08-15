/*      */ package mcheli.block;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import mcheli.MCH_IRecipeList;
/*      */ import mcheli.MCH_ItemRecipe;
/*      */ import mcheli.MCH_Lib;
/*      */ import mcheli.__helper.MCH_Recipes;
/*      */ import mcheli.__helper.client.renderer.GlUtil;
/*      */ import mcheli.aircraft.MCH_RenderAircraft;
/*      */ import mcheli.gui.MCH_GuiSliderVertical;
/*      */ import mcheli.helicopter.MCH_HeliInfoManager;
/*      */ import mcheli.plane.MCP_PlaneInfoManager;
/*      */ import mcheli.tank.MCH_TankInfoManager;
/*      */ import mcheli.vehicle.MCH_VehicleInfoManager;
/*      */ import mcheli.wrapper.W_GuiButton;
/*      */ import mcheli.wrapper.W_GuiContainer;
/*      */ import mcheli.wrapper.W_KeyBinding;
/*      */ import mcheli.wrapper.W_McClient;
/*      */ import mcheli.wrapper.modelloader.W_ModelCustom;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.gui.GuiButton;
/*      */ import net.minecraft.client.renderer.BufferBuilder;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.RenderHelper;
/*      */ import net.minecraft.client.renderer.Tessellator;
/*      */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.inventory.ClickType;
/*      */ import net.minecraft.inventory.Slot;
/*      */ import net.minecraft.item.crafting.IRecipe;
/*      */ import net.minecraft.item.crafting.Ingredient;
/*      */ import net.minecraft.util.NonNullList;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import net.minecraft.util.text.TextFormatting;
/*      */ import org.lwjgl.input.Mouse;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class MCH_DraftingTableGui
/*      */   extends W_GuiContainer
/*      */ {
/*      */   private final EntityPlayer thePlayer;
/*      */   private MCH_GuiSliderVertical listSlider;
/*      */   private GuiButton buttonCreate;
/*      */   private GuiButton buttonNext;
/*      */   private GuiButton buttonPrev;
/*      */   private GuiButton buttonNextPage;
/*      */   private GuiButton buttonPrevPage;
/*      */   private int drawFace;
/*      */   private int buttonClickWait;
/*      */   public static final int RECIPE_HELI = 0;
/*      */   public static final int RECIPE_PLANE = 1;
/*      */   public static final int RECIPE_VEHICLE = 2;
/*      */   public static final int RECIPE_TANK = 3;
/*      */   public static final int RECIPE_ITEM = 4;
/*      */   public MCH_IRecipeList currentList;
/*      */   public MCH_CurrentRecipe current;
/*      */   public static final int BUTTON_HELI = 10;
/*      */   public static final int BUTTON_PLANE = 11;
/*      */   public static final int BUTTON_VEHICLE = 12;
/*      */   public static final int BUTTON_TANK = 13;
/*      */   public static final int BUTTON_ITEM = 14;
/*      */   public static final int BUTTON_NEXT = 20;
/*      */   public static final int BUTTON_PREV = 21;
/*      */   public static final int BUTTON_CREATE = 30;
/*      */   public static final int BUTTON_SELECT = 40;
/*      */   public static final int BUTTON_NEXT_PAGE = 50;
/*      */   public static final int BUTTON_PREV_PAGE = 51;
/*      */   public List<List<GuiButton>> screenButtonList;
/*   79 */   public int screenId = 0;
/*      */   
/*      */   public static final int SCREEN_MAIN = 0;
/*      */   public static final int SCREEN_LIST = 1;
/*   83 */   public static float modelZoom = 1.0F;
/*   84 */   public static float modelRotX = 0.0F;
/*   85 */   public static float modelRotY = 0.0F;
/*   86 */   public static float modelPosX = 0.0F;
/*   87 */   public static float modelPosY = 0.0F;
/*      */ 
/*      */   
/*      */   public MCH_DraftingTableGui(EntityPlayer player, int posX, int posY, int posZ) {
/*   91 */     super(new MCH_DraftingTableGuiContainer(player, posX, posY, posZ));
/*      */     
/*   93 */     this.thePlayer = player;
/*   94 */     this.field_146999_f = 400;
/*   95 */     this.field_147000_g = 240;
/*   96 */     this.screenButtonList = new ArrayList<>();
/*   97 */     this.drawFace = 0;
/*   98 */     this.buttonClickWait = 0;
/*      */     
/*  100 */     MCH_Lib.DbgLog(player.field_70170_p, "MCH_DraftingTableGui.MCH_DraftingTableGui", new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void func_73866_w_() {
/*  106 */     super.func_73866_w_();
/*      */     
/*  108 */     this.field_146292_n.clear();
/*  109 */     this.screenButtonList.clear();
/*  110 */     this.screenButtonList.add(new ArrayList<>());
/*  111 */     this.screenButtonList.add(new ArrayList<>());
/*      */ 
/*      */ 
/*      */     
/*  115 */     List<GuiButton> list = this.screenButtonList.get(0);
/*      */     
/*  117 */     GuiButton btnHeli = new GuiButton(10, this.field_147003_i + 20, this.field_147009_r + 20, 90, 20, "Helicopter List");
/*  118 */     GuiButton btnPlane = new GuiButton(11, this.field_147003_i + 20, this.field_147009_r + 40, 90, 20, "Plane List");
/*  119 */     GuiButton btnVehicle = new GuiButton(12, this.field_147003_i + 20, this.field_147009_r + 60, 90, 20, "Vehicle List");
/*  120 */     GuiButton btnTank = new GuiButton(13, this.field_147003_i + 20, this.field_147009_r + 80, 90, 20, "Tank List");
/*  121 */     GuiButton btnItem = new GuiButton(14, this.field_147003_i + 20, this.field_147009_r + 100, 90, 20, "Item List");
/*  122 */     btnHeli.field_146124_l = (MCH_HeliInfoManager.getInstance().getRecipeListSize() > 0);
/*  123 */     btnPlane.field_146124_l = (MCP_PlaneInfoManager.getInstance().getRecipeListSize() > 0);
/*  124 */     btnVehicle.field_146124_l = (MCH_VehicleInfoManager.getInstance().getRecipeListSize() > 0);
/*  125 */     btnTank.field_146124_l = (MCH_TankInfoManager.getInstance().getRecipeListSize() > 0);
/*  126 */     btnItem.field_146124_l = (MCH_ItemRecipe.getInstance().getRecipeListSize() > 0);
/*  127 */     list.add(btnHeli);
/*  128 */     list.add(btnPlane);
/*  129 */     list.add(btnVehicle);
/*  130 */     list.add(btnTank);
/*  131 */     list.add(btnItem);
/*      */     
/*  133 */     this.buttonCreate = new GuiButton(30, this.field_147003_i + 120, this.field_147009_r + 89, 50, 20, "Create");
/*  134 */     this.buttonPrev = new GuiButton(21, this.field_147003_i + 120, this.field_147009_r + 111, 36, 20, "<<");
/*  135 */     this.buttonNext = new GuiButton(20, this.field_147003_i + 155, this.field_147009_r + 111, 35, 20, ">>");
/*  136 */     list.add(this.buttonCreate);
/*  137 */     list.add(this.buttonPrev);
/*  138 */     list.add(this.buttonNext);
/*      */     
/*  140 */     this.buttonPrevPage = new GuiButton(51, this.field_147003_i + 210, this.field_147009_r + 210, 60, 20, "Prev Page");
/*  141 */     this.buttonNextPage = new GuiButton(50, this.field_147003_i + 270, this.field_147009_r + 210, 60, 20, "Next Page");
/*  142 */     list.add(this.buttonPrevPage);
/*  143 */     list.add(this.buttonNextPage);
/*      */     
/*  145 */     list = this.screenButtonList.get(1);
/*      */     
/*  147 */     int y = 0; int i;
/*  148 */     for (i = 0; y < 3; y++) {
/*      */       
/*  150 */       for (int x = 0; x < 2; i++) {
/*      */         
/*  152 */         int px = this.field_147003_i + 30 + x * 140;
/*  153 */         int py = this.field_147009_r + 40 + y * 70;
/*  154 */         list.add(new GuiButton(40 + i, px, py, 45, 20, "Select"));
/*  155 */         x++;
/*      */       } 
/*      */     } 
/*      */     
/*  159 */     this.listSlider = new MCH_GuiSliderVertical(0, this.field_147003_i + 360, this.field_147009_r + 20, 20, 200, "", 0.0F, 0.0F, 0.0F, 1.0F);
/*      */     
/*  161 */     list.add(this.listSlider);
/*      */     
/*  163 */     for (i = 0; i < this.screenButtonList.size(); i++) {
/*      */       
/*  165 */       list = this.screenButtonList.get(i);
/*  166 */       for (int j = 0; j < list.size(); j++)
/*      */       {
/*  168 */         this.field_146292_n.add(list.get(j));
/*      */       }
/*      */     } 
/*      */     
/*  172 */     switchScreen(0);
/*  173 */     initModelTransform();
/*  174 */     modelRotX = 180.0F;
/*  175 */     modelRotY = 90.0F;
/*      */     
/*  177 */     if (MCH_ItemRecipe.getInstance().getRecipeListSize() > 0) {
/*      */       
/*  179 */       switchRecipeList((MCH_IRecipeList)MCH_ItemRecipe.getInstance());
/*      */     }
/*  181 */     else if (MCH_HeliInfoManager.getInstance().getRecipeListSize() > 0) {
/*      */       
/*  183 */       switchRecipeList((MCH_IRecipeList)MCH_HeliInfoManager.getInstance());
/*      */     }
/*  185 */     else if (MCP_PlaneInfoManager.getInstance().getRecipeListSize() > 0) {
/*      */       
/*  187 */       switchRecipeList((MCH_IRecipeList)MCP_PlaneInfoManager.getInstance());
/*      */     }
/*  189 */     else if (MCH_VehicleInfoManager.getInstance().getRecipeListSize() > 0) {
/*      */       
/*  191 */       switchRecipeList((MCH_IRecipeList)MCH_VehicleInfoManager.getInstance());
/*      */     }
/*  193 */     else if (MCH_TankInfoManager.getInstance().getRecipeListSize() > 0) {
/*      */       
/*  195 */       switchRecipeList((MCH_IRecipeList)MCH_TankInfoManager.getInstance());
/*      */     }
/*      */     else {
/*      */       
/*  199 */       switchRecipeList((MCH_IRecipeList)MCH_ItemRecipe.getInstance());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void initModelTransform() {
/*  205 */     modelRotX = 0.0F;
/*  206 */     modelRotY = 0.0F;
/*  207 */     modelPosX = 0.0F;
/*  208 */     modelPosY = 0.0F;
/*  209 */     modelZoom = 1.0F;
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateListSliderSize(int listSize) {
/*  214 */     int s = listSize / 2;
/*      */     
/*  216 */     if (listSize % 2 != 0) {
/*  217 */       s++;
/*      */     }
/*  219 */     if (s > 3) {
/*      */       
/*  221 */       this.listSlider.valueMax = (s - 3);
/*      */     }
/*      */     else {
/*      */       
/*  225 */       this.listSlider.valueMax = 0.0F;
/*      */     } 
/*      */     
/*  228 */     this.listSlider.setSliderValue(0.0F);
/*      */   }
/*      */ 
/*      */   
/*      */   public void switchScreen(int id) {
/*  233 */     this.screenId = id;
/*      */     
/*  235 */     for (int i = 0; i < this.field_146292_n.size(); i++)
/*      */     {
/*  237 */       W_GuiButton.setVisible(this.field_146292_n.get(i), false);
/*      */     }
/*      */     
/*  240 */     if (id < this.screenButtonList.size()) {
/*      */       
/*  242 */       List<GuiButton> list = this.screenButtonList.get(id);
/*      */       
/*  244 */       for (GuiButton b : list)
/*      */       {
/*  246 */         W_GuiButton.setVisible(b, true);
/*      */       }
/*      */     } 
/*      */     
/*  250 */     if (getScreenId() == 0 && this.current != null && this.current.getDescMaxPage() > 1) {
/*      */       
/*  252 */       W_GuiButton.setVisible(this.buttonNextPage, true);
/*  253 */       W_GuiButton.setVisible(this.buttonPrevPage, true);
/*      */     }
/*      */     else {
/*      */       
/*  257 */       W_GuiButton.setVisible(this.buttonNextPage, false);
/*  258 */       W_GuiButton.setVisible(this.buttonPrevPage, false);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setCurrentRecipe(MCH_CurrentRecipe currentRecipe) {
/*  264 */     modelPosX = 0.0F;
/*  265 */     modelPosY = 0.0F;
/*      */     
/*  267 */     if (this.current == null || currentRecipe == null || 
/*  268 */       !this.current.recipe.func_77571_b().func_77969_a(currentRecipe.recipe.func_77571_b()))
/*      */     {
/*  270 */       this.drawFace = 0;
/*      */     }
/*      */     
/*  273 */     this.current = currentRecipe;
/*      */     
/*  275 */     if (getScreenId() == 0 && this.current != null && this.current.getDescMaxPage() > 1) {
/*      */       
/*  277 */       W_GuiButton.setVisible(this.buttonNextPage, true);
/*  278 */       W_GuiButton.setVisible(this.buttonPrevPage, true);
/*      */     }
/*      */     else {
/*      */       
/*  282 */       W_GuiButton.setVisible(this.buttonNextPage, false);
/*  283 */       W_GuiButton.setVisible(this.buttonPrevPage, false);
/*      */     } 
/*      */     
/*  286 */     updateEnableCreateButton();
/*      */   }
/*      */ 
/*      */   
/*      */   public MCH_IRecipeList getCurrentList() {
/*  291 */     return this.currentList;
/*      */   }
/*      */ 
/*      */   
/*      */   public void switchRecipeList(MCH_IRecipeList list) {
/*  296 */     if (getCurrentList() != list) {
/*      */       
/*  298 */       setCurrentRecipe(new MCH_CurrentRecipe(list, 0));
/*  299 */       this.currentList = list;
/*  300 */       updateListSliderSize(list.getRecipeListSize());
/*      */     }
/*      */     else {
/*      */       
/*  304 */       this.listSlider.setSliderValue((this.current.index / 2));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void func_73876_c() {
/*  311 */     super.func_73876_c();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  330 */     if (this.buttonClickWait > 0) {
/*  331 */       this.buttonClickWait--;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_146281_b() {
/*  337 */     super.func_146281_b();
/*  338 */     MCH_Lib.DbgLog(this.thePlayer.field_70170_p, "MCH_DraftingTableGui.onGuiClosed", new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void func_146284_a(GuiButton button) throws IOException {
/*  345 */     super.func_146284_a(button);
/*      */     
/*  347 */     if (this.buttonClickWait > 0) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  352 */     if (!button.field_146124_l) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  357 */     this.buttonClickWait = 3;
/*      */     
/*  359 */     int index = 0;
/*  360 */     int page = this.current.getDescCurrentPage();
/*      */     
/*  362 */     switch (button.field_146127_k) {
/*      */       
/*      */       case 30:
/*  365 */         MCH_DraftingTableCreatePacket.send(this.current.recipe);
/*      */         break;
/*      */       case 21:
/*  368 */         if (this.current.isCurrentPageTexture())
/*      */         {
/*  370 */           page = 0;
/*      */         }
/*  372 */         index = this.current.index - 1;
/*      */         
/*  374 */         if (index < 0) {
/*  375 */           index = getCurrentList().getRecipeListSize() - 1;
/*      */         }
/*  377 */         setCurrentRecipe(new MCH_CurrentRecipe(getCurrentList(), index));
/*  378 */         this.current.setDescCurrentPage(page);
/*      */         break;
/*      */       case 20:
/*  381 */         if (this.current.isCurrentPageTexture())
/*      */         {
/*  383 */           page = 0;
/*      */         }
/*  385 */         index = (this.current.index + 1) % getCurrentList().getRecipeListSize();
/*  386 */         setCurrentRecipe(new MCH_CurrentRecipe(getCurrentList(), index));
/*  387 */         this.current.setDescCurrentPage(page);
/*      */         break;
/*      */       case 10:
/*  390 */         initModelTransform();
/*  391 */         modelRotX = 180.0F;
/*  392 */         modelRotY = 90.0F;
/*  393 */         switchRecipeList((MCH_IRecipeList)MCH_HeliInfoManager.getInstance());
/*  394 */         switchScreen(1);
/*      */         break;
/*      */       case 11:
/*  397 */         initModelTransform();
/*  398 */         modelRotX = 90.0F;
/*  399 */         modelRotY = 180.0F;
/*  400 */         switchRecipeList((MCH_IRecipeList)MCP_PlaneInfoManager.getInstance());
/*  401 */         switchScreen(1);
/*      */         break;
/*      */       case 13:
/*  404 */         initModelTransform();
/*  405 */         modelRotX = 180.0F;
/*  406 */         modelRotY = 90.0F;
/*  407 */         switchRecipeList((MCH_IRecipeList)MCH_TankInfoManager.getInstance());
/*  408 */         switchScreen(1);
/*      */         break;
/*      */       case 12:
/*  411 */         initModelTransform();
/*  412 */         modelRotX = 180.0F;
/*  413 */         modelRotY = 90.0F;
/*  414 */         switchRecipeList((MCH_IRecipeList)MCH_VehicleInfoManager.getInstance());
/*  415 */         switchScreen(1);
/*      */         break;
/*      */       case 14:
/*  418 */         switchRecipeList((MCH_IRecipeList)MCH_ItemRecipe.getInstance());
/*  419 */         switchScreen(1);
/*      */         break;
/*      */       case 50:
/*  422 */         if (this.current != null)
/*      */         {
/*  424 */           this.current.switchNextPage();
/*      */         }
/*      */         break;
/*      */       case 51:
/*  428 */         if (this.current != null)
/*      */         {
/*  430 */           this.current.switchPrevPage();
/*      */         }
/*      */         break;
/*      */       case 40:
/*      */       case 41:
/*      */       case 42:
/*      */       case 43:
/*      */       case 44:
/*      */       case 45:
/*  439 */         index = (int)this.listSlider.getSliderValue() * 2 + button.field_146127_k - 40;
/*  440 */         if (index < getCurrentList().getRecipeListSize()) {
/*      */           
/*  442 */           setCurrentRecipe(new MCH_CurrentRecipe(getCurrentList(), index));
/*  443 */           switchScreen(0);
/*      */         } 
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void updateEnableCreateButton() {
/*  451 */     MCH_DraftingTableGuiContainer container = (MCH_DraftingTableGuiContainer)this.field_147002_h;
/*  452 */     this.buttonCreate.field_146124_l = false;
/*      */     
/*  454 */     if (!container.func_75139_a(container.outputSlotIndex).func_75216_d())
/*      */     {
/*  456 */       this.buttonCreate.field_146124_l = MCH_Recipes.canCraft(this.thePlayer, this.current.recipe);
/*      */     }
/*      */     
/*  459 */     if (this.thePlayer.field_71075_bZ.field_75098_d)
/*      */     {
/*  461 */       this.buttonCreate.field_146124_l = true;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void func_73869_a(char par1, int keycode) throws IOException {
/*  469 */     if (keycode == 1 || keycode == W_KeyBinding.getKeyCode((Minecraft.func_71410_x()).field_71474_y.field_151445_Q))
/*      */     {
/*  471 */       if (getScreenId() == 0) {
/*      */         
/*  473 */         this.field_146297_k.field_71439_g.func_71053_j();
/*      */       }
/*      */       else {
/*      */         
/*  477 */         switchScreen(0);
/*      */       } 
/*      */     }
/*      */     
/*  481 */     if (getScreenId() == 0) {
/*      */       
/*  483 */       if (keycode == 205)
/*      */       {
/*  485 */         func_146284_a(this.buttonNext);
/*      */       }
/*      */       
/*  488 */       if (keycode == 203)
/*      */       {
/*  490 */         func_146284_a(this.buttonPrev);
/*      */       }
/*      */     }
/*  493 */     else if (getScreenId() == 1) {
/*      */       
/*  495 */       if (keycode == 200)
/*      */       {
/*  497 */         this.listSlider.scrollDown(1.0F);
/*      */       }
/*      */       
/*  500 */       if (keycode == 208)
/*      */       {
/*  502 */         this.listSlider.scrollUp(1.0F);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void func_146979_b(int mx, int my) {
/*  510 */     super.func_146979_b(mx, my);
/*      */ 
/*      */     
/*  513 */     this.field_73735_i = 0.0F;
/*      */ 
/*      */     
/*  516 */     GlStateManager.func_179147_l();
/*      */     
/*  518 */     if (getScreenId() == 0) {
/*      */       
/*  520 */       ArrayList<String> list = new ArrayList<>();
/*      */       
/*  522 */       if (this.current != null)
/*      */       {
/*  524 */         if (this.current.isCurrentPageTexture()) {
/*      */ 
/*      */           
/*  527 */           GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/*  528 */           this.field_146297_k.func_110434_K().func_110577_a(this.current.getCurrentPageTexture());
/*  529 */           drawTexturedModalRect(210, 20, 170, 190, 0, 0, 340, 380);
/*      */         }
/*  531 */         else if (this.current.isCurrentPageAcInfo()) {
/*      */ 
/*      */           
/*  534 */           for (int i = 0; i < this.current.infoItem.size(); i++)
/*      */           {
/*  536 */             this.field_146289_q.func_78276_b(this.current.infoItem.get(i), 210, 40 + 10 * i, -9491968);
/*      */             
/*  538 */             String data = this.current.infoData.get(i);
/*      */             
/*  540 */             if (!data.isEmpty())
/*      */             {
/*  542 */               this.field_146289_q.func_78276_b(data, 280, 40 + 10 * i, -9491968);
/*      */             }
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/*  548 */           W_McClient.MOD_bindTexture("textures/gui/drafting_table.png");
/*  549 */           drawTexturedModalRect(340, 215, 45, 15, 400, 60, 90, 30);
/*      */           
/*  551 */           if (mx >= 350 && mx <= 400 && my >= 214 && my <= 230) {
/*      */             
/*  553 */             boolean lb = Mouse.isButtonDown(0);
/*  554 */             boolean rb = Mouse.isButtonDown(1);
/*  555 */             boolean mb = Mouse.isButtonDown(2);
/*      */ 
/*      */ 
/*      */             
/*  559 */             list.add((lb ? (String)TextFormatting.AQUA : "") + "Mouse left button drag : Rotation model");
/*  560 */             list.add((rb ? (String)TextFormatting.AQUA : "") + "Mouse right button drag : Zoom model");
/*  561 */             list.add((mb ? (String)TextFormatting.AQUA : "") + "Mouse middle button drag : Move model");
/*      */           } 
/*      */         } 
/*      */       }
/*      */       
/*  566 */       drawString(this.current.displayName, 120, 20, -1);
/*  567 */       drawItemRecipe(this.current.recipe, 121, 34);
/*      */       
/*  569 */       if (list.size() > 0)
/*      */       {
/*  571 */         drawHoveringText(list, mx - 30, my - 0, this.field_146289_q);
/*      */       }
/*      */     } 
/*      */     
/*  575 */     if (getScreenId() == 1) {
/*      */       
/*  577 */       int index = 2 * (int)this.listSlider.getSliderValue();
/*  578 */       int i = 0;
/*      */       int r;
/*  580 */       for (r = 0; r < 3; r++) {
/*      */         
/*  582 */         for (int c = 0; c < 2; c++) {
/*      */           
/*  584 */           if (index + i < getCurrentList().getRecipeListSize()) {
/*      */             
/*  586 */             int rx = 110 + 140 * c;
/*  587 */             int ry = 20 + 70 * r;
/*  588 */             String s = getCurrentList().getRecipe(index + i).func_77571_b().func_82833_r();
/*  589 */             drawCenteredString(s, rx, ry, -1);
/*      */           } 
/*  591 */           i++;
/*      */         } 
/*      */       } 
/*      */       
/*  595 */       W_McClient.MOD_bindTexture("textures/gui/drafting_table.png");
/*      */       
/*  597 */       i = 0;
/*      */       
/*  599 */       for (r = 0; r < 3; r++) {
/*      */         
/*  601 */         for (int c = 0; c < 2; c++) {
/*      */           
/*  603 */           if (index + i < getCurrentList().getRecipeListSize()) {
/*      */             
/*  605 */             int rx = 80 + 140 * c - 1;
/*  606 */             int ry = 30 + 70 * r - 1;
/*      */             
/*  608 */             func_73729_b(rx, ry, 400, 0, 75, 54);
/*      */           } 
/*  610 */           i++;
/*      */         } 
/*      */       } 
/*      */       
/*  614 */       i = 0;
/*      */       
/*  616 */       for (r = 0; r < 3; r++) {
/*      */         
/*  618 */         for (int c = 0; c < 2; c++) {
/*      */           
/*  620 */           if (index + i < getCurrentList().getRecipeListSize()) {
/*      */             
/*  622 */             int rx = 80 + 140 * c;
/*  623 */             int ry = 30 + 70 * r;
/*      */             
/*  625 */             drawItemRecipe(getCurrentList().getRecipe(index + i), rx, ry);
/*      */           } 
/*  627 */           i++;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void func_184098_a(Slot slotIn, int slotId, int clickedButton, ClickType clickType) {
/*  637 */     if (getScreenId() != 1)
/*      */     {
/*  639 */       super.func_184098_a(slotIn, slotId, clickedButton, clickType);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private int getScreenId() {
/*  645 */     return this.screenId;
/*      */   }
/*      */ 
/*      */   
/*      */   public void drawItemRecipe(IRecipe recipe, int x, int y) {
/*  650 */     if (recipe == null) {
/*      */       return;
/*      */     }
/*      */     
/*  654 */     if (recipe.func_77571_b().func_190926_b()) {
/*      */       return;
/*      */     }
/*  657 */     if (recipe.func_77571_b().func_77973_b() == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  662 */     RenderHelper.func_74520_c();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  694 */     NonNullList<Ingredient> ingredients = recipe.func_192400_c();
/*      */     
/*  696 */     for (int i = 0; i < ingredients.size(); i++)
/*      */     {
/*  698 */       drawIngredient((Ingredient)ingredients.get(i), x + i % 3 * 18, y + i / 3 * 18);
/*      */     }
/*      */     
/*  701 */     drawItemStack(recipe.func_77571_b(), x + 54 + 3, y + 18);
/*  702 */     RenderHelper.func_74518_a();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void func_146274_d() throws IOException {
/*  709 */     super.func_146274_d();
/*      */     
/*  711 */     int dx = Mouse.getEventDX();
/*  712 */     int dy = Mouse.getEventDY();
/*      */     
/*  714 */     if (getScreenId() == 0 && Mouse.getX() > this.field_146297_k.field_71443_c / 2) {
/*      */       
/*  716 */       if (Mouse.isButtonDown(0) && (dx != 0 || dy != 0)) {
/*      */         
/*  718 */         modelRotX = (float)(modelRotX - dy / 2.0D);
/*  719 */         modelRotY = (float)(modelRotY - dx / 2.0D);
/*      */         
/*  721 */         if (modelRotX > 360.0F) {
/*  722 */           modelRotX -= 360.0F;
/*      */         }
/*  724 */         if (modelRotX < -360.0F) {
/*  725 */           modelRotX += 360.0F;
/*      */         }
/*  727 */         if (modelRotY > 360.0F) {
/*  728 */           modelRotY -= 360.0F;
/*      */         }
/*  730 */         if (modelRotY < -360.0F) {
/*  731 */           modelRotY += 360.0F;
/*      */         }
/*      */       } 
/*  734 */       if (Mouse.isButtonDown(2) && (dx != 0 || dy != 0)) {
/*      */         
/*  736 */         modelPosX = (float)(modelPosX + dx / 2.0D);
/*  737 */         modelPosY = (float)(modelPosY - dy / 2.0D);
/*      */         
/*  739 */         if (modelRotX > 1000.0F) {
/*  740 */           modelRotX = 1000.0F;
/*      */         }
/*  742 */         if (modelRotX < -1000.0F) {
/*  743 */           modelRotX = -1000.0F;
/*      */         }
/*  745 */         if (modelRotY > 1000.0F) {
/*  746 */           modelRotY = 1000.0F;
/*      */         }
/*  748 */         if (modelRotY < -1000.0F) {
/*  749 */           modelRotY = -1000.0F;
/*      */         }
/*      */       } 
/*  752 */       if (Mouse.isButtonDown(1) && dy != 0) {
/*      */         
/*  754 */         modelZoom = (float)(modelZoom + dy / 100.0D);
/*      */         
/*  756 */         if (modelZoom < 0.1D)
/*      */         {
/*  758 */           modelZoom = 0.1F;
/*      */         }
/*      */         
/*  761 */         if (modelZoom > 10.0F)
/*      */         {
/*  763 */           modelZoom = 10.0F;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  768 */     int wheel = Mouse.getEventDWheel();
/*      */     
/*  770 */     if (wheel != 0)
/*      */     {
/*  772 */       if (getScreenId() == 1) {
/*      */         
/*  774 */         if (wheel > 0)
/*      */         {
/*  776 */           this.listSlider.scrollDown(1.0F);
/*      */         }
/*  778 */         else if (wheel < 0)
/*      */         {
/*  780 */           this.listSlider.scrollUp(1.0F);
/*      */         }
/*      */       
/*  783 */       } else if (getScreenId() == 0) {
/*      */         
/*  785 */         if (wheel > 0) {
/*      */           
/*  787 */           func_146284_a(this.buttonPrev);
/*      */         }
/*  789 */         else if (wheel < 0) {
/*      */           
/*  791 */           func_146284_a(this.buttonNext);
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
/*  803 */     GlStateManager.func_179147_l();
/*  804 */     GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
/*  805 */     GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/*      */     
/*  807 */     if (getScreenId() == 0) {
/*      */       
/*  809 */       super.func_73863_a(mouseX, mouseY, partialTicks);
/*      */     }
/*      */     else {
/*      */       
/*  813 */       List<Slot> inventory = this.field_147002_h.field_75151_b;
/*  814 */       this.field_147002_h.field_75151_b = new ArrayList();
/*      */       
/*  816 */       super.func_73863_a(mouseX, mouseY, partialTicks);
/*      */       
/*  818 */       this.field_147002_h.field_75151_b = inventory;
/*      */     } 
/*      */     
/*  821 */     if (getScreenId() == 0 && this.current.isCurrentPageModel()) {
/*      */       
/*  823 */       RenderHelper.func_74520_c();
/*  824 */       drawModel(partialTicks);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void drawModel(float partialTicks) {
/*  830 */     W_ModelCustom model = this.current.getModel();
/*  831 */     double scl = 162.0D / ((MathHelper.func_76135_e(model.size) < 0.01D) ? 0.01D : model.size);
/*      */     
/*  833 */     this.field_146297_k.func_110434_K().func_110577_a(this.current.getModelTexture());
/*      */ 
/*      */     
/*  836 */     GlStateManager.func_179094_E();
/*      */     
/*  838 */     double cx = (model.maxX - model.minX) * 0.5D + model.minX;
/*  839 */     double cy = (model.maxY - model.minY) * 0.5D + model.minY;
/*  840 */     double cz = (model.maxZ - model.minZ) * 0.5D + model.minZ;
/*      */     
/*  842 */     if (this.current.modelRot == 0) {
/*      */ 
/*      */       
/*  845 */       GlStateManager.func_179137_b(cx * scl, cz * scl, 0.0D);
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  850 */       GlStateManager.func_179137_b(cz * scl, cy * scl, 0.0D);
/*      */     } 
/*      */ 
/*      */     
/*  854 */     GlStateManager.func_179137_b(((this.field_147003_i + 300) + modelPosX), ((this.field_147009_r + 110) + modelPosY), 550.0D);
/*      */ 
/*      */ 
/*      */     
/*  858 */     GlStateManager.func_179114_b(modelRotX, 1.0F, 0.0F, 0.0F);
/*  859 */     GlStateManager.func_179114_b(modelRotY, 0.0F, 1.0F, 0.0F);
/*      */ 
/*      */     
/*  862 */     GlStateManager.func_179139_a(scl * modelZoom, scl * modelZoom, -scl * modelZoom);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  868 */     GlStateManager.func_179101_C();
/*  869 */     GlStateManager.func_179140_f();
/*  870 */     GlStateManager.func_179141_d();
/*  871 */     GlStateManager.func_179147_l();
/*      */     
/*  873 */     int faceNum = model.getFaceNum();
/*      */     
/*  875 */     if (this.drawFace < faceNum * 2) {
/*      */ 
/*      */ 
/*      */       
/*  879 */       GlStateManager.func_179131_c(0.1F, 0.1F, 0.1F, 1.0F);
/*  880 */       GlStateManager.func_179090_x();
/*      */ 
/*      */ 
/*      */       
/*  884 */       GlUtil.polygonMode(GlStateManager.CullFace.FRONT_AND_BACK, GlUtil.RasterizeType.LINE);
/*  885 */       GlUtil.pushLineWidth(1.0F);
/*      */       
/*  887 */       model.renderAll(this.drawFace - faceNum, this.drawFace);
/*      */       
/*  889 */       MCH_RenderAircraft.renderCrawlerTrack(null, this.current.getAcInfo(), partialTicks);
/*      */ 
/*      */       
/*  892 */       GlUtil.popLineWidth();
/*      */ 
/*      */ 
/*      */       
/*  896 */       GlUtil.polygonMode(GlStateManager.CullFace.FRONT_AND_BACK, GlUtil.RasterizeType.FILL);
/*  897 */       GlStateManager.func_179098_w();
/*      */     } 
/*      */     
/*  900 */     if (this.drawFace >= faceNum) {
/*      */ 
/*      */       
/*  903 */       GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/*      */       
/*  905 */       model.renderAll(0, this.drawFace - faceNum);
/*      */       
/*  907 */       MCH_RenderAircraft.renderCrawlerTrack(null, this.current.getAcInfo(), partialTicks);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  913 */     GlStateManager.func_179091_B();
/*  914 */     GlStateManager.func_179145_e();
/*  915 */     GlStateManager.func_179121_F();
/*      */     
/*  917 */     if (this.drawFace < 10000000)
/*      */     {
/*  919 */       this.drawFace = (int)(this.drawFace + 20.0F);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void func_146976_a(float var1, int var2, int var3) {
/*  930 */     GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/*      */     
/*  932 */     float z = this.field_73735_i;
/*  933 */     this.field_73735_i = 0.0F;
/*  934 */     W_McClient.MOD_bindTexture("textures/gui/drafting_table.png");
/*      */     
/*  936 */     if (getScreenId() == 0)
/*      */     {
/*  938 */       func_73729_b(this.field_147003_i, this.field_147009_r, 0, 0, this.field_146999_f, this.field_147000_g);
/*      */     }
/*      */     
/*  941 */     if (getScreenId() == 1) {
/*      */       
/*  943 */       func_73729_b(this.field_147003_i, this.field_147009_r, 0, this.field_147000_g, this.field_146999_f, this.field_147000_g);
/*      */       
/*  945 */       List<GuiButton> list = this.screenButtonList.get(1);
/*  946 */       int index = (int)this.listSlider.getSliderValue() * 2;
/*      */       
/*  948 */       for (int i = 0; i < 6; i++)
/*      */       {
/*  950 */         W_GuiButton.setVisible(list.get(i), (index + i < getCurrentList().getRecipeListSize()));
/*      */       }
/*      */     } 
/*      */     
/*  954 */     this.field_73735_i = z;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void func_73729_b(int par1, int par2, int par3, int par4, int par5, int par6) {
/*  960 */     float w = 0.001953125F;
/*  961 */     float h = 0.001953125F;
/*  962 */     Tessellator tessellator = Tessellator.func_178181_a();
/*  963 */     BufferBuilder buffer = tessellator.func_178180_c();
/*      */     
/*  965 */     buffer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  970 */     buffer.func_181662_b((par1 + 0), (par2 + par6), this.field_73735_i).func_187315_a(((par3 + 0) * w), ((par4 + par6) * h)).func_181675_d();
/*  971 */     buffer.func_181662_b((par1 + par5), (par2 + par6), this.field_73735_i).func_187315_a(((par3 + par5) * w), ((par4 + par6) * h)).func_181675_d();
/*  972 */     buffer.func_181662_b((par1 + par5), (par2 + 0), this.field_73735_i).func_187315_a(((par3 + par5) * w), ((par4 + 0) * h)).func_181675_d();
/*  973 */     buffer.func_181662_b((par1 + 0), (par2 + 0), this.field_73735_i).func_187315_a(((par3 + 0) * w), ((par4 + 0) * h)).func_181675_d();
/*  974 */     tessellator.func_78381_a();
/*      */   }
/*      */ 
/*      */   
/*      */   public void drawTexturedModalRect(int dx, int dy, int dw, int dh, int u, int v, int tw, int th) {
/*  979 */     float w = 0.001953125F;
/*  980 */     float h = 0.001953125F;
/*  981 */     Tessellator tessellator = Tessellator.func_178181_a();
/*  982 */     BufferBuilder buffer = tessellator.func_178180_c();
/*      */     
/*  984 */     buffer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  989 */     buffer.func_181662_b((dx + 0), (dy + dh), this.field_73735_i).func_187315_a(((u + 0) * w), ((v + th) * h)).func_181675_d();
/*  990 */     buffer.func_181662_b((dx + dw), (dy + dh), this.field_73735_i).func_187315_a(((u + tw) * w), ((v + th) * h)).func_181675_d();
/*  991 */     buffer.func_181662_b((dx + dw), (dy + 0), this.field_73735_i).func_187315_a(((u + tw) * w), ((v + 0) * h)).func_181675_d();
/*  992 */     buffer.func_181662_b((dx + 0), (dy + 0), this.field_73735_i).func_187315_a(((u + 0) * w), ((v + 0) * h)).func_181675_d();
/*  993 */     tessellator.func_78381_a();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawTexturedModalRectWithColor(int x, int y, int width, int height, int u, int v, int uWidth, int vHeight, int color) {
/*  999 */     float w = 0.001953125F;
/* 1000 */     float h = 0.001953125F;
/* 1001 */     float f = (color >> 16 & 0xFF) / 255.0F;
/* 1002 */     float f1 = (color >> 8 & 0xFF) / 255.0F;
/* 1003 */     float f2 = (color & 0xFF) / 255.0F;
/* 1004 */     float f3 = (color >> 24 & 0xFF) / 255.0F;
/*      */     
/* 1006 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 1007 */     BufferBuilder buf = tessellator.func_178180_c();
/*      */     
/* 1009 */     buf.func_181668_a(7, DefaultVertexFormats.field_181709_i);
/* 1010 */     buf.func_181662_b(x, (y + height), this.field_73735_i).func_187315_a((u * w), ((v + vHeight) * h)).func_181666_a(f, f1, f2, f3).func_181675_d();
/* 1011 */     buf.func_181662_b((x + width), (y + height), this.field_73735_i).func_187315_a(((u + uWidth) * w), ((v + vHeight) * h)).func_181666_a(f, f1, f2, f3)
/* 1012 */       .func_181675_d();
/* 1013 */     buf.func_181662_b((x + width), y, this.field_73735_i).func_187315_a(((u + uWidth) * w), (v * h)).func_181666_a(f, f1, f2, f3).func_181675_d();
/* 1014 */     buf.func_181662_b(x, y, this.field_73735_i).func_187315_a((u * w), (v * h)).func_181666_a(f, f1, f2, f3).func_181675_d();
/*      */     
/* 1016 */     tessellator.func_78381_a();
/*      */   }
/*      */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\block\MCH_DraftingTableGui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */