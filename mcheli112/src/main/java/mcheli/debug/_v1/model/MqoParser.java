package mcheli.debug._v1.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import mcheli.__helper.debug.DebugException;

public class MqoParser {
  public static MqoModel parse(InputStream inputStream) throws DebugException {
    List<_Vertex> vertices = new ArrayList<>();
    List<_Face> faceList = new ArrayList<>();
    List<_GroupObject> groupObjects = new ArrayList<>();
    int vertexNum = 0;
    int faceNum = 0;
    BufferedReader reader = null;
    String currentLine = null;
    int lineCount = 0;
    try {
      reader = new BufferedReader(new InputStreamReader(inputStream));
      while ((currentLine = reader.readLine()) != null) {
        lineCount++;
        currentLine = currentLine.replaceAll("\\s+", " ").trim();
        if (isValidGroupObjectLine(currentLine)) {
          _GroupObject.Builder group = parseGroupObject(currentLine, lineCount);
          if (group != null) {
            vertices.clear();
            faceList.clear();
            int vertexNum2 = 0;
            boolean mirror = false;
            double facet = Math.cos(0.785398163375D);
            boolean shading = false;
            while ((currentLine = reader.readLine()) != null) {
              lineCount++;
              currentLine = currentLine.replaceAll("\\s+", " ").trim();
              if (currentLine.equalsIgnoreCase("mirror 1"))
                mirror = true; 
              if (currentLine.equalsIgnoreCase("shading 1"))
                shading = true; 
              String[] s = currentLine.split(" ");
              if (s.length == 2 && s[0].equalsIgnoreCase("facet"))
                facet = Math.cos(Double.parseDouble(s[1]) * 3.1415926535D / 180.0D); 
              if (isValidVertexLine(currentLine)) {
                vertexNum2 = Integer.valueOf(currentLine.split(" ")[1]).intValue();
                break;
              } 
            } 
            if (vertexNum2 > 0) {
              while ((currentLine = reader.readLine()) != null) {
                lineCount++;
                currentLine = currentLine.replaceAll("\\s+", " ").trim();
                String[] s = currentLine.split(" ");
                if (s.length == 3) {
                  _Vertex v = new _Vertex(Float.valueOf(s[0]).floatValue() / 100.0F, Float.valueOf(s[1]).floatValue() / 100.0F, Float.valueOf(s[2]).floatValue() / 100.0F);
                  vertices.add(v);
                  vertexNum2--;
                  if (vertexNum2 <= 0)
                    break; 
                  continue;
                } 
                if (s.length > 0)
                  throw new DebugException("format error : line=" + lineCount); 
              } 
              int faceNum2 = 0;
              while ((currentLine = reader.readLine()) != null) {
                lineCount++;
                currentLine = currentLine.replaceAll("\\s+", " ").trim();
                if (isValidFaceLine(currentLine)) {
                  faceNum2 = Integer.valueOf(currentLine.split(" ")[1]).intValue();
                  break;
                } 
              } 
              if (faceNum2 > 0) {
                while ((currentLine = reader.readLine()) != null) {
                  lineCount++;
                  currentLine = currentLine.replaceAll("\\s+", " ").trim();
                  String[] s = currentLine.split(" ");
                  if (s.length > 2) {
                    if (Integer.valueOf(s[0]).intValue() >= 3) {
                      _Face[] faces = parseFace(currentLine, lineCount, mirror, vertices);
                      for (_Face face : faces)
                        faceList.add(face); 
                    } 
                    faceNum2--;
                    if (faceNum2 <= 0)
                      break; 
                    continue;
                  } 
                  if (s.length > 2 && Integer.valueOf(s[0]).intValue() != 3)
                    throw new DebugException("found face is not triangle : line=" + lineCount); 
                } 
                for (_Face face : faceList)
                  group.addFace(face.calcVerticesNormal(faceList, shading, facet)); 
              } 
            } 
            vertexNum += vertices.size();
            faceNum += group.faceSize();
            vertices.clear();
            faceList.clear();
            groupObjects.add(group.build());
          } 
        } 
      } 
      return new MqoModel(groupObjects, vertexNum, faceNum);
    } catch (IOException e) {
      throw new DebugException("IO Exception reading model format.", e);
    } finally {
      try {
        reader.close();
      } catch (IOException iOException) {}
      try {
        inputStream.close();
      } catch (IOException iOException) {}
    } 
  }
  
