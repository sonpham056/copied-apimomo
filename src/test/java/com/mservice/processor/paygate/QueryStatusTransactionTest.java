package com.mservice.processor.paygate;

import com.mservice.allinone.models.QueryStatusTransactionRequest;
import com.mservice.allinone.models.QueryStatusTransactionResponse;
import com.mservice.allinone.processor.allinone.QueryStatusTransaction;
import com.mservice.shared.sharedmodels.Environment;
import com.mservice.shared.sharedmodels.PartnerInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QueryStatusTransactionTest {
    PartnerInfo devInfo = new PartnerInfo("MOMOLRJZ20181206", "mTCKt9W3eU1m39TW", "KqBEecvaJf1nULnhPF5htpG3AMtDIOlD");
    Environment env = new Environment("https://test-payment.momo.vn/gw_payment/transactionProcessor", devInfo, "development");

    String orderId = "1560763203689";
    String requestId = "1560760777994";

    String exceptionMessage;

    @ParameterizedTest
    @CsvSource({
            "1560763203689,1560760777994,fe7c5f9655dc30e9f559a6d1c81e7912b2cf74b08f7ea052a1defdb5d3d2c31a,bb26bacbc6aeeaf61a4fad93b879daac322d3f8f772c5e8a00703553f6442396,-1",
            "1561047808782,1561047808782,908349181c8f315d3feeb9c00e2293740caaf8fb33d89864321d6480fd76f9e6,f5547a014813b2292117a63d4c3997b10d7e55d711d5c69210753d079815d9a0,37"
    })
        //add more test if needed
    void queryStatusTest(String orderId, String requestId, String reqSign, String resSign, String errorCode) throws Exception {
        QueryStatusTransaction queryStatusTransaction = new QueryStatusTransaction(env);

        QueryStatusTransactionRequest queryStatusRequest = queryStatusTransaction.createQueryRequest(orderId, requestId);
        assertEquals(reqSign, queryStatusRequest.getSignature(), "Wrong Query Transaction Request Signature");

        QueryStatusTransactionResponse queryStatusResponse = queryStatusTransaction.execute(queryStatusRequest);
        assertEquals(Integer.valueOf(errorCode), queryStatusResponse.getErrorCode(), "Wrong Response");
        assertEquals(resSign, queryStatusResponse.getSignature(), "Wrong Query Transaction Response Signature");

    }
}