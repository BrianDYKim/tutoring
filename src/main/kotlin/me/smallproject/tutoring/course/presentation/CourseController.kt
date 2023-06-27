package me.smallproject.tutoring.course.presentation

import me.smallproject.tutoring.common.result.ResponseGenerator
import me.smallproject.tutoring.common.result.SuccessResults
import me.smallproject.tutoring.course.application.dto.CourseInbound
import me.smallproject.tutoring.course.application.dto.CourseOutbound
import me.smallproject.tutoring.course.application.service.CourseService
import me.smallproject.tutoring.course.domain.entity.enums.CourseLanguage
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


/**
 * @author Doyeop Kim
 * @since 2023/06/21
 */
@RestController
@RequestMapping("/api/courses")
class CourseController(private val courseService: CourseService) {
    @GetMapping("")
    fun searchAvailableCourses(
        @RequestParam("language") language: CourseLanguage,
        @RequestParam("voice") isVoiceAvailable: Boolean,
        @RequestParam("chat") isChatAvailable: Boolean,
        @RequestParam("video") isVideoAvailable: Boolean,
        @RequestParam("page") page: Int,
        @RequestParam("pageSize") pageSize: Int,
    ): SuccessResults.Paginated<CourseOutbound.PaginatedResponseData> {
        val getRequest = CourseInbound.GetSimpleRequest.of(
            language,
            isVoiceAvailable,
            isChatAvailable,
            isVideoAvailable,
            page,
            pageSize
        )

        val paginatedResponse = this.courseService.searchAvailableCourses(getRequest)

        val response = with(paginatedResponse) {
            ResponseGenerator.getPaginatedDataResponse(this.totalElements, getRequest.pageable.pageNumber, this.items)
        }

        return response
    }
}