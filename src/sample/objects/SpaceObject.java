package sample.objects;


import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

import java.io.FileWriter;
import java.io.IOException;


public class SpaceObject {
    public Node node;

    //X and Y position of the ball in JBox2D world
    public float posX;
    public float posY;
    protected int radius;
    protected Image image;
    public String name;
 //   protected Image meteoriteImage = new Image("https://previews.123rf.com/images/plrang/plrang1611/plrang161100006/68967407-stone-meteorite-or-coal.jpg",false);
    public double dx = 0, dy = 0;
    protected double mass;

     public Color color;



    Pane pane;
    public Path path=new Path();
    public SpaceObject(float posX, float posY, Color color) {
        this.posX = (float) posX;
        this.posY = (float) posY;
        this.color=color;
        node=create();
    }
    public SpaceObject(float posX, float posY, int radius, double mass) {
        this.mass=mass;
        this.posX = posX;
        this.posY = posY;
        this.radius = radius;
        this.color=Color.GRAY;


    }



    public SpaceObject(String name,Circle circle,Double mass, Image image) throws IOException {
        this.name=name;
        this.mass=mass;
        this.posX=(float) circle.getLayoutX();
        this.posY = (float)circle.getLayoutY();
        this.radius = (int)circle.getRadius();

        this.image = image;
        this.node=create();
        path.getElements().add(new MoveTo(posX,posY));
        pane=(Pane) circle.getParent();
        pane.getChildren().remove(circle);
        pane.getChildren().add(node);
        pane.getChildren().add(path);
    }


    public void drawPath(double x, double y){
        path.setStroke(Color.WHITE);
        path.getElements().add(new LineTo(x,y));
    }



    protected Node create(){
        Circle ball = new Circle();
        ball.setRadius(radius);
        ball.setFill(new ImagePattern(image));
        ball.setStroke(Color.BLACK);
        ball.setLayoutX(posX);
        ball.setLayoutY(posY);
        ball.setCache(true);
        return ball;
    }




    public double getMass() { return mass; }
    public void setMass(double mass) { this.mass = mass; }
    public int getRadius() { return radius; }
    public SpaceObject(){}
    void save(FileWriter writer) throws IOException {
        writer.write(toString()+"\n");
    }

    public void load(String s){

        String[] chars=s.split(" ");
        try {
        name=(chars[1].split("=")[1]);
        posX=Float.valueOf(chars[2].split("=")[1]);
        posY=Float.valueOf(chars[3].split("=")[1]);
        radius=Integer.valueOf(chars[4].split("=")[1]);
        dx=Double.valueOf(chars[5].split("=")[1]);
        dy=Double.valueOf(chars[6].split("=")[1]);

            String[] chars2 =chars[chars.length-1].split("}");
        mass=Double.parseDouble(chars2[0].split("=")[1]);
        }
        catch (ArrayIndexOutOfBoundsException ex){}

        node=new Circle();
        path=new Path();
        path.getElements().add(new MoveTo(posX,posY));


        switch (name){
            case "Sun":
                image=new Image("resourses/8k_sun.jpg",false);
                break;
            case "Mercury":
                image=new Image("resourses/1120702.png",false);
                break;
            case "Venus":
                image=new Image("resourses/venmap.gif",false);
                break;
            case "Earth":
                image=new Image("resourses/earth_clouds.jpg",false);
                break;
            case "Mars":
                image=new Image("resourses/matthew-milobowski-render3.jpg",false);
                break;
            case "Jupiter":
                image=new Image("resourses/bebab4e9768b78a4f5dc8b7a2b24daaf.jpg",false);
                break;
            case "Saturn":
                image=new Image("resourses/2048.jpg",false);
                break;
            case "Uranus":
                image=new Image("resourses/uranusmap-10x5k-CC.jpg",false);
                break;
            case "Neptune":
                image=new Image("resourses/2k_neptune.jpg",false);
                break;
        }
        ((Circle)node).setFill(new ImagePattern(image));
        ((Circle)node).setLayoutX(posX);
        ((Circle)node).setLayoutY(posY);
        ((Circle)node).setRadius(radius);
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "SpaceObject{" +
                " name="+name+
                " posX=" + posX +
                " posY=" + posY +
                " radius=" + radius +
                " dx=" + dx +
                " dy=" + dy +
                " mass=" + mass +
                '}';
    }
}
