/**
 * palava - a java-php-bridge
 * Copyright (C) 2007  CosmoCode GmbH
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package de.cosmocode.palava.model.base;

import javax.persistence.Column;

import com.google.common.base.Preconditions;

/**
 * Abstract base implementation of the {@link AddressBase} interface.
 *
 * @author Willi Schoenborn
 */
public abstract class AbstractAddress implements AddressBase {

    private String street;
    
    @Column(name = "street_number")
    private String streetNumber;
    
    private String additional;
    
    @Column(name = "postal_code")
    private String postalCode;
    
    private String district;
    
    private String city;
    
    private String state;
    
    @Column(name = "country_code")
    private String countryCode;

    private Double latitude;
    
    private Double longitude;
    
    private String phone;
    
    @Column(name = "mobile_phone")
    private String mobilePhone;
    
    private String fax;
    
    private String email;
    
    private String website;
    
    @Override
    public String getStreet() {
        return street;
    }
    
    @Override
    public void setStreet(String street) {
        this.street = street;
    }
    
    @Override
    public String getStreetNumber() {
        return streetNumber;
    }
    
    @Override
    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }
    
    @Override
    public String getAdditional() {
        return additional;
    }
    
    @Override
    public void setAdditional(String additional) {
        this.additional = additional;
    }
    
    @Override
    public String getPostalCode() {
        return postalCode;
    }
    
    @Override
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    
    @Override
    public String getDistrict() {
        return district;
    }
    
    @Override
    public void setDistrict(String district) {
        this.district = district;
    }
    
    @Override
    public String getCity() {
        return city;
    }
    
    @Override
    public void setCity(String city) {
        this.city = city;
    }
    
    @Override
    public String getState() {
        return state;
    }
    
    @Override
    public void setState(String state) {
        this.state = state;
    }
    
    @Override
    public String getCountryCode() {
        return countryCode;
    }
    
    @Override
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    
    @Override
    public LocationBase getLocation() {
        return new InternalLocation();
    }
    
    /**
     * 
     *
     * @author Willi Schoenborn
     */
    private class InternalLocation implements LocationBase {
        
        @Override
        public Double getLatitude() {
            return latitude;
        }
        
        @Override
        public void setLatitude(Double latitude) {
            AbstractAddress.this.latitude = latitude;
        }
        
        @Override
        public Double getLongitude() {
            return longitude;
        }
        
        @Override
        public void setLongitude(Double longitude) {
            AbstractAddress.this.longitude = longitude;
        }
        
    }
    
    @Override
    public void setLocation(LocationBase location) {
        Preconditions.checkNotNull(location, "Location");
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
    }
    
    @Override
    public boolean hasLocation() {
        return latitude != null && longitude != null;
    }
    
    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    @Override
    public String getMobilePhone() {
        return mobilePhone;
    }
    
    @Override
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
    
    @Override
    public String getFax() {
        return fax;
    }
    
    @Override
    public void setFax(String fax) {
        this.fax = fax;
    }
    
    @Override
    public String getEmail() {
        return email;
    }
    
    @Override
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public String getWebsite() {
        return website;
    }
    
    @Override
    public void setWebsite(String website) {
        this.website = website;
    }

}
