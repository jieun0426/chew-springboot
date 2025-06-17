$(document).ready(function(){

            var header = $("meta[name='_csrf_header']").attr("content");
            var token = $("meta[name='_csrf']").attr("content");

            $.ajaxSetup({
                beforeSend: function(xhr) {
                    xhr.setRequestHeader(header, token);
                }
            });

            //var loginstate = /*[[${#authorization.expression('isAuthenticated()')}]]*/ false;
            //var id = /*[[${#authentication.name}]]*/ 'anonymous';

            const body = document.querySelector('body');
            const loginstate = body.dataset.loginstate === 'true';
            const id = body.dataset.id;

            console.log(loginstate);

            $(document).on("click", "#writeBtn", function(){
                if(loginstate){
                    location.href='FAQin';
                }else{
                    alert("로그인 후 작성 가능합니다");
                }
            });

            $(document).on("change", "#myFAQ", function(){
                const check=$(this).prop('checked');

                console.log("check:"+check+", id:"+id+", loginstate:"+loginstate);
                if(id!='anonymousUser'){
                    $.ajax({
                        url: "/myFAQ",
                        method: "POST",
                        contentType: "application/json; charset=utf-8", //JSON 타입 명시
                        data: JSON.stringify({  //JSON 문자열로 변환해서 보냄
                            "check":check,
                             "page": 0
                        }),
                        success: function (response) {
                            const qlist = response.qlist;
                            let html = "";

                            if (qlist.length === 0) {
                                html += "<tr><td colspan='4'>등록된 FAQ가 없습니다</td></tr>";
                            } else {
                                qlist.forEach(q => {
                                    const isSecret = q.secret === 1;
                                    html += "<tr>";

                                    html += `<td>${q.state}</td>`;

                                    if (isSecret) {
                                        html += `<td><button class="secretBtn" value="${q.num}">${q.title} 🔒</button></td>`;
                                    } else {
                                        html += `<td><button class="titleBtn" value="${q.num}">${q.title}</button></td>`;
                                    }

                                    html += `<td>${q.id}</td>`;
                                    html += `<td>${q.wdate}</td>`;
                                    html += '<td>';
                                    html += `<a href="/deleteItem?num=${q.num}" >`;
                                    html += '<i class="fa-solid fa-trash-can" style="color: #ED751C;"></i>';
                                    html += '</a>';
                                    html += '</td>';

                                    html += "</tr>";
                                });
                            }

                            $("#FAQ_table_body").html(html); // tbody에 붙여넣기

                            // === 페이징 UI 생성 ===
                            const nowpage = response.nowpage;
                            const totalPage = response.totalPage;
                            const pageBlockSize = response.pageBlockSize;
                            const nowPageBlock = response.nowPageBlock;

                            let paginationHtml = '<ul style="display: flex; list-style: none; gap: 10px;">';

                            // 이전 블록
                            if (nowPageBlock > 0) {
                                const prevPage = (nowPageBlock - 1) * pageBlockSize;
                                paginationHtml += `<li><a href="/FAQ?page=${(nowPageBlock - 1) * pageBlockSize}&check=${check}" class="page-link" data-page="${prevPage}">&laquo; 이전</a></li>`;
                            }

                            // 페이지 번호들
                            const startPage = nowPageBlock * pageBlockSize;
                            const endPage = Math.min((nowPageBlock + 1) * pageBlockSize - 1, totalPage - 1);

                            for (let pageNum = startPage; pageNum <= endPage; pageNum++) {
                                if (pageNum !== nowpage) {
                                    paginationHtml += `<li><a href="/FAQ?page=${pageNum}&check=${check}" class="page-link" data-page="${pageNum}">${pageNum + 1}</a></li>`;
                                } else {
                                    paginationHtml += `<li><span style="font-weight: bold; color: red;">${pageNum + 1}</span></li>`;
                                }
                            }

                            // 다음 블록
                            if ((nowPageBlock + 1) * pageBlockSize < totalPage) {
                                const nextPage = (nowPageBlock + 1) * pageBlockSize;
                                paginationHtml += `<li><a href="/FAQ?page=${(nowPageBlock + 1) * pageBlockSize}&check=${check}" class="page-link" data-page="${nextPage}">다음 &raquo;</a></li>`;
                            }

                            paginationHtml += '</ul>';

                            $(".pagination").html(paginationHtml);
                        },
                        error: function () {
                            alert("리스트를 불러오는 데 실패했습니다.");
                        }
                    });
                }else{
                    alert("로그인 후 사용가능");
                    $(this).prop('checked',false);
                }
            });

            $(document).on("click", ".secretBtn", function(){
                var qnum = $(this).val();
                var $currentRow = $(this).closest("tr");

                // 이미 존재하는 댓글 tr이 있으면 제거 (토글 효과)
                if ($currentRow.next().hasClass("replyRow")) {
                    $currentRow.next().remove();
                    return;
                }

                $.ajax({
                    url: "/faq_secretBtn_click",
                    method: "POST",
                    data: {
                        "qnum": qnum
                    },
                    success: function (data) {
                        if(data=="fail"){
                            alert("작성자만 볼 수 있습니다.");
                        }else{
                            var replyRow = $("<tr class='replyRow'><td colspan='4'>" + data + "</td></tr>");
                            $currentRow.after(replyRow);
                        }
                    },
                    error: function () {
                        alert("리스트를 불러오는 데 실패했습니다.");
                    }
                });
            });

            $(document).on("click", ".titleBtn", function(){
                var qnum = $(this).val();
                //var loginid = id;
                var $currentRow = $(this).closest("tr");

                // 이미 존재하는 댓글 tr이 있으면 제거 (토글 효과)
                if ($currentRow.next().hasClass("replyRow")) {
                    $currentRow.next().remove();
                    return;
                }

                $.ajax({
                    url: "/faq_titleBtn_click",
                    method: "POST",
                    data: {
                        "qnum": qnum
                    },
                    success: function (data) {
                        var replyRow = $("<tr class='replyRow'><td colspan='4'>" + data + "</td></tr>");
                        $currentRow.after(replyRow);
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        alert("리스트를 불러오는 데 실패했습니다. 상태: " + jqXHR.status);
                        console.error("에러", textStatus, errorThrown);
                    }
                });
            });

            $(document).on("click", ".reply_inputBtn", function(){
                var qnum = $(this).data("qnum");
                var loginid = id;
                var $currentRow = $(this).closest("tr");

                // 이미 존재하는 댓글 tr이 있으면 제거
                if ($currentRow.next().hasClass("replyRow")) {
                    $currentRow.next().remove();
                    return;
                }

                $.ajax({
                    url: "/faq_reply_input",
                    method: "POST",
                    data: {
                        "qnum": qnum,
                        "id": loginid
                    },
                    success: function (data) {
                        var replyRow = $("<tr class='replyRow'><td colspan='4'>" + data + "</td></tr>");
                        $currentRow.after(replyRow);
                    },
                    error: function () {
                        alert("리스트를 불러오는 데 실패했습니다.");
                    }
                });
            });

            $(document).on("submit", "#replyForm", function(e){
                e.preventDefault(); // 기본 제출 막기

                var formData = $(this).serialize();

                $.ajax({
                    url: "/faq_save_reply",
                    method: "POST",
                    data: formData,
                    success: function(response){
                        alert("답변이 등록되었습니다!");
                        // 리스트를 새로고침하거나 해당 tr을 다시 렌더링
                        location.reload();
                    },
                    error: function(){
                        alert("답변 등록 실패");
                    }
                });
            });

            $(document).on("click", ".reply_updateBtn", function(){
                var qnum = $(this).data("qnum");
                var loginid = id;
                var $currentRow = $(this).closest("tr");

                // 이미 존재하는 댓글 tr이 있으면 제거
                if ($currentRow.next().hasClass("replyRow")) {
                    $currentRow.next().remove();
                    return;
                }

                $.ajax({
                    url: "/faq_reply_updateForm",
                    method: "POST",
                    data: {
                        "qnum": qnum,
                        "id": loginid
                    },
                    success: function (data) {
                        var replyRow = $("<tr class='replyRow'><td colspan='4'>" + data + "</td></tr>");
                        $currentRow.after(replyRow);
                    },
                    error: function () {
                        alert("리스트를 불러오는 데 실패했습니다.");
                    }
                });
            });

            $(document).on("submit", "#replyUpdateForm", function(e){
                e.preventDefault(); // 기본 제출 막기

                var formData = $(this).serialize();

                $.ajax({
                    url: "/faq_reply_update",
                    method: "POST",
                    data: formData,
                    success: function(response){
                        alert("답변이 수정되었습니다!");
                        // 리스트를 새로고침하거나 해당 tr을 다시 렌더링
                        location.reload();
                    },
                    error: function(){
                        alert("답변 등록 실패");
                    }
                });
            });
        });