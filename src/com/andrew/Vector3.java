package com.andrew;

public class Vector3{
    public double x;
    public double y;
    public double z;

    public Vector3(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Vector3(){
        this(0,0,0);
    }
    public static Vector3 multiply(Vector3 v1, Vector3 v2){
        return new Vector3(v1.x*v2.x, v1.y*v2.y, v1.z*v2.z);
    }
    public static Vector3 add(Vector3 v1, Vector3 v2){
        return new Vector3(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
    }
    public Vector3 add(Vector3 v2){
        return new Vector3(x + v2.x, y + v2.y, z + v2.z);
    }
    public static Vector3 subtract(Vector3 v1, Vector3 v2){
        return add(v1, new Vector3(-v2.x,-v2.y,-v2.z));
    }
    public Vector3 subtract(Vector3 v2){
        return add(this, new Vector3(-v2.x,-v2.y,-v2.z));
    }
    public Vector3 rotateXYZ(Vector3 axis){
        double rx = 0, ry = 0, rz = 0;
        double frx = 0, fry = 0, frz = 0;
        // rotate about the x axis
        ry = y*Math.cos(axis.x) - z*Math.sin(axis.x);
        rz = y*Math.sin(axis.x) + z*Math.cos(axis.x);
        fry = ry;
        frz = rz;
        // rotate about the y axis
        rx = x*Math.cos(axis.y)+rz*Math.sin(axis.y);
        rz = -x*Math.sin(axis.y)+rz*Math.cos(axis.y);
        frx = rx;
        frz = rz;
        // rotate about the z axis
        frx = rx*Math.cos(axis.z)-ry*Math.sin(axis.z);
        fry = rx*Math.sin(axis.z)+ry*Math.cos(axis.z);

        return new Vector3(frx,fry,frz);
    }
    public void set(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public double magnitude(){
        return Math.sqrt(Math.pow(x,2) + Math.pow(y,2) + Math.pow(z,2));
    }
    public static double distance(Vector3 v1, Vector3 v2){
        return Math.sqrt(Math.pow(v1.x-v2.x, 2) + Math.pow(v1.y-v2.y, 2) + Math.pow(v1.z-v2.z, 2));
    }
    public Vector3 unit(){
        double mag = magnitude();
        return new Vector3(x/mag, y/mag, z/mag);
    }
    public static double dotProduct(Vector3 v1, Vector3 v2){
        Vector3 line = multiply(v1,v2);
        return line.x + line.y + line.z;
    }

    @Override
    public String toString() {
        return "Vector3{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}