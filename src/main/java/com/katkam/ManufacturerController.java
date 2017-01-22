package com.katkam;

import com.katkam.entity.Manufacturer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Developer on 1/14/17.
 */
@Controller
public class ManufacturerController {
    Session sess = GrizzlyHelper.getSession();

    @RequestMapping(value = "/manufacturer-list", method = RequestMethod.GET)
    public ModelAndView getList() {
        ModelAndView mv = new ModelAndView("manufacturer_list");
        mv.addObject("list", getListContent());
        return mv;
    }

    private List<Manufacturer> getListContent() {
        List<Manufacturer> lstRet = new ArrayList<Manufacturer>();
        lstRet = (List<Manufacturer>) sess.createCriteria(Manufacturer.class).list();
        return lstRet;
    }

    @RequestMapping(value = "/manufacturer", method = RequestMethod.GET)
    public String getIndex() { return "manufacturer_index"; }

    @RequestMapping(value = "/manufacturer-edit", method = RequestMethod.GET)
    public ModelAndView getEdit(
        @RequestParam(name = "id", defaultValue = "-1")
        int a_id
    ) {
        ModelAndView mv = new ModelAndView("manufacturer_edit");
        if (a_id==-1) {
            //mv.addObject("m", new Manufacturer());
        } else {
//            Manufacturer m = sess.find(Manufacturer.class, new Manufacturer(a_id, null));
            Manufacturer m = sess.byId(Manufacturer.class).load(a_id);
            mv.addObject("m", m);
        }
//        mv.addObject("m", new Manufacturer(1, "Toyota"));
        return mv;
    }

    @RequestMapping(value = "/manufacturer-delete", method = RequestMethod.POST)
    public String postDelete(@RequestParam("id") int a_id) {
        Manufacturer m = sess.byId(Manufacturer.class).load(a_id);

        Transaction t = sess.beginTransaction();
        sess.delete(m);
        t.commit();

        return "redirect:/manufacturer-list";
    }

    @RequestMapping(value = "/manufacturer-save", method = RequestMethod.POST)
    public String postSave(
        @ModelAttribute
        Manufacturer a_m
    ) {
        //sess.merge(a_mfg);
        Transaction t = sess.beginTransaction();

        if (a_m.getId()==-1) {
            a_m.setId(0);
            sess.save(a_m);
        } else {
            sess.merge(a_m);
            //sess.update(a_mfg);
        }

        //sess.saveOrUpdate(a_mfg);
        //sess.flush();
        t.commit();

        return "redirect:/manufacturer";
//        return  String.format("ID: %d, Name: %s", a_mfg.getId(), a_mfg.getName());
    }
}
