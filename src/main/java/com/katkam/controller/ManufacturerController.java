package com.katkam.controller;

import com.katkam.GrizzlyHelper;
import com.katkam.entity.Manufacturer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Developer on 1/14/17.
 */
@Controller
public class ManufacturerController {
    Session sess = new GrizzlyHelper().getSession();
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/manufacturer-list", method = RequestMethod.GET)
    public ModelAndView getList() {
        ModelAndView mv = new ModelAndView("manufacturer_list");

        List<Manufacturer> list = getListContent();
        log.trace(String.format("Manufacturers fetched: %d", list.size()));
        mv.addObject("list", list);

        return mv;
    }

    private List<Manufacturer> getListContent() {
        List<Manufacturer> lstRet = new ArrayList<Manufacturer>();
        lstRet = (List<Manufacturer>) sess.createCriteria(Manufacturer.class).list();
        return lstRet;
    }

    @RequestMapping(value = "/manufacturer", method = RequestMethod.GET)
    public String getIndex() { return "manufacturer_index"; }

    @RequestMapping(value = "/manufacturer-edit", method = RequestMethod.GET)
    public ModelAndView getEdit(
        @RequestParam(name = "id", defaultValue = "-1")
        int a_id
    ) {
        ModelAndView mv = new ModelAndView("manufacturer_edit");

        if (a_id==-1) {
            log.trace("Editing new manufacturer record");
        } else {
//            Manufacturer m = sess.find(Manufacturer.class, new Manufacturer(a_id, null));
            Manufacturer m = sess.byId(Manufacturer.class).load(a_id);
            log.trace(String.format("Editing manufacturer record: %s (%d)", m.getName(), m.getId()));
            mv.addObject("m", m);
        }

        return mv;
    }

    @RequestMapping(value = "/manufacturer-delete", method = RequestMethod.POST)
    public String postDelete(@RequestParam("id") int a_id) {
        Manufacturer m = sess.byId(Manufacturer.class).load(a_id);

        Transaction t = sess.beginTransaction();
        log.trace(String.format("Deleting manufacturer record: %s (%d)", m.getName(), m.getId()));
        sess.delete(m);
        t.commit();

        return "redirect:/manufacturer-list";
    }

    @RequestMapping(value = "/manufacturer-save", method = RequestMethod.POST)
    public String postSave(
        @ModelAttribute
        Manufacturer m
    ) {
        //sess.merge(a_mfg);
        Transaction t = sess.beginTransaction();

        if (m.getId()==-1) {
            m.setId(0);
            sess.save(m);
            log.trace(String.format("Saved new manufacturer record: %s (%d)", m.getName(), m.getId()));
        } else {
            sess.merge(m);
            log.trace(String.format("Saved existing manufacturer record: %s (%d)", m.getName(), m.getId()));
            //sess.update(a_mfg);
        }

        //sess.saveOrUpdate(a_mfg);
        //sess.flush();
        t.commit();

        return "redirect:/manufacturer-list";
//        return  String.format("ID: %d, Name: %s", a_mfg.getId(), a_mfg.getName());
    }
}
