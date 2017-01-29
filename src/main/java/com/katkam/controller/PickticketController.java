package com.katkam.controller;

import com.katkam.GrizzlyHelper;
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
 * Created by Developer on 1/21/17.
 */
@Controller
public class PickticketController {
    Session sess = GrizzlyHelper.getSession();

    @RequestMapping(value = "/pickticket-list", method = RequestMethod.GET)
    public ModelAndView getList() {
        ModelAndView mv = new ModelAndView("pickticket_list");
        mv.addObject("list", getListContent());
        return mv;
    }

    private List<PickticketHeader> getListContent() {
        List<PickticketHeader> lstRet = new ArrayList<PickticketHeader>();
        lstRet = sess.createCriteria(PickticketHeader.class).list();
        return lstRet;
    }

    @RequestMapping(value = "/pickticket", method = RequestMethod.GET)
    public String getIndex() { return "pickticket_index"; }

    @RequestMapping(value = "/pickticket-edit", method = RequestMethod.GET)
    public ModelAndView getEdit(
        @RequestParam(name = "id", defaultValue = "-1")
        int a_id
    ) {
        ModelAndView mv = new ModelAndView("pickticket_edit");
        List<Store> stores = sess.createCriteria(Store.class).list();
        mv.addObject("stores", stores);
        if (a_id==-1) {
            //mv.addObject("m", null);
        } else {
            PickticketHeader m = sess.byId(PickticketHeader.class).load(a_id);
            mv.addObject("m", m);
            List<RequisitionLine> lines = (List<RequisitionLine>) sess.createQuery("from PickticketLine where header_id = :code").setParameter("code", a_id).list();
            mv.addObject("lines", lines);
            List<Part> parts = sess.createCriteria(Part.class).list();
            mv.addObject("parts", parts);
        }
        return mv;
    }

    @RequestMapping(value = "/pickticket-delete", method = RequestMethod.POST)
    public String postDelete(@RequestParam("id") int a_id) {
        PickticketHeader m = sess.byId(PickticketHeader.class).load(a_id);

        Transaction t = sess.beginTransaction();
        sess.delete(m);
        t.commit();

        return "redirect:/pickticket-list";
    }

    @RequestMapping(value = "/pickticketline-delete", method = RequestMethod.POST)
    public String postDeleteLine(@RequestParam("id") int a_id) {
        PickticketLine m = sess.byId(PickticketLine.class).load(a_id);

        Transaction t = sess.beginTransaction();
        sess.delete(m);
        t.commit();

        return "redirect:/pickticket-list"; //TODO return to the pickticket that the user was editing
    }

    @RequestMapping(value = "/pickticket-save", method = RequestMethod.POST)
    public String postSave(
            @ModelAttribute
            PickticketHeader a_m,
            @RequestParam("store_id")
            int store_id
    ) {
        Transaction t = sess.beginTransaction();

        a_m.setStore(sess.byId(Store.class).load(store_id));
        if (a_m.getId()==-1) {
            a_m.setId(0);
            sess.save(a_m);
        } else {
            sess.merge(a_m);
        }

        t.commit();

        return "redirect:/pickticket";
    }

    @RequestMapping(value = "/pickticketline-save", method = RequestMethod.POST)
    public String postSaveLine(
            @RequestParam("header_id")
                    int header_id,
            @RequestParam("part_id")
                    int part_id,
            @RequestParam("qty")
                    double qty
    ) {
        Transaction t = sess.beginTransaction();

        PickticketLine pl = new PickticketLine();
        pl.setPart(sess.byId(Part.class).load(part_id));
        pl.setHeader(sess.byId(PickticketHeader.class).load(header_id));
        pl.setQty(qty);
        //TODO handle case when same part is already in the list

        sess.save(pl);

        t.commit();

        return "redirect:/pickticket-list"; //TODO return to the pickticket that the user was editing
    }
}
