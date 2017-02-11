package com.katkam.controller;

import com.katkam.GrizzlyHelper;
import com.katkam.entity.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
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
 * Created by Developer on 1/18/17.
 */
@Controller
public class StockController {
    Session sess = new GrizzlyHelper().getSession();
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/stock-list", method = RequestMethod.GET)
    public ModelAndView getList() {
        ModelAndView mv = new ModelAndView("stock_list");

        List<Stock> list = getListContent();
        log.trace(String.format("Stocks fetched: %d", list.size()));
        mv.addObject("list", list);

        return mv;
    }

    private List<Stock> getListContent() {
        List<Stock> lstRet;
        lstRet = (List<Stock>) sess.createCriteria(Stock.class).list(); //simple, but deprecated TODO: Change this
        return lstRet;
    }

    @RequestMapping(value = "/stock", method = RequestMethod.GET)
    public String getIndex() { return "stock_index"; }

    @RequestMapping(value = "/stock-edit", method = RequestMethod.GET)
    public ModelAndView getEdit(
        @RequestParam(name = "id", defaultValue = "-1")
        int a_id
    ) {
        ModelAndView mv = new ModelAndView("stock_edit");

        if (a_id==-1) {
            log.trace("Editing new stock record");
        } else {
            Stock m = sess.byId(Stock.class).load(a_id);
            log.trace(String.format("Editing customer record: %s (%d)", m.getPart().getName(), m.getId()));
            mv.addObject("m", m);
        }

        List<Part> parts = sess.createCriteria(Part.class).list();
        log.trace(String.format("Parts fetched: %d", parts.size()));
        mv.addObject("parts", parts);

        List<Store> stores = sess.createCriteria(Store.class).list();
        log.trace(String.format("Stores fetched: %d", stores.size()));
        mv.addObject("stores", stores);

        return mv;
    }

    @RequestMapping(value = "/stock-delete", method = RequestMethod.POST)
    public String postDelete(@RequestParam("id") int a_id) {
        Stock m = sess.byId(Stock.class).load(a_id);

        if (m.getQty()==0) { //Ensure stock is zero before deleting
            Transaction t = sess.beginTransaction();
            log.trace(String.format("Deleting pick ticket record: %s (%d)", m.getPart().getName(), m.getId()));
            sess.delete(m);
            t.commit();
        }

        return "redirect:/stock-list";
    }

    @RequestMapping(value = "/stock-save", method = RequestMethod.POST)
    public String postSave(
        @ModelAttribute Stock m,
        @RequestParam(name = "store_id") int store_id,
        @RequestParam(name = "part_id") int part_id
    ) {
        Transaction t = sess.beginTransaction();

        Xact xact = new Xact();

        m.setStore(sess.byId(Store.class).load(store_id));
        m.setPart(sess.byId(Part.class).load(part_id));

        xact.setStore(m.getStore());
        xact.setPart(m.getPart());
        xact.setDate(new java.util.Date());

        if (m.getId()==-1) {
            m.setId(0);
            xact.setQty(m.getQty());
            xact.setNarration("Stock input entry");
            xact.setTypeCode("INPUTSTOCK");
            sess.save(m);
            log.trace(String.format("Saved new stock record: %s (%d)", m.getPart().getName(), m.getId()));
        } else {
            xact.setQty(m.getQty() - sess.byId(Stock.class).load(m.getId()).getQty());
            xact.setNarration("Stock adjustment entry");
            xact.setTypeCode("STOCKADJ");
            sess.merge(m);
            log.trace(String.format("Saved existing stock record: %s (%d)", m.getPart().getName(), m.getId()));
        }

        sess.save(xact);

        t.commit();

        return "redirect:/stock-list";
    }

    @RequestMapping(value = "/stock-receipt-purchase-order-list")
    public ModelAndView stockReceiptPurchaseOrderList() {
        ModelAndView mv = new ModelAndView("stock_receipt_purchase_order_list");
        List<PurchaseOrderHeader> pos = sess.createCriteria(PurchaseOrderHeader.class).list();
        //TODO Do not include POs with all items already received
        mv.addObject("pos", pos);
        return mv;
    }

    @RequestMapping(value = "/stock-receipt-purchase-order-select")
    public ModelAndView stockReceiptPurchaseOrderSelect(
        @RequestParam("id") int id
    ) {
        ModelAndView mv = new ModelAndView("stock_receipt_purchase_order_select");

        List<PurchaseOrderLine> lines = sess.createQuery("from PurchaseOrderLine where header_id = :code").setParameter("code", id).list();
        log.trace(String.format("Stock lines fetched: %d", lines.size()));
        mv.addObject("lines", lines);

        return mv;
    }

