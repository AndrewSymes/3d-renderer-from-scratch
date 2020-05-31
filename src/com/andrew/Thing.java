package com.andrew;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Thing{
    LinkedList<Triangle> triangles = new LinkedList<>();
    Vector3 orientation;
    Vector3 position;
    Thing(double[][][] verts){
        for(int t = 0; t < verts.length; t++){
            triangles.add(
                    new Triangle(
                            new Vector3(verts[t][0][0], verts[t][0][1], verts[t][0][2]),
                            new Vector3(verts[t][1][0], verts[t][1][1], verts[t][1][2]),
                            new Vector3(verts[t][2][0], verts[t][2][1], verts[t][2][2]))
            );
        }
        position = new Vector3(0,0,5);
        orientation = new Vector3(0,0,0);
    }

    Thing(LinkedList<Triangle> t){
        triangles = t;
    }
    Thing(String file) throws FileNotFoundException {
        File obj = new File(file);
        Scanner scan = new Scanner(obj);
        ArrayList<Vector3> verts = new ArrayList<>();
        position = new Vector3(0,0,0);
        orientation = new Vector3(0,0,0);

        while(scan.hasNextLine()){
            String line = scan.nextLine();
            if(line.startsWith("v")){
                line = line.substring(2);
                String[] a = line.split(" ");
                Vector3 b = new Vector3(Double.parseDouble(a[0]),Double.parseDouble(a[1]),Double.parseDouble(a[2]));
                verts.add(b);
            }
            if(line.startsWith("f")){
                line = line.substring(2);
                String[] a = line.split(" ");
                Triangle b = new Triangle(
                        verts.get(Integer.parseInt(a[0])-1),
                        verts.get(Integer.parseInt(a[1])-1),
                        verts.get(Integer.parseInt(a[2])-1)
                );
                triangles.add(b);
            }
        }
    }

    Thing translate(){
        LinkedList<Triangle> newTriangles = new LinkedList<>();
        for(Triangle tri : triangles){
            Vector3 tp1 = tri.p1.rotateXYZ(orientation);
            Vector3 tp2 = tri.p2.rotateXYZ(orientation);
            Vector3 tp3 = tri.p3.rotateXYZ(orientation);
            tp1.x +=position.x;tp2.x +=position.x;tp3.x +=position.x;
            tp1.y +=position.y;tp2.y +=position.y;tp3.y +=position.y;
            tp1.z +=position.z;tp2.z +=position.z;tp3.z +=position.z;
            newTriangles.add(new Triangle(tp1,tp2,tp3));
        }

        return new Thing(newTriangles);
    }
}