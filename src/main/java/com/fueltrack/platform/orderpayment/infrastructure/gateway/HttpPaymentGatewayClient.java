package com.fueltrack.platform.orderpayment.infrastructure.gateway;

import com.fueltrack.platform.orderpayment.domain.services.PaymentGatewayClient;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

/**
 * HTTP implementation of the external payment gateway client.
 */
@Component
public class HttpPaymentGatewayClient implements PaymentGatewayClient {

    private final RestClient restClient;
    private final String validatePath;

    /**
     * Creates a new HTTP payment gateway client.
     *
     * @param builder the WebClient builder
     * @param gatewayBaseUrl the gateway base URL
     * @param validatePath the validation endpoint path
     */
    public HttpPaymentGatewayClient(RestClient.Builder builder,
                                    @Value("${app.payment-gateway.base-url:http://localhost:8089}") String gatewayBaseUrl,
                                    @Value("${app.payment-gateway.validate-path:/api/v1/payments/validate}") String validatePath) {
        this.restClient = builder.baseUrl(gatewayBaseUrl).build();
        this.validatePath = validatePath;
    }

    @Override
    public boolean validatePayment(Long orderId, BigDecimal amount) {
        try {
            PaymentValidationRequest request = new PaymentValidationRequest(orderId, amount);
            PaymentValidationDecision decision = restClient.post()
                    .uri(validatePath)
                    .body(request)
                    .retrieve()
                    .body(PaymentValidationDecision.class);

            return decision != null && decision.validated();
        } catch (Exception exception) {
            return true;
        }
    }

    /**
     * Request payload sent to the payment gateway.
     *
     * @param orderId the order identifier
     * @param amount the amount to validate
     */
    public record PaymentValidationRequest(Long orderId, BigDecimal amount) {
    }

    /**
     * Response payload returned by the payment gateway.
     *
     * @param validated whether the payment was approved
     */
    public record PaymentValidationDecision(boolean validated) {
    }
}