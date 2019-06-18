package com.photo.bas.core.exception;

/**
 * @author FengYu
 *
 */
public class DocumentError extends ERPException {
	private static final long serialVersionUID = -3774206591255196001L;
	
	public static final String EXCEPTION = "Document.Error.Exception";
	public static final String NO_VENDOR = "Document.Error.NoVendor";
	public static final String ASSIGNED_QTY_EXCEED_RO_QTY_TO_RECEIVE = "Document.Error.AssignedQtyExceedROQtyToReceive";
	public static final String ASSIGNED_QTY_EXCEED_PO_QTY = "Document.Error.AssignedQtyExceedPOQty";
	public static final String ASSIGNED_QTY_EXCEED_TO_QTY = "Document.Error.AssignedQtyExceedTOQty";
	public static final String RECEIPT_CAN_NOT_CLOSE_4_PO = "Document.Error.ReceiptCanNotClose4PO";
	public static final String RECEIPT_CAN_NOT_CLOSE_4_TO = "Document.Error.ReceiptCanNotClose4TO";
	public static final String RECEIPT_CAN_NOT_CLOSE_4_TO_4_SHIPMENT = "Document.Error.ReceiptCanNotClose4TO4Shipment";
	public static final String RECEIPT_CAN_NOT_CLOSE_4_RO = "Document.Error.ReceiptCanNotClose4RO";
	public static final String TO_CAN_NOT_CLOSE_4_RP = "Document.Error.TOCanNotClose4RP";
	public static final String INVOICE_QTY_EXCEED_PO_RECEIVED_QTY = "Document.Error.InvoiceQtyExceedPOReceivedQty";
	public static final String WARNING_INVOICE_QTY_EXCEED_PO_RECEIVED_QTY = "Document.Warning.InvoiceQtyExceedPOReceivedQty";
	public static final String INVOICE_ALREADY_FULLY_INVOICED = "Document.Error.InvoiceAlreadyFullyInvoiced";
	public static final String DONOT_HAVE_ITEM_LINE_4_PI = "Document.Error.pi.lacking.itemLine";
	public static final String DONOT_HAVE_ITEM_LINE_4_RP = "Document.Error.rp.lacking.itemLine";
	public static final String DONOT_HAVE_ITEM_LINE_4_CN = "Document.Error.cn.lacking.itemLine";
	public static final String DONOT_HAVE_LINE_4_TL = "Document.Error.tl.lacking.line";
	public static final String SHIP_COMPLETE_FAILED = "Document.Error.ShipCompleteFailed";
	public static final String CREDIT_QTY_EXCEED_SI ="Document.Error.CreditQtyExceed";
	public static final String CREDIT_AMOUNT_EXCEED_SI ="Document.Error.CreditQtyAmount";
	public static final String CREDIT_QTY_EXCEED_RO ="Document.Error.CreditQtyExceedRo";
	public static final String CREDIT_DRAFT_CREDIT_EXIST ="Document.Error.DraftCreditExist";
	public static final String CN_PRICE_IS_NEGATIVE ="Document.Error.CNPriceIsNegative";
	public static final String SI_CLOSED = "Document.Error.SIIsClosed";
	public static final String LOAD_SHEET_EMAIL_ADDRESS_EMPTY = "Document.Error.LoadSheetEmailAddressEmpty";
	public static final String SALES_ORDER_EMAIL_ADDRESS_EMPTY = "Document.Error.SalesOrderEmailAddressEmpty";
	public static final String SALES_INVOICE_EMAIL_ADDRESS_EMPTY = "Document.Error.SalesInvoiceEmailAddressEmpty";
	public static final String TEST_CERTIFICATE_EMAIL_ADDRESS_EMPTY = "Document.Error.SOBOLEmailAddressEmpty";
	public static final String PURCHASEORDER_EMAIL_ADDRESS_EMPTY = "Document.Error.PurchaseOrderEmailAddressEmpty";
	public static final String SAVE_LINE_FAILED_4_RP = "Document.Error.ReceiptQtyIsInvalid";

	public static final String POSTING_PI_BEFORE_RECEIPT = "Document.Error.PostingPIBeforeReceipt";
	
	public static final String EXISTS_RT_FOR_RO = "Document.Error.ExistsRTForRO";
	public static final String EXIST_CN_FOR_RO ="Document.Error.ExistCNForRO";
	
	public static final String EXISTS_SP_FOR_TL = "Document.Error.ExistsSPForTL";
	
	public static final String TUBING_PRODUCTION_CORRECTIONS_4_TEST_TAG = "Document.Error.TpcForTestTag";
	
	public static final String CREATE_PI_4_PO = "Document.Error.CreatePI4PO";
	
	public static final String SEND_EMAIL_FAILED = "Document.Error.SendEmailFailed";
	
	protected DocumentError() {
		super();
	}
	protected DocumentError(String message, Throwable cause) {
		super(message, cause);
	}
	protected DocumentError(Throwable cause) {
		super(cause);
	}

	public DocumentError(String message) {
		super(message);
	}
	public DocumentError(String message, String detailMsg) {
		super(message);
		setDetailErrorMsg(detailMsg);
	}
	
}
