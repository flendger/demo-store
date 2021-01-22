package ru.flendger.demo.store.demo.store.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;
import ru.flendger.demo.store.demo.store.model.Product;

public class ProductSpecification {

    private static Specification<Product> priceGreaterOrEqualsThan(int min) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), min);
    }

    private static Specification<Product> priceLessOrEqualsThan(int max) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), max);
    }

    private static Specification<Product> titleLike(String title) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format("%%%s%%", title));
    }

    public static Specification<Product> build(MultiValueMap<String, String> params) {
        Specification<Product> spec = Specification.where(null);

        if (params.containsKey("min") && !params.getFirst("min").isBlank()) {
            spec = spec.and(priceGreaterOrEqualsThan(Integer.parseInt(params.getFirst("min"))));
        }

        if (params.containsKey("max") && !params.getFirst("max").isBlank()) {
            spec = spec.and(priceLessOrEqualsThan(Integer.parseInt(params.getFirst("max"))));
        }

        if (params.containsKey("title") && !params.getFirst("title").isBlank()) {
            spec = spec.and(titleLike(params.getFirst("title")));
        }

        return spec;
    }
}
