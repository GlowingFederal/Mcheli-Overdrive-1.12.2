/*     */ package mcheli.debug._v1.model;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.regex.Pattern;
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
/*     */ public class ObjParser
/*     */ {
/*  23 */   private static Pattern vertexPattern = Pattern.compile("(v( (\\-){0,1}\\d+\\.\\d+){3,4} *\\n)|(v( (\\-){0,1}\\d+\\.\\d+){3,4} *$)");
/*     */   
/*  25 */   private static Pattern vertexNormalPattern = Pattern.compile("(vn( (\\-){0,1}\\d+\\.\\d+){3,4} *\\n)|(vn( (\\-){0,1}\\d+\\.\\d+){3,4} *$)");
/*     */   
/*  27 */   private static Pattern textureCoordinatePattern = Pattern.compile("(vt( (\\-){0,1}\\d+\\.\\d+){2,3} *\\n)|(vt( (\\-){0,1}\\d+\\.\\d+){2,3} *$)");
/*     */   
/*  29 */   private static Pattern face_V_VT_VN_Pattern = Pattern.compile("(f( \\d+/\\d+/\\d+){3,4} *\\n)|(f( \\d+/\\d+/\\d+){3,4} *$)");
/*  30 */   private static Pattern face_V_VT_Pattern = Pattern.compile("(f( \\d+/\\d+){3,4} *\\n)|(f( \\d+/\\d+){3,4} *$)");
/*  31 */   private static Pattern face_V_VN_Pattern = Pattern.compile("(f( \\d+//\\d+){3,4} *\\n)|(f( \\d+//\\d+){3,4} *$)");
/*  32 */   private static Pattern face_V_Pattern = Pattern.compile("(f( \\d+){3,4} *\\n)|(f( \\d+){3,4} *$)");
/*     */   
/*  34 */   private static Pattern groupObjectPattern = Pattern.compile("([go]( [-\\$\\w\\d]+) *\\n)|([go]( [-\\$\\w\\d]+) *$)");
/*     */ 
/*     */   
/*     */   public static ObjModel parse(InputStream inputStream) throws DebugException {
/*  38 */     List<_Vertex> vertices = new ArrayList<>();
/*  39 */     List<_TextureCoord> textureCoordinates = new ArrayList<>();
/*  40 */     List<_Vertex> vertexNormals = new ArrayList<>();
/*  41 */     List<_GroupObject> groupObjects = new ArrayList<>();
/*  42 */     _GroupObject.Builder group = null;
/*  43 */     int vertexNum = 0;
/*  44 */     int faceNum = 0;
/*  45 */     BufferedReader reader = null;
/*  46 */     String currentLine = null;
/*  47 */     int lineCount = 0;
/*     */ 
/*     */     
/*     */     try {
/*  51 */       reader = new BufferedReader(new InputStreamReader(inputStream));
/*     */       
/*  53 */       while ((currentLine = reader.readLine()) != null) {
/*     */         
/*  55 */         lineCount++;
/*  56 */         currentLine = currentLine.replaceAll("\\s+", " ").trim();
/*     */         
/*  58 */         if (!currentLine.startsWith("#") && currentLine.length() != 0) {
/*     */           
/*  60 */           if (currentLine.startsWith("v ")) {
/*     */             
/*  62 */             _Vertex vertex = parseVertex(currentLine, lineCount);
/*     */             
/*  64 */             if (vertex != null)
/*     */             {
/*     */               
/*  67 */               vertices.add(vertex); } 
/*     */             continue;
/*     */           } 
/*  70 */           if (currentLine.startsWith("vn ")) {
/*     */             
/*  72 */             _Vertex vertex = parseVertexNormal(currentLine, lineCount);
/*     */             
/*  74 */             if (vertex != null)
/*     */             {
/*  76 */               vertexNormals.add(vertex); } 
/*     */             continue;
/*     */           } 
/*  79 */           if (currentLine.startsWith("vt ")) {
/*     */             
/*  81 */             _TextureCoord textureCoordinate = parseTextureCoordinate(currentLine, lineCount);
/*     */             
/*  83 */             if (textureCoordinate != null)
/*     */             {
/*  85 */               textureCoordinates.add(textureCoordinate); } 
/*     */             continue;
/*     */           } 
/*  88 */           if (currentLine.startsWith("f ")) {
/*     */             
/*  90 */             if (group == null)
/*     */             {
/*  92 */               group = _GroupObject.builder().name("Default");
/*     */             }
/*     */             
/*  95 */             _Face face = parseFace(currentLine, lineCount, vertices, textureCoordinates, vertexNormals);
/*     */             
/*  97 */             if (face != null)
/*     */             {
/*  99 */               group.addFace(face); } 
/*     */             continue;
/*     */           } 
/* 102 */           if ((currentLine.startsWith("g ") | currentLine.startsWith("o ")) != 0 && currentLine
/* 103 */             .charAt(2) == '$') {
/*     */             
/* 105 */             _GroupObject.Builder group2 = parseGroupObject(currentLine, lineCount);
/*     */             
/* 107 */             if (group2 != null)
/*     */             {
/* 109 */               if (group != null)
/*     */               {
/* 111 */                 groupObjects.add(group.build());
/*     */               }
/*     */             }
/*     */             
/* 115 */             group = group2;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 120 */       groupObjects.add(group.build());
/* 121 */       return new ObjModel(groupObjects, vertexNum, faceNum);
/*     */     }
/* 123 */     catch (IOException e) {
/*     */       
/* 125 */       throw new DebugException("IO Exception reading model format", e);
/*     */     } finally {
/*     */ 
/*     */       
/*     */       try {
/*     */ 
/*     */ 
/*     */         
/* 133 */         reader.close();
/*     */       }
/* 135 */       catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 141 */         inputStream.close();
/*     */       }
/* 143 */       catch (IOException iOException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static _Vertex parseVertex(String line, int lineCount) throws DebugException {
/* 151 */     _Vertex vertex = null;
/*     */     
/* 153 */     if (isValidVertexLine(line)) {
/*     */       
/* 155 */       line = line.substring(line.indexOf(" ") + 1);
/* 156 */       String[] tokens = line.split(" ");
/*     */ 
/*     */       
/*     */       try {
/* 160 */         if (tokens.length == 2)
/*     */         {
/* 162 */           return new _Vertex(Float.parseFloat(tokens[0]), Float.parseFloat(tokens[1]));
/*     */         }
/*     */         
/* 165 */         if (tokens.length == 3)
/*     */         {
/* 167 */           return new _Vertex(Float.parseFloat(tokens[0]), Float.parseFloat(tokens[1]), 
/* 168 */               Float.parseFloat(tokens[2]));
/*     */         }
/*     */       }
/* 171 */       catch (NumberFormatException e) {
/*     */         
/* 173 */         throw new DebugException(String.format("Number formatting error at line %d", new Object[] {
/*     */                 
/* 175 */                 Integer.valueOf(lineCount)
/*     */               }), e);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 181 */       throw new DebugException("Error parsing entry ('" + line + "', line " + lineCount + ") in file - Incorrect format");
/*     */     } 
/*     */ 
/*     */     
/* 185 */     return vertex;
/*     */   }
/*     */ 
/*     */   
/*     */   private static _Vertex parseVertexNormal(String line, int lineCount) throws DebugException {
/* 190 */     _Vertex vertexNormal = null;
/*     */     
/* 192 */     if (isValidVertexNormalLine(line)) {
/*     */       
/* 194 */       line = line.substring(line.indexOf(" ") + 1);
/* 195 */       String[] tokens = line.split(" ");
/*     */ 
/*     */       
/*     */       try {
/* 199 */         if (tokens.length == 3)
/*     */         {
/* 201 */           return new _Vertex(Float.parseFloat(tokens[0]), Float.parseFloat(tokens[1]), 
/* 202 */               Float.parseFloat(tokens[2]));
/*     */         }
/*     */       }
/* 205 */       catch (NumberFormatException e) {
/*     */         
/* 207 */         throw new DebugException(String.format("Number formatting error at line %d", new Object[] {
/*     */                 
/* 209 */                 Integer.valueOf(lineCount)
/*     */               }), e);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 215 */       throw new DebugException("Error parsing entry ('" + line + "', line " + lineCount + ") in file - Incorrect format");
/*     */     } 
/*     */ 
/*     */     
/* 219 */     return vertexNormal;
/*     */   }
/*     */ 
/*     */   
/*     */   private static _TextureCoord parseTextureCoordinate(String line, int lineCount) throws DebugException {
/* 224 */     _TextureCoord textureCoordinate = null;
/*     */     
/* 226 */     if (isValidTextureCoordinateLine(line)) {
/*     */       
/* 228 */       line = line.substring(line.indexOf(" ") + 1);
/* 229 */       String[] tokens = line.split(" ");
/*     */ 
/*     */       
/*     */       try {
/* 233 */         if (tokens.length == 2) {
/* 234 */           return new _TextureCoord(Float.parseFloat(tokens[0]), 1.0F - Float.parseFloat(tokens[1]));
/*     */         }
/* 236 */         if (tokens.length == 3)
/*     */         {
/* 238 */           return new _TextureCoord(Float.parseFloat(tokens[0]), 1.0F - Float.parseFloat(tokens[1]), 
/* 239 */               Float.parseFloat(tokens[2]));
/*     */         }
/*     */       }
/* 242 */       catch (NumberFormatException e) {
/*     */         
/* 244 */         throw new DebugException(String.format("Number formatting error at line %d", new Object[] {
/*     */                 
/* 246 */                 Integer.valueOf(lineCount)
/*     */               }), e);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 252 */       throw new DebugException("Error parsing entry ('" + line + "', line " + lineCount + ") in file - Incorrect format");
/*     */     } 
/*     */ 
/*     */     
/* 256 */     return textureCoordinate;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static _Face parseFace(String line, int lineCount, List<_Vertex> vertices, List<_TextureCoord> textureCoordinates, List<_Vertex> vertexNormals) throws DebugException {
/* 262 */     _Face face = null;
/*     */     
/* 264 */     if (isValidFaceLine(line)) {
/*     */       
/* 266 */       String trimmedLine = line.substring(line.indexOf(" ") + 1);
/* 267 */       String[] tokens = trimmedLine.split(" ");
/* 268 */       String[] subTokens = null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 296 */       int[] verticesID = new int[tokens.length];
/* 297 */       _Vertex[] verts = new _Vertex[tokens.length];
/* 298 */       _TextureCoord[] texCoords = new _TextureCoord[tokens.length];
/* 299 */       _Vertex[] normals = new _Vertex[tokens.length];
/*     */       
/* 301 */       if (isValidFace_V_VT_VN_Line(line))
/*     */       {
/* 303 */         for (int i = 0; i < tokens.length; i++) {
/*     */           
/* 305 */           subTokens = tokens[i].split("/");
/*     */           
/* 307 */           verticesID[i] = Integer.parseInt(subTokens[0]) - 1;
/* 308 */           verts[i] = vertices.get(Integer.parseInt(subTokens[0]) - 1);
/* 309 */           texCoords[i] = textureCoordinates.get(Integer.parseInt(subTokens[1]) - 1);
/* 310 */           normals[i] = vertexNormals.get(Integer.parseInt(subTokens[2]) - 1);
/*     */         } 
/*     */         
/* 313 */         face = new _Face(verticesID, verts, normals, texCoords);
/*     */       }
/* 315 */       else if (isValidFace_V_VT_Line(line))
/*     */       {
/* 317 */         for (int i = 0; i < tokens.length; i++) {
/*     */           
/* 319 */           subTokens = tokens[i].split("/");
/*     */           
/* 321 */           verticesID[i] = Integer.parseInt(subTokens[0]) - 1;
/* 322 */           verts[i] = vertices.get(Integer.parseInt(subTokens[0]) - 1);
/* 323 */           texCoords[i] = textureCoordinates.get(Integer.parseInt(subTokens[1]) - 1);
/*     */         } 
/*     */         
/* 326 */         face = new _Face(verticesID, verts, new _Vertex[0], texCoords);
/*     */       }
/* 328 */       else if (isValidFace_V_VN_Line(line))
/*     */       {
/* 330 */         for (int i = 0; i < tokens.length; i++) {
/*     */           
/* 332 */           subTokens = tokens[i].split("//");
/*     */           
/* 334 */           verticesID[i] = Integer.parseInt(subTokens[0]) - 1;
/* 335 */           verts[i] = vertices.get(Integer.parseInt(subTokens[0]) - 1);
/* 336 */           normals[i] = vertexNormals.get(Integer.parseInt(subTokens[2]) - 1);
/*     */         } 
/*     */         
/* 339 */         face = new _Face(verticesID, verts, normals, new _TextureCoord[0]);
/*     */       }
/* 341 */       else if (isValidFace_V_Line(line))
/*     */       {
/* 343 */         for (int i = 0; i < tokens.length; i++) {
/*     */           
/* 345 */           verticesID[i] = Integer.parseInt(tokens[0]) - 1;
/* 346 */           verts[i] = vertices.get(Integer.parseInt(tokens[0]) - 1);
/*     */         } 
/*     */         
/* 349 */         face = new _Face(verticesID, verts, new _Vertex[0], new _TextureCoord[0]);
/*     */       }
/*     */       else
/*     */       {
/* 353 */         throw new DebugException("Error parsing entry ('" + line + "', line " + lineCount + ") in file - Incorrect format");
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 359 */       throw new DebugException("Error parsing entry ('" + line + "', line " + lineCount + ") in file - Incorrect format");
/*     */     } 
/*     */ 
/*     */     
/* 363 */     return face;
/*     */   }
/*     */ 
/*     */   
/*     */   private static _GroupObject.Builder parseGroupObject(String line, int lineCount) throws DebugException {
/* 368 */     _GroupObject.Builder group = null;
/*     */     
/* 370 */     if (isValidGroupObjectLine(line)) {
/*     */       
/* 372 */       String trimmedLine = line.substring(line.indexOf(" ") + 1);
/*     */       
/* 374 */       if (trimmedLine.length() > 0)
/*     */       {
/* 376 */         group = _GroupObject.builder().name(trimmedLine);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 381 */       throw new DebugException("Error parsing entry ('" + line + "', line " + lineCount + ") in file - Incorrect format");
/*     */     } 
/*     */ 
/*     */     
/* 385 */     return group;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isValidVertexLine(String line) {
/* 390 */     return vertexPattern.matcher(line).matches();
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isValidVertexNormalLine(String line) {
/* 395 */     return vertexNormalPattern.matcher(line).matches();
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isValidTextureCoordinateLine(String line) {
/* 400 */     return textureCoordinatePattern.matcher(line).matches();
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isValidFace_V_VT_VN_Line(String line) {
/* 405 */     return face_V_VT_VN_Pattern.matcher(line).matches();
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isValidFace_V_VT_Line(String line) {
/* 410 */     return face_V_VT_Pattern.matcher(line).matches();
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isValidFace_V_VN_Line(String line) {
/* 415 */     return face_V_VN_Pattern.matcher(line).matches();
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isValidFace_V_Line(String line) {
/* 420 */     return face_V_Pattern.matcher(line).matches();
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isValidFaceLine(String line) {
/* 425 */     return (isValidFace_V_VT_VN_Line(line) || isValidFace_V_VT_Line(line) || isValidFace_V_VN_Line(line) || 
/* 426 */       isValidFace_V_Line(line));
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isValidGroupObjectLine(String line) {
/* 431 */     return groupObjectPattern.matcher(line).matches();
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\debug\_v1\model\ObjParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */