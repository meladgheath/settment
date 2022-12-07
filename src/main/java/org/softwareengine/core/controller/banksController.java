package org.softwareengine.core.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.softwareengine.config.languages;
import org.softwareengine.core.model.banks;
import org.softwareengine.core.view.bankview;
import java.sql.SQLException;
import java.util.Locale;

public class banksController {

        public bankview view;
        int itemID = 0 ;

        public banksController() {
            view = new bankview();
            try {
                initiate();
                setupLanguages();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        private void setupLanguages() {
            languages lang = new languages();

            view.nameTex.setText(lang.getWord("name"));
            view.referenceTex.setText(lang.getWord("reference"));
            view.saveButton.setText(lang.getWord("save"));
            view.printButton.setText(lang.getWord("print"));

            ((TableColumn) view.tableView.getColumns().get(0)).setText(lang.getWord("id"));//id
            ((TableColumn) view.tableView.getColumns().get(1)).setText(lang.getWord("name"));//name
            ((TableColumn) view.tableView.getColumns().get(2)).setText(lang.getWord("reference"));//reference number

            if (Locale.getDefault().getLanguage().equalsIgnoreCase("ar"))
                view.root.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            else
                view.root.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);

        }

        private void initiate() throws SQLException {
            getTableColum();

            view.tableView.setOnKeyPressed(onTablePressed());
            view.saveButton.setOnAction(OnSaveButton());
            view.printButton.setOnAction(onPrintButton());
            view.tableView.setOnKeyPressed(onTablePressed());
        }

    private EventHandler<KeyEvent> onTablePressed () {
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() != KeyCode.DELETE)
                    return;

                banks model = new banks();
                int index = view.tableView.getSelectionModel().getSelectedIndex() ;
                try {
                    model.setId(model.getInfoID().get(index).getId());
                    model.delete();
                    getTableDetail();
                } catch (SQLException e) {
                    e.printStackTrace();
                }




            }} ;
    }

    private void getTableColum() throws SQLException {
            TableColumn<Integer, banks> id = new TableColumn<>();
            TableColumn<String, banks> name = new TableColumn<>();
            TableColumn<String, banks> reference = new TableColumn<>();

            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            name    .setCellValueFactory(new PropertyValueFactory<>("name"));
            reference.setCellValueFactory(new PropertyValueFactory<>("referenceNumber"));

            id.setMaxWidth(50);
            id.setMinWidth(25);

            view.tableView.getColumns().add(id);
            view.tableView.getColumns().add(name);
            view.tableView.getColumns().add(reference);
            getTableDetail();
        }

        private void getTableDetail() throws SQLException {
            banks model = new banks();
            view.tableView.setItems(model.getInfo());
        }


    private EventHandler<ActionEvent> onPrintButton() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        };
    }
    private EventHandler<ActionEvent> OnSaveButton() {
            return new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    banks model = new banks();
                    model.setName(view.name.getText());
                    model.setReferenceNumber(view.referenceNumber.getText());
                    try {
                        model.save();
                        System.out.println("data is saved !");
                        getTableDetail();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    view.name.clear();
                    view.referenceNumber.clear();
               }
            };
        }// save Button
    }