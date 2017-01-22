package com.katkam;

import com.katkam.entity.Store;
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
public class StoreController {
    Session sess = GrizzlyHelper.getSession();

    @RequestMapping(value = "/store-list", method = RequestMethod.GET)
    public ModelAndView getList() {
        ModelAndView mv = new ModelAndView("store_list");
        mv.addObject("list", getListContent());
        return mv;
    }

    private List<Store> getListContent() {
        List<Store> lstRet;
        lstRet = (List<Store>) sess.createCriteria(Store.class).list();
        return lstRet;
    }

    @RequestMapping(value = "/store", method = RequestMethod.GET)
    public String getIndex() { return "store_index"; }

    @RequestMapping(value = "/store-edit", method = RequestMethod.GET)
    public ModelAndView getEdit(
        @RequestParam(name = "id", defaultValue = "-1")
        int a_id
    ) {
        ModelAndView mv = new ModelAndView("store_edit");

        if (a_id==-1) {
        } else {
            Store m = sess.byId(Store.class).load(a_id);
            mv.addObject("m", m);
        }

        return mv;
    }

    @RequestMapping(value = "/store-delete", method = RequestMethod.POST)
    public String postDelete(@RequestParam("id") int a_id) {
        Store m = sess.byId(Store.class).load(a_id);

        Transaction t = sess.beginTransaction();
        sess.delete(m);
        t.commit();

        return "redirect:/store-list";
    }

    @RequestMapping(value = "/store-save", method = RequestMethod.POST)
    public String postSave(
        @ModelAttribute
        Store a_m
    ) {
        Transaction t = sess.beginTransaction();

        if (a_m.getId()==-1) {
            a_m.setId(0);
            sess.save(a_m);
        } else {
            sess.merge(a_m);
        }

        t.commit();

        return "redirect:/store";
    }
}
