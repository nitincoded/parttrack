package com.katkam;

import com.katkam.entity.Part;
import com.katkam.entity.Stock;
import com.katkam.entity.Store;
import com.katkam.entity.Xact;
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
        @ModelAttribute Stock a_stock,
        @RequestParam(name = "store_id") int store_id,
        @RequestParam(name = "part_id") int part_id
    ) {
        Transaction t = sess.beginTransaction();

        Xact xact = new Xact();

        a_stock.setStore(sess.byId(Store.class).load(store_id));
        a_stock.setPart(sess.byId(Part.class).load(part_id));

        xact.setStore(a_stock.getStore());
        xact.setPart(a_stock.getPart());

        if (a_stock.getId()==-1) {
            a_stock.setId(0);
            xact.setQty(a_stock.getQty());
            sess.save(a_stock);
        } else {
            xact.setQty(sess.byId(Stock.class).load(a_stock.getId()).getQty() - a_stock.getQty());
            sess.save(xact);
            sess.merge(a_stock);
        }

        t.commit();

        return "redirect:/stock";
    }
}
