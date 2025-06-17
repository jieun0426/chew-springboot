document.addEventListener('DOMContentLoaded', function () {
  const slider = document.getElementById('slider');
  const container = document.getElementById('slider-container');

  if (!slider) {
    console.error("슬라이더 요소를 찾을 수 없습니다!");
    return;
  }

  const totalSlides = slider.children.length;
  let currentIndex = 0;
  let intervalId;

  function startSlide() {
    if (intervalId) return;
    intervalId = setInterval(() => {
      currentIndex = (currentIndex + 1) % totalSlides;
      slider.style.transform = `translateX(-${currentIndex * 100}%)`;
    }, 5000); // 5초마다 부드럽게 넘어감
  }

  function stopSlide() {
    clearInterval(intervalId);
  }

  startSlide();

  // 마우스 올리면 멈추고, 나가면 다시 재생
  container.addEventListener('mouseenter', stopSlide);
  container.addEventListener('mouseleave', startSlide);
});
