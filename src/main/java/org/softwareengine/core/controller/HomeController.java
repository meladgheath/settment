package org.softwareengine.core.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import org.softwareengine.core.view.Home;
import org.softwareengine.config.languages;
import org.softwareengine.core.model.Paths;

import java.util.Locale;

    public class HomeController {

        public Home view;

        public HomeController() {
            initiated();
            setupLanguages() ;

        }

        private void setupLanguages(){

        languages lang = new languages();

        view.employmentButton.setTooltip(new Tooltip(lang.getWord("item") ));
        view.bankButton        .setTooltip(new Tooltip(lang.getWord("bank") ));
        view.accountButton.setTooltip(new Tooltip(lang.getWord("types")));

        view.amountButton      .setTooltip(new Tooltip(lang.getWord("amounts")));
        view.settingButton  .setTooltip(new Tooltip(lang.getWord("setting")));
        }

        public void initiated() {

            

            Locale lang = new Locale("en") ;
            Locale.setDefault(lang);

           view = new Home();

           view.accountImageview  .setImage(new Image(getClass().getResourceAsStream(Paths.ACCOUNT_NUMBER.getPath())));
           view.bankImageview     .setImage(new Image(getClass().getResourceAsStream(Paths.BANKS         .getPath())));
           view.convenantImageview.setImage(new Image(getClass().getResourceAsStream(Paths.CONVENANT     .getPath())));
           view.settlementImageview.setImage(new Image(getClass().getResourceAsStream(Paths.STATESMENT.getPath())));
           view.settingImageview.setImage(new Image(getClass().getResourceAsStream(Paths.SETTING         .getPath())));

            view.settingButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    onSettingButton();
                }
            });


            view.bankButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                onBankButton();
                }
            });
            view.covenantButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    onCovenantButton();
                }
            });
            view.settlementButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    onSettemeltButton();
                }
            });
            view.accountButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    onAccountButton();
                }
            });

        }
        private void onBankButton() {
            banksController control = new banksController();
            view.root.setCenter(control.view.getRoot());
        }
        private void onCovenantButton() {
            convenantController control = new convenantController();
            view.root.setCenter(control.view.getRoot());
        }
        private void onSettingButton() {
            SettingController control = new SettingController();
            view.root.setCenter(control.view.getRoot());
        }
        private void onSettemeltButton() {
            settmentController control = new settmentController();
            view.root.setCenter(control.view.getRoot());
        }

        private void onAccountButton() {
            accountController control = new accountController();
            view.root.setCenter(control.view.getRoot());
        }
    }