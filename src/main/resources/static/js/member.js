//검색 모달 여닫
function openSearchModal() {
    document.getElementById('searchModal').style.display = 'block';
}

function closeSearchModal() {
    document.getElementById('searchModal').style.display = 'none';
    document.getElementById('searchResult').innerHTML = '';
}


function MemberSearch() {
    var key = document.getElementById('searchKey').value;
    var val = document.getElementById('searchValue').value;

    if (!val) {
        alert('검색어를 입력하세요.');
        return;
    }
    var xhr = new XMLHttpRequest();
    xhr.open('GET', '/membersearch?searchKey=' + encodeURIComponent(key) + '&searchValue=' + encodeURIComponent(val));
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var members = JSON.parse(xhr.responseText);
            if(members.length === 0){
                alert('일치하는 회원이 없습니다.');
            } else {
                closeSearchModal();
                renderMemberTable(members);
            }
        }
    };
    xhr.send();
}

// 검색 결과로 테이블 갱신


function renderMemberTable(members) {
    var tbody = document.querySelector('table tbody');
    var html = '';
    for(var i=0; i<members.length; i++) {
        var m = members[i];
        html += '<tr>'
             + '<td class="align_center"><a href="/mypinfo?id=' + m.id + '">' + m.id + '</a></td>'
             + '<td class="align_center">' + m.name + '</td>'
             + '<td class="align_center">' + m.phone + '</td>'
             + '<td class="align_center">' + (m.birth ? m.birth.substring(0,10) : '') + '</td>'
             + '<td class="align_center"><a href="/memberupdate?id=' + m.id + '" class="editBtn">수정</a></td>'
             + '<td class="align_center">'
             + '<form action="/memberdelete" method="post" class="deleteForm" style="display:inline;">'
             + '<input type="hidden" name="id" value="' + m.id + '">'
             + '<button type="button" class="deleteBtn" id="deleteBtn">삭제</button>'
             + '</form></td>'
             + '</tr>';
    }
    tbody.innerHTML = html;

    // 삭제 버튼 이벤트 재등록
    document.querySelectorAll('.deleteBtn').forEach(function(btn) {
        btn.addEventListener('click', function() {
            var form = btn.closest('form');
            showDeleteModal(form);
        });
    });
}
   
//회원 정보 수정 및 삭제 부분
//삭제 버튼 클릭시 alert
let deleteFormToSubmit = null;

document.addEventListener('DOMContentLoaded', function() {
    var deleteBtns = document.querySelectorAll('.deleteBtn');
    var deleteModal = document.getElementById('deleteModal');
    var deleteYesBtn = document.getElementById('deleteYesBtn');
    var deleteNoBtn = document.getElementById('deleteNoBtn');
    
    deleteBtns.forEach(function(btn) {
        btn.addEventListener('click', function() {
            showDeleteModal(this.closest('form'));
        });
    });

    if (deleteYesBtn) {
        deleteYesBtn.onclick = function() {
            if (deleteFormToSubmit) {
                deleteFormToSubmit.submit();
            }
            if (deleteModal) deleteModal.style.display = 'none';
        };
    }
    if (deleteNoBtn) {
        deleteNoBtn.onclick = function() {
            deleteFormToSubmit = null;
            if (deleteModal) deleteModal.style.display = 'none';
        };
    }
});

function showDeleteModal(form) {
    deleteFormToSubmit = form;
    var deleteModal = document.getElementById('deleteModal');
    if (deleteModal) deleteModal.style.display = 'block';
}

 