  private static _GroupObject.Builder parseGroupObject(String line, int lineCount) throws DebugException {
    _GroupObject.Builder group = null;
    if (isValidGroupObjectLine(line)) {
      String[] s = line.split(" ");
      String trimmedLine = s[1].substring(1, s[1].length() - 1);
      if (trimmedLine.length() > 0)
        group = _GroupObject.builder().name(trimmedLine); 
    } else {
      throw new DebugException("Error parsing entry ('" + line + "', line " + lineCount + ") in file - Incorrect format");
    } 
    return group;
  }
  
  private static _Face[] parseFace(String line, int lineCount, boolean mirror, List<_Vertex> vertices) {
    _TextureCoord[] texCoords1, texCoords2;
    String[] s = line.split("[ VU)(M]+");
    int vnum = Integer.valueOf(s[0]).intValue();
    if (vnum != 3 && vnum != 4)
      return new _Face[0]; 
    if (vnum == 3) {
      _TextureCoord[] texCoords;
      int[] verticesID = { Integer.valueOf(s[3]).intValue(), Integer.valueOf(s[2]).intValue(), Integer.valueOf(s[1]).intValue() };
      _Vertex[] verts = { vertices.get(verticesID[0]), vertices.get(verticesID[1]), vertices.get(verticesID[2]) };
      if (s.length >= 11) {
        texCoords = new _TextureCoord[] { new _TextureCoord(Float.valueOf(s[9]).floatValue(), Float.valueOf(s[10]).floatValue()), new _TextureCoord(Float.valueOf(s[7]).floatValue(), Float.valueOf(s[8]).floatValue()), new _TextureCoord(Float.valueOf(s[5]).floatValue(), Float.valueOf(s[6]).floatValue()) };
      } else {
        texCoords = new _TextureCoord[] { new _TextureCoord(0.0F, 0.0F), new _TextureCoord(0.0F, 0.0F), new _TextureCoord(0.0F, 0.0F) };
      } 
      return new _Face[] { new _Face(verticesID, verts, texCoords) };
    } 
    int[] verticesID1 = { Integer.valueOf(s[3]).intValue(), Integer.valueOf(s[2]).intValue(), Integer.valueOf(s[1]).intValue() };
    _Vertex[] verts1 = { vertices.get(verticesID1[0]), vertices.get(verticesID1[1]), vertices.get(verticesID1[2]) };
    if (s.length >= 12) {
      texCoords1 = new _TextureCoord[] { new _TextureCoord(Float.valueOf(s[10]).floatValue(), Float.valueOf(s[11]).floatValue()), new _TextureCoord(Float.valueOf(s[8]).floatValue(), Float.valueOf(s[9]).floatValue()), new _TextureCoord(Float.valueOf(s[6]).floatValue(), Float.valueOf(s[7]).floatValue()) };
    } else {
      texCoords1 = new _TextureCoord[] { new _TextureCoord(0.0F, 0.0F), new _TextureCoord(0.0F, 0.0F), new _TextureCoord(0.0F, 0.0F) };
    } 
    int[] verticesID2 = { Integer.valueOf(s[4]).intValue(), Integer.valueOf(s[3]).intValue(), Integer.valueOf(s[1]).intValue() };
    _Vertex[] verts2 = { vertices.get(verticesID2[0]), vertices.get(verticesID2[1]), vertices.get(verticesID2[2]) };
    if (s.length >= 14) {
      texCoords2 = new _TextureCoord[] { new _TextureCoord(Float.valueOf(s[12]).floatValue(), Float.valueOf(s[13]).floatValue()), new _TextureCoord(Float.valueOf(s[10]).floatValue(), Float.valueOf(s[11]).floatValue()), new _TextureCoord(Float.valueOf(s[6]).floatValue(), Float.valueOf(s[7]).floatValue()) };
    } else {
      texCoords2 = new _TextureCoord[] { new _TextureCoord(0.0F, 0.0F), new _TextureCoord(0.0F, 0.0F), new _TextureCoord(0.0F, 0.0F) };
    } 
    return new _Face[] { new _Face(verticesID1, verts1, texCoords1), new _Face(verticesID2, verts2, texCoords2) };
  }
  
  private static boolean isValidGroupObjectLine(String line) {
    String[] s = line.split(" ");
    if (s.length < 2 || !s[0].equals("Object"))
      return false; 
    if (s[1].length() < 4 || s[1].charAt(0) != '"')
      return false; 
    return true;
  }
  
  private static boolean isValidVertexLine(String line) {
    String[] s = line.split(" ");
    if (!s[0].equals("vertex"))
      return false; 
    return true;
  }
  
  private static boolean isValidFaceLine(String line) {
    String[] s = line.split(" ");
    if (!s[0].equals("face"))
      return false; 
    return true;
  }
}
