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
 * Created by Developer on 1/29/17.
 */
@Controller
public class PurchaseOrderController {
    Session sess = GrizzlyHelper.getSession();

    @RequestMapping("/purchaseorder")
    public String getIndex() { return "purchaseorder_index"; }

    @RequestMapping("/purchaseorder-list")
    public ModelAndView getList() {
        ModelAndView mv = new ModelAndView("purchaseorder_list");
        List<PurchaseOrderHeader> pos = sess.createCriteria(PurchaseOrderHeader.class).list();
        mv.addObject("pos", pos);
        return mv;
    }

    @RequestMapping("/purchaseorder-edit")
    public ModelAndView getEdit(
        @RequestParam(value = "id", defaultValue = "-1")
        int id
    ) {
        ModelAndView mv = new ModelAndView("purchaseorder_edit");

        if (id != -1) {
            PurchaseOrderHeader m = sess.byId(PurchaseOrderHeader.class).load(id);
            mv.addObject("m", m);
            List<PurchaseOrderLine> lines = sess.createQuery("from PurchaseOrderLine where header_id = :po").setParameter("po", id).list();
            mv.addObject("lines", lines);
            List<Part> parts = sess.createCriteria(Part.class).list();
            mv.addObject("parts", parts);
        }

        List<Store> stores = sess.createCriteria(Store.class).list();
        mv.addObject("stores", stores);

        return mv;
    }

    @RequestMapping(value = "/purchaseorder-delete", method = RequestMethod.POST)
    public String postDelete(
        @RequestParam("id")
        int id
    ) {
        PurchaseOrderHeader m = sess.byId(PurchaseOrderHeader.class).load(id);
        Transaction t = sess.beginTransaction();
        sess.delete(m);
        t.commit();
        return "redirect:/purchaseorder";
    }

    @RequestMapping(value = "/purchaseorderline-delete", method = RequestMethod.POST)
    public String postDeleteLine(
        @RequestParam("id")
        int id
    ) {
        PurchaseOrderLine m = sess.byId(PurchaseOrderLine.class).load(id);
        Transaction t = sess.beginTransaction();
        sess.delete(m);
        t.commit();
        return "redirect:/purchaseorder";
    }

    @RequestMapping(value = "/purchaseorder-save", method = RequestMethod.POST)
    public String postSave(
        @ModelAttribute
        PurchaseOrderHeader m,
        @RequestParam("store_id")
        int store_id
    ) {
        m.setStore(sess.byId(Store.class).load(store_id));
        Transaction t = sess.beginTransaction();

        if (m.getId()==-1) {
            m.setId(0);
            m.setSubmittedDate(new java.util.Date());
            sess.save(m);
        } else {
            sess.merge(m);
        }

        t.commit();
        return "redirect:/purchaseorder-list";
    }

    @RequestMapping(value = "/purchaseorderline-save", method = RequestMethod.POST)
    public String postSaveLine(
        @RequestParam("header_id")
        int header_id,
        @RequestParam("part_id")
        int part_id,
        @RequestParam("qty")
        double qty
    ) {
        PurchaseOrderLine pl = new PurchaseOrderLine();
        pl.setHeader(sess.byId(PurchaseOrderHeader.class).load(header_id));
        pl.setPart(sess.byId(Part.class).load(part_id));
        pl.setQty(qty);

        Transaction t = sess.beginTransaction();
        sess.save(pl);
        t.commit();

        return "redirect:/purchaseorder";
    }

    @RequestMapping(value = "/purchaseorder-from-requisition", method = RequestMethod.POST)
    public String postGeneratePoFromPr(
        @RequestParam("id")
        int id
    ) {
        PurchaseOrderHeader purchaseOrderHeader = new PurchaseOrderHeader();
        List<PurchaseOrderLine> purchaseOrderLines = new ArrayList<PurchaseOrderLine>();

        RequisitionHeader requisitionHeader = sess.byId(RequisitionHeader.class).load(id);
        purchaseOrderHeader.setSubmittedDate(new java.util.Date());
        //purchaseOrderHeader.setSubmittedDate(requisitionHeader.getSubmittedDate());
        purchaseOrderHeader.setStore(requisitionHeader.getStore());
        purchaseOrderHeader.setName(requisitionHeader.getName());
        purchaseOrderHeader.setRefno(requisitionHeader.getRefno());
        purchaseOrderHeader.setDeliverTo(requisitionHeader.getDeliverTo());

        for (RequisitionLine iterLine : requisitionHeader.getLines()) {
            PurchaseOrderLine iterPoLine = new PurchaseOrderLine();
            iterPoLine.setQty(iterLine.getQty());
            iterPoLine.setPart(iterLine.getPart());
            iterPoLine.setHeader(purchaseOrderHeader);
            purchaseOrderLines.add(iterPoLine);
        }

        Transaction t = sess.beginTransaction();

        sess.save(purchaseOrderHeader);
        for (PurchaseOrderLine iterPoLine : purchaseOrderLines) {
            sess.save(iterPoLine);
        }

        //TODO Associate PR to PO

        t.commit();
        return "redirect:/purchaseorder";
    }
}
