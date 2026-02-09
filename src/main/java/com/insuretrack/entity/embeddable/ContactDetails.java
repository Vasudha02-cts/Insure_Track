package com.insuretrack.entity.embeddable;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class ContactDetails {
    private String email;
    private String phone;
    private String contactInfo;
}