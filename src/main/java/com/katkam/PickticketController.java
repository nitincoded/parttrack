package com.katkam;

import com.katkam.entity.PickticketHeader;
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
        if (a_id==-1) {
            //mv.addObject("m", null);
        } else {
            PickticketHeader m = sess.byId(PickticketHeader.class).load(a_id);
            mv.addObject("m", m);
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

    @RequestMapping(value = "/pickticket-save", method = RequestMethod.POST)
    public String postSave(
            @ModelAttribute
            PickticketHeader a_m
    ) {
        Transaction t = sess.beginTransaction();

        if (a_m.getId()==-1) {
            a_m.setId(0);
            sess.save(a_m);
        } else {
            sess.merge(a_m);
        }

        t.commit();

        return "redirect:/pickticket";
    }

    //TODO Add PickticketLine functions
}
