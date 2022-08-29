package org.softwareengine.core.view;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


    public class degreeView {



        public static StackPane pane ;
        public VBox root;

        public TextField status;
        public TextField employment;
        public TextField num  ;

        public Text storeTex ;
        public Text emplomentTex;
        public Text numTex   ;


        public Button Vstatus;
        public Button Vemployment;
        public Button saveButton  ;
        public Button printButton ;

        public TableView tableView ;



        public degreeView() {

            root = new VBox();

            status = new TextField();
            employment = new TextField();
            num     = new TextField();


            storeTex = new Text();
            emplomentTex = new Text();
            numTex   = new Text();


            saveButton  = new Button();
            printButton = new Button();

            Vstatus = new Button("V");
            Vemployment = new Button("V");

            root.setPrefWidth(200);

            tableView = new TableView();
            tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            tableView.setPrefHeight(800);

            status.setDisable(true);
            employment.setDisable (true);

              HBox top = new HBox( );

            top.getChildren().add(emplomentTex) ;
            top.getChildren().add(employment);
            top.getChildren().add(Vemployment);
            top.getChildren().add(numTex);
            top.getChildren().add(num);
            top.getChildren().add(saveButton);
            top.getChildren().add(printButton);
            top.getChildren().add(status);
            top.getChildren().add(Vstatus);


            top.setSpacing(6.5);

            root.setPadding(new Insets(20));
            root.setSpacing(5);

            Separator line = new Separator();
            line.setOrientation(Orientation.HORIZONTAL);

            root.getChildren().add(tableView);
            root.getChildren().add(line);
            root.getChildren().add(top) ;

        }
        public StackPane getRoot() {
            pane = new StackPane();
            pane.getChildren().add(root);
            return pane ;
        }
    }