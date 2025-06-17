document.addEventListener("DOMContentLoaded", function () {
    const mapElement = document.getElementById('map');
    const lat = parseFloat(mapElement.dataset.lat);
    const lng = parseFloat(mapElement.dataset.lng);

    if (!isNaN(lat) && !isNaN(lng)) {
        const mapOption = {
            center: new kakao.maps.LatLng(lat, lng),
            level: 2
        };
        const map = new kakao.maps.Map(mapElement, mapOption);
        const marker = new kakao.maps.Marker({ position: new kakao.maps.LatLng(lat, lng) });
        marker.setMap(map);
    } else {
        console.error("잘못된 위치 정보입니다.");
    }
});
