package com.katkam;

import com.katkam.entity.Stock;
import org.hibernate.Session;
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

        return mv;
    }

    @RequestMapping(value = "/stock-delete", method = RequestMethod.POST)
    public String postDelete(@RequestParam("id") int a_id) {

        return null;
    }

    @RequestMapping(value = "/stock-save", method = RequestMethod.POST)
    public String postSave(
        @ModelAttribute
        Stock a_stock
    ) {
        return null;
    }
}
