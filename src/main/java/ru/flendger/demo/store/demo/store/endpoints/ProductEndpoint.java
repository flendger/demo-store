package ru.flendger.demo.store.demo.store.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.flendger.demo.store.demo.store.services.ProductService;
import ru.flendger.demo.store.demo.store.ws.products.GetProductsResponse;

@Endpoint
@RequiredArgsConstructor
public class ProductEndpoint {
    private static final String NAMESPACE_URI = "http://www.flendger.ru/store/ws/products";
    private final ProductService productService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductsRequest")
    @ResponsePayload
    public GetProductsResponse getProductsRequest() {
        GetProductsResponse response = new GetProductsResponse();
        productService.findAllProductXmlDto().forEach(response.getProducts()::add);
        return response;
    }
}
