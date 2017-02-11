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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Developer on 1/21/17.
 */
@Controller
public class PickticketController {
    Session sess = GrizzlyHelper.getSession();
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/pickticket-list", method = RequestMethod.GET)
    public ModelAndView getList() {
        ModelAndView mv = new ModelAndView("pickticket_list");

        List<PickticketHeader> list = getListContent();
        log.trace(String.format("Pick tickets fetched: %d", list.size()));
        mv.addObject("list", list);

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
        log.trace(String.format("Stores fetched: %d", stores.size()));
        mv.addObject("stores", stores);

        if (a_id==-1) {
            log.trace("Editing new pick ticket record");
        } else {
            PickticketHeader m = sess.byId(PickticketHeader.class).load(a_id);
            log.trace(String.format("Editing customer record: %s (%d)", m.getName(), m.getId()));
            mv.addObject("m", m);

            List<RequisitionLine> lines = (List<RequisitionLine>) sess.createQuery("from PickticketLine where header_id = :code").setParameter("code", a_id).list();
            log.trace(String.format("Pick ticket lines fetched: %d", lines.size()));
            mv.addObject("lines", lines);

            List<Part> parts = sess.createCriteria(Part.class).list();
            log.trace(String.format("Parts fetched: %d", parts.size()));
            mv.addObject("parts", parts);
        }

        return mv;
    }

    @RequestMapping(value = "/pickticket-delete", method = RequestMethod.POST)
    public String postDelete(@RequestParam("id") int a_id) {
        PickticketHeader m = sess.byId(PickticketHeader.class).load(a_id);

        Transaction t = sess.beginTransaction();
        log.trace(String.format("Deleting pick ticket record: %s (%d)", m.getName(), m.getId()));
        sess.delete(m);
        t.commit();

        return "redirect:/pickticket-list";
    }

    @RequestMapping(value = "/pickticketline-delete", method = RequestMethod.POST)
    public String postDeleteLine(@RequestParam("id") int a_id) {
        PickticketLine m = sess.byId(PickticketLine.class).load(a_id);

        int header_id = m.getHeader().getId();

        Transaction t = sess.beginTransaction();
        log.trace(String.format("Deleting pick ticket line record: %s - %s (%d)", m.getHeader().getName(), m.getPart().getName(), m.getId()));
        sess.delete(m);
        t.commit();

        return String.format("redirect:/pickticket-edit?id=%d", header_id);
    }

    @RequestMapping(value = "/pickticket-save", method = RequestMethod.POST)
    public String postSave(
            @ModelAttribute
            PickticketHeader m,
            @RequestParam("store_id")
            int store_id
    ) {
        m.setStore(sess.byId(Store.class).load(store_id));
        Transaction t = sess.beginTransaction();

        if (m.getId()==-1) {
            m.setId(0);
            sess.save(m);
            log.trace(String.format("Saved new pick ticket record: %s (%d)", m.getName(), m.getId()));
        } else {
            sess.merge(m);
            log.trace(String.format("Saved existing pick ticket record: %s (%d)", m.getName(), m.getId()));
        }

        t.commit();

        return "redirect:/pickticket-list";
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
        PickticketLine pl = new PickticketLine();
        pl.setPart(sess.byId(Part.class).load(part_id));
        pl.setHeader(sess.byId(PickticketHeader.class).load(header_id));
        pl.setQty(qty);
        pl.setIssued_qty(0);
        //TODO handle case when same part is already in the list

        Transaction t = sess.beginTransaction();
        sess.save(pl);
        log.trace(String.format("Saved new pick ticket record: %s - %s (%d)", pl.getHeader().getName(), pl.getPart().getName(), pl.getId()));
        t.commit();

        //Pass redirect parameter using a RedirectAttributes method parameter and calling addAttribute
        //See: http://stackoverflow.com/questions/19266427/what-are-ways-for-pass-parameters-from-controller-after-redirect-in-spring-mvc

        return String.format("redirect:/pickticket-edit?id=%d", header_id);
    }
}
