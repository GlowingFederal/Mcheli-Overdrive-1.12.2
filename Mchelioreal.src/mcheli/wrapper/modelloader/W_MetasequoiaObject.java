/*     */ package mcheli.wrapper.modelloader;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import mcheli.__helper.client._ModelFormatException;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.resources.IResource;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class W_MetasequoiaObject
/*     */   extends W_ModelCustom
/*     */ {
/*  29 */   public ArrayList<W_Vertex> vertices = new ArrayList<>();
/*  30 */   public ArrayList<W_GroupObject> groupObjects = new ArrayList<>();
/*  31 */   private W_GroupObject currentGroupObject = null;
/*     */   private String fileName;
/*  33 */   private int vertexNum = 0;
/*  34 */   private int faceNum = 0;
/*     */ 
/*     */   
/*     */   public W_MetasequoiaObject(ResourceLocation location, IResource resource) throws _ModelFormatException {
/*  38 */     this.fileName = resource.toString();
/*  39 */     loadObjModel(resource.func_110527_b());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public W_MetasequoiaObject(ResourceLocation resource) throws _ModelFormatException {
/*  45 */     this.fileName = resource.toString();
/*     */ 
/*     */     
/*     */     try {
/*  49 */       IResource res = Minecraft.func_71410_x().func_110442_L().func_110536_a(resource);
/*  50 */       loadObjModel(res.func_110527_b());
/*     */     }
/*  52 */     catch (IOException e) {
/*     */ 
/*     */       
/*  55 */       throw new _ModelFormatException("IO Exception reading model format:" + this.fileName, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public W_MetasequoiaObject(String fileName, URL resource) throws _ModelFormatException {
/*  62 */     this.fileName = fileName;
/*     */ 
/*     */     
/*     */     try {
/*  66 */       loadObjModel(resource.openStream());
/*     */     }
/*  68 */     catch (IOException e) {
/*     */ 
/*     */       
/*  71 */       throw new _ModelFormatException("IO Exception reading model format:" + this.fileName, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public W_MetasequoiaObject(String filename, InputStream inputStream) throws _ModelFormatException {
/*  78 */     this.fileName = filename;
/*  79 */     loadObjModel(inputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsPart(String partName) {
/*  85 */     for (W_GroupObject groupObject : this.groupObjects) {
/*     */       
/*  87 */       if (partName.equalsIgnoreCase(groupObject.name))
/*     */       {
/*  89 */         return true;
/*     */       }
/*     */     } 
/*  92 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadObjModel(InputStream inputStream) throws _ModelFormatException {
/*  98 */     BufferedReader reader = null;
/*  99 */     String currentLine = null;
/* 100 */     int lineCount = 0;
/*     */ 
/*     */     
/*     */     try {
/* 104 */       reader = new BufferedReader(new InputStreamReader(inputStream));
/*     */       
/* 106 */       while ((currentLine = reader.readLine()) != null) {
/*     */         
/* 108 */         lineCount++;
/* 109 */         currentLine = currentLine.replaceAll("\\s+", " ").trim();
/*     */         
/* 111 */         if (isValidGroupObjectLine(currentLine)) {
/*     */           
/* 113 */           W_GroupObject group = parseGroupObject(currentLine, lineCount);
/*     */           
/* 115 */           if (group != null) {
/*     */             
/* 117 */             group.glDrawingMode = 4;
/*     */             
/* 119 */             this.vertices.clear();
/* 120 */             int vertexNum = 0;
/* 121 */             boolean mirror = false;
/* 122 */             double facet = Math.cos(0.785398163375D);
/* 123 */             boolean shading = false;
/*     */             
/* 125 */             while ((currentLine = reader.readLine()) != null) {
/*     */               
/* 127 */               lineCount++;
/* 128 */               currentLine = currentLine.replaceAll("\\s+", " ").trim();
/*     */               
/* 130 */               if (currentLine.equalsIgnoreCase("mirror 1"))
/*     */               {
/* 132 */                 mirror = true;
/*     */               }
/*     */               
/* 135 */               if (currentLine.equalsIgnoreCase("shading 1"))
/*     */               {
/* 137 */                 shading = true;
/*     */               }
/*     */               
/* 140 */               String[] s = currentLine.split(" ");
/*     */               
/* 142 */               if (s.length == 2 && s[0].equalsIgnoreCase("facet"))
/*     */               {
/* 144 */                 facet = Math.cos(Double.parseDouble(s[1]) * 3.1415926535D / 180.0D);
/*     */               }
/*     */               
/* 147 */               if (isValidVertexLine(currentLine)) {
/*     */                 
/* 149 */                 vertexNum = Integer.valueOf(currentLine.split(" ")[1]).intValue();
/*     */                 
/*     */                 break;
/*     */               } 
/*     */             } 
/* 154 */             if (vertexNum > 0) {
/*     */               
/* 156 */               while ((currentLine = reader.readLine()) != null) {
/*     */                 
/* 158 */                 lineCount++;
/* 159 */                 currentLine = currentLine.replaceAll("\\s+", " ").trim();
/*     */                 
/* 161 */                 String[] s = currentLine.split(" ");
/*     */                 
/* 163 */                 if (s.length == 3) {
/*     */ 
/*     */ 
/*     */                   
/* 167 */                   W_Vertex v = new W_Vertex(Float.valueOf(s[0]).floatValue() / 100.0F, Float.valueOf(s[1]).floatValue() / 100.0F, Float.valueOf(s[2]).floatValue() / 100.0F);
/*     */                   
/* 169 */                   checkMinMax(v);
/* 170 */                   this.vertices.add(v);
/*     */                   
/* 172 */                   vertexNum--;
/*     */                   
/* 174 */                   if (vertexNum <= 0) {
/*     */                     break;
/*     */                   }
/*     */                   continue;
/*     */                 } 
/* 179 */                 if (s.length > 0)
/*     */                 {
/*     */                   
/* 182 */                   throw new _ModelFormatException("format error : " + this.fileName + " : line=" + lineCount);
/*     */                 }
/*     */               } 
/*     */ 
/*     */               
/* 187 */               int faceNum = 0;
/*     */               
/* 189 */               while ((currentLine = reader.readLine()) != null) {
/*     */                 
/* 191 */                 lineCount++;
/* 192 */                 currentLine = currentLine.replaceAll("\\s+", " ").trim();
/*     */                 
/* 194 */                 if (isValidFaceLine(currentLine)) {
/*     */                   
/* 196 */                   faceNum = Integer.valueOf(currentLine.split(" ")[1]).intValue();
/*     */                   
/*     */                   break;
/*     */                 } 
/*     */               } 
/* 201 */               if (faceNum > 0) {
/*     */                 
/* 203 */                 while ((currentLine = reader.readLine()) != null) {
/*     */                   
/* 205 */                   lineCount++;
/* 206 */                   currentLine = currentLine.replaceAll("\\s+", " ").trim();
/*     */                   
/* 208 */                   String[] s = currentLine.split(" ");
/* 209 */                   if (s.length > 2) {
/*     */                     
/* 211 */                     if (Integer.valueOf(s[0]).intValue() >= 3) {
/*     */                       
/* 213 */                       W_Face[] faces = parseFace(currentLine, lineCount, mirror);
/* 214 */                       for (W_Face face : faces)
/*     */                       {
/* 216 */                         group.faces.add(face);
/*     */                       }
/*     */                     } 
/* 219 */                     faceNum--;
/* 220 */                     if (faceNum <= 0) {
/*     */                       break;
/*     */                     }
/*     */                     
/*     */                     continue;
/*     */                   } 
/* 226 */                   if (s.length > 2 && Integer.valueOf(s[0]).intValue() != 3)
/*     */                   {
/*     */                     
/* 229 */                     throw new _ModelFormatException("found face is not triangle : " + this.fileName + " : line=" + lineCount);
/*     */                   }
/*     */                 } 
/*     */ 
/*     */                 
/* 234 */                 calcVerticesNormal(group, shading, facet);
/*     */               } 
/*     */             } 
/*     */             
/* 238 */             this.vertexNum += this.vertices.size();
/* 239 */             this.faceNum += group.faces.size();
/* 240 */             this.vertices.clear();
/* 241 */             this.groupObjects.add(group);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*     */       return;
/* 247 */     } catch (IOException e) {
/*     */ 
/*     */       
/* 250 */       throw new _ModelFormatException("IO Exception reading model format : " + this.fileName, e);
/*     */     }
/*     */     finally {
/*     */       
/* 254 */       checkMinMaxFinal();
/*     */       
/* 256 */       this.vertices = null;
/*     */ 
/*     */       
/*     */       try {
/* 260 */         reader.close();
/*     */       }
/* 262 */       catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 268 */         inputStream.close();
/*     */       }
/* 270 */       catch (IOException iOException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void calcVerticesNormal(W_GroupObject group, boolean shading, double facet) {
/* 278 */     for (W_Face f : group.faces) {
/*     */       
/* 280 */       f.vertexNormals = new W_Vertex[f.verticesID.length];
/* 281 */       for (int i = 0; i < f.verticesID.length; i++) {
/*     */         
/* 283 */         W_Vertex vn = getVerticesNormalFromFace(f.faceNormal, f.verticesID[i], group, (float)facet);
/* 284 */         vn.normalize();
/*     */         
/* 286 */         if (shading) {
/*     */           
/* 288 */           if ((f.faceNormal.x * vn.x + f.faceNormal.y * vn.y + f.faceNormal.z * vn.z) >= facet)
/*     */           {
/* 290 */             f.vertexNormals[i] = vn;
/*     */           }
/*     */           else
/*     */           {
/* 294 */             f.vertexNormals[i] = f.faceNormal;
/*     */           }
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 300 */           f.vertexNormals[i] = f.faceNormal;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public W_Vertex getVerticesNormalFromFace(W_Vertex faceNormal, int verticesID, W_GroupObject group, float facet) {
/* 308 */     W_Vertex v = new W_Vertex(0.0F, 0.0F, 0.0F);
/*     */     
/* 310 */     for (W_Face f : group.faces) {
/*     */       
/* 312 */       for (int id : f.verticesID) {
/*     */         
/* 314 */         if (id == verticesID) {
/*     */           
/* 316 */           if (f.faceNormal.x * faceNormal.x + f.faceNormal.y * faceNormal.y + f.faceNormal.z * faceNormal.z < facet) {
/*     */             break;
/*     */           }
/*     */           
/* 320 */           v.add(f.faceNormal);
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 326 */     v.normalize();
/* 327 */     return v;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderAll() {
/* 333 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 334 */     BufferBuilder builder = tessellator.func_178180_c();
/*     */     
/* 336 */     if (this.currentGroupObject != null) {
/*     */ 
/*     */       
/* 339 */       builder.func_181668_a(this.currentGroupObject.glDrawingMode, DefaultVertexFormats.field_181710_j);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 344 */       builder.func_181668_a(4, DefaultVertexFormats.field_181710_j);
/*     */     } 
/*     */     
/* 347 */     tessellateAll(tessellator);
/* 348 */     tessellator.func_78381_a();
/*     */   }
/*     */ 
/*     */   
/*     */   public void tessellateAll(Tessellator tessellator) {
/* 353 */     for (W_GroupObject groupObject : this.groupObjects)
/*     */     {
/* 355 */       groupObject.render(tessellator);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderOnly(String... groupNames) {
/* 362 */     for (W_GroupObject groupObject : this.groupObjects) {
/*     */       
/* 364 */       for (String groupName : groupNames) {
/*     */         
/* 366 */         if (groupName.equalsIgnoreCase(groupObject.name))
/*     */         {
/* 368 */           groupObject.render();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void tessellateOnly(Tessellator tessellator, String... groupNames) {
/* 376 */     for (W_GroupObject groupObject : this.groupObjects) {
/*     */       
/* 378 */       for (String groupName : groupNames) {
/*     */         
/* 380 */         if (groupName.equalsIgnoreCase(groupObject.name))
/*     */         {
/* 382 */           groupObject.render(tessellator);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderPart(String partName) {
/* 391 */     if (partName.charAt(0) == '$') {
/*     */       
/* 393 */       for (int i = 0; i < this.groupObjects.size(); i++) {
/*     */         
/* 395 */         W_GroupObject groupObject = this.groupObjects.get(i);
/*     */         
/* 397 */         if (partName.equalsIgnoreCase(groupObject.name)) {
/*     */           
/* 399 */           groupObject.render();
/*     */           
/* 401 */           i++;
/* 402 */           for (; i < this.groupObjects.size(); i++)
/*     */           {
/* 404 */             groupObject = this.groupObjects.get(i);
/* 405 */             if (groupObject.name.charAt(0) == '$') {
/*     */               break;
/*     */             }
/*     */ 
/*     */             
/* 410 */             groupObject.render();
/*     */           }
/*     */         
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 417 */       for (W_GroupObject groupObject : this.groupObjects) {
/*     */         
/* 419 */         if (partName.equalsIgnoreCase(groupObject.name))
/*     */         {
/* 421 */           groupObject.render();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void tessellatePart(Tessellator tessellator, String partName) {
/* 429 */     for (W_GroupObject groupObject : this.groupObjects) {
/*     */       
/* 431 */       if (partName.equalsIgnoreCase(groupObject.name))
/*     */       {
/* 433 */         groupObject.render(tessellator);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderAllExcept(String... excludedGroupNames) {
/* 441 */     for (W_GroupObject groupObject : this.groupObjects) {
/*     */       
/* 443 */       boolean skipPart = false;
/* 444 */       for (String excludedGroupName : excludedGroupNames) {
/*     */         
/* 446 */         if (excludedGroupName.equalsIgnoreCase(groupObject.name))
/*     */         {
/* 448 */           skipPart = true;
/*     */         }
/*     */       } 
/* 451 */       if (!skipPart)
/*     */       {
/* 453 */         groupObject.render();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void tessellateAllExcept(Tessellator tessellator, String... excludedGroupNames) {
/* 460 */     for (W_GroupObject groupObject : this.groupObjects) {
/*     */       
/* 462 */       boolean exclude = false;
/* 463 */       for (String excludedGroupName : excludedGroupNames) {
/*     */         
/* 465 */         if (excludedGroupName.equalsIgnoreCase(groupObject.name))
/*     */         {
/* 467 */           exclude = true;
/*     */         }
/*     */       } 
/* 470 */       if (!exclude)
/*     */       {
/* 472 */         groupObject.render(tessellator);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private W_Face[] parseFace(String line, int lineCount, boolean mirror) {
/* 479 */     String[] s = line.split("[ VU)(M]+");
/*     */     
/* 481 */     int vnum = Integer.valueOf(s[0]).intValue();
/* 482 */     if (vnum != 3 && vnum != 4)
/*     */     {
/* 484 */       return new W_Face[0];
/*     */     }
/*     */     
/* 487 */     if (vnum == 3) {
/*     */       
/* 489 */       W_Face face = new W_Face();
/* 490 */       face
/*     */ 
/*     */         
/* 493 */         .verticesID = new int[] { Integer.valueOf(s[3]).intValue(), Integer.valueOf(s[2]).intValue(), Integer.valueOf(s[1]).intValue() };
/*     */ 
/*     */       
/* 496 */       face
/*     */ 
/*     */         
/* 499 */         .vertices = new W_Vertex[] { this.vertices.get(face.verticesID[0]), this.vertices.get(face.verticesID[1]), this.vertices.get(face.verticesID[2]) };
/*     */ 
/*     */       
/* 502 */       if (s.length >= 11) {
/*     */         
/* 504 */         face
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 509 */           .textureCoordinates = new W_TextureCoordinate[] { new W_TextureCoordinate(Float.valueOf(s[9]).floatValue(), Float.valueOf(s[10]).floatValue()), new W_TextureCoordinate(Float.valueOf(s[7]).floatValue(), Float.valueOf(s[8]).floatValue()), new W_TextureCoordinate(Float.valueOf(s[5]).floatValue(), Float.valueOf(s[6]).floatValue()) };
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 516 */         face.textureCoordinates = new W_TextureCoordinate[] { new W_TextureCoordinate(0.0F, 0.0F), new W_TextureCoordinate(0.0F, 0.0F), new W_TextureCoordinate(0.0F, 0.0F) };
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 523 */       face.faceNormal = face.calculateFaceNormal();
/*     */       
/* 525 */       return new W_Face[] { face };
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 531 */     W_Face face1 = new W_Face();
/* 532 */     face1
/*     */       
/* 534 */       .verticesID = new int[] { Integer.valueOf(s[3]).intValue(), Integer.valueOf(s[2]).intValue(), Integer.valueOf(s[1]).intValue() };
/*     */ 
/*     */     
/* 537 */     face1
/*     */ 
/*     */       
/* 540 */       .vertices = new W_Vertex[] { this.vertices.get(face1.verticesID[0]), this.vertices.get(face1.verticesID[1]), this.vertices.get(face1.verticesID[2]) };
/*     */ 
/*     */     
/* 543 */     if (s.length >= 12) {
/*     */       
/* 545 */       face1
/*     */ 
/*     */ 
/*     */         
/* 549 */         .textureCoordinates = new W_TextureCoordinate[] { new W_TextureCoordinate(Float.valueOf(s[10]).floatValue(), Float.valueOf(s[11]).floatValue()), new W_TextureCoordinate(Float.valueOf(s[8]).floatValue(), Float.valueOf(s[9]).floatValue()), new W_TextureCoordinate(Float.valueOf(s[6]).floatValue(), Float.valueOf(s[7]).floatValue()) };
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 556 */       face1.textureCoordinates = new W_TextureCoordinate[] { new W_TextureCoordinate(0.0F, 0.0F), new W_TextureCoordinate(0.0F, 0.0F), new W_TextureCoordinate(0.0F, 0.0F) };
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 563 */     face1.faceNormal = face1.calculateFaceNormal();
/*     */     
/* 565 */     W_Face face2 = new W_Face();
/* 566 */     face2
/*     */       
/* 568 */       .verticesID = new int[] { Integer.valueOf(s[4]).intValue(), Integer.valueOf(s[3]).intValue(), Integer.valueOf(s[1]).intValue() };
/*     */ 
/*     */     
/* 571 */     face2
/*     */ 
/*     */       
/* 574 */       .vertices = new W_Vertex[] { this.vertices.get(face2.verticesID[0]), this.vertices.get(face2.verticesID[1]), this.vertices.get(face2.verticesID[2]) };
/*     */ 
/*     */     
/* 577 */     if (s.length >= 14) {
/*     */       
/* 579 */       face2
/*     */ 
/*     */ 
/*     */         
/* 583 */         .textureCoordinates = new W_TextureCoordinate[] { new W_TextureCoordinate(Float.valueOf(s[12]).floatValue(), Float.valueOf(s[13]).floatValue()), new W_TextureCoordinate(Float.valueOf(s[10]).floatValue(), Float.valueOf(s[11]).floatValue()), new W_TextureCoordinate(Float.valueOf(s[6]).floatValue(), Float.valueOf(s[7]).floatValue()) };
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 590 */       face2.textureCoordinates = new W_TextureCoordinate[] { new W_TextureCoordinate(0.0F, 0.0F), new W_TextureCoordinate(0.0F, 0.0F), new W_TextureCoordinate(0.0F, 0.0F) };
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 597 */     face2.faceNormal = face2.calculateFaceNormal();
/*     */     
/* 599 */     return new W_Face[] { face1, face2 };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isValidGroupObjectLine(String line) {
/* 607 */     String[] s = line.split(" ");
/*     */     
/* 609 */     if (s.length < 2 || !s[0].equals("Object"))
/*     */     {
/* 611 */       return false;
/*     */     }
/*     */     
/* 614 */     if (s[1].length() < 4 || s[1].charAt(0) != '"')
/*     */     {
/* 616 */       return false;
/*     */     }
/*     */     
/* 619 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private W_GroupObject parseGroupObject(String line, int lineCount) throws _ModelFormatException {
/* 625 */     W_GroupObject group = null;
/*     */     
/* 627 */     if (isValidGroupObjectLine(line)) {
/*     */       
/* 629 */       String[] s = line.split(" ");
/* 630 */       String trimmedLine = s[1].substring(1, s[1].length() - 1);
/*     */       
/* 632 */       if (trimmedLine.length() > 0)
/*     */       {
/* 634 */         group = new W_GroupObject(trimmedLine);
/*     */       
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 640 */       throw new _ModelFormatException("Error parsing entry ('" + line + "', line " + lineCount + ") in file '" + this.fileName + "' - Incorrect format");
/*     */     } 
/*     */ 
/*     */     
/* 644 */     return group;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isValidVertexLine(String line) {
/* 649 */     String[] s = line.split(" ");
/*     */     
/* 651 */     if (!s[0].equals("vertex"))
/*     */     {
/* 653 */       return false;
/*     */     }
/* 655 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isValidFaceLine(String line) {
/* 660 */     String[] s = line.split(" ");
/*     */     
/* 662 */     if (!s[0].equals("face"))
/*     */     {
/* 664 */       return false;
/*     */     }
/* 666 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getType() {
/* 672 */     return "mqo";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderAllLine(int startLine, int maxLine) {
/* 678 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 679 */     BufferBuilder builder = tessellator.func_178180_c();
/*     */     
/* 681 */     builder.func_181668_a(1, DefaultVertexFormats.field_181705_e);
/* 682 */     renderAllLine(tessellator, startLine, maxLine);
/* 683 */     tessellator.func_78381_a();
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderAllLine(Tessellator tessellator, int startLine, int maxLine) {
/* 688 */     int lineCnt = 0;
/* 689 */     BufferBuilder builder = tessellator.func_178180_c();
/*     */     
/* 691 */     for (W_GroupObject groupObject : this.groupObjects) {
/*     */       
/* 693 */       if (groupObject.faces.size() > 0)
/*     */       {
/* 695 */         for (W_Face face : groupObject.faces) {
/*     */           
/* 697 */           for (int i = 0; i < face.vertices.length / 3; i++) {
/*     */             
/* 699 */             W_Vertex v1 = face.vertices[i * 3 + 0];
/* 700 */             W_Vertex v2 = face.vertices[i * 3 + 1];
/* 701 */             W_Vertex v3 = face.vertices[i * 3 + 2];
/*     */             
/* 703 */             lineCnt++;
/* 704 */             if (lineCnt > maxLine) {
/*     */               return;
/*     */             }
/*     */             
/* 708 */             builder.func_181662_b(v1.x, v1.y, v1.z).func_181675_d();
/* 709 */             builder.func_181662_b(v2.x, v2.y, v2.z).func_181675_d();
/*     */             
/* 711 */             lineCnt++;
/* 712 */             if (lineCnt > maxLine) {
/*     */               return;
/*     */             }
/*     */             
/* 716 */             builder.func_181662_b(v2.x, v2.y, v2.z).func_181675_d();
/* 717 */             builder.func_181662_b(v3.x, v3.y, v3.z).func_181675_d();
/*     */             
/* 719 */             lineCnt++;
/* 720 */             if (lineCnt > maxLine) {
/*     */               return;
/*     */             }
/*     */             
/* 724 */             builder.func_181662_b(v3.x, v3.y, v3.z).func_181675_d();
/* 725 */             builder.func_181662_b(v1.x, v1.y, v1.z).func_181675_d();
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getVertexNum() {
/* 735 */     return this.vertexNum;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFaceNum() {
/* 741 */     return this.faceNum;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderAll(int startFace, int maxFace) {
/* 747 */     if (startFace < 0)
/*     */     {
/* 749 */       startFace = 0;
/*     */     }
/*     */     
/* 752 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 753 */     BufferBuilder builder = tessellator.func_178180_c();
/*     */     
/* 755 */     builder.func_181668_a(4, DefaultVertexFormats.field_181710_j);
/* 756 */     renderAll(tessellator, startFace, maxFace);
/* 757 */     tessellator.func_78381_a();
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderAll(Tessellator tessellator, int startFace, int maxLine) {
/* 762 */     int faceCnt = 0;
/* 763 */     for (W_GroupObject groupObject : this.groupObjects) {
/*     */       
/* 765 */       if (groupObject.faces.size() > 0)
/*     */       {
/* 767 */         for (W_Face face : groupObject.faces) {
/*     */           
/* 769 */           faceCnt++;
/* 770 */           if (faceCnt >= startFace) {
/*     */             
/* 772 */             if (faceCnt > maxLine)
/*     */               return; 
/* 774 */             face.addFaceForRender(tessellator);
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\wrapper\modelloader\W_MetasequoiaObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */