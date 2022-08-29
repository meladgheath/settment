package org.softwareengine.utils.report;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.softwareengine.core.model.Transaction;
import org.softwareengine.utils.service.DatabaseService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class report {


    public JasperPrint getReport(int covnenat) throws JRException, SQLException {

        JasperReport reports = null ;
        JasperPrint jp = null ;

        reports = JasperCompileManager.compileReport(
                getClass().getResourceAsStream("/report/covn.jrxml")
        );

        jp = JasperFillManager.fillReport(reports,null, DatabaseService.connection);

//        List<Amount> list = new ArrayList<Amount>();
        List<Transaction> list = new ArrayList<>();

//        Amount model = new Amount();
        Transaction model = new Transaction();
        model.setCovnenatID(covnenat);

        int size = model.getInfo().size();

        for (int i=0 ; i < size ; i++) {
            Transaction temp = new Transaction();

            temp.setCovnenatID(model.getInfo().get(i).getCovnenatID());
            temp.setCredit(model.getInfo().get(i).getCredit());
            temp.setDebit(model.getInfo().get(i).getDebit());
            temp.setSeq(model.getInfo().get(i).getSeq());
            temp.setName(model.getInfo().get(i).getName());

            list.add(temp) ;

        }

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);

        JasperPrint print = JasperFillManager.fillReport(reports,null,dataSource);

//        JasperViewer.viewReport(print,false);
        return print ;
    }

}

/*

   */
