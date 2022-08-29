package org.softwareengine.core.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.NodeOrientation;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;


import org.softwareengine.core.model.employment;
import org.softwareengine.config.languages;

import org.softwareengine.core.model.account;
import org.softwareengine.core.view.employmentView;

import org.softwareengine.utils.ui.FXDialog;

import java.sql.SQLException;
import java.util.Locale;


public class employmentController {

        public employmentView view;



    public FXDialog dialog ;

        int itemID = 0 ;
        int statusID = 0 ;


        public employmentController() {
            view = new employmentView();


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
            view.codeTex.setText(lang.getWord("code"));
            view.typeTex.setText(lang.getWord("types"));


            view.saveButton.setText(lang.getWord("save"));
            view.printButton.setText(lang.getWord("print"));


            ((TableColumn) view.tableView.getColumns().get(0)).setText(lang.getWord("id"));//id
            ((TableColumn) view.tableView.getColumns().get(1)).setText(lang.getWord("name"));//name
            ((TableColumn) view.tableView.getColumns().get(2)).setText(lang.getWord("code"));//code
            ((TableColumn) view.tableView.getColumns().get(3)).setText(lang.getWord("types"));//type


            if (Locale.getDefault().getLanguage().equalsIgnoreCase("ar"))
                view.root.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            else
                view.root.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);

        }

        private void initiate() throws SQLException {
            getTableColum();

            view.tableView.setOnKeyPressed(onTablePressed());
            view.printButton.setOnAction(onPrintButton());
            view.saveButton.setOnAction(OnSaveButton());
            view.Vtype.setOnAction(onType_V_Action());



        }

        private void getTableColum() throws SQLException {




            TableColumn<Integer, employmentController> id = new TableColumn<>();
            TableColumn<String, employment> name = new TableColumn<>();
            TableColumn<String, employment> code = new TableColumn<>();
            TableColumn<String, employment> type = new TableColumn<>();




            id.setCellValueFactory(new PropertyValueFactory<>("id"));

            name    .setCellValueFactory(new PropertyValueFactory<>("name"));
            code    .setCellValueFactory(new PropertyValueFactory<>("code"));

            type    .setCellValueFactory(new PropertyValueFactory<>("statusName"));


            id.setMaxWidth(50);
            id.setMinWidth(25);

            view.tableView.getColumns().add(id);
            view.tableView.getColumns().add(name);
            view.tableView.getColumns().add(code);
            view.tableView.getColumns().add(type);



            getTableDetail();

        }

        private void getTableDetail() throws SQLException {
            employment model = new employment();

            view.tableView.setItems(model.getInfo());

        }


        private EventHandler<KeyEvent> onTablePressed () {
            return new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {

                    if (event.getCode() != KeyCode.DELETE)
                        return;
                    employment model = new employment();
                    int index = view.tableView.getSelectionModel().getSelectedIndex() ;
                    try {
                        model.setId(new employment().getInfoIDs().get(index).getId());
                        model.delete();
                        getTableDetail();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }} ;
        }


        private EventHandler<KeyEvent> onUpdate() {
            return new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {

                    if (!(event.getCode()== KeyCode.ENTER))
                        return;

                    TextField t = (TextField) event.getSource() ;
                    System.out.println(t.getId());

                    employment model = new employment();
                    System.out.println(itemID);
                    model.setId(itemID);

                    switch (t.getId()) {
                        case "name" :
                            model.setName(t.getText());

                            break;
                        case "code" :
                            model.setCode(Integer.parseInt(t.getText()));
                            break;
                        case "package" :
                            model.setPackages(Integer.parseInt(t.getText()));
                            break;
                        case "value" :
                            model.setValue(Double.parseDouble(t.getText()));
                            break;

                    }


                    try {
                        model.update(t.getId());
                        dialog.dialog.close();
                        getTableDetail();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                }
            };

        }

        private EventHandler<ActionEvent> onPrintButton() {
            return new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                   /* JasperReport report = null ;
                    JasperPrint jp = null ;

                    try {
                         report = JasperCompileManager.compileReport("Cherry.jrxml");
                         jp = JasperFillManager.fillReport(report,null,DatabaseService.connection);

                    } catch (JRException e) {
                        e.printStackTrace();
                    }*/



                    System.out.println("Done here man . . .");
                }
            };
        }

        private EventHandler<ActionEvent> OnSaveButton() {
            return new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    employment model = new employment();



//                    model.setCode(Integer.parseInt(view.code.getText()));
                    model.setCode(Integer.parseInt(view.code.getText()));
                    model.setName(view.name.getText());
                    model.setStatus(statusID);
//                    model.setPackages(Integer.parseInt(view.packages.getText()));

                    try {
                        model.save();
                        System.out.println("data is saved !");
                        getTableDetail();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    view.name.clear();
                    view.code.clear();
                    view.type.clear();


                }
            };
        }// save Button



    private EventHandler<ActionEvent> onType_V_Action () {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                dialog = new FXDialog(view.pane, "types List . . . ",false);


                account model  = new account();


                int i = 0;
                int size = 0;
                try {
                    size = model.getInfo().size();
                    System.out.println("the size is " + size);

                    while (i < size)
                        dialog.listView.getItems().add(model.getInfo().get(i++).getName());



                    dialog.show();
                    dialog.listView.setOnKeyPressed(OnListPressed());
                    dialog.listView.setOnMouseClicked(OnMouseClick());

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        };


    }


    private EventHandler<KeyEvent> OnListPressed(){
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if (!(event.getCode() == KeyCode.ENTER))
                    return;
                ListEvent();



                dialog.dialog.close();

            }
        } ;
    }


    private EventHandler<MouseEvent> OnMouseClick () {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                if (event.getClickCount() < 2 )
                    return;
                ListEvent();
                dialog.dialog.close();
            }
        } ;
    }

    private void ListEvent() {


        int index;


        index = dialog.listView.getSelectionModel().getSelectedIndex();
        System.out.println(index +"this is the index . . . . ");

        account types = new account();

            try {


                view.type.setText(types.getInfo().get(index).getName());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
    }
}