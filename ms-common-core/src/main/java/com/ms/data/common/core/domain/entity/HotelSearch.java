package com.ms.data.common.core.domain.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "hotel_search")
public class HotelSearch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String searchId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hotel_search_details_id", referencedColumnName = "id")
    private HotelSearchDetails hotelSearchDetails;

    private Long count;

    @Column(name = "last_search_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastSearchAt;

    @PrePersist
    public void prePersist() {
        this.count = (this.count == null ? 0 : this.count) + 1;
    }

    @PreUpdate
    public void preUpdate() {
        this.count++;
    }

    public HotelSearch() {
    }

    public HotelSearch(final Long id, final String searchId, final HotelSearchDetails hotelSearchDetails,
                       final Long count) {
        this.id = id;
        this.searchId = searchId;
        this.hotelSearchDetails = hotelSearchDetails;
        this.count = count;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getSearchId() {
        return this.searchId;
    }

    public void setSearchId(final String searchId) {
        this.searchId = searchId;
    }

    public HotelSearchDetails getHotelSearchDetails() {
        return this.hotelSearchDetails;
    }

    public void setHotelSearchDetails(final HotelSearchDetails hotelSearchDetails) {
        this.hotelSearchDetails = hotelSearchDetails;
    }

    public Long getCount() {
        return this.count;
    }

    public void setCount(final Long count) {
        this.count = count;
    }

    public Date getLastSearchAt() {
        return this.lastSearchAt;
    }

    public void setLastSearchAt(final Date lastSearchAt) {
        this.lastSearchAt = lastSearchAt;
    }

    public HotelSearch setRandomValueToSearchId() {
        this.searchId = UUID.randomUUID().toString();
        return this;
    }

}
