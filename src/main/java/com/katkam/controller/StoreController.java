package com.katkam.controller;

import com.katkam.GrizzlyHelper;
import com.katkam.entity.Store;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    Session sess = new GrizzlyHelper().getSession();
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/store-list", method = RequestMethod.GET)
    public ModelAndView getList() {
        ModelAndView mv = new ModelAndView("store_list");

        List<Store> list = getListContent();
        log.trace(String.format("Stores fetched: %d", list.size()));
        mv.addObject("list", list);

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
            log.trace("Editing new store record");
        } else {
            Store m = sess.byId(Store.class).load(a_id);
            log.trace(String.format("Editing store record: %s (%d)", m.getName(), m.getId()));
            mv.addObject("m", m);
        }

        return mv;
    }

    @RequestMapping(value = "/store-delete", method = RequestMethod.POST)
    public String postDelete(@RequestParam("id") int a_id) {
        Store m = sess.byId(Store.class).load(a_id);

        Transaction t = sess.beginTransaction();
        log.trace(String.format("Deleting store record: %s (%d)", m.getName(), m.getId()));
        sess.delete(m);
        t.commit();

        return "redirect:/store-list";
    }

    @RequestMapping(value = "/store-save", method = RequestMethod.POST)
    public String postSave(
        @ModelAttribute
        Store m
    ) {
        Transaction t = sess.beginTransaction();

        if (m.getId()==-1) {
            m.setId(0);
            sess.save(m);
            log.trace(String.format("Saved new store record: %s (%d)", m.getName(), m.getId()));
        } else {
            sess.merge(m);
            log.trace(String.format("Saved existing store record: %s (%d)", m.getName(), m.getId()));
        }

        t.commit();

        return "redirect:/store-list";
    }
}
