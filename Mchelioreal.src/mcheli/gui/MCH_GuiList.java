/*     */ package mcheli.gui;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import mcheli.wrapper.W_GuiButton;
/*     */ import net.minecraft.client.Minecraft;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_GuiList
/*     */   extends W_GuiButton
/*     */ {
/*     */   public List<MCH_GuiListItem> listItems;
/*     */   public MCH_GuiSliderVertical scrollBar;
/*     */   public final int maxRowNum;
/*     */   public MCH_GuiListItem lastPushItem;
/*     */   
/*     */   public MCH_GuiList(int id, int maxRow, int posX, int posY, int w, int h, String name) {
/*  25 */     super(id, posX, posY, w, h, "");
/*  26 */     this.maxRowNum = (maxRow > 0) ? maxRow : 1;
/*  27 */     this.listItems = new ArrayList<>();
/*  28 */     this.scrollBar = new MCH_GuiSliderVertical(0, posX + w - 20, posY, 20, h, name, 0.0F, 0.0F, 0.0F, 1.0F);
/*  29 */     this.lastPushItem = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_191745_a(Minecraft mc, int x, int y, float partialTicks) {
/*  36 */     if (isVisible()) {
/*     */       
/*  38 */       func_73734_a(this.field_146128_h, this.field_146129_i, this.field_146128_h + this.field_146120_f, this.field_146129_i + this.field_146121_g, -2143272896);
/*     */ 
/*     */       
/*  41 */       this.scrollBar.func_191745_a(mc, x, y, partialTicks);
/*     */       
/*  43 */       for (int i = 0; i < this.maxRowNum; i++) {
/*     */         
/*  45 */         if (i + getStartRow() >= this.listItems.size())
/*     */           break; 
/*  47 */         MCH_GuiListItem item = this.listItems.get(i + getStartRow());
/*     */ 
/*     */         
/*  50 */         item.draw(mc, x, y, this.field_146128_h, this.field_146129_i + 5 + 20 * i, partialTicks);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addItem(MCH_GuiListItem item) {
/*  57 */     this.listItems.add(item);
/*  58 */     int listNum = this.listItems.size();
/*  59 */     this.scrollBar.valueMax = (listNum > this.maxRowNum) ? (listNum - this.maxRowNum) : 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public MCH_GuiListItem getItem(int i) {
/*  64 */     return (i < getItemNum()) ? this.listItems.get(i) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getItemNum() {
/*  69 */     return this.listItems.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public void scrollUp(float a) {
/*  74 */     if (isVisible())
/*     */     {
/*  76 */       this.scrollBar.scrollUp(a);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void scrollDown(float a) {
/*  82 */     if (isVisible())
/*     */     {
/*  84 */       this.scrollBar.scrollDown(a);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getStartRow() {
/*  90 */     int startRow = (int)this.scrollBar.getSliderValue();
/*  91 */     return (startRow >= 0) ? startRow : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_146119_b(Minecraft mc, int x, int y) {
/*  97 */     if (isVisible())
/*     */     {
/*  99 */       this.scrollBar.func_146119_b(mc, x, y);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_146116_c(Minecraft mc, int x, int y) {
/* 106 */     boolean b = false;
/* 107 */     if (isVisible()) {
/*     */       
/* 109 */       b |= this.scrollBar.func_146116_c(mc, x, y);
/*     */       
/* 111 */       for (int i = 0; i < this.maxRowNum; i++) {
/*     */         
/* 113 */         if (i + getStartRow() >= this.listItems.size())
/*     */           break; 
/* 115 */         MCH_GuiListItem item = this.listItems.get(i + getStartRow());
/*     */         
/* 117 */         if (item.mousePressed(mc, x, y)) {
/*     */           
/* 119 */           this.lastPushItem = item;
/* 120 */           b = true;
/*     */         } 
/*     */       } 
/*     */     } 
/* 124 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_146118_a(int x, int y) {
/* 130 */     if (isVisible()) {
/*     */       
/* 132 */       this.scrollBar.func_146118_a(x, y);
/*     */ 
/*     */       
/* 135 */       for (MCH_GuiListItem item : this.listItems) item.mouseReleased(x, y); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\gui\MCH_GuiList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */