/*     */ package mcheli.debug._v1.model;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import mcheli.__helper.debug.DebugException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MqoParser
/*     */ {
/*     */   public static MqoModel parse(InputStream inputStream) throws DebugException {
/*  23 */     List<_Vertex> vertices = new ArrayList<>();
/*  24 */     List<_Face> faceList = new ArrayList<>();
/*  25 */     List<_GroupObject> groupObjects = new ArrayList<>();
/*  26 */     int vertexNum = 0;
/*  27 */     int faceNum = 0;
/*     */     
/*  29 */     BufferedReader reader = null;
/*  30 */     String currentLine = null;
/*  31 */     int lineCount = 0;
/*     */ 
/*     */     
/*     */     try {
/*  35 */       reader = new BufferedReader(new InputStreamReader(inputStream));
/*     */       
/*  37 */       while ((currentLine = reader.readLine()) != null) {
/*     */         
/*  39 */         lineCount++;
/*  40 */         currentLine = currentLine.replaceAll("\\s+", " ").trim();
/*     */         
/*  42 */         if (isValidGroupObjectLine(currentLine)) {
/*     */           
/*  44 */           _GroupObject.Builder group = parseGroupObject(currentLine, lineCount);
/*     */           
/*  46 */           if (group != null) {
/*     */ 
/*     */             
/*  49 */             vertices.clear();
/*  50 */             faceList.clear();
/*     */             
/*  52 */             int vertexNum2 = 0;
/*  53 */             boolean mirror = false;
/*  54 */             double facet = Math.cos(0.785398163375D);
/*  55 */             boolean shading = false;
/*     */             
/*  57 */             while ((currentLine = reader.readLine()) != null) {
/*     */               
/*  59 */               lineCount++;
/*  60 */               currentLine = currentLine.replaceAll("\\s+", " ").trim();
/*     */               
/*  62 */               if (currentLine.equalsIgnoreCase("mirror 1"))
/*     */               {
/*  64 */                 mirror = true;
/*     */               }
/*     */               
/*  67 */               if (currentLine.equalsIgnoreCase("shading 1"))
/*     */               {
/*  69 */                 shading = true;
/*     */               }
/*     */               
/*  72 */               String[] s = currentLine.split(" ");
/*     */               
/*  74 */               if (s.length == 2 && s[0].equalsIgnoreCase("facet"))
/*     */               {
/*  76 */                 facet = Math.cos(Double.parseDouble(s[1]) * 3.1415926535D / 180.0D);
/*     */               }
/*     */               
/*  79 */               if (isValidVertexLine(currentLine)) {
/*     */                 
/*  81 */                 vertexNum2 = Integer.valueOf(currentLine.split(" ")[1]).intValue();
/*     */                 
/*     */                 break;
/*     */               } 
/*     */             } 
/*  86 */             if (vertexNum2 > 0) {
/*     */               
/*  88 */               while ((currentLine = reader.readLine()) != null) {
/*     */                 
/*  90 */                 lineCount++;
/*  91 */                 currentLine = currentLine.replaceAll("\\s+", " ").trim();
/*     */                 
/*  93 */                 String[] s = currentLine.split(" ");
/*     */                 
/*  95 */                 if (s.length == 3) {
/*     */ 
/*     */ 
/*     */                   
/*  99 */                   _Vertex v = new _Vertex(Float.valueOf(s[0]).floatValue() / 100.0F, Float.valueOf(s[1]).floatValue() / 100.0F, Float.valueOf(s[2]).floatValue() / 100.0F);
/*     */ 
/*     */                   
/* 102 */                   vertices.add(v);
/*     */                   
/* 104 */                   vertexNum2--;
/*     */                   
/* 106 */                   if (vertexNum2 <= 0) {
/*     */                     break;
/*     */                   }
/*     */                   continue;
/*     */                 } 
/* 111 */                 if (s.length > 0)
/*     */                 {
/* 113 */                   throw new DebugException("format error : line=" + lineCount);
/*     */                 }
/*     */               } 
/*     */               
/* 117 */               int faceNum2 = 0;
/*     */               
/* 119 */               while ((currentLine = reader.readLine()) != null) {
/*     */                 
/* 121 */                 lineCount++;
/* 122 */                 currentLine = currentLine.replaceAll("\\s+", " ").trim();
/*     */                 
/* 124 */                 if (isValidFaceLine(currentLine)) {
/*     */                   
/* 126 */                   faceNum2 = Integer.valueOf(currentLine.split(" ")[1]).intValue();
/*     */                   
/*     */                   break;
/*     */                 } 
/*     */               } 
/* 131 */               if (faceNum2 > 0) {
/*     */                 
/* 133 */                 while ((currentLine = reader.readLine()) != null) {
/*     */                   
/* 135 */                   lineCount++;
/* 136 */                   currentLine = currentLine.replaceAll("\\s+", " ").trim();
/*     */                   
/* 138 */                   String[] s = currentLine.split(" ");
/*     */                   
/* 140 */                   if (s.length > 2) {
/*     */                     
/* 142 */                     if (Integer.valueOf(s[0]).intValue() >= 3) {
/*     */                       
/* 144 */                       _Face[] faces = parseFace(currentLine, lineCount, mirror, vertices);
/*     */                       
/* 146 */                       for (_Face face : faces)
/*     */                       {
/* 148 */                         faceList.add(face);
/*     */                       }
/*     */                     } 
/*     */                     
/* 152 */                     faceNum2--;
/*     */                     
/* 154 */                     if (faceNum2 <= 0) {
/*     */                       break;
/*     */                     }
/*     */                     continue;
/*     */                   } 
/* 159 */                   if (s.length > 2 && Integer.valueOf(s[0]).intValue() != 3)
/*     */                   {
/* 161 */                     throw new DebugException("found face is not triangle : line=" + lineCount);
/*     */                   }
/*     */                 } 
/*     */                 
/* 165 */                 for (_Face face : faceList)
/*     */                 {
/* 167 */                   group.addFace(face.calcVerticesNormal(faceList, shading, facet));
/*     */                 }
/*     */               } 
/*     */             } 
/*     */             
/* 172 */             vertexNum += vertices.size();
/* 173 */             faceNum += group.faceSize();
/* 174 */             vertices.clear();
/* 175 */             faceList.clear();
/*     */             
/* 177 */             groupObjects.add(group.build());
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 182 */       return new MqoModel(groupObjects, vertexNum, faceNum);
/*     */     }
/* 184 */     catch (IOException e) {
/*     */       
/* 186 */       throw new DebugException("IO Exception reading model format.", e);
/*     */     } finally {
/*     */ 
/*     */       
/*     */       try {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 195 */         reader.close();
/*     */       }
/* 197 */       catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 203 */         inputStream.close();
/*     */       }
/* 205 */       catch (IOException iOException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static _GroupObject.Builder parseGroupObject(String line, int lineCount) throws DebugException {
/* 213 */     _GroupObject.Builder group = null;
/*     */     
/* 215 */     if (isValidGroupObjectLine(line)) {
/*     */       
/* 217 */       String[] s = line.split(" ");
/* 218 */       String trimmedLine = s[1].substring(1, s[1].length() - 1);
/*     */       
/* 220 */       if (trimmedLine.length() > 0)
/*     */       {
/* 222 */         group = _GroupObject.builder().name(trimmedLine);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 227 */       throw new DebugException("Error parsing entry ('" + line + "', line " + lineCount + ") in file - Incorrect format");
/*     */     } 
/*     */ 
/*     */     
/* 231 */     return group;
/*     */   }
/*     */   
/*     */   private static _Face[] parseFace(String line, int lineCount, boolean mirror, List<_Vertex> vertices) {
/*     */     _TextureCoord[] texCoords1, texCoords2;
/* 236 */     String[] s = line.split("[ VU)(M]+");
/* 237 */     int vnum = Integer.valueOf(s[0]).intValue();
/*     */     
/* 239 */     if (vnum != 3 && vnum != 4)
/*     */     {
/* 241 */       return new _Face[0];
/*     */     }
/*     */     
/* 244 */     if (vnum == 3) {
/*     */       _TextureCoord[] texCoords;
/*     */ 
/*     */ 
/*     */       
/* 249 */       int[] verticesID = { Integer.valueOf(s[3]).intValue(), Integer.valueOf(s[2]).intValue(), Integer.valueOf(s[1]).intValue() };
/*     */ 
/*     */ 
/*     */       
/* 253 */       _Vertex[] verts = { vertices.get(verticesID[0]), vertices.get(verticesID[1]), vertices.get(verticesID[2]) };
/*     */ 
/*     */ 
/*     */       
/* 257 */       if (s.length >= 11) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 263 */         texCoords = new _TextureCoord[] { new _TextureCoord(Float.valueOf(s[9]).floatValue(), Float.valueOf(s[10]).floatValue()), new _TextureCoord(Float.valueOf(s[7]).floatValue(), Float.valueOf(s[8]).floatValue()), new _TextureCoord(Float.valueOf(s[5]).floatValue(), Float.valueOf(s[6]).floatValue()) };
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 268 */         texCoords = new _TextureCoord[] { new _TextureCoord(0.0F, 0.0F), new _TextureCoord(0.0F, 0.0F), new _TextureCoord(0.0F, 0.0F) };
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 274 */       return new _Face[] { new _Face(verticesID, verts, texCoords) };
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 282 */     int[] verticesID1 = { Integer.valueOf(s[3]).intValue(), Integer.valueOf(s[2]).intValue(), Integer.valueOf(s[1]).intValue() };
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 287 */     _Vertex[] verts1 = { vertices.get(verticesID1[0]), vertices.get(verticesID1[1]), vertices.get(verticesID1[2]) };
/*     */ 
/*     */ 
/*     */     
/* 291 */     if (s.length >= 12) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 297 */       texCoords1 = new _TextureCoord[] { new _TextureCoord(Float.valueOf(s[10]).floatValue(), Float.valueOf(s[11]).floatValue()), new _TextureCoord(Float.valueOf(s[8]).floatValue(), Float.valueOf(s[9]).floatValue()), new _TextureCoord(Float.valueOf(s[6]).floatValue(), Float.valueOf(s[7]).floatValue()) };
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 302 */       texCoords1 = new _TextureCoord[] { new _TextureCoord(0.0F, 0.0F), new _TextureCoord(0.0F, 0.0F), new _TextureCoord(0.0F, 0.0F) };
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 310 */     int[] verticesID2 = { Integer.valueOf(s[4]).intValue(), Integer.valueOf(s[3]).intValue(), Integer.valueOf(s[1]).intValue() };
/*     */ 
/*     */ 
/*     */     
/* 314 */     _Vertex[] verts2 = { vertices.get(verticesID2[0]), vertices.get(verticesID2[1]), vertices.get(verticesID2[2]) };
/*     */ 
/*     */ 
/*     */     
/* 318 */     if (s.length >= 14) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 324 */       texCoords2 = new _TextureCoord[] { new _TextureCoord(Float.valueOf(s[12]).floatValue(), Float.valueOf(s[13]).floatValue()), new _TextureCoord(Float.valueOf(s[10]).floatValue(), Float.valueOf(s[11]).floatValue()), new _TextureCoord(Float.valueOf(s[6]).floatValue(), Float.valueOf(s[7]).floatValue()) };
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 329 */       texCoords2 = new _TextureCoord[] { new _TextureCoord(0.0F, 0.0F), new _TextureCoord(0.0F, 0.0F), new _TextureCoord(0.0F, 0.0F) };
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 335 */     return new _Face[] { new _Face(verticesID1, verts1, texCoords1), new _Face(verticesID2, verts2, texCoords2) };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isValidGroupObjectLine(String line) {
/* 343 */     String[] s = line.split(" ");
/*     */     
/* 345 */     if (s.length < 2 || !s[0].equals("Object"))
/*     */     {
/* 347 */       return false;
/*     */     }
/*     */     
/* 350 */     if (s[1].length() < 4 || s[1].charAt(0) != '"')
/*     */     {
/* 352 */       return false;
/*     */     }
/*     */     
/* 355 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isValidVertexLine(String line) {
/* 360 */     String[] s = line.split(" ");
/*     */     
/* 362 */     if (!s[0].equals("vertex"))
/*     */     {
/* 364 */       return false;
/*     */     }
/* 366 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isValidFaceLine(String line) {
/* 371 */     String[] s = line.split(" ");
/*     */     
/* 373 */     if (!s[0].equals("face"))
/*     */     {
/* 375 */       return false;
/*     */     }
/* 377 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\debug\_v1\model\MqoParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */