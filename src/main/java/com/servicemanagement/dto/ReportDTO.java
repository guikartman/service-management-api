package com.servicemanagement.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
public class ReportDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private BigDecimal totalCash;
    private BigDecimal totalCashEarned;
    private Integer totalOrder;
    private Integer totalOrderCompleted;
    private Integer totalOrderDelayed;
    private Integer totalOrderInProgress;
    private Integer totalOrderOpen;

    public ReportDTO(BigDecimal totalCash, BigDecimal totalCashEarned, Integer totalOrder, Integer totalOrderCompleted, Integer totalOrderDelayed, Integer totalOrderInProgress, Integer totalOrderOpen) {
        this.totalCash = totalCash;
        this.totalCashEarned = totalCashEarned;
        this.totalOrder = totalOrder;
        this.totalOrderCompleted = totalOrderCompleted;
        this.totalOrderDelayed = totalOrderDelayed;
        this.totalOrderInProgress = totalOrderInProgress;
        this.totalOrderOpen = totalOrderOpen;
    }
}
