package com.insuretrack.dto;
import com.insuretrack.dto.CoverageResponseDTO;
import com.insuretrack.dto.RatingRuleResponseDTO;
import lombok.Data;
import java.util.List;

@Data
public class ProductResponseDTO {
    private Long productId;
    private String name;
    private String description;
    private String status;

    // These allow you to see the nested data in one view
    private List<CoverageResponseDTO> coverages;
    private List<RatingRuleResponseDTO> ratingRules;
}