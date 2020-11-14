package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import sample.objects.*;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Controller {

    @FXML
    private AnchorPane space_pane;

    @FXML
    private Circle earth_circle;

    @FXML
    private Circle merc_circle;

    @FXML
    private Circle venus_cicrle;

    @FXML
    private Circle sun_circle;

    @FXML
    private Circle mars_circle;

    @FXML
    private Circle upit_circle;
    @FXML
    private Button save;
    @FXML
    private Circle saturn_circl;

    @FXML
    private Circle uranus_circle;

    @FXML
    private Circle neptune_circle;

    @FXML
    private AnchorPane control_pane;

    @FXML
    private Button btn;
    @FXML
    private CheckBox time;
    @FXML
    private Button stop;

    @FXML
    private Slider size_contr;

    @FXML
    private Slider mass_contr;
    @FXML
    private Button load;

    @FXML
    private Slider scale;
    @FXML
    private URL location;

    List<SpaceObject> list=new ArrayList();
    LoadC loadClass;
    double x_start=0.0;
    double y_start=0.0;
    double x_fin=0.0;
    double y_fin=0.0;
    final Timeline timeline = new Timeline();
    @FXML
    void initialize() throws IOException {
        control_pane.setBackground(new Background(new BackgroundFill(Color.web("#807c77"), CornerRadii.EMPTY, Insets.EMPTY)));

        timeline.setCycleCount(Timeline.INDEFINITE);
        Duration duration = Duration.seconds(1.0 / 60.0);

        SpaceObject sun=new SpaceObject("Sun",sun_circle,332946.0, new Image("resourses/8k_sun.jpg",false));
        SpaceObject merk=new SpaceObject("Mercury",merc_circle,0.0553,new Image("resourses/1120702.png",false));
        SpaceObject venus=new SpaceObject("Venus",venus_cicrle,0.815,new Image("resourses/venmap.gif",false));
        SpaceObject earth=new SpaceObject("Earth", earth_circle,1.0,new Image("resourses/earth_clouds.jpg",false));
        SpaceObject mars=new SpaceObject("Mars",mars_circle,0.107,new Image("resourses/matthew-milobowski-render3.jpg",false));
        SpaceObject jup=new SpaceObject("Jupiter",upit_circle,10.0,new Image("resourses/bebab4e9768b78a4f5dc8b7a2b24daaf.jpg",false));
        SpaceObject sat=new SpaceObject("Saturn",saturn_circl,4.0,new Image("resourses/2048.jpg",false));
        SpaceObject uranus=new SpaceObject("Uranus",uranus_circle,2.0,new Image("resourses/uranusmap-10x5k-CC.jpg",false));
        SpaceObject nept=new SpaceObject("Neptune",neptune_circle,2.0,new Image("resourses/2k_neptune.jpg",false));

        merk.dy=9.0;
        venus.dy=7.0;
        earth.dy=6.0;
        mars.dy=5.35;
        jup.dy = 4.77;
        sat.dy= 4.2;
        uranus.dy = 3.85;
        nept.dy = 3.60;





       list.add(sun);
       list.add(merk);
        list.add(earth);
        list.add(venus);
        list.add(mars);
        list.add(jup);
        list.add(sat);
        list.add(uranus);
        list.add(nept);
        loadClass=new LoadC(list);



        EventHandler<ActionEvent> ae = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
       /*         Properties.world.step(1.0f / 60.f, 8, 3);*/
                for (int i = 0; i < list.size(); i++) {
                    createPowers(list.get(i));
                }
            }
        };
        KeyFrame frame = new KeyFrame(duration, ae, null, null);
        timeline.getKeyFrames().add(frame);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                timeline.playFromStart();
            }
        });
        stop.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                timeline.stop();
            }
        });
        createPaneControllers();

    }

    public void createPowers(SpaceObject object){

        double distance;
        boolean not_fall = true;

        for (int i = 0; i < list.size(); i++) {
            SpaceObject p =  list.get(i);
            if (p == object) {
                continue;
            }
            double x1 = object.node.getLayoutX();
            double y1 = object.node.getLayoutY();
            double x2 = p.node.getLayoutX();
            double y2 = p.node.getLayoutY();
            distance = Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));
            if (distance > p.getRadius()){
                object.dx += 0.03*((p.getMass()/(distance*distance)) * ((x2-x1)/distance)); //F = G*m1*m2/r*r = m1*a
                object.dy += 0.03*((p.getMass()/(distance*distance)) * ((y2-y1)/distance));
            }
            else{
                not_fall = false;
                if(object instanceof Meteor){
                    space_pane.getChildren().remove(object.node);
                    list.remove(object);
                    space_pane.getChildren().remove(object.path);
                }
            }
        }
        if (not_fall){
        double x = object.node.getLayoutX() + object.dx;
        double y = object.node.getLayoutY() + object.dy;
        object.drawPath(x,y);

        object.posY= (float) y;
        object.posX= (float) x;
            object.node.setLayoutX(x);
            object.node.setLayoutY(y);

        }
    }

    public void createPaneControllers(){
        time.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                timeline.stop();

                    for (SpaceObject s:list
                         ) {
                        space_pane.getChildren().remove(s.path);
                        s.dy*=-1;
                        s.dx*=-1;
                        s.path=new Path(new MoveTo(s.posX,s.posY));
                        space_pane.getChildren().add(s.path);
                    }
                    timeline.play();


            }
        });
        load.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {

                    final FileChooser fileChooser = new FileChooser();
                    File file = fileChooser.showOpenDialog(load.getScene().getWindow());
                    if (file != null) {
                        List<File> files = Arrays.asList(file);
                    }
                    System.out.println();
                    if(file ==null)
                        return;
                    FileReader reader=new FileReader(file.toString());
                    for (int i=0;i<list.size();i++){
                        space_pane.getChildren().remove(list.get(i).node);
                       space_pane.getChildren().removeAll(list.get(i).path);
                    }
                    timeline.stop();
                    list=new ArrayList<>();
                    list=loadClass.laod(reader);
                    System.out.println();

                    for (int i=0;i<list.size();i++){
                        space_pane.getChildren().add(list.get(i).node);
                        space_pane.getChildren().add(list.get(i).path);

                    }


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        save.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                try {
                    final FileChooser fileChooser = new FileChooser();
                    File file = fileChooser.showOpenDialog(save.getScene().getWindow());
                    if (file != null) {
                        List<File> files = Arrays.asList(file);
                    }
                    if(file ==null)
                        return;
                    FileWriter writer=new FileWriter(file.toString());
                    loadClass.save(writer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        space_pane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                x_start=event.getX();
                y_start=event.getY();
                System.out.println(x_start+" "+y_start);
            }
        });

        EventHandler<MouseEvent> eventHandler =
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        x_fin=e.getX();
                        y_fin=e.getY();
                        SpaceObject spaceObject=new Meteor((float) x_start,(float) y_start, (int) size_contr.getValue(),mass_contr.getValue());

                        space_pane.getChildren().add(spaceObject.node);
                        spaceObject.dx=(x_fin-x_start)/100;
                        spaceObject.dy=(y_fin-y_start)/100;
                        list.add(spaceObject);
                        spaceObject.path.getElements().add(new MoveTo(x_start,y_start));
                        space_pane.getChildren().add(spaceObject.path);

                    }
                };
        space_pane.addEventFilter(MouseEvent.MOUSE_RELEASED, eventHandler);


    }


}