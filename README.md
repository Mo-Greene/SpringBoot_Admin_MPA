# AdminMPA 
## 2023/04/02~04/21 (1차 기능구현 완)

## commit 컨벤션
- feat : 새로운 기능 추가
- fix : 버그 수정
- docs : 문서 수정
- style : 코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우
- refactor : 코드 리펙토링
- test : 테스트 코드, 리펙토링 테스트 코드 추가
- chore : 빌드 업무 수정, 패키지 매니저 수정

### 이미지 게시판(갤러리) 진행
#### 필요한것
- 이미지 업로드(이미지만 업로드할것인지 생각)
- 업로드 후 사진 다운로드는 굳이 안해도 될듯
- 일단 파일업로드와 매우 유사한 구조로 진행됨
- 이 후 과정은 로컬에 있는 데이터를 가져와 자바스크립트에서 뿌려주면 완료

#### 완성이후
- 1차 adminMPA 완성
- 이 후 화면구현
- 예외처리, 발리데이션 까지 프론트, 백 전부
- 예상 목표 5월 8일
- 이미지게시판 예상목표 이번주 금요일

#### todo 중요
- 게시판 동적쿼리 중복이니 뽑아내서 사용가능하게
- AND category_board LIKE '' 이부분을 #{}이걸로 고치면 될듯
- AttacheDTO를 ImageDTO를 추가하여 만들어서 분리할지 생각해야됨

### html 저장용
```

                        <!-- 리스트 -->
                        <div id="gallery-table">
                            <table class="table text-center table-hover">
                                <thead class="table-light">
                                <tr>
                                    <th style="width: 50%">제목</th>
                                    <th>작성자</th>
                                    <th>조회수</th>
                                    <th>등록일시</th>
                                    <th>수정일시</th>
                                </tr>
                                </thead>
                                <tbody th:with="link = ${pageRequestDTO.getLink()}">
                                <tr th:each="list : ${galleryList}">
                                    <td>
                                        <a th:href="|@{/gallery/{boardNo}(boardNo=${list.boardNo})}?${link}|">
                                            [[ ${list.boardTitle} ]]
                                        </a>
                                        <div>
                                            <img style="width:100px" th:src="@{/view/{boardNo}(boardNo=${list.boardNo})}">
                                        </div>
                                    </td>
                                    <td>[[ ${list.boardWriter} ]]</td>
                                    <td>[[ ${list.boardView} ]]</td>
                                    <td>[[ ${#dates.format(list.boardRegDate, 'yy.MM.dd HH:mm')} ]]</td>
                                    <td th:text="${list.boardModDate == null ? '-' : #dates.format(list.boardModDate, 'yy.MM.dd HH:mm')}"></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
```
