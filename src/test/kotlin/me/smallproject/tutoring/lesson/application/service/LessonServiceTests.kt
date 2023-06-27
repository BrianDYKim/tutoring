package me.smallproject.tutoring.lesson.application.service


/**
 * @author Doyeop Kim
 * @since 2023/06/22
 */
@ExtendWith(MockKExtension::class)
class LessonServiceTests {
    private lateinit var lessonService: LessonService
    private lateinit var lessonValidator: LessonValidator
    private lateinit var lessonRepository: LessonRepository
    private lateinit var studentRepository: StudentRepository
    private lateinit var tutorRepository: TutorRepository
    private lateinit var lessonSubscriptionRepository: LessonSubscriptionRepository

    @BeforeEach
    fun testSetUp() {
        lessonRepository = mockk(relaxed = true, relaxUnitFun = true)
        studentRepository = mockk(relaxed = true, relaxUnitFun = true)
        tutorRepository = mockk(relaxed = true, relaxUnitFun = true)
        lessonSubscriptionRepository = mockk(relaxed = true, relaxUnitFun = true)
        lessonValidator = spyk(
            LessonValidatorImpl(
                studentRepository,
                tutorRepository,
                lessonSubscriptionRepository,
                lessonRepository,
            )
        )
        lessonService =
            spyk(LessonServiceImpl(lessonValidator, lessonRepository, lessonSubscriptionRepository, tutorRepository))
    }

    // ============================== [수업 시작 로직 테스트] ==============================
    @ParameterizedTest
    @CsvSource(value = ["1,1,1", "2,1,1"])
    fun `해당 식별자를 가지는 학생이 존재하지 않아서 실패한다`(studentId: Long, tutorId: Long, lessonSubscriptionId: Long): Unit {
        // given: 둘다 유효하지 않은 학생을 모킹한다
        val startRequest = LessonInbound.StartRequest(studentId, tutorId, lessonSubscriptionId)

        every { studentRepository.findByIdOrNull(1) } returns null
        every { studentRepository.findByIdOrNull(2) } returns Student().apply {
            this.id = 2
            this.isDeleted = true
        }

        // then
        val exception = shouldThrow<ResourceNotFoundException> { lessonService.startLesson(startRequest) }
        assertEquals(exception.errorCode, ErrorCode.STUDENT_NOT_FOUND_ERROR)
    }

    @ParameterizedTest
    @CsvSource(value = ["1,1,1", "1,2,1"])
    fun `해당 식별자를 가지는 튜터가 존재하지 않아서 실패한다`(studentId: Long, tutorId: Long, lessonSubscriptionId: Long): Unit {
        // given: 학생은 유효하지만, 튜터가 존재하지는 않는 상황을 모킹
        val startRequest = LessonInbound.StartRequest(studentId, tutorId, lessonSubscriptionId)

        every { studentRepository.findByIdOrNull(studentId) } returns Student().apply {
            this.id = studentId
        }
        every { tutorRepository.findByIdOrNull(1) } returns null
        every { tutorRepository.findByIdOrNull(2) } returns Tutor().apply {
            this.id = 2
            this.isDeleted = true
        }

        // then
        val exception = shouldThrow<ResourceNotFoundException> { lessonService.startLesson(startRequest) }
        assertEquals(exception.errorCode, ErrorCode.TUTOR_NOT_FOUND_ERROR)
    }

    @ParameterizedTest
    @CsvSource(value = ["1,1,1", "1,1,2"])
    fun `학생이 가진 수강권이 존재하지 않아서 실패한다`(studentId: Long, tutorId: Long, lessonSubscriptionId: Long): Unit {
        // given: 학생도 유효하고, 튜터도 유효하지만 수강권이 유효하지 않은 상황을 모킹
        val startRequest = LessonInbound.StartRequest(studentId, tutorId, lessonSubscriptionId)

        every { studentRepository.findByIdOrNull(studentId) } returns Student().apply {
            this.id = studentId
        }
        every { tutorRepository.findByIdOrNull(tutorId) } returns Tutor().apply {
            this.id = tutorId
        }
        every { lessonSubscriptionRepository.findByIdOrNull(1) } returns null
        every { lessonSubscriptionRepository.findByIdOrNull(2) } returns LessonSubscription().apply {
            this.id = 2
            this.isDeleted = true
        }

        // then
        val exception = shouldThrow<ResourceNotFoundException> { lessonService.startLesson(startRequest) }
        assertEquals(exception.errorCode, ErrorCode.SUBSCRIPTION_NOT_FOUND_ERROR)
    }