    public String postStockReceiptAgainstPurchaseOrder(
        @RequestParam("id") int id,
        @RequestParam("qty_delta") double qty_delta
    ) {
        PurchaseOrderLine obj = sess.byId(PurchaseOrderLine.class).load(id);
        Xact xact = new Xact();

        Stock stock = (Stock) sess.createQuery("from Stock where store_id=:store and part_id=:part").setParameter("store", obj.getHeader().getStore().getId()).setParameter("part", obj.getPart().getId()).list().get(0);
        Transaction t = sess.beginTransaction();
        obj.setReceived_qty(obj.getReceived_qty() + qty_delta);
        xact.setQty(qty_delta);
        xact.setPart(obj.getPart());
        xact.setDate(new java.util.Date());
        xact.setNarration("Received against PO");
        xact.setTypeCode("RECVWPO");
        stock.setQty(stock.getQty() + qty_delta);

        sess.save(xact);
        sess.merge(obj);
        sess.merge(stock);
        log.trace(String.format("Saved stock record: %s (%d)", stock.getPart().getName(), stock.getId()));

        t.commit();


        return "redirect:/purchaseorder-list";
    }

    @RequestMapping(value = "/stock-issue-pickticket-list")
    public ModelAndView stockIssuePickticketList() {
        ModelAndView mv = new ModelAndView("stock_issue_pickticket_list");

        List<PickticketHeader> picktickets = sess.createCriteria(PickticketHeader.class).list();
        log.trace(String.format("Pick tickets fetched: %d", picktickets.size()));
        //TODO Do not include picktickets with all items already issued
        mv.addObject("picktickets", picktickets);

        return mv;
    }

    @RequestMapping(value = "/stock-issue-pickticket-select")
    public ModelAndView stockIssuePickticketSelect(
        @RequestParam("id") int id
    ) {
        ModelAndView mv = new ModelAndView("stock_issue_pickticket_select");
        List<PickticketLine> lines = sess.createQuery("from PickticketLine where header_id = :code").setParameter("code", id).list();
        log.trace(String.format("Pick ticket lines fetched: %d", lines.size()));
        mv.addObject("lines", lines);
        return mv;
    }

    @RequestMapping(value = "/stock_issue_by_pickticket")
    public String postStockIssueAgainstPickticket(
        @RequestParam("id")
        int id,
        @RequestParam("qty_delta")
        double qty_delta
    ) {
        PickticketLine obj = sess.byId(PickticketLine.class).load(id);
        Xact xact = new Xact();

//        List<Stock> tmpList = sess.createQuery("from Stock where store_id=:store and part_id=:part").setParameter("store", obj.getHeader().getStore().getId()).setParameter("part", obj.getPart().getId()).list();
//        obj.toString();
//        Query qry;
//        qry = sess.createQuery("from Stock where store_id=:store and part_id=:part");
//        PickticketHeader phdr = obj.getHeader();
//        phdr.toString();
//        Store str = phdr.getStore();
//        str.toString();
//        qry = qry.setParameter("store", str.getId());
//        Part prt = obj.getPart();
//        prt.toString();
//        qry = qry.setParameter("part", prt.getId());
//        List<Stock> tmpList = qry.list();
//        Stock stock = tmpList.get(0);

        Stock stock = (Stock) sess.createQuery("from Stock where store_id=:store and part_id=:part").setParameter("store", obj.getHeader().getStore().getId()).setParameter("part", obj.getPart().getId()).list().get(0);
        Transaction t = sess.beginTransaction();

        obj.setIssued_qty(obj.getIssued_qty() + qty_delta);
        xact.setQty(qty_delta);
        xact.setPart(obj.getPart());
        xact.setDate(new java.util.Date());
        xact.setNarration("Issued against pick ticket");
        xact.setTypeCode("ISSUEWPICTIC");
        stock.setQty(stock.getQty() - qty_delta);

        sess.save(xact);
        sess.merge(obj);
        sess.merge(stock);
        log.trace(String.format("Saved stock record: %s (%d)", stock.getPart().getName(), stock.getId()));

        t.commit();

        return "redirect:/pickticket-list";
    }

    @RequestMapping("/stock-receipt")
    public ModelAndView getStockReceiptOverview() { return new ModelAndView("stock_receipt"); }

    @RequestMapping("/stock-issue")
    public ModelAndView getStockIssueOverview() { return new ModelAndView("stock_issue"); }

    @RequestMapping("/stock-issue-direct")
    public ModelAndView getStockIssueDirect() {
        ModelAndView mv = new ModelAndView("stock_issue_direct");

        List<Stock> list = sess.createCriteria(Stock.class).list();
        log.trace(String.format("Stocks fetched: %d", list.size()));
        mv.addObject("list", list);

        return mv;
    }

    @RequestMapping("/stock-issue-direct-save")
    public String postStockIssueDirect(
        @RequestParam("id")
        int id,
        @RequestParam("qty_delta")
        double qty_delta
    ) {
        Stock st = sess.byId(Stock.class).load(id);
        Xact xact = new Xact();

        st.setQty(st.getQty() - qty_delta);
        xact.setQty(qty_delta);
        xact.setPart(st.getPart());
        xact.setDate(new java.util.Date());
        xact.setNarration("Direct issue");
        xact.setTypeCode("ISSUEDIRECT");

        Transaction t = sess.beginTransaction();
        sess.merge(st);
        sess.save(xact);
        t.commit();
        log.trace(String.format("Saved stock record: %s (%d)", st.getPart().getName(), st.getId()));

        return "redirect:/stock-issue-direct";
    }
}
