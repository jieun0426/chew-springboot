 document.addEventListener("DOMContentLoaded", function () {
     const imageList = document.querySelector("#imageList");
     const images = document.querySelectorAll("#imageList li img");
     const prevButton = document.querySelector(".prev");
     const nextButton = document.querySelector(".next");

     let currentIndex = 0;
     const totalImages = images.length;

     function updateSlide() {
         const slideWidth = images[0].parentElement.offsetWidth + 20; // li의 width 기준
         const offset = -currentIndex * slideWidth;
         imageList.style.transform = `translateX(${offset}px)`;
     }

     prevButton.addEventListener("click", function () {
         currentIndex = (currentIndex - 1 + totalImages) % totalImages;
         updateSlide();
     });

     nextButton.addEventListener("click", function () {
         currentIndex = (currentIndex + 1) % totalImages;
         updateSlide();
     });

     updateSlide();

     // 모달 확대 이미지 관련
     const modal = document.getElementById("imageModal");
     const modalImg = document.getElementById("modalImage");
     const closeBtn = document.querySelector(".close");
     let scale = 1;

     // 모든 이미지에 클릭 이벤트 바인딩
     images.forEach(img => {
         img.addEventListener("click", () => {
             modal.style.display = "block";
             modalImg.src = img.src;
             scale = 1;
             modalImg.style.transform = 'scale(1)';
         });
     });

     // 휠로 확대/축소
     modalImg.addEventListener("wheel", function (e) {
         e.preventDefault();
         const zoomStep = 0.1;
         if (e.deltaY < 0) {
             scale = Math.min(scale + zoomStep, 5);
         } else {
             scale = Math.max(scale - zoomStep, 1);
         }
         modalImg.style.transform = `scale(${scale})`;
     });

     // 닫기 버튼
     closeBtn.addEventListener("click", () => {
         modal.style.display = "none";
     });

     // 모달 외부 클릭 시 닫기
     modal.addEventListener("click", (e) => {
         if (e.target === modal) {
             modal.style.display = "none";
         }
     });
 });