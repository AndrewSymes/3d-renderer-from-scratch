package com.andrew;

import java.awt.Graphics;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

import static com.andrew.Game.HEIGHT;
import static com.andrew.Game.WIDTH;

public class World {
    public LinkedList<Thing> things;
    public static Camera camera;
    public Vector3 sun;
    private double[][][] cubeVerts = {
            {{-1,-1,-1},{-1,-1,1},{-1,1,1}},
            {{1,1,-1},{-1,-1,-1},{-1,1,-1}},
            {{1,-1,1},{-1,-1,-1,},{1,-1,-1}},
            {{1,1,-1},{1,-1,-1,},{-1,-1,-1}},
            {{-1,-1,-1},{-1,1,1},{-1,1,-1}},
            {{1,-1,1},{-1,-1,1},{-1,-1,-1}},
            {{-1,1,1},{-1,-1,1},{1,-1,1}},
            {{1,1,1},{1,-1,-1},{1,1,-1}},
            {{1,-1,-1},{1,1,1},{1,-1,1}},
            {{1,1,1},{1,1,-1},{-1,1,-1}},
            {{1,1,1},{-1,1,-1,},{-1,1,1}},
            {{1,1,1},{-1,1,1},{1,-1,1}}
    };

    public void addThing(String file) {
        try {
            things.add(new Thing(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void addThing(double[][][] verts){
        things.add(new Thing(verts));
    }
    public void addThing(Thing t){
        things.add(t);
    }

    public World(){
        things = new LinkedList<Thing>();
        camera = new Camera();
        camera.position.set(5,0,-10);
        sun = new Vector3(0,-15,0);
        Thing thing1 = new Thing(cubeVerts);
        Thing thing2 = new Thing(cubeVerts);
        thing1.position.set(1,0,0);
        thing2.position.set(-1.1,0,0);
//        things.add(thing1);
//        things.add(thing2);
        Thing teaPot = null;

    }
    private Vector3 translateVector(Vector3 v){
        double x = v.x/v.z;
        double y = v.y/v.z;
        double z = v.z;
        return new Vector3(x*HEIGHT+WIDTH/2,y*HEIGHT+HEIGHT/2,z);
    }
    private Triangle translateTriangle(Triangle t){
        return new Triangle(
                translateVector(t.p1),
                translateVector(t.p2),
                translateVector(t.p3)
        );
    }
    public void render(Graphics g){
        LinkedList<Triangle> orderedTriangles = new LinkedList<>();

        for(Thing thing : things) {
            Thing translatedThing = thing.translate();
            orderedTriangles.addAll(translatedThing.triangles);
        }
        Collections.sort(orderedTriangles);
        for(Triangle tri : orderedTriangles){
            Triangle translated = translateTriangle(tri.subtract(camera.position).rotateXYZ(camera.orientation));
            if(Vector3.dotProduct(camera.position, translated.surfaceNormal()) > 0){
                double s = Vector3.dotProduct(tri.surfaceNormal(), sun.subtract(tri.p1).unit());
                s = (s + 1)/2;
                float[] hsb = {0,0,0};
                Color.RGBtoHSB((int)(s*200),(int)(s*200),(int)(s*255), hsb);
                Color c = Color.getHSBColor(hsb[0],hsb[1],hsb[2]);
                g.setColor(c);
                int[] xPoints = {(int)translated.p1.x, (int)translated.p2.x, (int)translated.p3.x};
                int[] yPoints = {(int)translated.p1.y, (int)translated.p2.y, (int)translated.p3.y};
                g.fillPolygon(xPoints, yPoints, 3);
//                g.setColor(Color.RED);
//                g.drawPolyline(xPoints, yPoints, 3);
            }
        }
    }

    class Camera{
        Vector3 position = new Vector3();
        Vector3 orientation = new Vector3();
        public void move(double x, double y, double z){
            this.position.x += x;
            this.position.y += y;
            this.position.z += z;
        }
    }
}