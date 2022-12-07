package org.softwareengine.core.controller;

import com.jfoenix.controls.events.JFXDialogEvent;
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

import org.softwareengine.core.model.Transaction;
import org.softwareengine.core.model.account;
import org.softwareengine.core.model.banks;
import org.softwareengine.core.view.settmentView;
import org.softwareengine.utils.report.report;
import org.softwareengine.utils.ui.FXDialog;

import java.sql.SQLException;
import java.util.Locale;
import java.util.regex.Pattern;

public class settmentController {



        public settmentView view;
        public FXDialog dialog;

        String covenantID   ;

        ObservableList<account> listAccount ;
        String ref ;

        String account  ;
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
            view.accountTx.setText(lang.getWord("account"));
            view.saveButton.setText(lang.getWord("save"));
            view.printButton.setText(lang.getWord("print"));
            view.printMenu.setText(lang.getWord("print"));

            ((TableColumn) view.tableView.getColumns().get(0)).setText(lang.getWord("id"));//seq
            ((TableColumn) view.tableView.getColumns().get(1)).setText(lang.getWord("CovenantID"));//covnenatID
            ((TableColumn) view.tableView.getColumns().get(2)).setText(lang.getWord("name"));//name
            ((TableColumn) view.tableView.getColumns().get(3)).setText(lang.getWord("debit"));//debit
            ((TableColumn) view.tableView.getColumns().get(4)).setText(lang.getWord("credit"));//credit
            ((TableColumn) view.tableView.getColumns().get(5)).setText(lang.getWord("account"));//account


            if (Locale.getDefault().getLanguage().equalsIgnoreCase("ar"))
                view.root.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            else
                view.root.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        }

        private void initiate() throws SQLException {
            getTableColum();
            view.VCovnenatIDButton.setOnAction(onCovenantButton());
            view.VaccountButton.setOnAction(onAccount_V_Action());

            view.saveButton.setOnAction(OnSaveButton());
            view.printButton.setOnAction(onPrintButton());
            view.tableView.setOnMouseClicked(ontableMouseClick());
            view.tableMenu.getItems().get(0).setOnAction(onPrintMenu());
        }
        private EventHandler<ActionEvent> onPrintMenu() {
            return new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    report r = new report() ;
                    int index = view.tableView.getSelectionModel().getSelectedIndex() ;
                    Transaction transaction = (Transaction) view.tableView.getItems().get(index);
                    Covenant c = new Covenant() ;
                    c.setId(transaction.getCovnenatID());

                    try {
                        c = c.getInfoWHEREid() ;

                        c.setAccount(transaction.getAccount());
                        c.setCredit(transaction.getCredit());
                        JasperViewer.viewReport(r.getSingleTransaction(c,"قيد إضافة"),false);

                    } catch (JRException e) {
                        throw new RuntimeException(e);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
        }

    private EventHandler<MouseEvent> ontableMouseClick() {
            return new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.isSecondaryButtonDown())
                        view.tableMenu.show(view.tableView,event.getScreenX(),event.getScreenY());
                }
            } ;
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
                    System.out.println("here ");
                    dialog.listView.setOnKeyPressed(OnListPressed2());
                    dialog.listView.setOnMouseClicked(OnMouseClick2());
                    dialog.textField.setOnAction(DialogTextFieldAccount());

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        };
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

    private EventHandler<MouseEvent> OnMouseClickBank (String name) {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                if (event.getClickCount() < 2 )
                    return;
                ListEventOnAccountButton();
                dialog.dialog.close();
            }
        };
    }
    private EventHandler<KeyEvent> OnListPressedBank (String name) {
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if (!(event.getCode() == KeyCode.ENTER))
                    return;

                ListEventOnAccountButton();
                dialog.dialog.close();
            }
        };
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

    private void getTableColum() throws SQLException {

            TableColumn<Integer, Transaction> seq = new TableColumn<>();
            TableColumn<Integer,Transaction> id = new TableColumn<>();
            TableColumn<String, Transaction> name = new TableColumn<>();
            TableColumn<String, Transaction> account = new TableColumn<>();
            TableColumn<Double,Transaction> debit  = new TableColumn<>();
            TableColumn<Double,Transaction> credit = new TableColumn<>();

            seq.setCellValueFactory(new PropertyValueFactory<>("seq"));
            id.setCellValueFactory(new PropertyValueFactory<>("covnenatID"));
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            debit.setCellValueFactory(new PropertyValueFactory<>("debit"));
            credit.setCellValueFactory(new PropertyValueFactory<>("credit"));
            account.setCellValueFactory(new PropertyValueFactory<>("account"));

            seq.setMaxWidth(50);
            seq.setMinWidth(25);

            view.tableView.getColumns().add(seq);
            view.tableView.getColumns().add(id);
            view.tableView.getColumns().add(name);
            view.tableView.getColumns().add(debit);
            view.tableView.getColumns().add(credit);
            view.tableView.getColumns().add(account);

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
                        dialog.listView.setOnKeyPressed(OnListPressed2());
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

                String id = dialog.textField.getText();

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
    private EventHandler<MouseEvent> OnMouseClick2 () {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                if (event.getClickCount() < 2 )
                    return;
                ListEvent2();
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

    private EventHandler<KeyEvent> OnListPressed2 () {
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if (!(event.getCode() == KeyCode.ENTER))
                    return;

                ListEvent2();


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
    private void ListEvent2 () {

        int index;
        index = dialog.listView.getSelectionModel().getSelectedIndex();


                try {
                    account acc = new account();
                    account = acc.getInfo().get(index).getNumber();

                    view.account.setText(acc.getInfo().get(index).getName());
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
                    model.setAccount(account);

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