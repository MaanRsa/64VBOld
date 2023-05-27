package com.maan.search;

/**
 * @author Administrator
 *
 */
public class InvalidVB {

	private int sno;

	private String  r_receiptslno;
	private String  r_bankno;
	private String  r_chequeno;
	private String  r_chequedate;
	private String  r_receiptno;
	private String  r_receiptdate;
	private String  r_amount;
	private String  r_banknameandloc;
	private String  r_reason;
	private String  r_receiptentryid;
	private String  r_bankcode;
	private String  r_receiptagname;
	private String  r_receiptagcode;
	private String  r_productcode;
	private String  r_receiptbranchcode;
	private String  r_particulars;
	private String  r_transsource;
	private String  r_currentbalance;
	private String  r_delayindays;
	private String  r_exchangecurrrate;
	private String  r_wtfoff1;
	private String  r_wtfoff2;
	private String  r_wtfoff3;
	private String  r_duedate;
	private String  r_paymenttype;
	private String  r_creditcardno;
	private String  r_creditcardtype;
	private String  r_creditcardbank;
	private String  r_creditcardexpiry;
	private String  r_transactionreference;
	private String  r_channel;
	private String  r_subchannel;
	private String  r_batch_id;
	private String  r_bankreferencenumber;
	private String  r_status;
	private String  r_rdate;
	private String  r_remarks;
	private String  r_transactionid;
	private String  r_transactionbankid;
	private String  r_chequestatus;
	private String  r_batchid;
	private String  r_active;
	private String  r_validatestatus;
	
	private String c_clientcode;
	private String c_depositdate;
	private String c_product;
	private String c_creditdebitdate;
	private String c_location;
	private String c_chequeno;
	private String c_chequeamt;
	private String c_typecrdr;
	private String c_narration;
	private String c_cbpno;
	private String c_depslipno;
	private String c_customerref;
	private String c_depositamt;
	private String c_dwebankcode;
	private String c_checkdata;
	private String c_covernoteno;
	private String c_bankname;
	private String c_pickpointname;
	private String c_pkuppointcode;
	private String c_remarks;
	private String c_bank_no;
	private String c_receiptslno;
	private String c_transactionid;
	private String c_actualchequeno;
	private String c_actualchequeamt;
	private String c_status;
	private String c_dishonourtype;
	private String c_cashtransactionid;
	private String c_batchid;
	private String c_active;
	private String c_validatestatus;
    private String r_receiptPayment;
      
	private String s_customername;
	private String s_enttype;
	private String s_crdr;
	private String s_entamount;
	private String s_creditdebitdate;
	private String s_product;
	private String s_pickuploc;
	private String s_pickuppoint;
	private String s_pickupdt;
	private String s_depositno;
	private String s_depdate;
	private String s_depamount;
	private String s_payorderno;
	private String s_chequeno;
	private String s_chequeamt;
	private String s_draweebank;
	private String s_drawnon;
	private String s_chequedate;
	private String s_drawer;
	private String s_reason;
	private String s_enrichmentno;
	private String s_enrichmentremarks;
	private String s_bank_no;
	private String s_receiptslno;
	private String s_transactionid;
	private String s_actualchequeno;
	private String s_actualchequeamt;
	private String s_status;
	private String s_dishonourtype;
	private String s_cashtransactionid;
	private String s_batchid;
	private String s_active;
	private String s_validatestatus;

	
	private String  a_type; 
	private String  a_cuscode; 
	private String  a_locationname; 
	private String  a_depositdate; 
	private String  a_crdate; 
	private String  a_rtndate; 
	private String  a_slipno; 
	private String  a_nofins; 
	private String  a_slipamount; 
	private String  a_instno; 
	private String  a_instdate; 
	private String  a_instrumentamount; 
	private String  a_iadditionalinfo1; 
	private String  a_iadditionalinfo2; 
	private String  a_rtnamt; 
	private String  a_returnreason; 
	private String  a_drnbk; 
	private String  a_drwnbrnchname; 
	private String  a_validatestatus;
	private String  a_bank_no;
	private String  a_receiptslno;
	private String  a_transactionid;
	private String  a_actualchequeno;
	private String  a_actualchequeamt;
	private String  a_status;
	private String  a_dishonourtype;
	private String  a_cashtransactionid;
	private String  a_batchid;
	private String  a_active;
	

