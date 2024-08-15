/*     */ package mcheli.command;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import mcheli.gui.MCH_Gui;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.ChatLine;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class MCH_GuiTitle
/*     */   extends MCH_Gui
/*     */ {
/*  31 */   private final List<ChatLine> chatLines = new ArrayList<>();
/*  32 */   private int prevPlayerTick = 0;
/*  33 */   private int restShowTick = 0;
/*  34 */   private int showTick = 0;
/*  35 */   private float colorAlpha = 0.0F;
/*  36 */   private int position = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MCH_GuiTitle(Minecraft minecraft) {
/*  42 */     super(minecraft);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73866_w_() {
/*  49 */     super.func_73866_w_();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_73868_f() {
/*  55 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDrawGui(EntityPlayer player) {
/*  61 */     if (this.restShowTick > 0 && this.chatLines.size() > 0)
/*     */     {
/*  63 */       if (player != null && player.field_70170_p != null) {
/*     */         
/*  65 */         if (this.prevPlayerTick != player.field_70173_aa) {
/*     */           
/*  67 */           this.showTick++;
/*  68 */           this.restShowTick--;
/*     */         } 
/*  70 */         this.prevPlayerTick = player.field_70173_aa;
/*     */       } 
/*     */     }
/*  73 */     return (this.restShowTick > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawGui(EntityPlayer player, boolean isThirdPersonView) {
/*  79 */     GL11.glLineWidth((scaleFactor * 2));
/*  80 */     GL11.glDisable(3042);
/*  81 */     if (scaleFactor <= 0)
/*     */     {
/*  83 */       scaleFactor = 1;
/*     */     }
/*  85 */     this.colorAlpha = 1.0F;
/*  86 */     if (this.restShowTick > 20 && this.showTick < 5)
/*     */     {
/*  88 */       this.colorAlpha = 0.2F * this.showTick;
/*     */     }
/*  90 */     if (this.showTick > 0 && this.restShowTick < 5)
/*     */     {
/*  92 */       this.colorAlpha = 0.2F * this.restShowTick;
/*     */     }
/*     */     
/*  95 */     drawChat();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String formatColors(String s) {
/* 101 */     return (Minecraft.func_71410_x()).field_71474_y.field_74344_o ? s : TextFormatting.func_110646_a(s);
/*     */   }
/*     */ 
/*     */   
/*     */   private int calculateChatboxWidth() {
/* 106 */     short short1 = 320;
/* 107 */     byte b0 = 40;
/* 108 */     return MathHelper.func_76141_d(this.field_146297_k.field_71474_y.field_96692_F * (short1 - b0) + b0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setupTitle(ITextComponent chatComponent, int showTime, int pos) {
/* 114 */     int displayTime = 20;
/* 115 */     int line = 0;
/* 116 */     this.chatLines.clear();
/*     */     
/* 118 */     this.position = pos;
/* 119 */     this.showTick = 0;
/* 120 */     this.restShowTick = showTime;
/*     */     
/* 122 */     int k = MathHelper.func_76141_d(calculateChatboxWidth() / this.field_146297_k.field_71474_y.field_96691_E);
/* 123 */     int l = 0;
/*     */     
/* 125 */     TextComponentString chatcomponenttext = new TextComponentString("");
/* 126 */     ArrayList<ITextComponent> arraylist = Lists.newArrayList();
/* 127 */     ArrayList<ITextComponent> arraylist1 = Lists.newArrayList((Iterable)chatComponent);
/*     */     
/* 129 */     for (int i1 = 0; i1 < arraylist1.size(); i1++) {
/*     */ 
/*     */       
/* 132 */       ITextComponent ichatcomponent1 = arraylist1.get(i1);
/*     */       
/* 134 */       String[] splitLine = (ichatcomponent1.func_150261_e() + "").split("\n");
/* 135 */       int lineCnt = 0;
/* 136 */       for (String sLine : splitLine) {
/*     */         
/* 138 */         String s = formatColors(ichatcomponent1.func_150256_b().func_150218_j() + sLine);
/* 139 */         int j1 = this.field_146297_k.field_71466_p.func_78256_a(s);
/*     */         
/* 141 */         TextComponentString chatcomponenttext1 = new TextComponentString(s);
/* 142 */         chatcomponenttext1.func_150255_a(ichatcomponent1.func_150256_b().func_150232_l());
/* 143 */         boolean flag1 = false;
/*     */         
/* 145 */         if (l + j1 > k) {
/*     */           
/* 147 */           String s1 = this.field_146297_k.field_71466_p.func_78262_a(s, k - l, false);
/* 148 */           String s2 = (s1.length() < s.length()) ? s.substring(s1.length()) : null;
/*     */           
/* 150 */           if (s2 != null && s2.length() > 0) {
/*     */             
/* 152 */             int k1 = s1.lastIndexOf(" ");
/*     */             
/* 154 */             if (k1 >= 0 && this.field_146297_k.field_71466_p.func_78256_a(s.substring(0, k1)) > 0) {
/*     */               
/* 156 */               s1 = s.substring(0, k1);
/* 157 */               s2 = s.substring(k1);
/*     */             } 
/*     */ 
/*     */             
/* 161 */             TextComponentString chatcomponenttext2 = new TextComponentString(s2);
/* 162 */             chatcomponenttext2.func_150255_a(ichatcomponent1.func_150256_b().func_150232_l());
/* 163 */             arraylist1.add(i1 + 1, chatcomponenttext2);
/*     */           } 
/*     */           
/* 166 */           j1 = this.field_146297_k.field_71466_p.func_78256_a(s1);
/*     */ 
/*     */           
/* 169 */           chatcomponenttext1 = new TextComponentString(s1);
/* 170 */           chatcomponenttext1.func_150255_a(ichatcomponent1.func_150256_b().func_150232_l());
/* 171 */           flag1 = true;
/*     */         } 
/*     */         
/* 174 */         if (l + j1 <= k) {
/*     */           
/* 176 */           l += j1;
/* 177 */           chatcomponenttext.func_150257_a((ITextComponent)chatcomponenttext1);
/*     */         }
/*     */         else {
/*     */           
/* 181 */           flag1 = true;
/*     */         } 
/*     */         
/* 184 */         if (flag1) {
/*     */           
/* 186 */           arraylist.add(chatcomponenttext);
/* 187 */           l = 0;
/*     */           
/* 189 */           chatcomponenttext = new TextComponentString("");
/*     */         } 
/*     */         
/* 192 */         lineCnt++;
/* 193 */         if (lineCnt < splitLine.length) {
/*     */           
/* 195 */           arraylist.add(chatcomponenttext);
/* 196 */           l = 0;
/*     */           
/* 198 */           chatcomponenttext = new TextComponentString("");
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 203 */     arraylist.add(chatcomponenttext);
/*     */     
/* 205 */     Iterator<ITextComponent> iterator = arraylist.iterator();
/*     */ 
/*     */     
/* 208 */     for (; iterator.hasNext(); this.chatLines.add(new ChatLine(displayTime, ichatcomponent2, line)))
/*     */     {
/*     */       
/* 211 */       ITextComponent ichatcomponent2 = iterator.next();
/*     */     }
/*     */     
/* 214 */     while (this.chatLines.size() > 100)
/*     */     {
/* 216 */       this.chatLines.remove(this.chatLines.size() - 1);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int calculateChatboxHeight() {
/* 223 */     short short1 = 180;
/* 224 */     byte b0 = 20;
/* 225 */     return MathHelper.func_76141_d(this.field_146297_k.field_71474_y.field_96694_H * (short1 - b0) + b0);
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawChat() {
/* 230 */     float charAlpha = this.field_146297_k.field_71474_y.field_74357_r * 0.9F + 0.1F;
/*     */     
/* 232 */     float scale = this.field_146297_k.field_71474_y.field_96691_E * 2.0F;
/* 233 */     GL11.glPushMatrix();
/* 234 */     float posY = 0.0F;
/* 235 */     switch (this.position) {
/*     */ 
/*     */       
/*     */       default:
/* 239 */         posY = (this.field_146297_k.field_71440_d / 2 / scaleFactor) - this.chatLines.size() / 2.0F * 9.0F * scale;
/*     */         break;
/*     */       case 1:
/* 242 */         posY = 0.0F;
/*     */         break;
/*     */       case 2:
/* 245 */         posY = (this.field_146297_k.field_71440_d / scaleFactor) - this.chatLines.size() * 9.0F * scale;
/*     */         break;
/*     */       case 3:
/* 248 */         posY = (this.field_146297_k.field_71440_d / 3 / scaleFactor) - this.chatLines.size() / 2.0F * 9.0F * scale;
/*     */         break;
/*     */       case 4:
/* 251 */         posY = (this.field_146297_k.field_71440_d * 2 / 3 / scaleFactor) - this.chatLines.size() / 2.0F * 9.0F * scale;
/*     */         break;
/*     */     } 
/* 254 */     GL11.glTranslatef(0.0F, posY, 0.0F);
/* 255 */     GL11.glScalef(scale, scale, 1.0F);
/*     */     
/* 257 */     for (int i = 0; i < this.chatLines.size(); i++) {
/*     */       
/* 259 */       ChatLine chatline = this.chatLines.get(i);
/*     */       
/* 261 */       if (chatline != null) {
/*     */         
/* 263 */         int alpha = (int)(255.0F * charAlpha * this.colorAlpha);
/*     */         
/* 265 */         int y = i * 9;
/* 266 */         func_73734_a(0, y + 9, this.field_146297_k.field_71443_c, y, alpha / 2 << 24);
/* 267 */         GL11.glEnable(3042);
/* 268 */         String s = chatline.func_151461_a().func_150254_d();
/* 269 */         int sw = this.field_146297_k.field_71443_c / 2 / scaleFactor - this.field_146297_k.field_71466_p.func_78256_a(s);
/* 270 */         sw = (int)(sw / scale);
/* 271 */         this.field_146297_k.field_71466_p.func_175063_a(s, sw, (y + 1), 16777215 + (alpha << 24));
/* 272 */         GL11.glDisable(3008);
/*     */       } 
/*     */     } 
/*     */     
/* 276 */     GL11.glTranslatef(-3.0F, 0.0F, 0.0F);
/*     */     
/* 278 */     GL11.glPopMatrix();
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\command\MCH_GuiTitle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */