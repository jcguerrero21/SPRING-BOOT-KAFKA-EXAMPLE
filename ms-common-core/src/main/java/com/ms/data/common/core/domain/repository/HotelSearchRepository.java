package com.ms.data.common.core.domain.repository;

import com.ms.data.common.core.domain.entity.HotelSearch;
import com.ms.data.common.core.domain.entity.HotelSearchDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface HotelSearchRepository extends JpaRepository<HotelSearch, Long> {

    @Query(value = """
            SELECT h.* FROM hotel_search h
            INNER JOIN hotel_search_details hd ON h.hotel_search_details_id = hd.id
            WHERE hd.hotel_id = :#{#hotelSearchDetails.hotelId} AND hd.check_in = :#{#hotelSearchDetails.checkIn} AND hd.check_out = :#{#hotelSearchDetails.checkOut}
            AND (SELECT COUNT(*) FROM ages a WHERE a.id = hd.id AND a.ages IN :#{#hotelSearchDetails.ages}) = :#{#hotelSearchDetails.ages.size()}
            AND (SELECT COUNT(*) FROM ages a WHERE a.id = hd.id) = :#{#hotelSearchDetails.ages.size()}
            """, nativeQuery = true)
    Optional<HotelSearch> findByHotelSearchDetails(@Param("hotelSearchDetails") HotelSearchDetails hotelSearchDetails);

    Optional<HotelSearch> findBySearchId(String searchId);

}