	private String h_month;
	private String h_rowtype;
	private String h_entryid;
	private String h_typeofen;
	private String h_drcr;
	private String h_entryamt;
	private String h_valdt;
	private String h_postdt;
	private String h_prodcode;
	private String h_pkuploc;
	private String h_pkuppt;
	private String h_pkupdt;
	private String h_depositslipno;
	private String h_depositdate;
	private String h_deptamt;
	private String h_noofinst;
	private String h_deptrmk;
	private String h_instrumentno;
	private String h_draweebk;
	private String h_clloc;
	private String h_instrumentamount;
	private String h_instdt;
	private String h_drawernam;
	private String h_dealcode;
	private String h_dealname;
	private String h_drawer;
	private String h_policyno;
	private String h_returnrsn;
	private String h_bankno;
	private String h_receiptslno;
	private String h_transactionid;
	private String h_actualchequeno;
	private String h_actualchequeamt;
	private String h_status;
	private String h_dishonourtype;
	private String h_cashtransactionid;
	private String h_validatestatus;
	private String h_active;
	private String h_batchid;
    private String h_dateOfEntry;
	
    
	
	public String getH_dateOfEntry() {
		return h_dateOfEntry;
	}
	public void setH_dateOfEntry(String hDateOfEntry) {
		h_dateOfEntry = hDateOfEntry;
	}
	public String getR_receiptPayment() {
		return r_receiptPayment;
	}
	public void setR_receiptPayment(String rReceiptPayment) {
		r_receiptPayment = rReceiptPayment;
	}
	public String getS_active() {
		return s_active;
	}
	public void setS_active(String s_active) {
		this.s_active = s_active;
	}
	public String getS_actualchequeamt() {
		return s_actualchequeamt;
	}
	public void setS_actualchequeamt(String s_actualchequeamt) {
		this.s_actualchequeamt = s_actualchequeamt;
	}
	public String getS_actualchequeno() {
		return s_actualchequeno;
	}
	public void setS_actualchequeno(String s_actualchequeno) {
		this.s_actualchequeno = s_actualchequeno;
	}
	public String getS_bank_no() {
		return s_bank_no;
	}
	public void setS_bank_no(String s_bank_no) {
		this.s_bank_no = s_bank_no;
	}
	public String getS_batchid() {
		return s_batchid;
	}
	public void setS_batchid(String s_batchid) {
		this.s_batchid = s_batchid;
	}
	public String getS_cashtransactionid() {
		return s_cashtransactionid;
	}
	public void setS_cashtransactionid(String s_cashtransactionid) {
		this.s_cashtransactionid = s_cashtransactionid;
	}
	public String getS_chequeamt() {
		return s_chequeamt;
	}
	public void setS_chequeamt(String s_chequeamt) {
		this.s_chequeamt = s_chequeamt;
	}
	public String getS_chequedate() {
		return s_chequedate;
	}
	public void setS_chequedate(String s_chequedate) {
		this.s_chequedate = s_chequedate;
	}
	public String getS_chequeno() {
		return s_chequeno;
	}
	public void setS_chequeno(String s_chequeno) {
		this.s_chequeno = s_chequeno;
	}
	public String getS_crdr() {
		return s_crdr;
	}
	public void setS_crdr(String s_crdr) {
		this.s_crdr = s_crdr;
	}
	public String getS_creditdebitdate() {
		return s_creditdebitdate;
	}
	public void setS_creditdebitdate(String s_creditdebitdate) {
		this.s_creditdebitdate = s_creditdebitdate;
	}
	public String getS_customername() {
		return s_customername;
	}
	public void setS_customername(String s_customername) {
		this.s_customername = s_customername;
	}
	public String getS_depamount() {
		return s_depamount;
	}
	public void setS_depamount(String s_depamount) {
		this.s_depamount = s_depamount;
	}
	public String getS_depdate() {
		return s_depdate;
	}
	public void setS_depdate(String s_depdate) {
		this.s_depdate = s_depdate;
	}
	public String getS_depositno() {
		return s_depositno;
	}
	public void setS_depositno(String s_depositno) {
		this.s_depositno = s_depositno;
	}
	public String getS_dishonourtype() {
		return s_dishonourtype;
	}
	public void setS_dishonourtype(String s_dishonourtype) {
		this.s_dishonourtype = s_dishonourtype;
	}
	public String getS_draweebank() {
		return s_draweebank;
	}
	public void setS_draweebank(String s_draweebank) {
		this.s_draweebank = s_draweebank;
	}
	public String getS_drawer() {
		return s_drawer;
	}
	public void setS_drawer(String s_drawer) {
		this.s_drawer = s_drawer;
	}
	public String getS_drawnon() {
		return s_drawnon;
	}
	public void setS_drawnon(String s_drawnon) {
		this.s_drawnon = s_drawnon;
	}
	public String getS_enrichmentno() {
		return s_enrichmentno;
	}
	public void setS_enrichmentno(String s_enrichmentno) {
		this.s_enrichmentno = s_enrichmentno;
	}
	public String getS_enrichmentremarks() {
		return s_enrichmentremarks;
	}
	public void setS_enrichmentremarks(String s_enrichmentremarks) {
		this.s_enrichmentremarks = s_enrichmentremarks;
	}
	public String getS_entamount() {
		return s_entamount;
	}
	public void setS_entamount(String s_entamount) {
		this.s_entamount = s_entamount;
	}
	public String getS_enttype() {
		return s_enttype;
	}
	public void setS_enttype(String s_enttype) {
		this.s_enttype = s_enttype;
	}
	public String getS_payorderno() {
		return s_payorderno;
	}
	public void setS_payorderno(String s_payorderno) {
		this.s_payorderno = s_payorderno;
	}
	public String getS_pickupdt() {
		return s_pickupdt;
	}
	public void setS_pickupdt(String s_pickupdt) {
		this.s_pickupdt = s_pickupdt;
	}
	public String getS_pickuploc() {
		return s_pickuploc;
	}
	public void setS_pickuploc(String s_pickuploc) {
		this.s_pickuploc = s_pickuploc;
	}
	public String getS_pickuppoint() {
		return s_pickuppoint;
	}
	public void setS_pickuppoint(String s_pickuppoint) {
		this.s_pickuppoint = s_pickuppoint;
	}
	public String getS_product() {
		return s_product;
	}
	public void setS_product(String s_product) {
		this.s_product = s_product;
	}
	public String getS_reason() {
		return s_reason;
	}
	public void setS_reason(String s_reason) {
		this.s_reason = s_reason;
	}
	public String getS_receiptslno() {
		return s_receiptslno;
	}
	public void setS_receiptslno(String s_receiptslno) {
		this.s_receiptslno = s_receiptslno;
	}
	public String getS_status() {
		return s_status;
	}
	public void setS_status(String s_status) {
		this.s_status = s_status;
	}
	public String getS_transactionid() {
		return s_transactionid;
	}
	public void setS_transactionid(String s_transactionid) {
		this.s_transactionid = s_transactionid;
	}
	public String getS_validatestatus() {
		return s_validatestatus;
	}
	public void setS_validatestatus(String s_validatestatus) {
		this.s_validatestatus = s_validatestatus;
	}
	public String getH_active() {
		return h_active;
	}
	public void setH_active(String h_active) {
		this.h_active = h_active;
	}
	public String getH_actualchequeamt() {
		return h_actualchequeamt;
	}
	public void setH_actualchequeamt(String h_actualchequeamt) {
		this.h_actualchequeamt = h_actualchequeamt;
	}
	public String getH_actualchequeno() {
		return h_actualchequeno;
	}
	public void setH_actualchequeno(String h_actualchequeno) {
		this.h_actualchequeno = h_actualchequeno;
	}
	public String getH_bankno() {
		return h_bankno;
	}
	public void setH_bankno(String h_bankno) {
		this.h_bankno = h_bankno;
	}
	public String getH_batchid() {
		return h_batchid;
	}
	public void setH_batchid(String h_batchid) {
		this.h_batchid = h_batchid;
	}
	public String getH_cashtransactionid() {
		return h_cashtransactionid;
	}
	public void setH_cashtransactionid(String h_cashtransactionid) {
		this.h_cashtransactionid = h_cashtransactionid;
	}
	public String getH_clloc() {
		return h_clloc;
	}
	public void setH_clloc(String h_clloc) {
		this.h_clloc = h_clloc;
	}
	public String getH_dealcode() {
		return h_dealcode;
	}
	public void setH_dealcode(String h_dealcode) {
		this.h_dealcode = h_dealcode;
	}
	public String getH_dealname() {
		return h_dealname;
	}
	public void setH_dealname(String h_dealname) {
		this.h_dealname = h_dealname;
	}
	public String getH_depositdate() {
		return h_depositdate;
	}
	public void setH_depositdate(String h_depositdate) {
		this.h_depositdate = h_depositdate;
	}
	public String getH_depositslipno() {
		return h_depositslipno;
	}
	public void setH_depositslipno(String h_depositslipno) {
		this.h_depositslipno = h_depositslipno;
	}
	public String getH_deptamt() {
		return h_deptamt;
	}
	public void setH_deptamt(String h_deptamt) {
		this.h_deptamt = h_deptamt;
	}
	public String getH_deptrmk() {
		return h_deptrmk;
	}
	public void setH_deptrmk(String h_deptrmk) {
		this.h_deptrmk = h_deptrmk;
	}
	public String getH_dishonourtype() {
		return h_dishonourtype;
	}
	public void setH_dishonourtype(String h_dishonourtype) {
		this.h_dishonourtype = h_dishonourtype;
	}
	public String getH_draweebk() {
		return h_draweebk;
	}
	public void setH_draweebk(String h_draweebk) {
		this.h_draweebk = h_draweebk;
	}
	public String getH_drawer() {
		return h_drawer;
	}
	public void setH_drawer(String h_drawer) {
		this.h_drawer = h_drawer;
	}
	public String getH_drawernam() {
		return h_drawernam;
	}
	public void setH_drawernam(String h_drawernam) {
		this.h_drawernam = h_drawernam;
	}
	public String getH_drcr() {
		return h_drcr;
	}
	public void setH_drcr(String h_drcr) {
		this.h_drcr = h_drcr;
	}
	public String getH_entryamt() {
		return h_entryamt;
	}
	public void setH_entryamt(String h_entryamt) {
		this.h_entryamt = h_entryamt;
	}
	public String getH_entryid() {
		return h_entryid;
	}
	public void setH_entryid(String h_entryid) {
		this.h_entryid = h_entryid;
	}
	public String getH_instdt() {
		return h_instdt;
	}
	public void setH_instdt(String h_instdt) {
		this.h_instdt = h_instdt;
	}
	public String getH_instrumentamount() {
		return h_instrumentamount;
	}
	public void setH_instrumentamount(String h_instrumentamount) {
		this.h_instrumentamount = h_instrumentamount;
	}
	public String getH_instrumentno() {
		return h_instrumentno;
	}
	public void setH_instrumentno(String h_instrumentno) {
		this.h_instrumentno = h_instrumentno;
	}
	public String getH_month() {
		return h_month;
	}
	public void setH_month(String h_month) {
		this.h_month = h_month;
	}
	public String getH_noofinst() {
		return h_noofinst;
	}
	public void setH_noofinst(String h_noofinst) {
		this.h_noofinst = h_noofinst;
	}
	public String getH_pkupdt() {
		return h_pkupdt;
	}
	public void setH_pkupdt(String h_pkupdt) {
		this.h_pkupdt = h_pkupdt;
	}
	public String getH_pkuploc() {
		return h_pkuploc;
	}
	public void setH_pkuploc(String h_pkuploc) {
		this.h_pkuploc = h_pkuploc;
	}
	public String getH_pkuppt() {
		return h_pkuppt;
	}
	public void setH_pkuppt(String h_pkuppt) {
		this.h_pkuppt = h_pkuppt;
	}
	public String getH_policyno() {
		return h_policyno;
	}
	public void setH_policyno(String h_policyno) {
		this.h_policyno = h_policyno;
	}
	public String getH_postdt() {
		return h_postdt;
	}
	public void setH_postdt(String h_postdt) {
		this.h_postdt = h_postdt;
	}
	public String getH_prodcode() {
		return h_prodcode;
	}
	public void setH_prodcode(String h_prodcode) {
		this.h_prodcode = h_prodcode;
	}
	public String getH_receiptslno() {
		return h_receiptslno;
	}
	public void setH_receiptslno(String h_receiptslno) {
		this.h_receiptslno = h_receiptslno;
	}
	public String getH_returnrsn() {
		return h_returnrsn;
	}
	public void setH_returnrsn(String h_returnrsn) {
		this.h_returnrsn = h_returnrsn;
	}
	public String getH_rowtype() {
		return h_rowtype;
	}
	public void setH_rowtype(String h_rowtype) {
		this.h_rowtype = h_rowtype;
	}
	public String getH_status() {
		return h_status;
	}
	public void setH_status(String h_status) {
		this.h_status = h_status;
	}
	public String getH_transactionid() {
		return h_transactionid;
	}
	public void setH_transactionid(String h_transactionid) {
		this.h_transactionid = h_transactionid;
	}
	public String getH_typeofen() {
		return h_typeofen;
	}
	public void setH_typeofen(String h_typeofen) {
		this.h_typeofen = h_typeofen;
	}
	public String getH_valdt() {
		return h_valdt;
	}
	public void setH_valdt(String h_valdt) {
		this.h_valdt = h_valdt;
	}
	public String getH_validatestatus() {
		return h_validatestatus;
	}
	public void setH_validatestatus(String h_validatestatus) {
		this.h_validatestatus = h_validatestatus;
	}
	public String getC_active() {
		return c_active;
	}
	public void setC_active(String c_active) {
		this.c_active = c_active;
	}
	public String getC_actualchequeamt() {
		return c_actualchequeamt;
	}
	public void setC_actualchequeamt(String c_actualchequeamt) {
		this.c_actualchequeamt = c_actualchequeamt;
	}
	public String getC_actualchequeno() {
		return c_actualchequeno;
	}
	public void setC_actualchequeno(String c_actualchequeno) {
		this.c_actualchequeno = c_actualchequeno;
	}
	public String getC_bank_no() {
		return c_bank_no;
	}
	public void setC_bank_no(String c_bank_no) {
		this.c_bank_no = c_bank_no;
	}
	public String getC_bankname() {
		return c_bankname;
	}
	public void setC_bankname(String c_bankname) {
		this.c_bankname = c_bankname;
	}
	public String getC_batchid() {
		return c_batchid;
	}
	public void setC_batchid(String c_batchid) {
		this.c_batchid = c_batchid;
	}
	public String getC_cashtransactionid() {
		return c_cashtransactionid;
	}
	public void setC_cashtransactionid(String c_cashtransactionid) {
		this.c_cashtransactionid = c_cashtransactionid;
	}
	public String getC_cbpno() {
		return c_cbpno;
	}
	public void setC_cbpno(String c_cbpno) {
		this.c_cbpno = c_cbpno;
	}
	public String getC_checkdata() {
		return c_checkdata;
	}
	public void setC_checkdata(String c_checkdata) {
		this.c_checkdata = c_checkdata;
	}
	public String getC_chequeamt() {
		return c_chequeamt;
	}
	public void setC_chequeamt(String c_chequeamt) {
		this.c_chequeamt = c_chequeamt;
	}
	public String getC_chequeno() {
		return c_chequeno;
	}
	public void setC_chequeno(String c_chequeno) {
		this.c_chequeno = c_chequeno;
	}
	public String getC_clientcode() {
		return c_clientcode;
	}
	public void setC_clientcode(String c_clientcode) {
		this.c_clientcode = c_clientcode;
	}
	public String getC_covernoteno() {
		return c_covernoteno;
	}
	public void setC_covernoteno(String c_covernoteno) {
		this.c_covernoteno = c_covernoteno;
	}
	public String getC_creditdebitdate() {
		return c_creditdebitdate;
	}
	public void setC_creditdebitdate(String c_creditdebitdate) {
		this.c_creditdebitdate = c_creditdebitdate;
	}
	public String getC_customerref() {
		return c_customerref;
	}
	public void setC_customerref(String c_customerref) {
		this.c_customerref = c_customerref;
	}
	public String getC_depositamt() {
		return c_depositamt;
	}
	public void setC_depositamt(String c_depositamt) {
		this.c_depositamt = c_depositamt;
	}
	public String getC_depositdate() {
		return c_depositdate;
	}
	public void setC_depositdate(String c_depositdate) {
		this.c_depositdate = c_depositdate;
	}
	public String getC_depslipno() {
		return c_depslipno;
	}
	public void setC_depslipno(String c_depslipno) {
		this.c_depslipno = c_depslipno;
	}
	public String getC_dishonourtype() {
		return c_dishonourtype;
	}
	public void setC_dishonourtype(String c_dishonourtype) {
		this.c_dishonourtype = c_dishonourtype;
	}
	public String getC_dwebankcode() {
		return c_dwebankcode;
	}
	public void setC_dwebankcode(String c_dwebankcode) {
		this.c_dwebankcode = c_dwebankcode;
	}
	public String getC_location() {
		return c_location;
	}
	public void setC_location(String c_location) {
		this.c_location = c_location;
	}
	public String getC_narration() {
		return c_narration;
	}
	public void setC_narration(String c_narration) {
		this.c_narration = c_narration;
	}
	public String getC_pickpointname() {
		return c_pickpointname;
	}
	public void setC_pickpointname(String c_pickpointname) {
		this.c_pickpointname = c_pickpointname;
	}
	public String getC_pkuppointcode() {
		return c_pkuppointcode;
	}
	public void setC_pkuppointcode(String c_pkuppointcode) {
		this.c_pkuppointcode = c_pkuppointcode;
	}
	public String getC_product() {
		return c_product;
	}
	public void setC_product(String c_product) {
		this.c_product = c_product;
	}
	public String getC_receiptslno() {
		return c_receiptslno;
	}
	public void setC_receiptslno(String c_receiptslno) {
		this.c_receiptslno = c_receiptslno;
	}
	public String getC_remarks() {
		return c_remarks;
	}
	public void setC_remarks(String c_remarks) {
		this.c_remarks = c_remarks;
	}
	public String getC_status() {
		return c_status;
	}
	public void setC_status(String c_status) {
		this.c_status = c_status;
	}
	public String getC_transactionid() {
		return c_transactionid;
	}
	public void setC_transactionid(String c_transactionid) {
		this.c_transactionid = c_transactionid;
	}
	public String getC_typecrdr() {
		return c_typecrdr;
	}
	public void setC_typecrdr(String c_typecrdr) {
		this.c_typecrdr = c_typecrdr;
	}
	public String getC_validatestatus() {
		return c_validatestatus;
	}
	public void setC_validatestatus(String c_validatestatus) {
		this.c_validatestatus = c_validatestatus;
	}
	public String getR_active() {
		return r_active;
	}
	public void setR_active(String r_active) {
		this.r_active = r_active;
	}
	public String getR_amount() {
		return r_amount;
	}
	public void setR_amount(String r_amount) {
		this.r_amount = r_amount;
	}
	public String getR_bankcode() {
		return r_bankcode;
	}
	public void setR_bankcode(String r_bankcode) {
		this.r_bankcode = r_bankcode;
	}
	public String getR_banknameandloc() {
		return r_banknameandloc;
	}
	public void setR_banknameandloc(String r_banknameandloc) {
		this.r_banknameandloc = r_banknameandloc;
	}
	public String getR_bankno() {
		return r_bankno;
	}
	public void setR_bankno(String r_bankno) {
		this.r_bankno = r_bankno;
	}
	public String getR_bankreferencenumber() {
		return r_bankreferencenumber;
	}
	public void setR_bankreferencenumber(String r_bankreferencenumber) {
		this.r_bankreferencenumber = r_bankreferencenumber;
	}
	public String getR_batch_id() {
		return r_batch_id;
	}
	public void setR_batch_id(String r_batch_id) {
		this.r_batch_id = r_batch_id;
	}
	public String getR_batchid() {
		return r_batchid;
	}
	public void setR_batchid(String r_batchid) {
		this.r_batchid = r_batchid;
	}
	public String getR_channel() {
		return r_channel;
	}
	public void setR_channel(String r_channel) {
		this.r_channel = r_channel;
	}
	public String getR_chequedate() {
		return r_chequedate;
	}
	public void setR_chequedate(String r_chequedate) {
		this.r_chequedate = r_chequedate;
	}
	public String getR_chequeno() {
		return r_chequeno;
	}
	public void setR_chequeno(String r_chequeno) {
		this.r_chequeno = r_chequeno;
	}
	public String getR_chequestatus() {
		return r_chequestatus;
	}
	public void setR_chequestatus(String r_chequestatus) {
		this.r_chequestatus = r_chequestatus;
	}
	public String getR_creditcardbank() {
		return r_creditcardbank;
	}
	public void setR_creditcardbank(String r_creditcardbank) {
		this.r_creditcardbank = r_creditcardbank;
	}
	public String getR_creditcardexpiry() {
		return r_creditcardexpiry;
	}
	public void setR_creditcardexpiry(String r_creditcardexpiry) {
		this.r_creditcardexpiry = r_creditcardexpiry;
	}
	public String getR_creditcardno() {
		return r_creditcardno;
	}
	public void setR_creditcardno(String r_creditcardno) {
		this.r_creditcardno = r_creditcardno;
	}
	public String getR_creditcardtype() {
		return r_creditcardtype;
	}
	public void setR_creditcardtype(String r_creditcardtype) {
		this.r_creditcardtype = r_creditcardtype;
	}
	public String getR_currentbalance() {
		return r_currentbalance;
	}
	public void setR_currentbalance(String r_currentbalance) {
		this.r_currentbalance = r_currentbalance;
	}
	public String getR_delayindays() {
		return r_delayindays;
	}
	public void setR_delayindays(String r_delayindays) {
		this.r_delayindays = r_delayindays;
	}
	public String getR_duedate() {
		return r_duedate;
	}
	public void setR_duedate(String r_duedate) {
		this.r_duedate = r_duedate;
	}
	public String getR_exchangecurrrate() {
		return r_exchangecurrrate;
	}
	public void setR_exchangecurrrate(String r_exchangecurrrate) {
		this.r_exchangecurrrate = r_exchangecurrrate;
	}
	public String getR_particulars() {
		return r_particulars;
	}
	public void setR_particulars(String r_particulars) {
		this.r_particulars = r_particulars;
	}
	public String getR_paymenttype() {
		return r_paymenttype;
	}
	public void setR_paymenttype(String r_paymenttype) {
		this.r_paymenttype = r_paymenttype;
	}
	public String getR_productcode() {
		return r_productcode;
	}
	public void setR_productcode(String r_productcode) {
		this.r_productcode = r_productcode;
	}
	public String getR_rdate() {
		return r_rdate;
	}
	public void setR_rdate(String r_rdate) {
		this.r_rdate = r_rdate;
	}
	public String getR_reason() {
		return r_reason;
	}
	public void setR_reason(String r_reason) {
		this.r_reason = r_reason;
	}
	public String getR_receiptagcode() {
		return r_receiptagcode;
	}
	public void setR_receiptagcode(String r_receiptagcode) {
		this.r_receiptagcode = r_receiptagcode;
	}
	public String getR_receiptagname() {
		return r_receiptagname;
	}
	public void setR_receiptagname(String r_receiptagname) {
		this.r_receiptagname = r_receiptagname;
	}
	public String getR_receiptbranchcode() {
		return r_receiptbranchcode;
	}
	public void setR_receiptbranchcode(String r_receiptbranchcode) {
		this.r_receiptbranchcode = r_receiptbranchcode;
	}
	public String getR_receiptdate() {
		return r_receiptdate;
	}
	public void setR_receiptdate(String r_receiptdate) {
		this.r_receiptdate = r_receiptdate;
	}
	public String getR_receiptentryid() {
		return r_receiptentryid;
	}
	public void setR_receiptentryid(String r_receiptentryid) {
		this.r_receiptentryid = r_receiptentryid;
	}
	public String getR_receiptno() {
		return r_receiptno;
	}
	public void setR_receiptno(String r_receiptno) {
		this.r_receiptno = r_receiptno;
	}
	public String getR_receiptslno() {
		return r_receiptslno;
	}
	public void setR_receiptslno(String r_receiptslno) {
		this.r_receiptslno = r_receiptslno;
	}
	public String getR_remarks() {
		return r_remarks;
	}
	public void setR_remarks(String r_remarks) {
		this.r_remarks = r_remarks;
	}
	public String getR_status() {
		return r_status;
	}
	public void setR_status(String r_status) {
		this.r_status = r_status;
	}
	public String getR_subchannel() {
		return r_subchannel;
	}
	public void setR_subchannel(String r_subchannel) {
		this.r_subchannel = r_subchannel;
	}
	public String getR_transactionbankid() {
		return r_transactionbankid;
	}
	public void setR_transactionbankid(String r_transactionbankid) {
		this.r_transactionbankid = r_transactionbankid;
	}
	public String getR_transactionid() {
		return r_transactionid;
	}
	public void setR_transactionid(String r_transactionid) {
		this.r_transactionid = r_transactionid;
	}
	public String getR_transactionreference() {
		return r_transactionreference;
	}
	public void setR_transactionreference(String r_transactionreference) {
		this.r_transactionreference = r_transactionreference;
	}
	public String getR_transsource() {
		return r_transsource;
	}
	public void setR_transsource(String r_transsource) {
		this.r_transsource = r_transsource;
	}
	public String getR_validatestatus() {
		return r_validatestatus;
	}
	public void setR_validatestatus(String r_validatestatus) {
		this.r_validatestatus = r_validatestatus;
	}
	public String getR_wtfoff1() {
		return r_wtfoff1;
	}
	public void setR_wtfoff1(String r_wtfoff1) {
		this.r_wtfoff1 = r_wtfoff1;
	}
	public String getR_wtfoff2() {
		return r_wtfoff2;
	}
	public void setR_wtfoff2(String r_wtfoff2) {
		this.r_wtfoff2 = r_wtfoff2;
	}
	public String getR_wtfoff3() {
		return r_wtfoff3;
	}
	public void setR_wtfoff3(String r_wtfoff3) {
		this.r_wtfoff3 = r_wtfoff3;
	}
	public int getSno() {
		return sno;
	}
	public void setSno(int sno) {
		this.sno = sno;
	}
	public String getA_active() {
		return a_active;
	}
	public void setA_active(String a_active) {
		this.a_active = a_active;
	}
	public String getA_actualchequeamt() {
		return a_actualchequeamt;
	}
	public void setA_actualchequeamt(String a_actualchequeamt) {
		this.a_actualchequeamt = a_actualchequeamt;
	}
	public String getA_actualchequeno() {
		return a_actualchequeno;
	}
	public void setA_actualchequeno(String a_actualchequeno) {
		this.a_actualchequeno = a_actualchequeno;
	}
	public String getA_bank_no() {
		return a_bank_no;
	}
	public void setA_bank_no(String a_bank_no) {
		this.a_bank_no = a_bank_no;
	}
	public String getA_batchid() {
		return a_batchid;
	}
	public void setA_batchid(String a_batchid) {
		this.a_batchid = a_batchid;
	}
	public String getA_cashtransactionid() {
		return a_cashtransactionid;
	}
	public void setA_cashtransactionid(String a_cashtransactionid) {
		this.a_cashtransactionid = a_cashtransactionid;
	}
	public String getA_crdate() {
		return a_crdate;
	}
	public void setA_crdate(String a_crdate) {
		this.a_crdate = a_crdate;
	}
	public String getA_cuscode() {
		return a_cuscode;
	}
	public void setA_cuscode(String a_cuscode) {
		this.a_cuscode = a_cuscode;
	}
	public String getA_depositdate() {
		return a_depositdate;
	}
	public void setA_depositdate(String a_depositdate) {
		this.a_depositdate = a_depositdate;
	}
	public String getA_dishonourtype() {
		return a_dishonourtype;
	}
	public void setA_dishonourtype(String a_dishonourtype) {
		this.a_dishonourtype = a_dishonourtype;
	}
	public String getA_drnbk() {
		return a_drnbk;
	}
	public void setA_drnbk(String a_drnbk) {
		this.a_drnbk = a_drnbk;
	}
	public String getA_drwnbrnchname() {
		return a_drwnbrnchname;
	}
	public void setA_drwnbrnchname(String a_drwnbrnchname) {
		this.a_drwnbrnchname = a_drwnbrnchname;
	}
	public String getA_iadditionalinfo1() {
		return a_iadditionalinfo1;
	}
	public void setA_iadditionalinfo1(String a_iadditionalinfo1) {
		this.a_iadditionalinfo1 = a_iadditionalinfo1;
	}
	public String getA_iadditionalinfo2() {
		return a_iadditionalinfo2;
	}
	public void setA_iadditionalinfo2(String a_iadditionalinfo2) {
		this.a_iadditionalinfo2 = a_iadditionalinfo2;
	}
	public String getA_instdate() {
		return a_instdate;
	}
	public void setA_instdate(String a_instdate) {
		this.a_instdate = a_instdate;
	}
	public String getA_instno() {
		return a_instno;
	}
	public void setA_instno(String a_instno) {
		this.a_instno = a_instno;
	}
	public String getA_instrumentamount() {
		return a_instrumentamount;
	}
	public void setA_instrumentamount(String a_instrumentamount) {
		this.a_instrumentamount = a_instrumentamount;
	}
	public String getA_locationname() {
		return a_locationname;
	}
	public void setA_locationname(String a_locationname) {
		this.a_locationname = a_locationname;
	}
	public String getA_nofins() {
		return a_nofins;
	}
	public void setA_nofins(String a_nofins) {
		this.a_nofins = a_nofins;
	}
	public String getA_receiptslno() {
		return a_receiptslno;
	}
	public void setA_receiptslno(String a_receiptslno) {
		this.a_receiptslno = a_receiptslno;
	}
	public String getA_returnreason() {
		return a_returnreason;
	}
	public void setA_returnreason(String a_returnreason) {
		this.a_returnreason = a_returnreason;
	}
	public String getA_rtnamt() {
		return a_rtnamt;
	}
	public void setA_rtnamt(String a_rtnamt) {
		this.a_rtnamt = a_rtnamt;
	}
	public String getA_rtndate() {
		return a_rtndate;
	}
	public void setA_rtndate(String a_rtndate) {
		this.a_rtndate = a_rtndate;
	}
	public String getA_slipamount() {
		return a_slipamount;
	}
	public void setA_slipamount(String a_slipamount) {
		this.a_slipamount = a_slipamount;
	}
	public String getA_slipno() {
		return a_slipno;
	}
	public void setA_slipno(String a_slipno) {
		this.a_slipno = a_slipno;
	}
	public String getA_status() {
		return a_status;
	}
	public void setA_status(String a_status) {
		this.a_status = a_status;
	}
	public String getA_transactionid() {
		return a_transactionid;
	}
	public void setA_transactionid(String a_transactionid) {
		this.a_transactionid = a_transactionid;
	}
	public String getA_type() {
		return a_type;
	}
	public void setA_type(String a_type) {
		this.a_type = a_type;
	}
	public String getA_validatestatus() {
		return a_validatestatus;
	}
	public void setA_validatestatus(String a_validatestatus) {
		this.a_validatestatus = a_validatestatus;
	}
	
	
	
	
}
