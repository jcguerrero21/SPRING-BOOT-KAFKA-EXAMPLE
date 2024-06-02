package com.ms.data.common.core.domain.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "hotel_search_details")
@DynamicUpdate
public class HotelSearchDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "hotel_id")
    private String hotelId;

    @Temporal(TemporalType.DATE)
    @Column(name = "check_in")
    private Date checkIn;

    @Temporal(TemporalType.DATE)
    @Column(name = "check_out")
    private Date checkOut;

    @ElementCollection
    @CollectionTable(name = "ages", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "ages")
    private Set<Integer> ages;

    public HotelSearchDetails() {
    }

    public HotelSearchDetails(final Long id, final String hotelId, final Date checkIn, final Date checkOut,
                              final Set<Integer> ages) {
        this.id = id;
        this.hotelId = hotelId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.ages = ages;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getHotelId() {
        return this.hotelId;
    }

    public void setHotelId(final String hotelId) {
        this.hotelId = hotelId;
    }

    public Date getCheckIn() {
        return this.checkIn;
    }

    public void setCheckIn(final Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return this.checkOut;
    }

    public void setCheckOut(final Date checkOut) {
        this.checkOut = checkOut;
    }

    public Set<Integer> getAges() {
        return this.ages;
    }

    public void setAges(final Set<Integer> ages) {
        this.ages = ages;
    }

}
