package org.softwareengine.core.view;


import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.Separator;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;

import javafx.scene.text.Text;

public class accountView {

    public static StackPane pane ;
    public VBox root;


    public TextField name   ;
    public TextField number ;

    public Button saveButton ;


    public TableView tableView ;

    public Text numberTex   ;
    public Text nameTex     ;



    public accountView() {

        root     = new VBox();

        name     = new TextField();
        number   = new TextField();

        nameTex   = new Text();
        numberTex = new Text();

        saveButton = new Button();

        Separator line = new Separator();
        line.setOrientation(Orientation.VERTICAL);

        root.setPrefWidth(200);

        tableView = new TableView();
        tableView.setPrefHeight(700);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        HBox down = new HBox();

        down.getChildren().add(numberTex);
        down.getChildren().add(number);
        down.getChildren().add(nameTex) ;
        down.getChildren().add(name);

        down.getChildren().add(saveButton);

        HBox right2 = new HBox();
        right2.getChildren().add(line);

        down.setSpacing(8);
        down.setPadding(new Insets(10,0,0,0));

        BorderPane pane = new BorderPane();

        pane.setCenter(tableView);
        pane.setBottom(down);

        root.setPadding(new Insets(20));
        root.setSpacing(5);

        root.getChildren().add(pane);
    }
    public StackPane getRoot() {
        pane = new StackPane();
        pane.getChildren().add(root);
        return pane ;
    }

}