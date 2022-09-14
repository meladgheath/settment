package org.softwareengine.utils.report;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.softwareengine.config.languages;
import org.softwareengine.core.model.Transaction;
import org.softwareengine.utils.service.DatabaseService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class report {


    public JasperPrint getReport(List<Transaction> list,String sender) throws JRException, SQLException {

        JasperReport reports = null ;
        JasperPrint jp = null ;
        reports = JasperCompileManager.compileReport(
                getClass().getResourceAsStream("/report/covn.jrxml")
        );

        jp = JasperFillManager.fillReport(reports,null, DatabaseService.connection);

        languages lang = new languages();

        HashMap params = new HashMap();
        params.put("REPORT_RESOURCE_BUNDLE", lang.get());
        params.put("sender",sender);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
        JasperPrint print = JasperFillManager.fillReport(reports,params,dataSource);
        return print ;
    }
}