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

public class settmentView {

        public static StackPane pane ;
        public VBox root;

        public TextField covnenatID ;
        public TextField name ;
        public TextField credit;
        public TextField debit ;


        public Text covnenatIDTX;
        public Text nameTx;
        public Text creditTX;
        public Text debitTX;

        public Button VCovnenatIDButton;


        public Button saveButton  ;
        public Button printButton ;

        public TableView tableView ;



        public settmentView(){

            root = new VBox();

            covnenatID = new TextField();
            name       = new TextField();
            credit     = new TextField();
            debit      = new TextField();

            covnenatIDTX = new Text();
            nameTx      = new Text();
            creditTX = new Text();
            debitTX = new Text();

            saveButton  = new Button();
            printButton = new Button();

            VCovnenatIDButton = new Button("V");

            root.setPrefWidth(200);

            tableView = new TableView();
            tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            tableView.setPrefHeight(800);

            covnenatID.setDisable(true);

            HBox top = new HBox( );

            top.getChildren().add(covnenatIDTX) ;
            top.getChildren().add(covnenatID);
            top.getChildren().add(VCovnenatIDButton);
            top.getChildren().add(nameTx);
            top.getChildren().add(name);
            top.getChildren().add(creditTX);
            top.getChildren().add(credit);
            top.getChildren().add(debitTX);
            top.getChildren().add(debit);
            top.getChildren().add(saveButton);
            top.getChildren().add(printButton);

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
