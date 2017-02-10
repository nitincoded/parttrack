package com.katkam.controller;

import com.katkam.GrizzlyHelper;
import com.katkam.entity.Customer;
import com.katkam.entity.Employee;
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

import java.util.List;

/**
 * Created by Developer on 2/1/17.
 */
@Controller
public class CustomerController {
    Session sess = GrizzlyHelper.getSession();
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/customer-list", method = RequestMethod.GET)
    public ModelAndView getList() {
        ModelAndView mv = new ModelAndView("customer_list");

        List<Customer> list = sess.createCriteria(Customer.class).list();
        log.trace(String.format("Customers fetched: %d", list.size()));
        mv.addObject("list", list);

        return mv;
    }

    @RequestMapping(value = "/customer", method = RequestMethod.GET)
    public String getIndex() { return "customer_index"; }

    @RequestMapping(value = "/customer-edit", method = RequestMethod.GET)
    public ModelAndView getEdit(
        @RequestParam(name = "id", defaultValue = "-1")
        int a_id
    ) {
        ModelAndView mv = new ModelAndView("customer_edit");

        if (a_id==-1) {
            log.trace("Editing new customer record");
        } else {
            Customer m = sess.byId(Customer.class).load(a_id);
            log.trace(String.format("Editing customer record: %s (%d)", m.getName(), m.getId()));
            mv.addObject("m", m);
        }

        return mv;
    }

    @RequestMapping(value = "/customer-delete", method = RequestMethod.POST)
    public String postDelete(@RequestParam("id") int a_id) {
        Customer m = sess.byId(Customer.class).load(a_id);

        Transaction t = sess.beginTransaction();
        log.trace(String.format("Deleting customer record: %s (%d)", m.getName(), m.getId()));
        sess.delete(m);
        t.commit();

        return "redirect:/customer";
    }

    @RequestMapping(value = "/customer-save", method = RequestMethod.POST)
    public String postSave(
        @ModelAttribute
        Customer m
    ) {
        Transaction t = sess.beginTransaction();

        if (m.getId()==-1) {
            m.setId(0);
            sess.save(m);
            log.trace(String.format("Saved new customer record: %s (%d)", m.getName(), m.getId()));
        } else {
            sess.merge(m);
            log.trace(String.format("Saved existing customer record: %s (%d)", m.getName(), m.getId()));
        }

        t.commit();
        return "redirect:/customer-list";
    }
}
