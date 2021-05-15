package com.mservice.processor.paygate;

import com.mservice.allinone.models.RefundMoMoRequest;
import com.mservice.allinone.models.RefundMoMoResponse;
import com.mservice.allinone.processor.allinone.RefundMoMo;
import com.mservice.shared.sharedmodels.Environment;
import com.mservice.shared.sharedmodels.PartnerInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RefundMoMoTest {
    PartnerInfo devInfo = new PartnerInfo("MOMOLRJZ20181206", "mTCKt9W3eU1m39TW", "KqBEecvaJf1nULnhPF5htpG3AMtDIOlD");
    Environment env = new Environment("https://test-payment.momo.vn/gw_payment/transactionProcessor", devInfo, "development");

    RefundMoMo refundMoMo = new RefundMoMo(env);

    String exceptionMessage;

    @Test
    void createRefundRequest() throws Exception {
        RefundMoMoRequest request = refundMoMo.createRefundRequest("1527314064527", "1527297954700", "55000", "144492817");
        assertEquals("a8794aded9704a61d115587a6a244d75999dd26f679da4844eb25616b6df2124", request.getSignature(), "Wrong Signature");
    }

    @Test
    void testShouldNotCreateNewRequest() throws Exception {
        //need actual transId
        String transId = "144492817";
        String orderId = "1562296730694";

        RefundMoMoRequest request = refundMoMo.createRefundRequest("1527314064527", orderId, "7500", transId);
        assertEquals("fe23df8fdbed97879145ad900d63b30fd91e7d661b2984da41ef45b824e2b2f8", request.getSignature(), "Wrong Signature -- Problem with Hash");
        assertEquals("refundMoMoWallet", request.getRequestType(), "Wrong Request Type -- Must be refundMoMoWallet");

        RefundMoMoResponse response = refundMoMo.execute(request);
        assertNull(response, "Wrong Processing Result after executing RefundMoMoRequest");

    }


}