$(function () {
          const modal = $('#bookingModal');
          const openBtn = $('#openModalBtn');
          const closeBtn = $('.close');

          // 로그인 체크 후 모달 열기
          openBtn.on('click', function (e) {
            e.preventDefault();
            $.ajax({
              url: '/logincheck',
              type: 'GET',
              success: function (result) {
                if (result === 'ok') {
                  modal.fadeIn();
                  $('.modal-content').on('click', function (e) {
                    e.stopPropagation();
                  });
                } else {
                  alert('로그인이 필요합니다.');
                  window.location.href = '/loginput';
                }
              },
              error: function () {
                alert('로그인 체크 실패');
              }
            });
          });

          // 닫기 버튼
          closeBtn.on('click', function () {
            modal.fadeOut();
          });

          // 외부 클릭 시 닫기
          $(window).on('click', function (e) {
            if ($(e.target).is(modal)) {
              modal.fadeOut();
            }
          });

          // 인원수 선택
          $('#personOptions .people-btn').on('click', function () {
            $('#personOptions .people-btn').removeClass('active');
            $(this).addClass('active');
            $('#saramsu').val($(this).data('value'));
          });

          // 시간 선택
          $('.time-btn').on('click', function () {
            $('.time-btn').removeClass('selected');
            $(this).addClass('selected');
            $('#bookingtime').val($(this).data('time'));
          });

          // 예약 폼 전송
          $('#bookingForm').on('submit', function (e) {
            e.preventDefault();
            const saramsu = $('#saramsu').val();
            const bookingdate = $('#bookingdate').val();
            const bookingtime = $('#bookingtime').val();

            if (!saramsu || !bookingdate || !bookingtime) {
              alert('입력을 완료해주세요.');
              return;
            }

            const timePattern = /^([01]\d|2[0-3]):([0-5]\d)$/;
            if (!timePattern.test(bookingtime)) {
              alert('시간 형식 오류');
              return;
            }

            $.ajax({
              url: '/bookingsave',
              type: 'POST',
              data: $('#bookingForm').serialize(),
              success: function (result) {
                if (result === 'success') {
                  alert('예약 성공!');
                  modal.fadeOut();
                } else if (result === 'login_required') {
                  alert('로그인이 필요합니다.');
                  window.location.href = '/loginput';
                } else if (result.startsWith('error:')) {
                  alert('서버 오류: ' + result.split(':')[1]);
                } else {
                  alert('예약 실패. 다시 시도해주세요.');
                }
              },
              error: function (xhr) {
                alert('예약 요청 실패: ' + xhr.status);
              }
            });
          });
        });