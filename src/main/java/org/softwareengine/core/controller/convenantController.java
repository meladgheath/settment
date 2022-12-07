package org.softwareengine.core.controller;

import javafx.collections.ObservableList;
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
import org.softwareengine.core.model.account;
import org.softwareengine.core.model.banks;
import org.softwareengine.core.view.CovenantView;

import org.softwareengine.utils.report.report;
import org.softwareengine.utils.ui.FXDialog;

import java.sql.SQLException;
import java.util.Locale;
import java.util.regex.Pattern;

public class convenantController {


    public CovenantView view;
    public FXDialog dialog;

    int bankID  = 0 ;

    String account  ;
    String ref ;
    ObservableList<banks> list ;
    ObservableList<account> listAccount ;

    public convenantController() {
        view = new CovenantView();


        try {
            initiate();
            setupLanguages();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private void setupLanguages() {
        languages lang = new languages();

        view.nameTx.setText(lang.getWord("convenantName"));
        view.bankTx.setText(lang.getWord("bank"));
        view.idTx.setText(lang.getWord("CovenantID"));
        view.valueTx.setText(lang.getWord("value"));
        view.descTx.setText(lang.getWord("description"));
        view.recipientTx.setText(lang.getWord("recipient"));
        view.accountTx.setText(lang.getWord("account"));
        view.saveButton.setText(lang.getWord("save"));
        view.tableMenu.getItems().get(0).setText(lang.getWord("print"));
        view.CovenantPrintMenu.setText(lang.getWord("convenantPrint"));


        ((TableColumn) view.tableView.getColumns().get(0)).setText(lang.getWord("id"));//seq
        ((TableColumn) view.tableView.getColumns().get(1)).setText(lang.getWord("number"));//id
        ((TableColumn) view.tableView.getColumns().get(2)).setText(lang.getWord("name"));//name
        ((TableColumn) view.tableView.getColumns().get(3)).setText(lang.getWord("recipient"));//recipient
        ((TableColumn) view.tableView.getColumns().get(4)).setText(lang.getWord("bank"));//bank
        ((TableColumn) view.tableView.getColumns().get(5)).setText(lang.getWord("debit"));//debit
        ((TableColumn) view.tableView.getColumns().get(6)).setText(lang.getWord("credit"));//credit
        ((TableColumn) view.tableView.getColumns().get(7)).setText(lang.getWord("account"));//account


        if (Locale.getDefault().getLanguage().equalsIgnoreCase("ar"))
            view.root.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        else
            view.root.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);

    }

    private void initiate() throws SQLException {
        getTableColum();
        view.VbankButton.setOnAction(onBank_V_Action());
        view.VaccountButton.setOnAction(onAccount_V_Action());
        view.tableView.setOnKeyPressed(onTablePressed());
        view.saveButton.setOnAction(OnSaveButton());
        view.tableView.setOnMouseClicked(onTableClickMouse());
        view.tableMenu.getItems().get(0).setOnAction(onPrintMenu()); // PrintMenu --> MenuItem
    }

    private EventHandler<MouseEvent> onTableClickMouse() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isSecondaryButtonDown())
                    view.tableMenu.show(view.tableView,event.getSceneX(),event.getScreenY());
            }
        } ;
    }
    private EventHandler<ActionEvent> onPrintMenu() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                int index = view.tableView.getSelectionModel().getSelectedIndex() ;
                Covenant cov = (Covenant) view.tableView.getItems().get(index);

                report r = new report();

                try {
                    JasperViewer.viewReport(r.getSingleConvenant(cov,"قيد خصم"),false);
                } catch (JRException e) {
                    throw new RuntimeException(e);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }
    private EventHandler<KeyEvent> onTablePressed () {
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() != KeyCode.DELETE)
                    return;


                int index = view.tableView.getSelectionModel().getSelectedIndex() ;
                Covenant model = (Covenant) view.tableView.getItems().get(index) ;
                String id = model.getId() ;

                try {
                    model.setId(id);
                    model.delete();
                    getTableDetail();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }} ;
    }

    private void getTableColum() throws SQLException {


        TableColumn<Integer,Covenant> seq = new TableColumn<>();
        TableColumn<Integer,Covenant> id = new TableColumn<>();
        TableColumn<String, Covenant> name = new TableColumn<>();
        TableColumn<String,Covenant> brnName = new TableColumn<>();
        TableColumn<String,Covenant> recipient = new TableColumn<>();
        TableColumn<Double,Covenant> debit  = new TableColumn<>();
        TableColumn<Double,Covenant> credit = new TableColumn<>();
        TableColumn<String,Covenant> account = new TableColumn<>();

        seq.setCellValueFactory(new PropertyValueFactory<>("seq"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        brnName.setCellValueFactory(new PropertyValueFactory<>("brnName"));
        recipient.setCellValueFactory(new PropertyValueFactory<>("recipient"));
        debit.setCellValueFactory(new PropertyValueFactory<>("debit"));
        credit.setCellValueFactory(new PropertyValueFactory<>("credit"));
        account.setCellValueFactory(new PropertyValueFactory<>("account"));


        seq.setMaxWidth(50);
        seq.setMinWidth(25);

        view.tableView.getColumns().add(seq);
        view.tableView.getColumns().add(id);
        view.tableView.getColumns().add(name);
        view.tableView.getColumns().add(recipient);
        view.tableView.getColumns().add(brnName);
        view.tableView.getColumns().add(debit);
        view.tableView.getColumns().add(credit);
        view.tableView.getColumns().add(account);
        getTableDetail();
    }

    private void getTableDetail() throws SQLException {
        Covenant model = new Covenant();
        view.tableView.setItems(model.getInfo());
    }
    private EventHandler<ActionEvent> onBank_V_Action() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                dialog = new FXDialog(view.pane, "Banks List . . . ", true);

                banks bank = new banks();

                String way  = "bank" ;
                int i = 0;
                int size = 0;
                try {
                    ObservableList<banks> banklist = bank.getInfo() ;
//                    size = bank.getInfo().size();
                    size = banklist.size();

                    while (i < size)
                        dialog.listView.getItems().add(banklist.get(i++).getName());

                    dialog.show();
                    dialog.listView.setOnKeyPressed(OnListPressed(way));
                    dialog.listView.setOnMouseClicked(OnMouseClick(way));
                    dialog.textField.setOnAction(DialogTextField());
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        };
    }

    private EventHandler<ActionEvent> DialogTextField( ) {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                banks model = new banks();
                dialog.listView.getItems().removeAll(dialog.listView.getItems());

                if (Pattern.matches("[0-9]+",dialog.textField.getText())) {
                    try {
                        int id = Integer.parseInt(dialog.textField.getText()) ;
                        model.setReferenceNumber(dialog.textField.getText());
                        list = model.getInfoWHEREref();
                        ref = model.getInfoWHEREref().get(0).getReferenceNumber();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }else
                if (Pattern.matches("[\\w|\\W]+",dialog.textField.getText()))
                {
                    try {
                        model.setName(dialog.textField.getText());
                        list = model.getWHERElike();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }


                int size = list.size();

                int i = 0 ;
                while (i < size)
                    dialog.listView.getItems().add(list.get(i++).getName());

                dialog.listView.setOnKeyPressed(OnListPressedBank("bank"));
                dialog.listView.setOnMouseClicked(OnMouseClickBank("bank"));
            }
        } ;
    }

    private EventHandler<ActionEvent> DialogTextFieldAccount( ) {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                account model = new account();
                dialog.listView.getItems().removeAll(dialog.listView.getItems());

                if (Pattern.matches("[0-9]+",dialog.textField.getText())) {
                    try {
                        int id = Integer.parseInt(dialog.textField.getText()) ;
                        model.setNumber(dialog.textField.getText());
                        listAccount = model.getInfoWHERENumber();
                        ref = model.getInfoWHERENumber().get(0).getNumber();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }else
                if (Pattern.matches("[\\w|\\W]+",dialog.textField.getText()))
                {
                    try {
                        model.setName(dialog.textField.getText());
                        listAccount = model.getWHERElike();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }


                int size = listAccount.size();

                int i = 0 ;
                while (i < size)
                    dialog.listView.getItems().add(listAccount.get(i++).getName());

                dialog.listView.setOnKeyPressed(OnListPressedBank("account"));
                dialog.listView.setOnMouseClicked(OnMouseClickBank("account"));
            }
        } ;
    }

    private EventHandler<ActionEvent> onAccount_V_Action() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                dialog = new FXDialog(view.pane, "Account List . . . ", true);


                org.softwareengine.core.model.account model = new account();


                int i = 0;
                int size = 0;
                String way = "account";
                try {
                    size = model.getInfo().size();

                    System.out.println("the size is " + size);

                    while (i < size)
                        dialog.listView.getItems().add(model.getInfo().get(i++).getName());

                    dialog.show();
                    dialog.listView.setOnKeyPressed(OnListPressedBank(way));
                    dialog.listView.setOnMouseClicked(OnMouseClick(way));
                    dialog.textField.setOnAction(DialogTextFieldAccount());

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private EventHandler<MouseEvent> OnMouseClick (String way) {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                if (event.getClickCount() < 2 )
                    return;
                ListEvent(way);
                dialog.dialog.close();
            }
        } ;
    }
    private EventHandler<MouseEvent> OnMouseClickBank (String name ) {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                if (event.getClickCount() < 2 )
                    return;


                switch (name){
                    case "bank":
                        ListEventOnBankButton();
                        break;
                    case "account" :
                        ListEventOnAccountButton();
                        break;

                }
                dialog.dialog.close();
            }
        } ;
    }
        private EventHandler<KeyEvent> OnListPressed (String way) {
            return new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {

                    if (!(event.getCode() == KeyCode.ENTER))
                        return;
                    ListEvent(way);


                    dialog.dialog.close();

                }
            };
        }
    private EventHandler<KeyEvent> OnListPressedBank (String name ) {
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if (!(event.getCode() == KeyCode.ENTER))
                    return;

                switch (name) {
                    case "bank" :
                        ListEventOnBankButton();
                        break;
                    case "account" :
                        ListEventOnAccountButton();
                        break;
                }
                dialog.dialog.close();
            }
        };
    }


    private void ListEventOnBankButton() {
        banks model = new banks();

        int index = dialog.listView.getSelectionModel().getSelectedIndex() ;


        bankID = list.get(index).getId();
        model.setReferenceNumber(ref);

        view.bank.setText(list.get(index).getName());

        dialog.dialog.close();
    }
    private void ListEventOnAccountButton() {
        account model = new account();

        int index = dialog.listView.getSelectionModel().getSelectedIndex() ;

        account = listAccount.get(index).getNumber();
        model.setNumber(ref);

        System.out.println(listAccount.get(index).getName()+" here ");
        view.account.setText(listAccount.get(index).getName());

        dialog.dialog.close();
    }
        private void ListEvent (String way) {

            int index;
            index = dialog.listView.getSelectionModel().getSelectedIndex();

            switch (way) {

                case "bank" :
                try {
                    banks bank = new banks();
                    bankID = bank.getInfo().get(index).getId();
                    view.bank.setText(bank.getInfo().get(index).getName());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                    break;
                case "account" :
                try {
                    account acc = new account();
                    account = acc.getInfo().get(index).getNumber();
                    view.account.setText(acc.getInfo().get(index).getName());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

            }
        }
        private EventHandler<ActionEvent> OnSaveButton () {
            return new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                  Covenant model = new Covenant();
                  model.setId(view.id.getText());
                  model.setName(view.name.getText());
                  model.setRecipient(view.recipient.getText());
                  model.setBrnID(bankID);
                  model.setDes(view.desc.getText());
                  model.setAccount(account);
                  model.setDebit(Double.parseDouble(view.value.getText()));
                  model.setCredit(0);

                    try {
                        model.save();
                        getTableDetail();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    view.id.clear();
                    view.name.clear();
                    view.recipient.clear();
                    view.bank.clear();
                    view.desc.clear();
                    view.value.clear();
                    view.account.clear();


                }
            };
        }
    }