    @ParameterizedTest
    @CsvSource(value = ["1,1,1"])
    fun `학생이 가진 수강권의 잔여 횟수가 모두 소진되어 실패한다`(studentId: Long, tutorId: Long, lessonSubscriptionId: Long): Unit {
        // given: 엔티티들은 모두 유효하지만, 수강권의 잔여 횟수가 0 이하인 경우 실패 처리
        val startRequest = LessonInbound.StartRequest(studentId, tutorId, lessonSubscriptionId)

        every { studentRepository.findByIdOrNull(studentId) } returns Student().apply {
            this.id = studentId
        }
        every { tutorRepository.findByIdOrNull(tutorId) } returns Tutor().apply {
            this.id = tutorId
        }
        every { lessonSubscriptionRepository.findByIdOrNull(lessonSubscriptionId) } returns LessonSubscription().apply {
            this.id = lessonSubscriptionId
            this.lessonCountInfo.lessonLeftCount = 0
        }

        // then
        val exception = shouldThrow<SubscriptionNotLeftException> { lessonService.startLesson(startRequest) }
        assertEquals(exception.errorCode, ErrorCode.SUBSCRIPTION_NOT_LEFT_ERROR)
    }

    @Test
    fun `수강권이 만료되어있어 실패한다`(): Unit {
        // given
        val studentId: Long = 1
        val tutorId: Long = 1
        val lessonSubscriptionId: Long = 1
        val lessonId: Long = 1

        val startRequest = LessonInbound.StartRequest(studentId, tutorId, lessonSubscriptionId)

        // 가짜 객체 선언
        val fakeStudent = Student().apply {
            this.id = studentId
        }

        val fakeTutor = Tutor().apply {
            this.id = tutorId
            this.tutorType = TutorType(isVoiceAvailable = true, isChattingAvailable = true, isVideoAvailable = true)
        }

        val fakeSubscription = LessonSubscription().apply {
            this.id = lessonSubscriptionId
            this.lessonCountInfo.lessonLeftCount = 31
            this.subscriptionPeriod =
                SubscriptionPeriod(startDate = LocalDate.now().minusDays(90), endDate = LocalDate.now().minusDays(1))
            this.subscriptionType =
                SubscriptionType(isVoiceAvailable = true, isChattingAvailable = true, isVideoAvailable = true)
        }

        // mocking
        every { studentRepository.findByIdOrNull(studentId) } returns fakeStudent
        every { tutorRepository.findByIdOrNull(tutorId) } returns fakeTutor
        every { lessonSubscriptionRepository.findByIdOrNull(lessonSubscriptionId) } returns fakeSubscription

        // then
        val exception = shouldThrow<SubscriptionExpiredException> { lessonService.startLesson(startRequest) }
        assertEquals(exception.errorCode, ErrorCode.SUBSCRIPTION_EXPIRED_ERROR)
    }

    @Test
    fun `튜터가 수강권에 명시된 언어를 수업할 수 없어 실패한다`(): Unit {
        // given
        val studentId: Long = 1
        val tutorId: Long = 1
        val lessonSubscriptionId: Long = 1
        val lessonId: Long = 1

        val startRequest = LessonInbound.StartRequest(studentId, tutorId, lessonSubscriptionId)

        // 가짜 객체 선언
        val fakeStudent = Student().apply {
            this.id = studentId
        }

        val fakeTutor = Tutor().apply {
            this.id = tutorId
            this.language = TutorLanguage.ENGLISH
            this.tutorType = TutorType(isVoiceAvailable = true, isChattingAvailable = true, isVideoAvailable = true)
        }

        val fakeSubscription = LessonSubscription().apply {
            this.id = lessonSubscriptionId
            this.language = SubscriptionLanguage.CHINESE
            this.lessonCountInfo.lessonLeftCount = 31
            this.subscriptionPeriod =
                SubscriptionPeriod(startDate = LocalDate.now().minusDays(30), endDate = LocalDate.now().plusDays(60))
            this.subscriptionType =
                SubscriptionType(isVoiceAvailable = true, isChattingAvailable = true, isVideoAvailable = true)
        }

        // mocking
        every { studentRepository.findByIdOrNull(studentId) } returns fakeStudent
        every { tutorRepository.findByIdOrNull(tutorId) } returns fakeTutor
        every { lessonSubscriptionRepository.findByIdOrNull(lessonSubscriptionId) } returns fakeSubscription

        // then
        val exception = shouldThrow<TutorNotSupportsLanguageException> { lessonService.startLesson(startRequest) }
        assertEquals(exception.errorCode, ErrorCode.TUTOR_NOT_SUPPORTS_LANGUAGE_ERROR)
    }

