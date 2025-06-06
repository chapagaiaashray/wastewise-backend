package com.aashray.backend.model;

import java.io.Serializable;

import jakarta.persistence.*;

@Entity
@Table(name = "bins")
public class BinEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "location_name")
    private String locationName;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "fill_level")
    private double fillLevel;

    @Column(name = "type")
    private String type;

    @Column(name = "status")
    private String status;

    // Constructors
    public BinEntity() {}

    public BinEntity(Long id, String locationName, double latitude, double longitude, double fillLevel, String type, String status) {
        this.id = id;
        this.locationName = locationName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.fillLevel = fillLevel;
        this.type = type;
        this.status = status;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLocationName() { return locationName; }
    public void setLocationName(String locationName) { this.locationName = locationName; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public double getFillLevel() { return fillLevel; }
    public void setFillLevel(double fillLevel) { this.fillLevel = fillLevel; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
