package com.insuretrack.entity.enums;

/**
 * Types of policy changes for endorsements.
 * Maps to Endorsement.ChangeType column.
 */
public enum EndorsementChangeType {
    AddCoverage, RemoveCoverage, LimitChange, BeneficiaryChange
}