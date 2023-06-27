package me.smallproject.tutoring.course.infrastructure.dao

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import me.smallproject.tutoring.course.application.dto.CourseInbound
import me.smallproject.tutoring.course.application.dto.CourseOutbound
import me.smallproject.tutoring.course.domain.entity.QCourse.course
import me.smallproject.tutoring.course.domain.repository.CourseRepositoryCustom
import org.springframework.stereotype.Component


/**
 * @author Doyeop Kim
 * @since 2023/06/20
 */
@Component
class CourseRepositoryImpl(private val jpaQueryFactory: JPAQueryFactory) : CourseRepositoryCustom {
    override fun searchCoursesByCriteria(getRequest: CourseInbound.GetSimpleRequest): CourseOutbound.PaginatedResponse {
        // 조건문
        val whereExpression = getPaginationCriteria(getRequest)

        // 조건에 맞는 수강과정들을 목록으로 받아오기
        val items = jpaQueryFactory.select(
            Projections.constructor(
                CourseOutbound.PaginatedResponseData::class.java,
                course.id,
                course.language,
                course.courseType,
                course.courseDuration,
                course.lessonTime,
                course.lessonsCount,
                course.price,
                course.sellingInfo,
                course.createdAt
            )
        )
            .from(course)
            .where(whereExpression) // 조건절에 맞춰서 쿼리
            .orderBy(course.createdAt.desc()) // 생성일자 기준으로 내림차순 정렬
            .offset(getRequest.pageable.offset)
            .limit(getRequest.pageable.pageSize.toLong()) // 정해진 페이지네이션 규칙에 따라 데이터 수집
            .fetch()

        // 조건에 맞는 아이템의 총 개수를 가져오기
        val totalElements = jpaQueryFactory.select(course.count())
            .from(course)
            .where(whereExpression)
            .fetchFirst()

        return CourseOutbound.PaginatedResponse(totalElements, items)
    }

    // 페이지네이션에 대한 조건문 쿼리를 반환하는 메소드
    private fun getPaginationCriteria(getRequest: CourseInbound.GetSimpleRequest): BooleanExpression {
        // 판매 기간, 판매 여부에 대한 쿼리
        val sellingConditionExpression = with(course.sellingInfo) {
            this.selling.eq(true)
                .and(isNowWithinDateRange(this.sellingStartDate, this.sellingEndDate))
        }

        // 수업 종류에 대한 쿼리
        val courseTypeExpression = with(course.courseType) {
            this.chattingServes.eq(getRequest.isChatAvailable)
                .and(this.voiceServes.eq(getRequest.isVoiceAvailable))
                .and(this.videoServes.eq(getRequest.isVideoAvailable))
        }

        // 조건문 조립
        return sellingConditionExpression
            .and(course.language.eq(getRequest.language))
            .and(courseTypeExpression)
    }

    // 현재 날짜가 주어진 두 날짜 사이에 위치하는지 여부를 표현하는 boolean expression
    private fun isNowWithinDateRange(startDate: DatePath<LocalDate>, endDate: DatePath<LocalDate>): BooleanExpression {
        val nowDate = LocalDate.now()

        // 현재 날짜는 주어진 판매 시작일보다 크거나 같아야한다
        val lessOrEqualsExpression = startDate.loe(nowDate)
        // 현재 날짜는 주어진 판매 종료일보다 작거나 같아야한다
        val greaterOrEqualsExpression = endDate.goe(nowDate)

        return lessOrEqualsExpression.and(greaterOrEqualsExpression)
    }
}