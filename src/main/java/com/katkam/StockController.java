package com.katkam;

import com.katkam.entity.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Developer on 1/18/17.
 */
@Controller
public class StockController {
    Session sess = GrizzlyHelper.getSession();

    @RequestMapping(value = "/stock-list", method = RequestMethod.GET)
    public ModelAndView getList() {
        ModelAndView mv = new ModelAndView("stock_list");
        mv.addObject("list", getListContent());
        return mv;
    }

    private List<Stock> getListContent() {
        List<Stock> lstRet;
        lstRet = (List<Stock>) sess.createCriteria(Stock.class).list(); //simple, but deprecated TODO: Change this
        return lstRet;
    }

    @RequestMapping(value = "/stock", method = RequestMethod.GET)
    public String getIndex() { return "stock_index"; }

    @RequestMapping(value = "/stock-edit", method = RequestMethod.GET)
    public ModelAndView getEdit(
        @RequestParam(name = "id", defaultValue = "-1")
        int a_id
    ) {
        ModelAndView mv = new ModelAndView("stock_edit");

        if (a_id==-1) {
        } else {
            Stock m = sess.byId(Stock.class).load(a_id);
            mv.addObject("m", m);
        }

        List<Part> parts = sess.createCriteria(Part.class).list();
        List<Store> stores = sess.createCriteria(Store.class).list();

        mv.addObject("parts", parts);
        mv.addObject("stores", stores);

        return mv;
    }

    @RequestMapping(value = "/stock-delete", method = RequestMethod.POST)
    public String postDelete(@RequestParam("id") int a_id) {
        Stock m = sess.byId(Stock.class).load(a_id);

        if (m.getQty()==0) { //Ensure stock is zero before deleting
            Transaction t = sess.beginTransaction();
            sess.delete(m);
            t.commit();
        }

        return "redirect:/stock-list";
    }

    @RequestMapping(value = "/stock-save", method = RequestMethod.POST)
    public String postSave(
        @ModelAttribute Stock a_m,
        @RequestParam(name = "store_id") int store_id,
        @RequestParam(name = "part_id") int part_id
    ) {
        Transaction t = sess.beginTransaction();

        Xact xact = new Xact();

        a_m.setStore(sess.byId(Store.class).load(store_id));
        a_m.setPart(sess.byId(Part.class).load(part_id));

        xact.setStore(a_m.getStore());
        xact.setPart(a_m.getPart());
        xact.setDate(new java.util.Date());

        if (a_m.getId()==-1) {
            a_m.setId(0);
            xact.setQty(a_m.getQty());
            sess.save(a_m);
        } else {
            xact.setQty(a_m.getQty() - sess.byId(Stock.class).load(a_m.getId()).getQty());
            sess.merge(a_m);
        }

        sess.save(xact);

        t.commit();

        return "redirect:/stock";
    }

    //TODO: Add direct issue without pick ticket

    @RequestMapping(value = "/stock-issue-pickticket-list")
    public ModelAndView stockIssuePickticketList() {
        ModelAndView mv = new ModelAndView("stock_issue_pickticket_list");
        List<PickticketHeader> picktickets = sess.createCriteria(PickticketHeader.class).list();
        //TODO Do not include picktickets with all items already issued
        mv.addObject("picktickets", picktickets);
        return mv;
    }
}
