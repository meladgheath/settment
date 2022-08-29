
package org.softwareengine.core.view;


import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;

import javafx.geometry.Orientation;
import javafx.scene.Scene;


import javafx.scene.control.Separator;

import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;

import java.awt.*;


public class Home {


            public Scene scene ;
            public  BorderPane root ;


            public JFXButton settingButton     ;
            public JFXButton employmentButton;
            public JFXButton bankButton;
            public JFXButton covenantButton;
            public JFXButton disbursementButton;
            public JFXButton transferButton    ;
            public JFXButton amountButton;
            public JFXButton moneyButton       ;
            public JFXButton treasuryButton    ;
            public JFXButton accountButton;
            public JFXButton settlementButton;
            public JFXButton transactionButton ;
            public JFXButton noticButton;
            public JFXButton permissionsButton ;
            public JFXButton depthBookButton   ;


            public ImageView employmentImageview;
            public ImageView bankImageview          ;
            public ImageView convenantImageview     ;
            public ImageView disbursementImageview  ;
            public ImageView settingImageview       ;
            public ImageView transferImageview      ;
            public ImageView amountsImageview       ;
            public ImageView moneyImageview         ;
            public ImageView treasuryImageview      ;
            public ImageView accountImageview;
            public ImageView userImageview          ;
            public ImageView settlementImageview;
            public ImageView customerTypeImageview  ;
            public ImageView permissionsImageview   ;
            public ImageView noticImageview         ;


            public Home() {


                root = new BorderPane() ;
                root.setPrefWidth(90);
                root.setPrefHeight(35);

                //*********************Right**********************************


                settingButton     = new JFXButton() ;
                employmentButton = new JFXButton() ;
                bankButton = new JFXButton() ;
                covenantButton = new JFXButton() ;
                disbursementButton = new JFXButton() ;
                transferButton    = new JFXButton() ;
                amountButton = new JFXButton() ;
                moneyButton       = new JFXButton() ;
                treasuryButton    = new JFXButton() ;
                accountButton = new JFXButton() ;
                settlementButton = new JFXButton() ;
                transactionButton = new JFXButton() ;
                noticButton = new JFXButton() ;
                permissionsButton = new JFXButton() ;
                depthBookButton   = new JFXButton() ;

                settingImageview      = new ImageView() ;
                employmentImageview = new ImageView() ;
                bankImageview = new ImageView() ;
                convenantImageview = new ImageView() ;
                disbursementImageview = new ImageView() ;
                transferImageview     = new ImageView() ;
                amountsImageview = new ImageView() ;
                moneyImageview        = new ImageView() ;
                treasuryImageview     = new ImageView() ;
                accountImageview = new ImageView() ;
                userImageview         = new ImageView() ;
                settlementImageview = new ImageView() ;
                customerTypeImageview = new ImageView() ;
                permissionsImageview  = new ImageView() ;
                noticImageview = new ImageView() ;

                
                setToolTips();

                settingButton.setMinWidth(root.getPrefWidth());
                settingButton.setMinHeight(root.getPrefHeight());

                employmentButton.setMinWidth(root.getPrefWidth());
                employmentButton.setMinHeight(root.getPrefHeight());

                bankButton.setMinWidth(root.getPrefWidth());
                bankButton.setMinHeight(root.getPrefHeight());

                covenantButton.setMinWidth(root.getPrefWidth());
                covenantButton.setMinHeight(root.getPrefHeight());

                disbursementButton.setMinWidth(root.getPrefWidth());
                disbursementButton.setMinHeight(root.getPrefHeight());


                transferButton.setMinWidth(root.getPrefWidth());
                transferButton.setMinHeight(root.getPrefHeight());

                amountButton.setMinWidth(root.getPrefWidth()  );
                amountButton.setMinHeight(root.getPrefHeight());

                moneyButton.setMinWidth(root.getPrefWidth()  );
                moneyButton.setMinHeight(root.getPrefHeight());

                treasuryButton.setMinWidth(root.getPrefWidth()  );
                treasuryButton.setMinHeight(root.getPrefHeight());

                accountButton.setMinWidth(root.getPrefWidth()  );
                accountButton.setMinHeight(root.getPrefHeight());

                settlementButton.setMinWidth(root.getPrefWidth()  );
                settlementButton.setMinHeight(root.getPrefHeight());

                transactionButton.setMinWidth(root.getPrefWidth());
                transactionButton.setMinHeight(root.getPrefHeight());

                noticButton.setMinWidth(root.getPrefWidth());
                noticButton.setMinHeight(root.getPrefHeight());

                depthBookButton.setMinWidth(root.getPrefWidth());
                depthBookButton.setMinHeight(root.getPrefHeight());

                permissionsButton.setMinWidth(root.getPrefWidth());
                permissionsButton.setMinHeight(root.getPrefHeight());

                VBox right = new VBox() ;
                right.setSpacing(15);
                right.setPadding(new Insets(20,20,20,20));


                right.getChildren().add(accountButton );
                right.getChildren().add(bankButton    );
                right.getChildren().add(covenantButton);
                right.getChildren().add(settlementButton);
                right.getChildren().add(settingButton );


                ////////////////////////////////////////////////
                HBox allRight = new HBox() ;
                Separator LineMid = new Separator() ;
                LineMid.setOrientation(Orientation.VERTICAL);

                LineMid.setStyle("-fx-background-color: #9ACD32;");

                allRight.getChildren().add(LineMid) ;
                allRight.getChildren().add(right) ;
                allRight.setStyle("-fx-background-color: rgb(0,200,0)");

                ///////////////////////////////////////////

                ////////////////////////////////////////////////////////
                root.setRight(allRight);
                Dimension screensize =Toolkit.getDefaultToolkit().getScreenSize() ;
                double width = screensize.getWidth() - (screensize.getWidth()/14) ;
                double height = screensize.getHeight() - (screensize.getHeight()/4);

                System.out.println(width+"   "+screensize.getWidth());
                System.out.println(height+"  "+screensize.getHeight());
                scene = new Scene(root , width, height) ;
            }

            public Scene getScene() {
                return scene ;
            }

            public void setToolTips() {

                employmentButton.setGraphic(employmentImageview);
//                itemButton.setTooltip(new Tooltip(resourceBundle.getString("item")));


                bankButton.setGraphic(bankImageview);
//                storeButton   .setTooltip(new Tooltip(resourceBundle.getString("store")));
                

                covenantButton.setGraphic(convenantImageview);
                // purchaseButton.setTooltip(new Tooltip("purchase"));

                disbursementButton.setGraphic(disbursementImageview);
//                deliveryButton.setTooltip(new Tooltip(resourceBundle.getString("delivery")));

                transferButton.setGraphic(transferImageview);

                amountButton.setGraphic(amountsImageview);

                accountButton.setGraphic(accountImageview);

                settlementButton.setGraphic(settlementImageview);

                settingButton.setGraphic(settingImageview);



            }
        }