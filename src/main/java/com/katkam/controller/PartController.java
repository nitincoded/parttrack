package com.katkam.controller;

import com.katkam.GrizzlyHelper;
import com.katkam.entity.Manufacturer;
import com.katkam.entity.Part;
import com.katkam.entity.Uom;
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
//import java.util.logging.Logger;

/**
 * Created by Developer on 1/15/17.
 */
@Controller
public class PartController {
    Session sess = GrizzlyHelper.getSession();
    private final Logger log = LoggerFactory.getLogger(this.getClass());

//    private final static Logger logger = Logger.getLogger(PartController.class.getName());

    @RequestMapping(value = "/part-list", method = RequestMethod.GET)
    public ModelAndView getList() {
        ModelAndView mv = new ModelAndView("part_list");

        List<Part> list = getListContent();
        log.trace(String.format("Parts fetched: %d", list.size()));
        mv.addObject("list", list);

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
            log.trace("Editing new part record");
        } else {
//            Manufacturer m = sess.find(Manufacturer.class, new Manufacturer(a_id, null));
            Part m = sess.byId(Part.class).load(a_id);
            log.trace(String.format("Editing part record: %s (%d)", m.getName(), m.getId()));
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
        log.trace(String.format("Deleting part record: %s (%d)", m.getName(), m.getId()));
        sess.delete(m);
        t.commit();

        return "redirect:/part-list";
    }

    @RequestMapping(value = "/part-save", method = RequestMethod.POST)
    public String postSave(
        @ModelAttribute Part m,
        @RequestParam(name = "uom_id") int uom_id,
        @RequestParam(name = "manufacturer_id") Integer manufacturer_id
    ) {
//        logger.setLevel(Level.ALL);
//
//        try {
//            logger.info(
//                    String.format(
//                            "ID: %d, Name: %s, MfgId: %d, UomId: %d",
//                            a_mfg.getId(),
//                            a_mfg.getName(),
//                            a_mfg.getManufacturer() == null ? "" : a_mfg.getManufacturer().getId(),
//                            a_mfg.getUom() == null ? a_mfg.getUom().getId() : ""
//                    )
//            );
//        }
//        catch (Exception ex) {}

        //sess.merge(a_mfg);
        Transaction t = sess.beginTransaction();

        m.setUom(sess.byId(Uom.class).load(uom_id));
        if (manufacturer_id != null) {
            m.setManufacturer(sess.byId(Manufacturer.class).load(manufacturer_id));
        }

        if (m.getId()==-1) {
            m.setId(0);
            sess.save(m);
            log.trace(String.format("Saved new part record: %s (%d)", m.getName(), m.getId()));
        } else {
            sess.merge(m);
            log.trace(String.format("Saved existing part record: %s (%d)", m.getName(), m.getId()));
            //sess.update(a_mfg);
        }

        //sess.saveOrUpdate(a_mfg);
        //sess.flush();
        t.commit();

        return "redirect:/part-list";
//        return  String.format("ID: %d, Name: %s", a_mfg.getId(), a_mfg.getName());
    }
}
