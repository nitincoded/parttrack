package com.katkam.controller;

import com.katkam.GrizzlyHelper;
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
public class EmployeeController {
    Session sess = new GrizzlyHelper().getSession();
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/employee-list", method = RequestMethod.GET)
    public ModelAndView getList() {
        ModelAndView mv = new ModelAndView("employee_list");

        List<Employee> list = sess.createCriteria(Employee.class).list();
        log.trace(String.format("Employees fetched: %d", list.size()));
        mv.addObject("list", list);

        return mv;
    }

    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    public String getIndex() { return "employee_index"; }

    @RequestMapping(value = "/employee-edit", method = RequestMethod.GET)
    public ModelAndView getEdit(
        @RequestParam(name = "id", defaultValue = "-1")
        int a_id
    ) {
        ModelAndView mv = new ModelAndView("employee_edit");

        if (a_id==-1) {
            log.trace("Editing new employee record");
        } else {
            Employee m = sess.byId(Employee.class).load(a_id);
            log.trace(String.format("Editing employee record: %s (%d)", m.getName(), m.getId()));
            mv.addObject("m", m);
        }

        return mv;
    }

    @RequestMapping(value = "/employee-delete", method = RequestMethod.POST)
    public String postDelete(@RequestParam("id") int a_id) {
        Employee m = sess.byId(Employee.class).load(a_id);

        Transaction t = sess.beginTransaction();
        log.trace(String.format("Deleting employee record: %s (%d)", m.getName(), m.getId()));
        sess.delete(m);
        t.commit();

        return "redirect:/employee";
    }

    @RequestMapping(value = "/employee-save", method = RequestMethod.POST)
    public String postSave(
        @ModelAttribute
        Employee m
    ) {
        Transaction t = sess.beginTransaction();

        if (m.getId()==-1) {
            m.setId(0);
            sess.save(m);
            log.trace(String.format("Saved new employee record: %s (%d)", m.getName(), m.getId()));
        } else {
            sess.merge(m);
            log.trace(String.format("Saved existing employee record: %s (%d)", m.getName(), m.getId()));
        }

        t.commit();
        return "redirect:/employee-list";
    }
}
