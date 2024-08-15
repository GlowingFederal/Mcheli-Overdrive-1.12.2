/*     */ package mcheli.hud;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import mcheli.MCH_BaseInfo;
/*     */ import mcheli.MCH_Lib;
/*     */ import mcheli.__helper.MCH_Utils;
/*     */ import mcheli.__helper.addon.AddonResourceLocation;
/*     */ import mcheli.__helper.info.ContentParseException;
/*     */ import mcheli.aircraft.MCH_EntityAircraft;
/*     */ import mcheli.wrapper.W_ScaledResolution;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_Hud
/*     */   extends MCH_BaseInfo
/*     */ {
/*  26 */   public static final MCH_Hud NoDisp = new MCH_Hud(MCH_Utils.buildinAddon("none"), "none");
/*     */   
/*     */   public final String name;
/*     */   
/*     */   public final String fileName;
/*     */   private List<MCH_HudItem> list;
/*     */   public boolean isWaitEndif;
/*     */   private boolean isDrawing;
/*     */   public boolean isIfFalse;
/*     */   public boolean exit;
/*     */   
/*     */   public MCH_Hud(AddonResourceLocation location, String filePath) {
/*  38 */     super(location, filePath);
/*     */     
/*  40 */     this.name = location.func_110623_a();
/*     */     
/*  42 */     this.fileName = filePath;
/*  43 */     this.list = new ArrayList<>();
/*  44 */     this.isDrawing = false;
/*  45 */     this.isIfFalse = false;
/*  46 */     this.exit = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean validate() throws Exception {
/*  53 */     for (MCH_HudItem hud : this.list)
/*     */     {
/*  55 */       hud.parent = this;
/*     */     }
/*     */     
/*  58 */     if (this.isWaitEndif) {
/*  59 */       throw new RuntimeException("Endif not found!");
/*     */     }
/*  61 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadItemData(int fileLine, String item, String data) {
/*  67 */     String[] prm = data.split("\\s*,\\s*");
/*     */     
/*  69 */     if (prm == null || prm.length == 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  74 */     if (item.equalsIgnoreCase("If")) {
/*     */       
/*  76 */       if (this.isWaitEndif)
/*     */       {
/*  78 */         throw new RuntimeException("Endif not found!");
/*     */       }
/*     */       
/*  81 */       this.list.add(new MCH_HudItemConditional(fileLine, false, prm[0]));
/*  82 */       this.isWaitEndif = true;
/*     */     }
/*  84 */     else if (item.equalsIgnoreCase("Endif")) {
/*     */       
/*  86 */       if (this.isWaitEndif)
/*     */       {
/*  88 */         this.list.add(new MCH_HudItemConditional(fileLine, true, ""));
/*  89 */         this.isWaitEndif = false;
/*     */       }
/*     */       else
/*     */       {
/*  93 */         throw new RuntimeException("IF in a pair can not be found!");
/*     */       }
/*     */     
/*  96 */     } else if (item.equalsIgnoreCase("DrawString") || item.equalsIgnoreCase("DrawCenteredString")) {
/*     */       
/*  98 */       if (prm.length >= 3) {
/*     */         
/* 100 */         String s = prm[2];
/*     */         
/* 102 */         if (s.charAt(0) == '"' && s.charAt(s.length() - 1) == '"')
/*     */         {
/* 104 */           s = s.substring(1, s.length() - 1);
/* 105 */           this.list.add(new MCH_HudItemString(fileLine, prm[0], prm[1], s, prm, item
/* 106 */                 .equalsIgnoreCase("DrawCenteredString")));
/*     */         }
/*     */       
/*     */       } 
/* 110 */     } else if (item.equalsIgnoreCase("Exit")) {
/*     */       
/* 112 */       this.list.add(new MCH_HudItemExit(fileLine));
/*     */     }
/* 114 */     else if (item.equalsIgnoreCase("Color")) {
/*     */       
/* 116 */       if (prm.length == 1) {
/*     */         
/* 118 */         MCH_HudItemColor c = MCH_HudItemColor.createByParams(fileLine, new String[] { prm[0] });
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 123 */         if (c != null) {
/* 124 */           this.list.add(c);
/*     */         }
/* 126 */       } else if (prm.length == 4) {
/*     */         
/* 128 */         String[] s = { prm[0], prm[1], prm[2], prm[3] };
/*     */ 
/*     */ 
/*     */         
/* 132 */         MCH_HudItemColor c = MCH_HudItemColor.createByParams(fileLine, s);
/*     */         
/* 134 */         if (c != null) {
/* 135 */           this.list.add(c);
/*     */         }
/*     */       } 
/* 138 */     } else if (item.equalsIgnoreCase("DrawTexture")) {
/*     */       
/* 140 */       if (prm.length >= 9 && prm.length <= 10)
/*     */       {
/* 142 */         String rot = (prm.length == 10) ? prm[9] : "0";
/*     */         
/* 144 */         this.list.add(new MCH_HudItemTexture(fileLine, prm[0], prm[1], prm[2], prm[3], prm[4], prm[5], prm[6], prm[7], prm[8], rot));
/*     */       }
/*     */     
/*     */     }
/* 148 */     else if (item.equalsIgnoreCase("DrawRect")) {
/*     */       
/* 150 */       if (prm.length == 4)
/*     */       {
/* 152 */         this.list.add(new MCH_HudItemRect(fileLine, prm[0], prm[1], prm[2], prm[3]));
/*     */       }
/*     */     }
/* 155 */     else if (item.equalsIgnoreCase("DrawLine")) {
/*     */       
/* 157 */       int len = prm.length;
/*     */       
/* 159 */       if (len >= 4 && len % 2 == 0)
/*     */       {
/* 161 */         this.list.add(new MCH_HudItemLine(fileLine, prm));
/*     */       }
/*     */     }
/* 164 */     else if (item.equalsIgnoreCase("DrawLineStipple")) {
/*     */       
/* 166 */       int len = prm.length;
/*     */       
/* 168 */       if (len >= 6 && len % 2 == 0)
/*     */       {
/* 170 */         this.list.add(new MCH_HudItemLineStipple(fileLine, prm));
/*     */       }
/*     */     }
/* 173 */     else if (item.equalsIgnoreCase("Call")) {
/*     */       
/* 175 */       int len = prm.length;
/*     */       
/* 177 */       if (len == 1)
/*     */       {
/* 179 */         this.list.add(new MCH_HudItemCall(fileLine, prm[0]));
/*     */       }
/*     */     }
/* 182 */     else if (item.equalsIgnoreCase("DrawEntityRadar") || item.equalsIgnoreCase("DrawEnemyRadar")) {
/*     */       
/* 184 */       if (prm.length == 5)
/*     */       {
/* 186 */         this.list.add(new MCH_HudItemRadar(fileLine, item.equalsIgnoreCase("DrawEntityRadar"), prm[0], prm[1], prm[2], prm[3], prm[4]));
/*     */       
/*     */       }
/*     */     }
/* 190 */     else if (item.equalsIgnoreCase("DrawGraduationYaw") || item.equalsIgnoreCase("DrawGraduationPitch1") || item
/* 191 */       .equalsIgnoreCase("DrawGraduationPitch2") || item.equalsIgnoreCase("DrawGraduationPitch3")) {
/*     */       
/* 193 */       if (prm.length == 4)
/*     */       {
/* 195 */         int type = -1;
/*     */         
/* 197 */         if (item.equalsIgnoreCase("DrawGraduationYaw")) {
/* 198 */           type = 0;
/*     */         }
/* 200 */         if (item.equalsIgnoreCase("DrawGraduationPitch1")) {
/* 201 */           type = 1;
/*     */         }
/* 203 */         if (item.equalsIgnoreCase("DrawGraduationPitch2")) {
/* 204 */           type = 2;
/*     */         }
/* 206 */         if (item.equalsIgnoreCase("DrawGraduationPitch3")) {
/* 207 */           type = 3;
/*     */         }
/* 209 */         this.list.add(new MCH_HudItemGraduation(fileLine, type, prm[0], prm[1], prm[2], prm[3]));
/*     */       }
/*     */     
/* 212 */     } else if (item.equalsIgnoreCase("DrawCameraRot")) {
/*     */       
/* 214 */       if (prm.length == 2)
/*     */       {
/* 216 */         this.list.add(new MCH_HudItemCameraRot(fileLine, prm[0], prm[1]));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void draw(MCH_EntityAircraft ac, EntityPlayer player, float partialTicks) {
/* 223 */     if (MCH_HudItem.mc == null)
/*     */     {
/* 225 */       MCH_HudItem.mc = Minecraft.func_71410_x();
/*     */     }
/* 227 */     MCH_HudItem.ac = ac;
/* 228 */     MCH_HudItem.player = player;
/* 229 */     MCH_HudItem.partialTicks = partialTicks;
/* 230 */     W_ScaledResolution w_ScaledResolution = new W_ScaledResolution(MCH_HudItem.mc, MCH_HudItem.mc.field_71443_c, MCH_HudItem.mc.field_71440_d);
/*     */ 
/*     */     
/* 233 */     MCH_HudItem.scaleFactor = w_ScaledResolution.func_78325_e();
/*     */     
/* 235 */     if (MCH_HudItem.scaleFactor <= 0)
/*     */     {
/* 237 */       MCH_HudItem.scaleFactor = 1;
/*     */     }
/*     */     
/* 240 */     MCH_HudItem.width = (MCH_HudItem.mc.field_71443_c / MCH_HudItem.scaleFactor);
/* 241 */     MCH_HudItem.height = (MCH_HudItem.mc.field_71440_d / MCH_HudItem.scaleFactor);
/* 242 */     MCH_HudItem.centerX = MCH_HudItem.width / 2.0D;
/* 243 */     MCH_HudItem.centerY = MCH_HudItem.height / 2.0D;
/*     */     
/* 245 */     this.isIfFalse = false;
/* 246 */     this.isDrawing = false;
/* 247 */     this.exit = false;
/*     */     
/* 249 */     if (ac != null && ac.getAcInfo() != null && player != null) {
/*     */       
/* 251 */       MCH_HudItem.update();
/* 252 */       drawItems();
/* 253 */       MCH_HudItem.drawVarMap();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void drawItems() {
/* 259 */     if (!this.isDrawing) {
/*     */       
/* 261 */       this.isDrawing = true;
/*     */       
/* 263 */       for (MCH_HudItem hud : this.list) {
/*     */         
/* 265 */         int line = -1;
/*     */ 
/*     */         
/*     */         try {
/* 269 */           line = hud.fileLine;
/*     */           
/* 271 */           if (hud.canExecute())
/*     */           {
/* 273 */             hud.execute();
/*     */             
/* 275 */             if (this.exit) {
/*     */               break;
/*     */             }
/*     */           }
/*     */         
/*     */         }
/* 281 */         catch (Exception e) {
/*     */           
/* 283 */           MCH_Lib.Log("#### Draw HUD Error!!!: line=%d, file=%s", new Object[] {
/*     */                 
/* 285 */                 Integer.valueOf(line), this.fileName
/*     */               });
/* 287 */           e.printStackTrace();
/* 288 */           throw new RuntimeException(e);
/*     */         } 
/*     */       } 
/*     */       
/* 292 */       this.exit = false;
/* 293 */       this.isIfFalse = false;
/* 294 */       this.isDrawing = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void parse(List<String> lines, String fileExtension, boolean reload) throws Exception {
/* 301 */     if ("txt".equals(fileExtension)) {
/*     */       
/* 303 */       int line = 0;
/*     */ 
/*     */       
/*     */       try {
/* 307 */         for (String str : lines)
/*     */         {
/* 309 */           line++;
/* 310 */           str = str.trim();
/*     */           
/* 312 */           if (str.equalsIgnoreCase("endif"))
/*     */           {
/* 314 */             str = "endif=0";
/*     */           }
/*     */           
/* 317 */           if (str.equalsIgnoreCase("exit"))
/*     */           {
/* 319 */             str = "exit=0";
/*     */           }
/*     */           
/* 322 */           int eqIdx = str.indexOf('=');
/*     */           
/* 324 */           if (eqIdx >= 0 && str.length() > eqIdx + 1)
/*     */           {
/* 326 */             loadItemData(line, str.substring(0, eqIdx).trim().toLowerCase(), str
/* 327 */                 .substring(eqIdx + 1).trim());
/*     */           }
/*     */         }
/*     */       
/* 331 */       } catch (Exception e) {
/*     */         
/* 333 */         throw new ContentParseException(e, line);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\hud\MCH_Hud.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */