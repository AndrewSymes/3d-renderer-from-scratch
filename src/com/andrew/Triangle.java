package com.andrew;

import java.awt.*;

public class Triangle implements Comparable<Triangle>{
    Vector3 p1;
    Vector3 p2;
    Vector3 p3;
    Color color;
    Triangle(Vector3 p1, Vector3 p2, Vector3 p3){
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.color = Color.WHITE;
    }
    Triangle(Vector3 p1, Vector3 p2, Vector3 p3, Color color){
        this(p1,p2,p3);
        this.color = color;
    }
    Triangle rotateXYZ(Vector3 axis){
        return new Triangle(
                p1.rotateXYZ(axis),
                p2.rotateXYZ(axis),
                p3.rotateXYZ(axis)
        );
    }
    Triangle add(Vector3 v){
        return new Triangle(
                p1.add(v),
                p2.add(v),
                p3.add(v)
        );
    }
    Triangle subtract(Vector3 v){
        return new Triangle(
                p1.subtract(v),
                p2.subtract(v),
                p3.subtract(v)
        );
    }
    Vector3 surfaceNormal(){
        Vector3 a = Vector3.subtract(p2,p1);
        Vector3 b = Vector3.subtract(p3,p1);
        return new Vector3(
                a.y*b.z-a.z*b.y,
                a.z*b.x-a.x*b.z,
                a.x*b.y-a.y*b.x
        ).unit();
    }

    @Override
    public int compareTo(Triangle o){
        double a = Vector3.distance(World.camera.position, this.p1) + Vector3.distance(World.camera.position, this.p2) + Vector3.distance(World.camera.position, this.p3);
        double b = Vector3.distance(World.camera.position, o.p1) + Vector3.distance(World.camera.position, o.p2) + Vector3.distance(World.camera.position, o.p3);
        if(a != b)
            return a > b ? -1 : 1;
        System.out.println("equal");
        return 0;
    }
}