package com.insuretrack.entity.enums;
public enum QuoteStatus {
    Draft,      // Created by Customer
    Submitted,  // Sent for Rating
    Rated,      // Premium Calculated
    Approved,   // Confirmed by Underwriter
    Bound,      // Paid and Policy Issued
    Expired     // If not paid in time
}