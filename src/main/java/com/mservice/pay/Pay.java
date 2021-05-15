package com.mservice.pay;

import com.google.gson.Gson;
import com.mservice.pay.models.*;
import com.mservice.pay.processor.notallinone.*;
import com.mservice.shared.constants.Parameter;
import com.mservice.shared.constants.RequestType;
import com.mservice.shared.sharedmodels.Environment;
import com.mservice.shared.sharedmodels.PartnerInfo;
import com.mservice.shared.utils.Encoder;
import com.mservice.shared.utils.LogUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Pay {

    public static void main(String... args) throws Exception {

        LogUtils.init();

        String publicKey = "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAkC3M5MmmME8yskpPsJaCli8KPvpz2J844mNQSGFBAzeL1gPay6FzCrYR7dxBfJ4PcHsPtKb0BjTigablnkigsry5QCb/i9GgEqVksRl4mbL6CsOLgPRL8XSzP4Gb0JuLhYWArssKNJ9DTkl/d1Y28B99RaznNQSVYQAT1heOYIFMW3//SxEOgatm6kA0DJ88z22z8N2zz94MG2Cka+u3Z8aRIEUBjrrqgq2CB9hXvfGtd/8IOLAWt5RFu2q3Wyt5JP7C9IFYUppFmrHrXZjiS4m78eQJwp4OzWEeOMiej6mD9i8iVGZZpd6V42+zGV5A2PusvlTVfHHPL8AYFhk/afpKXdyDQ7qqWnGEAOXNeVZBfUkoSaEo0GKOKg2wIcHF8VboDlIaebxxn12JAUcBYHVhoayI3uVNI4YQ4+mwrmWYHj2HunjYsvECH8JLGZGEAX+1/c7egLzt+gLPhQAjZ/oKVzsB9oEvQRmN3DoKQkeKP9gPk6NUgSNp7xsFt5Walz26IU+cHqGWL5dl/71+C2xY2zE3mSvAp4Mebs21/THHmfGHcHv40SyC9qAZK8weYBgk9aEYr0EwYV3SOwnSmbzmaB27KYzIzUj0KnOJQfjo+9rEno/COs0BkvcvlJ4ZvDLxOTX9K+FlbEsrez2vpf5S99izb9hVyDYz49YIyrcCAwEAAQ==";

//        PartnerInfo devInfo = new PartnerInfo("MOMOLRJZ20181206", "TNWFx9JWayevKPiB8LyTgODiCSrjstXN", "KqBEecvaJf1nULnhPF5htpG3AMtDIOlD",publicKey);
//        //change the endpoint according to the appropriate processes

//        String commit = RequestType.CONFIRM_APP_TRANSACTION;
//        String rollback = RequestType.CANCEL_APP_TRANSACTION;
        long amount = 10000;
        String partnerRefId = String.valueOf(System.currentTimeMillis());
//        String partnerTransId = "1561046083186";
//        String requestId = String.valueOf(System.currentTimeMillis());
//        String momoTransId = "147938695";
//        String customerNumber = "0963181714";
//        String partnerName = "1561046083186";
//        String storeId = "";
//        String storeName = "1561046083186";
//        String appData = "1561046083186";//data from MoMo app
//        String description = "Pay with MoMo";
//        String paymentCode = "MM515023896957011876";



        // Uncomment to use the processes you need
        // Make sure you are using the correct environment for each processes
        // Change to valid IDs and information to use AppPay, POS, Refund processes.

        //
//        AppPayResponse appProcessResponse = AppPay.process(Environment.selectEnv("dev", Environment.ProcessType.APP_IN_APP), partnerRefId, partnerTransId, amount, partnerName,
//                storeId, storeName, publicKey, customerNumber, appData, description, Parameter.VERSION, Parameter.APP_PAY_TYPE);
        POSPayResponse posProcessResponse = POSPay.process(Environment.selectEnv(Environment.EnvTarget.DEV, Environment.ProcessType.PAY_POS), partnerRefId, amount, "", "", publicKey, "", "536153917546247748", Parameter.VERSION, Parameter.APP_PAY_TYPE);
//
//        PayConfirmationResponse payConfirmationResponse = PayConfirmation.process(Environment.selectEnv("dev", Environment.ProcessType.PAY_CONFIRM), "35646", rollback, requestId, "2305062978", "", "");
//
//        TransactionQueryResponse transactionQueryResponse = TransactionQuery.process(Environment.selectEnv("dev", Environment.ProcessType.PAY_QUERY_STATUS), "1562298553079", "1562299067267", publicKey, "", Parameter.VERSION);
//        TransactionQuery.process(Environment.selectEnv("dev", Environment.ProcessType.PAY_QUERY_STATUS), "1562299067267", "1562299177871", publicKey, "", Parameter.VERSION);
//
//        TransactionRefundResponse transactionRefundResponse = TransactionRefund.process(Environment.selectEnv("dev", Environment.ProcessType.PAY_QUERY_STATUS), "1562298553079", "", publicKey, "2305062760", amount, "", "1562299067267", Parameter.VERSION);
    }
    //generate RSA signature from given information
    public static String generateRSA(String phoneNumber, String billId, String transId, String username, String partnerCode, long amount, String publicKey) throws Exception {
        // current version of Parameter key name is 2.0
        Map<String, Object> rawData = new HashMap<String, Object>();
        rawData.put(Parameter.CUSTOMER_NUMBER, phoneNumber);
        rawData.put(Parameter.PARTNER_REF_ID, billId);
        rawData.put(Parameter.PARTNER_TRANS_ID, transId);
        rawData.put(Parameter.USERNAME, username);
        rawData.put(Parameter.PARTNER_CODE, partnerCode);
        rawData.put(Parameter.AMOUNT, amount);

        Gson gson = new Gson();
        String jsonStr = gson.toJson(rawData);
        byte[] testByte = jsonStr.getBytes(StandardCharsets.UTF_8);
        String hashRSA = Encoder.encryptRSA(testByte, publicKey);

        return hashRSA;
    }
}
