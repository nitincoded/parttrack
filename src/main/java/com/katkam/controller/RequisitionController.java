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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Developer on 1/22/17.
 */
@Controller
public class RequisitionController {
    Session sess = GrizzlyHelper.getSession();

    @RequestMapping(value = "/requisition-list", method = RequestMethod.GET)
    public ModelAndView getList() {
        ModelAndView mv = new ModelAndView("requisition_list");
        mv.addObject("list", getListContent());
        return mv;
    }

    private List<RequisitionHeader> getListContent() {
        List<RequisitionHeader> lstRet = new ArrayList<RequisitionHeader>();
        lstRet = sess.createCriteria(RequisitionHeader.class).list();
        return lstRet;
    }

    @RequestMapping(value = "/requisition", method = RequestMethod.GET)
    public String getIndex() {
        return "requisition_index";
    }

    @RequestMapping(value = "/requisition-edit", method = RequestMethod.GET)
    public ModelAndView getEdit(
        @RequestParam(name = "id", defaultValue = "-1")
        int a_id
    ) {
        ModelAndView mv = new ModelAndView("requisition_edit");

        List<Store> stores = sess.createCriteria(Store.class).list();
        mv.addObject("stores", stores);

        mv.addObject("r", "rock");

        if (a_id == -1) {
            //mv.addObject("m", null);
        } else {
            RequisitionHeader m = sess.byId(RequisitionHeader.class).load(a_id);
            mv.addObject("m", m);
            List<RequisitionLine> lines = (List<RequisitionLine>) sess.createQuery("from RequisitionLine where header_id = :code").setParameter("code", a_id).list();
            mv.addObject("lines", lines);
            List<Part> parts = sess.createCriteria(Part.class).list();
            mv.addObject("parts", parts);
        }

        return mv;
    }

    @RequestMapping(value = "/requisition-delete", method = RequestMethod.POST)
    public String postDelete(@RequestParam("id") int a_id) {
        RequisitionHeader m = sess.byId(RequisitionHeader.class).load(a_id);

        Transaction t = sess.beginTransaction();
        sess.delete(m);
        t.commit();

        return "redirect:/requisition-list";
    }


    @RequestMapping(value = "/requisitionline-delete", method = RequestMethod.POST)
    public String postDeleteLine(@RequestParam("id") int a_id) {
        RequisitionLine m = sess.byId(RequisitionLine.class).load(a_id);

        Transaction t = sess.beginTransaction();
        sess.delete(m);
        t.commit();

        return "redirect:/requisition-list"; //TODO redirect to the same requisition
    }

    @RequestMapping(value = "/requisition-save", method = RequestMethod.POST)
    public String postSave(
        @ModelAttribute
        RequisitionHeader a_m,
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

        return "redirect:/requisition";
    }

    @RequestMapping(value = "/requisitionline-save", method = RequestMethod.POST)
    public String postSaveLine(
//        HttpServletRequest request
        @RequestParam("header_id")
        int header_id,
        @RequestParam("part_id")
        int part_id,
        @RequestParam("qty")
        double qty
    ) {
//        int header_id = Integer.parseInt(request.getParameter("header_id"));
//        int part_id = Integer.parseInt(request.getParameter("part_id"));
//        double qty = Double.parseDouble(request.getParameter("qty"));

        Transaction t = sess.beginTransaction();

        RequisitionLine rl = new RequisitionLine();
        rl.setQty(qty);
        rl.setPart(sess.byId(Part.class).load(part_id));
        rl.setHeader(sess.byId(RequisitionHeader.class).load(header_id));
        //TODO handle case when same part is already in the list

        //TODO Store attribute

        sess.save(rl);

        t.commit();

        return "redirect:/requisition"; //TODO redirect to the same requisition
    }

    //TODO Add RequisitionLine functions
}
