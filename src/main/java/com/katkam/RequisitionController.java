package com.katkam;

import com.katkam.entity.RequisitionHeader;
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

        if (a_id == -1) {
            //mv.addObject("m", null);
        } else {
            RequisitionHeader m = sess.byId(RequisitionHeader.class).load(a_id);
            mv.addObject("m", m);
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


    @RequestMapping(value = "/requisition-save", method = RequestMethod.POST)
    public String postSave(
        @ModelAttribute
        RequisitionHeader a_m
    ) {
        Transaction t = sess.beginTransaction();

        if (a_m.getId()==-1) {
            a_m.setId(0);
            sess.save(a_m);
        } else {
            sess.merge(a_m);
        }

        t.commit();

        return "redirect:/requisition";
    }

    //TODO Add RequisitionLine functions
}
