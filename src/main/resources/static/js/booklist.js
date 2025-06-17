$(document).ready(function(){
    const token = $("meta[name='_csrf']").attr("content");
    const header = $("meta[name='_csrf_header']").attr("content");

    // 모든 AJAX 요청에 CSRF 토큰 포함
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });

    $("#allCheck").change(function(){
        $(".deleteCheckbox").prop("checked", $(this).prop("checked"));
    });

    $(".deleteBtn").click(function(){
        // 체크된 체크박스들의 값을 배열로 수집
        var checkedValues = [];

        $(".deleteCheckbox:checked").each(function() {
            checkedValues.push($(this).val());
        });

        // 만약 선택된 항목이 있다면
        if (checkedValues.length > 0) {
            // AJAX 요청으로 서버로 데이터 전송
            $.ajax({
                type: "POST",          // POST 방식으로 요청
                url: "/deleteBookingSelectedItems",  // 서버의 URL을 여기에 입력
                data: { ids: checkedValues.join(',') },  // 체크된 체크박스의 값 배열을 전송
                success: function(response) {
                    // 서버로부터 응답이 오면 실행할 코드
                    console.log("예약삭제 성공:", response);
                    // 응답에 따라 처리 (예: 삭제된 항목 목록 갱신 등)
                    alert("예약이 삭제되었습니다");
                    location.href='booklist';
                },
                error: function(xhr, status, error) {
                    console.log("오류:", error);
                    console.log(checkedValues)
                }
            });
        } else {
            alert("삭제할 항목을 선택해주세요.");
        }
    });
});