    @ParameterizedTest
    @CsvSource(value = ["false,true,false,true,true,true", "true,true,false,true,true,true", "false,true,false,false,true,true"])
    fun `튜터가 지원하는 수업 종류와 수강권의 수업 종류가 일치하지 않아 실패한다`(
        tutorVoiceAvailable: Boolean,
        tutorChattingAvailable: Boolean,
        tutorVideoAvailable: Boolean,
        subscriptionVoiceAvailable: Boolean,
        subscriptionChattingAvailable: Boolean,
        subscriptionVideoAvailable: Boolean,
    ): Unit {
        // given
        val studentId: Long = 1
        val tutorId: Long = 1
        val lessonSubscriptionId: Long = 1
        val startRequest = LessonInbound.StartRequest(studentId, tutorId, lessonSubscriptionId)

        every { studentRepository.findByIdOrNull(studentId) } returns Student().apply {
            this.id = studentId
        }

        every { tutorRepository.findByIdOrNull(tutorId) } returns Tutor().apply {
            this.id = tutorId
            this.tutorType = TutorType(
                isVoiceAvailable = tutorVoiceAvailable,
                isChattingAvailable = tutorChattingAvailable,
                isVideoAvailable = tutorVideoAvailable
            )
        }
        // 수강권 = 음성 o 채팅 o 화상 o
        every { lessonSubscriptionRepository.findByIdOrNull(lessonSubscriptionId) } returns LessonSubscription().apply {
            this.id = lessonSubscriptionId
            this.lessonCountInfo.lessonLeftCount = 30
            this.subscriptionType =
                SubscriptionType(
                    isVoiceAvailable = subscriptionVoiceAvailable,
                    isChattingAvailable = subscriptionChattingAvailable,
                    isVideoAvailable = subscriptionVideoAvailable
                )
        }

        // then
        val exception = shouldThrow<TutorNotSupportsLessonTypeException> { lessonService.startLesson(startRequest) }
        assertEquals(exception.errorCode, ErrorCode.TUTOR_NOT_SUPPORTS_LESSON_TYPE_ERROR)
    }

    @Test
    fun `모든 조건을 만족하여 수업이 성사된다`(): Unit {
        // given
        val studentId: Long = 1
        val tutorId: Long = 1
        val lessonSubscriptionId: Long = 1
        val lessonId: Long = 1

        val startRequest = LessonInbound.StartRequest(studentId, tutorId, lessonSubscriptionId)

        // 가짜 객체 선언
        val fakeStudent = Student().apply {
            this.id = studentId
        }

        val fakeTutor = Tutor().apply {
            this.id = tutorId
            this.tutorType = TutorType(isVoiceAvailable = true, isChattingAvailable = true, isVideoAvailable = true)
        }

        val fakeSubscription = LessonSubscription().apply {
            this.id = lessonSubscriptionId
            this.lessonCountInfo = LessonCountInfo(lessonTotalCount = 90, lessonLeftCount = 31)
            this.purchasePrice = 560000
            this.subscriptionType =
                SubscriptionType(isVoiceAvailable = true, isChattingAvailable = true, isVideoAvailable = true)
        }

        // mocking
        every { studentRepository.findByIdOrNull(studentId) } returns fakeStudent
        every { tutorRepository.findByIdOrNull(tutorId) } returns fakeTutor
        every { lessonSubscriptionRepository.findByIdOrNull(lessonSubscriptionId) } returns fakeSubscription
        every { lessonSubscriptionRepository.minusLeftCount(lessonSubscriptionId) } returns true
        every { lessonRepository.save(startRequest.toEntity()) } returns startRequest.toEntity().apply {
            this.id = lessonId
        }

        // when
        val lessonStartResponse = lessonService.startLesson(startRequest)

        // then
        verify(exactly = 1) { studentRepository.findByIdOrNull(studentId) }
        verify(exactly = 1) { tutorRepository.findByIdOrNull(tutorId) }
        verify(exactly = 2) { lessonSubscriptionRepository.findByIdOrNull(lessonSubscriptionId) }
        verify(exactly = 1) { lessonSubscriptionRepository.minusLeftCount(lessonSubscriptionId) }
        verify(exactly = 1) { lessonRepository.save(startRequest.toEntity()) }

        with(lessonStartResponse) {
            assertEquals(this.id, lessonId)
            assertEquals(this.studentId, studentId)
            assertEquals(this.tutorId, tutorId)
            assertEquals(this.lessonSubscriptionId, lessonSubscriptionId)
        }
    }

    // ============================== [수업 종료 로직 테스트] ==============================
    @ParameterizedTest
    @CsvSource(value = ["1,1", "2,1"])
    fun `존재하지 않는 수업에 대해서 요청하는 경우 실패한다`(lessonId: Long, tutorId: Long): Unit {
        // given: lessonId로 조회 시 null 혹은 isDeleted = true 인 경우의 실패 테스트를 수행한다
        val endRequest = LessonInbound.EndRequest(lessonId = lessonId, tutorId = tutorId)

        every { lessonRepository.findByIdOrNull(1) } returns null
        every { lessonRepository.findByIdOrNull(2) } returns Lesson().apply {
            this.id = 2
            this.isDeleted = true
        }

        // then
        val exception = shouldThrow<ResourceNotFoundException> { lessonService.endLesson(endRequest) }
        assertEquals(exception.errorCode, ErrorCode.LESSON_NOT_FOUND_ERROR)
    }

