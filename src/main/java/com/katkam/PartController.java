package com.katkam;

import com.katkam.entity.Manufacturer;
import com.katkam.entity.Part;
import com.katkam.entity.Uom;
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
 * Created by Developer on 1/15/17.
 */
@Controller
public class PartController {
    Session sess = GrizzlyHelper.getSession();

    @RequestMapping(value = "/part-list", method = RequestMethod.GET)
    public ModelAndView getList() {
        ModelAndView mv = new ModelAndView("part_list");
        mv.addObject("list", getListContent());
        return mv;
    }

    private List<Part> getListContent() {
        List<Part> lstRet = new ArrayList<Part>();
//        lstRet.add(new Manufacturer(1, "Toyota"));
//        lstRet.add(new Manufacturer(2, "Honda"));
//        lstRet.add(new Manufacturer(3, "Jeep"));
//        lstRet = (List<Manufacturer>) sess.find(Manufacturer.class, null);
        lstRet = (List<Part>) sess.createCriteria(Part.class).list();
        return lstRet;
    }

    @RequestMapping(value = "/part", method = RequestMethod.GET)
    public String getIndex() { return "part_index"; }

    @RequestMapping(value = "/part-edit", method = RequestMethod.GET)
    public ModelAndView getEdit(
            @RequestParam(name = "id", defaultValue = "-1")
                    int a_id
    ) {
        ModelAndView mv = new ModelAndView("part_edit");

        List<Manufacturer> lstManufacturers = new ArrayList<Manufacturer>();
        lstManufacturers = (List<Manufacturer>) sess.createCriteria(Manufacturer.class).list();
        List<Uom> lstUom = new ArrayList<Uom>();
        lstUom = (List<Uom>) sess.createCriteria(Uom.class).list();

        if (a_id==-1) {
            //mv.addObject("m", new Manufacturer());
        } else {
//            Manufacturer m = sess.find(Manufacturer.class, new Manufacturer(a_id, null));
            Part m = sess.byId(Part.class).load(a_id);
            mv.addObject("m", m);
        }
//        mv.addObject("m", new Manufacturer(1, "Toyota"));

        mv.addObject("manufacturers", lstManufacturers);
        mv.addObject("uoms", lstUom);

        return mv;
    }

    @RequestMapping(value = "/part-delete", method = RequestMethod.POST)
    public String postDelete(@RequestParam("id") int a_id) {
        Part m = sess.byId(Part.class).load(a_id);

        Transaction t = sess.beginTransaction();
        sess.delete(m);
        t.commit();

        return "redirect:/part-list";
    }

    @RequestMapping(value = "/part-save", method = RequestMethod.POST)
    public String postSave(
            @ModelAttribute
                    Part a_mfg
    ) {
        //sess.merge(a_mfg);
        Transaction t = sess.beginTransaction();

        if (a_mfg.getId()==-1) {
            a_mfg.setId(0);
            sess.save(a_mfg);
        } else {
            sess.merge(a_mfg);
            //sess.update(a_mfg);
        }

        //sess.saveOrUpdate(a_mfg);
        //sess.flush();
        t.commit();

        return "redirect:/part";
//        return  String.format("ID: %d, Name: %s", a_mfg.getId(), a_mfg.getName());
    }
}
