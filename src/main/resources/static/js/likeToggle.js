$(function () {
    const storecodeInput = document.getElementById("storecode");
    const heartInput = document.getElementById("heart");
    const likeCountElement = document.getElementById("likeCount");

    if (!storecodeInput || !heartInput || !likeCountElement) return;

    const storecode = parseInt(storecodeInput.value, 10);
    const contextPath = "";  // 또는 '/yourContextPath' 로 수정
    const checkUrl = contextPath + "/like/check";
    const toggleUrl = contextPath + "/like/toggle";

    //  CSRF 토큰 값 가져오기
    const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    //  좋아요 상태 확인 (로그인 안 되어 있으면 그냥 무시)
    fetch(checkUrl, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            [header]: token
        },
        body: JSON.stringify({ storecode }),
        credentials: "include"
    })
    .then(response => {
        if (!response.ok) return null; // 로그인 안 된 경우 등
        return response.json();
    })
    .then(data => {
        if (!data) return; // 응답 실패 시 아무것도 안 함
        if (data.success) {
            heartInput.checked = data.liked;
            likeCountElement.textContent = data.storelikes ?? 0;
        }
    })
    .catch(() => {
        // 네트워크 오류 무시
    });

    //  좋아요 토글 (실제 클릭 시 로그인 체크)
    heartInput.addEventListener("change", function () {
        fetch(toggleUrl, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                [header]: token
            },
            body: JSON.stringify({ storecode }),
            credentials: "include"
        })
        .then(response => response.json())
        .then(data => {
            if (!data.success) {
                if (data.message?.includes("로그인")) {
                    alert("로그인이 필요합니다.");
                    heartInput.checked = false;
                    return;
                }
                alert(data.message || "좋아요 처리 중 오류 발생");
                heartInput.checked = !heartInput.checked;
            } else {
                likeCountElement.textContent = data.storelikes ?? 0;
            }
        })
        .catch(error => {
            console.error(" 좋아요 토글 실패:", error);
            alert("서버 오류 발생");
            heartInput.checked = !heartInput.checked;
        });
    });
});
