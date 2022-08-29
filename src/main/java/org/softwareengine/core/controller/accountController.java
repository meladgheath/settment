package org.softwareengine.core.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import org.softwareengine.core.model.account;
import org.softwareengine.config.languages;
import org.softwareengine.core.view.accountView;


import java.io.File;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;


public class accountController {


    public accountView view;
    File image ;


    public accountController() {
        view = new accountView();


        try {
            initiate();
            setupLanguages() ;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void setupLanguages() {

        languages lang = new languages();

        view.numberTex.setText(lang.getWord("account"));
        view.nameTex.setText(lang.getWord("name"));

        view.saveButton.setText(lang.getWord("save"));

        ((TableColumn)view.tableView.getColumns().get(0)).setText("#");
        ((TableColumn)view.tableView.getColumns().get(1)).setText(lang.getWord("account"));
        ((TableColumn)view.tableView.getColumns().get(2)).setText(lang.getWord("name"));

        if (Locale.getDefault().getLanguage().equalsIgnoreCase("ar"))
            view.root.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        else
            view.root.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);



    }

    private void initiate() throws SQLException, IOException {
        getTableColum();
        view.saveButton.setOnAction(OnSaveButton());
        view.tableView.setOnKeyPressed(onTableKeypressed());

    }

    private EventHandler<KeyEvent> onTableKeypressed() {
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

          if(event.getCode() != KeyCode.DELETE)
              return;

          int index = view.tableView.getSelectionModel().getSelectedIndex();
                account model = new account();
                try {
                    model.setNumber(model.getInfo().get(index).getNumber());

//                    model.delete();
                    getTableDetail();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }};
    }


    private void getTableColum() throws SQLException, IOException {

        TableColumn<Integer, account> id = new TableColumn<>("#");
        TableColumn<String , account> number = new TableColumn<>();
        TableColumn<String , account> name = new TableColumn<>("Name");

        id.setCellValueFactory(new PropertyValueFactory<>("seq"));
        number  .setCellValueFactory(new PropertyValueFactory<>("number"));
        name    .setCellValueFactory(new PropertyValueFactory<>("name"));

        id.setMaxWidth(50);
        id.setMinWidth(25);

        view.tableView.getColumns().add(id);
        view.tableView.getColumns().add(number);
        view.tableView.getColumns().add(name);

        getTableDetail();
    }

    private void getTableDetail() throws SQLException, IOException {
        account model = new account();

        view.tableView.setItems(model.getInfo());


    }

    private EventHandler<ActionEvent> OnSaveButton() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {


                account model = new account();

                model.setName(view.name.getText());
                model.setNumber(view.number.getText());

                try {
                    model.save();
                    System.out.println("data is saved !");
                    getTableDetail();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                view.name.clear();
                view.number.clear();


            }
        };
    }// save Button


    private EventHandler<ActionEvent> onBrowseImageButton() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                FileChooser fileChooser = new FileChooser();

                image = fileChooser.showOpenDialog(null) ;
                account model = new account();

            }
        };
    }// browse image  Button
}