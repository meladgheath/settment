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

public class CovenantView {


        public static StackPane pane ;
        public VBox root;

        public TextField id ;
        public TextField name ;
        public TextField recipient ;
        public TextField bank ;
        public TextField desc ;
        public TextField value ;
        public TextField account;

        public Text idTx ;
        public Text nameTx;
        public Text recipientTx   ;
        public Text bankTx ;
        public Text descTx ;
        public Text valueTx ;
        public Text accountTx;

        public Button VbankButton;
        public Button VaccountButton ;

        public Button saveButton  ;
        public Button printButton ;

        public TableView tableView ;



    public CovenantView(){

            root = new VBox();

            id        = new TextField();
            name      = new TextField();
            recipient = new TextField();
            bank      = new TextField();
            desc      = new TextField();
            value     = new TextField();
            account   = new TextField();

            idTx        = new Text();
            nameTx      = new Text();
            recipientTx = new Text();
            bankTx      = new Text();
            descTx      = new Text();
            valueTx     = new Text();
            accountTx   = new Text();

            saveButton  = new Button();
            printButton = new Button();

            VbankButton    = new Button("V");
            VaccountButton = new Button("V");


            root.setPrefWidth(200);

            tableView = new TableView();
            tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            tableView.setPrefHeight(800);

            bank   .setDisable(true);
            account.setDisable(true);

            HBox top = new HBox( );

            top.getChildren().add(idTx) ;
            top.getChildren().add(id);
            top.getChildren().add(nameTx);
            top.getChildren().add(name);
            top.getChildren().add(recipientTx);
            top.getChildren().add(recipient);
            top.getChildren().add(bankTx);
            top.getChildren().add(bank);
            top.getChildren().add(VbankButton);
            top.getChildren().add(accountTx);
            top.getChildren().add(account);
            top.getChildren().add(VaccountButton);
            top.getChildren().add(descTx);
            top.getChildren().add(desc);
            top.getChildren().add(valueTx);
            top.getChildren().add(value);
            top.getChildren().add(saveButton);

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