    @ParameterizedTest
    @CsvSource(value = ["1,2"])
    fun `수업에 배정된 튜터가 요청한게 아니라면 실패한다`(lessonId: Long, tutorId: Long): Unit {
        // given: tutorId로 조회 시 null 혹은 isDeleted = true 인 경우의 실패 테스트를 수행한다
        val endRequest = LessonInbound.EndRequest(lessonId = lessonId, tutorId = tutorId)

        every { lessonRepository.findByIdOrNull(lessonId) } returns Lesson().apply {
            this.id = lessonId
            this.tutorId = 1
        }

        // then
        val exception = shouldThrow<UnauthorizedTutorException> { lessonService.endLesson(endRequest) }
        assertEquals(exception.errorCode, ErrorCode.UNAUTHORIZED_TUTOR_ERROR)
    }

    @ParameterizedTest
    @CsvSource(value = ["1,1"])
    fun `이미 종료된 수업에 대해서 종료를 요청하면 실패한다`(lessonId: Long, tutorId: Long): Unit {
        // given: 이미 종료된 수업이면 예외를 일으킨다
        val endRequest = LessonInbound.EndRequest(lessonId = lessonId, tutorId = tutorId)

        every { lessonRepository.findByIdOrNull(lessonId) } returns Lesson().apply {
            this.id = lessonId
            this.tutorId = tutorId
            this.lessonTime.finishedAt = LocalDateTime.now()
        }

        // then
        val exception = shouldThrow<LessonAlreadyFinishedException> { lessonService.endLesson(endRequest) }
        assertEquals(exception.errorCode, ErrorCode.LESSON_ALREADY_FINISHED_ERROR)
    }

    @ParameterizedTest
    @CsvSource(value = ["1,1", "2,2"])
    fun `실제 존재하지 않는 튜터인 경우 실패한다`(lessonId: Long, tutorId: Long): Unit {
        // given: tutorId로 식별되는 튜터가 실제로 존재하지 않는 경우 실패한다
        val endRequest = LessonInbound.EndRequest(lessonId = lessonId, tutorId = tutorId)

        every { lessonRepository.findByIdOrNull(lessonId) } returns Lesson().apply {
            this.id = lessonId
            this.tutorId = tutorId
        }

        // 튜터는 null 이거나 isDeleted = true 이면 실패한다
        every { tutorRepository.findByIdOrNull(1) } returns null
        every { tutorRepository.findByIdOrNull(2) } returns Tutor().apply {
            this.isDeleted = true
        }

        // then
        val exception = shouldThrow<ResourceNotFoundException> { lessonService.endLesson(endRequest) }
        assertEquals(exception.errorCode, ErrorCode.TUTOR_NOT_FOUND_ERROR)
    }

    @ParameterizedTest
    @CsvSource(value = ["1,1", "2,2"])
    fun `모든 조건을 만족하여 수업 종료가 성립한다`(lessonId: Long, tutorId: Long): Unit {
        // given
        val endRequest = LessonInbound.EndRequest(lessonId = lessonId, tutorId = tutorId)
        val fakeLesson = Lesson().apply {
            this.id = lessonId
            this.tutorId = tutorId
            this.tutorRevenue = 5000
        }
        val finishedTimeUpdatedLesson = Lesson().apply {
            this.id = lessonId
            this.tutorId = tutorId
            this.lessonTime.finishedAt = LocalDateTime.now()
            this.tutorRevenue = 5000
        }
        val fakeTutor = Tutor().apply {
            this.id = tutorId
        }

        every { lessonRepository.findByIdOrNull(lessonId) } returns fakeLesson
        every { tutorRepository.findByIdOrNull(tutorId) } returns fakeTutor
        every { lessonRepository.save(fakeLesson.updateFinishedTime()) } returns finishedTimeUpdatedLesson

        // save 메소드를 mock 하는 과정에서 finishedAt이 now()로 초기화되는 현상을 막기 위한 코드
        fakeLesson.lessonTime.finishedAt = null

        // when
        val finishedLesson = lessonService.endLesson(endRequest)

        // then
        verify(exactly = 2) { lessonRepository.findByIdOrNull(lessonId) }
        verify(exactly = 1) { tutorRepository.findByIdOrNull(tutorId) }
        verify(exactly = 1) { tutorRepository.settleAmountByIdAndAmount(tutorId, fakeLesson.tutorRevenue) }
        verify(exactly = 1) { lessonRepository.save(fakeLesson.updateFinishedTime()) }
        with(finishedLesson) {
            assertEquals(this.id, lessonId)
            assertEquals(this.tutorId, tutorId)
        }
    }
}