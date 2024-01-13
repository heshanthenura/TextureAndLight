package com.heshanthenura.textureandlight;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private AnchorPane background;

    @FXML
    private Group cubeGroup;

    PerspectiveCamera camera = new PerspectiveCamera();
    PointLight light = new PointLight(Color.WHITE);
    Scene scene;

    int CUBE_LENGTH = 200;

    List<String> textures = new ArrayList<>(List.of("grass.jpg", "lava.jpg","rock.jpg"));
    List<String> bumps = new ArrayList<>(List.of("grass_bump.jpg", "lava_bump.jpg","rock_bump.jpg"));
    List<String> illumination = new ArrayList<>(List.of("", "lava_illu.jpg",""));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            init();
            backgroundListeners();
            initCube();
            cubeGroup.setRotationAxis(Rotate.X_AXIS);
            cubeGroup.setRotate(45);
                    new AnimationTimer() {

            @Override
            public void handle(long l) {
                cubeGroup.setRotate(cubeGroup.getRotate() + 0.5);
            }
        }.start();
        });
    }

    public void init() {
        scene = background.getScene();
        scene.setCamera(camera);
        camera.setLayoutX(0);
        camera.setLayoutY(0);
        background.getChildren().add(light);
        cubeGroup.setAutoSizeChildren(true);
        keepCenter();

    }

    public void initCube() {

        for (int i = 0; i < textures.size(); i++) {
            System.out.println(i);
            cubeGroup.getChildren().add(new Cube(cubeGroup, CUBE_LENGTH, i, textures.get(i), bumps.get(i), illumination.get(i)).getCube());

        }
        keepCenter();

    }

    public void backgroundListeners() {

        background.widthProperty().addListener((obs, oV, nV) -> {
            keepCenter();
        });
        background.heightProperty().addListener((obs, oV, nV) -> {
            keepCenter();
        });
        background.setOnMouseMoved(e -> {
            light.setLayoutX(e.getX());
            light.setLayoutY(e.getY());
        });

    }

    public void keepCenter() {
        cubeGroup.setLayoutX((background.getWidth() / 2) - (cubeGroup.getBoundsInParent().getWidth() / 2) + (CUBE_LENGTH / 2));
        cubeGroup.setLayoutY(background.getHeight() / 2 - (cubeGroup.getBoundsInParent().getHeight() / 2) + (CUBE_LENGTH / 2));
        camera.setLayoutX(0);
        camera.setLayoutY(0);
    }


}

class Cube {
    Box cube = new Box(100, 100, 100);

    public Cube(Group group, int l, int xT, String tex, String bump, String ill) {
        PhongMaterial material = new PhongMaterial();

        try {
            material.setDiffuseMap(new Image(MainApplication.class.getResourceAsStream(tex)));
        } catch (Exception e) {
            System.out.println("No Texture");
        }

        try {
            material.setBumpMap(new Image(MainApplication.class.getResourceAsStream(bump)));
        } catch (Exception e) {
            System.out.println("No Bump");
        }

        if (!ill.equals("")) {
            material.setSelfIlluminationMap(new Image(MainApplication.class.getResourceAsStream(ill)));
        }

        cube = new Box(l, l, l);
        cube.setMaterial(material);
        cube.setTranslateX((l+(l)) * xT);
        cube.setRotationAxis(Rotate.Y_AXIS);
        cube.setCullFace(null);


    }

    Box getCube() {
        System.out.println("Added");
        return cube;
    }

}