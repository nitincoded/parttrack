package com.katkam.controller;

import com.katkam.GrizzlyHelper;
import com.katkam.entity.Supplier;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Developer on 2/1/17.
 */
@Controller
public class SupplierController {
    Session sess = GrizzlyHelper.getSession();

    @RequestMapping(value = "/supplier-list", method = RequestMethod.GET)
    public ModelAndView getList() {
        ModelAndView mv = new ModelAndView("supplier_list");
        mv.addObject("list", sess.createCriteria(Supplier.class).list());
        return mv;
    }

    @RequestMapping(value = "/supplier", method = RequestMethod.GET)
    public String getIndex() { return "supplier_index"; }

    @RequestMapping(value = "/supplier-edit")
    public ModelAndView getEdit(
        @RequestParam(name = "id", defaultValue = "-1")
        int a_id
    ) {
        ModelAndView mv = new ModelAndView("supplier_edit");
        if (a_id==-1) {} else {
            Supplier m = sess.byId(Supplier.class).load(a_id);
            mv.addObject("m", m);
        }
        return mv;
    }

    @RequestMapping(value = "/supplier-delete", method = RequestMethod.POST)
    public String postDelete(@RequestParam("id") int a_id) {
        Supplier m = sess.byId(Supplier.class).load(a_id);
        Transaction t = sess.beginTransaction();
        sess.delete(m);
        t.commit();
        return "redirect:/supplier";
    }

    @RequestMapping(value = "/supplier-save", method = RequestMethod.POST)
    public String postSave(
        @ModelAttribute
        Supplier m
    ) {
        Transaction t = sess.beginTransaction();

        if (m.getId()==-1) {
            m.setId(0);
            sess.save(m);
        } else {
            sess.merge(m);
        }

        t.commit();

        return "redirect:/supplier";
    }
}
