package org.softwareengine.core.view;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class settmentView {

        public static StackPane pane ;
        public VBox root;

        public TextField covnenatID ;
        public TextField account;
        public TextField name ;
        public TextField credit;
        public TextField debit ;


        public Text covnenatIDTX;
        public Text nameTx;
        public Text creditTX;
        public Text debitTX;
        public Text accountTx;

        public Button VCovnenatIDButton;
        public Button VaccountButton ;

        public Button saveButton  ;
        public Button printButton ;

        public TableView tableView ;

        public ContextMenu tableMenu ;
        public MenuItem printMenu ;

        public settmentView(){

            root = new VBox();

            covnenatID = new TextField();
            account    = new TextField();
            name       = new TextField();
            credit     = new TextField();
            debit      = new TextField();

            account.setDisable(true);

            covnenatIDTX = new Text();
            nameTx       = new Text();
            accountTx    = new Text();
            creditTX = new Text();
            debitTX = new Text();

            saveButton  = new Button();
            printButton = new Button();

            VaccountButton    = new Button("V") ;
            VCovnenatIDButton = new Button("V");

            root.setPrefWidth(200);

            tableView = new TableView();
            tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            tableView.setPrefHeight(800);

            printMenu = new MenuItem();
            tableMenu = new ContextMenu();

            tableMenu.getItems().add(printMenu);

            tableView.setContextMenu(tableMenu);

            covnenatID.setDisable(true);

            HBox top = new HBox( );

            top.getChildren().add(covnenatIDTX) ;
            top.getChildren().add(covnenatID);
            top.getChildren().add(VCovnenatIDButton);
            top.getChildren().add(accountTx);
            top.getChildren().add(account);
            top.getChildren().add(VaccountButton);
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
