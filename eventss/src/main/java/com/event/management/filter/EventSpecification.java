package com.event.management.filter;

import com.event.management.model.entity.Event;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class EventSpecification {
    public static Specification<Event> filterEvents(EventFilter filter){
        return ((root, query, criteriaBuilder) -> {
            var predicate = criteriaBuilder.conjunction();

            if (filter.getTitle() != null){
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("title")),
                                "%" + filter.getTitle().toLowerCase() + "%"));

            }
            if (filter.getDescription() != null){
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("description")),
                                "%" + filter.getDescription().toLowerCase() + "%"));

            }

            if (filter.getCapacity() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get("capacity"), filter.getCapacity()));
            }

            if (filter.getMinPrice() != null){
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.greaterThanOrEqualTo(root.get("price"), filter.getMinPrice()));

            }
            if (filter.getMaxPrice() != null){
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.lessThanOrEqualTo(root.get("price"), filter.getMaxPrice()));

            }

            if (filter.getEventDate() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get("eventDate"), filter.getEventDate()));
            }
            // min <= 100 >= max
            return predicate;
        });
    }
}