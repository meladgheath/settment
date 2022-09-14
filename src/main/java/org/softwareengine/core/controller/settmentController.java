package org.softwareengine.core.controller;

import com.jfoenix.controls.events.JFXDialogEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import org.softwareengine.config.languages;
import org.softwareengine.core.model.Covenant;

import org.softwareengine.core.model.Transaction;
import org.softwareengine.core.view.settmentView;
import org.softwareengine.utils.report.report;
import org.softwareengine.utils.ui.FXDialog;

import java.sql.SQLException;
import java.util.Locale;

public class settmentController {



        public settmentView view;
        public FXDialog dialog;

        int covenantID  = 0 ;

        public settmentController() {
            view = new settmentView();


            try {
                initiate();
                setupLanguages();
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }

        private void setupLanguages() {
            languages lang = new languages();

            view.nameTx.setText(lang.getWord("constraint"));
            view.covnenatIDTX.setText(lang.getWord("CovenantID"));
            view.debitTX.setText(lang.getWord("debit"));
            view.creditTX.setText(lang.getWord("credit"));
            view.saveButton.setText(lang.getWord("save"));
            view.printButton.setText(lang.getWord("print"));

            ((TableColumn) view.tableView.getColumns().get(0)).setText(lang.getWord("id"));//seq
            ((TableColumn) view.tableView.getColumns().get(1)).setText(lang.getWord("CovenantID"));//covnenatID
            ((TableColumn) view.tableView.getColumns().get(2)).setText(lang.getWord("name"));//name
            ((TableColumn) view.tableView.getColumns().get(3)).setText(lang.getWord("debit"));//debit
            ((TableColumn) view.tableView.getColumns().get(4)).setText(lang.getWord("credit"));//credit

            if (Locale.getDefault().getLanguage().equalsIgnoreCase("ar"))
                view.root.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            else
                view.root.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        }

        private void initiate() throws SQLException {
            getTableColum();
            view.VCovnenatIDButton.setOnAction(onCovenantButton());

            view.saveButton.setOnAction(OnSaveButton());
            view.printButton.setOnAction(onPrintButton());
        }

        private void getTableColum() throws SQLException {


            TableColumn<Integer, Transaction> seq = new TableColumn<>();
            TableColumn<Integer,Transaction> id = new TableColumn<>();
            TableColumn<String, Transaction> name = new TableColumn<>();
            TableColumn<Double,Transaction> debit  = new TableColumn<>();
            TableColumn<Double,Transaction> credit = new TableColumn<>();


            seq.setCellValueFactory(new PropertyValueFactory<>("seq"));
            id.setCellValueFactory(new PropertyValueFactory<>("covnenatID"));
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            debit.setCellValueFactory(new PropertyValueFactory<>("debit"));
            credit.setCellValueFactory(new PropertyValueFactory<>("credit"));


            seq.setMaxWidth(50);
            seq.setMinWidth(25);

            view.tableView.getColumns().add(seq);
            view.tableView.getColumns().add(id);
            view.tableView.getColumns().add(name);
            view.tableView.getColumns().add(debit);
            view.tableView.getColumns().add(credit);


        }

        private void getTableDetail() throws SQLException {
            Transaction model = new Transaction();
            model.setCovnenatID(covenantID);

            if (model.Ishas())
                model.saveif();

            view.tableView.setItems(model.getInfo());
        }


        private EventHandler<ActionEvent> onCovenantButton() {
            return new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    dialog = new FXDialog(view.pane, "covenant List . . . ", true);
                    Covenant model = new Covenant();

                    int i = 0;
                    int size = 0;
                    try {
                        size = model.getInfo().size();
                        System.out.println("the size is " + size);

                        while (i < size)
                            dialog.listView.getItems().add(model.getInfo().get(i++).getId()+"");

                        dialog.show();
                        dialog.listView.setOnKeyPressed(OnListPressed());
                        dialog.listView.setOnMouseClicked(OnMouseClick());
                        dialog.dialog.setOnDialogClosed(onDialogClose());
                        dialog.textField.setOnAction(onDialogTextFieldAction());

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
            };
        }

        private EventHandler<JFXDialogEvent> onDialogClose() {
            return new EventHandler<JFXDialogEvent>() {
                @Override
                public void handle(JFXDialogEvent event) {
                    try {
                        getTableDetail();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
            };
        }
    private EventHandler<ActionEvent> onDialogTextFieldAction() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                dialog.listView.getItems().clear();

                int id = Integer.parseInt(dialog.textField.getText()) ;

                Covenant cov = new Covenant();
                cov.setId(id);

                try {
                    cov = cov.getInfoWHEREid();
                    covenantID = cov.getId();
                    dialog.listView.getItems().add(covenantID+"");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                dialog.listView.setOnKeyPressed(listEvent());
                dialog.listView.setOnMouseClicked(mouseEvent());
            }
        };
    }
    private EventHandler<MouseEvent> mouseEvent(){
            return new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                      event();
                    dialog.dialog.close();
                }
            };
    }

    private EventHandler<KeyEvent>  listEvent(){
            return new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                        event();
                        dialog.dialog.close();
                }
            };
    }

    private void event() {
        Covenant cov = new Covenant();
        cov.setId(covenantID);
        try {
            covenantID = cov.getInfoWHEREid().getId();
            String name = cov.getInfoWHEREid().getName();
            view.covnenatID.setText(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        private EventHandler<KeyEvent> OnListPressed () {
            return new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {

                    if (!(event.getCode() == KeyCode.ENTER))
                        return;
                    ListEvent();

                    dialog.dialog.close();

                }
            };
        }
        private void ListEvent () {
            int index;
            index = dialog.listView.getSelectionModel().getSelectedIndex();

                    try {
                        Covenant model = new Covenant();

                        covenantID = model.getInfo().get(index).getId();
                        view.covnenatID.setText(model.getInfo().get(index).getName());

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
            }
        private EventHandler<ActionEvent> OnSaveButton () {
            return new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    Transaction model = new Transaction();
                    model.setCovnenatID(covenantID);
                    model.setDebit(Double.parseDouble(view.debit.getText()));
                    model.setCredit(Double.parseDouble(view.credit.getText()));
                    model.setName(view.name.getText());

                    try {
                        model.save();
                        getTableDetail();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    view.covnenatID.clear();
                    view.name.clear();
                    view.debit.clear();
                    view.credit.clear();
                }
            };
        }
        private EventHandler<ActionEvent> onPrintButton() {
            return new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    report re = new report();

                    try {
                        Covenant cov = new Covenant();
                        cov.setId(covenantID);
                        String name = cov.getInfoWHERErecipient().getRecipient();
                        JasperPrint print = re.getReport(view.tableView.getItems(),name);
                        JasperViewer.viewReport(print,false);
                    } catch (JRException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            };
        }
    }