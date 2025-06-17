let accumulatedFiles = [];

$(document).ready(function() {
    // 파일 선택 변경 시
    $('#storeimage').on('change', function(e) {
        // 새로 선택된 파일들
        const newFiles = Array.from(e.target.files);
        
        // 파일 개수 체크 (옵션)
        if (accumulatedFiles.length + newFiles.length > 4) {
            alert('최대 4개만 업로드해주십쇼.');
            return;
        }
        
        // 누적 배열에 추가
        accumulatedFiles = accumulatedFiles.concat(newFiles);
        
        // 파일 목록 표시 업데이트
        updateFileList();
        
        // input 초기화 (같은 파일 다시 선택 가능)
        $(this).val('');
    });
    
    // 폼 제출 시
    $('#uploadForm').on('submit', function(e) {
        e.preventDefault(); // 폼 기본 제출 방지
        
        if (accumulatedFiles.length === 0) {
            alert('파일선택');
            return;
        }
        
        // FormData 생성 및 파일 외 데이터 추가
        const formData = new FormData();
        formData.append('storecode', $('#storecode').val());
        formData.append('storename', $('#storename').val());
        formData.append('storeaddress', $('#storeaddress').val());
        formData.append('storecategory', $('#storecategory').val());
        formData.append('storearea', $('#storearea').val());
        
        // 누적된 모든 파일 추가
        accumulatedFiles.forEach(file => {
            formData.append('storeimage', file);
        });
        
       
        $.ajax({
            url: 'storesave',
            method: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            success: function(res) {
                alert('이미지 업로드 성공');
                // 성공 시 파일 배열 초기화
                accumulatedFiles = [];
                // 폼 초기화
                $('#uploadForm')[0].reset();
                // 파일 목록 초기화
                $('#uploadimg').empty();
                // 페이지 이동 (선택사항)
                window.location.href = 'sout';
            },
            error: function(xhr) {
                alert('업로드 실패: ' + xhr.responseText);
            }
        });
    });
    
    // 파일 삭제 기능 (이벤트 위임)
    $(document).on('click', '.file-delete', function() {
        const index = $(this).data('index');
        accumulatedFiles.splice(index, 1);
        updateFileList();
    });
});

// 파일 목록 표시 함수
function updateFileList() {
    $('#uploadimg').empty();
    
    accumulatedFiles.forEach((file, index) => {
        $('#uploadimg').append(`
            <li>
                <div>${file.name} (${formatFileSize(file.size)})
                    <button type="button" class="file-delete" data-index="${index}">삭제</button>
                </div>
            </li>
        `);
    });
}

// 파일 크기 포맷 함수
function formatFileSize(bytes) {
    if (bytes < 1024) return bytes + ' bytes';
    else if (bytes < 1048576) return (bytes / 1024).toFixed(1) + ' KB';
    else return (bytes / 1048576).toFixed(1) + ' MB';
}