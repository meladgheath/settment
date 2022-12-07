package org.softwareengine.utils.report;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.type.WhenNoDataTypeEnum;
import org.softwareengine.config.languages;
import org.softwareengine.core.model.Covenant;
import org.softwareengine.core.model.Transaction;
import org.softwareengine.core.model.account;
import org.softwareengine.core.model.user;
import org.softwareengine.utils.service.ArabicNumberToWords;
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
    public JasperPrint getSingleConvenant(Covenant covenant,String typeReport) throws JRException, SQLException {
        JasperReport reports = null ;
        JasperPrint jp = null ;
        reports = JasperCompileManager.compileReport(
                getClass().getResourceAsStream("/report/single.jrxml")
        );

        jp = JasperFillManager.fillReport(reports,null, DatabaseService.connection);

        languages lang = new languages();

        reports.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);

        account a = new account();
        a.setNumber(covenant.getAccount());
        a = a.getInfoWHERENumber().get(0);

        HashMap params = new HashMap();
        params.put("REPORT_RESOURCE_BUNDLE", lang.get());
        params.put("typeReport",typeReport);
        params.put("account",covenant.getAccount());
        params.put("accountName",a.getName()) ;
        params.put("covID",covenant.getId());
        params.put("name","  "+covenant.getName());
        params.put("desc","  "+covenant.getDes());
        params.put("recipient",covenant.getRecipient());
        params.put("values",covenant.getDebit());
        params.put("brn",covenant.getBrnName());
        params.put("user", user.getName());
        params.put("arabicValue",new ArabicNumberToWords(covenant.getDebit()+"").get()) ;

        JasperPrint print = JasperFillManager.fillReport(reports,params);
        return print ;
    }
    public JasperPrint getSingleTransaction(Covenant covenant,String typeReport) throws JRException, SQLException {
        JasperReport reports = null ;
        JasperPrint jp = null ;
        reports = JasperCompileManager.compileReport(
                getClass().getResourceAsStream("/report/single.jrxml")
        );

        jp = JasperFillManager.fillReport(reports,null, DatabaseService.connection);

        languages lang = new languages();

        reports.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);

        account a = new account();
        a.setNumber(covenant.getAccount());
        a = a.getInfoWHERENumber().get(0);

        System.out.println(covenant.getDes());
        HashMap params = new HashMap();
        params.put("REPORT_RESOURCE_BUNDLE", lang.get());
        params.put("typeReport",typeReport);
        params.put("account",covenant.getAccount());
        params.put("accountName",a.getName());
        params.put("covID",covenant.getId());
        params.put("name","  "+covenant.getName());
        params.put("desc","  "+covenant.getDes());
//        params.put("recipient",covenant.getRecipient());
        params.put("values",covenant.getCredit());
        params.put("brn",covenant.getBrnName());
        params.put("user",user.getName());
        params.put("arabicValue",new ArabicNumberToWords(covenant.getCredit()+"").get());


//        params.put("covID",);

//        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
        JasperPrint print = JasperFillManager.fillReport(reports,params);
        return print ;
    }
}