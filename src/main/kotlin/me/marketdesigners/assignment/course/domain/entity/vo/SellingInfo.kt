package me.marketdesigners.assignment.course.domain.entity.vo

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.time.LocalDate

/**
 * @author Doyeop Kim
 * @since 2023/06/20
 */
@Embeddable
data class SellingInfo(
    @get:Column(name = "selling_start_date", nullable = false)
    var sellingStartDate: LocalDate = LocalDate.now(),
    @get:Column(name = "selling_end_date", nullable = false)
    var sellingEndDate: LocalDate = LocalDate.now(),
    @get:Column(name = "is_selling", nullable = false)
    var isSelling: Boolean = false,
)