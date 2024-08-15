/*     */ package mcheli.wrapper.modelloader;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
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
/*     */ 
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class W_WavefrontObject
/*     */   extends W_ModelCustom
/*     */ {
/*  33 */   private static Pattern vertexPattern = Pattern.compile("(v( (\\-){0,1}\\d+\\.\\d+){3,4} *\\n)|(v( (\\-){0,1}\\d+\\.\\d+){3,4} *$)");
/*     */   
/*  35 */   private static Pattern vertexNormalPattern = Pattern.compile("(vn( (\\-){0,1}\\d+\\.\\d+){3,4} *\\n)|(vn( (\\-){0,1}\\d+\\.\\d+){3,4} *$)");
/*     */   
/*  37 */   private static Pattern textureCoordinatePattern = Pattern.compile("(vt( (\\-){0,1}\\d+\\.\\d+){2,3} *\\n)|(vt( (\\-){0,1}\\d+\\.\\d+){2,3} *$)");
/*     */   
/*  39 */   private static Pattern face_V_VT_VN_Pattern = Pattern.compile("(f( \\d+/\\d+/\\d+){3,4} *\\n)|(f( \\d+/\\d+/\\d+){3,4} *$)");
/*  40 */   private static Pattern face_V_VT_Pattern = Pattern.compile("(f( \\d+/\\d+){3,4} *\\n)|(f( \\d+/\\d+){3,4} *$)");
/*  41 */   private static Pattern face_V_VN_Pattern = Pattern.compile("(f( \\d+//\\d+){3,4} *\\n)|(f( \\d+//\\d+){3,4} *$)");
/*  42 */   private static Pattern face_V_Pattern = Pattern.compile("(f( \\d+){3,4} *\\n)|(f( \\d+){3,4} *$)");
/*     */   
/*  44 */   private static Pattern groupObjectPattern = Pattern.compile("([go]( [-\\$\\w\\d]+) *\\n)|([go]( [-\\$\\w\\d]+) *$)");
/*     */   private static Matcher vertexMatcher;
/*     */   private static Matcher vertexNormalMatcher;
/*     */   private static Matcher textureCoordinateMatcher;
/*     */   private static Matcher face_V_VT_VN_Matcher;
/*     */   private static Matcher face_V_VT_Matcher;
/*     */   private static Matcher face_V_VN_Matcher;
/*     */   private static Matcher face_V_Matcher;
/*     */   private static Matcher groupObjectMatcher;
/*  53 */   public ArrayList<W_Vertex> vertices = new ArrayList<>();
/*  54 */   public ArrayList<W_Vertex> vertexNormals = new ArrayList<>();
/*  55 */   public ArrayList<W_TextureCoordinate> textureCoordinates = new ArrayList<>();
/*  56 */   public ArrayList<W_GroupObject> groupObjects = new ArrayList<>();
/*     */   
/*     */   private W_GroupObject currentGroupObject;
/*     */   private String fileName;
/*     */   
/*     */   public W_WavefrontObject(ResourceLocation location, IResource resource) throws _ModelFormatException {
/*  62 */     this.fileName = resource.toString();
/*  63 */     loadObjModel(resource.func_110527_b());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public W_WavefrontObject(ResourceLocation resource) throws _ModelFormatException {
/*  69 */     this.fileName = resource.toString();
/*     */ 
/*     */     
/*     */     try {
/*  73 */       IResource res = Minecraft.func_71410_x().func_110442_L().func_110536_a(resource);
/*  74 */       loadObjModel(res.func_110527_b());
/*     */     }
/*  76 */     catch (IOException e) {
/*     */ 
/*     */       
/*  79 */       throw new _ModelFormatException("IO Exception reading model format", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public W_WavefrontObject(String fileName, URL resource) throws _ModelFormatException {
/*  86 */     this.fileName = fileName;
/*     */ 
/*     */     
/*     */     try {
/*  90 */       loadObjModel(resource.openStream());
/*     */     }
/*  92 */     catch (IOException e) {
/*     */ 
/*     */       
/*  95 */       throw new _ModelFormatException("IO Exception reading model format", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public W_WavefrontObject(String filename, InputStream inputStream) throws _ModelFormatException {
/* 102 */     this.fileName = filename;
/* 103 */     loadObjModel(inputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsPart(String partName) {
/* 109 */     for (W_GroupObject groupObject : this.groupObjects) {
/*     */       
/* 111 */       if (partName.equalsIgnoreCase(groupObject.name))
/*     */       {
/* 113 */         return true;
/*     */       }
/*     */     } 
/* 116 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadObjModel(InputStream inputStream) throws _ModelFormatException {
/* 122 */     BufferedReader reader = null;
/* 123 */     String currentLine = null;
/* 124 */     int lineCount = 0;
/*     */ 
/*     */     
/*     */     try {
/* 128 */       reader = new BufferedReader(new InputStreamReader(inputStream));
/*     */       
/* 130 */       while ((currentLine = reader.readLine()) != null) {
/*     */         
/* 132 */         lineCount++;
/* 133 */         currentLine = currentLine.replaceAll("\\s+", " ").trim();
/*     */         
/* 135 */         if (!currentLine.startsWith("#") && currentLine.length() != 0) {
/*     */           
/* 137 */           if (currentLine.startsWith("v ")) {
/*     */             
/* 139 */             W_Vertex vertex = parseVertex(currentLine, lineCount);
/*     */             
/* 141 */             if (vertex != null) {
/*     */               
/* 143 */               checkMinMax(vertex);
/* 144 */               this.vertices.add(vertex);
/*     */             }  continue;
/*     */           } 
/* 147 */           if (currentLine.startsWith("vn ")) {
/*     */             
/* 149 */             W_Vertex vertex = parseVertexNormal(currentLine, lineCount);
/*     */             
/* 151 */             if (vertex != null)
/*     */             {
/* 153 */               this.vertexNormals.add(vertex); } 
/*     */             continue;
/*     */           } 
/* 156 */           if (currentLine.startsWith("vt ")) {
/*     */             
/* 158 */             W_TextureCoordinate textureCoordinate = parseTextureCoordinate(currentLine, lineCount);
/*     */             
/* 160 */             if (textureCoordinate != null)
/*     */             {
/* 162 */               this.textureCoordinates.add(textureCoordinate); } 
/*     */             continue;
/*     */           } 
/* 165 */           if (currentLine.startsWith("f ")) {
/*     */             
/* 167 */             if (this.currentGroupObject == null)
/*     */             {
/* 169 */               this.currentGroupObject = new W_GroupObject("Default");
/*     */             }
/*     */             
/* 172 */             W_Face face = parseFace(currentLine, lineCount);
/*     */             
/* 174 */             if (face != null)
/*     */             {
/* 176 */               this.currentGroupObject.faces.add(face); } 
/*     */             continue;
/*     */           } 
/* 179 */           if ((currentLine.startsWith("g ") | currentLine.startsWith("o ")) != 0 && currentLine
/* 180 */             .charAt(2) == '$') {
/*     */             
/* 182 */             W_GroupObject group = parseGroupObject(currentLine, lineCount);
/*     */             
/* 184 */             if (group != null)
/*     */             {
/* 186 */               if (this.currentGroupObject != null)
/*     */               {
/* 188 */                 this.groupObjects.add(this.currentGroupObject);
/*     */               }
/*     */             }
/*     */             
/* 192 */             this.currentGroupObject = group;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 197 */       this.groupObjects.add(this.currentGroupObject);
/*     */       
/*     */       return;
/* 200 */     } catch (IOException e) {
/*     */ 
/*     */       
/* 203 */       throw new _ModelFormatException("IO Exception reading model format", e);
/*     */     }
/*     */     finally {
/*     */       
/* 207 */       checkMinMaxFinal();
/*     */ 
/*     */       
/*     */       try {
/* 211 */         reader.close();
/*     */       }
/* 213 */       catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 219 */         inputStream.close();
/*     */       }
/* 221 */       catch (IOException iOException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderAll() {
/* 230 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 231 */     BufferBuilder builder = tessellator.func_178180_c();
/*     */     
/* 233 */     if (this.currentGroupObject != null) {
/*     */ 
/*     */       
/* 236 */       builder.func_181668_a(this.currentGroupObject.glDrawingMode, DefaultVertexFormats.field_181710_j);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 241 */       builder.func_181668_a(4, DefaultVertexFormats.field_181710_j);
/*     */     } 
/*     */     
/* 244 */     tessellateAll(tessellator);
/*     */     
/* 246 */     tessellator.func_78381_a();
/*     */   }
/*     */ 
/*     */   
/*     */   public void tessellateAll(Tessellator tessellator) {
/* 251 */     for (W_GroupObject groupObject : this.groupObjects)
/*     */     {
/* 253 */       groupObject.render(tessellator);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderOnly(String... groupNames) {
/* 260 */     for (W_GroupObject groupObject : this.groupObjects) {
/*     */       
/* 262 */       for (String groupName : groupNames) {
/*     */         
/* 264 */         if (groupName.equalsIgnoreCase(groupObject.name))
/*     */         {
/* 266 */           groupObject.render();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void tessellateOnly(Tessellator tessellator, String... groupNames) {
/* 274 */     for (W_GroupObject groupObject : this.groupObjects) {
/*     */       
/* 276 */       for (String groupName : groupNames) {
/*     */         
/* 278 */         if (groupName.equalsIgnoreCase(groupObject.name))
/*     */         {
/* 280 */           groupObject.render(tessellator);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderPart(String partName) {
/* 289 */     for (W_GroupObject groupObject : this.groupObjects) {
/*     */       
/* 291 */       if (partName.equalsIgnoreCase(groupObject.name))
/*     */       {
/* 293 */         groupObject.render();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void tessellatePart(Tessellator tessellator, String partName) {
/* 300 */     for (W_GroupObject groupObject : this.groupObjects) {
/*     */       
/* 302 */       if (partName.equalsIgnoreCase(groupObject.name))
/*     */       {
/* 304 */         groupObject.render(tessellator);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderAllExcept(String... excludedGroupNames) {
/* 312 */     for (W_GroupObject groupObject : this.groupObjects) {
/*     */       
/* 314 */       boolean skipPart = false;
/* 315 */       for (String excludedGroupName : excludedGroupNames) {
/*     */         
/* 317 */         if (excludedGroupName.equalsIgnoreCase(groupObject.name))
/*     */         {
/* 319 */           skipPart = true;
/*     */         }
/*     */       } 
/* 322 */       if (!skipPart)
/*     */       {
/* 324 */         groupObject.render();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void tessellateAllExcept(Tessellator tessellator, String... excludedGroupNames) {
/* 331 */     for (W_GroupObject groupObject : this.groupObjects) {
/*     */       
/* 333 */       boolean exclude = false;
/* 334 */       for (String excludedGroupName : excludedGroupNames) {
/*     */         
/* 336 */         if (excludedGroupName.equalsIgnoreCase(groupObject.name))
/*     */         {
/* 338 */           exclude = true;
/*     */         }
/*     */       } 
/* 341 */       if (!exclude)
/*     */       {
/* 343 */         groupObject.render(tessellator);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private W_Vertex parseVertex(String line, int lineCount) throws _ModelFormatException {
/* 351 */     W_Vertex vertex = null;
/*     */     
/* 353 */     if (isValidVertexLine(line)) {
/*     */       
/* 355 */       line = line.substring(line.indexOf(" ") + 1);
/* 356 */       String[] tokens = line.split(" ");
/*     */ 
/*     */       
/*     */       try {
/* 360 */         if (tokens.length == 2)
/*     */         {
/* 362 */           return new W_Vertex(Float.parseFloat(tokens[0]), Float.parseFloat(tokens[1]));
/*     */         }
/* 364 */         if (tokens.length == 3)
/*     */         {
/* 366 */           return new W_Vertex(Float.parseFloat(tokens[0]), Float.parseFloat(tokens[1]), 
/* 367 */               Float.parseFloat(tokens[2]));
/*     */         }
/*     */       }
/* 370 */       catch (NumberFormatException e) {
/*     */ 
/*     */         
/* 373 */         throw new _ModelFormatException(String.format("Number formatting error at line %d", new Object[] {
/*     */                 
/* 375 */                 Integer.valueOf(lineCount)
/*     */               }), e);
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 382 */       throw new _ModelFormatException("Error parsing entry ('" + line + "', line " + lineCount + ") in file '" + this.fileName + "' - Incorrect format");
/*     */     } 
/*     */ 
/*     */     
/* 386 */     return vertex;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private W_Vertex parseVertexNormal(String line, int lineCount) throws _ModelFormatException {
/* 392 */     W_Vertex vertexNormal = null;
/*     */     
/* 394 */     if (isValidVertexNormalLine(line)) {
/*     */       
/* 396 */       line = line.substring(line.indexOf(" ") + 1);
/* 397 */       String[] tokens = line.split(" ");
/*     */ 
/*     */       
/*     */       try {
/* 401 */         if (tokens.length == 3)
/*     */         {
/* 403 */           return new W_Vertex(Float.parseFloat(tokens[0]), Float.parseFloat(tokens[1]), 
/* 404 */               Float.parseFloat(tokens[2]));
/*     */         }
/*     */       }
/* 407 */       catch (NumberFormatException e) {
/*     */ 
/*     */         
/* 410 */         throw new _ModelFormatException(String.format("Number formatting error at line %d", new Object[] {
/*     */                 
/* 412 */                 Integer.valueOf(lineCount)
/*     */               }), e);
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 419 */       throw new _ModelFormatException("Error parsing entry ('" + line + "', line " + lineCount + ") in file '" + this.fileName + "' - Incorrect format");
/*     */     } 
/*     */ 
/*     */     
/* 423 */     return vertexNormal;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private W_TextureCoordinate parseTextureCoordinate(String line, int lineCount) throws _ModelFormatException {
/* 429 */     W_TextureCoordinate textureCoordinate = null;
/*     */     
/* 431 */     if (isValidTextureCoordinateLine(line)) {
/*     */       
/* 433 */       line = line.substring(line.indexOf(" ") + 1);
/* 434 */       String[] tokens = line.split(" ");
/*     */ 
/*     */       
/*     */       try {
/* 438 */         if (tokens.length == 2)
/* 439 */           return new W_TextureCoordinate(Float.parseFloat(tokens[0]), 1.0F - Float.parseFloat(tokens[1])); 
/* 440 */         if (tokens.length == 3)
/*     */         {
/* 442 */           return new W_TextureCoordinate(Float.parseFloat(tokens[0]), 1.0F - Float.parseFloat(tokens[1]), 
/* 443 */               Float.parseFloat(tokens[2]));
/*     */         }
/*     */       }
/* 446 */       catch (NumberFormatException e) {
/*     */ 
/*     */         
/* 449 */         throw new _ModelFormatException(String.format("Number formatting error at line %d", new Object[] {
/*     */                 
/* 451 */                 Integer.valueOf(lineCount)
/*     */               }), e);
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 458 */       throw new _ModelFormatException("Error parsing entry ('" + line + "', line " + lineCount + ") in file '" + this.fileName + "' - Incorrect format");
/*     */     } 
/*     */ 
/*     */     
/* 462 */     return textureCoordinate;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private W_Face parseFace(String line, int lineCount) throws _ModelFormatException {
/* 468 */     W_Face face = null;
/*     */     
/* 470 */     if (isValidFaceLine(line)) {
/*     */       
/* 472 */       face = new W_Face();
/*     */       
/* 474 */       String trimmedLine = line.substring(line.indexOf(" ") + 1);
/* 475 */       String[] tokens = trimmedLine.split(" ");
/* 476 */       String[] subTokens = null;
/*     */       
/* 478 */       if (tokens.length == 3) {
/*     */         
/* 480 */         if (this.currentGroupObject.glDrawingMode == -1)
/*     */         {
/* 482 */           this.currentGroupObject.glDrawingMode = 4;
/*     */         }
/* 484 */         else if (this.currentGroupObject.glDrawingMode != 4)
/*     */         {
/*     */           
/* 487 */           throw new _ModelFormatException("Error parsing entry ('" + line + "', line " + lineCount + ") in file '" + this.fileName + "' - Invalid number of points for face (expected 4, found " + tokens.length + ")");
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 492 */       else if (tokens.length == 4) {
/*     */         
/* 494 */         if (this.currentGroupObject.glDrawingMode == -1) {
/*     */           
/* 496 */           this.currentGroupObject.glDrawingMode = 7;
/*     */         }
/* 498 */         else if (this.currentGroupObject.glDrawingMode != 7) {
/*     */ 
/*     */           
/* 501 */           throw new _ModelFormatException("Error parsing entry ('" + line + "', line " + lineCount + ") in file '" + this.fileName + "' - Invalid number of points for face (expected 3, found " + tokens.length + ")");
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 507 */       if (isValidFace_V_VT_VN_Line(line))
/*     */       {
/* 509 */         face.vertices = new W_Vertex[tokens.length];
/* 510 */         face.textureCoordinates = new W_TextureCoordinate[tokens.length];
/* 511 */         face.vertexNormals = new W_Vertex[tokens.length];
/*     */         
/* 513 */         for (int i = 0; i < tokens.length; i++) {
/*     */           
/* 515 */           subTokens = tokens[i].split("/");
/*     */           
/* 517 */           face.vertices[i] = this.vertices.get(Integer.parseInt(subTokens[0]) - 1);
/* 518 */           face.textureCoordinates[i] = this.textureCoordinates.get(Integer.parseInt(subTokens[1]) - 1);
/* 519 */           face.vertexNormals[i] = this.vertexNormals.get(Integer.parseInt(subTokens[2]) - 1);
/*     */         } 
/*     */         
/* 522 */         face.faceNormal = face.calculateFaceNormal();
/*     */       
/*     */       }
/* 525 */       else if (isValidFace_V_VT_Line(line))
/*     */       {
/* 527 */         face.vertices = new W_Vertex[tokens.length];
/* 528 */         face.textureCoordinates = new W_TextureCoordinate[tokens.length];
/*     */         
/* 530 */         for (int i = 0; i < tokens.length; i++) {
/*     */           
/* 532 */           subTokens = tokens[i].split("/");
/*     */           
/* 534 */           face.vertices[i] = this.vertices.get(Integer.parseInt(subTokens[0]) - 1);
/* 535 */           face.textureCoordinates[i] = this.textureCoordinates.get(Integer.parseInt(subTokens[1]) - 1);
/*     */         } 
/*     */         
/* 538 */         face.faceNormal = face.calculateFaceNormal();
/*     */       
/*     */       }
/* 541 */       else if (isValidFace_V_VN_Line(line))
/*     */       {
/* 543 */         face.vertices = new W_Vertex[tokens.length];
/* 544 */         face.vertexNormals = new W_Vertex[tokens.length];
/*     */         
/* 546 */         for (int i = 0; i < tokens.length; i++) {
/*     */           
/* 548 */           subTokens = tokens[i].split("//");
/*     */           
/* 550 */           face.vertices[i] = this.vertices.get(Integer.parseInt(subTokens[0]) - 1);
/* 551 */           face.vertexNormals[i] = this.vertexNormals.get(Integer.parseInt(subTokens[1]) - 1);
/*     */         } 
/*     */         
/* 554 */         face.faceNormal = face.calculateFaceNormal();
/*     */       
/*     */       }
/* 557 */       else if (isValidFace_V_Line(line))
/*     */       {
/* 559 */         face.vertices = new W_Vertex[tokens.length];
/*     */         
/* 561 */         for (int i = 0; i < tokens.length; i++)
/*     */         {
/* 563 */           face.vertices[i] = this.vertices.get(Integer.parseInt(tokens[i]) - 1);
/*     */         }
/*     */         
/* 566 */         face.faceNormal = face.calculateFaceNormal();
/*     */       
/*     */       }
/*     */       else
/*     */       {
/* 571 */         throw new _ModelFormatException("Error parsing entry ('" + line + "', line " + lineCount + ") in file '" + this.fileName + "' - Incorrect format");
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 578 */       throw new _ModelFormatException("Error parsing entry ('" + line + "', line " + lineCount + ") in file '" + this.fileName + "' - Incorrect format");
/*     */     } 
/*     */ 
/*     */     
/* 582 */     return face;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private W_GroupObject parseGroupObject(String line, int lineCount) throws _ModelFormatException {
/* 588 */     W_GroupObject group = null;
/*     */     
/* 590 */     if (isValidGroupObjectLine(line)) {
/*     */       
/* 592 */       String trimmedLine = line.substring(line.indexOf(" ") + 1);
/*     */       
/* 594 */       if (trimmedLine.length() > 0)
/*     */       {
/* 596 */         group = new W_GroupObject(trimmedLine);
/*     */       
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 602 */       throw new _ModelFormatException("Error parsing entry ('" + line + "', line " + lineCount + ") in file '" + this.fileName + "' - Incorrect format");
/*     */     } 
/*     */ 
/*     */     
/* 606 */     return group;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isValidVertexLine(String line) {
/* 611 */     if (vertexMatcher != null)
/*     */     {
/* 613 */       vertexMatcher.reset();
/*     */     }
/*     */     
/* 616 */     vertexMatcher = vertexPattern.matcher(line);
/* 617 */     return vertexMatcher.matches();
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isValidVertexNormalLine(String line) {
/* 622 */     if (vertexNormalMatcher != null)
/*     */     {
/* 624 */       vertexNormalMatcher.reset();
/*     */     }
/*     */     
/* 627 */     vertexNormalMatcher = vertexNormalPattern.matcher(line);
/* 628 */     return vertexNormalMatcher.matches();
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isValidTextureCoordinateLine(String line) {
/* 633 */     if (textureCoordinateMatcher != null)
/*     */     {
/* 635 */       textureCoordinateMatcher.reset();
/*     */     }
/*     */     
/* 638 */     textureCoordinateMatcher = textureCoordinatePattern.matcher(line);
/* 639 */     return textureCoordinateMatcher.matches();
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isValidFace_V_VT_VN_Line(String line) {
/* 644 */     if (face_V_VT_VN_Matcher != null)
/*     */     {
/* 646 */       face_V_VT_VN_Matcher.reset();
/*     */     }
/*     */     
/* 649 */     face_V_VT_VN_Matcher = face_V_VT_VN_Pattern.matcher(line);
/* 650 */     return face_V_VT_VN_Matcher.matches();
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isValidFace_V_VT_Line(String line) {
/* 655 */     if (face_V_VT_Matcher != null)
/*     */     {
/* 657 */       face_V_VT_Matcher.reset();
/*     */     }
/*     */     
/* 660 */     face_V_VT_Matcher = face_V_VT_Pattern.matcher(line);
/* 661 */     return face_V_VT_Matcher.matches();
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isValidFace_V_VN_Line(String line) {
/* 666 */     if (face_V_VN_Matcher != null)
/*     */     {
/* 668 */       face_V_VN_Matcher.reset();
/*     */     }
/*     */     
/* 671 */     face_V_VN_Matcher = face_V_VN_Pattern.matcher(line);
/* 672 */     return face_V_VN_Matcher.matches();
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isValidFace_V_Line(String line) {
/* 677 */     if (face_V_Matcher != null)
/*     */     {
/* 679 */       face_V_Matcher.reset();
/*     */     }
/*     */     
/* 682 */     face_V_Matcher = face_V_Pattern.matcher(line);
/* 683 */     return face_V_Matcher.matches();
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isValidFaceLine(String line) {
/* 688 */     return (isValidFace_V_VT_VN_Line(line) || isValidFace_V_VT_Line(line) || isValidFace_V_VN_Line(line) || 
/* 689 */       isValidFace_V_Line(line));
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isValidGroupObjectLine(String line) {
/* 694 */     if (groupObjectMatcher != null)
/*     */     {
/* 696 */       groupObjectMatcher.reset();
/*     */     }
/*     */     
/* 699 */     groupObjectMatcher = groupObjectPattern.matcher(line);
/* 700 */     return groupObjectMatcher.matches();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getType() {
/* 706 */     return "obj";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderAllLine(int startLine, int maxLine) {
/* 712 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 713 */     BufferBuilder builder = tessellator.func_178180_c();
/*     */     
/* 715 */     builder.func_181668_a(1, DefaultVertexFormats.field_181705_e);
/* 716 */     renderAllLine(tessellator, startLine, maxLine);
/* 717 */     tessellator.func_78381_a();
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderAllLine(Tessellator tessellator, int startLine, int maxLine) {
/* 722 */     int lineCnt = 0;
/* 723 */     BufferBuilder builder = tessellator.func_178180_c();
/*     */     
/* 725 */     for (W_GroupObject groupObject : this.groupObjects) {
/*     */       
/* 727 */       if (groupObject.faces.size() > 0)
/*     */       {
/* 729 */         for (W_Face face : groupObject.faces) {
/*     */           
/* 731 */           for (int i = 0; i < face.vertices.length / 3; i++) {
/*     */             
/* 733 */             W_Vertex v1 = face.vertices[i * 3 + 0];
/* 734 */             W_Vertex v2 = face.vertices[i * 3 + 1];
/* 735 */             W_Vertex v3 = face.vertices[i * 3 + 2];
/*     */             
/* 737 */             lineCnt++;
/* 738 */             if (lineCnt > maxLine) {
/*     */               return;
/*     */             }
/*     */             
/* 742 */             builder.func_181662_b(v1.x, v1.y, v1.z).func_181675_d();
/* 743 */             builder.func_181662_b(v2.x, v2.y, v2.z).func_181675_d();
/*     */             
/* 745 */             lineCnt++;
/* 746 */             if (lineCnt > maxLine) {
/*     */               return;
/*     */             }
/*     */             
/* 750 */             builder.func_181662_b(v2.x, v2.y, v2.z).func_181675_d();
/* 751 */             builder.func_181662_b(v3.x, v3.y, v3.z).func_181675_d();
/*     */             
/* 753 */             lineCnt++;
/* 754 */             if (lineCnt > maxLine) {
/*     */               return;
/*     */             }
/*     */             
/* 758 */             builder.func_181662_b(v3.x, v3.y, v3.z).func_181675_d();
/* 759 */             builder.func_181662_b(v1.x, v1.y, v1.z).func_181675_d();
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getVertexNum() {
/* 769 */     return this.vertices.size();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFaceNum() {
/* 775 */     return getVertexNum() / 3;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderAll(int startFace, int maxFace) {
/* 781 */     if (startFace < 0)
/*     */     {
/* 783 */       startFace = 0;
/*     */     }
/*     */     
/* 786 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 787 */     BufferBuilder builder = tessellator.func_178180_c();
/*     */     
/* 789 */     builder.func_181668_a(4, DefaultVertexFormats.field_181710_j);
/* 790 */     renderAll(tessellator, startFace, maxFace);
/* 791 */     tessellator.func_78381_a();
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderAll(Tessellator tessellator, int startFace, int maxLine) {
/* 796 */     int faceCnt = 0;
/* 797 */     for (W_GroupObject groupObject : this.groupObjects) {
/*     */       
/* 799 */       if (groupObject.faces.size() > 0)
/*     */       {
/* 801 */         for (W_Face face : groupObject.faces) {
/*     */           
/* 803 */           faceCnt++;
/* 804 */           if (faceCnt >= startFace) {
/*     */             
/* 806 */             if (faceCnt > maxLine)
/*     */               return; 
/* 808 */             face.addFaceForRender(tessellator);
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\wrapper\modelloader\W_WavefrontObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */