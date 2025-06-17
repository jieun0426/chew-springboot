//삭제시 알림창
function confirmDelete() {
    return confirm("해당 리뷰를 삭제하시겠습니까?");
}

// 수정 버튼 클릭 시 showEdit 실행되도록 연결
document.addEventListener("DOMContentLoaded", () => {
    document.querySelectorAll(".edit-button").forEach(button => {
        button.addEventListener("click", () => {
            const id = button.dataset.id;
            const storecode = button.dataset.storecode;
            const title = button.dataset.title;
            const content = button.dataset.content;
            const stars = button.dataset.stars;

            showEdit(id, storecode, title, content, stars);
        });
    });
});


function showEdit(id, storecode, title, content, stars) {
    const form = document.querySelector(".review_form");

    // 리뷰 데이터 채우기
    form.querySelector("input[name='title']").value = title;
    form.querySelector("textarea[name='content']").value = content;
    form.querySelector("input[name='storecode']").value = storecode;

    // 별점 선택 (기존 선택 초기화 후 체크)
    const starsInputs = form.querySelectorAll("input[name='stars']");
    starsInputs.forEach(input => {
        input.checked = Number(input.value) === Math.round(stars);
    });

    // 숨겨진 ID 필드 추가 (없으면 생성)
    let idInput = form.querySelector("input[name='id']");
    if (!idInput) {
        idInput = document.createElement("input");
        idInput.type = "hidden";
        idInput.name = "id";
        form.appendChild(idInput);
    }
    idInput.value = id;

    // action을 수정용으로 변경
    form.action = "/review/update";

    // 버튼 텍스트 변경
    const submitBtn = form.querySelector("button[type='submit']");
    submitBtn.textContent = "리뷰 수정하기";

    // 수정 폼으로 부드럽게 스크롤 이동
        form.scrollIntoView({ behavior: 'smooth', block: 'start' });
}
