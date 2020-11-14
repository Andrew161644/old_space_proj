package sample.objects;


import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Meteor extends SpaceObject {

    public Meteor(float posX, float posY, int radius, double mas) {
        super(posX, posY, radius,mas);
       // this.image = new Image("https://previews.123rf.com/images/llepod/llepod1111/llepod111100007/11216236-seamless-texture-surface-of-the-moon-high-resolution-25-megapixels-texture-number-19-in-the-collecti.jpg",false);
        this.radius=radius;

        //node = create();
        node=meteoriteCreate();
    }
    protected Node meteoriteCreate(){
        Circle ball = new Circle();
        ball.setRadius(radius);
        ball.setFill(color);
        ball.setStroke(Color.BLACK);
        ball.setLayoutX(posX);
        ball.setLayoutY(posY);
        ball.setCache(true);
        return ball;
    }
}

