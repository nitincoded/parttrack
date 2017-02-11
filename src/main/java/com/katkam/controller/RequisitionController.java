package com.katkam.controller;

import com.katkam.GrizzlyHelper;
import com.katkam.entity.*;
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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Developer on 1/22/17.
 */
@Controller
public class RequisitionController {
    Session sess = GrizzlyHelper.getSession();
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/requisition-list", method = RequestMethod.GET)
    public ModelAndView getList() {
        ModelAndView mv = new ModelAndView("requisition_list");

        List<RequisitionHeader> list = getListContent();
        log.trace(String.format("Requisitions fetched: %d", list.size()));
        mv.addObject("list", list);

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
        log.trace(String.format("Stores fetched: %d", stores.size()));
        mv.addObject("stores", stores);

        if (a_id == -1) {
            log.trace("Editing new requisition record");
        } else {
            RequisitionHeader m = sess.byId(RequisitionHeader.class).load(a_id);
            log.trace(String.format("Editing requisition record: %s (%d)", m.getName(), m.getId()));
            mv.addObject("m", m);

            List<RequisitionLine> lines = (List<RequisitionLine>) sess.createQuery("from RequisitionLine where header_id = :code").setParameter("code", a_id).list();
            log.trace(String.format("Requisition lines fetched: %d", lines.size()));
            mv.addObject("lines", lines);

            List<Part> parts = sess.createCriteria(Part.class).list();
            log.trace(String.format("Parts fetched: %d", parts.size()));
            mv.addObject("parts", parts);
        }

        return mv;
    }

    @RequestMapping(value = "/requisition-delete", method = RequestMethod.POST)
    public String postDelete(@RequestParam("id") int a_id) {
        RequisitionHeader m = sess.byId(RequisitionHeader.class).load(a_id);

        Transaction t = sess.beginTransaction();
        log.trace(String.format("Deleting requisition record: %s (%d)", m.getName(), m.getId()));
        sess.delete(m);
        t.commit();

        return "redirect:/requisition-list";
    }


    @RequestMapping(value = "/requisitionline-delete", method = RequestMethod.POST)
    public String postDeleteLine(@RequestParam("id") int a_id) {
        RequisitionLine m = sess.byId(RequisitionLine.class).load(a_id);

        int header_id = m.getHeader().getId();

        Transaction t = sess.beginTransaction();
        log.trace(String.format("Deleting requisition line record: %s - %s (%d)", m.getHeader().getName(), m.getPart().getName(), m.getId()));
        sess.delete(m);
        t.commit();

        return String.format("redirect:/requisition-edit?id=%d", header_id);
    }

    @RequestMapping(value = "/requisition-save", method = RequestMethod.POST)
    public String postSave(
        @ModelAttribute
        RequisitionHeader m,
        @RequestParam("store_id")
        int store_id
    ) {
        m.setStore(sess.byId(Store.class).load(store_id));
        Transaction t = sess.beginTransaction();

        if (m.getId()==-1) {
            m.setId(0);
            sess.save(m);
            log.trace(String.format("Saved new requisition record: %s (%d)", m.getName(), m.getId()));
        } else {
            sess.merge(m);
            log.trace(String.format("Saved existing requisition record: %s (%d)", m.getName(), m.getId()));
        }

        t.commit();

        return "redirect:/requisition-list";
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
//        request.getParameter("param_name")); to get any additional parameters

        RequisitionLine rl = new RequisitionLine();
        rl.setQty(qty);
        rl.setPart(sess.byId(Part.class).load(part_id));
        rl.setHeader(sess.byId(RequisitionHeader.class).load(header_id));
        //TODO handle case when same part is already in the list

        //TODO Store attribute

        Transaction t = sess.beginTransaction();
        sess.save(rl);
        log.trace(String.format("Saved new requisition line record: %s - %s (%d)", rl.getHeader().getName(), rl.getPart().getName(), rl.getId()));
        t.commit();

        return String.format("redirect:/requisition-edit?id=%d", header_id);
    }

    //TODO Add RequisitionLine functions
}
